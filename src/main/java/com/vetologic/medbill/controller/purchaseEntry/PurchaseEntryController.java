package com.vetologic.medbill.controller.purchaseEntry;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryBean;
import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryItemBean;
import com.vetologic.medbill.beans.response.MedbillResponse;
import com.vetologic.medbill.beans.stock.StockBean;
import com.vetologic.medbill.beans.stock.StockItemBean;
import com.vetologic.medbill.controller.productCategoryMaster.ProductCategoryMasterController;
import com.vetologic.medbill.models.service.purchaseEntry.PurchaseEntryService;
import com.vetologic.medbill.models.service.stock.StockService;
import com.vetologic.medbill.utils.AppUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("purchaseEntry")
public class PurchaseEntryController {

	private static Logger log = LoggerFactory.getLogger(ProductCategoryMasterController.class);

	@Autowired
	private PurchaseEntryService purchaseEntryService;

	@Autowired
	private StockService stockService;

	@PostMapping(path = "/addPurchaseEntry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse addPurchaseEntry(@RequestBody PurchaseEntryBean purchaseEntryBean, StockBean stockBean,
			MedbillResponse medbillResponse) {
		try {
			purchaseEntryBean.setDeletionFlag(0);
			purchaseEntryBean.setCreatedDate(AppUtil.currentDateWithTime());
			int purchaseEntryId = purchaseEntryService.save(purchaseEntryBean);

			List<StockItemBean> stockList = new ArrayList<>();
			if (purchaseEntryId != 0) {
				purchaseEntryBean.setPurchaseEntryId(purchaseEntryId);
				for (PurchaseEntryItemBean purchaseItems : purchaseEntryBean.getPurchaseEntryList()) {
					purchaseItems.setCreatedDate(AppUtil.currentDateWithTime());
					purchaseItems.setPurchaseEntryId(purchaseEntryBean);
					purchaseItems.setDeletionFlag(0);
					int listId = purchaseEntryService.save(purchaseItems);
					if (listId > 0) {
						StockItemBean stocks = new StockItemBean();
						purchaseItems.setPurchaseEntryItemId(listId);
						stocks.setPurcItemBean(purchaseItems);
						stocks.setAmount(purchaseItems.getAmount());
						stocks.setBatchNumber(purchaseItems.getBatchNumber());
						stocks.setExpiryDate(purchaseItems.getExpiryDate());
						stocks.setManufactureDate(purchaseItems.getManufactureDate());
						stocks.setManufacturer(purchaseItems.getManufacturer());
						stocks.setPackaging(purchaseItems.getPackaging());
						stocks.setProductName(purchaseItems.getProductName());
						stocks.setProductType(purchaseItems.getProductType());
						stocks.setQuantity(purchaseItems.getQuantity());
						stocks.setUnitPrice(purchaseItems.getUnitPrice());
						stockList.add(stocks);
					}
				}
				log.info("Saved Sucessfully & Saved Purchase Id is: " + purchaseEntryId);

				stockBean.setDeletionFlag(0);
				stockBean.setCreatedDate(AppUtil.currentDateWithTime());
				stockBean.setOrderNumber(purchaseEntryBean.getOrderNumber());
				stockBean.setPurchaseEntryDiscount(purchaseEntryBean.getPurchaseEntryDiscount());
				stockBean.setPurchaseEntrySubTotal(purchaseEntryBean.getPurchaseEntrySubTotal());
				stockBean.setPurchaseEntryTax(purchaseEntryBean.getPurchaseEntryTax());
				stockBean.setPurchaseEntryTotal(purchaseEntryBean.getPurchaseEntryTotal());
				stockBean.setReceivedDate(purchaseEntryBean.getReceivedDate());
				// stockBean.setStockList(purchaseEntryBean.getStockList());
				stockBean.setSupplierInvoiceNumber(purchaseEntryBean.getSupplierInvoiceNumber());
				stockBean
						.setPurchaseEntryDiscountInPercentage(purchaseEntryBean.getPurchaseEntryDiscountInPercentage());
				int stockId = stockService.save(stockBean);
				if (stockId != 0) {
					for (StockItemBean stockItems : stockList) {
						stockItems.setCreatedDate(AppUtil.currentDateWithTime());
						stockItems.setStockId(stockBean);
						stockItems.setDeletionFlag(0);
						purchaseEntryService.save(stockItems);
					}
					log.info("Saved Sucessfully & Saved Purchase Id is: " + stockId);
				} else {
					medbillResponse.setSuccess(false);
					medbillResponse.setMessage("Saved UnSucessfully");
					log.info("Saved UnSucessfully");
					// purchase deletion logic here..!
					if (purchaseEntryService.deletePurchaseEntry(purchaseEntryBean)) {
						medbillResponse.setSuccess(false);
						medbillResponse.setMessage("Saved UnSuccessfully!");
						log.info("Saved UnSuccessfully for StockId is: " + stockId
								+ " Due to failure of save Purchase details.");
					}
				}
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Saved Sucessfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Saved UnSucessfully");
				log.info("Saved UnSucessfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Someting went wrong!");
			log.info("Someting went wrong!");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/listPurchaseEntry", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse listPurchaseEntry(MedbillResponse medbillResponse) {
		List<PurchaseEntryBean> purchaseList = (List<PurchaseEntryBean>) purchaseEntryService
				.getAll("PurchaseEntryBean");
		if (purchaseList.size() > 0) {
			medbillResponse.setListObject(purchaseList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("purchaseList  is Empty");
			log.info("purchaseList  is Empty");
		}
		return medbillResponse;
	}

	@GetMapping(path = "/getPurchaseEntryDetailsById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getPurchaseEntryDetailsById(@PathVariable int id, MedbillResponse medbillResponse) {
		PurchaseEntryBean purchaseEntry = (PurchaseEntryBean) purchaseEntryService.getById("PurchaseEntryBean", id);
		if (purchaseEntry != null) {
			medbillResponse.setObject(purchaseEntry);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("purchaseEntry Not Exist");
			log.info("This purchaseEntry Id: " + id + " Not Exist");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getPurchaseEntryItemListByPurchaseId/{purchaseEntryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getPurchaseEntryItemListByPurchaseId(@PathVariable int purchaseEntryId,
			MedbillResponse medbillResponse) {
		List<PurchaseEntryItemBean> purchaseItemList = (List<PurchaseEntryItemBean>) purchaseEntryService
				.getPurchaseEntryItemListByPurchaseEntryId("PurchaseEntryItemBean", purchaseEntryId);
		if (purchaseItemList.size() > 0) {
			System.out.println("PurchaseEntryItem" + purchaseItemList);
			medbillResponse.setListObject(purchaseItemList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("PurchaseEntryItem  is Empty");
			log.info("PurchaseEntryItem is Empty");
		}
		return medbillResponse;
	}

	// get list of data except this id for validate unique in (edit)
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getPurchaseEntryListExceptOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getPurchaseEntryListExceptOne(@PathVariable int id, MedbillResponse medbillResponse) {
		List<PurchaseEntryBean> purchaseEntryListExceptOne = (List<PurchaseEntryBean>) purchaseEntryService
				.getAllExceptOne("PurchaseEntryBean", id);
		if (purchaseEntryListExceptOne.size() > 0) {
			medbillResponse.setListObject(purchaseEntryListExceptOne);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("purchaseEntry List is Empty");
			log.info("purchaseEntry List is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/updatePurchaseEntryDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse updatePurchaseEntryDetails(@RequestBody PurchaseEntryBean purchaseEntryBean,
			MedbillResponse MedbillResponse) {
		PurchaseEntryBean purchaseEntryDetails = (PurchaseEntryBean) purchaseEntryService.getById("PurchaseEntryBean",
				purchaseEntryBean.getPurchaseEntryId());

		if (purchaseEntryDetails != null) {
			purchaseEntryDetails.setOrderNumber(purchaseEntryBean.getOrderNumber());
			purchaseEntryDetails.setPurchaseEntryDiscount(purchaseEntryBean.getPurchaseEntryDiscount());
			purchaseEntryDetails
					.setPurchaseEntryDiscountInPercentage(purchaseEntryBean.getPurchaseEntryDiscountInPercentage());
			purchaseEntryDetails.setPurchaseEntryList(purchaseEntryBean.getPurchaseEntryList());
			purchaseEntryDetails.setPurchaseEntrySubTotal(purchaseEntryBean.getPurchaseEntrySubTotal());
			purchaseEntryDetails.setPurchaseEntryTax(purchaseEntryBean.getPurchaseEntryTax());
			purchaseEntryDetails.setPurchaseEntryTotal(purchaseEntryBean.getPurchaseEntryTotal());
			purchaseEntryDetails.setStockList(purchaseEntryBean.getStockList());
			purchaseEntryDetails.setSupplierInvoiceNumber(purchaseEntryBean.getSupplierInvoiceNumber());
			purchaseEntryDetails.setReceivedDate(purchaseEntryBean.getReceivedDate());
			purchaseEntryDetails.setUpdatedDate(AppUtil.currentDateWithTime());

			System.out.println("purchaseEntryDetails.getPurchaseEntryId()" + purchaseEntryDetails.getPurchaseEntryId());

			List<StockItemBean> stockList = new ArrayList<>();
			if (purchaseEntryService.update(purchaseEntryDetails)) {
				List<PurchaseEntryItemBean> list = purchaseEntryBean.getPurchaseEntryList();
				for (PurchaseEntryItemBean purchase : list) {
					PurchaseEntryItemBean purchaseEntryItemBean = purchaseEntryService.getPurchaseEntryItemBeanById(
							"PurchaseEntryItemBean", purchase.getPurchaseEntryId().getPurchaseEntryId(),
							purchase.getPurchaseEntryItemId());
					purchaseEntryItemBean.setPackaging(purchase.getPackaging());
					purchaseEntryItemBean.setQuantity(purchase.getQuantity());
					purchaseEntryItemBean.setUnitPrice(purchase.getUnitPrice());
					purchaseEntryItemBean.setBatchNumber(purchase.getBatchNumber());
					purchaseEntryItemBean.setManufactureDate(purchase.getManufactureDate());
					purchaseEntryItemBean.setExpiryDate(purchase.getExpiryDate());
					purchaseEntryItemBean.setAmount(purchase.getAmount());
					purchaseEntryItemBean.setUpdatedDate(AppUtil.currentDateWithTime());
					if (purchaseEntryService.update(purchaseEntryItemBean)) {
						StockItemBean stocks = new StockItemBean();
						stocks.setPurcItemBean(purchaseEntryItemBean);
						stocks.setAmount(purchaseEntryItemBean.getAmount());
						stocks.setBatchNumber(purchaseEntryItemBean.getBatchNumber());
						stocks.setExpiryDate(purchaseEntryItemBean.getExpiryDate());
						stocks.setManufactureDate(purchaseEntryItemBean.getManufactureDate());
						stocks.setManufacturer(purchaseEntryItemBean.getManufacturer());
						stocks.setPackaging(purchaseEntryItemBean.getPackaging());
						stocks.setProductName(purchaseEntryItemBean.getProductName());
						stocks.setProductType(purchaseEntryItemBean.getProductType());
						stocks.setQuantity(purchaseEntryItemBean.getQuantity());
						stocks.setUnitPrice(purchaseEntryItemBean.getUnitPrice());
						stockList.add(stocks);
					}
				}

				StockBean stockDetails = (StockBean) stockService.getByOrderNumber("StockBean",
						purchaseEntryBean.getOrderNumber().getOrderId());

				stockDetails.setOrderNumber(purchaseEntryBean.getOrderNumber());
				stockDetails.setPurchaseEntryDiscount(purchaseEntryBean.getPurchaseEntryDiscount());
				stockDetails
						.setPurchaseEntryDiscountInPercentage(purchaseEntryBean.getPurchaseEntryDiscountInPercentage());
//				stockDetails.setPurchaseEntryList(purchaseEntryBean.getPurchaseEntryList());
				stockDetails.setPurchaseEntrySubTotal(purchaseEntryBean.getPurchaseEntrySubTotal());
				stockDetails.setPurchaseEntryTax(purchaseEntryBean.getPurchaseEntryTax());
				stockDetails.setPurchaseEntryTotal(purchaseEntryBean.getPurchaseEntryTotal());
				// stockDetails.setStockList(purchaseEntryBean.getStockList());
				stockDetails.setSupplierInvoiceNumber(purchaseEntryBean.getSupplierInvoiceNumber());
				stockDetails.setReceivedDate(purchaseEntryBean.getReceivedDate());
				stockDetails.setUpdatedDate(AppUtil.currentDateWithTime());

				if (stockService.update(stockDetails)) {
					for (StockItemBean stock : stockList) {
						System.err.println("stock.getPurcItemBean().getPurchaseEntryItemId() "
								+ stock.getPurcItemBean().getPurchaseEntryItemId());
						StockItemBean stockItemBean = stockService.getStockItemBeanById("StockItemBean",
								stockDetails.getStockId(), stock.getPurcItemBean().getPurchaseEntryItemId());
						stockItemBean.setPackaging(stock.getPackaging());
						stockItemBean.setQuantity(stock.getQuantity());
						stockItemBean.setUnitPrice(stock.getUnitPrice());
						stockItemBean.setBatchNumber(stock.getBatchNumber());
						stockItemBean.setManufactureDate(stock.getManufactureDate());
						stockItemBean.setExpiryDate(stock.getExpiryDate());
						stockItemBean.setAmount(stock.getAmount());
						stockItemBean.setUpdatedDate(AppUtil.currentDateWithTime());
						stockService.update(stockItemBean);
					}
				}
//				else {
//					MedbillResponse.setSuccess(false);
//					MedbillResponse.setMessage("Update Failed");
//					log.info("Update Failed");
//				}		
				MedbillResponse.setSuccess(true);
				MedbillResponse.setMessage("Updated Successfully");
				log.info("ThisPurchase Entry Id: " + purchaseEntryBean.getPurchaseEntryId() + " Deleted Successfully");
			} else {
				MedbillResponse.setSuccess(false);
				MedbillResponse.setMessage("Update Failed");
				log.info("Update Failed");
			}
		} else {
			MedbillResponse.setSuccess(false);
			MedbillResponse.setMessage("This PurchaseEntryId  Not Exist");
			log.info("This PurchaseEntry Id: " + purchaseEntryBean.getPurchaseEntryId() + " is Not Exist");
		}

		return MedbillResponse;
	}

}

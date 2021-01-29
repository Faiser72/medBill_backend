package com.vetologic.medbill.controller.purchaseEntry;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
			System.err.println(purchaseEntryBean);
			if (purchaseEntryId != 0) {
				purchaseEntryBean.setPurchaseEntryId(purchaseEntryId);
				for (PurchaseEntryItemBean purchaseItems : purchaseEntryBean.getPurchaseEntryList()) {
					purchaseItems.setCreatedDate(AppUtil.currentDateWithTime());
					purchaseItems.setPurchaseEntryId(purchaseEntryBean);
					purchaseItems.setDeletionFlag(0);
					purchaseEntryService.save(purchaseItems);
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
				stockBean.setStockList(purchaseEntryBean.getStockList());
				stockBean.setSupplierInvoiceNumber(purchaseEntryBean.getSupplierInvoiceNumber());
				int stockId = stockService.save(stockBean);
				System.err.println(stockBean);
				if (stockId != 0) {
					for (StockItemBean stockItems : stockBean.getStockList()) {
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
}

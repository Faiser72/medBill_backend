package com.vetologic.medbill.controller.salesOrder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

import com.vetologic.medbill.beans.order.OrderBean;
import com.vetologic.medbill.beans.order.OrderItemBean;
import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryBean;
import com.vetologic.medbill.beans.response.MedbillResponse;
import com.vetologic.medbill.beans.salesOrder.SalesOrderBean;
import com.vetologic.medbill.beans.salesOrder.SalesOrderListBean;
import com.vetologic.medbill.beans.stock.StockItemBean;
import com.vetologic.medbill.controller.productCategoryMaster.ProductCategoryMasterController;
import com.vetologic.medbill.models.service.salesOrder.SalesOrderService;
import com.vetologic.medbill.utils.AppUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("salesOrder")
public class SalesOrderController {

	private static Logger log = LoggerFactory.getLogger(ProductCategoryMasterController.class);

	@Autowired
	private SalesOrderService SalesOrderService;

	@GetMapping(value = "/getSalesOrderInvoiceNumber", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getSalesOrderInvoiceNumber(MedbillResponse medbillResponse) {
		try {
			String salesInvoiceNumber = SalesOrderService.getSalesInvoiceNumber();
			if (salesInvoiceNumber == null) {
				salesInvoiceNumber = "0";
			} else {
				String[] strArr = salesInvoiceNumber.split("-");
				salesInvoiceNumber = strArr[5];
			}
			String ticketIdFiveDigitNo = AppUtil.getFiveDigitsWithZeroNumber(Integer.parseInt(salesInvoiceNumber));
			String salesInvoiceId = "MED-" + "INV-" + AppUtil.currentDateWithoutTime() + "-" + ticketIdFiveDigitNo;
			medbillResponse.setObject(salesInvoiceId);
		} catch (Exception e) {
			medbillResponse.setMessage("Something Went wrong! Try again.");
			medbillResponse.setSuccess(false);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/productListByCategoryId/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse productListByCategoryId(@PathVariable int categoryId, MedbillResponse medbillResponse) {
		List<StockItemBean> stockProductList = (List<StockItemBean>) SalesOrderService
				.getAllProductCategoryId("StockItemBean", categoryId);
		if (stockProductList.size() > 0) {
			medbillResponse.setListObject(stockProductList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Product List is Empty");
			log.info("Product List is Empty");
		}
		return medbillResponse;
	}

	@PostMapping(path = "/saveSalesOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse saveSalesOrder(@RequestBody SalesOrderBean salesOrderBean, MedbillResponse medbillResponse) {
		System.err.println("Bean details" + salesOrderBean);
		salesOrderBean.setDeletionFlag(0);
		salesOrderBean.setCancellationFlag(0);
		salesOrderBean.setCreatedDate(AppUtil.currentDateWithTime());
		int id = SalesOrderService.save(salesOrderBean);

		if (id != 0) {
			salesOrderBean.setSalesOrderId(id);
			for (SalesOrderListBean salesOrderList : salesOrderBean.getSalesOrderList()) {
				int salId = salesOrderList.getStockItemId().getStockItemId();
				StockItemBean stockItemDetails = (StockItemBean) SalesOrderService.getStockDeatailById("StockItemBean",
						salId);
				int actualStock = stockItemDetails.getQuantity();
				int enteredStock = salesOrderList.getQuantity();
				int remainingStock = actualStock - enteredStock;
				stockItemDetails.setBalanceQuantity(remainingStock);
				stockItemDetails.setSoldQuantity(salesOrderList.getQuantity());
				SalesOrderService.update(stockItemDetails);
				salesOrderList.setCreatedDate(AppUtil.currentDateWithTime());
				salesOrderList.setSalesId(salesOrderBean);
				salesOrderList.setDeletionFlag(0);
				SalesOrderService.save(salesOrderList);
			}
			medbillResponse.setSuccess(true);
			medbillResponse.setMessage("Saved Sucessfully");
			log.info("Saved Sucessfully & Saved Sales Order is: " + id);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Saved UnSucessfully");
			log.info("Saved UnSucessfully");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getAllSalesOrders", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getAllSalesOrder(MedbillResponse medbillResponse) {
		List<SalesOrderBean> allSalesOrderList = (List<SalesOrderBean>) SalesOrderService.getAll("SalesOrderBean");
		if (allSalesOrderList.size() > 0) {
			medbillResponse.setListObject(allSalesOrderList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Sales Order List is Empty");
			log.info("Sales Order is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/deleteSalesOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse deleteSalesOrder(@RequestParam("salesOrderId") int salesOrderId,
			MedbillResponse medbillResponse) {
		SalesOrderBean salesOrderDetail = (SalesOrderBean) SalesOrderService.getById("SalesOrderBean", salesOrderId);
		if (salesOrderDetail != null) {
			salesOrderDetail.setUpdatedDate(AppUtil.currentDateWithTime());
			salesOrderDetail.setDeletionFlag(1);
			if (SalesOrderService.update(salesOrderDetail)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Deleted Successfully");
				log.info("This Sales Order Id: " + salesOrderId + " Deleted Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Deletion Failed");
				log.info("Deletion Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Sales Order does Not Exist");
			log.info("This Sales Order Id: " + salesOrderId + " Not Exist");
		}
		return medbillResponse;
	}

	@GetMapping(path = "/getSalesOrderDetail/{salesOrderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getSalesOrderDetail(@PathVariable int salesOrderId, MedbillResponse medbillResponse) {
		SalesOrderBean salesOrderDetails = (SalesOrderBean) SalesOrderService.getById("SalesOrderBean", salesOrderId);
		if (salesOrderDetails != null) {
			medbillResponse.setObject(salesOrderDetails);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Sales Order Doest Not Exist");
			log.info("This Sales Order  Id: " + salesOrderId + " Not Exist");
		}
		return medbillResponse;
	}

	@GetMapping(path = "/getSalesOrderListById/{salesOrderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getSalesOrderListById(@PathVariable int salesOrderId, MedbillResponse medbillResponse) {
		List<SalesOrderListBean> salesOrderListDetails = (List<SalesOrderListBean>) SalesOrderService
				.getAllSalesOrderListById("SalesOrderListBean", salesOrderId);
		if (salesOrderListDetails != null) {
			medbillResponse.setObject(salesOrderListDetails);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Sales Order List Doest Not Exist");
			log.info("This Sales Order List Id: " + salesOrderId + " Not Exist");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/cancelSalesOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse cancelSalesOrder(@RequestParam("salesOrderId") int salesOrderId,
			MedbillResponse medbillResponse) {
		SalesOrderBean salesOrderDetails = (SalesOrderBean) SalesOrderService.getById("SalesOrderBean", salesOrderId);
		if (salesOrderDetails != null) {
			salesOrderDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			salesOrderDetails.setCancellationFlag(1);
			if (SalesOrderService.update(salesOrderDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Canceled Successfully");
				log.info("This Sales Order Id: " + salesOrderId + " Canceled Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Canceled Failed");
				log.info("Canceled Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Sales Order does Not Exist");
			log.info("This Sales Order Id: " + salesOrderId + " Not Exist");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getAllDeletedSalesOrders", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getAllDeletedSalesOrders(MedbillResponse medbillResponse) {
		List<SalesOrderBean> salesOrderDetails = (List<SalesOrderBean>) SalesOrderService
				.getAllSalesOrdersDeleted("SalesOrderBean");
		if (salesOrderDetails.size() > 0) {
			medbillResponse.setListObject(salesOrderDetails);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("orderList  is Empty");
			log.info("orderList is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/undoDeletedSalesOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse undoDeletedSalesOrder(@RequestParam("salesOrderId") int salesOrderId,
			MedbillResponse medbillResponse) {
		SalesOrderBean salesOrderDetails = (SalesOrderBean) SalesOrderService.getDeletedById("SalesOrderBean",
				salesOrderId);
		if (salesOrderDetails != null) {
			salesOrderDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			salesOrderDetails.setDeletionFlag(0);
			if (SalesOrderService.update(salesOrderDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Recycled Successfully");
				log.info("This Sales Order Id: " + salesOrderId + " Recycled Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("ReCycled Failed");
				log.info("Recycled Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Sales Order does Not Exist");
			log.info("This Sales Order Id: " + salesOrderId + " Not Exist");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getAllCanceledSalesOrders", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getAllCanceledSalesOrders(MedbillResponse medbillResponse) {
		List<SalesOrderBean> salesOrderDetailsList = (List<SalesOrderBean>) SalesOrderService
				.getAllSalesOrdersCanceled("SalesOrderBean");
		if (salesOrderDetailsList.size() > 0) {
			medbillResponse.setListObject(salesOrderDetailsList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Cancelled Sales OrderList  is Empty");
			log.info("sales orderList is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/undoCanceledSalesOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse undoCanceledOrder(@RequestParam("salesOrderId") int salesOrderId,
			MedbillResponse medbillResponse) {
		SalesOrderBean salesOrderDetails = (SalesOrderBean) SalesOrderService.getCanceledById("SalesOrderBean",
				salesOrderId);
		if (salesOrderDetails != null) {
			salesOrderDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			salesOrderDetails.setCancellationFlag(0);
			if (SalesOrderService.update(salesOrderDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Recycled Successfully");
				log.info("This sales Order Id: " + salesOrderId + " Recycled Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("ReCycled Failed");
				log.info("Recycled Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This sales Order does Not Exist");
			log.info("This sales Order Id: " + salesOrderId + " Not Exist");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/deleteSalesOrderItem", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse deleteSalesOrderItem(@RequestParam("salesItemId") int salesItemId,
			MedbillResponse medbillResponse) {
		SalesOrderListBean salesOrderItemBeanList = (SalesOrderListBean) SalesOrderService
				.getSalesOrderItemBeanById("SalesOrderListBean", salesItemId);
		if (salesOrderItemBeanList != null) {
			salesOrderItemBeanList.setUpdatedDate(AppUtil.currentDateWithTime());
			salesOrderItemBeanList.setDeletionFlag(1);
			if (SalesOrderService.update(salesOrderItemBeanList)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Deleted Successfully");
				log.info("This Sales Order Id: " + salesItemId + " Deleted Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Deletion Failed");
				log.info("Deletion Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Purchase Order does Not Exist");
			log.info("This Order Id: " + salesItemId + " Not Exist");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getAllSalesListBtwnDatesAndPayment/{fromDate}/{toDate}/{paymentMode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getAllSalesListBtwnDatesAndPayment(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("paymentMode") String paymentMode,
			MedbillResponse MedbillResponse) {
		List<SalesOrderBean> allSalesOrderBeanDetails = (List<SalesOrderBean>) SalesOrderService
				.getAllSalesListBtwnDatesAndPayment("SalesOrderBean", fromDate, toDate, paymentMode);
		if (allSalesOrderBeanDetails.size() > 0) {
			MedbillResponse.setListObject(allSalesOrderBeanDetails);
			MedbillResponse.setSuccess(true);
		} else {
			MedbillResponse.setSuccess(false);
			MedbillResponse.setMessage("SalesOrderBean List is Empty");
			log.info("SalesOrderBean List is Empty");
		}
		return MedbillResponse;
	}
}

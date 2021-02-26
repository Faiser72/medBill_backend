package com.vetologic.medbill.controller.order;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.medbill.beans.order.OrderBean;
import com.vetologic.medbill.beans.order.OrderItemBean;
import com.vetologic.medbill.beans.productMaster.ProductMasterBean;
import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryBean;
import com.vetologic.medbill.beans.response.MedbillResponse;
import com.vetologic.medbill.controller.productCategoryMaster.ProductCategoryMasterController;
import com.vetologic.medbill.models.service.order.OrderService;
import com.vetologic.medbill.utils.AppUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("order")
public class OrderController {

	private static Logger log = LoggerFactory.getLogger(ProductCategoryMasterController.class);

	@Autowired
	private OrderService orderService;

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/listProductByCategoryId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse listAllProductByCategoryId(@PathVariable int id, MedbillResponse medbillResponse) {
		List<ProductMasterBean> productList = (List<ProductMasterBean>) orderService
				.getAllProductCategoryId("ProductMasterBean", id);
		if (productList.size() > 0) {
			System.out.println("productList" + productList);
			medbillResponse.setListObject(productList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Product List is Empty");
			log.info("Product List is Empty");
		}
		return medbillResponse;
	}

	@GetMapping(value = "/getOrderIdAuto", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getOrderIdAuto(MedbillResponse medbillResponse) {
		try {
			String maxPurchaseOrderId = orderService.getOrderMaxId();
			if (maxPurchaseOrderId == null) {
				maxPurchaseOrderId = "0";
			} else {
				String[] strArr = maxPurchaseOrderId.split("-");
				maxPurchaseOrderId = strArr[4];
			}
			String ticketIdFiveDigitNo = AppUtil.getFiveDigitsWithZeroNumber(Integer.parseInt(maxPurchaseOrderId));
			String purchaseOrderId = "ORD-" + AppUtil.currentDateWithoutTime() + "-" + ticketIdFiveDigitNo;
			medbillResponse.setObject(purchaseOrderId);
		} catch (Exception e) {
			medbillResponse.setMessage("Something Went wrong! Try again.");
			medbillResponse.setSuccess(false);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return medbillResponse;
	}

	@PostMapping(path = "/addOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse saveOrder(@RequestBody OrderBean orderBean, MedbillResponse medbillResponse) {
		orderBean.setDeletionFlag(0);
		orderBean.setCancellationFlag(0);
		orderBean.setCreatedDate(AppUtil.currentDateWithTime());
		int id = orderService.save(orderBean);
		if (id != 0) {
			orderBean.setOrderId(id);
			for (OrderItemBean orderItems : orderBean.getOrderItemList()) {
				orderItems.setCreatedDate(AppUtil.currentDateWithTime());
				orderItems.setOrderId(orderBean);
				orderItems.setDeletionFlag(0);
				orderService.save(orderItems);
			}
			medbillResponse.setSuccess(true);
			medbillResponse.setMessage("Saved Sucessfully");
			log.info("Saved Sucessfully & Saved Category Id is: " + id);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Saved UnSucessfully");
			log.info("Saved UnSucessfully");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/orderList", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse listAllOrders(MedbillResponse medbillResponse) {
		List<OrderBean> orderList = (List<OrderBean>) orderService.getAll("OrderBean");
		if (orderList.size() > 0) {
			System.out.println("productList" + orderList);
			medbillResponse.setListObject(orderList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("orderList  is Empty");
			log.info("orderList is Empty");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/orderListByOrderId/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse orderListByOrderId(@PathVariable int orderId, MedbillResponse medbillResponse) {
		List<OrderItemBean> orderItemList = (List<OrderItemBean>) orderService.getOrderListByOederId("OrderItemBean",
				orderId);
		if (orderItemList.size() > 0) {
			System.out.println("orderItemList" + orderItemList);
			medbillResponse.setListObject(orderItemList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("orderItemList  is Empty");
			log.info("orderItemList is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/deleteOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse deleteOrder(@RequestParam("orderId") int orderId, MedbillResponse medbillResponse) {
		OrderBean orderDetails = (OrderBean) orderService.getById("OrderBean", orderId);
		if (orderDetails != null) {
			orderDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			orderDetails.setDeletionFlag(1);
			if (orderService.update(orderDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Deleted Successfully");
				log.info("This Order Id: " + orderId + " Deleted Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Deletion Failed");
				log.info("Deletion Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Order does Not Exist");
			log.info("This Order Id: " + orderId + " Not Exist");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/orderByOrderId/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse orderByOrderId(@PathVariable int orderId, MedbillResponse medbillResponse) {
		List<OrderBean> orderList = (List<OrderBean>) orderService.getOrderByOrderId("OrderBean", orderId);
		if (orderList.size() > 0) {
			medbillResponse.setListObject(orderList);
			medbillResponse.setObject(orderList.get(0));
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("orderList  is Empty");
			log.info("orderList is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/cancelOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse cancelOrder(@RequestParam("orderId") int orderId, MedbillResponse medbillResponse) {
		OrderBean orderDetails = (OrderBean) orderService.getById("OrderBean", orderId);
		if (orderDetails != null) {
			orderDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			orderDetails.setCancellationFlag(1);
			if (orderService.update(orderDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Canceled Successfully");
				log.info("This Order Id: " + orderId + " Canceled Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Canceled Failed");
				log.info("Canceled Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Order does Not Exist");
			log.info("This Order Id: " + orderId + " Not Exist");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getAllDeletedOrders", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getAllDeletedOrders(MedbillResponse medbillResponse) {
		List<OrderBean> orderList = (List<OrderBean>) orderService.getAllOrdersDeleted("OrderBean");
		if (orderList.size() > 0) {
			System.out.println("productList" + orderList);
			medbillResponse.setListObject(orderList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("orderList  is Empty");
			log.info("orderList is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/undoDeletedOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse undoDeletedOrder(@RequestParam("orderId") int orderId, MedbillResponse medbillResponse) {
		OrderBean orderDetails = (OrderBean) orderService.getDetedById("OrderBean", orderId);
		if (orderDetails != null) {
			orderDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			orderDetails.setDeletionFlag(0);
			if (orderService.update(orderDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Recycled Successfully");
				log.info("This Order Id: " + orderId + " Recycled Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("ReCycled Failed");
				log.info("Recycled Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Order does Not Exist");
			log.info("This Order Id: " + orderId + " Not Exist");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getAllCanceledOrders", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getAllCanceledOrders(MedbillResponse medbillResponse) {
		List<OrderBean> orderList = (List<OrderBean>) orderService.getAllOrdersCanceled("OrderBean");
		if (orderList.size() > 0) {
			System.out.println("productList" + orderList);
			medbillResponse.setListObject(orderList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Cancelled OrderList  is Empty");
			log.info("orderList is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/undoCanceledOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse undoCanceledOrder(@RequestParam("orderId") int orderId, MedbillResponse medbillResponse) {
		OrderBean orderDetails = (OrderBean) orderService.getCanceledById("OrderBean", orderId);
		if (orderDetails != null) {
			orderDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			orderDetails.setCancellationFlag(0);
			if (orderService.update(orderDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Recycled Successfully");
				log.info("This Order Id: " + orderId + " Recycled Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("ReCycled Failed");
				log.info("Recycled Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Order does Not Exist");
			log.info("This Order Id: " + orderId + " Not Exist");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/updateOrderDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse updateOrderDetails(@RequestBody OrderBean orderBean, MedbillResponse medbillResponse) {
		OrderBean orderDetails = (OrderBean) orderService.getById("OrderBean", orderBean.getOrderId());
		orderDetails.setOrderGrandTotal(orderBean.getOrderGrandTotal());
		orderDetails.setOrderDate(orderBean.getOrderDate());
		orderDetails.setSupplierName(orderBean.getSupplierName());
		orderDetails.setUpdatedDate(AppUtil.currentDateWithTime());

		if (orderService.update(orderDetails)) {
			for (OrderItemBean orderItemList : orderBean.getOrderItemList()) {
				if (orderItemList != null) {
					if (orderItemList.getOrderItemId() == 0) {
						orderBean.setOrderId(orderBean.getOrderId());
						orderItemList.setCreatedDate(AppUtil.currentDateWithTime());
						orderItemList.setOrderId(orderBean);
						orderItemList.setDeletionFlag(0);
						orderService.save(orderItemList);
						int saveId = orderService.save(orderItemList);
					} else {
						OrderItemBean orderItemDetails = (OrderItemBean) orderService
								.getOrderItemBeanById("OrderItemBean", orderItemList.getOrderItemId());
						if (orderItemDetails != null) {
							orderItemDetails.setProductType(orderItemList.getProductType());
							orderItemDetails.setProductName(orderItemList.getProductName());
							orderItemDetails.setPackaging(orderItemList.getPackaging());
							orderItemDetails.setManufacturer(orderItemList.getManufacturer());
							orderItemDetails.setQuantity(orderItemList.getQuantity());
							orderItemDetails.setUnitPrice(orderItemList.getUnitPrice());
							orderItemDetails.setUnitPrice(orderItemList.getUnitPrice());
							orderItemDetails.setAmount(orderItemList.getAmount());
							orderItemDetails.setUpdatedDate(AppUtil.currentDateWithTime());
							boolean updateFlg = orderService.update(orderItemDetails);
							if (updateFlg) {
								medbillResponse.setSuccess(true);
								medbillResponse.setMessage("Updated Successfully");
								log.info("This Order Id: " + orderBean.getOrderId() + " Updated Successfully");
							}
						}
					}
					medbillResponse.setSuccess(true);
					medbillResponse.setMessage("Updated Successfully");
					log.info("This Order Id: " + orderBean.getOrderId() + " Updated Successfully");
				} else {
					medbillResponse.setSuccess(false);
					medbillResponse.setMessage("Updation Failed");
					log.info("Updation Failed");
				}

			}
			medbillResponse.setSuccess(true);
			medbillResponse.setMessage("Updated Successfully");
			log.info("This Order Id: " + orderBean.getOrderId() + " Updated Successfully");
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Updation Failed");
			log.info("Updation Failed");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/deleteOrderItem", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse deleteOrderItem(@RequestParam("orderItemId") int orderItemId,
			MedbillResponse medbillResponse) {
		OrderItemBean orderItemBeanList = (OrderItemBean) orderService.getOrderItemBeanById("OrderItemBean",
				orderItemId);
		if (orderItemBeanList != null) {
			orderItemBeanList.setUpdatedDate(AppUtil.currentDateWithTime());
			orderItemBeanList.setDeletionFlag(1);
			if (orderService.update(orderItemBeanList)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Deleted Successfully");
				log.info("This Order Id: " + orderItemId + " Deleted Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Deletion Failed");
				log.info("Deletion Failed");
			}

		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Purchase Order does Not Exist");
			log.info("This Order Id: " + orderItemId + " Not Exist");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getAllOrderListBtwnDates/{fromDate}/{toDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getAllOrderListBtwnDates(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, MedbillResponse MedbillResponse) {
		List<OrderBean> allOrderBeanDetails = (List<OrderBean>) orderService.getAllOrderListBtwnDates("OrderBean",
				fromDate, toDate);
		if (allOrderBeanDetails.size() > 0) {
			MedbillResponse.setListObject(allOrderBeanDetails);
			MedbillResponse.setSuccess(true);
		} else {
			MedbillResponse.setSuccess(false);
			MedbillResponse.setMessage("OrderBean List is Empty");
			log.info("OrderBean List is Empty");
		}
		return MedbillResponse;
	}
}

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.medbill.beans.order.OrderBean;
import com.vetologic.medbill.beans.order.OrderItemBean;
import com.vetologic.medbill.beans.productMaster.ProductMasterBean;
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
}

package com.vetologic.medbill.models.service.order;

import java.util.List;

import com.vetologic.medbill.beans.order.OrderItemBean;

public interface OrderService {

	int save(Object object);

	List<?> getAllProductCategoryId(String beanClassName, int id);

	String getOrderMaxId();

	List<?> getAll(String beanClassName);

	List<?> getAllOrdersDeleted(String beanClassName);

	List<?> getOrderListByOederId(String beanClassName, int id);

	List<?> getOrderByOrderId(String beanClassName, int id);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

	Object getDetedById(String beanClassName, int id);

	List<?> getAllOrdersCanceled(String beanClassName);

	Object getCanceledById(String beanClassName, int id);

	OrderItemBean getOrderItemBeanById(String string, int orderItemId);

	List<?> getAllOrderListBtwnDates(String beanClassName, String fromDate, String toDate);
}

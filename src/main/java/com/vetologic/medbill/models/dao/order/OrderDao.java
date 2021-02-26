package com.vetologic.medbill.models.dao.order;

import java.util.List;

import com.vetologic.medbill.beans.order.OrderItemBean;

public interface OrderDao {

	List<?> getAllProductCategoryId(String beanClassName, int id);

	String getOrderMaxId();

	int save(Object object);

	List<?> getAll(String beanClassName);

	List<?> getOrderListByOederId(String beanClassName, int id);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

	List<?> getAllOrdersDeleted(String beanClassName);

	Object getDetedById(String beanClassName, int id);

	List<?> getAllOrdersCanceled(String beanClassName);

	Object getCanceledById(String beanClassName, int id);

	List<?> getOrderByOrderId(String beanClassName, int id);

	OrderItemBean getOrderItemBeanById(String string, int orderItemId);

	List<?> getAllOrderListBtwnDates(String beanClassName, String fromDate, String toDate);
}

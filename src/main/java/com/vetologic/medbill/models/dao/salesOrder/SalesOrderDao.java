package com.vetologic.medbill.models.dao.salesOrder;

import java.util.List;

import com.vetologic.medbill.beans.order.OrderItemBean;

public interface SalesOrderDao {

	int save(Object object);

	List<?> getAll(String beanClassName);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

	String getSalesInvoiceNumber();

	List<?> getAllProductCategoryId(String beanClassName, int id);

	List<?> getAllSalesOrderListById(String beanClassName, int id);

	Object getStockDeatailById(String beanClassName, int id);
	
	List<?> getAllSalesOrdersDeleted(String beanClassName);
	
	Object getDeletedById(String beanClassName, int id);
	
	List<?> getAllSalesOrdersCanceled(String beanClassName);

	Object getCanceledById(String beanClassName, int id);
	
	Object getSalesOrderItemBeanById(String string, int salesItemId);
	
	List<?> getAllSalesListBtwnDatesAndPayment(String beanClassName, String fromDate, String toDate,
			String paymentMode);
}

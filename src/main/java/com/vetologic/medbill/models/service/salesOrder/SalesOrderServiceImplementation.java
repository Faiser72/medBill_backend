package com.vetologic.medbill.models.service.salesOrder;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.medbill.models.dao.salesOrder.SalesOrderDao;

@Service
public class SalesOrderServiceImplementation implements SalesOrderService {

	@Autowired
	private SalesOrderDao salesOrderDao;

	@Transactional
	@Override
	public int save(Object object) {
		return salesOrderDao.save(object);
	}

	@Override
	public List<?> getAll(String beanClassName) {
		return salesOrderDao.getAll(beanClassName);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return salesOrderDao.getById(beanClassName, id);
	}

	@Transactional
	@Override
	public boolean update(Object object) {
		return salesOrderDao.update(object);
	}

	@Override
	public String getSalesInvoiceNumber() {
		return salesOrderDao.getSalesInvoiceNumber();
	}

	@Override
	public List<?> getAllProductCategoryId(String beanClassName, int id) {
		return salesOrderDao.getAllProductCategoryId(beanClassName, id);
	}

	@Override
	public List<?> getAllSalesOrderListById(String beanClassName, int id) {
		return salesOrderDao.getAllSalesOrderListById(beanClassName, id);
	}

	@Override
	public Object getStockDeatailById(String beanClassName, int id) {
		return salesOrderDao.getStockDeatailById(beanClassName, id);
	}

	@Override
	public List<?> getAllSalesOrdersDeleted(String beanClassName) {
		return salesOrderDao.getAllSalesOrdersDeleted(beanClassName);
	}

	@Override
	public Object getDeletedById(String beanClassName, int id) {
		return salesOrderDao.getDeletedById(beanClassName, id);
	}

	@Override
	public List<?> getAllSalesOrdersCanceled(String beanClassName) {
		return salesOrderDao.getAllSalesOrdersCanceled(beanClassName);
	}

	@Override
	public Object getCanceledById(String beanClassName, int id) {
		return salesOrderDao.getCanceledById(beanClassName, id);
	}

	@Override
	public Object getSalesOrderItemBeanById(String string, int salesItemId) {
		return salesOrderDao.getSalesOrderItemBeanById(string, salesItemId);
	}

	@Override
	public List<?> getAllSalesListBtwnDatesAndPayment(String beanClassName, String fromDate, String toDate,
			String paymentMode) {
		return salesOrderDao.getAllSalesListBtwnDatesAndPayment(beanClassName, fromDate, toDate, paymentMode);
	}

	@Override
	public List<?> getAllSalesListOfProductBtwnDates(String beanClassName, String fromDate, String toDate,
			String productName) {
		return salesOrderDao.getAllSalesListOfProductBtwnDates(beanClassName, fromDate, toDate, productName);
	}
}

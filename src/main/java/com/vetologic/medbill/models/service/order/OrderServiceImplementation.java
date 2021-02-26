package com.vetologic.medbill.models.service.order;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.medbill.beans.order.OrderItemBean;
import com.vetologic.medbill.models.dao.order.OrderDao;

@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	public List<?> getAllProductCategoryId(String beanClassName, int id) {
		return orderDao.getAllProductCategoryId(beanClassName, id);
	}

	@Override
	public String getOrderMaxId() {
		return orderDao.getOrderMaxId();
	}

	@Override
	public int save(Object object) {
		return orderDao.save(object);
	}

	@Override
	public List<?> getAll(String beanClassName) {
		return orderDao.getAll(beanClassName);
	}

	@Override
	public List<?> getOrderListByOederId(String beanClassName, int id) {
		return orderDao.getOrderListByOederId(beanClassName, id);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return orderDao.getById(beanClassName, id);
	}

	@Transactional
	@Override
	public boolean update(Object object) {
		return orderDao.update(object);
	}

	@Override
	public List<?> getAllOrdersDeleted(String beanClassName) {
		return orderDao.getAllOrdersDeleted(beanClassName);
	}

	@Override
	public Object getDetedById(String beanClassName, int id) {
		return orderDao.getDetedById(beanClassName, id);
	}

	@Override
	public List<?> getAllOrdersCanceled(String beanClassName) {
		return orderDao.getAllOrdersCanceled(beanClassName);
	}

	@Override
	public Object getCanceledById(String beanClassName, int id) {
		return orderDao.getCanceledById(beanClassName, id);
	}

	@Override
	public List<?> getOrderByOrderId(String beanClassName, int id) {
		return orderDao.getOrderByOrderId(beanClassName, id);
	}

	@Override
	public OrderItemBean getOrderItemBeanById(String string, int orderItemId) {

		return orderDao.getOrderItemBeanById(string, orderItemId);
	}

	@Override
	public List<?> getAllOrderListBtwnDates(String beanClassName, String fromDate, String toDate) {
		return orderDao.getAllOrderListBtwnDates(beanClassName, fromDate, toDate);
	}
}

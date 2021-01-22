package com.vetologic.medbill.models.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.medbill.models.dao.order.OrderDao;
import com.vetologic.medbill.models.dao.productMaster.ProductMasterDao;

@Service
public class OrderServiceImplementation implements OrderService{

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
}

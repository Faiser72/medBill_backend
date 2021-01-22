package com.vetologic.medbill.models.service.order;

import java.util.List;

public interface OrderService {

	int save(Object object);
	
	List<?> getAllProductCategoryId(String beanClassName, int id);
	
	String getOrderMaxId();
	
	List<?> getAll(String beanClassName);

}

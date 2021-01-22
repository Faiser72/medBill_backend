package com.vetologic.medbill.models.dao.order;

import java.util.List;

public interface OrderDao {

	List<?> getAllProductCategoryId(String beanClassName, int id);

	String getOrderMaxId();

	int save(Object object);
	
	List<?> getAll(String beanClassName);



}

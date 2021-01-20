package com.vetologic.medbill.models.service.productCategoryMaster;

import java.util.List;

public interface ProductCategoryService {

	List<?> getAll(String beanClassName);
	
	int save(Object object);
	
	Object getById(String beanClassName, int id);
	
	boolean update(Object object);
	
	List<?> getAllExceptOne(String beanClassName, int id);
}

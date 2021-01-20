package com.vetologic.medbill.models.dao.productCategoryMaster;

import java.util.List;
public interface ProductCategoryDao {

	List<?> getAll(String beanClassName);
	
	int save(Object object);

	Object getById(String beanClassName, int id);

	boolean update(Object object);
	 
	List<?> getAllExceptOne(String beanClassName, int id);
}

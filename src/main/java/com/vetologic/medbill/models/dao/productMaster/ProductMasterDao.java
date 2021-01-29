package com.vetologic.medbill.models.dao.productMaster;

import java.util.List;

public interface ProductMasterDao {

	List<?> getAll(String beanClassName);

	int save(Object object);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

	List<?> getAllExceptOne(String beanClassName, int id);

}

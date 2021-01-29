package com.vetologic.medbill.models.service.productMaster;

import java.util.List;

public interface ProductMasterService {

	List<?> getAll(String beanClassName);

	int save(Object object);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

	List<?> getAllExceptOne(String beanClassName, int id);

}

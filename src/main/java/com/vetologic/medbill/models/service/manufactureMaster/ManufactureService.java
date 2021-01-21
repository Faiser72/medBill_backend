package com.vetologic.medbill.models.service.manufactureMaster;

import java.util.List;

public interface ManufactureService {

	List<?> getAll(String beanClassName);
	
	int save(Object object);
	
	Object getById(String beanClassName, int id);
	
	boolean update(Object object);
	
	List<?> getAllExceptOne(String beanClassName, int id);
}

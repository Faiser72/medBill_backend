package com.vetologic.medbill.models.service.supplierMaster;

import java.util.List;

public interface SupplierMasterService {

	List<?> getAll(String beanClassName);

	int save(Object object);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

}

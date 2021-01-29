package com.vetologic.medbill.models.dao.supplierMaster;

import java.util.List;

public interface SupplierMasterDao {

	List<?> getAll(String beanClassName);

	int save(Object object);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

}

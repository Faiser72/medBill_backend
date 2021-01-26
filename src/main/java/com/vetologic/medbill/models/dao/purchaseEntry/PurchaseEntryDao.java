package com.vetologic.medbill.models.dao.purchaseEntry;

import java.util.List;

public interface PurchaseEntryDao {


	int save(Object object);
	
	List<?> getAll(String beanClassName);
	

}

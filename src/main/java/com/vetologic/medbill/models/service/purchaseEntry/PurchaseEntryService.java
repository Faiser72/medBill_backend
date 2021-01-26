package com.vetologic.medbill.models.service.purchaseEntry;

import java.util.List;

public interface PurchaseEntryService {
	
	int save(Object object);
	
	List<?> getAll(String beanClassName);

}

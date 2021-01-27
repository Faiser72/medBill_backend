package com.vetologic.medbill.models.service.purchaseEntry;

import java.util.List;

import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryBean;

public interface PurchaseEntryService {
	
	int save(Object object);
	
	List<?> getAll(String beanClassName);
	
	boolean deletePurchaseEntry(PurchaseEntryBean purchase);


}

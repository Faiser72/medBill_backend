package com.vetologic.medbill.models.dao.purchaseEntry;

import java.util.List;

import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryBean;

public interface PurchaseEntryDao {

	int save(Object object);

	List<?> getAll(String beanClassName);

	boolean deletePurchaseEntry(PurchaseEntryBean purchase);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

	List<?> getPurchaseEntryItemListByPurchaseEntryId(String beanClassName, int id);
	
	List<?> getAllExceptOne(String beanClassName, int id);

}

package com.vetologic.medbill.models.service.purchaseEntry;

import java.util.List;

import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryBean;
import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryItemBean;

public interface PurchaseEntryService {

	int save(Object object);

	List<?> getAll(String beanClassName);

	boolean deletePurchaseEntry(PurchaseEntryBean purchase);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

	List<?> getPurchaseEntryItemListByPurchaseEntryId(String beanClassName, int id);

	List<?> getAllExceptOne(String beanClassName, int id);

	PurchaseEntryItemBean getPurchaseEntryItemBeanById(String branName, int purchaseBeanId, int purchaseListId);

	boolean deletePurchaseEntryItemListByPurchaseEntryId(int purchaseEntryId);

	List<?> getAllPurchaseEntryListBtwnDatesAndPayment(String beanClassName, String fromDate, String toDate,
			String paymentMode);

	List<?> getAllReturn(String beanClassName);

	List<?> getAllPurchaseEntryItem(String beanClassName);

}

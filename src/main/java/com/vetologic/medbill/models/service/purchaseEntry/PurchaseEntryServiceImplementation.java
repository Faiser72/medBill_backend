package com.vetologic.medbill.models.service.purchaseEntry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryBean;
import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryItemBean;
import com.vetologic.medbill.models.dao.purchaseEntry.PurchaseEntryDao;

@Service
public class PurchaseEntryServiceImplementation implements PurchaseEntryService {

	@Autowired
	private PurchaseEntryDao purchaseEntryDao;

	@Override
	public int save(Object object) {
		return purchaseEntryDao.save(object);
	}

	@Override
	public List<?> getAll(String beanClassName) {
		return purchaseEntryDao.getAll(beanClassName);
	}

	@Transactional
	@Override
	public boolean deletePurchaseEntry(PurchaseEntryBean purchase) {
		return purchaseEntryDao.deletePurchaseEntry(purchase);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return purchaseEntryDao.getById(beanClassName, id);
	}

	@Transactional
	@Override
	public boolean update(Object object) {
		return purchaseEntryDao.update(object);
	}

	@Override
	public List<?> getPurchaseEntryItemListByPurchaseEntryId(String beanClassName, int id) {
		return purchaseEntryDao.getPurchaseEntryItemListByPurchaseEntryId(beanClassName, id);
	}

	@Override
	public List<?> getAllExceptOne(String beanClassName, int id) {
		return purchaseEntryDao.getAllExceptOne(beanClassName, id);
	}

	@Override
	public PurchaseEntryItemBean getPurchaseEntryItemBeanById(String branName, int purchaseBeanId, int purchaseListId) {
		return purchaseEntryDao.getPurchaseEntryItemBeanById(branName, purchaseBeanId, purchaseListId);
	}

	@Override
	public boolean deletePurchaseEntryItemListByPurchaseEntryId(int purchaseEntryId) {
		return purchaseEntryDao.deletePurchaseEntryItemListByPurchaseEntryId(purchaseEntryId);
	}

	@Override
	public List<?> getAllPurchaseEntryListBtwnDatesAndPayment(String beanClassName, String fromDate, String toDate,
			String paymentMode) {
		return purchaseEntryDao.getAllPurchaseEntryListBtwnDatesAndPayment(beanClassName, fromDate, toDate,
				paymentMode);
	}
}

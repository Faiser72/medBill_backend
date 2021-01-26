package com.vetologic.medbill.models.service.purchaseEntry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.medbill.models.dao.purchaseEntry.PurchaseEntryDao;

@Service
public class PurchaseEntryServiceImplementation implements PurchaseEntryService{

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

}


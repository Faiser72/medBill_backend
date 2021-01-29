package com.vetologic.medbill.models.service.supplierMaster;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.medbill.models.dao.supplierMaster.SupplierMasterDao;

@Service
public class SupplierMasterServiceImplementation implements SupplierMasterService {

	@Autowired
	private SupplierMasterDao supplierMasterDao;

	@Override
	public List<?> getAll(String beanClassName) {
		return supplierMasterDao.getAll(beanClassName);
	}

	@Transactional
	@Override
	public int save(Object object) {
		return supplierMasterDao.save(object);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return supplierMasterDao.getById(beanClassName, id);
	}

	@Transactional
	@Override
	public boolean update(Object object) {
		return supplierMasterDao.update(object);
	}

}

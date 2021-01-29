package com.vetologic.medbill.models.service.productMaster;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.medbill.models.dao.productMaster.ProductMasterDao;

@Service
public class ProductMasterServiceImplementaion implements ProductMasterService {
	@Autowired
	private ProductMasterDao productDao;

	@Override
	public List<?> getAll(String beanClassName) {
		return productDao.getAll(beanClassName);
	}

	@Transactional
	@Override
	public int save(Object object) {
		return productDao.save(object);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return productDao.getById(beanClassName, id);
	}

	@Transactional
	@Override
	public boolean update(Object object) {
		return productDao.update(object);
	}

	@Override
	public List<?> getAllExceptOne(String beanClassName, int id) {
		return productDao.getAllExceptOne(beanClassName, id);
	}

}

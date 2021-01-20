package com.vetologic.medbill.models.service.productCategoryMaster;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.medbill.models.dao.productCategoryMaster.ProductCategoryDao;

@Service
public class ProductCategoryServiceImplementation implements ProductCategoryService{

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Override
	public List<?> getAll(String beanClassName) {
		return productCategoryDao.getAll(beanClassName);
	}
	
	@Transactional
	@Override
	public int save(Object object) {
		return productCategoryDao.save(object);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return productCategoryDao.getById(beanClassName, id);
	}

	@Transactional
	@Override
	public boolean update(Object object) {
		return productCategoryDao.update(object);
	}

	@Override
	public List<?> getAllExceptOne(String beanClassName, int id) {
		return productCategoryDao.getAllExceptOne(beanClassName, id);
	}
}

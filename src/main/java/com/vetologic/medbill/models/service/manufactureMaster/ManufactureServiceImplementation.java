package com.vetologic.medbill.models.service.manufactureMaster;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetologic.medbill.models.dao.manufactureMaster.ManufactureDao;

@Service
public class ManufactureServiceImplementation implements ManufactureService{
	
	@Autowired
	private ManufactureDao manufactureDao;
	
	@Override
	public List<?> getAll(String beanClassName) {
		return manufactureDao.getAll(beanClassName);
	}
	
	@Transactional
	@Override
	public int save(Object object) {
		return manufactureDao.save(object);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return manufactureDao.getById(beanClassName, id);
	}

	@Transactional
	@Override
	public boolean update(Object object) {
		return manufactureDao.update(object);
	}

	@Override
	public List<?> getAllExceptOne(String beanClassName, int id) {
		return manufactureDao.getAllExceptOne(beanClassName, id);
	}
}

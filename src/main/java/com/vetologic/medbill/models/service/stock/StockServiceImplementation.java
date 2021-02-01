package com.vetologic.medbill.models.service.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetologic.medbill.beans.stock.StockBean;
import com.vetologic.medbill.beans.stock.StockItemBean;
import com.vetologic.medbill.models.dao.stock.StockDao;

@Service
public class StockServiceImplementation implements StockService {

	@Autowired
	private StockDao stockDao;

	@Override
	public int save(Object object) {
		return stockDao.save(object);
	}

	@Transactional
	@Override
	public boolean deleteStock(StockBean stock) {
		return stockDao.deleteStock(stock);
	}

	@Override
	public List<?> getAll(String beanClassName) {
		return stockDao.getAll(beanClassName);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return stockDao.getById(beanClassName, id);
	}

	@Transactional
	@Override
	public boolean update(Object object) {
		return stockDao.update(object);
	}

	@Override
	public List<?> getStockItemListByStockId(String beanClassName, int id) {
		return stockDao.getStockItemListByStockId(beanClassName, id);
	}

	@Override
	public List<?> getAllExceptOne(String beanClassName, int id) {
		return stockDao.getAllExceptOne(beanClassName, id);
	}

	@Override
	public StockItemBean getStockItemBeanById(String beanName, int stockBeanId, int stockListId) {
		return stockDao.getStockItemBeanById(beanName, stockBeanId, stockListId);
	}

	@Override
	public Object getByOrderNumber(String beanClassName, int orderBean) {
		return stockDao.getByOrderNumber(beanClassName, orderBean);
	}
}

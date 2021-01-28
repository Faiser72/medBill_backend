package com.vetologic.medbill.models.service.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetologic.medbill.beans.stock.StockBean;
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
}

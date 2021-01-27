package com.vetologic.medbill.models.dao.stock;

import java.util.List;

import com.vetologic.medbill.beans.stock.StockBean;

public interface StockDao {

	int save(Object object);

	boolean deleteStock(StockBean stock);
	
//	List<?> getAll(String beanClassName);


}

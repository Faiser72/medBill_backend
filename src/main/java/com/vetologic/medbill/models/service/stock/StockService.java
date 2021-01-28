package com.vetologic.medbill.models.service.stock;

import java.util.List;

import com.vetologic.medbill.beans.stock.StockBean;

public interface StockService {

	int save(Object object);

	boolean deleteStock(StockBean stock);

	List<?> getAll(String beanClassName);

}

package com.vetologic.medbill.models.service.stock;

import com.vetologic.medbill.beans.stock.StockBean;

public interface StockService {

	int save(Object object);

	boolean deleteStock(StockBean stock);

}

package com.vetologic.medbill.models.dao.stock;

import java.util.List;

import com.vetologic.medbill.beans.stock.StockBean;
import com.vetologic.medbill.beans.stock.StockItemBean;

public interface StockDao {

	int save(Object object);

	boolean deleteStock(StockBean stock);

	List<?> getAll(String beanClassName);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

	List<?> getStockItemListByStockId(String beanClassName, int id);

	List<?> getAllExceptOne(String beanClassName, int id);

	StockItemBean getStockItemBeanById(String beanName, int stockBeanId, int stockListId);

	Object getByOrderNumber(String beanClassName, int orderBean);

	boolean deleteStockItemByStockId(int stockId);

	List<?> getStockItemListByProductId(String beanClassName, int productId);

	List<?> getAllPurchaseEntryListBtwnDatesAndPayment(String beanClassName, String fromDate, String toDate);

	Object getByStockItemId(String beanClassName, int stockItemId);

}

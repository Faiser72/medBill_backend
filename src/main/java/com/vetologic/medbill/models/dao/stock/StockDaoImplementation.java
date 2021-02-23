package com.vetologic.medbill.models.dao.stock;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vetologic.medbill.beans.stock.StockBean;
import com.vetologic.medbill.beans.stock.StockItemBean;

@Repository
public class StockDaoImplementation implements StockDao {

	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public int save(Object object) {
		Serializable serializable = 0;
		Session session = getSession();
		try {
			serializable = session.save(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) serializable;
	}

	@Override
	public boolean deleteStock(StockBean stock) {
		Session session = getSession();
		try {
			session.delete(stock);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<?> getAll(String beanClassName) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0");
			query.setParameter(0, 0);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public Object getById(String beanClassName, int id) {
		Session session = getSession();
		Object object = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND stockId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, id);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public boolean update(Object object) {
		Session session = getSession();
		try {
			session.update(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<?> getStockItemListByStockId(String beanClassName, int id) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND stockId.stockId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, id);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public List<?> getAllExceptOne(String beanClassName, int id) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE deletionFlag=?0 AND id NOT IN(?1)");
			query.setParameter(0, 0);
			query.setParameter(1, id);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public StockItemBean getStockItemBeanById(String beanName, int stockBeanId, int stockListId) {
		Session session = getSession();
		StockItemBean object = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanName
					+ " WHERE deletionFlag = ?0 AND stockId.stockId = ?1 AND purcItemBean.purchaseEntryItemId=?2 ");
			query.setParameter(0, 0);
			query.setParameter(1, stockBeanId);
			query.setParameter(2, stockListId);
			object = (StockItemBean) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public Object getByOrderNumber(String beanClassName, int orderBean) {
		Session session = getSession();
		Object object = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND orderNumber.orderId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, orderBean);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Transactional
	@Override
	public boolean deleteStockItemByStockId(int stockId) {
		Session session = getSession();
		try {
			Query<?> query = session.createQuery("UPDATE StockItemBean SET deletionFlag=?0 WHERE stockId.stockId=?1");
			query.setParameter(0, 1);
			query.setParameter(1, stockId);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<?> getStockItemListByProductId(String beanClassName, int productId) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName
					+ " WHERE deletionFlag = ?0 AND returnFlag=false AND productName.productId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, productId);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public List<?> getAllPurchaseEntryListBtwnDatesAndPayment(String beanClassName, String fromDate, String toDate) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName
					+ " WHERE deletionFlag = ?0 AND returnFlag=false AND expiryDate BETWEEN ?1 AND ?2");
			query.setParameter(0, 0);
			query.setParameter(1, fromDate);
			query.setParameter(2, toDate);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

}

package com.vetologic.medbill.models.dao.salesOrder;

import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class SalesOrderDaoImplementation implements SalesOrderDao {

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
	public List<?> getAll(String beanClassName) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND cancellationFlag = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, 0);
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
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND salesOrderId = ?1");
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
	public String getSalesInvoiceNumber() {
		String ticketId = null;
		Session session = getSession();
		try {
			Query<String> query = session.createQuery("SELECT MAX(invoiceNumber) FROM SalesOrderBean", String.class);
			ticketId = query.uniqueResult();
			return ticketId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketId;
	}

	@Override
	public List<?> getAllProductCategoryId(String beanClassName, int id) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND productType.categoryId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, id);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public List<?> getAllSalesOrderListById(String beanClassName, int id) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND salesId.salesOrderId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, id);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public Object getStockDeatailById(String beanClassName, int id) {
		Session session = getSession();
		Object object = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND stockItemId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, id);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public List<?> getAllSalesOrdersDeleted(String beanClassName) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0");
			query.setParameter(0, 1);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public Object getDeletedById(String beanClassName, int id) {
		Session session = getSession();
		Object object = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND salesOrderId = ?1");
			query.setParameter(0, 1);
			query.setParameter(1, id);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public List<?> getAllSalesOrdersCanceled(String beanClassName) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE cancellationFlag = ?0");
			query.setParameter(0, 1);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public Object getCanceledById(String beanClassName, int id) {
		Session session = getSession();
		Object object = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE cancellationFlag = ?0 AND salesOrderId = ?1");
			query.setParameter(0, 1);
			query.setParameter(1, id);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public Object getSalesOrderItemBeanById(String string, int salesItemId) {
		Session session = getSession();
		Object object = null;
		try {
			Query<?> query = session.createQuery("FROM " + string + " WHERE deletionFlag = ?0 AND salesItemId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, salesItemId);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public List<?> getAllSalesListBtwnDatesAndPayment(String beanClassName, String fromDate, String toDate,
			String paymentMode) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName
					+ " WHERE deletionFlag = ?0 AND cancellationFlag=?4 AND salesDate BETWEEN ?1 AND ?2 AND paymentMode=?3");
			query.setParameter(0, 0);
			query.setParameter(1, fromDate);
			query.setParameter(2, toDate);
			query.setParameter(3, paymentMode);
			query.setParameter(4, 0);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

}

package com.vetologic.medbill.models.dao.order;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vetologic.medbill.beans.order.OrderItemBean;

@Repository
public class OrderDaoImplementation implements OrderDao {

	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public List<?> getAllProductCategoryId(String beanClassName, int id) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery(
					"FROM " + beanClassName + " WHERE deletionFlag = ?0 and productCategory.categoryId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, id);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public String getOrderMaxId() {
		String ticketId = null;
		Session session = getSession();
		try {
			Query<String> query = session.createQuery("SELECT MAX(orderNumber) FROM OrderBean", String.class);
			ticketId = query.uniqueResult();
			return ticketId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketId;
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
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND cancellationFlag = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, 0);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public List<?> getOrderListByOederId(String beanClassName, int id) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag =?0 AND orderId.orderId =?1");
			query.setParameter(0, 0);
			query.setParameter(1, id);
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
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND orderId = ?1");
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
	public List<?> getAllOrdersDeleted(String beanClassName) {
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
	public Object getDetedById(String beanClassName, int id) {
		Session session = getSession();
		Object object = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND orderId = ?1");
			query.setParameter(0, 1);
			query.setParameter(1, id);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public List<?> getAllOrdersCanceled(String beanClassName) {
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
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE cancellationFlag = ?0 AND orderId = ?1");
			query.setParameter(0, 1);
			query.setParameter(1, id);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public List<?> getOrderByOrderId(String beanClassName, int id) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND orderId = ?1");
			query.setParameter(0, 0);
			query.setParameter(1, id);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public OrderItemBean getOrderItemBeanById(String string, int orderItemId) {
		Session session = getSession();
		OrderItemBean object = null;
		try {
			Query<OrderItemBean> query = session.createQuery("FROM " + string + " WHERE deletionFlag = ?0 AND orderItemId = ?1" ,OrderItemBean.class);
			query.setParameter(0, 0);
			query.setParameter(1, orderItemId);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@Override
	public List<?> getAllOrderListBtwnDates(String beanClassName, String fromDate, String toDate) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName
					+ " WHERE deletionFlag = ?0  AND orderDate BETWEEN ?1 AND ?2 AND cancellationFlag = ?3");
			query.setParameter(0, 0);
			query.setParameter(1, fromDate);
			query.setParameter(2, toDate);
			query.setParameter(3, 0);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}
}

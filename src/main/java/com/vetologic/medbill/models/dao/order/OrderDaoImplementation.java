package com.vetologic.medbill.models.dao.order;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImplementation implements OrderDao{

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
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 and productCategory.categoryId = ?1");
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
			Query<String> query = session.createQuery("SELECT MAX(orderNumber) FROM OrderBean",String.class);
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
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0");
			query.setParameter(0, 0);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}
}

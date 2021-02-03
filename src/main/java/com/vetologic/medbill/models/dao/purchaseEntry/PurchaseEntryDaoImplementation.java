package com.vetologic.medbill.models.dao.purchaseEntry;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryBean;
import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryItemBean;

@Repository
public class PurchaseEntryDaoImplementation implements PurchaseEntryDao {

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
	public boolean deletePurchaseEntry(PurchaseEntryBean purchase) {
		Session session = getSession();
		try {
			session.delete(purchase);
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
			Query<?> query = session
					.createQuery("FROM " + beanClassName + " WHERE deletionFlag = ?0 AND purchaseEntryId = ?1");
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
	public List<?> getPurchaseEntryItemListByPurchaseEntryId(String beanClassName, int id) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery(
					"FROM " + beanClassName + " WHERE deletionFlag = ?0 AND purchaseEntryId.purchaseEntryId = ?1");
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
	public PurchaseEntryItemBean getPurchaseEntryItemBeanById(String branName, int purchaseBeanId, int purchaseListId) {
		Session session = getSession();
		PurchaseEntryItemBean object = null;
		try {
			Query<?> query = session.createQuery("FROM " + branName
					+ " WHERE deletionFlag = ?0 AND purchaseEntryId.purchaseEntryId = ?1 AND purchaseEntryItemId=?2 ");
			query.setParameter(0, 0);
			query.setParameter(1, purchaseBeanId);
			query.setParameter(2, purchaseListId);
			object = (PurchaseEntryItemBean) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Transactional
	@Override
	public boolean deletePurchaseEntryItemListByPurchaseEntryId(int purchaseEntryId) {
		Session session = getSession();
		try {
			Query<?> query = session.createQuery("UPDATE PurchaseEntryItemBean SET deletionFlag=?0 WHERE purchaseEntryId.purchaseEntryId=?1");
			query.setParameter(0, 1);
			query.setParameter(1, purchaseEntryId);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

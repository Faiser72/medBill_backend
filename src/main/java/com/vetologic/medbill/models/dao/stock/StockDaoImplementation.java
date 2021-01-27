package com.vetologic.medbill.models.dao.stock;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vetologic.medbill.beans.stock.StockBean;


@Repository
public class StockDaoImplementation implements StockDao{

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
}

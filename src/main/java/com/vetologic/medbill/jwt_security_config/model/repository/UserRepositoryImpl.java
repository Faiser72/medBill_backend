package com.vetologic.medbill.jwt_security_config.model.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vetologic.medbill.beans.users.AdminBean;



@Repository
public class UserRepositoryImpl implements UserRepository {
	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Transactional
	@Override
	public AdminBean findByUsername(String username) {
		Session session = getSession();
		AdminBean userAdmin = null;
		try {
			Query<AdminBean> query = session.createQuery("FROM AdminBean WHERE deletionFlag=?0 AND username=?1",
					AdminBean.class);
			query.setParameter(0, 0);
			query.setParameter(1, username);
//			query.setParameter(2, "ROLE_ADMIN");
			userAdmin = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userAdmin;
	}

}

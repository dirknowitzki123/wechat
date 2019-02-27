package com.by.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BaseDaoOracle extends ABaseDao{
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
}

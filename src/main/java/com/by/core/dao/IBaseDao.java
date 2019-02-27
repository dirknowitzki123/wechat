package com.by.core.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public interface IBaseDao {
	public Session getSession();
	public void save(Object obj);
	public void update(Object obj);
	@SuppressWarnings("rawtypes")
	public void delete(Class Clazz, Serializable id);
	public void delete(Object obj);
	public void delete(final List<?> objs);
	@SuppressWarnings("rawtypes")
	public void delete(final List<String> ids,Class clazz);
	@SuppressWarnings("rawtypes")
	public void delete(final String[] ids,Class clazz);
	@SuppressWarnings("rawtypes")
	public Object get(Class clazz, Serializable id);
	public String getUUID();
}

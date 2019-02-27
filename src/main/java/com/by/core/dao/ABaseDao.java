package com.by.core.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.id.Hex;
import org.hibernate.SessionFactory;

public abstract class ABaseDao implements IBaseDao{
	public abstract SessionFactory getSessionFactory();
	
	@Override
	public void save(Object obj) {
		getSessionFactory().getCurrentSession().save(obj);
		getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public void update(Object obj) {
		getSessionFactory().getCurrentSession().saveOrUpdate(obj);
		getSessionFactory().getCurrentSession().flush();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void delete(Class clazz, Serializable id) {
		getSessionFactory().getCurrentSession().delete(get(clazz, id));
		getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void delete(Object obj) {
		getSessionFactory().getCurrentSession().delete(obj);
		getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public void delete(final List<?> objs){
		if(CollectionUtils.isEmpty(objs))return;
		int max=objs.size();
		for(int i=0;i<max;++i){
			getSessionFactory().getCurrentSession().refresh(objs.get(i));
			getSessionFactory().getCurrentSession().delete(objs.get(i));
			if (((i != 0) && (i % 50 == 0)) || (i == max - 1))
				getSessionFactory().getCurrentSession().flush();
		}
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void delete(final List<String> ids,Class clazz){
		if(CollectionUtils.isEmpty(ids))return;
		for(int i=0;i<ids.size();++i){
			Object obj=get(clazz,ids.get(i));
			if(obj==null) continue;
			getSessionFactory().getCurrentSession().delete(obj);
			if (((i != 0) && (i % 50 == 0)) || (i == ids.size() - 1))
				getSessionFactory().getCurrentSession().flush();
		}
		
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void delete(final String[] ids,Class clazz){
		if(ids==null||ids.length==0)return;
		for(int i=0;i<ids.length;++i){
			Object obj=get(clazz,ids[i]);
			if(obj==null) continue;
			getSessionFactory().getCurrentSession().delete(obj);
			if (((i != 0) && (i % 50 == 0)) || (i == ids.length - 1))
				getSessionFactory().getCurrentSession().flush();
		}
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Object get(Class clazz, Serializable id) {
		return getSessionFactory().getCurrentSession().get(clazz, id);
	}
	@Override
	public String getUUID() {
		return new String(Hex.encodeHex(org.apache.commons.id.uuid.UUID.randomUUID().getRawBytes()));
	}
}

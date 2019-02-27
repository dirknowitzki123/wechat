package com.by.core.service;

import java.util.List;
import java.util.Map;

public interface IBaseService<T> {
	T get(Map<String,Object> map);
	List<T> getList(Map<String,Object> map);
	void save(T obj);
	void update(T Obj);
	void delete(List<String> ids );
}

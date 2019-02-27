package com.by.core.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基本操作mapper类
 * @author jqiu
 * @param <T>
 */
public interface BaseMapper<T> {
	/**
	 * 插入对象
	 * @param vo
	 */
	public void insert(T vo);
	/**
	 * 通过主键更新对象
	 * @param params
	 */
	public void updateByPrimaryKey(Map<String, Object> params);
	/**
	 * 通过主键删除对象
	 * @param primaryKey
	 */
	public void deleteByPrimaryKey(Serializable primaryKey);
	/**
	 * 通过主键获得对象
	 * @param primaryKey
	 * @return
	 */
	public T getByPrimaryKey(Serializable primaryKey);
	/**
	 * 获得对象
	 * @param params where条件
	 * @return
	 */
	public T get(Map<String, Object> params);
	/**
	 * 获得对象列表
	 * @param params where条件
	 * @return
	 */
	public List<T> getList(Map<String, Object> params);

}

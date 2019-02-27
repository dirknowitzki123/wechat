package com.by.core.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes","unchecked"})
public class Page<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static final String SUCCESS_MESSAGE = "操作成功";
	private static final String FAIL_MESSAGE = "操作失败";
	private static final String __PAGENO__ = "__pageNo__";
	private static final String __PAGESIZE__ = "__pageSize__";
	private static final String __ORDERBY__ = "__orderBy__";
	
	
	private Boolean success = true;
	private String msg;
	private String code;
	
	private T t;
	private List<T> list;
	private Map<String,Object> map = new HashMap<String,Object>();
	
	private Map<String,Object> params = new HashMap<String,Object>();//查询条件
	
	private Boolean isPage=false; //是否分頁，默认不分页
	private Integer pageNo = 1;
	private Integer pageSize = 10;//每页显示数
	private Long totalSize;//总记录数
	
	private String order;
	private String orderDesc="asc";
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		if ( StringUtils.isEmpty(msg) ) {
			msg = success ? SUCCESS_MESSAGE : FAIL_MESSAGE; 
		}
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List list) {
		if ( list != null && list instanceof com.github.pagehelper.Page ) {
			com.github.pagehelper.Page pager = (com.github.pagehelper.Page) list;
			this.setTotalSize(pager.getTotal());
		}
		this.list = list;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		this.setIsPage( this.isPage );
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		this.setIsPage( this.isPage );
	}
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
		if ( StringUtils.isNotEmpty(order) ) {
			this.params.put(__ORDERBY__, order + " " + this.getOrderDesc() );
		}
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
		this.setOrder(order);
	}
	public Boolean getIsPage() {
		return isPage;
	}
	public void setIsPage(Boolean isPage) {
		this.isPage = isPage;
		if ( this.isPage ) {
			this.params.put(__PAGENO__, this.getPageNo());
			this.params.put(__PAGESIZE__, this.getPageSize());
		}
	}
	/**
	 * 后台强制分页
	 */
	public void startPage() {
		this.setIsPage(true);
	}
	/**
	 * 后台强制分页
	 * @param pageNo 当前页
	 */
	public void startPage(Integer pageNo) {
		this.setPageNo(pageNo);
		this.setIsPage(true);
	}
	/**
	 * 后台强制分页
	 * @param pageNo 当前页
	 * @param order 排序字段 数据库同名
	 */
	public void startPage(Integer pageNo, String order) {
		this.setPageNo(pageNo);
		this.setIsPage(true);
		this.setOrder(order);
	}
	/**
	 * 后台强制分页
	 * @param pageNo 当前页
	 * @param order 排序字段 数据库同名
	 * @param orderDesc 排序顺序
	 */
	public void startPage(Integer pageNo, String order, String orderDesc) {
		this.setPageNo(pageNo);
		this.setIsPage(true);
		this.orderBy(order, orderDesc);
	}
	/**
	 * 后台强制分页
	 * @param pageNo 当前页
	 * @param pageSize 分页条数
	 */
	public void startPage(Integer pageNo, Integer pageSize) {
		this.setPageNo(pageNo);
		this.setPageSize(pageSize);
		this.setIsPage(true);
	}
	/**
	 * 后台强制分页
	 * @param pageNo 当前页
	 * @param pageSize 分页条数
	 * @param order 排序字段 数据库同名
	 */
	public void startPage(Integer pageNo, Integer pageSize, String order) {
		this.setPageNo(pageNo);
		this.setPageSize(pageSize);
		this.setIsPage(true);
		this.orderBy(order);
	}
	/**
	 * 后台强制分页
	 * @param pageNo 当前页
	 * @param pageSize 分页条数
	 * @param order 排序字段 数据库同名
	 * @param orderDesc 排序顺序
	 */
	public void startPage(Integer pageNo, Integer pageSize, String order, String orderDesc) {
		this.setPageNo(pageNo);
		this.setPageSize(pageSize);
		this.setIsPage(true);
		this.orderBy(order, orderDesc);
	}
	/**
	 * 排序
	 * @param order 排序字段 数据库同名
	 */
	public void orderBy(String order) {
		this.setOrder(order);
	}
	/**
	 * 排序
	 * @param order 排序字段 数据库同名
	 * @param orderDesc 排序顺序
	 */
	public void orderBy(String order, String orderDesc) {
		this.setOrder(order);
		this.setOrderDesc(orderDesc);
	}
}

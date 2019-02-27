package com.by.system.model;

import java.io.Serializable;

/**
 *  对象
 * @author autocreate
 * @create 2016-03-20
 */
public class ASysResc implements Serializable{
	private static final long serialVersionUID = 1L;
    /***  */
  	private String id ; 
    
    /*** 资源名称 */
  	private String rescName ; 
    
    /*** 资源类型 */
  	private String rescCode ; 
    
    /*** 资源URI */
  	private String rescUri ; 
    
    /*** 资源类型 */
  	private String rescType ; 
    
    /*** 资源备注 */
  	private String rescRemark ; 

    //构造函数
    public ASysResc(){}

    //getter和setter方法
    
    /**
	 * 获取 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

    
    /**
	 * 获取 资源名称
	 * @return String
	 */
	public String getRescName() {
		return rescName;
	}

	/**
	 * 设置 资源名称
	 * @param rescName
	 */
	public void setRescName(String rescName) {
		this.rescName = rescName;
	}

    
    /**
	 * 获取 资源类型
	 * @return String
	 */
	public String getRescCode() {
		return rescCode;
	}

	/**
	 * 设置 资源类型
	 * @param rescCode
	 */
	public void setRescCode(String rescCode) {
		this.rescCode = rescCode;
	}

    
    /**
	 * 获取 资源URI
	 * @return String
	 */
	public String getRescUri() {
		return rescUri;
	}

	/**
	 * 设置 资源URI
	 * @param rescUri
	 */
	public void setRescUri(String rescUri) {
		this.rescUri = rescUri;
	}

    
    /**
	 * 获取 资源类型
	 * @return String
	 */
	public String getRescType() {
		return rescType;
	}

	/**
	 * 设置 资源类型
	 * @param rescType
	 */
	public void setRescType(String rescType) {
		this.rescType = rescType;
	}

    
    /**
	 * 获取 资源备注
	 * @return String
	 */
	public String getRescRemark() {
		return rescRemark;
	}

	/**
	 * 设置 资源备注
	 * @param rescRemark
	 */
	public void setRescRemark(String rescRemark) {
		this.rescRemark = rescRemark;
	}

}
package com.by.system.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_MENU")
public class ASysMenu implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String menuName;
	private String menuCode;
	private Integer menuLevel;
	private String parentMenuId;
	private String url;
	private String isUserAble;
	private Integer byOrder;
	private String remark;
	private String menuIcon;
	private String menuType;
	private String sysCode;
	private String sysName;
	private Date instDate;
	private String instUserNo;
	private Date updtDate;
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="MENU_NAME",length=128,nullable=true)
	public String getMenuName(){
		return menuName;
	}
	public void setMenuName(String menuName){
		this.menuName=menuName;
	}
	@Column(name="MENU_CODE",length=32,nullable=true)
	public String getMenuCode(){
		return menuCode;
	}
	public void setMenuCode(String menuCode){
		this.menuCode=menuCode;
	}
	@Column(name="MENU_LEVEL",length=11,nullable=true)
	public Integer getMenuLevel(){
		return menuLevel;
	}
	public void setMenuLevel(Integer menuLevel){
		this.menuLevel=menuLevel;
	}
	@Column(name="PARENT_MENU_ID",length=32,nullable=true)
	public String getParentMenuId(){
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId){
		this.parentMenuId=parentMenuId;
	}
	@Column(name="URL",length=255,nullable=true)
	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url=url;
	}
	@Column(name="IS_USER_ABLE",length=8,nullable=true)
	public String getIsUserAble(){
		return isUserAble;
	}
	public void setIsUserAble(String isUserAble){
		this.isUserAble=isUserAble;
	}
	@Column(name="BY_ORDER",length=11,nullable=true)
	public Integer getByOrder(){
		return byOrder;
	}
	public void setByOrder(Integer byOrder){
		this.byOrder=byOrder;
	}
	@Column(name="REMARK",length=128,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	@Column(name="MENU_ICON",length=128,nullable=true)
	public String getMenuIcon(){
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon){
		this.menuIcon=menuIcon;
	}
	@Column(name="MENU_TYPE",length=32,nullable=true)
	public String getMenuType(){
		return menuType;
	}
	public void setMenuType(String menuType){
		this.menuType=menuType;
	}
	@Column(name="SYS_CODE",length=128,nullable=true)
	public String getSysCode(){
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode=sysCode;
	}
	@Column(name="SYS_NAME",length=128,nullable=true)
	public String getSysName(){
		return sysName;
	}
	public void setSysName(String sysName){
		this.sysName=sysName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INST_DATE",length=19,nullable=true)
	public Date getInstDate(){
		return instDate;
	}
	public void setInstDate(Date instDate){
		this.instDate=instDate;
	}
	@Column(name="INST_USER_NO",length=40,nullable=true)
	public String getInstUserNo(){
		return instUserNo;
	}
	public void setInstUserNo(String instUserNo){
		this.instUserNo=instUserNo;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDT_DATE",length=19,nullable=true)
	public Date getUpdtDate(){
		return updtDate;
	}
	public void setUpdtDate(Date updtDate){
		this.updtDate=updtDate;
	}
}


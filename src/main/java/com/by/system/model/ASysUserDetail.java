package com.by.system.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
@Entity
@Table(name = "A_SYS_USER_DETAIL")
public class ASysUserDetail implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	private String idType;
	private String idNo;
	private String college;
	private String leaderComment;
	private String levelNo;
	private String levelName;
	private String positionNo;
	private String positionName;
	private String titleNo;
	private String titleName;
	private String workArea;
	private String natiPlac;
	private String nowAddre;
	private String homeRegiType;
	private String arriWorkDate;
	private Integer finaObtaTimes;
	private String weixinAddr;
	private String profInfo;
	private String bankCardNo;
	private String openAcctInfo;
	private String prvCompName;
	private String soseInfo;
	private String emplChnl;
	
	@Id
	@Column(name="ID",length=32,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="USER_NAME",length=128,nullable=true)
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	@Column(name="ID_TYPE",length=18,nullable=true)
	public String getIdType(){
		return idType;
	}
	public void setIdType(String idType){
		this.idType=idType;
	}
	@Column(name="ID_NO",length=32,nullable=true)
	public String getIdNo(){
		return idNo;
	}
	public void setIdNo(String idNo){
		this.idNo=idNo;
	}
	@Column(name="COLLEGE",length=32,nullable=true)
	public String getCollege(){
		return college;
	}
	public void setCollege(String college){
		this.college=college;
	}
	@Column(name="LEADER_COMMENT",length=512,nullable=true)
	public String getLeaderComment(){
		return leaderComment;
	}
	public void setLeaderComment(String leaderComment){
		this.leaderComment=leaderComment;
	}
	@Column(name="LEVEL_NO",length=8,nullable=true)
	public String getLevelNo(){
		return levelNo;
	}
	public void setLevelNo(String levelNo){
		this.levelNo=levelNo;
	}
	@Column(name="LEVEL_NAME",length=8,nullable=true)
	public String getLevelName(){
		return levelName;
	}
	public void setLevelName(String levelName){
		this.levelName=levelName;
	}
	@Column(name="POSITION_NO",length=8,nullable=true)
	public String getPositionNo(){
		return positionNo;
	}
	public void setPositionNo(String positionNo){
		this.positionNo=positionNo;
	}
	@Column(name="POSITION_NAME",length=32,nullable=true)
	public String getPositionName(){
		return positionName;
	}
	public void setPositionName(String positionName){
		this.positionName=positionName;
	}
	@Column(name="TITLE_NO",length=18,nullable=true)
	public String getTitleNo(){
		return titleNo;
	}
	public void setTitleNo(String titleNo){
		this.titleNo=titleNo;
	}
	@Column(name="TITLE_NAME",length=32,nullable=true)
	public String getTitleName(){
		return titleName;
	}
	public void setTitleName(String titleName){
		this.titleName=titleName;
	}
	@Column(name="WORK_AREA",length=32,nullable=true)
	public String getWorkArea() {
		return workArea;
	}
	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}
	@Column(name="NATI_PLAC",length=256,nullable=true)
	public String getNatiPlac() {
		return natiPlac;
	}
	public void setNatiPlac(String natiPlac) {
		this.natiPlac = natiPlac;
	}
	@Column(name="NOW_ADDRE",length=256,nullable=true)
	public String getNowAddre() {
		return nowAddre;
	}
	public void setNowAddre(String nowAddre) {
		this.nowAddre = nowAddre;
	}
	@Column(name="HOME_REGI_TYPE",length=32,nullable=true)
	public String getHomeRegiType() {
		return homeRegiType;
	}
	public void setHomeRegiType(String homeRegiType) {
		this.homeRegiType = homeRegiType;
	}
	@Column(name="ARRI_WORK_DATE",length=17,nullable=true)
	public String getArriWorkDate() {
		return arriWorkDate;
	}
	public void setArriWorkDate(String arriWorkDate) {
		this.arriWorkDate = arriWorkDate;
	}
	@Column(name="FINA_OBTA_TIMES",length=11,nullable=true)
	public Integer getFinaObtaTimes() {
		return finaObtaTimes;
	}
	public void setFinaObtaTimes(Integer finaObtaTimes) {
		this.finaObtaTimes = finaObtaTimes;
	}
	
	@Column(name="WEIXIN_ADDR",length=64,nullable=true)
	public String getWeixinAddr() {
		return weixinAddr;
	}
	public void setWeixinAddr(String weixinAddr) {
		this.weixinAddr = weixinAddr;
	}
	@Column(name="PROF_INFO",length=64,nullable=true)
	public String getProfInfo() {
		return profInfo;
	}
	public void setProfInfo(String profInfo) {
		this.profInfo = profInfo;
	}
	@Column(name="BANK_CARD_NO",length=32,nullable=true)
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	@Column(name="OPEN_ACCT_INFO",length=128,nullable=true)
	public String getOpenAcctInfo() {
		return openAcctInfo;
	}
	public void setOpenAcctInfo(String openAcctInfo) {
		this.openAcctInfo = openAcctInfo;
	}
	@Column(name="PRV_COMP_NAME",length=64,nullable=true)
	public String getPrvCompName() {
		return prvCompName;
	}
	public void setPrvCompName(String prvCompName) {
		this.prvCompName = prvCompName;
	}
	
	@Column(name="SOSE_INFO",length=32,nullable=true)
	public String getSoseInfo() {
		return soseInfo;
	}
	public void setSoseInfo(String soseInfo) {
		this.soseInfo = soseInfo;
	}
	@Column(name="EMPL_CHNL",length=32,nullable=true)
	public String getEmplChnl() {
		return emplChnl;
	}
	public void setEmplChnl(String emplChnl) {
		this.emplChnl = emplChnl;
	}
	
	
}


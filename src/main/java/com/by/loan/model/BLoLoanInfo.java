package com.by.loan.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "B_LO_LOAN_INFO")
public class BLoLoanInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**  */
	private String id;
	/** 贷款编号 */
	private String loanNo;
	/** 客户编号 */
	private String custNo;
	/** 客户名字 */
	private String custName;
	/** 贷款本金 */
	private BigDecimal loanAmt;
	/** 合同金额 **/
	private BigDecimal contrAmt;
	/** 贷款类型（码类：Loan_Type） */
	private String loanTyp;
	/** 分期期数 */
	private Integer instNum;
	/** 首付金额 */
	private BigDecimal fstPayAmt;
	/** 首次还款额 */
	private BigDecimal fstRepayAmt;
	/** 首次还款日 */
	private String fstRepayDate;
	/** 每月还款日 */
	private String mthRepayDate;
	/** 月还款金额 */
	private BigDecimal mthRepayAmt;
	/** 申请日期 */
	private Date applyDate;
	/** 审批日期 */
	private Date aprvDate;
	/** 注册日期 */
	private Date regDate;
	/** 放款日期 */
	private Date distrDate;
	/** 档案编号 */
	private String fileNo;
	/** 贷款状态（码类:Aprov_Result） */
	private String stat;
	/** 贷后状态（码类：Loan_After） */
	private String afterStat;
	/** 五级分类（码类：Fiv_Cls） */
	private String fivCls;
	/** 是否自营(码类：Is_No) */
	private String proprFlag;
	/** 是否前置（码类：Is_No，是：13900001   否：13900002） */
	private String checkFlag;
	/** 前置收费金额 */
	private BigDecimal checkAmt;
	/** 前置收费月还款 */
	private BigDecimal checkMthAmt;
	/** 是否分账（码类：Is_No，是：13900001   否：13900002） */
	private String checkAccFlag;
	/** 工单号 */
	private String workOrderId;
	/** 贷款用途 */
	private String loanUse;
	/** 办理所在省 */
	private String applyProv;
	/** 办理所在区/县 */
	private String applyArea;
	/** 办理所在市 */
	private String applyCity;
	/** 来源客户端类型(码类：Source_Type) */
	private String sourceType;
	/** 来源人员类型(码类：Source_User_Type) */
	private String sourceUserType;
	/** 来源系统类型（码类：Source_Os_Type） */
	private String sourceOsType;
	/**  */
	private Date instDate;
	/**  */
	private String instUserNo;
	/**  */
	private Date updtDate;
	/**审批金额**/
	private BigDecimal apprAmt;
	/**是否外访**/
	private String isOutVisit;
	
	@Column(name="ID",length=40,nullable=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Id
	@Column(name="LOAN_NO",length=40,nullable=false,unique=true)
	public String getLoanNo(){
		return loanNo;
	}
	public void setLoanNo(String loanNo){
		this.loanNo=loanNo;
	}
	@Column(name="CUST_NO",length=40,nullable=true)
	public String getCustNo(){
		return custNo;
	}
	public void setCustNo(String custNo){
		this.custNo=custNo;
	}
	@Column(name="CUST_NAME",length=80,nullable=true)
	public String getCustName(){
		return custName;
	}
	public void setCustName(String custName){
		this.custName=custName;
	}
	@Column(name="LOAN_AMT",length=22,nullable=true)
	public BigDecimal getLoanAmt(){
		return loanAmt;
	}
	public void setLoanAmt(BigDecimal loanAmt){
		this.loanAmt=loanAmt;
	}
	@Column(name="LOAN_TYP",length=8,nullable=true)
	public String getLoanTyp(){
		return loanTyp;
	}
	public void setLoanTyp(String loanTyp){
		this.loanTyp=loanTyp;
	}
	@Column(name="INST_NUM",length=11,nullable=true)
	public Integer getInstNum(){
		return instNum;
	}
	public void setInstNum(Integer instNum){
		this.instNum=instNum;
	}
	@Column(name="FST_PAY_AMT",length=22,nullable=true)
	public BigDecimal getFstPayAmt(){
		return fstPayAmt;
	}
	public void setFstPayAmt(BigDecimal fstPayAmt){
		this.fstPayAmt=fstPayAmt;
	}
	@Column(name="FST_REPAY_AMT",length=22,nullable=true)
	public BigDecimal getFstRepayAmt(){
		return fstRepayAmt;
	}
	public void setFstRepayAmt(BigDecimal fstRepayAmt){
		this.fstRepayAmt=fstRepayAmt;
	}
	@Column(name="FST_REPAY_DATE",length=10,nullable=true)
	public String getFstRepayDate(){
		return fstRepayDate;
	}
	public void setFstRepayDate(String fstRepayDate){
		this.fstRepayDate=fstRepayDate;
	}
	@Column(name="MTH_REPAY_DATE",length=10,nullable=true)
	public String getMthRepayDate(){
		return mthRepayDate;
	}
	public void setMthRepayDate(String mthRepayDate){
		this.mthRepayDate=mthRepayDate;
	}
	@Column(name="MTH_REPAY_AMT",length=22,nullable=true)
	public BigDecimal getMthRepayAmt(){
		return mthRepayAmt;
	}
	public void setMthRepayAmt(BigDecimal mthRepayAmt){
		this.mthRepayAmt=mthRepayAmt;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="APPLY_DATE",length=19,nullable=true)
	public Date getApplyDate(){
		return applyDate;
	}
	public void setApplyDate(Date applyDate){
		this.applyDate=applyDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="APRV_DATE",length=19,nullable=true)
	public Date getAprvDate(){
		return aprvDate;
	}
	public void setAprvDate(Date aprvDate){
		this.aprvDate=aprvDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REG_DATE",length=19,nullable=true)
	public Date getRegDate(){
		return regDate;
	}
	public void setRegDate(Date regDate){
		this.regDate=regDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DISTR_DATE",length=19,nullable=true)
	public Date getDistrDate(){
		return distrDate;
	}
	public void setDistrDate(Date distrDate){
		this.distrDate=distrDate;
	}
	@Column(name="FILE_NO",length=10,nullable=true)
	public String getFileNo(){
		return fileNo;
	}
	public void setFileNo(String fileNo){
		this.fileNo=fileNo;
	}
	@Column(name="STAT",length=10,nullable=true)
	public String getStat(){
		return stat;
	}
	public void setStat(String stat){
		this.stat=stat;
	}
	@Column(name="AFTER_STAT",length=10,nullable=true)
	public String getAfterStat(){
		return afterStat;
	}
	public void setAfterStat(String afterStat){
		this.afterStat=afterStat;
	}
	@Column(name="FIV_CLS",length=10,nullable=true)
	public String getFivCls(){
		return fivCls;
	}
	public void setFivCls(String fivCls){
		this.fivCls=fivCls;
	}
	@Column(name="PROPR_FLAG",length=10,nullable=true)
	public String getProprFlag(){
		return proprFlag;
	}
	public void setProprFlag(String proprFlag){
		this.proprFlag=proprFlag;
	}
	@Column(name="CHECK_FLAG",length=10,nullable=true)
	public String getCheckFlag(){
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag){
		this.checkFlag=checkFlag;
	}
	@Column(name="CHECK_AMT",length=22,nullable=true)
	public BigDecimal getCheckAmt(){
		return checkAmt;
	}
	public void setCheckAmt(BigDecimal checkAmt){
		this.checkAmt=checkAmt;
	}
	@Column(name="CHECK_MTH_AMT",length=22,nullable=true)
	public BigDecimal getCheckMthAmt(){
		return checkMthAmt;
	}
	public void setCheckMthAmt(BigDecimal checkMthAmt){
		this.checkMthAmt=checkMthAmt;
	}
	@Column(name="CHECK_ACC_FLAG",length=10,nullable=true)
	public String getCheckAccFlag(){
		return checkAccFlag;
	}
	public void setCheckAccFlag(String checkAccFlag){
		this.checkAccFlag=checkAccFlag;
	}
	@Column(name="WORK_ORDER_ID",length=40,nullable=true)
	public String getWorkOrderId(){
		return workOrderId;
	}
	public void setWorkOrderId(String workOrderId){
		this.workOrderId=workOrderId;
	}
	@Column(name="LOAN_USE",length=200,nullable=true)
	public String getLoanUse(){
		return loanUse;
	}
	public void setLoanUse(String loanUse){
		this.loanUse=loanUse;
	}
	@Column(name="APPLY_PROV",length=128,nullable=true)
	public String getApplyProv(){
		return applyProv;
	}
	public void setApplyProv(String applyProv){
		this.applyProv=applyProv;
	}
	@Column(name="APPLY_AREA",length=128,nullable=true)
	public String getApplyArea(){
		return applyArea;
	}
	public void setApplyArea(String applyArea){
		this.applyArea=applyArea;
	}
	@Column(name="APPLY_CITY",length=128,nullable=true)
	public String getApplyCity(){
		return applyCity;
	}
	public void setApplyCity(String applyCity){
		this.applyCity=applyCity;
	}
	@Column(name="SOURCE_TYPE",length=8,nullable=true)
	public String getSourceType(){
		return sourceType;
	}
	public void setSourceType(String sourceType){
		this.sourceType=sourceType;
	}
	@Column(name="SOURCE_USER_TYPE",length=8,nullable=true)
	public String getSourceUserType(){
		return sourceUserType;
	}
	public void setSourceUserType(String sourceUserType){
		this.sourceUserType=sourceUserType;
	}
	@Column(name="SOURCE_OS_TYPE",length=8,nullable=true)
	public String getSourceOsType(){
		return sourceOsType;
	}
	public void setSourceOsType(String sourceOsType){
		this.sourceOsType=sourceOsType;
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
	@Column(name="APPR_AMT",length=22,nullable=true)
	public BigDecimal getApprAmt() {
		return apprAmt;
	}
	public void setApprAmt(BigDecimal apprAmt) {
		this.apprAmt = apprAmt;
	}
	@Column(name="IS_OUT_VISIT",length=10,nullable=true)
	public String getIsOutVisit() {
		return isOutVisit;
	}
	public void setIsOutVisit(String isOutVisit) {
		this.isOutVisit = isOutVisit;
	}
	@Column(name="CONTR_AMT")
	public BigDecimal getContrAmt() {
		return contrAmt;
	}
	public void setContrAmt(BigDecimal contrAmt) {
		this.contrAmt = contrAmt;
	}
	
}


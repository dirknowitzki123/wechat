package com.by.core.constant;

public class PubBusinessConstant {
	/*** 码表----是 */
	public final static String YES = "13900001";
	/*** 码表----否 */
	public final static String NO = "13900002";
	
	/*** 审核结果----退单*/
	public final static String APROV_RESULT_BACK = "31000001";
	/*** 审核结果----已签约 */
	public final static String APROV_RESULT_SIGN = "31000002";
	/*** 审核结果----取消*/
	public final static String APROV_RESULT_CANCEL = "31000003";
	/*** 审核结果----通过*/
	public final static String APROV_RESULT_PASS = "31000004";
	/*** 审核结果----不通过*/
	public final static String APROV_RESULT_NOPASS = "31000005";
	/*** 审核结果----返回销售修改*/
	public final static String APROV_RESULT_RETMODIFY = "31000006";
	/*** 审核结果----撤销*/
	public final static String APROV_RESULT_REVOKE = "31000007";
	/*** 审核结果----审核中*/
	public final static String APROV_RESULT_AUDITING = "31000008";
	/*** 审核结果----未签约 */
	public final static String APROV_RESULT_UNSIGN = "31000009";
	/*** 审核结果----签约失败 */
	public final static String APROV_RESULT_SIGNFAIL = "31000010";
	/*** 审核结果----待审批 */
	public final static String APROV_RESULT_UNAUDIT = "31000011";
	/*** 审核结果----未提交 */
	public final static String APROV_RESULT_UNSUB = "31000012";
	/*** 审核结果----机审不通过 */
	public final static String APROV_RESULT_MACHINE_TRIAL_NOPASS = "31000016";
	/*** 码表----产品待售 */
	public final static String FOR_SALE = "16500001";
	/*** 码表----产品销售中 */
	public final static String IS_SELLING = "16500002";
	/*** 码表----产品下架 */
	public final static String OFF_SALE = "16500003";
	/*** 登录类型----网点 */
	public final static String LOGIN_TYPE_BIZCHANNEL= "24200002";

	/*** 产品类型----现金贷产品 */
	public final static String PROD_TYPE_CASH = "10100003";
	
	/*** 码组----贷款可撤销状态组 */
	public final static String GRP_CODE_REVOKE = "revok_stat";
	/*** 码组----贷款可取消状态组 */
	public static final String GRP_CODE_CANCEL = "cancel_stat";
	/*** 码组----贷款是历史状态组 */
	public static final String GRP_CODE_HISTORY = "history_stat";
	/*** 码组----贷款是处理中的状态组 */
	public static final String GRP_CODE_HANDLING = "history_handling";
	/*** 码组----贷款是审批通过的状态组*/
	public static final String GRP_CODE_PASS = "pass_stat";
	
	
	/*** 区域经理角色id */
	public static final String AREA_MANAGER_ROLEID = "xxxxx";
	/*** 销售角色code */
	public static final String SALE_ROLECODE = "xxxx";
	/**申请状态---待审批*/
	public static final String ADMIN_APROV_RESULT_WAIT = "25300001";
	/**申请状态---审批成功*/
	public static final String ADMIN_APROV_RESULT_SUCC = "25300003";
	/**申请状态---审批失败*/
	public static final String ADMIN_APROV_RESULT_FAIL = "25300004";
	

	/*** 码表--关系----父母 */
	public static final String RELATION_PARENT = "20100001";
	/*** 码表--关系----子女 */
	public static final String RELATION_CHILD = "20100009";
	/*** 码表--关系----配偶 */
	public static final String RELATION_SPOUSE = "20100008";

	/*** 经办信息类型----销售 */
	public static final String HANDLE_TYPE_SALER = "23300001";
	/*** 经办信息类型----审批 */
	public static final String HANDLE_TYPE_APPROV = "23300002";
	/*** 经办信息类型----催收 */
	public static final String HANDLE_TYPE_COLLECTION = "23300003";

	/*** 费用项编号----保险费 */
	public static final String FEENO_ISUAN = "2001";
	/*** 费用项编号----账户管理费 */
	public static final String FEENO_AM = "1801";
	/*** 费用项编号----灵活还款包 */
	public static final String FEENO_REPAYPACK="2601";
	/*** 费用项编号----产品费率（月利率） */
	public static final String FEENO_RATE_MONTH = "3001";
	
	/*** 职业类型----学生 */
	public static final String WORKJOB_STUDENT = "20700002";
	/*** 分组类型----商户组 */
	public static final String BRANCH_GRP_TYPE="16300002";
	
	/*** 权限所属类型----角色 */
	public static final String AUTTYPE_ROLE = "25900001";
	/*** 权限所属类型----人员 */
	public static final String AUTTYPE_STAFF = "25900002";
	/*** 权限所属类型----机构 */
	public static final String AUTTYPE_ORG = "25900004";
	/*** 权限所属类型----和员工表有关系的组 */
	public static final String AUTTYPE_GRP = "25900003";
	
	/*** 权限类型 ---- 指定 */
	public static final String AUTCODETYPE_APT = "26200004"; 
	
	
	
	
	
	
	
	
}

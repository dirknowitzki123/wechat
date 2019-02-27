package com.by.bussiness.wechat.constants;

/**
 * Created by yiqr on 2017/6/5.
 */
public class Constants {
    public static final String YON_Y = "13900001";	//是
    public static final String YON_N = "13900002";	//否
    /*** 状态 —— 正常 */
    public static final String STAT_ENABLE = "10002001";
    /*** 状态 —— 失效 */
    public static final String STAT_DISABLE = "10002002";
    /** 短信类型--注册 **/
    public static final String MSG_TYPE_REGISTER = "B010060001";
    /** 短信类型--重置密码 **/
    public static final String MSG_TYPE_RESET_PASSWORDS = "B010060002";
    
    
    /** 是否办理过分期-是，还满5期  **/
    public static final String IS_STAGED_SATISFY_FIVE = "39200003";
    /** 是否办理过分期-是，未满5期  **/
    public static final String IS_STAGED_UNSATISFY_FIVE = "39200002";
    /** 是否办理过分期-否  **/
    public static final String NO_STAGED = "39200001";
    
    /** 是否办理过分期-码值集合  **/
    public static final String[] IS_STAGED_GATHER={"39200001","39200002","39200003"};
    /** Is_No 码值集合 **/
    public static final String[] IS_NO_GATHER={"13900001","13900002"};
    /** 专属二维码 **/
    public static final String EXCLUSIVE_QR_CODE = "exclusiveQRCode";
    /** 电子签章合同**/
    public static final String ELE_SIGNATURE_CONTRACT = "eleSignatureContract";
}

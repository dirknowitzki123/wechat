package com.by.core.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.id.Hex;

import com.by.core.exception.BusinessException;

/**
 * 扩展org.apache.commons.lang.StringUtils 类
 * @author wl
 *
 */
@SuppressWarnings("rawtypes")
public class StringUtils extends org.apache.commons.lang.StringUtils{
	
	
	/**
     * 生成指定长度的验证码 小写字母加数字
     * @param verificationCodeLength
     * @return String
     */
    public static final String createVerificationCode(int verificationCodeLength){
        //    所有候选组成验证码的字符，可以用中文
        String[] verificationCodeArrary={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
        };
        String verificationCode = "";
        Random random = new Random();
        for(int i=0;i<verificationCodeLength;i++){
            verificationCode += verificationCodeArrary[random.nextInt(verificationCodeArrary.length)];
        }
        return verificationCode;
    }
	
	public static boolean empty(String str){
		
		if(isEmpty(str)||str.equalsIgnoreCase("null")){
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(empty("null"));
	}
	/**
	 * 把逗号隔开的字符串转成LIST
	 * @param orgStr String orgStr = "ID10002,ID10003,ID10004,ID10005,ID10006";
	 * @return
	 */
	public static List<String>  strSplit(String orgStr){
		try {
			if(StringUtils.isEmpty(orgStr)){
				return null;
			}
			String [] orgStrs = orgStr.split(",");
			return Arrays.asList(orgStrs);
		} catch (Exception e) {
			throw new BusinessException("参数不合法");
		}
		
	}
	/**
	 * 生成UUID
	 */
	public static String getUUID() {
		return new String(Hex.encodeHex(org.apache.commons.id.uuid.UUID.randomUUID().getRawBytes()));
	}

	/**
	 * 
	 * @param prefix 格式化出来的String的抬头
	 * @param no 在传入的这个no数字的基础上+1
	 * @return
	 */
	private static final String STR_FORMAT = "000000";
	public static String getFormtStr(String prefix,int no){
		no = no+1;
		DecimalFormat df = new DecimalFormat(STR_FORMAT);  
		return prefix+df.format(no);
	}
	/**
	 * List转逗号分隔字符串
	 */
	public static String listToString(List list) {
	    StringBuilder sb = new StringBuilder();  
	    if (list != null && list.size() > 0) {  
	        for (int i = 0; i < list.size(); i++) {  
	            if (i < list.size() - 1) {  
	                sb.append(list.get(i) + ",");  
	            } else {  
	                sb.append(list.get(i));  
	            }  
	        }  
	    }  
	    return sb.toString();  
	}
	/**
	 * 数组转逗号分隔字符串
	 */
	public static String converToString(String[] ig) {  
	    String str = "";  
	    if (ig != null && ig.length > 0) {  
	        for (int i = 0; i < ig.length; i++) {  
	            str += ig[i] + ",";  
	        }  
	    }  
	    str = str.substring(0, str.length() - 1);  
	    return str;  
	}
	
	/**
	 * 去除字符串的回车换行
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}

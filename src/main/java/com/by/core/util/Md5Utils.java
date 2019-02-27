package com.by.core.util;

import java.security.MessageDigest;

import com.by.core.exception.BusinessException;

import sun.misc.BASE64Encoder;

/**
 * 加密  解密工具
 * @author wl
 *
 */
public class Md5Utils {
	
	private static final String ALGORITHM = "MD5";
    private static final String CHARSET = "UTF-8";
	
	public static String md5(String input) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);

            byte[] md5Bytes = md5.digest(input.getBytes(CHARSET));
            return byte2hex(md5Bytes);
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
	
	private static String byte2hex(byte[] b) {
        String str = "";
        String stmp = "";
        int length = b.length;
        for (int n = 0; n < length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                str = str + "0" + stmp;
            } else {
                str = str + stmp;
            }
            if (n < length - 1) {
                str = str + "";
            }
        }
        return str.toLowerCase();
    }
	
	/**
	 * 对STR  MD5加密
	 * @param str 需要加密的str
	 * @return 加密后的数据
	 */
	public static String encode(String str){
		try {
			if(StringUtils.isNotEmpty(str)){
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				BASE64Encoder encoder = new BASE64Encoder();
				String encodeStr = encoder.encode(md5.digest(str.getBytes("utf-8"))) ;
				return encodeStr;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("数据加密失败");
		}
		return null;
	}
	
	
	
}

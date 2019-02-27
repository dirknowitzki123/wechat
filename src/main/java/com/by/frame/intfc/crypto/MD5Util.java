package com.by.frame.intfc.crypto;

import java.security.MessageDigest;

/**
 * MD5散列
 * @author hejian
 *
 */
public class MD5Util {
	
	/**
	 * md5散列
	 * @param input 需要签名的源
	 * @return 散列结果
	 */
	public static String hash(String input){
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(input.getBytes("UTF-8"));
			byte[] byteArray = messageDigest.digest();
			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
				} else {
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}
			}
			return md5StrBuff.toString();
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public static void main(String[] args) {
		String str = "this is a example哈哈";
		System.out.println(hash(str));
	}
	
}

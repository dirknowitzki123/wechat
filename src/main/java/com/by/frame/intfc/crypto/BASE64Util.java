package com.by.frame.intfc.crypto;

import java.io.UnsupportedEncodingException;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * BASE64编解码
 * @author hejian
 *
 */
public class BASE64Util {

	/**
	 * BASE64编码
	 * @param content 待编码的内容
	 * @return 编码后的内容
	 */
	public static String encode(String content){
		try {
			return Base64.encode(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * BASE64解码
	 * @param ciphertext 待解码的内容
	 * @return utf-8编码的内容
	 */
	public static String decode(String ciphertext){
		try {
			byte[] bytes = Base64.decode(ciphertext);
			return new String(bytes,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public static void main(String[] args) {
		String str = "this is a example哈哈";
		String eStr = encode(str);
		System.out.println("原文："+str);
		System.out.println("编码后："+eStr);
		String str2 = decode(eStr);
		System.out.println("解码后："+str2);
		
	}
	
}

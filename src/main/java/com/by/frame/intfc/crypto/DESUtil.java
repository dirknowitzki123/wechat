package com.by.frame.intfc.crypto;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 3DES DES加密解密
 * @author hejian
 *
 */
public class DESUtil {
	
	/**
	 * 3des 加密
	 * @param content 需要加密的明文
	 * @param desKey 3des密钥
	 * @param desIv 3des向量
	 * @return 加密后再base64的字符串
	 */
	public static String triDesEnc(String content, String desKey, String desIv) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			DESedeKeySpec dks = new DESedeKeySpec(desKey.getBytes("UTF-8"));
			Key key = keyFactory.generateSecret(dks);
			
			IvParameterSpec iv = new IvParameterSpec(desIv.getBytes("UTF-8"));
			
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, iv, new SecureRandom());
			byte[] array = cipher.doFinal(content.getBytes("UTF-8"));
			return new BASE64Encoder().encode(array);
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
  
	/**
	 * 3des解密
	 * @param ciphertext 待解密的密文
	 * @param desKey 3des密钥
	 * @param desIv 3des向量
	 * @return 解密出的明文
	 */
	public static String triDesDec(String ciphertext, String desKey, String desIv) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			DESedeKeySpec dks = new DESedeKeySpec(desKey.getBytes("UTF-8"));
			SecretKey key = keyFactory.generateSecret(dks);
			
			IvParameterSpec iv = new IvParameterSpec(desIv.getBytes("UTF-8"));
			
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, iv, new SecureRandom());
			byte[] array = cipher.doFinal(new BASE64Decoder().decodeBuffer(ciphertext));
			return new String(array, "UTF-8");
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
}

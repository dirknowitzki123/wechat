package com.by.frame.intfc.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * AES加密解密
 * @author hejian
 *
 */
public class AESUtil {

    /**
     * AES加密
     * @param content	待加密字符串
     * @param strKey	 密钥（长度128位）
     * @return 加密后转换为base64格式字符串
     */
    public static String encrypt(String content, String strKey){
        try {
        	//指定具体产生key的算法，跨操作系统产生 SecretKey；如果不指定，各种操作系统产生的安全key不一致
        	/*
        	KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            keygen.init(128, secureRandom);
            SecretKey secretKey = keygen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            */
            SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES");
            byte[] byteContent = content.getBytes("utf-8");
            
            // 创建密码器,加密算法aes，加密模式ecb，填充方式PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encode(cipher.doFinal(byteContent));
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
	
    
	 /**
     * AES解密 以String密文输入,String明文输出
     * 
     * @param ciphertext	经BASE64编码过的待解密的密文
     * @param strKey	 密钥（长度128位）
     * @return utf-8编码的明文
     * 
     *         
     */
    public static String decrypt(String ciphertext, String strKey) {
    	try {
    		//首先base64解密，而后在用key解密
    		//指定具体产生key的算法，跨操作系统产生 SecretKey；如果不指定，各种操作系统产生的安全key不一致
    		/*        	
    		KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            keygen.init(128, secureRandom);
            SecretKey secretKey = keygen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            */
            SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES");
            byte[] cipherbytes = Base64.decode(ciphertext);
            //创建密码器
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(cipherbytes);
            return new String(result, "utf-8");
    	} catch (Exception e) {
              e.printStackTrace();
              throw new RuntimeException(e);
    	}
    }
	
    
    public static void main(String[] args) {
    	String content = "abcd哈";  
    	String key = "1234567890123456";  //密钥必须16位长度
    	//加密   
    	System.out.println("加密前：" + content);  
    	String encryptResult = encrypt(content, key);
    	System.out.println("密文"+encryptResult);
    	//解密   
    	String decryptResult = decrypt(encryptResult,key);  
    	System.out.println("解密后：" + new String(decryptResult)); 
	}
	
}

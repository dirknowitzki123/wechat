package com.by.frame.intfc.crypto;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * RSA非对称加密解密
 * @author hejian
 *
 */
public class RSAUtil {
	/*** 私钥文件名 */
	private static final String PRIVKEY_NAME = "rsa_pri_key.pem";
	/*** 公钥文件名 */
	private static final String PUBKEY_NAME = "rsa_pub_key.pem";
	
	
	/**
	 * 生成经BASE64编码后的RSA公钥和私钥到文件系统
     * @param path 文件路径
     * 
     */
    @SuppressWarnings("resource")
	public static void createKeyPairs(String path) {
    	try {
    		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    		generator.initialize(1024, new SecureRandom());
    		KeyPair pair = generator.generateKeyPair();
    		PublicKey pubKey = pair.getPublic();
    		PrivateKey privKey = pair.getPrivate();
    		byte[] pubk = pubKey.getEncoded();
    		byte[] privk = privKey.getEncoded();
    		// base64编码，屏蔽特殊字符
    		String strpk = new String(Base64.encode(pubk));
    		String strprivk = new String(Base64.encode(privk));
    		
    		// 输出私钥文件
    		File priKeyfile = new File(path + "/" + PRIVKEY_NAME);
    		FileOutputStream out = new FileOutputStream(priKeyfile);
    		out.write(strprivk.getBytes());
    		// 输出公钥文件
    		File pubKeyfile = new File(path +"/" +PUBKEY_NAME);
    		FileOutputStream outPub = new FileOutputStream(pubKeyfile);
    		outPub.write(strpk.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
	
    
    /**
     * RSA公钥加密
     * @param content	待加密的明文
     * @param pubKey	RSA公钥
     * @return	经BASE64编码后的密文
     */
    public static String pubKeyEnc(String content,String pubKey){
    	try {
    		KeyFactory keyf = KeyFactory.getInstance("RSA");
            //获取公钥
            InputStream is = new ByteArrayInputStream(pubKey.getBytes("utf-8"));
            byte[] pubbytes = new byte[new Long(pubKey.length()).intValue()];
            is.read(pubbytes);
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decode(new String(pubbytes)));
            PublicKey pkey = keyf.generatePublic(pubX509);
            
            //公钥加密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pkey);
            byte[] cipherText = cipher.doFinal(content.getBytes());
           
            // 将加密结果转换为Base64编码结果；便于internet传送
            return Base64.encode(cipherText);
    	} catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
    	}
    }
    
    
    /**
     * RSA公钥解密
     * @param ciphertext 经BASE64编码过的待解密的密文
     * @param pubKey RSA公钥
     * @return utf-8编码的明文
     */
    public static String pubKeyDec(String ciphertext ,String pubKey){
    	try {
    		KeyFactory keyf = KeyFactory.getInstance("RSA");
			 
			//获取公钥
			InputStream is = new ByteArrayInputStream(pubKey.getBytes("utf-8"));
			byte[] pubbytes = new byte[new Long(pubKey.length()).intValue()];
			is.read(pubbytes);
			X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decode(new String(pubbytes)));
			PublicKey pkey = keyf.generatePublic(pubX509);
			
			//公钥解密
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, pkey);
			byte[] text = cipher.doFinal(Base64.decode(ciphertext));
			
			return new String(text,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    
    /**
     * RSA私钥加密
     * @param content 待加密的明文
     * @param privKey RSA私钥
     * @return	经BASE64编码后的密文
     */
    public static String privKeyEnc(String content,String privKey){
    	try {
    		KeyFactory keyf = KeyFactory.getInstance("RSA");
    		
            //获取私钥
            InputStream key = new ByteArrayInputStream(privKey.getBytes("utf-8"));
            byte[] pribytes = new byte[new Long(privKey.length()).intValue()];
            key.read(pribytes);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(new String(pribytes)));
            PrivateKey prikey = keyf.generatePrivate(priPKCS8);
            
            //私钥加密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, prikey);
            byte[] cipherText = cipher.doFinal(content.getBytes());
            
            //将加密结果转换为Base64编码结果；便于internet传送
            return Base64.encode(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		}
    }
    
    
    /**
     * RSA私钥解密
     * @param ciphertext	经BASE84编码过的待解密密文
     * @param privKey	RSA私钥
     * @return	utf-8编码的明文
     */
    public static String privKeyDec(String ciphertext ,String privKey){
    	try {
    		KeyFactory keyf = KeyFactory.getInstance("RSA");
    		
            //获取私钥
            InputStream key = new ByteArrayInputStream(privKey.getBytes("utf-8"));
            byte[] pribytes = new byte[new Long(privKey.length()).intValue()];
            key.read(pribytes);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(new String(pribytes)));
            PrivateKey prikey = keyf.generatePrivate(priPKCS8);
            
            //私钥解密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, prikey);
            byte[] content = cipher.doFinal(Base64.decode(ciphertext));
            
            return new String(content,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		}
    }
    
    /**
     * RSA私钥数字签名
     * @param content 待签内容
     * @param privKey RSA私钥
     * @return 经BASE64编码后的签名串
     */
    public static String sign(String content,String privKey){
    	try {
			KeyFactory keyf=KeyFactory.getInstance("RSA");
			
			 //获取私钥
			InputStream key = new ByteArrayInputStream(privKey.getBytes("utf-8"));
			byte[] pribytes = new byte[new Long(privKey.length()).intValue()];
			key.read(pribytes);
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(new String(pribytes)));
			PrivateKey priKey=keyf.generatePrivate(priPKCS8);
			
			//实例化Signature；签名算法：MD5withRSA
			Signature signature = Signature.getInstance("MD5withRSA");
			//初始化Signature
			signature.initSign(priKey);
			//更新
			signature.update(content.getBytes());
			return Base64.encode(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    /**
     * RSA公钥校验数字签名
     * @param content 待校验的内容
     * @param pubKey RSA公钥
     * @param signedStr 签名字符串
     * @return	true:校验成功；false:校验失败
     */
    public static boolean verify(String content,String pubKey,String signedStr){
    	try {
    		//实例化密钥工厂  
			KeyFactory keyf=KeyFactory.getInstance("RSA");
			
			//获取公钥
			InputStream is = new ByteArrayInputStream(pubKey.getBytes("utf-8"));
			byte[] pubbytes = new byte[new Long(pubKey.length()).intValue()];
			is.read(pubbytes);
			X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decode(new String(pubbytes)));
			PublicKey pkey = keyf.generatePublic(pubX509);
   
			//实例化Signature；签名算法：MD5withRSA
			Signature signature = Signature.getInstance("MD5withRSA");  
			signature.initVerify(pkey);
			signature.update(content.getBytes());
			//验证
			return signature.verify(Base64.decode(signedStr));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    
    public static void main(String[] args) {
		//createKeyPairs("d:");
	/*
    	String privKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKvoaIn9OrwzL+ynkABf80cx5aXgZFB5g9sWlgm27wgL31X353HfIkf4zAvXyIC94ewK7fC9uKsqqxFOBfqMD1g2M+YTVsrThw3rqjds8m4xLv4T76xEXAJygnWjs66HZ/LO+rk8kGKuc8qEKaH643pdsJBM7xYpAFrvU8sO+s2vAgMBAAECgYAZJw8sqX2PjdnEQZwjQebBkv5r79IdqDnvs3VOhJKi1cHevUAC/EfyyedpEDCJDb9hnxPDdq7vdHXFOWpMh3OzEcg/Vs47C6BJg4LxZmJFsf5e87DcqgmrXWyZB0h3cDFJFP55aXJ6Cf7sCgqZ/g9oWLrL/qA6mb2WS6bqgOkYQQJBAOa/LRyhsEumMSKqjcHiDlPLRJEjpXk5SG7UHEZZv7kizvKeSLHxd0tQ9IPg6UW8vzUqZDGkdx4rh1k/BA/8c2ECQQC+uL5vas8kPD5Qy6HWUoBf/aLDYfLoHrYWXsCiAODTUr+UE6btB6PMnjP592aYjD/5NnYuIj+74e8T+tLNBusPAkEAwtroTohf5tI2Jg8e3H54GNzLT0Fp6D+uYiDuq534oG+7kPiHbPT7B3gatA9HDmQAl/XvXFiFWs3pYz0qwhEuoQJBALRNqVNh0YR7I5p0eIJKqqyU9zvpLJ/+8LvDRi+hzupZb15JGeU59MOryI2HM0oCRTSQJTfGDyTLGp9z37gO00cCQQCx/IooT+6+VDIrxnHdbJwcR3Xvcb1UhXjRIrF7xmEHdT7xCOiJzyhnAiGDbK5kgBillSY4VG6UaKIIZ3hCZc82";
    	String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCr6GiJ/Tq8My/sp5AAX/NHMeWl4GRQeYPbFpYJtu8IC99V9+dx3yJH+MwL18iAveHsCu3wvbirKqsRTgX6jA9YNjPmE1bK04cN66o3bPJuMS7+E++sRFwCcoJ1o7Ouh2fyzvq5PJBirnPKhCmh+uN6XbCQTO8WKQBa71PLDvrNrwIDAQAB";
    	String name = "this is a example哈哈";
    	System.out.println("------------私钥加密公钥解密------------------");
    	//私钥加密
    	String smiwen = privKeyEnc(name, privKey);
    	System.out.println("私钥加密结果："+smiwen);
    	//公钥解密
    	String content = pubKeyDec(smiwen, pubKey);
    	System.out.println("公钥还原内容："+content);
    	
    	System.out.println("-----------公钥加密私钥解密---------------");
    	//公钥加密
    	String pubcstr = pubKeyEnc(name, pubKey);
    	System.out.println("公钥加密结果："+pubcstr);
    	//私钥解密
    	String c2 = privKeyDec(pubcstr, privKey);
    	System.out.println("私钥还原内容："+c2);
    	
    	System.out.println("-----------私钥签名公钥验签--------------------");
    	String singedStr = sign(name, privKey);
    	System.out.println("私钥签名结果："+singedStr);
    	System.out.println("验签结果："+verify(name, pubKey, singedStr));
    	*/
    	createKeyPairs("d:/");
	}
    
	
}

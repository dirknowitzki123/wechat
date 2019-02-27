package com.by.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import jdk.internal.org.objectweb.asm.commons.Remapper;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.by.core.exception.FrameworkException;
import com.by.core.startup.AppConfig;
import com.by.frame.intfc.crypto.AESUtil;
import com.by.frame.intfc.crypto.RSAUtil;
import com.by.frame.intfc.model.TransHead;
import com.by.frame.intfc.model.TransResult;


/**
 * http帮助类
 * @author hejian
 *
 */
public class HttpHelper {
	private static Logger log =  LoggerFactory.getLogger(HttpHelper.class);
	
	/***设置连接超时时间 30s*/
	public final static  int REQ_TIMEOUT = 30*1000*2*3;
	/*** 设置响应超时时间 30s*/
	public final static  int RESP_TIMEOUT = 30*1000*2*3;
	
	/*** http响应状态 */
	public final static String STATUS = "status";
	/*** http响应的所有头 */
	public final static String RESP_HEADERS = "respHeaders";
	/*** http响应的内容 */
	public final static String RESP_STR = "respStr";
	/*** http响应的二进制内容 */
	public final static String RESP_BINARY = "respBinary";
	/*** http响应的inputstream */
	public final static String RESP_INSTREAM = "respInStream";
	
	
	/**
	 * 以指定的编码post xml数据
	 * @param url 请求地址
	 * @param xmlStr 
	 * @param encoding 编码
	 * @return 返回响应结果 
	 * @throws AppException 
	 */
	public static Map<String,Object> postXml(String postUrl,String xmlStr,String encoding){
		Map<String,Object> result = new HashMap<String,Object>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(postUrl);
		RequestConfig config = RequestConfig.custom()  
		        .setConnectTimeout(REQ_TIMEOUT)
		        .setConnectionRequestTimeout(3000)//设置从connect Manager获取Connection 超时时间 3s
		        .setSocketTimeout(RESP_TIMEOUT).build();
		post.setConfig(config);  
		try {
			StringEntity reqEntity= new StringEntity(xmlStr,Charset.forName(encoding));
			post.setEntity(reqEntity);
			post.addHeader("Content-Type", "text/xml"); 
			log.debug("====>>>>executing request：{} ",post.getURI());
			CloseableHttpResponse resp = null;
			resp = httpclient.execute(post);
			StatusLine status = resp.getStatusLine();
			log.debug("respose status：{}",status);
			int stat = status.getStatusCode();
			String respXml = "";
			HttpEntity entity = resp.getEntity();
			if(entity != null){
				BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
				respXml = EntityUtils.toString(bufferedEntity, Charset.forName(encoding));
			}
			result.put(STATUS, stat+"");
			result.put(RESP_STR, respXml);
		} catch (Exception e) {
			log.error("===>>>access: "+postUrl+" error : "+e.getMessage());
			e.printStackTrace();
			throw new FrameworkException(e);
		}finally{
			try {
				if(httpclient!=null)
					httpclient.close();
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
				throw new FrameworkException(e);
			}
		}
		return result;
	}
	
	
	/**
	 * post提交表单数据
	 * @param postUrl 提交的地址
	 * @param paramMap 表单参数map
	 * @return 返回响应的内容
	 */
	public static Map<String,Object> postForm(String postUrl ,Map<String,String> paramMap){
		return postForm(postUrl,paramMap,null);
	}
	
	
	/**
	 * post表单提交
	 * @param postUrl
	 * @param paramMap
	 * @param reqHeaders
	 * @return
	 */
	public static Map<String,Object> postForm(String postUrl , Map<String,String> paramMap,Map<String,String> reqHeaders){
		Map<String,Object> rsMap = new HashMap<String,Object>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post =new HttpPost(postUrl);
		RequestConfig config = RequestConfig.custom()  
		        .setConnectTimeout(REQ_TIMEOUT)
		        .setConnectionRequestTimeout(3000)//设置从connect Manager获取Connection 超时时间 3s
		        .setSocketTimeout(RESP_TIMEOUT).build();
		post.setConfig(config);  
		CloseableHttpResponse resp = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//参数组装
		for(Map.Entry<String, String> para : paramMap.entrySet()){
			params.add(new BasicNameValuePair(para.getKey(), para.getValue()));
		}
		//组装header，同名的header会被覆盖
		if(reqHeaders != null && !reqHeaders.isEmpty()){
			for(Map.Entry<String, String> header : reqHeaders.entrySet()){
				post.setHeader(header.getKey(), header.getValue());
			}
		}
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
			post.setEntity(entity);
			log.debug("executing request：{} ",post.getURI());
			resp = httpClient.execute(post);
			StatusLine status = resp.getStatusLine();
			log.debug("respose status：{}",status);
			int stat = status.getStatusCode();
			String respStr = "";
			InputStream inStream = null;
			HttpEntity hEntity = resp.getEntity();
			if(hEntity != null){
				BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(hEntity);
				respStr = EntityUtils.toString(bufferedEntity, Charset.forName("UTF-8"));
				//返回InputStream
				byte[] respByteArray = EntityUtils.toByteArray(bufferedEntity);
				inStream = new ByteArrayInputStream(respByteArray);
			}
			//获取响应的头信息
			Header[] headers = resp.getAllHeaders();
			if(headers!=null && headers.length > 0){
				Map<String,String> respHeaders = new HashMap<String, String>();
				for(Header hd : headers){
					respHeaders.put(hd.getName(), hd.getValue());
				}
				rsMap.put(RESP_HEADERS, respHeaders);
			}
			rsMap.put(STATUS, stat+"");
			rsMap.put(RESP_STR, respStr);
			rsMap.put(RESP_INSTREAM, inStream);
		} catch (Exception e){
			log.error("post请求失败：{}",e.getMessage());
			e.printStackTrace();
			throw new FrameworkException(e);
		}finally{
			try {
				if(resp!=null)
					resp.close();
				if(httpClient!=null)
					httpClient.close();
			} catch (IOException e) {
				log.error("资源关闭失败：{}",e.getMessage());
				e.printStackTrace();
				throw new FrameworkException(e);
			}
		}
		return rsMap;
	} 
	
	
	/**
	 * 发送get请求
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static Map<String,Object> requestByGet(String reqUrl,Map<String,String> paramMap,Map<String,String> reqHeaders){

		Map<String,Object> rsMap = new HashMap<String,Object>();
		
		//参数组装
		CloseableHttpClient httpclient = HttpClients.createDefault();
		if(paramMap!=null && !paramMap.isEmpty()){
			String param = "";
			for(Map.Entry<String, String> para : paramMap.entrySet()){
				String value = para.getValue();
				try {
					value = URLEncoder.encode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				param+="&"+para.getKey()+"="+value;
			}
			if(reqUrl.trim().indexOf("?")==-1 && param.trim().length()!=0 ){
				reqUrl+="?"+param.trim().substring(1);
			}else{
				reqUrl+=param.trim();
			}
		}
		
		 //配置请求的超时设置  
        RequestConfig config = RequestConfig.custom()
		        .setConnectTimeout(REQ_TIMEOUT)
		        .setConnectionRequestTimeout(3000)//设置从connect Manager获取Connection 超时时间 3s
		        .setSocketTimeout(RESP_TIMEOUT).build();
        
        CloseableHttpResponse resp = null;
        try{
            HttpGet get = new HttpGet(reqUrl);
    		//请求头组装，同名的header会被覆盖
    		if(reqHeaders != null && !reqHeaders.isEmpty()){
    			for(Map.Entry<String, String> header : reqHeaders.entrySet()){
    				get.setHeader(header.getKey(), header.getValue());
    			}
    		}
            get.setConfig(config);  
        	log.debug("====>>>>executing request：{} ",get.getURI());
        	resp = httpclient.execute(get);
        	StatusLine status = resp.getStatusLine();
			log.debug("respose status：{}",status);
			String respStr = "";
			int stat = status.getStatusCode();
			HttpEntity hEntity = resp.getEntity();
			if(hEntity!=null){
				BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(hEntity);
				respStr = EntityUtils.toString(bufferedEntity, "UTF-8");
			}
			//获取响应的头信息
			Header[] headers = resp.getAllHeaders();
			if(headers!=null && headers.length > 0){
				Map<String,String> respHeaders = new HashMap<String, String>();
				for(Header hd : headers){
					respHeaders.put(hd.getName(), hd.getValue());
				}
				rsMap.put(RESP_HEADERS, respHeaders);
			}
			rsMap.put(STATUS, stat+"");
			rsMap.put(RESP_STR, respStr);
        }catch(Exception e){
        	log.error("请求失败：{}",e.getMessage());
			e.printStackTrace();
			throw new FrameworkException(e);
        }finally{
        	try {
				if(resp!=null)
					resp.close();
				if(httpclient!=null)
					httpclient.close();
			} catch (IOException e) {
				log.error("资源关闭失败：{}",e.getMessage());
				e.printStackTrace();
				throw new FrameworkException(e);
			}
        }
        
        return rsMap;
	}
	
	/**
	 * post输入流操作
	 * @param postUrl
	 * @param ins
	 * @return
	 */
	public static Map<String,Object> postStream(String postUrl, InputStream ins,Map<String,String> reqHeaders){
		Map<String,Object> rsMap = new HashMap<String,Object>();
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post =new HttpPost(postUrl);
		RequestConfig config = RequestConfig.custom()  
		        .setConnectTimeout(REQ_TIMEOUT)
		        .setConnectionRequestTimeout(3000)//设置从connect Manager获取Connection 超时时间 3s
		        .setSocketTimeout(RESP_TIMEOUT).build();
		post.setConfig(config);  
		CloseableHttpResponse resp = null;
		//组装header，同名的header会被覆盖
		if(reqHeaders != null && !reqHeaders.isEmpty()){
			for(Map.Entry<String, String> header : reqHeaders.entrySet()){
				post.setHeader(header.getKey(), header.getValue());
			}
		}
		try {
			InputStreamEntity entity = new InputStreamEntity(ins);
			post.setEntity(entity);
			log.debug("executing request：{} ",post.getURI());
			resp = httpClient.execute(post);
			StatusLine status = resp.getStatusLine();
			log.debug("respose status：{}",status);
			int stat = status.getStatusCode();
			String respStr = "";
			HttpEntity hEntity = resp.getEntity();
			if(hEntity != null){
				BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(hEntity);
				respStr = EntityUtils.toString(bufferedEntity, Charset.forName("UTF-8"));
			}
			//获取响应的头信息
			Header[] headers = resp.getAllHeaders();
			if(headers!=null && headers.length > 0){
				Map<String,String> respHeaders = new HashMap<String, String>();
				for(Header hd : headers){
					respHeaders.put(hd.getName(), hd.getValue());
				}
				rsMap.put(RESP_HEADERS, respHeaders);
			}
			rsMap.put(STATUS, stat+"");
			rsMap.put(RESP_STR, respStr);
		} catch (Exception e){
			log.error("post请求失败：{}",e.getMessage());
			e.printStackTrace();
			throw new FrameworkException(e);
		}finally{
			try {
				if(resp!=null)
					resp.close();
				if(httpClient!=null)
					httpClient.close();
			} catch (IOException e) {
				log.error("资源关闭失败：{}",e.getMessage());
				e.printStackTrace();
				throw new FrameworkException(e);
			}
		}
		return rsMap;
	}

	/**
	 * post方式获取请求响应流
	 * @param url
	 * @param pMap 参数
	 * @return
	 */
	public static Map<String, Object> getStream(String url, Map<String, String> pMap) {
		Map<String,Object> rsMap = new HashMap<String,Object>();
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post =new HttpPost(url);
		RequestConfig config = RequestConfig.custom()  
		        .setConnectTimeout(REQ_TIMEOUT)
		        .setConnectionRequestTimeout(3000)//设置从connect Manager获取Connection 超时时间 3s
		        .setSocketTimeout(RESP_TIMEOUT).build();
		post.setConfig(config);  
		CloseableHttpResponse resp = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//参数组装
		for(Map.Entry<String, String> para : pMap.entrySet()){
			params.add(new BasicNameValuePair(para.getKey(), para.getValue()));
		}
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
			post.setEntity(entity);
			log.debug("executing request：{} ",post.getURI());
			resp = httpClient.execute(post);
			StatusLine status = resp.getStatusLine();
			log.debug("respose status：{}",status);
			int stat = status.getStatusCode();
			rsMap.put(STATUS, stat+"");
			HttpEntity hEntity = resp.getEntity();
			if(hEntity != null){
				BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(hEntity);
				InputStream rsIns = new ByteArrayInputStream(EntityUtils.toByteArray(bufferedEntity));
				rsMap.put(RESP_BINARY, rsIns);
			}
			//获取响应的头信息
			Header[] headers = resp.getAllHeaders();
			if(headers!=null && headers.length > 0){
				Map<String,String> respHeaders = new HashMap<String, String>();
				for(Header hd : headers){
					respHeaders.put(hd.getName(), hd.getValue());
				}
				rsMap.put(RESP_HEADERS, respHeaders);
			}
		} catch (Exception e){
			log.error("post请求失败：{}",e.getMessage());
			e.printStackTrace();
			throw new FrameworkException(e);
		}finally{
			try {
				if(resp!=null)
					resp.close();
				if(httpClient!=null)
					httpClient.close();
			} catch (IOException e) {
				log.error("资源关闭失败：{}",e.getMessage());
				e.printStackTrace();
				throw new FrameworkException(e);
			}
		}
		return rsMap;
	}
	
	/**
	 * 发送post请求,aes加密
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String aesPostJson(String postUrl,String jsonContent){
		String aesKey = AppConfig.get("trans.head.aes.key");
		String rsaPubKey = AppConfig.get("trans.head.rsa.pub.key");
		
		TransHead head = new TransHead();
		head.setClientFlag(AppConfig.get("trans.head.client.flag"));
		head.setSecretKey(RSAUtil.pubKeyEnc(aesKey, rsaPubKey));
		head.setService(AppConfig.get("trans.head.service"));
		head.setVersion(AppConfig.get("trans.head.version"));
		head.setReqNo(System.currentTimeMillis()+"");
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("transHead", JSON.toJSONString(head));
		param.put("transBody",AESUtil.encrypt(jsonContent, aesKey) );
		
		Map<String, Object> rsMap = HttpHelper.postForm(postUrl, param);
		String rsStr = (String) rsMap.get(RESP_STR);
		log.info("===>>>"+rsStr);
		TransResult trs = JSON.parseObject(rsStr, TransResult.class);
		String rsAesKey = RSAUtil.pubKeyDec(trs.getSecretKey(), rsaPubKey);
		log.info("aesKey:"+rsAesKey);
		String jsonStr = AESUtil.decrypt((String)trs.getResult(), rsAesKey);
		log.info("返回结果："+jsonStr);
		return jsonStr;
	}
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    public static CloseableHttpClient createSSLClientDefault(){
    	try {
             SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                 //信任所有
                 @SuppressWarnings("unused")
				public boolean isTrusted(X509Certificate[] chain,
                                 String authType) throws CertificateException {
                     return true;
                 }

				@Override
				public boolean isTrusted(
						java.security.cert.X509Certificate[] arg0,
						String arg1)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub
					return false;
				}
             }).build();
             SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
             return HttpClients.custom().setSSLSocketFactory(sslsf).build();
         } catch (KeyManagementException e) {
             e.printStackTrace();
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
         } catch (KeyStoreException e) {
             e.printStackTrace();
         }
         return  HttpClients.createDefault();
    }
    
    /**
	 * postJson返回json或普通字符串
	 * @param postUrl 请求的地址
	 * @param jsonStr 请求的json格式的报文
	 * @return String 返回接口响应的json字符串报文，失败返回null
	 */
	public static String postJson(String postUrl,String jsonStr){
		String rs = null;
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = HttpHelper.createSSLClientDefault();
		HttpPost post = new HttpPost(postUrl);
		try {
			StringEntity reqEntity= new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
			post.setEntity(reqEntity);
			System.out.println("begin request"+DateUtils.getCurrentPrettyDate()+"===>>>executing request："+post.getURI());
			System.out.println("===>>>request param : " + jsonStr);
			CloseableHttpResponse resp = null;
			try {
				resp = httpclient.execute(post);
				StatusLine status = resp.getStatusLine();
				System.out.println("<<<=== respose status : "+status);
				if(status.getStatusCode()==200){
					HttpEntity entity = resp.getEntity();
					if(entity!=null){
						BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
						rs = EntityUtils.toString(bufferedEntity, "UTF-8");
						System.out.println("respones "+DateUtils.getCurrentPrettyDate()+"<<<===response content:"+rs);
					}
				}
			} catch (ClientProtocolException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				if(resp!=null){
					resp.close();
				}
			}
		} catch (UnsupportedCharsetException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				if(httpclient!=null)
					httpclient.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return rs;
	}
    
	
}

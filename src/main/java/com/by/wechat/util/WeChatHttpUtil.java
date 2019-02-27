package com.by.wechat.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/**
 * Created by yiqr on 2017/6/12.
 */
public class WeChatHttpUtil {
    private static Log log= LogFactory.getLog(WeChatHttpUtil.class);
    /**
     * http get
     * @param url
     * @return
     */
    public static String get(String url){
        String rs = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet post = new HttpGet(url);
        try {
            CloseableHttpResponse resp = null;
            try {
                resp = httpclient.execute(post);
                StatusLine status = resp.getStatusLine();
                if(status.getStatusCode()==200){
                    HttpEntity entity = resp.getEntity();
                    if(entity!=null){
                        BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
                        rs = EntityUtils.toString(bufferedEntity, "UTF-8");
                    }
                }
            } catch (Exception e) {
                log.error(e);
            } finally {
                if(resp!=null){
                    resp.close();
                }
            }
        } catch (Exception e) {
            log.error(e);
        }finally{
            try {
                if(httpclient!=null)
                    httpclient.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
        return rs;
    }



}

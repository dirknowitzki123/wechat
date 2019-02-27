package com.by.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 微信消息（接收）
 * Created by yiqr on 2017/6/13.
 */
public class ReqMessage {
	/** 接收文本消息 **/
    public static String REQ_MESSAGE_TYPE_TEXT = "text";
    /** 接收图片消息 **/
    public static String REQ_MESSAGE_TYPE_IMAGE = "image";
    /** 接收音频消息 **/
    public static String REQ_MESSAGE_TYPE_VOICE = "voice";
    /** 接收视频消息  **/
    public static String REQ_MESSAGE_TYPE_VIDEO = "video";
    /** 接收小视频消息 **/
    public static String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    /** 接收地理位置消息 **/
    public static String REQ_MESSAGE_TYPE_LOCATION = "location";
    /** 接收链接消息 **/
    public static String REQ_MESSAGE_TYPE_LINK = "link";
    /** 接收事件推送 **/
    public static String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 解析微信发来的请求（XML）
     * @return
     * @throws IOException DocumentException 
     */
    @SuppressWarnings("unchecked")
	public static Map<String,String> parseReqMessage(HttpServletRequest request) throws IOException, DocumentException {
    	// 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
    	InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        // 读取输入流
        Document document = reader.read(inputStream);
        //获取根节点
        Element root = document.getRootElement();
        //遍历所有节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList){
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

}

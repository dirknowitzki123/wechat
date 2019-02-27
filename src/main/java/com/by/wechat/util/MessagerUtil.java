package com.by.wechat.util;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.by.wechat.model.BaseMessage;
import com.by.wechat.model.CustomerServiceMessage;
import com.by.wechat.model.MusicMessage;
import com.by.wechat.model.NewsMessage;
import com.by.wechat.model.TextMessage;

public class MessagerUtil {

	public static String RESP_MESSAGE_TYPE_TEXT = "text";// 回复文本消息

    public static String REQ_MESSAGE_TYPE_TEXT = "text";// 接收文本消息

    public static String REQ_MESSAGE_TYPE_IMAGE = "image";// 接收图片消息

    public static String REQ_MESSAGE_TYPE_LOCATION = "location";// 接收地理位置消息

    public static String REQ_MESSAGE_TYPE_LINK = "link";// 接收链接消息

    public static String REQ_MESSAGE_TYPE_VOICE = "voice";// 接收音频消息

    public static String REQ_MESSAGE_TYPE_EVENT = "event";// 接收事件推送

    public static String EVENT_TYPE_SUBSCRIBE = "subscribe"; // 订阅

    public static String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";// 取消订阅

    public static String EVENT_TYPE_CLICK = "CLICK";// 自定义菜单点击事件

    /**
     * 解析微信发来的请求（XML）
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());
        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }
    
    /**
     * 主类消息对象转换成xml
     * 
     * @param baseMessage
     *            主类消息对象
     * @return xml
     */
    public static String baseMessageToXml(BaseMessage baseMessage) {
    	return convertToXml(baseMessage, "UTF-8");
    }

    /**
     * 文本消息对象转换成xml
     * 
     * @param textMessage
     *            文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
    	return convertToXml(textMessage, "UTF-8");
    }

    public static String customerServiceToXml(CustomerServiceMessage customerServiceMessage){
        return convertToXml(customerServiceMessage,"UTF-8");
    }



    /**
     * 音乐消息对象转换成xml
     * 
     * @param musicMessage
     *            音乐消息对象
     * @return xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
    	return convertToXml(musicMessage, "UTF-8");
    }

    /**
     * 图文消息对象转换成xml
     * 
     * @param newsMessage
     *            图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
    	return convertToXml(newsMessage, "UTF-8");
    }
    
	private static String convertToXml(Object obj, String encoding) {  
        String result = null;  
        try {  
        	JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller marshaller = context.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            
            StringWriter writer = new StringWriter();  
            marshaller.marshal(obj, writer);  
            result = writer.toString();   
            result=result.replace("&lt;", "<");
            result=result.replace("&gt;", ">");
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;
    }
}

package com.by.wechat.service.impl;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.by.core.startup.AppConfig;
import com.by.core.util.HttpHelper;
import com.by.core.util.StringUtils;
import com.by.wechat.config.WeChatConfig;
import com.by.wechat.model.CustomerServiceMessage;
import com.by.wechat.service.IWeChatConfigService;
import com.by.wechat.service.IWeChatMessageService;
import com.by.wechat.util.MessagerUtil;
import com.by.wechat.util.ReqMessage;
import com.by.wechat.util.RespMessage;
import com.by.wechat.util.SHA1;

/**
 * 微信消息接入
 * Created by yiqr on 2017/6/13.
 */
@Service
public class WeChatMessageService implements IWeChatMessageService{

    @Autowired
    private IWeChatConfigService weChatConfigService;

    /** 客服消息--向用户发送文本消息地址 **/
    public static String PSOT_MESSAGE_TEXT = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    public static String RESP_MESSAGE_TYPE_TEXT = "text";// 回复文本消息

    public static String EVENT_TYPE_SUBSCRIBE = "subscribe"; // 订阅
    public static String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";// 取消订阅
    public static String EVENT_TYPE_CLICK = "CLICK";// 自定义菜单点击事件


    public boolean access(HttpServletRequest request) {
//		开发者通过检验signature对请求进行校验（下面有校验方式）。
        // 		若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，
        // 		否则接入失败。加密/校验流程如下：
        //		1）将token、timestamp、nonce三个参数进行字典序排序
        //		2）将三个参数字符串拼接成一个字符串进行sha1加密
        //		3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        //		参数        描述
        //		signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        //		timestamp	时间戳
        //		nonce		随机数
        //		echostr		随机字符串
        // 微信加密签名
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        //String echostr = request.getParameter("echostr");
        String reSignature = "";

        //1）将token、timestamp、nonce三个参数进行字典序排序
        String[] str = { AppConfig.get("wx_token")};
        
        if (StringUtils.isNotEmpty(timestamp)) {
        	str = new String[]{ AppConfig.get("wx_token"), timestamp };
		}
        if (StringUtils.isNotEmpty(nonce)) {
        	str = new String[]{ AppConfig.get("wx_token"), nonce };
		}
        if (StringUtils.isNotEmpty(nonce) && StringUtils.isNotEmpty(timestamp)) {
        	str = new String[]{ AppConfig.get("wx_token"), timestamp, nonce };
		}
        Arrays.sort(str);
        String bigStr = "";
        //2）将三个参数字符串拼接成一个字符串进行sha1加密
        for (int i = 0; i < str.length; i++) {
        	bigStr+=str[i];
		}
        if (StringUtils.isNotEmpty(bigStr)) {
        	reSignature = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
		}
        //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (StringUtils.isNotEmpty(reSignature) && signature.equals(reSignature)) {
            return true;
        }else {
            return false;
        }
    }

    public void process(HttpServletRequest request,HttpServletResponse response) {
        // 响应消息
        PrintWriter out = null;
    	try {
    	    out = response.getWriter();
			Map<String, String> parseReqMessage = MessagerUtil.parseXml(request);
			// 发送方帐号（open_id）
            String fromUserName = parseReqMessage.get("FromUserName");
            // 公众帐号
            String toUserName = parseReqMessage.get("ToUserName");
            // 消息类型
            String msgType = parseReqMessage.get("MsgType");
            //自定义内容
            String eventKey = parseReqMessage.get("EventKey");
            
            if (msgType.equals(ReqMessage.REQ_MESSAGE_TYPE_TEXT)) {
//				TextMessage textMessage = new TextMessage();
//	            textMessage.setToUserName(fromUserName);
//	            textMessage.setFromUserName(toUserName);
//	            textMessage.setCreateTime(new Date().getTime());
//	            String Content = parseReqMessage.get("Content");
//	            textMessage.setContent("学你说话:"+Content);
//                out.print(MessagerUtil.textMessageToXml(textMessage));
//            	System.out.println("普通文本消息");
            }
            else if (msgType.equals(ReqMessage.REQ_MESSAGE_TYPE_IMAGE)) {
				System.out.println("====================图片消息");
			}
            else if (msgType.equals(ReqMessage.REQ_MESSAGE_TYPE_LINK)) {
            	System.out.println("====================链接消息");
			}
            else if(msgType.equals(ReqMessage.REQ_MESSAGE_TYPE_VOICE)){
            	System.out.println("====================音频消息");
            }
            else if(msgType.equals(ReqMessage.REQ_MESSAGE_TYPE_VIDEO)){
            	System.out.println("====================视频消息");
            }
            else if(msgType.equals(ReqMessage.REQ_MESSAGE_TYPE_LOCATION)){
            	System.out.println("====================地理消息");
            }
            else if(msgType.equals(ReqMessage.REQ_MESSAGE_TYPE_SHORTVIDEO)){
            	System.out.println("====================小视频消息");
            }
            //连接客服
            else if(msgType.equals(ReqMessage.REQ_MESSAGE_TYPE_EVENT) && AppConfig.get("wx_customer_service").equals(eventKey)){
                sendTextMessage(fromUserName,"连接客服中，请耐心等待。。。");
                CustomerServiceMessage customerServiceMessage = new CustomerServiceMessage();
                customerServiceMessage.setToUserName(fromUserName);
                customerServiceMessage.setFromUserName(toUserName);
                customerServiceMessage.setCreateTime(new Date().getTime());
                out.print(MessagerUtil.customerServiceToXml(customerServiceMessage));
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
    	    out.close();
        }
    }

    @Override
    public void sendTextMessage(String openId,String content) {
        if(StringUtils.isEmpty(WeChatConfig.getAccessToken())){
            weChatConfigService.getToken();
        }
        String url  = PSOT_MESSAGE_TEXT
                .replace("ACCESS_TOKEN", WeChatConfig.getAccessToken());
        Map<String,Object> map = new HashMap<>();
        map.put("touser",openId);
        map.put("msgtype", RespMessage.RESP_MESSAGE_TYPE_TEXT);
        Map<String,String> textMap = new HashMap<>();
        textMap.put("content",content);
        map.put("text",textMap);
        System.out.println("=======================>"+JSON.toJSONString(map));
        String s = HttpHelper.postJson(url, JSON.toJSONString(map));
        System.out.println("主动发送消息。。。》》"+JSON.toJSONString(s));
    }

}

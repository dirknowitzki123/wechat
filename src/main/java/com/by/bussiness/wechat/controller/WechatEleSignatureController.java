package com.by.bussiness.wechat.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.bussiness.wechat.service.IWeChatEleSignatureService;
import com.by.core.util.Page;

/**
 * 用户电子签名
 * Created by duxin on 2018/3/14
 */
@Controller
@RequestMapping(value = "/wechat/signature/contract")
public class WechatEleSignatureController {
	
	@Autowired
	private IWeChatEleSignatureService wechatEleSignatureService;
	
	/**
	 * @desc 生成合同
	 * @param map
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/gene", method = RequestMethod.POST)
    public Page<?> contractGenerate(@RequestBody Map<String, String> map){
        return wechatEleSignatureService.contractGenerate(map);
    }
	
	/**
	 * @desc 获取待签约列表
	 * @param map
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/getsignurl", method = RequestMethod.POST)
    public Page<?> contractGetSignUrl(@RequestBody Map<String, String> map){
        return wechatEleSignatureService.contractGetSignUrl(map);
    }
	
	/**
	 * @desc 查询签约状态
	 * @param map
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/querysigninfo", method = RequestMethod.POST)
    public Page<?> contractQuerySignInfo(@RequestBody Map<String, String> map){
        return wechatEleSignatureService.contractQuerySignInfo(map);
    }
	
	/**
	 * @desc 生成合同PDF
	 * @param map
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/downloadPDF", method = RequestMethod.POST)
    public Page<?> downloadPDF(@RequestBody Map<String, String> map){
        return wechatEleSignatureService.downloadPDF(map);
    }

}

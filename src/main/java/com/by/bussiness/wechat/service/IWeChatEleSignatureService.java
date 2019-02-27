package com.by.bussiness.wechat.service;

import java.util.Map;

import com.by.core.util.Page;

public interface IWeChatEleSignatureService {

	/**
	 * @desc 生成合同
	 * @param map
	 * @return
	 */
	public Page<?> contractGenerate(Map<String, String> map);
	
	/**
	 * @desc 获取待签约列表
	 * @param map
	 * @return
	 */
	public Page<?> contractGetSignUrl(Map<String, String> map);
	
	/**
	 * @desc 查询签约状态
	 * @param map
	 * @return
	 */
	public Page<?> contractQuerySignInfo(Map<String, String> map);
	
	/**
	 * @desc 生成合同PDF
	 * @param map
	 * @return
	 */
	public Page<?> downloadPDF(Map<String, String> map);
}

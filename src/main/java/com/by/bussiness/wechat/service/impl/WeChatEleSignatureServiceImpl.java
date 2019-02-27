package com.by.bussiness.wechat.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.mapper.TCustEleSignatureMapper;
import com.by.bussiness.wechat.model.TCustEleSignature;
import com.by.bussiness.wechat.model.TCustExclusiveQrcode;
import com.by.bussiness.wechat.service.IWeChatEleSignatureService;
import com.by.core.exception.BusinessException;
import com.by.core.log.BusiOperaLog;
import com.by.core.log.BusiOperaLogEventEnum;
import com.by.core.log.CustomLogger;
import com.by.core.startup.AppConfig;
import com.by.core.util.HttpHelper;
import com.by.core.util.IntfcClient;
import com.by.core.util.Page;
import com.by.core.util.QRCodeUtils;
import com.by.core.util.StringUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfStream;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class WeChatEleSignatureServiceImpl implements IWeChatEleSignatureService{

	@Autowired
	private TCustEleSignatureMapper custEleSignatureMapper;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<?> contractGenerate(Map<String, String> map) {
		Page<?> page = new Page<>();
		BusiOperaLog busiOperaLog = new BusiOperaLog(BusiOperaLogEventEnum.CONTRACT_GENERATE);
		if (map == null ){
            throw new BusinessException("参数不合法","100002");
        }
		//记录请求参数
		busiOperaLog.setReqParams(JSON.toJSONString(map));
		busiOperaLog.setKeyWord("phoneNo is " + map.get("phoneNo")+"   "+"contractNo is " + map.get("contractNo"));
		try {
			Map<String,Object> retMap = HttpHelper.postForm(AppConfig.get("contract.generate"), map);
			busiOperaLog.setRespMsg((String) retMap.get("respStr"));
			//解析返回json
			Map<String,Object> retResp = JSON.parseObject((String)retMap.get("respStr"), Map.class);
			if(!(boolean)retResp.get("success")){
				page.setSuccess(false);
				page.setMap(retResp);
			}
			//记录日志
			CustomLogger.busiOperaLogInfo(busiOperaLog);
		} catch (Exception e) {
			page.setSuccess(false);
			busiOperaLog.setErrorMsg("生成合同异常");
			CustomLogger.busiOperaLogInfo(busiOperaLog, e);
		}
		
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<?> contractGetSignUrl(Map<String, String> map) {
		Page<?> page = new Page<>();
		BusiOperaLog busiOperaLog = new BusiOperaLog(BusiOperaLogEventEnum.CONTRACT_GET_SIGN_URL);
		if (map == null ){
            throw new BusinessException("参数不合法","100002");
        }
		//记录请求参数
		busiOperaLog.setReqParams(JSON.toJSONString(map));
		busiOperaLog.setKeyWord("phoneNo is " + map.get("phoneNo")+"   "+"contractNo is " + map.get("contractNo"));
		try {
			Map<String, Object> retMap = IntfcClient.sendData(Map.class, AppConfig.get("contract.getsign.url"), map);
			busiOperaLog.setRespMsg(JSON.toJSONString(retMap));
			//解析返回的json
			JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(retMap.get("result")));
			String contractUrl = "";
	        for (int i = 0; i < jsonArray.size(); i++){
	            JSONObject jsonObject = jsonArray.getJSONObject(i);
	            //System.out.println(jsonObject.getString("contractUrl"));
	            contractUrl = jsonObject.getString("contractUrl");
	        }
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("url", contractUrl);
	        page.setMap(paramMap);
			//记录日志
			CustomLogger.busiOperaLogInfo(busiOperaLog);
		} catch (Exception e) {
			page.setSuccess(false);
			busiOperaLog.setErrorMsg("获取待签约列表异常");
			CustomLogger.busiOperaLogInfo(busiOperaLog, e);
		}
		
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<?> contractQuerySignInfo(Map<String, String> map) {
		Page<?> page = new Page<>();
		BusiOperaLog busiOperaLog = new BusiOperaLog(BusiOperaLogEventEnum.CONTRACT_QUERY_SIGN_INFO);
		if (map == null ){
            throw new BusinessException("参数不合法","100002");
        }
		//记录请求参数
		busiOperaLog.setReqParams(JSON.toJSONString(map));
		busiOperaLog.setKeyWord("phoneNo is " + map.get("phoneNo")+"   "+"contractNo is " + map.get("contractNo"));
		try {
			Map<String, Object> retMap = IntfcClient.sendData(Map.class, AppConfig.get("contract.query.signinfo"), map);
			busiOperaLog.setRespMsg(JSON.toJSONString(retMap));
			page.setMap(retMap);
			//记录日志
			CustomLogger.busiOperaLogInfo(busiOperaLog);
		} catch (Exception e) {
			page.setSuccess(false);
			busiOperaLog.setErrorMsg("查询签约状态异常");
			CustomLogger.busiOperaLogInfo(busiOperaLog, e);
		}
		
		return page;
	}

	@Override
	public Page<?> downloadPDF(Map<String, String> map) {
		Page<?> page = new Page<>();
		BusiOperaLog busiOperaLog = new BusiOperaLog(BusiOperaLogEventEnum.CONTRACT_DOWNLOAD_PDF);
		if (map == null ){
            throw new BusinessException("参数不合法","100002");
        }
		//记录请求参数
		busiOperaLog.setReqParams(JSON.toJSONString(map));
		busiOperaLog.setKeyWord("phoneNo is " + map.get("phoneNo")+"   "+"contractNo is " + map.get("contractNo"));
		try {
			Map<String,Object> retMap = HttpHelper.postForm(AppConfig.get("contract.download.contract.pdf"), map);
			//busiOperaLog.setRespMsg((String) retMap.get("respStr"));
			convertInputStreamToPDFAndSave((InputStream) retMap.get("respInStream"), map.get("phoneNo"));
			//记录日志
			CustomLogger.busiOperaLogInfo(busiOperaLog);
		} catch (Exception e) {
			page.setSuccess(false);
			busiOperaLog.setErrorMsg("下载合同PDF异常");
			CustomLogger.busiOperaLogInfo(busiOperaLog, e);
		}
		
		return page;
	}
	
	/*把合同流转成PDF文件并保存*/
	private void convertInputStreamToPDFAndSave(InputStream in, String phoneNo) throws Exception{
		//先删在新增
        Map<String, Object> params = new HashMap<>();
        params.put("busiNo", phoneNo);
        params.put("busiType", Constants.ELE_SIGNATURE_CONTRACT);
		TCustEleSignature tCustEleSignature = custEleSignatureMapper.get(params);
        if (tCustEleSignature != null) {
        	custEleSignatureMapper.deleteByPhoneNo(phoneNo);
		}
        File filePath = new File(AppConfig.get("upload.ele.signature.contract.path")); //电子合同保存路径
        if (filePath.exists() == false) {
        	filePath.mkdirs();
		}
        File file = new File(filePath +"/" +phoneNo + ".pdf");
        if (file.exists() == false){
        	file.createNewFile();
        }
        
		FileOutputStream fout = new FileOutputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int a = 0;
		while ((a = in.read()) != -1) {
			baos.write(a);
		}
		baos.flush();

		// 使用itext操作PDF
		Document doc = new Document();
		PdfStream pdfs = new PdfStream(baos.toByteArray());
		PdfWriter pdfw = PdfWriter.getInstance(doc, fout);
		pdfs.toPdf(pdfw, fout);
		pdfw.flush();
		
		//电子签章信息保存
		TCustEleSignature custEleSignature = new TCustEleSignature();
		custEleSignature.setId(StringUtils.getUUID());
		custEleSignature.setBusiNo(phoneNo);
		custEleSignature.setBusiType(Constants.ELE_SIGNATURE_CONTRACT);
		custEleSignature.setIsWechat(Constants.YON_N);
		custEleSignature.setAttName(phoneNo);
		custEleSignature.setAttSuffix("pdf");
		custEleSignature.setAttPath(AppConfig.get("upload.ele.signature.contract.path"));
		custEleSignature.setRemark("电子签章合同");
		custEleSignature.setCreateDate(new Date());
		custEleSignatureMapper.insert(custEleSignature);
		
		// 关闭流
		baos.close();
		pdfw.close();
		fout.close();
		in.close();
	}
	

}

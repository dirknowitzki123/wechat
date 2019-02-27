package com.by.manage.wechat.service;
import java.util.List;
import java.util.Map;

import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.core.service.IBaseService;
import com.by.manage.wechat.dto.TCustBaseInfoDto;
import com.file.model.FileBody;

public interface ITCustBaseInfoService extends IBaseService<TCustBaseInfo>{
	
	//更新客户信息(可以修改父级推荐码、自身推荐码)
	void updateInfo(TCustBaseInfo obj);
	
	//查询所有客户记录(包括"正常"和"销户"的记录)
	public List<TCustBaseInfoDto> getAllList(Map<String,Object> map);
	
	//启用客户功能
	void enable(List<String> ids);
	
	//通过phoneNo得到文件流
	public FileBody getEleContractInputStream(String phoneNo);
	
}


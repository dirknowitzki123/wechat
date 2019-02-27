package com.by.manage.wechat.service;

import java.util.List;

import com.by.core.service.IBaseService;
import com.by.manage.wechat.model.BCoWechatBranch;
import com.file.model.FileBody;

public interface IBCoWechatBranchService extends IBaseService<BCoWechatBranch>{
	public FileBody showDedicatedQrcode(String id);
	
	//更新客户信息(可以修改父级推荐码、自身推荐码)
	void updateInfo(BCoWechatBranch obj);
	
	//启用商户功能
	void enable(List<String> ids);
}


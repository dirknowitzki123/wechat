package com.by.system.service;
import com.by.core.service.IBaseService;
import com.by.system.model.ASysAppVersions;

public interface IASysAppVersionsService extends IBaseService<ASysAppVersions>{
	/**
	 * 获取最新app下载信息
	 * @param aSysAppVersions
	 * @return
	 */
	public ASysAppVersions queryNewVersion(ASysAppVersions aSysAppVersions);
}


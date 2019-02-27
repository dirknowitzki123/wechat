package com.by.system.service;
import java.util.List;
import java.util.Map;

import com.by.core.service.IBaseService;
import com.by.frame.bo.CodeBo;
import com.by.system.model.ASysCodeInfo;

public interface IASysCodeInfoService extends IBaseService<ASysCodeInfo>{
	/**
	 * 通过typeCode获取码值，提供前端接口
	 * @param codes 类型编码
	 * @return
	 */
	Map<String, Object> getCode(String typeCode);
	Map<String,Object> getCode(List<String> list);
	Map<String, Object> getCode2(List<CodeBo> codes);
}


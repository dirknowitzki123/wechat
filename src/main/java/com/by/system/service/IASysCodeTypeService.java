package com.by.system.service;
import java.util.List;

import com.by.core.service.IBaseService;
import com.by.system.model.ASysCodeType;

public interface IASysCodeTypeService extends IBaseService<ASysCodeType>{
	void delete(List<String> ids);
}


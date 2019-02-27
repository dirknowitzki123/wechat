package com.by.pub.service;
import org.springframework.web.multipart.MultipartFile;

import com.by.core.service.IBaseService;
import com.by.pub.model.ASysAtt;
import com.by.pub.model.ASysAttExt;

public interface IASysAttExtService extends IBaseService<ASysAttExt>{
	
	ASysAttExt upload(MultipartFile file,ASysAtt aSysAtt,ASysAttExt aSysAttExt);
	
}


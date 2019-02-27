package com.by.pub.service;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.by.core.service.IBaseService;
import com.by.pub.model.ASysAtt;
import com.file.model.FileBody;

public interface IASysAttService extends IBaseService<ASysAtt>{
	static final List<String> imageExtensions = Arrays.asList("JPEG,JPG,PNG,BMP,GIF".split(","));
	

	FileBody downloadPUB(String id, String thumb);

	ASysAtt uploadPUB(MultipartFile file, ASysAtt aSysAtt);
	
	public FileBody downloadPUB(String busiNo,String attTyp, String thumb);
	
	/**
	 * 通过主键获得对象
	 * @param primaryKey
	 * @return
	 */
	public ASysAtt getByPrimaryKey(Serializable primaryKey);
	
}


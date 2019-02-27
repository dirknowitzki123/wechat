package com.by.pub.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.core.util.SessionUtil;
import com.by.core.util.UUIDUtil;
import com.by.pub.mapper.ASysAttExtMapper;
import com.by.pub.model.ASysAtt;
import com.by.pub.model.ASysAttExt;
import com.by.pub.service.IASysAttExtService;
import com.by.pub.service.IASysAttService;
import com.by.system.model.ASysUser;

@Service
public class ASysAttExtServiceImpl extends BaseService implements IASysAttExtService{
	
	
	@Autowired
	private IASysAttService aSysAttService;
	
	@Autowired
	private ASysAttExtMapper aSysAttExtMapper;

	@Override
	public ASysAttExt get(Map<String,Object> map){
		return aSysAttExtMapper.get(map);
	}
	@Override
	public List<ASysAttExt> getList(Map<String,Object> map){
		return aSysAttExtMapper.getList(map);
	}
	@Override
	public void save(ASysAttExt obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(ASysAttExt obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		List<String> attId = new ArrayList<String>();
		ASysAttExt aSysAttExt = new ASysAttExt();
		for (String id : ids) {
			aSysAttExt.setId(id);
		}
		aSysAttExt = aSysAttExtMapper.get(BeanUtil.transBean2Map(aSysAttExt));               //获取当前删除数据
		attId.add(aSysAttExt.getAttNo());
		aSysAttService.delete(attId);               //删除附件表
		super.daoMysql.delete(ids, ASysAttExt.class);
	}
	
	
	@Override
	public ASysAttExt upload(MultipartFile file,ASysAtt aSysAtt, ASysAttExt aSysAttExt) {
		ASysUser currentASysUser = SessionUtil.getCurrentASysUser();       //获取当前登录用户
		Date now = new Date();                                             //获取当前时间
		String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase();    //获取文档后缀
		aSysAttExt.setUpdtDate(now); //设置修改时间
		
		/** 删除附件*/
		if ( file != null && file.getSize() > 0 && StringUtils.isNotEmpty(aSysAttExt.getAttNo()) ) {     //判断文件不为空并且attNo不为空
			List<String> ids = new ArrayList<String>(); 
			ids.add(aSysAttExt.getAttNo());
			aSysAttService.delete(ids);                                          
		} 
		
		/** 新增*/
		if (StringUtils.isEmpty( aSysAttExt.getId())) {                     
			if( !file.isEmpty()){
				aSysAtt.setBusiNo(currentASysUser.getId());           //根据当前登录用户id 设置业务id 
				aSysAtt.setBusiTyp("21400003");                       //设置业务类型     其他
				aSysAtt = aSysAttService.uploadPUB(file, aSysAtt);     //调用附件上传
				aSysAttExt.setAttNo(aSysAtt.getId());                  //根据附件上传id存储	
			}
			String uuid = UUIDUtil.getUUID();                          //获取uuid 
			aSysAttExt.setId(uuid);                                    //ext id
			aSysAttExt.setBusiNo(aSysAtt.getBusiNo());                 //ext 业务id
			aSysAttExt.setBusiTyp(aSysAtt.getBusiTyp());               //ext 业务类型
			aSysAttExt.setAttFile(extension);                          //文档后缀
			aSysAttExt.setInstUserNo(currentASysUser.getId());         //当前登录用户
			aSysAttExt.setInstUserName(currentASysUser.getUserName());
			aSysAttExt.setInstDate(now);
			aSysAttExtMapper.insert(aSysAttExt);
			return aSysAttExt;
		}
		
		/** 修改*/
		if(file.getSize() >0 ){                                          
			aSysAtt.setBusiNo(currentASysUser.getId());
			aSysAtt.setBusiTyp("11111111");
			aSysAtt = aSysAttService.uploadPUB(file, aSysAtt);
			aSysAttExt.setAttNo(aSysAtt.getId());    //根据附件上传id存储	
		}
		aSysAttExtMapper.updateByPrimaryKey(BeanUtil.transBean2Map(aSysAttExt));
		
		return aSysAttExt;
		
	}
}


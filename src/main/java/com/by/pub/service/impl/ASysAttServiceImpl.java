package com.by.pub.service.impl;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.DateUtils;
import com.by.core.util.ImageUtil;
import com.by.core.util.SessionUtil;
import com.by.core.util.StringUtils;
import com.by.core.util.UUIDUtil;
import com.by.pub.dto.mapper.ASysAttDtoMapper;
import com.by.pub.mapper.ASysAttMapper;
import com.by.pub.model.ASysAtt;
import com.by.pub.service.IASysAttService;
import com.by.system.model.ASysUser;
import com.file.client.FileDownClient;
import com.file.client.FileUploadClient;
import com.file.model.FileBody;
import com.file.model.FileResult;

@Service
public class ASysAttServiceImpl extends BaseService implements IASysAttService{
	@Value("${upload.file.server.addr}")
	private String FILE_SERVER_ADDR;
	
	@Value("${upload.file.path}") 
	private String UPLOAD_SAVE_PATH;
	
	@Value("${down.file.server.addr}")
	private String DOWN_SERVER_ADDR;
	
	@Value("${upload.file.zoom.width}")
	private Integer UPLOAD_ZOOM_WIDTH;
	
	@Value("${upload.file.zoom.height}")
	private Integer UPLOAD_ZOOM_HEIGHT;
	
	@Autowired
	private ASysAttMapper aSysAttMapper;
	
	@Autowired
	private ASysAttDtoMapper aSysAttDtoMapper;

	@Override
	public ASysAtt get(Map<String,Object> map){
		return aSysAttMapper.get(map);
	}
	@Override
	public List<ASysAtt> getList(Map<String,Object> map){
		
		Object obj = map.get("busiNo");
		if ( null == obj || obj.equals("") ) {
			return new ArrayList<ASysAtt>();
		}
		
		return aSysAttDtoMapper.getListPubASysAtts( map );
	}
	@Override
	public void save(ASysAtt obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(ASysAtt obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysAtt.class);
	}
	@Override
	public ASysAtt uploadPUB(MultipartFile file, ASysAtt aSysAtt) {
		
		ASysUser currentASysUser = SessionUtil.getCurrentASysUser();
		
		if ( StringUtils.isEmpty(aSysAtt.getBusiNo())) {
			throw new BusinessException("文件上传失败：参数【busiNo】业务编号不能为空。");
		}
		
		String uuid = UUIDUtil.getUUID();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase();
		String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
		
		if ( StringUtils.isNotEmpty(aSysAtt.getOldAtt()) ) {
			baseName = FilenameUtils.getBaseName(aSysAtt.getOldAtt());
		}
		
		String uuidName =uuid;
		String originFileName = baseName;
		String extName = "." + extension.toLowerCase();
		InputStream ins = null;
		String attSize = null;
		try {
			ins = file.getInputStream();
			attSize = String.valueOf(ins.available());
		} catch (IOException e) {
			throw new BusinessException( "文件上传失败" );
		}
		
		
		String savePath = UPLOAD_SAVE_PATH + "/" + DateUtils.getCurDate("yyyyMMdd");
		
		FileResult fileResult = FileUploadClient.upload(FILE_SERVER_ADDR, uuidName, originFileName, extName, savePath, ins);
		
		if ( imageExtensions.contains( extension ) ) {
			try {
				ins = file.getInputStream();
			} catch (IOException e) {
				throw new BusinessException( "文件上传失败" );
			}
			//缩略图处理
			uuidName += "_thumb";
			ins = ImageUtil.zoom(ins, UPLOAD_ZOOM_WIDTH, UPLOAD_ZOOM_HEIGHT, extension);
			fileResult = FileUploadClient.upload(FILE_SERVER_ADDR, uuidName, originFileName, extName, savePath, ins);
		}
		
		Date now = new Date();
		
		aSysAtt.setId(uuid);
		aSysAtt.setAttSize( attSize );
		/*aSysAtt.setBusiNo(busiNo);
		aSysAtt.setBusiTyp(busiTyp);
		aSysAtt.setAttTyp(attTyp);*/
		
		aSysAtt.setNewAtt(uuid);
		aSysAtt.setOldAtt(originFileName);
		aSysAtt.setAttFile(extension);
		aSysAtt.setPhPath(fileResult.getBody().getSavePath());
		aSysAtt.setInPath(DOWN_SERVER_ADDR);
		
		aSysAtt.setInstUserName(currentASysUser.getUserName());
		aSysAtt.setInstUserNo(currentASysUser.getId());
		aSysAtt.setInstUserOrg(currentASysUser.getOrgCode());
		aSysAtt.setInstDate(now);
		aSysAtt.setUpdtUserName(currentASysUser.getUserName());
		aSysAtt.setUpdtUserNo(currentASysUser.getId());
		aSysAtt.setUpdtUserOrg(currentASysUser.getOrgCode());
		aSysAtt.setUpdtDate(now);
		
		//先根据busiNo和attTyp删除数据
		Map<String, Object> delMaps = new HashMap<String, Object>();
		delMaps.put("busiNo", aSysAtt.getBusiNo());
		delMaps.put("attTyp", aSysAtt.getAttTyp());
		aSysAttDtoMapper.deleteByBusiNoAttTyp(delMaps);
		
		aSysAttMapper.insert(aSysAtt);
		
		return aSysAtt;
	}
	@Override
	public FileBody downloadPUB(String uuid, String thumb) {
		
		if ( StringUtils.isEmpty(thumb) ) {
			thumb = "";
		} else {
			thumb = "_" + thumb;
		}
		
		ASysAtt aSysAtt = aSysAttMapper.getByPrimaryKey(uuid);
		String uuidName = aSysAtt.getNewAtt() + thumb;
		String extName = "." + aSysAtt.getAttFile().toLowerCase();
		String savePath = aSysAtt.getPhPath();
		String originalFilename = aSysAtt.getOldAtt();
		String downServerAddr = aSysAtt.getInPath();
		FileBody fileBody = FileDownClient.down(downServerAddr , uuidName, originalFilename, extName , savePath);
		return fileBody;
	}
	
	public FileBody downloadPUB(String busiNo,String attTyp, String thumb) {
		if(StringUtils.isEmpty(busiNo) || StringUtils.isEmpty(attTyp)){
			return null;
		}
		if ( StringUtils.isEmpty(thumb) ) {
			thumb = "";
		} else {
			thumb = "_" + thumb;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("busiNo", busiNo);
		params.put("attTyp", attTyp);
		List<ASysAtt> list = aSysAttDtoMapper.getListByInstDateDesc(params);
		if(null == list || list.size() == 0){
			return null;
		}
		FileBody fileBody = null;
		ASysAtt aSysAtt = list.get(0);
		if(null != aSysAtt){			
			String uuidName = aSysAtt.getNewAtt() + thumb;
			String extName = "." + aSysAtt.getAttFile().toLowerCase();
			String savePath = aSysAtt.getPhPath();
			String originalFilename = aSysAtt.getOldAtt();
			String downServerAddr = aSysAtt.getInPath();
			fileBody = FileDownClient.down(downServerAddr , uuidName, originalFilename, extName , savePath);
		}
		return fileBody;
	}
	/* (non-Javadoc)
	 * @see com.by.pub.service.IASysAttService#getByPrimaryKey(java.io.Serializable)
	 */
	@Override
	public ASysAtt getByPrimaryKey(Serializable primaryKey) {
		return aSysAttMapper.getByPrimaryKey(primaryKey);
	}
}


package com.by.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.system.dto.CodeGroupInfoDTO;
import com.by.system.model.ASysCodeGroup;
import com.by.system.model.ASysCodeInfo;
import com.by.system.model.ASysCodeType;
import com.by.system.service.IASysCodeGroupService;
import com.by.system.service.IASysCodeInfoService;
import com.by.system.service.IASysCodeTypeService;

@Controller
@RequestMapping(value="/system/aSysCode")
public class ASysCodeController  extends BaseController {
	@Autowired 
	private IASysCodeInfoService aSysCodeInfoService;
	@Autowired 
	private IASysCodeTypeService aSysCodeTypeService;
	@Autowired 
	private IASysCodeGroupService aSysCodeGroupService;
	
	@RequiresPermissions(value="system:aSysCode:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/system/asyscode/aSysCode.typeIndex";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:index")
	@RequestMapping(value="/typeList", method = RequestMethod.POST)
	public Page<?> typeList(Page<?> page) {
		page.startPage();
		List<ASysCodeType> list = aSysCodeTypeService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:index")
	@RequestMapping(value="/codeList", method = RequestMethod.POST)
	public Page<?> codeList(Page<?> page) {
		page.startPage();
		List<ASysCodeInfo> list = aSysCodeInfoService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	@RequiresPermissions(value="system:aSysCode:index")
	@RequestMapping(value="/groupIndex", method = RequestMethod.GET)
	public String groupIndex(Page<?> page) {
		return "/system/asyscode/aSysCode.groupIndex";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:index")
	@RequestMapping(value="/groupList", method = RequestMethod.POST)
	public Page<?> groupList(Page<?> page) {
//		page.startPage();
		List<ASysCodeGroup> list = aSysCodeGroupService.getGroupDistinct(page.getParams());
		page.setList(list);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:index")
	@RequestMapping(value="/groupCodeList", method = RequestMethod.POST)
	public List<ASysCodeGroup> groupCodeList(@RequestBody ASysCodeGroup aSysCodeGroup) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("groupCode", aSysCodeGroup.getGroupCode());
		map.put("typeCode", aSysCodeGroup.getTypeCode());
		List<ASysCodeGroup> list = aSysCodeGroupService.getList(map);
		return list;
	}
	
	//form
	@RequiresPermissions(value="system:aSysCode:save")
	@RequestMapping(value="/typeForm", method = RequestMethod.GET)
	public String typeForm(Page<?> page) {
		return "/system/asyscode/aSysCode.typeForm";
	}
	
	@RequiresPermissions(value="system:aSysCode:save")
	@RequestMapping(value="/codeForm", method = RequestMethod.GET)
	public String codeForm(Page<?> page) {
		return "/system/asyscode/aSysCode.codeForm";
	}
	
	@RequiresPermissions(value="system:aSysCode:save")
	@RequestMapping(value="/groupForm", method = RequestMethod.GET)
	public String groupForm(Page<?> page) {
		return "/system/asyscode/aSysCode.groupForm";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:save")
	@RequestMapping(value="/typeSave", method = RequestMethod.POST)
	public Page<?> typeSave(Page<?> page, ASysCodeType aSysCodeType) {
		aSysCodeTypeService.save(aSysCodeType);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:save")
	@RequestMapping(value="/codeSave", method = RequestMethod.POST)
	public Page<?> codeSave(Page<?> page, ASysCodeInfo aSysCodeInfo) {
		aSysCodeInfoService.save(aSysCodeInfo);
		return page;
	}
	
	/**
	 * 保存码表组基本信息
	 * 码表组下码值保存的基本思路：
	 * 由于码组只有一张表，所以先保存一条不含码值信息的码组记录。然后由功能：码组管理像其表中插入详细的码组和码值的详细记录。
	 * 故：查询码组下的码值时，一定带有码值为空的查询条件，否则将会查询出为空码值为空的那条码组基本信息
	 * @param page
	 * @param typeCode
	 * @param remark
	 * @param groupCode
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:save")
	@RequestMapping(value="/groupSave", method = RequestMethod.POST)
	public Page<?> groupSave(Page<?> page, String typeCode,String remark,String groupCode) {
		aSysCodeGroupService.saveGroup(typeCode,groupCode,remark);
		return page;
	}
	
	//delete
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:delete")
	@RequestMapping(value="/typeDelete", method = RequestMethod.POST)
	public Page<?> typeDelete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysCodeTypeService.delete(ids);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:delete")
	@RequestMapping(value="/codeDelete", method = RequestMethod.POST)
	public Page<?> codeDelete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysCodeInfoService.delete(ids);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:aSysCode:delete")
	@RequestMapping(value="/groupDelete", method = RequestMethod.POST)
	public Page<?> groupDelete(Page<?> page,@RequestParam("ids[]") List<String> groupCodes,@RequestParam("typeCode") String typeCode) {
		aSysCodeGroupService.delete(groupCodes,typeCode);
		return page;
	}
	
	/**
	 * 跳转到码表组的码值管理界面
	 * @param page
	 * @return
	 */
	@RequiresPermissions(value="system:aSysCode:index")
	@RequestMapping(value="/groupManageIndex", method = RequestMethod.GET)
	public String groupManageIndex(Page<?> page) {
		return "/system/asyscode/aSysCode.groupManageIndex";
	}
	
	/**
	 * 删除码组中的码值
	 * @param page
	 * @param valCodes
	 * @param typeCode
	 * @param groupCode
	 * @return
	 */
	@RequiresPermissions(value="system:aSysCode:delete")
	@RequestMapping(value = "/delGroupCode", method = RequestMethod.POST)
	@ResponseBody
	public Page<?> delGroupCode (Page<?> page, @RequestParam("valCodes[]") List<String> valCodes,
			@RequestParam("typeCode") String typeCode,@RequestParam("groupCode") String groupCode){
		aSysCodeGroupService.deleteGrpCodes(valCodes, typeCode, groupCode);
		return page;
	}
	
	/**
	 * 保存码组和码值的关系
	 * @param codeGroupInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequiresPermissions(value="system:aSysCode:save")
	@RequestMapping(value="/saveGrpCodes",method = RequestMethod.POST)
	@ResponseBody
	public Page<?> saveGrpCodes(@RequestBody CodeGroupInfoDTO codeGroupInfo){
		Page page = new Page();
		aSysCodeGroupService.saveGrpCodes(codeGroupInfo.getCodeGrp(),codeGroupInfo.getCodes());
		return page;
	}
	
	/**
	 *	获取码表组下存在的码值 
	 * @param page
	 * @param typeCode
	 * @param groupCode
	 * @return
	 */
	@RequiresPermissions(value="system:aSysCode:index")
	@ResponseBody
	@RequestMapping(value = "/grpExistCodeLst",method = RequestMethod.POST)
	public Page<?> getGrpExistCodeLst(Page<?> page ){
		Map<String, Object> params = page.getParams();
		List<ASysCodeInfo> lst = aSysCodeGroupService.getGrpExistCodeLst(params);
		page.setList(lst);
		return page;
	}
	
	/**
	 * 获取码表组下未关联的码值
	 * @param page
	 * @param typeCode
	 * @param groupCode
	 * @return
	 */
	@RequiresPermissions(value="system:aSysCode:index")
	@RequestMapping(value="/grpNotExistCodeLst" ,method= RequestMethod.POST)
	@ResponseBody
	public Page<?> getGrpNotExistCodeLst(Page<?> page ){
		Map<String, Object> params = page.getParams();
		List<ASysCodeInfo> lst = aSysCodeGroupService.getGrpNotExistCodeLst(params);
		page.setList(lst);
		return page;
	}
	
	
}

package com.by.frame.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.core.util.StringUtils;

@Controller
@RequestMapping("/frame/shiro")
public class ShiroController extends BaseController {

	@ResponseBody
	@RequestMapping(value="/perms", method=RequestMethod.POST)
	public Page<Object> perms( Page<Object> page, @RequestParam( "perms[]" ) List<String> perms ) {
		page.setMap(new HashMap<String, Object>());
		
		Boolean a = true, v = false;
		
		Subject subject = SecurityUtils.getSubject();
		String perm;
		Boolean r;
		for (int i = 0; i < perms.size(); i++) {
			perm = perms.get(i).trim();
			if ( StringUtils.isNotEmpty(perm) ) {
				r = subject.isPermitted(perm); 
				if (r && !v ) v = true;
				else if ( !r && a ) a = false;
				page.getMap().put(perm, r);
			}
		}
		
		page.getMap().put("all", a);
		page.getMap().put("valid", v);
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value="/roles", method=RequestMethod.POST)
	public Page<Object> roles( Page<Object> page, @RequestParam( "roles[]" ) List<String> roles ) {
		page.setMap(new HashMap<String, Object>());
		
		Subject subject = SecurityUtils.getSubject();
		String role;
		for (int i = 0; i < roles.size(); i++) {
			role = roles.get(i).trim();
			if ( StringUtils.isNotEmpty(role) ) {
				page.getMap().put(role, subject.isPermitted(role));
			}
		}
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value="/permsOrRoles", method=RequestMethod.POST)
	public Page<Object> permsOrRoles( Page<Object> page, @RequestParam( "perms[]" ) List<String> perms, @RequestParam( "roles[]" ) List<String> roles ) {
		page.setMap(new HashMap<String, Object>());
		Boolean a = true, v = false;
		
		Subject subject = SecurityUtils.getSubject();
		String perm; Boolean r;
		
		for (int i = 0; i < perms.size(); i++) {
			perm = perms.get(i).trim();
			if ( StringUtils.isNotEmpty(perm) ) {
				r = subject.isPermitted(perm); 
				if (r && !v ) v = true;
				else if ( !r && a ) a = false;
				page.getMap().put(perm, r);
			}
		}
		
		String role; v = false;
		for (int i = 0; i < roles.size(); i++) {
			role = perms.get(i).trim();
			if ( StringUtils.isNotEmpty(role) ) {
				r = subject.hasRole(role);
				if (r && !v ) v = true;
				else if ( !r && a ) a = false;
				page.getMap().put(role, r);
			}
		}
		page.getMap().put("all", a);
		page.getMap().put("valid", v);
		return page;
	}
	
	
}

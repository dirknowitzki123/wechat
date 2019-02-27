/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.by.core.shiro;

import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.by.frame.service.IShiroRealmService;

public class ShiroRealm extends AuthorizingRealm {

	private static final Logger logger = Logger.getLogger(ShiroRealm.class);
	
	@Autowired private IShiroRealmService shiroRealmService;
	
	/**
	 * 认证回调函数 登录时调用
	 */
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		return new SimpleAuthenticationInfo(token.getUsername() ,token.getCredentials(), getName());
    }


	/**
	 * 权限授权
	 */
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.debug("=========================================================================");
		logger.debug("AuthorizingRealm doGetAuthorizationInfo start");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String principal = (String) principals.fromRealm(getName()).iterator().next();
        
        //角色roles授权
        List<String> roles = shiroRealmService.findRolesByUserId(principal);
        if(roles != null && roles.size() > 0) authorizationInfo.setRoles(new HashSet<String>(roles));
  
        //许可perms授权
        List<String> perms = shiroRealmService.findPermissionsByUserId(principal);
        perms.addAll(roles);
        if(perms != null && perms.size() > 0) authorizationInfo.addStringPermissions(perms);
        
        authorizationInfo.setRoles(new HashSet<String>( roles ));
        authorizationInfo.addStringPermissions(perms);
        
        logger.debug("登录用户：" + principal);
        logger.debug("roles:" + roles);
        logger.debug("perms:" + perms);
        
        logger.debug("AuthorizingRealm doGetAuthorizationInfo end");
		logger.debug("=========================================================================");
        return authorizationInfo;  
    }

}


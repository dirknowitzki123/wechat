package com.by.frame.service;

import java.util.List;

public interface IShiroRealmService {
	
	List<String> findRolesByUserId( String userId );
	
	List<String> findPermissionsByUserId( String userId );
	
	List<String> findRoles();
	
	List<String> findPermissions();
}

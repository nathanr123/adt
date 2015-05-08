/**
 * 
 */
package com.cti.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cti.model.UserGroupPermission;

/**
 * @author dharshini
 *
 */
@Transactional
public interface GroupPermissionService {

	public void setGroupPerssions(UserGroupPermission userGroupPermission);

	public void setGroupPerssions(List<UserGroupPermission> userGroupPermissionList);
	
	public UserGroupPermission getGroupPerssions(String groupId,String componentName);
	
	public List<UserGroupPermission> getGroupPerssionsByGroupId(String groupId);

	public List<UserGroupPermission> getGroupPerssionsByComponent(
			String componentName);

}

/**
 * 
 */
package com.cti.dao;

import java.util.List;

import com.cti.model.UserGroupPermission;

/**
 * @author dharshini
 *
 */
public interface GroupPermissionDAO {

	public void setGroupPerssions(UserGroupPermission userGroupPermission);

	public void setGroupPerssions(
			List<UserGroupPermission> userGroupPermissionList);

	public UserGroupPermission getGroupPerssions(String groupId,
			String componentName);

	public List<UserGroupPermission> getGroupPerssionsByGroupId(String groupId);

	public List<UserGroupPermission> getGroupPerssionsByComponent(
			String componentName);
}

/**
 * 
 */
package com.cti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cti.dao.GroupPermissionDAO;
import com.cti.model.UserGroupPermission;

/**
 * @author dharshini
 *
 */
@Service
public class GroupPermissionServiceEx implements GroupPermissionService {

	@Autowired
	GroupPermissionDAO groupPermissionDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.GroupPermissionService#setGroupPerssions(com.cti.model
	 * .UserGroupPermission)
	 */
	@Override
	public void setGroupPerssions(UserGroupPermission userGroupPermission) {

		groupPermissionDAO.setGroupPerssions(userGroupPermission);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.GroupPermissionService#getGroupPerssionsByGroupId(java
	 * .lang.String)
	 */
	@Override
	public List<UserGroupPermission> getGroupPerssionsByGroupId(String groupId) {

		return groupPermissionDAO.getGroupPerssionsByGroupId(groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.GroupPermissionService#getGroupPerssionsByComponent(java
	 * .lang.String)
	 */
	@Override
	public List<UserGroupPermission> getGroupPerssionsByComponent(
			String componentName) {

		return groupPermissionDAO.getGroupPerssionsByComponent(componentName);
	}

	@Override
	public void setGroupPerssions(
			List<UserGroupPermission> userGroupPermissionList) {

		groupPermissionDAO.setGroupPerssions(userGroupPermissionList);

	}

	@Override
	public UserGroupPermission getGroupPerssions(String groupId,
			String componentName) {

		return groupPermissionDAO.getGroupPerssions(groupId, componentName);
	}

}

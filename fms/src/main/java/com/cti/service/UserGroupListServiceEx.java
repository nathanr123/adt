/**
 * 
 */
package com.cti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cti.dao.UserGroupListDAO;
import com.cti.model.UsersGroupList;

/**
 * @author nathanr_kamal
 *
 */
@Service
public class UserGroupListServiceEx implements UserGroupListService {

	@Autowired
	UserGroupListDAO userGroupListDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserGroupListService#getUserGroupListByUserId(java.lang
	 * .String)
	 */
	@Override
	public List<UsersGroupList> getUserGroupListByUserId(String username) {
		return userGroupListDAO.getUserGroupListByUserId(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserGroupListService#getUserGroupListByGroupId(java.lang
	 * .String)
	 */
	@Override
	public List<UsersGroupList> getUserGroupListByGroupId(String groupId) {
		return userGroupListDAO.getUserGroupListByGroupId(groupId);
	}

	@Override
	public void saveUsersandGroup(List<UsersGroupList> userGroupList) {
		userGroupListDAO.saveUsersandGroup(userGroupList);

	}

	@Override
	public void saveUsersandGroup(List<String> userList, List<String> grpList) {
		userGroupListDAO.saveUsersandGroup(userList, grpList);

	}

}

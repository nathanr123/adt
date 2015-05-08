package com.cti.dao;

import java.util.List;

import com.cti.model.UsersGroupList;

public interface UserGroupListDAO {

	public void saveUsersandGroup(List<UsersGroupList> userGroupList);
	
	public void saveUsersandGroup(List<String> userList, List<String> grpList);
	
	public List<UsersGroupList> getUserGroupListByUserId(String username);
	
	public List<UsersGroupList> getUserGroupListByGroupId(String groupId);
}

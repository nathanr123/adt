package com.cti.dao;

import java.util.List;

import com.cti.model.UserGroup;

public interface GroupDAO {

	public void addGroup(UserGroup group);

	public void updateGroup(UserGroup group);

	public void deletGroup(String groupId);

	public UserGroup getGroupById(String groupId);
	
	public boolean isGroupAvaliable(String groupId);
	
	public String getLatestGroupID();

	public List<UserGroup> listGroups();
	
	public List<UserGroup> listGroups(List<String> grpList);

}

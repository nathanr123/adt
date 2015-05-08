/**
 * 
 */
package com.cti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cti.dao.GroupDAO;
import com.cti.model.UserGroup;

/**
 * @author nathanr_kamal
 *
 */
@Service
public class GroupServiceEx implements GroupService {

	@Autowired
	GroupDAO groupDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.GroupService#addGroup(com.cti.model.UserGroup)
	 */
	@Override
	public void addGroup(UserGroup group) {
		groupDAO.addGroup(group);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.GroupService#updateGroup(com.cti.model.UserGroup)
	 */
	@Override
	public void updateGroup(UserGroup group) {
		groupDAO.updateGroup(group);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.GroupService#deletGroup(java.lang.String)
	 */
	@Override
	public void deletGroup(String groupId) {
		groupDAO.deletGroup(groupId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.GroupService#getGroupById(java.lang.String)
	 */
	@Override
	public UserGroup getGroupById(String groupId) {

		return groupDAO.getGroupById(groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.GroupService#listGroups()
	 */
	@Override
	public List<UserGroup> listGroups() {
		return groupDAO.listGroups();
	}

	@Override
	public List<UserGroup> listGroups(List<String> grpList) {
		return groupDAO.listGroups(grpList);
	}

	@Override
	public String getLatestGroupID() {
		return groupDAO.getLatestGroupID();
	}

	@Override
	public boolean isGroupAvaliable(String groupId) {

		return groupDAO.isGroupAvaliable(groupId);
	}

}

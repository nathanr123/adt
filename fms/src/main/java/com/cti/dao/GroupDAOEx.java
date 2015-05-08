/**
 * 
 */
package com.cti.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cti.model.UserGroup;
import com.cti.model.UserGroupPermission;
import com.cti.model.UsersGroupList;

/**
 * @author dharshini
 *
 */
@Repository
public class GroupDAOEx implements GroupDAO {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.GroupDAO#addGroup(com.cti.model.UserGroup)
	 */
	@Override
	public void addGroup(UserGroup group) {
		getCurrentSession().save(group);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.GroupDAO#updateGroup(com.cti.model.UserGroup)
	 */
	@Override
	public void updateGroup(UserGroup group) {
		getCurrentSession().update(group);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.GroupDAO#deletGroup(java.lang.String)
	 */
	@Override
	public void deletGroup(String groupId) {
		deleteAllGroupRecords(groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.GroupDAO#getGroupById(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserGroup getGroupById(String groupId) {
		List<UserGroup> grpList = new ArrayList<UserGroup>();

		Query query = getCurrentSession().createQuery(
				"from UserGroup u where u.groupid = :groupid");

		query.setParameter("groupid", groupId);

		grpList = query.list();

		if (grpList.size() > 0)
			return grpList.get(0);

		else
			return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.GroupDAO#listGroups()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserGroup> listGroups() {

		return getCurrentSession().createQuery("from UserGroup").list();

	}

	@Override
	public List<UserGroup> listGroups(List<String> grpList) {
		if (grpList != null) {
			List<UserGroup> userGroupList = new ArrayList<UserGroup>();

			for (Iterator<String> iterator = grpList.iterator(); iterator
					.hasNext();) {
				String group = iterator.next();

				userGroupList.add(getGroupById(group));

			}
			return userGroupList;
		} else
			return null;
	}

	private String getDeleteQuery(String table, String username) {

		return String.format("DELETE FROM %s WHERE groupid= \'%s\'", table,
				username);
	}

	private List<String> getAllDeletingQueries(String username) {

		List<String> qryList = new ArrayList<String>();

		Iterator<String> it = getAllGroupChildNames().iterator();

		while (it.hasNext()) {
			qryList.add(getDeleteQuery(it.next(), username));
		}

		return qryList;
	}

	private boolean deleteAllGroupRecords(String groupName) {

		try {
			Session session = getCurrentSession();

			List<String> it = getAllDeletingQueries(groupName);

			for (Iterator<String> iterator = it.iterator(); iterator.hasNext();) {

				session.createQuery(iterator.next()).executeUpdate();
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Set<String> getAllGroupChildNames() {

		LinkedHashSet<String> childList = new LinkedHashSet<String>();

		childList.add(UsersGroupList.class.getName());

		childList.add(UserGroupPermission.class.getName());

		childList.add(UserGroup.class.getName());

		return childList;
	}

	@Override
	public String getLatestGroupID() {

		Query query = getCurrentSession().createQuery(
				"SELECT MAX(groupid) FROM UserGroup");

		return (String) query.list().get(0);

	}

	@Override
	public boolean isGroupAvaliable(String groupId) {

		UserGroup userGroup = (UserGroup) getCurrentSession().get(
				UserGroup.class, groupId);

		return (userGroup != null) ? true : false;
	}
}

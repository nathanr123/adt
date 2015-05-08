/**
 * 
 */
package com.cti.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cti.model.User;
import com.cti.model.UserGroup;
import com.cti.model.UsersGroupList;
import com.cti.service.GroupService;
import com.cti.service.UserService;

/**
 * @author dharshini
 *
 */
@Repository
public class UserGroupListDAOEx implements UserGroupListDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	UserService userService;

	@Autowired
	GroupService groupService;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<UsersGroupList> getUserGroupListByUserId(String username) {
		List<UsersGroupList> grpList = new ArrayList<UsersGroupList>();

		Query query = getCurrentSession().createQuery(
				"from UsersGroupList u where u.username = :username");

		query.setParameter("username", username);

		grpList = query.list();

		if (grpList.size() > 0)
			return grpList;

		else
			return null;
	}

	@Override
	public List<UsersGroupList> getUserGroupListByGroupId(String groupId) {
		List<UsersGroupList> grpList = new ArrayList<UsersGroupList>();

		Query query = getCurrentSession().createQuery(
				"from UsersGroupList u where u.groupid = :groupid");

		query.setParameter("groupid", groupId);

		grpList = query.list();

		if (grpList.size() > 0)
			return grpList;

		else
			return null;
	}

	@Override
	public void saveUsersandGroup(List<UsersGroupList> userGroupList) {

		// No Implementations
	}

	private void clearOldEntries(List<String> list) {

		Session session = getCurrentSession();

		if (list != null) {
			for (Iterator<String> iterator = list.iterator(); iterator
					.hasNext();) {

				String userName = iterator.next();

				session.createQuery(
						"DELETE FROM UsersGroupList WHERE username=\'"
								+ userName + "\'").executeUpdate();

			}
		}

	}

	@Override
	public void saveUsersandGroup(List<String> userList, List<String> grpList) {

		Session session = getCurrentSession();

		if (userList != null && grpList != null) {
			clearOldEntries(userList);

			List<User> users = userService.listUsers(userList);

			List<UserGroup> groups = groupService.listGroups(grpList);

			for (Iterator<User> userIterator = users.iterator(); userIterator
					.hasNext();) {

				User user = userIterator.next();

				for (Iterator<UserGroup> groupIterator = groups.iterator(); groupIterator
						.hasNext();) {

					UserGroup userGroup = groupIterator.next();

					if (!userGroup.getUsers().contains(user)) {

						userGroup.addUserToGroup(user);
					}

					if (!user.getGroups().contains(userGroup)) {
						user.addGroupToUser(userGroup);
					}

				}
			}

			for (Iterator<UserGroup> groupIterator = groups.iterator(); groupIterator
					.hasNext();) {

				UserGroup userGroup = groupIterator.next();

				session.save(userGroup);
			}
		}
	}

}

/**
 * 
 */
package com.cti.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cti.model.UserDetail;

/**
 * @author dharshini
 *
 */
@Repository
public class UserDetailDAOEx implements UserDetailDAO {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserDetailDAO#saveUserDetail(com.cti.model.UserDetail)
	 */
	@Override
	public void saveUserDetail(UserDetail userDetail) {
		getCurrentSession().save(userDetail);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserDetailDAO#updateUserDetail(com.cti.model.UserDetail)
	 */
	@Override
	public void updateUserDetail(UserDetail userDetail) {
		getCurrentSession().update(userDetail);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserDetailDAO#removeUserDetail(java.lang.String)
	 */
	@Override
	public void removeUserDetail(String username) {
		UserDetail user = (UserDetail) getCurrentSession().get(
				UserDetail.class, username);

		if (null != user) {
			getCurrentSession().delete(user);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserDetailDAO#getUserDetailById(java.lang.String)
	 */
	@Override
	public UserDetail getUserDetailById(String username) {
		UserDetail user = (UserDetail) getCurrentSession().get(
				UserDetail.class, username);

		return user;
	}

	@Override
	public boolean isUserProfileAlreadyAvailable(String username) {
		UserDetail user = (UserDetail) getCurrentSession().get(
				UserDetail.class, username);
		if (user != null)
			return true;
		else
			return false;
	}

}

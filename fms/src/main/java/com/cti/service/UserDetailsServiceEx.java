/**
 * 
 */
package com.cti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cti.dao.UserDetailDAO;
import com.cti.model.UserDetail;

/**
 * @author dharshini
 *
 */
@Service
public class UserDetailsServiceEx implements UserDetailsService {

	@Autowired
	UserDetailDAO userDetailDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserDetailsService#saveUserDetail(com.cti.model.UserDetail
	 * )
	 */
	@Override
	public void saveUserDetail(UserDetail userDetail) {
		userDetailDAO.saveUserDetail(userDetail);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserDetailsService#updateUserDetail(com.cti.model.UserDetail
	 * )
	 */
	@Override
	public void updateUserDetail(UserDetail userDetail) {
		userDetailDAO.updateUserDetail(userDetail);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserDetailsService#removeUserDetail(java.lang.String)
	 */
	@Override
	public void removeUserDetail(String username) {
		userDetailDAO.removeUserDetail(username);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserDetailsService#getUserDetailById(java.lang.String)
	 */
	@Override
	public UserDetail getUserDetailById(String username) {

		return userDetailDAO.getUserDetailById(username);
	}

	@Override
	public boolean isUserProfileAlreadyAvailable(String username) {
		
		return userDetailDAO.isUserProfileAlreadyAvailable(username);
	}

}

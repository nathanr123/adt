/**
 * 
 */
package com.cti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cti.dao.UserAttepmtDAO;
import com.cti.model.UserAttempts;

/**
 * @author nathanr_kamal
 *
 */
@Service
public class UserAttemptsServiceEx implements UserAttemptsService {

	@Autowired
	UserAttepmtDAO userDetailsDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserAttemptsService#updateFailAttempts(java.lang.String)
	 */
	@Override
	public void updateFailAttempts(String username) {
		userDetailsDao.updateFailAttempts(username);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserAttemptsService#resetFailAttempts(java.lang.String)
	 */
	@Override
	public void resetFailAttempts(String username) {
		userDetailsDao.resetFailAttempts(username);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserAttemptsService#getUserAttempts(java.lang.String)
	 */
	@Override
	public UserAttempts getUserAttempts(String username) {

		return userDetailsDao.getUserAttempts(username);
	}

}

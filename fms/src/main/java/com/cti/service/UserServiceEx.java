/**
 * 
 */
package com.cti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cti.dao.UserDAO;
import com.cti.model.User;

/**
 * @author nathanr_kamal
 *
 */
@Service
public class UserServiceEx implements UserService {

	@Autowired
	UserDAO userDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserService#saveUser(com.cti.model.User)
	 */
	@Override
	public boolean saveUser(User user) {
		return userDAO.saveUser(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserService#updateUser(com.cti.model.User)
	 */
	@Override
	public boolean updateUser(User user) {
		return userDAO.updateUser(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserService#removeUser(java.lang.String)
	 */
	@Override
	public boolean removeUser(String username) {
		return userDAO.removeUser(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserService#getUserById(java.lang.String)
	 */
	@Override
	public User getUserById(String username) {
		return userDAO.getUserById(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserService#listUsers()
	 */
	@Override
	public List<User> listUsers() {
		return userDAO.listUsers();
	}

	@Override
	public boolean updatePassword(User user) {
		return false;// TODO Auto-generated method stub

	}

	@Override
	public List<User> listUsers(List<String> userList) {
		// TODO Auto-generated method stub
		return userDAO.listUsers(userList);
	}

}

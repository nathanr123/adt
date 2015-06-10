/**
 * 
 */
package com.cti.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.cti.model.SaveSettings;
import com.cti.model.User;
import com.cti.model.UserAttempts;
import com.cti.model.UserDetail;
import com.cti.model.UserUploads;

/**
 * @author nathanr_kamal
 *
 */
@Repository
public class UserDAOEx implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean saveUser(User user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			getCurrentSession().save(user);

			return true;

		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean updateUser(User user) {
		try {
			getCurrentSession().update(user);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public User getUserById(String username) {

		User user = (User) getCurrentSession().get(User.class, username);

		return user;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUsers() {
		return getCurrentSession().createQuery("from User").list();
	}

	@Override
	public boolean updatePassword(User user) {
		try {
			/*
			 * user.setPassword(passwordEncoder.encode(user.getPassword()));
			 * 
			 * user.setModifiedtime(new Date());
			 */
			getCurrentSession().save(user);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<User> listUsers(List<String> userList) {

		if (userList != null) {
			List<User> usersList = new ArrayList<User>();

			for (Iterator<String> iterator = userList.iterator(); iterator.hasNext();) {
				String user = (String) iterator.next();

				usersList.add(getUserById(user));

			}
			return usersList;
		} else
			return null;

	}

	@Override
	public boolean removeUser(String username) {

		return deleteAllUserRecords(username);
	}

	private String getDeleteQuery(String table, String username) {

		return String.format("DELETE FROM %s WHERE username= \'%s\'", table, username);
	}

	private List<String> getAllDeletingQueries(String username) {

		List<String> qryList = new ArrayList<String>();

		Iterator<String> it = getAllUserChildNames().iterator();

		while (it.hasNext()) {
			qryList.add(getDeleteQuery(it.next(), username));
		}

		return qryList;
	}

	private boolean deleteAllUserRecords(String username) {

		try {
			Session session = getCurrentSession();

			List<String> it = getAllDeletingQueries(username);

			for (Iterator<String> iterator = it.iterator(); iterator.hasNext();) {

				session.createQuery(iterator.next()).executeUpdate();
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Set<String> getAllUserChildNames() {

		LinkedHashSet<String> childList = new LinkedHashSet<String>();

		childList.add(UserAttempts.class.getName());

		childList.add(UserDetail.class.getName());

		childList.add(User.class.getName());

		childList.add(UserUploads.class.getName());

		return childList;
	}

	@Override
	public void saveSettings(SaveSettings saveSettings) {
		try {

			Date d = new Date();
			
			saveSettings.setCreatedtime(d);
			
			saveSettings.setModifiedtime(d);
			
			getCurrentSession().saveOrUpdate(saveSettings);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SaveSettings getSettings() {
		try{
		SaveSettings s = (SaveSettings) getCurrentSession().createQuery("from SaveSettings").list().get(0);
		
		
		if(s.getFolderpath() == null){
			s.setFolderpath("");
		}
		
		if(s.getFoldername() == null){
			s.setFoldername("");
			s.setModifiedtime(null);
		}
		
		return s;
		}catch(Exception e){
			return new SaveSettings();
		}
	}
}

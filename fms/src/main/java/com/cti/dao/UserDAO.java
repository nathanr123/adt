package com.cti.dao;

import java.util.List;

import com.cti.model.SaveSettings;
import com.cti.model.User;

public interface UserDAO {

	public boolean saveUser(User user);

	public boolean updateUser(User user);

	public boolean updatePassword(User user);

	public boolean removeUser(String username);

	public User getUserById(String username);

	public List<User> listUsers();

	public List<User> listUsers(List<String> userList);

	public void saveSettings(SaveSettings saveSettings);

	public SaveSettings getSettings();
}

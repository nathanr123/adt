/**
 * 
 */
package com.cti.dao;

import com.cti.model.UserDetail;

/**
 * @author dharshini
 *
 */
public interface UserDetailDAO {

	public void saveUserDetail(UserDetail userDetail);

	public void updateUserDetail(UserDetail userDetail);

	public boolean isUserProfileAlreadyAvailable(String username);
	
	public void removeUserDetail(String username);

	public UserDetail getUserDetailById(String username);
	
}

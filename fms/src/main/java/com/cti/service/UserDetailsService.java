/**
 * 
 */
package com.cti.service;

import org.springframework.transaction.annotation.Transactional;

import com.cti.model.UserDetail;

/**
 * @author dharshini
 *
 */
@Transactional
public interface UserDetailsService {
	
	public void saveUserDetail(UserDetail userDetail);

	public void updateUserDetail(UserDetail userDetail);

	public boolean isUserProfileAlreadyAvailable(String username);
	
	public void removeUserDetail(String username);

	public UserDetail getUserDetailById(String username);
}

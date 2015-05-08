package com.cti.dao;

import com.cti.model.UserAttempts;

public interface UserAttepmtDAO {

	void updateFailAttempts(String username);

	void resetFailAttempts(String username);
	
	UserAttempts getUserAttempts(String username);

}
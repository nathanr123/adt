package com.cti.service;

import org.springframework.transaction.annotation.Transactional;

import com.cti.model.UserAttempts;

@Transactional
public interface UserAttemptsService {

	void updateFailAttempts(String username);

	void resetFailAttempts(String username);
	
	UserAttempts getUserAttempts(String username);

}
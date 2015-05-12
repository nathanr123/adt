package com.cti.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cti.model.UserUploads;

@Transactional
public interface UserUploadService {
	
	public boolean saveUploads(UserUploads upload);

	public boolean updateUploads(UserUploads upload);

	public boolean deleteUploads(UserUploads upload);

	public List<UserUploads> listAllUploads();

	public List<UserUploads> listRequestedUploads();

	public List<UserUploads> listCanceledRequests();

	public List<UserUploads> listPendingRequests();

	public List<UserUploads> listDeletedRequests();
}

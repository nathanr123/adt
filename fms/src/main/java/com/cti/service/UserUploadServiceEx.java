/**
 * 
 */
package com.cti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cti.dao.UserUploadDAO;
import com.cti.model.UserUploads;

/**
 * @author nathanr_kamal
 *
 */
@Service
public class UserUploadServiceEx implements UserUploadService {

	@Autowired
	UserUploadDAO userUploadDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserUploadService#saveUploads(com.cti.model.UserUploads)
	 */
	@Override
	public boolean saveUploads(UserUploads upload) {
		return userUploadDAO.saveUploads(upload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserUploadService#updateUploads(com.cti.model.UserUploads
	 * )
	 */
	@Override
	public boolean updateUploads(UserUploads upload) {
		return userUploadDAO.updateUploads(upload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cti.service.UserUploadService#deleteUploads(com.cti.model.UserUploads
	 * )
	 */
	@Override
	public boolean deleteUploads(UserUploads upload) {
		return userUploadDAO.deleteUploads(upload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserUploadService#listAllUploads()
	 */
	@Override
	public List<UserUploads> listAllUploads() {
		return userUploadDAO.listAllUploads();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserUploadService#listRequestedUploads()
	 */
	@Override
	public List<UserUploads> listRequestedUploads() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserUploadService#listCanceledRequests()
	 */
	@Override
	public List<UserUploads> listCanceledRequests() {

		return userUploadDAO.listCanceledRequests();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserUploadService#listPendingRequests()
	 */
	@Override
	public List<UserUploads> listPendingRequests() {

		return userUploadDAO.listPendingRequests();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.service.UserUploadService#listDeletedRequests()
	 */
	@Override
	public List<UserUploads> listDeletedRequests() {

		return userUploadDAO.listDeletedRequests();
	}

	@Override
	public List<UserUploads> listAllUploads(String username) {
		return userUploadDAO.listAllUploads(username);
	}

}

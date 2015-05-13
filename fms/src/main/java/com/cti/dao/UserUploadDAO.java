/**
 * 
 */
package com.cti.dao;

import java.util.List;

import com.cti.model.UserUploads;

/**
 * @author nathanr_kamal
 *
 */
public interface UserUploadDAO {

	public boolean saveUploads(UserUploads upload);

	public boolean updateUploads(UserUploads upload);

	public boolean deleteUploads(UserUploads upload);

	public List<UserUploads> listAllUploads();
	
	public List<UserUploads> listAllUploads(String username);

	public List<UserUploads> listRequestedUploads();

	public List<UserUploads> listCanceledRequests();

	public List<UserUploads> listPendingRequests();

	public List<UserUploads> listDeletedRequests();
}

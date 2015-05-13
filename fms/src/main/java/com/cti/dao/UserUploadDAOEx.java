/**
 * 
 */
package com.cti.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cti.model.UserUploads;

/**
 * @author nathanr_kamal
 *
 */
@Repository
public class UserUploadDAOEx implements UserUploadDAO {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserUploadDAO#saveUploads(com.cti.model.UserUploads)
	 */
	@Override
	public boolean saveUploads(UserUploads upload) {
		getCurrentSession().save(upload);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserUploadDAO#updateUploads(com.cti.model.UserUploads)
	 */
	@Override
	public boolean updateUploads(UserUploads upload) {
		getCurrentSession().update(upload);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserUploadDAO#deleteUploads(com.cti.model.UserUploads)
	 */
	@Override
	public boolean deleteUploads(UserUploads upload) {

		getCurrentSession().delete(upload);

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserUploadDAO#listAllUploads()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserUploads> listAllUploads() {

		return getCurrentSession().createQuery("from UserUploads").list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserUploadDAO#listRequestedUploads()
	 */
	@Override
	public List<UserUploads> listRequestedUploads() {
		@SuppressWarnings("unchecked")
		List<UserUploads> grpList = getCurrentSession().createQuery("from UserUploads u where u.request=1").list();
		return grpList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserUploadDAO#listCanceledRequests()
	 */
	@Override
	public List<UserUploads> listCanceledRequests() {
		@SuppressWarnings("unchecked")
		List<UserUploads> grpList = getCurrentSession().createQuery("from UserUploads u where  u.isCanceled=1").list();
		return grpList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserUploadDAO#listPendingRequests()
	 */
	@Override
	public List<UserUploads> listPendingRequests() {
		@SuppressWarnings("unchecked")
		List<UserUploads> grpList = getCurrentSession().createQuery(
				"from UserUploads u where u.request=1 AND u.isDeleted=0 AND u.isCanceled = 0 AND u.isAccepted = 0")
				.list();
		return grpList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cti.dao.UserUploadDAO#listDeletedRequests()
	 */
	@Override
	public List<UserUploads> listDeletedRequests() {
		@SuppressWarnings("unchecked")
		List<UserUploads> grpList = getCurrentSession().createQuery("from UserUploads u where  u.isDeleted=1").list();
		return grpList;
	}

	@Override
	public List<UserUploads> listAllUploads(String username) {

		Query query = getCurrentSession().createQuery("from UserUploads u where  u.username = :username");

		query.setParameter("username", username);

		List<UserUploads> grpList = query.list();

		if (grpList.size() > 0)
			return grpList;

		else
			return null;
	}

}

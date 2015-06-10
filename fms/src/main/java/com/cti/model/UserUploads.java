/**
 * 
 */
package com.cti.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author nathanr_kamal
 *
 */
@Entity
@Table(name = "useruploads")
public class UserUploads implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8478964924269844918L;

	private String username;

	private int fileid;

	private boolean request = false;

	private boolean isCanceled = false;

	private boolean isDeleted = false;

	private boolean isAccepted = false;

	private String filepath;

	private String description = "";

	private double size;

	private Date createdtime;

	private Date modifiedtime;

	private User user;

	public UserUploads() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param username
	 * @param fileid
	 * @param request
	 * @param isCanceled
	 * @param isDeleted
	 * @param isAccepted
	 * @param filepath
	 * @param description
	 * @param size
	 * @param createdtime
	 * @param modifiedtime
	 * @param user
	 */
	public UserUploads(String username, int fileid, boolean request, boolean isCanceled, boolean isDeleted,
			boolean isAccepted, String filepath, String description, double size, Date createdtime, Date modifiedtime,
			User user) {
		super();
		this.username = username;
		this.fileid = fileid;
		this.request = request;
		this.isCanceled = isCanceled;
		this.isDeleted = isDeleted;
		this.isAccepted = isAccepted;
		this.filepath = filepath;
		this.description = description;
		this.size = size;
		this.createdtime = createdtime;
		this.modifiedtime = modifiedtime;
		this.user = user;
	}

	/**
	 * @return the username
	 */
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	@Column(name = "username", nullable = false, length = 16)
	public String getUsername() {
		return username;
	}

	/**
	 * @return the fileid
	 */
	@Id
	@Column(name = "fileid", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getFileid() {
		return fileid;
	}

	/**
	 * @return the isCanceled
	 */
	@Column(name = "isCanceled", nullable = false)
	public boolean isCanceled() {
		return isCanceled;
	}

	/**
	 * @return the isDeleted
	 */
	@Column(name = "isDeleted", nullable = false)
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @return the isAccepted
	 */
	@Column(name = "isAccepted", nullable = false)
	public boolean isAccepted() {
		return isAccepted;
	}

	/**
	 * @return the filepath
	 */
	@Column(name = "filepath", nullable = false, length = 250)
	public String getFilepath() {
		return filepath;
	}

	/**
	 * @return the createdtime
	 */
	@Column(name = "createdtime", nullable = false)
	public Date getCreatedtime() {
		return createdtime;
	}

	/**
	 * @return the modifiedtime
	 */
	@Column(name = "modifiedtime", nullable = false)
	public Date getModifiedtime() {
		return modifiedtime;
	}

	/**
	 * @return the description
	 */
	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	/**
	 * @return the user
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return user;
	}

	/**
	 * @return the request
	 */
	@Column(name = "request", nullable = false)
	public boolean isRequest() {
		return request;
	}

	/**
	 * @return the size
	 */
	@Column(name = "size", nullable = false)
	public double getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(double size) {
		this.size = size;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param fileid
	 *            the fileid to set
	 */
	public void setFileid(int fileid) {
		this.fileid = fileid;
	}

	/**
	 * @param isCanceled
	 *            the isCanceled to set
	 */
	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @param isAccepted
	 *            the isAccepted to set
	 */
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	/**
	 * @param filepath
	 *            the filepath to set
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param createdtime
	 *            the createdtime to set
	 */
	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	/**
	 * @param modifiedtime
	 *            the modifiedtime to set
	 */
	public void setModifiedtime(Date modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(boolean request) {
		this.request = request;
	}

}

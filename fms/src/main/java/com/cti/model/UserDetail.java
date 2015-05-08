package com.cti.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "rems_user_detail")
public class UserDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3148442339401956177L;

	// Variables for corresponding to DB Table

	private String username;

	private String fullname;

	private String mailid;

	private String mobileno;

	private Date createdtime;

	private Date modifiedtime;

	private User user;

	// Constructors

	public UserDetail() {

	}

	/**
	 * @param username
	 * @param fullname
	 * @param mailid
	 * @param mobileno
	 * @param createdtime
	 * @param modifiedtime
	 */
	public UserDetail(String username, String fullname, String mailid,
			String mobileno, Date createdtime, Date modifiedtime) {

		this.username = username;

		this.fullname = fullname;

		this.mailid = mailid;

		this.mobileno = mobileno;

		this.createdtime = createdtime;

		this.modifiedtime = modifiedtime;
	}

	// Getter Methods

	/**
	 * @return the username
	 */
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "rems_user"))
	@Id
	@Column(name = "username", unique = true, nullable = false, length = 16)
	public String getUsername() {
		return username;
	}

	/**
	 * @return the fullname
	 */
	@Column(name = "fullname", nullable = false, length = 35)
	public String getFullname() {
		return fullname;
	}

	/**
	 * @return the mailid
	 */
	@Column(name = "mailid", nullable = false, length = 124)
	public String getMailid() {
		return mailid;
	}

	/**
	 * @return the mobileno
	 */
	@Column(name = "mobilenumber", nullable = false, length = 15)
	public String getMobileno() {
		return mobileno;
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

	// Setter Methods

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param fullname
	 *            the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @param mailid
	 *            the mailid to set
	 */
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	/**
	 * @param mobileno
	 *            the mobileno to set
	 */
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
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
	 * @return the user
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}

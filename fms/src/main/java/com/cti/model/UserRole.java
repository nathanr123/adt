package com.cti.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rems_user_role", catalog = "rems_db")
public class UserRole {

	// Variables for corresponding to DB Table

	private String roleid;

	private String roletype;

	private Date createdtime;

	private Date modifiedtime;

	// Constructors

	/**
	 * @param roleid
	 * @param roletype
	 * @param createdtime
	 * @param modifiedtime
	 */
	public UserRole(String roleid, String roletype, Date createdtime,
			Date modifiedtime) {

		this.roleid = roleid;

		this.roletype = roletype;

		this.createdtime = createdtime;

		this.modifiedtime = modifiedtime;
	}

	// Getter Methods

	/**
	 * @return the roleid
	 */
	@Id
	@Column(name = "roleid", nullable = false, unique = true, length = 35)
	public String getRoleid() {
		return roleid;
	}

	/**
	 * @return the roletype
	 */
	@Column(name = "roletype", nullable = false, length = 35)
	public String getRoletype() {
		return roletype;
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
	 * @param roleid
	 *            the roleid to set
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	/**
	 * @param roletype
	 *            the roletype to set
	 */
	public void setRoletype(String roletype) {
		this.roletype = roletype;
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

}

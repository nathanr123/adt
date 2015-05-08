/**
 * 
 */
package com.cti.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author nathanr_kamal
 *
 */
@Entity
@Table(name = "rems_users_grouplist")
public class UsersGroupList implements Serializable {

	// Variables for corresponding to DB Table

	/**
	 * 
	 */
	private static final long serialVersionUID = -4289323341585063838L;

	private String username;

	private String groupid;

	private Date createdtime;

	private Date modifiedtime;

	// Constructors
	
	public UsersGroupList() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param username
	 * @param groupid
	 * @param createdtime
	 * @param modifiedtime
	 */
	public UsersGroupList(String username, String groupid, Date createdtime,
			Date modifiedtime) {

		this.username = username;

		this.groupid = groupid;

		this.createdtime = createdtime;

		this.modifiedtime = modifiedtime;
	}

	// Getter Methods

	/**
	 * @return the username
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "rems_user"))
	@Column(name = "username", nullable = false, length = 16)
	public String getUsername() {
		return username;
	}

	/**
	 * @return the groupid
	 */
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "rems_group"))
	@Column(name = "groupid", nullable = false, length = 10)
	public String getGroupid() {
		return groupid;
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
	 * @param groupid
	 *            the groupid to set
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
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

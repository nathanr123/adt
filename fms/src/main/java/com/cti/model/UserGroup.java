package com.cti.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rems_group")
public class UserGroup implements Serializable {

	// Variables for corresponding to DB Table

	/**
	 * 
	 */
	private static final long serialVersionUID = 7996266639669172483L;

	private String groupid;

	private String groupname;

	private int priority;

	private Date createdtime;

	private Date modifiedtime;

	private Set<User> users = new HashSet<User>();

	private Set<UserGroupPermission> userGroupPermissions = new HashSet<UserGroupPermission>();

	public UserGroup() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param groupid
	 * @param groupname
	 * @param priority
	 * @param createdtime
	 * @param modifiedtime
	 */
	public UserGroup(String groupid, String groupname, int priority,
			Date createdtime, Date modifiedtime) {

		this.groupid = groupid;

		this.groupname = groupname;

		this.priority = priority;

		this.createdtime = createdtime;

		this.modifiedtime = modifiedtime;
	}

	/**
	 * @return the groupid
	 */
	@Id
	@Column(name = "groupid", nullable = false, unique = true, length = 10)
	public String getGroupid() {
		return groupid;
	}

	/**
	 * @return the groupname
	 */
	@Column(name = "groupname", nullable = false, length = 35)
	public String getGroupname() {
		return groupname;
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
	 * @param groupid
	 *            the groupid to set
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	/**
	 * @param groupname
	 *            the groupname to set
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
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
	 * @return the users
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "rems_users_grouplist", joinColumns = @JoinColumn(name = "groupid"), inverseJoinColumns = @JoinColumn(name = "username"))
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * @return the priority
	 */
	@Column(name = "priority", nullable = false)
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void addUserToGroup(User usr) {
		this.users.add(usr);
	}

	/**
	 * @return the userGroupPermissions
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groups", cascade = CascadeType.ALL)
	public Set<UserGroupPermission> getUserGroupPermissions() {
		return userGroupPermissions;
	}

	/**
	 * @param userGroupPermissions
	 *            the userGroupPermissions to set
	 */
	public void setUserGroupPermissions(
			Set<UserGroupPermission> userGroupPermissions) {
		this.userGroupPermissions = userGroupPermissions;
	}

}

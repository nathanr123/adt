package com.cti.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rems_group_permission")
public class UserGroupPermission {

	// Variables for corresponding to DB Table

	private int permissionid;

	private String groupid;

	private String component;

	private int canread;

	private int cancreate;

	private int canmodify;

	private int candelete;

	private Date createdtime;

	private Date modifiedtime;

	private UserGroup groups;

	// Constructors

	public UserGroupPermission() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param permissionid
	 * @param groupid
	 * @param component
	 * @param canread
	 * @param cancreate
	 * @param canmodify
	 * @param candelete
	 * @param createdtime
	 * @param modifiedtime
	 * @param groups
	 */
	public UserGroupPermission(int permissionid, String groupid,
			String component, int canread, int cancreate, int canmodify,
			int candelete, Date createdtime, Date modifiedtime, UserGroup groups) {
		this.permissionid = permissionid;
		this.groupid = groupid;
		this.component = component;
		this.canread = canread;
		this.cancreate = cancreate;
		this.canmodify = canmodify;
		this.candelete = candelete;
		this.createdtime = createdtime;
		this.modifiedtime = modifiedtime;
		this.groups = groups;
	}

	public UserGroupPermission(String groupid, String component,
			int[] permissions, Date time) {

		this.groupid = groupid;

		this.component = component;

		this.cancreate = permissions[0];

		this.canmodify = permissions[1];

		this.candelete = permissions[2];

		this.canread = permissions[3];

		this.createdtime = time;

		this.modifiedtime = time;
	}

	// Getter Methods

	/**
	 * @return the permissionid
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "permissionid", unique = true, nullable = false)
	public int getPermissionid() {
		return permissionid;
	}

	/**
	 * @param permissionid
	 *            the permissionid to set
	 */
	public void setPermissionid(int permissionid) {
		this.permissionid = permissionid;
	}

	/**
	 * @return the groupid
	 */
	public String getGroupid() {
		return groupid;
	}

	/**
	 * @return the component
	 */
	@Column(name = "component", nullable = false, length = 35)
	public String getComponent() {
		return component;
	}

	/**
	 * @return the canread
	 */
	@Column(name = "canread", nullable = false)
	public int getCanread() {
		return canread;
	}

	/**
	 * @return the cancreate
	 */
	@Column(name = "cancreate", nullable = false)
	public int getCancreate() {
		return cancreate;
	}

	/**
	 * @return the canmodify
	 */
	@Column(name = "canmodify", nullable = false)
	public int getCanmodify() {
		return canmodify;
	}

	/**
	 * @return the candelete
	 */
	@Column(name = "candelete", nullable = false)
	public int getCandelete() {
		return candelete;
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
	 * @param groupid
	 *            the groupid to set
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	/**
	 * @param component
	 *            the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
	}

	/**
	 * @param canread
	 *            the canread to set
	 */
	public void setCanread(int canread) {
		this.canread = canread;
	}

	/**
	 * @param cancreate
	 *            the cancreate to set
	 */
	public void setCancreate(int cancreate) {
		this.cancreate = cancreate;
	}

	/**
	 * @param canmodify
	 *            the canmodify to set
	 */
	public void setCanmodify(int canmodify) {
		this.canmodify = canmodify;
	}

	/**
	 * @param candelete
	 *            the candelete to set
	 */
	public void setCandelete(int candelete) {
		this.candelete = candelete;
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
	 * @return the groups
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupid", nullable = false, insertable = false, updatable = false)
	public UserGroup getGroups() {
		return groups;
	}

	/**
	 * @param groups
	 *            the groups to set
	 */
	public void setGroups(UserGroup groups) {
		this.groups = groups;
	}

}

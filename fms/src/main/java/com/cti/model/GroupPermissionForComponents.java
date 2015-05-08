/**
 * 
 */
package com.cti.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nathanr_kamal
 *
 */
public class GroupPermissionForComponents implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4789101217338998238L;

	private List<String> fromGroup = new ArrayList<String>();

	private List<String> toGroup = new ArrayList<String>();

	private List<String> fromComponent = new ArrayList<String>();

	private List<String> toComponent = new ArrayList<String>();

	private List<String> permissions = new ArrayList<String>();

	public GroupPermissionForComponents() {
	}

	/**
	 * @return the fromGroup
	 */
	public List<String> getFromGroup() {
		return fromGroup;
	}

	/**
	 * @param fromGroup
	 *            the fromGroup to set
	 */
	public void setFromGroup(List<String> fromGroup) {
		this.fromGroup = fromGroup;
	}

	/**
	 * @return the toGroup
	 */
	public List<String> getToGroup() {
		return toGroup;
	}

	/**
	 * @param toGroup
	 *            the toGroup to set
	 */
	public void setToGroup(List<String> toGroup) {
		this.toGroup = toGroup;
	}

	/**
	 * @return the fromComponent
	 */
	public List<String> getFromComponent() {
		return fromComponent;
	}

	/**
	 * @param fromComponent
	 *            the fromComponent to set
	 */
	public void setFromComponent(List<String> fromComponent) {
		this.fromComponent = fromComponent;
	}

	/**
	 * @return the toComponent
	 */
	public List<String> getToComponent() {
		return toComponent;
	}

	/**
	 * @param toComponent
	 *            the toComponent to set
	 */
	public void setToComponent(List<String> toComponent) {
		this.toComponent = toComponent;
	}

	/**
	 * @return the permissions
	 */
	public List<String> getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions
	 *            the permissions to set
	 */
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

}

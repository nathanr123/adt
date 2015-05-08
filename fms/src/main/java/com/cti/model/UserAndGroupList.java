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
public class UserAndGroupList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7835850558142683329L;

	private List<String> fromGroup = new ArrayList<String>();

	private List<String> toGroup = new ArrayList<String>();

	private List<String> fromUser = new ArrayList<String>();

	private List<String> toUser = new ArrayList<String>();

	public UserAndGroupList() {
		// TODO Auto-generated constructor stub
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
	 * @return the fromUser
	 */
	public List<String> getFromUser() {
		return fromUser;
	}

	/**
	 * @param fromUser
	 *            the fromUser to set
	 */
	public void setFromUser(List<String> fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * @return the toUser
	 */
	public List<String> getToUser() {
		return toUser;
	}

	/**
	 * @param toUser
	 *            the toUser to set
	 */
	public void setToUser(List<String> toUser) {
		this.toUser = toUser;
	}

}

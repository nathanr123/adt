/**
 * 
 */
package com.cti.model;

import java.io.Serializable;

/**
 * @author nathanr_kamal
 *
 */
public class ChangePassword implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1950245345642700390L;

	private String username;

	private String oldPassword;

	private String newPassword;

	private String confirmPassword;

	public ChangePassword() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isOldPasswordMatches(String oldPwd) {
		return this.oldPassword.equals(oldPwd);
	}

}

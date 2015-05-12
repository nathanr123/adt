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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "userattempts")
public class UserAttempts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7219073064037414531L;

	private String username;

	private int attempts;

	private Date lastModified;

	private String formattedDate;

	private User userAttempted;

	// Constructors

	public UserAttempts() {

	}

	@Id
	@Column(name = "username", nullable = false, length = 16)
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "attempts", nullable = false)
	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	@Column(name = "lastModified", nullable = false)
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the formattedDate
	 */
	@Transient
	public String getFormattedDate() {
		return formattedDate;
	}

	/**
	 * @param formattedDate
	 *            the formattedDate to set
	 */
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	/**
	 * @return the userAttempted
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public User getUserAttempted() {
		return userAttempted;
	}

	/**
	 * @param userAttempted
	 *            the userAttempted to set
	 */
	public void setUserAttempted(User userAttempted) {
		this.userAttempted = userAttempted;
	}

}
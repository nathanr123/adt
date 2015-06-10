package com.cti.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "settings")
public class SaveSettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2259162875684245219L;

	private int id=0;

	private String foldername = "";

	private String folderpath = "";

	private Date createdtime;

	private Date modifiedtime;

	public SaveSettings() {
		Date d = new Date();
		
		setCreatedtime(d);
		
		setModifiedtime(d);
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "id", nullable = false, unique = true, length = 16)
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the foldername
	 */
	@Column(name = "foldername", nullable = false, unique = true, length = 16)
	public String getFoldername() {
		return foldername;
	}

	/**
	 * @param foldername
	 *            the foldername to set
	 */
	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}

	/**
	 * @return the folderpath
	 */
	@Column(name = "folderpath", nullable = false, unique = true, length = 16)
	public String getFolderpath() {
		return folderpath;
	}

	/**
	 * @param folderpath
	 *            the folderpath to set
	 */
	public void setFolderpath(String folderpath) {
		this.folderpath = folderpath;
	}

	/**
	 * @return the createdtime
	 */
	@Column(name = "createdtime", nullable = false, unique = true, length = 16)
	public Date getCreatedtime() {
		return createdtime;
	}

	/**
	 * @param createdtime
	 *            the createdtime to set
	 */
	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	/**
	 * @return the modifiedtime
	 */
	@Column(name = "modifiedtime", nullable = false, unique = true, length = 16)
	public Date getModifiedtime() {
		return modifiedtime;
	}

	/**
	 * @param modifiedtime
	 *            the modifiedtime to set
	 */
	public void setModifiedtime(Date modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

}

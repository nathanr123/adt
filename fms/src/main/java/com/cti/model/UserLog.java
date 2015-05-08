package com.cti.model;

import java.util.Date;

public class UserLog {

	// Variables for corresponding to DB Table

	private int logid;

	private String owner;

	private String ipaddress;

	private String loggroup;

	private String logdescription;

	private Date createdtime;

	// Constructors

	/**
	 * @param logid
	 * @param owner
	 * @param ipaddress
	 * @param loggroup
	 * @param logdescription
	 * @param createdtime
	 */
	public UserLog(int logid, String owner, String ipaddress, String loggroup,
			String logdescription, Date createdtime) {

		this.logid = logid;

		this.owner = owner;

		this.ipaddress = ipaddress;

		this.loggroup = loggroup;

		this.logdescription = logdescription;

		this.createdtime = createdtime;
	}

	// Getter Methods

	/**
	 * @return the logid
	 */
	public int getLogid() {
		return logid;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the ipaddress
	 */
	public String getIpaddress() {
		return ipaddress;
	}

	/**
	 * @return the loggroup
	 */
	public String getLoggroup() {
		return loggroup;
	}

	/**
	 * @return the logdescription
	 */
	public String getLogdescription() {
		return logdescription;
	}

	/**
	 * @return the createdtime
	 */
	public Date getCreatedtime() {
		return createdtime;
	}

	// Setter Methods

	/**
	 * @param logid
	 *            the logid to set
	 */
	public void setLogid(int logid) {
		this.logid = logid;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @param ipaddress
	 *            the ipaddress to set
	 */
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	/**
	 * @param loggroup
	 *            the loggroup to set
	 */
	public void setLoggroup(String loggroup) {
		this.loggroup = loggroup;
	}

	/**
	 * @param logdescription
	 *            the logdescription to set
	 */
	public void setLogdescription(String logdescription) {
		this.logdescription = logdescription;
	}

	/**
	 * @param createdtime
	 *            the createdtime to set
	 */
	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

}

package com.cti.model;

import java.util.Date;

public class Port {

	// Variables for corresponding to DB Table

	private String portid;

	private String switchid;

	private String portname;

	private int portnumber;

	private String portstatus;

	private String porttype;

	private Date createdtime;

	private Date modifiedtime;

	// Constructors

	/**
	 * @param portid
	 * @param switchid
	 * @param portname
	 * @param portnumber
	 * @param portstatus
	 * @param porttype
	 * @param createdtime
	 * @param modifiedtime
	 */
	public Port(String portid, String switchid, String portname,
			int portnumber, String portstatus, String porttype,
			Date createdtime, Date modifiedtime) {

		this.portid = portid;

		this.switchid = switchid;

		this.portname = portname;

		this.portnumber = portnumber;

		this.portstatus = portstatus;

		this.porttype = porttype;

		this.createdtime = createdtime;

		this.modifiedtime = modifiedtime;
	}

	// Getter Methods

	/**
	 * @return the portid
	 */
	public String getPortid() {
		return portid;
	}

	/**
	 * @return the switchid
	 */
	public String getSwitchid() {
		return switchid;
	}

	/**
	 * @return the portname
	 */
	public String getPortname() {
		return portname;
	}

	/**
	 * @return the portnumber
	 */
	public int getPortnumber() {
		return portnumber;
	}

	/**
	 * @return the portstatus
	 */
	public String getPortstatus() {
		return portstatus;
	}

	/**
	 * @return the porttype
	 */
	public String getPorttype() {
		return porttype;
	}

	/**
	 * @return the createdtime
	 */
	public Date getCreatedtime() {
		return createdtime;
	}

	/**
	 * @return the modifiedtime
	 */
	public Date getModifiedtime() {
		return modifiedtime;
	}

	// Setter Methods

	/**
	 * @param portid
	 *            the portid to set
	 */
	public void setPortid(String portid) {
		this.portid = portid;
	}

	/**
	 * @param switchid
	 *            the switchid to set
	 */
	public void setSwitchid(String switchid) {
		this.switchid = switchid;
	}

	/**
	 * @param portname
	 *            the portname to set
	 */
	public void setPortname(String portname) {
		this.portname = portname;
	}

	/**
	 * @param portnumber
	 *            the portnumber to set
	 */
	public void setPortnumber(int portnumber) {
		this.portnumber = portnumber;
	}

	/**
	 * @param portstatus
	 *            the portstatus to set
	 */
	public void setPortstatus(String portstatus) {
		this.portstatus = portstatus;
	}

	/**
	 * @param porttype
	 *            the porttype to set
	 */
	public void setPorttype(String porttype) {
		this.porttype = porttype;
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

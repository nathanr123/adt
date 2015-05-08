package com.cti.model;

import java.util.Date;

public class Switch {

	// Variables for corresponding to DB Table

	private String switchid;

	private String switchname;

	private String switchip;

	private int switchport;

	private String modelname;

	private String modelnumber;

	private String serialnumber;

	private int totalnoofports;

	private int noofingress;

	private int noofegress;

	private Date createdtime;

	private Date modifiedtime;

	// Constructors

	/**
	 * @param switchid
	 * @param switchname
	 * @param switchip
	 * @param switchport
	 * @param modelname
	 * @param modelnumber
	 * @param serialnumber
	 * @param totalnoofports
	 * @param noofingress
	 * @param noofegress
	 * @param createdtime
	 * @param modifiedtime
	 */
	public Switch(String switchid, String switchname, String switchip,
			int switchport, String modelname, String modelnumber,
			String serialnumber, int totalnoofports, int noofingress,
			int noofegress, Date createdtime, Date modifiedtime) {

		this.switchid = switchid;

		this.switchname = switchname;

		this.switchip = switchip;

		this.switchport = switchport;

		this.modelname = modelname;

		this.modelnumber = modelnumber;

		this.serialnumber = serialnumber;

		this.totalnoofports = totalnoofports;

		this.noofingress = noofingress;

		this.noofegress = noofegress;

		this.createdtime = createdtime;

		this.modifiedtime = modifiedtime;
	}

	// Getter Methods

	/**
	 * @return the switchid
	 */
	public String getSwitchid() {
		return switchid;
	}

	/**
	 * @return the switchname
	 */
	public String getSwitchname() {
		return switchname;
	}

	/**
	 * @return the switchip
	 */
	public String getSwitchip() {
		return switchip;
	}

	/**
	 * @return the switchport
	 */
	public int getSwitchport() {
		return switchport;
	}

	/**
	 * @return the modelname
	 */
	public String getModelname() {
		return modelname;
	}

	/**
	 * @return the modelnumber
	 */
	public String getModelnumber() {
		return modelnumber;
	}

	/**
	 * @return the serialnumber
	 */
	public String getSerialnumber() {
		return serialnumber;
	}

	/**
	 * @return the totalnoofports
	 */
	public int getTotalnoofports() {
		return totalnoofports;
	}

	/**
	 * @return the noofingress
	 */
	public int getNoofingress() {
		return noofingress;
	}

	/**
	 * @return the noofegress
	 */
	public int getNoofegress() {
		return noofegress;
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
	 * @param switchid
	 *            the switchid to set
	 */
	public void setSwitchid(String switchid) {
		this.switchid = switchid;
	}

	/**
	 * @param switchname
	 *            the switchname to set
	 */
	public void setSwitchname(String switchname) {
		this.switchname = switchname;
	}

	/**
	 * @param switchip
	 *            the switchip to set
	 */
	public void setSwitchip(String switchip) {
		this.switchip = switchip;
	}

	/**
	 * @param switchport
	 *            the switchport to set
	 */
	public void setSwitchport(int switchport) {
		this.switchport = switchport;
	}

	/**
	 * @param modelname
	 *            the modelname to set
	 */
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	/**
	 * @param modelnumber
	 *            the modelnumber to set
	 */
	public void setModelnumber(String modelnumber) {
		this.modelnumber = modelnumber;
	}

	/**
	 * @param serialnumber
	 *            the serialnumber to set
	 */
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	/**
	 * @param totalnoofports
	 *            the totalnoofports to set
	 */
	public void setTotalnoofports(int totalnoofports) {
		this.totalnoofports = totalnoofports;
	}

	/**
	 * @param noofingress
	 *            the noofingress to set
	 */
	public void setNoofingress(int noofingress) {
		this.noofingress = noofingress;
	}

	/**
	 * @param noofegress
	 *            the noofegress to set
	 */
	public void setNoofegress(int noofegress) {
		this.noofegress = noofegress;
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

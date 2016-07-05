package com.cti.vpx.model;

import java.util.ArrayList;
import java.util.List;

public class NWInterface {
	String name;

	boolean isEnabled;

	boolean isConnected;

	boolean isDedicated;

	boolean isDHCPEnabled;
	
	List<String> ipAddresses = new ArrayList<String>();

	String gateWay;

	String subnet;

	String DisplayName;

	public NWInterface() {
	}

	/**
	 * @param name
	 * @param isEnabled
	 * @param isConnected
	 * @param isDedicated
	 * @param ipAddresses
	 * @param gateWay
	 * @param subnet
	 */
	public NWInterface(String name, boolean isEnabled, boolean isConnected, boolean isDedicated,
			List<String> ipAddresses, String gateWay, String subnet) {
		super();
		this.name = name;
		this.isEnabled = isEnabled;
		this.isConnected = isConnected;
		this.isDedicated = isDedicated;
		this.ipAddresses = ipAddresses;
		this.gateWay = gateWay;
		this.subnet = subnet;
	}

	/**
	 * @param name
	 * @param isEnabled
	 * @param isConnected
	 * @param isDedicated
	 * @param ipAddresses
	 * @param gateWay
	 * @param subnet
	 */
	public NWInterface(String name, boolean isEnabled, boolean isConnected, boolean isDedicated, String ipAddresse,
			String gateWay, String subnet) {
		super();
		this.name = name;
		this.isEnabled = isEnabled;
		this.isConnected = isConnected;
		this.isDedicated = isDedicated;
		this.gateWay = gateWay;
		this.subnet = subnet;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the isConnected
	 */
	public boolean isConnected() {
		return isConnected;
	}

	/**
	 * @param isConnected
	 *            the isConnected to set
	 */
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	/**
	 * @return the isDedicated
	 */
	public boolean isDedicated() {
		return isDedicated;
	}

	/**
	 * @param isDedicated
	 *            the isDedicated to set
	 */
	public void setDedicated(boolean isDedicated) {
		this.isDedicated = isDedicated;
	}

	/**
	 * @return the ipAddresses
	 */
	public List<String> getIpAddresses() {
		return ipAddresses;
	}

	/**
	 * @param ipAddresses
	 *            the ipAddresses to set
	 */
	public void setIpAddresses(List<String> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	/**
	 * @return the gateWay
	 */
	public String getGateWay() {
		return gateWay;
	}

	/**
	 * @param gateWay
	 *            the gateWay to set
	 */
	public void setGateWay(String gateWay) {
		this.gateWay = gateWay;
	}

	/**
	 * @return the subnet
	 */
	public String getSubnet() {
		return subnet;
	}

	/**
	 * @param subnet
	 *            the subnet to set
	 */
	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}

	public void addIPAddress(String ip) {
		ipAddresses.add(ip);
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public boolean isDHCPEnabled() {
		return isDHCPEnabled;
	}

	public void setDHCPEnabled(boolean isDHCPEnabled) {
		this.isDHCPEnabled = isDHCPEnabled;
	}

}

package com.cti.vpx.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Processor implements VPX {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6214146807961201313L;

	private String iP_Addresses;

	private String name;

	private String msg;

	private long responseTime;

	private long upTime = 0;

	public Processor() {

	}

	public Processor(String iP_Addresses, long responsedTime, String msg) {

		super();

		this.iP_Addresses = iP_Addresses;

		this.responseTime = responsedTime;

		if (msg.startsWith("P2")) {

			name = "(P2020)" + iP_Addresses;

		} else if (msg.startsWith("D1")) {

			name = "(DSP1)" + iP_Addresses;

		} else if (msg.startsWith("D2")) {

			name = "(DSP2)" + iP_Addresses;

		}

		if (this.upTime == 0) {

			upTime = responsedTime;
		}

		this.msg = msg;
	}

	public String getiP_Addresses() {
		return iP_Addresses;
	}

	public void setiP_Addresses(String iP_Addresses) {
		this.iP_Addresses = iP_Addresses;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {

		this.responseTime = responseTime;

		if (this.upTime == 0) {

			upTime = responseTime;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getUpTime() {
		return upTime;
	}

	public void setUpTime(long upTime) {
		this.upTime = upTime;
	}
	
	

}

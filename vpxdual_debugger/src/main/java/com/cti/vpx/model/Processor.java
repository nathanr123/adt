package com.cti.vpx.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Processor implements VPX {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6214146807961201313L;

	private int ID;

	private String name;

	private String iP_Addresses;

	public static final int PORTNO = 12345;

	public static final int UDP_PORTNO = 12346;

	private PROCESSOR_LIST processorType;

	public Processor() {

	}

	public Processor(String ipAddress, PROCESSOR_LIST pType) {
		this.iP_Addresses = ipAddress;

		this.processorType = pType;

		setName(pType);
	}

	public Processor(PROCESSOR_LIST pType) {

		this.processorType = pType;

		setName(pType);
	}

	public int getID() {
		return ID;
	}

	@XmlElement
	public void setID(int iD) {
		ID = iD;
	}

	public PROCESSOR_LIST getProcessorType() {
		return processorType;
	}

	@XmlElement
	public void setProcessorType(PROCESSOR_LIST processorType) {
		this.processorType = processorType;
		setName(processorType);
	}

	public int getPortno() {
		return PORTNO;
	}

	public static int getUdpPortno() {
		return UDP_PORTNO;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getiP_Addresses() {
		return iP_Addresses;
	}

	@XmlElement
	public void setiP_Addresses(String iP_Addresses) {
		this.iP_Addresses = iP_Addresses;
	}

	public void setName(PROCESSOR_LIST pType) {
		if (pType == PROCESSOR_LIST.PROCESSOR_DSP1) {
			this.name = "DSP - 1";
			this.ID = 0;
		} else if (pType == PROCESSOR_LIST.PROCESSOR_DSP2) {
			this.name = "DSP - 2";
			this.ID = 1;
		} else if (pType == PROCESSOR_LIST.PROCESSOR_P2020) {
			this.name = "P2020";
			this.ID = 2;
		}
	}
}

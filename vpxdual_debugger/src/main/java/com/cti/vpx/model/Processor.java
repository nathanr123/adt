package com.cti.vpx.model;

import java.util.ArrayList;
import java.util.List;

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

	private List<String> iP_Addresses;

	public static final int PORTNO = 12345;

	private List<Core> cores;

	private PROCESSOR_TYPE processorType;
	
	public Processor() {
	
	}

	public Processor(PROCESSOR_TYPE pType) {

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

	public List<String> getiP_Addresses() {
		return iP_Addresses;
	}

	@XmlElement
	public void setiP_Addresses(List<String> iP_Addresses) {
		this.iP_Addresses = iP_Addresses;
	}

	public List<Core> getCores() {
		return cores;
	}

	@XmlElement
	public void setCores(List<Core> cores) {
		this.cores = cores;
	}

	public PROCESSOR_TYPE getProcessorType() {
		return processorType;
	}

	@XmlElement
	public void setProcessorType(PROCESSOR_TYPE processorType) {
		this.processorType = processorType;
	}

	public static int getPortno() {
		return PORTNO;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public void setName(PROCESSOR_TYPE pType) {
		if (pType == PROCESSOR_TYPE.DSP1) {
			this.name = "DSP - 1";
			this.ID = 0;
		} else if (pType == PROCESSOR_TYPE.DSP2) {
			this.name = "DSP - 2";
			this.ID = 1;
		} else if (pType == PROCESSOR_TYPE.P2020) {
			this.name = "P2020";
			this.ID = 2;
		}
	}

	public void addIPAddress(String ipAddress) {
		if (iP_Addresses == null) {
			iP_Addresses = new ArrayList<String>();
		}

		iP_Addresses.add(ipAddress);

	}

	public void addCore(Core core) {
		if (cores == null) {
			cores = new ArrayList<Core>();
		}

		cores.add(core);

	}

}

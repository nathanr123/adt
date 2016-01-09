package com.cti.vpx.model;

import java.io.Serializable;

import javax.swing.JLabel;

public class VPXNWPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7053770860344811258L;

	private int pktNo;

	private String recievedTime;

	private String sourceIP;

	private String destIP;

	private String protocol;

	private int port;

	private int length;

	private JLabel info;

	private byte[] bytes;

	public VPXNWPacket() {

	}

	public VPXNWPacket(int pktNo, String recievedTime, String sourceIP, String destIP, String protocol, int port,
			int length, JLabel info, byte[] byts) {

		super();

		this.pktNo = pktNo;

		this.recievedTime = recievedTime;

		this.sourceIP = sourceIP;

		this.destIP = destIP;

		this.protocol = protocol;

		this.port = port;

		this.length = length;

		this.info = info;

		this.bytes = byts;
	}

	public int getPktNo() {
		return pktNo;
	}

	public void setPktNo(int pktNo) {
		this.pktNo = pktNo;
	}

	public String getRecievedTime() {
		return recievedTime;
	}

	public void setRecievedTime(String recievedTime) {
		this.recievedTime = recievedTime;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getDestIP() {
		return destIP;
	}

	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public JLabel getInfo() {
		return info;
	}

	public void setInfo(JLabel info) {
		this.info = info;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}

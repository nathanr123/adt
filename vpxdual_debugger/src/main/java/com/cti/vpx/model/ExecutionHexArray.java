package com.cti.vpx.model;

import java.io.Serializable;

public class ExecutionHexArray implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8555131571507032829L;

	private String fileName;

	private byte[] hexArray;

	public ExecutionHexArray(String filename, byte[] byteArray) {

		this.fileName = filename;

		this.hexArray = byteArray;

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getHexArray() {
		return hexArray;
	}

	public void setHexArray(byte[] hexArray) {
		this.hexArray = hexArray;
	}

}

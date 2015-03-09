/**
 * 
 */
package com.cti.vpx.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abi_Achu
 *
 */
@XmlRootElement
public class Core implements VPX {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2902955762681036239L;

	private String name;

	private int ID;

	private int port;

	public Core() {

	}

	public Core(int iD, int port) {
		super();
		this.name = this.getClass().getSimpleName() + " : " + iD;
		ID = iD;
		this.port = port;
	}

	public Core(String name, int iD, int port) {
		super();
		this.name = name;
		ID = iD;
		this.port = port;
	}

	public int getID() {
		return ID;
	}

	@XmlElement
	public void setID(int iD) {
		ID = iD;
	}

	public int getPort() {
		return port;
	}

	@XmlElement
	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

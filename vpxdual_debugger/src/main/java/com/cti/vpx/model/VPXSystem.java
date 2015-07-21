package com.cti.vpx.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class VPXSystem implements VPX {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1422619400056758867L;

	private String name;

	private List<VPXSubSystem> subsystem = new ArrayList<VPXSubSystem>();

	private List<Processor> unListed = new ArrayList<Processor>();

	public VPXSystem() {
		this.name = this.getClass().getSimpleName();
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the subsystem
	 */
	public List<VPXSubSystem> getSubsystem() {
		return subsystem;
	}

	/**
	 * @param subsystem
	 *            the subsystem to set
	 */
	@XmlElement
	public void setSubsystem(List<VPXSubSystem> subsystem) {
		this.subsystem = subsystem;
	}

	public List<Processor> getUnListed() {
		return unListed;
	}

	@XmlTransient
	public void setUnListed(List<Processor> unListed) {
		this.unListed = unListed;
	}

	public void addInUnListed(String ip, long duration, String msg) {

		unListed.add(new Processor(ip, duration, msg));
	}
}

package com.cti.vpx.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Slot implements VPX {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2300947795323018962L;

	private int ID;

	private String name;

	private String model;

	private List<Processor> processors;

	public Slot() {

	}

	public Slot(int iD) {
		super();
		ID = iD;
		this.name = this.getClass().getSimpleName() + " : " + iD;
	}

	public Slot(int iD, String model, List<Processor> processors) {
		super();
		ID = iD;
		this.name = this.getClass().getSimpleName() + " : " + iD;
		this.model = model;
		this.processors = processors;
	}

	public int getID() {
		return ID;
	}

	@XmlElement
	public void setID(int iD) {
		ID = iD;
	}

	public String getModel() {
		return model;
	}

	@XmlElement
	public void setModel(String model) {
		this.model = model;
	}

	public List<Processor> getProcessors() {
		return processors;
	}

	@XmlElement
	public void setProcessors(List<Processor> processors) {
		this.processors = processors;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public void addProcessor(Processor porcessor) {
		if (processors == null) {
			processors = new ArrayList<Processor>();
		}

		processors.add(porcessor);
	}

}

package com.cti.vpx.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cage implements VPXSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1422619400056758867L;

	/**
	 * 
	 */
	

	private int ID;

	private String name;

	private List<Slot> slots;

	public Cage() {
		this.name = "System";
	}

	public Cage(int iD, List<Slot> slots) {
		super();

		this.name = "System";
		ID = iD;
		this.slots = slots;
	}

	public int getID() {
		return ID;
	}

	@XmlElement
	public void setID(int iD) {
		ID = iD;
	}

	public List<Slot> getSlots() {
		return slots;
	}

	@XmlElement
	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public void addSlot(Slot slot) {
		if (slots == null) {
			slots = new ArrayList<Slot>();
		}

		slots.add(slot);
	}
}

package com.cti.vpx.model;

import javax.xml.bind.annotation.XmlTransient;

public class VPXSubSystem implements VPX, Comparable<VPXSubSystem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -695611620747305080L;

	private int id;

	private String subSystem;

	private String ipP2020;

	private String ipDSP1;

	private String ipDSP2;

	private long p2020ResponseTime;

	private long dsp1ResponseTime;

	private long dsp2ResponseTime;

	public VPXSubSystem() {
		// TODO Auto-generated constructor stub
	}

	public VPXSubSystem(int id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param subSystem
	 * @param ipP2020
	 * @param ipDSP1
	 * @param ipDSP2
	 */
	public VPXSubSystem(int id, String subSystem, String ipP2020, String ipDSP1, String ipDSP2) {

		super();

		this.id = id;

		this.subSystem = subSystem;

		this.ipP2020 = ipP2020;

		this.ipDSP1 = ipDSP1;

		this.ipDSP2 = ipDSP2;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the subSystem
	 */
	public String getSubSystem() {
		return subSystem;
	}

	/**
	 * @param subSystem
	 *            the subSystem to set
	 */
	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	/**
	 * @return the ipP2020
	 */
	public String getIpP2020() {
		return ipP2020;
	}

	/**
	 * @param ipP2020
	 *            the ipP2020 to set
	 */
	public void setIpP2020(String ipP2020) {
		this.ipP2020 = ipP2020;
	}

	/**
	 * @return the ipDSP1
	 */
	public String getIpDSP1() {
		return ipDSP1;
	}

	/**
	 * @param ipDSP1
	 *            the ipDSP1 to set
	 */
	public void setIpDSP1(String ipDSP1) {
		this.ipDSP1 = ipDSP1;
	}

	/**
	 * @return the ipDSP2
	 */
	public String getIpDSP2() {
		return ipDSP2;
	}

	public String getP2020Name() {
		return "(P2020)" + ipP2020;
	}

	public String getDSP1Name() {
		return "(DSP1)" + ipDSP1;
	}

	public String getDSP2Name() {
		return "(DSP2)" + ipDSP2;
	}

	/**
	 * @param ipDSP2
	 *            the ipDSP2 to set
	 */
	public void setIpDSP2(String ipDSP2) {
		this.ipDSP2 = ipDSP2;
	}

	/**
	 * @return the p2020ResponseTime
	 */
	public long getP2020ResponseTime() {
		return p2020ResponseTime;
	}

	/**
	 * @param p2020ResponseTime
	 *            the p2020ResponseTime to set
	 */
	@XmlTransient
	public void setP2020ResponseTime(long p2020ResponseTime) {
		this.p2020ResponseTime = p2020ResponseTime;
	}

	/**
	 * @return the dsp1ResponseTime
	 */
	public long getDsp1ResponseTime() {
		return dsp1ResponseTime;
	}

	/**
	 * @param dsp1ResponseTime
	 *            the dsp1ResponseTime to set
	 */
	@XmlTransient
	public void setDsp1ResponseTime(long dsp1ResponseTime) {
		this.dsp1ResponseTime = dsp1ResponseTime;
	}

	/**
	 * @return the dsp2ResponseTime
	 */
	public long getDsp2ResponseTime() {
		return dsp2ResponseTime;
	}

	/**
	 * @param dsp2ResponseTime
	 *            the dsp2ResponseTime to set
	 */
	@XmlTransient
	public void setDsp2ResponseTime(long dsp2ResponseTime) {
		this.dsp2ResponseTime = dsp2ResponseTime;
	}

	@Override
	public int compareTo(VPXSubSystem o) {

		int compareId = o.getId();

		return this.id - compareId;
	}

	@Override
	public boolean equals(Object arg0) {
		VPXSubSystem vpx = (VPXSubSystem) arg0;

		return (this.id == vpx.id);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}

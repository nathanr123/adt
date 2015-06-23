package com.cti.vpx.model;

public class VPXSubSystem implements VPX,Comparable<VPXSubSystem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -695611620747305080L;

	private int id;

	private String subSystem;

	private String ipP2020;

	private String ipDSP1;

	private String ipDSP2;

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

	/**
	 * @param ipDSP2
	 *            the ipDSP2 to set
	 */
	public void setIpDSP2(String ipDSP2) {
		this.ipDSP2 = ipDSP2;
	}

	@Override
	public int compareTo(VPXSubSystem o) {
		
		int compareId = o.getId();
		
		return this.id-compareId;
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

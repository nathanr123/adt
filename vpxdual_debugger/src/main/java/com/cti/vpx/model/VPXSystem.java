package com.cti.vpx.model;

import java.util.ArrayList;
import java.util.Iterator;
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

	public void addSubSystem(VPXSubSystem sub) {

		this.subsystem.add(sub);

	}

	public List<Processor> getUnListed() {
		return unListed;
	}

	@XmlTransient
	public void setUnListed(List<Processor> unListed) {
		this.unListed = unListed;
	}

	public void clearUnlisted() {

		this.unListed.clear();
	}

	public void addInUnListed(String ip, long duration, String msg) {

		unListed.add(new Processor(ip, duration, msg));
	}

	public void addToUnListed(Processor p) {

		unListed.add(p);
	}

	// Accessible Methods

	public PROCESSOR_LIST getProcessorTypeByIP(String ip) {

		PROCESSOR_LIST proc = null;

		if (subsystem.size() > 0) {

			for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getIpP2020().equals(ip)) {

					proc = PROCESSOR_LIST.PROCESSOR_P2020;

				} else if (vpxSubSystem.getIpDSP1().equals(ip)) {

					proc = PROCESSOR_LIST.PROCESSOR_DSP1;

				} else if (vpxSubSystem.getIpDSP2().equals(ip)) {

					proc = PROCESSOR_LIST.PROCESSOR_DSP2;
				}
			}

		}

		if (unListed.size() > 0) {

			for (Iterator<Processor> iterator = unListed.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				if (processor.getiP_Addresses().equals(ip)) {

					if (processor.getName().contains("P2020")) {

						proc = PROCESSOR_LIST.PROCESSOR_P2020;

					} else if (processor.getName().contains("DSP1")) {

						proc = PROCESSOR_LIST.PROCESSOR_DSP1;

					} else if (processor.getName().contains("DSP2")) {

						proc = PROCESSOR_LIST.PROCESSOR_DSP2;
					}

				}
			}

		}

		return proc;

	}

	public long getRespondedTime(String ips) {

		long time = 0;

		String ip = "";

		if (ips.contains(")")) {

			ip = ips.substring(ips.indexOf(")") + 1, ips.length());
		} else
			ip = ips;

		boolean isNotfound = false;

		if (subsystem.size() > 0) {

			for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getIpP2020().equals(ip)) {

					time = vpxSubSystem.getP2020ResponseTime();

					isNotfound = true;

					break;

				} else if (vpxSubSystem.getIpDSP1().equals(ip)) {

					time = vpxSubSystem.getDsp1ResponseTime();

					isNotfound = true;

					break;

				} else if (vpxSubSystem.getIpDSP2().equals(ip)) {

					time = vpxSubSystem.getDsp2ResponseTime();

					isNotfound = true;

					break;
				}

			}
		}
		if (!isNotfound) {

			if (unListed.size() > 0) {

			}
			for (Iterator<Processor> iterator = unListed.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				if (processor.getiP_Addresses().equals(ip)) {

					time = processor.getResponseTime();

					break;
				}

			}
		}

		return time;

	}

	public VPXSubSystem getSubSystemByName(String name) {

		VPXSubSystem sub = null;

		if (subsystem.size() > 0) {

			for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getSubSystem().equals(name)) {

					sub = vpxSubSystem;

					break;
				}

			}
		}
		return sub;

	}

	public VPXSubSystem getSubSystemByIP(String ip) {

		VPXSubSystem sub = null;

		if (subsystem.size() > 0) {

			for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getIpP2020().equals(ip) || vpxSubSystem.getIpDSP1().equals(ip)
						|| vpxSubSystem.getIpDSP2().equals(ip)) {

					sub = vpxSubSystem;

					break;
				}

			}
		}
		return sub;

	}
}

package com.cti.vpx.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class FileBytesToSend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long fileSize;

	private long totalpackets;

	private long currentPacket;

	private long remainigPacket;

	private Map<Long, byte[]> bytesMap = new LinkedHashMap<Long, byte[]>();

	private Iterator<Entry<Long, byte[]>> iteratorByteArray;

	/**
	 * @param fileSize
	 * @param totalpackets
	 * @param currentPacket
	 * @param remainigPacket
	 */
	public FileBytesToSend(long fileSize, long totalpackets, long currentPacket, long remainigPacket) {

		super();

		this.fileSize = fileSize;

		this.totalpackets = totalpackets;

		this.currentPacket = currentPacket;

		this.remainigPacket = remainigPacket;
	}

	public FileBytesToSend(long fileSize, Map<Long, byte[]> map) {

		super();

		this.fileSize = fileSize;

		this.bytesMap = map;

		setTotalpackets(map.size());

		setCurrentPacket(0);

		setIterator();

	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the totalpackets
	 */
	public long getTotalpackets() {
		return totalpackets;
	}

	/**
	 * @param totalpackets
	 *            the totalpackets to set
	 */
	public void setTotalpackets(long totalpackets) {
		this.totalpackets = totalpackets;
	}

	/**
	 * @return the currentPacket
	 */
	public long getCurrentPacket() {
		return currentPacket;
	}

	/**
	 * @param currentPacket
	 *            the currentPacket to set
	 */
	public void setCurrentPacket(long currentPacket) {

		this.remainigPacket = this.totalpackets - currentPacket;

		this.currentPacket = currentPacket;
	}

	/**
	 * @return the remainigPacket
	 */
	public long getRemainigPacket() {
		return remainigPacket;
	}

	/**
	 * @param remainigPacket
	 *            the remainigPacket to set
	 */
	public void setRemainigPacket(long remainigPacket) {
		this.remainigPacket = remainigPacket;
	}

	/**
	 * @return the bytesMap
	 */
	public Map<Long, byte[]> getBytesMap() {
		return bytesMap;
	}

	/**
	 * @param bytesMap
	 *            the bytesMap to set
	 */
	public void setBytesMap(Map<Long, byte[]> bytesMap) {

		this.bytesMap = bytesMap;

		setIterator();
	}

	public byte[] getBytePacket(long index) {

		return bytesMap.get(index);
	}

	public byte[] getCurrentBytePacket() {

		return bytesMap.get(currentPacket);
	}

	public byte[] getNextPacket() {

		long temp = currentPacket++;

		setCurrentPacket(currentPacket);

		if (bytesMap.containsKey(temp))

			return bytesMap.get(temp);
		else
			return null;
	}

	public byte[] getNext() {

		if (iteratorByteArray != null) {
			if (iteratorByteArray.hasNext()) {

				Entry<Long, byte[]> e = iteratorByteArray.next();

				currentPacket = e.getKey();

				return e.getValue();

			} else
				return null;
		} else
			return null;
	}

	public void setIterator() {

		Set<Entry<Long, byte[]>> set = bytesMap.entrySet();

		iteratorByteArray = set.iterator();

	}
}
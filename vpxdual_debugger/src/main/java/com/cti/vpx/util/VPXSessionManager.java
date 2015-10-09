package com.cti.vpx.util;

import com.cti.vpx.model.VPXSystem;

public class VPXSessionManager {

	private static String currentProcessor = "";

	private static String currentSubSystem = "";

	private static String currentProcType = "";

	private static String currentSystemIP;

	private static String currentLogFileName;

	private static int currentPeriodicity = VPXConstants.PERIODICITY;

	private static VPXSystem vpxSystem = null;

	public static String getCurrentIP() {

		return currentSystemIP;
	}

	public static void setCurrentIP(String ip) {

		currentSystemIP = ip;
	}

	/**
	 * @return the currentPeriodicity
	 */
	public static int getCurrentPeriodicity() {
		return currentPeriodicity;
	}

	/**
	 * @param currentPeriodicity
	 *            the currentPeriodicity to set
	 */
	public static void setCurrentPeriodicity(int periodicity) {
		currentPeriodicity = periodicity;
	}

	/**
	 * @return the currentProcessor
	 */
	public static String getCurrentProcessor() {
		return currentProcessor;
	}

	/**
	 * @return the currentSubSystem
	 */
	public static String getCurrentSubSystem() {
		return currentSubSystem;
	}

	/**
	 * @param currentSubSystem
	 *            the currentSubSystem to set
	 */
	public static void setCurrentSubSystem(String subSystem) {
		currentSubSystem = subSystem;
	}

	/**
	 * @return the currentProcType
	 */
	public static String getCurrentProcType() {
		return currentProcType;
	}

	/**
	 * @param currentProcType
	 *            the currentProcType to set
	 */
	public static void setCurrentProcType(String procType) {
		currentProcType = procType;
	}

	/**
	 * @param currentProcessor
	 *            the currentProcessor to set
	 */
	public static void setCurrentProcessor(String sub, String procType, String processor) {

		currentSubSystem = sub;

		currentProcessor = processor;

		currentProcType = procType;

	}

	public static VPXSystem getVPXSystem() {

		if (vpxSystem == null)
			vpxSystem = VPXUtilities.readFromXMLFile();

		return vpxSystem;
	}

	public static void setVPXSystem(VPXSystem sys) {

		vpxSystem = sys;
	}

	public static String getCurrentSystemIP() {

		return currentSystemIP;
	}

	public static void setCurrentSystemIP(String currentSystemIP) {

		VPXSessionManager.currentSystemIP = currentSystemIP;
	}

	public static String getCurrentLogFileName() {

		return currentLogFileName;
	}

	public static void setCurrentLogFileName(String currentLogFileName) {

		VPXSessionManager.currentLogFileName = currentLogFileName;
	}

	public static VPXSystem getVpxSystem() {

		return vpxSystem;
	}

	public static void setVpxSystem(VPXSystem vpxSystem) {

		VPXSessionManager.vpxSystem = vpxSystem;
	}

	public static void setCurrentProcessor(String currentProcessor) {

		VPXSessionManager.currentProcessor = currentProcessor;
	}

}

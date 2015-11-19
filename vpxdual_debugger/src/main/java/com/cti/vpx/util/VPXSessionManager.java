package com.cti.vpx.util;

import java.util.ArrayList;
import java.util.List;

import com.cti.vpx.model.VPX;
import com.cti.vpx.model.VPXSystem;

public class VPXSessionManager {

	private static String currentProcessor = "";

	private static String currentSubSystem = "";

	private static String currentProcType = "";

	private static String currentSystemIP;

	private static String currentLogFileName;

	private static String currentErrorLogFileName;

	private static String currentWorkspacePath;

	private static int currentPeriodicity = VPXConstants.PERIODICITY;

	private static List<VPX.PROCESSOR_LIST> bistProcessors = new ArrayList<VPX.PROCESSOR_LIST>();

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

	public static void setCurrentLogFileName(String logFileName) {

		currentLogFileName = getEventPath() + "/" + logFileName;
	}

	public static String getCurrentErrorLogFileName() {

		return currentErrorLogFileName;
	}

	public static void setCurrentErrorLogFileName(String currentErrorLogFileName) {

		VPXSessionManager.currentErrorLogFileName = getErrorPath() + "/" + currentErrorLogFileName;
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

	public static void setWorkspacePath(String path) {

		currentWorkspacePath = path;
	}

	public static String getWorkspacePath() {

		return currentWorkspacePath;
	}

	public static String getSubSystemPath() {

		return getAsFullPath(currentWorkspacePath,
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM));
	}

	public static String getDSPPath() {

		return getAsFullPath(getSubSystemPath(),
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM_DSP));
	}

	public static String getDataPath() {

		return getAsFullPath(currentWorkspacePath,
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_DATA));
	}

	public static String getLogPath() {

		return getAsFullPath(currentWorkspacePath,
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG));
	}

	public static String getEventPath() {

		return getAsFullPath(getLogPath(),
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG_EVENT));
	}

	public static String getErrorPath() {

		return getAsFullPath(getLogPath(),
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG_ERROR));
	}

	public static String getConsolePath() {

		return getAsFullPath(getLogPath(),
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG_CONSOLE));
	}

	public static String getMessagePath() {

		return getAsFullPath(getLogPath(),
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG_MESSAGE));
	}

	public static String getExecutePath() {

		
		return "C:\\Users\\kamalanathan\\vpxWorkspace\\execute";
		/*
		return getAsFullPath(currentWorkspacePath,
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_EXECUTE));
*/
	}

	public static String getTFTPPath() {

		return getAsFullPath(getWorkspacePath(),
				VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_TFTP));
	}

	public static void addBISTProcessor(VPX.PROCESSOR_LIST processor) {

		bistProcessors.add(processor);

	}

	public static List<VPX.PROCESSOR_LIST> getBISTProcessor() {

		return bistProcessors;

	}

	private static String getAsFullPath(String parent, String child) {

		return parent + "\\" + child;
	}
}

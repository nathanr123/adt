package com.cti.vpx.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.cti.vpx.controls.VPX_StatusBar;
import com.cti.vpx.controls.VPX_LoggerPanel;

public class VPXLogger {

	private static VPX_LoggerPanel loggerPanel;

	private static VPX_StatusBar statusBar;

	private static String logMsg;

	public static VPX_LoggerPanel getLoggerPanel() {
		return loggerPanel;
	}

	public static void setLoggerPanel(VPX_LoggerPanel loggerPanel) {

		VPXLogger.loggerPanel = loggerPanel;
	}

	public static VPX_StatusBar getStatusBar() {
		return statusBar;
	}

	public static void setStatusBar(VPX_StatusBar statusBar) {
		VPXLogger.statusBar = statusBar;
	}

	public static void updateLog(String log) {

		updateLog(VPXConstants.INFO, log);
	}

	public static void updateLog(int LEVEL, String log) {

		if (log.length() > 0) {

			logMsg = VPXUtilities.getCurrentTime() + "  " + getLevel(LEVEL) + "  " + log;

			loggerPanel.appendLog(logMsg);

			updateLogtoFile(logMsg);

			updateStatus(log);
		}
	}

	public static void updateLogtoFile(String log) {

		try {

			if (VPXUtilities.isLogEnabled()) {

				String filePath = VPXSessionManager.getCurrentLogFileName();

				VPXUtilities.updateToFile(filePath, log);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void updateStatus(String stats) {

		statusBar.updateStatus(stats);
	}

	public static void updateError(Exception exception) {

		StringWriter error = new StringWriter();

		PrintWriter pw = new PrintWriter(error);

		exception.printStackTrace(pw);

		VPXUtilities.updateToFile(VPXSessionManager.getCurrentErrorLogFileName(),
				("\n" + VPXUtilities.getCurrentTime() + "\n" + error.toString()));

	}

	private static String getLevel(int level) {

		String lvl = "INFO: ";

		if (level == VPXConstants.INFO) {

			lvl = "INFO: ";

		} else if (level == VPXConstants.ERROR) {

			lvl = "ERROR: ";

		} else if (level == VPXConstants.WARN) {

			lvl = "WARN: ";

		} else if (level == VPXConstants.FATAL) {

			lvl = "FATAL: ";

		}

		return lvl;
	}
}

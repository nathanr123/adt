package com.cti.vpx.util;

import com.cti.vpx.controls.VPX_NetworkMonitorPanel;
import com.cti.vpx.model.VPXNWPacket;

public class VPXNetworkLogger {

	private static VPX_NetworkMonitorPanel nwMonitor;

	public static VPX_NetworkMonitorPanel getNwMonitor() {
		return nwMonitor;
	}

	public static void setNwMonitor(VPX_NetworkMonitorPanel nwMonitor) {
		VPXNetworkLogger.nwMonitor = nwMonitor;
	}

	public static void updatePacket(VPXNWPacket pkt) {

		nwMonitor.addPacket(pkt);
	}
}

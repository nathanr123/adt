package com.cti.vpx.listener;

import com.cti.vpx.controls.hex.MemoryViewFilter;
import com.cti.vpx.model.BIST;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;

/**
 * @author RajuDhachu
 *
 */
public interface VPXCommunicationListener extends VPXUDPListener {

	public void updateExit(int val);

	public void updateTestProgress(PROCESSOR_LIST pType, int val);

	public void updateBIST(BIST bist);

	public void readMemory(MemoryViewFilter filter);

	public void populateMemory(int memID, long startAddress, int stride, byte[] buffer);

	public void readPlot(MemoryViewFilter filter);

	public void readPlot(MemoryViewFilter filter1, MemoryViewFilter filter2);

	public void populatePlot(int plotID, int lineID, long startAddress, byte[] buffer);

	public void readAnalyticalData(String ip, int core, int id);

	public void populateAnalyticalData(String ip, int core, int id, float[] yAxis);

	public void sendAnalyticalDataInterrupt(String ip);

}

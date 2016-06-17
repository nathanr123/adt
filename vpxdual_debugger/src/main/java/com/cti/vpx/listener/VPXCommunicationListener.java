package com.cti.vpx.listener;

import com.cti.vpx.controls.hex.MemoryViewFilter;
import com.cti.vpx.model.BIST;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;

public interface VPXCommunicationListener extends VPXUDPListener {

	public void updateExit(int val);

	public void updateTestProgress(PROCESSOR_LIST pType, int val);

	public void updateBIST(BIST bist);

	// Memory Window

	public void readMemory(MemoryViewFilter filter);

	public void populateMemory(int memID, long startAddress, int stride, byte[] buffer);

	public void reIndexMemoryBrowserIndex();

	// Plot Window

	public void readPlot(MemoryViewFilter filter);

	public void readPlot(MemoryViewFilter filter1, MemoryViewFilter filter2);

	public void populatePlot(int plotID, int lineID, long startAddress, byte[] buffer);

	public void reIndexMemoryPlotIndex();

	// Spectrum Window

	public void readSpectrum(String ip, int core, int id);

	public void populateSpectrum(String ip, int core, int id, float[] yAxis);

	public void sendSpectrumInterrupt(String ip, int core);

	public void reIndexSpectrumWindowIndex(String ip, int core);

}

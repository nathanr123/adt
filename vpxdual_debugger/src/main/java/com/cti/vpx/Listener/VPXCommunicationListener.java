package com.cti.vpx.Listener;

import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.model.BIST;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;

/**
 * @author RajuDhachu
 *
 */
public interface VPXCommunicationListener extends VPXUDPListener {

	public void updateCommand(ATPCommand command);

	public void updateExit(int val);

	public void updateTestProgress(PROCESSOR_LIST pType, int val);

	public void updateBIST(BIST bist);

	public void sendCommand(ATPCommand command);

}

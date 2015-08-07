package com.cti.vpx.Listener;

import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.P2020ATPCommand;

/**
 * @author RajuDhachu
 *
 */
public interface VPXCommunicationListener extends VPXUDPListener {

	public void updateCommand(ATPCommand command);

	public void updateCommand(P2020ATPCommand command);

	public void updateCommand(DSPATPCommand command);

	public void sendCommand(ATPCommand command);

	public void sendCommand(P2020ATPCommand command);

	public void sendCommand(DSPATPCommand command);
}

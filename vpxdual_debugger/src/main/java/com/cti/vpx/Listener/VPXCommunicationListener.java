package com.cti.vpx.Listener;

import com.cti.vpx.command.ATPCommand;

/**
 * @author RajuDhachu
 *
 */
public interface VPXCommunicationListener extends VPXUDPListener {

	public void updateCommand(ATPCommand command);

	public void sendCommand(ATPCommand command);

}

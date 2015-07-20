package com.cti.vpx.Listener;

import com.cti.vpx.command.ATP;

/**
 * @author RajuDhachu
 *
 */
public interface CommunicationListener extends UDPListener {

	public void updateCommand(ATP command);
	
	public void sendCommand(ATP command);
}

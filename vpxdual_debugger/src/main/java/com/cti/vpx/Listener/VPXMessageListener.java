/**
 * 
 */
package com.cti.vpx.Listener;

import com.cti.vpx.command.MSGCommand;

/**
 * @author RajuDhachu
 *
 */
public interface VPXMessageListener extends VPXUDPListener {

	public int PORTNO = CONSOLE_MSG_PORTNO;

	public void updateMessage(String ip, String msg);

	public void updateMessage(String ip, MSGCommand command);

	public void sendMessage(String ip, int core, String msg);

	public void printConsoleMessage(String ip, String msg);

	public void printConsoleMessage(String ip, MSGCommand command);

}

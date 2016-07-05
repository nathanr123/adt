/**
 * 
 */
package com.cti.vpx.listener;

import com.cti.vpx.command.MSGCommand;

public interface VPXMessageListener extends VPXUDPListener {

	public int PORTNO = DEFAULT_CONSOLE_MSG_PORTNO;

	public void updateMessage(String ip, String msg);

	public void updateMessage(String ip, MSGCommand command);

	public void sendMessage(String ip, int core, String msg);

	public void printConsoleMessage(String ip, String msg);

	public void printConsoleMessage(String ip, MSGCommand command);

}

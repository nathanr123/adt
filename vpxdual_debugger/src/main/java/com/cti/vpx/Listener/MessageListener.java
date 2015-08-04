/**
 * 
 */
package com.cti.vpx.Listener;

import com.cti.vpx.command.MessageCommand;

/**
 * @author RajuDhachu
 *
 */
public interface MessageListener extends UDPListener {

	public int PORTNO = CONSOLE_MSG_PORTNO;

	public void updateMessage(String ip, String msg);

	public void updateMessage(String ip, MessageCommand command);

	public void sendMessage(String ip, int core, String msg);

	public void printConsoleMessage(String ip, String msg);

	public void printConsoleMessage(String ip, MessageCommand command);

}

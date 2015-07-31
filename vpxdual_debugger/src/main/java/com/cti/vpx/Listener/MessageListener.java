/**
 * 
 */
package com.cti.vpx.Listener;

import com.cti.vpx.command.DSPMessageCommand;
import com.cti.vpx.command.MessageCommand;
import com.cti.vpx.command.P2020MessageCommand;

/**
 * @author RajuDhachu
 *
 */
public interface MessageListener extends UDPListener {

	public int PORTNO = CONSOLE_MSG_PORTNO;

	public void updateMessage(String ip, String msg);

	public void updateMessage(String ip, MessageCommand command);

	public void updateMessage(String ip, DSPMessageCommand command);

	public void updateMessage(String ip, P2020MessageCommand command);

	public void sendMessage(String ip, String msg);

	public void sendMessage(String ip, MessageCommand command);

	public void sendMessage(String ip, DSPMessageCommand command);

	public void sendMessage(String ip, P2020MessageCommand command);

	public void printConsoleMessage(String ip, String msg);

	public void printConsoleMessage(String ip, MessageCommand command);

	public void printConsoleMessage(String ip, DSPMessageCommand command);

	public void printConsoleMessage(String ip, P2020MessageCommand command);
}

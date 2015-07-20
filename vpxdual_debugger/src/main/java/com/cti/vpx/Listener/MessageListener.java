/**
 * 
 */
package com.cti.vpx.Listener;

/**
 * @author RajuDhachu
 *
 */
public interface MessageListener extends UDPListener {

	public int PORTNO = CONSOLE_MSG_PORTNO;

	public void updateMessage(String ip, String msg);

	public void sendMessage(String ip, String msg);

	public void printConsoleMessage(String ip, String msg);
}

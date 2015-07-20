/**
 * 
 */
package com.cti.vpx.Listener;

import java.io.Serializable;

/**
 * @author RajuDhachu
 *
 */
public interface UDPListener extends Serializable{

	public int ADV_PORTNO = 12345;

	public int COMM_PORTNO = 12346;

	public int CONSOLE_MSG_PORTNO = 12347;

	public int CONNECTION_TIMEOUT = 300000;

}

/**
 * 
 */
package com.cti.vpx.listener;

import java.io.Serializable;

public interface VPXUDPListener extends Serializable{

	public int ADV_PORTNO = 12345;

	public int COMM_PORTNO = 12346;

	public int CONSOLE_MSG_PORTNO = 12347;

	public int CONNECTION_TIMEOUT = 300000;

}

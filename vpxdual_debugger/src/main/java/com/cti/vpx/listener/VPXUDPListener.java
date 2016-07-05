/**
 * 
 */
package com.cti.vpx.listener;

import java.io.Serializable;

public interface VPXUDPListener extends Serializable{

	public int DEFAULT_ADV_PORTNO = 12345;

	public int DEFAULT_COMM_PORTNO = 12346;

	public int DEFAULT_CONSOLE_MSG_PORTNO = 12347;

	public int CONNECTION_TIMEOUT = 300000;

}

/**
 * 
 */
package com.cti.vpx.model;

import java.io.Serializable;

/**
 * @author Abi_Achu
 *
 */
public interface VPX extends Serializable {

	public int ADV_PORTNO = 12345;

	public int COMM_PORTNO = 12346;

	public int CONSOLE_PORTNO = 12347;

	public int MSG_PORTNO = 12348;
	
	public int CONNECTION_TIMEOUT = 300000;

	public enum PROCESSOR_LIST {
		PROCESSOR_P2020, PROCESSOR_DSP1, PROCESSOR_DSP2
	};
}

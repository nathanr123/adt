/**
 * 
 */
package com.cti.vpx.command;

import java.nio.ByteOrder;

/**
 * @author Raju_Dachu
 *
 */
public class DSPATPCommand extends ATPCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9159745143361625196L;

	public DSPATPCommand() {
		super();
	}
	@Override
	public ByteOrder byteOrder() {
		return  ATP.BYTEORDER_DSP;
	}

}

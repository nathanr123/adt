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

	public DSPATPCommand() {
		super();
	}

	@Override
	public ByteOrder byteOrder() {
		return ATP.BYTEORDER_DSP;
	}

}

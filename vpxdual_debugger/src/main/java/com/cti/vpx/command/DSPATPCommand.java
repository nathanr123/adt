/**
 * 
 */
package com.cti.vpx.command;

import java.nio.ByteOrder;

public class DSPATPCommand extends ATPCommand {

	public DSPATPCommand() {
		super();
	}

	@Override
	public ByteOrder byteOrder() {
		return ATP.BYTEORDER_DSP;
	}

}

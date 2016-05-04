package com.cti.vpx.command;

import java.nio.ByteOrder;

public class DSPMSGCommand extends MSGCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 587715014203358591L;

	@Override
	public ByteOrder byteOrder() {
		return ByteOrder.LITTLE_ENDIAN;
	}
}

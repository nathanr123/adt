package com.cti.vpx.command;

import java.nio.ByteOrder;

public class P2020MSGCommand extends MSGCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7669199560696911813L;

	@Override
	public ByteOrder byteOrder() {
		return ByteOrder.BIG_ENDIAN;
	}
}

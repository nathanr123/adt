package com.cti.vpx.command;

import java.io.Serializable;
import java.nio.ByteOrder;

import javolution.io.Struct;

public class MSGCommand extends Struct implements ATP, Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = -3422816712139640712L;

	public final Enum32<MESSAGE_MODE> mode = new Enum32<MESSAGE_MODE>(MESSAGE_MODE.values());

	public final Unsigned32 core = new Unsigned32();

	public final UTF8String command_msg = new UTF8String(128);

	public MSGCommand() {

	}

	/**
	 * @return the mode
	 */
	public Enum32<MESSAGE_MODE> getMode() {
		return mode;
	}

	/**
	 * @return the core
	 */
	public Unsigned32 getCore() {
		return core;
	}

	/**
	 * @return the command_msg
	 */
	public UTF8String getCommand_msg() {
		return command_msg;
	}

	@Override
	public ByteOrder byteOrder() {
		return ByteOrder.nativeOrder();
	}

	@Override
	public boolean isPacked() {
		return true;
	}
}

package com.cti.vpx.command;

import java.nio.ByteOrder;

import javolution.io.Struct;

import javolution.io.Union;

public class ATP_MSG_COMMAND extends Struct implements ATP {

	public final Unsigned32 msgType = new Unsigned32();

	public final Unsigned32 msgID = new Unsigned32();

	public final MSGParamaenters params = inner(new MSGParamaenters());

	public ATP_MSG_COMMAND() {

	}

	class ProcessorInfo extends Struct {

		public final Unsigned32 slotID = new Unsigned32();

		public final Unsigned32 processorID = new Unsigned32();

		public final Enum32<PROCESSOR_TYPE> processorTYPE = new Enum32<PROCESSOR_TYPE>(PROCESSOR_TYPE.values());

		public ProcessorInfo() {
		}
	}

	class MSGParamaenters extends Union {
		public final ProcessorInfo proccesorInfo = inner(new ProcessorInfo());

		public MSGParamaenters() {
		}
	}

	@Override
	public ByteOrder byteOrder() {

		return ByteOrder.LITTLE_ENDIAN;
	}

}

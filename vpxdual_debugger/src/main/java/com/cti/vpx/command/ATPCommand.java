package com.cti.vpx.command;

import java.nio.ByteOrder;

import javolution.io.Struct;

public class ATPCommand extends Struct implements ATP {

	/**
	 * 
	 */

	public final Unsigned32 msgType = new Unsigned32();

	public final Unsigned32 msgID = new Unsigned32();

	public final Unsigned32 periodicity = new Unsigned32();

	public final Enum32<PROCESSOR_TYPE> processorTYPE = new Enum32<PROCESSOR_TYPE>(PROCESSOR_TYPE.values());

	public final MSGParamaenters params = inner(new MSGParamaenters());

	public class TestInfo extends Struct {

		// DSP
		public final Unsigned8 RESULT_DSP_PROCESSOR = new Unsigned8();

		public final Unsigned8 RESULT_DSP_DDR3 = new Unsigned8();

		public final Unsigned8 RESULT_DSP_NAND = new Unsigned8();

		public final Unsigned8 RESULT_DSP_NOR = new Unsigned8();

		// P2020

		public final Unsigned8 RESULT_P2020_PROCESSOR = new Unsigned8();

		public final Unsigned8 RESULT_P2020_DDR3 = new Unsigned8();

		public final Unsigned8 RESULT_P2020_NORFLASH = new Unsigned8();

		public final Unsigned8 RESULT_P2020_ETHERNET = new Unsigned8();

		public final Unsigned8 RESULT_P2020_SRIO = new Unsigned8();

		public final Unsigned8 RESULT_P2020_PCIE = new Unsigned8();

		public final Unsigned16 Resrved = new Unsigned16();

		public final Unsigned32 RESULT_P2020_TEMP1 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_TEMP2 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_TEMP3 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_VOLT1_3p3 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_VOLT2_2p5 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_VOLT3_1p8 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_VOLT4_1p5 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_VOLT5_1p2 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_VOLT6_1p0 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_VOLT7_1p05 = new Unsigned32();

	}

	public class FlashInfo extends Struct {

		public final Unsigned32 flashdevice = new Unsigned32();

		public final Unsigned32 offset = new Unsigned32();

		public final Unsigned32 totalfilesize = new Unsigned32();

		public final Unsigned32 totalnoofpackets = new Unsigned32();

		public final Unsigned32 currentpacket = new Unsigned32();

	}

	public class TFTPInfo extends Struct {

		public final Unsigned32 filetype = new Unsigned32();

		public final UTF8String filename = new UTF8String(128);

		public final UTF8String ip = new UTF8String(15);
	}

	public class MemoryInfo extends Struct {

		public final Unsigned32 memIndex = new Unsigned32();

		public final Unsigned32 address = new Unsigned32();

		public final Unsigned32 core = new Unsigned32();

		public final Unsigned32 length = new Unsigned32();

		public final Unsigned32 stride = new Unsigned32();

		public final Unsigned32 newvalue = new Unsigned32();

		public final Unsigned32 byteZero = new Unsigned32();

		public final Unsigned8 buffer[] = array(new Unsigned8[DEFAULTBUFFERSIZE]);

	}

	public class MSGParamaenters extends Struct {

		public final TestInfo testinfo = inner(new TestInfo());

		public final FlashInfo flash_info = inner(new FlashInfo());

		public final MemoryInfo memoryinfo = inner(new MemoryInfo());

		public final TFTPInfo tftpinfo = inner(new TFTPInfo());
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

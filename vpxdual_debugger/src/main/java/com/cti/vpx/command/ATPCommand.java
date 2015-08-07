package com.cti.vpx.command;

import java.io.Serializable;
import java.nio.ByteOrder;

import javolution.io.Struct;
import javolution.io.Union;

public class ATPCommand extends Struct implements ATP, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -799775047978799947L;

	public final Unsigned32 msgType = new Unsigned32();

	public final Unsigned32 msgID = new Unsigned32();

	public final MSGParamaenters params = inner(new MSGParamaenters());

	private static final transient ATPCommand instance = new ATPCommand();

	public ATPCommand() {

	}

	public class TestInfo extends Struct {

		// P2020

		public final Unsigned32 RESULT_P2020_PROCESSOR = new Unsigned32();

		public final Unsigned32 RESULT_P2020_DDR3 = new Unsigned32();

		public final Unsigned32 RESULT_P2020_NORFLASH = new Unsigned32();

		public final Unsigned32 RESULT_P2020_ETHERNET = new Unsigned32();

		public final Unsigned32 RESULT_P2020_SRIO = new Unsigned32();

		public final Unsigned32 RESULT_P2020_PCIE = new Unsigned32();

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

		// DSP
		public final Unsigned32 RESULT_DSP_PROCESSOR = new Unsigned32();

		public final Unsigned32 RESULT_DSP_DDR3 = new Unsigned32();

		public final Unsigned32 RESULT_DSP_NAND = new Unsigned32();

		public final Unsigned32 RESULT_DSP_NOR = new Unsigned32();
	}

	public class FlashInfo extends Struct {

		public final Unsigned32 flashdevice = new Unsigned32();

		public final Unsigned32 offset = new Unsigned32();

		public final Unsigned32 totalfilesize = new Unsigned32();

		public final Unsigned32 totalnoofpackets = new Unsigned32();

		public final Unsigned32 currentpacket = new Unsigned32();

	}

	public class ProcessorInfo extends Struct {

		public final Unsigned32 slotID = new Unsigned32();

		public final Unsigned32 processorID = new Unsigned32();

		public final Enum32<PROCESSOR_TYPE> processorTYPE = new Enum32<PROCESSOR_TYPE>(PROCESSOR_TYPE.values());

	}

	public class MemoryInfo extends Struct {

		public final Unsigned32 address = new Unsigned32();

		public final Unsigned32 length = new Unsigned32();

		public final Unsigned32 stride = new Unsigned32();

		public final Unsigned32 newvalue = new Unsigned32();

		public final Unsigned32 buffer[] = array(new Unsigned32[1024]);

	}

	public class MSGParamaenters extends Union {

		public final Unsigned32 testType = new Unsigned32();

		public final Unsigned32 periodicity = new Unsigned32();

		public final ProcessorInfo proccesorInfo = inner(new ProcessorInfo());

		public final MemoryInfo memoryinfo = inner(new MemoryInfo());

		public final FlashInfo flash_info = inner(new FlashInfo());

		public TestInfo testinfo = inner(new TestInfo());

		public MSGParamaenters() {
		}
	}

	@Override
	public ByteOrder byteOrder() {
		return ByteOrder.nativeOrder();
	}

	@Override
	public boolean isPacked() {
		return true;
	}

	public static int getSize() {
		return instance.size();
	}
}

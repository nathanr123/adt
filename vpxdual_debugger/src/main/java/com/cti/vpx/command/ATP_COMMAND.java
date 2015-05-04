package com.cti.vpx.command;

import java.nio.ByteOrder;

import javolution.io.Struct;

import javolution.io.Union;

public class ATP_COMMAND extends Struct implements ATP {

	public final Unsigned32 msgType = new Unsigned32();

	public final Unsigned32 msgID = new Unsigned32();

	public final MSGParamaenters params = inner(new MSGParamaenters());

	public ATP_COMMAND() {

	}

	public class TestInfo extends Struct {
	
		// P2020
		public  final Unsigned32 RESULT_P2020_PROCESSOR = new Unsigned32();
				
		public  final Unsigned32 RESULT_P2020_MEMORY = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_FLASH = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_RTC = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_USB = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_TEMP = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_ETHERNET = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_LISTPCI = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_SATATUS = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_AUDIO = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_PMCXMC = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_SRIOLOOP = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_TEMP1 = new Unsigned32();
		
		public  final Unsigned32 RESULT_P2020_TEMP2 = new Unsigned32();
		
		//Full Test P2020
		public  final Unsigned32 RESULT_P2020_FULL = new Unsigned32();

		//DSP		
		public  final Unsigned32 RESULT_DSP_DDR = new Unsigned32();
		
		public  final Unsigned32 RESULT_DSP_DMA = new Unsigned32();
		
		public  final Unsigned32 RESULT_DSP_NAND = new Unsigned32();
		
		public  final Unsigned32 RESULT_DSP_NOR = new Unsigned32();
		
		public  final Unsigned32 RESULT_DSP_HYPLOOP = new Unsigned32();
		
		public  final Unsigned32 RESULT_DSP_ETHERNET = new Unsigned32();
		
		public  final Unsigned32 RESULT_DSP_PCIE = new Unsigned32();
		
		public  final Unsigned32 RESULT_DSP_SRIO = new Unsigned32();
		
		//Full Test DSP
		public  final Unsigned32 RESULT_DSP_FULL = new Unsigned32();
	}

	public class ProcessorInfo extends Struct {

		public final Unsigned32 slotID = new Unsigned32();

		public final Unsigned32 processorID = new Unsigned32();

		public final Enum32<PROCESSOR_TYPE> processorTYPE = new Enum32<PROCESSOR_TYPE>(PROCESSOR_TYPE.values());

	}

	public class MSGParamaenters extends Union {

		public final Unsigned32 testType = new Unsigned32();

		public final ProcessorInfo proccesorInfo = inner(new ProcessorInfo());

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
}

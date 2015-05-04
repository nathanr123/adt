package com.cti.vpx.command;

public interface ATP {

	// Message ID
	public static final int MSG_ID_GET = 0xA0;
	public static final int MSG_ID_SET = 0xA1;

	// Message Type
	public static final int MSG_TYPE_QUERY = 0xC0;
	public static final int MSG_TYPE_TEST = 0xC1;
	
	// Result
	public static final int TEST_RESULT_PASS = 0x01;
	public static final int TEST_RESULT_FAIL = 0x00;
	
	// Test Types
	// P2020
	public static final int TEST_P2020_PROCESSOR = 0x0F01;
	
	public static final int TEST_P2020_MEMORY = 0x0F02;
	
	public static final int TEST_P2020_FLASH = 0x0F03;
	
	public static final int TEST_P2020_RTC = 0x0F04;
	
	public static final int TEST_P2020_USB = 0x0F05;
	
	public static final int TEST_P2020_TEMP = 0x0F06;
	
	public static final int TEST_P2020_ETHERNET = 0x0F07;
	
	public static final int TEST_P2020_LISTPCI = 0x0F08;
	
	public static final int TEST_P2020_SATATUS = 0x0F09;
	
	public static final int TEST_P2020_AUDIO = 0x0F0A;
	
	public static final int TEST_P2020_PMCXMC = 0x0F0B;
	
	public static final int TEST_P2020_SRIOLOOP = 0x0F0C;
	
	public static final int TEST_P2020_FULL = 0x0FFF;
	
	// DSP
	public static final int TEST_DSP_DDR = 0x0E01;

	public static final int TEST_DSP_DMA = 0x0E02;
	
	public static final int TEST_DSP_NAND = 0x0E03;
	
	public static final int TEST_DSP_NOR = 0x0E04;
	
	public static final int TEST_DSP_HYPLOOP = 0x0E05;
	
	public static final int TEST_DSP_ETHERNET = 0x0E06;
	
	public static final int TEST_DSP_PCIE = 0x0E07;
	
	public static final int TEST_DSP_SRIO = 0x0E08;
	
	public static final int TEST_DSP_FULL = 0x0EFE;

	public enum PROCESSOR_TYPE {
		PROCESSOR_P2020, PROCESSOR_DSP1, PROCESSOR_DSP2
	};

}

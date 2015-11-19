package com.cti.vpx.command;

import java.nio.ByteOrder;

public interface ATP {

	// Message ID
	public static final int MSG_ID_GET = 0xA0;

	public static final int MSG_ID_SET = 0xA1;

	// Message Type
	public static final int MSG_TYPE_PERIDAICITY = 0xC0;

	public static final int MSG_TYPE_BIST = 0xC1;

	public static final int MSG_TYPE_MEMORY = 0xC2;

	public static final int MSG_TYPE_FLASH = 0xC3;

	public static final int MSG_TYPE_BOOT = 0xC4;

	public static final int MSG_TYPE_FLASH_ACK = 0xC5;

	public static final int MSG_TYPE_FLASH_INTERRUPTED = 0xC6;

	public static final int MSG_TYPE_FLASH_DONE = 0xC7;

	public static final int MSG_TYPE_CLOSE = 0xC8;

	public static final int MSG_TYPE_PLOT = 0xC9;

	public static final int MSG_TYPE_LOADMEMORY = 0xCA;

	public static final int MSG_TYPE_LOADMEMORY_ACK = 0xCB;

	public static final int MSG_TYPE_LOADMEMORY_DONE = 0xCC;

	public static final int MSG_TYPE_DATA_ANALYSIS = 0xCD;

	public static final int MSG_TYPE_DATA_ANALYSISL_INTERRUPTED = 0xCE;

	public static final int MSG_TYPE_EXECUTE = 0xCF;
	
	public static final int MSG_TYPE_EXECUTE_ACK = 0xD4;
	
	public static final int MSG_TYPE_EXECUTE_DONE = 0xD5;

	public static final int MSG_TYPE_EXECUTE_START = 0xD0;

	public static final int MSG_TYPE_EXECUTE_STOP = 0xD1;

	public static final int MSG_TYPE_EXECUTE_PAUSE = 0xD2;

	public static final int MSG_TYPE_EXECUTE_RESUME = 0xD3;

	// Result
	public static final int TFTP_FILEMODE_LINUX = 0x00;

	public static final int TFTP_FILEMODE_FS = 0x01;

	public static final int TFTP_FILEMODE_DTB = 0x02;

	public static final int TFTP_FILEMODE_UBOOT = 0x03;

	// Result
	public static final int TEST_RESULT_PASS = 0x01;

	public static final int TEST_RESULT_FAIL = 0x00;

	// FLASH Devices
	public static final int FLASH_DEVICE_NAND = 0x01;

	public static final int FLASH_DEVICE_NOR = 0x02;

	// Type Size
	public static final int DATA_TYPE_SIZE_BIT8 = 0x00;

	public static final int DATA_TYPE_SIZE_BIT16 = 0x01;

	public static final int DATA_TYPE_SIZE_BIT32 = 0x02;

	public static final int DATA_TYPE_SIZE_BIT64 = 0x03;

	// Enums
	public enum PROCESSOR_TYPE {
		PROCESSOR_P2020, PROCESSOR_DSP1, PROCESSOR_DSP2
	};

	public enum MESSAGE_MODE {
		MSG_MODE_CONSOLE, MSG_MODE_MESSAGE
	};

	// Address Ranges

	// 256MB DDR3 SDRAM for cores 0 - 7

	// Core 0
	public static final long CORE0_DDR3_START_ADDRESS = 0x80000000l;

	public static final long CORE0_DDR3_END_ADDRESS = 0x8FFFFFFFl;

	// Core 1
	public static final long CORE1_DDR3_START_ADDRESS = 0x90000000l;

	public static final long CORE1_DDR3_END_ADDRESS = 0x9FFFFFFFl;

	// Core 2
	public static final long CORE2_DDR3_START_ADDRESS = 0xA0000000l;

	public static final long CORE2_DDR3_END_ADDRESS = 0xAFFFFFFFl;

	// Core 3
	public static final long CORE3_DDR3_START_ADDRESS = 0xB0000000l;

	public static final long CORE3_DDR3_END_ADDRESS = 0xBFFFFFFFl;

	// Core 4
	public static final long CORE4_DDR3_START_ADDRESS = 0xC0000000l;

	public static final long CORE4_DDR3_END_ADDRESS = 0xCFFFFFFFl;

	// Core 5
	public static final long CORE5_DDR3_START_ADDRESS = 0xD0000000l;

	public static final long CORE5_DDR3_END_ADDRESS = 0xDFFFFFFFl;

	// Core 6
	public static final long CORE6_DDR3_START_ADDRESS = 0xE0000000l;

	public static final long CORE6_DDR3_END_ADDRESS = 0xEFFFFFFFl;

	// Core 7
	public static final long CORE7_DDR3_START_ADDRESS = 0xF0000000l;

	public static final long CORE7_DDR3_END_ADDRESS = 0xFFFFFFFFl;

	// L2 SDRAM for cores 0 - 7

	// Core 0
	public static final long C0_L2SRAM_START_ADDRESS = 0x10800000l;

	public static final long C0_L2SRAM_END_ADDRESS = 0x1087FFFFl;

	// Core 1
	public static final long C1_L2SRAM_START_ADDRESS = 0x11800000l;

	public static final long C1_L2SRAM_END_ADDRESS = 0x1187FFFFl;

	// Core 2
	public static final long C2_L2SRAM_START_ADDRESS = 0x12800000l;

	public static final long C2_L2SRAM_END_ADDRESS = 0x1287FFFFl;

	// Core 3
	public static final long C3_L2SRAM_START_ADDRESS = 0x13800000l;

	public static final long C3_L2SRAM_END_ADDRESS = 0x1387FFFFl;

	// Core 4
	public static final long C4_L2SRAM_START_ADDRESS = 0x14800000l;

	public static final long C4_L2SRAM_END_ADDRESS = 0x1487FFFFl;

	// Core 5
	public static final long C5_L2SRAM_START_ADDRESS = 0x15800000l;

	public static final long C5_L2SRAM_END_ADDRESS = 0x1587FFFFl;

	// Core 6
	public static final long C6_L2SRAM_START_ADDRESS = 0x16800000l;

	public static final long C6_L2SRAM_END_ADDRESS = 0x1687FFFFl;

	// Core 7
	public static final long C7_L2SRAM_START_ADDRESS = 0x17800000l;

	public static final long C7_L2SRAM_END_ADDRESS = 0x1787FFFFl;

	/*
	 * #define MSMC_START_ADDRESS 0x0C000000 #define MSMC_END_ADDRESS 0x0C3FFFFF
	 * 
	 * #define DDR3_START_ADDRESS 0x80000000 #define DDR3_END_ADDRESS 0xBFFFFFFF
	 * 
	 * 
	 * #define C0_L2SRAM_START_ADDRESS 0x00800000 #define C0_L2SRAM_END_ADDRESS
	 * 0x0087FFFF
	 */

	// Endianess ByteOrder
	public static final ByteOrder BYTEORDER_P2020 = ByteOrder.BIG_ENDIAN;

	public static final ByteOrder BYTEORDER_DSP = ByteOrder.LITTLE_ENDIAN;

	public static final int DEFAULTBUFFERSIZE = 1024 * 4;

}

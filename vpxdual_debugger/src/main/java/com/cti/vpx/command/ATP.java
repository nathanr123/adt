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

	// Enums
	public enum PROCESSOR_TYPE {
		PROCESSOR_P2020, PROCESSOR_DSP1, PROCESSOR_DSP2
	};

	public enum MESSAGE_MODE {
		MSG_MODE_CONSOLE, MSG_MODE_MESSAGE
	};

	// Endianess ByteOrder
	public static final ByteOrder BYTEORDER_P2020 = ByteOrder.BIG_ENDIAN;

	public static final ByteOrder BYTEORDER_DSP = ByteOrder.LITTLE_ENDIAN;

	public static final int DEFAULTBUFFERSIZE = 1024;

}

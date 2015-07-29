package com.cti.vpx.command;

public interface ATP {

	// Message ID
	public static final int MSG_ID_GET = 0xA0;
	public static final int MSG_ID_SET = 0xA1;

	// Message Type
	public static final int MSG_TYPE_PERIDAICITY = 0xC0;
	public static final int MSG_TYPE_BIST = 0xC1;
	public static final int MSG_TYPE_MEMORY = 0xC2;

	// Result
	public static final int TEST_RESULT_PASS = 0x01;
	public static final int TEST_RESULT_FAIL = 0x00;

	// FLASH Devices
	public static final int FLASH_DEVICE_NAND = 0x01;
	public static final int FLASH_DEVICE_NOR = 0x02;

	public enum PROCESSOR_TYPE {
		PROCESSOR_P2020, PROCESSOR_DSP1, PROCESSOR_DSP2
	};

	public enum MESSAGE_MODE {
		MSG_MODE_CONSOLE, MSG_MODE_MESSAGE
	};

}

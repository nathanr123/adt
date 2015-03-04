package com.cti.vpx.command;

public interface ADT_Command_Constants {

	// Processor Type
	public final int PROCESSOR_TYPE_P2020 = 0;
	public final int PROCESSOR_TYPE_DSP1 = 1;
	public final int PROCESSOR_TYPE_DSP2 = 2;

	// TEST Types
	public final int TEST_TYPE_ETHERNET = 0xB0;
	public final int TEST_TYPE_AUDIO = 0xB1;
	public final int TEST_TYPE_MEMORY = 0xB2;
	public final int TEST_TYPE_THROUGHPUT = 0xB3;
	public final int TEST_TYPE_PROTOCOL = 0xB4;
	public final int TEST_TYPE_EDMA = 0xB5;

	// Status Types
	public final int STATUS_GET_V_T = 0xC0; // To Get Volatage & Temperature
	public final int STATUS_GET_TIME = 0xC1; // To Get Board Time

	public final int STATUS_SET_UART = 0xC2; // To Set the UART for DSP 1 or 2
	public final int STATUS_SET_RESET = 0xC3; // To Reset and Reboot the DSP 1
												// or 2
	public final int STATUS_SET_TIME = 0xC4; // To Set the Board Time
	public final int STATUS_SET_NORMODE = 0xC5; // To Set the NOR Accessing Mode

	public final int STATUS_GET_VLAN = 0xC6; // To get VLAN Param from board
	public final int STATUS_SET_VLAN = 0xC7; // To set VLAN Param to board

	// Ethernet Test

	public final int ETH_TEST_TYPE_PING = 0;
	public final int ETH_TEST_TYPE_THROUGHPUT = 1;

	public final int ETH_PANNEL_FRONT = 0;
	public final int ETH_PANNEL_REAR = 1;

	public final int ETH_JUMBO_DISABLED = 0;
	public final int ETH_JUMBO_ENABLED = 1;

	public final int ETH_PORT_P1 = 1;
	public final int ETH_PORT_P2 = 2;
	public final int ETH_PORT_P3 = 3;

	public final int AUD_TEST_TYPE_PLYBACK = 0;
	public final int AUD_TEST_TYPE_D_LOOPBACK = 1;
	public final int AUD_TEST_TYPE_P_LOOPBACK = 2;

	public final int AUD_ACTION_PLAY = 0;
	public final int AUD_ACTION_STOP = 1;

	public final int MEMORY_TEST_TYPE_1 = 0;
	public final int MEMORY_DEV_TYPE_NAND = 2;
	public final int MEMORY_DEV_TYPE_NOR = 1;
	public final int MEMORY_DEV_TYPE_DDR = 0;

	public final int MEM_THROUGH_PUT_TYPE_1 = 0;

	public final int MEM_THROUGH_PUT_DEV_DDR = 0;
	public final int MEM_THROUGH_PUT_DEV_USB = 1;
	public final int MEM_THROUGH_PUT_DEV_SATA = 2;

	public final int DATA_XFER_SPEED_5P000 = 0;
	public final int DATA_XFER_SPEED_6P250 = 1;
	public final int DATA_XFER_SPEED_7P500 = 2;
	public final int DATA_XFER_SPEED_10P000 = 3;
	public final int DATA_XFER_SPEED_12P500 = 4;

	public final int SRIO_XFER_SPEED_1P250 = 0;
	public final int SRIO_XFER_SPEED_2P500 = 1;
	public final int SRIO_XFER_SPEED_3P125 = 2;
	public final int SRIO_XFER_SPEED_5P000 = 3;

	public final int DATA_XFER_TYPE_HYPLINK = 0;
	public final int DATA_XFER_TYPE_SRIO = 1;
	public final int DATA_XFER_TYPE_PCIE = 2;

	public final int EDMA_DEV_MSMC = 0;
	public final int EDMA_DEV_L2 = 1;
	public final int EDMA_DEV_DDR3 = 2;
	public final int EDMA_DEV_HYLK = 3;

	public final int BOOT_DEV_TYPE_NAND = 1;
	public final int BOOT_DEV_TYPE_NOR = 0;

	public final int NOR_HIGH_REGION = 1;
	public final int NOR_LOW_REGION = 0;

	public final int MESSAGE_TYPE_TEST = 0xA0;
	public final int MESSAGE_TYPE_STATUS = 0xA1;
	public final int MESSAGE_TYPE_PRINT = 0xA2;
}

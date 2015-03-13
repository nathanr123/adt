package com.cti.vpx.command;

public interface ATP {

	// Message Type
	public static final int MSG_TYPE_QUERY = 0xC0;
	
	
	// Message ID
	public static final int MSG_ID_INFO = 0xA0;

	public enum PROCESSOR_TYPE {
		PROCESSOR_P2020, PROCESSOR_DSP1, PROCESSOR_DSP2
	};

}

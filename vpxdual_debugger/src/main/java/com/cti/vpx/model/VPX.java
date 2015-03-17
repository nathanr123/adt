/**
 * 
 */
package com.cti.vpx.model;

import java.io.Serializable;

/**
 * @author Abi_Achu
 *
 */
public interface VPX extends Serializable {

	public enum PROCESSOR_LIST {
		PROCESSOR_P2020, PROCESSOR_DSP1, PROCESSOR_DSP2
	};

	public int MAX_CORE_DSP = 8;

	public int MAX_CORE_P2020 = 2;
}
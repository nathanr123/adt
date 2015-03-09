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

	public enum PROCESSOR_TYPE {

		DSP1,

		DSP2,

		P2020
	};

	public int MAX_CORE_DSP = 8;

	public int MAX_CORE_P2020 = 2;
}

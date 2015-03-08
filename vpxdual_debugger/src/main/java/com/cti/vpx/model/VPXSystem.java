/**
 * 
 */
package com.cti.vpx.model;

import java.io.Serializable;

/**
 * @author Abi_Achu
 *
 */
public interface VPXSystem extends Serializable {

	public enum PROCESSOR_TYPE {

		DSP1,

		DSP2,

		P2020
	};

	public int MAX_CORE = 8;
}

/**
 * 
 */
package com.cti.vpx.command;

import java.nio.ByteOrder;

/**
 * @author Raju_Dachu
 *
 */
public class P2020ATPCommand extends ATPCommand {

	@Override
	public ByteOrder byteOrder() {
		return ATP.BYTEORDER_P2020;
	}

}

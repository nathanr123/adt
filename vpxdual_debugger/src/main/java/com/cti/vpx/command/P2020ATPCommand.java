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

    /**
	 * 
	 */
    private static final long serialVersionUID = 6737875272065675523L;

    @Override
    public ByteOrder byteOrder() {
	return ByteOrder.BIG_ENDIAN;
    }

}

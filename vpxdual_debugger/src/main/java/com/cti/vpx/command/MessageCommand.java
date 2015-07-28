package com.cti.vpx.command;

import java.io.Serializable;
import java.nio.ByteOrder;

import javolution.io.Struct;

public class MessageCommand extends Struct implements ATP, Serializable {

    /**
     * 
     */

    
    private static final long serialVersionUID = -3422816712139640712L;

    public final Enum32<MESSAGE_MODE> mode = new Enum32<MESSAGE_MODE>(MESSAGE_MODE.values());

    public final Unsigned32 core = new Unsigned32();

    public final UTF8String command = new UTF8String(16);

    public final UTF8String argu1 = new UTF8String(16);

    public final UTF8String argu2 = new UTF8String(16);
   
    public final UTF8String argu3 = new UTF8String(16);

    public final UTF8String argu4 = new UTF8String(16);

    public MessageCommand() {

    }

    @Override
    public ByteOrder byteOrder() {
	return ByteOrder.nativeOrder();
    }

    @Override
    public boolean isPacked() {
	return true;
    }
}

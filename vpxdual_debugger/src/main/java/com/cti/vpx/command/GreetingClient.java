package com.cti.vpx.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class GreetingClient {
	public static void main(String[] args) {
		
		String serverName = "172.17.1.28";
		
		int port = 12345;
		
		try {
			System.out.println("Connecting to " + serverName + " on port " + port);

			Socket client = new Socket();

			client.connect(new InetSocketAddress(serverName, port), 500);

			client.setSoTimeout(1000);
			
			System.out.println("Just connected to " + client.getRemoteSocketAddress());

			OutputStream outToServer = client.getOutputStream();

			DataOutputStream out = new DataOutputStream(outToServer);

			System.out.println("Hello from " + client.getLocalSocketAddress());

			ATP_COMMAND cmd = new ATP_COMMAND();

			cmd.msgType.set(ATP_COMMAND.MSG_TYPE_QUERY);

			cmd.msgID.set(ATP_COMMAND.MSG_ID_INFO);

			cmd.write(out);

			InputStream inFromServer = client.getInputStream();

			DataInputStream in = new DataInputStream(inFromServer);

			ATP_COMMAND msg = new ATP_COMMAND();

			ByteBuffer bf = ByteBuffer.allocate(msg.size());

			byte[] b = new byte[msg.size()];

			bf.clear();

			in.read(b);

			bf.put(b);

			bf.flip();

			msg.getByteBuffer().put(bf);

			System.out.println("Message Type : " + ("0x" + Long.toHexString(msg.msgType.get()).toUpperCase()));

			System.out.println("Message ID : " + ("0x" + Long.toHexString(msg.msgID.get()).toUpperCase()));

			System.out.println("Slot ID : " + msg.params.proccesorInfo.slotID.get());

			System.out.println("Processor : " + msg.params.proccesorInfo.processorID.get());

			System.out.println("Processor Type : " + msg.params.proccesorInfo.processorTYPE);

			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
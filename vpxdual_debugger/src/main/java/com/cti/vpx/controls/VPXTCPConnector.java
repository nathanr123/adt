package com.cti.vpx.controls;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.cti.vpx.command.ATP_COMMAND;

public class VPXTCPConnector {

	private static final int PORT = 12345;

	public static String connet1(String ipaddress) {

		try {
			Socket client = new Socket();

			client.connect(new InetSocketAddress(ipaddress, PORT), 100);

			client.setSoTimeout(1000);

			OutputStream outToServer = client.getOutputStream();

			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeBytes("Who Are YOU?");

			InputStream inFromServer = client.getInputStream();

			DataInputStream in = new DataInputStream(inFromServer);

			byte[] b = new byte[1024];

			in.read(b);

			ByteBuffer b2 = ByteBuffer.allocate(1024);

			b2.put(b);

			b2.flip();

			String s = (new String(b2.array()).trim()).split(";;")[0];

			client.close();

			return s;
		} catch (IOException e) {
			return null;
		}
	}

	public static ATP_COMMAND identifyProcessor(String ipaddress) {

		try {

			Socket client = new Socket();

			client.connect(new InetSocketAddress(ipaddress, PORT), 1000);

			client.setSoTimeout(1000);

			OutputStream outToServer = client.getOutputStream();

			DataOutputStream out = new DataOutputStream(outToServer);

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

			/*
			System.out.println("Message Type : " + ("0x" + Long.toHexString(msg.msgType.get()).toUpperCase()));

			System.out.println("Message ID : " + ("0x" + Long.toHexString(msg.msgID.get()).toUpperCase()));

			System.out.println("Slot ID : " + msg.params.proccesorInfo.slotID.get());

			System.out.println("Processor : " + msg.params.proccesorInfo.processorID.get());

			System.out.println("Processor Type : " + msg.params.proccesorInfo.processorTYPE);
			*/
			client.close();

			return msg;
		} catch (IOException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		VPXTCPConnector.connet1("");
	}
}

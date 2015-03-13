package com.cti.vpx.controls;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class VPXTCPConnector {

	private static final int PORT = 12345;

	public static String connet(String ipaddress) {

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

	public static void main(String[] args) {
		VPXTCPConnector.connet("");
	}
}

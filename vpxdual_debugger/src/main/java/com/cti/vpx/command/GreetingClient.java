package com.cti.vpx.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.swing.UIManager;

import com.cti.vpx.controls.VPX_BusyWindow;
import com.cti.vpx.controls.VPX_FullTestResult;

public class GreetingClient {
	public static void main(String[] args) {

		String serverName = "172.17.10.1";

		int port = 12345;

		try {
			System.out.println("Connecting to " + serverName + " on port " + port);

			Socket client = new Socket();

			client.connect(new InetSocketAddress(serverName, port), 300000);

			client.setSoTimeout(300000);

			System.out.println("Just connected to " + client.getRemoteSocketAddress());

			OutputStream outToServer = client.getOutputStream();

			DataOutputStream out = new DataOutputStream(outToServer);

			System.out.println("Hello from " + client.getLocalSocketAddress());

			ATPCommand cmd = new ATPCommand();

			cmd.msgType.set(ATPCommand.MSG_TYPE_TEST);

			cmd.msgID.set(ATPCommand.MSG_ID_GET);

			cmd.params.testType.set(ATP.TEST_P2020_FULL);

			VPX_BusyWindow busyWindow = new VPX_BusyWindow(null, "Built in Self Test",
					"Complete Testing on process....");

			cmd.write(out);

			long start = System.currentTimeMillis();

			InputStream inFromServer = client.getInputStream();

			DataInputStream in = new DataInputStream(inFromServer);

			ATPCommand msg = new ATPCommand();

			ByteBuffer bf = ByteBuffer.allocate(msg.size());

			byte[] b = new byte[msg.size()];

			boolean isRecieved = true;

		//	while (isRecieved) {
				int i = in.read(b);

				 System.out.println("Listeneing : "+i);
				//System.out.println(b.length);
				//if (i == 104) {

					//isRecieved = false;

					System.out.println("Received");
					//
					bf.clear();

					bf.put(b);

					bf.flip();

					System.out.println(bf.hasArray());
					msg.getByteBuffer().clear();

					msg.getByteBuffer().put(bf);

				//	System.out.println("Received And Packed : " + msg.isPacked());

					long end = System.currentTimeMillis();

					try {
						busyWindow.dispose();

						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

						new VPX_FullTestResult(msg, serverName, start, end);
					} catch (Exception e) {
						e.printStackTrace();
					}
				//}
		//	}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * identification module try { System.out.println("Connecting to " +
		 * serverName + " on port " + port);
		 * 
		 * Socket client = new Socket();
		 * 
		 * client.connect(new InetSocketAddress(serverName, port), 500);
		 * 
		 * client.setSoTimeout(1000);
		 * 
		 * System.out.println("Just connected to " +
		 * client.getRemoteSocketAddress());
		 * 
		 * OutputStream outToServer = client.getOutputStream();
		 * 
		 * DataOutputStream out = new DataOutputStream(outToServer);
		 * 
		 * System.out.println("Hello from " + client.getLocalSocketAddress());
		 * 
		 * ATP_COMMAND cmd = new ATP_COMMAND();
		 * 
		 * cmd.msgType.set(ATP_COMMAND.MSG_TYPE_QUERY);
		 * 
		 * cmd.msgID.set(ATP_COMMAND.MSG_ID_GET);
		 * 
		 * cmd.write(out);
		 * 
		 * InputStream inFromServer = client.getInputStream();
		 * 
		 * DataInputStream in = new DataInputStream(inFromServer);
		 * 
		 * ATP_COMMAND msg = new ATP_COMMAND();
		 * 
		 * ByteBuffer bf = ByteBuffer.allocate(msg.size());
		 * 
		 * byte[] b = new byte[msg.size()];
		 * 
		 * bf.clear();
		 * 
		 * in.read(b);
		 * 
		 * bf.put(b);
		 * 
		 * bf.flip();
		 * 
		 * msg.getByteBuffer().put(bf);
		 * 
		 * System.out.println("Message Type : " + ("0x" +
		 * Long.toHexString(msg.msgType.get()).toUpperCase()));
		 * 
		 * System.out.println("Message ID : " + ("0x" +
		 * Long.toHexString(msg.msgID.get()).toUpperCase()));
		 * 
		 * System.out.println("Slot ID : " +
		 * msg.params.proccesorInfo.slotID.get());
		 * 
		 * System.out.println("Processor : " +
		 * msg.params.proccesorInfo.processorID.get());
		 * 
		 * System.out.println("Processor Type : " +
		 * msg.params.proccesorInfo.processorTYPE);
		 * 
		 * client.close(); } catch (Exception e) { e.printStackTrace(); }
		 */
	}

}
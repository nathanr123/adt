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
import com.cti.vpx.util.VPXUtilities;

public class GreetingClient {
	public static void main(String[] args) {

		// new GreetingClient().identify("172.17.10.21", 12345);

		new GreetingClient().bIST("172.17.1.28", 12345);
	}

	public void bIST(String serverName, int port) {

		try {
			System.out.println("Connecting to " + serverName + " on port " + port);

			Socket client = new Socket();

			client.connect(new InetSocketAddress(serverName, port), 300000);

			client.setSoTimeout(300000);

			System.out.println("Just connected to " + client.getRemoteSocketAddress());

			System.out.println("Hello from " + client.getLocalSocketAddress());

			/*DSPATPCommand cmd = VPXUtilities.createDSPCommand();

			cmd.params.testType.set(ATP.TEST_DSP_FULL);*/

			ATPCommand cmd = VPXUtilities.createATPCommand();
			
			cmd.params.testType.set(ATP.TEST_P2020_FULL);
			
			cmd.msgType.set(ATPCommand.MSG_TYPE_TEST);

			cmd.msgID.set(ATPCommand.MSG_ID_GET);

			VPX_BusyWindow busyWindow = new VPX_BusyWindow(null, "Built in Self Test",
					"Complete Testing on process....");

			cmd.write(client.getOutputStream());

			long start = System.currentTimeMillis();
			
			ATPCommand msg = new ATPCommand();

			msg.read( client.getInputStream());
			
			long end = System.currentTimeMillis();

			try {
				busyWindow.dispose();

				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

				new VPX_FullTestResult(msg, serverName, start, end);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void identify(String serverName, int port) {

		try {
			System.out.println("Connecting to " + serverName + " on port " + port);

			Socket client = new Socket();

			client.connect(new InetSocketAddress(serverName, port), 500);

			client.setSoTimeout(1000);

			System.out.println("Just connected to " + client.getRemoteSocketAddress());

			OutputStream outToServer = client.getOutputStream();

			DataOutputStream out = new DataOutputStream(outToServer);

			System.out.println("Hello from " + client.getLocalSocketAddress());

			ATPCommand cmd = new ATPCommand();

			cmd.msgType.set(ATPCommand.MSG_TYPE_QUERY);

			cmd.msgID.set(ATPCommand.MSG_ID_GET);

			cmd.write(out);

			InputStream inFromServer = client.getInputStream();

			DataInputStream in = new DataInputStream(inFromServer);

			ATPCommand msg = new ATPCommand();

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
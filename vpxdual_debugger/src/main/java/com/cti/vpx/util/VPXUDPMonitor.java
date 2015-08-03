package com.cti.vpx.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

import javax.swing.SwingWorker;

import com.cti.vpx.Listener.AdvertisementListener;
import com.cti.vpx.Listener.CommunicationListener;
import com.cti.vpx.Listener.MessageListener;
import com.cti.vpx.Listener.UDPListener;
import com.cti.vpx.command.ATP;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.P2020ATPCommand;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPXUDPMonitor {

	UDPListener listener;

	private static SubnetFilter subnet = null;

	private VPXCommunicationMonitor communicationMonitor = new VPXCommunicationMonitor();

	private VPXMessageMonitor messageMonitor = new VPXMessageMonitor();

	private VPXAdvertisementMonitor advertisementMonitor = new VPXAdvertisementMonitor();

	private boolean isipinRange = true;

	public VPXUDPMonitor() {

	}

	public VPXUDPMonitor(UDPListener parent) {

		listener = parent;

	}

	public void startMonitor() {

		if (listener != null) {

			start();
		}
	}

	public void stopMonitor() {

		if (listener != null) {

			stop();
		}

	}

	public void applyFilterbySubnet(String subnetmask) {

		subnet = SubnetFilter.createInstance(VPXUtilities.getCurrentIP() + "/" + subnetmask);
	}

	public void clearFilterbySubnet() {

		subnet = null;
	}

	private void start() {

		advertisementMonitor.startMonitor();

		communicationMonitor.startMonitor();

		messageMonitor.startMonitor();
	}

	private void stop() {

		advertisementMonitor.stopMonitor();

		communicationMonitor.stoponitor();

		messageMonitor.stopMonitor();
	}

	public void sendMessageToProcessor(String ip, String msg) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			byte[] buffer = msg.getBytes();

			InetAddress receiverAddress = InetAddress.getByName(ip);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiverAddress,
					UDPListener.CONSOLE_MSG_PORTNO);

			datagramSocket.send(packet);

			// System.out.println("Message : " + msg + " to IP : " + ip);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void setPeriodicity(int period) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket(UDPListener.COMM_PORTNO);

			datagramSocket.setBroadcast(true);

			ATPCommand msg = new ATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.params.periodicity.set(period);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			datagramSocket.send(packet);

			((VPX_ETHWindow) listener).updateLog("Periodicity has changed to " + period + " seconds");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void setPeriodicity(String ip, PROCESSOR_LIST procesor, int period) {

		DatagramSocket datagramSocket;

		ATPCommand msg;
		try {
			datagramSocket = new DatagramSocket();

			if (procesor == PROCESSOR_LIST.PROCESSOR_P2020) {

				msg = new P2020ATPCommand();

			} else {

				msg = new DSPATPCommand();
			}

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.params.periodicity.set(period);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					UDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			((VPX_ETHWindow) listener).updateLog("Periodicity has changed to " + period + " seconds");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void close(String ip) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			P2020ATPCommand msg = new P2020ATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_CLOSE);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					UDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			((VPX_ETHWindow) listener).updateLog("Closing Appliction.");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendCommandToProcessor(String ip, ATP cmd) {

	}

	public void addUDPListener(UDPListener udpListener) {

		this.listener = udpListener;

	}

	class VPXMessageMonitor extends SwingWorker<Void, Void> {

		DatagramSocket messageReceiverSocket;

		byte[] messageData = new byte[1024];

		DatagramPacket messagePacket = new DatagramPacket(messageData, messageData.length);

		public VPXMessageMonitor() {
			try {
				messageReceiverSocket = new DatagramSocket(UDPListener.CONSOLE_MSG_PORTNO);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground() throws Exception {
			while (true) {

				messageReceiverSocket.receive(messagePacket);

				String sentence = new String(messagePacket.getData(), 0, messagePacket.getLength());

				if (sentence.startsWith("M:")) {
					((MessageListener) listener).updateMessage(messagePacket.getAddress().getHostAddress(),
							sentence.substring(2, sentence.length()));

				} else if (sentence.startsWith("C:")) {
					((MessageListener) listener).printConsoleMessage(messagePacket.getAddress().getHostAddress(),
							sentence.substring(2, sentence.length()));
				}

				Thread.sleep(500);
			}
		}

		public void startMonitor() {
			execute();
		}

		public void stopMonitor() {
			cancel(true);
		}
	}

	class VPXCommunicationMonitor extends SwingWorker<Void, Void> {

		DatagramSocket communicationSocket = null;

		byte[] commandData = new byte[1024];

		DatagramPacket commandPacket = new DatagramPacket(commandData, commandData.length);

		public VPXCommunicationMonitor() {
			try {
				communicationSocket = new DatagramSocket(UDPListener.COMM_PORTNO);

			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground() {

			while (true) {

				try {

					communicationSocket.receive(commandPacket);

					((CommunicationListener) listener).updateCommand(new ATPCommand());

					Thread.sleep(500);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		public void startMonitor() {
			execute();
		}

		public void stoponitor() {
			cancel(true);
		}

	}

	class VPXAdvertisementMonitor extends SwingWorker<Void, Void> {

		DatagramSocket advertisementSocket;

		byte[] advertisementData = new byte[6];

		DatagramPacket advertisementPacket = new DatagramPacket(advertisementData, advertisementData.length);

		public VPXAdvertisementMonitor() {

			try {

				advertisementSocket = new DatagramSocket(UDPListener.ADV_PORTNO);

			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground() throws Exception {
			while (true) {

				isipinRange = true;

				advertisementSocket.receive(advertisementPacket);

				if (subnet != null) {

					isipinRange = subnet.isInNet(advertisementPacket.getAddress().getHostAddress());

				}

				if (isipinRange) {

					String sentence = new String(advertisementPacket.getData(), 0, advertisementPacket.getLength());

					System.out.println(sentence);

					((AdvertisementListener) listener).updateProcessorStatus(advertisementPacket.getAddress()
							.getHostAddress(), sentence);
				}

				Thread.sleep(500);
			}
		}

		public void startMonitor() {
			execute();
		}

		public void stopMonitor() {
			cancel(true);
		}
	}
}

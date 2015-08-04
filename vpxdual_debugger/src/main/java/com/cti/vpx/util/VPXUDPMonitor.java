package com.cti.vpx.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import com.cti.vpx.Listener.AdvertisementListener;
import com.cti.vpx.Listener.CommunicationListener;
import com.cti.vpx.Listener.MessageListener;
import com.cti.vpx.Listener.UDPListener;
import com.cti.vpx.command.ATP;
import com.cti.vpx.command.ATP.PROCESSOR_TYPE;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.DSPMessageCommand;
import com.cti.vpx.command.MessageCommand;
import com.cti.vpx.command.P2020ATPCommand;
import com.cti.vpx.command.P2020MessageCommand;
import com.cti.vpx.command.ATP.MESSAGE_MODE;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
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

	public void setPeriodicityByUnicast(int period) {

		VPXSystem sys = VPXUtilities.getVPXSystem();

		List<VPXSubSystem> subs = sys.getSubsystem();

		for (Iterator<VPXSubSystem> iterator = subs.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			sendPeriodicity(vpxSubSystem.getIpP2020(), period, PROCESSOR_LIST.PROCESSOR_P2020);

			sendPeriodicity(vpxSubSystem.getIpDSP1(), period, PROCESSOR_LIST.PROCESSOR_DSP1);

			sendPeriodicity(vpxSubSystem.getIpDSP2(), period, PROCESSOR_LIST.PROCESSOR_DSP2);

		}

		List<Processor> unlist = sys.getUnListed();

		for (Iterator<Processor> iterator = unlist.iterator(); iterator.hasNext();) {

			Processor processor = iterator.next();

			if (processor.getName().contains("P2020")) {

				sendPeriodicity(processor.getiP_Addresses(), period, PROCESSOR_LIST.PROCESSOR_P2020);

			} else {

				sendPeriodicity(processor.getiP_Addresses(), period, PROCESSOR_LIST.PROCESSOR_DSP2);

			}

		}

	}

	public void setPeriodicityByBroadcast(int period) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			datagramSocket.setBroadcast(true);

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.params.periodicity.set(period);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, VPXUtilities.getCurrentInterfaceAddress()
					.getBroadcast(), UDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			P2020ATPCommand msg1 = new P2020ATPCommand();

			byte[] buffer1 = new byte[msg1.size()];

			ByteBuffer bf1 = ByteBuffer.wrap(buffer1);

			bf1.order(msg1.byteOrder());

			msg1.setByteBuffer(bf1, 0);

			msg1.msgID.set(ATP.MSG_ID_SET);

			msg1.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg1.params.periodicity.set(period);

			DatagramPacket packet1 = new DatagramPacket(buffer1, buffer1.length, VPXUtilities
					.getCurrentInterfaceAddress().getBroadcast(), UDPListener.COMM_PORTNO);

			datagramSocket.send(packet1);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendPeriodicity(String ip, int period, PROCESSOR_LIST procesor) {

		DatagramSocket datagramSocket;

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			datagramSocket = new DatagramSocket();

			msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.params.periodicity.set(period);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					UDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendPeriodicity(String ip, int period) {

		DatagramSocket datagramSocket;

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			datagramSocket = new DatagramSocket();

			PROCESSOR_LIST procesor = VPXUtilities.getProcessorType(ip);

			msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.params.periodicity.set(period);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					UDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendMessageToProcessor(String ip, int core, String message) {

		DatagramSocket datagramSocket;

		MessageCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {
			datagramSocket = new DatagramSocket();

			msg = (VPXUtilities.getProcessorType(ip) == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020MessageCommand()
					: new DSPMessageCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.mode.set(MESSAGE_MODE.MSG_MODE_MESSAGE);

			msg.core.set(core);

			msg.command_msg.set(message);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					UDPListener.CONSOLE_MSG_PORTNO);

			datagramSocket.send(packet);

			((VPX_ETHWindow) listener).updateLog("Message sent to " + ip);

		} catch (Exception e) {

			e.printStackTrace();
		}

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

	public void sendCommandToProcessor(String ip, ATP cmd) {

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

	public void addUDPListener(UDPListener udpListener) {

		this.listener = udpListener;

	}

	class VPXMessageMonitor extends SwingWorker<Void, Void> {

		DatagramSocket messageReceiverSocket;

		MessageCommand msg = null;

		byte[] messageData = null;

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

				if (VPXUtilities.getProcessorType(messagePacket.getAddress().getHostAddress()) == PROCESSOR_LIST.PROCESSOR_P2020) {

					msg = new P2020MessageCommand();
				} else {
					msg = new DSPMessageCommand();
				}

				messageData = new byte[msg.size()];

				ByteBuffer bf = ByteBuffer.allocate(msg.size());

				bf.clear();

				bf.put(messageData);

				bf.flip();

				msg.getByteBuffer().clear();

				msg.getByteBuffer().put(bf);

				if (msg.mode.get() == ATP.MESSAGE_MODE.MSG_MODE_CONSOLE) {

					((MessageListener) listener).printConsoleMessage(messagePacket.getAddress().getHostAddress(), msg);

				} else if (msg.mode.get() == ATP.MESSAGE_MODE.MSG_MODE_MESSAGE) {

					((MessageListener) listener).updateMessage(messagePacket.getAddress().getHostAddress(), msg);

				}

				/*
				 * String sentence = new String(messagePacket.getData(), 0,
				 * messagePacket.getLength());
				 * 
				 * if (sentence.startsWith("M:")) { ((MessageListener)
				 * listener).
				 * updateMessage(messagePacket.getAddress().getHostAddress(),
				 * sentence.substring(2, sentence.length()));
				 * 
				 * } else if (sentence.startsWith("C:")) { ((MessageListener)
				 * listener
				 * ).printConsoleMessage(messagePacket.getAddress().getHostAddress
				 * (), sentence.substring(2, sentence.length())); }
				 */
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

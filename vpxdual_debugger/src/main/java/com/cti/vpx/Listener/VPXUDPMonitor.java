package com.cti.vpx.Listener;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import com.cti.vpx.command.ATP;
import com.cti.vpx.command.ATP.MESSAGE_MODE;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.DSPMSGCommand;
import com.cti.vpx.command.MSGCommand;
import com.cti.vpx.command.P2020ATPCommand;
import com.cti.vpx.command.P2020MSGCommand;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.SubnetFilter;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPXUDPMonitor {

	VPXUDPListener listener;

	private static SubnetFilter subnet = null;

	private VPXCommunicationMonitor communicationMonitor;

	private VPXAdvertisementMonitor advertisementMonitor;

	private boolean isipinRange = true;

	public VPXUDPMonitor() throws Exception {

		createDefaultMonitors();
	}

	public VPXUDPMonitor(VPXUDPListener parent) throws Exception {

		listener = parent;

		createDefaultMonitors();

	}

	private void createDefaultMonitors() throws Exception {

		communicationMonitor = new VPXCommunicationMonitor();

		advertisementMonitor = new VPXAdvertisementMonitor();
	}

	public void startMonitor() throws Exception {

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

	private void start() throws Exception {

		advertisementMonitor.startMonitor();

		communicationMonitor.startMonitor();

		Thread th = new Thread(new VPXMessageConsoleMonitor());

		th.start();
	}

	private void stop() {

		advertisementMonitor.stopMonitor();

		communicationMonitor.stopMonitor();

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
					.getBroadcast(), VPXUDPListener.COMM_PORTNO);

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
					.getCurrentInterfaceAddress().getBroadcast(), VPXUDPListener.COMM_PORTNO);

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
					VPXUDPListener.COMM_PORTNO);

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

			PROCESSOR_LIST procesor = getProcType(ip);// VPXUtilities.getProcessorType(ip);

			String recvip = getProcIP(ip);

			msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.params.periodicity.set(period);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(recvip),
					VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private PROCESSOR_LIST getProcType(String ip) {

		PROCESSOR_LIST proc = PROCESSOR_LIST.PROCESSOR_P2020;

		if (ip.contains("P2020")) {

			proc = PROCESSOR_LIST.PROCESSOR_P2020;

		} else if (ip.contains("DSP1")) {

			proc = PROCESSOR_LIST.PROCESSOR_DSP1;

		} else if (ip.contains("DSP2")) {

			proc = PROCESSOR_LIST.PROCESSOR_DSP2;
		}

		return proc;
	}

	private String getProcIP(String ip) {

		return ip.trim().substring(ip.indexOf(")") + 1);
	}

	public void sendMessageToProcessor(String ip, int core, String message) {

		DatagramSocket datagramSocket;

		MSGCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {
			datagramSocket = new DatagramSocket();

			PROCESSOR_LIST processor = VPXUtilities.getProcessorType(ip);

			msg = (processor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020MSGCommand() : new DSPMSGCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.mode.set(MESSAGE_MODE.MSG_MODE_MESSAGE);

			msg.core.set(core);

			msg.command_msg.set(message);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.CONSOLE_MSG_PORTNO);

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
					VPXUDPListener.CONSOLE_MSG_PORTNO);

			datagramSocket.send(packet);

			// System.out.println("Message : " + msg + " to IP : " + ip);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendCommandToProcessor(String ip, ATP cmd) {

	}

	public void close() {

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

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("0.0.0.0"),
					VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			((VPX_ETHWindow) listener).updateLog("Closing Appliction.");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void addUDPListener(VPXUDPListener udpListener) {

		this.listener = udpListener;

	}

	// Parsing Commication Packets
	private synchronized void parseCommunicationPacket(String ip, ATPCommand msgCommand) {

		int msgID = (int) msgCommand.msgID.get();

		int msgType = (int) msgCommand.msgType.get();

		if (msgID == ATP.MSG_ID_SET) {

			switch (msgType) {

			case ATP.MSG_TYPE_FLASH:

				// recvAndSaveFile(ip, msgCommand);

				break;

			case ATP.MSG_TYPE_FLASH_ACK:

				// sendNextPacket(ip, msgCommand);

				break;

			case ATP.MSG_TYPE_PERIDAICITY:

				break;

			case ATP.MSG_TYPE_BIST:

				break;

			case ATP.MSG_TYPE_MEMORY:

				break;
			}

		} else if (msgID == ATP.MSG_ID_GET) {

			switch (msgType) {

			case ATP.MSG_TYPE_FLASH:

				break;

			case ATP.MSG_TYPE_FLASH_ACK:

				break;

			case ATP.MSG_TYPE_PERIDAICITY:

				break;

			case ATP.MSG_TYPE_BIST:

				// populateBISTResult(msgCommand);

				break;

			case ATP.MSG_TYPE_MEMORY:

				break;
			}

		}

	}

	// Parsing Advertisement Packets
	private synchronized void parseAdvertisementPacket(String ip, String msg) {

		if (subnet != null) {

			isipinRange = subnet.isInNet(ip);

		}

		if (isipinRange) {

			((VPXAdvertisementListener) listener).updateProcessorStatus(ip, msg);
		}

	}

	// Parsing Advertisement Packets
	private synchronized void parseMessagePacket(String ip, MSGCommand msgCommand) {

		if (msgCommand.mode.get() == ATP.MESSAGE_MODE.MSG_MODE_CONSOLE) {

			((VPXMessageListener) listener).printConsoleMessage(ip, msgCommand);

		} else if (msgCommand.mode.get() == ATP.MESSAGE_MODE.MSG_MODE_MESSAGE) {

			((VPXMessageListener) listener).updateMessage(ip, msgCommand);

		}

	}

	private ATPCommand createATPCommand(String ip, byte[] recvdBytes) {

		ATPCommand msgCommand = new ATPCommand();

		ByteBuffer bf = ByteBuffer.allocate(msgCommand.size());

		if (VPXUtilities.getProcessorType(ip) == PROCESSOR_LIST.PROCESSOR_P2020) {

			msgCommand = new P2020ATPCommand();

		} else {

			msgCommand = new DSPATPCommand();
		}

		bf.clear();

		bf.put(recvdBytes);

		bf.flip();

		msgCommand.getByteBuffer().clear();

		msgCommand.getByteBuffer().put(bf);

		return msgCommand;
	}

	private MSGCommand createMSGCommand(String ip, byte[] recvdBytes) {

		MSGCommand msgCommand = new MSGCommand();

		ByteBuffer bf = ByteBuffer.allocate(msgCommand.size());

		if (VPXUtilities.getProcessorType(ip) == PROCESSOR_LIST.PROCESSOR_P2020) {

			msgCommand = new P2020MSGCommand();

		} else {

			msgCommand = new DSPMSGCommand();
		}

		bf.clear();

		bf.put(recvdBytes);

		bf.flip();

		msgCommand.getByteBuffer().clear();

		msgCommand.getByteBuffer().put(bf);

		return msgCommand;
	}

	// Monitors Starts

	// Message Monitor
	class VPXMessageConsoleMonitor implements Runnable {

		DatagramSocket messageReceiverSocket;

		byte[] messageData = new byte[MSGCommand.getSize()];

		DatagramPacket messagePacket = new DatagramPacket(messageData, messageData.length);

		public VPXMessageConsoleMonitor() throws Exception {

			messageReceiverSocket = new DatagramSocket(VPXUDPListener.CONSOLE_MSG_PORTNO);

		}

		@Override
		public void run() {

			while (true) {
				try {

					messageReceiverSocket.receive(messagePacket);

					String ip = messagePacket.getAddress().getHostAddress();

					parseMessagePacket(ip, createMSGCommand(ip, messageData));

					Thread.sleep(500);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	// Communication Monitor
	class VPXCommunicationMonitor extends SwingWorker<Void, Void> {

		DatagramSocket communicationSocket = null;

		byte[] commandData = new byte[ATPCommand.getSize()];

		DatagramPacket messagePacket = new DatagramPacket(commandData, commandData.length);

		public VPXCommunicationMonitor() throws Exception {

			communicationSocket = new DatagramSocket(VPXUDPListener.COMM_PORTNO);
		}

		@Override
		protected Void doInBackground() {

			while (true) {

				try {

					communicationSocket.receive(messagePacket);

					String ip = messagePacket.getAddress().getHostAddress();

					parseCommunicationPacket(ip, createATPCommand(ip, commandData));

					Thread.sleep(500);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		public void startMonitor() {
			execute();
		}

		public void stopMonitor() {
			cancel(true);
		}

	}

	// Advertisement Monitor
	class VPXAdvertisementMonitor extends SwingWorker<Void, Void> {

		DatagramSocket advertisementSocket;

		byte[] advertisementData = new byte[6];

		DatagramPacket advertisementPacket = new DatagramPacket(advertisementData, advertisementData.length);

		public VPXAdvertisementMonitor() throws Exception {

			advertisementSocket = new DatagramSocket(VPXUDPListener.ADV_PORTNO);
		}

		@Override
		protected Void doInBackground() throws Exception {

			while (true) {

				isipinRange = true;

				advertisementSocket.receive(advertisementPacket);

				parseAdvertisementPacket(advertisementPacket.getAddress().getHostAddress(), new String(
						advertisementPacket.getData(), 0, advertisementPacket.getLength()));

				Thread.sleep(1000);
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

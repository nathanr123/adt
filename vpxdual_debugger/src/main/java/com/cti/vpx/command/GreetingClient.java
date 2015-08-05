package com.cti.vpx.command;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;

import com.cti.vpx.Listener.UDPListener;
import com.cti.vpx.command.ATP.MESSAGE_MODE;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXUtilities;

public class GreetingClient {
	public static void main(String[] args) {

		// new GreetingClient().identify("172.17.10.21", 12345);

		new GreetingClient();

	}

	private JTextArea jt;
	private byte[] filestoSend;
	private byte[] filesfromRecv;
	private int offset = 0;
	private int start;
	private int end;
	private int tot;
	private long size;

	public GreetingClient() {

		VPXUtilities.setCurrentNWIface("172.17.1.28");

		JFrame f = new JFrame();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setBounds(10, 10, 500, 500);

		JPanel jp = new JPanel();

		jt = new JTextArea();

		jp.setPreferredSize(new Dimension(500, 50));

		JButton jb = new JButton("Send Command");

		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// sendCMD();

				VPXSystem vpx = new VPXSystem();

				List<VPXSubSystem> sub = new ArrayList<VPXSubSystem>();

				sub.add(new VPXSubSystem(1, "Sub", "172.17.10.1", "172.17.10.130", "172.17.1.131"));

				vpx.setSubsystem(sub);

				VPXUtilities.setVPXSystem(vpx);

				setPeriodicityByUnicast(4);

			}
		});

		jp.add(jb);

		JButton jb1 = new JButton("Send Message");

		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// sendCMD();

				sendMessage();

			}
		});

		jp.add(jb1);

		JButton jb2 = new JButton("Send File");

		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// sendCMD();

				sendFile();

			}
		});

		jp.add(jb2);

		f.getContentPane().setLayout(new BorderLayout());

		f.getContentPane().add(new JScrollPane(jt));

		f.getContentPane().add(jp, BorderLayout.SOUTH);

		f.setVisible(true);

		startMessage();

		startCMD();

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

	public void setPeriodicityByBradcast(int period) {

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

	private void startMessage() {
		Thread th = new Thread(new MThreadMonitor());

		th.start();

	}

	private void startCMD() {
		Thread th = new Thread(new CThreadMonitor());

		th.start();
	}

	public void sendCMD() {
		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(ATP.BYTEORDER_DSP);

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.params.periodicity.set(5);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("172.17.10.130"),
					UDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			jt.append("Message Sent\n");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendMessage() {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			DSPMessageCommand msg = new DSPMessageCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());
			msg.setByteBuffer(bf, 0);

			msg.mode.set(MESSAGE_MODE.MSG_MODE_MESSAGE);

			msg.core.set(0);

			msg.command_msg.set("memget 0xa0000000");
			// msg.command_msg.set("temp1");

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("172.17.10.130"),
					UDPListener.CONSOLE_MSG_PORTNO);

			datagramSocket.send(packet);

			jt.append("Message Sent\n");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	class MThreadMonitor implements Runnable {
		DatagramSocket messageReceiverSocket;

		MessageCommand msgCommand = new DSPMessageCommand();

		byte[] messageData = new byte[msgCommand.size()];

		DatagramPacket messagePacket = new DatagramPacket(messageData, messageData.length);

		public MThreadMonitor() {
			try {
				messageReceiverSocket = new DatagramSocket(UDPListener.CONSOLE_MSG_PORTNO);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			ByteBuffer bf = ByteBuffer.allocate(msgCommand.size());

			while (true) {
				try {

					messageReceiverSocket.receive(messagePacket);

					bf.clear();

					bf.put(messageData);

					bf.flip();

					msgCommand.getByteBuffer().clear();

					msgCommand.getByteBuffer().put(bf);

					jt.append(msgCommand.mode + "\n");

					jt.append(msgCommand.command_msg + "\n");

					Thread.sleep(500);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	class CThreadMonitor implements Runnable {
		DatagramSocket messageReceiverSocket;

		ATPCommand msgCommand = new ATPCommand();

		byte[] messageData = new byte[msgCommand.size()];

		DatagramPacket messagePacket = new DatagramPacket(messageData, messageData.length);

		public CThreadMonitor() {
			try {
				messageReceiverSocket = new DatagramSocket(UDPListener.COMM_PORTNO);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			ByteBuffer bf = ByteBuffer.allocate(msgCommand.size());

			while (true) {
				try {

					messageReceiverSocket.receive(messagePacket);

					bf.clear();

					bf.put(messageData);

					bf.flip();

					msgCommand.getByteBuffer().clear();

					msgCommand.getByteBuffer().put(bf);

					if (msgCommand.msgID.get() == ATP.MSG_ID_SET) {

						int i = (int) msgCommand.msgType.get();

						if (i == ATP.MSG_TYPE_FLASH) {
							recvAndSaveFile(messagePacket.getAddress().getHostAddress(), msgCommand);
						} else if (i == ATP.MSG_TYPE_FLASH_ACK) {
							sendNextPacket(messagePacket.getAddress().getHostAddress(), msgCommand);
						}

					}

					Thread.sleep(500);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	public void sendFile() {

		try {

			File f = new File("e:\\bookmarks_2_23_15.html");

			size = FileUtils.sizeOf(f);

			filestoSend = FileUtils.readFileToByteArray(f);

			byte b[] = new byte[1024];

			System.arraycopy(filestoSend, 0, b, 0, 1024);

			sendFileToProcessor("192.168.0.100", FileUtils.sizeOf(f), b);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendFileToProcessor(String ip, long size, byte[] sendBuffer) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			datagramSocket.setBroadcast(true);

			ATPCommand msg = new ATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_FLASH);

			msg.params.flash_info.flashdevice.set(ATP.FLASH_DEVICE_NOR);

			msg.params.flash_info.totalfilesize.set(size);

			tot = (int) (size / 1024);

			int rem = (int) (size % 1024);

			if (rem > 0)
				tot++;

			msg.params.flash_info.totalnoofpackets.set(tot);

			for (int i = 0; i < sendBuffer.length; i++) {

				msg.params.memoryinfo.buffer[i].set(sendBuffer[i]);
			}

			msg.params.flash_info.currentpacket.set(0);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					UDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void recvAndSaveFile(String ip, ATPCommand msg) {

		int currPacket = (int) msg.params.flash_info.currentpacket.get();

		if (currPacket == 0) {
			filesfromRecv = new byte[(int) msg.params.flash_info.totalfilesize.get()];
		}
		start = currPacket * msg.params.memoryinfo.buffer.length;

		end = start + msg.params.memoryinfo.buffer.length;
		
		if(end > filestoSend.length) {
			end = filestoSend.length;
		}

		for (int i = start, j = 0; i < end; i++, j++) {
		
				filesfromRecv[i] = (byte) msg.params.memoryinfo.buffer[j].get();
		
		}
		//System.out.println(currPacket);

		// Sending part
		
		System.out.println(currPacket +" "+ msg.params.flash_info.totalnoofpackets.get());

		if (currPacket < msg.params.flash_info.totalnoofpackets.get()) {

			DatagramSocket datagramSocket;

			try {
				datagramSocket = new DatagramSocket();

				datagramSocket.setBroadcast(true);

				byte[] buffer = new byte[msg.size()];

				ByteBuffer bf = ByteBuffer.wrap(buffer);

				bf.order(msg.byteOrder());

				msg.setByteBuffer(bf, 0);

				msg.msgID.set(ATP.MSG_ID_SET);

				msg.msgType.set(ATP.MSG_TYPE_FLASH_ACK);

				msg.params.flash_info.flashdevice.set(ATP.FLASH_DEVICE_NOR);

				msg.params.flash_info.currentpacket.set(currPacket);

				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
						UDPListener.COMM_PORTNO);

				datagramSocket.send(packet);

			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			try {
				FileOutputStream fos = new FileOutputStream("D:\\Test1.html");
				fos.write(filesfromRecv);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void sendNextPacket(String ip, ATPCommand msg) {

		DatagramSocket datagramSocket;

		int currPacket = (int) msg.params.flash_info.currentpacket.get();

		currPacket++;

		try {
			datagramSocket = new DatagramSocket();

			datagramSocket.setBroadcast(true);

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_FLASH);

			msg.params.flash_info.flashdevice.set(ATP.FLASH_DEVICE_NOR);

			start = currPacket * 1024;

			end = start + 1024;

			if (end > filestoSend.length) {
				end = filestoSend.length;
			}
			System.out.println(filestoSend.length + " " + end);

			for (int i = start, j = 0; i < end; i++, j++) {

				msg.params.memoryinfo.buffer[j].set(filestoSend[i]);
			}

			msg.params.flash_info.totalfilesize.set(size);
			msg.params.flash_info.totalnoofpackets.set(tot);

			msg.params.flash_info.currentpacket.set(currPacket);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					UDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
package com.cti.vpx.command;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javolution.io.Struct.Unsigned32;

import org.apache.commons.io.FileUtils;

import com.cti.vpx.Listener.VPXUDPListener;
import com.cti.vpx.command.ATP.MESSAGE_MODE;
import com.cti.vpx.controls.VPX_FlashProgressWindow;
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
	private VPX_FlashProgressWindow dialog;
	private JFrame f;
	private FileBytesToSend fb;

	public GreetingClient() {

		// VPXUtilities.setCurrentNWIface("172.17.1.28");

		f = new JFrame();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setBounds(10, 10, 500, 500);

		JPanel jp = new JPanel();

		jt = new JTextArea();

		jp.setPreferredSize(new Dimension(500, 50));

		JButton jb = new JButton("Send Command");

		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendCMD();

				/*
				 * VPXSystem vpx = new VPXSystem();
				 * 
				 * List<VPXSubSystem> sub = new ArrayList<VPXSubSystem>();
				 * 
				 * sub.add(new VPXSubSystem(1, "Sub", "172.17.10.1",
				 * "172.17.10.130", "172.17.1.131"));
				 * 
				 * vpx.setSubsystem(sub);
				 * 
				 * VPXUtilities.setVPXSystem(vpx);
				 * 
				 * setPeriodicityByUnicast(4);
				 */

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

		JButton jb3 = new JButton("Start BIST");

		jb3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// sendCMD();

				startBist();

			}
		});

		jp.add(jb3);

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

	public void populateBISTResult(ATPCommand msg) {

		/*
		 * System.out.println(msg.params.testinfo.RESULT_P2020_PROCESSOR);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_DDR3);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_NORFLASH);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_ETHERNET);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_SRIO);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_PCIE);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_TEMP1);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_TEMP2);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_TEMP3);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_VOLT1_3p3);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_VOLT2_2p5);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_VOLT3_1p8);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_VOLT4_1p5);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_VOLT5_1p2);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_VOLT6_1p0);
		 * 
		 * System.out.println(msg.params.testinfo.RESULT_P2020_VOLT7_1p05);
		 */

		System.out.println(msg.params.testinfo.RESULT_DSP_DDR3);

		System.out.println(msg.params.testinfo.RESULT_DSP_NAND);

		System.out.println(msg.params.testinfo.RESULT_DSP_NOR);

		System.out.println(msg.params.testinfo.RESULT_DSP_PROCESSOR);

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
		/*
		 * DatagramSocket datagramSocket;
		 * 
		 * try {
		 * 
		 * 
		 * datagramSocket = new DatagramSocket();
		 * 
		 * DSPATPCommand msg = new DSPATPCommand();
		 * 
		 * byte[] buffer = new byte[msg.size()];
		 * 
		 * ByteBuffer bf = ByteBuffer.wrap(buffer);
		 * 
		 * bf.order(msg.byteOrder()); msg.setByteBuffer(bf, 0);
		 * 
		 * msg.msgID.set(ATP.MSG_ID_SET);
		 * 
		 * msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);
		 * 
		 * msg.params.periodicity.set(5);
		 * 
		 * DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
		 * InetAddress.getByName("172.17.10.130"), VPXUDPListener.COMM_PORTNO);
		 * 
		 * datagramSocket.send(packet);
		 * 
		 * jt.append("Message Sent\n");
		 * 
		 * } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 */

		sendPeriodicity("172.17.10.130", 5, PROCESSOR_LIST.PROCESSOR_DSP1);

	}

	public void startBist() {

		DatagramSocket datagramSocket;

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			datagramSocket = new DatagramSocket();

			msg = new P2020ATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_GET);

			msg.msgType.set(ATP.MSG_TYPE_BIST);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("172.17.10.1"),
					VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendMessage() {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			DSPMSGCommand msg = new DSPMSGCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());
			msg.setByteBuffer(bf, 0);

			msg.mode.set(MESSAGE_MODE.MSG_MODE_MESSAGE);

			msg.core.set(0);

			msg.command_msg.set("memget 0xa0000000");
			// msg.command_msg.set("temp1");

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("172.17.10.130"),
					VPXUDPListener.CONSOLE_MSG_PORTNO);

			datagramSocket.send(packet);

			jt.append("Message Sent\n");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	class MThreadMonitor implements Runnable {
		DatagramSocket messageReceiverSocket;

		MSGCommand msgCommand = new DSPMSGCommand();

		byte[] messageData = new byte[msgCommand.size()];

		DatagramPacket messagePacket = new DatagramPacket(messageData, messageData.length);

		public MThreadMonitor() {
			try {
				messageReceiverSocket = new DatagramSocket(VPXUDPListener.CONSOLE_MSG_PORTNO);
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
				messageReceiverSocket = new DatagramSocket(VPXUDPListener.COMM_PORTNO);
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

						} else if (i == ATP.MSG_TYPE_PERIDAICITY) {

							System.out.println("Periodcity : " + msgCommand.params.periodicity);
						} else if (i == ATP.MSG_TYPE_FLASH_DONE) {

							JOptionPane.showMessageDialog(null, "Flash Completed");
						}

					} else if (msgCommand.msgID.get() == ATP.MSG_ID_GET) {
						populateBISTResult(msgCommand);
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

			File f = new File("D:\\UARTTestProject.bin");

			size = FileUtils.sizeOf(f);

			filestoSend = FileUtils.readFileToByteArray(f);

			Map<Long, byte[]> t = divideArrayAsMap(filestoSend, ATP.DEFAULTBUFFERSIZE);

			fb = new FileBytesToSend(size, t);

			byte b[] = new byte[ATP.DEFAULTBUFFERSIZE];

			for (int i = 0; i < b.length; i++) {
				b[i] = filestoSend[i];
			}

			dialog = new VPX_FlashProgressWindow(GreetingClient.this.f);

			dialog.setVisible(true);

			sendFileToProcessor("172.17.10.130", FileUtils.sizeOf(f), fb.getBytePacket(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<byte[]> divideArray(byte[] source, int chunksize) {

		List<byte[]> result = new ArrayList<byte[]>();

		int start = 0;

		while (start < source.length) {

			int end = Math.min(source.length, start + chunksize);

			result.add(Arrays.copyOfRange(source, start, end));

			start += chunksize;
		}

		return result;
	}

	public static Map<Long, byte[]> divideArrayAsMap(byte[] source, int chunksize) {

		Map<Long, byte[]> bytesMap = new LinkedHashMap<Long, byte[]>();

		int start = 0;

		long i = 0;

		while (start < source.length) {

			int end = Math.min(source.length, start + chunksize);

			bytesMap.put(i, Arrays.copyOfRange(source, start, end));

			start += chunksize;

			i++;
		}

		return bytesMap;
	}

	public void sendFileToProcessor(String ip, long size, byte[] sendBuffer) {

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

			msg.msgType.set(ATP.MSG_TYPE_FLASH);

			msg.params.flash_info.flashdevice.set(ATP.FLASH_DEVICE_NOR);

			msg.params.flash_info.totalfilesize.set(size);

			tot = (int) (size / ATP.DEFAULTBUFFERSIZE);

			int rem = (int) (size % ATP.DEFAULTBUFFERSIZE);

			if (rem > 0)
				tot++;

			msg.params.flash_info.totalnoofpackets.set(tot);

			msg.params.memoryinfo.byteZero.set(sendBuffer[0]);

			for (int i = 0; i < sendBuffer.length; i++) {

				msg.params.memoryinfo.buffer[i].set(sendBuffer[i]);
			
				System.out.println(sendBuffer[i]);
			
			}

			msg.params.flash_info.currentpacket.set(0);

			dialog.updatePackets(size, tot, 0);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.COMM_PORTNO);

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

		if (end > filestoSend.length) {
			end = filestoSend.length;
		}

		for (int i = start, j = 0; i < end; i++, j++) {

			filesfromRecv[i] = (byte) msg.params.memoryinfo.buffer[j].get();
		}


		// Sending part

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
						VPXUDPListener.COMM_PORTNO);

				datagramSocket.send(packet);

			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			try {
				FileOutputStream fos = new FileOutputStream("D:\\WinRegistry1.java");
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

		if (currPacket <= tot) {
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

				dialog.updatePackets(size, tot, currPacket);

				byte[] bb = fb.getBytePacket(currPacket);
				if (bb != null) {

					msg.params.memoryinfo.byteZero.set(bb[0]);

					for (int i = 0; i < bb.length; i++) {
						msg.params.memoryinfo.buffer[i].set(bb[i]);
						
							System.out.println(bb[i]);
						
					}
				}
				msg.params.flash_info.totalfilesize.set(size);
				msg.params.flash_info.totalnoofpackets.set(tot);

				msg.params.flash_info.currentpacket.set(currPacket);
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
						VPXUDPListener.COMM_PORTNO);

				datagramSocket.send(packet);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

	public class FileBytesToSend implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private long fileSize;

		private long totalpackets;

		private long currentPacket;

		private long remainigPacket;

		private Map<Long, byte[]> bytesMap = new LinkedHashMap<Long, byte[]>();

		private Iterator<Entry<Long, byte[]>> iteratorByteArray;

		/**
		 * @param fileSize
		 * @param totalpackets
		 * @param currentPacket
		 * @param remainigPacket
		 */
		public FileBytesToSend(long fileSize, long totalpackets, long currentPacket, long remainigPacket) {

			super();

			this.fileSize = fileSize;

			this.totalpackets = totalpackets;

			this.currentPacket = currentPacket;

			this.remainigPacket = remainigPacket;
		}

		public FileBytesToSend(long fileSize, Map<Long, byte[]> map) {

			super();

			this.fileSize = fileSize;

			this.bytesMap = map;

			setTotalpackets(map.size());

			setCurrentPacket(0);

			setIterator();

		}

		/**
		 * @return the fileSize
		 */
		public long getFileSize() {
			return fileSize;
		}

		/**
		 * @param fileSize
		 *            the fileSize to set
		 */
		public void setFileSize(long fileSize) {
			this.fileSize = fileSize;
		}

		/**
		 * @return the totalpackets
		 */
		public long getTotalpackets() {
			return totalpackets;
		}

		/**
		 * @param totalpackets
		 *            the totalpackets to set
		 */
		public void setTotalpackets(long totalpackets) {
			this.totalpackets = totalpackets;
		}

		/**
		 * @return the currentPacket
		 */
		public long getCurrentPacket() {
			return currentPacket;
		}

		/**
		 * @param currentPacket
		 *            the currentPacket to set
		 */
		public void setCurrentPacket(long currentPacket) {

			this.remainigPacket = this.totalpackets - currentPacket;

			this.currentPacket = currentPacket;
		}

		/**
		 * @return the remainigPacket
		 */
		public long getRemainigPacket() {
			return remainigPacket;
		}

		/**
		 * @param remainigPacket
		 *            the remainigPacket to set
		 */
		public void setRemainigPacket(long remainigPacket) {
			this.remainigPacket = remainigPacket;
		}

		/**
		 * @return the bytesMap
		 */
		public Map<Long, byte[]> getBytesMap() {
			return bytesMap;
		}

		/**
		 * @param bytesMap
		 *            the bytesMap to set
		 */
		public void setBytesMap(Map<Long, byte[]> bytesMap) {

			this.bytesMap = bytesMap;

			setIterator();
		}

		public byte[] getBytePacket(long index) {

			return bytesMap.get(index);
		}

		public byte[] getCurrentBytePacket() {

			return bytesMap.get(currentPacket);
		}

		public byte[] getNextPacket() {

			long temp = currentPacket++;

			setCurrentPacket(currentPacket);

			if (bytesMap.containsKey(temp))

				return bytesMap.get(temp);
			else
				return null;
		}

		public byte[] getNext() {

			if (iteratorByteArray != null) {
				if (iteratorByteArray.hasNext()) {

					Entry<Long, byte[]> e = iteratorByteArray.next();

					currentPacket = e.getKey();

					return e.getValue();

				} else
					return null;
			} else
				return null;
		}

		public void setIterator() {

			Set<Entry<Long, byte[]>> set = bytesMap.entrySet();

			iteratorByteArray = set.iterator();

		}
	}

}
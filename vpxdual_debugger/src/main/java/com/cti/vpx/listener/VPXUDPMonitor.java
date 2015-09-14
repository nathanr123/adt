package com.cti.vpx.listener;

import java.io.File;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.cti.vpx.command.ATP;
import com.cti.vpx.command.ATP.MESSAGE_MODE;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.DSPMSGCommand;
import com.cti.vpx.command.MSGCommand;
import com.cti.vpx.command.P2020ATPCommand;
import com.cti.vpx.command.P2020MSGCommand;
import com.cti.vpx.controls.VPX_FlashProgressWindow;
import com.cti.vpx.controls.hex.HexEditorDemoApp;
import com.cti.vpx.controls.hex.MemoryViewFilter;
import com.cti.vpx.model.BIST;
import com.cti.vpx.model.FileBytesToSend;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXSubnetFilter;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPXUDPMonitor {

	VPXUDPListener listener;

	private static VPXSubnetFilter subnet = null;

	private VPXCommunicationMonitor communicationMonitor;

	private VPXAdvertisementMonitor advertisementMonitor;

	private boolean isipinRange = true;

	private byte[] filestoSend;

	// private byte[] filesfromRecv;

	private int start;

	private int end;

	private int tot;

	private long size;

	private VPX_FlashProgressWindow dialog;

	private FileBytesToSend fb;

	private BIST bist = null;

	private int pass;

	private int fail;

	private int loop = 0;

	private static boolean isFlashingStatred = false;

	private VPXSystem vpxSystem;

	private byte[] bf;

	public VPXUDPMonitor() throws Exception {

		vpxSystem = VPXSessionManager.getVPXSystem();

		createDefaultMonitors();
	}

	public VPXUDPMonitor(VPXUDPListener parent) throws Exception {

		listener = parent;

		vpxSystem = VPXSessionManager.getVPXSystem();

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

		subnet = VPXSubnetFilter.createInstance(VPXSessionManager.getCurrentIP() + "/" + subnetmask);
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

	public void sendBoot(String ip) {

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			msg = new P2020ATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_BOOT);

			send(buffer, ip, VPXUDPListener.COMM_PORTNO, false);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void setPeriodicityByUnicast(int period) {

		VPXSystem sys = VPXSessionManager.getVPXSystem();

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

		try {

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.periodicity.set(period);

			send(buffer, VPXUtilities.getCurrentInterfaceAddress().getBroadcast(), VPXUDPListener.COMM_PORTNO, true);

			P2020ATPCommand msg1 = new P2020ATPCommand();

			byte[] buffer1 = new byte[msg1.size()];

			ByteBuffer bf1 = ByteBuffer.wrap(buffer1);

			bf1.order(msg1.byteOrder());

			msg1.setByteBuffer(bf1, 0);

			msg1.msgID.set(ATP.MSG_ID_SET);

			msg1.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg1.periodicity.set(period);

			send(buffer, VPXUtilities.getCurrentInterfaceAddress().getBroadcast(), VPXUDPListener.COMM_PORTNO, true);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendPeriodicity(String ip, int period, PROCESSOR_LIST procesor) {

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.periodicity.set(period);

			send(buffer, ip, VPXUDPListener.COMM_PORTNO, false);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendPeriodicity(String ips, int period) {

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		String ip = "";

		try {

			if (ips.contains(")")) {

				ip = ips.substring(ips.indexOf(")") + 1, ips.length());
			} else
				ip = ips;

			PROCESSOR_LIST procesor = vpxSystem.getProcessorTypeByIP(ip);// VPXUtilities.getProcessorType(ip);

			String recvip = getProcIP(ip);

			msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_PERIDAICITY);

			msg.periodicity.set(period);

			send(buffer, recvip, VPXUDPListener.COMM_PORTNO, false);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private String getProcIP(String ip) {

		return ip.trim().substring(ip.indexOf(")") + 1);
	}

	public void sendMessageToProcessor(String ip, int core, String message) {

		MSGCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			PROCESSOR_LIST processor = vpxSystem.getProcessorTypeByIP(ip);

			msg = (processor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020MSGCommand() : new DSPMSGCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.mode.set(MESSAGE_MODE.MSG_MODE_MESSAGE);

			msg.core.set(core);

			msg.command_msg.set(message);

			send(buffer, ip, VPXUDPListener.CONSOLE_MSG_PORTNO, false);

			((VPX_ETHWindow) listener).updateLog("Message sent to " + ip);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendMessageToProcessor(String ip, String msg) {

		send(msg.getBytes(), ip, VPXUDPListener.CONSOLE_MSG_PORTNO, false);

	}

	// Sending File

	public void sendFile(VPX_FlashProgressWindow parentDialog, String filename, String ip) {

		try {

			File f = new File(filename);

			size = FileUtils.sizeOf(f);

			filestoSend = FileUtils.readFileToByteArray(f);

			Map<Long, byte[]> t = VPXUtilities.divideArrayAsMap(filestoSend, ATP.DEFAULTBUFFERSIZE);

			fb = new FileBytesToSend(size, t);

			byte b[] = new byte[ATP.DEFAULTBUFFERSIZE];

			for (int i = 0; i < b.length; i++) {
				b[i] = filestoSend[i];
			}

			this.dialog = parentDialog;

			isFlashingStatred = true;

			sendFileToProcessor(ip, FileUtils.sizeOf(f), fb.getBytePacket(0));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void sendFileToProcessor(String ip, long size, byte[] sendBuffer) {
		try {

			ATPCommand msg = new DSPATPCommand();

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

			}

			msg.params.memoryinfo.length.set(sendBuffer.length);

			msg.params.flash_info.currentpacket.set(0);

			dialog.updatePackets(size, tot, 0, sendBuffer.length, sendBuffer.length);

			send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void recvAndSaveFile(String ip, ATPCommand msg) {
		/*
		 * 
		 * int currPacket = (int) msg.params.flash_info.currentpacket.get();
		 * 
		 * if (currPacket == 0) {
		 * 
		 * filesfromRecv = new byte[(int)
		 * msg.params.flash_info.totalfilesize.get()]; } start = currPacket *
		 * msg.params.memoryinfo.buffer.length;
		 * 
		 * end = start + msg.params.memoryinfo.buffer.length;
		 * 
		 * if (end > filestoSend.length) { end = filestoSend.length; }
		 * 
		 * for (int i = start, j = 0; i < end; i++, j++) {
		 * 
		 * filesfromRecv[i] = (byte) msg.params.memoryinfo.buffer[j].get(); }
		 * 
		 * // Sending part
		 * 
		 * if (currPacket < msg.params.flash_info.totalnoofpackets.get()) {
		 * 
		 * DatagramSocket datagramSocket;
		 * 
		 * try { datagramSocket = new DatagramSocket();
		 * 
		 * datagramSocket.setBroadcast(true);
		 * 
		 * byte[] buffer = new byte[msg.size()];
		 * 
		 * ByteBuffer bf = ByteBuffer.wrap(buffer);
		 * 
		 * bf.order(msg.byteOrder());
		 * 
		 * msg.setByteBuffer(bf, 0);
		 * 
		 * msg.msgID.set(ATP.MSG_ID_SET);
		 * 
		 * msg.msgType.set(ATP.MSG_TYPE_FLASH_ACK);
		 * 
		 * msg.params.flash_info.flashdevice.set(ATP.FLASH_DEVICE_NOR);
		 * 
		 * msg.params.flash_info.currentpacket.set(currPacket);
		 * 
		 * DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
		 * InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO);
		 * 
		 * datagramSocket.send(packet);
		 * 
		 * } catch (Exception e) {
		 * 
		 * e.printStackTrace(); } } else { try { FileOutputStream fos = new
		 * FileOutputStream("D:\\2.java"); fos.write(filesfromRecv);
		 * fos.close(); } catch (Exception e) { e.printStackTrace(); } }
		 */
	}

	public void sendNextPacket(String ip, ATPCommand msg) {

		int currPacket = (int) msg.params.flash_info.currentpacket.get();

		currPacket++;

		if (currPacket < tot) {
			try {

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

				byte[] bb = fb.getBytePacket(currPacket);

				if (bb != null) {

					msg.params.memoryinfo.byteZero.set(bb[0]);

					for (int i = 0; i < bb.length; i++) {

						msg.params.memoryinfo.buffer[i].set(bb[i]);

					}
					dialog.updatePackets(size, tot, currPacket, bb.length, bb.length);

					msg.params.memoryinfo.length.set(bb.length);
				} else {
					dialog.updatePackets(size, tot, currPacket, 0, 0);
				}

				msg.params.flash_info.totalfilesize.set(size);

				msg.params.flash_info.totalnoofpackets.set(tot);

				msg.params.flash_info.currentpacket.set(currPacket);

				send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	public void sendCommandToProcessor(String ip, ATP cmd) {

	}

	public void send(byte[] buffer, String ip, int port, boolean isBroadCast) {

		try {

			send(buffer, InetAddress.getByName(ip), port, isBroadCast);

		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
	}

	public void send(byte[] buffer, InetAddress ip, int port, boolean isBroadCast) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			if (isBroadCast) {

				datagramSocket.setBroadcast(true);
			}

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, port);

			datagramSocket.send(packet);

			datagramSocket.close();

		} catch (Exception e) {

			// Fix Me
			// e.printStackTrace();
		}
	}

	public void sendInterrupt(String ip, PROCESSOR_LIST procesor) {

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_FLASH_INTERRUPTED);

			send(buffer, ip, VPXUDPListener.COMM_PORTNO, false);

			isFlashingStatred = false;

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void startBist(String ip, String SubSystem) {

		bist = new BIST();

		pass = 0;

		fail = 0;

		bist.setTestSubSystem(SubSystem);

		loop = 0;

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

			send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);

			datagramSocket.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void readMemory(MemoryViewFilter filter) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			PROCESSOR_LIST procesor = vpxSystem.getProcessorTypeByIP(filter.getProcessor());// VPXUtilities.getProcessorType(ip);

			ATPCommand msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_GET);

			msg.msgType.set(ATP.MSG_TYPE_MEMORY);

			String str = filter.getMemoryAddress();

			if (str.startsWith("0x") || str.startsWith("0X")) {

				str = str.substring(2, str.length());
			}

			// new BigInteger(str, 16).intValue()
			msg.params.memoryinfo.address.set(new BigInteger(str, 16).intValue());

			msg.params.memoryinfo.length.set(Integer.valueOf(filter.getMemoryLength()));

			msg.params.memoryinfo.memIndex.set(filter.getMemoryBrowserID());
			// msg.command_msg.set("temp1");

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					InetAddress.getByName(filter.getProcessor()), VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			// ((VPX_ETHWindow) listener).updateLog("Memory Read from IP : " +
			// filter.getProcessor() + " Address : "
			// + filter.getMemoryAddress() + " Length : " +
			// filter.getMemoryLength());

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void populateMemory(String ip, ATPCommand msg) {

		boolean isComplete = false;

		byte[] b = new byte[msg.params.memoryinfo.buffer.length];

		for (int i = 0; i < b.length; i++) {

			b[i] = (byte) msg.params.memoryinfo.buffer[i].get();

			/*
			System.out.print(String.format("%02x ", msg.params.memoryinfo.buffer[i].get()));

			if (i > 0) {
				if ((i % 16) == 0) {
					System.out.println();
				}
			}*/
		}

		if (msg.params.flash_info.totalnoofpackets.get() > 1) {

			if (msg.params.flash_info.currentpacket.get() == 1) {

				bf = ArrayUtils.addAll(b);

			} else if (msg.params.flash_info.currentpacket.get() == msg.params.flash_info.totalnoofpackets.get()) {

				bf = ArrayUtils.addAll(bf, b);

				isComplete = true;
			}

		} else {

			bf = ArrayUtils.addAll(b);

			isComplete = true;
		}

		if (isComplete) {

			byte[] bfs = new byte[(int) msg.params.memoryinfo.length.get()];

			System.arraycopy(bf, 0, bfs, 0, (int) msg.params.memoryinfo.length.get());

			((VPX_ETHWindow) listener).populateMemory((int) msg.params.memoryinfo.memIndex.get(),
					(int) msg.params.memoryinfo.address.get(), bfs);

			// HexEditorDemoApp hd = new HexEditorDemoApp();

			// hd.setBytes((int) msg.params.memoryinfo.address.get(),bfs);

			// hd.setVisible(true);
		}
	}

	public void populateBISTResult(String ip, ATPCommand msg) {

		if (bist != null) {

			if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_P2020) {

				bist.setResultP2020Processor(getResultInColor(msg.params.testinfo.RESULT_P2020_PROCESSOR.get(), 0));

				bist.setResultP2020DDR3(getResultInColor(msg.params.testinfo.RESULT_P2020_DDR3.get(), 0));

				bist.setResultP2020NORFlash(getResultInColor(msg.params.testinfo.RESULT_P2020_NORFLASH.get(), 0));

				bist.setResultP2020Ethernet(getResultInColor(msg.params.testinfo.RESULT_P2020_ETHERNET.get(), 0));

				bist.setResultP2020SRIO(getResultInColor(msg.params.testinfo.RESULT_P2020_SRIO.get(), 0));

				bist.setResultP2020PCIe(getResultInColor(msg.params.testinfo.RESULT_P2020_PCIE.get(), 0));

				bist.setResultP2020Temprature1(getResultInColor(msg.params.testinfo.RESULT_P2020_TEMP1.get(), 1));

				bist.setResultP2020Temprature2(getResultInColor(msg.params.testinfo.RESULT_P2020_TEMP2.get(), 1));

				bist.setResultP2020Temprature3(getResultInColor(msg.params.testinfo.RESULT_P2020_TEMP3.get(), 1));

				bist.setResultP2020Voltage1(getResultInColor(msg.params.testinfo.RESULT_P2020_VOLT1_3p3.get(), 2));

				bist.setResultP2020Voltage2(getResultInColor(msg.params.testinfo.RESULT_P2020_VOLT2_2p5.get(), 2));

				bist.setResultP2020Voltage3(getResultInColor(msg.params.testinfo.RESULT_P2020_VOLT3_1p8.get(), 2));

				bist.setResultP2020Voltage4(getResultInColor(msg.params.testinfo.RESULT_P2020_VOLT4_1p5.get(), 2));

				bist.setResultP2020Voltage5(getResultInColor(msg.params.testinfo.RESULT_P2020_VOLT5_1p2.get(), 2));

				bist.setResultP2020Voltage6(getResultInColor(msg.params.testinfo.RESULT_P2020_VOLT6_1p0.get(), 2));

				bist.setResultP2020Voltage7(getResultInColor(msg.params.testinfo.RESULT_P2020_VOLT7_1p05.get(), 2));

				bist.setTestP2020IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_P2020, loop);

				bist.setP2020Completed(true);

			} else if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_DSP1) {

				bist.setResultDSP1DDR3(getResultInColor(msg.params.testinfo.RESULT_DSP_DDR3.get(), 0));

				bist.setResultDSP1NAND(getResultInColor(msg.params.testinfo.RESULT_DSP_NAND.get(), 0));

				bist.setResultDSP1NOR(getResultInColor(msg.params.testinfo.RESULT_DSP_NOR.get(), 0));

				bist.setResultDSP1Processor(getResultInColor(msg.params.testinfo.RESULT_DSP_PROCESSOR.get(), 0));

				bist.setTestDSP1IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_DSP1, loop);

				bist.setDSP1Completed(true);

			} else if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_DSP2) {

				bist.setResultDSP2DDR3(getResultInColor(msg.params.testinfo.RESULT_DSP_DDR3.get(), 0));

				bist.setResultDSP2NAND(getResultInColor(msg.params.testinfo.RESULT_DSP_NAND.get(), 0));

				bist.setResultDSP2NOR(getResultInColor(msg.params.testinfo.RESULT_DSP_NOR.get(), 0));

				bist.setResultDSP2Processor(getResultInColor(msg.params.testinfo.RESULT_DSP_PROCESSOR.get(), 0));

				bist.setTestDSP2IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_DSP2, loop);

				bist.setDSP2Completed(true);
			}

			if (bist.isDSP1Completed() && bist.isP2020Completed() && bist.isDSP2Completed()) {

				bist.setResultTestNoofTests(String.format("%d Tests", (pass + fail)));

				bist.setResultTestFailed(String.format("%d Tests", fail));

				if (fail == 0)

					bist.setResultTestStatus("Success !");

				else

					bist.setResultTestStatus("Failed !");

				bist.setResultTestPassed(String.format("%d Tests", pass));

				bist.setResultTestCompletedAt(VPXUtilities.getCurrentTime(2));

				bist.setResultTestDuration(
						VPXUtilities.getCurrentTime(2, System.currentTimeMillis() - bist.getStartTime()));

				((VPXCommunicationListener) listener).updateBIST(bist);
			}

			if (loop == VPXConstants.MAX_PROCESSOR) {

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_P2020, -1);
			}
		}
	}

	private String getResultInColor(long res, int type) {

		String ret = "";

		switch (type) {
		case 0:

			if (res == 0) {

				ret = String.format("<html><font color='red'>%s</font></html>", "FAIL");

				fail++;

			} else {

				ret = String.format("<html><font color='green'>%s</font></html>", "PASS");

				pass++;
			}

			break;

		case 1:

			ret = String.format("<html><font color='green'>%d &deg;C</font></html>", res);

			break;

		case 2:

			ret = String.format("<html><font color='green'>%d.%d V</font></html>", (res / 1000), (res % 1000));

			break;
		}

		return ret;
	}

	public void close() {

		closeByUnicast();

	}

	public void sendClose(String ip, PROCESSOR_LIST procesor) {

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_CLOSE);

			send(buffer, ip, VPXUDPListener.COMM_PORTNO, false);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void closeByUnicast() {

		try {
			int i = 0;

			VPXSystem sys = VPXSessionManager.getVPXSystem();

			List<VPXSubSystem> subs = sys.getSubsystem();

			for (Iterator<VPXSubSystem> iterator = subs.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				sendClose(vpxSubSystem.getIpP2020(), PROCESSOR_LIST.PROCESSOR_P2020);

				((VPX_ETHWindow) listener).updateExit(i++);

				Thread.sleep(150);

				sendClose(vpxSubSystem.getIpDSP1(), PROCESSOR_LIST.PROCESSOR_DSP1);

				((VPX_ETHWindow) listener).updateExit(i++);

				Thread.sleep(150);

				sendClose(vpxSubSystem.getIpDSP2(), PROCESSOR_LIST.PROCESSOR_DSP2);

				((VPX_ETHWindow) listener).updateExit(i++);

				Thread.sleep(150);

			}

			List<Processor> unlist = sys.getUnListed();

			for (Iterator<Processor> iterator = unlist.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				if (processor.getName().contains("P2020")) {

					sendClose(processor.getiP_Addresses(), PROCESSOR_LIST.PROCESSOR_P2020);

				} else {

					sendClose(processor.getiP_Addresses(), PROCESSOR_LIST.PROCESSOR_DSP2);

				}

				((VPX_ETHWindow) listener).updateExit(i++);

				Thread.sleep(150);

			}
		} catch (Exception e) {
		}

		((VPX_ETHWindow) listener).updateExit(-1);
	}

	public void addUDPListener(VPXUDPListener udpListener) {

		this.listener = udpListener;

	}

	// Parsing Communication Packets
	private synchronized void parseCommunicationPacket(String ip, ATPCommand msgCommand) {

		int msgID = (int) msgCommand.msgID.get();

		int msgType = (int) msgCommand.msgType.get();

		if (msgID == ATP.MSG_ID_SET) {

			switch (msgType) {

			case ATP.MSG_TYPE_FLASH:

				recvAndSaveFile(ip, msgCommand);

				break;

			case ATP.MSG_TYPE_FLASH_ACK:

				if (isFlashingStatred) {

					sendNextPacket(ip, msgCommand);

				}

				break;

			case ATP.MSG_TYPE_PERIDAICITY:

				break;

			case ATP.MSG_TYPE_BIST:

				break;

			case ATP.MSG_TYPE_FLASH_DONE:

				dialog.doneFlash();

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

				populateBISTResult(ip, msgCommand);

				break;

			case ATP.MSG_TYPE_MEMORY:
				populateMemory(ip, msgCommand);
				break;
			}

		}

	}

	// Parsing Advertisement Packets
	private synchronized void parseAdvertisementPacket(String ip, String msg) {

		if (msg.length() == 6) {

			if (subnet != null) {

				isipinRange = subnet.isInNet(ip);

			}

			if (isipinRange) {

				((VPXAdvertisementListener) listener).updateProcessorStatus(ip, msg);
			}
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

		ATPCommand msgCommand = new DSPATPCommand();

		ByteBuffer bf = ByteBuffer.allocate(msgCommand.size());

		if (vpxSystem.getProcessorTypeByIP(ip) == PROCESSOR_LIST.PROCESSOR_P2020) {

			msgCommand = new P2020ATPCommand();

		} else {

			msgCommand = new DSPATPCommand();
		}
		/*
		 * if (ip.equals("172.17.10.1")) {
		 * 
		 * msgCommand = new P2020ATPCommand();
		 * 
		 * } else if (ip.equals("172.17.10.130")) {
		 * 
		 * msgCommand = new DSPATPCommand(); }
		 */

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

		if (vpxSystem.getProcessorTypeByIP(ip) == PROCESSOR_LIST.PROCESSOR_P2020) {

			msgCommand = new P2020MSGCommand();

		} else {

			msgCommand = new DSPMSGCommand();
		}

		// msgCommand = new DSPMSGCommand();

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

		MSGCommand msg = new MSGCommand();

		byte[] messageData = new byte[msg.size()];

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

		ATPCommand cmd = new ATPCommand();

		byte[] commandData = new byte[cmd.size()];

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

					Thread.sleep(10);

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
		protected Void doInBackground() {

			while (true) {

				try {
					isipinRange = true;

					advertisementSocket.receive(advertisementPacket);

					parseAdvertisementPacket(advertisementPacket.getAddress().getHostAddress(),
							new String(advertisementPacket.getData(), 0, advertisementPacket.getLength()));

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

}

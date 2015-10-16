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

import com.cti.vpx.command.ATP;
import com.cti.vpx.command.ATP.MESSAGE_MODE;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.DSPMSGCommand;
import com.cti.vpx.command.MSGCommand;
import com.cti.vpx.command.P2020ATPCommand;
import com.cti.vpx.command.P2020MSGCommand;
import com.cti.vpx.controls.VPX_FlashProgressWindow;
import com.cti.vpx.controls.hex.MemoryViewFilter;
import com.cti.vpx.controls.hex.VPX_MemoryLoadProgressWindow;
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

	VPX_ETHWindow logger;

	private static VPXSubnetFilter subnet = null;

	private VPXCommunicationMonitor communicationMonitor;

	private VPXAdvertisementMonitor advertisementMonitor;

	private boolean isipinRange = true;

	private byte[] filestoSend;

	private int start;

	private int end;

	private int tot;

	private long size;

	private VPX_FlashProgressWindow dialog;

	private VPX_MemoryLoadProgressWindow memLoadDialog;

	private FileBytesToSend fb;

	private BIST bist = null;

	private int pass;

	private int fail;

	private int loop = 0;

	private static boolean isFlashingStatred = false;

	private static boolean isLoingMemoryStatred = false;

	private VPXSystem vpxSystem;

	// Memory Window

	// memory offset
	private static int memOffset0 = 0;

	private static int memOffset1 = 0;

	private static int memOffset2 = 0;

	private static int memOffset3 = 0;

	// memory buffer
	private byte[] memoryBuff0;

	private byte[] memoryBuff1;

	private byte[] memoryBuff2;

	private byte[] memoryBuff3;

	// Plot Window

	// plot offset
	private static int plotOffset0 = 0;

	private static int plotOffset1 = 0;

	private static int plotOffset2 = 0;

	// plot buffer
	private byte[] plotBuff0;

	private byte[] plotBuff1;

	private byte[] plotBuff2;

	// Amplitude Window

	private String[] amplitudeIPs = new String[VPXConstants.MAX_AMPLITUDE];

	// amplitude offset
	private static int amplitudeOffset0 = 0;

	private static int amplitudeOffset1 = 0;

	private static int amplitudeOffset2 = 0;

	// amplitude buffer
	private byte[] amplitudeBuff0;

	private byte[] amplitudeBuff1;

	private byte[] amplitudeBuff2;

	public VPXUDPMonitor() throws Exception {

		vpxSystem = VPXSessionManager.getVPXSystem();

		init();

		createDefaultMonitors();
	}

	public VPXUDPMonitor(VPXUDPListener parent) throws Exception {

		this.listener = parent;

		logger = ((VPX_ETHWindow) listener);

		vpxSystem = VPXSessionManager.getVPXSystem();

		init();

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

		logger.updateLog(String.format("Subnet Filter %s enabled", subnet));
	}

	public void clearFilterbySubnet() {

		logger.updateLog(String.format("Subnet Filter %s disabled", subnet));

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

	private void init() {

		// Initialize amplitude ips
		for (int i = 0; i < VPXConstants.MAX_AMPLITUDE; i++) {

			amplitudeIPs[i] = "";
		}

	}

	public void sendBoot(String ip, int processor, int flashdevice, int page) {

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

			if (processor == 0)
				msg.processorTYPE.set(ATP.PROCESSOR_TYPE.PROCESSOR_P2020);
			else if (processor == 1)
				msg.processorTYPE.set(ATP.PROCESSOR_TYPE.PROCESSOR_DSP1);
			else if (processor == 2)
				msg.processorTYPE.set(ATP.PROCESSOR_TYPE.PROCESSOR_DSP2);

			if (flashdevice == 0)
				msg.params.flash_info.flashdevice.set(ATP.FLASH_DEVICE_NAND);
			else
				msg.params.flash_info.flashdevice.set(ATP.FLASH_DEVICE_NOR);

			msg.params.flash_info.offset.set(page);

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

			logger.updateLog(String.format("Periodicity updated %s into %d seconds", ip, period));

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

			logger.updateLog(String.format("Periodicity updated to %s into %d seconds", ip, period));

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

			logger.updateLog("Message sent to " + ip);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendMessageToProcessor(String ip, String msg) {

		send(msg.getBytes(), ip, VPXUDPListener.CONSOLE_MSG_PORTNO, false);

	}

	// Sending File

	public void sendFile(VPX_FlashProgressWindow parentDialog, String filename, String ip, int flashDevice,
			int location) {

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

			sendFileToProcessor(ip, FileUtils.sizeOf(f), fb.getBytePacket(0), flashDevice, location);

			logger.updateLog(String.format("Start flashing file ( %s ) for %s on flash device %s at page %d", filename,
					ip, (flashDevice == 0 ? "NAND" : "NOR"), location));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void sendFileToProcessor(String ip, long size, byte[] sendBuffer, int flashDevice, int location) {
		try {

			ATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_FLASH);

			if (flashDevice == 0)
				msg.params.flash_info.flashdevice.set(ATP.FLASH_DEVICE_NAND);
			else
				msg.params.flash_info.flashdevice.set(ATP.FLASH_DEVICE_NOR);

			msg.params.flash_info.offset.set(location);

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

	// loading file to memory

	public void sendMemoryFile(VPX_MemoryLoadProgressWindow parentDialog, String filename, long address, String ip) {

		try {

			File f = new File(filename);

			size = FileUtils.sizeOf(f);

			filestoSend = FileUtils.readFileToByteArray(f);

			Map<Long, byte[]> t = VPXUtilities.divideArrayAsMap(filestoSend, ATP.DEFAULTBUFFERSIZE);

			fb = new FileBytesToSend(size, t);

			byte b[] = new byte[ATP.DEFAULTBUFFERSIZE];

			long len = (filestoSend.length > b.length) ? b.length : filestoSend.length;

			for (int i = 0; i < len; i++) {
				b[i] = filestoSend[i];
			}

			this.memLoadDialog = parentDialog;

			isLoingMemoryStatred = true;

			sendMemoryFileToProcessor(ip, address, FileUtils.sizeOf(f), fb.getBytePacket(0));

			logger.updateLog(String.format("Sending file ( %s ) to %s start address 0x%08x", filename, ip, address));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void sendMemoryFileToProcessor(String ip, long address, long size, byte[] sendBuffer) {
		try {

			int length = sendBuffer.length;

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_LOADMEMORY);

			msg.params.memoryinfo.address.set(address);

			tot = (int) (size / ATP.DEFAULTBUFFERSIZE);

			int rem = (int) (size % ATP.DEFAULTBUFFERSIZE);

			if (rem > 0)
				tot++;

			msg.params.memoryinfo.byteZero.set(sendBuffer[0]);

			for (int i = 0; i < sendBuffer.length; i++) {

				msg.params.memoryinfo.buffer[i].set(sendBuffer[i]);

			}

			msg.params.flash_info.totalnoofpackets.set(tot);

			msg.params.flash_info.totalfilesize.set(size);

			msg.params.flash_info.currentpacket.set(0);

			msg.params.memoryinfo.length.set(length);

			this.memLoadDialog.updatePackets(size, tot, 0, sendBuffer.length, sendBuffer.length);

			send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendMemoryFileNextPacket(String ip, ATPCommand msg) {

		int currPacket = (int) msg.params.flash_info.currentpacket.get();

		currPacket++;

		if (currPacket < tot) {
			try {

				byte[] buffer = new byte[msg.size()];

				ByteBuffer bf = ByteBuffer.wrap(buffer);

				bf.order(msg.byteOrder());

				msg.setByteBuffer(bf, 0);

				msg.msgID.set(ATP.MSG_ID_SET);

				msg.msgType.set(ATP.MSG_TYPE_LOADMEMORY);

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

					this.memLoadDialog.updatePackets(size, tot, currPacket, bb.length, bb.length);

					msg.params.memoryinfo.length.set(bb.length);

				} else {
					this.memLoadDialog.updatePackets(size, tot, currPacket, 0, 0);
				}

				msg.params.flash_info.totalnoofpackets.set(tot);

				msg.params.flash_info.totalfilesize.set(size);

				msg.params.flash_info.currentpacket.set(currPacket);

				send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
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

			logger.updateLog(String.format("Flashing %s  is cacelled by user", ip));

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

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			msg = new P2020ATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_GET);

			msg.msgType.set(ATP.MSG_TYPE_BIST);

			send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);

			logger.updateLog(String.format("Built in Self Test is started for %s", ip));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void setMemory(String ip, int core, long fromAddress, int memindex, int typeSize, int length,
			long newValue) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_MEMORY);

			msg.params.memoryinfo.address.set(fromAddress);

			msg.params.memoryinfo.byteZero.set(typeSize);

			msg.params.memoryinfo.length.set(length);

			msg.params.memoryinfo.memIndex.set(memindex);

			msg.params.memoryinfo.newvalue.set(newValue);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			logger.updateLog(String.format("New value set for %s core %d address 0x%08X length %d Value 0x%08X", ip,
					core, fromAddress, length, newValue));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void readMemory(MemoryViewFilter filter) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			PROCESSOR_LIST procesor = vpxSystem.getProcessorTypeByIP(filter.getProcessor());

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

			msg.params.memoryinfo.core.set(Integer.parseInt(filter.getCore()));

			msg.params.memoryinfo.address.set(new BigInteger(str, 16).intValue());

			msg.params.memoryinfo.length.set(Integer.valueOf(filter.getMemoryLength()));

			msg.params.memoryinfo.memIndex.set(filter.getMemoryBrowserID());

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					InetAddress.getByName(filter.getProcessor()), VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			logger.updateLog(String.format("Read memory from %s core %s address %s length %s", filter.getProcessor(),
					filter.getCore(), filter.getMemoryAddress(), filter.getMemoryLength()));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void populateMemory(String ip, ATPCommand msg) {

		try {
			boolean isComplete = false;

			int index = (int) msg.params.memoryinfo.memIndex.get();

			byte[] b = new byte[msg.params.memoryinfo.buffer.length];

			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) msg.params.memoryinfo.buffer[i].get();

				printBytes(b[i], i);

			}

			if (msg.params.flash_info.totalnoofpackets.get() > 1) {

				if (msg.params.flash_info.currentpacket.get() == 1) {

					if (index == 0) {

						memoryBuff0 = new byte[(int) msg.params.memoryinfo.length.get()];

						System.arraycopy(b, 0, memoryBuff0, 0, b.length);

					} else if (index == 1) {

						memoryBuff1 = new byte[(int) msg.params.memoryinfo.length.get()];

						System.arraycopy(b, 0, memoryBuff1, 0, b.length);

					} else if (index == 2) {

						memoryBuff2 = new byte[(int) msg.params.memoryinfo.length.get()];

						System.arraycopy(b, 0, memoryBuff2, 0, b.length);

					} else if (index == 3) {

						memoryBuff3 = new byte[(int) msg.params.memoryinfo.length.get()];

						System.arraycopy(b, 0, memoryBuff3, 0, b.length);

					}

					isComplete = false;

				} else if (msg.params.flash_info.currentpacket.get() == msg.params.flash_info.totalnoofpackets.get()) {

					if (index == 0) {

						memOffset0 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						int len = (int) msg.params.memoryinfo.length.get() - memOffset0;

						System.arraycopy(b, 0, memoryBuff0, memOffset0, len);

					} else if (index == 1) {

						memOffset1 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						int len = (int) msg.params.memoryinfo.length.get() - memOffset1;

						System.arraycopy(b, 0, memoryBuff1, memOffset1, len);

					} else if (index == 2) {

						memOffset2 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						int len = (int) msg.params.memoryinfo.length.get() - memOffset2;

						System.arraycopy(b, 0, memoryBuff1, memOffset2, len);

					} else if (index == 3) {

						memOffset3 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						int len = (int) msg.params.memoryinfo.length.get() - memOffset3;

						System.arraycopy(b, 0, memoryBuff3, memOffset3, len);

					}

					isComplete = true;

				} else {

					if (index == 0) {

						memOffset0 = (int) (msg.params.flash_info.currentpacket.get() * 1024);

						System.arraycopy(b, 0, memoryBuff0, memOffset0, b.length);

					} else if (index == 1) {

						memOffset1 = (int) (msg.params.flash_info.currentpacket.get() * 1024);

						System.arraycopy(b, 0, memoryBuff1, memOffset1, b.length);

					} else if (index == 2) {

						memOffset2 = (int) (msg.params.flash_info.currentpacket.get() * 1024);

						System.arraycopy(b, 0, memoryBuff0, memOffset2, b.length);

					} else if (index == 3) {

						memOffset2 = (int) (msg.params.flash_info.currentpacket.get() * 1024);

						System.arraycopy(b, 0, memoryBuff2, memOffset3, b.length);

					}

					isComplete = false;
				}

			} else {

				if (index == 0) {

					memoryBuff0 = new byte[(int) msg.params.memoryinfo.length.get()];

					memOffset0 = 0;

					System.arraycopy(b, memOffset0, memoryBuff0, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 1) {

					memoryBuff1 = new byte[(int) msg.params.memoryinfo.length.get()];

					memOffset1 = 0;

					System.arraycopy(b, memOffset1, memoryBuff1, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 2) {

					memoryBuff2 = new byte[(int) msg.params.memoryinfo.length.get()];

					memOffset2 = 0;

					System.arraycopy(b, memOffset2, memoryBuff2, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 3) {

					memoryBuff3 = new byte[(int) msg.params.memoryinfo.length.get()];

					memOffset3 = 0;

					System.arraycopy(b, memOffset3, memoryBuff3, 0, (int) msg.params.memoryinfo.length.get());

				}
				isComplete = true;
			}

			if (isComplete) {

				byte[] bb = new byte[(int) msg.params.memoryinfo.length.get()];

				if (index == 0) {
					System.arraycopy(memoryBuff0, 0, bb, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 1) {
					System.arraycopy(memoryBuff1, 0, bb, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 2) {
					System.arraycopy(memoryBuff2, 0, bb, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 3) {

					System.arraycopy(memoryBuff3, 0, bb, 0, (int) msg.params.memoryinfo.length.get());
				}

				logger.populateMemory(index, msg.params.memoryinfo.address.get(), bb);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readPlot(MemoryViewFilter filter) {

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

			msg.msgType.set(ATP.MSG_TYPE_PLOT);

			String str = filter.getMemoryAddress();

			if (str.startsWith("0x") || str.startsWith("0X")) {

				str = str.substring(2, str.length());
			}

			msg.params.memoryinfo.core.set(Integer.parseInt(filter.getCore()));

			msg.params.memoryinfo.address.set(new BigInteger(str, 16).intValue());

			msg.params.memoryinfo.length.set(Integer.valueOf(filter.getMemoryLength()));

			msg.params.memoryinfo.memIndex.set(filter.getMemoryBrowserID());

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					InetAddress.getByName(filter.getProcessor()), VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			logger.updateLog(String.format("Read plot from %s core %s address %s length %s", filter.getProcessor(),
					filter.getCore(), filter.getMemoryAddress(), filter.getMemoryLength()));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void readPlot(MemoryViewFilter filter1, MemoryViewFilter filter2) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			// Filter 1
			PROCESSOR_LIST procesor = vpxSystem.getProcessorTypeByIP(filter1.getProcessor());// VPXUtilities.getProcessorType(ip);

			ATPCommand msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_GET);

			msg.msgType.set(ATP.MSG_TYPE_PLOT);

			String str = filter1.getMemoryAddress();

			if (str.startsWith("0x") || str.startsWith("0X")) {

				str = str.substring(2, str.length());
			}

			msg.params.memoryinfo.address.set(new BigInteger(str, 16).intValue());

			msg.params.memoryinfo.core.set(Integer.parseInt(filter1.getCore()));

			msg.params.memoryinfo.length.set(Integer.valueOf(filter1.getMemoryLength()));

			msg.params.memoryinfo.memIndex.set(filter1.getMemoryBrowserID());

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					InetAddress.getByName(filter1.getProcessor()), VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			// Filter 2
			procesor = vpxSystem.getProcessorTypeByIP(filter2.getProcessor());// VPXUtilities.getProcessorType(ip);

			msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			buffer = new byte[msg.size()];

			bf.clear();

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_GET);

			msg.msgType.set(ATP.MSG_TYPE_PLOT);

			str = filter2.getMemoryAddress();

			if (str.startsWith("0x") || str.startsWith("0X")) {

				str = str.substring(2, str.length());
			}

			msg.params.memoryinfo.address.set(new BigInteger(str, 16).intValue());

			msg.params.memoryinfo.core.set(Integer.parseInt(filter2.getCore()));

			msg.params.memoryinfo.length.set(Integer.valueOf(filter2.getMemoryLength()));

			msg.params.memoryinfo.memIndex.set(filter2.getMemoryBrowserID());

			packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(filter2.getProcessor()),
					VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			logger.updateLog(String.format("Read plot from %s core %s address %s length %s", filter1.getProcessor(),
					filter1.getCore(), filter1.getMemoryAddress(), filter1.getMemoryLength()));

			logger.updateLog(String.format("Read plot from %s core %s address %s length %s", filter2.getProcessor(),
					filter2.getCore(), filter2.getMemoryAddress(), filter2.getMemoryLength()));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void populatePlot(String ip, ATPCommand msg) {

		try {
			boolean isComplete = false;

			char[] idxs = String.format("%02d", msg.params.memoryinfo.memIndex.get()).toCharArray();

			int index = Character.getNumericValue(idxs[0]);

			int lineid = Character.getNumericValue(idxs[1]);

			byte[] b = new byte[msg.params.memoryinfo.buffer.length];

			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) msg.params.memoryinfo.buffer[i].get();

			}

			if (msg.params.flash_info.totalnoofpackets.get() > 1) {

				if (msg.params.flash_info.currentpacket.get() == 1) {

					if (index == 0) {

						plotBuff0 = new byte[(int) msg.params.memoryinfo.length.get()];

						System.arraycopy(b, 0, plotBuff0, 0, b.length);

					} else if (index == 1) {

						plotBuff1 = new byte[(int) msg.params.memoryinfo.length.get()];

						System.arraycopy(b, 0, plotBuff1, 0, b.length);

					} else if (index == 2) {

						plotBuff2 = new byte[(int) msg.params.memoryinfo.length.get()];

						System.arraycopy(b, 0, plotBuff2, 0, b.length);

					}

					isComplete = false;

				} else if (msg.params.flash_info.currentpacket.get() == msg.params.flash_info.totalnoofpackets.get()) {

					if (index == 0) {

						plotOffset0 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						int len = (int) msg.params.memoryinfo.length.get() - plotOffset0;

						System.arraycopy(b, 0, plotBuff0, plotOffset0, len);

					} else if (index == 1) {

						plotOffset1 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						int len = (int) msg.params.memoryinfo.length.get() - plotOffset1;

						System.arraycopy(b, 0, plotBuff1, plotOffset1, len);

					} else if (index == 2) {

						plotOffset2 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						int len = (int) msg.params.memoryinfo.length.get() - plotOffset2;

						System.arraycopy(b, 0, plotBuff2, plotOffset2, len);

					}

					isComplete = true;

				} else {

					if (index == 0) {

						plotOffset0 = (int) (msg.params.flash_info.currentpacket.get() * 1024);

						System.arraycopy(b, 0, plotBuff0, plotOffset0, b.length);

					} else if (index == 1) {

						plotOffset1 = (int) (msg.params.flash_info.currentpacket.get() * 1024);

						System.arraycopy(b, 0, plotBuff1, plotOffset1, b.length);

					} else if (index == 2) {

						plotOffset2 = (int) (msg.params.flash_info.currentpacket.get() * 1024);

						System.arraycopy(b, 0, plotBuff2, plotOffset2, b.length);

					}

					isComplete = false;
				}

			} else {

				if (index == 0) {

					plotBuff0 = new byte[(int) msg.params.memoryinfo.length.get()];

					plotOffset0 = 0;

					System.arraycopy(b, plotOffset0, plotBuff0, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 1) {

					plotBuff1 = new byte[(int) msg.params.memoryinfo.length.get()];

					plotOffset1 = 0;

					System.arraycopy(b, plotOffset1, plotBuff1, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 2) {

					plotBuff2 = new byte[(int) msg.params.memoryinfo.length.get()];

					plotOffset2 = 0;

					System.arraycopy(b, plotOffset2, plotBuff2, 0, (int) msg.params.memoryinfo.length.get());

				}

				isComplete = true;
			}

			if (isComplete) {

				byte[] bb = new byte[(int) msg.params.memoryinfo.length.get()];

				if (index == 0) {
					System.arraycopy(plotBuff0, 0, bb, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 1) {
					System.arraycopy(plotBuff1, 0, bb, 0, (int) msg.params.memoryinfo.length.get());

				} else if (index == 2) {
					System.arraycopy(plotBuff2, 0, bb, 0, (int) msg.params.memoryinfo.length.get());

				}

				logger.populatePlot(index, lineid, msg.params.memoryinfo.address.get(), bb);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readWaterfallData(String ip) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_GET);

			msg.msgType.set(ATP.MSG_TYPE_WATERFALL);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			logger.updateLog(String.format("Read waterfall from %s", ip));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void setWaterfallInterrupted(String ip) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_WATERFALL_INTERRUPTED);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			logger.updateLog(String.format("Reading waterfall from %s interrupted", ip));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void populateWaterfallData(String ip, ATPCommand msg) {

		byte[] b = new byte[msg.params.memoryinfo.buffer.length];

		for (int i = 0; i < b.length; i++) {

			b[i] = (byte) msg.params.memoryinfo.buffer[i].get();

		}

		logger.populateWaterfall(ip, b);

	}

	public void readAmplitudeData(String ip) {

		DatagramSocket datagramSocket;

		try {

			if (isContainingAmplitudeIP(ip) < 0) {
				addAmplitudeIP(ip);
			}

			datagramSocket = new DatagramSocket();

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_GET);

			msg.msgType.set(ATP.MSG_TYPE_AMPLITUDE);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			logger.updateLog(String.format("Read amplitude from %s", ip));

		} catch (Exception e) {

			removeAmplitudeIP(ip);

			e.printStackTrace();
		}

	}

	public void setAmplitudeInterrupted(String ip) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_AMPLITUDE_INTERRUPTED);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			removeAmplitudeIP(ip);

			logger.updateLog(String.format("Reading amplitude from %s interrupted", ip));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public synchronized void populateAmplitudeData(String ip, ATPCommand msg) {

		try {
			boolean isComplete = false;

			int index = isContainingAmplitudeIP(ip);

			byte[] b = new byte[msg.params.memoryinfo.buffer.length];

			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) msg.params.memoryinfo.buffer[i].get();

				// if(ip.endsWith("140")){
				// printBytes(b[i], i);
				// }

			}

			float[] f0 = new float[b.length / 4];

			ByteBuffer.wrap(b).order(ATP.BYTEORDER_DSP).asFloatBuffer().get(f0);

			if (msg.params.flash_info.totalnoofpackets.get() > 1) {

				if (msg.params.flash_info.currentpacket.get() == 1) {

					if (index == 0) {

						amplitudeBuff0 = new byte[(int) (msg.params.flash_info.totalnoofpackets.get() * 1024)];

						System.arraycopy(b, 0, amplitudeBuff0, 0, b.length);

					} else if (index == 1) {

						amplitudeBuff1 = new byte[(int) (msg.params.flash_info.totalnoofpackets.get() * 1024)];

						System.arraycopy(b, 0, amplitudeBuff1, 0, b.length);

					} else if (index == 2) {

						amplitudeBuff2 = new byte[(int) (msg.params.flash_info.totalnoofpackets.get() * 1024)];

						System.arraycopy(b, 0, amplitudeBuff2, 0, b.length);

					}

					isComplete = false;

				} else if (msg.params.flash_info.currentpacket.get() == msg.params.flash_info.totalnoofpackets.get()) {

					if (index == 0) {

						amplitudeOffset0 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						System.arraycopy(b, 0, amplitudeBuff0, amplitudeOffset0, b.length);

					} else if (index == 1) {

						amplitudeOffset1 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						System.arraycopy(b, 0, amplitudeBuff1, amplitudeOffset1, b.length);

					} else if (index == 2) {

						amplitudeOffset2 = (int) (msg.params.flash_info.currentpacket.get() - 1) * 1024;

						System.arraycopy(b, 0, amplitudeBuff2, amplitudeOffset2, b.length);

					}

					isComplete = true;

				} else {

					if (index == 0) {

						amplitudeOffset0 = (int) ((msg.params.flash_info.currentpacket.get() - 1) * 1024);

						System.arraycopy(b, 0, amplitudeBuff0, amplitudeOffset0, b.length);

					} else if (index == 1) {

						amplitudeOffset1 = (int) ((msg.params.flash_info.currentpacket.get() - 1) * 1024);

						System.arraycopy(b, 0, amplitudeBuff1, amplitudeOffset1, b.length);

					} else if (index == 2) {

						amplitudeOffset2 = (int) ((msg.params.flash_info.currentpacket.get() - 1) * 1024);

						System.arraycopy(b, 0, amplitudeBuff2, amplitudeOffset2, b.length);

					}

					isComplete = false;
				}

			} else {

				int len = (int) (msg.params.flash_info.totalnoofpackets.get() * 1024);

				if (index == 0) {

					amplitudeBuff0 = new byte[len];

					amplitudeOffset0 = 0;

					System.arraycopy(b, amplitudeOffset0, amplitudeBuff0, 0, len);

				} else if (index == 1) {

					amplitudeBuff1 = new byte[len];

					amplitudeOffset1 = 0;

					System.arraycopy(b, amplitudeOffset1, amplitudeBuff1, 0, len);

				} else if (index == 2) {

					amplitudeBuff2 = new byte[len];

					amplitudeOffset2 = 0;

					System.arraycopy(b, amplitudeOffset2, amplitudeBuff2, 0, len);

				}

				isComplete = true;
			}

			if (isComplete) {

				float[] fl = new float[(int) msg.params.flash_info.totalfilesize.get() * 2];

				byte[] bb = new byte[(int) (msg.params.flash_info.totalnoofpackets.get() * 1024)];

				if (index == 0) {
					System.arraycopy(amplitudeBuff0, 0, bb, 0, bb.length);

				} else if (index == 1) {
					System.arraycopy(amplitudeBuff1, 0, bb, 0, bb.length);

				} else if (index == 2) {
					System.arraycopy(amplitudeBuff2, 0, bb, 0, bb.length);

				}

				ByteBuffer.wrap(bb).order(ATP.BYTEORDER_DSP).asFloatBuffer().get(fl);

				float x[] = new float[fl.length / 2];

				float y[] = new float[fl.length / 2];

				int k = 0;

				for (int i = 0; i < fl.length; i += 2) {

					x[k] = fl[i];

					y[k] = fl[i + 1];

					k++;

				}

				((VPX_ETHWindow) listener).populateAmplitude(ip, x, y);

			}
		} catch (Exception e) {
			e.printStackTrace();
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

				logger.updateLog(String.format("P2020 (%s) test is completed", ip));

				bist.setP2020Completed(true);

			} else if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_DSP1) {

				bist.setResultDSP1DDR3(getResultInColor(msg.params.testinfo.RESULT_DSP_DDR3.get(), 0));

				bist.setResultDSP1NAND(getResultInColor(msg.params.testinfo.RESULT_DSP_NAND.get(), 0));

				bist.setResultDSP1NOR(getResultInColor(msg.params.testinfo.RESULT_DSP_NOR.get(), 0));

				bist.setResultDSP1Processor(getResultInColor(msg.params.testinfo.RESULT_DSP_PROCESSOR.get(), 0));

				bist.setTestDSP1IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_DSP1, loop);

				logger.updateLog(String.format("DSP 1 (%s) test is completed", ip));

				bist.setDSP1Completed(true);

			} else if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_DSP2) {

				bist.setResultDSP2DDR3(getResultInColor(msg.params.testinfo.RESULT_DSP_DDR3.get(), 0));

				bist.setResultDSP2NAND(getResultInColor(msg.params.testinfo.RESULT_DSP_NAND.get(), 0));

				bist.setResultDSP2NOR(getResultInColor(msg.params.testinfo.RESULT_DSP_NOR.get(), 0));

				bist.setResultDSP2Processor(getResultInColor(msg.params.testinfo.RESULT_DSP_PROCESSOR.get(), 0));

				bist.setTestDSP2IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_DSP2, loop);

				logger.updateLog(String.format("DSP 2 (%s) test is completed", ip));

				bist.setDSP2Completed(true);
			}

			if (bist.isDSP1Completed() && bist.isP2020Completed() && bist.isDSP2Completed()) {

				bist.setResultTestNoofTests(String.format("%d Tests", (pass + fail)));

				bist.setResultTestFailed(String.format("%d Tests", fail));

				if (fail == 0) {

					bist.setResultTestStatus("Success !");

					logger.updateLog("Built In Self Test Success");

				} else {

					bist.setResultTestStatus("Failed !");

					logger.updateLog("Built In Self Test Failed");
				}

				bist.setResultTestPassed(String.format("%d Tests", pass));

				bist.setResultTestCompletedAt(VPXUtilities.getCurrentTime(2));

				bist.setResultTestDuration(
						VPXUtilities.getCurrentTime(2, System.currentTimeMillis() - bist.getStartTime()));

				((VPXCommunicationListener) listener).updateBIST(bist);
			}

			if (loop == VPXConstants.MAX_PROCESSOR) {

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_P2020, -1);

				logger.updateLog(String.format("Built In Selft Test completed"));
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

			ret = String.format("<html><font color='green'>%d.%03d V</font></html>", (res / 1000), (res % 1000));

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

				logger.updateExit(i++);

				Thread.sleep(150);

				sendClose(vpxSubSystem.getIpDSP1(), PROCESSOR_LIST.PROCESSOR_DSP1);

				logger.updateExit(i++);

				Thread.sleep(150);

				sendClose(vpxSubSystem.getIpDSP2(), PROCESSOR_LIST.PROCESSOR_DSP2);

				logger.updateExit(i++);

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

				logger.updateExit(i++);

				Thread.sleep(150);

			}
		} catch (Exception e) {
		}

		logger.updateExit(-1);
	}

	public void addUDPListener(VPXUDPListener udpListener) {

		this.listener = udpListener;

		logger = ((VPX_ETHWindow) listener);

	}

	// Parsing Communication Packets
	private synchronized void parseCommunicationPacket(String ip, ATPCommand msgCommand) {

		int msgID = (int) msgCommand.msgID.get();

		int msgType = (int) msgCommand.msgType.get();

		if (msgID == ATP.MSG_ID_SET) {

			switch (msgType) {

			case ATP.MSG_TYPE_FLASH:

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

			case ATP.MSG_TYPE_LOADMEMORY:

				break;

			case ATP.MSG_TYPE_LOADMEMORY_ACK:

				if (isLoingMemoryStatred) {

					sendMemoryFileNextPacket(ip, msgCommand);

				}

				break;

			case ATP.MSG_TYPE_LOADMEMORY_DONE:

				this.memLoadDialog.closeLoadMemory();

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

			case ATP.MSG_TYPE_PLOT:

				populatePlot(ip, msgCommand);

				break;

			case ATP.MSG_TYPE_WATERFALL:

				populateWaterfallData(ip, msgCommand);

				break;

			case ATP.MSG_TYPE_AMPLITUDE:

				populateAmplitudeData(ip, msgCommand);

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

	private void addAmplitudeIP(String ip) {

		int idx = -1;

		for (int i = 0; i < amplitudeIPs.length; i++) {

			if (amplitudeIPs[i].equals(ip)) {

				idx = -1;

				break;
			} else {

				if (amplitudeIPs[i].equals("")) {

					idx = i;

					break;

				}

			}

		}

		if (idx > -1) {
			amplitudeIPs[idx] = ip;
		}

	}

	private void removeAmplitudeIP(String ip) {

		for (int i = 0; i < amplitudeIPs.length; i++) {

			if (amplitudeIPs[i].equals(ip)) {

				amplitudeIPs[i] = "";

				break;
			}
		}

	}

	private int isContainingAmplitudeIP(String ip) {

		int ret = -1;

		for (int i = 0; i < amplitudeIPs.length; i++) {

			System.out.println(amplitudeIPs[i] + " -- " + i);

			if (amplitudeIPs[i].equals(ip)) {

				ret = i;

				break;
			}
		}

		return ret;
	}

	private void printBytes(byte byteVal, int i) {

		System.out.print(String.format("%02x ", byteVal));

		if (((i + 1) % 16) == 0) {

			System.out.println();
		}

	}

}

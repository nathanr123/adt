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

import javax.swing.JLabel;

import org.apache.commons.io.FileUtils;

import com.cti.vpx.command.ATP;
import com.cti.vpx.command.ATP.MESSAGE_MODE;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.BISTCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.DSPMSGCommand;
import com.cti.vpx.command.MSGCommand;
import com.cti.vpx.command.P2020ATPCommand;
import com.cti.vpx.command.P2020MSGCommand;
import com.cti.vpx.controls.VPX_ExecutionFileTransferingWindow;
import com.cti.vpx.controls.VPX_FlashProgressWindow;
import com.cti.vpx.controls.hex.MemoryViewFilter;
import com.cti.vpx.controls.hex.VPX_MemoryLoadProgressWindow;
import com.cti.vpx.model.BIST;
import com.cti.vpx.model.ExecutionHexArray;
import com.cti.vpx.model.FileBytesToSend;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.model.VPXNWPacket;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXNetworkLogger;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXSubnetFilter;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPXUDPMonitor {

	VPXUDPListener listener;

	VPX_ETHWindow parent;

	private static VPXSubnetFilter subnet = null;

	// private VPXCommunicationMonitor communicationMonitor;

	private boolean isipinRange = true;

	private byte[] filestoSend;

	private int start;

	private int end;

	private int tot;

	private long size;

	private VPX_FlashProgressWindow dialog;

	private VPX_MemoryLoadProgressWindow memLoadDialog;

	private VPX_ExecutionFileTransferingWindow executionLoadingDialog;

	private List<ExecutionHexArray> executionCoreHexArray = null;

	private VPXTFTPMonitor tftp;

	private FileBytesToSend fb;

	private BIST bist = null;

	private int pass;

	private int fail;

	private int loop = 0;

	private static boolean isFlashingStatred = false;

	private static boolean isExecutionDownloadStatred = false;

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

	private MemoryViewFilter currFilter0;

	private MemoryViewFilter currFilter1;

	private MemoryViewFilter currFilter2;

	private MemoryViewFilter currFilter3;

	// Plot Window

	// plot offset
	private static int plotOffset0 = 0;

	private static int plotOffset1 = 0;

	private static int plotOffset2 = 0;

	// plot buffer
	private byte[] plotBuff0;

	private byte[] plotBuff1;

	private byte[] plotBuff2;

	private int currentFileIndex;

	private byte[] currentbyteArray;

	private int currentbyteTotalpckt;

	private int currentCore;

	private String bytes = "";

	private boolean isRunning = true;

	private Thread messageMonitor;

	private VPXMessageConsoleMonitor messageMonitorRunnable;

	private Thread advMonitor;

	private VPXAdvMonitor advMonitorRunnable;

	private Thread commMonitor;

	private VPXCommMonitor commMonitorRunnable;

	public VPXUDPMonitor() throws Exception {

		vpxSystem = VPXSessionManager.getVPXSystem();

		createDefaultMonitors();
	}

	public VPXUDPMonitor(VPXUDPListener prent) throws Exception {

		this.listener = prent;

		parent = ((VPX_ETHWindow) listener);

		vpxSystem = VPXSessionManager.getVPXSystem();

		createDefaultMonitors();

	}

	private void createDefaultMonitors() throws Exception {

		// communicationMonitor = new VPXCommunicationMonitor();

		// advertisementMonitor = new VPXAdvertisementMonitor();

		commMonitorRunnable = new VPXCommMonitor();

		commMonitor = new Thread(commMonitorRunnable);

		advMonitorRunnable = new VPXAdvMonitor();

		advMonitor = new Thread(advMonitorRunnable);

		messageMonitorRunnable = new VPXMessageConsoleMonitor();

		messageMonitor = new Thread(messageMonitorRunnable);

		advMonitor.setPriority(10);

		advMonitor.setDaemon(true);

		commMonitor.setPriority(5);

		messageMonitor.setPriority(5);

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

		VPXLogger.updateLog(String.format("Subnet Filter %s enabled", subnet));
	}

	public void clearFilterbySubnet() {

		VPXLogger.updateLog(String.format("Subnet Filter %s disabled", subnet));

		subnet = null;

	}

	private void start() throws Exception {

		// advertisementMonitor.startMonitor();

		advMonitor.start();

		commMonitor.start();

		// communicationMonitor.startMonitor();

		messageMonitor.start();
	}

	private void stop() {

		// advertisementMonitor.stopMonitor();

		messageMonitorRunnable.stop();

		advMonitorRunnable.stop();

		commMonitorRunnable.stop();

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
			VPXLogger.updateError(e);
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
			VPXLogger.updateError(e);
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

			VPXLogger.updateLog(String.format("Periodicity updated %s into %d seconds", ip, period));

		} catch (Exception e) {
			VPXLogger.updateError(e);
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

			VPXLogger.updateLog(String.format("Periodicity updated to %s into %d seconds", ip, period));

		} catch (Exception e) {
			VPXLogger.updateError(e);
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

			VPXLogger.updateLog("Message sent to " + ip);

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

	}

	public void sendMessageToProcessor(String ip, String msg) {

		send(msg.getBytes(), ip, VPXUDPListener.CONSOLE_MSG_PORTNO, false);

	}

	// Sending hex array to processors to execution

	public void startDownloading(VPX_ExecutionFileTransferingWindow exeProgressWindow, String ip,
			List<ExecutionHexArray> hexArray) {

		this.executionCoreHexArray = hexArray;

		this.executionLoadingDialog = exeProgressWindow;

		currentFileIndex = -1;

		currentbyteArray = null;

		executionLoadingDialog.setTotalMaxFiles(executionCoreHexArray.size());

		isExecutionDownloadStatred = true;

		DSPATPCommand atp = new DSPATPCommand();

		atp.msgID.set(ATP.MSG_ID_SET);

		atp.msgType.set(ATP.MSG_TYPE_EXECUTE);

		sendNextFile(ip, atp);

	}

	private void sendNextFile(String ip, ATPCommand command) {

		String filename = null;

		currentFileIndex++;

		if (currentFileIndex < executionCoreHexArray.size()) {

			executionLoadingDialog.resetCurrentProcess();

			ExecutionHexArray e = executionCoreHexArray.get(currentFileIndex);

			filename = e.getFileName();

			currentCore = Integer.parseInt(filename.substring(filename.length() - 3, filename.length() - 2));

			command.params.memoryinfo.core.set(currentCore);

			executionLoadingDialog.updateCurrentFile(filename);

			executionLoadingDialog.updateOverallProgress(currentFileIndex + 1);

			currentbyteArray = e.getHexArray();

			// System.out.println(currentbyteArray.length);

			currentbyteTotalpckt = currentbyteArray.length / ATP.DEFAULTBUFFERSIZE;

			int rem = (int) (currentbyteArray.length % ATP.DEFAULTBUFFERSIZE);

			if (rem > 0)
				currentbyteTotalpckt++;

			command.params.flash_info.totalnoofpackets.set(currentbyteTotalpckt);

			command.params.flash_info.totalfilesize.set(currentbyteArray.length);

			executionLoadingDialog.setCurrentMaxPackets(currentbyteTotalpckt);

			sendNextArray(ip, command, -1);
		} else {

			executionLoadingDialog.resetCurrentProcess();

			executionLoadingDialog.resetTotalProcess();

			this.executionCoreHexArray.clear();

			isExecutionDownloadStatred = false;

			VPXUtilities.writeFile("D:\\sample.txt", bytes);
		}

	}

	private void sendNextArray(String ip, ATPCommand command, int currArrPacket) {

		int cur = currArrPacket + 1;

		int start = cur * ATP.DEFAULTBUFFERSIZE;

		int end = start + ATP.DEFAULTBUFFERSIZE;

		if (end > currentbyteArray.length) {

			end = currentbyteArray.length;
		}

		byte[] b = new byte[end - start];

		int j = 0;

		for (int i = start; i < end; i++) {

			b[j] = currentbyteArray[i];

			// addBytes(b[j], j);

			j++;
		}

		command.params.flash_info.currentpacket.set(cur);

		// System.out.println(cur);

		executionLoadingDialog.updateCurrentProgress(cur);

		sendHexArrayToProcessor(ip, cur, command.params.flash_info.totalnoofpackets.get(),
				command.params.flash_info.totalfilesize.get(), (int) command.params.memoryinfo.core.get(), b);
	}

	public void sendHexArrayToProcessor(String ip, int currentPacket, long totalPacket, long arrayLenth, int core,
			byte[] buf) {
		try {

			if (currentPacket < totalPacket) {

				ATPCommand msg = new DSPATPCommand();

				byte[] buffer = new byte[msg.size()];

				ByteBuffer bf = ByteBuffer.wrap(buffer);

				bf.order(msg.byteOrder());

				msg.setByteBuffer(bf, 0);

				msg.msgID.set(ATP.MSG_ID_SET);

				msg.msgType.set(ATP.MSG_TYPE_EXECUTE);

				msg.params.flash_info.totalfilesize.set(arrayLenth);

				msg.params.flash_info.totalnoofpackets.set(totalPacket);

				for (int i = 0; i < buf.length; i++) {

					msg.params.memoryinfo.buffer[i].set(buf[i]);

				}

				msg.params.memoryinfo.core.set(core);

				msg.params.memoryinfo.length.set(buf.length);

				msg.params.flash_info.currentpacket.set(currentPacket);

				send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);
			}
		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

	}

	public void sendExecutionCommand(String ip, int command, int core) {

		try {

			ATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(command);

			// 0 - Single Core
			// 1 - Multi Core
			msg.params.memoryinfo.byteZero.set(0);

			msg.params.memoryinfo.core.set(core);

			send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

	}

	public void sendExecutionCommand(String ip, int command, int core1, int core2, int core3, int core4, int core5,
			int core6, int core7) {

		try {

			ATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(command);

			// 0 - Single Core
			// 1 - Multi Core
			msg.params.memoryinfo.byteZero.set(1);

			msg.params.memoryinfo.buffer[0].set((short) core1);

			msg.params.memoryinfo.buffer[1].set((short) core2);

			msg.params.memoryinfo.buffer[2].set((short) core3);

			msg.params.memoryinfo.buffer[3].set((short) core4);

			msg.params.memoryinfo.buffer[4].set((short) core5);

			msg.params.memoryinfo.buffer[5].set((short) core6);

			msg.params.memoryinfo.buffer[6].set((short) core7);

			send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

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

			VPXLogger.updateLog(String.format("Start flashing file ( %s ) for %s on flash device %s at page %d",
					filename, ip, (flashDevice == 0 ? "NAND" : "NOR"), location));

		} catch (Exception e) {
			VPXLogger.updateError(e);
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
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

	}

	public void sendTFTP(String ip, VPX_FlashProgressWindow parentDialog, String serverIP, String filename,
			int fileType) {

		DatagramSocket datagramSocket;

		ATPCommand msg = null;

		byte[] buffer = null;

		ByteBuffer bf = null;

		try {

			this.dialog = parentDialog;

			datagramSocket = new DatagramSocket();

			msg = new P2020ATPCommand();

			buffer = new byte[msg.size()];

			bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_FLASH);

			msg.params.TFTPInfo.filename.set(filename);

			msg.params.TFTPInfo.filetype.set(fileType);

			msg.params.TFTPInfo.ip.set(serverIP);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.COMM_PORTNO);

			VPXNetworkLogger
					.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), VPXSessionManager.getCurrentIP(),
							ip, "UDP", VPXUDPListener.COMM_PORTNO, buffer.length, new JLabel("Sending"), buffer));

			datagramSocket.send(packet);

			datagramSocket.close();

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
				VPXLogger.updateError(e);
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

			VPXLogger.updateLog(String.format("Sending file ( %s ) to %s start address 0x%08x", filename, ip, address));

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}
	}

	public void sendMemoryFile(VPX_MemoryLoadProgressWindow parentDialog, String filename, long address, String ip,
			boolean isBinary, int format, int delimiter) {

		try {

			File f = new File(filename);

			// size = FileUtils.sizeOf(f);

			if (isBinary)

				filestoSend = VPXUtilities.readFileToByteArray(filename);

			else
				filestoSend = VPXUtilities.readFileToByteArray(filename, format, delimiter);

			size = filestoSend.length;

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

			VPXLogger.updateLog(String.format("Sending file ( %s ) to %s start address 0x%08x", filename, ip, address));

		} catch (Exception e) {
			VPXLogger.updateError(e);
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

				printBytes(sendBuffer[i], i);

			}

			msg.params.flash_info.totalnoofpackets.set(tot);

			msg.params.flash_info.totalfilesize.set(size);

			msg.params.flash_info.currentpacket.set(0);

			msg.params.memoryinfo.length.set(length);

			this.memLoadDialog.updatePackets(size, tot, 0, sendBuffer.length, sendBuffer.length);

			send(buffer, InetAddress.getByName(ip), VPXUDPListener.COMM_PORTNO, false);

		} catch (Exception e) {
			VPXLogger.updateError(e);
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

						printBytes(bb[i], i);

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
				VPXLogger.updateError(e);
				e.printStackTrace();
			}
		}
	}

	public void send(byte[] buffer, String ip, int port, boolean isBroadCast) {

		try {

			send(buffer, InetAddress.getByName(ip), port, isBroadCast);

		} catch (UnknownHostException e) {
			VPXLogger.updateError(e);
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

			VPXNetworkLogger
					.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), VPXSessionManager.getCurrentIP(),
							ip.getHostAddress(), "UDP", port, buffer.length, new JLabel("Sending"), buffer));

			datagramSocket.send(packet);

			datagramSocket.close();

		} catch (Exception e) {

			VPXLogger.updateError(e);

			e.printStackTrace();
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

			VPXLogger.updateLog(String.format("Flashing %s  is cacelled by user", ip));

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

	}

	public void startBist(String ip, String SubSystem, BIST bit) {

		bist = bit;// new BIST();

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

			VPXLogger.updateLog(String.format("Built in Self Test is started for %s", ip));

		} catch (Exception e) {
			VPXLogger.updateError(e);
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

			VPXNetworkLogger
					.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), VPXSessionManager.getCurrentIP(),
							ip, "UDP", VPXUDPListener.COMM_PORTNO, buffer.length, new JLabel("Sending"), buffer));

			datagramSocket.send(packet);

			VPXLogger.updateLog(String.format("New value set for %s core %d address 0x%08X length %d Value 0x%08X", ip,
					core, fromAddress, length, newValue));

			MemoryViewFilter filter = null;

			switch (memindex) {
			case 0:
			default:

				filter = currFilter0;

				break;

			case 1:

				filter = currFilter1;

				break;

			case 2:

				filter = currFilter2;

				break;

			case 3:

				filter = currFilter3;

				break;
			}

			Thread.sleep(15);

			readMemory(filter);

		} catch (Exception e) {
			VPXLogger.updateError(e);
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

			msg.params.memoryinfo.stride.set(Integer.valueOf(filter.getMemoryStride()));

			msg.params.memoryinfo.byteZero.set(Integer.valueOf(filter.getSize()));

			msg.params.memoryinfo.memIndex.set(filter.getMemoryBrowserID());

			int id = filter.getMemoryBrowserID();

			switch (id) {
			case 0:
			default:

				currFilter0 = filter;

				break;

			case 1:

				currFilter1 = filter;

				break;

			case 2:

				currFilter2 = filter;

				break;

			case 3:

				currFilter3 = filter;

				break;
			}

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					InetAddress.getByName(filter.getProcessor()), VPXUDPListener.COMM_PORTNO);

			VPXNetworkLogger.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0),
					VPXSessionManager.getCurrentIP(), filter.getProcessor(), "UDP", VPXUDPListener.COMM_PORTNO,
					buffer.length, new JLabel("Sending"), buffer));

			datagramSocket.send(packet);

			VPXLogger.updateLog(String.format("Read memory from %s core %s address %s length %s", filter.getProcessor(),
					filter.getCore(), filter.getMemoryAddress(), filter.getMemoryLength()));

		} catch (Exception e) {
			VPXLogger.updateError(e);
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

				// printBytes(b[i], i);

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

						memOffset0 = (int) (msg.params.flash_info.currentpacket.get() - 1) * ATP.DEFAULTBUFFERSIZE;

						int len = (int) msg.params.memoryinfo.length.get() - memOffset0;

						System.arraycopy(b, 0, memoryBuff0, memOffset0, len);

					} else if (index == 1) {

						memOffset1 = (int) (msg.params.flash_info.currentpacket.get() - 1) * ATP.DEFAULTBUFFERSIZE;

						int len = (int) msg.params.memoryinfo.length.get() - memOffset1;

						System.arraycopy(b, 0, memoryBuff1, memOffset1, len);

					} else if (index == 2) {

						memOffset2 = (int) (msg.params.flash_info.currentpacket.get() - 1) * ATP.DEFAULTBUFFERSIZE;

						int len = (int) msg.params.memoryinfo.length.get() - memOffset2;

						System.arraycopy(b, 0, memoryBuff1, memOffset2, len);

					} else if (index == 3) {

						memOffset3 = (int) (msg.params.flash_info.currentpacket.get() - 1) * ATP.DEFAULTBUFFERSIZE;

						int len = (int) msg.params.memoryinfo.length.get() - memOffset3;

						System.arraycopy(b, 0, memoryBuff3, memOffset3, len);

					}

					isComplete = true;

				} else {

					if (index == 0) {

						memOffset0 = (int) (msg.params.flash_info.currentpacket.get() * ATP.DEFAULTBUFFERSIZE);

						System.arraycopy(b, 0, memoryBuff0, memOffset0, b.length);

					} else if (index == 1) {

						memOffset1 = (int) (msg.params.flash_info.currentpacket.get() * ATP.DEFAULTBUFFERSIZE);

						System.arraycopy(b, 0, memoryBuff1, memOffset1, b.length);

					} else if (index == 2) {

						memOffset2 = (int) (msg.params.flash_info.currentpacket.get() * ATP.DEFAULTBUFFERSIZE);

						System.arraycopy(b, 0, memoryBuff0, memOffset2, b.length);

					} else if (index == 3) {

						memOffset2 = (int) (msg.params.flash_info.currentpacket.get() * ATP.DEFAULTBUFFERSIZE);

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

				parent.populateMemory(index, msg.params.memoryinfo.address.get(),
						(int) msg.params.memoryinfo.stride.get(), bb);

			}
		} catch (Exception e) {
			VPXLogger.updateError(e);
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

			msg.params.memoryinfo.stride.set(Integer.valueOf(filter.getMemoryStride()));

			msg.params.memoryinfo.memIndex.set(filter.getMemoryBrowserID());

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					InetAddress.getByName(filter.getProcessor()), VPXUDPListener.COMM_PORTNO);

			VPXNetworkLogger.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0),
					VPXSessionManager.getCurrentIP(), filter.getProcessor(), "UDP", VPXUDPListener.COMM_PORTNO,
					buffer.length, new JLabel("Sending"), buffer));

			datagramSocket.send(packet);

			VPXLogger.updateLog(String.format("Read plot from %s core %s address %s length %s", filter.getProcessor(),
					filter.getCore(), filter.getMemoryAddress(), filter.getMemoryLength()));

		} catch (Exception e) {
			VPXLogger.updateError(e);
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

			msg.params.memoryinfo.stride.set(Integer.valueOf(filter1.getMemoryStride()));

			msg.params.memoryinfo.memIndex.set(filter1.getMemoryBrowserID());

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					InetAddress.getByName(filter1.getProcessor()), VPXUDPListener.COMM_PORTNO);

			VPXNetworkLogger.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0),
					VPXSessionManager.getCurrentIP(), filter1.getProcessor(), "UDP", VPXUDPListener.COMM_PORTNO,
					buffer.length, new JLabel("Sending"), buffer));

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

			msg.params.memoryinfo.stride.set(Integer.valueOf(filter2.getMemoryStride()));

			msg.params.memoryinfo.memIndex.set(filter2.getMemoryBrowserID());

			packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(filter2.getProcessor()),
					VPXUDPListener.COMM_PORTNO);

			VPXNetworkLogger.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0),
					VPXSessionManager.getCurrentIP(), filter2.getProcessor(), "UDP", VPXUDPListener.COMM_PORTNO,
					buffer.length, new JLabel("Sending"), buffer));

			datagramSocket.send(packet);

			VPXLogger.updateLog(String.format("Read plot from %s core %s address %s length %s", filter1.getProcessor(),
					filter1.getCore(), filter1.getMemoryAddress(), filter1.getMemoryLength()));

			VPXLogger.updateLog(String.format("Read plot from %s core %s address %s length %s", filter2.getProcessor(),
					filter2.getCore(), filter2.getMemoryAddress(), filter2.getMemoryLength()));

		} catch (Exception e) {
			VPXLogger.updateError(e);
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

						plotOffset0 = (int) (msg.params.flash_info.currentpacket.get() - 1) * ATP.DEFAULTBUFFERSIZE;

						int len = (int) msg.params.memoryinfo.length.get() - plotOffset0;

						System.arraycopy(b, 0, plotBuff0, plotOffset0, len);

					} else if (index == 1) {

						plotOffset1 = (int) (msg.params.flash_info.currentpacket.get() - 1) * ATP.DEFAULTBUFFERSIZE;

						int len = (int) msg.params.memoryinfo.length.get() - plotOffset1;

						System.arraycopy(b, 0, plotBuff1, plotOffset1, len);

					} else if (index == 2) {

						plotOffset2 = (int) (msg.params.flash_info.currentpacket.get() - 1) * ATP.DEFAULTBUFFERSIZE;

						int len = (int) msg.params.memoryinfo.length.get() - plotOffset2;

						System.arraycopy(b, 0, plotBuff2, plotOffset2, len);

					}

					isComplete = true;

				} else {

					if (index == 0) {

						plotOffset0 = (int) (msg.params.flash_info.currentpacket.get() * ATP.DEFAULTBUFFERSIZE);

						System.arraycopy(b, 0, plotBuff0, plotOffset0, b.length);

					} else if (index == 1) {

						plotOffset1 = (int) (msg.params.flash_info.currentpacket.get() * ATP.DEFAULTBUFFERSIZE);

						System.arraycopy(b, 0, plotBuff1, plotOffset1, b.length);

					} else if (index == 2) {

						plotOffset2 = (int) (msg.params.flash_info.currentpacket.get() * ATP.DEFAULTBUFFERSIZE);

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

				parent.populatePlot(index, lineid, msg.params.memoryinfo.address.get(), bb);

			}
		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}
	}

	public void readSpectrum(String ip, int core, int id) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			PROCESSOR_LIST procesor = vpxSystem.getProcessorTypeByIP(ip);

			ATPCommand msg = (procesor == PROCESSOR_LIST.PROCESSOR_P2020) ? new P2020ATPCommand() : new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_GET);

			msg.msgType.set(ATP.MSG_TYPE_DATA_ANALYSIS);

			msg.params.memoryinfo.memIndex.set(id);

			msg.params.memoryinfo.core.set(core);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.COMM_PORTNO);

			VPXNetworkLogger
					.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), VPXSessionManager.getCurrentIP(),
							ip, "UDP", VPXUDPListener.COMM_PORTNO, buffer.length, new JLabel("Sending"), buffer));

			datagramSocket.send(packet);

			VPXLogger.updateLog("Data read from " + ip);

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

	}

	public void setSpectrumInterrupted(String ip) {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			DSPATPCommand msg = new DSPATPCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.msgID.set(ATP.MSG_ID_SET);

			msg.msgType.set(ATP.MSG_TYPE_DATA_ANALYSISL_INTERRUPTED);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),
					VPXUDPListener.COMM_PORTNO);

			VPXNetworkLogger
					.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), VPXSessionManager.getCurrentIP(),
							ip, "UDP", VPXUDPListener.COMM_PORTNO, buffer.length, new JLabel("Sending"), buffer));

			datagramSocket.send(packet);

			VPXLogger.updateLog(String.format("Reading data from %s interrupted", ip));

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

	}

	public void populateSpectrum(String ip, ATPCommand msg) {

		try {

			int index = (int) msg.params.memoryinfo.memIndex.get();

			byte[] b = new byte[(int) msg.params.memoryinfo.length.get() * 4];

			float[] fl = new float[(int) msg.params.memoryinfo.length.get()];

			for (int i = 0; i < b.length; i++) {

				b[i] = (byte) msg.params.memoryinfo.buffer[i].get();

				// printBytes(b[i], i);

			}

			ByteBuffer.wrap(b).order(ATP.BYTEORDER_DSP).asFloatBuffer().get(fl);

			parent.populateAnalyticalData(ip, (int) msg.params.memoryinfo.core.get(), index, fl);

		} catch (Exception e) {

			VPXLogger.updateError(e);

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

				VPXLogger.updateLog(String.format("P2020 (%s) test is completed", ip));

				bist.setP2020Completed(true);

			} else if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_DSP1) {

				bist.setResultDSP1DDR3(getResultInColor(msg.params.testinfo.RESULT_DSP_DDR3.get(), 0));

				bist.setResultDSP1NAND(getResultInColor(msg.params.testinfo.RESULT_DSP_NAND.get(), 0));

				bist.setResultDSP1NOR(getResultInColor(msg.params.testinfo.RESULT_DSP_NOR.get(), 0));

				bist.setResultDSP1Processor(getResultInColor(msg.params.testinfo.RESULT_DSP_PROCESSOR.get(), 0));

				bist.setTestDSP1IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_DSP1, loop);

				VPXLogger.updateLog(String.format("DSP 1 (%s) test is completed", ip));

				bist.setDSP1Completed(true);

			} else if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_DSP2) {

				bist.setResultDSP2DDR3(getResultInColor(msg.params.testinfo.RESULT_DSP_DDR3.get(), 0));

				bist.setResultDSP2NAND(getResultInColor(msg.params.testinfo.RESULT_DSP_NAND.get(), 0));

				bist.setResultDSP2NOR(getResultInColor(msg.params.testinfo.RESULT_DSP_NOR.get(), 0));

				bist.setResultDSP2Processor(getResultInColor(msg.params.testinfo.RESULT_DSP_PROCESSOR.get(), 0));

				bist.setTestDSP2IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_DSP2, loop);

				VPXLogger.updateLog(String.format("DSP 2 (%s) test is completed", ip));

				bist.setDSP2Completed(true);
			}

			bist.setResultTestNoofTests(String.format("%d Tests", (pass + fail)));

			bist.setResultTestFailed(String.format("%d Tests", fail));

			if (fail == 0) {

				bist.setResultTestStatus("Success !");

				VPXLogger.updateLog("Built In Self Test Success");

			} else {

				bist.setResultTestStatus("Failed !");

				VPXLogger.updateLog("Built In Self Test Failed");
			}

			bist.setResultTestPassed(String.format("%d Tests", pass));

			long diff = System.currentTimeMillis() - bist.getStartTime();

			bist.setResultTestDuration(VPXUtilities.getCurrentTime(4, diff));

			String str = VPXUtilities.getCurrentTime(5, System.currentTimeMillis());

			bist.setResultTestCompletedAt(str);

			((VPXCommunicationListener) listener).updateBIST(bist);

		}
	}

	public void populateBISTResult(String ip, BISTCommand msg) {

		if (bist != null) {

			if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_P2020) {

				bist.setResultP2020Processor(getResultInColor(msg.test.RESULT_P2020_PROCESSOR.get(), 0));

				bist.setResultP2020DDR3(getResultInColor(msg.test.RESULT_P2020_DDR3.get(), 0));

				bist.setResultP2020NORFlash(getResultInColor(msg.test.RESULT_P2020_NORFLASH.get(), 0));

				bist.setResultP2020Ethernet(getResultInColor(msg.test.RESULT_P2020_ETHERNET.get(), 0));

				bist.setResultP2020SRIO(getResultInColor(msg.test.RESULT_P2020_SRIO.get(), 0));

				bist.setResultP2020PCIe(getResultInColor(msg.test.RESULT_P2020_PCIE.get(), 0));

				bist.setResultP2020Temprature1(getResultInColor(msg.test.RESULT_P2020_TEMP1.get(), 1));

				bist.setResultP2020Temprature2(getResultInColor(msg.test.RESULT_P2020_TEMP2.get(), 1));

				bist.setResultP2020Temprature3(getResultInColor(msg.test.RESULT_P2020_TEMP3.get(), 1));

				bist.setResultP2020Voltage1(getResultInColor(msg.test.RESULT_P2020_VOLT1_3p3.get(), 2));

				bist.setResultP2020Voltage2(getResultInColor(msg.test.RESULT_P2020_VOLT2_2p5.get(), 2));

				bist.setResultP2020Voltage3(getResultInColor(msg.test.RESULT_P2020_VOLT3_1p8.get(), 2));

				bist.setResultP2020Voltage4(getResultInColor(msg.test.RESULT_P2020_VOLT4_1p5.get(), 2));

				bist.setResultP2020Voltage5(getResultInColor(msg.test.RESULT_P2020_VOLT5_1p2.get(), 2));

				bist.setResultP2020Voltage6(getResultInColor(msg.test.RESULT_P2020_VOLT6_1p0.get(), 2));

				bist.setResultP2020Voltage7(getResultInColor(msg.test.RESULT_P2020_VOLT7_1p05.get(), 2));

				bist.setTestP2020IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_P2020, loop);

				VPXLogger.updateLog(String.format("P2020 (%s) test is completed", ip));

				bist.setP2020Completed(true);

			} else if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_DSP1) {

				bist.setResultDSP1DDR3(getResultInColor(msg.test.RESULT_DSP_DDR3.get(), 0));

				bist.setResultDSP1NAND(getResultInColor(msg.test.RESULT_DSP_NAND.get(), 0));

				bist.setResultDSP1NOR(getResultInColor(msg.test.RESULT_DSP_NOR.get(), 0));

				bist.setResultDSP1Processor(getResultInColor(msg.test.RESULT_DSP_PROCESSOR.get(), 0));

				bist.setTestDSP1IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_DSP1, loop);

				VPXLogger.updateLog(String.format("DSP 1 (%s) test is completed", ip));

				bist.setDSP1Completed(true);

			} else if (msg.processorTYPE.get() == ATP.PROCESSOR_TYPE.PROCESSOR_DSP2) {

				bist.setResultDSP2DDR3(getResultInColor(msg.test.RESULT_DSP_DDR3.get(), 0));

				bist.setResultDSP2NAND(getResultInColor(msg.test.RESULT_DSP_NAND.get(), 0));

				bist.setResultDSP2NOR(getResultInColor(msg.test.RESULT_DSP_NOR.get(), 0));

				bist.setResultDSP2Processor(getResultInColor(msg.test.RESULT_DSP_PROCESSOR.get(), 0));

				bist.setTestDSP2IP(ip);

				loop++;

				((VPXCommunicationListener) listener).updateTestProgress(PROCESSOR_LIST.PROCESSOR_DSP2, loop);

				VPXLogger.updateLog(String.format("DSP 2 (%s) test is completed", ip));

				bist.setDSP2Completed(true);
			}

			bist.setResultTestNoofTests(String.format("%d Tests", (pass + fail)));

			bist.setResultTestFailed(String.format("%d Tests", fail));

			if (fail == 0) {

				bist.setResultTestStatus("Success !");

				VPXLogger.updateLog("Built In Self Test Success");

			} else {

				bist.setResultTestStatus("Failed !");

				VPXLogger.updateLog("Built In Self Test Failed");
			}

			bist.setResultTestPassed(String.format("%d Tests", pass));

			String str = VPXUtilities.getCurrentTime(5, System.currentTimeMillis());

			bist.setResultTestCompletedAt(str);

			bist.setResultTestDuration(
					VPXUtilities.getCurrentTime(2, System.currentTimeMillis() - bist.getStartTime()));

			((VPXCommunicationListener) listener).updateBIST(bist);
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

	public void setTFTPServer(VPXTFTPMonitor tftpServer) {

		this.tftp = tftpServer;
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
			VPXLogger.updateError(e);
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

				parent.updateExit(i++);

				Thread.sleep(150);

				sendClose(vpxSubSystem.getIpDSP1(), PROCESSOR_LIST.PROCESSOR_DSP1);

				parent.updateExit(i++);

				Thread.sleep(150);

				sendClose(vpxSubSystem.getIpDSP2(), PROCESSOR_LIST.PROCESSOR_DSP2);

				parent.updateExit(i++);

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

				parent.updateExit(i++);

				Thread.sleep(150);

			}
		} catch (Exception e) {
			VPXLogger.updateError(e);
		}

		parent.updateExit(-1);
	}

	public void addUDPListener(VPXUDPListener udpListener) {

		this.listener = udpListener;

		parent = ((VPX_ETHWindow) listener);

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

			case ATP.MSG_TYPE_EXECUTE_ACK:

				if (isExecutionDownloadStatred) {

					sendNextArray(ip, msgCommand, (int) msgCommand.params.flash_info.currentpacket.get());

				}

				break;

			case ATP.MSG_TYPE_EXECUTE_DONE:

				if (isExecutionDownloadStatred) {

					sendNextFile(ip, msgCommand);

				}

				break;

			case ATP.MSG_TYPE_PERIDAICITY:

				break;

			case ATP.MSG_TYPE_BIST:

				break;

			case ATP.MSG_TYPE_FLASH_DONE:

				if (dialog != null)
					dialog.doneFlash();

				if (tftp != null)
					tftp.shutdown();

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

			case ATP.MSG_TYPE_DATA_ANALYSIS:

				populateSpectrum(ip, msgCommand);

				break;

			}

		}

	}

	private synchronized void parseCommunicationPacket(String ip, BISTCommand msgCommand) {

		int msgID = (int) msgCommand.msgID.get();

		int msgType = (int) msgCommand.msgType.get();

		if (msgID == ATP.MSG_ID_GET) {

			if (msgType == ATP.MSG_TYPE_BIST) {

				populateBISTResult(ip, msgCommand);
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

	private BISTCommand createBISTCommand(String ip, byte[] recvdBytes) {

		BISTCommand bistCommand = new BISTCommand();

		byte[] b = new byte[bistCommand.size()];

		System.arraycopy(recvdBytes, 0, b, 0, b.length);

		ByteBuffer bf = ByteBuffer.allocate(b.length);// istCommand.size());

		bf.clear();

		bf.put(b);

		bf.flip();

		bistCommand.getByteBuffer().clear();

		bistCommand.getByteBuffer().put(bf);

		return bistCommand;
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

			while (isRunning) {

				// if (!messageReceiverSocket.isClosed()) {
				try {

					messageReceiverSocket.receive(messagePacket);

					String ip = messagePacket.getAddress().getHostAddress();

					VPXNetworkLogger.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), ip,
							VPXSessionManager.getCurrentIP(), "UDP", messageReceiverSocket.getLocalPort(),
							messagePacket.getLength(), new JLabel("Recieved Message Packet"), messageData));

					parseMessagePacket(ip, createMSGCommand(ip, messageData));

					Thread.sleep(10);

				} catch (Exception e) {

					VPXLogger.updateError(e);

					// e.printStackTrace();
				}
				// } else {
				// break;
				// }
			}

		}

		public void stop() {

			isRunning = false;

			messageReceiverSocket.close();
		}

	}

	// Communication Monitor
	class VPXCommMonitor implements Runnable {

		DatagramSocket communicationSocket = null;

		ATPCommand cmd = new ATPCommand();

		byte[] commandData = new byte[((ATPCommand) cmd).size()];

		DatagramPacket messagePacket = new DatagramPacket(commandData, commandData.length);

		public VPXCommMonitor() throws Exception {

			communicationSocket = new DatagramSocket(VPXUDPListener.COMM_PORTNO);

		}

		@Override
		public void run() {

			while (isRunning) {

				// if (!communicationSocket.isClosed()) {
				try {

					communicationSocket.receive(messagePacket);

					String ip = messagePacket.getAddress().getHostAddress();

					VPXNetworkLogger.updatePacket(new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), ip,
							VPXSessionManager.getCurrentIP(), "UDP", communicationSocket.getLocalPort(),
							messagePacket.getLength(), new JLabel("Recieved Communication Packet"), commandData));

					if (messagePacket.getLength() > 64)

						parseCommunicationPacket(ip, createATPCommand(ip, commandData));
					else
						parseCommunicationPacket(ip, createBISTCommand(ip, commandData));

					Thread.sleep(10);

				} catch (Exception e) {
					VPXLogger.updateError(e);
					// e.printStackTrace();
				}
				// } else {
				// break;
				// }

			}

		}

		public void stop() {

			isRunning = false;

			communicationSocket.close();
		}

	}

	// Advertisement Monitor
	class VPXAdvMonitor implements Runnable {

		DatagramSocket advertisementSocket;

		byte[] advertisementData = new byte[6];

		String ip = "";

		DatagramPacket advertisementPacket = new DatagramPacket(advertisementData, advertisementData.length);

		public VPXAdvMonitor() {

			try {
				advertisementSocket = new DatagramSocket(VPXUDPListener.ADV_PORTNO);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {

			while (isRunning) {

				// if (!advertisementSocket.isClosed()) {

				try {
					isipinRange = true;

					advertisementSocket.receive(advertisementPacket);

					ip = advertisementPacket.getAddress().getHostAddress();

					VPXNetworkLogger.updatePacket(
							new VPXNWPacket(0, VPXUtilities.getCurrentTime(0), ip, VPXSessionManager.getCurrentIP(),
									"UDP", advertisementSocket.getLocalPort(), advertisementPacket.getLength(),
									new JLabel("Recieved Advertisement Packet"), advertisementData));

					parseAdvertisementPacket(ip,
							new String(advertisementPacket.getData(), 0, advertisementPacket.getLength()));

					Thread.sleep(10);

				} catch (Exception e) {
					VPXLogger.updateError(e);
					e.printStackTrace();
				}

				// } else {
				// break;
				// }
			}

		}

		public void stop() {

			isRunning = false;

			advertisementSocket.close();
		}

	}

	private void printBytes(byte byteVal, int i) {

		System.out.print(String.format("%02x ", byteVal));

		if (((i + 1) % 16) == 0) {

			System.out.println();
		}

	}

	private void addBytes(byte byteVal, int i) {

		bytes = bytes + String.format("%02x ", byteVal).toUpperCase();

		if (((i + 1) % 16) == 0) {

			bytes = bytes + System.getProperty("line.separator");

		} else {
			bytes = bytes + " ";
		}

	}

	public void closeAllConnections() {

		isRunning = false;

		// communicationMonitor.stopMonitor();

		// advertisementMonitor.stopMonitor();

		advMonitorRunnable.stop();

		messageMonitorRunnable.stop();

		commMonitorRunnable.stop();

		// communicationMonitor = null;

		// advertisementMonitor = null;

		commMonitorRunnable = null;

		commMonitor = null;

		advMonitorRunnable = null;

		advMonitor = null;

		messageMonitorRunnable = null;

		messageMonitor = null;
	}
}

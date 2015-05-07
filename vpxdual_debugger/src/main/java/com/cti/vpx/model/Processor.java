package com.cti.vpx.model;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cti.vpx.command.ATP;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.P2020ATPCommand;
import com.cti.vpx.util.VPXUtilities;

@XmlRootElement
public class Processor implements VPX {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6214146807961201313L;

	private int ID;

	private String name;

	private String iP_Addresses;

	public static final int PORTNO = 12345;

	public static final int UDP_PORTNO = 12346;

	public static final int CONNECTION_TIMEOUT = 300000;

	private PROCESSOR_LIST processorType;

	private Socket socket = null;

	private ATPCommand cmd;

	private Thread recieverThread = null;

	public Processor() {

	}

	public Processor(String ipAddress, PROCESSOR_LIST pType) {
		this.iP_Addresses = ipAddress;

		this.processorType = pType;

		setName(pType);
	}

	public Processor(PROCESSOR_LIST pType) {

		this.processorType = pType;

		setName(pType);
	}

	public int getID() {
		return ID;
	}

	@XmlElement
	public void setID(int iD) {
		ID = iD;
	}

	public PROCESSOR_LIST getProcessorType() {
		return processorType;
	}

	@XmlElement
	public void setProcessorType(PROCESSOR_LIST processorType) {
		this.processorType = processorType;
		setName(processorType);
	}

	public int getPortno() {
		return PORTNO;
	}

	public static int getUdpPortno() {
		return UDP_PORTNO;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getiP_Addresses() {
		return iP_Addresses;
	}

	@XmlElement
	public void setiP_Addresses(String iP_Addresses) {
		this.iP_Addresses = iP_Addresses;
	}

	public boolean connect() {
		try {

			if (socket == null) {
				socket = new Socket();
			}

			socket.connect(new InetSocketAddress(iP_Addresses, PORTNO), CONNECTION_TIMEOUT);

			socket.setSoTimeout(CONNECTION_TIMEOUT);

			VPXUtilities.updateLog(String.format("%s Connected", iP_Addresses));

			startReciever();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public void disConnect() {
		try {
			if (socket != null) {

				socket.close();

				recieverThread = null;
			}
		} catch (Exception e) {
		}
	}

	public boolean isConnected() {
		if (socket == null)
			return false;

		return socket.isConnected();
	}

	public void send(ATP atpCommand) {

		synchronized (this) {
			try {
				if (processorType == PROCESSOR_LIST.PROCESSOR_P2020) {
					cmd = (P2020ATPCommand) atpCommand;
				} else {
					cmd = (DSPATPCommand) atpCommand;
				}

				cmd.write(socket.getOutputStream());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void receive() {
		try {

			if (isConnected()) {

				cmd.getByteBuffer().clear();

				cmd.read(socket.getInputStream());
			}
		} catch (Exception e) {
		}
	}

	private void startReciever() {

		if (recieverThread == null) {
			recieverThread = new Thread(new ReceiverThread());
		}

		recieverThread.start();

	}

	public void setName(PROCESSOR_LIST pType) {
		if (pType == PROCESSOR_LIST.PROCESSOR_DSP1) {
			this.name = "DSP - 1";
			this.ID = 0;
		} else if (pType == PROCESSOR_LIST.PROCESSOR_DSP2) {
			this.name = "DSP - 2";
			this.ID = 1;
		} else if (pType == PROCESSOR_LIST.PROCESSOR_P2020) {
			this.name = "P2020";
			this.ID = 2;
		}
	}

	private class ReceiverThread implements Runnable {

		@Override
		public void run() {
			while (isConnected()) {
				receive();
			}
		}

	}
}

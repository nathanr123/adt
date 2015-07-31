package com.cti.vpx.command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

import com.cti.vpx.Listener.UDPListener;
import com.cti.vpx.command.ATP.MESSAGE_MODE;

public class GreetingClient {
	public static void main(String[] args) {

		// new GreetingClient().identify("172.17.10.21", 12345);

		new GreetingClient();

	}

	public GreetingClient() {
		startCMD();

	}

	private void startMessage() {
		Thread th = new Thread(new MThreadMonitor());

		th.start();

		sendMessage();
	}

	private void startCMD() {
		Thread th = new Thread(new CThreadMonitor());

		th.start();

		sendCMD();
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

			msg.params.periodicity.set(1);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("172.17.10.130"),
					UDPListener.COMM_PORTNO);

			datagramSocket.send(packet);

			System.out.println("Message Sent");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void sendMessage() {

		DatagramSocket datagramSocket;

		try {
			datagramSocket = new DatagramSocket();

			MessageCommand msg = new MessageCommand();

			byte[] buffer = new byte[msg.size()];

			ByteBuffer bf = ByteBuffer.wrap(buffer);

			// bf.order(msg.byteOrder());

			msg.setByteBuffer(bf, 0);

			msg.mode.set(MESSAGE_MODE.MSG_MODE_MESSAGE);

			msg.core.set(11);

			msg.command_msg.set("msg");

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("172.17.1.28"),
					UDPListener.CONSOLE_MSG_PORTNO);

			datagramSocket.send(packet);

			// System.out.println("Message : " + msg + " to IP : " + ip);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	class MThreadMonitor implements Runnable {
		DatagramSocket messageReceiverSocket;

		MessageCommand msgCommand = new MessageCommand();

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

					System.out.println(msgCommand.core);

					System.out.println(msgCommand.mode);

					System.out.println(msgCommand.command_msg);

					Thread.sleep(500);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	class CThreadMonitor implements Runnable {
		DatagramSocket messageReceiverSocket;

		DSPATPCommand msgCommand = new DSPATPCommand();
		
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

					System.out.println(msgCommand.msgID);

					System.out.println(msgCommand.msgType);

					System.out.println(msgCommand.params.periodicity);

					Thread.sleep(500);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}
}
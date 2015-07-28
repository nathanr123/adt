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

	//Thread th = new Thread(new MThreadMonitor());

	//th.start();

	send();
    }

    public void send() {

	DatagramSocket datagramSocket;

	try {
	    datagramSocket = new DatagramSocket();

	    MessageCommand msg = new MessageCommand();

	    msg.mode.set(MESSAGE_MODE.MSG_MODE_MESSAGE);

	    msg.core.set(11);

	    ByteBuffer buffer1 = ByteBuffer.allocateDirect(10000);

	    buffer1.flip();
	    
	    byte[] buffer = new byte[buffer1.remaining()];
	    
	    buffer1.get(buffer);

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

		    System.out.println(msgCommand.command);

		    System.out.println(msgCommand.argu1);

		    System.out.println(msgCommand.argu2);

		    System.out.println(msgCommand.argu3);

		    System.out.println(msgCommand.argu4);

		    Thread.sleep(500);

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }

	}

    }
}
package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.model.VPXNWPacket;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VPX_NWDataDetailWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9173932374472797677L;
	private JPanel contentPane;
	private String bytes = "";
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPX_NWDataDetailWindow frame = new VPX_NWDataDetailWindow(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VPX_NWDataDetailWindow(VPXNWPacket pkt) {

		init();

		loadComponents();

		loadDetail(pkt);

		setVisible(true);

	}

	private void init() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 600, 500);
		
		setResizable(false);

	}

	private void loadComponents() {

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();

		contentPane.add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();

		textArea.setEditable(false);

		scrollPane.setViewportView(textArea);
	}

	public void loadDetail(VPXNWPacket pkt) {

		createSentData(pkt.getBytes());

		String value = String.format(
				"Packet No : %d\nTime : %s\nSource IP : %s\nDestination IP : %s\nPort : %d\nProtocol : %s\nLength : %s\n-----------------------------\nData : \n\n%s",
				pkt.getPktNo(), pkt.getRecievedTime(), pkt.getSourceIP(), pkt.getDestIP(), pkt.getPort(),
				pkt.getProtocol(), pkt.getLength(), bytes);

		setTitle("Captured packet detail");
		
		textArea.setText(value);
		
		textArea.setCaretPosition(0);
	}

	private void createSentData(byte[] byts) {

		for (int i = 0; i < byts.length; i++) {

			addBytes(byts[i], i);
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

}

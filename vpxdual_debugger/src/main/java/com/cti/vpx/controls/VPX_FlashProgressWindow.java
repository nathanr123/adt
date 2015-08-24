package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import com.cti.vpx.util.VPXUtilities;

public class VPX_FlashProgressWindow extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7250292684798353091L;
	private JLabel lblTotalFileSizeVal;
	private JLabel lblTotalPacketsVal;
	private JLabel lblPacketsSentVal;
	private JLabel lblPacketsRemainingVal;
	private JLabel lblElapsedTimeVal;
	private JProgressBar progressFileSent;
	private JLabel lblFlashing;

	private long starttime;

	private long bytesRecieved;

	private boolean isFlashingStatred = false;

	private VPX_EthernetFlashPanel parent;
	private JLabel lblBytesSentVal;
	private JLabel lblBytesRemainingVal;
	private JLabel lblBytesTotalVal;
	private JLabel lblCurrentBytesVal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_FlashProgressWindow dialog = new VPX_FlashProgressWindow(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_FlashProgressWindow(VPX_EthernetFlashPanel prnt) {

		this.parent = prnt;

		init();

		loadComponents();

	}

	private void init() {

		setTitle("Flash Out File");
		
		setIconImage(VPXUtilities.getAppIcon());

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		setSize(500, 250);

		setLocationRelativeTo(parent);

		getContentPane().setLayout(new BorderLayout(0, 0));

		addWindowListener(this);

	}

	private void loadComponents() {

		JPanel progressPanel = new JPanel();

		progressPanel.setPreferredSize(new Dimension(10, 35));

		getContentPane().add(progressPanel, BorderLayout.SOUTH);

		progressPanel.setLayout(new BorderLayout(0, 0));

		lblFlashing = new JLabel("Flashing File");

		progressPanel.add(lblFlashing, BorderLayout.NORTH);

		progressFileSent = new JProgressBar();

		progressFileSent.setStringPainted(true);

		progressPanel.add(progressFileSent, BorderLayout.SOUTH);

		JPanel detailPanel = new JPanel();

		detailPanel.setBorder(new TitledBorder(null, "Flashing Detail", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		detailPanel.setPreferredSize(new Dimension(25, 10));

		getContentPane().add(detailPanel, BorderLayout.CENTER);

		detailPanel.setLayout(new MigLayout("", "[89px,grow][pref!,grow][pref!,grow][][][fill][grow,fill]",
				"[16px,grow][16px,grow][23.00px,grow][22.00px,grow][14px,grow]"));

		JLabel lblTotalFileSize = new JLabel("Total File Size");

		lblTotalFileSize.setHorizontalAlignment(SwingConstants.LEFT);

		detailPanel.add(lblTotalFileSize, "cell 0 0,alignx left,aligny center");

		JLabel lblDummy1 = new JLabel("");

		lblDummy1.setPreferredSize(new Dimension(30, 0));

		detailPanel.add(lblDummy1, "cell 1 0");

		lblTotalFileSizeVal = new JLabel("10 MBs");

		detailPanel.add(lblTotalFileSizeVal, "cell 2 0,alignx left,aligny center");

		JLabel lblCurrentBytes = new JLabel("Current Bytes");
		lblCurrentBytes.setHorizontalAlignment(SwingConstants.LEFT);
		detailPanel.add(lblCurrentBytes, "cell 4 0");

		JLabel label = new JLabel("");
		label.setPreferredSize(new Dimension(30, 0));
		detailPanel.add(label, "cell 5 0");

		lblCurrentBytesVal = new JLabel("10 MBs");
		detailPanel.add(lblCurrentBytesVal, "cell 6 0");

		JLabel lblTotalPackets = new JLabel("Total Packets");

		lblTotalPackets.setHorizontalAlignment(SwingConstants.LEFT);

		detailPanel.add(lblTotalPackets, "cell 0 1,alignx left,aligny center");

		lblTotalPacketsVal = new JLabel("1500 ");

		detailPanel.add(lblTotalPacketsVal, "cell 2 1,alignx left,aligny center");

		JLabel lblBytesTotal = new JLabel("Total Bytes");
		lblBytesTotal.setHorizontalAlignment(SwingConstants.LEFT);
		detailPanel.add(lblBytesTotal, "cell 4 1");

		lblBytesTotalVal = new JLabel("10 MBs");
		detailPanel.add(lblBytesTotalVal, "cell 6 1");

		JLabel lblPacketsSent = new JLabel("Pakets Sent");

		lblPacketsSent.setHorizontalAlignment(SwingConstants.LEFT);

		detailPanel.add(lblPacketsSent, "cell 0 2,alignx left,aligny center");

		lblPacketsSentVal = new JLabel("100");

		detailPanel.add(lblPacketsSentVal, "cell 2 2,alignx left,aligny center");

		JLabel lblBytesSent = new JLabel("Bytes Sent");
		lblBytesSent.setHorizontalAlignment(SwingConstants.LEFT);
		detailPanel.add(lblBytesSent, "cell 4 2");

		lblBytesSentVal = new JLabel("10 MBs");
		detailPanel.add(lblBytesSentVal, "cell 6 2");

		JLabel lblPacketsRemaining = new JLabel("Packets Remaining");

		lblPacketsRemaining.setHorizontalAlignment(SwingConstants.LEFT);

		detailPanel.add(lblPacketsRemaining, "cell 0 3,alignx left,aligny center");

		lblPacketsRemainingVal = new JLabel("1400");

		detailPanel.add(lblPacketsRemainingVal, "cell 2 3,alignx left,aligny center");

		JLabel lblBytesRemaining = new JLabel("Bytes Remaining");
		lblBytesRemaining.setHorizontalAlignment(SwingConstants.LEFT);
		detailPanel.add(lblBytesRemaining, "cell 4 3");

		lblBytesRemainingVal = new JLabel("10 MBs");
		detailPanel.add(lblBytesRemainingVal, "cell 6 3");

		JLabel lblElapsedTime = new JLabel("Elapsed Time");

		detailPanel.add(lblElapsedTime, "cell 0 4,alignx left,aligny center");

		lblElapsedTimeVal = new JLabel("00:00:03");

		detailPanel.add(lblElapsedTimeVal, "cell 2 4,aligny center");
	}

	public void updatePackets(long size, long totpkt, long curpacket, long bytesRcvd, long currBufferSize) {

		long cur = curpacket;

		if (cur == 0) {

			progressFileSent.setMaximum((int) totpkt);

			progressFileSent.setMinimum((int) cur);

			lblFlashing.setText("Sending File");

			starttime = System.currentTimeMillis();
		}

		cur = cur + 1;

		progressFileSent.setValue((int) cur);

		lblTotalFileSizeVal.setText(toNumInUnits(size));

		lblTotalPacketsVal.setText("" + totpkt);

		lblPacketsSentVal.setText("" + cur);

		bytesRecieved = bytesRecieved + bytesRcvd;

		lblBytesSentVal.setText(bytesRecieved + " bytes");

		lblBytesRemainingVal.setText((size - bytesRecieved) + " bytes");

		lblBytesTotalVal.setText(size + " bytes");

		lblCurrentBytesVal.setText(currBufferSize + " bytes");

		long remain = (totpkt - cur);

		lblPacketsRemainingVal.setText("" + remain);

		if (remain == 0) {
			progressFileSent.setString("done!");
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			flashFile();

		}

		lblElapsedTimeVal.setText(VPXUtilities.getCurrentTime(2, (System.currentTimeMillis() - starttime)));
	}

	public void doneFlash() {

		isFlashingStatred = false;
		
		JOptionPane.showMessageDialog(VPX_FlashProgressWindow.this, "Flash Completed");

		VPX_FlashProgressWindow.this.dispose();

	}

	public void flashFile() {

		isFlashingStatred = true;

		lblFlashing.setText("Flashing File");

		progressFileSent.setString("Flashing on progress");

		progressFileSent.setIndeterminate(true);

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				while (isFlashingStatred) {
					try {

						lblElapsedTimeVal.setText(VPXUtilities.getCurrentTime(2,
								(System.currentTimeMillis() - starttime)));

						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}

			}
		});
		th.start();
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

		String ObjButtons[] = { "Yes", "No" };

		int PromptResult = JOptionPane.showOptionDialog(VPX_FlashProgressWindow.this, "Do you want cancel flashing ?",
				"Confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
				ObjButtons[1]);

		if (PromptResult == 0) {

			isFlashingStatred = false;

			parent.interruptFlash();

			VPX_FlashProgressWindow.this.dispose();
		}

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public String toNumInUnits(long bytes) {
		int u = 0;
		for (; bytes > 1024 * 1024; bytes >>= 10) {
			u++;
		}
		if (bytes > 1024)
			u++;
		return String.format("%.1f %cB", bytes / 1024f, " kMGTPE".charAt(u));
	}
}

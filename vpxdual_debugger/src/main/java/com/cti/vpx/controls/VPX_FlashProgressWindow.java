package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dialog;
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

	private boolean isFlashingStatred = false;

	private JFrame parent;

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
	public VPX_FlashProgressWindow(JFrame prnt) {

		super(prnt);

		this.parent = prnt;

		init();

		loadComponents();

	}

	private void init() {

		setTitle("Flash Out File");

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		setSize(450, 200);

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

		detailPanel.setLayout(new MigLayout("", "[pref!][89px][pref!][46px,grow]",
				"[16px,grow][16px,grow][23.00px,grow][22.00px,grow][14px,grow]"));

		JLabel lblDummy2 = new JLabel("");

		lblDummy2.setPreferredSize(new Dimension(30, 0));

		detailPanel.add(lblDummy2, "cell 0 0");

		JLabel lblTotalFileSize = new JLabel("Total File Size");

		lblTotalFileSize.setHorizontalAlignment(SwingConstants.LEFT);

		detailPanel.add(lblTotalFileSize, "cell 1 0,alignx left,aligny center");

		JLabel lblDummy1 = new JLabel("");

		lblDummy1.setPreferredSize(new Dimension(30, 0));

		detailPanel.add(lblDummy1, "cell 2 0");

		lblTotalFileSizeVal = new JLabel("10 MBs");

		detailPanel.add(lblTotalFileSizeVal, "cell 3 0,alignx left,aligny center");

		JLabel lblTotalPackets = new JLabel("Total Packets");

		lblTotalPackets.setHorizontalAlignment(SwingConstants.LEFT);

		detailPanel.add(lblTotalPackets, "cell 1 1,alignx left,aligny center");

		lblTotalPacketsVal = new JLabel("1500 ");

		detailPanel.add(lblTotalPacketsVal, "cell 3 1,alignx left,aligny center");

		JLabel lblPacketsSent = new JLabel("Pakets Sent");

		lblPacketsSent.setHorizontalAlignment(SwingConstants.LEFT);

		detailPanel.add(lblPacketsSent, "cell 1 2,alignx left,aligny center");

		lblPacketsSentVal = new JLabel("100");

		detailPanel.add(lblPacketsSentVal, "cell 3 2,alignx left,aligny center");

		JLabel lblPacketsRemaining = new JLabel("Packets Remaining");

		lblPacketsRemaining.setHorizontalAlignment(SwingConstants.LEFT);

		detailPanel.add(lblPacketsRemaining, "cell 1 3,alignx left,aligny center");

		lblPacketsRemainingVal = new JLabel("1400");

		detailPanel.add(lblPacketsRemainingVal, "cell 3 3,alignx left,aligny center");

		JLabel lblElapsedTime = new JLabel("Elapsed Time");

		detailPanel.add(lblElapsedTime, "cell 1 4,alignx left,aligny center");

		lblElapsedTimeVal = new JLabel("00:00:03");

		detailPanel.add(lblElapsedTimeVal, "cell 3 4,aligny center");
	}

	public void updatePackets(long size, long totpkt, long curpacket) {

		if (curpacket == 0) {

			progressFileSent.setMaximum((int) totpkt);

			progressFileSent.setMinimum((int) curpacket);

			lblFlashing.setText("Sending File");

			starttime = System.currentTimeMillis();
		}

		progressFileSent.setValue((int) curpacket);

		lblTotalFileSizeVal.setText(toNumInUnits(size));

		lblTotalPacketsVal.setText("" + totpkt);

		lblPacketsSentVal.setText("" + curpacket);

		long remain = (totpkt - curpacket);

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

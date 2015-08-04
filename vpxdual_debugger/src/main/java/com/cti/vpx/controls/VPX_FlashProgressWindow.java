package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.cti.vpx.command.ATPCommand;

import net.miginfocom.swing.MigLayout;

public class VPX_FlashProgressWindow extends JDialog {

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_FlashProgressWindow dialog = new VPX_FlashProgressWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_FlashProgressWindow() {

		init();

		loadComponents();

	}

	private void init() {

		setTitle("Flash Out File");

		setSize(450, 200);

		getContentPane().setLayout(new BorderLayout(0, 0));

	}

	private void loadComponents() {

		JPanel progressPanel = new JPanel();

		progressPanel.setPreferredSize(new Dimension(10, 35));

		getContentPane().add(progressPanel, BorderLayout.SOUTH);

		progressPanel.setLayout(new BorderLayout(0, 0));

		lblFlashing = new JLabel("Flashing File");

		progressPanel.add(lblFlashing, BorderLayout.NORTH);

		progressFileSent = new JProgressBar();

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

	public void updatePackets(ATPCommand cmd) {

		lblTotalFileSizeVal.setText("" + cmd.params.flash_info.totalfilesize.get());

		lblTotalPacketsVal.setText("" + cmd.params.flash_info.totalfilesize.get());

		lblPacketsSentVal.setText("" + cmd.params.flash_info.totalfilesize.get());

		lblPacketsRemainingVal.setText("" + cmd.params.flash_info.totalfilesize.get());
	}
}

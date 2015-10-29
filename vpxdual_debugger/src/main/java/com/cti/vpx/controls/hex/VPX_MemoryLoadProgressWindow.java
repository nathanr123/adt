package com.cti.vpx.controls.hex;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXUtilities;

import net.miginfocom.swing.MigLayout;

public class VPX_MemoryLoadProgressWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7250292684798353091L;

	private JProgressBar progressFileSent;

	private JLabel lblFlashing;

	private long bytesRecieved;

	private VPX_MemoryBrowserWindow parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_MemoryLoadProgressWindow dialog = new VPX_MemoryLoadProgressWindow(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_MemoryLoadProgressWindow(VPX_MemoryBrowserWindow prnt) {
		setUndecorated(true);

		this.parent = prnt;

		init();

		loadComponents();

	}

	private void init() {

		setTitle("Loading Memory");

		setAlwaysOnTop(true);
		
		setIconImage(VPXUtilities.getAppIcon());

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		setSize(500, 149);

		setLocationRelativeTo(parent);

	}

	private void loadComponents() {

		getContentPane().setLayout(new MigLayout("", "[500px]", "[][][35px][]"));

		JPanel progressPanel = new JPanel();

		progressPanel.setPreferredSize(new Dimension(10, 35));

		getContentPane().add(progressPanel, "cell 0 2,growx,aligny top");

		progressPanel.setLayout(new BorderLayout(0, 0));

		lblFlashing = new JLabel("Loading...");

		progressPanel.add(lblFlashing, BorderLayout.NORTH);

		progressFileSent = new JProgressBar();

		progressFileSent.setStringPainted(true);

		progressPanel.add(progressFileSent, BorderLayout.SOUTH);
	}

	public void updatePackets(long size, long totpkt, long curpacket, long bytesRcvd, long currBufferSize) {

		long cur = curpacket;

		if (cur == 0) {

			progressFileSent.setMaximum((int) totpkt);

			progressFileSent.setMinimum((int) cur);

			lblFlashing.setText("Sending File");
		}

		cur = cur + 1;

		progressFileSent.setValue((int) cur);

		bytesRecieved = bytesRecieved + bytesRcvd;

	}

	public void closeLoadMemory() {

		VPX_MemoryLoadProgressWindow.this.dispose();

	}

}

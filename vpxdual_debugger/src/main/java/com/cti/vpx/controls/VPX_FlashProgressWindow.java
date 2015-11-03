package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.cti.vpx.util.VPXUtilities;

import net.miginfocom.swing.MigLayout;

public class VPX_FlashProgressWindow extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7250292684798353091L;

	private JLabel lblElapsedTimeVal;

	private JProgressBar progressFileSent;

	private JLabel lblFlashing;

	private long starttime;

	private boolean isFlashingStatred = false;

	private VPX_EthernetFlashPanel parent;

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

		setSize(500, 104);

		setAlwaysOnTop(true);

		setLocationRelativeTo(parent);

		getContentPane().setLayout(new BorderLayout(0, 0));

		addWindowListener(this);

	}

	private void loadComponents() {

		JPanel progressPanel = new JPanel();

		progressPanel.setPreferredSize(new Dimension(10, 35));

		getContentPane().add(progressPanel, BorderLayout.CENTER);

		progressPanel.setLayout(new MigLayout("", "[440px,left][][44px]", "[14px,grow,fill][17px,grow,fill]"));

		lblFlashing = new JLabel("Flashing File");

		progressPanel.add(lblFlashing, "flowx,cell 0 0 3 1,alignx left,growy");

		progressFileSent = new JProgressBar();

		progressFileSent.setStringPainted(true);

		progressPanel.add(progressFileSent, "cell 0 1 3 1,growx,aligny top");

		lblElapsedTimeVal = new JLabel("");

		progressPanel.add(lblElapsedTimeVal, "cell 1 0,alignx left,growy");
	}

	public void setRangePackets(long min, long max) {

		progressFileSent.setMaximum((int) max);

		progressFileSent.setMinimum((int) min);

		progressFileSent.setValue((int) min);

	}

	public void updatePackets(long curpacket) {

		if (curpacket == 1) {

			lblFlashing.setText("Transferring File");

			starttime = System.currentTimeMillis();
		}

		progressFileSent.setValue((int) curpacket);

		if (progressFileSent.getMaximum() == curpacket) {

			progressFileSent.setString("done!");

			try {

				Thread.sleep(100);

			} catch (Exception e) {
			}

			flashFile();

		}

		lblElapsedTimeVal.setText(VPXUtilities.getCurrentTime(4, (System.currentTimeMillis() - starttime)));
	}

	public void updatePackets(long size, long totpkt, long curpacket, long bytesRcvd, long currBufferSize) {

		long cur = curpacket;

		if (cur == 0) {

			progressFileSent.setMaximum((int) totpkt);

			progressFileSent.setMinimum((int) cur);

			lblFlashing.setText("Transferring File");

			starttime = System.currentTimeMillis();
		}

		cur = cur + 1;

		progressFileSent.setValue((int) cur);

		long remain = (totpkt - cur);

		if (remain == 0) {

			progressFileSent.setString("done!");

			try {

				Thread.sleep(100);

			} catch (Exception e) {
			}

			flashFile();

		}

		lblElapsedTimeVal.setText(VPXUtilities.getCurrentTime(4, (System.currentTimeMillis() - starttime)));
	}

	public void doneFlash() {

		isFlashingStatred = false;

		progressFileSent.setString("flash done!");

		JOptionPane.showMessageDialog(VPX_FlashProgressWindow.this, "Flash Completed");

		parent.shutdownTFTPServer();

		VPX_FlashProgressWindow.this.dispose();

	}

	public void closeLoadMemory() {

		isFlashingStatred = false;

		VPX_FlashProgressWindow.this.dispose();

	}

	public void setLoadingTFTPFileProcess(String name) {

		lblFlashing.setText("Loading file " + name + " on progress");

		progressFileSent.setString("");

		progressFileSent.setIndeterminate(true);
	}

	public void setIndeterminate(boolean val) {

		progressFileSent.setIndeterminate(val);
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

						lblElapsedTimeVal
								.setText(VPXUtilities.getCurrentTime(2, (System.currentTimeMillis() - starttime)));

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

			if (parent != null)
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

}

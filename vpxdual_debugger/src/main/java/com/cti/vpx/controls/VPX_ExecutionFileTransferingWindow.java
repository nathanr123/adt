package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;

import com.cti.vpx.model.ExecutionHexArray;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

public class VPX_ExecutionFileTransferingWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1326475841548848797L;

	private final JPanel contentPanel = new JPanel();

	private JLabel lblCurrentTransfer;

	private JLabel lblTotalTransfer;

	private JProgressBar progressTotal;

	private JProgressBar progressCurrent;

	private JProgressBar progressProcessing;

	private String[] filesDL = null;

	// private Map<String, byte[]> hexFileArray = new TreeMap<String, byte[]>();

	private List<ExecutionHexArray> hexFileArray = new ArrayList<ExecutionHexArray>();// TreeMap<String,
																						// byte[]>();

	private JLabel lblProcessing;

	private VPX_ExecutionPanel executionPanel;

	private VPX_ETHWindow parent;

	private String currentIP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		String[] fileArray = { "D:/outs/EXECUTE_OUT0.out", "D:/outs/EXECUTE_OUT1.out", "D:/outs/EXECUTE_OUT2.out",
				"D:/outs/EXECUTE_OUT3.out", "D:/outs/EXECUTE_OUT4.out", "D:/outs/EXECUTE_OUT5.out",
				"D:/outs/EXECUTE_OUT6.out", "D:/outs/EXECUTE_OUT7.out" };

		try {

			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {

					VPX_ExecutionFileTransferingWindow dialog = new VPX_ExecutionFileTransferingWindow(null);

					dialog.setFiles(fileArray);

					dialog.showProgress("");

				}
			});

			th.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_ExecutionFileTransferingWindow(VPX_ExecutionPanel parentPanel) {

		this.executionPanel = parentPanel;

		init();

		loadComponents();

		centerFrame();
	}

	private void init() {

		setTitle("Downloading file");

		setResizable(false);

		setSize(new Dimension(660, 200));

		setLocationRelativeTo(executionPanel);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}

	private void loadComponents() {

		contentPanel.setLayout(new MigLayout("", "[629px]", "[29px][17px][29px][17px][29px][17px]"));

		lblTotalTransfer = new JLabel("");

		contentPanel.add(lblTotalTransfer, "cell 0 2,grow");

		progressTotal = new JProgressBar();

		progressTotal.setStringPainted(true);

		contentPanel.add(progressTotal, "cell 0 3,growx,aligny top");

		lblCurrentTransfer = new JLabel("Transferring temp.out ");

		contentPanel.add(lblCurrentTransfer, "cell 0 4,grow");

		progressCurrent = new JProgressBar();

		progressCurrent.setStringPainted(true);

		contentPanel.add(progressCurrent, "cell 0 5,growx,aligny top");

		lblProcessing = new JLabel("");

		contentPanel.add(lblProcessing, "cell 0 0,grow");

		progressProcessing = new JProgressBar();

		progressProcessing.setStringPainted(true);

		contentPanel.add(progressProcessing, "cell 0 1,growx,aligny top");
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	public void setCurrentIP(String ip) {

		this.currentIP = ip;

		setTitle("Downloading file to " + ip);

	}

	public void setTotalMaxFiles(int totalFiles) {

		progressTotal.setMinimum(0);

		progressTotal.setValue(0);

		progressTotal.setMaximum(totalFiles);
	}

	public void setCurrentMaxPackets(int totalPackets) {

		progressCurrent.setMinimum(0);

		progressCurrent.setValue(0);

		progressCurrent.setMaximum(totalPackets);
	}

	public void setProcessMaxBytes(int totalBytes) {

		progressProcessing.setValue(0);

		progressProcessing.setMinimum(0);

		progressProcessing.setMaximum(totalBytes);
	}

	public void updateOverallProgress(int currentFile) {

		progressTotal.setValue(currentFile);
	}

	public void updateCurrentProgress(int currentPacket) {

		progressCurrent.setValue(currentPacket);

		progressCurrent.setString(currentPacket + "");
	}

	public void updateProcessingProgress(int currentByte) {

		progressProcessing.setValue(currentByte);

		progressProcessing.setString(currentByte + "");

	}

	public void setParent(VPX_ETHWindow parent) {

		this.parent = parent;

	}

	public void setFiles(String[] filenames) {

		this.filesDL = filenames;
	}

	public void doProcessFile() {

		try {

			String[] byteStringArray = null;

			byte[] byteArray = null;

			FileUtils.cleanDirectory(new java.io.File(VPXSessionManager.getExecutePath()));

			for (int i = 0; i < filesDL.length; i++) {

				if (filesDL[i].length() > 0) {

					lblProcessing.setText("Loading " + filesDL[i]);

					VPXUtilities.createOutArrayFile(filesDL[i],
							VPXSessionManager.getExecutePath() + "\\" + String.format("%d.h", i));
				}

				Thread.sleep(500);
			}

			Thread.sleep(500);

			java.io.File[] f = (new java.io.File(VPXSessionManager.getExecutePath())).listFiles();

			for (int i = 0; i < f.length; i++) {

				lblProcessing.setText("Processing " + f[i].getName());

				byteStringArray = VPXUtilities.getOutFileAsArray(f[i].getAbsolutePath());

				setProcessMaxBytes(byteStringArray.length);

				byteArray = new byte[byteStringArray.length];

				for (int j = 0; j < byteStringArray.length; j++) {

					updateProcessingProgress(j);

					byteArray[j] = (byte) Integer.parseInt(byteStringArray[j].trim(), 16);

				}

				hexFileArray.add(new ExecutionHexArray(f[i].getAbsolutePath(), byteArray));

			}

			progressProcessing.setValue(0);

			progressProcessing.setString("");

			lblProcessing.setText("Processing Completed");

			parent.startDownloadingApplication(this, hexFileArray, currentIP);

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}

	public void showProgress(String ip) {

		this.currentIP = ip;

		try {

			setVisible(true);

			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {

					doProcessFile();

				}
			});

			th.start();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void updateCurrentFile(String filename) {

		lblTotalTransfer.setText("Downloading " + filename);

		lblCurrentTransfer.setText("Transfering " + filename);
	}

	public void resetCurrentProcess() {

		lblCurrentTransfer.setText("Transfering Completed");

		progressCurrent.setValue(0);

		progressCurrent.setString("");
	}

	public void resetTotalProcess() {

		lblTotalTransfer.setText("Downloading Completed");

		progressTotal.setValue(0);

		progressTotal.setString("");

		this.dispose();
	}

	public void startDownloadingFile() {

		/*
		 * Set<Entry<String, byte[]>> set = hexFileArray.entrySet();
		 * 
		 * Iterator<Entry<String, byte[]>> iter = set.iterator();
		 * 
		 * int ii = 0;
		 * 
		 * setTotalMaxFiles(hexFileArray.size());
		 * 
		 * while (iter.hasNext()) {
		 * 
		 * Entry<String, byte[]> e = iter.next();
		 * 
		 * updateCurrentFile(e.getKey());
		 * 
		 * updateOverallProgress(ii + 1);
		 * 
		 * byte[] b = e.getValue();
		 * 
		 * setCurrentMaxPackets(b.length);
		 * 
		 * for (int i = 0; i < b.length; i++) {
		 * 
		 * updateCurrentProgress(i + 1);
		 * 
		 * }
		 * 
		 * resetCurrentProcess();
		 * 
		 * ii++; }
		 * 
		 * resetTotalProcess();
		 */
	}

}

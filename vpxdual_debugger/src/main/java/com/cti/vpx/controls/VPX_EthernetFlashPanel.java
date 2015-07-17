package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VPX_EthernetFlashPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3656689916187874512L;

	private static String ETHNOTE = "<html><body><br><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ethernet Mode will be switched into full functional windows.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User must program LAN Configuration</left></body></html>";

	private JTextField txtBinFilePath;

	final JFileChooser fileDialog = new JFileChooser();

	final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Out Files", "out");

	private JComboBox<String> cmbOffset;

	private JComboBox<String> cmbFlshDevice;

	/**
	 * Create the panel.
	 */
	public VPX_EthernetFlashPanel() {
		init();

		loadComponents();

	}

	private void init() {

		setLayout(new BorderLayout(0, 0));

		setPreferredSize(new Dimension(700, 355));
	}

	private void loadComponents() {

		JPanel flashPanl = new JPanel();

		flashPanl.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Ethernet Flash",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		add(flashPanl, BorderLayout.CENTER);

		flashPanl.setLayout(null);

		JPanel notePanel = new JPanel();

		FlowLayout fl_notePanel = (FlowLayout) notePanel.getLayout();

		fl_notePanel.setAlignment(FlowLayout.LEFT);

		notePanel.setBorder(new TitledBorder(null, "Note", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		notePanel.setBounds(10, 227, 676, 119);

		flashPanl.add(notePanel);

		JLabel lblNote = new JLabel(ETHNOTE);

		notePanel.add(lblNote);

		JPanel flashOptionPanel = new JPanel();

		flashOptionPanel.setBounds(10, 22, 676, 194);

		flashPanl.add(flashOptionPanel);

		flashOptionPanel.setLayout(null);

		JLabel lblBinFile = new JLabel("Bin File");

		lblBinFile.setBounds(10, 107, 114, 22);

		flashOptionPanel.add(lblBinFile);

		JLabel lblOffset = new JLabel("Offset");

		lblOffset.setBounds(15, 64, 114, 22);

		flashOptionPanel.add(lblOffset);

		JLabel lblFlashDevice = new JLabel("Flash Device");

		lblFlashDevice.setBounds(15, 21, 114, 22);

		flashOptionPanel.add(lblFlashDevice);

		JButton btnFlash = new JButton("Flash");

		btnFlash.setBounds(153, 150, 68, 23);

		flashOptionPanel.add(btnFlash);

		JButton btnBoot = new JButton("Boot");

		btnBoot.setBounds(231, 150, 68, 23);

		flashOptionPanel.add(btnBoot);

		JButton btnFlashBoot = new JButton("Flash & Boot");

		btnFlashBoot.setBounds(309, 150, 114, 23);

		flashOptionPanel.add(btnFlashBoot);

		txtBinFilePath = new JTextField();

		txtBinFilePath.setColumns(10);

		txtBinFilePath.setBounds(153, 107, 406, 22);

		flashOptionPanel.add(txtBinFilePath);

		JButton btnBinFileBrowse = new JButton(new BrowseAction("Browse", txtBinFilePath));

		btnBinFileBrowse.setBounds(564, 107, 91, 23);

		flashOptionPanel.add(btnBinFileBrowse);

		cmbOffset = new JComboBox<String>();

		cmbOffset.setPreferredSize(new Dimension(175, 22));

		cmbOffset.setBounds(153, 64, 175, 22);

		flashOptionPanel.add(cmbOffset);

		cmbFlshDevice = new JComboBox<String>();

		cmbFlshDevice.setPreferredSize(new Dimension(175, 22));

		cmbFlshDevice.setBounds(153, 21, 175, 22);

		flashOptionPanel.add(cmbFlshDevice);
	}

	public class BrowseAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		JTextField jtf;

		boolean isXMLFilter = false;

		public BrowseAction(String name, JTextField txt) {

			jtf = txt;

			putValue(NAME, name);

		}

		public BrowseAction(String name, JTextField txt, boolean isXML) {
			jtf = txt;

			this.isXMLFilter = isXML;

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			fileDialog.addChoosableFileFilter(filterOut);

			fileDialog.setAcceptAllFileFilterUsed(false);

			int returnVal = fileDialog.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				java.io.File file = fileDialog.getSelectedFile();

				jtf.setText(file.getPath());
			}
		}
	}
}

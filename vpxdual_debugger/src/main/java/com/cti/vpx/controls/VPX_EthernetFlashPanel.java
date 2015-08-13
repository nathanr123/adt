package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_EthernetFlashPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3656689916187874512L;

	private static String ETHNOTE = "<html><body><left><b>Flash - User must select the .bin file to flash at above selected Device and Location for DSP<br></b></left></body></html>";

	private JTextField txtBinFilePath;

	private final JFileChooser fileDialog = new JFileChooser();

	private final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Bin Files", "bin");

	private JComboBox<String> cmbOffset;

	private JComboBox<String> cmbFlshDevice;

	private JComboBox<String> cmbSubSystem;

	private JComboBox<String> cmbFlshProcessors;

	private boolean isWizard = false;

	private VPX_ETHWindow parent;

	private VPXSystem sys;

	private JButton btnFlash;

	private String flashIP = "";

	public VPX_EthernetFlashPanel(VPX_ETHWindow parent) {

		this.parent = parent;

		init();

		loadComponents();

		loadSubSystems();

		enableWizardComponents();

		loadFlashDevices();

		loadFlashLocations();
	}

	/**
	 * Create the panel.
	 */
	public VPX_EthernetFlashPanel(VPX_ETHWindow parent, boolean iswizard) {

		this.parent = parent;

		this.isWizard = iswizard;

		init();

		loadComponents();

		enableWizardComponents();

		loadFlashDevices();

		loadFlashLocations();
	}

	private void init() {

		setLayout(new BorderLayout(0, 0));

		setPreferredSize(new Dimension(800, 496));
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

		notePanel.setBounds(10, 317, 780, 119);

		flashPanl.add(notePanel);

		JLabel lblNote = new JLabel(ETHNOTE);

		notePanel.add(lblNote);

		JPanel flashOptionPanel = new JPanel();

		flashOptionPanel.setBounds(10, 22, 780, 284);

		flashPanl.add(flashOptionPanel);

		flashOptionPanel.setLayout(null);

		JLabel lblBinFile = new JLabel("Bin File");

		lblBinFile.setBounds(15, 193, 114, 22);

		flashOptionPanel.add(lblBinFile);

		JLabel lblOffset = new JLabel("Offset");

		lblOffset.setBounds(15, 150, 114, 22);

		flashOptionPanel.add(lblOffset);

		JLabel lblFlashDevice = new JLabel("Flash Device");

		lblFlashDevice.setBounds(15, 107, 114, 22);

		flashOptionPanel.add(lblFlashDevice);

		btnFlash = new JButton("Flash");

		btnFlash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_FlashProgressWindow dialog = new VPX_FlashProgressWindow(VPX_EthernetFlashPanel.this);

				dialog.setVisible(true);

				if (VPXUtilities.isFileValid(txtBinFilePath.getText().trim())) {

					if (flashIP.length() == 0) {
						flashIP = cmbFlshProcessors.getSelectedItem().toString();
					}

					parent.sendFile(flashIP, txtBinFilePath.getText(), dialog);

				} else {

					JOptionPane.showMessageDialog(parent, "Selected file is not valid", "Validation",
							JOptionPane.ERROR_MESSAGE);

					txtBinFilePath.requestFocus();
				}
			}
		});

		btnFlash.setBounds(153, 236, 68, 23);

		flashOptionPanel.add(btnFlash);

		txtBinFilePath = new JTextField();

		txtBinFilePath.setColumns(10);

		txtBinFilePath.setBounds(153, 193, 406, 22);

		flashOptionPanel.add(txtBinFilePath);

		JButton btnBinFileBrowse = new JButton(new BrowseAction("Browse", txtBinFilePath));

		btnBinFileBrowse.setBounds(564, 191, 91, 23);

		flashOptionPanel.add(btnBinFileBrowse);

		cmbOffset = new JComboBox<String>();

		cmbOffset.setPreferredSize(new Dimension(175, 22));

		cmbOffset.setBounds(153, 150, 175, 22);

		flashOptionPanel.add(cmbOffset);

		cmbFlshDevice = new JComboBox<String>();

		cmbFlshDevice.setPreferredSize(new Dimension(175, 22));

		cmbFlshDevice.setBounds(153, 107, 175, 22);

		flashOptionPanel.add(cmbFlshDevice);

		cmbSubSystem = new JComboBox<String>();

		loadSubSystems();

		cmbSubSystem.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbSubSystem)) {

					if (arg0.getStateChange() == ItemEvent.SELECTED) {

						loadProcessors();
					}
				}
			}
		});

		cmbSubSystem.setPreferredSize(new Dimension(175, 22));

		cmbSubSystem.setBounds(153, 21, 175, 22);

		flashOptionPanel.add(cmbSubSystem);

		JLabel lblSubSystem = new JLabel("Sub System");

		lblSubSystem.setBounds(15, 21, 114, 22);

		flashOptionPanel.add(lblSubSystem);

		cmbFlshProcessors = new JComboBox<String>();

		cmbFlshProcessors.setPreferredSize(new Dimension(175, 22));

		cmbFlshProcessors.setBounds(153, 64, 175, 22);

		flashOptionPanel.add(cmbFlshProcessors);

		JLabel lblProcessor = new JLabel("Processor");

		lblProcessor.setBounds(15, 64, 114, 22);

		flashOptionPanel.add(lblProcessor);
	}

	public void setProcessor(String ip) {

		boolean isFound = false;

		flashIP = ip;

		String subs = "";

		List<VPXSubSystem> sub = sys.getSubsystem();

		for (Iterator<VPXSubSystem> iterator = sub.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			if (vpxSubSystem.getIpP2020().equals(ip) || vpxSubSystem.getIpDSP1().equals(ip)
					|| vpxSubSystem.getIpDSP2().equals(ip)) {

				subs = vpxSubSystem.getSubSystem();

				isFound = true;

				break;
			}

		}

		if (!isFound) {

			List<Processor> unListed = sys.getUnListed();

			for (Iterator<Processor> iterator = unListed.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				if (ip.equals(processor.getiP_Addresses())) {

					subs = "Unlisted";

					isFound = true;

					break;

				}
			}
		}

		if (isFound) {

			cmbSubSystem.setSelectedItem(subs);

			cmbFlshProcessors.setSelectedItem(ip);
		}
	}

	private void loadFlashDevices() {

		cmbFlshDevice.addItem("NAND");

		cmbFlshDevice.addItem("NOR");
	}

	private void loadFlashLocations() {

		cmbOffset.addItem("Location 0");

		cmbOffset.addItem("Location 1");
	}

	private void enableWizardComponents() {

		cmbSubSystem.setEnabled(isWizard);

		cmbFlshProcessors.setEnabled(isWizard);

	}

	private void loadSubSystems() {

		cmbSubSystem.removeAllItems();

		cmbSubSystem.addItem("Select Subsystem");

		sys = VPXUtilities.getVPXSystem();

		List<VPXSubSystem> sub = sys.getSubsystem();

		for (Iterator<VPXSubSystem> iterator = sub.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			cmbSubSystem.addItem(vpxSubSystem.getSubSystem());

		}

		List<Processor> unListed = sys.getUnListed();

		if (unListed.size() > 0) {

			cmbSubSystem.addItem("Unlisted");
		}

	}

	private void loadProcessors() {

		cmbFlshProcessors.removeAllItems();

		cmbSubSystem.addItem("Select Processor");

		if (cmbSubSystem.getSelectedItem().toString().equals("Unlisted")) {

			List<Processor> unListed = sys.getUnListed();

			for (Iterator<Processor> iterator = unListed.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				if (!processor.getName().contains("P2020")) {

					cmbFlshProcessors.addItem(processor.getiP_Addresses());
				}
			}

		} else {

			List<VPXSubSystem> sub = sys.getSubsystem();

			for (Iterator<VPXSubSystem> iterator = sub.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getSubSystem().equals(cmbSubSystem.getSelectedItem().toString())) {

					// cmbFlshProcessors.addItem(vpxSubSystem.getIpP2020());

					cmbFlshProcessors.addItem(vpxSubSystem.getIpDSP1());

					cmbFlshProcessors.addItem(vpxSubSystem.getIpDSP2());

					break;
				}

			}

		}

	}

	public void setFilePath(String path) {

		txtBinFilePath.setText(path);
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

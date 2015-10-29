package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
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

import org.apache.commons.io.FileUtils;

import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

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

	private boolean isWizard = true;

	private VPX_ETHWindow parent;

	private VPXSystem sys;

	private PROCESSOR_LIST currentType;

	private JButton btnFlash;

	private String flashIP = "";

	private JLabel lblOffset;

	private JLabel lblBinFile;

	/**
	 * @wbp.parser.constructor
	 */
	public VPX_EthernetFlashPanel(VPX_ETHWindow parent) {

		this.parent = parent;

		init();

		loadComponents();

		loadSubSystems();

		enableWizardComponents();

		loadFlashDevices(true);

		loadFlashLocations(true);
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

		loadFlashDevices(true);

		loadFlashLocations(true);
	}

	private void init() {

		setLayout(new BorderLayout(0, 0));
	}

	public void interruptFlash() {
		parent.setInterrupt(flashIP);

	}

	private void loadComponents() {

		JPanel flashPanl = new JPanel();

		flashPanl.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Ethernet Flash",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		add(flashPanl, BorderLayout.CENTER);
		flashPanl.setLayout(new BorderLayout(0, 0));

		JPanel notePanel = new JPanel();

		FlowLayout fl_notePanel = (FlowLayout) notePanel.getLayout();

		fl_notePanel.setAlignment(FlowLayout.LEFT);

		notePanel.setBorder(new TitledBorder(null, "Note", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		flashPanl.add(notePanel, BorderLayout.SOUTH);

		JLabel lblNote = new JLabel(ETHNOTE);

		notePanel.add(lblNote);

		JPanel flashOptionPanel = new JPanel();

		flashPanl.add(flashOptionPanel);

		flashOptionPanel.setLayout(new MigLayout("", "[114px][406px,center][91px,center]",
				"[22px,grow][22px,grow][22px,grow][22px,grow][24px,grow][23px,grow]"));

		lblBinFile = new JLabel("Bin File");

		flashOptionPanel.add(lblBinFile, "cell 0 4,growx,aligny center");

		lblOffset = new JLabel("Page");

		flashOptionPanel.add(lblOffset, "cell 0 3,growx,aligny center");

		JLabel lblFlashDevice = new JLabel("Flash Device");

		flashOptionPanel.add(lblFlashDevice, "cell 0 2,growx,aligny center");

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

					if (currentType == PROCESSOR_LIST.PROCESSOR_P2020) {

						Thread t = new Thread(new Runnable() {

							@Override
							public void run() {
								try {

									if (!FileUtils.directoryContains(new File(VPXSessionManager.getWorkspacePath()),
											new File(VPXUtilities
													.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_TFTP)))) {

										FileUtils.forceMkdir(new File(VPXSessionManager.getTFTPPath()));

									}

									FileUtils.copyFileToDirectory(new File(txtBinFilePath.getText().trim()),
											new File(VPXSessionManager.getTFTPPath()));

								} catch (IOException e1) {

									e1.printStackTrace();
								}

								parent.sendTFTPFile(flashIP, txtBinFilePath.getText(), dialog,
										cmbOffset.getSelectedIndex());

							}
						});

						t.start();

					} else {

						parent.sendFile(flashIP, txtBinFilePath.getText(), dialog, cmbFlshDevice.getSelectedIndex(),
								cmbOffset.getSelectedIndex());
					}

				} else {

					JOptionPane.showMessageDialog(parent, "Selected file is not valid", "Validation",
							JOptionPane.ERROR_MESSAGE);

					txtBinFilePath.requestFocus();
				}
			}
		});

		flashOptionPanel.add(btnFlash, "cell 1 5,alignx left,aligny top");

		txtBinFilePath = new JTextField();

		txtBinFilePath.setColumns(10);

		flashOptionPanel.add(txtBinFilePath, "cell 1 4,growx,aligny center");

		JButton btnBinFileBrowse = new JButton(new BrowseAction("Browse", txtBinFilePath));

		flashOptionPanel.add(btnBinFileBrowse, "cell 2 4,growx,aligny center");

		cmbOffset = new JComboBox<String>();

		cmbOffset.setPreferredSize(new Dimension(175, 22));

		flashOptionPanel.add(cmbOffset, "cell 1 3,alignx left,aligny center");

		cmbFlshDevice = new JComboBox<String>();

		cmbFlshDevice.setPreferredSize(new Dimension(175, 22));

		flashOptionPanel.add(cmbFlshDevice, "cell 1 2,alignx left,aligny center");

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

		flashOptionPanel.add(cmbSubSystem, "cell 1 0,alignx left,aligny center");

		JLabel lblSubSystem = new JLabel("Sub System");

		flashOptionPanel.add(lblSubSystem, "cell 0 0,growx,aligny center");

		cmbFlshProcessors = new JComboBox<String>();

		cmbFlshProcessors.setPreferredSize(new Dimension(175, 22));

		cmbFlshProcessors.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbFlshProcessors)) {

					if (arg0.getStateChange() == ItemEvent.SELECTED) {

						setProcessorDetail(cmbFlshProcessors.getSelectedItem().toString());

					}
				}
			}
		});

		flashOptionPanel.add(cmbFlshProcessors, "cell 1 1,alignx left,aligny center");

		JLabel lblProcessor = new JLabel("Processor");

		flashOptionPanel.add(lblProcessor, "cell 0 1,growx,aligny center");
	}

	public void setProcessorDetail(String ip) {

		currentType = sys.getProcessorTypeByIP(ip);

		if (currentType == PROCESSOR_LIST.PROCESSOR_P2020) {

			lblBinFile.setText("File");

			lblOffset.setText("File Type");

			loadFlashDevices(false);

			loadFlashLocations(false);

		} else {

			lblBinFile.setText("Bin File");

			lblOffset.setText("Page");

			loadFlashDevices(true);

			loadFlashLocations(true);

		}

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

					subs = VPXConstants.VPXUNLIST;

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

	private void loadFlashDevices(boolean isDSP) {

		cmbFlshDevice.removeAllItems();

		if (isDSP)
			cmbFlshDevice.addItem("NAND");

		cmbFlshDevice.addItem("NOR");
	}

	private void loadFlashLocations(boolean isDSP) {

		cmbOffset.removeAllItems();

		if (isDSP) {

			cmbOffset.addItem("0");

			cmbOffset.addItem("1");

		} else {

			cmbOffset.addItem("Linux");

			cmbOffset.addItem("File System");

			cmbOffset.addItem("DTB");

			cmbOffset.addItem("UBoot");
		}
	}

	private void enableWizardComponents() {

		cmbSubSystem.setEnabled(isWizard);

		cmbFlshProcessors.setEnabled(isWizard);

	}

	private void loadSubSystems() {

		cmbSubSystem.removeAllItems();

		cmbSubSystem.addItem("Select Subsystem");

		sys = VPXSessionManager.getVPXSystem();

		List<VPXSubSystem> sub = sys.getSubsystem();

		for (Iterator<VPXSubSystem> iterator = sub.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			cmbSubSystem.addItem(vpxSubSystem.getSubSystem());

		}

		List<Processor> unListed = sys.getUnListed();

		if (unListed.size() > 0) {

			cmbSubSystem.addItem(VPXConstants.VPXUNLIST);
		}

	}

	private void loadProcessors() {

		cmbFlshProcessors.removeAllItems();

		cmbFlshProcessors.addItem("Select Processor");

		if (cmbSubSystem.getSelectedItem().toString().equals(VPXConstants.VPXUNLIST)) {

			List<Processor> unListed = sys.getUnListed();

			for (Iterator<Processor> iterator = unListed.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				// if (!processor.getName().contains("P2020")) {

				cmbFlshProcessors.addItem(processor.getiP_Addresses());
				// }
			}

		} else {

			List<VPXSubSystem> sub = sys.getSubsystem();

			for (Iterator<VPXSubSystem> iterator = sub.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getSubSystem().equals(cmbSubSystem.getSelectedItem().toString())) {

					cmbFlshProcessors.addItem(vpxSubSystem.getIpP2020());

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

			// fileDialog.addChoosableFileFilter(filterOut);

			// fileDialog.setAcceptAllFileFilterUsed(false);

			int returnVal = fileDialog.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				java.io.File file = fileDialog.getSelectedFile();

				jtf.setText(file.getPath());
			}
		}
	}
}

package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import com.cti.vpx.model.NWInterface;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;
import com.cti.vpx.view.VPX_UARTWindow;

public class VPX_AppModeWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3306489875227159785L;

	private static String UARTNOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;UART Mode will be switched into minimal functionalities and windows.</left></body></html>";

	private static String ETHNOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ethernet Mode will be switched into full functional windows.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User must program LAN Configuration<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Peridicity limit Min: 3 Secs Max: 60 Secs</left></body></html>";

	private JPanel contentPane, basePane;

	private final ButtonGroup debugModeGroup = new ButtonGroup();

	private JLabel lblNote;

	private JButton btnOpen;

	private JButton btnClose;

	private JPanel centerPanel;

	private JTextField txtSpeed;

	private JComboBox<String> cmbCOMM;

	private JTextField txtLogFileName;

	private int currentMode = -1;

	private List<NWInterface> nwIFaces = null;

	private List<String> cmmPorts = null;

	private JComboBox<String> cmbNWIface;

	private DefaultComboBoxModel<String> nwIFModel = new DefaultComboBoxModel<String>();

	private DefaultComboBoxModel<String> commModel = new DefaultComboBoxModel<String>();

	private JTextField txtIPAddress;

	private JTextField txtSubnet;

	private JTextField txtGateway;

	private JSpinner spinPeriodicity;

	private JButton btnBrowse;

	private Boolean isLogEnabled;

	private JCheckBox chkLog;

	private NWInterface nw;

	private SpinnerNumberModel periodicitySpinnerModel;

	private JTextField txtDiplayName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					VPX_AppModeWindow frame = new VPX_AppModeWindow(VPXUtilities.getEthernetPorts(),
							VPXUtilities.getSerialPorts());
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

	public VPX_AppModeWindow(List<NWInterface> nws, List<String> comms) {

		this.nwIFaces = nws;

		this.cmmPorts = comms;

		init();

		loadComponents();

		setLogProperties();

		centerFrame();

	}

	public VPX_AppModeWindow() {

		init();

		loadComponents();

		setLogProperties();

		centerFrame();
	}

	private void init() {

		setTitle("Debug Wizard");

		setIconImage(VPXUtilities.getAppIcon());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);

		basePane = new JPanel();

		basePane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(basePane);

		basePane.setLayout(new BorderLayout(0, 0));

		contentPane = new JPanel();

		contentPane.setBorder(
				new TitledBorder(null, "Debugging Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		basePane.add(contentPane, BorderLayout.CENTER);

		contentPane.setLayout(new BorderLayout(0, 0));

	}

	private void loadComponents() {

		loadLogComponents();

		loadTopComponents();

		loadCenterComponents();

		loadBottomComponents();

		reloadCenterComponents(VPXConstants.UARTMODE);

	}

	private void loadLogComponents() {

		JPanel logPanel = new JPanel();

		logPanel.setBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		logPanel.setPreferredSize(new Dimension(10, 135));

		contentPane.add(logPanel, BorderLayout.NORTH);

		logPanel.setLayout(null);

		chkLog = new JCheckBox("Enable Log");

		chkLog.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				boolean val = (e.getStateChange() == ItemEvent.SELECTED);

				txtLogFileName.setEnabled(val);

				btnBrowse.setEnabled(val);
			}
		});

		chkLog.setBounds(16, 20, 157, 14);

		logPanel.add(chkLog);

		JLabel lblLog = new JLabel("Select Log file name");

		lblLog.setBounds(16, 40, 157, 14);

		logPanel.add(lblLog);

		txtLogFileName = new JTextField();

		txtLogFileName.setBounds(16, 65, 425, 20);

		logPanel.add(txtLogFileName);

		txtLogFileName.setColumns(10);

		btnBrowse = new JButton("Browse");

		btnBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jb = new JFileChooser();

				int i = jb.showOpenDialog(VPX_AppModeWindow.this);

				if (i == JFileChooser.APPROVE_OPTION) {

					String flnmae = jb.getSelectedFile().getPath();

					if (!flnmae.endsWith(".log")) {

						if (Boolean.valueOf(
								VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.LOG_APPENDCURTIME))) {

							flnmae = flnmae + "_" + VPXUtilities.getCurrentTime(3) + ".log";
						} else {
							flnmae += ".log";
						}

					} else {
						if (Boolean.valueOf(
								VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.LOG_APPENDCURTIME))) {

							flnmae = flnmae.substring(0, flnmae.length() - 4);

							flnmae = flnmae + "_" + VPXUtilities.getCurrentTime(3) + ".log";
						}

					}

					txtLogFileName.setText(flnmae);

					VPXUtilities.setEnableLog(true);

					VPXUtilities.updateProperties(VPXConstants.ResourceFields.LOG_FILEPATH, txtLogFileName.getText());
				}
			}
		});

		btnBrowse.setBounds(350, 100, 91, 23);

		logPanel.add(btnBrowse);

		basePane.add(logPanel, BorderLayout.NORTH);

	}

	private void loadBottomComponents() {

		JPanel bottomPanel = new JPanel();

		bottomPanel.setPreferredSize(new Dimension(10, 105));

		basePane.add(bottomPanel, BorderLayout.SOUTH);

		bottomPanel.setLayout(null);

		lblNote = new JLabel(UARTNOTE);

		lblNote.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblNote.setBounds(10, 0, 402, 65);

		bottomPanel.add(lblNote);

		btnOpen = new JButton("Open");

		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				openDebugWindow();
			}
		});

		btnOpen.setBounds(110, 75, 91, 23);

		bottomPanel.add(btnOpen);

		btnClose = new JButton("Close");

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VPX_AppModeWindow.this.dispose();

			}
		});
		btnClose.setBounds(211, 75, 91, 23);

		bottomPanel.add(btnClose);

	}

	private void loadTopComponents() {

		JPanel topPanel = new JPanel();

		// topPanel.setBorder(new TitledBorder(null, "Debugging Mode",
		// TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPane.add(topPanel, BorderLayout.NORTH);

		topPanel.setPreferredSize(new Dimension(10, 60));

		topPanel.setLayout(null);

		JLabel lblDbugMode = new JLabel("Select Debug Mode");

		lblDbugMode.setBounds(10, 24, 115, 23);

		topPanel.add(lblDbugMode);

		JRadioButton radUART = new JRadioButton("UART");

		radUART.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (currentMode != VPXConstants.UARTMODE)

					reloadCenterComponents(VPXConstants.UARTMODE);

			}
		});

		radUART.setSelected(true);

		debugModeGroup.add(radUART);

		radUART.setBounds(148, 24, 73, 23);

		topPanel.add(radUART);

		JRadioButton radETH = new JRadioButton("Ethernet");

		radETH.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (currentMode != VPXConstants.ETHMODE)

					reloadCenterComponents(VPXConstants.ETHMODE);

			}
		});

		// radETH.setSelected(true);

		debugModeGroup.add(radETH);

		radETH.setBounds(242, 24, 108, 23);

		topPanel.add(radETH);
	}

	private void reloadCenterComponents(int mode) {

		currentMode = mode;

		if (mode == VPXConstants.UARTMODE) {

			centerPanel.removeAll();

			setSize(470, 450);

			centerPanel.setBounds(5, 80, 412, 100);

			centerPanel.add(getUARTComponents(), BorderLayout.CENTER);

			lblNote.setText(UARTNOTE);

			loadUARTProperties();

			if (cmbCOMM.getItemCount() > 0) {

				cmbCOMM.setSelectedIndex(0);

				populateUARTValues(cmbCOMM.getSelectedItem().toString());
			}

		} else if (mode == VPXConstants.ETHMODE) {

			centerPanel.removeAll();

			setSize(470, 600);

			centerPanel.setBounds(5, 80, 412, 220);

			centerPanel.add(getEthernetComponents(), BorderLayout.CENTER);

			lblNote.setText(ETHNOTE);

			loadETHProperties();

			cmbNWIface.setSelectedIndex(0);
		}

		periodicitySpinnerModel.setValue(VPXSessionManager.getCurrentPeriodicity());
	}

	private void loadCenterComponents() {

		centerPanel = new JPanel();

		centerPanel.setLayout(new BorderLayout());

		// centerPanel.setBorder(new TitledBorder(null, "Options",
		// TitledBorder.LEADING, TitledBorder.TOP, null, null));

		centerPanel.add(getEthernetComponents(), BorderLayout.CENTER);

		contentPane.add(centerPanel);

		centerPanel.setLocation(5, 80);
	}

	private JPanel getUARTComponents() {

		JPanel uartPanel = new JPanel();

		uartPanel.setLayout(null);

		JLabel lblSelectCommPort = new JLabel("Comm Ports");

		lblSelectCommPort.setBounds(10, 14, 115, 22);

		uartPanel.add(lblSelectCommPort);

		JLabel lblSpeed = new JLabel("Speed");

		lblSpeed.setBounds(10, 50, 115, 22);

		uartPanel.add(lblSpeed);

		cmbCOMM = new JComboBox<String>(commModel);

		cmbCOMM.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					// System.out.println("Item Changed");
				}

			}
		});

		cmbCOMM.setBounds(148, 14, 260, 22);

		uartPanel.add(cmbCOMM);

		txtSpeed = new JTextField();

		txtSpeed.setEditable(false);

		txtSpeed.setBounds(148, 50, 155, 22);

		uartPanel.add(txtSpeed);

		txtSpeed.setColumns(10);

		return uartPanel;
	}

	private JPanel getEthernetComponents() {

		JPanel ethPanel = new JPanel();

		ethPanel.setLayout(null);

		JLabel lblNwInterface = new JLabel("Network Interface");

		lblNwInterface.setBounds(10, 15, 115, 22);

		ethPanel.add(lblNwInterface);

		JLabel lblDispName = new JLabel("Name");

		lblDispName.setBounds(10, 52, 115, 22);

		ethPanel.add(lblDispName);

		JLabel lblIpAddress = new JLabel("IP Address");

		lblIpAddress.setBounds(10, 89, 115, 22);

		ethPanel.add(lblIpAddress);

		JLabel lblSubnet = new JLabel("Subnet");

		lblSubnet.setBounds(10, 126, 115, 22);

		ethPanel.add(lblSubnet);

		JLabel lblGateway = new JLabel("Gateway");

		lblGateway.setBounds(10, 163, 115, 22);

		ethPanel.add(lblGateway);

		JLabel lblPeriodicity = new JLabel("Periodicity");

		lblPeriodicity.setBounds(10, 200, 115, 22);

		ethPanel.add(lblPeriodicity);

		cmbNWIface = new JComboBox<String>(nwIFModel);

		cmbNWIface.setFocusable(false);

		cmbNWIface.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbNWIface)) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						populateETHValues(arg0.getItem().toString());
					}
				}
			}
		});

		cmbNWIface.setBounds(148, 15, 260, 22);

		ethPanel.add(cmbNWIface);

		txtDiplayName = new JTextField("");

		txtDiplayName.setEditable(false);

		txtDiplayName.setBounds(148, 52, 260, 22);

		ethPanel.add(txtDiplayName);

		txtIPAddress = new JTextField();

		txtIPAddress.setBounds(148, 89, 260, 22);

		ethPanel.add(txtIPAddress);

		txtIPAddress.setColumns(10);

		txtSubnet = new JTextField();

		txtSubnet.setColumns(10);

		txtSubnet.setBounds(148, 126, 260, 22);

		ethPanel.add(txtSubnet);

		txtGateway = new JTextField();

		txtGateway.setColumns(10);

		txtGateway.setBounds(148, 163, 260, 22);

		ethPanel.add(txtGateway);

		periodicitySpinnerModel = new SpinnerNumberModel(3, 3, 60, 1);

		spinPeriodicity = new JSpinner(periodicitySpinnerModel);

		JFormattedTextField txt = ((JSpinner.NumberEditor) spinPeriodicity.getEditor()).getTextField();

		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

		spinPeriodicity.setBounds(148, 200, 77, 22);

		ethPanel.add(spinPeriodicity);

		JLabel lblInMins = new JLabel("in Secs (Min: 3, Max: 60)");

		lblInMins.setBounds(234, 200, 150, 22);

		ethPanel.add(lblInMins);

		return ethPanel;

	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	private void loadUARTProperties() {

		commModel.removeAllElements();

		if (cmmPorts.size() > 0) {

			for (Iterator<String> iterator = cmmPorts.iterator(); iterator.hasNext();) {

				commModel.addElement(iterator.next());
			}

			cmbCOMM.setSelectedIndex(0);
		}

		txtSpeed.setText("115200");

	}

	private void loadETHProperties() {

		nwIFModel.removeAllElements();

		nwIFModel.addElement("Select Network");

		if (nwIFaces.size() > 0) {

			for (Iterator<NWInterface> iterator = nwIFaces.iterator(); iterator.hasNext();) {

				NWInterface nw = iterator.next();

				nwIFModel.addElement(nw.getName());

			}

			cmbNWIface.setSelectedIndex(0);
		}
	}

	private void populateETHValues(String name) {

		for (Iterator<NWInterface> iterator = nwIFaces.iterator(); iterator.hasNext();) {

			if (iterator.next().getName().equals(name)) {

				nw = VPXUtilities.getEthernetPort(name);

				if (nw.getIpAddresses().size() > 0) {
					txtIPAddress.setText(nw.getIpAddresses().get(0));
				} else
					txtIPAddress.setText("");

				txtSubnet.setText(nw.getSubnet());

				txtGateway.setText(nw.getGateWay());

				txtDiplayName.setText(nw.getDisplayName());

				if (nw.isConnected() && nw.isEnabled()) {

					setEnabledETHs(true);

				} else {

					setEnabledETHs(false);

					if (!nw.isEnabled()) {

						String msg = name + " is disabled.Please enable and continue!";

						JOptionPane.showMessageDialog(VPX_AppModeWindow.this, msg);

					} else {

						if (!nw.isConnected()) {
							String msg = name + " is not connected.Please connect and continue!";

							JOptionPane.showMessageDialog(VPX_AppModeWindow.this, msg);
						}
					}
				}
				break;
			}

		}
	}

	private void populateUARTValues(String name) {

		setEnabledUARTs(true);
	}

	private void setEnabledETHs(boolean val) {

		txtIPAddress.setEnabled(val);

		txtSubnet.setEnabled(val);

		txtGateway.setEnabled(val);

		txtDiplayName.setEnabled(val);

		spinPeriodicity.setEnabled(val);

		setLogProperties();

		btnOpen.setEnabled(val);
	}

	private void setEnabledUARTs(boolean val) {

		setLogProperties();

		btnOpen.setEnabled(val);
	}

	private void setLogProperties() {

		isLogEnabled = Boolean.valueOf(VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.LOG_ENABLE));

		chkLog.setSelected(isLogEnabled);

		if (isLogEnabled) {

			txtLogFileName.setText(VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.LOG_FILEPATH));

		} else {

			txtLogFileName.setEnabled(false);

			btnBrowse.setEnabled(false);
		}

	}

	private boolean updateLogSettings() {

		boolean ret = false;

		if (chkLog.isSelected()) {

			if (txtLogFileName.getText().length() > 0) { // validation for valid
															// filepath
				VPXUtilities.setEnableLog(true);

				VPXUtilities.updateProperties(VPXConstants.ResourceFields.LOG_FILEPATH, txtLogFileName.getText());
				ret = true;
			}
		} else {
			ret = true;
		}

		return ret;
	}

	private boolean isValueChanged() {

		boolean retIP = false, retSub = false, retGW = false;

		retIP = (nw.getIpAddresses().get(0).equals(txtIPAddress.getText().trim())) ? true : false;

		retSub = (nw.getSubnet().equals(txtSubnet.getText().trim())) ? true : false;

		retGW = (nw.getGateWay().equals(txtGateway.getText().trim())) ? true : false;

		if (retIP && retSub && retGW)
			return false;
		else
			return true;
	}

	private void openDebugWindow() {

		if (updateLogSettings()) {

			if (currentMode == VPXConstants.ETHMODE) {

				boolean ip = VPXUtilities.isValidIP(txtIPAddress.getText());

				boolean sub = VPXUtilities.isValidIP(txtSubnet.getText());

				boolean gateway = VPXUtilities.isValidIP(txtGateway.getText());

				if (ip && sub && gateway) {

					if (isValueChanged()) {

						VPXUtilities.setEthernetPort(cmbNWIface.getSelectedItem().toString(), txtIPAddress.getText(),
								txtSubnet.getText(), txtGateway.getText());

					}

					VPXSessionManager
							.setCurrentPeriodicity(Integer.valueOf(spinPeriodicity.getValue().toString().trim()));

					VPXSessionManager.setCurrentIP(txtIPAddress.getText());

					try {

						VPX_ETHWindow window = new VPX_ETHWindow();

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Another instance is running or the ports are bind by another application.",
								"Opening Application", JOptionPane.ERROR_MESSAGE);

						e.printStackTrace();
						System.exit(0);
					}

				} else {

					if (!ip) {

						JOptionPane.showMessageDialog(VPX_AppModeWindow.this, "Given IP is not a valid.Please check",
								"Error IP", JOptionPane.ERROR_MESSAGE);

						txtIPAddress.requestFocus();

					}
					if (!sub) {

						JOptionPane.showMessageDialog(VPX_AppModeWindow.this,
								"Given Subnet Mask is not a valid.Please check", "Error Log",
								JOptionPane.ERROR_MESSAGE);

						txtSubnet.requestFocus();
					}
					if (!gateway) {

						JOptionPane.showMessageDialog(VPX_AppModeWindow.this,
								"Given Gateway is not a valid.Please check", "Error Log", JOptionPane.ERROR_MESSAGE);

						txtGateway.requestFocus();
					}
				}

			} else {

				if (cmbCOMM.getSelectedIndex() > 0) {

					updateLogSettings();

					VPX_UARTWindow window = new VPX_UARTWindow(cmbCOMM.getSelectedItem().toString());

					window.setVisible(true);
				}

			}

			VPX_AppModeWindow.this.dispose();
		} else {
			JOptionPane.showMessageDialog(VPX_AppModeWindow.this, "Please enter the valid log filename", "Error Log",
					JOptionPane.ERROR_MESSAGE);

			txtLogFileName.requestFocusInWindow();
		}

	}

	public void showWindow() {

		this.setVisible(true);
	}
}

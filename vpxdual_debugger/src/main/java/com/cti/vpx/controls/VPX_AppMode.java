package com.cti.vpx.controls;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class VPX_AppMode extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3306489875227159785L;

	private static int UARTMODE = 0;

	private static int ETHMODE = 1;

	private static String UARTNOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;UART Mode will be switched into minimal functionalities and windows.</left></body></html>";

	private static String ETHNOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ethernet Mode will be switched into full functional windows.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User must program LAN Configuration</left></body></html>";

	private JPanel contentPane;

	private final ButtonGroup debugModeGroup = new ButtonGroup();

	private JLabel lblNote;

	private JButton btnOpen;

	private JButton btnClose;

	private JPanel centerPanel;

	private JPanel uart_Panel, eth_Panel;

	private JTextField txtSpeed;

	private JComboBox cmbCOMM;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					VPX_AppMode frame = new VPX_AppMode();
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
	public VPX_AppMode() {
		init();

		loadComponents();

		centerFrame();
	}

	private void init() {

		setTitle("Connection Mode");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(430, 420);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));

		uart_Panel = getUARTComponents();

		eth_Panel = getEthernetComponents();

	}

	private void loadComponents() {

		loadTopComponents();

		loadCenterComponents();

		loadBottomComponents();

		reloadCenterComponents(UARTMODE);
	}

	private void loadBottomComponents() {

		JPanel bottomPanel = new JPanel();

		bottomPanel.setPreferredSize(new Dimension(10, 105));

		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		bottomPanel.setLayout(null);

		lblNote = new JLabel(UARTNOTE);

		lblNote.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblNote.setBounds(0, 0, 402, 65);

		bottomPanel.add(lblNote);

		btnOpen = new JButton("Open");

		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("App Opened");

			}
		});

		btnOpen.setBounds(110, 75, 91, 23);

		bottomPanel.add(btnOpen);

		btnClose = new JButton("Close");

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VPX_AppMode.this.dispose();

			}
		});
		btnClose.setBounds(211, 75, 91, 23);

		bottomPanel.add(btnClose);

	}

	private void loadTopComponents() {

		JPanel topPanel = new JPanel();

		topPanel.setBorder(new TitledBorder(null, "Debugging Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));

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
				reloadCenterComponents(UARTMODE);

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
				reloadCenterComponents(ETHMODE);

			}
		});

		debugModeGroup.add(radETH);

		radETH.setBounds(242, 24, 108, 23);

		topPanel.add(radETH);
	}

	private void reloadCenterComponents(int mode) {
		if (mode == UARTMODE) {

			centerPanel.removeAll();

			setSize(430, 303);

			centerPanel.setBounds(5, 80, 412, 86);

			centerPanel.add(uart_Panel, BorderLayout.CENTER);

			lblNote.setText(UARTNOTE);

			loadUARTProperties();

		} else if (mode == ETHMODE) {
			centerPanel.removeAll();

			setSize(430, 420);

			centerPanel.setBounds(5, 80, 412, 215);

			centerPanel.add(eth_Panel, BorderLayout.CENTER);

			lblNote.setText(ETHNOTE);
		}
	}

	private void loadCenterComponents() {

		centerPanel = new JPanel();

		centerPanel.setLayout(new BorderLayout());

		centerPanel.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));

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

		cmbCOMM = new JComboBox();

		cmbCOMM.setBounds(148, 14, 248, 22);

		uartPanel.add(cmbCOMM);

		txtSpeed = new JTextField();

		txtSpeed.setEditable(false);

		txtSpeed.setBounds(148, 50, 165, 22);

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

		JLabel lblIpAddress = new JLabel("IP Address");

		lblIpAddress.setBounds(10, 52, 115, 22);

		ethPanel.add(lblIpAddress);

		JLabel lblSubnet = new JLabel("Subnet");

		lblSubnet.setBounds(10, 89, 115, 22);

		ethPanel.add(lblSubnet);

		JLabel lblGateway = new JLabel("Gateway");

		lblGateway.setBounds(10, 126, 115, 22);

		ethPanel.add(lblGateway);

		JLabel lblPeriodicity = new JLabel("Periodicity");

		lblPeriodicity.setBounds(10, 163, 115, 22);

		ethPanel.add(lblPeriodicity);

		JComboBox cmbNWIface = new JComboBox();

		cmbNWIface.setBounds(148, 15, 248, 22);

		ethPanel.add(cmbNWIface);

		JTextField txtIPAddress = new JTextField();

		txtIPAddress.setBounds(148, 52, 248, 22);

		ethPanel.add(txtIPAddress);

		txtIPAddress.setColumns(10);

		JTextField txtSubnet = new JTextField();

		txtSubnet.setColumns(10);

		txtSubnet.setBounds(148, 89, 248, 22);

		ethPanel.add(txtSubnet);

		JTextField txtGateway = new JTextField();

		txtGateway.setColumns(10);

		txtGateway.setBounds(148, 126, 248, 22);

		ethPanel.add(txtGateway);

		JTextField txtPeriodicity = new JTextField();

		txtPeriodicity.setColumns(10);

		txtPeriodicity.setBounds(148, 163, 77, 22);

		ethPanel.add(txtPeriodicity);

		JLabel lblInMins = new JLabel("in Mins");

		lblInMins.setBounds(234, 163, 64, 22);

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
	
		getAvailableSerialPorts();
		txtSpeed.setText("115200");
	}

	private void loadETHProperties() {

	}
	
	  public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
	        HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
	        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
	        while (thePorts.hasMoreElements()) {
	            CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
	            switch (com.getPortType()) {
	            case CommPortIdentifier.PORT_SERIAL:
	                try {
	                    CommPort thePort = com.open("CommUtil", 50);
	                    thePort.close();
	                    h.add(com);
	                } catch (PortInUseException e) {
	                    System.out.println("Port, "  + com.getName() + ", is in use.");
	                } catch (Exception e) {
	                    System.err.println("Failed to open port " +  com.getName());
	                    e.printStackTrace();
	                }
	            }
	        }
	        return h;
	    }
}

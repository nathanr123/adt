package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.cti.vpx.util.VPXUtilities;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;

public class VPX_UARTWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5022638845933883226L;

	private ResourceBundle rBundle;

	private JPanel contentPane;
	private JTextField txtConsoleMsg;
	private JTextArea txtAConsole;
	private JTextArea txtAMessage;
	private JComboBox<String> cmbConsoleCoresFilter;
	private JComboBox<String> cmbMessagesCoresFilter;
	Map<Integer, String> cmdHistory = new LinkedHashMap<Integer, String>();
	private int currentCommandIndex = -1;

	private String currCommport = "";

	private OutputStream out;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPX_UARTWindow frame = new VPX_UARTWindow("COM3");
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
	public VPX_UARTWindow(String commport) {
		setResizable(false);

		this.currCommport = commport;

		rBundle = VPXUtilities.getResourceBundle();

		init();

		loadComponents();

		loadCores();

		centerFrame();

		setVisible(true);

		try {
			connect(commport);
		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, "Error in connecting..", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void init() {

		setTitle(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version"));

		setPreferredSize(new Dimension(800, 600));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(782, 700);
	}

	private void loadCores() {

		cmbConsoleCoresFilter.removeAll();

		cmbMessagesCoresFilter.removeAll();

		cmbConsoleCoresFilter.addItem("All Cores");

		for (int i = 0; i < 8; i++) {

			cmbConsoleCoresFilter.addItem(String.format("Core - %d", i));

			cmbMessagesCoresFilter.addItem(String.format("Core - %d", i));

		}

		cmbConsoleCoresFilter.setSelectedIndex(0);

		cmbMessagesCoresFilter.setSelectedIndex(0);
	}

	private void loadComponents() {

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel consolePanel = new JPanel();

		consolePanel.setBorder(new TitledBorder(null, "Console", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPane.add(consolePanel);

		consolePanel.setLayout(new BorderLayout(0, 0));

		JPanel consoleFilterPanel = new JPanel();

		FlowLayout fl_consoleFilterPanel = (FlowLayout) consoleFilterPanel.getLayout();

		fl_consoleFilterPanel.setAlignment(FlowLayout.RIGHT);

		consolePanel.add(consoleFilterPanel, BorderLayout.NORTH);

		cmbConsoleCoresFilter = new JComboBox<String>();

		cmbConsoleCoresFilter.setPreferredSize(new Dimension(130, 20));

		consoleFilterPanel.add(cmbConsoleCoresFilter);

		JScrollPane scrConsole = new JScrollPane();

		consolePanel.add(scrConsole, BorderLayout.CENTER);

		txtAConsole = new JTextArea();
		txtAConsole.setEditable(false);
		txtAConsole.setBackground(Color.BLACK);
		txtAConsole.setForeground(Color.WHITE);

		scrConsole.setViewportView(txtAConsole);

		JPanel messagesControlPanel = new JPanel();

		contentPane.add(messagesControlPanel);

		messagesControlPanel.setLayout(new BorderLayout(0, 0));

		JPanel ControlsPanel = new JPanel();

		messagesControlPanel.add(ControlsPanel, BorderLayout.SOUTH);

		ControlsPanel.setLayout(new BorderLayout(0, 0));

		JPanel sendOptionPanel = new JPanel();

		ControlsPanel.add(sendOptionPanel, BorderLayout.CENTER);

		sendOptionPanel.setLayout(new MigLayout("", "[93.00,left][grow,fill]", "[]"));

		cmbMessagesCoresFilter = new JComboBox<String>();

		cmbMessagesCoresFilter.setPreferredSize(new Dimension(130, 20));

		sendOptionPanel.add(cmbMessagesCoresFilter, "flowx,cell 0 0");

		txtConsoleMsg = new JTextField();

		sendOptionPanel.add(txtConsoleMsg, "cell 1 0");

		txtConsoleMsg.setColumns(10);

		txtConsoleMsg.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				if (arg0.getKeyChar() == KeyEvent.VK_ENTER) {

					sendData();

				}
				if (arg0.getKeyCode() == KeyEvent.VK_UP) {

					navigateCommand(arg0);

				} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {

					navigateCommand(arg0);
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		JPanel buttonPanel = new JPanel();

		FlowLayout fl_buttonPanel = (FlowLayout) buttonPanel.getLayout();

		fl_buttonPanel.setAlignment(FlowLayout.LEFT);

		ControlsPanel.add(buttonPanel, BorderLayout.SOUTH);

		JButton btnSend = new JButton("Send");

		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendData();

			}
		});

		btnSend.setHorizontalAlignment(SwingConstants.LEFT);

		buttonPanel.add(btnSend);

		JButton btnClear = new JButton("Clear");

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				txtConsoleMsg.setText("");

				txtAConsole.setText("");

				txtAMessage.setText("");

			}
		});

		buttonPanel.add(btnClear);

		JPanel messagePanel = new JPanel();

		messagePanel.setBorder(new TitledBorder(null, "Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		messagesControlPanel.add(messagePanel, BorderLayout.CENTER);

		messagePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrMessages = new JScrollPane();

		messagePanel.add(scrMessages, BorderLayout.CENTER);

		txtAMessage = new JTextArea();
		txtAMessage.setEditable(false);
		txtAMessage.setBackground(Color.BLACK);
		txtAMessage.setForeground(Color.WHITE);

		scrMessages.setViewportView(txtAMessage);
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	private void navigateCommand(KeyEvent keyEvent) {

		if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {

			if (currentCommandIndex > 0) {

				currentCommandIndex = currentCommandIndex - 1;

				txtConsoleMsg.setText(cmdHistory.get(currentCommandIndex));
			}

		} else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {

			if (currentCommandIndex < cmdHistory.size()) {

				currentCommandIndex = currentCommandIndex + 1;

				txtConsoleMsg.setText(cmdHistory.get(currentCommandIndex));
			}

		}

	}

	public void connect(String portName) throws Exception {

		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

		if (portIdentifier.isCurrentlyOwned()) {

			JOptionPane.showMessageDialog(this, "Port is currently in use", "Error", JOptionPane.ERROR_MESSAGE);

		} else {

			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {

				SerialPort serialPort = (SerialPort) commPort;

				serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();

				out = serialPort.getOutputStream();

				serialPort.addEventListener(new SerialReader(in));

				serialPort.notifyOnDataAvailable(true);

			}
		}
	}

	public void sendData() {

		try {

			String data = txtConsoleMsg.getText().trim();

			if (data.length() > 0) {

				txtAMessage.append(String.format(" %s@%s\\> %s", this.currCommport,
						cmbMessagesCoresFilter.getSelectedItem().toString(), data) + "\n");

				cmdHistory.put(cmdHistory.size(), txtConsoleMsg.getText().trim());

				currentCommandIndex = cmdHistory.size();

				out.write(String.format("%d::%s", cmbMessagesCoresFilter.getSelectedIndex(), data).getBytes());

				txtConsoleMsg.setText("");
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public class SerialReader implements SerialPortEventListener {

		private InputStream in;

		private byte[] buffer = new byte[1024];

		public SerialReader(InputStream in) {

			this.in = in;
		}

		public void serialEvent(SerialPortEvent arg0) {

			int data;

			try {

				int len = 0;

				while ((data = in.read()) > -1) {

					if (data == '\n') {
						break;
					}

					buffer[len++] = (byte) data;
				}

				String msg1 = new String(buffer, 0, len);

				if (msg1.length() > 0) {
					
					String msg = msg1.split("::")[1];

					if (cmbConsoleCoresFilter.getSelectedIndex() > 0) {

						if (msg1.startsWith(String.valueOf(cmbConsoleCoresFilter.getSelectedIndex() - 1))) {

							txtAConsole.append(msg + "\n");
						}

					} else {

						txtAConsole.append(msg + "\n");
					}
				}

			} catch (IOException e) {

				txtAConsole.append("\n");

			}
		}

	}

	public class SerialWriter implements Runnable {
		OutputStream out;

		public SerialWriter(OutputStream out) {

			this.out = out;
		}

		public void run() {
			int i = 0;
			try {

				while (i <= 10000) {

					this.out.write(String.format("UART %d\n", i).getBytes());

					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

}

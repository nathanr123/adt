package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.cti.vpx.controls.VPX_AboutWindow;
import com.cti.vpx.controls.VPX_AppModeWindow;
import com.cti.vpx.controls.VPX_VLANConfig;
import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import net.miginfocom.swing.MigLayout;

public class VPX_UARTWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5022638845933883226L;

	private static final VPX_AboutWindow aboutDialog = new VPX_AboutWindow();

	private ResourceBundle rBundle;

	private JPanel contentPane;

	private JTextField txtConsoleMsg;

	private JTextArea txtAConsole;

	private JComboBox<String> cmbConsoleCoresFilter;

	private JComboBox<String> cmbMessagesCoresFilter;

	private Map<Integer, String> cmdHistory = new LinkedHashMap<Integer, String>();

	private int currentCommandIndex = -1;

	private String currCommport = "";

	private OutputStream out;

	private JButton btn_Msg_Clear;

	private JButton btn_Msg_Save;

	private JMenuBar vpx_UART_MenuBar;

	private JMenu vpx_UART_Menu_File;

	private JMenu vpx_UART_Menu_Window;

	private JMenu vpx_UART_Menu_Help;

	private JMenuItem vpx_UART_Menu_File_OpenCommPort;

	private JMenuItem vpx_UART_Menu_File_Save;

	private JMenuItem vpx_UART_Menu_File_Exit;

	private JMenu vpx_UART_Menu_Window_ChangeMode;

	private JMenuItem vpx_UART_Menu_Window_ChangeMode_ETH;

	private JMenuItem vpx_UART_Menu_Help_About;

	private JMenuItem vpx_UART_Menu_Help_Help;

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
					VPXLogger.updateError(e);
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

		createMenuBar();

		loadCores();

		centerFrame();

		setVisible(true);

		try {
			connect(commport);
		} catch (Exception e) {
			VPXLogger.updateError(e);
			JOptionPane.showMessageDialog(this, "Error in connecting..", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void init() {

		setTitle(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version") + " :: "
				+ currCommport);

		setIconImage(VPXUtilities.getAppIcon());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(1000, 700);
	}

	private void createMenuBar() {

		vpx_UART_MenuBar = VPXComponentFactory.createJMenuBar();

		vpx_UART_Menu_File = VPXComponentFactory.createJMenu("File");

		vpx_UART_Menu_Window = VPXComponentFactory.createJMenu("Window");

		vpx_UART_Menu_Help = VPXComponentFactory.createJMenu("Help");

		vpx_UART_Menu_File_OpenCommPort = VPXComponentFactory.createJMenuItem("Open Comm Port");

		vpx_UART_Menu_File_Save = VPXComponentFactory.createJMenuItem("Save");

		vpx_UART_Menu_File_Exit = VPXComponentFactory.createJMenuItem("Exit");

		vpx_UART_Menu_Window_ChangeMode = VPXComponentFactory.createJMenu("Change Mode");

		vpx_UART_Menu_Window_ChangeMode_ETH = VPXComponentFactory.createJMenuItem("Ethernet Mode");

		vpx_UART_Menu_Window_ChangeMode.add(vpx_UART_Menu_Window_ChangeMode_ETH);

		vpx_UART_Menu_Help_About = VPXComponentFactory.createJMenuItem("About");

		vpx_UART_Menu_Help_Help = VPXComponentFactory.createJMenuItem("Help");

		// Adding
		vpx_UART_MenuBar.add(vpx_UART_Menu_File);

		vpx_UART_MenuBar.add(vpx_UART_Menu_Window);

		vpx_UART_MenuBar.add(vpx_UART_Menu_Help);

		vpx_UART_Menu_File.add(vpx_UART_Menu_File_OpenCommPort);

		vpx_UART_Menu_File.add(vpx_UART_Menu_File_Save);

		vpx_UART_Menu_File.add(VPXComponentFactory.createJSeparator());

		vpx_UART_Menu_File.add(vpx_UART_Menu_File_Exit);

		vpx_UART_Menu_Window.add(vpx_UART_Menu_Window_ChangeMode);

		vpx_UART_Menu_Help.add(vpx_UART_Menu_Help_About);

		vpx_UART_Menu_Help.add(VPXComponentFactory.createJSeparator());

		vpx_UART_Menu_Help.add(vpx_UART_Menu_Help_Help);

		setJMenuBar(vpx_UART_MenuBar);

		vpx_UART_Menu_File_Exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				exitApplication();

			}
		});

		vpx_UART_Menu_File_Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				btn_Msg_Save.doClick();

			}
		});

		vpx_UART_Menu_Help_About.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showAbout();

			}
		});

		vpx_UART_Menu_Help_Help.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showHelp();

			}
		});

		vpx_UART_Menu_File_OpenCommPort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showComports();

			}
		});

		vpx_UART_Menu_Window_ChangeMode_ETH.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showAppMode();

			}
		});

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

		contentPane.setLayout(new GridLayout(1, 1, 0, 0));

		JPanel consolePanel = new JPanel();

		consolePanel.setBorder(new TitledBorder(null, "Console", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPane.add(consolePanel);

		consolePanel.setLayout(new BorderLayout(0, 0));

		JPanel consoleFilterPanel = new JPanel();

		FlowLayout fl_consoleFilterPanel = (FlowLayout) consoleFilterPanel.getLayout();

		fl_consoleFilterPanel.setAlignment(FlowLayout.RIGHT);

		consolePanel.add(consoleFilterPanel, BorderLayout.NORTH);

		cmbConsoleCoresFilter = new JComboBox<String>();

		cmbConsoleCoresFilter.setVisible(false);

		cmbConsoleCoresFilter.setPreferredSize(new Dimension(130, 20));

		btn_Msg_Clear = VPXComponentFactory.createJButton(new ClearAction("Clear"));

		btn_Msg_Clear.setPreferredSize(new Dimension(22, 22));

		btn_Msg_Clear.setBorderPainted(false);

		btn_Msg_Save = VPXComponentFactory.createJButton(new SaveAction("Save"));

		btn_Msg_Save.setPreferredSize(new Dimension(22, 22));

		btn_Msg_Save.setBorderPainted(false);

		consoleFilterPanel.add(cmbConsoleCoresFilter);

		consoleFilterPanel.add(btn_Msg_Save);

		consoleFilterPanel.add(btn_Msg_Clear);

		JScrollPane scrConsole = new JScrollPane();

		consolePanel.add(scrConsole, BorderLayout.CENTER);

		txtAConsole = new JTextArea();

		txtAConsole.setEditable(false);

		txtAConsole.setBackground(Color.BLACK);

		txtAConsole.setForeground(Color.WHITE);

		scrConsole.setViewportView(txtAConsole);

		JPanel ControlsPanel = new JPanel();
		consolePanel.add(ControlsPanel, BorderLayout.SOUTH);

		ControlsPanel.setLayout(new BorderLayout(0, 0));

		JPanel sendOptionPanel = new JPanel();

		ControlsPanel.add(sendOptionPanel, BorderLayout.CENTER);

		sendOptionPanel.setLayout(new MigLayout("", "[grow,fill]", "[]"));

		txtConsoleMsg = new JTextField();

		sendOptionPanel.add(txtConsoleMsg, "cell 0 0");

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

		cmbMessagesCoresFilter = new JComboBox<String>();
		cmbMessagesCoresFilter.setVisible(false);
		buttonPanel.add(cmbMessagesCoresFilter);

		cmbMessagesCoresFilter.setPreferredSize(new Dimension(130, 20));
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

	public void connect(String portName) {

		try {

			CommPortIdentifier portIdentifier;

			portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

			if (portIdentifier.isCurrentlyOwned()) {

				JOptionPane.showMessageDialog(this, "Port is currently in use", "Error", JOptionPane.ERROR_MESSAGE);

			} else {

				CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

				if (commPort instanceof SerialPort) {

					SerialPort serialPort = (SerialPort) commPort;

					serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);

					InputStream in = serialPort.getInputStream();

					out = serialPort.getOutputStream();

					serialPort.addEventListener(new SerialReader(in));

					serialPort.notifyOnDataAvailable(true);

				}
			}

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}
	}

	public void sendData() {

		try {

			String data = txtConsoleMsg.getText().trim();

			if (data.length() > 0) {

				cmdHistory.put(cmdHistory.size(), txtConsoleMsg.getText().trim());

				currentCommandIndex = cmdHistory.size();

				out.write(data.getBytes());

				out.write("\n".getBytes());

				txtConsoleMsg.setText("");
			}

		} catch (IOException e) {
			VPXLogger.updateError(e);
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

					/*
					 * if (msg1.trim().startsWith("~ #")) {
					 * 
					 * msg1 = "\n"; } if
					 * (msg1.trim().equals(currentCommand.trim())) {
					 * 
					 * msg1 = String.format(" %s\\> %s",
					 * VPX_UARTWindow.this.currCommport, msg1); }
					 */
					// txtAConsole.append(String.format(" %s\\> %s",
					// this.currCommport, data) + "\n");

					txtAConsole.append(msg1 + "\n");

					txtAConsole.setCaretPosition(txtAConsole.getText().length());

					updateLog(msg1);
					/*
					 * String msg = msg1.split("::")[1];
					 * 
					 * if (cmbConsoleCoresFilter.getSelectedIndex() > 0) {
					 * 
					 * if (msg1.startsWith(String.valueOf(cmbConsoleCoresFilter.
					 * getSelectedIndex() - 1))) {
					 * 
					 * txtAConsole.append(msg + "\n"); }
					 * 
					 * } else {
					 * 
					 * txtAConsole.append(msg + "\n"); }
					 */
				}

			} catch (IOException e) {
				VPXLogger.updateError(e);
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
				VPXLogger.updateError(e);
				System.exit(-1);
			}
		}
	}

	class SaveAction extends AbstractAction {

		/**
		 * 
		 */

		public SaveAction(String name) {

			putValue(Action.SHORT_DESCRIPTION, name);

			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_SAVE);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			saveLogtoFile();
		}
	}

	class ClearAction extends AbstractAction {

		/**
		 * 
		 */

		public ClearAction(String name) {

			putValue(Action.SHORT_DESCRIPTION, name);

			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_CLEAR);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			clearContents();
		}
	}

	private void clearContents() {

		txtAConsole.setText("");

		// txtAMessage.setText("");

		cmbConsoleCoresFilter.setSelectedIndex(0);
	}

	private void saveLogtoFile() {

		JFileChooser chooser;

		try {

			chooser = new JFileChooser();

			chooser.setCurrentDirectory(new java.io.File("."));

			chooser.setDialogTitle("Select folder to save");

			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

				String path = chooser.getSelectedFile().getPath() + "\\Messages."
						+ getCurrentTime().split("  ")[0].replace(':', '_').replace(' ', '_').replace('-', '_')
						+ ".txt";

				FileWriter fw = new FileWriter(new File(path), true);

				fw.write(txtAConsole.getText());

				fw.close();

				VPXUtilities.showPopup("File Saved at " + path, path);
			}

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

	}

	private String getCurrentTime() {

		return VPXConstants.DATEFORMAT_FULL.format(Calendar.getInstance().getTime());
	}

	public void exitApplication() {

		String ObjButtons[] = { "Yes", "No" };

		int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?",
				rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version"),
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);

		if (PromptResult == 0) {

			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {

					VPX_UARTWindow.this.dispose();

					System.exit(0);

				}
			});
			th.start();
		}

	}

	public void showHelp() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				final VPX_VLANConfig browserCanvas = new VPX_VLANConfig();

				JPanel contentPane = new JPanel();

				contentPane.setLayout(new BorderLayout());

				contentPane.add(browserCanvas, BorderLayout.CENTER);

				JDialog frame = new JDialog(VPX_UARTWindow.this, "Help");

				frame.setBounds(100, 100, 650, 600);

				frame.setLocationRelativeTo(VPX_UARTWindow.this);

				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				frame.setContentPane(contentPane);

				frame.setResizable(true);

				frame.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) { // Dispose of the
						// native
						// component cleanly
						browserCanvas.dispose();
					}
				});

				frame.setVisible(true);

				if (browserCanvas.initialise()) {

					browserCanvas.setUrl(System.getProperty("user.dir") + "\\help\\help.html");
				} else {
				}

				frame.setSize(651, 601);

			}
		});

		th.start();

	}

	public void showAppMode() {

		this.dispose();

		VPX_AppModeWindow window = new VPX_AppModeWindow(VPXUtilities.getEthernetPorts(),
				VPXUtilities.getSerialPorts());

		window.showWindow();

	}

	public void showAbout() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				aboutDialog.setVisible(true);

			}
		});

		th.start();

	}

	public void showComports() {

		List<String> ls = VPXUtilities.getSerialPorts();

		Object[] ob = new Object[ls.size()];

		Object obd = ls.get(0);

		for (int i = 0; i < ob.length; i++) {

			ob[i] = ls.get(i);
		}

		String s = (String) JOptionPane.showInputDialog(this, "Select Comm ports", "Select Comm ports",
				JOptionPane.PLAIN_MESSAGE, null, ob, obd);

		if ((s != null) && (s.length() > 0) && (!s.equals("Select Comm Port"))) {

			this.currCommport = s;

			setTitle(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version") + " :: "
					+ currCommport);

			connect(s);

			return;
		}

	}

	public void updateLog(String log) {

		updateLog(VPXConstants.INFO, log);
	}

	public void updateLog(int LEVEL, String log) {

		updateLogtoFile(log);

	}

	public void updateLogtoFile(String log) {

		try {

			if (VPXUtilities.isLogEnabled()) {

				String filePath = VPXSessionManager.getCurrentLogFileName();// VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.LOG_FILEPATH);

				VPXUtilities.updateToFile(filePath, log);

			}

		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}
	}
}

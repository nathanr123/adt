package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import com.cti.vpx.controls.VPX_About_Dialog;
import com.cti.vpx.controls.VPX_AliasConfigWindow;
import com.cti.vpx.controls.VPX_ConnectedProcessor;
import com.cti.vpx.controls.VPX_ConsolePanel;
import com.cti.vpx.controls.VPX_LoggerPanel;
import com.cti.vpx.controls.VPX_MAD;
import com.cti.vpx.controls.VPX_MessagePanel;
import com.cti.vpx.controls.VPX_Preference;
import com.cti.vpx.controls.VPX_ProcessorTree;
import com.cti.vpx.controls.VPX_StatusBar;
import com.cti.vpx.controls.hex.VPX_MemoryBrowser;
import com.cti.vpx.controls.tab.VPX_TabbedPane;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPX;
import com.cti.vpx.util.ComponentFactory;
import com.cti.vpx.util.VPXUtilities;

public class VPX_ETHWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2505823813174035752L;

	private ResourceBundle rBundle;

	private JMenuBar vpx_MenuBar;

	private JMenu vpx_Menu_File;

	private JMenu vpx_Menu_Run;

	private JMenu vpx_Menu_Window;

	private JMenu vpx_Menu_Help;

	private JMenuItem vpx_Menu_File_Scan;

	private JMenuItem vpx_Menu_File_Exit;

	private JMenuItem vpx_Menu_Run_Run;

	private JMenuItem vpx_Menu_Run_Pause;

	private JMenuItem vpx_Menu_Help_Help;

	private JMenuItem vpx_Menu_Help_About;

	private JMenuItem vpx_Menu_Window_MemoryBrowser;

	private JMenuItem vpx_Menu_Window_Flash;

	private JMenuItem vpx_Menu_Preference;

	private JToolBar vpx_ToolBar;

	private JSplitPane vpx_SplitPane;

	private JSplitPane vpx_Right_SplitPane;

	private VPX_TabbedPane vpx_Content_Tabbed_Pane_Right;

	private VPX_LoggerPanel logger;

	private VPX_ConsolePanel console;

	private VPX_ProcessorTree vpx_Processor_Tree;

	private JTabbedPane vpx_Content_Tabbed_Pane_Message;

	private VPX_StatusBar statusBar;

	private VPX_About_Dialog aboutDialog = new VPX_About_Dialog();

	private VPX_MessagePanel messagePanel;

	private MsgReceiver msgReciever = new MsgReceiver();

	/**
	 * Create the frame.
	 */
	public VPX_ETHWindow() {

		VPXUtilities.setParent(this);

		rBundle = VPXUtilities.getResourceBundle();

		initializeRootWindow();

		promptAliasConfigFileName();

		startMessageReciever();
	}

	private void initializeRootWindow() {

		setTitle(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setAlwaysOnTop(true);

		setIconImage(VPXUtilities.getAppIcon());

		setBounds(0, 0, VPXUtilities.getScreenWidth(), VPXUtilities.getScreenHeight());

		loadComponents();

		updateLog(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version") + " Started");

		setVisible(true);

		toFront();
	}

	private void loadComponents() {

		loadMenuBar();

		loadToolBar();

		loadContentPane();

		loadStatusPane();
	}

	private void loadMenuBar() {

		vpx_MenuBar = ComponentFactory.createJMenuBar();// new JMenuBar();

		vpx_Menu_File = ComponentFactory.createJMenu(rBundle.getString("Menu.File"));

		vpx_Menu_Run = ComponentFactory.createJMenu(rBundle.getString("Menu.Run"));

		vpx_Menu_Window = ComponentFactory.createJMenu(rBundle.getString("Menu.Window"));

		vpx_Menu_Help = ComponentFactory.createJMenu(rBundle.getString("Menu.Help"));

		vpx_MenuBar.add(vpx_Menu_File);

		vpx_Menu_File_Scan = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Scan"));

		vpx_Menu_File_Exit = ComponentFactory.createJMenuItem(new ExitAction(rBundle.getString("Menu.File.Exit")));

		vpx_Menu_File.add(vpx_Menu_File_Scan);

		vpx_Menu_File.add(ComponentFactory.createJSeparator());

		vpx_Menu_File.add(vpx_Menu_File_Exit);

		vpx_MenuBar.add(vpx_Menu_Run);

		vpx_Menu_Run_Run = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Run.Run"));

		vpx_Menu_Run_Pause = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Run.Pause"));

		vpx_Menu_Run.add(vpx_Menu_Run_Run);

		vpx_Menu_Run.add(ComponentFactory.createJSeparator());

		vpx_Menu_Run.add(vpx_Menu_Run_Pause);

		vpx_MenuBar.add(vpx_Menu_Window);

		vpx_Menu_Window_MemoryBrowser = ComponentFactory.createJMenuItem(new MemoryAction(rBundle
				.getString("Menu.Window.MemoryBrowser")));

		vpx_Menu_Window.add(vpx_Menu_Window_MemoryBrowser);

		vpx_Menu_Window_Flash = ComponentFactory.createJMenuItem(new FlashAction(rBundle
				.getString("Menu.Window.FlashProcessor")));

		vpx_Menu_Window.add(vpx_Menu_Window_Flash);

		vpx_Menu_Window.add(ComponentFactory.createJSeparator());

		vpx_Menu_Preference = ComponentFactory.createJMenuItem(new PreferenceAction(rBundle
				.getString("Menu.Window.Preferences")));

		vpx_Menu_Window.add(vpx_Menu_Preference);

		vpx_MenuBar.add(vpx_Menu_Help);

		vpx_Menu_Help_Help = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.Help"));

		vpx_Menu_Help_About = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.About"));

		vpx_Menu_Help_About.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				aboutDialog.showDialog();
			}
		});

		vpx_Menu_Help.add(vpx_Menu_Help_Help);

		vpx_Menu_Help.add(ComponentFactory.createJSeparator());

		vpx_Menu_Help.add(vpx_Menu_Help_About);

		setJMenuBar(vpx_MenuBar);
	}

	private void loadToolBar() {

		vpx_ToolBar = ComponentFactory.createJToolBar();

		vpx_ToolBar.setFloatable(false);

		vpx_ToolBar
				.add(ComponentFactory.createJButton("", VPXUtilities.getImageIcon("images\\scan.png", 14, 14), null));

		getContentPane().add(vpx_ToolBar, BorderLayout.NORTH);
	}

	private void loadContentPane() {

		vpx_SplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		JTabbedPane tb = new JTabbedPane();

		tb.addTab("Processor Explorer", getSystemTree());

		vpx_SplitPane.setLeftComponent(tb);

		vpx_SplitPane.setRightComponent(getRightComponent());

		vpx_SplitPane.setDividerLocation((int) VPXUtilities.getScreenWidth() / 6);

		vpx_SplitPane.setDividerSize(2);

		getContentPane().add(vpx_SplitPane, BorderLayout.CENTER);
	}

	private void loadStatusPane() {

		statusBar = new VPX_StatusBar();

		statusBar.setOpaque(true);

		getContentPane().add(statusBar, BorderLayout.SOUTH);
	}

	private JSplitPane getRightComponent() {

		vpx_Right_SplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		JPanel contentPanel = ComponentFactory.createJPanel();

		contentPanel.setLayout(new BorderLayout());

		messagePanel = new VPX_MessagePanel(this);

		messagePanel.setPreferredSize(new Dimension(375, 300));

		contentPanel.add(messagePanel, BorderLayout.EAST);

		vpx_Content_Tabbed_Pane_Right = new VPX_TabbedPane(true, true);

		contentPanel.add(vpx_Content_Tabbed_Pane_Right);

		vpx_Right_SplitPane.setLeftComponent(contentPanel);

		vpx_Content_Tabbed_Pane_Message = new JTabbedPane();

		logger = new VPX_LoggerPanel();

		console = new VPX_ConsolePanel();

		vpx_Content_Tabbed_Pane_Message.addTab("Logger", logger);

		vpx_Content_Tabbed_Pane_Message.addTab("Console", console);

		vpx_Right_SplitPane.setRightComponent(vpx_Content_Tabbed_Pane_Message);

		vpx_Right_SplitPane.setDividerLocation(((int) VPXUtilities.getScreenHeight() / 2 + (int) VPXUtilities
				.getScreenHeight() / 7));

		vpx_Right_SplitPane.setDividerSize(2);

		return vpx_Right_SplitPane;
	}

	private JScrollPane getSystemTree() {

		vpx_Processor_Tree = ComponentFactory.createProcessorTree(this);

		JScrollPane vpx_Processor_Tree_ScrollPane = new JScrollPane(vpx_Processor_Tree);

		for (int i = 0; i < vpx_Processor_Tree.getRowCount(); i++) {
			vpx_Processor_Tree.expandRow(i);
		}

		return vpx_Processor_Tree_ScrollPane;

	}

	public void updateProcessorTree() {
		vpx_Processor_Tree.updateVPXSystemTree();
	}

	public void updateStatus(String stats) {
		statusBar.updateStatus(stats);
	}

	public void updateLog(String logMsg) {

		logger.updateLog(logMsg);

		updateStatus(logMsg);
	}

	public void updateLog(int level, String logMsg) {
		logger.updateLog(level, logMsg);

		updateStatus(logMsg);
	}

	private void startMessageReciever() {
		msgReciever.execute();
	}

	private void stopMessageReciever() {
		msgReciever.cancel(true);
	}

	public void addTab(String tabName, JScrollPane comp) {
		vpx_Content_Tabbed_Pane_Right.addTab(tabName, comp);

		vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);
	}

	public void connectProcessor(Processor pro) {
		vpx_Content_Tabbed_Pane_Right.addTab(String.format("%s(%s)", pro.getiP_Addresses(), pro.getName()),
				new VPX_ConnectedProcessor(VPX_ETHWindow.this));

		vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

		pro.connect();
	}

	public class MemoryAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public MemoryAction(String name) {

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			vpx_Content_Tabbed_Pane_Right.addTab("Memory Browser", new VPX_MemoryBrowser());

			vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);
		}
	}

	public class FlashAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public FlashAction(String name) {

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = isAlreadyExist();
			if (isAlreadyExist() == -1) {
				vpx_Content_Tabbed_Pane_Right.addTab("Flash", new JScrollPane(new VPX_MAD()));

				vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);
			} else
				vpx_Content_Tabbed_Pane_Right.setSelectedIndex(i);
		}
	}

	private int isAlreadyExist() {
		int ret = -1;

		for (int i = 0; i < vpx_Content_Tabbed_Pane_Right.getTabCount(); i++) {
			if (vpx_Content_Tabbed_Pane_Right.getTitleAt(i).equals("Flash")) {
				ret = i;
				break;
			}
		}
		return ret;
	}

	public class PreferenceAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public PreferenceAction(String name) {

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new VPX_Preference().showPreferenceWindow();
		}
	}

	public class ExitAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public ExitAction(String name) {

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			VPX_ETHWindow.this.dispose();

		}
	}

	private void promptAliasConfigFileName() {

		if (!vpx_Processor_Tree.isSubSystemsAvailable()) {
			int option = JOptionPane.showConfirmDialog(VPX_ETHWindow.this,
					"Alias config file not found.\nDo you want to config now?", "Confirmation",
					JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
				new VPX_AliasConfigWindow(this).setVisible(true);
			}
		}

	}

	class MsgReceiver extends SwingWorker<Void, String> {

		DatagramSocket serverSocket;

		byte[] receiveData = new byte[1024];

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		public MsgReceiver() {
			try {
				serverSocket = new DatagramSocket(VPX.CONSOLE_MSG_PORTNO);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground() throws Exception {
			while (true) {

				serverSocket.receive(receivePacket);

				String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

				if (sentence.startsWith("M:")) {
					messagePanel.updateProcessorMessage(receivePacket.getAddress().getHostAddress(),
							sentence.substring(2, sentence.length()));
				} else if (sentence.startsWith("C:")) {
					console.printConsoleMsg(receivePacket.getAddress().getHostAddress(),
							sentence.substring(2, sentence.length()));
				}

				Thread.sleep(500);
			}
		}

		@Override
		protected void process(List<String> chunks) {
			// TODO Auto-generated method stub
			super.process(chunks);
		}

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		stopMessageReciever();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}

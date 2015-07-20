package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.cti.vpx.Listener.AdvertisementListener;
import com.cti.vpx.Listener.CommunicationListener;
import com.cti.vpx.Listener.MessageListener;
import com.cti.vpx.command.ATP;
import com.cti.vpx.controls.VPX_About_Dialog;
import com.cti.vpx.controls.VPX_AliasConfigWindow;
import com.cti.vpx.controls.VPX_ConnectedProcessor;
import com.cti.vpx.controls.VPX_ConsolePanel;
import com.cti.vpx.controls.VPX_LoggerPanel;
import com.cti.vpx.controls.VPX_MADPanel;
import com.cti.vpx.controls.VPX_MessagePanel;
import com.cti.vpx.controls.VPX_Preference;
import com.cti.vpx.controls.VPX_ProcessorTree;
import com.cti.vpx.controls.VPX_StatusBar;
import com.cti.vpx.controls.hex.MemoryViewFilter;
import com.cti.vpx.controls.hex.VPX_MemoryBrowser;
import com.cti.vpx.controls.hex.VPX_MemoryBrowserWindow;
import com.cti.vpx.controls.hex.VPX_MemoryPlotWindow;
import com.cti.vpx.controls.tab.VPX_TabbedPane;
import com.cti.vpx.model.Processor;
import com.cti.vpx.util.ComponentFactory;
import com.cti.vpx.util.VPXUDPMonitor;
import com.cti.vpx.util.VPXUtilities;

public class VPX_ETHWindow extends JFrame implements WindowListener, AdvertisementListener, MessageListener,
		CommunicationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2505823813174035752L;

	private static final int MAX_MEMORY_BROWSER = 4;

	private static final int MAX_MEMORY_PLOT = 3;

	private ResourceBundle rBundle;

	private JMenuBar vpx_MenuBar;

	private JMenu vpx_Menu_File;

	private JMenu vpx_Menu_Run;

	private JMenu vpx_Menu_Window;

	private JMenu vpx_Menu_Help;

	private JMenuItem vpx_Menu_File_Exit;

	private JMenuItem vpx_Menu_Run_Run;

	private JMenuItem vpx_Menu_Run_Pause;

	private JMenuItem vpx_Menu_Help_Help;

	private JMenuItem vpx_Menu_Help_About;

	private JMenuItem vpx_Menu_Window_MemoryBrowser;

	private JMenuItem vpx_Menu_Window_MemoryPlot;

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

	private VPXUDPMonitor udpMonitor = new VPXUDPMonitor();

	public VPX_MemoryBrowser vpxMemoryBrowser = null;

	private VPX_MemoryBrowserWindow[] memoryBrowserWindow;

	private VPX_MemoryPlotWindow[] memoryPlotWindow;

	ByteBuffer bb = ByteBuffer.allocate(9216);

	private int len;

	private JPanel baseTreePanel;

	private static int currentNoofMemoryView;

	private static int currentNoofMemoryPlot;

	/**
	 * Create the frame.
	 */
	public VPX_ETHWindow() {

		VPXUtilities.setParent(this);

		rBundle = VPXUtilities.getResourceBundle();

		init();

		promptAliasConfigFileName();

		udpMonitor.startMonitor();

	}

	private void init() {

		setTitle(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setIconImage(VPXUtilities.getAppIcon());

		setBounds(0, 0, VPXUtilities.getScreenWidth(), VPXUtilities.getScreenHeight());

		loadComponents();

		updateLog(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version") + " Started");

		setVisible(true);

		udpMonitor.addUDPListener(this);

		toFront();
	}

	private void loadComponents() {

		loadMenuBar();

		loadToolBar();

		loadContentPane();

		loadStatusPane();

		createMemoryBrowsers();

		createMemoryPlots();
	}

	public void createMemoryBrowsers() {

		memoryBrowserWindow = new VPX_MemoryBrowserWindow[MAX_MEMORY_BROWSER];

		for (int i = 0; i < MAX_MEMORY_BROWSER; i++) {

			memoryBrowserWindow[i] = new VPX_MemoryBrowserWindow(i);

			memoryBrowserWindow[i].setParent(this);
		}
	}

	public void openMemoryBrowser(MemoryViewFilter filter) {

		currentNoofMemoryView++;

		if (currentNoofMemoryView > MAX_MEMORY_BROWSER) {

			JOptionPane.showMessageDialog(this, "Maximum 4 memory Browsers are allowed.You are exceeding the limit",
					"Maximum Reached", JOptionPane.ERROR_MESSAGE);

			currentNoofMemoryView = MAX_MEMORY_BROWSER;
		}

		for (int i = 0; i < MAX_MEMORY_BROWSER; i++) {

			if (!memoryBrowserWindow[i].isVisible()) {

				memoryBrowserWindow[i].setMemoryFilter(filter);

				memoryBrowserWindow[i].showMemoryBrowser();

				break;
			}
		}

		if (currentNoofMemoryView == MAX_MEMORY_BROWSER) {

			vpx_Menu_Window_MemoryBrowser.setEnabled(false);

		} else {

			vpx_Menu_Window_MemoryBrowser.setEnabled(true);
		}

		vpx_Menu_Window_MemoryBrowser.setText(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (MAX_MEMORY_BROWSER - currentNoofMemoryView) + " ) ");
	}

	public void reindexMemoryBrowserIndex() {

		currentNoofMemoryView--;

		if (currentNoofMemoryView == MAX_MEMORY_BROWSER) {

			vpx_Menu_Window_MemoryBrowser.setEnabled(false);

		} else {

			vpx_Menu_Window_MemoryBrowser.setEnabled(true);
		}

		vpx_Menu_Window_MemoryBrowser.setText(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (MAX_MEMORY_BROWSER - currentNoofMemoryView) + " ) ");
	}

	public void createMemoryPlots() {

		memoryPlotWindow = new VPX_MemoryPlotWindow[MAX_MEMORY_PLOT];

		for (int i = 0; i < MAX_MEMORY_PLOT; i++) {

			memoryPlotWindow[i] = new VPX_MemoryPlotWindow(i);

			memoryPlotWindow[i].setParent(this);
		}
	}

	public void openMemoryPlot() {

		currentNoofMemoryPlot++;

		if (currentNoofMemoryPlot > MAX_MEMORY_PLOT) {

			JOptionPane.showMessageDialog(this, "Maximum 4 memory plots are allowed.You are exceeding the limit",
					"Maximum Reached", JOptionPane.ERROR_MESSAGE);

			currentNoofMemoryPlot = MAX_MEMORY_PLOT;
		}

		for (int i = 0; i < MAX_MEMORY_PLOT; i++) {

			if (!memoryPlotWindow[i].isVisible()) {

				memoryPlotWindow[i].showMemoryPlot();

				break;
			}
		}

		if (currentNoofMemoryPlot == MAX_MEMORY_PLOT) {

			vpx_Menu_Window_MemoryPlot.setEnabled(false);

		} else {

			vpx_Menu_Window_MemoryPlot.setEnabled(true);
		}

		vpx_Menu_Window_MemoryPlot.setText(rBundle.getString("Menu.Window.MemoryPlot") + " ( "
				+ (MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " ) ");
	}

	public void reindexMemoryPlotIndex() {

		currentNoofMemoryPlot--;

		if (currentNoofMemoryPlot == MAX_MEMORY_PLOT) {

			vpx_Menu_Window_MemoryPlot.setEnabled(false);

		} else {

			vpx_Menu_Window_MemoryPlot.setEnabled(true);
		}

		vpx_Menu_Window_MemoryPlot.setText(rBundle.getString("Menu.Window.MemoryPlot") + " ( "
				+ (MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " ) ");
	}

	private void loadMenuBar() {

		vpx_MenuBar = ComponentFactory.createJMenuBar();// new JMenuBar();

		vpx_Menu_File = ComponentFactory.createJMenu(rBundle.getString("Menu.File"));

		vpx_Menu_Run = ComponentFactory.createJMenu(rBundle.getString("Menu.Run"));

		vpx_Menu_Window = ComponentFactory.createJMenu(rBundle.getString("Menu.Window"));

		vpx_Menu_Help = ComponentFactory.createJMenu(rBundle.getString("Menu.Help"));

		vpx_MenuBar.add(vpx_Menu_File);

		vpx_Menu_File_Exit = ComponentFactory.createJMenuItem(new ExitAction(rBundle.getString("Menu.File.Exit")));

		vpx_Menu_File.add(ComponentFactory.createJSeparator());

		vpx_Menu_File.add(vpx_Menu_File_Exit);

		vpx_MenuBar.add(vpx_Menu_Run);

		vpx_Menu_Run_Run = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Run.Run"));

		vpx_Menu_Run_Pause = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Run.Pause"));

		vpx_Menu_Run.add(vpx_Menu_Run_Run);

		vpx_Menu_Run.add(ComponentFactory.createJSeparator());

		vpx_Menu_Run.add(vpx_Menu_Run_Pause);

		vpx_MenuBar.add(vpx_Menu_Window);

		vpx_Menu_Window_MemoryBrowser = ComponentFactory
				.createJMenuItem(new MemoryAction(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
						+ (MAX_MEMORY_BROWSER - currentNoofMemoryView) + " ) "));

		vpx_Menu_Window.add(vpx_Menu_Window_MemoryBrowser);

		vpx_Menu_Window_MemoryPlot = ComponentFactory.createJMenuItem(new PlotAction(rBundle
				.getString("Menu.Window.MemoryPlot") + " ( " + (MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " ) "));

		vpx_Menu_Window.add(vpx_Menu_Window_MemoryPlot);

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

		vpx_ToolBar.add(ComponentFactory.createJButton("", VPXUtilities.getImageIcon("image\\scan.png", 14, 14), null));

		getContentPane().add(vpx_ToolBar, BorderLayout.NORTH);
	}

	private void loadContentPane() {

		vpx_SplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		JTabbedPane tb = new JTabbedPane();

		createSystemTree();

		tb.addTab("Processor Explorer", baseTreePanel);

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

	private void createSystemTree() {

		baseTreePanel = new JPanel();

		baseTreePanel.setLayout(new BorderLayout());

		createLegendPanel();

		vpx_Processor_Tree = ComponentFactory.createProcessorTree(this);

		JScrollPane vpx_Processor_Tree_ScrollPane = new JScrollPane(vpx_Processor_Tree);

		for (int i = 0; i < vpx_Processor_Tree.getRowCount(); i++) {
			vpx_Processor_Tree.expandRow(i);
		}

		baseTreePanel.add(vpx_Processor_Tree_ScrollPane, BorderLayout.CENTER);

	}

	public void createLegendPanel() {

		JPanel treeLegendPanel = new JPanel();

		treeLegendPanel.setPreferredSize(new Dimension(300, 120));

		treeLegendPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");

		lblNewLabel.setOpaque(true);

		lblNewLabel.setBackground(new Color(0, 128, 0));

		lblNewLabel.setSize(new Dimension(32, 24));

		lblNewLabel.setPreferredSize(new Dimension(32, 14));

		lblNewLabel.setBounds(22, 64, 46, 14);

		treeLegendPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");

		lblNewLabel_1.setBackground(new Color(255, 0, 0));

		lblNewLabel_1.setOpaque(true);

		lblNewLabel_1.setSize(new Dimension(32, 24));

		lblNewLabel_1.setBounds(22, 90, 46, 14);

		treeLegendPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("W");

		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel_2.setSize(new Dimension(32, 24));

		lblNewLabel_2.setBounds(22, 12, 46, 14);

		treeLegendPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("A");

		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel_3.setSize(new Dimension(32, 24));

		lblNewLabel_3.setBounds(22, 38, 46, 14);

		treeLegendPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Waterfall Graph");

		lblNewLabel_4.setBounds(93, 12, 148, 14);

		treeLegendPanel.add(lblNewLabel_4);

		JLabel lblAmplitudeGraph = new JLabel("Amplitude Graph");

		lblAmplitudeGraph.setBounds(93, 38, 148, 14);

		treeLegendPanel.add(lblAmplitudeGraph);

		JLabel lblAvailable = new JLabel("Available");

		lblAvailable.setBounds(93, 64, 148, 14);

		treeLegendPanel.add(lblAvailable);

		JLabel lblUnavailable = new JLabel("Unavailable");

		lblUnavailable.setBounds(93, 90, 148, 14);

		treeLegendPanel.add(lblUnavailable);

		baseTreePanel.add(treeLegendPanel, BorderLayout.SOUTH);
	}

	public void updateMemory(byte[] b) {

		if (len < 9216) {
			bb.put(b);
			len = len + b.length;
		} else {
			vpxMemoryBrowser.updateMemory(b);
			len = 0;
			bb.clear();
		}

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

	public void addTab(String tabName, JScrollPane comp) {
		vpx_Content_Tabbed_Pane_Right.addTab(tabName, comp);

		vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);
	}

	public void addTab(String tabName, JPanel comp) {
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

			MemoryViewFilter m = new MemoryViewFilter();

			m.setSubsystem("Sub1");

			m.setProcessor("192.168.0.102");

			m.setCore("Core 3");

			m.setAutoRefresh(true);

			m.setTimeinterval(10);

			m.setUseMapFile(false);

			m.setMapPath("C:\\temp.map");

			m.setMemoryName("memory X");

			m.setMemoryLength(10 + "");

			m.setMemoryStride(10 + "");

			m.setDirectMemory(true);

			m.setMemoryAddress("0XFF00000");

			openMemoryBrowser(m);
		}
	}

	public class PlotAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public PlotAction(String name) {

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			openMemoryPlot();
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
				vpx_Content_Tabbed_Pane_Right.addTab("Flash", new JScrollPane(new VPX_MADPanel(VPX_ETHWindow.this)));

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
		udpMonitor.stopMonitor();
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

	@Override
	public void updateCommand(ATP command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendCommand(ATP command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMessage(String ip, String msg) {

		messagePanel.updateProcessorMessage(ip, msg);

	}

	@Override
	public void sendMessage(String ip, String msg) {
		System.out.println("Message : " + msg + " to IP : " + ip);

	}

	@Override
	public void printConsoleMessage(String ip, String msg) {

		System.out.println(msg);

		console.printConsoleMsg(ip, msg);

	}

	@Override
	public void updateProcessorStatus(String ip, String msg) {

		vpx_Processor_Tree.updateProcessorResponse(ip, msg);

	}
}

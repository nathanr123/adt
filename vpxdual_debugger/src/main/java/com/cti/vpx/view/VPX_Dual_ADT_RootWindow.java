package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.tree.DefaultMutableTreeNode;

import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.controls.VPX_About_Dialog;
import com.cti.vpx.controls.VPX_ConnectedProcessor;
import com.cti.vpx.controls.VPX_FlashProcessor;
import com.cti.vpx.controls.VPX_LoggerPanel;
import com.cti.vpx.controls.VPX_MessagePanel;
import com.cti.vpx.controls.VPX_Preference;
import com.cti.vpx.controls.VPX_ProcessorTree;
import com.cti.vpx.controls.VPX_ScanWindow;
import com.cti.vpx.controls.VPX_StatusBar;
import com.cti.vpx.controls.hex.VPX_MemoryBrowser;
import com.cti.vpx.controls.tab.VPX_TabbedPane;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.ComponentFactory;
import com.cti.vpx.util.Pinger;
import com.cti.vpx.util.VPXUtilities;

public class VPX_Dual_ADT_RootWindow extends JFrame {

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

	private VPXSystem system;

	private DefaultMutableTreeNode systemRootNode;

	private VPX_LoggerPanel logger;

	private VPX_ProcessorTree vpx_Processor_Tree;

	private JTabbedPane vpx_Content_Tabbed_Pane_Message;

	private VPX_StatusBar statusBar;

	private VPX_About_Dialog aboutDialog = new VPX_About_Dialog();

	private ProcessorMonitorThread monitor = new ProcessorMonitorThread();

	private VPX_MessagePanel messagePanel;

	/**
	 * Create the frame.
	 */
	public VPX_Dual_ADT_RootWindow() {

		rBundle = VPXUtilities.getResourceBundle();

		initializeRootWindow();

	}

	private void initializeRootWindow() {

		setTitle(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setAlwaysOnTop(true);

		setIconImage(VPXUtilities.getAppIcon());

		setBounds(0, 0, VPXUtilities.getScreenWidth(), VPXUtilities.getScreenHeight());

		loadComponents();

		updateLog(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version") + " Started");

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

		vpx_Menu_File_Scan = ComponentFactory.createJMenuItem(new ScanAction(rBundle.getString("Menu.File.Scan")));

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

		vpx_ToolBar.add(ComponentFactory.createJToolBarButton(new ScanAction(VPXUtilities.getImageIcon(
				"images\\scan.png", 14, 14))), null);

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

		vpx_Content_Tabbed_Pane_Message.addTab("Logger", logger);

		vpx_Right_SplitPane.setRightComponent(vpx_Content_Tabbed_Pane_Message);

		vpx_Right_SplitPane.setDividerLocation(((int) VPXUtilities.getScreenHeight() / 2 + (int) VPXUtilities
				.getScreenHeight() / 7));

		vpx_Right_SplitPane.setDividerSize(2);

		return vpx_Right_SplitPane;
	}

	private JScrollPane getSystemTree() {

		systemRootNode = new DefaultMutableTreeNode();

		createVPXObject();

		vpx_Processor_Tree = ComponentFactory.createProcessorTree(this, systemRootNode);

		vpx_Processor_Tree.setVPXSystem(system);

		JScrollPane vpx_Processor_Tree_ScrollPane = new JScrollPane(vpx_Processor_Tree);

		for (int i = 0; i < vpx_Processor_Tree.getRowCount(); i++) {
			vpx_Processor_Tree.expandRow(i);
		}

		return vpx_Processor_Tree_ScrollPane;
	}

	private void createVPXObject() {

		if (system == null) {

			system = VPXUtilities.getVPXSystem();

			systemRootNode.setUserObject(system.getName());
		}

		VPXUtilities.setVPXSystem(system);

		loadSystemRootNode(system);

	}

	private void loadSystemRootNode(VPXSystem system) {

		systemRootNode.removeAllChildren();

		systemRootNode.setUserObject(system.getName());

		List<Processor> processorList = system.getProcessors();

		if (processorList != null) {
			for (Iterator<Processor> iterator = processorList.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				DefaultMutableTreeNode processorNode = new DefaultMutableTreeNode(String.format("%s(%s)",
						processor.getiP_Addresses(), processor.getName(), processor.getPortno()));

				systemRootNode.add(processorNode);
			}
		}

	}

	private void updateProcessorTree(VPXSystem sys) {
		vpx_Processor_Tree.updateUI();

		for (int i = 0; i < vpx_Processor_Tree.getRowCount(); i++) {
			vpx_Processor_Tree.expandRow(i);
		}

		VPXUtilities.setVPXSystem(sys);

	}

	private void reloadVPXSystemTree(VPXSystem system) {

		loadSystemRootNode(system);

		updateProcessorTree(system);

		monitor.startMonitor();

		messagePanel.startRecieveMessage();

	}

	public void reloadProcessorTree(VPXSystem vpx) {

		if (vpx == null) {
			updateLog(VPX_LoggerPanel.ERROR, "VPX not created");
		} else {
			updateLog("VPX Object Created");
		}

		reloadVPXSystemTree(vpx);

		updateLog("VPX System Tree Refreshed");

		updateStatus("VPX System Tree Refreshed");
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

	public void connectProcessor(Processor pro) {
		vpx_Content_Tabbed_Pane_Right.addTab(String.format("%s(%s)", pro.getiP_Addresses(), pro.getName()),
				new VPX_ConnectedProcessor(VPX_Dual_ADT_RootWindow.this));

		vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);
	}

	public class ScanAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public ScanAction(String name) {

			putValue(NAME, name);

		}

		public ScanAction(ImageIcon icon) {

			super("", icon);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			monitor.stopMonitor();

			VPX_ScanWindow ir = new VPX_ScanWindow(VPX_Dual_ADT_RootWindow.this);

			ir.setVisible(true);

		}
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
				vpx_Content_Tabbed_Pane_Right.addTab("Flash", new JScrollPane(new VPX_FlashProcessor()));

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
			VPX_Dual_ADT_RootWindow.this.dispose();

		}
	}

	private void refreshProcessorTree(Processor processor, boolean isRemove) {

		int size = systemRootNode.getChildCount();

		String ip = processor.getiP_Addresses();

		int foundAt = -1;

		for (int i = 0; i < size; i++) {

			DefaultMutableTreeNode child = (DefaultMutableTreeNode) systemRootNode.getChildAt(i);

			if (child.getUserObject().toString().startsWith(ip)) {

				foundAt = i;

				break;
			}

		}

		if (isRemove) {
			if (foundAt > -1) {
				systemRootNode.remove(foundAt);

				updateLog(ip + " Lost");
			}
		} else {
			if (foundAt == -1) {

				DefaultMutableTreeNode processorNode = new DefaultMutableTreeNode(String.format("%s(%s)",
						processor.getiP_Addresses(), processor.getName(), processor.getPortno()));

				systemRootNode.add(processorNode);

				updateLog(processor.getiP_Addresses() + " Found");
			}
		}

		vpx_Processor_Tree.updateUI();
	}

	private void refreshProcessorTree(ATPCommand cmd, String ip, boolean isRemove) {

		int size = systemRootNode.getChildCount();

		int foundAt = -1;

		for (int i = 0; i < size; i++) {

			DefaultMutableTreeNode child = (DefaultMutableTreeNode) systemRootNode.getChildAt(i);

			if (child.getUserObject().toString().startsWith(ip)) {

				foundAt = i;

				break;
			}

		}

		if (isRemove) {
			if (foundAt > -1) {
				systemRootNode.remove(foundAt);

				updateLog(ip + " Lost");
			}
		} else {
			if (foundAt == -1) {
				Processor processor = new Processor(ip,
						VPXUtilities.getProcessor(cmd.params.proccesorInfo.processorTYPE));

				DefaultMutableTreeNode processorNode = new DefaultMutableTreeNode(String.format("%s(%s)",
						processor.getiP_Addresses(), processor.getName(), processor.getPortno()));

				systemRootNode.add(processorNode);

				updateLog(processor.getiP_Addresses() + " Found");
			}
		}

		vpx_Processor_Tree.updateUI();
	}

	class ProcessorMonitorThread implements Runnable {

		Thread th;

		private boolean isStarted = true;

		public ProcessorMonitorThread() {

		}

		@Override
		public void run() {
			List<Processor> vpxSystemProcessors = VPXUtilities.getVPXSystem().getProcessors();

			while (isStarted) {
				try {

					Thread.sleep(10000);

					for (Iterator<Processor> iterator = vpxSystemProcessors.iterator(); iterator.hasNext();) {

						Processor processor = iterator.next();

						refreshProcessorTree(processor, !Pinger.ping(processor.getiP_Addresses()));

					}
				} catch (Exception e) {

				}
			}

		}

		public void startMonitor() {
			isStarted = true;
			if (th == null)
				th = new Thread(this, "Test");
			th.start();
		}

		public void stopMonitor() {
			isStarted = false;

			th = null;
		}

	}

}

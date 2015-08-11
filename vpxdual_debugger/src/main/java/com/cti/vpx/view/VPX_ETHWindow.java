package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.Listener.VPXAdvertisementListener;
import com.cti.vpx.Listener.VPXCommunicationListener;
import com.cti.vpx.Listener.VPXMessageListener;
import com.cti.vpx.Listener.VPXUDPMonitor;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.MSGCommand;
import com.cti.vpx.controls.VPX_About_Dialog;
import com.cti.vpx.controls.VPX_AliasConfigWindow;
import com.cti.vpx.controls.VPX_BISTResultWindow;
import com.cti.vpx.controls.VPX_ChangePasswordWindow;
import com.cti.vpx.controls.VPX_ChangePeriodicity;
import com.cti.vpx.controls.VPX_ConsolePanel;
import com.cti.vpx.controls.VPX_DetailPanel;
import com.cti.vpx.controls.VPX_EthernetFlashPanel;
import com.cti.vpx.controls.VPX_ExecutionPanel;
import com.cti.vpx.controls.VPX_FlashProgressWindow;
import com.cti.vpx.controls.VPX_FlashWizard;
import com.cti.vpx.controls.VPX_LogFileViewPanel;
import com.cti.vpx.controls.VPX_LoggerPanel;
import com.cti.vpx.controls.VPX_MADPanel;
import com.cti.vpx.controls.VPX_MessagePanel;
import com.cti.vpx.controls.VPX_PasswordWindow;
import com.cti.vpx.controls.VPX_Preference;
import com.cti.vpx.controls.VPX_ProcessorTree;
import com.cti.vpx.controls.VPX_StatusBar;
import com.cti.vpx.controls.VPX_SubnetFilterWindow;
import com.cti.vpx.controls.VPX_VLANConfig;
import com.cti.vpx.controls.hex.MemoryViewFilter;
import com.cti.vpx.controls.hex.VPX_MemoryBrowser;
import com.cti.vpx.controls.hex.VPX_MemoryBrowserWindow;
import com.cti.vpx.controls.hex.VPX_MemoryPlotWindow;
import com.cti.vpx.controls.tab.VPX_TabbedPane;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.ComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;

public class VPX_ETHWindow extends JFrame implements WindowListener, VPXAdvertisementListener, VPXMessageListener,
		VPXCommunicationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2505823813174035752L;

	final JFileChooser fileDialog = new JFileChooser();

	final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Log Files", "log");

	private static final VPX_About_Dialog aboutDialog = new VPX_About_Dialog();

	private static VPX_PasswordWindow paswordWindow = new VPX_PasswordWindow();

	private ResourceBundle rBundle;

	private JMenuBar vpx_MenuBar;

	private JMenu vpx_Menu_File;

	private JMenu vpx_Menu_Window;

	private JMenu vpx_Menu_Tool;

	private JMenu vpx_Menu_Help;

	// File Menu Items
	private JMenuItem vpx_Menu_File_AliasConfig;

	private JMenuItem vpx_Menu_File_SubnetFilter;

	private JMenuItem vpx_Menu_File_OpenLog;

	private JMenuItem vpx_Menu_File_Detail;

	private JMenuItem vpx_Menu_File_Refresh;

	private JMenuItem vpx_Menu_File_Exit;

	// Window Menu Items
	private JMenuItem vpx_Menu_Window_MemoryBrowser;

	private JMenuItem vpx_Menu_Window_MemoryPlot;

	private JMenuItem vpx_Menu_Window_EthFlash;

	private JMenuItem vpx_Menu_Window_Amplitude;

	private JMenuItem vpx_Menu_Window_Waterfall;

	private JMenuItem vpx_Menu_Window_MAD;

	private JMenuItem vpx_Menu_Window_FlashWizard;

	private JMenuItem vpx_Menu_Window_BIST;

	private JMenuItem vpx_Menu_Window_Execution;

	private JMenuItem vpx_Menu_Window_P2020Config;

	private JMenuItem vpx_Menu_Window_ChangeIP;

	// Tools Menu Items
	private JMenuItem vpx_Menu_Tools_ChangePWD;

	private JMenuItem vpx_Menu_Tools_ChangePeriod;

	private JMenuItem vpx_Menu_Tools_Prefrences;

	private JMenuItem vpx_Menu_Tools_Console;

	private JMenuItem vpx_Menu_Tools_Log;

	// Help Menu Items
	private JMenuItem vpx_Menu_Help_Help;

	private JMenuItem vpx_Menu_Help_About;

	private JToolBar vpx_ToolBar;

	private JSplitPane vpx_SplitPane;

	private JSplitPane vpx_Right_SplitPane;

	private VPX_TabbedPane vpx_Content_Tabbed_Pane_Right;

	private VPX_LoggerPanel logger;

	private VPX_ConsolePanel console;

	private VPX_ProcessorTree vpx_Processor_Tree;

	private JTabbedPane vpx_Content_Tabbed_Pane_Message;

	private VPX_StatusBar statusBar;

	private VPX_MessagePanel messagePanel;

	private VPXUDPMonitor udpMonitor;

	public VPX_MemoryBrowser vpxMemoryBrowser = null;

	private VPX_MemoryBrowserWindow[] memoryBrowserWindow;

	private VPX_MemoryPlotWindow[] memoryPlotWindow;

	private JPanel baseTreePanel;

	private JToggleButton btnFilter;

	private static VPX_SubnetFilterWindow subnetFilter;

	public static int currentNoofMemoryView;

	public static int currentNoofMemoryPlot;

	/**
	 * Create the frame.
	 */
	public VPX_ETHWindow() throws Exception {

		VPXUtilities.setParent(this);

		rBundle = VPXUtilities.getResourceBundle();

		init();

		promptAliasConfigFileName();

		subnetFilter = new VPX_SubnetFilterWindow(VPX_ETHWindow.this);

		udpMonitor = new VPXUDPMonitor();

		udpMonitor.addUDPListener(this);

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

		toFront();
	}

	private void loadComponents() {

		createMenuBar();

		createToolBar();

		loadContentPane();

		loadStatusPane();

		createMemoryBrowsers();

		createMemoryPlots();
	}

	public void createMemoryBrowsers() {

		memoryBrowserWindow = new VPX_MemoryBrowserWindow[VPXConstants.MAX_MEMORY_BROWSER];

		for (int i = 0; i < VPXConstants.MAX_MEMORY_BROWSER; i++) {

			memoryBrowserWindow[i] = new VPX_MemoryBrowserWindow(i);

			memoryBrowserWindow[i].setParent(this);
		}
	}

	public void applyMask(String mask) {
		udpMonitor.applyFilterbySubnet(mask);
	}

	public void openMemoryBrowser(MemoryViewFilter filter) {

		currentNoofMemoryView++;

		if (currentNoofMemoryView > VPXConstants.MAX_MEMORY_BROWSER) {

			JOptionPane.showMessageDialog(this, "Maximum 4 memory Browsers are allowed.You are exceeding the limit",
					"Maximum Reached", JOptionPane.ERROR_MESSAGE);

			currentNoofMemoryView = VPXConstants.MAX_MEMORY_BROWSER;
		}

		for (int i = 0; i < VPXConstants.MAX_MEMORY_BROWSER; i++) {

			if (!memoryBrowserWindow[i].isVisible()) {

				memoryBrowserWindow[i].setMemoryFilter(filter);

				memoryBrowserWindow[i].showMemoryBrowser();

				break;
			}
		}

		if (currentNoofMemoryView == VPXConstants.MAX_MEMORY_BROWSER) {

			vpx_Menu_Window_MemoryBrowser.setEnabled(false);

		} else {

			vpx_Menu_Window_MemoryBrowser.setEnabled(true);
		}

		vpx_Menu_Window_MemoryBrowser.setText(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (VPXConstants.MAX_MEMORY_BROWSER - currentNoofMemoryView) + " ) ");

		updateLog("Memory Browser opened");
	}

	public void reindexMemoryBrowserIndex() {

		currentNoofMemoryView--;

		if (currentNoofMemoryView == VPXConstants.MAX_MEMORY_BROWSER) {

			vpx_Menu_Window_MemoryBrowser.setEnabled(false);

		} else {

			vpx_Menu_Window_MemoryBrowser.setEnabled(true);
		}

		vpx_Menu_Window_MemoryBrowser.setText(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (VPXConstants.MAX_MEMORY_BROWSER - currentNoofMemoryView) + " ) ");
	}

	public void createMemoryPlots() {

		memoryPlotWindow = new VPX_MemoryPlotWindow[VPXConstants.MAX_MEMORY_PLOT];

		for (int i = 0; i < VPXConstants.MAX_MEMORY_PLOT; i++) {

			memoryPlotWindow[i] = new VPX_MemoryPlotWindow(i);

			memoryPlotWindow[i].setParent(this);
		}
	}

	public void openMemoryPlot() {

		currentNoofMemoryPlot++;

		if (currentNoofMemoryPlot > VPXConstants.MAX_MEMORY_PLOT) {

			JOptionPane.showMessageDialog(this, "Maximum 4 memory plots are allowed.You are exceeding the limit",
					"Maximum Reached", JOptionPane.ERROR_MESSAGE);

			currentNoofMemoryPlot = VPXConstants.MAX_MEMORY_PLOT;
		}

		for (int i = 0; i < VPXConstants.MAX_MEMORY_PLOT; i++) {

			if (!memoryPlotWindow[i].isVisible()) {

				memoryPlotWindow[i].showMemoryPlot();

				break;
			}
		}

		if (currentNoofMemoryPlot == VPXConstants.MAX_MEMORY_PLOT) {

			vpx_Menu_Window_MemoryPlot.setEnabled(false);

		} else {

			vpx_Menu_Window_MemoryPlot.setEnabled(true);
		}

		vpx_Menu_Window_MemoryPlot.setText(rBundle.getString("Menu.Window.MemoryPlot") + " ( "
				+ (VPXConstants.MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " ) ");

		updateLog("Memory Plot opened");
	}

	public void reindexMemoryPlotIndex() {

		currentNoofMemoryPlot--;

		if (currentNoofMemoryPlot == VPXConstants.MAX_MEMORY_PLOT) {

			vpx_Menu_Window_MemoryPlot.setEnabled(false);

		} else {

			vpx_Menu_Window_MemoryPlot.setEnabled(true);
		}

		vpx_Menu_Window_MemoryPlot.setText(rBundle.getString("Menu.Window.MemoryPlot") + " ( "
				+ (VPXConstants.MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " ) ");
	}

	public void reloadVPXSystem() {

		console.reloadSubsystems();

		for (int i = 0; i < VPXConstants.MAX_MEMORY_BROWSER; i++) {
			memoryBrowserWindow[i].reloadSubsystems();
		}

		for (int i = 0; i < VPXConstants.MAX_MEMORY_PLOT; i++) {

			memoryPlotWindow[i].reloadAllSubsystems();
		}
	}

	private void createMenuBar() {

		// Creating MenuBar
		vpx_MenuBar = ComponentFactory.createJMenuBar();// new JMenuBar();

		// Creating Menus
		vpx_Menu_File = ComponentFactory.createJMenu(rBundle.getString("Menu.File"));

		vpx_Menu_Window = ComponentFactory.createJMenu(rBundle.getString("Menu.Window"));

		vpx_Menu_Tool = ComponentFactory.createJMenu(rBundle.getString("Menu.Tools"));

		vpx_Menu_Help = ComponentFactory.createJMenu(rBundle.getString("Menu.Help"));

		// Creating MenuItems

		// File Menus
		vpx_Menu_File_AliasConfig = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Config"),
				VPXConstants.Icons.ICON_CONFIG);

		vpx_Menu_File_AliasConfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				showAliasConfig();

			}
		});

		vpx_Menu_File_SubnetFilter = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Filter"),
				VPXConstants.Icons.ICON_FILTER);

		vpx_Menu_File_SubnetFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				btnFilter.doClick();

			}
		});

		vpx_Menu_File_OpenLog = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.OpenLog"),
				VPXConstants.Icons.ICON_OPEN);

		vpx_Menu_File_OpenLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				openLogFile();

			}
		});

		vpx_Menu_File_Refresh = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Refresh"),
				VPXConstants.Icons.ICON_REFRESH);

		vpx_Menu_File_Refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				refreshProcessorTree();

			}
		});

		vpx_Menu_File_Detail = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Detail"),
				VPXConstants.Icons.ICON_DETAIL);

		vpx_Menu_File_Detail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showDetail();

			}
		});

		vpx_Menu_File_Exit = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Exit"),
				VPXConstants.Icons.ICON_DELETE_EXIT);

		vpx_Menu_File_Exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				exitApplication();

			}
		});

		// Window Menus
		vpx_Menu_Window_MemoryBrowser = ComponentFactory

		.createJMenuItem(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (VPXConstants.MAX_MEMORY_BROWSER - currentNoofMemoryView) + " ) ", VPXConstants.Icons.ICON_MEMORY);

		vpx_Menu_Window_MemoryBrowser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showMemoryBrowser();

			}
		});

		vpx_Menu_Window_MemoryPlot = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.MemoryPlot")
				+ " ( " + (VPXConstants.MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " ) ", VPXConstants.Icons.ICON_PLOT);

		vpx_Menu_Window_MemoryPlot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showMemoryPlot();

			}
		});

		vpx_Menu_Window_EthFlash = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.EthFlash"),
				VPXConstants.Icons.ICON_ETHFLASH);

		vpx_Menu_Window_EthFlash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showEthFlash();
			}
		});

		vpx_Menu_Window_Amplitude = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Amplitude"),
				VPXUtilities.getEmptyIcon(14, 14));

		vpx_Menu_Window_Amplitude.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showAmplitude();

			}
		});
		vpx_Menu_Window_Waterfall = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Waterfall"),
				VPXUtilities.getEmptyIcon(14, 14));

		vpx_Menu_Window_Waterfall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showWaterfall();

			}
		});

		vpx_Menu_Window_MAD = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.MAD"),
				VPXConstants.Icons.ICON_MAD);

		vpx_Menu_Window_MAD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showMAD();

			}
		});

		vpx_Menu_Window_FlashWizard = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Wizard"),
				VPXConstants.Icons.ICON_MAD);

		vpx_Menu_Window_FlashWizard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showFlashWizard();

			}
		});

		vpx_Menu_Window_BIST = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.BIST"),
				VPXConstants.Icons.ICON_BIST);

		vpx_Menu_Window_BIST.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showBIST();

			}
		});

		vpx_Menu_Window_Execution = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Execute"),
				VPXConstants.Icons.ICON_EXECUTION);

		vpx_Menu_Window_Execution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showExecution();

			}
		});

		vpx_Menu_Window_P2020Config = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.P2020Config"),
				VPXUtilities.getEmptyIcon(14, 14));

		vpx_Menu_Window_P2020Config.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showVLAN(0);

			}
		});

		vpx_Menu_Window_ChangeIP = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.ChangeIP"),
				VPXUtilities.getEmptyIcon(14, 14));

		vpx_Menu_Window_ChangeIP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showVLAN(1);

			}
		});
		// Tools Menus

		vpx_Menu_Tools_ChangePWD = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.ChangePWD"),
				VPXUtilities.getEmptyIcon(14, 14));

		vpx_Menu_Tools_ChangePWD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showChangePassword();

			}
		});

		vpx_Menu_Tools_ChangePeriod = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Periodicity"),
				VPXUtilities.getEmptyIcon(14, 14));

		vpx_Menu_Tools_ChangePeriod.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showChangePeriodicity();

			}
		});
		vpx_Menu_Tools_Prefrences = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Preferences"),
				VPXUtilities.getEmptyIcon(14, 14));

		vpx_Menu_Tools_Prefrences.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showPrefrences();
			}
		});

		vpx_Menu_Tools_Console = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Console"),
				VPXUtilities.getEmptyIcon(14, 14));

		vpx_Menu_Tools_Console.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showConsole();

			}
		});

		vpx_Menu_Tools_Log = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Log"),
				VPXUtilities.getEmptyIcon(14, 14));

		vpx_Menu_Tools_Log.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showLog();

			}
		});

		// Help Menus
		vpx_Menu_Help_Help = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.Help"),
				VPXConstants.Icons.ICON_HELP);

		vpx_Menu_Help_Help.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showHelp();

			}
		});

		vpx_Menu_Help_About = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.About"),
				VPXConstants.Icons.ICON_ABOUT);

		vpx_Menu_Help_About.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showAbout();

			}
		});
		// Adding

		// Adding Menus
		vpx_MenuBar.add(vpx_Menu_File);

		vpx_MenuBar.add(vpx_Menu_Window);

		vpx_MenuBar.add(vpx_Menu_Tool);

		vpx_MenuBar.add(vpx_Menu_Help);

		// Adding Menu Items

		// File Menu Items
		vpx_Menu_File.add(vpx_Menu_File_AliasConfig);

		vpx_Menu_File.add(vpx_Menu_File_OpenLog);

		vpx_Menu_File.add(vpx_Menu_File_SubnetFilter);

		vpx_Menu_File.add(ComponentFactory.createJSeparator());

		vpx_Menu_File.add(vpx_Menu_File_Refresh);

		vpx_Menu_File.add(vpx_Menu_File_Detail);

		vpx_Menu_File.add(ComponentFactory.createJSeparator());

		vpx_Menu_File.add(vpx_Menu_File_Exit);

		// Window Menu Items
		vpx_Menu_Window.add(vpx_Menu_Window_Execution);

		vpx_Menu_Window.add(vpx_Menu_Window_EthFlash);

		vpx_Menu_Window.add(vpx_Menu_Window_MAD);

		vpx_Menu_Window.add(vpx_Menu_Window_FlashWizard);

		vpx_Menu_Window.add(vpx_Menu_Window_BIST);

		vpx_Menu_Window.add(ComponentFactory.createJSeparator());

		vpx_Menu_Window.add(vpx_Menu_Window_MemoryBrowser);

		vpx_Menu_Window.add(vpx_Menu_Window_MemoryPlot);

		vpx_Menu_Window.add(vpx_Menu_Window_Amplitude);

		vpx_Menu_Window.add(vpx_Menu_Window_Waterfall);

		vpx_Menu_Window.add(ComponentFactory.createJSeparator());

		vpx_Menu_Window.add(vpx_Menu_Window_P2020Config);

		vpx_Menu_Window.add(vpx_Menu_Window_ChangeIP);

		// Tool Menu Items
		vpx_Menu_Tool.add(vpx_Menu_Tools_ChangePWD);

		vpx_Menu_Tool.add(vpx_Menu_Tools_ChangePeriod);

		vpx_Menu_Tool.add(ComponentFactory.createJSeparator());

		vpx_Menu_Tool.add(vpx_Menu_Tools_Console);

		vpx_Menu_Tool.add(vpx_Menu_Tools_Log);

		vpx_Menu_Tool.add(ComponentFactory.createJSeparator());

		vpx_Menu_Tool.add(vpx_Menu_Tools_Prefrences);

		// Help Menu Items
		vpx_Menu_Help.add(vpx_Menu_Help_Help);

		vpx_Menu_Help.add(ComponentFactory.createJSeparator());

		vpx_Menu_Help.add(vpx_Menu_Help_About);

		setJMenuBar(vpx_MenuBar);
	}

	private void createToolBar() {

		vpx_ToolBar = ComponentFactory.createJToolBar();

		vpx_ToolBar.setFloatable(false);

		JButton btnAliasRefresh = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_REFRESH, null);

		btnAliasRefresh.setToolTipText("Refresh Processors List Tree");

		btnAliasRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_File_Refresh.doClick();

			}
		});

		vpx_ToolBar.add(btnAliasRefresh);

		btnFilter = new JToggleButton(VPXConstants.Icons.ICON_FILTER);

		btnFilter.setToolTipText("Subnet Filter");

		btnFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				AbstractButton abstractButton = (AbstractButton) e.getSource();

				boolean selected = abstractButton.getModel().isSelected();

				if (selected) {

					subnetFilter.showFilter();

				} else {

					int option = JOptionPane.showConfirmDialog(VPX_ETHWindow.this,
							"Do you want to clear subnet mask now?", "Confirmation", JOptionPane.YES_NO_OPTION);

					if (option == JOptionPane.YES_OPTION) {

						udpMonitor.clearFilterbySubnet();

						vpx_Processor_Tree.refreshProcessorTree();
					}
				}
			}
		});

		JButton btnOpenLog = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_OPEN, null);

		btnOpenLog.setToolTipText("Open Log File");

		btnOpenLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_File_OpenLog.doClick();

			}
		});

		vpx_ToolBar.add(btnOpenLog);

		JButton btnAliasConfig = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_CONFIG, null);

		btnAliasConfig.setToolTipText("Alias Configuration");

		btnAliasConfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_File_AliasConfig.doClick();

			}
		});

		vpx_ToolBar.add(btnAliasConfig);

		JButton btnDetail = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_DETAIL, null);

		btnDetail.setToolTipText("VPX System Detail");

		btnDetail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_File_Detail.doClick();

			}
		});

		JButton btnMemoryBrowser = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_MEMORY, null);

		btnMemoryBrowser.setToolTipText("Memory Browser");

		btnMemoryBrowser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vpx_Menu_Window_MemoryBrowser.doClick();

			}
		});

		JButton btnMemoryPlot = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_PLOT, null);

		btnMemoryPlot.setToolTipText("Memory Plots");

		btnMemoryPlot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_Window_MemoryPlot.doClick();

			}
		});

		JButton btnMAD = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_MAD, null);

		btnMAD.setToolTipText("MAD Utility");

		btnMAD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_Window_MAD.doClick();

			}
		});

		JButton btnBIST = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_BIST, null);

		btnBIST.setToolTipText("Built In Self Test");

		btnBIST.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_Window_BIST.doClick();

			}
		});

		JButton btnFlash = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_ETHFLASH, null);

		btnFlash.setToolTipText("Ethernet Flash");

		btnFlash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_Window_EthFlash.doClick();

			}
		});

		JButton btnExecute = ComponentFactory.createJButton("", VPXConstants.Icons.ICON_EXECUTION, null);

		btnExecute.setToolTipText("Execution");

		btnExecute.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_Window_Execution.doClick();

			}
		});

		// Adding to Toolbar

		vpx_ToolBar.add(btnFilter);

		vpx_ToolBar.add(btnDetail);

		vpx_ToolBar.add(btnOpenLog);

		vpx_ToolBar.addSeparator();

		vpx_ToolBar.add(btnMemoryBrowser);

		vpx_ToolBar.add(btnMemoryPlot);

		vpx_ToolBar.addSeparator();

		vpx_ToolBar.add(btnMAD);

		vpx_ToolBar.add(btnBIST);

		vpx_ToolBar.add(btnFlash);

		vpx_ToolBar.add(btnExecute);

		vpx_ToolBar.addSeparator();

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

		messagePanel.setPreferredSize(new Dimension(450, 300));

		contentPanel.add(messagePanel, BorderLayout.EAST);

		vpx_Content_Tabbed_Pane_Right = new VPX_TabbedPane(true, true);

		contentPanel.add(vpx_Content_Tabbed_Pane_Right);

		vpx_Right_SplitPane.setLeftComponent(contentPanel);

		vpx_Content_Tabbed_Pane_Message = new JTabbedPane();

		logger = new VPX_LoggerPanel();

		console = new VPX_ConsolePanel();

		vpx_Content_Tabbed_Pane_Message.addTab("Log", logger);

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

		treeLegendPanel.setBorder(BorderFactory.createLoweredBevelBorder());

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

	public void unToggleFilter() {

		btnFilter.setSelected(false);
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

	private int isAlreadyExist(String find) {
		int ret = -1;

		for (int i = 0; i < vpx_Content_Tabbed_Pane_Right.getTabCount(); i++) {
			if (vpx_Content_Tabbed_Pane_Right.getTitleAt(i).equals(find)) {
				ret = i;
				break;
			}
		}
		return ret;
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

	public void refreshProcessorTree() {

		vpx_Processor_Tree.refreshProcessorsStatus();
	}

	public void refreshProcessorTree(boolean isReload) {

		if (isReload) {
			vpx_Processor_Tree.refreshProcessorTree();
		} else {
			vpx_Processor_Tree.refreshProcessorsStatus();
		}
	}

	public void openLogFile() {

		fileDialog.addChoosableFileFilter(filterOut);

		int returnVal = fileDialog.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File file = fileDialog.getSelectedFile();

			vpx_Content_Tabbed_Pane_Right
					.addTab("Log File - " + file.getAbsolutePath(), new VPX_LogFileViewPanel(file));

			vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

		}

	}

	public void showAliasConfig() {

		new VPX_AliasConfigWindow(VPX_ETHWindow.this).setVisible(true);

	}

	public void showDetail() {

		VPX_DetailPanel detail = new VPX_DetailPanel(VPX_ETHWindow.this, VPXSystem.class.getSimpleName());

		detail.setVisible(true);

	}

	public void exitApplication() {

		String ObjButtons[] = { "Yes", "No" };

		int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?",
				rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version"),
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);

		if (PromptResult == 0) {

			udpMonitor.stopMonitor();

			udpMonitor.close();

			VPX_ETHWindow.this.dispose();

			System.exit(0);
		}

	}

	public void showMemoryBrowser() {

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

	public void showMemoryPlot() {

		openMemoryPlot();

	}

	public void showEthFlash() {

		vpx_Content_Tabbed_Pane_Right.addTab("Ethernet Flash", new JScrollPane(new VPX_EthernetFlashPanel(
				VPX_ETHWindow.this)));

		vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

		updateLog("Ethernet Flash opened");
	}

	public void showAmplitude() {

		updateLog("Showing Amplitude Graph");
	}

	public void showWaterfall() {

		updateLog("Showing Waterfall Graph");
	}

	public void showMAD() {

		int i = isAlreadyExist("Mad Utility");

		if (i == -1) {

			vpx_Content_Tabbed_Pane_Right.addTab("Mad Utility", new JScrollPane(new VPX_MADPanel(VPX_ETHWindow.this)));

			vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

			updateLog("MAD utility opened");

		} else {

			vpx_Content_Tabbed_Pane_Right.setSelectedIndex(i);

			updateLog("MAD utility already opened");
		}
	}

	public void showBIST() {

		updateLog("Starting Built in Self Test");

		new VPX_BISTResultWindow().setVisible(true);

		updateLog("Built in Self Test Completed");
	}

	public void showExecution() {

		updateLog("Showing Execution Window");

		vpx_Content_Tabbed_Pane_Right.addTab("Execution", new JScrollPane(new VPX_ExecutionPanel()));

		vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

	}

	public void showFlashWizard() {

		VPX_FlashWizard dialog = new VPX_FlashWizard(VPX_ETHWindow.this);

		dialog.setVisible(true);
	}

	public void showVLAN(int tab) {

		paswordWindow.resetPassword();

		paswordWindow.setVisible(true);

		updateLog("Asking Super user password for VLAN Configuration");

		if (VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.SECURITY_PWD).equals(paswordWindow.getPasword())) {

			// addTab("VLAN", new JScrollPane(new
			// VPX_P2020ConfigurationPanel(tab)));

			final VPX_VLANConfig browserCanvas = new VPX_VLANConfig();

			JPanel contentPane = new JPanel();

			contentPane.setLayout(new BorderLayout());

			contentPane.add(browserCanvas, BorderLayout.CENTER);

			JDialog frame = new JDialog(this, "VLAN Configuration");

			frame.setBounds(100, 100, 1400, 700);

			frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

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

				browserCanvas.setUrl("http://192.168.10.1/cgi-bin/portsetting.cgi");
			} else {
			}

			frame.setSize(1401, 701);

			updateLog("VLAN Configuration opened");

		} else {

			JOptionPane.showMessageDialog(this, "Pasword invalid", "Authentication", JOptionPane.ERROR_MESSAGE);

			paswordWindow.resetPassword();

			paswordWindow.setVisible(true);

			updateLog("Invalid Password");
		}

	}

	public void showChangePassword() {

		updateLog("Showing change password");

		new VPX_ChangePasswordWindow(VPX_ETHWindow.this).setVisible(true);

	}

	public void showChangePeriodicity() {

		updateLog("Showing change periodicity");

		new VPX_ChangePeriodicity(VPX_ETHWindow.this).setVisible(true);

	}

	public void showPrefrences() {

		updateLog("Showing Preference window");

		new VPX_Preference().showPreferenceWindow();

	}

	public void showConsole() {

		vpx_Content_Tabbed_Pane_Message.setSelectedIndex(1);
	}

	public void showLog() {

		vpx_Content_Tabbed_Pane_Message.setSelectedIndex(0);
	}

	public void showHelp() {
	}

	public void showAbout() {

		aboutDialog.setVisible(true);
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

		exitApplication();

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

	// Message Listener
	@Override
	public void updateMessage(String ip, String msg) {

		messagePanel.updateProcessorMessage(ip, msg);

	}

	@Override
	public void updateMessage(String ip, MSGCommand command) {
		messagePanel.updateProcessorMessage(ip, command);

	}

	@Override
	public void sendMessage(String ip, int core, String msg) {
		udpMonitor.sendMessageToProcessor(ip, core, msg);

	}

	@Override
	public void printConsoleMessage(String ip, String msg) {

		console.printConsoleMsg(ip, msg);

	}

	@Override
	public void printConsoleMessage(String ip, MSGCommand command) {

		console.printConsoleMsg(ip, command);

	}

	// Communication Listener
	@Override
	public void updateCommand(ATPCommand command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendCommand(ATPCommand command) {
		// TODO Auto-generated method stub

	}

	public void sendFile(String ip, String filename, VPX_FlashProgressWindow flashingWindow) {

		udpMonitor.sendFile(flashingWindow, filename, ip);

	}

	// Advertisement Listener
	@Override
	public void updateProcessorStatus(String ip, String msg) {

		vpx_Processor_Tree.updateProcessorResponse(ip, msg);

	}

	@Override
	public void updatePeriodicity(String ip, int periodicity) {

		udpMonitor.sendPeriodicity(ip, periodicity);

	}

	@Override
	public void updatePeriodicity(int periodicity) {

		udpMonitor.setPeriodicityByUnicast(periodicity);

	}
}

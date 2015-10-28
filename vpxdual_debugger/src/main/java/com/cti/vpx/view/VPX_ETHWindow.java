package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.command.MSGCommand;
import com.cti.vpx.controls.VPX_AboutWindow;
import com.cti.vpx.controls.VPX_AliasConfigWindow;
import com.cti.vpx.controls.VPX_AppModeWindow;
import com.cti.vpx.controls.VPX_BISTResultWindow;
import com.cti.vpx.controls.VPX_BootWindow;
import com.cti.vpx.controls.VPX_ChangePasswordWindow;
import com.cti.vpx.controls.VPX_ChangePeriodicityWindow;
import com.cti.vpx.controls.VPX_ConsolePanel;
import com.cti.vpx.controls.VPX_DetailWindow;
import com.cti.vpx.controls.VPX_EthernetFlashPanel;
import com.cti.vpx.controls.VPX_ExecutionPanel;
import com.cti.vpx.controls.VPX_FlashProgressWindow;
import com.cti.vpx.controls.VPX_FlashWizardWindow;
import com.cti.vpx.controls.VPX_LogFileViewPanel;
import com.cti.vpx.controls.VPX_LoggerPanel;
import com.cti.vpx.controls.VPX_MADPanel;
import com.cti.vpx.controls.VPX_MessagePanel;
import com.cti.vpx.controls.VPX_PasswordWindow;
import com.cti.vpx.controls.VPX_PreferenceWindow;
import com.cti.vpx.controls.VPX_ProcessorNode;
import com.cti.vpx.controls.VPX_ProcessorTree;
import com.cti.vpx.controls.VPX_StatusBar;
import com.cti.vpx.controls.VPX_SubnetFilterWindow;
import com.cti.vpx.controls.VPX_VLANConfig;
import com.cti.vpx.controls.graph.VPX_AmpWindow;
import com.cti.vpx.controls.graph.VPX_WaterfallWindow;
import com.cti.vpx.controls.hex.MemoryViewFilter;
import com.cti.vpx.controls.hex.VPX_MemoryBrowserWindow;
import com.cti.vpx.controls.hex.VPX_MemoryLoadProgressWindow;
import com.cti.vpx.controls.hex.VPX_MemoryPlotWindow;
import com.cti.vpx.controls.tab.VPX_TabbedPane;
import com.cti.vpx.listener.VPXAdvertisementListener;
import com.cti.vpx.listener.VPXCommunicationListener;
import com.cti.vpx.listener.VPXMessageListener;
import com.cti.vpx.listener.VPXUDPMonitor;
import com.cti.vpx.model.BIST;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;

public class VPX_ETHWindow extends JFrame
		implements WindowListener, VPXAdvertisementListener, VPXMessageListener, VPXCommunicationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2505823813174035752L;

	final JFileChooser fileDialog = new JFileChooser();

	final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Log Files", "log");

	private static final VPX_AboutWindow aboutDialog = new VPX_AboutWindow();

	private VPX_PasswordWindow paswordWindow = new VPX_PasswordWindow();

	private VPX_BISTResultWindow bistWindow = new VPX_BISTResultWindow();

	private VPX_BootWindow bootWindow = new VPX_BootWindow(this);

	private VPX_DetailWindow detail = new VPX_DetailWindow(VPX_ETHWindow.this);

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

	private JMenuItem vpx_Menu_Window_Boot;

	private JMenuItem vpx_Menu_Window_Execution;

	private JMenuItem vpx_Menu_Window_P2020Config;

	private JMenuItem vpx_Menu_Window_ChangeIP;

	// Tools Menu Items
	private JMenuItem vpx_Menu_Tools_ChangePWD;

	private JMenuItem vpx_Menu_Tools_ChangePeriod;

	private JMenuItem vpx_Menu_Tools_Prefrences;

	private JMenu vpx_Menu_Tools_ShowView;

	private JMenuItem vpx_Menu_Tools_ShowView_Console;

	private JMenuItem vpx_Menu_Tools_ShowView_Log;

	private JMenu vpx_Menu_Tools_ShowView_Mode;

	private JMenuItem vpx_Menu_Tools_ShowView_Mode_UART;

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

	private VPX_MemoryBrowserWindow[] memoryBrowserWindow;

	private VPX_WaterfallWindow[] waterfallWindow;

	private VPX_AmpWindow[] amplitudeWindow;

	private VPX_MemoryPlotWindow[] memoryPlotWindow;

	private JPanel baseTreePanel;

	private JToggleButton btnFilter;

	private VPX_CloseProgressWindow closeWindow;

	private JButton btnOpenLog;

	private JButton btnAliasConfig;

	private JButton btnDetail;

	private JButton btnMemoryBrowser;

	private JButton btnMemoryPlot;

	private JButton btnMAD;

	private JButton btnBIST;

	private JButton btnFlash;

	private JButton btnExecute;

	private static VPX_SubnetFilterWindow subnetFilter;

	public static int currentNoofMemoryView;

	public static int currentNoofMemoryPlot;

	public static int currentNoofWaterfall;

	public static int currentNoofAmplitude;

	/**
	 * Create the frame.
	 */
	public VPX_ETHWindow() throws Exception {

		VPXUtilities.setParent(this);

		rBundle = VPXUtilities.getResourceBundle();

		init();

		subnetFilter = new VPX_SubnetFilterWindow(VPX_ETHWindow.this);

		udpMonitor = new VPXUDPMonitor();

		udpMonitor.addUDPListener(this);

		udpMonitor.startMonitor();

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		enableSelectedProcessorMenus(VPXConstants.PROCESSOR_SELECTED_MODE_NONE);

		setVisible(true);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					promptAliasConfigFileName();

				} catch (Exception e) {
					VPXUtilities.updateError(e);
				}
			}
		});

	}

	private void init() {

		setTitle(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version"));

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setIconImage(VPXUtilities.getAppIcon());

		setBounds(0, 0, VPXUtilities.getScreenWidth(), VPXUtilities.getScreenHeight());

		loadComponents();

		updateLog(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version") + " Started");

		addWindowListener(this);

	}

	private void loadComponents() {

		createMenuBar();

		createToolBar();

		loadContentPane();

		loadStatusPane();

		createMemoryBrowsers();

		createMemoryPlots();

		createWaterfallWindows();

		createAmplitudeWindows();
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

	// Memory Window

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

	// Plot Window

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

	// Waterfall Window

	private void createWaterfallWindows() {

		waterfallWindow = new VPX_WaterfallWindow[VPXConstants.MAX_WATERFALL];

		for (int i = 0; i < VPXConstants.MAX_WATERFALL; i++) {

			waterfallWindow[i] = new VPX_WaterfallWindow(this, i);

		}

	}

	public void openWaterfall(String ip) {

		currentNoofWaterfall++;

		if (currentNoofWaterfall > VPXConstants.MAX_WATERFALL) {

			JOptionPane.showMessageDialog(this, "Maximum 3 waterfall graphs are allowed.You are exceeding the limit",
					"Maximum Reached", JOptionPane.ERROR_MESSAGE);

			currentNoofWaterfall = VPXConstants.MAX_WATERFALL;
		}

		for (int i = 0; i < VPXConstants.MAX_WATERFALL; i++) {

			if (waterfallWindow[i].getIP().equals(ip)) {

				currentNoofWaterfall--;

				break;
			}

			if (!waterfallWindow[i].isVisible()) {

				waterfallWindow[i].showWaterFall(ip);

				readWaterfall(ip);

				updateLog("Waterfall graph for " + ip + " opened ");

				break;
			}
		}

		if (currentNoofWaterfall == VPXConstants.MAX_WATERFALL) {

			vpx_Menu_Window_Waterfall.setEnabled(false);

		} else {

			vpx_Menu_Window_Waterfall.setEnabled(true);
		}

		vpx_Menu_Window_Waterfall.setText(rBundle.getString("Menu.Window.Waterfall") + " ( "
				+ (VPXConstants.MAX_WATERFALL - currentNoofWaterfall) + " ) ");

	}

	public void reindexWaterfallIndex() {

		currentNoofWaterfall--;

		if (currentNoofWaterfall == VPXConstants.MAX_WATERFALL) {

			vpx_Menu_Window_Waterfall.setEnabled(false);

		} else {

			vpx_Menu_Window_Waterfall.setEnabled(true);
		}

		vpx_Menu_Window_Waterfall.setText(rBundle.getString("Menu.Window.Waterfall") + " ( "
				+ (VPXConstants.MAX_WATERFALL - currentNoofWaterfall) + " ) ");
	}

	// Amplitude Window

	private void createAmplitudeWindows() {

		amplitudeWindow = new VPX_AmpWindow[VPXConstants.MAX_AMPLITUDE];

		for (int i = 0; i < VPXConstants.MAX_AMPLITUDE; i++) {

			amplitudeWindow[i] = new VPX_AmpWindow(this, i);

		}

	}

	public void openAmplitude(String ip) {

		currentNoofAmplitude++;

		if (currentNoofAmplitude > VPXConstants.MAX_AMPLITUDE) {

			JOptionPane.showMessageDialog(this, "Maximum 3 amplitude graphs are allowed.You are exceeding the limit",
					"Maximum Reached", JOptionPane.ERROR_MESSAGE);

			currentNoofAmplitude = VPXConstants.MAX_AMPLITUDE;
		}

		for (int i = 0; i < VPXConstants.MAX_AMPLITUDE; i++) {

			if (amplitudeWindow[i].getIP().equals(ip)) {

				currentNoofAmplitude--;

				break;
			}
			if (!amplitudeWindow[i].isVisible()) {

				amplitudeWindow[i].showAmplitude(ip);

				readAmplitude(ip);

				updateLog("Amplitude graph for " + ip + " opened ");

				break;
			}
		}

		if (currentNoofAmplitude == VPXConstants.MAX_AMPLITUDE) {

			vpx_Menu_Window_Amplitude.setEnabled(false);

		} else {

			vpx_Menu_Window_Amplitude.setEnabled(true);
		}

		vpx_Menu_Window_Amplitude.setText(rBundle.getString("Menu.Window.Amplitude") + " ( "
				+ (VPXConstants.MAX_AMPLITUDE - currentNoofAmplitude) + " ) ");

	}

	public void reindexAmplitudeIndex() {

		currentNoofAmplitude--;

		if (currentNoofAmplitude == VPXConstants.MAX_AMPLITUDE) {

			vpx_Menu_Window_Amplitude.setEnabled(false);

		} else {

			vpx_Menu_Window_Amplitude.setEnabled(true);
		}

		vpx_Menu_Window_Amplitude.setText(rBundle.getString("Menu.Window.Amplitude") + " ( "
				+ (VPXConstants.MAX_AMPLITUDE - currentNoofAmplitude) + " ) ");
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
		vpx_MenuBar = VPXComponentFactory.createJMenuBar();// new JMenuBar();

		// Creating Menus
		vpx_Menu_File = VPXComponentFactory.createJMenu(rBundle.getString("Menu.File"));

		vpx_Menu_Window = VPXComponentFactory.createJMenu(rBundle.getString("Menu.Window"));

		vpx_Menu_Tool = VPXComponentFactory.createJMenu(rBundle.getString("Menu.Tools"));

		vpx_Menu_Tools_ShowView = VPXComponentFactory.createJMenu(rBundle.getString("Menu.Tools.ShowView"));

		vpx_Menu_Help = VPXComponentFactory.createJMenu(rBundle.getString("Menu.Help"));

		// Creating MenuItems

		// File Menus
		vpx_Menu_File_AliasConfig = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Config"),
				VPXConstants.Icons.ICON_CONFIG);

		vpx_Menu_File_AliasConfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				showAliasConfig("");

			}
		});

		vpx_Menu_File_SubnetFilter = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Filter"),
				VPXConstants.Icons.ICON_FILTER);

		vpx_Menu_File_SubnetFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				btnFilter.doClick();

			}
		});

		vpx_Menu_File_OpenLog = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.OpenLog"),
				VPXConstants.Icons.ICON_OPEN);

		vpx_Menu_File_OpenLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				openLogFile();

			}
		});

		vpx_Menu_File_Refresh = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Refresh"),
				VPXConstants.Icons.ICON_REFRESH);

		vpx_Menu_File_Refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				refreshProcessorTree(false);

			}
		});

		vpx_Menu_File_Detail = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Detail"),
				VPXConstants.Icons.ICON_DETAIL);

		vpx_Menu_File_Detail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showDetail();

			}
		});

		vpx_Menu_File_Exit = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Exit"),
				VPXConstants.Icons.ICON_DELETE_EXIT);

		vpx_Menu_File_Exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				exitApplication();

			}
		});

		// Window Menus
		vpx_Menu_Window_MemoryBrowser = VPXComponentFactory

		.createJMenuItem(
				rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
						+ (VPXConstants.MAX_MEMORY_BROWSER - currentNoofMemoryView) + " ) ",
				VPXConstants.Icons.ICON_MEMORY);

		vpx_Menu_Window_MemoryBrowser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showMemoryBrowser();

			}
		});

		vpx_Menu_Window_MemoryPlot = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.MemoryPlot")
				+ " ( " + (VPXConstants.MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " ) ", VPXConstants.Icons.ICON_PLOT);

		vpx_Menu_Window_MemoryPlot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showMemoryPlot();

			}
		});

		vpx_Menu_Window_EthFlash = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.EthFlash"),
				VPXConstants.Icons.ICON_ETHFLASH);

		vpx_Menu_Window_EthFlash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showEthFlash();
			}
		});

		vpx_Menu_Window_Amplitude = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Amplitude")
				+ " ( " + (VPXConstants.MAX_AMPLITUDE - currentNoofAmplitude) + " ) ", VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Window_Amplitude.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showAmplitude(VPXSessionManager.getCurrentIP());

			}
		});
		vpx_Menu_Window_Waterfall = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Waterfall")
				+ " ( " + (VPXConstants.MAX_WATERFALL - currentNoofWaterfall) + " ) ", VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Window_Waterfall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showWaterfall(VPXSessionManager.getCurrentIP());

			}
		});

		vpx_Menu_Window_MAD = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.MAD"),
				VPXConstants.Icons.ICON_MAD);

		vpx_Menu_Window_MAD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showMAD();

			}
		});

		vpx_Menu_Window_FlashWizard = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Wizard"),
				VPXConstants.Icons.ICON_MAD_WIZARD);

		vpx_Menu_Window_FlashWizard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showFlashWizard();

			}
		});

		vpx_Menu_Window_BIST = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.BIST"),
				VPXConstants.Icons.ICON_BIST);

		vpx_Menu_Window_BIST.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showBIST();

			}
		});

		vpx_Menu_Window_Boot = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Reboot"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Window_Boot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showBootOption(VPXSessionManager.getCurrentIP());

			}
		});

		vpx_Menu_Window_Execution = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Execute"),
				VPXConstants.Icons.ICON_EXECUTION);

		vpx_Menu_Window_Execution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showExecution();

			}
		});

		vpx_Menu_Window_P2020Config = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.P2020Config"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Window_P2020Config.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showVLAN(0);

			}
		});

		vpx_Menu_Window_ChangeIP = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.ChangeIP"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Window_ChangeIP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showVLAN(1);

			}
		});
		// Tools Menus

		vpx_Menu_Tools_ChangePWD = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.ChangePWD"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Tools_ChangePWD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showChangePassword();

			}
		});

		vpx_Menu_Tools_ChangePeriod = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Periodicity"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Tools_ChangePeriod.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showChangePeriodicity();

			}
		});
		vpx_Menu_Tools_Prefrences = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Preferences"),
				VPXConstants.Icons.ICON_SETTINGS);

		vpx_Menu_Tools_Prefrences.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showPrefrences();
			}
		});

		vpx_Menu_Tools_ShowView_Console = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Console"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Tools_ShowView_Console.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showConsole();

			}
		});

		vpx_Menu_Tools_ShowView_Log = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Log"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Tools_ShowView_Log.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showLog();

			}
		});

		vpx_Menu_Tools_ShowView_Mode = VPXComponentFactory.createJMenu(rBundle.getString("Menu.Tools.ShowView.Mode"));

		vpx_Menu_Tools_ShowView_Mode_UART = VPXComponentFactory
				.createJMenuItem(rBundle.getString("Menu.Tools.ShowView.Mode.UART"), VPXConstants.Icons.ICON_EMPTY);

		vpx_Menu_Tools_ShowView_Mode_UART.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showAppMode();

			}
		});

		vpx_Menu_Tools_ShowView_Mode.add(vpx_Menu_Tools_ShowView_Mode_UART);

		// Help Menus
		vpx_Menu_Help_Help = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.Help"),
				VPXConstants.Icons.ICON_HELP);

		vpx_Menu_Help_Help.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showHelp();

			}
		});

		vpx_Menu_Help_About = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.About"),
				VPXConstants.Icons.ICON_ABOUT);

		vpx_Menu_Help_About.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showAbout();

			}
		});

		// Shorcuts

		// File Menu Items
		vpx_Menu_File_AliasConfig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		vpx_Menu_File_SubnetFilter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

		vpx_Menu_File_OpenLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));

		vpx_Menu_File_Detail.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));

		vpx_Menu_File_Refresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

		vpx_Menu_File_Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));

		vpx_Menu_Window_MAD.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));

		vpx_Menu_Window_FlashWizard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));

		// Tools Menu Items
		vpx_Menu_Tools_ChangePWD.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

		vpx_Menu_Tools_ChangePeriod.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));

		vpx_Menu_Tools_Prefrences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));

		vpx_Menu_Tools_ShowView_Console.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));

		vpx_Menu_Tools_ShowView_Log.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));

		// Help Menu Items
		vpx_Menu_Help_Help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

		vpx_Menu_Help_About.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK));

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

		vpx_Menu_File.add(VPXComponentFactory.createJSeparator());

		vpx_Menu_File.add(vpx_Menu_File_Refresh);

		vpx_Menu_File.add(vpx_Menu_File_Detail);

		vpx_Menu_File.add(VPXComponentFactory.createJSeparator());

		vpx_Menu_File.add(vpx_Menu_File_Exit);

		// Window Menu Items
		vpx_Menu_Window.add(vpx_Menu_Window_Execution);

		vpx_Menu_Window.add(vpx_Menu_Window_EthFlash);

		vpx_Menu_Window.add(vpx_Menu_Window_MAD);

		vpx_Menu_Window.add(vpx_Menu_Window_FlashWizard);

		vpx_Menu_Window.add(vpx_Menu_Window_BIST);

		vpx_Menu_Window.add(vpx_Menu_Window_Boot);

		vpx_Menu_Window.add(VPXComponentFactory.createJSeparator());

		vpx_Menu_Window.add(vpx_Menu_Window_MemoryBrowser);

		vpx_Menu_Window.add(vpx_Menu_Window_MemoryPlot);

		vpx_Menu_Window.add(vpx_Menu_Window_Amplitude);

		vpx_Menu_Window.add(vpx_Menu_Window_Waterfall);

		vpx_Menu_Window.add(VPXComponentFactory.createJSeparator());

		vpx_Menu_Window.add(vpx_Menu_Window_P2020Config);

		vpx_Menu_Window.add(vpx_Menu_Window_ChangeIP);

		// Tool Menu Items
		vpx_Menu_Tool.add(vpx_Menu_Tools_ChangePWD);

		vpx_Menu_Tool.add(vpx_Menu_Tools_ChangePeriod);

		vpx_Menu_Tool.add(VPXComponentFactory.createJSeparator());

		vpx_Menu_Tools_ShowView.add(vpx_Menu_Tools_ShowView_Console);

		vpx_Menu_Tools_ShowView.add(vpx_Menu_Tools_ShowView_Log);

		vpx_Menu_Tools_ShowView.add(VPXComponentFactory.createJSeparator());

		vpx_Menu_Tools_ShowView.add(vpx_Menu_Tools_ShowView_Mode);

		vpx_Menu_Tool.add(vpx_Menu_Tools_ShowView);

		vpx_Menu_Tool.add(VPXComponentFactory.createJSeparator());

		vpx_Menu_Tool.add(vpx_Menu_Tools_Prefrences);

		// Help Menu Items
		vpx_Menu_Help.add(vpx_Menu_Help_Help);

		vpx_Menu_Help.add(VPXComponentFactory.createJSeparator());

		vpx_Menu_Help.add(vpx_Menu_Help_About);

		setJMenuBar(vpx_MenuBar);
	}

	private void createToolBar() {

		vpx_ToolBar = VPXComponentFactory.createJToolBar();

		vpx_ToolBar.setFloatable(false);

		JButton btnAliasRefresh = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_REFRESH, null);

		btnAliasRefresh.setToolTipText("Reload processors tree");

		btnAliasRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				refreshProcessorTree(true);

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

		btnOpenLog = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_OPEN, null);

		btnOpenLog.setToolTipText("Open Log File");

		btnOpenLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_File_OpenLog.doClick();

			}
		});

		vpx_ToolBar.add(btnOpenLog);

		btnAliasConfig = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_CONFIG, null);

		btnAliasConfig.setToolTipText("Alias Configuration");

		btnAliasConfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_File_AliasConfig.doClick();

			}
		});

		vpx_ToolBar.add(btnAliasConfig);

		btnDetail = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_DETAIL, null);

		btnDetail.setToolTipText("VPX System Detail");

		btnDetail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_File_Detail.doClick();

			}
		});

		btnMemoryBrowser = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_MEMORY, null);

		btnMemoryBrowser.setToolTipText("Memory Browser");

		btnMemoryBrowser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vpx_Menu_Window_MemoryBrowser.doClick();

			}
		});

		btnMemoryPlot = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_PLOT, null);

		btnMemoryPlot.setToolTipText("Memory Plots");

		btnMemoryPlot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_Window_MemoryPlot.doClick();

			}
		});

		btnMAD = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_MAD, null);

		btnMAD.setToolTipText("MAD Utility");

		btnMAD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_Window_MAD.doClick();

			}
		});

		btnBIST = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_BIST, null);

		btnBIST.setToolTipText("Built In Self Test");

		btnBIST.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_Window_BIST.doClick();

			}
		});

		btnFlash = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_ETHFLASH, null);

		btnFlash.setToolTipText("Ethernet Flash");

		btnFlash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Menu_Window_EthFlash.doClick();

			}
		});

		btnExecute = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_EXECUTION, null);

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

		statusBar.setPreferredSize(new Dimension(10, 30));

		statusBar.setOpaque(true);

		getContentPane().add(statusBar, BorderLayout.SOUTH);
	}

	private JSplitPane getRightComponent() {

		vpx_Right_SplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		JPanel contentPanel = VPXComponentFactory.createJPanel();

		contentPanel.setLayout(new BorderLayout());

		messagePanel = new VPX_MessagePanel(this);

		messagePanel.setPreferredSize(new Dimension(450, 300));

		contentPanel.add(messagePanel, BorderLayout.EAST);

		vpx_Content_Tabbed_Pane_Right = new VPX_TabbedPane(true, true);

		contentPanel.add(vpx_Content_Tabbed_Pane_Right);

		vpx_Right_SplitPane.setLeftComponent(contentPanel);

		vpx_Content_Tabbed_Pane_Message = new JTabbedPane();

		logger = new VPX_LoggerPanel(this);

		console = new VPX_ConsolePanel(this);

		vpx_Content_Tabbed_Pane_Message.addTab("Log", logger);

		vpx_Content_Tabbed_Pane_Message.addTab("Console", console);

		vpx_Right_SplitPane.setRightComponent(vpx_Content_Tabbed_Pane_Message);

		vpx_Right_SplitPane.setDividerLocation(
				((int) VPXUtilities.getScreenHeight() / 2 + (int) VPXUtilities.getScreenHeight() / 7));

		vpx_Right_SplitPane.setDividerSize(2);

		return vpx_Right_SplitPane;
	}

	private void createSystemTree() {

		baseTreePanel = new JPanel();

		baseTreePanel.setLayout(new BorderLayout());

		createLegendPanel();

		vpx_Processor_Tree = VPXComponentFactory.createProcessorTree(this);

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

	public void updateProcessorSettings() {
		messagePanel.updateProcessorSettings();
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

	public void refreshProcessorTree(boolean isReload) {

		if (isReload) {
			vpx_Processor_Tree.refreshProcessorTree();
		} else {
			vpx_Processor_Tree.refreshStatus();
		}
	}

	public void openLogFile() {

		fileDialog.addChoosableFileFilter(filterOut);

		int returnVal = fileDialog.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File file = fileDialog.getSelectedFile();

			vpx_Content_Tabbed_Pane_Right.addTab("Log File - " + file.getAbsolutePath(),
					new VPX_LogFileViewPanel(file));

			vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

		}

	}

	public void openFile(String fileName) {

		File file = new File(fileName);

		vpx_Content_Tabbed_Pane_Right.addTab("Log File - " + file.getAbsolutePath(), new VPX_LogFileViewPanel(file));

		vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

	}

	public void showAliasConfig(String subsystem) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				new VPX_AliasConfigWindow(VPX_ETHWindow.this).showRenameAliasWindow(subsystem);

			}
		});

		th.start();

	}

	public void showAliasConfig(String p2IP, String d1IP, String d2IP) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				new VPX_AliasConfigWindow(VPX_ETHWindow.this).showAddAliasWindow(p2IP, d1IP, d2IP);

			}
		});

		th.start();

	}

	public void deleteSubSystem(String subsystem) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				new VPX_AliasConfigWindow(VPX_ETHWindow.this).deleteSelectedSubSystem(subsystem);

			}
		});

		th.start();

	}

	public void showDetail() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				detail.showDetailWindow();

			}
		});

		th.start();

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
					closeWindow = new VPX_CloseProgressWindow(VPX_ETHWindow.this,
							VPX_ETHWindow.this.vpx_Processor_Tree.getProcessorCount());

					closeWindow.setVisible(true);

					udpMonitor.close();

					VPX_ETHWindow.this.dispose();

					System.exit(0);

				}
			});
			th.start();
		}

	}

	public void showMemoryBrowser() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				openMemoryBrowser(null);

			}
		});

		th.start();

	}

	public void showMemoryPlot() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				openMemoryPlot();

			}
		});

		th.start();

	}

	public void showWaterfall(String ip) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				openWaterfall(ip);

			}
		});

		th.start();

	}

	public void showEthFlash() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				VPX_EthernetFlashPanel eth = new VPX_EthernetFlashPanel(VPX_ETHWindow.this);

				eth.setProcessor(VPXSessionManager.getCurrentProcessor());

				vpx_Content_Tabbed_Pane_Right.addTab("Ethernet Flash", new JScrollPane(eth));

				vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

				updateLog("Ethernet Flash opened");

			}
		});

		th.start();

	}

	public void showAmplitude(String ip) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				openAmplitude(ip);

			}
		});

		th.start();

		updateLog("Opening Amplitude Graph");
	}

	public void showMAD() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				int i = isAlreadyExist("Mad Utility");

				if (i == -1) {

					vpx_Content_Tabbed_Pane_Right.addTab("Mad Utility",
							new JScrollPane(new VPX_MADPanel(VPX_ETHWindow.this)));

					vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

					updateLog("MAD utility opened");

				} else {

					vpx_Content_Tabbed_Pane_Right.setSelectedIndex(i);

					updateLog("MAD utility already opened");
				}

			}
		});

		th.start();

	}

	public void showBootOption(String ip) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				bootWindow.showBootoption(ip);

				updateLog("Opening boot option");
			}
		});

		th.start();

	}

	public void showBIST() {

		updateLog("Starting Built in Self Test");

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				bistWindow.showBISTWindow();

				udpMonitor.startBist(VPXSessionManager.getCurrentProcessor(), VPXSessionManager.getCurrentSubSystem());

				updateLog("Built in Self Test Completed");

			}
		});

		th.start();

	}

	public void setReboot(String ip, int processor, int flashdevice, int page) {

		updateLog("P2020 " + ip + " is set to Reboot");

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				udpMonitor.sendBoot(ip, processor, flashdevice, page);

			}
		});

		th.start();

	}

	public void setInterrupt(String ip) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				udpMonitor.sendInterrupt(ip, PROCESSOR_LIST.PROCESSOR_DSP1);
			}
		});

		th.start();

	}

	public void showExecution() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				vpx_Content_Tabbed_Pane_Right.addTab("Execution", new JScrollPane(new VPX_ExecutionPanel()));

				vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

			}
		});

		th.start();

		updateLog("Opening Execution Window");

	}

	public void showFlashWizard() {

		updateLog("Opening flash wizard window");

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				VPX_FlashWizardWindow dialog = new VPX_FlashWizardWindow(VPX_ETHWindow.this);

				dialog.setVisible(true);

			}
		});

		th.start();

	}

	public void showVLAN(int tab) {

		paswordWindow.resetPassword();

		paswordWindow.setVisible(true);

		updateLog("Asking Super user password for VLAN Configuration");

		if (VPXUtilities.getCurrentPassword().equals(VPXUtilities.encodePassword(paswordWindow.getPasword()))) {

			paswordWindow.dispose();

			final VPX_VLANConfig browserCanvas = new VPX_VLANConfig();

			JPanel contentPane = new JPanel();

			contentPane.setLayout(new BorderLayout());

			contentPane.add(browserCanvas, BorderLayout.CENTER);

			JDialog frame = new JDialog(this, "VLAN Configuration");

			frame.setBounds(100, 100, (int) (VPXUtilities.getScreenWidth() * .80),
					(int) (VPXUtilities.getScreenHeight() * .70));

			frame.setLocationRelativeTo(VPX_ETHWindow.this);

			frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			frame.setContentPane(contentPane);

			frame.setResizable(true);

			frame.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {

					browserCanvas.dispose();
				}
			});

			frame.setVisible(true);

			if (browserCanvas.initialise()) {

				if (tab == 1) {

					browserCanvas.setUrl("http://192.168.10.1/cgi-bin/portsetting.cgi");

				} else {

					browserCanvas.setUrl("http://192.168.10.1/cgi-bin/ipsetting.cgi");
				}

			}
			frame.setBounds(100, 100, (int) (VPXUtilities.getScreenWidth() * .80) + 1,
					(int) (VPXUtilities.getScreenHeight() * .70) + 1);

			updateLog("VLAN Configuration opened");

		} else {

			JOptionPane.showMessageDialog(this, "Pasword invalid", "Authentication", JOptionPane.ERROR_MESSAGE);

			paswordWindow.dispose();

			showVLAN(tab);

			updateLog("Invalid Password");
		}

	}

	public void showChangePassword() {

		updateLog("Opening change password");

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				new VPX_ChangePasswordWindow(VPX_ETHWindow.this).setVisible(true);

			}
		});

		th.start();

	}

	public void showChangePeriodicity() {

		updateLog("Opening change periodicity");

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				new VPX_ChangePeriodicityWindow(VPX_ETHWindow.this).setVisible(true);

			}
		});

		th.start();

	}

	public void showPrefrences() {

		updateLog("Opening Preference window");

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				new VPX_PreferenceWindow().showPreferenceWindow();
			}
		});

		th.start();

	}

	public void showConsole() {

		vpx_Content_Tabbed_Pane_Message.setSelectedIndex(1);
	}

	public void showLog() {

		vpx_Content_Tabbed_Pane_Message.setSelectedIndex(0);
	}

	public void showAppMode() {

		this.dispose();

		VPX_AppModeWindow window = new VPX_AppModeWindow(VPXUtilities.getEthernetPorts(),
				VPXUtilities.getSerialPorts());

		window.showWindow();

	}

	public void showHelp() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				messagePanel.showHelp();

			}
		});

		th.start();

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

	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {

	}

	@Override
	public void windowClosing(WindowEvent arg0) {

		exitApplication();

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {

	}

	@Override
	public void windowIconified(WindowEvent arg0) {

	}

	@Override
	public void windowOpened(WindowEvent arg0) {

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
	public void readMemory(MemoryViewFilter filter) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				udpMonitor.readMemory(filter);

			}
		});

		th.start();

	}

	public void setMemoryValue(String ip, int core, long fromAddress, int memindex, int typeSize, int length,
			long newValue) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				udpMonitor.setMemory(ip, core, fromAddress, memindex, typeSize, length, newValue);

			}
		});

		th.start();
	}

	@Override
	public void readPlot(MemoryViewFilter filter) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				udpMonitor.readPlot(filter);

			}
		});

		th.start();

	}

	@Override
	public void readPlot(MemoryViewFilter filter1, MemoryViewFilter filter2) {

		udpMonitor.readPlot(filter1, filter2);

	}

	@Override
	public void populatePlot(int plotID, int lineID, long startAddress, byte[] buffer) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					if (memoryPlotWindow[plotID].isVisible()) {

						memoryPlotWindow[plotID].populateValues(lineID, buffer);

						//memoryPlotWindow[plotID].setVisible(true);
					}

				} catch (Exception e) {
					e.printStackTrace();
					VPXUtilities.updateError(e);
				}

			}
		});

		th.start();

	}

	@Override
	public void populateMemory(int memID, long startAddress, byte[] buffer) {
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					if (memoryBrowserWindow[memID].isVisible()) {

						memoryBrowserWindow[memID].setBytes(startAddress, buffer);

						//memoryBrowserWindow[memID].setVisible(true);
					}

				} catch (Exception e) {
					e.printStackTrace();
					VPXUtilities.updateError(e);
				}

			}
		});

		th.start();

	}

	@Override
	public void readWaterfall(String ip) {

		udpMonitor.readWaterfallData(ip);

	}

	@Override
	public void populateWaterfall(String ip, byte[] bytes) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					int idx = -1;

					for (int i = 0; i < waterfallWindow.length; i++) {

						if (waterfallWindow[i].getIP().equals(ip)) {

							idx = i;

							break;
						}
					}
					if (idx >= 0) {

						if (waterfallWindow[idx].isVisible()) {

							waterfallWindow[idx].loadData(bytes);

						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					VPXUtilities.updateError(e);
				}

			}
		});

		th.start();

	}

	@Override
	public void sendWaterfallInterrupt(String ip) {

		udpMonitor.setWaterfallInterrupted(ip);

	}

	@Override
	public void readAmplitude(String ip) {

		udpMonitor.readAmplitudeData(ip);

	}

	@Override
	public void populateAmplitude(String ip, float[] xAxis, float[] yAxis) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					int idx = -1;

					for (int i = 0; i < amplitudeWindow.length; i++) {

						if (amplitudeWindow[i].getIP().equals(ip)) {

							idx = i;

							break;
						}
					}

					if (idx >= 0) {
						if (amplitudeWindow[idx].isVisible()) {

							amplitudeWindow[idx].loadData(xAxis, yAxis);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					VPXUtilities.updateError(e);
				}

			}
		});

		th.start();

	}

	@Override
	public void sendAmplitudeInterrupt(String ip) {

		udpMonitor.setAmplitudeInterrupted(ip);

	}

	@Override
	public void updateBIST(BIST bist) {

		if (bistWindow.isVisible()) {

			bistWindow.setResult(bist);
		}
	}

	@Override
	public void updateExit(int val) {

		closeWindow.updateExitProgress(val);

	}

	@Override
	public void updateTestProgress(PROCESSOR_LIST pType, int val) {

		if (bistWindow.isVisible()) {

			bistWindow.updateTestProgress(pType, val);

		}
	}

	public void sendFile(String ip, String filename, VPX_FlashProgressWindow flashingWindow, int flashdevice,
			int location) {

		udpMonitor.sendFile(flashingWindow, filename, ip, flashdevice, location);

	}

	public void sendMemoryFile(String ip, String filename, long startAddress,
			VPX_MemoryLoadProgressWindow flashingWindow) {

		udpMonitor.sendMemoryFile(flashingWindow, filename, startAddress, ip);

	}

	// Advertisement Listener
	@Override
	public void updateProcessorStatus(String ip, String msg) {

		vpx_Processor_Tree.updateProcessorResponse(ip, msg);

		updateStatus("Advertisement received from " + ip);

	}

	@Override
	public void updatePeriodicity(String ip, int periodicity) {

		udpMonitor.sendPeriodicity(ip, periodicity);

	}

	@Override
	public void updatePeriodicity(int periodicity) {

		udpMonitor.setPeriodicityByUnicast(periodicity);

	}

	public class VPX_CloseProgressWindow extends JDialog {
		

		/**
		 * 
		 */
		private static final long serialVersionUID = -7250292684798353091L;

		private JProgressBar progressFileSent;

		private JFrame parent;

		private int maxVal;

		/**
		 * Create the dialog.
		 */
		public VPX_CloseProgressWindow(JFrame prnt, int max) {

			super(prnt);

			this.maxVal = max;

			this.parent = prnt;

			init();

			loadComponents();

		}

		private void init() {

			setTitle("Closing Application");

			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

			setSize(500, 150);

			setLocationRelativeTo(parent);

			getContentPane().setLayout(new BorderLayout(0, 0));

		}

		private void loadComponents() {

			JPanel progressPanel = new JPanel();

			progressPanel.setPreferredSize(new Dimension(10, 35));

			getContentPane().add(progressPanel, BorderLayout.SOUTH);

			progressPanel.setLayout(new BorderLayout(0, 0));

			progressFileSent = new JProgressBar();

			progressFileSent.setStringPainted(true);

			progressFileSent.setMaximum(maxVal);

			progressFileSent.setMinimum(0);

			progressPanel.add(progressFileSent, BorderLayout.SOUTH);

			JPanel detailPanel = new JPanel();

			detailPanel.setBorder(null);

			detailPanel.setPreferredSize(new Dimension(25, 10));

			getContentPane().add(detailPanel, BorderLayout.CENTER);

			detailPanel.setLayout(new BorderLayout(0, 0));

			JLabel lblExitingApplication = new JLabel("   Exiting Application....");

			lblExitingApplication.setPreferredSize(new Dimension(30, 0));

			detailPanel.add(lblExitingApplication);
		}

		public void updateExitProgress(int value) {

			if (value == -1) {

				VPX_CloseProgressWindow.this.dispose();

			} else {

				progressFileSent.setValue(value);
			}
		}

	}

	public void enableSelectedProcessorMenus(int option, VPX_ProcessorNode node) {

		enableSelectedProcessorMenus(option);

		if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_DSP1
				|| node.getNodeType() == PROCESSOR_LIST.PROCESSOR_DSP2) {

			vpx_Menu_Window_Amplitude.setEnabled(node.isAmplitude());

			vpx_Menu_Window_Waterfall.setEnabled(node.isWaterfall());
		}

	}

	public void enableSelectedProcessorMenus(int option) {

		switch (option) {

		case VPXConstants.PROCESSOR_SELECTED_MODE_NONE:
		default:

			vpx_Menu_Window_MemoryBrowser.setEnabled(false);

			vpx_Menu_Window_MemoryPlot.setEnabled(false);

			vpx_Menu_Window_EthFlash.setEnabled(false);

			vpx_Menu_Window_Execution.setEnabled(false);

			vpx_Menu_Window_Amplitude.setEnabled(false);

			vpx_Menu_Window_Waterfall.setEnabled(false);

			vpx_Menu_Window_BIST.setEnabled(false);

			vpx_Menu_Window_Boot.setEnabled(false);

			vpx_Menu_Window_P2020Config.setEnabled(false);

			vpx_Menu_Window_ChangeIP.setEnabled(false);

			btnMemoryBrowser.setEnabled(false);

			btnMemoryPlot.setEnabled(false);

			btnMAD.setEnabled(false);

			btnBIST.setEnabled(false);

			btnFlash.setEnabled(false);

			btnExecute.setEnabled(false);

			break;

		case VPXConstants.PROCESSOR_SELECTED_MODE_DSP:

			vpx_Menu_Window_MemoryBrowser.setEnabled(true);

			vpx_Menu_Window_MemoryPlot.setEnabled(true);

			vpx_Menu_Window_EthFlash.setEnabled(true);

			vpx_Menu_Window_Execution.setEnabled(true);

			vpx_Menu_Window_Amplitude.setEnabled(true);

			vpx_Menu_Window_Waterfall.setEnabled(true);

			vpx_Menu_Window_BIST.setEnabled(false);

			vpx_Menu_Window_Boot.setEnabled(false);

			vpx_Menu_Window_P2020Config.setEnabled(false);

			vpx_Menu_Window_ChangeIP.setEnabled(false);

			btnMemoryBrowser.setEnabled(true);

			btnMemoryPlot.setEnabled(true);

			btnMAD.setEnabled(true);

			btnBIST.setEnabled(false);

			btnFlash.setEnabled(true);

			btnExecute.setEnabled(true);

			break;
		case VPXConstants.PROCESSOR_SELECTED_MODE_P2020:

			vpx_Menu_Window_MemoryBrowser.setEnabled(false);

			vpx_Menu_Window_MemoryPlot.setEnabled(false);

			vpx_Menu_Window_EthFlash.setEnabled(false);

			vpx_Menu_Window_Execution.setEnabled(false);

			vpx_Menu_Window_Amplitude.setEnabled(false);

			vpx_Menu_Window_Waterfall.setEnabled(false);

			vpx_Menu_Window_BIST.setEnabled(true);

			vpx_Menu_Window_Boot.setEnabled(true);

			vpx_Menu_Window_P2020Config.setEnabled(true);

			vpx_Menu_Window_ChangeIP.setEnabled(true);

			btnMemoryBrowser.setEnabled(false);

			btnMemoryPlot.setEnabled(false);

			btnMAD.setEnabled(true);

			btnBIST.setEnabled(true);

			btnFlash.setEnabled(false);

			btnExecute.setEnabled(false);

			break;

		case VPXConstants.PROCESSOR_SELECTED_MODE_SUBSYSTEM:

			vpx_Menu_Window_MemoryBrowser.setEnabled(false);

			vpx_Menu_Window_MemoryPlot.setEnabled(false);

			vpx_Menu_Window_EthFlash.setEnabled(false);

			vpx_Menu_Window_Execution.setEnabled(false);

			vpx_Menu_Window_Amplitude.setEnabled(false);

			vpx_Menu_Window_Waterfall.setEnabled(false);

			vpx_Menu_Window_BIST.setEnabled(false);

			vpx_Menu_Window_Boot.setEnabled(false);

			vpx_Menu_Window_P2020Config.setEnabled(false);

			vpx_Menu_Window_ChangeIP.setEnabled(false);

			btnMemoryBrowser.setEnabled(false);

			btnMemoryPlot.setEnabled(false);

			btnMAD.setEnabled(false);

			btnBIST.setEnabled(false);

			btnFlash.setEnabled(false);

			btnExecute.setEnabled(false);

			break;

		}
	}

}

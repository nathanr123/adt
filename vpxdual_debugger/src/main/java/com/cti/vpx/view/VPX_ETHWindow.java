package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
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
import com.cti.vpx.controls.VPX_ExecutionFileTransferingWindow;
import com.cti.vpx.controls.VPX_ExecutionPanel;
import com.cti.vpx.controls.VPX_FlashProgressWindow;
import com.cti.vpx.controls.VPX_FlashWizardWindow;
import com.cti.vpx.controls.VPX_LogFileViewPanel;
import com.cti.vpx.controls.VPX_LoggerPanel;
import com.cti.vpx.controls.VPX_MADPanel;
import com.cti.vpx.controls.VPX_MenuBar;
import com.cti.vpx.controls.VPX_MessagePanel;
import com.cti.vpx.controls.VPX_NetworkMonitorPanel;
import com.cti.vpx.controls.VPX_PasswordWindow;
import com.cti.vpx.controls.VPX_PreferenceWindow;
import com.cti.vpx.controls.VPX_ProcessorNode;
import com.cti.vpx.controls.VPX_ProcessorTree;
import com.cti.vpx.controls.VPX_StatusBar;
import com.cti.vpx.controls.VPX_SubnetFilterWindow;
import com.cti.vpx.controls.VPX_VLANConfig;
import com.cti.vpx.controls.graph.VPX_SpectrumWindow;
import com.cti.vpx.controls.hex.MemoryViewFilter;
import com.cti.vpx.controls.hex.VPX_MemoryBrowserWindow;
import com.cti.vpx.controls.hex.VPX_MemoryLoadProgressWindow;
import com.cti.vpx.controls.hex.VPX_MemoryPlotWindow;
import com.cti.vpx.controls.tab.VPX_TabbedPane;
import com.cti.vpx.listener.VPXAdvertisementListener;
import com.cti.vpx.listener.VPXCommunicationListener;
import com.cti.vpx.listener.VPXMessageListener;
import com.cti.vpx.listener.VPXTFTPMonitor;
import com.cti.vpx.listener.VPXUDPMonitor;
import com.cti.vpx.model.BIST;
import com.cti.vpx.model.ExecutionHexArray;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXNetworkLogger;
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
	
	private JToolBar vpx_ToolBar;
	
	private JSplitPane vpx_SplitPane;
	
	private JSplitPane vpx_Right_SplitPane;
	
	private VPX_TabbedPane vpx_Content_Tabbed_Pane_Right;
	
	private VPX_LoggerPanel logger;
	
	private VPX_ConsolePanel console;
	
	private VPX_NetworkMonitorPanel nwMonitor;
	
	private VPX_ProcessorTree vpx_Processor_Tree;
	
	private JTabbedPane vpx_Content_Tabbed_Pane_Message;
	
	private VPX_StatusBar statusBar;
	
	private VPX_MessagePanel messagePanel;
	
	private VPXUDPMonitor udpMonitor;
	
	private VPXTFTPMonitor tftpMonitor = null;
	
	private VPX_MemoryBrowserWindow[] memoryBrowserWindow;
	
	private VPX_SpectrumWindow[] spectrumWindow;
	
	private VPX_MemoryPlotWindow[] memoryPlotWindow;
	
	private JPanel baseTreePanel;
	
	private JToggleButton btnFilter;
	
	private VPX_CloseProgressWindow closeWindow;
	
	private JButton btnOpenLog;
	
	private JButton btnAliasConfig;
	
	private JButton btnDetail;
	
	private JButton btnMemoryBrowser;
	
	private JButton btnMemoryPlot;
	
	private JButton btnMemorySpectrum;
	
	private JButton btnMAD;
	
	private JButton btnBIST;
	
	private JButton btnFlash;
	
	private JButton btnExecute;
	
	private VPX_MenuBar vpx_MenuBar;
	
	private static VPX_SubnetFilterWindow subnetFilter;
	
	public static int currentNoofMemoryView;
	
	public static int currentNoofMemoryPlot;
	
	public static int currentNoofSpectrum;
	
	/**
	 * Create the frame.
	 */
	public VPX_ETHWindow() {
		
		VPXUtilities.setParent(this);
		
		rBundle = VPXUtilities.getResourceBundle();
		
		init();
		
		subnetFilter = new VPX_SubnetFilterWindow(VPX_ETHWindow.this);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		enableSelectedProcessorMenus(VPXConstants.PROCESSOR_SELECTED_MODE_NONE);
		
		udpMonitor = new VPXUDPMonitor(VPX_ETHWindow.this, VPXUtilities.getAllPorts());
		
		udpMonitor.startMonitor();
		
		setVisible(true);
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					promptAliasConfigFileName();
					
				}
				catch (Exception e) {
					VPXLogger.updateError(e);
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
		
		VPXLogger.updateLog(
				rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version") + " Started");
		
		addWindowListener(this);
		
	}
	
	private void loadComponents() {
		
		createMenuBar();
		
		createToolBar();
		
		loadContentPane();
		
		loadStatusPane();
		
		createMemoryBrowsers();
		
		createMemoryPlots();
		
		createSpectrumWindow();
	}
	
	public void applyMask(String mask) {
		
		udpMonitor.applyFilterbySubnet(mask);
	}
	
	// Memory Window
	
	public void createMemoryBrowsers() {
		
		memoryBrowserWindow = new VPX_MemoryBrowserWindow[VPXConstants.MAX_MEMORY_BROWSER];
		
		for (int i = 0; i < VPXConstants.MAX_MEMORY_BROWSER; i++) {
			
			memoryBrowserWindow[i] = new VPX_MemoryBrowserWindow(i);
			
			memoryBrowserWindow[i].setParent(this);
		}
		
		btnMemoryBrowser.setToolTipText(
				"remain " + (VPXConstants.MAX_MEMORY_BROWSER - currentNoofMemoryView) + " memory browsers");
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
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_BROWSER, false);
			
			btnMemoryBrowser.setEnabled(false);
			
		} else {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_BROWSER, true);
			
			btnMemoryBrowser.setEnabled(true);
		}
		
		vpx_MenuBar.setText(VPX_MenuBar.MEMORY_BROWSER, rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (VPXConstants.MAX_MEMORY_BROWSER - currentNoofMemoryView) + " ) ");
		
		btnMemoryBrowser.setToolTipText(
				"remain " + (VPXConstants.MAX_MEMORY_BROWSER - currentNoofMemoryView) + " memory browsers");
		
		VPXLogger.updateLog("Memory Browser opened");
	}
	
	// Plot Window
	
	public void createMemoryPlots() {
		
		memoryPlotWindow = new VPX_MemoryPlotWindow[VPXConstants.MAX_MEMORY_PLOT];
		
		for (int i = 0; i < VPXConstants.MAX_MEMORY_PLOT; i++) {
			
			memoryPlotWindow[i] = new VPX_MemoryPlotWindow(i);
			
			memoryPlotWindow[i].setParent(this);
		}
		
		btnMemoryPlot
				.setToolTipText("remain " + (VPXConstants.MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " memory plots");
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
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_PLOT, false);
			
			btnMemoryPlot.setEnabled(false);
			
		} else {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_PLOT, true);
			
			btnMemoryPlot.setEnabled(true);
		}
		
		vpx_MenuBar.setText(VPX_MenuBar.MEMORY_PLOT, rBundle.getString("Menu.Window.MemoryPlot") + " ( "
				+ (VPXConstants.MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " ) ");
		
		btnMemoryPlot
				.setToolTipText("remain " + (VPXConstants.MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " memory plots");
		
		VPXLogger.updateLog("Memory Plot opened");
	}
	
	// Spectrum Window
	
	public void createSpectrumWindow() {
		
		spectrumWindow = new VPX_SpectrumWindow[VPXConstants.MAX_SPECTRUM];
		
		for (int i = 0; i < VPXConstants.MAX_SPECTRUM; i++) {
			
			spectrumWindow[i] = new VPX_SpectrumWindow(this, i);
		}
		
		btnMemorySpectrum
				.setToolTipText("remain " + (VPXConstants.MAX_SPECTRUM - currentNoofSpectrum) + " data analyser");
	}
	
	public void openSpectrumWindow() {
		
		currentNoofSpectrum++;
		
		if (currentNoofSpectrum > VPXConstants.MAX_SPECTRUM) {
			
			JOptionPane.showMessageDialog(this, "Maximum 6 data analysis are allowed.You are exceeding the limit",
					"Maximum Reached", JOptionPane.ERROR_MESSAGE);
			
			currentNoofSpectrum = VPXConstants.MAX_SPECTRUM;
		}
		
		for (int i = 0; i < VPXConstants.MAX_SPECTRUM; i++) {
			
			if (!spectrumWindow[i].isVisible()) {
				
				spectrumWindow[i].showSpectrumWindow();
				
				break;
			}
		}
		
		if (currentNoofSpectrum == VPXConstants.MAX_SPECTRUM) {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_SPECTRUM, false);
			
			btnMemorySpectrum.setEnabled(false);
			
		} else {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_SPECTRUM, true);
			
			btnMemorySpectrum.setEnabled(true);
		}
		
		vpx_MenuBar.setText(VPX_MenuBar.MEMORY_SPECTRUM, rBundle.getString("Menu.Window.Spectrum") + " ( "
				+ (VPXConstants.MAX_SPECTRUM - currentNoofSpectrum) + " ) ");
		
		btnMemorySpectrum
				.setToolTipText("remain " + (VPXConstants.MAX_SPECTRUM - currentNoofSpectrum) + " data analyser");
		
		VPXLogger.updateLog("Data analyser opened");
	}
	
	public boolean isAlreadyAvailable(String ip, int core) {
		
		boolean ret = false;
		
		for (int j = 0; j < spectrumWindow.length; j++) {
			
			if (spectrumWindow[j].getCurrentProcIP().equals(ip) &&
					
					spectrumWindow[j].getCurrentCore() == core) {
				
				ret = true;
				
				break;
				
			}
			
		}
		
		return ret;
		
	}
	
	public void updateConsoleFilters() {
		
		console.reloadSubsystems();
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
		
		vpx_MenuBar = new VPX_MenuBar(VPX_ETHWindow.this, rBundle);
		
		setJMenuBar(vpx_MenuBar);
	}
	
	private void createToolBar() {
		
		vpx_ToolBar = VPXComponentFactory.createJToolBar();
		
		vpx_ToolBar.setFloatable(false);
		
		JButton btnAliasRefresh = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_REFRESH, null);
		
		btnAliasRefresh.setToolTipText("Reload processors");
		
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
				
				openLogFile();
				
			}
		});
		
		vpx_ToolBar.add(btnOpenLog);
		
		btnAliasConfig = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_CONFIG, null);
		
		btnAliasConfig.setToolTipText("Alias Configuration");
		
		btnAliasConfig.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showAliasConfig("");
				
			}
		});
		
		vpx_ToolBar.add(btnAliasConfig);
		
		btnDetail = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_DETAIL, null);
		
		btnDetail.setToolTipText("VPX System Detail");
		
		btnDetail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showDetail();
				
			}
		});
		
		btnMemoryBrowser = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_MEMORY, null);
		
		btnMemoryBrowser.setToolTipText("Memory Browser");
		
		btnMemoryBrowser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showMemoryBrowser();
				
			}
		});
		
		btnMemoryPlot = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_PLOT, null);
		
		btnMemoryPlot.setToolTipText("Memory Plots");
		
		btnMemoryPlot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showMemoryPlot();
				
			}
		});
		
		btnMemorySpectrum = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_SPECTRUM, null);
		
		btnMemorySpectrum.setToolTipText("Data Analyser");
		
		btnMemorySpectrum.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showDataAnalyzer(VPXSessionManager.getCurrentIP());
				
			}
		});
		
		btnMAD = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_MAD, null);
		
		btnMAD.setToolTipText("MAD Utility");
		
		btnMAD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showMAD();
				
			}
		});
		
		btnBIST = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_BIST, null);
		
		btnBIST.setToolTipText("Built In Self Test");
		
		btnBIST.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showBIST();
				
			}
		});
		
		btnFlash = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_ETHFLASH, null);
		
		btnFlash.setToolTipText("Ethernet Flash");
		
		btnFlash.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showEthFlash();
				
			}
		});
		
		btnExecute = VPXComponentFactory.createJButton("", VPXConstants.Icons.ICON_EXECUTION, null);
		
		btnExecute.setToolTipText("Execution");
		
		btnExecute.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showExecution();
				
			}
		});
		
		// Adding to Toolbar
		
		vpx_ToolBar.add(btnFilter);
		
		vpx_ToolBar.add(btnDetail);
		
		vpx_ToolBar.add(btnOpenLog);
		
		vpx_ToolBar.addSeparator();
		
		vpx_ToolBar.add(btnMemoryBrowser);
		
		vpx_ToolBar.add(btnMemoryPlot);
		
		vpx_ToolBar.add(btnMemorySpectrum);
		
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
		
		vpx_SplitPane.setRightComponent(getRightComponents());
		
		vpx_SplitPane.setDividerLocation((int) VPXUtilities.getScreenWidth() / 6);
		
		vpx_SplitPane.setDividerSize(2);
		
		getContentPane().add(vpx_SplitPane, BorderLayout.CENTER);
	}
	
	private void loadStatusPane() {
		
		statusBar = new VPX_StatusBar();
		
		VPXLogger.setStatusBar(statusBar);
		
		statusBar.setPreferredSize(new Dimension(10, 30));
		
		statusBar.setOpaque(true);
		
		getContentPane().add(statusBar, BorderLayout.SOUTH);
	}
	
	private JPanel getRightComponents() {
		
		JPanel rightPanel = new JPanel();
		
		rightPanel.setLayout(new BorderLayout());
		
		JSplitPane contentSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		contentSplitPane.setDividerLocation(
				((int) (VPXUtilities.getScreenWidth() / 2.5) + (int) VPXUtilities.getScreenWidth() / 5));
		
		contentSplitPane.setDividerSize(2);
		
		rightPanel.add(contentSplitPane, BorderLayout.CENTER);
		
		// Loading left component
		
		vpx_Right_SplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		vpx_Content_Tabbed_Pane_Right = new VPX_TabbedPane(true, true);
		
		vpx_Right_SplitPane.setLeftComponent(vpx_Content_Tabbed_Pane_Right);
		
		vpx_Content_Tabbed_Pane_Message = new JTabbedPane();
		
		logger = new VPX_LoggerPanel(this);
		
		VPXLogger.setLoggerPanel(logger);
		
		console = new VPX_ConsolePanel(this);
		
		vpx_Content_Tabbed_Pane_Message.addTab("Log", logger);
		
		vpx_Content_Tabbed_Pane_Message.addTab("Console", console);
		
		if (Boolean.valueOf(VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.NETWORK_PKT_SNIFFER))) {
			
			nwMonitor = new VPX_NetworkMonitorPanel();
			
			VPXNetworkLogger.setNwMonitor(nwMonitor);
			
			vpx_Content_Tabbed_Pane_Message.addTab("Network", nwMonitor);
		}
		
		vpx_Right_SplitPane.setRightComponent(vpx_Content_Tabbed_Pane_Message);
		
		vpx_Right_SplitPane.setDividerLocation(
				((int) (VPXUtilities.getScreenHeight() / 2.5) + (int) VPXUtilities.getScreenHeight() / 7));
		
		vpx_Right_SplitPane.setDividerSize(2);
		
		contentSplitPane.setLeftComponent(vpx_Right_SplitPane);
		
		// Loading right component
		messagePanel = new VPX_MessagePanel(this,
				Boolean.valueOf(VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.MESSAGE_COMMAND)));
		
		contentSplitPane.setRightComponent(messagePanel);
		
		return rightPanel;
		
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
		
		JLabel lblNewLabel_2 = new JLabel(" ");
		
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel_2.setSize(new Dimension(32, 24));
		
		lblNewLabel_2.setBounds(22, 12, 46, 14);
		
		treeLegendPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("A");
		
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel_3.setSize(new Dimension(32, 24));
		
		lblNewLabel_3.setBounds(22, 12, 46, 32);
		
		treeLegendPanel.add(lblNewLabel_3);
		
		JLabel lblAmplitudeGraph = new JLabel("<html>Amplitude /<br>Waterfall Graph</html>");
		
		lblAmplitudeGraph.setBounds(93, 12, 148, 32);
		
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
		
		statusBar.updateProcessor();
	}
	
	public void unToggleFilter() {
		
		btnFilter.setSelected(false);
	}
	
	public void updateProcessorTree() {
		
		vpx_Processor_Tree.updateVPXSystemTree();
		
		console.reloadFilters();
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
	
	private boolean isConfigRemind() {
		
		try {
			
			return Boolean.valueOf(VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.REMIND_ALIAS_CONFIG));
			
		}
		catch (Exception e) {
			return false;
		}
	}
	
	private void promptAliasConfigFileName() {
		
		if (!vpx_Processor_Tree.isSubSystemsAvailable() && isConfigRemind()) {
			
			String[] buttons = { "Yes", "No", "Don't ask me again" };
			
			int option = JOptionPane.showOptionDialog(null, "Alias config file not found.\nDo you want to config now?",
					"Confirmation", JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[2]);
			
			if (option == 0) {
				
				new VPX_AliasConfigWindow(this).setVisible(true);
				
				VPXUtilities.updateProperties(VPXConstants.ResourceFields.REMIND_ALIAS_CONFIG, "true");
				
			} else
				if (option == 1) {
					
					VPXUtilities.updateProperties(VPXConstants.ResourceFields.REMIND_ALIAS_CONFIG, "true");
					
				} else
					if (option == 2) {
						
						VPXUtilities.updateProperties(VPXConstants.ResourceFields.REMIND_ALIAS_CONFIG, "false");
						
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
		
		fileDialog.setMultiSelectionEnabled(true);
		
		int returnVal = fileDialog.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			
			File[] file = fileDialog.getSelectedFiles();
			
			for (int i = 0; i < file.length; i++) {
				
				vpx_Content_Tabbed_Pane_Right.addTab(file[i].getName(), new VPX_LogFileViewPanel(file[i]));
				
				vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);
				
			}
		}
		
		fileDialog.setMultiSelectionEnabled(false);
		
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
	
	public void toggleFilter() {
		
		btnFilter.doClick();
	}
	
	public void exitApplication() {
		
		String ObjButtons[] = { "Yes", "No" };
		
		int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?",
				rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version"),
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ObjButtons, ObjButtons[1]);
		
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
				
				// openWaterfall(ip);
				
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
				
				VPXLogger.updateLog("Ethernet Flash opened for " + VPXSessionManager.getCurrentProcessor() + "("
						+ VPXSessionManager.getCurrentProcType() + ")");
				
			}
		});
		
		th.start();
		
	}
	
	public void showDataAnalyzer(String ip) {
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				openSpectrumWindow();
				
			}
		});
		
		th.start();
		
		VPXLogger.updateLog("Opening Amplitude Graph");
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
					
					VPXLogger.updateLog("MAD utility opened");
					
				} else {
					
					vpx_Content_Tabbed_Pane_Right.setSelectedIndex(i);
					
					VPXLogger.updateLog("MAD utility already opened");
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
				
				VPXLogger.updateLog("Opening boot option");
			}
		});
		
		th.start();
		
	}
	
	public void showBIST() {
		
		VPXLogger.updateLog("Starting Built in Self Test");
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				startBist(true, true, true);
				
			}
		});
		
		th.start();
		
	}
	
	public void startBist(boolean isP2020, boolean isDSP1, boolean isDSP2) {
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				BIST bist = new BIST(isP2020, isDSP1, isDSP2);
				
				bistWindow.showBISTWindow(VPX_ETHWindow.this, bist.getTotalProcessor());
				
				udpMonitor.startBist(VPXSessionManager.getCurrentProcessor(), VPXSessionManager.getCurrentSubSystem(),
						bist);
				
			}
		});
		
		th.start();
		
	}
	
	public void setReboot(String ip, int processor, int flashdevice, int page) {
		
		VPXLogger.updateLog("P2020 " + ip + " is set to Reboot");
		
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
				
				if (tftpMonitor != null) {
					
					tftpMonitor.shutdown();
					
					tftpMonitor = null;
				}
			}
		});
		
		th.start();
		
	}
	
	public void setTFTPServerDown() {
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				if (tftpMonitor != null) {
					
					tftpMonitor.shutdown();
					
					tftpMonitor = null;
				}
			}
		});
		
		th.start();
		
	}
	
	public void startDownloadingApplication(VPX_ExecutionFileTransferingWindow dialog,
			List<ExecutionHexArray> hexFileArray, String ip) {
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				udpMonitor.startDownloading(dialog, ip, hexFileArray);
				
			}
		});
		
		th.start();
		
	}
	
	public void showExecution() {
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				vpx_Content_Tabbed_Pane_Right.addTab("Execution",
						new JScrollPane(new VPX_ExecutionPanel(VPX_ETHWindow.this,
								VPXSessionManager.getCurrentSubSystem(), VPXSessionManager.getCurrentProcessor())));
				
				vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);
				
			}
		});
		
		th.start();
		
		VPXLogger.updateLog("Opening Execution Window");
		
	}
	
	public void showFlashWizard() {
		
		VPXLogger.updateLog("Opening flash wizard window");
		
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
		
		if (paswordWindow.isAccepted()) {
			
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
						
						VPXLogger.updateLog("Asking Super user password for VLAN Configuration");
						
						browserCanvas.setUrl("http://192.168.10.1/cgi-bin/portsetting.cgi");
						
						frame.setTitle("VLAN Configuration");
						
					} else {
						
						VPXLogger.updateLog("Asking Super user password for IP Configuration");
						
						browserCanvas.setUrl("http://192.168.10.1/cgi-bin/ipsetting.cgi");
						
						frame.setTitle("IP Configuration");
					}
					
				}
				frame.setBounds(100, 100, (int) (VPXUtilities.getScreenWidth() * .80) + 1,
						(int) (VPXUtilities.getScreenHeight() * .70) + 1);
				
				VPXLogger.updateLog("VLAN Configuration opened");
				
			} else {
				
				JOptionPane.showMessageDialog(this, "Pasword invalid", "Authentication", JOptionPane.ERROR_MESSAGE);
				
				paswordWindow.dispose();
				
				showVLAN(tab);
				
				VPXLogger.updateLog("Invalid Password");
			}
		}
		
	}
	
	public void showChangePassword() {
		
		VPXLogger.updateLog("Opening change password");
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				new VPX_ChangePasswordWindow(VPX_ETHWindow.this).setVisible(true);
				
			}
		});
		
		th.start();
		
	}
	
	public void showChangePeriodicity() {
		
		VPXLogger.updateLog("Opening change periodicity");
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				new VPX_ChangePeriodicityWindow(VPX_ETHWindow.this).setVisible(true);
				
			}
		});
		
		th.start();
		
	}
	
	public void showPrefrences() {
		
		VPXLogger.updateLog("Opening Preference window");
		
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
		
		VPX_ETHWindow.this.dispose();
		
		VPX_ETHWindow.this.udpMonitor.closeAllConnections();
		
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
	
	public void showShortcuts() {
		
		statusBar.showShorcuts();
		
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
	
	public void sendExecuteCommand(String ip, int command, int core) {
		
		udpMonitor.sendExecutionCommand(ip, command, core);
	}
	
	public void sendExecuteCommand(String ip, int command, int core1, int core2, int core3, int core4, int core5,
			int core6, int core7) {
		
		udpMonitor.sendExecutionCommand(ip, command, core1, core2, core3, core4, core5, core6, core7);
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
		
		udpMonitor.readMemory(filter);
		
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
	
	public void setMemoryValue(String ip, int core, long fromAddress, int memindex, int typeSize, int length,
			byte[] newValue) {
		
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
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
					VPXLogger.updateError(e);
				}
				
			}
		});
		
		th.start();
		
	}
	
	@Override
	public void reIndexMemoryPlotIndex() {
		
		currentNoofMemoryPlot--;
		
		if (currentNoofMemoryPlot == VPXConstants.MAX_MEMORY_PLOT) {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_PLOT, false);
			
			btnMemoryPlot.setEnabled(false);
			
		} else {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_PLOT, true);
			
			btnMemoryPlot.setEnabled(true);
		}
		
		vpx_MenuBar.setText(VPX_MenuBar.MEMORY_PLOT, rBundle.getString("Menu.Window.MemoryPlot") + " ( "
				+ (VPXConstants.MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " ) ");
		
		btnMemoryPlot
				.setToolTipText("remain " + (VPXConstants.MAX_MEMORY_PLOT - currentNoofMemoryPlot) + " memory plots");
	}
	
	@Override
	public void populateMemory(String ip, int memID, long startAddress, int stride, byte[] buffer) {
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					
					if (memoryBrowserWindow[memID].isVisible()
							&& (memoryBrowserWindow[memID].getCurrentIP().equals(ip))) {
						
						memoryBrowserWindow[memID].setBytes(startAddress, stride, buffer);
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
					VPXLogger.updateError(e);
				}
				
			}
		});
		
		th.start();
		
	}
	
	@Override
	public void reIndexMemoryBrowserIndex() {
		
		currentNoofMemoryView--;
		
		if (currentNoofMemoryView == VPXConstants.MAX_MEMORY_BROWSER) {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_BROWSER, false);
			
			btnMemoryBrowser.setEnabled(false);
			
		} else {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_BROWSER, true);
			
			btnMemoryBrowser.setEnabled(true);
		}
		
		vpx_MenuBar.setText(VPX_MenuBar.MEMORY_BROWSER, rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (VPXConstants.MAX_MEMORY_BROWSER - currentNoofMemoryView) + " ) ");
		
		btnMemoryBrowser.setToolTipText(
				"remain " + (VPXConstants.MAX_MEMORY_BROWSER - currentNoofMemoryView) + " memory browsers");
	}
	
	@Override
	public void readSpectrum(String ip, int core, int id) {
		
		udpMonitor.readSpectrum(ip, core, id);
	}
	
	@Override
	public void populateSpectrum(String ip, int core, int id, float[] yAxis) {
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					
					for (int i = 0; i < spectrumWindow.length; i++) {
						
						if (spectrumWindow[i].isVisible()) {
							
							if ((spectrumWindow[i].getCurrentCore() == core)
									&& (spectrumWindow[i].getCurrentProcIP().equals(ip))) {
								
								spectrumWindow[i].loadData(core, yAxis);
								
							}
						}
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
					VPXLogger.updateError(e);
				}
				
			}
		});
		
		th.start();
		
	}
	
	@Override
	public void reIndexSpectrumWindowIndex(String ip, int core) {
		
		currentNoofSpectrum--;
		
		if (currentNoofSpectrum == VPXConstants.MAX_SPECTRUM) {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_SPECTRUM, false);
			
			btnMemorySpectrum.setEnabled(false);
			
		} else {
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_SPECTRUM, true);
			
			btnMemorySpectrum.setEnabled(true);
		}
		
		vpx_MenuBar.setText(VPX_MenuBar.MEMORY_SPECTRUM, rBundle.getString("Menu.Window.Spectrum") + " ( "
				+ (VPXConstants.MAX_SPECTRUM - currentNoofSpectrum) + " ) ");
		
		btnMemorySpectrum
				.setToolTipText("remain " + (VPXConstants.MAX_SPECTRUM - currentNoofSpectrum) + " data analyser");
		
		if (ip.length() > 0)
			sendSpectrumInterrupt(ip, core);
	}
	
	@Override
	public void sendSpectrumInterrupt(String ip, int core) {
		
		udpMonitor.setSpectrumInterrupted(ip, core);
		
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
	
	public void sendTFTPFile(String ip, String filename, VPX_FlashProgressWindow flashingWindow, int fileType) {
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					File file = new File(filename);
					
					if (tftpMonitor == null)
						tftpMonitor = new VPXTFTPMonitor(new File(VPXSessionManager.getTFTPPath()),
								VPXTFTPMonitor.ServerMode.GET_ONLY);
					
					udpMonitor.setTFTPServer(tftpMonitor);
					
					udpMonitor.sendTFTP(ip, flashingWindow, VPXSessionManager.getCurrentIP(), file.getName(), fileType);
					
					tftpMonitor.setProgressWindow(flashingWindow);
					
					long size = file.length();
					
					int tot = (int) (size / 512);
					
					int rem = (int) (size % 512);
					
					if (rem > 0)
						tot++;
					
					tftpMonitor.setTotalPackets(tot);
					
				}
				catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
		});
		
		th.start();
		
	}
	
	public void sendMemoryFile(String ip, String filename, long startAddress, boolean isBinary, int format,
			int delimiter, VPX_MemoryLoadProgressWindow flashingWindow) {
		
		udpMonitor.sendMemoryFile(flashingWindow, filename, startAddress, ip, isBinary, format, delimiter);
		
	}
	
	// Advertisement Listener
	@Override
	public void updateProcessorStatus(String ip, String msg) {
		
		vpx_Processor_Tree.updateProcessorResponse(ip, msg);
		
		VPXLogger.updateStatus("Advertisement received from " + ip);
		
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
	
	public void updateNetworkError(String msg) {
		
		/*
		 * String[] buttons = { "Ok", "<html><b>Help<b></html>" }; int rc =
		 * JOptionPane.showOptionDialog(null, msg, "Network Error",
		 * JOptionPane.ERROR_MESSAGE, 0, null, buttons, buttons[0]); if (rc ==
		 * 0) { } else if (rc == 1) { try { Desktop.getDesktop().open(new
		 * File("E:\\netip.pdf")); } catch (IOException e) {
		 * VPXLogger.updateError(e); } } System.exit(0);
		 */
	}
	
	public void enableSelectedProcessorMenus(int option, VPX_ProcessorNode node) {
		
		enableSelectedProcessorMenus(option);
		
		if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_DSP1
				|| node.getNodeType() == PROCESSOR_LIST.PROCESSOR_DSP2) {
			
			// vpx_Menu_Window_Spectrum.setEnabled(true);
			
			vpx_MenuBar.enableMenuItem(VPX_MenuBar.MEMORY_SPECTRUM, true);
			
		}
		
	}
	
	public void enableSelectedProcessorMenus(int option) {
		
		vpx_MenuBar.enableSelectedProcessorMenus(option);
		
		switch (option) {
			
			case VPXConstants.PROCESSOR_SELECTED_MODE_NONE:
			default:
				
				btnMemoryBrowser.setEnabled(false);
				
				btnMemoryPlot.setEnabled(false);
				
				btnMemorySpectrum.setEnabled(false);
				
				btnBIST.setEnabled(false);
				
				btnFlash.setEnabled(false);
				
				btnExecute.setEnabled(false);
				
				break;
			
			case VPXConstants.PROCESSOR_SELECTED_MODE_DSP:
				
				btnMemoryBrowser.setEnabled(true);
				
				btnMemoryPlot.setEnabled(true);
				
				btnMemorySpectrum.setEnabled(true);
				
				btnBIST.setEnabled(false);
				
				btnFlash.setEnabled(true);
				
				btnExecute.setEnabled(true);
				
				break;
			case VPXConstants.PROCESSOR_SELECTED_MODE_P2020:
				
				btnMemoryBrowser.setEnabled(false);
				
				btnMemoryPlot.setEnabled(false);
				
				btnMemorySpectrum.setEnabled(false);
				
				btnBIST.setEnabled(true);
				
				btnFlash.setEnabled(true);
				
				btnExecute.setEnabled(false);
				
				break;
			
			case VPXConstants.PROCESSOR_SELECTED_MODE_SUBSYSTEM:
				
				btnMemoryBrowser.setEnabled(false);
				
				btnMemoryPlot.setEnabled(false);
				
				btnMemorySpectrum.setEnabled(false);
				
				btnBIST.setEnabled(false);
				
				btnFlash.setEnabled(false);
				
				btnExecute.setEnabled(false);
				
				break;
			
		}
	}
	
}

package com.cti.vpx.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_MenuBar extends JMenuBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 962411189653197523L;
	
	public static final int MEMORY_BROWSER = 0;
	
	public static final int MEMORY_PLOT = 1;
	
	public static final int MEMORY_SPECTRUM = 2;
	
	private VPX_ETHWindow parent;
	
	private JMenu vpx_Menu_File;
	
	private JMenu vpx_Menu_Window;
	
	private JMenu vpx_Menu_Tool;
	
	private JMenu vpx_Menu_Help;
	
	// File Menu Items
	private JMenuItem vpx_Menu_File_AliasConfig;
	
	private JMenuItem vpx_Menu_File_SubnetFilter;
	
	private JMenuItem vpx_Menu_File_OpenLog;
	
	private JMenuItem vpx_Menu_File_Detail;
	
	private JMenuItem vpx_Menu_File_Reload;
	
	private JMenuItem vpx_Menu_File_Exit;
	
	// Window Menu Items
	private JMenuItem vpx_Menu_Window_MemoryBrowser;
	
	private JMenuItem vpx_Menu_Window_MemoryPlot;
	
	private JMenuItem vpx_Menu_Window_EthFlash;
	
	private JMenuItem vpx_Menu_Window_Spectrum;
	
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
	
	private JMenuItem vpx_Menu_Help_Shortcut;
	
	private JMenuItem vpx_Menu_Help_About;
	
	private ResourceBundle rBundle;
	
	public VPX_MenuBar(VPX_ETHWindow paren, ResourceBundle bundle) {
		
		this.parent = paren;
		
		this.rBundle = bundle;
		
		createMenus();
		
	}
	
	private void createMenus() {
		
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
				
				parent.showAliasConfig("");
				
			}
		});
		
		vpx_Menu_File_SubnetFilter = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Filter"),
				VPXConstants.Icons.ICON_FILTER);
		
		vpx_Menu_File_SubnetFilter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.toggleFilter();
				
			}
		});
		
		vpx_Menu_File_OpenLog = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.OpenLog"),
				VPXConstants.Icons.ICON_OPEN);
		
		vpx_Menu_File_OpenLog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.openLogFile();
				
			}
		});
		
		vpx_Menu_File_Reload = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Reload"),
				VPXConstants.Icons.ICON_REFRESH);
		
		vpx_Menu_File_Reload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.refreshProcessorTree(true);
				
			}
		});
		
		vpx_Menu_File_Detail = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Detail"),
				VPXConstants.Icons.ICON_DETAIL);
		
		vpx_Menu_File_Detail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showDetail();
				
			}
		});
		
		vpx_Menu_File_Exit = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Exit"),
				VPXConstants.Icons.ICON_DELETE_EXIT);
		
		vpx_Menu_File_Exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.exitApplication();
				
			}
		});
		
		// Window Menus
		vpx_Menu_Window_MemoryBrowser = VPXComponentFactory
				
				.createJMenuItem(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
						+ (VPXConstants.MAX_MEMORY_BROWSER - 0) + " ) ", VPXConstants.Icons.ICON_MEMORY);
		
		vpx_Menu_Window_MemoryBrowser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showMemoryBrowser();
				
			}
		});
		
		vpx_Menu_Window_MemoryPlot = VPXComponentFactory.createJMenuItem(
				rBundle.getString("Menu.Window.MemoryPlot") + " ( " + (VPXConstants.MAX_MEMORY_PLOT - 0) + " ) ",
				VPXConstants.Icons.ICON_PLOT);
		
		vpx_Menu_Window_MemoryPlot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showMemoryPlot();
				
			}
		});
		
		vpx_Menu_Window_EthFlash = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.EthFlash"),
				VPXConstants.Icons.ICON_ETHFLASH);
		
		vpx_Menu_Window_EthFlash.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showEthFlash();
			}
		});
		
		vpx_Menu_Window_Spectrum = VPXComponentFactory.createJMenuItem(
				rBundle.getString("Menu.Window.Spectrum") + " ( " + (VPXConstants.MAX_SPECTRUM - 0) + " ) ",
				VPXConstants.Icons.ICON_SPECTRUM);
		
		vpx_Menu_Window_Spectrum.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showDataAnalyzer(VPXSessionManager.getCurrentIP());
				
			}
		});
		
		vpx_Menu_Window_MAD = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.MAD"),
				VPXConstants.Icons.ICON_MAD);
		
		vpx_Menu_Window_MAD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showMAD();
				
			}
		});
		
		vpx_Menu_Window_FlashWizard = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Wizard"),
				VPXConstants.Icons.ICON_MAD_WIZARD);
		
		vpx_Menu_Window_FlashWizard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showFlashWizard();
				
			}
		});
		
		vpx_Menu_Window_BIST = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.BIST"),
				VPXConstants.Icons.ICON_BIST);
		
		vpx_Menu_Window_BIST.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showBIST();
				
			}
		});
		
		vpx_Menu_Window_Boot = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Reboot"),
				VPXConstants.Icons.ICON_EMPTY);
		
		vpx_Menu_Window_Boot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showBootOption(VPXSessionManager.getCurrentIP());
				
			}
		});
		
		vpx_Menu_Window_Execution = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Execute"),
				VPXConstants.Icons.ICON_EXECUTION);
		
		vpx_Menu_Window_Execution.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showExecution();
				
			}
		});
		
		vpx_Menu_Window_P2020Config = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.P2020Config"),
				VPXConstants.Icons.ICON_EMPTY);
		
		vpx_Menu_Window_P2020Config.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showVLAN(1);
				
			}
		});
		
		vpx_Menu_Window_ChangeIP = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.ChangeIP"),
				VPXConstants.Icons.ICON_EMPTY);
		
		vpx_Menu_Window_ChangeIP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showVLAN(0);
				
			}
		});
		// Tools Menus
		
		vpx_Menu_Tools_ChangePWD = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.ChangePWD"),
				VPXConstants.Icons.ICON_EMPTY);
		
		vpx_Menu_Tools_ChangePWD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showChangePassword();
				
			}
		});
		
		vpx_Menu_Tools_ChangePeriod = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Periodicity"),
				VPXConstants.Icons.ICON_EMPTY);
		
		vpx_Menu_Tools_ChangePeriod.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showChangePeriodicity();
				
			}
		});
		vpx_Menu_Tools_Prefrences = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Preferences"),
				VPXConstants.Icons.ICON_SETTINGS);
		
		vpx_Menu_Tools_Prefrences.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showPrefrences();
			}
		});
		
		vpx_Menu_Tools_ShowView_Console = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Console"),
				VPXConstants.Icons.ICON_EMPTY);
		
		vpx_Menu_Tools_ShowView_Console.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showConsole();
				
			}
		});
		
		vpx_Menu_Tools_ShowView_Log = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.Log"),
				VPXConstants.Icons.ICON_EMPTY);
		
		vpx_Menu_Tools_ShowView_Log.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showLog();
				
			}
		});
		
		vpx_Menu_Tools_ShowView_Mode = VPXComponentFactory.createJMenu(rBundle.getString("Menu.Tools.ShowView.Mode"));
		
		vpx_Menu_Tools_ShowView_Mode_UART = VPXComponentFactory
				.createJMenuItem(rBundle.getString("Menu.Tools.ShowView.Mode.UART"), VPXConstants.Icons.ICON_EMPTY);
		
		vpx_Menu_Tools_ShowView_Mode_UART.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showAppMode();
				
			}
		});
		
		vpx_Menu_Tools_ShowView_Mode.add(vpx_Menu_Tools_ShowView_Mode_UART);
		
		// Help Menus
		vpx_Menu_Help_Help = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.Help"),
				VPXConstants.Icons.ICON_HELP);
		
		vpx_Menu_Help_Help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showHelp();
				
			}
		});
		
		vpx_Menu_Help_Shortcut = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.Shortcuts"),
				VPXConstants.Icons.ICON_SHORTCUT);
		
		vpx_Menu_Help_Shortcut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showShortcuts();
				
			}
		});
		
		vpx_Menu_Help_About = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.About"),
				VPXConstants.Icons.ICON_ABOUT);
		
		vpx_Menu_Help_About.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				parent.showAbout();
				
			}
		});
		
		// Shorcuts
		
		// File Menu Items
		vpx_Menu_File_AliasConfig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		
		vpx_Menu_File_SubnetFilter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		
		vpx_Menu_File_OpenLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		
		vpx_Menu_File_Detail.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		
		vpx_Menu_File_Reload.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		
		vpx_Menu_File_Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		
		vpx_Menu_Window_MAD.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		
		vpx_Menu_Window_FlashWizard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		
		// Tools Menu Items
		vpx_Menu_Tools_ChangePWD.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		
		vpx_Menu_Tools_ChangePeriod.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		
		vpx_Menu_Tools_Prefrences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		
		vpx_Menu_Tools_ShowView_Console.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, ActionEvent.CTRL_MASK));
		
		vpx_Menu_Tools_ShowView_Log.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, ActionEvent.CTRL_MASK));
		
		// Help Menu Items
		vpx_Menu_Help_Help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		
		vpx_Menu_Help_About.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK));
		
		vpx_Menu_Help_Shortcut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));
		
		// Adding
		
		// Adding Menus
		this.add(vpx_Menu_File);
		
		this.add(vpx_Menu_Window);
		
		this.add(vpx_Menu_Tool);
		
		this.add(vpx_Menu_Help);
		
		// Adding Menu Items
		
		// File Menu Items
		vpx_Menu_File.add(vpx_Menu_File_AliasConfig);
		
		vpx_Menu_File.add(vpx_Menu_File_OpenLog);
		
		vpx_Menu_File.add(vpx_Menu_File_SubnetFilter);
		
		vpx_Menu_File.add(VPXComponentFactory.createJSeparator());
		
		vpx_Menu_File.add(vpx_Menu_File_Reload);
		
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
		
		vpx_Menu_Window.add(vpx_Menu_Window_Spectrum);
		
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
		
		vpx_Menu_Help.add(vpx_Menu_Help_Shortcut);
		
		vpx_Menu_Help.add(VPXComponentFactory.createJSeparator());
		
		vpx_Menu_Help.add(vpx_Menu_Help_About);
		
	}
	
	public void setText(int id, String text) {
		
		switch (id) {
			case MEMORY_BROWSER:
				
				vpx_Menu_Window_MemoryBrowser.setText(text);
				
				break;
			
			case MEMORY_PLOT:
				
				vpx_Menu_Window_MemoryPlot.setText(text);
				
				break;
			
			case MEMORY_SPECTRUM:
				
				vpx_Menu_Window_Spectrum.setText(text);
				
				break;
		}
	}
	
	public void enableMenuItem(int id, boolean option) {
		
		switch (id) {
			case MEMORY_BROWSER:
				
				vpx_Menu_Window_MemoryBrowser.setEnabled(option);
				
				break;
			
			case MEMORY_PLOT:
				
				vpx_Menu_Window_MemoryPlot.setEnabled(option);
				
				break;
			
			case MEMORY_SPECTRUM:
				
				vpx_Menu_Window_Spectrum.setEnabled(option);
				
				break;
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
				
				vpx_Menu_Window_Spectrum.setEnabled(false);
				
				vpx_Menu_Window_BIST.setEnabled(false);
				
				vpx_Menu_Window_Boot.setEnabled(false);
				
				vpx_Menu_Window_P2020Config.setEnabled(false);
				
				vpx_Menu_Window_ChangeIP.setEnabled(false);
				
				break;
			
			case VPXConstants.PROCESSOR_SELECTED_MODE_DSP:
				
				vpx_Menu_Window_MemoryBrowser.setEnabled(true);
				
				vpx_Menu_Window_MemoryPlot.setEnabled(true);
				
				vpx_Menu_Window_EthFlash.setEnabled(true);
				
				vpx_Menu_Window_Execution.setEnabled(true);
				
				vpx_Menu_Window_Spectrum.setEnabled(true);
				
				vpx_Menu_Window_BIST.setEnabled(false);
				
				vpx_Menu_Window_Boot.setEnabled(false);
				
				vpx_Menu_Window_P2020Config.setEnabled(false);
				
				vpx_Menu_Window_ChangeIP.setEnabled(false);
				
				break;
			case VPXConstants.PROCESSOR_SELECTED_MODE_P2020:
				
				vpx_Menu_Window_MemoryBrowser.setEnabled(false);
				
				vpx_Menu_Window_MemoryPlot.setEnabled(false);
				
				vpx_Menu_Window_EthFlash.setEnabled(false);
				
				vpx_Menu_Window_Execution.setEnabled(false);
				
				vpx_Menu_Window_Spectrum.setEnabled(false);
				
				vpx_Menu_Window_BIST.setEnabled(true);
				
				vpx_Menu_Window_Boot.setEnabled(true);
				
				vpx_Menu_Window_P2020Config.setEnabled(true);
				
				vpx_Menu_Window_ChangeIP.setEnabled(true);
				
				break;
			
			case VPXConstants.PROCESSOR_SELECTED_MODE_SUBSYSTEM:
				
				vpx_Menu_Window_MemoryBrowser.setEnabled(false);
				
				vpx_Menu_Window_MemoryPlot.setEnabled(false);
				
				vpx_Menu_Window_EthFlash.setEnabled(false);
				
				vpx_Menu_Window_Execution.setEnabled(false);
				
				vpx_Menu_Window_Spectrum.setEnabled(false);
				
				vpx_Menu_Window_BIST.setEnabled(false);
				
				vpx_Menu_Window_Boot.setEnabled(false);
				
				vpx_Menu_Window_P2020Config.setEnabled(false);
				
				vpx_Menu_Window_ChangeIP.setEnabled(false);
				
				break;
			
		}
	}
	
}

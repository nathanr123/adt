/**
 * 
 */
package com.cti.rmopp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JSeparator;

import com.cti.rmopp.controls.CFrame;
import com.cti.rmopp.controls.CLoggerPane;
import com.cti.rmopp.controls.CMenu;
import com.cti.rmopp.controls.CMenuBar;
import com.cti.rmopp.controls.CMenuItem;
import com.cti.rmopp.controls.CPanel;
import com.cti.rmopp.controls.CTabbedPane;
import com.cti.rmopp.controls.CToolBar;
import com.cti.rmopp.controls.ComponentFactory;
import com.cti.rmopp.util.Util;

/**
 * @author nathanr_kamal
 *
 */
public class VPX_Dual_ADT extends CFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CPanel centerPanel;

	private CMenuBar nmsServerMenubar;

	private CMenu nmsServerMenu;

	private CMenuItem miRestart, miShutdown, miExit;

	private CTabbedPane nmsServerTab;

	private Dimension scrSize;

	private String appTitle;

	private CLoggerPane loggerPane;

	public VPX_Dual_ADT(String title) throws HeadlessException {

		super(title, true, true, true);

		this.appTitle = title;

		init();

		initAndLoadComponents();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					VPX_Dual_ADT frame = new VPX_Dual_ADT("Cornet VPX Dual ADT(TMS320C6678) ");

					frame.start();

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

	}

	private void init() {
		scrSize = Util.getScreenResoultion();

		setBounds(0, 0, (int) scrSize.getWidth(), (int) scrSize.getHeight());

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setAppTitle(appTitle);

		setUndecorated(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				close();
			}
		});
	}

	private void initAndLoadComponents() {

		centerPanel = getCenterPanel();

		loadAppMenuBar();

		setAppJMenuBar(nmsServerMenubar);

		CToolBar tb = new CToolBar();

		tb.addButton("But");
		
		tb.addButton("But1");
		
		tb.addButton("But2");
		
		tb.addButton("","open");

		setAppCToolBar(tb);

		getContentPane().add(centerPanel, BorderLayout.CENTER);

	}

	private void loadAppMenuBar() {

		nmsServerMenubar = ComponentFactory.createMenuBar();

		nmsServerMenu = ComponentFactory.createMenu("Server");

		miRestart = ComponentFactory.createMenuItem("Restart Server");

		miShutdown = ComponentFactory.createMenuItem("Shutdown Server");

		miExit = ComponentFactory.createMenuItem("Exit Server");

		miExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				loggerPane.updateLog("Application Exited");
			}
		});

		nmsServerMenu.add(miRestart);

		nmsServerMenu.add(miShutdown);

		nmsServerMenu.add(new JSeparator());

		nmsServerMenu.add(miExit);

		nmsServerMenubar.add(nmsServerMenu);
	}

	private CPanel getCenterPanel() {

		CPanel panel = ComponentFactory.createPanel(new BorderLayout());

		CPanel homePanel = new HomePanel();

		CPanel threadPanel = ComponentFactory.createPanel(new BorderLayout());

		CPanel systemPanel = ComponentFactory.createPanel(new BorderLayout());

		nmsServerTab = ComponentFactory.createTabbedPane();

		nmsServerTab.addTab("Home", homePanel);

		nmsServerTab.addTab("Threads", threadPanel);

		nmsServerTab.addTab("System", systemPanel);

		panel.add(nmsServerTab, BorderLayout.CENTER);

		loggerPane = ComponentFactory.createLoggerPane();

		loggerPane.setPreferredSize(new Dimension(loggerPane.getWidth(), (int) scrSize.getHeight() / 2));

		panel.add(loggerPane, BorderLayout.SOUTH);

		loggerPane.updateLog("Logger Created");

		return panel;
	}

	public void start() {
		setVisible(true);
	}

}

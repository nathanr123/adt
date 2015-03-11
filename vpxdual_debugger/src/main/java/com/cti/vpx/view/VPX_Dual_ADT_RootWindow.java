package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

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

import com.cti.vpx.controls.IPRangeSelector;
import com.cti.vpx.controls.VPX_LoggerPanel;
import com.cti.vpx.controls.VPX_MessagePanel;
import com.cti.vpx.controls.VPX_ProcessorTree;
import com.cti.vpx.controls.hex.swing.demo.HexEditorDemoPanel;
import com.cti.vpx.model.Core;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.Slot;
import com.cti.vpx.model.VPX;
import com.cti.vpx.model.VPX.PROCESSOR_TYPE;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.ComponentFactory;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.util.XMLParser;

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

	private JToolBar vpx_ToolBar;

	private JSplitPane vpx_SplitPane;

	private JSplitPane vpx_Right_SplitPane;

	private JTabbedPane vpx_Content_Tabbed_Pane_Right;

	private VPXSystem system;

	private DefaultMutableTreeNode cageRootNode;

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

		setBounds(0, 0, VPXUtilities.getScreenWidth(), VPXUtilities.getScreenHeight());

		// setAlwaysOnTop(true);

		loadComponents();
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
		vpx_Menu_File_Scan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IPRangeSelector ir =  new IPRangeSelector(VPX_Dual_ADT_RootWindow.this);
				ir.setVisible(true);
			}
		});

		vpx_Menu_File_Exit = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Exit"));

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

		vpx_Menu_Window_MemoryBrowser = ComponentFactory
				.createJMenuItem(rBundle.getString("Menu.Window.MemoryBrowser"));

		vpx_Menu_Window_MemoryBrowser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vpx_Content_Tabbed_Pane_Right.addTab("Memory Browser", new HexEditorDemoPanel());

				vpx_Content_Tabbed_Pane_Right.setSelectedIndex(vpx_Content_Tabbed_Pane_Right.getTabCount() - 1);

			}
		});

		vpx_Menu_Window.add(vpx_Menu_Window_MemoryBrowser);

		vpx_MenuBar.add(vpx_Menu_Help);

		vpx_Menu_Help_Help = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.Help"));

		vpx_Menu_Help_About = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Help.About"));

		vpx_Menu_Help.add(vpx_Menu_Help_Help);

		vpx_Menu_Help.add(ComponentFactory.createJSeparator());

		vpx_Menu_Help.add(vpx_Menu_Help_About);

		setJMenuBar(vpx_MenuBar);
	}

	private void loadToolBar() {

		vpx_ToolBar = ComponentFactory.createJToolBar();

		vpx_ToolBar.setFloatable(false);

		vpx_ToolBar.add(ComponentFactory.createJButton("", ComponentFactory.getImageIcon("images\\open.gif", 12, 12),
				null));

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

		JPanel jp = new JPanel();

		jp.setOpaque(true);

		jp.setPreferredSize(new Dimension(VPXUtilities.getScreenWidth(), VPXUtilities.getScreenHeight() / 35));

		getContentPane().add(jp, BorderLayout.SOUTH);
	}

	private JSplitPane getRightComponent() {

		vpx_Right_SplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		vpx_Content_Tabbed_Pane_Right = new JTabbedPane();

		vpx_Content_Tabbed_Pane_Right.addTab("Processor", new JPanel());

		vpx_Right_SplitPane.setLeftComponent(vpx_Content_Tabbed_Pane_Right);

		JTabbedPane tb1 = new JTabbedPane();

		tb1.addTab("Message", new VPX_MessagePanel());

		tb1.addTab("Logger", new VPX_LoggerPanel());

		vpx_Right_SplitPane.setRightComponent(tb1);

		vpx_Right_SplitPane.setDividerLocation(((int) VPXUtilities.getScreenHeight() / 2 + (int) VPXUtilities
				.getScreenHeight() / 10));

		vpx_Right_SplitPane.setDividerSize(2);

		return vpx_Right_SplitPane;
	}

	private JScrollPane getSystemTree() {

		cageRootNode = new DefaultMutableTreeNode();

		createCageObject();

		VPX_ProcessorTree tree = new VPX_ProcessorTree(cageRootNode);

		JScrollPane jp = new JScrollPane(tree);

		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
		return jp;
	}

	private void createCageObject() {
		system = new VPXSystem();

		system.setID(0);

		Slot slot = new Slot(0);

		slot.setModel("C66778");

		int idx = 0;

		system = XMLParser.readFromFile();

		if (system == null) {

			system = new VPXSystem();

			for (PROCESSOR_TYPE pType : PROCESSOR_TYPE.values()) {

				Processor p = new Processor(pType);

				p.setID(idx);

				p.addIPAddress("192.168.2.1");

				p.addIPAddress("192.168.2.2");

				int loop = VPX.MAX_CORE_DSP;

				if (pType == PROCESSOR_TYPE.P2020) {
					loop = VPX.MAX_CORE_P2020;
				}
				for (int i = 0; i < loop; i++) {
					p.addCore(new Core(i, 5555));
				}

				slot.addProcessor(p);

				idx++;
			}

			system.addSlot(slot);

			XMLParser.writeToFile(system);
		}

		cageRootNode.setUserObject(system.getName());

		List<Slot> sl = system.getSlots();

		for (Iterator<Slot> iterator = sl.iterator(); iterator.hasNext();) {

			Slot slt = iterator.next();

			DefaultMutableTreeNode slotNode = new DefaultMutableTreeNode(slt.getName());

			List<Processor> prc = slt.getProcessors();

			for (Iterator<Processor> iterator2 = prc.iterator(); iterator2.hasNext();) {

				Processor processor = iterator2.next();

				DefaultMutableTreeNode processorNode = new DefaultMutableTreeNode(processor.getName());

				List<Core> crs = processor.getCores();

				for (Iterator<Core> iterator3 = crs.iterator(); iterator3.hasNext();) {

					Core core = iterator3.next();

					processorNode.add(new DefaultMutableTreeNode(core.getName()));
				}
				slotNode.add(processorNode);
			}

			cageRootNode.add(slotNode);
		}

	}

}

/**
 * 
 */
package com.cti.vpx.controls;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.ComponentFactory;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

/**
 * @author Abi_Achu
 *
 */
public class VPX_ProcessorTree extends JTree implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4220657730002684130L;

	private static final int MAXRESPONSETIMEOUT = 10;

	private static JPopupMenu vpx_contextMenu = new JPopupMenu();

	// File Menu Items
	private JMenuItem vpx_Menu_File_AliasConfig;

	private JMenuItem vpx_Menu_File_Detail;

	// Window Menu Items
	private JMenuItem vpx_Menu_Window_MemoryBrowser;

	private JMenuItem vpx_Menu_Window_MemoryPlot;

	private JMenuItem vpx_Menu_Window_EthFlash;

	private JMenuItem vpx_Menu_Window_Amplitude;

	private JMenuItem vpx_Menu_Window_Waterfall;

	private JMenuItem vpx_Menu_Window_MAD;

	private JMenuItem vpx_Menu_Window_BIST;

	private JMenuItem vpx_Menu_Window_Execution;

	private JMenuItem vpx_Menu_Window_P2020Config;

	private JMenuItem vpx_Menu_Window_ChangeIP;

	// Tools Menu Items
	private JMenuItem vpx_Menu_Tools_ChangePWD;

	private VPX_ETHWindow parent;

	private VPXSystem system = new VPXSystem();

	private static DefaultMutableTreeNode systemRootNode = new DefaultMutableTreeNode();

	private ProcessorMonitor monitor = new ProcessorMonitor();

	private ResourceBundle rBundle = VPXUtilities.getResourceBundle();

	/**
	 * 
	 */
	public VPX_ProcessorTree() {
		initTree();

		createPopupMenu();

		monitor.execute();
	}

	/**
	 * @param value
	 */
	public VPX_ProcessorTree(Object[] value) {

		super(value);

		initTree();

		createPopupMenu();

		monitor.execute();
	}

	/**
	 * @param value
	 */
	public VPX_ProcessorTree(Vector<?> value) {

		super(value);

		initTree();

		createPopupMenu();

		monitor.execute();
	}

	/**
	 * @param value
	 */
	public VPX_ProcessorTree(Hashtable<?, ?> value) {

		super(value);

		initTree();

		createPopupMenu();

		monitor.execute();
	}

	/**
	 * @param root
	 */
	public VPX_ProcessorTree(VPX_ETHWindow prnt, TreeNode root) {

		super(root);

		this.parent = prnt;

		initTree();

		createPopupMenu();

		monitor.execute();
	}

	/**
	 * @param prnt
	 */
	public VPX_ProcessorTree(VPX_ETHWindow prnt) {

		super(systemRootNode);

		this.parent = prnt;

		initTree();

		loadSystemRootNode();

		createPopupMenu();

		monitor.execute();

	}

	/**
	 * @param newModel
	 */
	public VPX_ProcessorTree(TreeModel newModel) {

		super(newModel);

		initTree();

		createPopupMenu();

		monitor.execute();
	}

	/**
	 * @param root
	 * @param asksAllowsChildren
	 */
	public VPX_ProcessorTree(TreeNode root, boolean asksAllowsChildren) {

		super(root, asksAllowsChildren);

		initTree();

		createPopupMenu();

		monitor.execute();
	}

	private void initTree() {

		system = VPXUtilities.getVPXSystem();

		addMouseListener(this);

		setCellRenderer(new VPX_ProcessorTreeCellRenderer());

		setRowHeight(20);

		monitor.execute();

	}

	public void setVPXSystem(VPXSystem sys) {
		this.system = sys;

		loadSystemRootNode();

		for (int i = 0; i < getRowCount(); i++) {
			expandRow(i);
		}
	}

	public void updateVPXSystemTree() {

		this.system = VPXUtilities.getVPXSystem();

		loadSystemRootNode();

		for (int i = 0; i < getRowCount(); i++) {

			expandRow(i);
		}
	}

	public boolean isSubSystemsAvailable() {

		return (system.getSubsystem().size() > 0);
	}

	private String getNodeUserObject(String name, boolean isAlive, boolean isWaterfall, boolean isAmplitude) {

		String sub = "<html>";

		if (isAlive) {

			sub = sub + "<font face='Tahoma' size='2.5' color='green'>" + name + "</font>" + "&nbsp;&nbsp;";

			if (isWaterfall) {

				sub = sub + "<font face='Tahoma' size='2' color='green'>W</font>" + "&nbsp;&nbsp;";

			} else {

				sub = sub + "<font face='Tahoma' size='2' color='red'>W</font>" + "&nbsp;&nbsp;";
			}

			if (isAmplitude) {

				sub = sub + "<font face='Tahoma' size='2' color='green'>A</font>" + "&nbsp;&nbsp;";

			} else {

				sub = sub + "<font face='Tahoma' size='2' color='red'>A</font>" + "&nbsp;&nbsp;";
			}

		} else {

			sub = sub + "<font face='Tahom' size='2.5' color='red'>" + name + "</font>" + "&nbsp;&nbsp;";

			sub = sub + "<font face='Tahoma' size='2' color='red'>W</font>" + "&nbsp;&nbsp;";

			sub = sub + "<font face='Tahoma' size='2' color='red'>A</font>" + "&nbsp;&nbsp;";
		}

		sub = sub + "</html>";

		return sub;
	}

	public void updateProcessorResponse(String ip, String res) {

		boolean isNotFound = false;

		DefaultMutableTreeNode node = null;

		Enumeration<DefaultMutableTreeNode> e = systemRootNode.depthFirstEnumeration();

		while (e.hasMoreElements()) {

			node = e.nextElement();

			if (node.isLeaf()) {

				if (node.getUserObject().toString().contains(ip)) {

					parseResponse(node, res);

					isNotFound = true;

					break;
				}

			}
		}

		if (!isNotFound) {

			system.addInUnListed(ip, System.currentTimeMillis(), res);

			System.out.println(ip + " " + res);

			VPXUtilities.setVPXSystem(system);

			loadSystemRootNode();
			/*
			 * systemRootNode.add(getProcessorNode(ip, res));
			 * 
			 * ((DefaultTreeModel) getModel()).reload();
			 * 
			 * for (int i = 0; i < getRowCount(); i++) {
			 * 
			 * expandRow(i); }
			 */
			// System.out.println(system.getUnListed().size());

			parent.reloadVPXSystem();
		}

		setRespondTimetoVPXSystem(ip);

	}

	private void setRespondTimetoVPXSystem(String ip) {

		long time = System.currentTimeMillis();

		boolean isFound = false;

		List<VPXSubSystem> s = system.getSubsystem();

		for (Iterator<VPXSubSystem> iterator = s.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			if (vpxSubSystem.getIpP2020().equals(ip)) {

				vpxSubSystem.setP2020ResponseTime(time);

				isFound = true;

				break;

			} else if (vpxSubSystem.getIpDSP1().equals(ip)) {

				vpxSubSystem.setDsp1ResponseTime(time);

				isFound = true;

				break;

			} else if (vpxSubSystem.getIpDSP2().equals(ip)) {

				vpxSubSystem.setDsp2ResponseTime(time);

				isFound = true;

				break;
			}

		}

		if (!isFound) {

			List<Processor> p = system.getUnListed();

			for (Iterator<Processor> iterator = p.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				if (processor.getiP_Addresses().equals(ip)) {

					processor.setResponseTime(time);

					break;
				}
			}

		}
	}

	private long getRespondTimeFromVPXSystem(String ip) {

		long time = 0;// System.currentTimeMillis();

		boolean isFound = false;

		List<VPXSubSystem> s = system.getSubsystem();

		for (Iterator<VPXSubSystem> iterator = s.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			if (ip.contains(vpxSubSystem.getIpP2020())) {

				time = vpxSubSystem.getP2020ResponseTime();

				isFound = true;

				break;

			} else if (ip.contains(vpxSubSystem.getIpDSP1())) {

				time = vpxSubSystem.getDsp1ResponseTime();

				isFound = true;

				break;

			} else if (ip.contains(vpxSubSystem.getIpDSP2())) {

				time = vpxSubSystem.getDsp2ResponseTime();

				isFound = true;

				break;
			}

		}

		if (!isFound) {

			List<Processor> p = system.getUnListed();

			for (Iterator<Processor> iterator = p.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				if (ip.contains(processor.getiP_Addresses())) {

					time = processor.getResponseTime();

					break;
				}
			}

		}

		return time;
	}

	private DefaultMutableTreeNode getProcessorNode(String ip, String msg) {

		String ret = "";

		String w = msg.substring(2, 3);

		String a = msg.substring(3, 4);

		if (msg.startsWith("P2")) {

			ret = getNodeUserObject("(P2020)" + ip, true);

		} else if (msg.startsWith("D1")) {

			ret = getNodeUserObject("(DSP1)" + ip, true, w.equals("1"), a.equals("1"));

		} else if (msg.startsWith("D2")) {

			ret = getNodeUserObject("(DSP2)" + ip, true, w.equals("1"), a.equals("1"));

		}

		return new DefaultMutableTreeNode(ret + ip);
	}

	private void parseResponse(DefaultMutableTreeNode node, String msg) {

		if (msg.length() == 4) {

			String name = node.getUserObject().toString();

			name = name.substring(name.indexOf(">(") + 1, name.indexOf("</"));

			if (msg.startsWith("P2")) {

				node.setUserObject(getNodeUserObject(name, true));

			} else {

				String w = msg.substring(2, 3);

				String a = msg.substring(3, 4);

				name = getNodeUserObject(name, true, w.equals("1"), a.equals("1"));

				node.setUserObject(name);

			}
		}

		VPX_ProcessorTree.this.repaint();
	}

	private String getNodeUserObject(String name, boolean isAlive) {

		String sub = "<html>";

		if (isAlive) {
			sub = sub + "<font face='Tahom' size='2.5' color='green'>" + name + "</font>" + "&nbsp;&nbsp;";

		} else {
			sub = sub + "<font face='Tahom' size='2.5' color='red'>" + name + "</font>" + "&nbsp;&nbsp;";
		}

		sub = sub + "</html>";

		return sub;
	}

	private void loadSystemRootNode() {

		systemRootNode.removeAllChildren();

		systemRootNode.setUserObject(system.getName());

		List<VPXSubSystem> subSystems = system.getSubsystem();

		if (subSystems != null) {

			for (Iterator<VPXSubSystem> iterator = subSystems.iterator(); iterator.hasNext();) {

				VPXSubSystem subSystem = iterator.next();

				DefaultMutableTreeNode subSystemNode = new DefaultMutableTreeNode(subSystem.getSubSystem());

				DefaultMutableTreeNode ipNode1 = new DefaultMutableTreeNode(getNodeUserObject(subSystem.getP2020Name(),
						false));

				DefaultMutableTreeNode ipNode2 = new DefaultMutableTreeNode(getNodeUserObject(subSystem.getDSP1Name(),
						false, false, false));

				DefaultMutableTreeNode ipNode3 = new DefaultMutableTreeNode(getNodeUserObject(subSystem.getDSP2Name(),
						false, false, false));

				subSystemNode.add(ipNode1);

				subSystemNode.add(ipNode2);

				subSystemNode.add(ipNode3);

				systemRootNode.add(subSystemNode);
			}
		}

		List<Processor> unlisted = system.getUnListed();

		if (unlisted.size() > 0) {

			DefaultMutableTreeNode unlist = new DefaultMutableTreeNode("Un Listed");

			systemRootNode.add(unlist);

			for (Iterator<Processor> iterator = unlisted.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				unlist.add(new DefaultMutableTreeNode(getProcessorNode(processor.getiP_Addresses(), processor.getMsg())));
			}
		}

		((DefaultTreeModel) getModel()).reload();

		for (int i = 0; i < getRowCount(); i++) {

			expandRow(i);
		}

		// updateUI();

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

			VPX_ScanWindow ir = new VPX_ScanWindow(parent);

			ir.setVisible(true);

		}
	}

	private class VPX_ProcessorTreeCellRenderer extends DefaultTreeCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3402533538077987491L;

		ImageIcon systemIcon = VPXUtilities.getImageIcon("image\\System.jpg", 18, 18);

		ImageIcon processorIcon = VPXUtilities.getImageIcon("image\\Processor4.jpg", 14, 14);

		ImageIcon subSystemIcon = VPXUtilities.getImageIcon("image\\Slot.jpg", 14, 14);

		public VPX_ProcessorTreeCellRenderer() {

		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

			String nodo = "";

			DefaultMutableTreeNode dNode = ((DefaultMutableTreeNode) value);

			if (sel) {
				setTextSelectionColor(Color.white);
			}

			if (dNode.getUserObject() != null)
				nodo = ((DefaultMutableTreeNode) value).getUserObject().toString();

			if (nodo.startsWith(VPXSystem.class.getSimpleName())) {

				setIcon(systemIcon);

			} else if (dNode.isLeaf()) {

				setIcon(processorIcon);

			} else {

				setIcon(subSystemIcon);

			}

			return this;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();

		if (e.getButton() == 1) {

			if (node != null) {
				if (node.isLeaf()) {

					setSelectedProcessor(node);

				} else {
					VPXUtilities.setCurrentProcessor("", "", "");
				}
			}

		} else if (e.getButton() == 3) {

			int row = getRowForLocation(e.getX(), e.getY());

			if (row == -1) {

				return;
			}

			setSelectionRow(row);

			node = (DefaultMutableTreeNode) getLastSelectedPathComponent();

			if (node != null) {

				setSelectedProcessor(node);

				showConextMenu(e.getX(), e.getY(), node);
			}
		}

	}

	private void setSelectedProcessor(DefaultMutableTreeNode node) {

		String s = node.getUserObject().toString();

		String ss = s;

		if (s.startsWith("<html>")) {
			ss = s.substring(s.indexOf("'>") + 2, s.indexOf("</")).trim();
		}

		if (node.isRoot()) {

			VPXUtilities.setCurrentProcessor("", "", "");

		} else {

			VPXUtilities.setCurrentProcessor(((DefaultMutableTreeNode) node.getParent()).getUserObject().toString(),
					ss.substring(0, ss.indexOf(")") + 1), ss.substring(ss.indexOf(")") + 1));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void reNameSelectedProcessorNode() {

		DefaultMutableTreeNode f = (DefaultMutableTreeNode) VPX_ProcessorTree.this.getLastSelectedPathComponent();

		String old_Value = f.getUserObject().toString();

		String old_name = old_Value.substring(old_Value.indexOf("(") + 1, old_Value.indexOf(")"));

		parent.updateStatus("Rename processor " + old_name);

		String new_Name = JOptionPane.showInputDialog(parent, "New Name", old_name);

		if (new_Name != null) {

			// VPXUtilities.getSelectedSubSystem(VPX_ProcessorTree.this.getLastSelectedPathComponent().toString()).setName(new_Name);

			String de = old_Value.substring(0, old_Value.indexOf("(")) + "(" + new_Name + ")";

			f.setUserObject(de);

			parent.updateLog("Processor name " + old_name + " changed to " + new_Name);

			parent.updateStatus("Processor name " + old_name + " changed to " + new_Name);

			VPX_ProcessorTree.this.updateUI();

		} else {

			parent.updateStatus("Rename processor " + old_name + " interrupted");
		}
	}

	private void createPopupMenu() {

		// Creating MenuItems

		// File Menus
		vpx_Menu_File_AliasConfig = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Config"));

		vpx_Menu_File_AliasConfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				parent.showAliasConfig();

			}
		});

		vpx_Menu_File_Detail = ComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Detail"));

		vpx_Menu_File_Detail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showDetail();

			}
		});

		// Window Menus
		vpx_Menu_Window_MemoryBrowser = ComponentFactory

		.createJMenuItem(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (VPX_ETHWindow.MAX_MEMORY_BROWSER - VPX_ETHWindow.currentNoofMemoryView) + " ) ");

		vpx_Menu_Window_MemoryBrowser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showMemoryBrowser();

			}
		});

		vpx_Menu_Window_MemoryPlot = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.MemoryPlot")
				+ " ( " + (VPX_ETHWindow.MAX_MEMORY_PLOT - VPX_ETHWindow.currentNoofMemoryPlot) + " ) ");

		vpx_Menu_Window_MemoryPlot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showMemoryPlot();

			}
		});

		vpx_Menu_Window_EthFlash = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.EthFlash"));

		vpx_Menu_Window_EthFlash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showEthFlash();

			}
		});

		vpx_Menu_Window_Amplitude = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Amplitude"));

		vpx_Menu_Window_Amplitude.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showAmplitude();

			}
		});
		vpx_Menu_Window_Waterfall = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Waterfall"));

		vpx_Menu_Window_Waterfall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showWaterfall();

			}
		});

		vpx_Menu_Window_MAD = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.MAD"));

		vpx_Menu_Window_MAD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showMAD();

			}
		});

		vpx_Menu_Window_BIST = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.BIST"));

		vpx_Menu_Window_BIST.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showBIST();

			}
		});

		vpx_Menu_Window_Execution = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Execute"));

		vpx_Menu_Window_Execution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showExecution();

			}
		});

		vpx_Menu_Window_P2020Config = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.P2020Config"));

		vpx_Menu_Window_P2020Config.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showVLAN(0);

			}
		});

		vpx_Menu_Window_ChangeIP = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.ChangeIP"));

		vpx_Menu_Window_ChangeIP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showVLAN(1);

			}
		});
		// Tools Menus

		vpx_Menu_Tools_ChangePWD = ComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.ChangePWD"));

		vpx_Menu_Tools_ChangePWD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showChangePassword();

			}
		});

	}

	private void showConextMenu(int x, int y, DefaultMutableTreeNode node) {

		int i = node.getLevel();

		if (i == 0) {

		} else if (i == 1) {

			if (node.isLeaf()) {

				if (node.getUserObject().toString().contains("P2020")) {

					i = 3;

				} else {

					i = 2;
				}
			}
		}
		if (i == 2) {

			if (node.getUserObject().toString().contains("P2020")) {

				i = 3;

			} else {

				i = 2;
			}

		}

		getPopupMenu(i).show(this, x, y);
	}

	public JPopupMenu getPopupMenu(int nodeLevel) {

		vpx_contextMenu.removeAll();

		vpx_Menu_Window_MemoryBrowser.setText(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (VPX_ETHWindow.MAX_MEMORY_BROWSER - VPX_ETHWindow.currentNoofMemoryView) + " ) ");

		vpx_Menu_Window_MemoryPlot.setText((rBundle.getString("Menu.Window.MemoryPlot") + " ( "
				+ (VPX_ETHWindow.MAX_MEMORY_PLOT - VPX_ETHWindow.currentNoofMemoryPlot) + " ) "));

		if (nodeLevel == 0) {

			vpx_contextMenu.add(vpx_Menu_File_AliasConfig);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			// Window Menu Items
			vpx_contextMenu.add(vpx_Menu_Window_MemoryBrowser);

			vpx_contextMenu.add(vpx_Menu_Window_MemoryPlot);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_Amplitude);

			vpx_contextMenu.add(vpx_Menu_Window_Waterfall);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_MAD);

			vpx_contextMenu.add(vpx_Menu_Window_EthFlash);

			vpx_contextMenu.add(vpx_Menu_Window_BIST);

			vpx_contextMenu.add(vpx_Menu_Window_Execution);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			// Tools Menu Items
			vpx_contextMenu.add(vpx_Menu_Tools_ChangePWD);

			vpx_contextMenu.add(vpx_Menu_File_Detail);

		} else if (nodeLevel == 1) {

			vpx_contextMenu.add(vpx_Menu_File_AliasConfig);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_MAD);

			vpx_contextMenu.add(vpx_Menu_Window_EthFlash);

			vpx_contextMenu.add(vpx_Menu_Window_BIST);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_File_Detail);

		} else if (nodeLevel == 2) {

			vpx_contextMenu.add(vpx_Menu_Window_MemoryBrowser);

			vpx_contextMenu.add(vpx_Menu_Window_MemoryPlot);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_Amplitude);

			vpx_contextMenu.add(vpx_Menu_Window_Waterfall);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_MAD);

			vpx_contextMenu.add(vpx_Menu_Window_EthFlash);

			vpx_contextMenu.add(vpx_Menu_Window_BIST);

			vpx_contextMenu.add(vpx_Menu_Window_Execution);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_File_Detail);

		} else if (nodeLevel == 3) {

			vpx_contextMenu.add(vpx_Menu_Window_P2020Config);

			vpx_contextMenu.add(vpx_Menu_Window_ChangeIP);

			vpx_contextMenu.add(ComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_File_Detail);
		}

		return vpx_contextMenu;
	}

	class ProcessorMonitor extends SwingWorker<Void, Void> {

		String ip;

		long response;

		long difference;

		public ProcessorMonitor() {

		}

		@Override
		protected Void doInBackground() throws Exception {

			DefaultMutableTreeNode node = null;

			while (true) {

				Enumeration<DefaultMutableTreeNode> e = systemRootNode.depthFirstEnumeration();

				while (e.hasMoreElements()) {

					node = e.nextElement();

					if (node.isLeaf()) {

						ip = node.getUserObject().toString();

						ip = ip.substring(ip.indexOf(">(") + 1, ip.indexOf("</"));

						response = getRespondTimeFromVPXSystem(ip);

						difference = (System.currentTimeMillis() - response) / 1000;

						if (difference > MAXRESPONSETIMEOUT) {

							if (ip.contains("(P2020)")) {

								node.setUserObject(getNodeUserObject(ip, false));
							} else {
								node.setUserObject(getNodeUserObject(ip, false, false, false));
							}
						}

					}
				}

				repaint();

				Thread.sleep(5000);
			}
		}

		public void startMonitor() {
			execute();
		}

		public void stopMonitor() {
			cancel(true);
		}
	}

	class VPX_Processor_Leaf_Cell_Editor extends DefaultTreeCellEditor {

		public VPX_Processor_Leaf_Cell_Editor(JTree tree, DefaultTreeCellRenderer renderer) {
			super(tree, renderer);
		}

		public VPX_Processor_Leaf_Cell_Editor(JTree tree, DefaultTreeCellRenderer renderer, TreeCellEditor editor) {
			super(tree, renderer, editor);
		}
	}

	class VPX_Processor_Leaf_Editor extends AbstractCellEditor implements TreeCellEditor, ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2053120822277708914L;

		String old_Value;
		String old_name;

		JTextField textField;

		public VPX_Processor_Leaf_Editor() {
			textField = new JTextField();
			textField.addActionListener(this);
		}

		public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
				boolean leaf, int row) {

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

			old_Value = node.toString();

			old_name = old_Value.substring(old_Value.indexOf("(") + 1, old_Value.indexOf(")"));

			textField.setText(old_name);

			return textField;
		}

		public boolean isCellEditable(EventObject event) {
			// System.out.println("event = " + event);
			// Get initial setting
			boolean returnValue = super.isCellEditable(event);
			if (event instanceof MouseEvent) {
				// If still possible, check if current tree node is a leaf
				if (returnValue) {
					JTree tree = (JTree) event.getSource();
					Object node = tree.getLastSelectedPathComponent();
					if ((node != null) && (node instanceof TreeNode)) {
						TreeNode treeNode = (TreeNode) node;
						returnValue = treeNode.isLeaf();
					}
				}
			}
			return returnValue;
		}

		public Object getCellEditorValue() {

			return old_Value.replaceAll(old_name, textField.getText());
		}

		/** Press enter key to save the edited value. */
		public void actionPerformed(ActionEvent e) {
			super.stopCellEditing();
		}
	}
}

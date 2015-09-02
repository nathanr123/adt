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
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
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

	private final JPopupMenu vpx_contextMenu = new JPopupMenu();

	// File Menu Items
	private JMenuItem vpx_Menu_File_AliasConfig;

	private JMenuItem vpx_Menu_File_Detail;

	private JMenuItem vpx_Menu_File_Refresh;

	// Window Menu Items
	private JMenuItem vpx_Menu_Window_MemoryBrowser;

	private JMenuItem vpx_Menu_Window_MemoryPlot;

	private JMenuItem vpx_Menu_Window_EthFlash;

	private JMenuItem vpx_Menu_Window_Amplitude;

	private JMenuItem vpx_Menu_Window_Waterfall;

	private JMenuItem vpx_Menu_Window_MAD;

	private JMenuItem vpx_Menu_Window_BIST;

	private JMenuItem vpx_Menu_Window_Reboot;

	private JMenuItem vpx_Menu_Window_Execution;

	private JMenuItem vpx_Menu_Window_P2020Config;

	private JMenuItem vpx_Menu_Window_ChangeIP;

	// Tools Menu Items
	private JMenuItem vpx_Menu_Tools_ChangePWD;

	private VPX_ETHWindow parent;

	private VPXSystem system = new VPXSystem();

	private static VPX_ProcessorNode systemRootNode = new VPX_ProcessorNode(VPX_ProcessorNode.SYSTEM_NODE,
			VPXConstants.VPXROOT);

	private ProcessorMonitor monitor = new ProcessorMonitor();

	private ResourceBundle rBundle = VPXUtilities.getResourceBundle();

	private VPX_ProcessorNode unlist;

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

		system = VPXSessionManager.getVPXSystem();

		addMouseListener(this);

		setCellRenderer(new VPX_ProcessorTreeCellRenderer());

		setRowHeight(20);

		getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

	}

	public int getProcessorCount() {

		int count = 0;

		VPX_ProcessorNode node = null;

		@SuppressWarnings("unchecked")
		Enumeration<VPX_ProcessorNode> e = systemRootNode.depthFirstEnumeration();

		while (e.hasMoreElements()) {

			node = e.nextElement();

			if (node.isLeaf()) {

				count++;
			}
		}

		return count;
	}

	public void setVPXSystem(VPXSystem sys) {

		this.system = sys;

		loadSystemRootNode();

		for (int i = 0; i < getRowCount(); i++) {
			expandRow(i);
		}
	}

	public void updateVPXSystemTree() {

		this.system = VPXSessionManager.getVPXSystem();

		loadSystemRootNode();

		for (int i = 0; i < getRowCount(); i++) {

			expandRow(i);
		}
	}

	public boolean isSubSystemsAvailable() {

		return (system.getSubsystem().size() > 0);
	}

	public void updateProcessorResponse(String ip, String res) {

		long time = System.currentTimeMillis();

		boolean isNotFound = false;

		VPX_ProcessorNode node = null;

		@SuppressWarnings("unchecked")
		Enumeration<VPX_ProcessorNode> e = systemRootNode.depthFirstEnumeration();

		while (e.hasMoreElements()) {

			node = e.nextElement();

			if (node.isProcessorNode()) {

				if (node.getNodeIP().equals(ip)) {

					parseResponse(node, res, time);

					isNotFound = true;

					break;
				}

			}
		}

		if (!isNotFound) {

			Processor p = new Processor(ip, time, res);

			addToUnlistedNode(p);

		}

		setRespondTimetoVPXSystem(ip, time);

	}

	private void setRespondTimetoVPXSystem(String ip, long time) {

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

	private VPX_ProcessorNode createProcessorNode(String ip, String subsystem, String msg) {

		VPX_ProcessorNode node = null;

		String w = msg.substring(2, 3);

		String a = msg.substring(3, 4);

		if (msg.startsWith("P2")) {

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_P2020, true);

		} else if (msg.startsWith("D1")) {

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_DSP1, true, w.equals("1"),
					a.equals("1"));

		} else if (msg.startsWith("D2")) {

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_DSP2, true, w.equals("1"),
					a.equals("1"));

		}

		return node;
	}

	private VPX_ProcessorNode createProcessorNode(String ip, String subsystem, String msg, long time) {

		VPX_ProcessorNode node = null;

		String w = msg.substring(2, 3);

		String a = msg.substring(3, 4);

		if (msg.startsWith("P2")) {

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_P2020, true);

		} else if (msg.startsWith("D1")) {

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_DSP1, true, w.equals("1"),
					a.equals("1"));

		} else if (msg.startsWith("D2")) {

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_DSP2, true, w.equals("1"),
					a.equals("1"));

		}

		node.setRespondedTime(time);

		return node;
	}

	private void parseResponse(VPX_ProcessorNode node, String msg, long time) {

		if (msg.length() == 6) {

			int periods = Integer.parseInt(msg.substring(4, 6).trim());

			if (periods != VPXSessionManager.getCurrentPeriodicity() || (node.getRespondedTime() == 0)) {

				parent.updatePeriodicity(node.getNodeIP(), VPXSessionManager.getCurrentPeriodicity());
			}

			if (msg.startsWith("P2")) {

				node.setStatus(true);

			} else {

				String w = msg.substring(2, 3).trim();

				String a = msg.substring(3, 4).trim();

				node.setStatus(true, w.equals("1"), a.equals("1"));

			}

			node.setRespondedTime(time);
		}
	}

	private void addToUnlistedNode(Processor proc) {

		boolean isExists = false;

		List<Processor> unlisted = system.getUnListed();

		VPX_ProcessorNode node = null;

		if (unlisted.size() == 0) {

			unlist = new VPX_ProcessorNode(VPX_ProcessorNode.SUBSYSTEM_NODE, VPXConstants.VPXUNLIST);

			systemRootNode.add(unlist);

			isExists = false;

		} else {

			@SuppressWarnings("unchecked")
			Enumeration<VPX_ProcessorNode> e = unlist.depthFirstEnumeration();

			while (e.hasMoreElements()) {

				VPX_ProcessorNode nodes = e.nextElement();

				if (nodes.getNodeIP().equals(proc.getiP_Addresses())) {

					node = nodes;

					isExists = true;

					break;

				}
			}
		}

		if (isExists) {

			parseResponse(node, proc.getMsg(), proc.getResponseTime());

		} else {

			unlist.add(createProcessorNode(proc.getiP_Addresses(), unlist.getNodeName(), proc.getMsg(),
					proc.getResponseTime()));

			system.addToUnListed(proc);

			parent.updatePeriodicity(proc.getiP_Addresses(), VPXSessionManager.getCurrentPeriodicity());

		}

		refresh();

	}

	private void loadSystemRootNode() {

		systemRootNode.removeAllChildren();

		List<VPXSubSystem> subSystems = system.getSubsystem();

		if (subSystems != null) {

			for (Iterator<VPXSubSystem> iterator = subSystems.iterator(); iterator.hasNext();) {

				VPXSubSystem subSystem = iterator.next();

				VPX_ProcessorNode subSystemNode = new VPX_ProcessorNode(VPX_ProcessorNode.SUBSYSTEM_NODE,
						subSystem.getSubSystem());

				VPX_ProcessorNode p2020Node = new VPX_ProcessorNode(subSystem.getIpP2020(),
						subSystemNode.getNodeName(), PROCESSOR_LIST.PROCESSOR_P2020, false);

				VPX_ProcessorNode dsp1Node = new VPX_ProcessorNode(subSystem.getIpDSP1(), subSystemNode.getNodeName(),
						PROCESSOR_LIST.PROCESSOR_DSP1, false, false, false);

				VPX_ProcessorNode dsp2Node = new VPX_ProcessorNode(subSystem.getIpDSP2(), subSystemNode.getNodeName(),
						PROCESSOR_LIST.PROCESSOR_DSP2, false, false, false);

				subSystemNode.add(p2020Node);

				subSystemNode.add(dsp1Node);

				subSystemNode.add(dsp2Node);

				systemRootNode.add(subSystemNode);
			}
		}

		List<Processor> unlisted = system.getUnListed();

		if (unlisted.size() > 0) {

			VPX_ProcessorNode unlist = new VPX_ProcessorNode(VPX_ProcessorNode.SUBSYSTEM_NODE, VPXConstants.VPXUNLIST);

			systemRootNode.add(unlist);

			for (Iterator<Processor> iterator = unlisted.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				unlist.add(createProcessorNode(processor.getiP_Addresses(), unlist.getNodeName(), processor.getMsg()));
			}
		}

		// refresh();

	}

	private void loadSystemRootNodeWithoutUnListed() {

		systemRootNode.removeAllChildren();

		List<VPXSubSystem> subSystems = system.getSubsystem();

		system.clearUnlisted();

		if (subSystems != null) {

			for (Iterator<VPXSubSystem> iterator = subSystems.iterator(); iterator.hasNext();) {

				VPXSubSystem subSystem = iterator.next();

				VPX_ProcessorNode subSystemNode = new VPX_ProcessorNode(VPX_ProcessorNode.SUBSYSTEM_NODE,
						subSystem.getSubSystem());

				VPX_ProcessorNode p2020Node = new VPX_ProcessorNode(subSystem.getIpP2020(),
						subSystemNode.getNodeName(), PROCESSOR_LIST.PROCESSOR_P2020, false);

				VPX_ProcessorNode dsp1Node = new VPX_ProcessorNode(subSystem.getIpDSP1(), subSystemNode.getNodeName(),
						PROCESSOR_LIST.PROCESSOR_DSP1, false, false, false);

				VPX_ProcessorNode dsp2Node = new VPX_ProcessorNode(subSystem.getIpDSP2(), subSystemNode.getNodeName(),
						PROCESSOR_LIST.PROCESSOR_DSP2, false, false, false);

				subSystemNode.add(p2020Node);

				subSystemNode.add(dsp1Node);

				subSystemNode.add(dsp2Node);

				systemRootNode.add(subSystemNode);
			}
		}

		// updateUI();

		// refresh();

	}

	public void refreshProcessorsStatus() {

		long response = 0, difference = 0;

		VPX_ProcessorNode node = null;

		@SuppressWarnings("unchecked")
		Enumeration<VPX_ProcessorNode> e = systemRootNode.depthFirstEnumeration();

		while (e.hasMoreElements()) {

			node = e.nextElement();

			if (node.isProcessorNode()) {

				response = node.getRespondedTime();

				difference = (System.currentTimeMillis() - response) / 1000;

				if (difference > (VPXSessionManager.getCurrentPeriodicity() + VPXConstants.MAXRESPONSETIMEOUT)) {

					if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

						node.setStatus(false);

					} else {

						node.setStatus(false, false, false);
					}
				}

			}
		}

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

			VPX_ScanWindow ir = new VPX_ScanWindow();

			ir.setVisible(true);

		}
	}

	private class VPX_ProcessorTreeCellRenderer extends DefaultTreeCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3402533538077987491L;

		public VPX_ProcessorTreeCellRenderer() {

		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

			String nodo = "";

			VPX_ProcessorNode dNode = ((VPX_ProcessorNode) value);

			if (sel) {
				setTextSelectionColor(Color.white);
			}

			if (dNode.getUserObject() != null)
				nodo = ((VPX_ProcessorNode) value).getUserObject().toString();

			if (nodo.startsWith(VPXSystem.class.getSimpleName())) {

				setIcon(VPXConstants.Icons.ICON_VPXSYSTEM);

			} else if (dNode.isLeaf()) {

				setIcon(VPXConstants.Icons.ICON_PROCESSOR);

			} else {

				setIcon(VPXConstants.Icons.ICON_VPXSUBSYSTEM);

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

		VPX_ProcessorNode node = (VPX_ProcessorNode) getLastSelectedPathComponent();

		if (e.getButton() == 1) {

			if (node != null) {
				if (node.isProcessorNode()) {

					setSelectedProcessor(node);

				} else {
					VPXSessionManager.setCurrentProcessor("", "", "");
				}
			}

		} else if (e.getButton() == 3) {

			int row = getRowForLocation(e.getX(), e.getY());

			if (row == -1) {

				return;
			}

			setSelectionRow(row);

			node = (VPX_ProcessorNode) getLastSelectedPathComponent();

			if (node != null) {

				setSelectedProcessor(node);

				showConextMenu(e.getX(), e.getY(), node);
			}
		}

	}

	private void setSelectedProcessor(VPX_ProcessorNode node) {

		if (node.isProcessorNode()) {

			VPXSessionManager.setCurrentProcessor(node.getSubSystemName(), node.getNodeTypeString(), node.getNodeIP());

		} else {

			VPXSessionManager.setCurrentProcessor("", "", "");
		}

		parent.updateProcessorSettings();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void createPopupMenu() {

		// Creating MenuItems

		// File Menus
		vpx_Menu_File_AliasConfig = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Config"),
				VPXConstants.Icons.ICON_CONFIG);

		vpx_Menu_File_AliasConfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				parent.showAliasConfig();

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

		vpx_Menu_File_Refresh = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Refresh"),
				VPXConstants.Icons.ICON_REFRESH);

		vpx_Menu_File_Refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				refreshProcessorsStatus();

			}
		});

		// Window Menus
		vpx_Menu_Window_MemoryBrowser = VPXComponentFactory

		.createJMenuItem(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (VPXConstants.MAX_MEMORY_BROWSER - VPX_ETHWindow.currentNoofMemoryView) + " ) ",
				VPXConstants.Icons.ICON_MEMORY);

		vpx_Menu_Window_MemoryBrowser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showMemoryBrowser();

			}
		});

		vpx_Menu_Window_MemoryPlot = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.MemoryPlot")
				+ " ( " + (VPXConstants.MAX_MEMORY_PLOT - VPX_ETHWindow.currentNoofMemoryPlot) + " ) ",
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

		vpx_Menu_Window_Amplitude = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Amplitude"));

		vpx_Menu_Window_Amplitude.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showAmplitude();

			}
		});
		vpx_Menu_Window_Waterfall = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Waterfall"));

		vpx_Menu_Window_Waterfall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showWaterfall();

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

		vpx_Menu_Window_Reboot = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Reboot"));

		vpx_Menu_Window_Reboot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.setReboot(VPXSessionManager.getCurrentProcessor());

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

		vpx_Menu_Window_Execution = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Execute"),
				VPXConstants.Icons.ICON_EXECUTION);

		vpx_Menu_Window_Execution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showExecution();

			}
		});

		vpx_Menu_Window_P2020Config = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.P2020Config"));

		vpx_Menu_Window_P2020Config.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showVLAN(0);

			}
		});

		vpx_Menu_Window_ChangeIP = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.ChangeIP"));

		vpx_Menu_Window_ChangeIP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showVLAN(1);

			}
		});
		// Tools Menus

		vpx_Menu_Tools_ChangePWD = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.ChangePWD"));

		vpx_Menu_Tools_ChangePWD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showChangePassword();

			}
		});

	}

	private void refresh() {

		((DefaultTreeModel) getModel()).reload();

		for (int i = 0; i < getRowCount(); i++) {

			expandRow(i);
		}

	}

	private void showConextMenu(int x, int y, VPX_ProcessorNode node) {

		int i = node.getLevel();

		if (i == 0) {

		} else if (i == 1) {

			if (node.isLeaf()) {

				if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

					i = 3;

				} else {

					i = 2;
				}
			}
		}
		if (i == 2) {

			if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

				i = 3;

			} else {

				i = 2;
			}

		}

		getPopupMenu(i).show(this, x, y);
	}

	public void refreshProcessorTree() {

		loadSystemRootNodeWithoutUnListed();

	}

	public JPopupMenu getPopupMenu(int nodeLevel) {

		vpx_contextMenu.removeAll();

		vpx_Menu_Window_MemoryBrowser.setText(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
				+ (VPXConstants.MAX_MEMORY_BROWSER - VPX_ETHWindow.currentNoofMemoryView) + " ) ");

		vpx_Menu_Window_MemoryPlot.setText((rBundle.getString("Menu.Window.MemoryPlot") + " ( "
				+ (VPXConstants.MAX_MEMORY_PLOT - VPX_ETHWindow.currentNoofMemoryPlot) + " ) "));

		if (nodeLevel == 0) {

			vpx_contextMenu.add(vpx_Menu_File_AliasConfig);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			// Window Menu Items
			vpx_contextMenu.add(vpx_Menu_Window_MemoryBrowser);

			vpx_contextMenu.add(vpx_Menu_Window_MemoryPlot);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_Amplitude);

			vpx_contextMenu.add(vpx_Menu_Window_Waterfall);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_MAD);

			vpx_contextMenu.add(vpx_Menu_Window_EthFlash);

			vpx_contextMenu.add(vpx_Menu_Window_BIST);

			vpx_contextMenu.add(vpx_Menu_Window_Execution);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			// Tools Menu Items
			vpx_contextMenu.add(vpx_Menu_Tools_ChangePWD);

			vpx_contextMenu.add(vpx_Menu_File_Refresh);

			vpx_contextMenu.add(vpx_Menu_File_Detail);

		} else if (nodeLevel == 1) {

			vpx_contextMenu.add(vpx_Menu_File_AliasConfig);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_MAD);

			vpx_contextMenu.add(vpx_Menu_Window_EthFlash);

			vpx_contextMenu.add(vpx_Menu_Window_BIST);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_File_Refresh);

			vpx_contextMenu.add(vpx_Menu_File_Detail);

		} else if (nodeLevel == 2) {

			vpx_contextMenu.add(vpx_Menu_Window_MemoryBrowser);

			vpx_contextMenu.add(vpx_Menu_Window_MemoryPlot);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_Amplitude);

			vpx_contextMenu.add(vpx_Menu_Window_Waterfall);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_Window_MAD);

			vpx_contextMenu.add(vpx_Menu_Window_EthFlash);

			vpx_contextMenu.add(vpx_Menu_Window_Execution);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_File_Refresh);

			vpx_contextMenu.add(vpx_Menu_File_Detail);

		} else if (nodeLevel == 3) {

			vpx_contextMenu.add(vpx_Menu_Window_BIST);

			vpx_contextMenu.add(vpx_Menu_Window_P2020Config);

			vpx_contextMenu.add(vpx_Menu_Window_ChangeIP);

			vpx_contextMenu.add(vpx_Menu_Window_Reboot);

			vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

			vpx_contextMenu.add(vpx_Menu_File_Refresh);

			vpx_contextMenu.add(vpx_Menu_File_Detail);
		}

		return vpx_contextMenu;
	}

	@Override
	public boolean isPathSelected(TreePath path) {
		return !(((VPX_ProcessorNode) path.getLastPathComponent()).isLeaf());
	}

	class ProcessorMonitor extends SwingWorker<Void, Void> {

		String ip;

		long response;

		long difference;

		public ProcessorMonitor() {

		}

		@Override
		protected Void doInBackground() throws Exception {

			VPX_ProcessorNode node = null;

			while (true) {

				@SuppressWarnings("unchecked")
				Enumeration<VPX_ProcessorNode> e = systemRootNode.depthFirstEnumeration();

				while (e.hasMoreElements()) {

					node = e.nextElement();

					if (node.isProcessorNode()) {

						response = node.getRespondedTime();

						difference = (System.currentTimeMillis() - response) / 1000;

						if (difference > (VPXSessionManager.getCurrentPeriodicity() + VPXConstants.MAXRESPONSETIMEOUT)) {

							if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

								node.setStatus(false);

							} else {

								node.setStatus(false, false, false);
							}

						} else {

							if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

								node.setStatus(true);

							} else {

								node.setStatus(true, node.isWaterfall(), node.isAmplitude());
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

			VPX_ProcessorNode node = (VPX_ProcessorNode) value;

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

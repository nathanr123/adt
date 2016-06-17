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

import javax.swing.AbstractCellEditor;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.ToolTipManager;
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

public class VPX_ProcessorTree extends JTree implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4220657730002684130L;

	private final JPopupMenu vpx_contextMenu = new JPopupMenu();

	private JMenuItem vpx_Cxt_AliasConfig;

	private JMenuItem vpx_Cxt_Detail;

	// private JMenuItem vpx_Cxt_Refresh;

	private JMenuItem vpx_Cxt_MemoryBrowser;

	private JMenuItem vpx_Cxt_MemoryPlot;

	private JMenuItem vpx_Cxt_EthFlash;

	private JMenuItem vpx_Cxt_Spectrum;

	private JMenuItem vpx_Cxt_MAD;

	private JMenuItem vpx_Cxt_BIST;

	private JMenuItem vpx_Cxt_Reboot;

	private JMenuItem vpx_Cxt_Execution;

	private JMenuItem vpx_Cxt_P2020Config;

	private JMenuItem vpx_Cxt_ChangeIP;

	private JMenuItem vpx_Cxt_ChangePWD;

	private JMenu vpx_Cxt_SubSystem;

	private JMenuItem vpx_Cxt_Rename;

	private JMenuItem vpx_Cxt_Remove;

	private VPX_ETHWindow parent;

	private VPXSystem system = new VPXSystem();

	private static VPX_ProcessorNode systemRootNode = new VPX_ProcessorNode(VPX_ProcessorNode.SYSTEM_NODE,
			VPXConstants.VPXROOT);

	// private ProcessorMonitor monitor = new ProcessorMonitor();

	private ProcMonitor procMonitor;

	private ResourceBundle rBundle = VPXUtilities.getResourceBundle();

	private VPX_ProcessorNode unlist;

	private JMenuItem vpx_Cxt_New;

	private JMenuItem vpx_Cxt_Reload;

	/**
	 * 
	 */
	public VPX_ProcessorTree() {
		initTree();

		createPopupMenu();

		// monitor.execute();

		startMonitor();
	}

	/**
	 * @param value
	 */
	public VPX_ProcessorTree(Object[] value) {

		super(value);

		initTree();

		createPopupMenu();

		// monitor.execute();
		startMonitor();
	}

	/**
	 * @param value
	 */
	public VPX_ProcessorTree(Vector<?> value) {

		super(value);

		initTree();

		createPopupMenu();

		// monitor.execute();

		startMonitor();
	}

	/**
	 * @param value
	 */
	public VPX_ProcessorTree(Hashtable<?, ?> value) {

		super(value);

		initTree();

		createPopupMenu();

		// monitor.execute();

		startMonitor();
	}

	/**
	 * @param root
	 */
	public VPX_ProcessorTree(VPX_ETHWindow prnt, TreeNode root) {

		super(root);

		this.parent = prnt;

		initTree();

		createPopupMenu();

		// monitor.execute();

		startMonitor();
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

		// monitor.execute();

		startMonitor();

	}

	/**
	 * @param newModel
	 */
	public VPX_ProcessorTree(TreeModel newModel) {

		super(newModel);

		initTree();

		createPopupMenu();

		// monitor.execute();

		startMonitor();
	}

	/**
	 * @param root
	 * @param asksAllowsChildren
	 */
	public VPX_ProcessorTree(TreeNode root, boolean asksAllowsChildren) {

		super(root, asksAllowsChildren);

		initTree();

		createPopupMenu();

		// monitor.execute();

		startMonitor();
	}

	private void initTree() {

		system = VPXSessionManager.getVPXSystem();

		addMouseListener(this);

		setCellRenderer(new VPX_ProcessorTreeCellRenderer());

		setRowHeight(20);

		getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

		ToolTipManager.sharedInstance().registerComponent(this);

	}

	private void startMonitor() {

		procMonitor = new ProcMonitor();

		Thread th = new Thread(procMonitor);

		th.start();

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
	}

	public boolean isSubSystemsAvailable() {

		return (this.system.getSubsystem().size() > 0);
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

			parent.updateConsoleFilters();

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

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_DSP1, true,
					(w.equals("1") || a.equals("1")));

		} else if (msg.startsWith("D2")) {

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_DSP2, true,
					(w.equals("1") || a.equals("1")));

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

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_DSP1, true,
					(w.equals("1") || a.equals("1")));

		} else if (msg.startsWith("D2")) {

			node = new VPX_ProcessorNode(ip, subsystem, PROCESSOR_LIST.PROCESSOR_DSP2, true,
					(w.equals("1") || a.equals("1")));

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

				node.setStatus(true, (w.equals("1") || a.equals("1")));

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

		VPX_ProcessorNode p2020Node = null;

		VPX_ProcessorNode dsp1Node = null;

		VPX_ProcessorNode dsp2Node = null;

		systemRootNode.removeAllChildren();

		List<VPXSubSystem> subSystems = this.system.getSubsystem();

		if (subSystems != null) {

			for (Iterator<VPXSubSystem> iterator = subSystems.iterator(); iterator.hasNext();) {

				VPXSubSystem subSystem = iterator.next();

				VPX_ProcessorNode subSystemNode = new VPX_ProcessorNode(VPX_ProcessorNode.SUBSYSTEM_NODE,
						subSystem.getSubSystem());

				p2020Node = new VPX_ProcessorNode(subSystem.getIpP2020(), subSystemNode.getNodeName(),
						PROCESSOR_LIST.PROCESSOR_P2020, false);

				if (!subSystem.getIpDSP1().isEmpty())
					dsp1Node = new VPX_ProcessorNode(subSystem.getIpDSP1(), subSystemNode.getNodeName(),
							PROCESSOR_LIST.PROCESSOR_DSP1, false, false);

				if (!subSystem.getIpDSP2().isEmpty())
					dsp2Node = new VPX_ProcessorNode(subSystem.getIpDSP2(), subSystemNode.getNodeName(),
							PROCESSOR_LIST.PROCESSOR_DSP2, false, false);

				subSystemNode.add(p2020Node);

				if (dsp1Node != null)
					subSystemNode.add(dsp1Node);

				if (dsp2Node != null)
					subSystemNode.add(dsp2Node);

				systemRootNode.add(subSystemNode);

				p2020Node = null;

				dsp1Node = null;

				dsp2Node = null;
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

		refresh();

	}

	private void reload() {

		VPX_ProcessorNode p2020Node = null;

		VPX_ProcessorNode dsp1Node = null;

		VPX_ProcessorNode dsp2Node = null;

		systemRootNode.removeAllChildren();

		List<VPXSubSystem> subSystems = system.getSubsystem();

		system.clearUnlisted();

		if (subSystems != null) {

			for (Iterator<VPXSubSystem> iterator = subSystems.iterator(); iterator.hasNext();) {

				VPXSubSystem subSystem = iterator.next();

				VPX_ProcessorNode subSystemNode = new VPX_ProcessorNode(VPX_ProcessorNode.SUBSYSTEM_NODE,
						subSystem.getSubSystem());

				p2020Node = new VPX_ProcessorNode(subSystem.getIpP2020(), subSystemNode.getNodeName(),
						PROCESSOR_LIST.PROCESSOR_P2020, false);

				if (!subSystem.getIpDSP1().isEmpty())
					dsp1Node = new VPX_ProcessorNode(subSystem.getIpDSP1(), subSystemNode.getNodeName(),
							PROCESSOR_LIST.PROCESSOR_DSP1, false, false);

				if (!subSystem.getIpDSP2().isEmpty())
					dsp2Node = new VPX_ProcessorNode(subSystem.getIpDSP2(), subSystemNode.getNodeName(),
							PROCESSOR_LIST.PROCESSOR_DSP2, false, false);

				subSystemNode.add(p2020Node);

				if (dsp1Node != null)
					subSystemNode.add(dsp1Node);

				if (dsp2Node != null)
					subSystemNode.add(dsp2Node);

				systemRootNode.add(subSystemNode);

				p2020Node = null;

				dsp1Node = null;

				dsp2Node = null;
			}
		}

		refresh();
	}

	public void refreshStatus() {

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

						node.setStatus(false, false);
					}
				}

			}
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

			setToolTipText(dNode.getToolTip());

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

		if (e.isPopupTrigger()) {
			nodePopupEvent(e);
		} else {
			if (e.getButton() == 1) {

				VPX_ProcessorNode node = (VPX_ProcessorNode) getLastSelectedPathComponent();

				if (node != null) {

					enableNodeComponents(node);

					if (node.isProcessorNode()) {

						setSelectedProcessor(node);

					} else if (node.isSubSytemNode()) {
						VPXSessionManager.setCurrentProcessor("", "", "");

						parent.updateProcessorSettings();

					} else if (node.isRootNode()) {

						VPXSessionManager.setCurrentProcessor("", "", "");

						parent.updateProcessorSettings();

					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (e.isPopupTrigger())
			nodePopupEvent(e);
	}

	private void nodePopupEvent(MouseEvent e) {

		int x = e.getX();

		int y = e.getY();

		JTree tree = (JTree) e.getSource();

		TreePath path = tree.getPathForLocation(x, y);

		if (path == null)
			showConextMenu(e.getX(), e.getY(), systemRootNode);
		else {
			VPX_ProcessorNode rightClickedNode = (VPX_ProcessorNode) path.getLastPathComponent();

			TreePath[] selectionPaths = tree.getSelectionPaths();

			// check if node was selected
			boolean isSelected = false;

			if (selectionPaths != null) {

				for (TreePath selectionPath : selectionPaths) {

					if (selectionPath.equals(path)) {

						isSelected = true;
					}
				}
			}
			// if clicked node was not selected, select it
			if (!isSelected) {

				tree.setSelectionPath(path);
			}

			showConextMenu(e.getX(), e.getY(), rightClickedNode);
		}
	}

	private void setSelectedProcessor(VPX_ProcessorNode node) {

		enableNodeComponents(node);

		if (node.isProcessorNode()) {

			VPXSessionManager.setCurrentProcessor(node.getSubSystemName(), node.getNodeTypeString(), node.getNodeIP());

		} else {

			VPXSessionManager.setCurrentProcessor("", "", "");
		}

		parent.updateProcessorSettings();
	}

	private void enableNodeComponents(VPX_ProcessorNode node) {

		if (node.isProcessorNode()) {

			if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

				parent.enableSelectedProcessorMenus(VPXConstants.PROCESSOR_SELECTED_MODE_P2020);

			} else if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_DSP2
					|| node.getNodeType() == PROCESSOR_LIST.PROCESSOR_DSP1) {

				parent.enableSelectedProcessorMenus(VPXConstants.PROCESSOR_SELECTED_MODE_DSP, node);
			}

		} else if (node.isSubSytemNode()) {

			parent.enableSelectedProcessorMenus(VPXConstants.PROCESSOR_SELECTED_MODE_SUBSYSTEM);

		} else if (node.isRootNode()) {

			parent.enableSelectedProcessorMenus(VPXConstants.PROCESSOR_SELECTED_MODE_NONE);
		}

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
		vpx_Cxt_AliasConfig = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Config"),
				VPXConstants.Icons.ICON_CONFIG);

		vpx_Cxt_AliasConfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				parent.showAliasConfig("");

			}
		});

		vpx_Cxt_Detail = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Detail"),
				VPXConstants.Icons.ICON_DETAIL);

		vpx_Cxt_Detail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showDetail();

			}
		});

		vpx_Cxt_Reload = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.File.Reload"),
				VPXConstants.Icons.ICON_REFRESH);

		vpx_Cxt_Reload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				refreshProcessorTree();

			}
		});

		// Window Menus
		vpx_Cxt_MemoryBrowser = VPXComponentFactory

				.createJMenuItem(
						rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
								+ (VPXConstants.MAX_MEMORY_BROWSER - VPX_ETHWindow.currentNoofMemoryView) + " ) ",
						VPXConstants.Icons.ICON_MEMORY);

		vpx_Cxt_MemoryBrowser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showMemoryBrowser();

			}
		});

		vpx_Cxt_MemoryPlot = VPXComponentFactory.createJMenuItem(
				rBundle.getString("Menu.Window.MemoryPlot") + " ( "
						+ (VPXConstants.MAX_MEMORY_PLOT - VPX_ETHWindow.currentNoofMemoryPlot) + " ) ",
				VPXConstants.Icons.ICON_PLOT);

		vpx_Cxt_MemoryPlot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showMemoryPlot();

			}
		});

		vpx_Cxt_EthFlash = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.EthFlash"),
				VPXConstants.Icons.ICON_ETHFLASH);

		vpx_Cxt_EthFlash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showEthFlash();

			}
		});

		vpx_Cxt_Spectrum = VPXComponentFactory.createJMenuItem(
				rBundle.getString("Menu.Window.Spectrum") + " ( "
						+ (VPXConstants.MAX_SPECTRUM - VPX_ETHWindow.currentNoofSpectrum) + " ) ",
				VPXConstants.Icons.ICON_SPECTRUM);

		vpx_Cxt_Spectrum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				TreePath path = getSelectionPath();

				if (path != null) {

					VPX_ProcessorNode rightClickedNode = (VPX_ProcessorNode) getSelectionPath().getLastPathComponent();

					parent.showDataAnalyzer(rightClickedNode.getNodeIP());
				}

			}
		});

		vpx_Cxt_MAD = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.MAD"),
				VPXConstants.Icons.ICON_MAD);

		vpx_Cxt_MAD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showMAD();

			}
		});

		vpx_Cxt_Reboot = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Reboot"));

		vpx_Cxt_Reboot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showBootOption(VPXSessionManager.getCurrentProcessor());

			}
		});

		vpx_Cxt_BIST = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.BIST"),
				VPXConstants.Icons.ICON_BIST);

		vpx_Cxt_BIST.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showBIST();

			}
		});

		vpx_Cxt_Execution = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.Execute"),
				VPXConstants.Icons.ICON_EXECUTION);

		vpx_Cxt_Execution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showExecution();

			}
		});

		vpx_Cxt_P2020Config = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.P2020Config"));

		vpx_Cxt_P2020Config.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showVLAN(1);

			}
		});

		vpx_Cxt_ChangeIP = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Window.ChangeIP"));

		vpx_Cxt_ChangeIP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showVLAN(0);

			}
		});
		// Tools Menus

		vpx_Cxt_ChangePWD = VPXComponentFactory.createJMenuItem(rBundle.getString("Menu.Tool.ChangePWD"));

		vpx_Cxt_ChangePWD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.showChangePassword();

			}
		});

		vpx_Cxt_SubSystem = VPXComponentFactory.createJMenu("Sub System");

		vpx_Cxt_New = VPXComponentFactory.createJMenuItem("Create New SubSystem");

		vpx_Cxt_New.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doCreateNew(getSelectionPaths());
			}
		});

		vpx_Cxt_Rename = VPXComponentFactory.createJMenuItem("Rename SubSystem");

		vpx_Cxt_Rename.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_ProcessorNode nd = (VPX_ProcessorNode) getSelectionModel().getSelectionPath()
						.getLastPathComponent();

				parent.showAliasConfig(nd.getNodeName());

			}
		});

		vpx_Cxt_Remove = VPXComponentFactory.createJMenuItem("Remove SubSystem");

		vpx_Cxt_Remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_ProcessorNode nd = (VPX_ProcessorNode) getSelectionModel().getSelectionPath()
						.getLastPathComponent();

				if (nd.isSubSytemNode()) {

					int result = JOptionPane.showConfirmDialog(parent,
							String.format("Are yousure you want to remove subsystem \'%s\' from the VPXSystem?",
									nd.getNodeName()),
							"Confirmation", JOptionPane.YES_NO_OPTION);

					if (result == JOptionPane.YES_OPTION) {

						parent.deleteSubSystem(nd.getNodeName());
					}
				} else {
					String temp = getSelectionModel().getSelectionPath().getParentPath().toString();

					temp = temp.substring(temp.lastIndexOf(" ") + 1, temp.length() - 1);

					int result = JOptionPane.showConfirmDialog(parent,
							String.format("Are yousure you want to remove subsystem \'%s\' from the VPXSystem?", temp),
							"Confirmation", JOptionPane.YES_NO_OPTION);

					if (result == JOptionPane.YES_OPTION) {

						parent.deleteSubSystem(temp);
					}
				}

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

		getPopupMenu(node, i).show(this, x, y);
	}

	public void refreshProcessorTree() {

		reload();

	}

	private void doCreateNew(TreePath[] paths) {

		String[] nameArrays = getNodeNames(paths);

		String p1 = "";

		String d1 = "";

		String d2 = "";

		if (nameArrays != null) {

			for (int i = 0; i < nameArrays.length; i++) {

				if (nameArrays[i].contains("P2020")) {

					p1 = nameArrays[i].substring(nameArrays[i].indexOf(")") + 1);

				} else if (nameArrays[i].contains("DSP1")) {

					d1 = nameArrays[i].substring(nameArrays[i].indexOf(")") + 1);

				} else if (nameArrays[i].contains("DSP2")) {

					d2 = nameArrays[i].substring(nameArrays[i].indexOf(")") + 1);
				}

			}

			parent.showAliasConfig(p1, d1, d2);

		} else {

			JOptionPane.showMessageDialog(parent, "Must select 3 Processors only.\nSelect P2020, DSP1, DSP2 order");
		}

	}

	public JPopupMenu getPopupMenu(VPX_ProcessorNode node, int nodeLevel) {

		TreePath[] paths = getSelectionPaths();

		int length = 1;

		if (paths == null) {

			length = 1;

			nodeLevel = 0;
		} else {

			length = paths.length;
		}

		vpx_contextMenu.removeAll();

		if (length == 1) {

			setSelectedProcessor(node);

			vpx_Cxt_MemoryBrowser.setText(rBundle.getString("Menu.Window.MemoryBrowser") + " ( "
					+ (VPXConstants.MAX_MEMORY_BROWSER - VPX_ETHWindow.currentNoofMemoryView) + " ) ");

			vpx_Cxt_MemoryPlot.setText((rBundle.getString("Menu.Window.MemoryPlot") + " ( "
					+ (VPXConstants.MAX_MEMORY_PLOT - VPX_ETHWindow.currentNoofMemoryPlot) + " ) "));

			vpx_Cxt_Spectrum.setText(rBundle.getString("Menu.Window.Spectrum") + " ( "
					+ (VPXConstants.MAX_SPECTRUM - VPX_ETHWindow.currentNoofSpectrum) + " ) ");

			if (nodeLevel == 0) { // VPXSystem - Root Node

				vpx_contextMenu.add(vpx_Cxt_AliasConfig);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				// Window Menu Items
				vpx_contextMenu.add(vpx_Cxt_MemoryBrowser);

				vpx_contextMenu.add(vpx_Cxt_MemoryPlot);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				vpx_contextMenu.add(vpx_Cxt_Spectrum);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				vpx_contextMenu.add(vpx_Cxt_MAD);

				vpx_contextMenu.add(vpx_Cxt_EthFlash);

				vpx_contextMenu.add(vpx_Cxt_BIST);

				vpx_contextMenu.add(vpx_Cxt_Execution);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				// Tools Menu Items
				vpx_contextMenu.add(vpx_Cxt_ChangePWD);

				// vpx_contextMenu.add(vpx_Cxt_Refresh);

				vpx_contextMenu.add(vpx_Cxt_Reload);

				vpx_contextMenu.add(vpx_Cxt_Detail);

			} else if (nodeLevel == 1) { // VPXSubSystem - Child Node

				vpx_contextMenu.add(vpx_Cxt_AliasConfig);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				vpx_contextMenu.add(vpx_Cxt_MAD);

				vpx_contextMenu.add(vpx_Cxt_EthFlash);

				vpx_contextMenu.add(vpx_Cxt_BIST);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				if (!node.getNodeName().trim().equals(VPXConstants.VPXUNLIST)) {

					vpx_Cxt_SubSystem.removeAll();

					vpx_Cxt_SubSystem.add(vpx_Cxt_Rename);

					vpx_Cxt_SubSystem.add(vpx_Cxt_Remove);

					vpx_contextMenu.add(vpx_Cxt_SubSystem);

					vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				}

				vpx_contextMenu.add(vpx_Cxt_Reload);

				vpx_contextMenu.add(vpx_Cxt_Detail);

			} else if (nodeLevel == 2) { // DSP Node

				vpx_contextMenu.add(vpx_Cxt_MemoryBrowser);

				vpx_contextMenu.add(vpx_Cxt_MemoryPlot);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				vpx_contextMenu.add(vpx_Cxt_Spectrum);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				vpx_contextMenu.add(vpx_Cxt_MAD);

				vpx_contextMenu.add(vpx_Cxt_EthFlash);

				vpx_contextMenu.add(vpx_Cxt_Execution);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				vpx_contextMenu.add(vpx_Cxt_Reload);

				vpx_contextMenu.add(vpx_Cxt_Detail);

				// vpx_Cxt_Amplitude.setEnabled(node.isAmplitude());

				// vpx_Cxt_Waterfall.setEnabled(node.isWaterfall());

			} else if (nodeLevel == 3) { // P2020 Node

				vpx_contextMenu.add(vpx_Cxt_BIST);

				vpx_contextMenu.add(vpx_Cxt_P2020Config);

				vpx_contextMenu.add(vpx_Cxt_ChangeIP);

				vpx_contextMenu.add(vpx_Cxt_Reboot);

				vpx_contextMenu.add(vpx_Cxt_EthFlash);

				vpx_contextMenu.add(VPXComponentFactory.createJSeparator());

				vpx_contextMenu.add(vpx_Cxt_Reload);

				vpx_contextMenu.add(vpx_Cxt_Detail);
			}
		} else {

			// vpx_Cxt_SubSystem.removeAll();

			if (isSameParent(paths)) {

				if (paths[0].getParentPath().toString().contains(VPXConstants.VPXUNLIST)) {

					vpx_contextMenu.add(vpx_Cxt_New);

				} else {

					if (length <= 3) {

						vpx_contextMenu.add(vpx_Cxt_Remove);
					}
				}

			} else {

				// vpx_contextMenu.add(vpx_Cxt_Swap);

			}
			// To Do
			// vpx_contextMenu.add(vpx_Cxt_SubSystem);
		}

		boolean hasSubystem = (system.getTotalSize() > 0);

		vpx_Cxt_MemoryBrowser.setEnabled(hasSubystem);

		vpx_Cxt_MemoryPlot.setEnabled(hasSubystem);

		vpx_Cxt_Spectrum.setEnabled(hasSubystem);

		// Testing pending
		/*
		 * if (hasSubystem) {
		 * 
		 * vpx_Cxt_MemoryBrowser.setEnabled(!(VPX_ETHWindow.
		 * currentNoofMemoryView == VPXConstants.MAX_MEMORY_BROWSER));
		 * 
		 * vpx_Cxt_MemoryPlot.setEnabled(!(VPX_ETHWindow.currentNoofMemoryPlot
		 * == VPXConstants.MAX_MEMORY_PLOT));
		 * 
		 * vpx_Cxt_Spectrum.setEnabled(!(VPX_ETHWindow.currentNoofSpectrum ==
		 * VPXConstants.MAX_SPECTRUM));
		 * 
		 * }
		 */
		return vpx_contextMenu;
	}

	private String[] getNodeNames(TreePath[] paths) {

		if (paths.length != 3)
			return null;

		String[] nodes = new String[paths.length];

		String temp = "";

		for (int i = 0; i < paths.length; i++) {

			temp = paths[i].getLastPathComponent().toString();

			String str = temp.substring(temp.indexOf("'>") + 2, temp.indexOf("</"));

			nodes[i] = str;

		}

		return nodes;
	}

	private boolean isSameParent(TreePath[] paths) {

		String temp = "";

		String parentPath = "";

		boolean ret = true;

		for (int i = 0; i < paths.length; i++) {

			temp = paths[i].getParentPath().toString();

			if (i == 0) {
				parentPath = temp.substring(temp.lastIndexOf(" ") + 1, temp.length() - 1);
			} else {
				if (!parentPath.equals(temp.substring(temp.lastIndexOf(" ") + 1, temp.length() - 1))) {

					ret = false;

					break;
				}
			}

		}

		return ret;
	}

	@Override
	public boolean isPathSelected(TreePath path) {
		return !(((VPX_ProcessorNode) path.getLastPathComponent()).isLeaf());
	}

	class ProcMonitor implements Runnable {

		String ip;

		long response;

		long difference;

		boolean isStarted = true;

		public ProcMonitor() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {

				VPX_ProcessorNode node = null;

				while (isStarted) {

					@SuppressWarnings("unchecked")
					Enumeration<VPX_ProcessorNode> e = systemRootNode.depthFirstEnumeration();

					while (e.hasMoreElements()) {

						node = e.nextElement();

						if (node.isProcessorNode()) {

							response = node.getRespondedTime();

							difference = (System.currentTimeMillis() - response) / 1000;

							if (difference > (VPXSessionManager.getCurrentPeriodicity()
									+ VPXConstants.MAXRESPONSETIMEOUT)) {

								if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

									node.setStatus(false);

								} else {

									node.setStatus(false, false);
								}

							} else {

								if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

									node.setStatus(true);

								} else {

									node.setStatus(true, node.isAmplitude());
								}
							}

						}
					}

					repaint();

					Thread.sleep(5000);

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		public void stop() {
			isStarted = false;
		}

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

						if (difference > (VPXSessionManager.getCurrentPeriodicity()
								+ VPXConstants.MAXRESPONSETIMEOUT)) {

							if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

								node.setStatus(false);

							} else {

								node.setStatus(false, false);
							}

						} else {

							if (node.getNodeType() == PROCESSOR_LIST.PROCESSOR_P2020) {

								node.setStatus(true);

							} else {

								node.setStatus(true, node.isAmplitude());
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

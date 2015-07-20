/**
 * 
 */
package com.cti.vpx.controls;

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
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.cti.vpx.model.BIST;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
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

	private VPX_ETHWindow parent;

	private VPXSystem system = new VPXSystem();

	private static DefaultMutableTreeNode systemRootNode = new DefaultMutableTreeNode();

	private ProcessorMonitorThread monitor = new ProcessorMonitorThread();

	private VPX_PasswordWindow paswordWindow = new VPX_PasswordWindow();

	/**
	 * 
	 */
	public VPX_ProcessorTree() {
		initTree();
	}

	/**
	 * @param value
	 */
	public VPX_ProcessorTree(Object[] value) {

		super(value);

		initTree();
	}

	/**
	 * @param value
	 */
	public VPX_ProcessorTree(Vector<?> value) {

		super(value);

		initTree();
	}

	/**
	 * @param value
	 */
	public VPX_ProcessorTree(Hashtable<?, ?> value) {

		super(value);

		initTree();
	}

	/**
	 * @param root
	 */
	public VPX_ProcessorTree(VPX_ETHWindow prnt, TreeNode root) {

		super(root);

		this.parent = prnt;

		initTree();
	}

	/**
	 * @param prnt
	 */
	public VPX_ProcessorTree(VPX_ETHWindow prnt) {

		super(systemRootNode);

		this.parent = prnt;

		initTree();

		loadSystemRootNode();

	}

	/**
	 * @param newModel
	 */
	public VPX_ProcessorTree(TreeModel newModel) {

		super(newModel);

		initTree();
	}

	/**
	 * @param root
	 * @param asksAllowsChildren
	 */
	public VPX_ProcessorTree(TreeNode root, boolean asksAllowsChildren) {

		super(root, asksAllowsChildren);

		initTree();
	}

	private void initTree() {

		system = VPXUtilities.getVPXSystem();

		addMouseListener(this);

		setCellRenderer(new VPX_ProcessorTreeCellRenderer());

		setRowHeight(20);

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

		DefaultMutableTreeNode node = null;
		Enumeration<DefaultMutableTreeNode> e = systemRootNode.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			node = e.nextElement();
			if (node.isLeaf()) {
				if (node.getUserObject().toString().contains(ip)) {

					parseResponse(node, res);

					break;
				}

			}
		}

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
						true, false, true));
				DefaultMutableTreeNode ipNode3 = new DefaultMutableTreeNode(getNodeUserObject(subSystem.getDSP2Name(),
						true, true, false));

				subSystemNode.add(ipNode1);

				subSystemNode.add(ipNode2);

				subSystemNode.add(ipNode3);

				systemRootNode.add(subSystemNode);
			}
		}

		for (int i = 0; i < getRowCount(); i++) {
			expandRow(i);
		}

		updateUI();

	}

	class ProcessorMonitorThread implements Runnable {

		Thread th;

		private boolean isStarted = true;

		public ProcessorMonitorThread() {

		}

		@Override
		public void run() {
			/*
			 * List<Processor> vpxSystemProcessors =
			 * VPXUtilities.getVPXSystem().getProcessors();
			 * 
			 * while (isStarted) { try {
			 * 
			 * Thread.sleep(10000);
			 * 
			 * for (Iterator<Processor> iterator =
			 * vpxSystemProcessors.iterator(); iterator.hasNext();) {
			 * 
			 * Processor processor = iterator.next();
			 * 
			 * refreshProcessorTree(processor,
			 * !Pinger.ping(processor.getiP_Addresses()));
			 * 
			 * } } catch (Exception e) {
			 * 
			 * } }
			 */
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

				showConextMenu(e.getX(), e.getY(), node.getUserObject().toString());
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

	private void startBIST() {

		new VPX_BISTResultWindow(new BIST()).setVisible(true);

	}

	private void showExecuteWindow() {

		parent.addTab("Execute", new JScrollPane(new VPX_ExecutionPanel()));

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

	private void showVLAN() {

		paswordWindow.resetPassword();

		paswordWindow.setVisible(true);

		if (VPXUtilities.getPropertyValue(VPXUtilities.SECURITY_PWD).equals(paswordWindow.getPasword())) {

			parent.addTab("VLAN", new JScrollPane(new VPX_P2020ConfigurationPanel()));
		}
		else{
			JOptionPane.showMessageDialog(VPX_ProcessorTree.this.parent, "Pasword invalid","Authentication",JOptionPane.ERROR_MESSAGE);
			
			paswordWindow.resetPassword();

			paswordWindow.setVisible(true);
		}

		/*
		 * final VPX_VLANConfig browserCanvas = new VPX_VLANConfig();
		 * 
		 * JPanel contentPane = new JPanel(); contentPane.setLayout(new
		 * BorderLayout()); contentPane.add(browserCanvas, BorderLayout.CENTER);
		 * 
		 * JDialog frame = new JDialog(parent, "VLAN Configuration");
		 * frame.setBounds(100, 100, 1400, 500);
		 * frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		 * frame.setContentPane(contentPane); frame.setResizable(false);
		 * frame.addWindowListener(new WindowAdapter() {
		 * 
		 * @Override public void windowClosing(WindowEvent e) { // Dispose of
		 * the native component cleanly browserCanvas.dispose(); } });
		 * 
		 * frame.setVisible(true);
		 * 
		 * // Initialise the native browser component, and if successful... if
		 * (browserCanvas.initialise()) { // ...navigate to the desired URL
		 * browserCanvas.setUrl("http://192.168.10.1/cgi-bin/portsetting.cgi");
		 * } else { } frame.setSize(1401, 501);
		 */
	}

	private void showConextMenu(int x, int y, String node) {

		JPopupMenu popup = new JPopupMenu();

		if (node.startsWith("VPXSystem")) {

			/*
			 * JMenuItem itemScan = new JMenuItem("Scan");
			 * 
			 * itemScan.addActionListener(new ActionListener() {
			 * 
			 * @Override public void actionPerformed(ActionEvent e) {
			 * 
			 * VPX_ScanWindow ir = new VPX_ScanWindow(parent);
			 * 
			 * ir.setVisible(true);
			 * 
			 * } });
			 * 
			 * popup.add(itemScan);
			 */

			JMenuItem itemRefresh = new JMenuItem("Configure");

			itemRefresh.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new VPX_AliasConfigWindow(parent).setVisible(true);
				}
			});

			popup.add(itemRefresh);

		} else {

			JMenuItem itemConnect = new JMenuItem("Connect");

			itemConnect.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					/*
					 * parent.connectProcessor(VPXUtilities.getSelectedSubSystem(
					 * VPX_ProcessorTree.this
					 * .getLastSelectedPathComponent().toString()));
					 */
				}
			});

			popup.add(itemConnect);

			JMenuItem itemRename = new JMenuItem("Rename");

			itemRename.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					reNameSelectedProcessorNode();
				}
			});

			popup.add(itemRename);

			JMenuItem itemVLAN = new JMenuItem("VLAN Configuration");

			itemVLAN.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					showVLAN();
				}
			});

			popup.add(itemVLAN);

			JMenuItem itemExecute = new JMenuItem("Execute");

			itemExecute.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					showExecuteWindow();
				}
			});

			popup.add(itemExecute);

			JMenuItem itemBist = new JMenuItem("Built In Self Test");

			itemBist.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					startBIST();

				}

			});

			popup.add(itemBist);
		}

		popup.add(new JSeparator());

		JMenuItem itemDetail = new JMenuItem("Detail");

		itemDetail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VPX_DetailPanel detail = new VPX_DetailPanel(VPX_ProcessorTree.this.getLastSelectedPathComponent()
						.toString());
				detail.setVisible(true);
			}
		});

		popup.add(itemDetail);

		popup.show(this, x, y);
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

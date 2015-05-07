/**
 * 
 */
package com.cti.vpx.controls;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.cti.vpx.command.ATP;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_Dual_ADT_RootWindow;

/**
 * @author Abi_Achu
 *
 */
public class VPX_ProcessorTree extends JTree implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4220657730002684130L;

	private VPX_Dual_ADT_RootWindow parent;

	private VPXSystem system;

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
	public VPX_ProcessorTree(VPX_Dual_ADT_RootWindow prnt, TreeNode root) {

		super(root);

		this.parent = prnt;

		initTree();
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

		addMouseListener(this);

		setCellRenderer(new VPX_ProcessorTreeCellRenderer());

		setRowHeight(20);

		// DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)
		// getCellRenderer();
	}

	public void setVPXSystem(VPXSystem sys) {
		this.system = sys;
	}

	private class VPX_ProcessorTreeCellRenderer extends DefaultTreeCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3402533538077987491L;

		ImageIcon systemIcon = VPXUtilities.getImageIcon("images\\System.jpg", 18, 18);

		ImageIcon processorIcon = VPXUtilities.getImageIcon("images\\Processor4.jpg", 14, 14);

		public VPX_ProcessorTreeCellRenderer() {

		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

			String nodo = "";

			if (((DefaultMutableTreeNode) value).getUserObject() != null)
				nodo = ((DefaultMutableTreeNode) value).getUserObject().toString();

			if (nodo.startsWith(VPXSystem.class.getSimpleName())) {

				setIcon(systemIcon);

			} else {

				setIcon(processorIcon);

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

		if (e.getButton() == 3) {

			int row = getRowForLocation(e.getX(), e.getY());

			if (row == -1) {

				return;
			}

			setSelectionRow(row);

			showConextMenu(e.getX(), e.getY(), ((DefaultMutableTreeNode) getLastSelectedPathComponent())
					.getUserObject().toString());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void startBIST() {

	}

	private void reNameSelectedProcessorNode() {
		DefaultMutableTreeNode f = (DefaultMutableTreeNode) VPX_ProcessorTree.this.getLastSelectedPathComponent();

		String old_Value = f.getUserObject().toString();

		String old_name = old_Value.substring(old_Value.indexOf("(") + 1, old_Value.indexOf(")"));

		parent.updateStatus("Rename processor " + old_name);

		String new_Name = JOptionPane.showInputDialog(parent, "New Name", old_name);

		if (new_Name != null) {

			VPXUtilities.getSelectedProcessor(VPX_ProcessorTree.this.getLastSelectedPathComponent().toString())
					.setName(new_Name);

			String de = old_Value.substring(0, old_Value.indexOf("(")) + "(" + new_Name + ")";

			f.setUserObject(de);

			parent.updateLog("Processor name " + old_name + " changed to " + new_Name);

			parent.updateStatus("Processor name " + old_name + " changed to " + new_Name);

			VPX_ProcessorTree.this.updateUI();
		} else {
			parent.updateStatus("Rename processor " + old_name + " interrupted");
		}
	}

	private void showConextMenu(int x, int y, String node) {

		JPopupMenu popup = new JPopupMenu();

		if (node.startsWith("VPXSystem")) {

			JMenuItem itemScan = new JMenuItem("Scan");

			itemScan.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					VPX_ScanWindow ir = new VPX_ScanWindow(parent);

					ir.setVisible(true);

				}
			});

			JMenuItem itemRefresh = new JMenuItem("Refresh");

			itemRefresh.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});

			popup.add(itemScan);

			popup.add(itemRefresh);

		} else {

			JMenuItem itemConnect = new JMenuItem("Connect");

			itemConnect.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					parent.connectProcessor(VPXUtilities.getSelectedProcessor(VPX_ProcessorTree.this
							.getLastSelectedPathComponent().toString()));
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

			JMenuItem itemBist = new JMenuItem("BIST");

			itemBist.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Bist");
					try {
						Socket client = new Socket();

						client.connect(new InetSocketAddress("172.17.1.29", 12345), 300000);

						client.setSoTimeout(300000);

						System.out.println("Just connected to " + client.getRemoteSocketAddress());

						System.out.println("Hello from " + client.getLocalSocketAddress());

						/*
						 * DSPATPCommand cmd = VPXUtilities.createDSPCommand();
						 * 
						 * cmd.params.testType.set(ATP.TEST_DSP_FULL);
						 */

						ATPCommand cmd = VPXUtilities.createATPCommand();

						cmd.params.testType.set(ATP.TEST_P2020_FULL);

						cmd.msgType.set(ATPCommand.MSG_TYPE_TEST);

						cmd.msgID.set(ATPCommand.MSG_ID_GET);

						VPX_BusyWindow busyWindow = new VPX_BusyWindow(null, "Built in Self Test",
								"Complete Testing on process....");

						cmd.write(client.getOutputStream());

						long start = System.currentTimeMillis();

						ATPCommand msg = new ATPCommand();

						msg.read(client.getInputStream());

						long end = System.currentTimeMillis();

						try {
							busyWindow.dispose();

							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

							new VPX_FullTestResult(msg, "172.17.1.29", start, end);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						
						client.close();
					} catch (Exception e3) {
						e3.printStackTrace();
					}
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

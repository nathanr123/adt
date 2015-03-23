/**
 * 
 */
package com.cti.vpx.controls;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.cti.vpx.model.Core;
import com.cti.vpx.model.Slot;
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

		ImageIcon slotIcon = VPXUtilities.getImageIcon("images\\Slot.jpg", 18, 18);

		ImageIcon processorIcon = VPXUtilities.getImageIcon("images\\Processor4.jpg", 14, 14);

		ImageIcon coreIcon = VPXUtilities.getImageIcon("images\\Core.png", 14, 14);

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

			} else if (nodo.startsWith(Slot.class.getSimpleName())) {

				setIcon(slotIcon);

			} else if (nodo.startsWith("DSP") || nodo.startsWith("P2020")) {

				setIcon(processorIcon);

			} else if (nodo.startsWith(Core.class.getSimpleName())) {

				setIcon(coreIcon);
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

			popup.add(itemScan);

			popup.add(itemRefresh);

		} else if ((node.startsWith("Slot"))) {

			JMenuItem itemConnectAll = new JMenuItem("Connect All Processors");

			popup.add(itemConnectAll);

		} else if ((node.startsWith("DSP")) || (node.startsWith("P2020"))) {

			JMenuItem itemConnect = new JMenuItem("Connect");

			popup.add(itemConnect);
		}

		popup.add(new JSeparator());

		JMenuItem itemDetail = new JMenuItem("Detail");

		itemDetail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VPX_DetailPanel detail = new VPX_DetailPanel(system);
				detail.setVisible(true);
			}
		});

		popup.add(itemDetail);

		popup.show(this, x, y);
	}
}

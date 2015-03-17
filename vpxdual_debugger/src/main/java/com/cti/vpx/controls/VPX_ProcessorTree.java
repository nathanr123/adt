/**
 * 
 */
package com.cti.vpx.controls;

import java.awt.Component;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.cti.vpx.model.Core;
import com.cti.vpx.model.Slot;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXUtilities;

/**
 * @author Abi_Achu
 *
 */
public class VPX_ProcessorTree extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4220657730002684130L;

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
	public VPX_ProcessorTree(TreeNode root) {
		super(root);
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
		setCellRenderer(new VPX_ProcessorTreeCellRenderer());
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

			String nodo = ((DefaultMutableTreeNode) value).getUserObject().toString();
			
			
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
	
}

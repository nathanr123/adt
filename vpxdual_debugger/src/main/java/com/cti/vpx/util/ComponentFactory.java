/**
 * 
 */
package com.cti.vpx.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.StyledDocument;
import javax.swing.tree.TreeNode;

import com.cti.vpx.controls.VPX_ProcessorTree;
import com.cti.vpx.view.VPX_ETHWindow;

/**
 * @author nathanr_kamal
 *
 */
public class ComponentFactory {

	public static JPanel createJPanel() {

		return new JPanel();
	}

	public static JMenuBar createJMenuBar() {

		return new JMenuBar();
	}

	public static JMenu createJMenu(String name) {

		return new JMenu(name);
	}

	public static JMenuItem createJMenuItem(String name) {

		JMenuItem item = new JMenuItem(name);

		Dimension d = item.getPreferredSize();

		d.width = 250;

		d.height = d.height + 5;

		item.setPreferredSize(d);

		return item;
	}

	public static JMenuItem createJMenuItem(String name, Icon icon) {

		JMenuItem item = new JMenuItem(name, icon);

		Dimension d = item.getPreferredSize();

		d.width = 250;

		d.height = d.height + 5;

		item.setPreferredSize(d);

		return item;
	}

	public static JMenuItem createJMenuItem(Action action) {

		JMenuItem item = new JMenuItem(action);

		Dimension d = item.getPreferredSize();

		d.width = 250;

		d.height = d.height + 5;

		item.setPreferredSize(d);

		return item;
	}

	public static JMenuItem createJMenuItem(String name, ActionListener listener) {

		JMenuItem i = new JMenuItem(name);

		Dimension d = i.getPreferredSize();

		d.width = 250;

		d.height = d.height + 5;

		i.setPreferredSize(d);

		i.addActionListener(listener);

		return i;
	}

	public static JToolBar createJToolBar() {

		return new JToolBar();
	}

	public static JButton createJButton(String name) {

		return new JButton(name);
	}

	public static JButton createJButton(String name, ActionListener listner) {

		JButton jb = new JButton(name);

		jb.addActionListener(listner);

		return jb;
	}

	public static JButton createJToolBarButton(Action action) {

		JButton jb = new JButton(action);

		jb.setFocusable(false);

		jb.setBorderPainted(false);

		return jb;
	}

	public static JButton createJButton(Action action) {

		return new JButton(action);
	}

	public static JButton createJButton(String name, Icon icon, ActionListener listner) {

		JButton jb = new JButton(name, icon);

		jb.addActionListener(listner);

		return jb;
	}

	public static JLabel createJLabel(String name) {

		return new JLabel(name);
	}

	public static JSeparator createJSeparator() {

		return new JSeparator();
	}

	public static JTextField createJTextField() {
		return new JTextField();
	}

	public static JTextField createJTextField(String text) {
		return new JTextField(text);
	}

	public static JCheckBox createJCheckBox(String text) {
		return new JCheckBox(text);
	}

	public static VPX_ProcessorTree createProcessorTree(VPX_ETHWindow parent, TreeNode root) {
		return new VPX_ProcessorTree(parent, root);
	}

	public static VPX_ProcessorTree createProcessorTree(VPX_ETHWindow parent) {
		return new VPX_ProcessorTree(parent);
	}

	public static JComboBox<String> createJComboBox() {
		return new JComboBox<String>();
	}

	public static JList<String> createJList() {
		return new JList<String>();
	}

	public static JScrollPane createJScrollPane() {
		return new JScrollPane();
	}

	public static JTextArea createJTextArea() {
		return new JTextArea();
	}

	public static JTextPane createJTextPane() {
		return new JTextPane();
	}

	public static JTextPane createJTextPane(StyledDocument doc) {
		return new JTextPane(doc);
	}

	public static ImageIcon getImageIcon(String path, int w, int h) {

		return new ImageIcon(getImage(path).getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH));
	}

	private static Image getImage(String name) {

		ClassLoader cl = ComponentFactory.class.getClassLoader();

		InputStream in = cl.getResourceAsStream(name);

		try {
			if (in != null) {

				return ImageIO.read(in);

			} else {

				File file = new File(name);

				if (file.isFile()) {

					return ImageIO.read(file);
				} else {

					throw new IOException("Image not found: " + name);
				}
			}

		} catch (IOException ioe) {

			ioe.printStackTrace();
		}

		return null;
	}
}

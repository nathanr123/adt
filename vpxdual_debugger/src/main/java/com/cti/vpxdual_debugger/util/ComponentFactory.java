/**
 * 
 */
package com.cti.vpxdual_debugger.util;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

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

		return new JMenuItem(name);
	}

	public static JMenuItem createJMenuItem(String name, ActionListener listener) {

		JMenuItem i = new JMenuItem(name);

		i.addActionListener(listener);

		return i;
	}

	public static JToolBar createJToolBar() {

		return new JToolBar();
	}

	public static JButton createJButton(String name, ActionListener listner) {

		JButton jb = new JButton(name);

		jb.addActionListener(listner);

		return jb;
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

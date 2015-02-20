/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author nathanr_kamal
 *
 */
public class CToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8048418239957329416L;

	private static final Color TOOLBAR_BG_DEFAULT = Color.DARK_GRAY;

	private static final Color TOOLBAR_FG_DEFAULT = new Color(254, 254, 254);

	private static final Color TOOLBAR_BORDER_DARKSHADOW_COLOR_DEFAULT = Color.BLACK;

	private static final Color TOOLBAR_BORDER_SHADOW_COLOR_DEFAULT = Color.LIGHT_GRAY;

	private static final Border TOOLBAR_BORDER_LINE = new LineBorder(TOOLBAR_BG_DEFAULT);

	private static final int WINDOW_BUTTON_WIDTH_DEFAULT = 32;

	private static final int WINDOW_BUTTON_HEIGHT_DEFAULT = 32;

	private static final String IMAGE_PATH = "images\\";

	public CToolBar() {

		super();

		init();
	}

	public void init() {

		setFloatable(false);

		UIManager.put("ToolBar.background", Color.GRAY.darker());

		UIManager.put("ToolBar.border", TOOLBAR_BORDER_LINE);

		UIManager.put("ToolBar.borderColor", TOOLBAR_BORDER_DARKSHADOW_COLOR_DEFAULT);

		UIManager.put("ToolBar.darkShadow", TOOLBAR_BORDER_DARKSHADOW_COLOR_DEFAULT);

		UIManager.put("ToolBar.font", Constants.FONTDEFAULT);

		UIManager.put("ToolBar.foreground", TOOLBAR_FG_DEFAULT);

		UIManager.put("ToolBar.shadow", TOOLBAR_BORDER_SHADOW_COLOR_DEFAULT);

		SwingUtilities.updateComponentTreeUI(this);
	}

	public void addButton(String name) {

		add(new ToolBarButton(name));
	}

	public void addButton(Action action) {

		add(new ToolBarButton(action));
	}

	public void addButton(String name, String path) {

		add(new ToolBarButton(name, path));
	}

	public void addButton(ImageIcon ico) {

		add(new ToolBarButton(ico));
	}

	public void addButton(String name, Icon ico, ActionListener listener) {
	}

	private class ToolBarButton extends JButton implements ChangeListener {

		private static final long serialVersionUID = 2419313605594289553L;

		private String imagepath;

		public ToolBarButton(String text) {

			super(text);

			setText(text);

			init();

		}

		public ToolBarButton(Action act) {

			super(act);

			init();

		}

		public ToolBarButton(String name, String path) {

			super();

			this.imagepath = path;

			init();

		}

		public ToolBarButton(ImageIcon ico) {

			super();

			init();

			setIcon(ico);
		}

		private void init() {

			setOpaque(true);

			setFocusable(false);

			setFont(Constants.FONTSMALL);

			setBackground(Color.GRAY);

			setForeground(TOOLBAR_FG_DEFAULT);

			setFocusPainted(false);

			if (imagepath != null) {

				setIcon(getIconImage(imagepath, false, 24, 24));
			}

			setBorder(Constants.NOBORDER);

			addChangeListener(this);

			setSize(new Dimension(WINDOW_BUTTON_WIDTH_DEFAULT, WINDOW_BUTTON_HEIGHT_DEFAULT));

			repaint();
		}

		@Override
		public Dimension getPreferredSize() {

			return new Dimension(WINDOW_BUTTON_WIDTH_DEFAULT, WINDOW_BUTTON_HEIGHT_DEFAULT);
		}

		@Override
		public Dimension getMaximumSize() {

			return new Dimension(WINDOW_BUTTON_WIDTH_DEFAULT, WINDOW_BUTTON_HEIGHT_DEFAULT);
		}

		@Override
		public Dimension getMinimumSize() {

			return new Dimension(WINDOW_BUTTON_WIDTH_DEFAULT, WINDOW_BUTTON_HEIGHT_DEFAULT);
		}

		public void stateChanged(ChangeEvent e) {

			ButtonModel model = this.getModel();

			setContentAreaFilled(false);

			this.setOpaque(true);

			if (model.isRollover()) {

				this.setBackground(this.getBackground().brighter());

				this.setForeground(TOOLBAR_BORDER_DARKSHADOW_COLOR_DEFAULT);

				setIcon(getIconImage(imagepath, false, 22, 22));

			} else if (model.isPressed()) {

				this.setForeground(TOOLBAR_BORDER_DARKSHADOW_COLOR_DEFAULT);

				setIcon(getIconImage(imagepath, true, 20, 20));

			} else {

				setBackground(Color.GRAY);

				setForeground(TOOLBAR_FG_DEFAULT);

				setIcon(getIconImage(imagepath, false, 20, 20));
			}

		}

	}

	public ImageIcon getIconImage(String path, boolean isHover, int w, int h) {

		if (isHover)

			return ComponentFactory.getImageIcon(IMAGE_PATH + path + "_active.png", w, h);

		else

			return ComponentFactory.getImageIcon(IMAGE_PATH + path + ".png", w, h);
	}
}

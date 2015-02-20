/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * @author nathanr_kamal
 *
 */
public class CTabbedPane extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5223414289680525274L;

	private static final int TABBEDPANE_TAB_WIDTH = 100;

	private static final int TABBEDPANE_TAB_HEIGHT = 25;

	private static final Color TABBEDPANE_TAB_BG_DEFAULT = Color.DARK_GRAY;

	private static final Color TABBEDPANE_TAB_BG_SELECTED = Color.LIGHT_GRAY.brighter();

	private static final Color TABBEDPANE_BORDER_DEFAULT = Color.GRAY;

	private static final Color TABBEDPANE_BORDER_CONTENT = Color.BLACK;

	private static final Color TABBEDPANE_FG_DEFAULT = Color.BLACK;

	private static final Color TABBEDPANE_FG_SELECTED = Color.WHITE;

	private CLabel tabLabel;

	private TabButton button;

	/**
	 * 
	 */
	public CTabbedPane() {

		super();

		init();
	}

	/**
	 * @param arg0
	 */
	public CTabbedPane(int arg0) {

		super(arg0);

		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CTabbedPane(int arg0, int arg1) {

		super(arg0, arg1);

		init();
	}

	private void init() {

		setFocusable(false);

		SwingUtilities.updateComponentTreeUI(this);

		setUI(new CTIJTabbedPaneUI());

	}

	@Override
	public void addTab(String title, Component component) {

		super.addTab(title, component);
		/*
		 * if (getTabCount() > 1) { int count = this.getTabCount() - 1;
		 * 
		 * setTabComponentAt(count, new CloseButtonTab(component, title, null));
		 * }
		 */
	}

	private static class CTIJTabbedPaneUI extends BasicTabbedPaneUI {

		public static ComponentUI createUI(JComponent c) {

			return new CTIJTabbedPaneUI();
		}

		protected void installDefaults() {

			super.installDefaults();

			tabAreaInsets.left = 4;

			selectedTabPadInsets = new Insets(0, 0, 0, 0);

			tabInsets = selectedTabPadInsets;

		}

		public int getTabRunCount(JTabbedPane pane) {

			return 1;
		}

		protected Insets getContentBorderInsets(int tabPlacement) {

			return new Insets(0, 0, 0, 0);
		}

		protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {

			return TABBEDPANE_TAB_HEIGHT;
		}

		protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {

			return TABBEDPANE_TAB_WIDTH;// super.calculateTabWidth(tabPlacement,
										// tabIndex,
			// metrics) + metrics.getHeight();
		}

		protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
				boolean isSelected) {

			if (isSelected) {

				g.setColor(TABBEDPANE_TAB_BG_DEFAULT);

			} else {

				g.setColor(TABBEDPANE_TAB_BG_SELECTED);

			}

			g.fillRect(x, y, w, h);
		}

		protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
				boolean isSelected) {

			g.setColor(TABBEDPANE_TAB_BG_DEFAULT);

			g.drawLine(x, y, x, y + h);

			g.drawLine(x, y, x + w, y);
		}

		protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
				int h) {

			Rectangle selectedRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);

			selectedRect.width = selectedRect.width + (selectedRect.height / 2) - 1;

			g.setColor(TABBEDPANE_BORDER_CONTENT);

			g.drawLine(x, y, selectedRect.x, y);

			g.drawLine(selectedRect.x, y, x + w, y);

		}

		protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {

			int tw = tabPane.getBounds().width;

			g.setColor(TABBEDPANE_BORDER_DEFAULT);

			g.fillRect(0, 0, tw, rects[0].height + 3);

			super.paintTabArea(g, tabPlacement, selectedIndex);
		}

		protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex,
				String title, Rectangle textRect, boolean isSelected) {

			int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

			if (isSelected) {

				g.setFont(Constants.FONTDEFAULT);

				g.setColor(TABBEDPANE_FG_SELECTED);

			} else {

				g.setFont(Constants.FONTSMALL);

				g.setColor(TABBEDPANE_FG_DEFAULT);
			}

			BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x,
					textRect.y + metrics.getAscent());
		}

		protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {

			return 0;
		}
	}

	public class CloseButtonTab extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -925039726902932471L;

		public CloseButtonTab(final Component tab, String title, Icon icon) {

			setOpaque(false);

			FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 6);

			setLayout(flowLayout);

			setVisible(true);

			setPreferredSize(new Dimension(TABBEDPANE_TAB_WIDTH, TABBEDPANE_TAB_HEIGHT));

			tabLabel = new CLabel(title);

			tabLabel.setFont(Constants.FONTTITLE);

			tabLabel.setIcon(icon);

			add(new JLabel(" "));

			add(tabLabel);

			add(new JLabel("                "));

			button = new TabButton("close");

			button.setMargin(new Insets(0, 0, 0, 0));

			button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

			button.addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent e) {

					JTabbedPane tabbedPane = (JTabbedPane) getParent().getParent();

					tabbedPane.remove(tab);
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {

				}

				public void mouseExited(MouseEvent e) {

				}
			});

			add(button);
		}

	}

	private class TabButton extends JButton implements ChangeListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2419313605594289553L;

		private static final String IMAGE_PATH = "images\\";

		private String imagepath;

		public TabButton(String path) {

			this.imagepath = path;

			setOpaque(true);

			setFocusable(false);

			setContentAreaFilled(false);

			// setBackground(titlePanel.getBackground());

			setFocusPainted(false);

			setIcon(getIconImage(imagepath, false, 16, 16));

			setBorder(Constants.NOBORDER);

			addChangeListener(this);
		}

		public void stateChanged(ChangeEvent e) {

			ButtonModel model = this.getModel();

			this.setOpaque(true);

			if (model.isRollover()) {

				this.setBackground(this.getBackground().brighter().brighter());

				setIcon(getIconImage(imagepath, false, 16, 16));

				setBorder(new LineBorder(Color.GRAY));

			} else if (model.isPressed()) {

				setIcon(getIconImage(imagepath, true, 16, 16));

			} else {

				// setBackground(titlePanel.getBackground());

				setIcon(getIconImage(imagepath, false, 16, 16));

				setBorder(null);
			}

		}

		private ImageIcon getIconImage(String path, boolean isHover, int w, int h) {

			if (isHover)

				return ComponentFactory.getImageIcon(IMAGE_PATH + path + "_active.png", w, h);

			else

				return ComponentFactory.getImageIcon(IMAGE_PATH + path + ".png", w, h);
		}

	}

}

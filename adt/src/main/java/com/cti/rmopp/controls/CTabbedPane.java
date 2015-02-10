/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
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

	private static final int TABBEDPANE_TAB_WIDTH = 120;

	private static final int TABBEDPANE_TAB_HEIGHT = 30;

	private static final Color TABBEDPANE_TAB_BG_DEFAULT = Color.DARK_GRAY;

	private static final Color TABBEDPANE_TAB_BG_SELECTED = Color.LIGHT_GRAY.brighter();

	private static final Color TABBEDPANE_BORDER_DEFAULT = Color.GRAY;

	private static final Color TABBEDPANE_BORDER_CONTENT = Color.BLACK;

	private static final Color TABBEDPANE_FG_DEFAULT = Color.BLACK;

	private static final Color TABBEDPANE_FG_SELECTED = Color.WHITE;

	/**
	 * 
	 */
	public CTabbedPane() {
		init();
	}

	/**
	 * @param arg0
	 */
	public CTabbedPane(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CTabbedPane(int arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	private void init() {

		setFocusable(false);

		SwingUtilities.updateComponentTreeUI(this);

		setUI(new CTIJTabbedPaneUI());

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
			return TABBEDPANE_TAB_WIDTH;// super.calculateTabWidth(tabPlacement, tabIndex,
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

				g.setFont(Constants.FONTTITLE);

				g.setColor(TABBEDPANE_FG_SELECTED);
			} else {

				g.setFont(Constants.FONTDEFAULT);

				g.setColor(TABBEDPANE_FG_DEFAULT);
			}
			BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x,
					textRect.y + metrics.getAscent());
		}

		protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
			return 0;
		}
	}

}

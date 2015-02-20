/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * @author nathanr_kamal
 *
 */
public class CMenuItem extends JMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -311781268885436943L;

	private static final Color MENUITEM_BG_DEFAULT = new Color(254, 254, 254);

	private static final Color MENUITEM_FG_DEFAULT = Color.DARK_GRAY;

	private static final Color MENUITEM_BG_SELECTION = MENUITEM_FG_DEFAULT;

	private static final Color MENUITEM_FG_SELECTION = MENUITEM_BG_DEFAULT;

	private static final Color MENUITEM_FG_DISABLED = Color.LIGHT_GRAY;

	private static final Color MENUITEM_BORDER_COLOR = Color.BLACK;

	/**
	 * 
	 */
	public CMenuItem() {
		init();
	}

	/**
	 * @param icon
	 */
	public CMenuItem(Icon icon) {

		super(icon);

		init();
	}

	/**
	 * @param text
	 */
	public CMenuItem(String text) {

		super(text);

		init();
	}

	/**
	 * @param a
	 */
	public CMenuItem(Action a) {

		super(a);

		init();
	}

	/**
	 * @param text
	 * @param icon
	 */
	public CMenuItem(String text, Icon icon) {

		super(text, icon);

		init();
	}

	/**
	 * @param text
	 * @param mnemonic
	 */
	public CMenuItem(String text, int mnemonic) {

		super(text, mnemonic);

		init();
	}

	private void init() {

		UIManager.put("MenuItem.background", MENUITEM_BG_DEFAULT);

		UIManager.put("MenuItem.border", MENUITEM_BORDER_COLOR);

		UIManager.put("MenuItem.borderPainted", false);

		UIManager.put("MenuItem.disabledForeground", MENUITEM_FG_DISABLED);

		UIManager.put("MenuItem.font", Constants.FONTDEFAULT);

		UIManager.put("MenuItem.foreground", MENUITEM_FG_DEFAULT);

		UIManager.put("MenuItem.evenHeight", true);

		UIManager.put("MenuItem.opaque", true);

		UIManager.put("MenuItem.selectionBackground", MENUITEM_BG_SELECTION);

		UIManager.put("MenuItem.selectionForeground", MENUITEM_FG_SELECTION);

		SwingUtilities.updateComponentTreeUI(this);

	}

	@Override
	public Dimension getPreferredSize() {

		return (new Dimension((int) super.getPreferredSize().getWidth(), 30));
	}
}

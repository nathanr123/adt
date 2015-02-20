/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;

import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * @author nathanr_kamal
 *
 */
public class CMenuBar extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8993503421160964482L;

	private static final Color MENUBAR_BG_DEFAULT = Color.DARK_GRAY;

	private static final Color MENUBAR_FG_DEFAULT = new Color(254, 254, 254);

	private static final Color MENUBAR_BORDER_DARKSHADOW_COLOR_DEFAULT = Color.BLACK;

	private static final Color MENUBAR_BORDER_SHADOW_COLOR_DEFAULT = Color.LIGHT_GRAY;

	private static final Border MENUBAR_BORDER_LINE = new LineBorder(MENUBAR_BG_DEFAULT);

	/**
	 * 
	 */
	public CMenuBar() {

		init();
	}

	public void init() {

		UIManager.put("MenuBar.background", MENUBAR_BG_DEFAULT);

		UIManager.put("MenuBar.border", MENUBAR_BORDER_LINE);

		UIManager.put("MenuBar.borderColor", MENUBAR_BORDER_DARKSHADOW_COLOR_DEFAULT);

		UIManager.put("MenuBar.darkShadow", MENUBAR_BORDER_DARKSHADOW_COLOR_DEFAULT);

		UIManager.put("MenuBar.font", Constants.FONTDEFAULT);

		UIManager.put("MenuBar.foreground", MENUBAR_FG_DEFAULT);

		UIManager.put("MenuBar.shadow", MENUBAR_BORDER_SHADOW_COLOR_DEFAULT);

		SwingUtilities.updateComponentTreeUI(this);
	}
}

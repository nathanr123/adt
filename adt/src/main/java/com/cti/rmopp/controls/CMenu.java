/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * @author nathanr_kamal
 *
 */
public class CMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8100356025600138133L;
	
	private static final Color MENU_BG_DEFAULT = Color.DARK_GRAY;
	
	private static final Color MENU_BG_SELECTION = Color.GRAY;
	
	private static final Color MENU_FG_DEFAULT = new Color(254,254,254);
	
	private static final Color MENU_FG_SELECTION = new Color(254,254,254);
	
	private static final Color MENU_FG_DISABLED = Color.GRAY;
	
	private static final Border MENU_BORDER_POPUP = BorderFactory.createLineBorder(Color.DARK_GRAY);

	/**
	 * 
	 */
	public CMenu() {
		
		init();
	}

	/**
	 * @param s
	 */
	public CMenu(String s) {
		
		super(s);
		
		init();
	}

	/**
	 * @param a
	 */
	public CMenu(Action a) {
		
		super(a);
		
		init();
	}

	/**
	 * @param s
	 * @param b
	 */
	public CMenu(String s, boolean b) {
		
		super(s, b);
		
		init();
	}

	

	private void init() {

		UIManager.put("Menu.background",MENU_BG_DEFAULT);

		UIManager.put("Menu.border", Constants.NOBORDER);
		
		UIManager.put("Menu.opaque", true);

		UIManager.put("Menu.borderPainted", false);

		UIManager.put("Menu.disabledForeground", MENU_FG_DISABLED);

		UIManager.put("Menu.font", Constants.FONTDEFAULT);

		UIManager.put("Menu.foreground", MENU_FG_DEFAULT);

		UIManager.put("Menu.opaque", true);

		UIManager.put("Menu.selectionBackground", MENU_BG_SELECTION);

		UIManager.put("Menu.selectionForeground", MENU_FG_SELECTION);

		UIManager.put("PopupMenu.border", MENU_BORDER_POPUP);

		SwingUtilities.updateComponentTreeUI(this);
	}

}

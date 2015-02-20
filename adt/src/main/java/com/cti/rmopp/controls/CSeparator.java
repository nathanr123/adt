/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;

import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * @author nathanr_kamal
 *
 */
public class CSeparator extends JSeparator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6109674141688963400L;

	private static final Color SEPARATOR_BG_DEFAULT = Color.DARK_GRAY;

	private static final Color SEPARATOR_FG_DEFAULT = Color.LIGHT_GRAY.brighter();

	/**
	 * 
	 */
	public CSeparator() {
		
		super();
		
		init();
	}

	/**
	 * @param orientation
	 */
	public CSeparator(int orientation) {
		
		super(orientation);
		
		init();
	}

	private void init() {
		
		UIManager.put("Separator.foreground", SEPARATOR_BG_DEFAULT);

		UIManager.put("Separator.background", SEPARATOR_FG_DEFAULT);

		SwingUtilities.updateComponentTreeUI(this);
	}
}

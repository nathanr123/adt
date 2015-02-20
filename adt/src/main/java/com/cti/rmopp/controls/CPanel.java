/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author nathanr_kamal
 *
 */
public class CPanel extends JPanel {

	/**
	 * 
	 */
	private boolean isLight = false;

	private static final long serialVersionUID = -6831267217080794624L;

	private static final Color PANEL_BG_DARK = Color.DARK_GRAY;

	private static final Color PANEL_FG_DARK = new Color(254, 253, 251);

	private static final Color PANEL_BG_LIGHT = PANEL_FG_DARK;

	private static final Color PANEL_FG_LIGHT = PANEL_BG_DARK;

	/**
	 * 
	 */
	public CPanel() {
		init();
	}

	public CPanel(boolean isLight) {

		this.isLight = isLight;

		init();
	}

	public CPanel(LayoutManager layout) {

		super(layout);

		init();
	}

	private void init() {

		setFont(Constants.FONTDEFAULT);

		if (isLight) {
			
			setBackground(PANEL_BG_LIGHT);

			setForeground(PANEL_FG_LIGHT);
		} else {

			setBackground(PANEL_BG_DARK);

			setForeground(PANEL_FG_DARK);

		}
		
		applyBorder();
	}

	public void applyBorder() {
		
		if (isLight)
			
			this.setBorder(BorderFactory.createLineBorder(PANEL_FG_DARK));
		
		else
			
			this.setBorder(BorderFactory.createLineBorder(PANEL_BG_DARK));
	}

	public void applyLight() {
		
		this.isLight = true;
		
		init();
	}

}

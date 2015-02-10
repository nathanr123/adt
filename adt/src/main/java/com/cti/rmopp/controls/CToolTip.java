/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JToolTip;

/**
 * @author nathanr_kamal
 *
 */
public class CToolTip extends JToolTip {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6937025489024967617L;

	private static final Color TOOLTIP_BG_DEFAULT = Color.WHITE;

	private static final Color TOOLTIP_FG_DEFAULT = Color.DARK_GRAY;

	private static final Color TOOLTIP_BORDER_DEFAULT = Color.GRAY;

	public CToolTip() {
		init();
	}

	private void init() {
		setFont(Constants.FONTSMALL);

		setForeground(TOOLTIP_FG_DEFAULT);

		setBackground(TOOLTIP_BG_DEFAULT);

		setBorder(BorderFactory.createLineBorder(TOOLTIP_BORDER_DEFAULT));

	}

}

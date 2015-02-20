/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

/**
 * @author nathanr_kamal
 *
 */
public class CTitledBorder extends TitledBorder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -466551296119323731L;

	private static final Color TITLE_BG_DEFAULT = Color.WHITE;

	private static final Color TITLE_BORDER_DEFAULT = Color.GRAY;

	public CTitledBorder(String title) {
		
		super(title);

		setTitleFont(Constants.FONTTITLE);

		setTitleColor(TITLE_BG_DEFAULT);

		setBorder(BorderFactory.createLineBorder(TITLE_BORDER_DEFAULT));

	}

}

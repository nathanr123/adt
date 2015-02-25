/**
 * 
 */
package com.cti.vpxdual_debugger.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ResourceBundle;

/**
 * @author nathanr_kamal
 *
 */
public class VPXUtilities {

	private static int scrWidth;

	private static int scrHeight;

	private static final String MSG = "VPX_Dual_adt";

	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(MSG);

	public static ResourceBundle getResourceBundle() {

		return resourceBundle;
	}

	public static Dimension getScreenResoultion() {

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		scrWidth = (int) d.getWidth();

		scrHeight = (int) d.getHeight();

		return d;

	}

	public static int getScreenWidth() {

		if (scrWidth == 0)

			getScreenResoultion();

		return scrWidth;
	}

	public static int getScreenHeight() {

		if (scrHeight == 0)

			getScreenResoultion();

		return scrHeight;
	}

}

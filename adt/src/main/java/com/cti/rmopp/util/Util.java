/**
 * 
 */
package com.cti.rmopp.util;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * @author nathanr_kamal
 *
 */
public class Util {

	/**
	 * 
	 */

	private static int scrWidth;

	private static int scrHeight;

	public Util() {
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

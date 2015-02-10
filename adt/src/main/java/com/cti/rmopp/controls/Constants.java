/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.border.Border;

/**
 * @author nathanr_kamal
 *
 */
public class Constants {

	public static final String FONTNAMEDEFAULT = "Segoe UI Light";// font_win8.ttf";

	public static final String FONTNAMETITLE = "Segoe UI Bold";

	public static final int FONTSIZESMALL = 12;

	public static final int FONTSIZEDEFAULT = 14;

	public static final int FONTSIZETITLE = 17;

	public static final Font FONTDEFAULT = new Font(FONTNAMEDEFAULT, Font.PLAIN, FONTSIZEDEFAULT);

	public static final Font FONTSMALL = new Font(FONTNAMEDEFAULT, Font.PLAIN, FONTSIZESMALL);

	public static final Font FONTTITLE = new Font(FONTNAMEDEFAULT, Font.PLAIN, FONTSIZETITLE);
	
	public static final Border NOBORDER = null;

	public static final Cursor HANDCURSOR = new Cursor(Cursor.HAND_CURSOR);

}

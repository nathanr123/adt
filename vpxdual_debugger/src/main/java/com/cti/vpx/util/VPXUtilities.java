/**
 * 
 */
package com.cti.vpx.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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

	public static long getLongFromIP(String ip) {

		String[] split = ip.split("\\.");

		return (Long.parseLong(split[0]) << 24 | Long.parseLong(split[1]) << 16 | Long.parseLong(split[2]) << 8 | Long
				.parseLong(split[3]));

	}

	public static String getIPFromLong(final long ipaslong) {

		return String.format("%d.%d.%d.%d", (ipaslong >>> 24) & 0xff, (ipaslong >>> 16) & 0xff,
				(ipaslong >>> 8) & 0xff, (ipaslong) & 0xff);
	}

	public static ImageIcon getImageIcon(String path, int w, int h) {

		return new ImageIcon(getImage(path).getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH));
	}

	private static Image getImage(String name) {

		ClassLoader cl = ComponentFactory.class.getClassLoader();

		InputStream in = cl.getResourceAsStream(name);

		try {
			if (in != null) {

				return ImageIO.read(in);

			} else {

				File file = new File(name);

				if (file.isFile()) {

					return ImageIO.read(file);
				} else {

					throw new IOException("Image not found: " + name);
				}
			}

		} catch (IOException ioe) {

			ioe.printStackTrace();
		}

		return null;
	}
}

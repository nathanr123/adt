/**
 * 
 */
package com.cti.vpx.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import javolution.io.Struct.Enum32;

import com.cti.vpx.command.ATP.PROCESSOR_TYPE;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;

/**
 * @author nathanr_kamal
 *
 */
public class VPXUtilities {

	private static int scrWidth;

	private static int scrHeight;

	private static final String MSG = "VPX_Dual_adt";

	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(MSG);

	private static VPXSystem vpxSystem = new VPXSystem();

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

	public static void showPopup(String msg) {
		final JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);

		final JDialog dialog = new JDialog();
		dialog.setTitle("Message");
		dialog.setModal(true);
		dialog.setUndecorated(true);
		dialog.setContentPane(optionPane);
		optionPane.setBorder(new LineBorder(Color.GRAY));
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setAlwaysOnTop(true);
		dialog.setSize(new Dimension(450, 100));

		// create timer to dispose of dialog after 5 seconds
		Timer timer = new Timer(1000, new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5640039573419174479L;

			@Override
			public void actionPerformed(ActionEvent ae) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false);// the timer should only go off once

		// start timer to close JDialog as dialog modal we must start the timer
		// before its visible
		timer.start();

		Dimension windowSize = dialog.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		dialog.setLocation(dx, dy);

		dialog.setVisible(true);
	}

	public static VPXSystem getVPXSystem() {
		return vpxSystem;
	}

	public static void setVPXSystem(VPXSystem sys) {
		vpxSystem = sys;
	}

	public static Image getAppIcon() {
		return getImageIcon("images\\cornet.png", 24, 24).getImage();
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

	public static Processor getSelectedProcessor(String path) {

		Processor pros = null;

		List<Processor> ps = VPXUtilities.getVPXSystem().getProcessors();

		for (Iterator<Processor> iterator = ps.iterator(); iterator.hasNext();) {
			Processor processor = iterator.next();
			if (path.startsWith(processor.getiP_Addresses())) {
				pros = processor;
				break;
			}

		}

		return pros;
	}
	
	public static PROCESSOR_LIST getProcessor(Enum32<PROCESSOR_TYPE> pType) {

		if (pType.toString().equals(PROCESSOR_LIST.PROCESSOR_P2020.toString())) {

			return PROCESSOR_LIST.PROCESSOR_P2020;

		} else if (pType.toString().equals(PROCESSOR_LIST.PROCESSOR_DSP1.toString())) {

			return PROCESSOR_LIST.PROCESSOR_DSP1;

		} else if (pType.toString().equals(PROCESSOR_LIST.PROCESSOR_DSP2.toString())) {

			return PROCESSOR_LIST.PROCESSOR_DSP2;
		}

		return null;

	}
}

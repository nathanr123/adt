/**
 * 
 */
package com.cti.vpx.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;

import com.cti.vpx.command.ATP.PROCESSOR_TYPE;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.P2020ATPCommand;
import com.cti.vpx.controls.hex.HexEditor;
import com.cti.vpx.model.NWInterface;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.view.VPX_ETHWindow;

import gnu.io.CommPortIdentifier;
import javolution.io.Struct.Enum32;

/**
 * @author nathanr_kamal
 *
 */
public class VPXUtilities {

	private static int scrWidth;

	private static int scrHeight;

	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(VPXConstants.RESOURCENAME);

	private static Properties props;

	private static boolean isLogEnabled = false;

	private static VPXSystem vpxSystem = null;

	private static VPX_ETHWindow parent;

	private static InterfaceAddress currentInterfaceAddress;

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

	public static String getCurrentTime() {
		return getCurrentTime(0);
	}

	public static ATPCommand createATPCommand() {
		return new ATPCommand();
	}

	public static P2020ATPCommand createP2020Command() {
		return new P2020ATPCommand();
	}

	public static DSPATPCommand createDSPCommand() {
		return new DSPATPCommand();
	}

	public static String friendlyTimeDiff(long different) {

		long secondsInMilli = 1000;

		long minutesInMilli = secondsInMilli * 60;

		long hoursInMilli = minutesInMilli * 60;

		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;

		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;

		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;

		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;

		long elapsedMilliSeconds = different % secondsInMilli;

		String str = "";

		if (elapsedDays > 0) {

			str += elapsedDays + "days ";
		}
		if (elapsedHours > 0) {

			str += elapsedHours + " hrs ";
		}
		if (elapsedMinutes > 0) {

			str += elapsedMinutes + " mins ";
		}
		if (elapsedSeconds > 0) {

			str += elapsedSeconds + " secs ";
		}
		if (elapsedMilliSeconds > 0) {

			str += elapsedMilliSeconds + "  millis ";
		}

		return str;
	}

	public static void setParent(VPX_ETHWindow prnt) {
		parent = prnt;
	}

	public static VPX_ETHWindow getParent() {
		return parent;
	}

	public static String getCurrentTime(int format) {

		String ret = null;

		if (format == 0)

			ret = VPXConstants.DATEFORMAT_FULL.format(Calendar.getInstance().getTime());

		else if (format == 1)

			ret = VPXConstants.DATEFORMAT_DATE.format(Calendar.getInstance().getTime());

		else if (format == 2)

			ret = VPXConstants.DATEFORMAT_TIME.format(Calendar.getInstance().getTime());

		else if (format == 3)

			ret = VPXConstants.DATEFORMAT_FILE.format(Calendar.getInstance().getTime());

		else if (format == 4) {

			VPXConstants.DATEFORMAT_TIME.setTimeZone(TimeZone.getTimeZone("UTC"));

			ret = VPXConstants.DATEFORMAT_TIME.format(Calendar.getInstance().getTime());

		} else if (format == 5) {

			ret = VPXConstants.DATEFORMAT_TIME12.format(Calendar.getInstance().getTime());

		}
		return ret;
	}

	public static String getCurrentTime(int format, long millis) {

		String ret = null;

		if (millis == 0)

			return "";

		if (format == 0)

			ret = VPXConstants.DATEFORMAT_FULL.format(millis);

		else if (format == 1)

			ret = VPXConstants.DATEFORMAT_DATE.format(millis);

		else if (format == 2) {

			// VPXConstants.DATEFORMAT_TIME.setTimeZone(TimeZone.getTimeZone("UTC"));

			ret = VPXConstants.DATEFORMAT_TIME.format(millis);

		} else if (format == 3)

			ret = VPXConstants.DATEFORMAT_TIMEFULL.format(millis);

		else if (format == 4) {

			VPXConstants.DATEFORMAT_TIME.setTimeZone(TimeZone.getTimeZone("UTC"));

			ret = VPXConstants.DATEFORMAT_TIME.format(millis);

		} else if (format == 5) {

			ret = VPXConstants.DATEFORMAT_TIME12.format(millis);

		}

		return ret;
	}

	public static String formatElapsedTime(long elapsedTime) {

		int centiseconds = (int) (((elapsedTime % 1000L) + 5L) / 10L);

		int seconds = (int) (elapsedTime / 1000L);

		int minutes = seconds / 60;

		seconds -= minutes * 60;

		int hours = minutes / 60;

		minutes -= hours * 60;

		if (hours > 0) {

			return String.format("%2d:%02d:%02d.%02d", hours, minutes, seconds, centiseconds);

		} else if (minutes > 0) {

			return String.format("%2d:%02d.%02d", minutes, seconds, centiseconds);
		} else {

			return String.format("%2d.%02d", seconds, centiseconds);
		}
	}

	public static long getLongFromIP(String ip) {

		String[] split = ip.split("\\.");

		return (Long.parseLong(split[0]) << 24 | Long.parseLong(split[1]) << 16 | Long.parseLong(split[2]) << 8
				| Long.parseLong(split[3]));

	}

	public static String getIPFromLong(final long ipaslong) {

		return String.format("%d.%d.%d.%d", (ipaslong >>> 24) & 0xff, (ipaslong >>> 16) & 0xff, (ipaslong >>> 8) & 0xff,
				(ipaslong) & 0xff);
	}

	public static String toNumInUnits(long bytes) {
		int u = 0;
		for (; bytes > 1024 * 1024; bytes >>= 10) {
			u++;
		}
		if (bytes > 1024)
			u++;
		return String.format("%.1f %cB", bytes / 1024f, " kMGTPE".charAt(u));
	}

	public static void showPopup(String msg) {

		final JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
				null, new Object[] {}, null);

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

	public static void showPopup(String msg, String file) {

		showPopup(msg, file, false);
	}

	public static void showPopup(String msg, String file, boolean isUART) {

		JPanel centerPanel = new JPanel(new BorderLayout());

		final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		final JButton btnOpen;

		final JButton btnOpenFolder;

		final JButton btnClose;

		final JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
				null, new Object[] {}, null);

		final JDialog dialog = new JDialog();

		dialog.setTitle("Message");

		dialog.setModal(true);

		dialog.setUndecorated(true);

		dialog.setLayout(new BorderLayout());

		centerPanel.setBorder(new LineBorder(Color.GRAY));

		btnOpen = new JButton("Open");

		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (parent != null) {

						parent.openFile(file);
					} else {

						Desktop.getDesktop().open(new File(file));

					}

				} catch (IOException ee) {

				}

				dialog.dispose();

			}
		});

		btnOpenFolder = new JButton("Open Folder");

		btnOpenFolder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					Desktop.getDesktop().open(new File(file.substring(0, file.lastIndexOf("/"))));

				} catch (IOException ee) {

				}

				dialog.dispose();

			}
		});

		btnClose = new JButton("Close");

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dialog.dispose();

			}
		});

		if (!isUART)
			buttonsPanel.add(btnOpen);

		buttonsPanel.add(btnOpenFolder);

		buttonsPanel.add(btnClose);

		centerPanel.add(optionPane, BorderLayout.CENTER);

		centerPanel.add(buttonsPanel, BorderLayout.SOUTH);

		dialog.setContentPane(centerPanel);

		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		dialog.setAlwaysOnTop(true);

		dialog.setSize(new Dimension(450, 100));

		Dimension windowSize = dialog.getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		dialog.setLocation(dx, dy);

		dialog.setVisible(true);
	}

	public static Map<Long, byte[]> divideArrayAsMap(byte[] source, int chunksize) {

		Map<Long, byte[]> bytesMap = new LinkedHashMap<Long, byte[]>();

		int start = 0;

		long i = 0;

		while (start < source.length) {

			int end = Math.min(source.length, start + chunksize);

			bytesMap.put(i, Arrays.copyOfRange(source, start, end));

			start += chunksize;

			i++;
		}

		return bytesMap;
	}

	public static Image getAppIcon() {

		return getImageIcon(VPXConstants.Icons.ICON_CORNET_NAME, 24, 24).getImage();
	}

	public static Icon getEmptyIcon(int w, int h) {

		return new VPX_EmptyIcon(w, h);
	}

	public static ImageIcon getImageIcon(String path, int w, int h) {

		return new ImageIcon(getImage(path).getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH));
	}

	public static ImageIcon getImageIcon(String path) {

		return new ImageIcon(getImage(path).getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH));
	}

	private static Image getImage(String name) {

		try {

			return new ImageIcon(System.getProperty("user.dir") + "/image/" + name).getImage();

		} catch (Exception ioe) {

			ioe.printStackTrace();
		}

		return null;
	}

	public static VPXSubSystem getSelectedSubSystem(String path) {

		VPXSubSystem sub = null;

		vpxSystem = readFromXMLFile();

		List<VPXSubSystem> subs = vpxSystem.getSubsystem();

		for (Iterator<VPXSubSystem> iterator = subs.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			if (vpxSubSystem.getSubSystem().equals(path)) {

				sub = vpxSubSystem;

				break;
			}

		}
		return sub;
	}

	public static PROCESSOR_LIST getProcessorType(Enum32<PROCESSOR_TYPE> pType) {

		if (pType.toString().equals(PROCESSOR_LIST.PROCESSOR_P2020.toString())) {

			return PROCESSOR_LIST.PROCESSOR_P2020;

		} else if (pType.toString().equals(PROCESSOR_LIST.PROCESSOR_DSP1.toString())) {

			return PROCESSOR_LIST.PROCESSOR_DSP1;

		} else if (pType.toString().equals(PROCESSOR_LIST.PROCESSOR_DSP2.toString())) {

			return PROCESSOR_LIST.PROCESSOR_DSP2;
		}

		return null;

	}

	public static void writeProperties() {
		OutputStream output = null;
		try {

			Properties prop = new Properties();

			output = new FileOutputStream(resourceBundle.getString("system.preference.property.name"));

			// General Tab Settings
			prop.setProperty(VPXConstants.ResourceFields.GENERAL_SPLASH, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.GENERAL_MEMORY, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.SECURITY_PWD, encodePassword("cornet"));
			// Log Tab Settings

			prop.setProperty(VPXConstants.ResourceFields.WORKSPACE_PATH,
					System.getProperty("user.home") + "/vpxworkspace");

			prop.setProperty(VPXConstants.ResourceFields.LOG_ENABLE, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.LOG_PROMPT, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.LOG_MAXFILE, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.LOG_FILEPATH, "log");

			prop.setProperty(VPXConstants.ResourceFields.LOG_MAXFILESIZE, "2");

			prop.setProperty(VPXConstants.ResourceFields.LOG_FILEFORMAT, "$(SerialNumber)_$(CurrentTime)");

			prop.setProperty(VPXConstants.ResourceFields.LOG_APPENDCURTIME, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.LOG_OVERWRITE, String.valueOf(false));

			prop.setProperty(VPXConstants.ResourceFields.FILTER_SUBNET, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_PYTHON, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_MAP, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_PRELINKER, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_STRIPER, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_OFD, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_MAL, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_NML, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_DUMMY, "");

			prop.setProperty(VPXConstants.ResourceFields.DUMMY_CHK, "false");

			prop.setProperty(VPXConstants.ResourceFields.PATH_CORE0, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_CORE1, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_CORE2, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_CORE3, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_CORE4, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_CORE5, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_CORE6, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_CORE7, "");

			prop.setProperty(VPXConstants.ResourceFields.PATH_OUT, "");

			// save properties to project root folder
			prop.store(output, null);

		} catch (Exception e) {
			e.printStackTrace();

			if (output != null) {
				try {
					output.close();
				} catch (IOException ea) {
					ea.printStackTrace();
				}
			}
		}

	}

	public static String getPropertyValue(String key) {

		if (props == null)
			readProperties();

		return props.getProperty(key);
	}

	public static String getString(String key) {

		return resourceBundle.getString(key);
	}

	public static void updateProperties(Properties prop) {
		try {
			props = (Properties) prop.clone();

			FileOutputStream out = new FileOutputStream(resourceBundle.getString("system.preference.property.name"));

			prop.store(out, null);

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setEnableLog(boolean val) {
		isLogEnabled = val;
	}

	public static boolean isLogEnabled() {
		return isLogEnabled;
	}

	public static boolean isValidIP(String ip) {

		return VPXConstants.IPPattern.matcher(ip.trim()).matches();
	}

	public static boolean isValidName(String name) {

		return VPXConstants.NAMEPATTERN.matcher(name.trim()).matches();
	}

	public static void updateProperties(String key, String value) {
		try {
			if (props == null)
				readProperties();

			FileOutputStream out = new FileOutputStream(resourceBundle.getString("system.preference.property.name"));

			props.setProperty(key, value);

			props.store(out, null);

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Properties readProperties() {

		Properties properties = new Properties();

		try {

			File f = new File(resourceBundle.getString("system.preference.property.name"));

			if (!f.exists())
				writeProperties();

			InputStream in = new FileInputStream(resourceBundle.getString("system.preference.property.name"));

			properties.load(in);

		} catch (Exception e) {
			e.printStackTrace();
			properties = null;
		}

		props = (Properties) properties.clone();

		return properties;
	}

	public static boolean deleteXMLFile() {

		try {

			File file = new File(resourceBundle.getString("Scan.processor.data.path") + "/"
					+ resourceBundle.getString("Scan.processor.data.xml"));

			if (file.exists()) {

				file.delete();
			}

			return true;

		} catch (Exception e) {

			return false;
		}

	}

	public static VPXSystem readFromXMLFile() {

		VPXSystem cag = null;

		try {

			File file = new File(
					VPXSessionManager.getDataPath() + "/" + resourceBundle.getString("Scan.processor.data.xml"));

			if (file.exists()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(VPXSystem.class);

				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				cag = (VPXSystem) jaxbUnmarshaller.unmarshal(file);
			} else
				cag = new VPXSystem();

		} catch (JAXBException e) {

			e.printStackTrace();
		}

		return cag;
	}

	public static void writeToXMLFile(VPXSystem system) {
		try {

			File folder = new File(VPXSessionManager.getDataPath());

			if (!folder.exists()) {

				folder.mkdir();
			}

			File file = new File(
					VPXSessionManager.getDataPath() + "/" + resourceBundle.getString("Scan.processor.data.xml"));

			JAXBContext jaxbContext = JAXBContext.newInstance(VPXSystem.class);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(system, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public static String[] parseBuffertoString(byte[] buffer) {
		String[] strArr = new String(buffer).trim().split(";;");

		return strArr;
	}

	public static String getPythonInterpreterPath() {
		String ret = null;
		String path = System.getenv().get("Path");
		if (path != null) {

			String[] paths = path.split(";");

			for (int i = 0; i < paths.length; i++) {
				int k = paths[i].indexOf("Python");
				if (k > -1) {
					ret = paths[i].substring(0, (paths[i].indexOf("\\", k) + 1));
					break;
				}
			}

			return ret + "python.exe";
		} else
			return "";
	}

	public static String findPyVersion() {
		String version = "", s;

		String Command = "python -V";

		try {
			Process p = Runtime.getRuntime().exec(Command);

			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
				if (s.startsWith("Python ")) {
					version = s.split(" ")[1];
				}
			}

			s = null;

			inputStream.close();

		} catch (Exception e) {

		}

		return version;
	}

	public static long getValue(String value) {

		try {

			String val = value;

			if (value.startsWith("0x") || value.startsWith("0X")) {

				val = value.substring(2);
			}

			return Long.parseLong(val, 16);// new BigInteger(val).intValue();

		} catch (Exception e) {

			return -1;
		}
	}

	/**
	 * Use FileWriter when number of write operations are less
	 * 
	 * @param filePath
	 * @param text
	 * @param noOfLines
	 */
	public static void updateToFile(String filePath, String text) {
		File file = new File(filePath);
		BufferedWriter fr = null;
		try {
			// Below constructor argument decides whether to append or override
			fr = new BufferedWriter(new FileWriter(file, true));
			fr.write(text);
			fr.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<String> getSerialPorts() {

		List<String> portList = new ArrayList<String>();

		portList.add("Select Comm Port");

		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> thePorts = CommPortIdentifier.getPortIdentifiers();

		while (thePorts.hasMoreElements()) {

			CommPortIdentifier com = thePorts.nextElement();

			switch (com.getPortType()) {

			case CommPortIdentifier.PORT_SERIAL:

				portList.add(com.getName());

			}
		}

		return portList;
	}

	public static String getNetworkSettings(String lanName) {

		String ipAddress = null;

		String subnet = null;

		String gateway = null;

		String s = null;

		boolean isAlreadyIPDone = false;

		boolean isAlreadySubDone = false;

		try {

			String os = System.getProperty("os.name");

			if (os.startsWith(VPXConstants.WIN_OSNAME)) {

				String cmd = "netsh interface IP show addresses \"" + lanName + "\"";

				Process proc = Runtime.getRuntime().exec(cmd);

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

				while ((s = stdInput.readLine()) != null) {

					if (s.contains("IP Address:") && (!isAlreadyIPDone)) {

						ipAddress = s.split(":")[1].trim();

						isAlreadyIPDone = true;
					}

					if (s.contains("Subnet") && (!isAlreadySubDone)) {

						subnet = (s.split(":")[1].trim());

						String d = subnet.split(" ")[2].trim();

						subnet = d.substring(0, d.length() - 1);

						isAlreadyIPDone = true;
					}
					if (s.contains("Default")) {

						gateway = s.split(":")[1].trim();

					}

				}
			} else if (os.startsWith(VPXConstants.LINUX_OSNAME)) {

				String[] cmd = { "/bin/sh", "-c", "ifconfig " + lanName };

				Process proc = Runtime.getRuntime().exec(cmd);

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

				while ((s = stdInput.readLine()) != null) {

					if (s.trim().startsWith("inet addr")) {

						String[] detail = s.trim().split(" ");

						for (int i = 0; i < detail.length; i++) {

							if (detail[i].length() > 0) {

								if (detail[i].startsWith("addr:")) {

									ipAddress = detail[i].trim().split(":")[1].trim();

								} else if (detail[i].startsWith("Mask:")) {

									subnet = detail[i].trim().split(":")[1].trim();
								}
							}
						}

					}

				}

				String[] cmd1 = { "/bin/sh", "-c", "route -e | grep " + lanName };

				proc = Runtime.getRuntime().exec(cmd1);

				stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

				while ((s = stdInput.readLine()) != null) {

					if (s.startsWith("default")) {

						int j = 0;

						String[] detail = s.trim().split(" ");

						for (int i = 0; i < detail.length; i++) {

							if (detail[i].length() > 0) {
								if (j == 1) {
									gateway = detail[i].trim();
									break;
								}

								j++;
							}

						}
					}

				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		ipAddress = (ipAddress != null) ? ipAddress : " ";

		subnet = (subnet != null) ? subnet : " ";

		gateway = (gateway != null) ? gateway : " ";

		return ipAddress + "," + subnet + "," + gateway;
	}

	public static List<NWInterface> getEthernetPorts() {

		List<NWInterface> nwIfaces = new ArrayList<NWInterface>();
		String s;
		List<String> nwIface = new ArrayList<String>();

		boolean isLeft = false;

		int j = 0;

		try {
			String os = System.getProperty("os.name");

			if (os.startsWith(VPXConstants.WIN_OSNAME)) {

				Process p = Runtime.getRuntime().exec(VPXConstants.WIN_CMD_BASE);

				BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

				// reading output stream of the command
				while ((s = inputStream.readLine()) != null) {
					if (isLeft) {
						String[] ss = s.split("  ");
						for (int i = 0; i < ss.length; i++) {
							if (ss[i].trim().length() > 0) {
								nwIface.add(ss[i].trim());
							}
						}
					}
					if (s.startsWith("----"))
						isLeft = true;
				}

				s = null;

				inputStream.close();

				NWInterface ifs = null;

				for (Iterator<String> iterator = nwIface.iterator(); iterator.hasNext();) {
					String string = iterator.next();

					switch (j) {
					case 0:
						ifs = null;
						ifs = new NWInterface();
						ifs.setEnabled(string.equals("Enabled"));
						break;
					case 1:
						ifs.setConnected(string.equals("Connected"));
						break;
					case 2:
						ifs.setDedicated(string.equals("Dedicated"));
						break;
					case 3:
						ifs.setName(string);
						nwIfaces.add(ifs);
						j = -1;
						break;
					}

					j++;
				}

			} else if (os.startsWith(VPXConstants.LINUX_OSNAME)) {

				String[] cmd = { "/bin/sh", "-c", VPXConstants.LINUX_CMD_BASE };

				Process p = Runtime.getRuntime().exec(cmd);

				BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

				// reading output stream of the command

				while ((s = inputStream.readLine()) != null) {

					nwIface.add(s);

				}
				s = null;

				inputStream.close();

				NWInterface ifs = null;

				for (Iterator<String> iterator = nwIface.iterator(); iterator.hasNext();) {

					String[] string = iterator.next().split(":");

					if (string[0].trim().length() > 0) {

						ifs = null;

						ifs = new NWInterface();

						ifs.setName(string[0].trim());

						String state = string[1].trim().split(" ")[1].trim();

						if (state.contains("up") || state.contains("UP")) {

							ifs.setEnabled(true);

							ifs.setConnected(true);

							ifs.setDedicated(true);
						} else {
							ifs.setEnabled(false);

							ifs.setConnected(false);

							ifs.setDedicated(false);
						}

						nwIfaces.add(ifs);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return nwIfaces;
	}

	public static String findDisplayName(String ip) {

		String ret = "";
		try {

			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();

			for (NetworkInterface netint : Collections.list(nets)) {

				Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();

				for (InetAddress inetAddress : Collections.list(inetAddresses)) {

					if (inetAddress.getHostAddress().endsWith(ip)) {

						ret = netint.getDisplayName();

						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	public static void setCurrentNWIface(String ip) {

		try {

			String os = System.getProperty("os.name");

			if (os.startsWith(VPXConstants.WIN_OSNAME) || os.startsWith(VPXConstants.LINUX_OSNAME)) {

				Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();

				while (e.hasMoreElements()) {

					NetworkInterface currentNetwordInterface = e.nextElement();

					if (currentNetwordInterface.isLoopback() || !currentNetwordInterface.isUp())
						continue;

					List<InterfaceAddress> ls = currentNetwordInterface.getInterfaceAddresses();

					for (Iterator<InterfaceAddress> iterator = ls.iterator(); iterator.hasNext();) {

						InterfaceAddress interfaceAddress = iterator.next();

						if (interfaceAddress.getAddress().getHostAddress().equals(ip)) {

							currentInterfaceAddress = interfaceAddress;

							break;
						}

					}
				}
			}
		} catch (Exception e) {
		}
	}

	public static InterfaceAddress getCurrentInterfaceAddress() {

		return currentInterfaceAddress;

	}

	public static boolean setEthernetPort(String lan, String ip, String subnet, String gateway) {

		try {
			String os = System.getProperty("os.name");

			if (os.startsWith(VPXConstants.WIN_OSNAME)) {

				if (gateway.length() == 0) {

					gateway = "none";
				}

				String cmd = VPXConstants.RUNASADMIN + "netsh interface ip set address \"" + lan

						+ "\" static " + ip + " " + subnet + " " + gateway + " 1";

				Process pp = Runtime.getRuntime().exec(cmd);

			} else if (os.startsWith(VPXConstants.LINUX_OSNAME)) {

				String password = VPXLinuxCommand.getPasswdForRoot();

				String cmd = String.format("sudo ifconfig %s %s netmask %s up", lan, ip, subnet);

				Process pp = VPXLinuxCommand.runFromRoot(cmd, password);

				cmd = String.format("sudo ip route replace default via %s", gateway);

				pp = VPXLinuxCommand.runFromRoot(cmd, password);

			}

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}

	}

	public static NWInterface getEthernetPort(String name) {

		String s;

		List<String> nwIface = new ArrayList<String>();

		NWInterface nw = null;

		boolean isLeft = false;

		int j = 0;

		try {
			String os = System.getProperty("os.name");

			if (os.startsWith(VPXConstants.WIN_OSNAME)) {

				Process p = Runtime.getRuntime().exec(VPXConstants.WIN_CMD_BASE);

				BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

				// reading output stream of the command
				while ((s = inputStream.readLine()) != null) {
					if (isLeft) {
						String[] ss = s.split("  ");
						for (int i = 0; i < ss.length; i++) {
							if (ss[i].trim().length() > 0) {
								nwIface.add(ss[i].trim());
							}
						}
					}
					if (s.startsWith("----"))
						isLeft = true;
				}

				s = null;

				inputStream.close();

				NWInterface ifs = null;

				for (Iterator<String> iterator = nwIface.iterator(); iterator.hasNext();) {
					String string = iterator.next();

					switch (j) {
					case 0:
						ifs = null;
						ifs = new NWInterface();
						ifs.setEnabled(string.equals("Enabled"));
						break;
					case 1:
						ifs.setConnected(string.equals("Connected"));
						break;
					case 2:
						ifs.setDedicated(string.equals("Dedicated"));
						break;
					case 3:

						if (string.equals(name)) {

							ifs.setName(string);

							String[] sss = getNetworkSettings(string).split(",");

							ifs.addIPAddress(sss[0]);

							ifs.setSubnet(sss[1]);

							ifs.setGateWay(sss[2]);

							ifs.setDisplayName(findDisplayName(sss[0]));

							nw = ifs;
						}
						j = -1;
						break;
					}

					j++;
				}

			} else if (os.startsWith(VPXConstants.LINUX_OSNAME)) {

				String[] cmd = { "/bin/sh", "-c", VPXConstants.LINUX_CMD_BASE };

				Process p = Runtime.getRuntime().exec(cmd);

				BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

				// reading output stream of the command

				while ((s = inputStream.readLine()) != null) {

					nwIface.add(s);

				}
				s = null;

				inputStream.close();

				NWInterface ifs = null;

				for (Iterator<String> iterator = nwIface.iterator(); iterator.hasNext();) {

					String[] string = iterator.next().split(":");

					if (string[0].trim().length() > 0 && string[0].trim().equals(name)) {

						ifs = null;

						ifs = new NWInterface();

						ifs.setName(string[0].trim());

						String state = string[1].trim().split(" ")[1].trim();

						if (state.contains("up") || state.contains("UP")) {

							ifs.setEnabled(true);

							ifs.setConnected(true);

							ifs.setDedicated(true);

							String[] sss = getNetworkSettings(string[0].trim()).split(",");

							ifs.addIPAddress(sss[0]);

							ifs.setSubnet(sss[1]);

							ifs.setGateWay(sss[2]);

						} else {
							ifs.setEnabled(false);

							ifs.setConnected(false);

							ifs.setDedicated(false);
						}

						nw = ifs;

						break;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nw;
	}

	public static Map<String, String> getMemoryAddressVariables(String filename) {

		Map<String, String> memVars = new TreeMap<String, String>();

		memVars.put("", "");

		String str = readFile(filename, VPXConstants.DELIMITER_MAP);

		String vars = str.substring(str.lastIndexOf("--------   ----"));

		String[] varArray = vars.split("\n");

		for (int i = 1; i < varArray.length - 1; i++) {

			if (!(varArray[i].trim().equals("--------   ----") && varArray[i].contains(VPXConstants.DELIMITER_MAP))) {

				if (varArray[i].trim().length() > 0) {

					String[] var = varArray[i].trim().split("   ");

					memVars.put(var[1].trim(), var[0].trim());
				}
			}

		}

		return memVars;
	}

	public static void createOutArrayFile(String outFile, String headerFile) {

		try {

			String os = System.getProperty("os.name");

			if (os.startsWith(VPXConstants.WIN_OSNAME)) {

				// create run.bat

				Thread.sleep(150);

				String run = readFile("execute/run.data", VPXConstants.DELIMITER_FILE);

				run = run.replace("outfile", outFile);

				run = run.replace("headerfile", headerFile);

				writeFile("execute/run.bat", run);

				// start run.bat

				String[] batchArg = { "cmd", "/k", "cd /d " + System.getProperty("user.dir") + "\\execute & run.bat" };

				Thread.sleep(250);

				Process p = Runtime.getRuntime().exec(batchArg);

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String s = null;

				while ((s = stdInput.readLine()) != null) {

					VPXLogger.updateLog(s);

				}

				// System.out.println("Exit Code : "+p.exitValue());

				// delete run.bat, and other extra files

				deleteDeploymentFiles(System.getProperty("user.dir") + "/execute/bootimage.bin",
						System.getProperty("user.dir") + "/execute/bootimage.btbl", false);

				deleteDeploymentFiles(System.getProperty("user.dir") + "/execute/bootimage.h",
						System.getProperty("user.dir") + "/execute/run.bat", false);

			} else {

				// create run.bat

				Thread.sleep(150);

				String run = readFile("execute/run_linux.data", VPXConstants.DELIMITER_FILE);

				run = run.replace("outfile", outFile);

				run = run.replace("headerfile", headerFile);

				// run = run.replace("pathtochange", "execute/");

				writeFile("execute/run.sh", run);

				setExecutePermission("execute/run.sh");

				setPermission("execute/");

				// start run.bat

				// String[] batchArg = { "cmd", "/k", "cd /d " +
				// System.getProperty("user.dir") + "\\execute & run.bat" };

				Thread.sleep(250);

				Process p = Runtime.getRuntime().exec(new String[] { "sh", "-c", "execute/run.sh" });

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String s = null;

				while ((s = stdInput.readLine()) != null) {

					VPXLogger.updateLog(s);

				}

				BufferedReader stdErrInput = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				String sErr = null;

				while ((sErr = stdErrInput.readLine()) != null) {

					VPXLogger.updateLog(sErr);

				}

				// System.out.println("Exit Code : "+p.exitValue());

				// delete run.bat, and other extra files

				deleteDeploymentFiles(System.getProperty("user.dir") + "/execute/bootimage.bin",
						System.getProperty("user.dir") + "/execute/bootimage.btbl", false);

				deleteDeploymentFiles(System.getProperty("user.dir") + "/execute/bootimage.h",
						System.getProperty("user.dir") + "/execute/run.sh", false);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String[] getOutFileAsArray(String filename) {

		String[] varArray = null;

		String str = readFile(filename, VPXConstants.DELIMITER_ARRAY);

		String vars = str.substring(str.lastIndexOf("[] = {")).trim().replaceAll("0x", "").replaceAll("0X", "");

		vars = vars.replace("[] = {", "");

		if (vars != null) {

			varArray = vars.split(",");

			return varArray;

		} else {

			return null;
		}
	}

	public static String readFile(String filename, String delimiter) {
		try {
			final String CHARSET = "UTF-8";

			// final String DELIMITER = "==end==";

			File file = new File(filename);

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(file, CHARSET).useDelimiter(delimiter);

			String content = null;

			if (scanner.hasNext())
				content = scanner.next();

			scanner.close();

			return content;

		} catch (Exception e) {
			return null;
		}
	}

	public static String readFile(String filename) {

		try {

			String content = new String(
					Files.readAllBytes(Paths.get(URI.create("file:///" + getPathAsLinuxStandard(filename)))));

			return content;

		} catch (Exception e) {

			e.printStackTrace();

			return null;
		}
	}

	public static byte[] readFileToByteArray(String filename) throws IOException {

		return FileUtils.readFileToByteArray(new File(filename));
	}

	public static byte[] readFileToByteArray(String filename, int format, int delimiter) {

		String content = readFile(filename);

		String seperator = (delimiter == 0) ? System.lineSeparator() : ",";

		String[] arr = content.split(seperator);

		return getByteArray(arr, format);
	}

	private static byte[] getByteArray(String[] arr, int format) {

		ByteBuffer bb = null;
		byte[] b = null;
		// 0 - 8 Bit Hex
		// 1 - 16 Bit Hex
		// 2 - 32 Bit Hex
		// 3 - 64 Bit Hex
		// 4 - 16 Bit Signed
		// 5 - 32 Bit Signed
		// 6 - 16 Bit Unsigned
		// 7 - 32 Bit Unsigned
		// 8 - 32 Bit Floating

		switch (format) {
		case HexEditor.HEX8:

			b = new byte[arr.length];

			break;
		case HexEditor.HEX16:
		case HexEditor.SINGNEDINT16:
		case HexEditor.UNSINGNEDINT16:

			b = new byte[arr.length * 2];

			break;
		case HexEditor.HEX32:
		case HexEditor.SINGNEDINT32:
		case HexEditor.UNSINGNEDINT32:
		case HexEditor.UNSINGNEDFLOAT32:

			b = new byte[arr.length * 4];

			break;
		case HexEditor.HEX64:

			b = new byte[arr.length * 8];

			break;

		}

		switch (format) {

		case HexEditor.HEX8:

			for (int i = 0; i < arr.length; i++) {

				b[i] = (byte) Integer.parseInt(arr[i].trim(), 16);
			}

			break;

		case HexEditor.HEX16:

			bb = ByteBuffer.allocate(arr.length * 2);

			for (int i = 0; i < arr.length; i++) {

				byte[] bArr = new byte[2];

				String val = arr[i].trim();

				bArr[1] = (byte) Integer.parseInt(val.substring(0, 2), 16);

				bArr[0] = (byte) Integer.parseInt(val.substring(2, 4), 16);

				bb.put(bArr);
			}

			b = bb.array();

			break;

		case HexEditor.SINGNEDINT16:

			bb = ByteBuffer.allocate(arr.length * 2);

			for (int i = 0; i < arr.length; i++) {

				byte[] bArra = BigInteger.valueOf(Long.parseLong(arr[i].trim())).toByteArray();

				byte[] bArr = new byte[bArra.length];

				bArr[1] = bArra[0];

				bArr[0] = bArra[1];

				bb.put(bArr);
			}

			b = bb.array();

			break;

		case HexEditor.UNSINGNEDINT16:

			bb = ByteBuffer.allocate(arr.length * 2);

			for (int i = 0; i < arr.length; i++) {

				byte[] bArr = new byte[2];

				String val = arr[i].trim();

				bArr[1] = (byte) Integer.parseInt(val.substring(0, 2), 16);

				bArr[0] = (byte) Integer.parseInt(val.substring(2, 4), 16);

				bb.put(bArr);
			}

			b = bb.array();

			break;

		case HexEditor.HEX32:

			bb = ByteBuffer.allocate(arr.length * 4);

			for (int i = 0; i < arr.length; i++) {

				byte[] bArr = new byte[4];

				String val = arr[i].trim();

				bArr[3] = (byte) Integer.parseInt(val.substring(0, 2), 16);

				bArr[2] = (byte) Integer.parseInt(val.substring(2, 4), 16);

				bArr[1] = (byte) Integer.parseInt(val.substring(4, 6), 16);

				bArr[0] = (byte) Integer.parseInt(val.substring(6, 8), 16);

				bb.put(bArr);
			}

			b = bb.array();

			break;

		case HexEditor.SINGNEDINT32:

			bb = ByteBuffer.allocate(arr.length * 4);

			for (int i = 0; i < arr.length; i++) {

				byte[] bArra = BigInteger.valueOf(Long.parseLong(arr[i].trim())).toByteArray();

				byte[] bArr = new byte[bArra.length];

				bArr[3] = bArra[0];

				bArr[2] = bArra[1];

				bArr[1] = bArra[2];

				bArr[0] = bArra[3];

				bb.put(bArr);
			}

			b = bb.array();

			break;

		case HexEditor.UNSINGNEDINT32:

			bb = ByteBuffer.allocate(arr.length * 4);

			for (int i = 0; i < arr.length; i++) {

				byte[] bArra = BigInteger.valueOf(Long.parseLong(arr[i].trim())).toByteArray();

				byte[] bArr = new byte[bArra.length];

				bArr[3] = bArra[0];

				bArr[2] = bArra[1];

				bArr[1] = bArra[2];

				bArr[0] = bArra[3];

				bb.put(bArr);
			}

			b = bb.array();

			break;

		case HexEditor.UNSINGNEDFLOAT32:

			bb = ByteBuffer.allocate(arr.length * 4);

			for (int i = 0; i < arr.length; i++) {

				// long l = convertIntoLong(arr[i].trim());

				// byte[] bArra = BigInteger.valueOf(l).toByteArray();

				byte[] bArra = convertIntoByte(arr[i].trim());// BigInteger.valueOf(l).toByteArray();

				byte[] bArr = new byte[4];

				if (bArra.length == 1) {
					bArr[3] = bArra[0];

					bArr[2] = bArra[0];

					bArr[1] = bArra[0];

					bArr[0] = bArra[0];
				} else {

					bArr[3] = bArra[0];

					bArr[2] = bArra[1];

					bArr[1] = bArra[2];

					bArr[0] = bArra[3];
				}

				bb.put(bArr);
			}

			b = bb.array();

			break;

		case HexEditor.HEX64:

			bb = ByteBuffer.allocate(arr.length * 8);

			for (int i = 0; i < arr.length; i++) {

				byte[] bArr = new byte[8];

				String val = arr[i].trim();

				bArr[7] = (byte) Integer.parseInt(val.substring(0, 2), 16);

				bArr[6] = (byte) Integer.parseInt(val.substring(2, 4), 16);

				bArr[5] = (byte) Integer.parseInt(val.substring(4, 6), 16);

				bArr[4] = (byte) Integer.parseInt(val.substring(6, 8), 16);

				bArr[3] = (byte) Integer.parseInt(val.substring(8, 10), 16);

				bArr[2] = (byte) Integer.parseInt(val.substring(10, 12), 16);

				bArr[1] = (byte) Integer.parseInt(val.substring(12, 14), 16);

				bArr[0] = (byte) Integer.parseInt(val.substring(14, 16), 16);

				bb.put(bArr);
			}

			b = bb.array();

			break;

		}

		return b;
	}

	private static byte[] convertIntoByte(String str) {

		String val = String.format("%08X", Float.floatToIntBits(Float.parseFloat(str)));

		byte[] bArr = new byte[4];

		bArr[0] = (byte) Integer.parseInt(val.substring(0, 2), 16);

		bArr[1] = (byte) Integer.parseInt(val.substring(2, 4), 16);

		bArr[2] = (byte) Integer.parseInt(val.substring(4, 6), 16);

		bArr[3] = (byte) Integer.parseInt(val.substring(6, 8), 16);

		return bArr;

	}

	private static long convertIntoLong(String str) {

		String val = String.format("%08X", Float.floatToIntBits(Float.parseFloat(str)));

		byte[] bArr = new byte[4];

		bArr[0] = (byte) Integer.parseInt(val.substring(0, 2), 16);

		bArr[1] = (byte) Integer.parseInt(val.substring(2, 4), 16);

		bArr[2] = (byte) Integer.parseInt(val.substring(4, 6), 16);

		bArr[3] = (byte) Integer.parseInt(val.substring(6, 8), 16);

		long s = bytesToLong(bArr);

		return s;

	}

	private static long bytesToLong(byte[] b) {

		long result = 0;

		for (int i = 0; i < 4; i++) {

			result <<= 8;

			result |= (b[i] & 0xFF);
		}

		return result;
	}

	public static void writeFile(String filename, String content) {
		try {

			File newTextFile = new File(filename);

			FileWriter fw = new FileWriter(newTextFile);

			content = content.replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\"));

			fw.write(content);

			fw.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static boolean isFileValid(String fileName) {

		return isFileValid(fileName, false);
	}

	public static boolean isBetween(long value, long min, long max) {

		return (value >= min && value <= max);
	}

	public static boolean isFileValid(String fileName, boolean isDirectory) {

		try {

			if (fileName.length() == 0)
				return false;

			if (isDirectory) {
				fileName = fileName.substring(0, fileName.lastIndexOf("/"));
			}

			File f = new File(fileName);

			return f.exists();

		} catch (Exception e) {
			return false;
		}

	}

	public static boolean isFileNameValid(String fileName) {

		try {

			if (fileName.length() < 3)
				return false;
			else
				return true;

		} catch (Exception e) {
			return false;
		}

	}

	public static void deleteAllGeneratedFilesAndFlders(String path, String deployFile, String cfgFile) {

		deleteDeploymentFiles(path + "/" + VPXConstants.ResourceFields.DEPLOYMENTFILE,
				path + "/" + VPXConstants.ResourceFields.DEPLOYMENTCONFIGFILE, false);

		deleteDeploymentFiles("images", "", true);

		deleteDeploymentFiles("tmp", "", true);
	}

	private static void setPermission(String fileName) {

		String s = null;

		try {

			String cmd = String.format("chmod -R 777 %s", fileName);

			Process proc = Runtime.getRuntime().exec(cmd);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			while ((s = stdInput.readLine()) != null) {
				VPXLogger.updateLog(s);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void setExecutePermission(String fileName) {

		String s = null;

		try {

			String cmd = String.format("chmod +x %s", fileName);

			Process proc = Runtime.getRuntime().exec(cmd);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			while ((s = stdInput.readLine()) != null) {
				VPXLogger.updateLog(s);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void deleteDeploymentFiles(String deployFile, String cfgFile, boolean isdirectory) {

		String cmd = "";

		String os = System.getProperty("os.name");

		if (os.startsWith(VPXConstants.WIN_OSNAME)) {

			if (isdirectory) {
				cmd = String.format("cmd /c rmdir /S /Q %s %s", deployFile, cfgFile);
			} else {
				cmd = String.format("cmd /c del /F %s %s", deployFile.replaceAll("/", "\\\\"),
						cfgFile.replaceAll("/", "\\\\"));
			}
		} else {

			if (isdirectory) {

				// setPermission(deployFile);

				cmd = String.format("rm -rf %s %s", deployFile, cfgFile);
			} else {
				cmd = String.format("rm -rf %s %s", deployFile, cfgFile);
			}

		}

		String s = null;

		try {

			Process proc = Runtime.getRuntime().exec(cmd);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			while ((s = stdInput.readLine()) != null) {
				VPXLogger.updateLog(s);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void setCurrentPassword(String password) {

		updateProperties(VPXConstants.ResourceFields.SECURITY_PWD, encodePassword(password));
	}

	public static String getDecodedPassword() {

		return decodePassword(getCurrentPassword());
	}

	public static String getCurrentPassword() {

		return VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.SECURITY_PWD);
	}

	public static String encodePassword(String password) {

		return Base64.getEncoder().encodeToString(Base64.getEncoder().encode(password.getBytes()));
	}

	public static String decodePassword(String password) {

		return new String(Base64.getDecoder().decode(Base64.getDecoder().decode(password)));
	}

	public static boolean createWorkspaceDirs(String rootPath) {

		boolean isWritable = false;

		try {

			File root = new File(rootPath);

			String os = System.getProperty("os.name");

			if (os.startsWith(VPXConstants.WIN_OSNAME)) {

				File[] s = File.listRoots();

				for (int i = 0; i < s.length; i++) {

					if (s[i].canWrite()) {

						if (root.getPath().startsWith(s[i].getPath())) {

							isWritable = true;

							break;
						}
					}
				}

			} else {

				isWritable = true;

			}

			if (isWritable) {

				root.mkdirs();// FileUtils.forceMkdir(root);

				FileUtils.forceMkdir(new File(
						rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG)));

				FileUtils.forceMkdir(new File(
						rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_EXECUTE)));

				FileUtils.forceMkdir(new File(
						rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG) + "/"
								+ VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG_EVENT)));

				FileUtils.forceMkdir(new File(
						rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG) + "/"
								+ VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG_ERROR)));

				FileUtils.forceMkdir(new File(
						rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG) + "/"
								+ VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG_CONSOLE)));

				FileUtils.forceMkdir(new File(
						rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG) + "/"
								+ VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_LOG_MESSAGE)));

				FileUtils.forceMkdir(new File(rootPath + "/"
						+ VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM)));

				FileUtils.forceMkdir(new File(
						rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_DATA)));

				FileUtils.forceMkdir(new File(
						rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_TFTP)));

				createSubsystemFolder(rootPath);

			} else {

				VPXLogger.updateLog("root directory is wrong");

				return false;
			}
		} catch (Exception e) {

			e.printStackTrace();

			return false;

		}

		return true;
	}

	public static void createSubsystemFolder(String rootPath) throws Exception {

		// DSP folder
		FileUtils.forceMkdir(
				new File(rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM)
						+ "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM_DSP)));

		// DSP bin folder
		FileUtils.forceMkdir(
				new File(rootPath + "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM)
						+ "/" + VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM_DSP) + "/"
						+ VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM_DSP_BIN)));

		// DSP core folders
		for (int i = 0; i < 8; i++) {

			FileUtils
					.forceMkdir(
							new File(
									rootPath + "/"
											+ VPXUtilities
													.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM)
											+ "/"
											+ VPXUtilities.getString(
													VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM_DSP)
											+ "/"
											+ (VPXUtilities.getString(
													VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM_DSP_CORE)
													+ " " + i)));

		}

	}

	public static String getPathAsLinuxStandard(String path) {

		return path.replaceAll("\\\\", "/");
	}

	public static void mkDir(String parent, String path) {

		File subdir = new File(parent, path);

		subdir.mkdir();

	}
}

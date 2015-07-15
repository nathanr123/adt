/**
 * 
 */
package com.cti.vpx.util;

import gnu.io.CommPortIdentifier;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javolution.io.Struct.Enum32;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cti.vpx.command.ATP.PROCESSOR_TYPE;
import com.cti.vpx.command.ATPCommand;
import com.cti.vpx.command.DSPATPCommand;
import com.cti.vpx.command.P2020ATPCommand;
import com.cti.vpx.model.NWInterface;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.view.VPX_ETHWindow;

/**
 * @author nathanr_kamal
 *
 */
public class VPXUtilities {

	private static int scrWidth;

	private static int scrHeight;

	private final static String WIN_OSNAME = "Windows";

	private final static String WIN_CMD_BASE = "netsh interface show interface";

	private static final String MSG = "VPX_Dual_adt";

	private static final String RUNASADMIN = "elevate ";

	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private static final String NAME_PATTERN = "^[a-zA-Z\\d-_]+$";

	private static final Pattern ipPattern = Pattern.compile(IPADDRESS_PATTERN);

	private static final Pattern namePattern = Pattern.compile(NAME_PATTERN);

	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(MSG);

	private static final DateFormat DATEFORMAT_FULL = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private static final DateFormat DATEFORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");

	private static final DateFormat DATEFORMAT_TIME = new SimpleDateFormat("HH:mm:ss");

	private static final DateFormat DATEFORMAT_TIMEFULL = new SimpleDateFormat("HH:mm:ss.SSS");

	public static final String GENERAL_SPLASH = "general.splash";

	public static final String GENERAL_MEMORY = "general.memorybar";

	public static final String LOG_SERIALNO = "log.serialno";

	public static final String LOG_ENABLE = "log.enable";

	public static final String LOG_PROMPT = "log.promptSno";

	public static final String LOG_MAXFILE = "log.maxfile";

	public static final String LOG_MAXFILESIZE = "log.maxfilesize";

	// public static final String LOG_FILEPATH = "log.filepath";

	public static final String LOG_FILEFORMAT = "log.fileformat";

	public static final String LOG_APPENDCURTIME = "log.appendcurtime";

	public static final String LOG_OVERWRITE = "log.overwrite";

	public static final String PYTHON_VERSION = "python.version";

	public static final String PYTHON_INTERPRETERPATH = "python.intrptrpath";

	public static final String PYTHON_USEDUMMY = "python.usedummy";

	public static final String PYTHON_DUMMYFILE = "python.dummyfile";

	private static Properties props;

	private static boolean isLogEnabled = false;

	private static VPXSystem vpxSystem = null;// new VPXSystem();

	private static VPX_ETHWindow parent;

	private static String currentProcessor = "";

	private static String currentSubSystem = "";

	private static String currentProcType = "";

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
			str += elapsedHours + " hours ";
		}
		if (elapsedMinutes > 0) {
			str += elapsedMinutes + " minutes ";
		}
		if (elapsedSeconds > 0) {
			str += elapsedSeconds + " seconds ";
		}
		if (elapsedMilliSeconds > 0) {
			str += elapsedMilliSeconds + "  milli seconds ";
		}

		return str;
	}

	public static void setParent(VPX_ETHWindow prnt) {
		parent = prnt;
	}

	public static void updateLog(String log) {
		parent.updateLog(log);
	}

	public static void updateStatus(String status) {
		parent.updateStatus(status);
	}

	public static String getCurrentTime(int format) {
		String ret = null;
		if (format == 0)
			ret = DATEFORMAT_FULL.format(Calendar.getInstance().getTime());
		else if (format == 1)
			ret = DATEFORMAT_DATE.format(Calendar.getInstance().getTime());
		else if (format == 2)
			ret = DATEFORMAT_TIME.format(Calendar.getInstance().getTime());
		return ret;
	}

	public static String getCurrentTime(int format, long millis) {
		String ret = null;
		if (format == 0)
			ret = DATEFORMAT_FULL.format(millis);
		else if (format == 1)
			ret = DATEFORMAT_DATE.format(millis);
		else if (format == 2)
			ret = DATEFORMAT_TIME.format(millis);
		else if (format == 3)
			ret = DATEFORMAT_TIMEFULL.format(millis);
		return ret;
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

		vpxSystem = readFromXMLFile();

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

		try {

			return new ImageIcon(name).getImage();

		} catch (Exception ioe) {

			ioe.printStackTrace();
		}

		return null;
	}

	public static Processor getSelectedProcessor(String path) {

		Processor pros = null;
		/*
		 * List<Processor> ps = VPXUtilities.getVPXSystem().getProcessors();
		 * 
		 * for (Iterator<Processor> iterator = ps.iterator();
		 * iterator.hasNext();) { Processor processor = iterator.next(); if
		 * (path.startsWith(processor.getiP_Addresses())) { pros = processor;
		 * break; }
		 * 
		 * }
		 */
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

	public static void writeToXMLFile(VPXSystem system) {
		try {

			File folder = new File(resourceBundle.getString("Scan.processor.data.path"));

			if (!folder.exists()) {

				folder.mkdir();
			}

			File file = new File(resourceBundle.getString("Scan.processor.data.path") + "\\"
					+ resourceBundle.getString("Scan.processor.data.xml"));

			JAXBContext jaxbContext = JAXBContext.newInstance(VPXSystem.class);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(system, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public static boolean writetoXLSFile(VPXSystem system) {

		File folder = new File(resourceBundle.getString("Scan.processor.data.path"));

		if (!folder.exists()) {

			folder.mkdir();
		}

		String FILE_PATH = resourceBundle.getString("Scan.processor.data.path") + "\\" + system.getName() + ".xls";

		Workbook workbook = new XSSFWorkbook();

		Sheet subsystemSheet = workbook.createSheet(system.getName());

		List<VPXSubSystem> subSystems = system.getSubsystem();

		int rowIndex = 0;

		for (VPXSubSystem subsystem : subSystems) {

			Row row = subsystemSheet.createRow(rowIndex++);

			int cellIndex = 0;

			row.createCell(cellIndex++).setCellValue(subsystem.getId());

			row.createCell(cellIndex++).setCellValue(subsystem.getSubSystem());

			row.createCell(cellIndex++).setCellValue(subsystem.getIpP2020());

			row.createCell(cellIndex++).setCellValue(subsystem.getIpDSP1());

			row.createCell(cellIndex++).setCellValue(subsystem.getIpDSP2());

		}

		try {

			FileOutputStream fos = new FileOutputStream(FILE_PATH);

			workbook.write(fos);

			fos.close();

			return true;

		} catch (Exception e) {
			return false;
		}

	}

	public static void writeProperties() {
		OutputStream output = null;
		try {

			Properties prop = new Properties();

			output = new FileOutputStream(resourceBundle.getString("system.preference.property.name"));

			// General Tab Settings
			prop.setProperty(GENERAL_SPLASH, String.valueOf(true));
			prop.setProperty(GENERAL_MEMORY, String.valueOf(true));

			// Log Tab Settings
			prop.setProperty(LOG_ENABLE, String.valueOf(true));
			prop.setProperty(LOG_PROMPT, String.valueOf(true));
			prop.setProperty(LOG_MAXFILE, String.valueOf(true));
			prop.setProperty(LOG_SERIALNO, "");
			prop.setProperty(LOG_MAXFILESIZE, "2");
			prop.setProperty(LOG_SERIALNO, "");
			prop.setProperty(LOG_FILEFORMAT, "$(SerialNumber)_$(CurrentTime)");
			prop.setProperty(LOG_APPENDCURTIME, String.valueOf(true));
			prop.setProperty(LOG_OVERWRITE, String.valueOf(false));

			// Python Tab Settings
			prop.setProperty(PYTHON_INTERPRETERPATH, getPythonInterpreterPath());
			prop.setProperty(PYTHON_USEDUMMY, String.valueOf(false));
			prop.setProperty(PYTHON_DUMMYFILE, "");
			prop.setProperty(PYTHON_VERSION, findPyVersion());

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

		return ipPattern.matcher(ip.trim()).matches();
	}

	public static boolean isValidName(String name) {

		return namePattern.matcher(name.trim()).matches();
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

			File file = new File(resourceBundle.getString("Scan.processor.data.path") + "\\"
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

			File file = new File(resourceBundle.getString("Scan.processor.data.path") + "\\"
					+ resourceBundle.getString("Scan.processor.data.xml"));

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

	public static VPXSystem readFromXLSFile() {

		String FILE_PATH = "data\\VPXSystem.xls";

		VPXSystem vpx = new VPXSystem();

		List<VPXSubSystem> subsystems = new ArrayList<VPXSubSystem>();

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(FILE_PATH);

			Workbook workbook = new XSSFWorkbook(fis);

			int numberOfSheets = workbook.getNumberOfSheets();

			for (int i = 0; i < numberOfSheets; i++) {

				Sheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rowIterator = sheet.iterator();

				while (rowIterator.hasNext()) {

					VPXSubSystem subsystem = new VPXSubSystem();

					Row row = rowIterator.next();

					Iterator<Cell> cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();

						switch (cell.getColumnIndex()) {
						case 0:

							subsystem.setId((int) cell.getNumericCellValue());

							break;

						case 1:

							subsystem.setSubSystem(cell.getStringCellValue());

							break;

						case 2:

							subsystem.setIpP2020(cell.getStringCellValue());

							break;

						case 3:

							subsystem.setIpDSP1(cell.getStringCellValue());

							break;

						case 4:

							subsystem.setIpDSP2(cell.getStringCellValue());

							break;
						}

					}

					subsystems.add(subsystem);
				}

			}

			fis.close();

			vpx.setSubsystem(subsystems);

			return vpx;

		} catch (Exception e) {
			return null;
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

	/**
	 * Use Streams when you are dealing with raw data, binary data
	 * 
	 * @param data
	 */
	public static void appendUsingOutputStream(String fileName, String data) {
		OutputStream os = null;
		try {
			// below true flag tells OutputStream to append
			os = new FileOutputStream(new File(fileName), true);
			os.write(data.getBytes(), 0, data.length());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Use BufferedWriter when number of write operations are more
	 * 
	 * @param filePath
	 * @param text
	 * @param noOfLines
	 */
	public static void appendUsingBufferedWriter(String filePath, String text, int noOfLines) {
		File file = new File(filePath);
		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			// to append to file, you need to initialize FileWriter using below
			// constructor
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			for (int i = 0; i < noOfLines; i++) {
				br.newLine();
				// you can use write or append method
				br.write(text);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Use FileWriter when number of write operations are less
	 * 
	 * @param filePath
	 * @param text
	 * @param noOfLines
	 */
	public static void appendUsingFileWriter(String filePath, String text) {
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

		Enumeration<CommPortIdentifier> thePorts = CommPortIdentifier.getPortIdentifiers();

		while (thePorts.hasMoreElements()) {

			CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();

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

			if (os.startsWith(WIN_OSNAME)) {

				Process p = Runtime.getRuntime().exec(WIN_CMD_BASE);

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
						// String[] sss = getNetworkSettings(string).split(",");
						// ifs.addIPAddress(sss[0]);
						// ifs.setSubnet(sss[1]);
						// ifs.setGateWay(sss[2]);
						nwIfaces.add(ifs);
						j = -1;
						break;
					}

					j++;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nwIfaces;
	}

	public static boolean setEthernetPort(String lan, String ip, String subnet, String gateway) {

		try {

			String cmd = RUNASADMIN + "netsh interface ip set address \"" + lan

			+ "\" static " + ip + " " + subnet + " " + gateway + " 1";

			Process pp = Runtime.getRuntime().exec(cmd);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(pp.getInputStream()));

			String s = "";

			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			BufferedReader stderr = new BufferedReader(new InputStreamReader(pp.getErrorStream()));

			while ((s = stderr.readLine()) != null) {
				System.out.println(s);
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

			if (os.startsWith(WIN_OSNAME)) {

				Process p = Runtime.getRuntime().exec(WIN_CMD_BASE);

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

							nw = ifs;
						}
						j = -1;
						break;
					}

					j++;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nw;
	}

	/**
	 * @return the currentProcessor
	 */
	public static String getCurrentProcessor() {
		return currentProcessor;
	}

	/**
	 * @return the currentSubSystem
	 */
	public static String getCurrentSubSystem() {
		return currentSubSystem;
	}

	/**
	 * @param currentSubSystem
	 *            the currentSubSystem to set
	 */
	public static void setCurrentSubSystem(String currentSubSystem) {
		VPXUtilities.currentSubSystem = currentSubSystem;
	}

	/**
	 * @return the currentProcType
	 */
	public static String getCurrentProcType() {
		return currentProcType;
	}

	/**
	 * @param currentProcType
	 *            the currentProcType to set
	 */
	public static void setCurrentProcType(String currentProcType) {
		VPXUtilities.currentProcType = currentProcType;
	}

	/**
	 * @param currentProcessor
	 *            the currentProcessor to set
	 */
	public static void setCurrentProcessor(String sub, String procType, String currentProcessor) {

		VPXUtilities.currentSubSystem = sub;

		VPXUtilities.currentProcessor = currentProcessor;

		VPXUtilities.currentProcType = procType;

	}

}

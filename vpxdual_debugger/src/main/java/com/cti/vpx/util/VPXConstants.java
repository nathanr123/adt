package com.cti.vpx.util;

import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class VPXConstants {
	/*
	 * 
	 * Constant Values
	 */

	public final static String VPXROOT = "VPXSystem";

	public final static String VPXUNLIST = "Unlisted";

	public final static String WIN_OSNAME = "Windows";

	public final static String LINUX_OSNAME = "Linux";

	public final static String WIN_CMD_BASE = "netsh interface show interface";

	public final static String LINUX_CMD_BASE = "ip link show | grep UP |grep eth | awk '{print $2,$8,$9}'";

	public static final String RESOURCENAME = "VPX_Dual_adt";

	public static final String RUNASADMIN = "elevate ";

	public static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public static final String NAME_PATTERN = "^[a-zA-Z\\d-_]+$";

	public static final Pattern IPPattern = Pattern.compile(IPADDRESS_PATTERN);

	public static final Pattern NAMEPATTERN = Pattern.compile(NAME_PATTERN);

	public static final DateFormat DATEFORMAT_FULL = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static final DateFormat DATEFORMAT_FILE = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

	public static final DateFormat DATEFORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");

	public static final DateFormat DATEFORMAT_TIME = new SimpleDateFormat("HH:mm:ss");

	public static final DateFormat DATEFORMAT_TIMEFULL = new SimpleDateFormat("HH:mm:ss.SSS");

	public static final int UARTMODE = 0;

	public static final int ETHMODE = 1;

	public static final int SUBSYSTEMAVAILBLE = 0;

	public static final int SUBNAMEAVAILBLE = -1;

	public static final int P2020AVAILBLE = -2;

	public static final int DSP1AVAILBLE = -3;

	public static final int DSP2AVAILBLE = -4;

	public static final int SUBNAMEUNLIST = -5;

	public static final int MAXRESPONSETIMEOUT = 5;

	public static final int PERIODICITY = 03;

	public static final Font BISTRESULTFONT = new Font("Tahoma", Font.BOLD, 12);

	public static final int INFO = 0;

	public static final int ERROR = 1;

	public static final int WARN = 2;

	public static final int FATAL = 3;

	public static final int CONFIGURATION = 0;

	public static final int COMPILATION = 1;

	public static final int PROCESSOR_SELECTED_MODE_NONE = 0;

	public static final int PROCESSOR_SELECTED_MODE_P2020 = 1;

	public static final int PROCESSOR_SELECTED_MODE_DSP = 2;

	public static final int PROCESSOR_SELECTED_MODE_SUBSYSTEM = 3;

	public static final StyleContext PROCESSOR_MESSAGE_DISPLAY_CONTEXT = new StyleContext();

	public static final StyledDocument PROCESSOR_MESSAGE_DISPLAY_DOCUMENT = new DefaultStyledDocument(
			PROCESSOR_MESSAGE_DISPLAY_CONTEXT);

	public static final StyleContext USER_MESSAGE_DISPLAY_CONTEXT = new StyleContext();

	public static final StyledDocument USER_MESSAGE_DISPLAY_DOCUMENT = new DefaultStyledDocument(
			USER_MESSAGE_DISPLAY_CONTEXT);

	public static final int MB = 1024 * 1024;

	public static final int MAX_MEMORY_BROWSER = 4;

	public static final int MAX_MEMORY_PLOT = 3;

	public static final int MAX_PROCESSOR = 3;

	/*
	 * 
	 * Field Values
	 */

	public static class ResourceFields {

		public static final String GENERAL_SPLASH = "general.splash";

		public static final String GENERAL_MEMORY = "general.memorybar";

		public static final String SECURITY_PWD = "security.pwd";

		public static final String FILTER_SUBNET = "filter.subnet";

		public static final String LOG_ENABLE = "log.enable";

		public static final String LOG_PROMPT = "log.promptSno";

		public static final String LOG_MAXFILE = "log.maxfile";

		public static final String LOG_MAXFILESIZE = "log.maxfilesize";

		public static final String LOG_FILEPATH = "log.filepath";

		public static final String LOG_FILEFORMAT = "log.fileformat";

		public static final String LOG_APPENDCURTIME = "log.appendcurtime";

		public static final String LOG_OVERWRITE = "log.overwrite";

		public static final String PATH_PYTHON = "path.python";

		public static final String PATH_MAP = "path.map";

		public static final String PATH_PRELINKER = "path.prelinker";

		public static final String PATH_STRIPER = "path.striper";

		public static final String PATH_OFD = "path.ofd";

		public static final String PATH_MAL = "path.mal";

		public static final String PATH_NML = "path.nml";

		public static final String PATH_DUMMY = "path.dummy";

		public static final String DUMMY_CHK = "dummy.chk";

		public static final String PATH_CORE0 = "path.core0";

		public static final String PATH_CORE1 = "path.core1";

		public static final String PATH_CORE2 = "path.core2";

		public static final String PATH_CORE3 = "path.core3";

		public static final String PATH_CORE4 = "path.core4";

		public static final String PATH_CORE5 = "path.core5";

		public static final String PATH_CORE6 = "path.core6";

		public static final String PATH_CORE7 = "path.core7";

		public static final String PATH_OUT = "path.out";

		public static final String DEPLOYMENTFILE = "deploymnet_C678.json";

		public static final String DEPLOYMENTCONFIGFILE = "maptoolCfg_C678.json";

	}

	public static class Icons {

		// Icon Names

		public static final String ICON_SAVE_NAME = "save.gif";

		public static final String ICON_COPY_NAME = "copy.gif";

		public static final String ICON_CUT_NAME = "cut.gif";

		public static final String ICON_DELETE_EXIT_NAME = "delete.gif";

		public static final String ICON_UNDO_NAME = "undo.gif";

		public static final String ICON_REDO_NAME = "redo.gif";

		public static final String ICON_OPEN_NAME = "open.gif";

		public static final String ICON_PASTE_NAME = "copy.gif";

		public static final String ICON_CORNET_NAME = "cornet.png";

		public static final String ICON_DOWNLOAD_NAME = "download.png";

		public static final String ICON_UPLOAD_NAME = "upload.png";

		public static final String ICON_CLEAR_NAME = "clear.gif";

		public static final String ICON_DOWNLOADOUT_NAME = "download1.png";

		public static final String ICON_RUN_NAME = "play.png";

		public static final String ICON_PAUSE_NAME = "pause.png";

		public static final String ICON_CONFIG_NAME = "config.png";

		public static final String ICON_DETAIL_NAME = "properties.gif";

		public static final String ICON_REFRESH_NAME = "refresh.png";

		public static final String ICON_MEMORY_NAME = "memory2.gif";

		public static final String ICON_MAD_NAME = "mad.png";

		public static final String ICON_MAD_WIZARD_NAME = "config_wiz.gif";

		public static final String ICON_PLOT_NAME = "plot.png";

		public static final String ICON_BIST_NAME = "BIST.png";

		public static final String ICON_EXECUTION_NAME = "execution.png";

		public static final String ICON_ETHFLASH_NAME = "memory1.png";

		public static final String ICON_PROCESSOR_NAME = "Processor4.jpg";

		public static final String ICON_VPXSYSTEM_NAME = "System.jpg";

		public static final String ICON_VPXSUBSYSTEM_NAME = "Slot.jpg";

		public static final String ICON_CLOSE_NAME = "close4.png";

		public static final String ICON_DETACH_NAME = "detach.png";

		public static final String ICON_SETTINGS_NAME = "settings2.png";

		public static final String ICON_FILTER_NAME = "filter.png";

		public static final String IMAGE_BG_NAME = "BG.jpg";

		public static final String ICON_HELP_NAME = "help.png";

		public static final String ICON_ABOUT_NAME = "detail.png";

		public static final String ICON_WATERFALL_NAME = "waterfall.png";

		public static final String ICON_AMPLITUDE_NAME = "amplitude.png";

		// Icons

		public static final Icon ICON_EMPTY = VPXUtilities.getEmptyIcon(14, 14);

		public static final ImageIcon ICON_HELP = VPXUtilities.getImageIcon(ICON_HELP_NAME, 14, 14);

		public static final ImageIcon ICON_ABOUT = VPXUtilities.getImageIcon(ICON_ABOUT_NAME, 14, 14);

		public static final ImageIcon ICON_FILTER = VPXUtilities.getImageIcon(ICON_FILTER_NAME, 14, 14);

		public static final ImageIcon ICON_SETTINGS = VPXUtilities.getImageIcon(ICON_SETTINGS_NAME, 14, 14);

		public static final ImageIcon ICON_SAVE = VPXUtilities.getImageIcon(ICON_SAVE_NAME, 14, 14);

		public static final ImageIcon ICON_COPY = VPXUtilities.getImageIcon(ICON_COPY_NAME, 14, 14);

		public static final ImageIcon ICON_UNDO = VPXUtilities.getImageIcon(ICON_UNDO_NAME, 14, 14);

		public static final ImageIcon ICON_DELETE_EXIT = VPXUtilities.getImageIcon(ICON_DELETE_EXIT_NAME, 14, 14);

		public static final ImageIcon ICON_CUT = VPXUtilities.getImageIcon(ICON_CUT_NAME, 14, 14);

		public static final ImageIcon ICON_REDO = VPXUtilities.getImageIcon(ICON_REDO_NAME, 14, 14);

		public static final ImageIcon ICON_OPEN = VPXUtilities.getImageIcon(ICON_OPEN_NAME, 14, 14);

		public static final ImageIcon ICON_PASTE = VPXUtilities.getImageIcon(ICON_PASTE_NAME, 14, 14);

		public static final ImageIcon ICON_CORNET = VPXUtilities.getImageIcon(ICON_CORNET_NAME, 14, 14);

		public static final ImageIcon ICON_CORNET_BIG = VPXUtilities.getImageIcon(ICON_CORNET_NAME, 50, 50);

		public static final ImageIcon ICON_DOWNLOAD = VPXUtilities.getImageIcon(ICON_DOWNLOAD_NAME, 14, 14);

		public static final ImageIcon ICON_UPLOAD = VPXUtilities.getImageIcon(ICON_UPLOAD_NAME, 14, 14);

		public static final ImageIcon ICON_CLEAR = VPXUtilities.getImageIcon(ICON_CLEAR_NAME, 14, 14);

		public static final ImageIcon ICON_DOWNLOADOUT = VPXUtilities.getImageIcon(ICON_DOWNLOADOUT_NAME, 14, 14);

		public static final ImageIcon ICON_RUN = VPXUtilities.getImageIcon(ICON_RUN_NAME, 14, 14);

		public static final ImageIcon ICON_PAUSE = VPXUtilities.getImageIcon(ICON_PAUSE_NAME, 14, 14);

		public static final ImageIcon ICON_CONFIG = VPXUtilities.getImageIcon(ICON_CONFIG_NAME, 14, 14);

		public static final ImageIcon ICON_DETAIL = VPXUtilities.getImageIcon(ICON_DETAIL_NAME, 14, 14);

		public static final ImageIcon ICON_REFRESH = VPXUtilities.getImageIcon(ICON_REFRESH_NAME, 14, 14);

		public static final ImageIcon ICON_MEMORY = VPXUtilities.getImageIcon(ICON_MEMORY_NAME, 14, 14);

		public static final ImageIcon ICON_MAD = VPXUtilities.getImageIcon(ICON_MAD_NAME, 14, 14);

		public static final ImageIcon ICON_MAD_WIZARD = VPXUtilities.getImageIcon(ICON_MAD_WIZARD_NAME, 14, 14);

		public static final ImageIcon ICON_PLOT = VPXUtilities.getImageIcon(ICON_PLOT_NAME, 14, 14);

		public static final ImageIcon ICON_BIST = VPXUtilities.getImageIcon(ICON_BIST_NAME, 14, 14);

		public static final ImageIcon ICON_EXECUTION = VPXUtilities.getImageIcon(ICON_EXECUTION_NAME, 14, 14);

		public static final ImageIcon ICON_ETHFLASH = VPXUtilities.getImageIcon(ICON_ETHFLASH_NAME, 14, 14);

		public static final ImageIcon ICON_PROCESSOR = VPXUtilities.getImageIcon(ICON_PROCESSOR_NAME, 14, 14);

		public static final ImageIcon ICON_VPXSYSTEM = VPXUtilities.getImageIcon(ICON_VPXSYSTEM_NAME, 18, 18);

		public static final ImageIcon ICON_VPXSUBSYSTEM = VPXUtilities.getImageIcon(ICON_VPXSUBSYSTEM_NAME, 14, 14);

		public static final ImageIcon ICON_CLOSE = VPXUtilities.getImageIcon(ICON_CLOSE_NAME, 14, 14);

		public static final ImageIcon ICON_DETACH = VPXUtilities.getImageIcon(ICON_DETACH_NAME, 14, 14);

		public static final ImageIcon IMAGE_BG = VPXUtilities.getImageIcon(IMAGE_BG_NAME, 596, 596);

		public static final ImageIcon ICON_WATERFALL = VPXUtilities.getImageIcon(ICON_WATERFALL_NAME, 14, 14);

		public static final ImageIcon ICON_AMPLITUDE = VPXUtilities.getImageIcon(ICON_AMPLITUDE_NAME, 596, 596);

	}

}

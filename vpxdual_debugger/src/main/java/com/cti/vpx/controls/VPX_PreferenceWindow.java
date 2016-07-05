package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.StringUtils;

import com.cti.vpx.listener.VPXUDPListener;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXUtilities;

import net.miginfocom.swing.MigLayout;

public class VPX_PreferenceWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -999389272992924693L;

	private static final String PORTNOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Advertisement port is receive advertisement packets through Adv. port.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RX Tx port is receive RxTx packets through Comm port.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Messaging port is receive message packets through Msg port.<p><font color='red'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Please ensure board side also using same ports</u></b></font></p></left></body></html>";

	private static String ADVNOTE = "<html><body><b>Note:</b><br><left><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Advertisement port is receive advertisement packets through Adv. port.</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RX Tx port is receive RxTx packets through Comm port.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Messaging port is receive message packets through Msg port.<p><font color='red'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Please ensure board side also using same ports</u></b></font></p></left></body></html>";

	private static String COMMNOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Advertisement port is receive advertisement packets through Adv. port.<br><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RX Tx port is receive RxTx packets through Comm port.</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Messaging port is receive message packets through Msg port.<p><font color='red'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Please ensure board side also using same ports</u></b></font></p></left></body></html>";

	private static String MSGNOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Advertisement port is receive advertisement packets through Adv. port.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RX Tx port is receive RxTx packets through Comm port.<br><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Messaging port is receive message packets through Msg port.</b><p><font color='red'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Please ensure board side also using same ports</u></b></font></p></left></body></html>";

	private static final String NETWORKNOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Showing network packet capturing tab.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;It keeps the packets send or receive</left></html>";

	private VPX_PasswordWindow paswordWindow = new VPX_PasswordWindow();

	private final JPanel basePanel = new JPanel();

	private JTabbedPane preference_TabbedPane;

	private JTextField txtLogFileFormat;

	private JTextField txtLogFilePath;

	private Properties preferenceProperties;

	private Properties preferenceProperties_Backup;

	private final JFileChooser fileDialog = new JFileChooser();

	private JCheckBox chkShowSplash;

	private JCheckBox chkShowMemory;

	private JCheckBox chkEnableLog;

	private JCheckBox chkPromptSerialNo;

	private JCheckBox chkOverwrite;

	private JCheckBox chkAppndTimeLogFile;

	private JCheckBox chkMaxLogFile;

	private JSpinner spinMaxFileSize;

	private JLabel lblJVersion;

	private JLabel lblJName;

	private JLabel lblJInstallPath;

	private JLabel lbJInstallJRE;

	private JLabel lblJVMName;

	private JLabel lblJVMArch;

	private JLabel lblErrLogPath;

	private JTextField txtAdvPort;

	private JTextField txtTxRxCommPort;

	private JTextField txtMsgConPort;

	private JCheckBox chkNWPktAnalyzer;

	private JLabel lblNWNote;

	private JLabel lblNote;

	private JCheckBox chkShowAliasCheck;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			VPX_PreferenceWindow dialog = new VPX_PreferenceWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.showPreferenceWindow();
		} catch (Exception e) {
			e.printStackTrace();
			VPXLogger.updateError(e);
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_PreferenceWindow() {
		setTitle("Preferences");

		init();

		loadComponents();

		centerFrame();
	}

	private void init() {
		setBounds(100, 100, 576, 412);

		setType(Type.POPUP);

		setModalityType(ModalityType.APPLICATION_MODAL);

		setModal(true);

		setResizable(false);

		setIconImage(VPXConstants.Icons.ICON_SETTINGS.getImage());

		fileDialog.setAcceptAllFileFilterUsed(false);
	}

	public void showPreferenceWindow() {

		readProperties();

		loadProperties();

		setVisible(true);
	}

	private void readProperties() {

		preferenceProperties = VPXUtilities.readProperties();

		preferenceProperties_Backup = (Properties) preferenceProperties.clone();
	}

	private void loadPropertiesTab() {

		loadGeneralPropertiesPanel();

		loadLogPropertiesPanel();

		loadNetworkPropertiesPanel();

		loadJavaPropertiesPanel();
	}

	private void loadProperties() {

		loadGeneralProperties(false);

		loadLogProperties(false);

		loadNWProperties(false);

		loadJavaProperties();
	}

	private void loadProperties(int tabId, boolean isRestore) {
		switch (tabId) {
		case 0:
			loadGeneralProperties(isRestore);
			break;
		case 1:
			loadLogProperties(isRestore);
			break;
		case 2:
			loadNWProperties(isRestore);
			break;
		case 3:
			loadJavaProperties();
			break;
		}
	}

	private void loadGeneralProperties(boolean isRestore) {

		boolean splash = true;

		boolean memory = true;

		boolean askAlias = true;

		if (!isRestore) {

			splash = Boolean.valueOf(preferenceProperties.getProperty(VPXConstants.ResourceFields.GENERAL_SPLASH));

			memory = Boolean.valueOf(preferenceProperties.getProperty(VPXConstants.ResourceFields.GENERAL_MEMORY));

			askAlias = Boolean
					.valueOf(preferenceProperties.getProperty(VPXConstants.ResourceFields.REMIND_ALIAS_CONFIG));
		}

		chkShowSplash.setSelected(!splash);

		chkShowMemory.setSelected(!memory);

		chkShowAliasCheck.setSelected(!askAlias);

		chkShowSplash.setSelected(splash);

		chkShowMemory.setSelected(memory);

		chkShowAliasCheck.setSelected(askAlias);

	}

	private void loadNWProperties(boolean isRestore) {

		Properties prop = new Properties();

		if (isRestore) {

			prop.setProperty(VPXConstants.ResourceFields.NETWORK_PORT_ADV,
					String.format("%d", VPXUDPListener.DEFAULT_ADV_PORTNO));

			prop.setProperty(VPXConstants.ResourceFields.NETWORK_PORT_COMM,
					String.format("%d", VPXUDPListener.DEFAULT_COMM_PORTNO));

			prop.setProperty(VPXConstants.ResourceFields.NETWORK_PORT_MSG,
					String.format("%d", VPXUDPListener.DEFAULT_CONSOLE_MSG_PORTNO));

			prop.setProperty(VPXConstants.ResourceFields.NETWORK_PKT_SNIFFER, String.valueOf(true));

		} else {
			prop = (Properties) preferenceProperties.clone();
		}

		chkNWPktAnalyzer
				.setSelected(Boolean.valueOf(prop.getProperty(VPXConstants.ResourceFields.NETWORK_PKT_SNIFFER)));

		txtAdvPort.setText(prop.getProperty(VPXConstants.ResourceFields.NETWORK_PORT_ADV));

		txtTxRxCommPort.setText(prop.getProperty(VPXConstants.ResourceFields.NETWORK_PORT_COMM));

		txtMsgConPort.setText(prop.getProperty(VPXConstants.ResourceFields.NETWORK_PORT_MSG));

	}

	private void loadLogProperties(boolean isRestore) {

		Properties prop = new Properties();

		if (isRestore) {

			prop.setProperty(VPXConstants.ResourceFields.LOG_ENABLE, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.LOG_PROMPT, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.LOG_MAXFILE, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.LOG_MAXFILESIZE, "2");

			prop.setProperty(VPXConstants.ResourceFields.LOG_FILEPATH, "log");

			prop.setProperty(VPXConstants.ResourceFields.LOG_FILEFORMAT, "$(FileName)_$(CurrentTime)");

			prop.setProperty(VPXConstants.ResourceFields.LOG_APPENDCURTIME, String.valueOf(true));

			prop.setProperty(VPXConstants.ResourceFields.LOG_OVERWRITE, String.valueOf(false));
		} else {
			prop = (Properties) preferenceProperties.clone();
		}

		boolean enablelog = Boolean.valueOf(prop.getProperty(VPXConstants.ResourceFields.LOG_ENABLE));

		boolean maxLog = Boolean.valueOf(prop.getProperty(VPXConstants.ResourceFields.LOG_MAXFILE));

		boolean appTime = Boolean.valueOf(prop.getProperty(VPXConstants.ResourceFields.LOG_APPENDCURTIME));

		chkEnableLog.setSelected(!enablelog);

		chkPromptSerialNo.setSelected(Boolean.valueOf(prop.getProperty(VPXConstants.ResourceFields.LOG_PROMPT)));

		chkMaxLogFile.setSelected(!maxLog);

		spinMaxFileSize.setValue(Integer.valueOf(prop.getProperty(VPXConstants.ResourceFields.LOG_MAXFILESIZE)));

		txtLogFilePath.setText(prop.getProperty(VPXConstants.ResourceFields.LOG_FILEPATH));

		txtLogFileFormat.setText(prop.getProperty(VPXConstants.ResourceFields.LOG_FILEFORMAT));

		chkAppndTimeLogFile.setSelected(!appTime);

		chkOverwrite.setSelected(Boolean.valueOf(prop.getProperty(VPXConstants.ResourceFields.LOG_OVERWRITE)));

		chkEnableLog.setSelected(enablelog);

		chkMaxLogFile.setSelected(maxLog);

		chkAppndTimeLogFile.setSelected(appTime);

		if (txtLogFilePath.getText().length() < 3) {
			lblErrLogPath.setText("No log path specified");
		} else {
			lblErrLogPath.setText("");
		}

	}

	private void loadJavaProperties() {
		// Java Settings Properties
				
		lblJVersion.setText(System.getProperty("java.version"));

		lblJName.setText(System.getProperty("java.runtime.name"));

		String path = System.getProperty("java.home");

		lblJInstallPath.setText(path.substring(0, path.indexOf("\\jre")));

		lblJVMName.setText(System.getProperty("java.vm.name"));

		lblJVMArch.setText(System.getProperty("sun.cpu.isalist"));

		lbJInstallJRE.setText(System.getProperty("java.home"));

	}

	private void updateProperties(int propId) {

		switch (propId) {

		case 0:// General Tab Settings

			preferenceProperties.setProperty(VPXConstants.ResourceFields.GENERAL_SPLASH,
					String.valueOf(chkShowSplash.isSelected()));

			preferenceProperties.setProperty(VPXConstants.ResourceFields.GENERAL_MEMORY,
					String.valueOf(chkShowMemory.isSelected()));

			preferenceProperties.setProperty(VPXConstants.ResourceFields.REMIND_ALIAS_CONFIG,
					String.valueOf(chkShowAliasCheck.isSelected()));

			break;
		case 1:// Log Tab Settings

			preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_ENABLE,
					String.valueOf(chkEnableLog.isSelected()));

			preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_PROMPT,
					String.valueOf(chkPromptSerialNo.isSelected()));

			preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_MAXFILE,
					String.valueOf(chkMaxLogFile.isSelected()));

			preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_MAXFILESIZE,
					spinMaxFileSize.getValue().toString());

			preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_FILEPATH, txtLogFilePath.getText());

			preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_FILEFORMAT, txtLogFileFormat.getText());

			preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_APPENDCURTIME,
					String.valueOf(chkAppndTimeLogFile.isSelected()));

			preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_OVERWRITE,
					String.valueOf(chkOverwrite.isSelected()));

			break;

		case 2:// Network Tab Settings

			preferenceProperties.setProperty(VPXConstants.ResourceFields.NETWORK_PORT_ADV, txtAdvPort.getText());

			preferenceProperties.setProperty(VPXConstants.ResourceFields.NETWORK_PORT_COMM, txtTxRxCommPort.getText());

			preferenceProperties.setProperty(VPXConstants.ResourceFields.NETWORK_PORT_MSG, txtMsgConPort.getText());

			preferenceProperties.setProperty(VPXConstants.ResourceFields.NETWORK_PKT_SNIFFER,
					String.valueOf(chkNWPktAnalyzer.isSelected()));

			break;
		}

	}

	private void updateProperties() {

		// General Tab Settings

		preferenceProperties.setProperty(VPXConstants.ResourceFields.GENERAL_SPLASH,
				String.valueOf(chkShowSplash.isSelected()));

		preferenceProperties.setProperty(VPXConstants.ResourceFields.GENERAL_MEMORY,
				String.valueOf(chkShowMemory.isSelected()));

		preferenceProperties.setProperty(VPXConstants.ResourceFields.REMIND_ALIAS_CONFIG,
				String.valueOf(chkShowAliasCheck.isSelected()));

		// Log Tab Settings

		preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_ENABLE,
				String.valueOf(chkEnableLog.isSelected()));

		preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_PROMPT,
				String.valueOf(chkPromptSerialNo.isSelected()));

		preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_MAXFILE,
				String.valueOf(chkMaxLogFile.isSelected()));

		preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_MAXFILESIZE,
				spinMaxFileSize.getValue().toString());

		preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_FILEPATH, txtLogFilePath.getText());

		preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_FILEFORMAT, txtLogFileFormat.getText());

		preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_APPENDCURTIME,
				String.valueOf(chkAppndTimeLogFile.isSelected()));

		preferenceProperties.setProperty(VPXConstants.ResourceFields.LOG_OVERWRITE,
				String.valueOf(chkOverwrite.isSelected()));

		// Network Tab Settings

		preferenceProperties.setProperty(VPXConstants.ResourceFields.NETWORK_PORT_ADV, txtAdvPort.getText());

		preferenceProperties.setProperty(VPXConstants.ResourceFields.NETWORK_PORT_COMM, txtTxRxCommPort.getText());

		preferenceProperties.setProperty(VPXConstants.ResourceFields.NETWORK_PORT_MSG, txtMsgConPort.getText());

		preferenceProperties.setProperty(VPXConstants.ResourceFields.NETWORK_PKT_SNIFFER,
				String.valueOf(chkNWPktAnalyzer.isSelected()));
	}

	private void loadGeneralPropertiesPanel() {

		JPanel tabPanel_General = new JPanel();

		tabPanel_General.setLayout(new MigLayout("", "[190px][16px][89px][16px][343px]", "[102px][102px][76px][23px]"));

		JPanel panel_Splash = new JPanel();

		panel_Splash.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		tabPanel_General.add(panel_Splash, "cell 0 0 5 1,grow");

		panel_Splash.setLayout(new MigLayout("", "[50px][458px]", "[23px][54px]"));

		chkShowSplash = new JCheckBox("Show Splash Screen");

		panel_Splash.add(chkShowSplash, "cell 0 0 2 1,alignx left,aligny top");

		JLabel lblNote = new JLabel("Note:");

		lblNote.setFont(new Font("Tahoma", Font.BOLD, 11));

		panel_Splash.add(lblNote, "cell 0 1,growx,aligny top");

		JLabel lblNewLabel = new JLabel("Display Spalsh screen before starting the application");

		panel_Splash.add(lblNewLabel, "cell 1 1,grow");

		JPanel panel_Memory = new JPanel();

		panel_Memory.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		tabPanel_General.add(panel_Memory, "cell 0 1 5 1,grow");

		panel_Memory.setLayout(new MigLayout("", "[50px][458px]", "[23px][54px]"));

		chkShowMemory = new JCheckBox("Show Memory Bar");

		panel_Memory.add(chkShowMemory, "cell 0 0 2 1,alignx left,aligny top");

		JLabel label = new JLabel("Note:");

		label.setFont(new Font("Tahoma", Font.BOLD, 11));

		panel_Memory.add(label, "cell 0 1,growx,aligny top");

		JLabel lblDisplayMemoryBar = new JLabel("Display memory bar on status bar(Bottom Right)");

		panel_Memory.add(lblDisplayMemoryBar, "cell 1 1,grow");

		JButton btnGenDefault = new JButton(new RestoreAction("Restore Default"));

		tabPanel_General.add(btnGenDefault, "cell 4 3,alignx left,aligny top");

		JButton btnGenApply = new JButton(new ModuleApplyAction("Apply"));

		tabPanel_General.add(btnGenApply, "cell 0 3,alignx right,aligny top");

		JButton btnGenReset = new JButton(new ResetAction("Reset"));

		tabPanel_General.add(btnGenReset, "cell 2 3,growx,aligny top");

		JPanel panel_Alias = new JPanel();

		panel_Alias.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		tabPanel_General.add(panel_Alias, "cell 0 2 5 1,grow");

		panel_Alias.setLayout(new MigLayout("", "[50px][5px][564px]", "[23px][23px]"));

		JLabel lblNotes = new JLabel("Ask user to cofig Alias on startup if configurations is not found");

		panel_Alias.add(lblNotes, "cell 2 1,grow");

		chkShowAliasCheck = new JCheckBox("Always ask Alias Configuration");

		panel_Alias.add(chkShowAliasCheck, "cell 0 0 3 1,growx,aligny top");

		JLabel lblNoteAlias = new JLabel("Note:");

		lblNoteAlias.setFont(new Font("Tahoma", Font.BOLD, 11));

		panel_Alias.add(lblNoteAlias, "cell 0 1,grow");

		preference_TabbedPane.addTab("General", tabPanel_General);

	}

	private void loadLogPropertiesPanel() {

		JPanel tabPanel_Log = new JPanel();

		tabPanel_Log.setLayout(new MigLayout("", "[97px][10px][91px][8px][91px][10px][67px][11px][154px]",
				"[23px][90px][133px][23px]"));

		chkEnableLog = new JCheckBox("Enable Log", true);

		tabPanel_Log.add(chkEnableLog, "cell 0 0,growx,aligny top");

		JPanel panelSerialNo = new JPanel();

		panelSerialNo
				.setBorder(new TitledBorder(null, "Log File Name", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		tabPanel_Log.add(panelSerialNo, "cell 0 1 7 1,grow");

		panelSerialNo.setLayout(new MigLayout("", "[50px][10px][294px]", "[23px][32px]"));

		chkPromptSerialNo = new JCheckBox("Prompt log filename on startup", true);

		panelSerialNo.add(chkPromptSerialNo, "cell 0 0 3 1,growx,aligny top");

		JLabel label_2 = new JLabel("Note:");

		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));

		panelSerialNo.add(label_2, "cell 0 1,growx,aligny top");

		JLabel lblAlwaysPromptBoard = new JLabel(
				"<html>Always prompt log filename from user.<br/>on application startup</html>");

		lblAlwaysPromptBoard.setVerticalAlignment(SwingConstants.TOP);

		panelSerialNo.add(lblAlwaysPromptBoard, "cell 2 1,grow");

		JPanel panelLogFile = new JPanel();

		panelLogFile.setBorder(new TitledBorder(null, "Log File", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		tabPanel_Log.add(panelLogFile, "cell 0 2 9 1,grow");

		panelLogFile.setLayout(
				new MigLayout("", "[48px][10px][29px][19px][252px][8px][147px]", "[23px][14px][23px][32px]"));

		JLabel lblNewLabel_1 = new JLabel("File name pattern");

		panelLogFile.add(lblNewLabel_1, "cell 0 2 3 1,grow");

		txtLogFileFormat = new JTextField();

		txtLogFileFormat.setEditable(false);

		panelLogFile.add(txtLogFileFormat, "cell 4 2,grow");

		txtLogFileFormat.setColumns(10);

		lblErrLogPath = new JLabel("");

		lblErrLogPath.setForeground(Color.RED);

		panelLogFile.add(lblErrLogPath, "cell 4 1 3 1,grow");

		JLabel lblLogFilePath = new JLabel("File Name");

		panelLogFile.add(lblLogFilePath, "cell 0 0 3 1,grow");

		txtLogFilePath = new JTextField();

		txtLogFilePath.setColumns(10);

		panelLogFile.add(txtLogFilePath, "cell 4 0 3 1,growx,aligny center");

		chkOverwrite = new JCheckBox("Overwrite old log file", true);

		chkOverwrite.setEnabled(false);

		panelLogFile.add(chkOverwrite, "cell 6 3,growx,aligny top");

		JLabel label_5 = new JLabel("Note:");

		label_5.setFont(new Font("Tahoma", Font.BOLD, 11));

		panelLogFile.add(label_5, "cell 0 3,growx,aligny bottom");

		chkAppndTimeLogFile = new JCheckBox("Append Current Time", true);

		panelLogFile.add(chkAppndTimeLogFile, "cell 6 2,growx,aligny top");

		JLabel lblNewLabel_5 = new JLabel("Append current time with file name while creating log file");

		lblNewLabel_5.setVerticalAlignment(SwingConstants.TOP);

		panelLogFile.add(lblNewLabel_5, "cell 2 3 3 1,growx,aligny bottom");

		JPanel panelMaxLogFileSize = new JPanel();

		panelMaxLogFileSize
				.setBorder(new TitledBorder(null, "Log File Size", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		tabPanel_Log.add(panelMaxLogFileSize, "cell 8 1,grow");

		panelMaxLogFileSize.setLayout(new MigLayout("", "[74px][10px][58px]", "[23px][23px]"));

		chkMaxLogFile = new JCheckBox("Max. Log File Size", true);

		panelMaxLogFileSize.add(chkMaxLogFile, "cell 0 0 3 1,growx,aligny top");

		spinMaxFileSize = new JSpinner(new SpinnerNumberModel(2, 2, 10, 1));

		panelMaxLogFileSize.add(spinMaxFileSize, "cell 0 1,grow");

		JLabel lblNewLabel_2 = new JLabel("MB(s)");

		lblNewLabel_2.setVerticalAlignment(SwingConstants.BOTTOM);

		panelMaxLogFileSize.add(lblNewLabel_2, "cell 2 1,alignx left,growy");

		JButton btnLogApply = new JButton(new ModuleApplyAction("Apply"));

		tabPanel_Log.add(btnLogApply, "cell 2 3,growx,aligny top");

		JButton btnLogDefault = new JButton(new RestoreAction("Restore Default"));

		tabPanel_Log.add(btnLogDefault, "cell 6 3 3 1,alignx left,aligny top");

		JButton btnLogReset = new JButton(new ResetAction("Reset"));

		tabPanel_Log.add(btnLogReset, "cell 4 3,growx,aligny top");

		chkEnableLog.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean val = (e.getStateChange() == ItemEvent.SELECTED);

				panelSerialNo.setEnabled(val);

				chkPromptSerialNo.setEnabled(val);

				label_2.setEnabled(val);

				lblAlwaysPromptBoard.setEnabled(val);

				panelLogFile.setEnabled(val);

				lblNewLabel_1.setEnabled(val);

				txtLogFileFormat.setEnabled(val);

				lblErrLogPath.setEnabled(val);

				lblLogFilePath.setEnabled(val);

				txtLogFilePath.setEnabled(val);

				chkOverwrite.setEnabled(val);

				label_5.setEnabled(val);

				chkAppndTimeLogFile.setEnabled(val);

				panelMaxLogFileSize.setEnabled(val);

				chkMaxLogFile.setEnabled(val);

				if (chkMaxLogFile.isSelected() && val) {

					spinMaxFileSize.setEnabled(true);

					lblNewLabel_2.setEnabled(true);
				} else {
					spinMaxFileSize.setEnabled(false);

					lblNewLabel_2.setEnabled(false);

				}

				lblNewLabel_5.setEnabled(val);
			}
		});

		chkMaxLogFile.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean val = (e.getStateChange() == ItemEvent.SELECTED);

				if (chkEnableLog.isSelected()) {
					spinMaxFileSize.setEnabled(val);

					lblNewLabel_2.setEnabled(val);
				} else {
					spinMaxFileSize.setEnabled(false);

					lblNewLabel_2.setEnabled(false);
				}
			}
		});

		chkAppndTimeLogFile.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				chkOverwrite.setEnabled(!chkAppndTimeLogFile.isSelected());

				if (e.getStateChange() == ItemEvent.SELECTED) {

					txtLogFileFormat.setText("$(FileName)_$(CurrentTime)");
				} else {
					txtLogFileFormat.setText("$(FileName)");
				}
			}
		});

		preference_TabbedPane.addTab("Log", tabPanel_Log);
	}

	private void loadJavaPropertiesPanel() {

		JPanel tabPanel_Java = new JPanel();

		tabPanel_Java
				.setBorder(new TitledBorder(null, "Java Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		tabPanel_Java.setLayout(new MigLayout("", "[96px][][324px]",
				"[14px,grow][14px,grow][14px,grow][14px,grow][14px,grow][14px,grow]"));

		JLabel lblNewLabel_6 = new JLabel("Java Version : ");

		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);

		tabPanel_Java.add(lblNewLabel_6, "cell 0 0,grow");

		JLabel lblNewLabel_7 = new JLabel("Name : ");

		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);

		tabPanel_Java.add(lblNewLabel_7, "cell 0 1,grow");

		JLabel lblInstalledPath = new JLabel("Installed path : ");

		lblInstalledPath.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblInstalledPath.setHorizontalAlignment(SwingConstants.RIGHT);

		tabPanel_Java.add(lblInstalledPath, "cell 0 2,grow");

		JLabel lblInstalledJre = new JLabel("Installed jre : ");

		lblInstalledJre.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblInstalledJre.setHorizontalAlignment(SwingConstants.RIGHT);

		tabPanel_Java.add(lblInstalledJre, "cell 0 3,grow");

		JLabel lblVmName = new JLabel("Vm Name : ");

		lblVmName.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblVmName.setHorizontalAlignment(SwingConstants.RIGHT);

		tabPanel_Java.add(lblVmName, "cell 0 4,grow");

		JLabel lblVmArchitecture = new JLabel("VM Architecture : ");

		lblVmArchitecture.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblVmArchitecture.setHorizontalAlignment(SwingConstants.RIGHT);

		tabPanel_Java.add(lblVmArchitecture, "cell 0 5,grow");

		lblJVersion = new JLabel("");

		lblJVersion.setFont(new Font("Tahoma", Font.BOLD, 11));

		tabPanel_Java.add(lblJVersion, "cell 2 0,grow");

		lblJName = new JLabel("");

		lblJName.setFont(new Font("Tahoma", Font.BOLD, 11));

		tabPanel_Java.add(lblJName, "cell 2 1,grow");

		lblJInstallPath = new JLabel("");

		lblJInstallPath.setFont(new Font("Tahoma", Font.BOLD, 11));

		tabPanel_Java.add(lblJInstallPath, "cell 2 2,grow");

		lbJInstallJRE = new JLabel("");

		lbJInstallJRE.setFont(new Font("Tahoma", Font.BOLD, 11));

		tabPanel_Java.add(lbJInstallJRE, "cell 2 3,grow");

		lblJVMName = new JLabel("");

		lblJVMName.setFont(new Font("Tahoma", Font.BOLD, 11));

		tabPanel_Java.add(lblJVMName, "cell 2 4,grow");

		lblJVMArch = new JLabel("");

		lblJVMArch.setFont(new Font("Tahoma", Font.BOLD, 11));

		tabPanel_Java.add(lblJVMArch, "cell 2 5,grow");

		preference_TabbedPane.addTab("Java", tabPanel_Java);

	}

	private void loadNetworkPropertiesPanel() {

		JPanel tabPanel_Network = new JPanel();

		preference_TabbedPane.addTab("Network", tabPanel_Network);

		tabPanel_Network.setLayout(new BorderLayout(0, 0));

		JPanel panel_PortSettings = new JPanel();

		panel_PortSettings
				.setBorder(new TitledBorder(null, "Port Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		tabPanel_Network.add(panel_PortSettings, BorderLayout.CENTER);

		panel_PortSettings
				.setLayout(new MigLayout("", "[194px][28px][176px][28px][110px]", "[23px][23px][23px][96px]"));

		JLabel lblAdvPort = new JLabel("Advertisement Port");

		panel_PortSettings.add(lblAdvPort, "cell 0 0,grow");

		JLabel lblTxRxCommPort = new JLabel("Tx Rx Communication Port");

		panel_PortSettings.add(lblTxRxCommPort, "cell 0 1,grow");

		JLabel lblMsgConPort = new JLabel("Message / Console Port");

		panel_PortSettings.add(lblMsgConPort, "cell 0 2,grow");

		txtAdvPort = new JTextField();

		txtAdvPort.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {

				lblNote.setText(ADVNOTE);

			}

			@Override
			public void focusLost(FocusEvent e) {

				lblNote.setText(PORTNOTE);
			}
		});

		panel_PortSettings.add(txtAdvPort, "cell 2 0,growx,aligny center");

		txtAdvPort.setColumns(10);

		txtTxRxCommPort = new JTextField();

		txtTxRxCommPort.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {

				lblNote.setText(COMMNOTE);

			}

			@Override
			public void focusLost(FocusEvent e) {

				lblNote.setText(PORTNOTE);
			}
		});
		txtTxRxCommPort.setColumns(10);

		panel_PortSettings.add(txtTxRxCommPort, "cell 2 1,growx,aligny center");

		txtMsgConPort = new JTextField();

		txtMsgConPort.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {

				lblNote.setText(MSGNOTE);
			}

			@Override
			public void focusLost(FocusEvent e) {

				lblNote.setText(PORTNOTE);
			}
		});

		txtMsgConPort.setColumns(10);

		panel_PortSettings.add(txtMsgConPort, "cell 2 2,growx,aligny center");

		lblNote = new JLabel(PORTNOTE);

		panel_PortSettings.add(lblNote, "cell 0 3 5 1,grow");

		JButton btnApply = new JButton(new ModuleApplyAction("Apply"));

		panel_PortSettings.add(btnApply, "cell 4 0,growx,aligny top");

		JButton btnReset = new JButton(new ResetAction("Reset"));

		panel_PortSettings.add(btnReset, "cell 4 1,growx,aligny top");

		JButton btnRestore = new JButton(new RestoreAction("Restore  Default"));

		panel_PortSettings.add(btnRestore, "cell 4 2,alignx center,aligny top");

		JPanel panel_NetworkPackt = new JPanel();

		panel_NetworkPackt.setPreferredSize(new Dimension(10, 100));

		panel_NetworkPackt.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Network Packet Analyzer", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		tabPanel_Network.add(panel_NetworkPackt, BorderLayout.SOUTH);

		panel_NetworkPackt.setLayout(new MigLayout("", "[536px]", "[23px][55px]"));

		chkNWPktAnalyzer = new JCheckBox("Show Network Packet Analyzer");

		panel_NetworkPackt.add(chkNWPktAnalyzer, "cell 0 0,growx,aligny top");

		lblNWNote = new JLabel(NETWORKNOTE);

		panel_NetworkPackt.add(lblNWNote, "cell 0 1,grow");
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	private void loadComponents() {

		getContentPane().setLayout(new BorderLayout());

		basePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(basePanel, BorderLayout.CENTER);

		basePanel.setLayout(new BorderLayout(0, 0));

		preference_TabbedPane = new JTabbedPane(JTabbedPane.TOP);

		basePanel.add(preference_TabbedPane);

		loadPropertiesTab();

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton(new ApplyAction("Apply"));

		okButton.setActionCommand("OK");

		buttonPane.add(okButton);

		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Close");

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_PreferenceWindow.this.dispose();

			}
		});

		cancelButton.setActionCommand("Cancel");

		buttonPane.add(cancelButton);
	}

	private String isValidPorts(String advPort, String commPort, String msgPort) {

		StringBuilder errors = new StringBuilder("");

		if (StringUtils.isEmpty(advPort)) {
			errors.append("Advertisement port should not be empty.\n");
		}
		if (StringUtils.isEmpty(commPort)) {
			errors.append("Communication port should not be empty.\n");
		}
		if (StringUtils.isEmpty(msgPort)) {
			errors.append("Message port should not be empty.\n");
		}

		if (StringUtils.isEmpty(errors.toString())) {

			boolean b = false;

			if (!StringUtils.isNumeric(advPort)) {

				errors.append("Please enter the valid port number for Advertisement.\n");

				b = true;
			}
			if (!StringUtils.isNumeric(commPort)) {

				errors.append("Please enter the valid port number for Communication.\n");

				b = true;

			}
			if (!StringUtils.isNumeric(msgPort)) {

				errors.append("Please enter the valid port number for Message.\n");

				b = true;
			}

			if (!b) {

				if (advPort.equals(msgPort) || advPort.equals(commPort) || msgPort.equals(commPort)) {

					errors.append("Ports are not same.\nPlease enter the different ports for each\n");
				}

			}
		}
		return errors.toString();
	}

	private boolean isValueChanged(int tab) {

		return (tab == 2
				&& (!txtAdvPort.getText().trim()
						.equals(preferenceProperties_Backup.getProperty(VPXConstants.ResourceFields.NETWORK_PORT_ADV))
						|| !txtTxRxCommPort.getText().trim()
								.equals(preferenceProperties_Backup
										.getProperty(VPXConstants.ResourceFields.NETWORK_PORT_COMM))
						|| !txtMsgConPort.getText().trim().equals(preferenceProperties_Backup
								.getProperty(VPXConstants.ResourceFields.NETWORK_PORT_MSG))));
	}

	public class RestoreAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public RestoreAction(String name) {

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			loadProperties(preference_TabbedPane.getSelectedIndex(), true);
		}
	}

	public class ResetAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public ResetAction(String name) {

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			loadProperties(preference_TabbedPane.getSelectedIndex(), false);
		}
	}

	public class ModuleApplyAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public ModuleApplyAction(String name) {

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (preference_TabbedPane.getSelectedIndex() == 2) {
				if (isValueChanged(preference_TabbedPane.getSelectedIndex())) {

					String error = isValidPorts(txtAdvPort.getText().trim(), txtTxRxCommPort.getText().trim(),
							txtMsgConPort.getText().trim());

					if (error.length() == 0) {

						paswordWindow.resetPassword();

						paswordWindow.setVisible(true);

						if (paswordWindow.isAccepted()) {

							if (VPXUtilities.getCurrentPassword()
									.equals(VPXUtilities.encodePassword(paswordWindow.getPasword()))) {

								paswordWindow.dispose();

								updateProperties(preference_TabbedPane.getSelectedIndex());

								if (VPXUtilities.updateProperties(preferenceProperties)) {

									readProperties();

									JOptionPane.showMessageDialog(VPX_PreferenceWindow.this,
											"Updated successfully.\nPlease restart the appliction to take effect");
								}

							} else {

								JOptionPane.showMessageDialog(VPX_PreferenceWindow.this, "Pasword invalid",
										"Authentication", JOptionPane.ERROR_MESSAGE);

								paswordWindow.dispose();

								VPXLogger.updateLog("Invalid Password");
							}
						}
					} else {
						JOptionPane.showMessageDialog(VPX_PreferenceWindow.this, error, "Validation",
								JOptionPane.ERROR_MESSAGE);

						txtAdvPort.requestFocus();

						VPXLogger.updateLog("Invalid Ports");
					}
				}
			} else {
				updateProperties(preference_TabbedPane.getSelectedIndex());

				if (VPXUtilities.updateProperties(preferenceProperties)) {

					readProperties();

					JOptionPane.showMessageDialog(VPX_PreferenceWindow.this,
							"Updated successfully.\nPlease restart the appliction to take effect");
				}
			}
		}
	}

	public class ApplyAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		public ApplyAction(String name) {

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (isValueChanged(2)) {

				paswordWindow.resetPassword();

				paswordWindow.setVisible(true);

				if (paswordWindow.isAccepted()) {

					if (VPXUtilities.getCurrentPassword()
							.equals(VPXUtilities.encodePassword(paswordWindow.getPasword()))) {

						paswordWindow.dispose();

						updateProperties();

						if (VPXUtilities.updateProperties(preferenceProperties)) {

							readProperties();

							JOptionPane.showMessageDialog(VPX_PreferenceWindow.this,
									"Updated successfully.\nPlease restart the appliction to take effect");
						}

						VPX_PreferenceWindow.this.dispose();
					}
				}
			} else {
				updateProperties();

				if (VPXUtilities.updateProperties(preferenceProperties)) {

					readProperties();

					JOptionPane.showMessageDialog(VPX_PreferenceWindow.this,
							"Updated successfully.\nPlease restart the appliction to take effect");
				}

				VPX_PreferenceWindow.this.dispose();
			}
		}
	}
}

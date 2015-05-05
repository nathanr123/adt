package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.util.VPXUtilities;

import java.awt.Color;

public class VPX_Preference extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -999389272992924693L;
	private final JPanel basePanel = new JPanel();
	private JTabbedPane preference_TabbedPane;
	private JTextField txtLogFileFormat;
	private JTextField txtLogFilePath;
	private JTextField txtInterpreterPath;
	private JTextField txtDummyOut;

	private Properties preferenceProperties;
	private final JFileChooser fileDialog = new JFileChooser();
	private JCheckBox chkShowSplash;
	private JCheckBox chkShowMemory;
	private JCheckBox chkEnableLog;
	private JCheckBox chkPromptSerialNo;
	private JCheckBox chkOverwrite;
	private JCheckBox chkAppndTimeLogFile;
	private JCheckBox chkMaxLogFile;
	private JCheckBox chkUseDummy;
	private JSpinner spinMaxFileSize;
	private JLabel lblJVersion;
	private JLabel lblJName;
	private JLabel lblJInstallPath;
	private JLabel lbJInstallJRE;
	private JLabel lblJVMName;
	private JLabel lblJVMArch;
	private JLabel lblPyVersion;
	private JLabel lblErrLogPath;
	private JLabel lblErrDummyFile;
	private JLabel lblErrIntrprtrPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_Preference dialog = new VPX_Preference();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.showPreferenceWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_Preference() {
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

		fileDialog.setAcceptAllFileFilterUsed(false);
	}

	public void showPreferenceWindow() {

		preferenceProperties = VPXUtilities.readProperties();

		loadProperties();

		setVisible(true);
	}

	private void loadPropertiesTab() {

		loadGeneralPropertiesPanel();

		loadLogPropertiesPanel();

		loadPythonPropertiesPanel();

		loadJavaPropertiesPanel();
	}

	private void loadProperties() {

		loadGeneralProperties(false);

		loadLogProperties(false);

		loadPythonProperties(false);

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
			loadPythonProperties(isRestore);
			break;
		case 3:
			loadJavaProperties();
			break;
		}
	}

	private void loadGeneralProperties(boolean isRestore) {
		boolean splash = true;

		boolean memory = true;

		if (!isRestore) {

			splash = Boolean.valueOf(preferenceProperties.getProperty(VPXUtilities.GENERAL_SPLASH));

			memory = Boolean.valueOf(preferenceProperties.getProperty(VPXUtilities.GENERAL_MEMORY));
		}

		chkShowSplash.setSelected(!splash);

		chkShowMemory.setSelected(!memory);

		chkShowSplash.setSelected(splash);

		chkShowMemory.setSelected(memory);

	}

	private void loadLogProperties(boolean isRestore) {
		Properties prop = new Properties();

		if (isRestore) {

			prop.setProperty(VPXUtilities.LOG_ENABLE, String.valueOf(true));

			prop.setProperty(VPXUtilities.LOG_PROMPT, String.valueOf(true));

			prop.setProperty(VPXUtilities.LOG_MAXFILE, String.valueOf(true));

			prop.setProperty(VPXUtilities.LOG_MAXFILESIZE, "2");

			prop.setProperty(VPXUtilities.LOG_FILEPATH, System.getProperty("user.home"));

			prop.setProperty(VPXUtilities.LOG_FILEFORMAT, "$(SerialNumber)_$(CurrentTime)");

			prop.setProperty(VPXUtilities.LOG_APPENDCURTIME, String.valueOf(true));

			prop.setProperty(VPXUtilities.LOG_OVERWRITE, String.valueOf(false));
		} else {
			prop = (Properties) preferenceProperties.clone();
		}

		boolean enablelog = Boolean.valueOf(prop.getProperty(VPXUtilities.LOG_ENABLE));

		boolean maxLog = Boolean.valueOf(prop.getProperty(VPXUtilities.LOG_MAXFILE));

		boolean appTime = Boolean.valueOf(prop.getProperty(VPXUtilities.LOG_APPENDCURTIME));

		chkEnableLog.setSelected(!enablelog);

		chkPromptSerialNo.setSelected(Boolean.valueOf(prop.getProperty(VPXUtilities.LOG_PROMPT)));

		chkMaxLogFile.setSelected(!maxLog);

		spinMaxFileSize.setValue(Integer.valueOf(prop.getProperty(VPXUtilities.LOG_MAXFILESIZE)));

		txtLogFilePath.setText(prop.getProperty(VPXUtilities.LOG_FILEPATH));

		txtLogFileFormat.setText(prop.getProperty(VPXUtilities.LOG_FILEFORMAT));

		chkAppndTimeLogFile.setSelected(!appTime);

		chkOverwrite.setSelected(Boolean.valueOf(prop.getProperty(VPXUtilities.LOG_OVERWRITE)));

		chkEnableLog.setSelected(enablelog);

		chkMaxLogFile.setSelected(maxLog);

		chkAppndTimeLogFile.setSelected(appTime);

		if (txtLogFilePath.getText().length() < 3) {
			lblErrLogPath.setText("No log path specified");
		} else {
			lblErrLogPath.setText("");
		}

	}

	private void loadPythonProperties(boolean isRestore) {

		Properties prop = new Properties();

		if (isRestore) {

			prop.setProperty(VPXUtilities.PYTHON_INTERPRETERPATH, VPXUtilities.getPythonInterpreterPath());

			prop.setProperty(VPXUtilities.PYTHON_USEDUMMY, String.valueOf(false));

			prop.setProperty(VPXUtilities.PYTHON_DUMMYFILE, "");

			prop.setProperty(VPXUtilities.PYTHON_VERSION, VPXUtilities.findPyVersion());
		} else {
			prop = (Properties) preferenceProperties.clone();
		}

		txtInterpreterPath.setText(preferenceProperties.getProperty(VPXUtilities.PYTHON_INTERPRETERPATH));

		boolean dummy = Boolean.valueOf(preferenceProperties.getProperty(VPXUtilities.PYTHON_USEDUMMY));

		chkUseDummy.setSelected(!dummy);

		txtDummyOut.setText(preferenceProperties.getProperty(VPXUtilities.PYTHON_DUMMYFILE));

		lblPyVersion.setText(preferenceProperties.getProperty(VPXUtilities.PYTHON_VERSION));

		chkUseDummy.setSelected(dummy);

		if (txtInterpreterPath.getText().length() < 13) {
			lblErrIntrprtrPath.setText("Python interpreter not configured");
		} else {
			lblErrIntrprtrPath.setText("");
		}

		if (txtDummyOut.getText().length() < 5) {
			lblErrDummyFile.setText("No .out file selected");
		} else {
			lblErrDummyFile.setText("");
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

			preferenceProperties.setProperty(VPXUtilities.GENERAL_SPLASH, String.valueOf(chkShowSplash.isSelected()));

			preferenceProperties.setProperty(VPXUtilities.GENERAL_MEMORY, String.valueOf(chkShowMemory.isSelected()));

			break;
		case 1:// Log Tab Settings

			preferenceProperties.setProperty(VPXUtilities.LOG_ENABLE, String.valueOf(chkEnableLog.isSelected()));

			preferenceProperties.setProperty(VPXUtilities.LOG_PROMPT, String.valueOf(chkPromptSerialNo.isSelected()));

			preferenceProperties.setProperty(VPXUtilities.LOG_MAXFILE, String.valueOf(chkMaxLogFile.isSelected()));

			preferenceProperties.setProperty(VPXUtilities.LOG_MAXFILESIZE, spinMaxFileSize.getValue().toString());

			preferenceProperties.setProperty(VPXUtilities.LOG_FILEPATH, txtLogFilePath.getText());

			preferenceProperties.setProperty(VPXUtilities.LOG_FILEFORMAT, txtLogFileFormat.getText());

			preferenceProperties.setProperty(VPXUtilities.LOG_APPENDCURTIME,
					String.valueOf(chkAppndTimeLogFile.isSelected()));

			preferenceProperties.setProperty(VPXUtilities.LOG_OVERWRITE, String.valueOf(chkOverwrite.isSelected()));

			break;
		case 2:// Python Tab Settings

			preferenceProperties.setProperty(VPXUtilities.PYTHON_INTERPRETERPATH, txtInterpreterPath.getText());

			preferenceProperties.setProperty(VPXUtilities.PYTHON_USEDUMMY, String.valueOf(chkUseDummy.isSelected()));

			preferenceProperties.setProperty(VPXUtilities.PYTHON_DUMMYFILE, txtDummyOut.getText());

			break;

		}

	}

	private void updateProperties() {

		// General Tab Settings

		preferenceProperties.setProperty(VPXUtilities.GENERAL_SPLASH, String.valueOf(chkShowSplash.isSelected()));

		preferenceProperties.setProperty(VPXUtilities.GENERAL_MEMORY, String.valueOf(chkShowMemory.isSelected()));

		// Log Tab Settings

		preferenceProperties.setProperty(VPXUtilities.LOG_ENABLE, String.valueOf(chkEnableLog.isSelected()));

		preferenceProperties.setProperty(VPXUtilities.LOG_PROMPT, String.valueOf(chkPromptSerialNo.isSelected()));

		preferenceProperties.setProperty(VPXUtilities.LOG_MAXFILE, String.valueOf(chkMaxLogFile.isSelected()));

		preferenceProperties.setProperty(VPXUtilities.LOG_MAXFILESIZE, spinMaxFileSize.getValue().toString());

		preferenceProperties.setProperty(VPXUtilities.LOG_FILEPATH, txtLogFilePath.getText());

		preferenceProperties.setProperty(VPXUtilities.LOG_FILEFORMAT, txtLogFileFormat.getText());

		preferenceProperties.setProperty(VPXUtilities.LOG_APPENDCURTIME,
				String.valueOf(chkAppndTimeLogFile.isSelected()));

		preferenceProperties.setProperty(VPXUtilities.LOG_OVERWRITE, String.valueOf(chkOverwrite.isSelected()));

		// Python Tab Settings

		preferenceProperties.setProperty(VPXUtilities.PYTHON_INTERPRETERPATH, txtInterpreterPath.getText());

		preferenceProperties.setProperty(VPXUtilities.PYTHON_USEDUMMY, String.valueOf(chkUseDummy.isSelected()));

		preferenceProperties.setProperty(VPXUtilities.PYTHON_DUMMYFILE, txtDummyOut.getText());

	}

	private void loadGeneralPropertiesPanel() {

		JPanel tabPanel_General = new JPanel();

		preference_TabbedPane.addTab("General", tabPanel_General);
		tabPanel_General.setLayout(null);

		JPanel panel_Splash = new JPanel();
		panel_Splash.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Splash.setBounds(10, 11, 535, 102);
		tabPanel_General.add(panel_Splash);
		panel_Splash.setLayout(null);

		chkShowSplash = new JCheckBox("Show Splash Screen");
		chkShowSplash.setBounds(6, 7, 265, 23);
		panel_Splash.add(chkShowSplash);

		JLabel lblNote = new JLabel("Note:");
		lblNote.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNote.setBounds(10, 37, 46, 14);
		panel_Splash.add(lblNote);

		JLabel lblNewLabel = new JLabel("Display Spalsh screen before starting the application");
		lblNewLabel.setBounds(56, 46, 458, 45);
		panel_Splash.add(lblNewLabel);

		JPanel panel_Memory = new JPanel();
		panel_Memory.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Memory.setLayout(null);
		panel_Memory.setBounds(10, 127, 535, 102);
		tabPanel_General.add(panel_Memory);

		chkShowMemory = new JCheckBox("Show Memory Bar");
		chkShowMemory.setBounds(6, 7, 265, 23);
		panel_Memory.add(chkShowMemory);

		JLabel label = new JLabel("Note:");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(10, 37, 46, 14);
		panel_Memory.add(label);

		JLabel lblDisplayMemoryBar = new JLabel("Display memory bar on status bar(Bottom Right)");
		lblDisplayMemoryBar.setBounds(56, 46, 458, 45);
		panel_Memory.add(lblDisplayMemoryBar);

		JButton btnGenDefault = new JButton(new RestoreAction("Restore Default"));
		btnGenDefault.setBounds(313, 284, 131, 23);
		tabPanel_General.add(btnGenDefault);

		JButton btnGenApply = new JButton(new ModuleApplyAction("Apply"));
		btnGenApply.setBounds(454, 284, 91, 23);
		tabPanel_General.add(btnGenApply);

		JButton btnGenReset = new JButton(new ResetAction("Reset"));
		btnGenReset.setBounds(212, 284, 91, 23);
		tabPanel_General.add(btnGenReset);

	}

	private void loadLogPropertiesPanel() {
		JPanel tabPanel_Log = new JPanel();

		preference_TabbedPane.addTab("Log", tabPanel_Log);
		tabPanel_Log.setLayout(null);

		chkEnableLog = new JCheckBox("Enable Log", true);
		chkEnableLog.setBounds(6, 7, 97, 23);
		tabPanel_Log.add(chkEnableLog);

		JPanel panelSerialNo = new JPanel();
		panelSerialNo.setBorder(new TitledBorder(null, "Serial Number", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		panelSerialNo.setLayout(null);
		panelSerialNo.setBounds(10, 36, 370, 90);
		tabPanel_Log.add(panelSerialNo);

		chkPromptSerialNo = new JCheckBox("Prompt serial number after scanning processors", true);
		chkPromptSerialNo.setBounds(6, 20, 354, 23);
		panelSerialNo.add(chkPromptSerialNo);

		JLabel label_2 = new JLabel("Note:");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(10, 50, 46, 14);
		panelSerialNo.add(label_2);

		JLabel lblAlwaysPromptBoard = new JLabel(
				"<html>Always prompt Board serial number from user.<br/>If new scan has performed</html>");
		lblAlwaysPromptBoard.setVerticalAlignment(SwingConstants.TOP);
		lblAlwaysPromptBoard.setBounds(66, 50, 294, 32);
		panelSerialNo.add(lblAlwaysPromptBoard);

		JPanel panelLogFile = new JPanel();
		panelLogFile.setBorder(new TitledBorder(null, "Log File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLogFile.setLayout(null);
		panelLogFile.setBounds(10, 137, 535, 133);
		tabPanel_Log.add(panelLogFile);

		JLabel lblNewLabel_1 = new JLabel("File name format");
		lblNewLabel_1.setBounds(12, 73, 87, 14);
		panelLogFile.add(lblNewLabel_1);

		txtLogFileFormat = new JTextField();
		txtLogFileFormat.setEditable(false);
		txtLogFileFormat.setBounds(118, 67, 241, 23);
		panelLogFile.add(txtLogFileFormat);
		txtLogFileFormat.setColumns(10);

		lblErrLogPath = new JLabel("");
		lblErrLogPath.setForeground(Color.RED);
		lblErrLogPath.setBounds(118, 46, 305, 14);
		panelLogFile.add(lblErrLogPath);

		JLabel lblLogFilePath = new JLabel("Select Path");
		lblLogFilePath.setBounds(10, 21, 87, 14);
		panelLogFile.add(lblLogFilePath);

		txtLogFilePath = new JTextField();
		txtLogFilePath.setColumns(10);
		txtLogFilePath.setBounds(116, 18, 307, 20);
		panelLogFile.add(txtLogFilePath);

		JButton btnLogFilePathBrowse = new JButton("Browse");
		btnLogFilePathBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int returnVal = fileDialog.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					java.io.File file = fileDialog.getSelectedFile();

					txtLogFilePath.setText(file.getPath());
				}
			}
		});
		btnLogFilePathBrowse.setBounds(433, 17, 87, 23);
		panelLogFile.add(btnLogFilePathBrowse);

		chkOverwrite = new JCheckBox("Overwrite old log file", true);
		chkOverwrite.setBounds(376, 90, 147, 23);
		panelLogFile.add(chkOverwrite);

		JLabel label_5 = new JLabel("Note:");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_5.setBounds(12, 108, 46, 14);
		panelLogFile.add(label_5);

		chkAppndTimeLogFile = new JCheckBox("Append Current Time", true);
		chkAppndTimeLogFile.setBounds(376, 67, 147, 23);
		panelLogFile.add(chkAppndTimeLogFile);

		JLabel lblNewLabel_5 = new JLabel("Append current time with file name while creating log file");
		lblNewLabel_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_5.setBounds(68, 108, 300, 14);
		panelLogFile.add(lblNewLabel_5);

		JPanel panelMaxLogFileSize = new JPanel();
		panelMaxLogFileSize.setBorder(new TitledBorder(null, "Log File Size", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panelMaxLogFileSize.setLayout(null);
		panelMaxLogFileSize.setBounds(391, 36, 154, 90);
		tabPanel_Log.add(panelMaxLogFileSize);

		chkMaxLogFile = new JCheckBox("Max. Log File Size", true);
		chkMaxLogFile.setBounds(6, 20, 142, 23);
		panelMaxLogFileSize.add(chkMaxLogFile);

		spinMaxFileSize = new JSpinner(new SpinnerNumberModel(2, 2, 10, 1));
		spinMaxFileSize.setBounds(20, 50, 74, 23);
		panelMaxLogFileSize.add(spinMaxFileSize);

		JLabel lblNewLabel_2 = new JLabel("MB(s)");
		lblNewLabel_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_2.setBounds(100, 50, 34, 23);
		panelMaxLogFileSize.add(lblNewLabel_2);

		JButton btnLogApply = new JButton(new ModuleApplyAction("Apply"));
		btnLogApply.setBounds(454, 281, 91, 23);
		tabPanel_Log.add(btnLogApply);

		JButton btnLogDefault = new JButton(new RestoreAction("Restore Default"));
		btnLogDefault.setBounds(313, 281, 131, 23);
		tabPanel_Log.add(btnLogDefault);

		JButton btnLogReset = new JButton(new ResetAction("Reset"));
		btnLogReset.setBounds(212, 281, 91, 23);
		tabPanel_Log.add(btnLogReset);

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

				btnLogFilePathBrowse.setEnabled(val);

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
				if (e.getStateChange() == ItemEvent.SELECTED) {
					txtLogFileFormat.setText("$(SerialNumber)_$(CurrentTime)");
				} else {
					txtLogFileFormat.setText("$(SerialNumber)");
				}
			}
		});
	}

	private void loadPythonPropertiesPanel() {
		JPanel tabPanel_Python = new JPanel();

	//	preference_TabbedPane.addTab("Python", tabPanel_Python);
		tabPanel_Python.setLayout(null);

		JPanel panelInterpreterPath = new JPanel();
		panelInterpreterPath.setBorder(new TitledBorder(null, "Interpreter", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panelInterpreterPath.setBounds(10, 31, 535, 95);
		tabPanel_Python.add(panelInterpreterPath);
		panelInterpreterPath.setLayout(null);

		JLabel lblPath = new JLabel("Path");
		lblPath.setBounds(10, 25, 46, 14);
		panelInterpreterPath.add(lblPath);

		txtInterpreterPath = new JTextField();
		txtInterpreterPath.setBounds(61, 22, 366, 20);
		panelInterpreterPath.add(txtInterpreterPath);
		txtInterpreterPath.setColumns(10);

		JButton btnSelInterpreterPath = new JButton("Browse");
		btnSelInterpreterPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int returnVal = fileDialog.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					java.io.File file = fileDialog.getSelectedFile();

					txtInterpreterPath.setText(file.getPath());
				}
			}
		});
		btnSelInterpreterPath.setBounds(437, 22, 91, 23);
		panelInterpreterPath.add(btnSelInterpreterPath);

		lblErrIntrprtrPath = new JLabel("");
		lblErrIntrprtrPath.setForeground(Color.RED);
		lblErrIntrprtrPath.setBounds(10, 65, 485, 14);
		panelInterpreterPath.add(lblErrIntrprtrPath);

		JPanel panelDummyOutFile = new JPanel();
		panelDummyOutFile.setBorder(new TitledBorder(null, "Dummy Out File", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panelDummyOutFile.setBounds(10, 137, 535, 133);
		tabPanel_Python.add(panelDummyOutFile);
		panelDummyOutFile.setLayout(null);

		chkUseDummy = new JCheckBox("Use Dummy Out file when no out file is selected");
		chkUseDummy.setBounds(6, 14, 398, 23);
		panelDummyOutFile.add(chkUseDummy);

		JLabel lblFile = new JLabel("Dummy Out File");
		lblFile.setBounds(10, 44, 99, 14);
		panelDummyOutFile.add(lblFile);

		txtDummyOut = new JTextField();
		txtDummyOut.setColumns(10);
		txtDummyOut.setBounds(10, 69, 414, 20);
		panelDummyOutFile.add(txtDummyOut);

		JButton btnSelDummyOutFile = new JButton("Browse");

		btnSelDummyOutFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				FileNameExtensionFilter filter = new FileNameExtensionFilter("Out file", "out");

				fileDialog.addChoosableFileFilter(filter);

				int returnVal = fileDialog.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fileDialog.getSelectedFile();

					txtDummyOut.setText(file.getPath());
				}
			}
		});
		btnSelDummyOutFile.setBounds(434, 68, 91, 23);
		panelDummyOutFile.add(btnSelDummyOutFile);

		lblErrDummyFile = new JLabel("");
		lblErrDummyFile.setForeground(Color.RED);
		lblErrDummyFile.setBounds(10, 108, 485, 14);
		panelDummyOutFile.add(lblErrDummyFile);

		JLabel lblNewLabel_3 = new JLabel("Version");
		lblNewLabel_3.setBounds(10, 6, 46, 14);
		tabPanel_Python.add(lblNewLabel_3);

		lblPyVersion = new JLabel("");
		lblPyVersion.setBounds(96, 6, 46, 14);
		tabPanel_Python.add(lblPyVersion);

		JButton btnPythonApply = new JButton(new ModuleApplyAction("Apply"));
		btnPythonApply.setBounds(454, 284, 91, 23);
		tabPanel_Python.add(btnPythonApply);

		JButton btnPythonDefault = new JButton(new RestoreAction("Restore Default"));
		btnPythonDefault.setBounds(313, 284, 131, 23);
		tabPanel_Python.add(btnPythonDefault);

		JButton btnPythonReset = new JButton(new ResetAction("Reset"));
		btnPythonReset.setBounds(212, 284, 91, 23);
		tabPanel_Python.add(btnPythonReset);

		chkUseDummy.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean val = (e.getStateChange() == ItemEvent.SELECTED);

				btnSelDummyOutFile.setEnabled(val);

				txtDummyOut.setEnabled(val);

				lblFile.setEnabled(val);

				lblErrDummyFile.setEnabled(val);
			}
		});
	}

	private void loadJavaPropertiesPanel() {

		JPanel tabPanel_Java = new JPanel();

		preference_TabbedPane.addTab("Java", tabPanel_Java);
		tabPanel_Java.setLayout(null);

		JLabel lblNewLabel_6 = new JLabel("Java Version");
		lblNewLabel_6.setBounds(10, 28, 96, 14);
		tabPanel_Java.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Name");
		lblNewLabel_7.setBounds(10, 70, 96, 14);
		tabPanel_Java.add(lblNewLabel_7);

		JLabel lblInstalledPath = new JLabel("Installed path");
		lblInstalledPath.setBounds(10, 112, 96, 14);
		tabPanel_Java.add(lblInstalledPath);

		JLabel lblInstalledJre = new JLabel("Installed jre");
		lblInstalledJre.setBounds(10, 154, 96, 14);
		tabPanel_Java.add(lblInstalledJre);

		JLabel lblVmName = new JLabel("Vm Name");
		lblVmName.setBounds(10, 196, 96, 14);
		tabPanel_Java.add(lblVmName);

		JLabel lblVmArchitecture = new JLabel("VM Architecture");
		lblVmArchitecture.setBounds(10, 238, 96, 14);
		tabPanel_Java.add(lblVmArchitecture);

		lblJVersion = new JLabel("");
		lblJVersion.setBounds(148, 28, 324, 14);
		tabPanel_Java.add(lblJVersion);

		lblJName = new JLabel("");
		lblJName.setBounds(148, 70, 324, 14);
		tabPanel_Java.add(lblJName);

		lblJInstallPath = new JLabel("");
		lblJInstallPath.setBounds(148, 112, 324, 14);
		tabPanel_Java.add(lblJInstallPath);

		lbJInstallJRE = new JLabel("");
		lbJInstallJRE.setBounds(148, 154, 324, 14);
		tabPanel_Java.add(lbJInstallJRE);

		lblJVMName = new JLabel("");
		lblJVMName.setBounds(148, 196, 324, 14);
		tabPanel_Java.add(lblJVMName);

		lblJVMArch = new JLabel("");
		lblJVMArch.setBounds(148, 238, 324, 14);
		tabPanel_Java.add(lblJVMArch);
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
				VPX_Preference.this.dispose();

			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
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

			updateProperties(preference_TabbedPane.getSelectedIndex());

			VPXUtilities.updateProperties(preferenceProperties);

			JOptionPane.showMessageDialog(VPX_Preference.this,
					"Updated successfully.\nPlease restart the appliction to take effect");
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

			updateProperties();

			VPXUtilities.updateProperties(preferenceProperties);

			JOptionPane.showMessageDialog(VPX_Preference.this,
					"Updated successfully.\nPlease restart the appliction to take effect");

			VPX_Preference.this.dispose();
		}
	}
}

package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.util.VPXUtilities;

public class VPX_MAD extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4991577328261732341L;

	private static final int CONFIGURATION = 0;

	private static final int COMPILATION = 1;

	private JTabbedPane madTab;

	private JPanel configPanel;

	private JPanel compilePanel;

	private JTextField txtConfigPathPython;

	private JTextField txtConfigPathMAP;

	private JTextField txtConfigPathPrelinker;

	private JTextField txtConfigPathStriper;

	private JTextField txtConfigPathOFD;

	private JTextField txtConfigPathMAL;

	private JTextField txtConfigPathNML;

	private JTextField txtConfigPathDummyOut;

	private JTextField txtCompilePathCore0;

	private JTextField txtCompilePathCore1;

	private JTextField txtCompilePathCore2;

	private JTextField txtCompilePathCore3;

	private JTextField txtCompilePathCore4;

	private JTextField txtCompilePathCore5;

	private JTextField txtCompilePathCore6;

	private JTextField txtCompilePathFinalOut;

	private JTextField txtCompilePathCore7;

	final JFileChooser fileDialog = new JFileChooser();

	final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Out Files", "out");

	Properties p = VPXUtilities.readProperties();

	private JCheckBox chkConfigDummyOut;

	/**
	 * Create the panel.
	 */
	public VPX_MAD() {

		init();

		loadComponents();

		loadPathsFromProperties();

	}

	private void init() {

		setLayout(new BorderLayout(0, 0));
	}

	private void loadComponents() {

		madTab = new JTabbedPane(JTabbedPane.TOP);

		add(madTab, BorderLayout.CENTER);

		createCompilationTab();

		createConfigurationTab();

		madTab.addTab("Configuration", null, new JScrollPane(configPanel), null);

		madTab.addTab("Compilation", null, new JScrollPane(compilePanel), null);

		madTab.setEnabledAt(1, false);

	}

	private void createConfigurationTab() {

		configPanel = new JPanel();

		configPanel.setPreferredSize(new Dimension(760, 540));

		configPanel.setLayout(new BorderLayout(0, 0));

		JPanel configPathPanel = new JPanel();

		configPathPanel.setBorder(new TitledBorder(null, "Paths", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		configPanel.add(configPathPanel, BorderLayout.CENTER);

		configPathPanel.setLayout(null);

		JLabel lblConfigPython = new JLabel("Python");

		lblConfigPython.setBounds(22, 26, 100, 26);

		configPathPanel.add(lblConfigPython);

		txtConfigPathPython = new JTextField();

		txtConfigPathPython.setBounds(132, 26, 509, 26);
		configPathPanel.add(txtConfigPathPython);

		txtConfigPathPython.setColumns(10);

		JButton btnConfigBrowsePython = new JButton(new BrowseAction("Browse", txtConfigPathPython));

		btnConfigBrowsePython.setBounds(647, 26, 89, 26);

		configPathPanel.add(btnConfigBrowsePython);

		JLabel lblConfigMAP = new JLabel("MAP Tool");

		lblConfigMAP.setBounds(22, 78, 100, 26);

		configPathPanel.add(lblConfigMAP);

		txtConfigPathMAP = new JTextField();

		txtConfigPathMAP.setColumns(10);

		txtConfigPathMAP.setBounds(132, 78, 509, 26);

		configPathPanel.add(txtConfigPathMAP);

		JButton btnConfigBrowseMAP = new JButton(new BrowseAction("Browse", txtConfigPathMAP));

		btnConfigBrowseMAP.setBounds(647, 78, 89, 26);

		configPathPanel.add(btnConfigBrowseMAP);

		JLabel lblConfigPrelinker = new JLabel("Prelinker Tool");

		lblConfigPrelinker.setBounds(22, 130, 100, 26);

		configPathPanel.add(lblConfigPrelinker);

		txtConfigPathPrelinker = new JTextField();

		txtConfigPathPrelinker.setColumns(10);

		txtConfigPathPrelinker.setBounds(132, 130, 509, 26);

		configPathPanel.add(txtConfigPathPrelinker);

		JButton btnConfigBrowsePrelinker = new JButton(new BrowseAction("Browse", txtConfigPathPrelinker));

		btnConfigBrowsePrelinker.setBounds(647, 130, 89, 26);

		configPathPanel.add(btnConfigBrowsePrelinker);

		JLabel lblConfigStriper = new JLabel("Striper Tool");

		lblConfigStriper.setBounds(22, 182, 100, 26);

		configPathPanel.add(lblConfigStriper);

		txtConfigPathStriper = new JTextField();

		txtConfigPathStriper.setColumns(10);

		txtConfigPathStriper.setBounds(132, 182, 509, 26);

		configPathPanel.add(txtConfigPathStriper);

		JButton btnConfigBrowseStriper = new JButton(new BrowseAction("Browse", txtConfigPathStriper));

		btnConfigBrowseStriper.setBounds(647, 182, 89, 26);

		configPathPanel.add(btnConfigBrowseStriper);

		JLabel lblConfigOFD = new JLabel("OFD Tool");

		lblConfigOFD.setBounds(22, 234, 100, 26);

		configPathPanel.add(lblConfigOFD);

		txtConfigPathOFD = new JTextField();

		txtConfigPathOFD.setColumns(10);

		txtConfigPathOFD.setBounds(132, 234, 509, 26);

		configPathPanel.add(txtConfigPathOFD);

		JButton btnConfigBrowseOFD = new JButton(new BrowseAction("Browse", txtConfigPathOFD));

		btnConfigBrowseOFD.setBounds(647, 234, 89, 26);

		configPathPanel.add(btnConfigBrowseOFD);

		JLabel lblConfigMAL = new JLabel("MAL Application");

		lblConfigMAL.setBounds(22, 286, 100, 26);

		configPathPanel.add(lblConfigMAL);

		txtConfigPathMAL = new JTextField();

		txtConfigPathMAL.setColumns(10);

		txtConfigPathMAL.setBounds(132, 286, 509, 26);

		configPathPanel.add(txtConfigPathMAL);

		JButton btnConfigBrowseMAL = new JButton(new BrowseAction("Browse", txtConfigPathMAL));

		btnConfigBrowseMAL.setBounds(647, 286, 89, 26);

		configPathPanel.add(btnConfigBrowseMAL);

		JLabel lblConfigNML = new JLabel("NML Loader");

		lblConfigNML.setBounds(22, 338, 100, 26);

		configPathPanel.add(lblConfigNML);

		txtConfigPathNML = new JTextField();

		txtConfigPathNML.setColumns(10);

		txtConfigPathNML.setBounds(132, 338, 509, 26);

		configPathPanel.add(txtConfigPathNML);

		JButton btnConfigBrowseNMLLoader = new JButton(new BrowseAction("Browse", txtConfigPathNML));

		btnConfigBrowseNMLLoader.setBounds(647, 338, 89, 26);

		configPathPanel.add(btnConfigBrowseNMLLoader);

		JPanel configDummyPanel = new JPanel();

		configDummyPanel.setPreferredSize(new Dimension(10, 140));

		configPanel.add(configDummyPanel, BorderLayout.SOUTH);

		configDummyPanel.setLayout(new BorderLayout(0, 0));

		JPanel configDummyOutFilePanel = new JPanel();

		configDummyOutFilePanel.setBorder(new TitledBorder(null, "Dummy File", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		configDummyPanel.add(configDummyOutFilePanel, BorderLayout.CENTER);

		configDummyOutFilePanel.setLayout(null);

		chkConfigDummyOut = new JCheckBox("Use dummy out file if no out file is selected");

		chkConfigDummyOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				txtConfigPathDummyOut.setEnabled(chkConfigDummyOut.isSelected());

			}
		});

		chkConfigDummyOut.setBounds(22, 19, 604, 23);

		configDummyOutFilePanel.add(chkConfigDummyOut);

		JLabel lblConfigDummyOutFile = new JLabel("Dummy Out File");

		lblConfigDummyOutFile.setBounds(22, 61, 100, 26);

		configDummyOutFilePanel.add(lblConfigDummyOutFile);

		txtConfigPathDummyOut = new JTextField();

		txtConfigPathDummyOut.setBounds(132, 61, 509, 26);

		txtConfigPathDummyOut.setColumns(10);

		configDummyOutFilePanel.add(txtConfigPathDummyOut);

		JButton btnConfigBrowseDummyOut = new JButton(new BrowseAction("Browse", txtConfigPathDummyOut));

		btnConfigBrowseDummyOut.setBounds(647, 61, 89, 26);

		configDummyOutFilePanel.add(btnConfigBrowseDummyOut);

		JPanel configControlPanel = new JPanel();

		configDummyPanel.add(configControlPanel, BorderLayout.SOUTH);

		JButton btnConfigApply = new JButton("Apply");

		btnConfigApply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String error = checkPathsValid(CONFIGURATION);

				if (error.length() == 0) {

					VPXUtilities.updateConfigFile(txtConfigPathPython.getText(), txtConfigPathMAP.getText(),
							txtConfigPathPrelinker.getText(), txtConfigPathOFD.getText(),
							txtConfigPathStriper.getText(), txtConfigPathMAL.getText(), txtConfigPathNML.getText(),
							chkConfigDummyOut.isSelected(), txtConfigPathDummyOut.getText());

					JOptionPane.showMessageDialog(null, "Paths are configured successfully");

					madTab.setEnabledAt(1, true);

					madTab.setSelectedIndex(1);
				} else {

					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		configControlPanel.add(btnConfigApply);

		JButton btnConfigClear = new JButton("Clear");

		btnConfigClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearConfigurationFields();

			}
		});

		configControlPanel.add(btnConfigClear);

	}

	private void createCompilationTab() {

		compilePanel = new JPanel();

		compilePanel.setLayout(new BorderLayout(0, 0));

		compilePanel.setPreferredSize(new Dimension(760, 500));

		JPanel compilePathPanel = new JPanel();

		compilePathPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Out Files",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		compilePanel.add(compilePathPanel, BorderLayout.CENTER);

		compilePathPanel.setLayout(null);

		JLabel lblCompileCore0 = new JLabel("Core 0");

		lblCompileCore0.setBounds(22, 19, 100, 26);

		compilePathPanel.add(lblCompileCore0);

		txtCompilePathCore0 = new JTextField();

		txtCompilePathCore0.setBounds(132, 19, 509, 26);

		compilePathPanel.add(txtCompilePathCore0);

		txtCompilePathCore0.setColumns(10);

		JButton btnCompileCore0 = new JButton(new BrowseAction("Browse", txtCompilePathCore0));

		btnCompileCore0.setBounds(647, 19, 89, 26);

		compilePathPanel.add(btnCompileCore0);

		JLabel lblCompileCore1 = new JLabel("Core 1");

		lblCompileCore1.setBounds(22, 64, 100, 26);

		compilePathPanel.add(lblCompileCore1);

		txtCompilePathCore1 = new JTextField();

		txtCompilePathCore1.setColumns(10);

		txtCompilePathCore1.setBounds(132, 64, 509, 26);

		compilePathPanel.add(txtCompilePathCore1);

		JButton btnCompileCore1 = new JButton(new BrowseAction("Browse", txtCompilePathCore1));

		btnCompileCore1.setBounds(647, 64, 89, 26);

		compilePathPanel.add(btnCompileCore1);

		JLabel lblCompileCore2 = new JLabel("Core 2");

		lblCompileCore2.setBounds(22, 109, 100, 26);

		compilePathPanel.add(lblCompileCore2);

		txtCompilePathCore2 = new JTextField();

		txtCompilePathCore2.setColumns(10);

		txtCompilePathCore2.setBounds(132, 109, 509, 26);

		compilePathPanel.add(txtCompilePathCore2);

		JButton btnCompileCore2 = new JButton(new BrowseAction("Browse", txtCompilePathCore2));

		btnCompileCore2.setBounds(647, 109, 89, 26);

		compilePathPanel.add(btnCompileCore2);

		JLabel lblCompileCore3 = new JLabel("Core 3");

		lblCompileCore3.setBounds(22, 154, 100, 26);

		compilePathPanel.add(lblCompileCore3);

		txtCompilePathCore3 = new JTextField();

		txtCompilePathCore3.setColumns(10);

		txtCompilePathCore3.setBounds(132, 154, 509, 26);

		compilePathPanel.add(txtCompilePathCore3);

		JButton btnCompileCore3 = new JButton(new BrowseAction("Browse", txtCompilePathCore3));

		btnCompileCore3.setBounds(647, 154, 89, 26);

		compilePathPanel.add(btnCompileCore3);

		JLabel lblCompileCore4 = new JLabel("Core 4");

		lblCompileCore4.setBounds(22, 199, 100, 26);

		compilePathPanel.add(lblCompileCore4);

		txtCompilePathCore4 = new JTextField();

		txtCompilePathCore4.setColumns(10);

		txtCompilePathCore4.setBounds(132, 199, 509, 26);

		compilePathPanel.add(txtCompilePathCore4);

		JButton btnCompileCore4 = new JButton(new BrowseAction("Browse", txtCompilePathCore4));

		btnCompileCore4.setBounds(647, 199, 89, 26);

		compilePathPanel.add(btnCompileCore4);

		JLabel lblCompileCore5 = new JLabel("Core 5");

		lblCompileCore5.setBounds(22, 244, 100, 26);

		compilePathPanel.add(lblCompileCore5);

		txtCompilePathCore5 = new JTextField();

		txtCompilePathCore5.setColumns(10);

		txtCompilePathCore5.setBounds(132, 244, 509, 26);

		compilePathPanel.add(txtCompilePathCore5);

		JButton btnCompileCore5 = new JButton(new BrowseAction("Browse", txtCompilePathCore5));

		btnCompileCore5.setBounds(647, 244, 89, 26);

		compilePathPanel.add(btnCompileCore5);

		JLabel lblCompileCore6 = new JLabel("Core 6");

		lblCompileCore6.setBounds(22, 289, 100, 26);

		compilePathPanel.add(lblCompileCore6);

		txtCompilePathCore6 = new JTextField();

		txtCompilePathCore6.setColumns(10);

		txtCompilePathCore6.setBounds(132, 289, 509, 26);

		compilePathPanel.add(txtCompilePathCore6);

		JButton btnCompileCore6 = new JButton(new BrowseAction("Browse", txtCompilePathCore6));

		btnCompileCore6.setBounds(647, 289, 89, 26);

		compilePathPanel.add(btnCompileCore6);

		JLabel lblCompileCore7 = new JLabel("Core 7");

		lblCompileCore7.setBounds(22, 334, 100, 26);

		compilePathPanel.add(lblCompileCore7);

		txtCompilePathCore7 = new JTextField();

		txtCompilePathCore7.setColumns(10);

		txtCompilePathCore7.setBounds(132, 334, 509, 26);

		compilePathPanel.add(txtCompilePathCore7);

		JButton btnCompileCore7 = new JButton(new BrowseAction("Browse", txtCompilePathCore7));

		btnCompileCore7.setBounds(647, 334, 89, 26);

		compilePathPanel.add(btnCompileCore7);

		JPanel compileOutFilePanel = new JPanel();

		compileOutFilePanel.setPreferredSize(new Dimension(10, 100));

		compilePanel.add(compileOutFilePanel, BorderLayout.SOUTH);

		compileOutFilePanel.setLayout(new BorderLayout(0, 0));

		JPanel compileFinalOutFilePanel = new JPanel();

		compileFinalOutFilePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Out File",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		compileOutFilePanel.add(compileFinalOutFilePanel, BorderLayout.CENTER);

		compileFinalOutFilePanel.setLayout(null);

		JLabel lblLblconfigfinaloutfile = new JLabel("Final Out File");

		lblLblconfigfinaloutfile.setBounds(25, 24, 100, 26);

		compileFinalOutFilePanel.add(lblLblconfigfinaloutfile);

		txtCompilePathFinalOut = new JTextField();

		txtCompilePathFinalOut.setBounds(135, 24, 509, 26);

		txtCompilePathFinalOut.setColumns(10);

		compileFinalOutFilePanel.add(txtCompilePathFinalOut);

		JButton btnCompileFinalOutFile = new JButton(new BrowseAction("Browse", txtCompilePathFinalOut));

		btnCompileFinalOutFile.setBounds(650, 24, 89, 26);

		compileFinalOutFilePanel.add(btnCompileFinalOutFile);

		JPanel compileControlsPanel = new JPanel();

		compileOutFilePanel.add(compileControlsPanel, BorderLayout.SOUTH);

		JButton btnCompileApply = new JButton("Compile");

		btnCompileApply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String error = checkPathsValid(COMPILATION);

				if (error.length() == 0) {
					VPXUtilities.createDeploymentFile(txtCompilePathFinalOut.getText(), txtCompilePathCore0.getText(),
							txtCompilePathCore1.getText(), txtCompilePathCore2.getText(),
							txtCompilePathCore3.getText(), txtCompilePathCore4.getText(),
							txtCompilePathCore5.getText(), txtCompilePathCore6.getText(), txtCompilePathCore7.getText());

					VPXUtilities.createOutFile();

					JOptionPane.showMessageDialog(null, "Out file created successfully");
				} else {

					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		compileControlsPanel.add(btnCompileApply);

		JButton btnCompileClear = new JButton("Clear");

		btnCompileClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearCompilationFields();

			}
		});

		compileControlsPanel.add(btnCompileClear);
	}

	private void loadPathsFromProperties() {

		txtConfigPathPython.setText(p.getProperty(VPXUtilities.PATH_PYTHON));

		txtConfigPathMAP.setText(p.getProperty(VPXUtilities.PATH_MAP));

		txtConfigPathPrelinker.setText(p.getProperty(VPXUtilities.PATH_PRELINKER));

		txtConfigPathStriper.setText(p.getProperty(VPXUtilities.PATH_STRIPER));

		txtConfigPathOFD.setText(p.getProperty(VPXUtilities.PATH_OFD));

		txtConfigPathMAL.setText(p.getProperty(VPXUtilities.PATH_MAL));

		txtConfigPathNML.setText(p.getProperty(VPXUtilities.PATH_NML));

		txtConfigPathDummyOut.setText(p.getProperty(VPXUtilities.PATH_DUMMY));

		boolean dummy = Boolean.valueOf(p.getProperty(VPXUtilities.DUMMY_CHK));

		chkConfigDummyOut.setSelected(dummy);

		txtConfigPathDummyOut.setEnabled(dummy);

		txtCompilePathCore0.setText(p.getProperty(VPXUtilities.PATH_CORE0));

		txtCompilePathCore1.setText(p.getProperty(VPXUtilities.PATH_CORE1));

		txtCompilePathCore2.setText(p.getProperty(VPXUtilities.PATH_CORE2));

		txtCompilePathCore3.setText(p.getProperty(VPXUtilities.PATH_CORE3));

		txtCompilePathCore4.setText(p.getProperty(VPXUtilities.PATH_CORE4));

		txtCompilePathCore5.setText(p.getProperty(VPXUtilities.PATH_CORE5));

		txtCompilePathCore6.setText(p.getProperty(VPXUtilities.PATH_CORE6));

		txtCompilePathCore7.setText(p.getProperty(VPXUtilities.PATH_CORE7));

		txtCompilePathFinalOut.setText(p.getProperty(VPXUtilities.PATH_OUT));

	}

	private void clearCompilationFields() {

		txtCompilePathCore0.setText("");

		txtCompilePathCore1.setText("");

		txtCompilePathCore2.setText("");

		txtCompilePathCore3.setText("");

		txtCompilePathCore4.setText("");

		txtCompilePathCore5.setText("");

		txtCompilePathCore6.setText("");

		txtCompilePathCore7.setText("");

		txtCompilePathFinalOut.setText("");
	}

	private void clearConfigurationFields() {

		txtConfigPathPython.setText("");

		txtConfigPathMAP.setText("");

		txtConfigPathPrelinker.setText("");

		txtConfigPathStriper.setText("");

		txtConfigPathOFD.setText("");

		txtConfigPathMAL.setText("");

		txtConfigPathNML.setText("");

		txtConfigPathDummyOut.setText("");

		chkConfigDummyOut.setSelected(false);

		txtConfigPathDummyOut.setEnabled(false);
	}

	private String checkPathsValid(int option) {

		boolean isValid = true;

		StringBuilder paths = new StringBuilder("");

		if (option == CONFIGURATION) {

			paths.append("Error occured while configuring paths.\n");

			if (!VPXUtilities.isFileValid(txtConfigPathPython.getText().trim())) {

				paths.append("Python path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtConfigPathMAP.getText().trim())) {

				paths.append("MAP file path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtConfigPathPrelinker.getText().trim())) {

				paths.append("Prelinker path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtConfigPathStriper.getText().trim())) {

				paths.append("Striper path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtConfigPathOFD.getText().trim())) {

				paths.append("OFD path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtConfigPathMAL.getText().trim())) {

				paths.append("MAL Application path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtConfigPathNML.getText().trim())) {

				paths.append("NML Loader path is not valid.\n");

				isValid = false;
			}

			if (chkConfigDummyOut.isSelected()) {

				if (!VPXUtilities.isFileValid(txtConfigPathDummyOut.getText().trim())) {

					paths.append("Dummy Out file path is not valid.\n");

					isValid = false;
				}
			}

		} else if (option == COMPILATION) {

			paths.append("Error occured while compiling out files.\n");

			if (!VPXUtilities.isFileValid(txtCompilePathCore0.getText().trim())) {

				paths.append("Core 0 Out file path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtCompilePathCore1.getText().trim())) {

				paths.append("Core 1 Out file path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtCompilePathCore2.getText().trim())) {

				paths.append("Core 2 Out file path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtCompilePathCore3.getText().trim())) {

				paths.append("Core 3 Out file path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtCompilePathCore4.getText().trim())) {

				paths.append("Core 4 Out file path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtCompilePathCore5.getText().trim())) {

				paths.append("Core 5 Out file path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtCompilePathCore6.getText().trim())) {

				paths.append("Core 6 Out file path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtCompilePathCore7.getText().trim())) {

				paths.append("Core 7 Out file path is not valid.\n");

				isValid = false;
			}

			if (!VPXUtilities.isFileValid(txtCompilePathFinalOut.getText().trim(), true)) {

				paths.append("Final Out file path is not valid.\n");

				isValid = false;
			}

		}

		if (isValid)
			paths = paths.delete(0, paths.length());

		return paths.toString();
	}

	public class BrowseAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		JTextField jtf;

		boolean isXMLFilter = false;

		public BrowseAction(String name, JTextField txt) {

			jtf = txt;

			putValue(NAME, name);

		}

		public BrowseAction(String name, JTextField txt, boolean isXML) {
			jtf = txt;

			this.isXMLFilter = isXML;

			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			fileDialog.addChoosableFileFilter(filterOut);

			fileDialog.setAcceptAllFileFilterUsed(false);

			int returnVal = fileDialog.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				java.io.File file = fileDialog.getSelectedFile();

				jtf.setText(file.getPath());
			}
		}
	}
}

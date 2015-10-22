package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_FlashWizardWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6933734296966776392L;

	private static MADProcessPanel madProcessPanel;

	private JPanel introPanel;

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

	private final JFileChooser fileDialog = new JFileChooser();

	private Properties p = VPXUtilities.readProperties();

	private JCheckBox chkConfigDummyOut;

	private static String currMapPath;

	private static String currentdployCfg = "";

	private static String folderPath;

	private VPX_ETHWindow parent;

	private JTabbedPane madTab;

	private VPX_EthernetFlashPanel flashPanel;

	private JButton btnBack;

	private JButton btnNext;

	private JButton btnClose;

	private JButton btnOpenFolder;

	private JButton btnClearFields;

	private boolean isFileCreated = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPX_FlashWizardWindow dialog = new VPX_FlashWizardWindow();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public VPX_FlashWizardWindow() {

		// this.parent = parnt;

		init();

		loadComponents();

		loadDefaultSettings();

		pack();

		centerFrame();

		loadPathsFromProperties();

	}

	public VPX_FlashWizardWindow(VPX_ETHWindow parnt) {

		this.parent = parnt;

		init();

		loadComponents();

		loadDefaultSettings();

		pack();

		centerFrame();

		loadPathsFromProperties();

	}

	private void init() {

		setTitle("Flash Wizard");

		setIconImage(VPXConstants.Icons.ICON_MAD_WIZARD.getImage());

		setResizable(false);

		setSize(800, 600);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout(0, 0));

	}

	private void loadComponents() {

		JPanel wizardsContainerPanel = new JPanel();

		getContentPane().add(wizardsContainerPanel, BorderLayout.CENTER);

		wizardsContainerPanel.setLayout(new BorderLayout(0, 0));

		madTab = new JTabbedPane(JTabbedPane.TOP);

		wizardsContainerPanel.add(madTab, BorderLayout.CENTER);

		createIntroPanel();

		createConfigPanel();

		createCompilePanel();

		createGeneratePanel();

		createFlashPanel();

		madTab.addTab("Intro", null, new JScrollPane(introPanel), null);

		madTab.addTab("Configuration", null, new JScrollPane(configPanel), null);

		madTab.addTab("Compilation", null, new JScrollPane(compilePanel), null);

		madTab.addTab("Generate Out File", null, new JScrollPane(madProcessPanel), null);

		madTab.addTab("Flash", null, new JScrollPane(flashPanel), null);

		createControlsPanel();

	}

	private void createIntroPanel() {

		String intro = "<html>" + "<body>" + "<p><h2>This wizard is combination of</h2> </p>" + "<ul>"
				+ "<li><b>MAD Utility</b></li><br>" + "<li><b>Ethernet Flash</b></li>" + "</ul>" + "<ol>"
				+ "<li><b>MAD Utility</b></li>" + "<ul type='disc'>"
				+ "<li>User can select the paths of tools which are generating the out file</li><br>"
				+ "<li>User have an option to use dummy file instead of empty out file</li><br>"
				+ "<li>User can select the paths of core out files and generating out file</li><br>" + "</ul>" + "<br>"
				+ "<li><b>Ethernet Flash</b></li>" + "<ul type='disc'>"
				+ "<li>User can flash the generated out file into to memory</li><br>"
				+ "<li>User have an option to select device NAND or NOR</li><br>"
				+ "<li>User have an option to flash location</li><br>" + "</ul>" + "</ol>" + "</body>" + "</html>";

		introPanel = new JPanel();

		introPanel
				.setBorder(new TitledBorder(null, "Introduction", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		introPanel.setPreferredSize(new Dimension(800, 500));

		introPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblIntro = new JLabel(intro);

		lblIntro.setVerticalAlignment(SwingConstants.TOP);

		introPanel.add(lblIntro, BorderLayout.CENTER);

	}

	private void createConfigPanel() {

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

		JLabel lblConfigStriper = new JLabel("Stripper Tool");

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

		configDummyOutFilePanel
				.setBorder(new TitledBorder(null, "Dummy File", TitledBorder.LEADING, TitledBorder.TOP, null, null));

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

	}

	private void createCompilePanel() {

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

	}

	private void createGeneratePanel() {

		madProcessPanel = new MADProcessPanel();
	}

	private void createFlashPanel() {

		flashPanel = new VPX_EthernetFlashPanel(parent, true);
	}

	private void createControlsPanel() {

		JPanel controlsPanel = new JPanel();

		controlsPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));

		controlsPanel.setPreferredSize(new Dimension(10, 50));

		getContentPane().add(controlsPanel, BorderLayout.SOUTH);

		controlsPanel.setLayout(new BorderLayout(0, 0));

		JPanel emptyPanel = new JPanel();

		emptyPanel.setPreferredSize(new Dimension(150, 10));

		controlsPanel.add(emptyPanel, BorderLayout.WEST);

		JPanel navigationPanel = new JPanel();

		FlowLayout fl_navigationPanel = (FlowLayout) navigationPanel.getLayout();

		fl_navigationPanel.setVgap(12);

		fl_navigationPanel.setAlignment(FlowLayout.LEFT);

		controlsPanel.add(navigationPanel, BorderLayout.CENTER);

		btnBack = new JButton("<  Back");

		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (madTab.getSelectedIndex() > 0) {

					int idx = madTab.getSelectedIndex() - 1;

					madTab.setSelectedIndex(idx);

					disableAll(idx);
				}

				if (madTab.getSelectedIndex() == 0) {

					btnBack.setEnabled(false);
				}

				btnNext.setEnabled(true);

			}
		});

		navigationPanel.add(btnBack);

		btnNext = new JButton("Next  >");

		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (madTab.getSelectedIndex() < madTab.getTabCount()) {

					doNext();
				}

				if (madTab.getSelectedIndex() == madTab.getTabCount() - 1) {

					btnNext.setEnabled(false);
				}

				btnBack.setEnabled(true);

			}
		});

		navigationPanel.add(btnNext);

		btnClearFields = new JButton("Clear");

		btnClearFields.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				doClear();

			}
		});

		navigationPanel.add(btnClearFields);

		btnOpenFolder = new JButton("Open Folder");

		btnOpenFolder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				madProcessPanel.openFolder();

			}
		});

		navigationPanel.add(btnOpenFolder);

		btnClose = new JButton("Close");

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				VPX_FlashWizardWindow.this.dispose();

			}
		});

		navigationPanel.add(btnClose);

	}

	private void doClear() {

		if (madTab.getSelectedIndex() == 1) {

			clearConfigurationFields();

		} else if (madTab.getSelectedIndex() == 2) {

			clearCompilationFields();

		} else if (madTab.getSelectedIndex() == 4) {

			// clearFlashFields();
		}

	}

	private void doNext() {

		if (madTab.getSelectedIndex() == 0 || madTab.getSelectedIndex() == 3) {

			int idx = madTab.getSelectedIndex() + 1;

			madTab.setEnabledAt(idx, true);

			madTab.setSelectedIndex(idx);

			disableAll(idx);

			if (idx == 4) {
				applyFlash();
			}

		} else if (madTab.getSelectedIndex() == 1) {

			applyConfiguration();

		} else if (madTab.getSelectedIndex() == 2) {

			applyCompilation();

			btnNext.setEnabled(false);

			btnBack.setEnabled(false);

		}

	}

	private void disableAll(int idx) {

		for (int i = 0; i < madTab.getTabCount(); i++) {

			madTab.setEnabledAt(i, false);
		}

		madTab.setEnabledAt(idx, true);

		if (idx == 0 || idx == 3) {

			btnClearFields.setEnabled(false);

		} else {

			btnClearFields.setEnabled(true);
		}

	}

	private void loadDefaultSettings() {

		madTab.setEnabledAt(0, true);

		for (int i = 1; i < madTab.getTabCount(); i++) {

			madTab.setEnabledAt(i, false);
		}

		btnNext.setEnabled(true);

		btnClearFields.setEnabled(false);

		btnOpenFolder.setEnabled(false);

		btnBack.setEnabled(false);

		btnClose.setEnabled(true);

		madTab.setSelectedIndex(0);
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	private void loadPathsFromProperties() {

		txtConfigPathPython.setText(p.getProperty(VPXConstants.ResourceFields.PATH_PYTHON));

		txtConfigPathMAP.setText(p.getProperty(VPXConstants.ResourceFields.PATH_MAP));

		txtConfigPathPrelinker.setText(p.getProperty(VPXConstants.ResourceFields.PATH_PRELINKER));

		txtConfigPathStriper.setText(p.getProperty(VPXConstants.ResourceFields.PATH_STRIPER));

		txtConfigPathOFD.setText(p.getProperty(VPXConstants.ResourceFields.PATH_OFD));

		txtConfigPathMAL.setText(p.getProperty(VPXConstants.ResourceFields.PATH_MAL));

		txtConfigPathNML.setText(p.getProperty(VPXConstants.ResourceFields.PATH_NML));

		txtConfigPathDummyOut.setText(p.getProperty(VPXConstants.ResourceFields.PATH_DUMMY));

		boolean dummy = Boolean.valueOf(p.getProperty(VPXConstants.ResourceFields.DUMMY_CHK));

		chkConfigDummyOut.setSelected(dummy);

		txtConfigPathDummyOut.setEnabled(dummy);

		txtCompilePathCore0.setText(p.getProperty(VPXConstants.ResourceFields.PATH_CORE0));

		txtCompilePathCore1.setText(p.getProperty(VPXConstants.ResourceFields.PATH_CORE1));

		txtCompilePathCore2.setText(p.getProperty(VPXConstants.ResourceFields.PATH_CORE2));

		txtCompilePathCore3.setText(p.getProperty(VPXConstants.ResourceFields.PATH_CORE3));

		txtCompilePathCore4.setText(p.getProperty(VPXConstants.ResourceFields.PATH_CORE4));

		txtCompilePathCore5.setText(p.getProperty(VPXConstants.ResourceFields.PATH_CORE5));

		txtCompilePathCore6.setText(p.getProperty(VPXConstants.ResourceFields.PATH_CORE6));

		txtCompilePathCore7.setText(p.getProperty(VPXConstants.ResourceFields.PATH_CORE7));

		txtCompilePathFinalOut.setText(p.getProperty(VPXConstants.ResourceFields.PATH_OUT));

	}

	private void applyConfiguration() {

		String error = checkPathsValid(VPXConstants.CONFIGURATION);

		if (error.length() == 0) {

			updateConfigFile(txtConfigPathPython.getText(), txtConfigPathMAP.getText(),
					txtConfigPathPrelinker.getText(), txtConfigPathOFD.getText(), txtConfigPathStriper.getText(),
					txtConfigPathMAL.getText(), txtConfigPathNML.getText(), chkConfigDummyOut.isSelected(),
					txtConfigPathDummyOut.getText());

			JOptionPane.showMessageDialog(null, "Paths are configured successfully");

			parent.updateLog("Configured Successfully");

			int idx = madTab.getSelectedIndex() + 1;

			madTab.setEnabledAt(idx, true);

			madTab.setSelectedIndex(idx);

			disableAll(idx);
		} else {

			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);

			parent.updateLog("Configuration error");

			parent.updateLog(error);
		}
	}

	private void applyCompilation() {

		if (chkConfigDummyOut.isSelected()) {
			fillDummyFiles();
		}

		String error = checkPathsValid(VPXConstants.COMPILATION);

		if (error.length() == 0) {

			parent.updateLog("MAD Compilation started");

			if (isFileCreated) {

				updateConfigFile(txtConfigPathPython.getText(), txtConfigPathMAP.getText(),
						txtConfigPathPrelinker.getText(), txtConfigPathOFD.getText(), txtConfigPathStriper.getText(),
						txtConfigPathMAL.getText(), txtConfigPathNML.getText(), chkConfigDummyOut.isSelected(),
						txtConfigPathDummyOut.getText());

			}

			createDeploymentFile(txtCompilePathFinalOut.getText(), txtCompilePathCore0.getText(),
					txtCompilePathCore1.getText(), txtCompilePathCore2.getText(), txtCompilePathCore3.getText(),
					txtCompilePathCore4.getText(), txtCompilePathCore5.getText(), txtCompilePathCore6.getText(),
					txtCompilePathCore7.getText());

			String path = txtCompilePathFinalOut.getText();

			madProcessPanel.setPath(path.substring(0, path.lastIndexOf("\\")));

			madProcessPanel.doCompile();

			parent.updateLog("MAD Compilation Completed");

			int idx = madTab.getSelectedIndex() + 1;

			madTab.setEnabledAt(idx, true);

			madTab.setSelectedIndex(idx);

			disableAll(idx);
		} else {

			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);

			parent.updateLog("MAD out files error");

			parent.updateLog(error);
		}
	}

	private void applyFlash() {

		flashPanel.setFilePath(txtCompilePathFinalOut.getText().trim());
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

	private void fillDummyFiles() {

		Properties p = VPXUtilities.readProperties();

		String dummy = p.getProperty(VPXConstants.ResourceFields.PATH_DUMMY);

		if (txtCompilePathCore0.getText().trim().length() == 0) {

			txtCompilePathCore0.setForeground(Color.BLUE);

			txtCompilePathCore0.setText(dummy);

		}

		if (txtCompilePathCore1.getText().trim().length() == 0) {

			txtCompilePathCore1.setForeground(Color.BLUE);

			txtCompilePathCore1.setText(dummy);

		}

		if (txtCompilePathCore2.getText().trim().length() == 0) {

			txtCompilePathCore2.setForeground(Color.BLUE);

			txtCompilePathCore2.setText(dummy);

		}

		if (txtCompilePathCore3.getText().trim().length() == 0) {

			txtCompilePathCore3.setForeground(Color.BLUE);

			txtCompilePathCore3.setText(dummy);

		}

		if (txtCompilePathCore4.getText().trim().length() == 0) {

			txtCompilePathCore4.setForeground(Color.BLUE);

			txtCompilePathCore4.setText(dummy);

		}

		if (txtCompilePathCore5.getText().trim().length() == 0) {

			txtCompilePathCore5.setForeground(Color.BLUE);

			txtCompilePathCore5.setText(dummy);
		}

		if (txtCompilePathCore6.getText().trim().length() == 0) {

			txtCompilePathCore6.setForeground(Color.BLUE);

			txtCompilePathCore6.setText(dummy);
		}

		if (txtCompilePathCore7.getText().trim().length() == 0) {

			txtCompilePathCore7.setForeground(Color.BLUE);

			txtCompilePathCore7.setText(dummy);
		}

	}

	private String checkPathsValid(int option) {

		boolean isValid = true;

		StringBuilder paths = new StringBuilder("");

		if (option == VPXConstants.CONFIGURATION) {

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

		} else if (option == VPXConstants.COMPILATION) {

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

	public void updateConfigFile(String pythonPath, String mapTool, String prelinker, String ofd, String strip,
			String mal, String nml, boolean isUseDummy, String dummy) {

		pythonPath = pythonPath.replaceAll("\\\\", "/");

		mapTool = mapTool.replaceAll("\\\\", "/");

		prelinker = prelinker.replaceAll("\\\\", "/");

		ofd = ofd.replaceAll("\\\\", "/");

		strip = strip.replaceAll("\\\\", "/");

		mal = mal.replaceAll("\\\\", "/");

		nml = nml.replaceAll("\\\\", "/");

		dummy = dummy.replaceAll("\\\\", "/");

		Properties p = VPXUtilities.readProperties();

		p.setProperty(VPXConstants.ResourceFields.PATH_PYTHON, pythonPath);

		p.setProperty(VPXConstants.ResourceFields.PATH_MAP, mapTool);

		p.setProperty(VPXConstants.ResourceFields.PATH_PRELINKER, prelinker);

		p.setProperty(VPXConstants.ResourceFields.PATH_OFD, ofd);

		p.setProperty(VPXConstants.ResourceFields.PATH_STRIPER, strip);

		p.setProperty(VPXConstants.ResourceFields.PATH_MAL, mal);

		p.setProperty(VPXConstants.ResourceFields.PATH_NML, nml);

		p.setProperty(VPXConstants.ResourceFields.DUMMY_CHK, isUseDummy ? "true" : "false");

		p.setProperty(VPXConstants.ResourceFields.PATH_DUMMY, dummy);

		VPXUtilities.updateProperties(p);

		currMapPath = mapTool;

		currentdployCfg = VPXUtilities.readFile("deploy/config.data");

		currentdployCfg = currentdployCfg.replace("prelinkpath", prelinker);

		currentdployCfg = currentdployCfg.replace("ofdpath", ofd);

		currentdployCfg = currentdployCfg.replace("strippath", strip);

		currentdployCfg = currentdployCfg.replace("malpath", mal);

		currentdployCfg = currentdployCfg.replace("nampath", nml);
	}

	public void createDeploymentFile(String outfilename, String out1Path, String out2Path, String out3Path,
			String out4Path, String out5Path, String out6Path, String out7Path, String out8Path) {

		String str = VPXUtilities.readFile("deploy/deployment.data");

		str = str.replace("out1", out1Path.replaceAll("\\\\", "/"));

		str = str.replace("out2", out2Path.replaceAll("\\\\", "/"));

		str = str.replace("out3", out3Path.replaceAll("\\\\", "/"));

		str = str.replace("out4", out4Path.replaceAll("\\\\", "/"));

		str = str.replace("out5", out5Path.replaceAll("\\\\", "/"));

		str = str.replace("out6", out6Path.replaceAll("\\\\", "/"));

		str = str.replace("out7", out7Path.replaceAll("\\\\", "/"));

		str = str.replace("out8", out8Path.replaceAll("\\\\", "/"));

		Properties p = VPXUtilities.readProperties();

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE0, out1Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE1, out2Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE2, out3Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE3, out4Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE4, out5Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE5, out6Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE6, out7Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE7, out8Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_OUT, outfilename);

		VPXUtilities.updateProperties(p);

		folderPath = currMapPath.substring(0, currMapPath.lastIndexOf("/"));

		VPXUtilities.writeFile(folderPath + "/" + VPXConstants.ResourceFields.DEPLOYMENTFILE, str);

		currentdployCfg = currentdployCfg.replace("jsonpath",
				folderPath + "/" + VPXConstants.ResourceFields.DEPLOYMENTFILE);

		currentdployCfg = currentdployCfg.replace("imagenamepath", outfilename);

		VPXUtilities.writeFile(folderPath + "/" + VPXConstants.ResourceFields.DEPLOYMENTCONFIGFILE, currentdployCfg);

	}

	public boolean createOutFile() {

		boolean ret = true;

		Properties p = VPXUtilities.readProperties();

		String cmd = String.format("cmd /c %s %s bypass-prelink", p.getProperty(VPXConstants.ResourceFields.PATH_MAP),
				folderPath + "/" + VPXConstants.ResourceFields.DEPLOYMENTCONFIGFILE);
		// String cmd = String.format("cmd /c ping 192.168.0.102");

		parent.updateLog("Creating deployment files");

		parent.updateLog("Creating deployment configuration files");

		try {

			Process proc = Runtime.getRuntime().exec(cmd);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			String s = null;

			madProcessPanel.clearMessage();

			while ((s = stdInput.readLine()) != null) {

				madProcessPanel.updateGeneratingMessage(s);

				parent.updateLog(s);

				if (s.contains("Error")) {
					ret = false;
				}

			}

			if (ret) {

				madProcessPanel.setSuccess();

			} else {

				madProcessPanel.setFailure();

				JOptionPane.showMessageDialog(madProcessPanel, "Error in generating out file");

				parent.updateLog("Error in generating out file");
			}

			VPXUtilities.deleteAllGeneratedFilesAndFlders(folderPath, VPXConstants.ResourceFields.DEPLOYMENTFILE,
					VPXConstants.ResourceFields.DEPLOYMENTCONFIGFILE);

			isFileCreated = true;

			return ret;
		} catch (Exception e) {
			ret = false;
			e.printStackTrace();
			VPXUtilities.updateError(e);
		}

		return ret;
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

			// fileDialog.addChoosableFileFilter(filterOut);

			// fileDialog.setAcceptAllFileFilterUsed(false);

			int returnVal = fileDialog.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				java.io.File file = fileDialog.getSelectedFile();

				jtf.setText(file.getPath());
			}
		}
	}

	public class MADProcessPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3411706910706113431L;

		private final JPanel contentPanel = new JPanel();

		private JButton btnOpen;

		// private JButton btnCancel;

		private String path = null;

		private JLabel lblOpenFolder;

		private JTextArea txtAResult;

		private JScrollPane scrResult;

		/**
		 * Create the dialog.
		 */
		public MADProcessPanel(String pathToOPen) {

			this.path = pathToOPen;

			init();

			loadComponents();

			centerFrame();

		}

		public MADProcessPanel() {

			init();

			loadComponents();

			centerFrame();

		}

		public void setPath(String pathToOPen) {

			this.path = pathToOPen;
		}

		public void doCompile() {

			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {
					createOutFile();

				}
			});

			th.start();

		}

		private void init() {

			this.setPreferredSize(new Dimension(800, 350));

			this.setLayout(new BorderLayout());

		}

		private void loadComponents() {

			JPanel basePanel = new JPanel();

			basePanel.setLayout(new BorderLayout());

			basePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

			add(basePanel, BorderLayout.CENTER);

			basePanel.add(contentPanel, BorderLayout.CENTER);

			contentPanel.setLayout(new BorderLayout(0, 0));

			JPanel descriptionPanel = new JPanel();

			descriptionPanel.setPreferredSize(new Dimension(10, 50));

			contentPanel.add(descriptionPanel, BorderLayout.NORTH);

			descriptionPanel.setLayout(new BorderLayout(0, 0));

			JLabel lblDescription = new JLabel("Generating out file started.Detail are showing below");

			descriptionPanel.add(lblDescription, BorderLayout.CENTER);

			scrResult = new JScrollPane();

			contentPanel.add(scrResult, BorderLayout.CENTER);

			txtAResult = new JTextArea();

			txtAResult.setEditable(false);

			scrResult.setViewportView(txtAResult);

			lblOpenFolder = new JLabel("Click open button to open generated out file folder");

			lblOpenFolder.setEnabled(false);

			lblOpenFolder.setPreferredSize(new Dimension(243, 20));

			contentPanel.add(lblOpenFolder, BorderLayout.SOUTH);

			contentPanel.add(new JLabel("   "), BorderLayout.EAST);

			contentPanel.add(new JLabel("   "), BorderLayout.WEST);

			JPanel buttonPane = new JPanel();

			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

			// basePanel.add(buttonPane, BorderLayout.SOUTH);

			btnOpen = new JButton("Open");

			btnOpen.setActionCommand("OK");

			btnOpen.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					openFolder();

				}
			});

			btnOpen.setEnabled(false);

			buttonPane.add(btnOpen);

		}

		public void openFolder() {

			try {

				Desktop.getDesktop().open(new File(path));

			} catch (IOException e) {
				VPXUtilities.updateError(e);
			}
		}

		public void clearMessage() {

			txtAResult.setText("");
		}

		public void updateGeneratingMessage(String msg) {

			if (msg.length() > 0) {

				txtAResult.append(msg + "\n");

				txtAResult.setCaretPosition(txtAResult.getDocument().getLength());
			}
		}

		public void setSuccess() {

			btnOpen.setEnabled(true);

			btnOpenFolder.setEnabled(true);

			btnNext.setEnabled(true);

			btnBack.setEnabled(true);
			// btnCancel.setEnabled(true);

			lblOpenFolder.setEnabled(true);
		}

		public void setFailure() {

			btnOpen.setEnabled(false);

			btnOpenFolder.setEnabled(false);

			btnNext.setEnabled(true);

			btnBack.setEnabled(true);

			// btnCancel.setEnabled(true);

			lblOpenFolder.setEnabled(false);
		}
	}
}

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
import java.io.FilenameFilter;
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

import org.apache.commons.io.FileUtils;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

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

	protected JLabel lblConfigDummyOutFile;

	protected JButton btnConfigBrowseDummyOut;

	private FilenameFilter outFilter = null;

	private VPX_FlashProgressWindow dialog;

	private JButton btnLoadWorkspace;

	private JButton btnBackup;

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

		// introPanel.setPreferredSize(new Dimension(800, 500));

		introPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblIntro = new JLabel(intro);

		lblIntro.setVerticalAlignment(SwingConstants.TOP);

		introPanel.add(lblIntro, BorderLayout.CENTER);

	}

	private void createConfigPanel() {

		configPanel = new JPanel();

		configPanel.setLayout(new BorderLayout(0, 0));

		JPanel configPathPanel = new JPanel();

		configPathPanel.setBorder(new TitledBorder(null, "Paths", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		configPanel.add(configPathPanel, BorderLayout.CENTER);

		configPathPanel
				.setLayout(new MigLayout("", "[][][][grow][][][]", "[grow][grow][grow][grow][grow][grow][grow]"));

		JLabel lblConfigPython = new JLabel("Python");

		configPathPanel.add(lblConfigPython, "cell 1 0");

		txtConfigPathPython = new JTextField();

		configPathPanel.add(txtConfigPathPython, "flowx,cell 3 0,growx");

		txtConfigPathPython.setColumns(10);

		JButton btnConfigBrowsePython = new JButton(new BrowseAction("Browse", txtConfigPathPython));

		configPathPanel.add(btnConfigBrowsePython, "cell 5 0");

		JLabel lblConfigMAP = new JLabel("MAP Tool");

		configPathPanel.add(lblConfigMAP, "cell 1 1");

		txtConfigPathMAP = new JTextField();

		txtConfigPathMAP.setColumns(10);

		configPathPanel.add(txtConfigPathMAP, "flowx,cell 3 1,growx");

		JButton btnConfigBrowseMAP = new JButton(new BrowseAction("Browse", txtConfigPathMAP));

		configPathPanel.add(btnConfigBrowseMAP, "cell 5 1");

		JLabel lblConfigPrelinker = new JLabel("Prelinker Tool");

		configPathPanel.add(lblConfigPrelinker, "cell 1 2");

		txtConfigPathPrelinker = new JTextField();

		txtConfigPathPrelinker.setColumns(10);

		configPathPanel.add(txtConfigPathPrelinker, "flowx,cell 3 2,growx");

		JButton btnConfigBrowsePrelinker = new JButton(new BrowseAction("Browse", txtConfigPathPrelinker));

		configPathPanel.add(btnConfigBrowsePrelinker, "cell 5 2");

		JLabel lblConfigStriper = new JLabel("Stripper Tool");

		configPathPanel.add(lblConfigStriper, "cell 1 3");

		txtConfigPathStriper = new JTextField();

		txtConfigPathStriper.setColumns(10);

		configPathPanel.add(txtConfigPathStriper, "flowx,cell 3 3,growx");

		JButton btnConfigBrowseStriper = new JButton(new BrowseAction("Browse", txtConfigPathStriper));

		configPathPanel.add(btnConfigBrowseStriper, "cell 5 3");

		JLabel lblConfigOFD = new JLabel("OFD Tool");

		configPathPanel.add(lblConfigOFD, "cell 1 4");

		txtConfigPathOFD = new JTextField();

		txtConfigPathOFD.setColumns(10);

		configPathPanel.add(txtConfigPathOFD, "flowx,cell 3 4,growx");

		JButton btnConfigBrowseOFD = new JButton(new BrowseAction("Browse", txtConfigPathOFD));

		configPathPanel.add(btnConfigBrowseOFD, "cell 5 4");

		JLabel lblConfigMAL = new JLabel("MAL Application");

		configPathPanel.add(lblConfigMAL, "cell 1 5");

		txtConfigPathMAL = new JTextField();

		txtConfigPathMAL.setColumns(10);

		configPathPanel.add(txtConfigPathMAL, "flowx,cell 3 5,growx");

		JButton btnConfigBrowseMAL = new JButton(new BrowseAction("Browse", txtConfigPathMAL));

		configPathPanel.add(btnConfigBrowseMAL, "cell 5 5");

		JLabel lblConfigNML = new JLabel("NML Loader");

		configPathPanel.add(lblConfigNML, "cell 1 6");

		txtConfigPathNML = new JTextField();

		txtConfigPathNML.setColumns(10);

		configPathPanel.add(txtConfigPathNML, "flowx,cell 3 6,growx");

		JButton btnConfigBrowseNMLLoader = new JButton(new BrowseAction("Browse", txtConfigPathNML));

		configPathPanel.add(btnConfigBrowseNMLLoader, "cell 5 6");

		JPanel configDummyPanel = new JPanel();

		configPanel.add(configDummyPanel, BorderLayout.SOUTH);

		configDummyPanel.setLayout(new BorderLayout(0, 0));

		JPanel configDummyOutFilePanel = new JPanel();

		configDummyPanel.add(configDummyOutFilePanel, BorderLayout.CENTER);

		configDummyOutFilePanel.setLayout(new MigLayout("", "[][][grow][][][]", "[][]"));

		chkConfigDummyOut = new JCheckBox("Use dummy out file if no out file is selected");

		chkConfigDummyOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				txtConfigPathDummyOut.setEnabled(chkConfigDummyOut.isSelected());

				lblConfigDummyOutFile.setEnabled(chkConfigDummyOut.isSelected());

				btnConfigBrowseDummyOut.setEnabled(chkConfigDummyOut.isSelected());

			}
		});

		configDummyOutFilePanel.add(chkConfigDummyOut, "cell 1 0");

		lblConfigDummyOutFile = new JLabel("Dummy Out File");

		configDummyOutFilePanel.add(lblConfigDummyOutFile, "cell 1 1,alignx left");

		txtConfigPathDummyOut = new JTextField();

		configDummyOutFilePanel.add(txtConfigPathDummyOut, "flowx,cell 2 1,growx");

		txtConfigPathDummyOut.setColumns(10);

		btnConfigBrowseDummyOut = new JButton(new BrowseAction("Browse", txtConfigPathDummyOut));

		configDummyOutFilePanel.add(btnConfigBrowseDummyOut, "cell 4 1");

	}

	private void createCompilePanel() {

		compilePanel = new JPanel();

		compilePanel.setLayout(new BorderLayout(0, 0));

		JPanel compilePathPanel = new JPanel();

		compilePathPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Out Files",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		compilePanel.add(compilePathPanel, BorderLayout.CENTER);

		compilePathPanel
				.setLayout(new MigLayout("", "[][][][grow][][][]", "[grow][grow][grow][grow][grow][grow][grow][grow]"));

		JLabel lblCompileCore0 = new JLabel("Core 0");

		compilePathPanel.add(lblCompileCore0, "cell 1 0");

		txtCompilePathCore0 = new JTextField();

		compilePathPanel.add(txtCompilePathCore0, "flowx,cell 3 0,growx");

		txtCompilePathCore0.setColumns(10);

		JButton btnCompileCore0 = new JButton(new BrowseAction("Browse", txtCompilePathCore0));

		compilePathPanel.add(btnCompileCore0, "cell 5 0");

		JLabel lblCompileCore1 = new JLabel("Core 1");

		compilePathPanel.add(lblCompileCore1, "cell 1 1");

		txtCompilePathCore1 = new JTextField();

		txtCompilePathCore1.setColumns(10);

		compilePathPanel.add(txtCompilePathCore1, "flowx,cell 3 1,growx");

		JButton btnCompileCore1 = new JButton(new BrowseAction("Browse", txtCompilePathCore1));

		compilePathPanel.add(btnCompileCore1, "cell 5 1");

		JLabel lblCompileCore2 = new JLabel("Core 2");

		compilePathPanel.add(lblCompileCore2, "cell 1 2");

		txtCompilePathCore2 = new JTextField();

		txtCompilePathCore2.setColumns(10);

		compilePathPanel.add(txtCompilePathCore2, "flowx,cell 3 2,growx");

		JButton btnCompileCore2 = new JButton(new BrowseAction("Browse", txtCompilePathCore2));

		compilePathPanel.add(btnCompileCore2, "cell 5 2");

		JLabel lblCompileCore3 = new JLabel("Core 3");

		compilePathPanel.add(lblCompileCore3, "cell 1 3");

		txtCompilePathCore3 = new JTextField();

		txtCompilePathCore3.setColumns(10);

		compilePathPanel.add(txtCompilePathCore3, "flowx,cell 3 3,growx");

		JButton btnCompileCore3 = new JButton(new BrowseAction("Browse", txtCompilePathCore3));

		compilePathPanel.add(btnCompileCore3, "cell 5 3");

		JLabel lblCompileCore4 = new JLabel("Core 4");

		compilePathPanel.add(lblCompileCore4, "cell 1 4");

		txtCompilePathCore4 = new JTextField();

		txtCompilePathCore4.setColumns(10);

		compilePathPanel.add(txtCompilePathCore4, "flowx,cell 3 4,growx");

		JButton btnCompileCore4 = new JButton(new BrowseAction("Browse", txtCompilePathCore4));

		compilePathPanel.add(btnCompileCore4, "cell 5 4");

		JLabel lblCompileCore5 = new JLabel("Core 5");

		compilePathPanel.add(lblCompileCore5, "cell 1 5");

		txtCompilePathCore5 = new JTextField();

		txtCompilePathCore5.setColumns(10);

		compilePathPanel.add(txtCompilePathCore5, "flowx,cell 3 5,growx");

		JButton btnCompileCore5 = new JButton(new BrowseAction("Browse", txtCompilePathCore5));

		compilePathPanel.add(btnCompileCore5, "cell 5 5");

		JLabel lblCompileCore6 = new JLabel("Core 6");

		compilePathPanel.add(lblCompileCore6, "cell 1 6");

		txtCompilePathCore6 = new JTextField();

		txtCompilePathCore6.setColumns(10);

		compilePathPanel.add(txtCompilePathCore6, "flowx,cell 3 6,growx");

		JButton btnCompileCore6 = new JButton(new BrowseAction("Browse", txtCompilePathCore6));

		compilePathPanel.add(btnCompileCore6, "cell 5 6");

		JLabel lblCompileCore7 = new JLabel("Core 7");

		compilePathPanel.add(lblCompileCore7, "cell 1 7");

		txtCompilePathCore7 = new JTextField();

		txtCompilePathCore7.setColumns(10);

		compilePathPanel.add(txtCompilePathCore7, "cell 3 7,growx");

		JButton btnCompileCore7 = new JButton(new BrowseAction("Browse", txtCompilePathCore7));

		compilePathPanel.add(btnCompileCore7, "cell 5 7");

		JPanel compileOutFilePanel = new JPanel();

		compilePanel.add(compileOutFilePanel, BorderLayout.SOUTH);

		compileOutFilePanel.setLayout(new BorderLayout(0, 0));

		JPanel compileFinalOutFilePanel = new JPanel();

		compileOutFilePanel.add(compileFinalOutFilePanel, BorderLayout.CENTER);

		compileFinalOutFilePanel.setLayout(new MigLayout("", "[][][grow][][][]", "[]"));

		JLabel lblLblconfigfinaloutfile = new JLabel("File Name");

		compileFinalOutFilePanel.add(lblLblconfigfinaloutfile, "cell 1 0,alignx left");

		txtCompilePathFinalOut = new JTextField();

		compileFinalOutFilePanel.add(txtCompilePathFinalOut, "flowx,cell 2 0,growx");

		txtCompilePathFinalOut.setColumns(10);

		// JButton btnCompileFinalOutFile = new JButton(new
		// BrowseAction("Browse", txtCompilePathFinalOut));

		// compileFinalOutFilePanel.add(btnCompileFinalOutFile, "cell 4 0");

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

		JPanel navigationPanel = new JPanel();

		FlowLayout fl_navigationPanel = (FlowLayout) navigationPanel.getLayout();

		fl_navigationPanel.setVgap(12);

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

		btnLoadWorkspace = new JButton("Load from workspace");

		btnLoadWorkspace.setEnabled(false);

		btnLoadWorkspace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				loadCoreFilesFromWorkspace();

			}
		});

		navigationPanel.add(btnLoadWorkspace);

		btnBackup = new JButton("Backup");

		btnBackup.setEnabled(false);

		btnBackup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Thread th = new Thread(new Runnable() {

					@Override
					public void run() {

						backupOutFiles();

					}
				});

				th.start();
			}
		});

		navigationPanel.add(btnBackup);

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

	private void loadCoreFilesFromWorkspace() {

		File f = null;

		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File file, String name) {
				if (name.endsWith(".out")) {
					// filters files whose extension is .out
					return true;
				} else {
					return false;
				}
			}
		};

		String corePath = VPXSessionManager.getDSPPath() + "/"
				+ VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM_DSP_CORE);

		for (int i = 0; i < 8; i++) {

			f = new File(String.format("%s %d", corePath, i));

			if (f.exists()) {

				File[] fs = f.listFiles(filter);

				if (fs.length > 0) {

					switch (i) {

					case 0:

						txtCompilePathCore0.setText(fs[0].getAbsolutePath());

						break;

					case 1:

						txtCompilePathCore1.setText(fs[0].getAbsolutePath());

						break;
					case 2:

						txtCompilePathCore2.setText(fs[0].getAbsolutePath());

						break;
					case 3:

						txtCompilePathCore3.setText(fs[0].getAbsolutePath());

						break;
					case 4:

						txtCompilePathCore4.setText(fs[0].getAbsolutePath());

						break;
					case 5:

						txtCompilePathCore5.setText(fs[0].getAbsolutePath());

						break;
					case 6:

						txtCompilePathCore6.setText(fs[0].getAbsolutePath());

						break;
					case 7:

						txtCompilePathCore7.setText(fs[0].getAbsolutePath());

						break;

					}

				}
			}
		}

	}

	private void backupOutFiles() {

		dialog = new VPX_FlashProgressWindow();

		dialog.setRangePackets(1, 8);

		dialog.setVisible(true);

		String corePath = VPXSessionManager.getDSPPath() + "/"
				+ VPXUtilities.getString(VPXConstants.ResourceFields.FOLDER_WORKSPACE_SUBSYSTEM_DSP_CORE);

		try {

			getFileNameFilter();

			String core0 = txtCompilePathCore0.getText().trim().replaceAll("\\\\", "/");
			String core1 = txtCompilePathCore1.getText().trim().replaceAll("\\\\", "/");
			String core2 = txtCompilePathCore2.getText().trim().replaceAll("\\\\", "/");
			String core3 = txtCompilePathCore3.getText().trim().replaceAll("\\\\", "/");
			String core4 = txtCompilePathCore4.getText().trim().replaceAll("\\\\", "/");
			String core5 = txtCompilePathCore5.getText().trim().replaceAll("\\\\", "/");
			String core6 = txtCompilePathCore6.getText().trim().replaceAll("\\\\", "/");
			String core7 = txtCompilePathCore7.getText().trim().replaceAll("\\\\", "/");
			String bin = txtCompilePathFinalOut.getText().trim().replaceAll("\\\\", "/");

			dialog.updatePackets(1, core0);
			checkAndCopy(corePath, core0, 0, false);
			dialog.updatePackets(2, core1);
			checkAndCopy(corePath, core1, 1, false);
			dialog.updatePackets(3, core2);
			checkAndCopy(corePath, core2, 2, false);
			dialog.updatePackets(4, core3);
			checkAndCopy(corePath, core3, 3, false);
			dialog.updatePackets(5, core4);
			checkAndCopy(corePath, core4, 4, false);
			dialog.updatePackets(6, core5);
			checkAndCopy(corePath, core5, 5, false);
			dialog.updatePackets(7, core6);
			checkAndCopy(corePath, core6, 6, false);
			dialog.updatePackets(8, core7);
			checkAndCopy(corePath, core7, 7, false);
			// dialog.updatePackets(9, bin);

			/*
			 * checkAndCopy( VPXSessionManager.getDSPPath() + "/" +
			 * VPXUtilities.getString(VPXConstants.ResourceFields.
			 * FOLDER_WORKSPACE_SUBSYSTEM_DSP_BIN), bin, 0, true);
			 */

		} catch (Exception e) {
			VPXLogger.updateError(e);
		}
	}

	private void checkAndCopy(String src, String dest, int coreID, boolean isBin) {

		if (isBin) {

			try {

				FileUtils.copyFileToDirectory(new File(dest), new File(src));

			} catch (Exception e) {

				e.printStackTrace();
			}

		} else {

			boolean isFound = false;

			File coreFile = new File(dest);

			File coreBFile = new File(src + " " + coreID);

			File[] lists = coreBFile.listFiles(outFilter);

			if (lists.length > 0) {

				for (int i = 0; i < lists.length; i++) {

					if (lists[i].equals(coreFile)) {

						isFound = true;

						break;
					}
				}
			}
			if (!isFound) {

				try {

					FileUtils.cleanDirectory(coreBFile);

					FileUtils.copyFileToDirectory(coreFile, coreBFile);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}

	private FilenameFilter getFileNameFilter() {

		if (outFilter == null) {
			outFilter = new FilenameFilter() {

				public boolean accept(File file, String name) {
					if (name.endsWith(".out")) {
						// filters files whose extension is .mp3
						return true;
					} else {
						return false;
					}
				}
			};
		}
		return outFilter;

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

		btnLoadWorkspace.setEnabled(false);

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

			btnLoadWorkspace.setEnabled(true);

		} else if (madTab.getSelectedIndex() == 2) {

			applyCompilation();

		

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

		btnLoadWorkspace.setEnabled(false);

		btnBack.setEnabled(false);

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

		txtConfigPathPython
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_PYTHON)));

		txtConfigPathMAP
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_MAP)));

		txtConfigPathPrelinker.setText(
				VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_PRELINKER)));

		txtConfigPathStriper
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_STRIPER)));

		txtConfigPathOFD
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_OFD)));

		txtConfigPathMAL
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_MAL)));

		txtConfigPathNML
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_NML)));

		txtConfigPathDummyOut
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_DUMMY)));

		boolean dummy = Boolean.valueOf(p.getProperty(VPXConstants.ResourceFields.DUMMY_CHK));

		chkConfigDummyOut.setSelected(dummy);

		txtConfigPathDummyOut.setEnabled(dummy);

		txtCompilePathCore0
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_CORE0)));

		txtCompilePathCore1
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_CORE1)));

		txtCompilePathCore2
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_CORE2)));

		txtCompilePathCore3
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_CORE3)));

		txtCompilePathCore4
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_CORE4)));

		txtCompilePathCore5
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_CORE5)));

		txtCompilePathCore6
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_CORE6)));

		txtCompilePathCore7
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_CORE7)));

		txtCompilePathFinalOut
				.setText(VPXUtilities.getPathAsLinuxStandard(p.getProperty(VPXConstants.ResourceFields.PATH_OUT)));

	}

	private void applyConfiguration() {

		String error = checkPathsValid(VPXConstants.CONFIGURATION);

		if (error.length() == 0) {

			updateConfigFile(txtConfigPathPython.getText(), txtConfigPathMAP.getText(),
					txtConfigPathPrelinker.getText(), txtConfigPathOFD.getText(), txtConfigPathStriper.getText(),
					txtConfigPathMAL.getText(), txtConfigPathNML.getText(), chkConfigDummyOut.isSelected(),
					txtConfigPathDummyOut.getText());

			JOptionPane.showMessageDialog(null, "Paths are configured successfully");

			VPXLogger.updateLog("Configured Successfully");

			int idx = madTab.getSelectedIndex() + 1;

			madTab.setEnabledAt(idx, true);

			madTab.setSelectedIndex(idx);

			disableAll(idx);
		} else {

			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);

			VPXLogger.updateLog("Configuration error");

			VPXLogger.updateLog(error);
		}
	}

	private void applyCompilation() {

		if (chkConfigDummyOut.isSelected()) {
			fillDummyFiles();
		}

		String error = checkPathsValid(VPXConstants.COMPILATION);

		if (error.length() == 0) {

			VPXLogger.updateLog("MAD Compilation started");

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

			String path = VPXSessionManager.getDSPPath() + "/bin/";

			madProcessPanel.setPath(path);

			madProcessPanel.doCompile();

			VPXLogger.updateLog("MAD Compilation Completed");

			int idx = madTab.getSelectedIndex() + 1;

			madTab.setEnabledAt(idx, true);

			madTab.setSelectedIndex(idx);

			disableAll(idx);
			
			btnNext.setEnabled(false);

			btnBack.setEnabled(false);
		} else {

			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);

			VPXLogger.updateLog("MAD out files error");

			VPXLogger.updateLog(error);
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

			if (!VPXUtilities.isFileNameValid(txtCompilePathFinalOut.getText().trim())) {

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

		pythonPath = VPXUtilities.getPathAsLinuxStandard(pythonPath);

		mapTool = VPXUtilities.getPathAsLinuxStandard(mapTool);

		prelinker = VPXUtilities.getPathAsLinuxStandard(prelinker);

		ofd = VPXUtilities.getPathAsLinuxStandard(ofd);

		strip = VPXUtilities.getPathAsLinuxStandard(strip);

		mal = VPXUtilities.getPathAsLinuxStandard(mal);

		nml = VPXUtilities.getPathAsLinuxStandard(nml);

		dummy = VPXUtilities.getPathAsLinuxStandard(dummy);

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

		currentdployCfg = VPXUtilities.readFile("deploy/config.data", VPXConstants.DELIMITER_FILE);

		currentdployCfg = currentdployCfg.replace("prelinkpath", prelinker);

		currentdployCfg = currentdployCfg.replace("ofdpath", ofd);

		currentdployCfg = currentdployCfg.replace("strippath", strip);

		currentdployCfg = currentdployCfg.replace("malpath", mal);

		currentdployCfg = currentdployCfg.replace("nampath", nml);
	}

	public void createDeploymentFile(String outfileName, String out1Path, String out2Path, String out3Path,
			String out4Path, String out5Path, String out6Path, String out7Path, String out8Path) {

		String str = VPXUtilities.readFile("deploy/deployment.data", VPXConstants.DELIMITER_FILE);

		String bin = outfileName;

		if (!bin.endsWith(".bin")) {

			bin = bin + ".bin";

		}

		String outfilename = VPXSessionManager.getDSPPath() + "/bin/" + bin;

		str = str.replace("out1", VPXUtilities.getPathAsLinuxStandard(out1Path));

		str = str.replace("out2", VPXUtilities.getPathAsLinuxStandard(out2Path));

		str = str.replace("out3", VPXUtilities.getPathAsLinuxStandard(out3Path));

		str = str.replace("out4", VPXUtilities.getPathAsLinuxStandard(out4Path));

		str = str.replace("out5", VPXUtilities.getPathAsLinuxStandard(out5Path));

		str = str.replace("out6", VPXUtilities.getPathAsLinuxStandard(out6Path));

		str = str.replace("out7", VPXUtilities.getPathAsLinuxStandard(out7Path));

		str = str.replace("out8", VPXUtilities.getPathAsLinuxStandard(out8Path));

		Properties p = VPXUtilities.readProperties();

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE0, out1Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE1, out2Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE2, out3Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE3, out4Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE4, out5Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE5, out6Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE6, out7Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_CORE7, out8Path);

		p.setProperty(VPXConstants.ResourceFields.PATH_OUT, outfileName);

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

		VPXLogger.updateLog("Creating deployment files");

		VPXLogger.updateLog("Creating deployment configuration files");

		try {

			Process proc = Runtime.getRuntime().exec(cmd);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			String s = null;

			madProcessPanel.clearMessage();

			while ((s = stdInput.readLine()) != null) {

				madProcessPanel.updateGeneratingMessage(s);

				VPXLogger.updateLog(s);

				if (s.contains("Error")) {
					ret = false;
				}

			}

			if (ret) {

				madProcessPanel.setSuccess();

			} else {

				madProcessPanel.setFailure();

				JOptionPane.showMessageDialog(madProcessPanel, "Error in generating out file");

				VPXLogger.updateLog("Error in generating out file");
			}

			VPXUtilities.deleteAllGeneratedFilesAndFlders(folderPath, VPXConstants.ResourceFields.DEPLOYMENTFILE,
					VPXConstants.ResourceFields.DEPLOYMENTCONFIGFILE);

			isFileCreated = true;

			return ret;
		} catch (Exception e) {
			ret = false;
			e.printStackTrace();
			VPXLogger.updateError(e);
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

				jtf.setText(VPXUtilities.getPathAsLinuxStandard(file.getPath()));
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
				VPXLogger.updateError(e);
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

			btnBackup.setEnabled(true);
			// btnCancel.setEnabled(true);

			lblOpenFolder.setEnabled(true);
		}

		public void setFailure() {

			btnOpen.setEnabled(false);

			btnOpenFolder.setEnabled(false);

			btnNext.setEnabled(true);

			btnBack.setEnabled(true);

			btnBackup.setEnabled(true);

			// btnCancel.setEnabled(true);

			lblOpenFolder.setEnabled(false);
		}
	}
}

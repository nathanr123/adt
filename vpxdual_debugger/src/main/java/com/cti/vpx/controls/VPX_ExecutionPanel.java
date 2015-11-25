package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.command.ATP;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

public class VPX_ExecutionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5981154131918463867L;

	private final JFileChooser fileDialog = new JFileChooser();

	private final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Out Files", "out");

	private JTextField txtCore1;

	private JTextField txtCore2;

	private JTextField txtCore3;

	private JTextField txtCore4;

	private JTextField txtCore5;

	private JTextField txtCore6;

	private JTextField txtCore7;

	private JPanel basePanel;

	private VPX_ETHWindow parent;

	private VPX_ExecutionFileTransferingWindow processingWindow = new VPX_ExecutionFileTransferingWindow(this);

	private JButton btnRunCore1;

	private JButton btnDLCore1;

	private JButton btnRunCore2;

	private JButton btnDLCore2;

	private JButton btnRunCore3;

	private JButton btnDLCore3;

	private JButton btnRunCore4;

	private JButton btnDLCore4;

	private JButton btnRunCore5;

	private JButton btnDLCore5;

	private JButton btnRunCore6;

	private JButton btnDLCore6;

	private JButton btnRunCore7;

	private JButton btnDLCore7;

	private JButton btnBatchRun;

	private JPanel panelDetail;

	private JLabel lblSubsystem;

	private JLabel lblSubsystemVal;

	private JLabel lblProcessor;

	private JLabel lblProcessorVal;

	private String currentIP;

	private String currentSub;
	private JCheckBox chkCore1;
	private JCheckBox chkCore2;
	private JCheckBox chkCore3;
	private JCheckBox chkCore4;
	private JCheckBox chkCore5;
	private JCheckBox chkCore6;
	private JCheckBox chkCore7;
	private JButton btnBatchDL;
	private JButton btnBrowseCore1;
	private JButton btnBrowseCore2;
	private JButton btnBrowseCore3;
	private JButton btnBrowseCore4;
	private JButton btnBrowseCore5;
	private JButton btnBrowseCore6;
	private JButton btnBrowseCore7;

	public static void main(String[] args) {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			JFrame f = new JFrame();

			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			f.setBounds(50, 10, 660, 400);

			f.getContentPane().setLayout(new BorderLayout());

			f.getContentPane().add(new VPX_ExecutionPanel(null, "", ""));

			f.setVisible(true);

		} catch (Exception e) {
			VPXLogger.updateError(e);
		}
	}

	/**
	 * Create the panel.
	 */
	public VPX_ExecutionPanel(VPX_ETHWindow prnt, String subsystem, String ip) {

		this.parent = prnt;

		this.currentSub = subsystem;

		this.currentIP = ip;

		processingWindow.setParent(parent);

		init();

		loadComponents();

		updateProcessorDetail();

	}

	private void init() {

		setLayout(new BorderLayout(0, 0));

		setPreferredSize(new Dimension(790, 594));
	}

	private void loadComponents() {

		basePanel = new JPanel();

		basePanel.setBorder(
				new TitledBorder(null, "Out File Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		add(basePanel, BorderLayout.CENTER);

		JPanel batchControlPanel = new JPanel();

		batchControlPanel.setPreferredSize(new Dimension(10, 50));

		add(batchControlPanel, BorderLayout.SOUTH);

		FlowLayout flowLayout = (FlowLayout) batchControlPanel.getLayout();

		flowLayout.setAlignment(FlowLayout.LEFT);

		btnBatchDL = new JButton("Batch Download");

		btnBatchDL.setEnabled(false);

		btnBatchDL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doBatchDL();

			}
		});

		batchControlPanel.add(btnBatchDL);

		btnBatchRun = new JButton("Batch Run");

		btnBatchRun.setEnabled(false);

		btnBatchRun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				startAllCores();

			}
		});

		batchControlPanel.add(btnBatchRun);

		basePanel.setLayout(new MigLayout("", "[490px][87px][33px][33px]",
				"[pref!,center][pref!,center][pref!,center][pref!,center][pref!,center][pref!,center][pref!,center][pref!,center][pref!,center][pref!,center][pref!,center][pref!,center][pref!,center][pref!,center]"));

		txtCore2 = new JTextField();

		txtCore2.setEnabled(false);

		basePanel.add(txtCore2, "cell 0 3,grow");

		txtCore2.setColumns(10);

		btnBrowseCore2 = new JButton(new BrowseAction("Browse", txtCore2));

		btnBrowseCore2.setEnabled(false);

		basePanel.add(btnBrowseCore2, "cell 1 3,alignx left,aligny top");

		btnRunCore2 = new JButton(VPXConstants.Icons.ICON_RUN);

		btnRunCore2.setEnabled(false);

		btnRunCore2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setStart(2);
			}
		});

		basePanel.add(btnRunCore2, "cell 2 3,alignx left,growy");

		btnDLCore2 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		btnDLCore2.setEnabled(false);

		btnDLCore2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doCoreDL(2, txtCore2.getText().trim());

			}
		});

		basePanel.add(btnDLCore2, "cell 3 3,alignx left,growy");

		txtCore1 = new JTextField();

		txtCore1.setEnabled(false);

		basePanel.add(txtCore1, "cell 0 1,grow");

		txtCore1.setColumns(10);

		btnBrowseCore1 = new JButton(new BrowseAction("Browse", txtCore1));

		btnBrowseCore1.setEnabled(false);

		basePanel.add(btnBrowseCore1, "cell 1 1,alignx left,aligny top");

		btnRunCore1 = new JButton(VPXConstants.Icons.ICON_RUN);

		btnRunCore1.setEnabled(false);

		btnRunCore1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setStart(1);
			}
		});

		basePanel.add(btnRunCore1, "cell 2 1,alignx left,growy");

		btnDLCore1 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		btnDLCore1.setEnabled(false);

		btnDLCore1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doCoreDL(1, txtCore1.getText().trim());

			}
		});

		basePanel.add(btnDLCore1, "cell 3 1,alignx left,growy");

		txtCore3 = new JTextField();

		txtCore3.setEnabled(false);

		basePanel.add(txtCore3, "cell 0 5,grow");

		txtCore3.setColumns(10);

		btnBrowseCore3 = new JButton(new BrowseAction("Browse", txtCore3));

		btnBrowseCore3.setEnabled(false);

		basePanel.add(btnBrowseCore3, "cell 1 5,alignx left,aligny top");

		btnRunCore3 = new JButton(VPXConstants.Icons.ICON_RUN);

		btnRunCore3.setEnabled(false);

		btnRunCore3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setStart(3);
			}
		});

		basePanel.add(btnRunCore3, "cell 2 5,alignx left,growy");

		btnDLCore3 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		btnDLCore3.setEnabled(false);

		btnDLCore3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doCoreDL(3, txtCore3.getText().trim());

			}
		});

		basePanel.add(btnDLCore3, "cell 3 5,alignx left,growy");

		txtCore4 = new JTextField();

		txtCore4.setEnabled(false);

		basePanel.add(txtCore4, "cell 0 7,grow");

		txtCore4.setColumns(10);

		btnBrowseCore4 = new JButton(new BrowseAction("Browse", txtCore4));

		btnBrowseCore4.setEnabled(false);

		basePanel.add(btnBrowseCore4, "cell 1 7,alignx left,aligny top");

		btnRunCore4 = new JButton(VPXConstants.Icons.ICON_RUN);

		btnRunCore4.setEnabled(false);

		btnRunCore4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setStart(4);
			}
		});

		basePanel.add(btnRunCore4, "cell 2 7,alignx left,growy");

		btnDLCore4 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		btnDLCore4.setEnabled(false);

		btnDLCore4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doCoreDL(4, txtCore4.getText().trim());

			}
		});

		basePanel.add(btnDLCore4, "cell 3 7,alignx left,growy");

		txtCore5 = new JTextField();

		txtCore5.setEnabled(false);

		basePanel.add(txtCore5, "cell 0 9,grow");

		txtCore5.setColumns(10);

		btnBrowseCore5 = new JButton(new BrowseAction("Browse", txtCore5));

		btnBrowseCore5.setEnabled(false);

		basePanel.add(btnBrowseCore5, "cell 1 9,alignx left,aligny top");

		btnRunCore5 = new JButton(VPXConstants.Icons.ICON_RUN);

		btnRunCore5.setEnabled(false);

		btnRunCore5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setStart(5);
			}
		});

		basePanel.add(btnRunCore5, "cell 2 9,alignx left,growy");

		btnDLCore5 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		btnDLCore5.setEnabled(false);

		btnDLCore5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doCoreDL(5, txtCore5.getText().trim());

			}
		});

		basePanel.add(btnDLCore5, "cell 3 9,alignx left,growy");

		txtCore6 = new JTextField();

		txtCore6.setEnabled(false);

		basePanel.add(txtCore6, "cell 0 11,grow");

		txtCore6.setColumns(10);

		btnBrowseCore6 = new JButton(new BrowseAction("Browse", txtCore6));

		btnBrowseCore6.setEnabled(false);

		basePanel.add(btnBrowseCore6, "cell 1 11,alignx left,aligny top");

		btnRunCore6 = new JButton(VPXConstants.Icons.ICON_RUN);

		btnRunCore6.setEnabled(false);

		btnRunCore6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setStart(6);
			}
		});

		basePanel.add(btnRunCore6, "cell 2 11,alignx left,growy");

		btnDLCore6 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		btnDLCore6.setEnabled(false);

		btnDLCore6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doCoreDL(6, txtCore6.getText().trim());

			}
		});

		basePanel.add(btnDLCore6, "cell 3 11,alignx left,growy");

		txtCore7 = new JTextField();

		txtCore7.setEnabled(false);

		basePanel.add(txtCore7, "cell 0 13,grow");

		txtCore7.setColumns(10);

		btnBrowseCore7 = new JButton(new BrowseAction("Browse", txtCore7));

		btnBrowseCore7.setEnabled(false);

		basePanel.add(btnBrowseCore7, "cell 1 13,alignx left,aligny top");

		btnRunCore7 = new JButton(VPXConstants.Icons.ICON_RUN);

		btnRunCore7.setEnabled(false);

		btnRunCore7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setStart(7);
			}
		});

		basePanel.add(btnRunCore7, "cell 2 13,alignx left,growy");

		btnDLCore7 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		btnDLCore7.setEnabled(false);

		btnDLCore7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doCoreDL(7, txtCore7.getText().trim());

			}
		});

		basePanel.add(btnDLCore7, "cell 3 13,alignx left,growy");

		chkCore1 = new JCheckBox("Core 1");

		chkCore1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setEnabledCoreComponents(1, chkCore1.isSelected());

			}
		});

		basePanel.add(chkCore1, "cell 0 0,alignx left,aligny top");

		chkCore2 = new JCheckBox("Core 2");

		chkCore2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setEnabledCoreComponents(2, chkCore2.isSelected());

			}
		});

		basePanel.add(chkCore2, "cell 0 2,alignx left,aligny top");

		chkCore3 = new JCheckBox("Core 3");

		chkCore3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setEnabledCoreComponents(3, chkCore3.isSelected());

			}
		});

		basePanel.add(chkCore3, "cell 0 4,alignx left,aligny top");

		chkCore4 = new JCheckBox("Core 4");

		chkCore4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setEnabledCoreComponents(4, chkCore4.isSelected());

			}
		});

		basePanel.add(chkCore4, "cell 0 6,alignx left,aligny top");

		chkCore5 = new JCheckBox("Core 5");

		chkCore5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setEnabledCoreComponents(5, chkCore5.isSelected());

			}
		});

		basePanel.add(chkCore5, "cell 0 8,alignx left,aligny top");

		chkCore6 = new JCheckBox("Core 6");

		chkCore6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setEnabledCoreComponents(6, chkCore6.isSelected());

			}
		});

		basePanel.add(chkCore6, "cell 0 10,alignx left,aligny top");

		chkCore7 = new JCheckBox("Core 7");

		chkCore7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setEnabledCoreComponents(7, chkCore7.isSelected());

			}
		});

		basePanel.add(chkCore7, "cell 0 12,alignx left,aligny top");

		panelDetail = new JPanel();

		panelDetail.setBorder(new TitledBorder(null, "Detail", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panelDetail.setPreferredSize(new Dimension(10, 50));

		add(panelDetail, BorderLayout.NORTH);

		panelDetail.setLayout(new MigLayout("", "[56px][22.00px,fill][47px][60px][][][][][][][][][][]", "[36px]"));

		lblSubsystem = new JLabel("Sub System");

		lblSubsystem.setHorizontalAlignment(SwingConstants.CENTER);

		panelDetail.add(lblSubsystem, "cell 0 0,alignx left,growy");

		lblSubsystemVal = new JLabel("Unlisted");

		panelDetail.add(lblSubsystemVal, "cell 2 0,grow");

		lblProcessor = new JLabel("Processor");

		panelDetail.add(lblProcessor, "cell 4 0,alignx left,growy");

		lblProcessorVal = new JLabel("172.17.1.25");

		panelDetail.add(lblProcessorVal, "cell 6 0,alignx left,growy");
	}

	private void doCoreDL(int core, String value) {

		String msg = "";

		if (VPXUtilities.isFileValid(value)) {

			String[] fileArray = new String[8];

			for (int j = 0; j < fileArray.length; j++) {

				fileArray[j] = "";

			}

			fileArray[core] = value;

			processingWindow.setFiles(fileArray);

			processingWindow.showProgress(currentIP);

		} else {

			msg = String.format("Core %d : %s is not valid file", core, value);

			JOptionPane.showMessageDialog(VPX_ExecutionPanel.this, msg, "Error", JOptionPane.ERROR_MESSAGE);

		}

	}

	private void setStart(int option) {

		doStart(option);

		JOptionPane.showMessageDialog(VPX_ExecutionPanel.this,
				String.format("%s core %d started successfully", currentIP, option), "Error",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void doStart(int option) {

		parent.sendExecuteCommand(currentIP, option, ATP.MSG_TYPE_EXECUTE_START);

		VPXLogger.updateLog(String.format("%s core %d started successfully", currentIP, option));
	}

	private void startAllCores() {

		String cores = "";

		try {

			if (chkCore1.isSelected()) {

				Thread.sleep(10);

				doStart(ATP.EXECUTE_CORE_1);

				cores = "1,";

			}

			if (chkCore2.isSelected()) {

				Thread.sleep(10);

				doStart(ATP.EXECUTE_CORE_2);

				cores = cores + "2,";

			}

			if (chkCore3.isSelected()) {

				Thread.sleep(10);

				doStart(ATP.EXECUTE_CORE_3);

				cores = cores + "3,";

			}

			if (chkCore4.isSelected()) {

				Thread.sleep(10);

				doStart(ATP.EXECUTE_CORE_4);

				cores = cores + "4,";

			}

			if (chkCore5.isSelected()) {

				Thread.sleep(10);

				doStart(ATP.EXECUTE_CORE_5);

				cores = cores + "5,";

			}

			if (chkCore6.isSelected()) {

				Thread.sleep(10);

				doStart(ATP.EXECUTE_CORE_6);

				cores = cores + "6,";

			}

			if (chkCore7.isSelected()) {

				Thread.sleep(10);

				doStart(ATP.EXECUTE_CORE_7);

				cores = cores + "7,";

			}

			if (cores.endsWith(",")) {

				cores = cores.substring(0, cores.length() - 1);
			}

			String msg = String.format("%s batch run for selected cores %s started successfully", currentIP, cores);

			JOptionPane.showMessageDialog(VPX_ExecutionPanel.this, msg, "Error", JOptionPane.INFORMATION_MESSAGE);

			VPXLogger.updateLog(msg);

		} catch (Exception e) {

			VPXLogger.updateError(e);

			e.printStackTrace();

		}

	}

	private void updateProcessorDetail() {

		lblProcessorVal.setText(currentIP);

		lblSubsystemVal.setText(currentSub);

	}

	private void setEnabledCoreComponents(int core, boolean option) {

		switch (core) {

		case ATP.EXECUTE_CORE_1:

			btnBrowseCore1.setEnabled(option);

			btnDLCore1.setEnabled(option);

			btnRunCore1.setEnabled(option);

			txtCore1.setEnabled(option);

			break;

		case ATP.EXECUTE_CORE_2:

			btnBrowseCore2.setEnabled(option);

			btnDLCore2.setEnabled(option);

			btnRunCore2.setEnabled(option);

			txtCore2.setEnabled(option);

			break;

		case ATP.EXECUTE_CORE_3:

			btnBrowseCore3.setEnabled(option);

			btnDLCore3.setEnabled(option);

			btnRunCore3.setEnabled(option);

			txtCore3.setEnabled(option);

			break;

		case ATP.EXECUTE_CORE_4:

			btnBrowseCore4.setEnabled(option);

			btnDLCore4.setEnabled(option);

			btnRunCore4.setEnabled(option);

			txtCore4.setEnabled(option);

			break;

		case ATP.EXECUTE_CORE_5:

			btnBrowseCore5.setEnabled(option);

			btnDLCore5.setEnabled(option);

			btnRunCore5.setEnabled(option);

			txtCore5.setEnabled(option);

			break;

		case ATP.EXECUTE_CORE_6:

			btnBrowseCore6.setEnabled(option);

			btnDLCore6.setEnabled(option);

			btnRunCore6.setEnabled(option);

			txtCore6.setEnabled(option);

			break;

		case ATP.EXECUTE_CORE_7:

			btnBrowseCore7.setEnabled(option);

			btnDLCore7.setEnabled(option);

			btnRunCore7.setEnabled(option);

			txtCore7.setEnabled(option);

			break;

		}

		if (chkCore1.isSelected() || chkCore2.isSelected() || chkCore3.isSelected() || chkCore4.isSelected()
				|| chkCore5.isSelected() || chkCore6.isSelected() || chkCore7.isSelected()) {

			btnBatchDL.setEnabled(true);

			btnBatchRun.setEnabled(true);

		} else {

			btnBatchDL.setEnabled(false);

			btnBatchRun.setEnabled(false);
		}

	}

	private void doBatchDL() {

		try {

			String msg = "Following selected files are not valid\n";

			boolean isCore1File = true, isCore2File = true, isCore3File = true, isCore4File = true, isCore5File = true,
					isCore6File = true, isCore7File = true;

			String[] fileArray = new String[8];

			for (int j = 0; j < fileArray.length; j++) {

				fileArray[j] = "";

			}

			if (chkCore1.isSelected()) {

				isCore1File = VPXUtilities.isFileValid(txtCore1.getText().trim());

			}

			if (chkCore2.isSelected()) {

				isCore2File = VPXUtilities.isFileValid(txtCore2.getText().trim());

			}

			if (chkCore3.isSelected()) {

				isCore3File = VPXUtilities.isFileValid(txtCore3.getText().trim());

			}

			if (chkCore4.isSelected()) {

				isCore4File = VPXUtilities.isFileValid(txtCore4.getText().trim());

			}

			if (chkCore5.isSelected()) {

				isCore5File = VPXUtilities.isFileValid(txtCore5.getText().trim());

			}

			if (chkCore6.isSelected()) {

				isCore6File = VPXUtilities.isFileValid(txtCore6.getText().trim());

			}

			if (chkCore7.isSelected()) {

				isCore7File = VPXUtilities.isFileValid(txtCore7.getText().trim());

			}

			if (isCore1File && isCore2File && isCore3File && isCore4File && isCore5File && isCore6File && isCore7File) {

				if (chkCore1.isSelected()) {

					fileArray[1] = txtCore1.getText().trim();

				}

				if (chkCore2.isSelected()) {

					fileArray[2] = txtCore2.getText().trim();

				}

				if (chkCore3.isSelected()) {

					fileArray[3] = txtCore3.getText().trim();

				}

				if (chkCore4.isSelected()) {

					fileArray[4] = txtCore4.getText().trim();

				}

				if (chkCore5.isSelected()) {

					fileArray[5] = txtCore5.getText().trim();

				}

				if (chkCore6.isSelected()) {

					fileArray[6] = txtCore6.getText().trim();

				}

				if (chkCore7.isSelected()) {

					fileArray[7] = txtCore7.getText().trim();

				}

				processingWindow.setFiles(fileArray);

				processingWindow.showProgress(currentIP);

			} else {

				if (!isCore1File) {

					msg = msg + "Core 1 : " + txtCore1.getText().trim() + "\n";

				}

				if (!isCore2File) {

					msg = msg + "Core 2 : " + txtCore2.getText().trim() + "\n";

				}

				if (!isCore3File) {

					msg = msg + "Core 3 : " + txtCore3.getText().trim() + "\n";

				}

				if (!isCore4File) {

					msg = msg + "Core 4 : " + txtCore4.getText().trim() + "\n";

				}

				if (!isCore5File) {

					msg = msg + "Core 5 : " + txtCore5.getText().trim() + "\n";

				}

				if (!isCore6File) {

					msg = msg + "Core 6 : " + txtCore6.getText().trim() + "\n";

				}

				if (!isCore7File) {

					msg = msg + "Core 7 : " + txtCore7.getText().trim() + "\n";

				}

				JOptionPane.showMessageDialog(VPX_ExecutionPanel.this, msg, "Error", JOptionPane.ERROR_MESSAGE);

				txtCore1.requestFocus();

			}

		} catch (Exception e2) {

			e2.printStackTrace();
		}
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

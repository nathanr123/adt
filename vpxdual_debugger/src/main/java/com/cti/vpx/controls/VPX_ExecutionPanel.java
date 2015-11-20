package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class VPX_ExecutionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5981154131918463867L;

	private final JFileChooser fileDialog = new JFileChooser();

	private final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Out Files", "out");

	private final FileNameExtensionFilter filterXml = new FileNameExtensionFilter("XML Files", "xml");

	private JTextField txtCore0;

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

	private JToggleButton btnRunCore0;

	private JToggleButton btnHaltCore0;

	private JButton btnDLCore0;

	private JToggleButton btnRunCore1;

	private JToggleButton btnHaltCore1;

	private JButton btnDLCore1;

	private JToggleButton btnRunCore2;

	private JToggleButton btnHaltCore2;

	private JButton btnDLCore2;

	private JToggleButton btnRunCore3;

	private JToggleButton btnHaltCore3;

	private JButton btnDLCore3;

	private JToggleButton btnRunCore4;

	private JToggleButton btnHaltCore4;

	private JButton btnDLCore4;

	private JToggleButton btnRunCore5;

	private JToggleButton btnHaltCore5;

	private JButton btnDLCore5;

	private JToggleButton btnRunCore6;

	private JToggleButton btnHaltCore6;

	private JButton btnDLCore6;

	private JToggleButton btnRunCore7;

	private JToggleButton btnHaltCore7;

	private JButton btnDLCore7;

	private JToggleButton btnBatchRun;

	private JToggleButton btnBatchHalt;
	private JPanel panelDetail;
	private JLabel lblSubsystem;
	private JLabel lblSubsystemVal;
	private JLabel lblProcessor;
	private JLabel lblProcessorVal;

	public static void main(String[] args) {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			JFrame f = new JFrame();

			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			f.setBounds(50, 10, 660, 400);

			f.getContentPane().setLayout(new BorderLayout());

			f.getContentPane().add(new VPX_ExecutionPanel(null));

			f.setVisible(true);

		} catch (Exception e) {
			VPXLogger.updateError(e);
		}
	}

	/**
	 * Create the panel.
	 */
	public VPX_ExecutionPanel(VPX_ETHWindow prnt) {

		this.parent = prnt;

		processingWindow.setParent(parent);

		init();

		loadComponents();

	}

	private void init() {

		setLayout(new BorderLayout(0, 0));

		setPreferredSize(new Dimension(790, 350));
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

		JButton btnBatchDL = new JButton("Batch Download");

		btnBatchDL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String[] fileArray = { "D:/outs/EXECUTE_OUT0.out", "D:/outs/EXECUTE_OUT1.out",
						"D:/outs/EXECUTE_OUT2.out", "D:/outs/EXECUTE_OUT3.out", "D:/outs/EXECUTE_OUT4.out",
						"D:/outs/EXECUTE_OUT5.out", "D:/outs/EXECUTE_OUT6.out", "D:/outs/EXECUTE_OUT7.out" };

				processingWindow.setFiles(fileArray);

				processingWindow.showProgress();

			}
		});

		
		batchControlPanel.add(btnBatchDL);

		btnBatchRun = new JToggleButton("Batch Run");

		
		batchControlPanel.add(btnBatchRun);

		btnBatchHalt = new JToggleButton("Batch Halt");

		batchControlPanel.add(btnBatchHalt);

		basePanel.setLayout(new MigLayout("", "[60px][490px][70px][26px][26px][26px]",
				"[pref!,grow,center][pref!,grow,center][pref!,grow,center][pref!,grow,center][pref!,grow,center][pref!,grow,center][pref!,grow,center][pref!,grow,center]"));

		JLabel lblCore2 = new JLabel("Core 2");

		basePanel.add(lblCore2, "cell 0 2,grow");

		txtCore2 = new JTextField();

		basePanel.add(txtCore2, "cell 1 2,grow");

		txtCore2.setColumns(10);

		JButton btnBrowseCore2 = new JButton(new BrowseAction("Browse", txtCore2));

		basePanel.add(btnBrowseCore2, "cell 2 2,grow");

		btnRunCore2 = new JToggleButton(VPXConstants.Icons.ICON_RUN);

		basePanel.add(btnRunCore2, "cell 3 2,grow");

		btnHaltCore2 = new JToggleButton(VPXConstants.Icons.ICON_PAUSE);

		basePanel.add(btnHaltCore2, "cell 4 2,grow");

		btnDLCore2 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		basePanel.add(btnDLCore2, "cell 5 2,grow");

		JLabel lblCore0 = new JLabel("Core 0");

		basePanel.add(lblCore0, "cell 0 0,grow");

		txtCore0 = new JTextField();

		basePanel.add(txtCore0, "cell 1 0,grow");

		txtCore0.setColumns(10);

		JButton btnBrowseCore0 = new JButton(new BrowseAction("Browse", txtCore0));

		basePanel.add(btnBrowseCore0, "cell 2 0,grow");

		btnRunCore0 = new JToggleButton(VPXConstants.Icons.ICON_RUN);

		basePanel.add(btnRunCore0, "cell 3 0,grow");

		btnHaltCore0 = new JToggleButton(VPXConstants.Icons.ICON_PAUSE);

		basePanel.add(btnHaltCore0, "cell 4 0,grow");

		btnDLCore0 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		basePanel.add(btnDLCore0, "cell 5 0,grow");

		JLabel lblCore1 = new JLabel("Core 1");

		basePanel.add(lblCore1, "cell 0 1,grow");

		txtCore1 = new JTextField();

		basePanel.add(txtCore1, "cell 1 1,grow");

		txtCore1.setColumns(10);

		JButton btnBrowseCore1 = new JButton(new BrowseAction("Browse", txtCore1));

		basePanel.add(btnBrowseCore1, "cell 2 1,grow");

		btnRunCore1 = new JToggleButton(VPXConstants.Icons.ICON_RUN);

		basePanel.add(btnRunCore1, "cell 3 1,grow");

		btnHaltCore1 = new JToggleButton(VPXConstants.Icons.ICON_PAUSE);

		basePanel.add(btnHaltCore1, "cell 4 1,grow");

		btnDLCore1 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		basePanel.add(btnDLCore1, "cell 5 1,grow");

		JLabel lblCore3 = new JLabel("Core 3");

		basePanel.add(lblCore3, "cell 0 3,grow");

		txtCore3 = new JTextField();

		basePanel.add(txtCore3, "cell 1 3,grow");

		txtCore3.setColumns(10);

		JButton btnBrowseCore3 = new JButton(new BrowseAction("Browse", txtCore3));

		basePanel.add(btnBrowseCore3, "cell 2 3,grow");

		btnRunCore3 = new JToggleButton(VPXConstants.Icons.ICON_RUN);

		basePanel.add(btnRunCore3, "cell 3 3,grow");

		btnHaltCore3 = new JToggleButton(VPXConstants.Icons.ICON_PAUSE);

		basePanel.add(btnHaltCore3, "cell 4 3,grow");

		btnDLCore3 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		basePanel.add(btnDLCore3, "cell 5 3,grow");

		JLabel lblCore4 = new JLabel("Core 4");

		basePanel.add(lblCore4, "cell 0 4,grow");

		txtCore4 = new JTextField();

		basePanel.add(txtCore4, "cell 1 4,grow");

		txtCore4.setColumns(10);

		JButton btnBrowseCore4 = new JButton(new BrowseAction("Browse", txtCore4));

		basePanel.add(btnBrowseCore4, "cell 2 4,grow");

		btnRunCore4 = new JToggleButton(VPXConstants.Icons.ICON_RUN);

		basePanel.add(btnRunCore4, "cell 3 4,grow");

		btnHaltCore4 = new JToggleButton(VPXConstants.Icons.ICON_PAUSE);

		basePanel.add(btnHaltCore4, "cell 4 4,grow");

		btnDLCore4 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		basePanel.add(btnDLCore4, "cell 5 4,grow");

		JLabel lblCore5 = new JLabel("Core 5");

		basePanel.add(lblCore5, "cell 0 5,grow");

		txtCore5 = new JTextField();

		basePanel.add(txtCore5, "cell 1 5,grow");

		txtCore5.setColumns(10);

		JButton btnBrowseCore5 = new JButton(new BrowseAction("Browse", txtCore5));

		basePanel.add(btnBrowseCore5, "cell 2 5,grow");

		btnRunCore5 = new JToggleButton(VPXConstants.Icons.ICON_RUN);

		basePanel.add(btnRunCore5, "cell 3 5,grow");

		btnHaltCore5 = new JToggleButton(VPXConstants.Icons.ICON_PAUSE);

		basePanel.add(btnHaltCore5, "cell 4 5,grow");

		btnDLCore5 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		basePanel.add(btnDLCore5, "cell 5 5,grow");

		JLabel lblCore6 = new JLabel("Core 6");

		basePanel.add(lblCore6, "cell 0 6,grow");

		txtCore6 = new JTextField();

		basePanel.add(txtCore6, "cell 1 6,grow");

		txtCore6.setColumns(10);

		JButton btnBrowseCore6 = new JButton(new BrowseAction("Browse", txtCore6));

		basePanel.add(btnBrowseCore6, "cell 2 6,grow");

		btnRunCore6 = new JToggleButton(VPXConstants.Icons.ICON_RUN);

		basePanel.add(btnRunCore6, "cell 3 6,grow");

		btnHaltCore6 = new JToggleButton(VPXConstants.Icons.ICON_PAUSE);

		basePanel.add(btnHaltCore6, "cell 4 6,grow");

		btnDLCore6 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		basePanel.add(btnDLCore6, "cell 5 6,grow");

		JLabel lblCore7 = new JLabel("Core 7");

		basePanel.add(lblCore7, "cell 0 7,grow");

		txtCore7 = new JTextField();

		basePanel.add(txtCore7, "cell 1 7,grow");

		txtCore7.setColumns(10);

		JButton btnBrowseCore7 = new JButton(new BrowseAction("Browse", txtCore7));

		basePanel.add(btnBrowseCore7, "cell 2 7,grow");

		btnRunCore7 = new JToggleButton(VPXConstants.Icons.ICON_RUN);

		basePanel.add(btnRunCore7, "cell 3 7,grow");

		btnHaltCore7 = new JToggleButton(VPXConstants.Icons.ICON_PAUSE);

		basePanel.add(btnHaltCore7, "cell 4 7,grow");

		btnDLCore7 = new JButton(VPXConstants.Icons.ICON_DOWNLOAD);

		basePanel.add(btnDLCore7, "cell 5 7,grow");
		
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

	private void setHalt(int option) {

	}

	private void setStart(int option) {

	}

	private void setResume(int option) {

	}

	private void setStop(int option) {

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

			if (isXMLFilter) {

				fileDialog.removeChoosableFileFilter(filterOut);

				fileDialog.addChoosableFileFilter(filterXml);
			} else {

				fileDialog.removeChoosableFileFilter(filterXml);

				fileDialog.addChoosableFileFilter(filterOut);
			}

			fileDialog.setAcceptAllFileFilterUsed(false);

			int returnVal = fileDialog.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				java.io.File file = fileDialog.getSelectedFile();

				jtf.setText(file.getPath());
			}
		}
	}
}

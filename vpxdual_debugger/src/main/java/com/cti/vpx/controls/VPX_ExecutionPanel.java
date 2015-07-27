package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.util.VPXUtilities;

public class VPX_ExecutionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5981154131918463867L;

	final JFileChooser fileDialog = new JFileChooser();

	final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Out Files", "out");

	final FileNameExtensionFilter filterXml = new FileNameExtensionFilter("XML Files", "xml");

	private JTextField txtOutFile_0;
	private JTextField txtMapFile_0;
	private JTextField txtOutFile_1;
	private JTextField txtMapFile_1;
	private JTextField txtOutFile_2;
	private JTextField txtMapFile_2;
	private JTextField txtOutFile_3;
	private JTextField txtMapFile_3;
	private JTextField txtOutFile_4;
	private JTextField txtMapFile_4;
	private JTextField txtOutFile_5;
	private JTextField txtMapFile_5;
	private JTextField txtOutFile_6;
	private JTextField txtMapFile_6;
	private JTextField txtOutFile_7;
	private JTextField txtMapFile_7;
	private JPanel basePanel;

	public static void main(String[] args) {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			JFrame f = new JFrame();

			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			f.setBounds(50, 10, 792, 882);

			f.getContentPane().setLayout(new BorderLayout());

			f.getContentPane().add(new VPX_ExecutionPanel());
			f.setVisible(true);
		} catch (Exception e) {

		}
	}

	/**
	 * Create the panel.
	 */
	public VPX_ExecutionPanel() {
		init();

		loadComponents();
		
	}

	private void init() {
		
		setLayout(new BorderLayout(0, 0));
		
		setPreferredSize(new Dimension(790,863));
	}

	private void loadComponents() {

		basePanel = new JPanel();

		basePanel.setBorder(new TitledBorder(null, "Out File Configuration", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		add(basePanel, BorderLayout.CENTER);

		basePanel.setLayout(null);

		createCore0Panel();

		createCore1Panel();

		createCore2Panel();

		createCore3Panel();

		createCore4Panel();

		createCore5Panel();

		createCore6Panel();

		createCore7Panel();

		createBatchControlPanel();

	}

	private void createCore0Panel() {

		JPanel core0Panel = new JPanel();

		core0Panel.setBorder(new TitledBorder(null, "Core 0", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		core0Panel.setBounds(10, 24, 767, 90);

		basePanel.add(core0Panel);

		core0Panel.setLayout(null);

		JLabel lblOut_0 = new JLabel("Out File");

		lblOut_0.setBounds(12, 20, 60, 26);

		core0Panel.add(lblOut_0);

		JLabel lblMap_0 = new JLabel("Map File");

		lblMap_0.setBounds(12, 54, 60, 26);

		core0Panel.add(lblMap_0);

		txtOutFile_0 = new JTextField();

		txtOutFile_0.setBounds(84, 20, 490, 26);

		core0Panel.add(txtOutFile_0);

		txtOutFile_0.setColumns(10);

		txtMapFile_0 = new JTextField();

		txtMapFile_0.setColumns(10);

		txtMapFile_0.setBounds(84, 54, 490, 26);

		core0Panel.add(txtMapFile_0);

		JButton btnOutBrowse_0 = new JButton(new BrowseAction("Browse", txtOutFile_0));

		btnOutBrowse_0.setBounds(586, 20, 70, 26);

		core0Panel.add(btnOutBrowse_0);

		JButton btnMapBrowse_0 = new JButton(new BrowseAction("Browse", txtMapFile_0, true));

		btnMapBrowse_0.setBounds(586, 54, 70, 26);

		core0Panel.add(btnMapBrowse_0);

		JButton btnRun_0 = new JButton(VPXUtilities.getImageIcon("image\\play.png", 18, 18));

		btnRun_0.setBounds(665, 54, 26, 26);

		core0Panel.add(btnRun_0);

		JButton btnHalt_0 = new JButton(VPXUtilities.getImageIcon("image\\pause.png", 18, 18));

		btnHalt_0.setBounds(698, 54, 26, 26);

		core0Panel.add(btnHalt_0);

		JButton btnDL_0 = new JButton(VPXUtilities.getImageIcon("image\\download1.png", 18, 18));

		btnDL_0.setBounds(731, 54, 26, 26);

		core0Panel.add(btnDL_0);
	}

	private void createCore1Panel() {

		JPanel core1Panel = new JPanel();

		core1Panel.setLayout(null);

		core1Panel.setBorder(new TitledBorder(null, "Core 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		core1Panel.setBounds(10, 122, 767, 90);

		basePanel.add(core1Panel);

		JLabel lblOut_1 = new JLabel("Out File");

		lblOut_1.setBounds(12, 17, 60, 26);

		core1Panel.add(lblOut_1);

		JLabel lblMap_1 = new JLabel("Map File");

		lblMap_1.setBounds(12, 51, 60, 26);

		core1Panel.add(lblMap_1);

		txtOutFile_1 = new JTextField();

		txtOutFile_1.setColumns(10);

		txtOutFile_1.setBounds(84, 17, 490, 26);

		core1Panel.add(txtOutFile_1);

		txtMapFile_1 = new JTextField();

		txtMapFile_1.setColumns(10);

		txtMapFile_1.setBounds(84, 51, 490, 26);

		core1Panel.add(txtMapFile_1);

		JButton btnOutBrowse_1 = new JButton(new BrowseAction("Browse", txtOutFile_1));

		btnOutBrowse_1.setBounds(586, 17, 70, 26);

		core1Panel.add(btnOutBrowse_1);

		JButton btnMapBrowse_1 = new JButton(new BrowseAction("Browse", txtMapFile_1, true));

		btnMapBrowse_1.setBounds(586, 51, 70, 26);

		core1Panel.add(btnMapBrowse_1);

		JButton btnRun_1 = new JButton(VPXUtilities.getImageIcon("image\\play.png", 18, 18));

		btnRun_1.setBounds(665, 51, 26, 26);

		core1Panel.add(btnRun_1);

		JButton btnHalt_1 = new JButton(VPXUtilities.getImageIcon("image\\pause.png", 18, 18));

		btnHalt_1.setBounds(698, 51, 26, 26);

		core1Panel.add(btnHalt_1);

		JButton btnDL_1 = new JButton(VPXUtilities.getImageIcon("image\\download1.png", 18, 18));

		btnDL_1.setBounds(731, 51, 26, 26);

		core1Panel.add(btnDL_1);
	}

	private void createCore2Panel() {

		JPanel core2Panel = new JPanel();

		core2Panel.setLayout(null);

		core2Panel.setBorder(new TitledBorder(null, "Core 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		core2Panel.setBounds(10, 220, 767, 90);

		basePanel.add(core2Panel);

		JLabel lblOut_2 = new JLabel("Out File");

		lblOut_2.setBounds(12, 17, 60, 26);

		core2Panel.add(lblOut_2);

		JLabel lblMap_2 = new JLabel("Map File");

		lblMap_2.setBounds(12, 51, 60, 26);

		core2Panel.add(lblMap_2);

		txtOutFile_2 = new JTextField();

		txtOutFile_2.setColumns(10);

		txtOutFile_2.setBounds(84, 17, 490, 26);

		core2Panel.add(txtOutFile_2);

		txtMapFile_2 = new JTextField();

		txtMapFile_2.setColumns(10);

		txtMapFile_2.setBounds(84, 51, 490, 26);

		core2Panel.add(txtMapFile_2);

		JButton btnOutBrowse_2 = new JButton(new BrowseAction("Browse", txtOutFile_2));

		btnOutBrowse_2.setBounds(586, 17, 70, 26);

		core2Panel.add(btnOutBrowse_2);

		JButton btnMapBrowse_2 = new JButton(new BrowseAction("Browse", txtMapFile_2, true));

		btnMapBrowse_2.setBounds(586, 51, 70, 26);

		core2Panel.add(btnMapBrowse_2);

		JButton btnRun_2 = new JButton(VPXUtilities.getImageIcon("image\\play.png", 18, 18));

		btnRun_2.setBounds(665, 51, 26, 26);

		core2Panel.add(btnRun_2);

		JButton btnHalt_2 = new JButton(VPXUtilities.getImageIcon("image\\pause.png", 18, 18));

		btnHalt_2.setBounds(698, 51, 26, 26);

		core2Panel.add(btnHalt_2);

		JButton btnDL_2 = new JButton(VPXUtilities.getImageIcon("image\\download1.png", 18, 18));

		btnDL_2.setBounds(731, 51, 26, 26);

		core2Panel.add(btnDL_2);
	}

	private void createCore3Panel() {

		JPanel core3panel = new JPanel();

		core3panel.setLayout(null);

		core3panel.setBorder(new TitledBorder(null, "Core 3", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		core3panel.setBounds(10, 318, 767, 90);

		basePanel.add(core3panel);

		JLabel lblOut_3 = new JLabel("Out File");

		lblOut_3.setBounds(12, 17, 60, 26);

		core3panel.add(lblOut_3);

		JLabel lblMap_3 = new JLabel("Map File");

		lblMap_3.setBounds(12, 51, 60, 26);

		core3panel.add(lblMap_3);

		txtOutFile_3 = new JTextField();

		txtOutFile_3.setColumns(10);

		txtOutFile_3.setBounds(84, 17, 490, 26);

		core3panel.add(txtOutFile_3);

		txtMapFile_3 = new JTextField();

		txtMapFile_3.setColumns(10);

		txtMapFile_3.setBounds(84, 51, 490, 26);

		core3panel.add(txtMapFile_3);

		JButton btnOutBrowse_3 = new JButton(new BrowseAction("Browse", txtOutFile_3));

		btnOutBrowse_3.setBounds(586, 17, 70, 26);

		core3panel.add(btnOutBrowse_3);

		JButton btnMapBrowse_3 = new JButton(new BrowseAction("Browse", txtMapFile_3, true));

		btnMapBrowse_3.setBounds(586, 51, 70, 26);

		core3panel.add(btnMapBrowse_3);

		JButton btnRun_3 = new JButton(VPXUtilities.getImageIcon("image\\play.png", 18, 18));

		btnRun_3.setBounds(665, 51, 26, 26);

		core3panel.add(btnRun_3);

		JButton btnHalt_3 = new JButton(VPXUtilities.getImageIcon("image\\pause.png", 18, 18));

		btnHalt_3.setBounds(698, 51, 26, 26);

		core3panel.add(btnHalt_3);

		JButton btnDL_3 = new JButton(VPXUtilities.getImageIcon("image\\download1.png", 18, 18));

		btnDL_3.setBounds(731, 51, 26, 26);

		core3panel.add(btnDL_3);
	}

	private void createCore4Panel() {

		JPanel core4Panel = new JPanel();

		core4Panel.setLayout(null);

		core4Panel.setBorder(new TitledBorder(null, "Core 4", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		core4Panel.setBounds(10, 416, 767, 90);

		basePanel.add(core4Panel);

		JLabel lblOut_4 = new JLabel("Out File");

		lblOut_4.setBounds(12, 17, 60, 26);

		core4Panel.add(lblOut_4);

		JLabel lblMap_4 = new JLabel("Map File");

		lblMap_4.setBounds(12, 51, 60, 26);

		core4Panel.add(lblMap_4);

		txtOutFile_4 = new JTextField();

		txtOutFile_4.setColumns(10);

		txtOutFile_4.setBounds(84, 17, 490, 26);

		core4Panel.add(txtOutFile_4);

		txtMapFile_4 = new JTextField();

		txtMapFile_4.setColumns(10);

		txtMapFile_4.setBounds(84, 51, 490, 26);

		core4Panel.add(txtMapFile_4);

		JButton btnOutBrowse_4 = new JButton(new BrowseAction("Browse", txtOutFile_4));

		btnOutBrowse_4.setBounds(586, 17, 70, 26);

		core4Panel.add(btnOutBrowse_4);

		JButton btnMapBrowse_4 = new JButton(new BrowseAction("Browse", txtMapFile_4, true));

		btnMapBrowse_4.setBounds(586, 51, 70, 26);

		core4Panel.add(btnMapBrowse_4);

		JButton btnRun_4 = new JButton(VPXUtilities.getImageIcon("image\\play.png", 18, 18));

		btnRun_4.setBounds(665, 51, 26, 26);

		core4Panel.add(btnRun_4);

		JButton btnHalt_4 = new JButton(VPXUtilities.getImageIcon("image\\pause.png", 18, 18));

		btnHalt_4.setBounds(698, 51, 26, 26);

		core4Panel.add(btnHalt_4);

		JButton btnDL_4 = new JButton(VPXUtilities.getImageIcon("image\\download1.png", 18, 18));

		btnDL_4.setBounds(731, 51, 26, 26);

		core4Panel.add(btnDL_4);
	}

	private void createCore5Panel() {

		JPanel core5Panel = new JPanel();

		core5Panel.setLayout(null);

		core5Panel.setBorder(new TitledBorder(null, "Core 5", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		core5Panel.setBounds(10, 514, 767, 90);

		basePanel.add(core5Panel);

		JLabel lblOut_5 = new JLabel("Out File");

		lblOut_5.setBounds(12, 17, 60, 26);

		core5Panel.add(lblOut_5);

		JLabel lblMap_5 = new JLabel("Map File");

		lblMap_5.setBounds(12, 51, 60, 26);

		core5Panel.add(lblMap_5);

		txtOutFile_5 = new JTextField();

		txtOutFile_5.setColumns(10);

		txtOutFile_5.setBounds(84, 17, 490, 26);

		core5Panel.add(txtOutFile_5);

		txtMapFile_5 = new JTextField();

		txtMapFile_5.setColumns(10);

		txtMapFile_5.setBounds(84, 51, 490, 26);

		core5Panel.add(txtMapFile_5);

		JButton btnOutBrowse_5 = new JButton(new BrowseAction("Browse", txtOutFile_5));

		btnOutBrowse_5.setBounds(586, 17, 70, 26);

		core5Panel.add(btnOutBrowse_5);

		JButton btnMapBrowse_5 = new JButton(new BrowseAction("Browse", txtMapFile_5, true));

		btnMapBrowse_5.setBounds(586, 51, 70, 26);

		core5Panel.add(btnMapBrowse_5);

		JButton btnRun_5 = new JButton(VPXUtilities.getImageIcon("image\\play.png", 18, 18));

		btnRun_5.setBounds(665, 51, 26, 26);

		core5Panel.add(btnRun_5);

		JButton btnHalt_5 = new JButton(VPXUtilities.getImageIcon("image\\pause.png", 18, 18));

		btnHalt_5.setBounds(698, 51, 26, 26);

		core5Panel.add(btnHalt_5);

		JButton btnDL_5 = new JButton(VPXUtilities.getImageIcon("image\\download1.png", 18, 18));

		btnDL_5.setBounds(731, 51, 26, 26);

		core5Panel.add(btnDL_5);
	}

	private void createCore6Panel() {

		JPanel core6Panel = new JPanel();

		core6Panel.setLayout(null);

		core6Panel.setBorder(new TitledBorder(null, "Core 6", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		core6Panel.setBounds(10, 612, 767, 90);

		basePanel.add(core6Panel);

		JLabel lblOut6 = new JLabel("Out File");

		lblOut6.setBounds(12, 17, 60, 26);

		core6Panel.add(lblOut6);

		JLabel lblMap6 = new JLabel("Map File");

		lblMap6.setBounds(12, 51, 60, 26);

		core6Panel.add(lblMap6);

		txtOutFile_6 = new JTextField();

		txtOutFile_6.setColumns(10);

		txtOutFile_6.setBounds(84, 17, 490, 26);

		core6Panel.add(txtOutFile_6);

		txtMapFile_6 = new JTextField();

		txtMapFile_6.setColumns(10);

		txtMapFile_6.setBounds(84, 51, 490, 26);

		core6Panel.add(txtMapFile_6);

		JButton btnOutBrowse_6 = new JButton(new BrowseAction("Browse", txtOutFile_6));

		btnOutBrowse_6.setBounds(586, 17, 70, 26);

		core6Panel.add(btnOutBrowse_6);

		JButton btnMapBrowse_6 = new JButton(new BrowseAction("Browse", txtMapFile_6, true));

		btnMapBrowse_6.setBounds(586, 51, 70, 26);

		core6Panel.add(btnMapBrowse_6);

		JButton btnRun_6 = new JButton(VPXUtilities.getImageIcon("image\\play.png", 18, 18));

		btnRun_6.setBounds(665, 51, 26, 26);

		core6Panel.add(btnRun_6);

		JButton btnHalt_6 = new JButton(VPXUtilities.getImageIcon("image\\pause.png", 18, 18));

		btnHalt_6.setBounds(698, 51, 26, 26);

		core6Panel.add(btnHalt_6);

		JButton btnDL_6 = new JButton(VPXUtilities.getImageIcon("image\\download1.png", 18, 18));

		btnDL_6.setBounds(731, 51, 26, 26);

		core6Panel.add(btnDL_6);
	}

	private void createCore7Panel() {

		JPanel core7Panel = new JPanel();

		core7Panel.setLayout(null);

		core7Panel.setBorder(new TitledBorder(null, "Core 7", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		core7Panel.setBounds(10, 710, 767, 90);

		basePanel.add(core7Panel);

		JLabel lblOut7 = new JLabel("Out File");

		lblOut7.setBounds(12, 17, 60, 26);

		core7Panel.add(lblOut7);

		JLabel lblMap7 = new JLabel("Map File");

		lblMap7.setBounds(12, 51, 60, 26);

		core7Panel.add(lblMap7);

		txtOutFile_7 = new JTextField();

		txtOutFile_7.setColumns(10);

		txtOutFile_7.setBounds(84, 17, 490, 26);

		core7Panel.add(txtOutFile_7);

		txtMapFile_7 = new JTextField();

		txtMapFile_7.setColumns(10);

		txtMapFile_7.setBounds(84, 51, 490, 26);

		core7Panel.add(txtMapFile_7);

		JButton btnOutBrowse_7 = new JButton(new BrowseAction("Browse", txtOutFile_7));

		btnOutBrowse_7.setBounds(586, 17, 70, 26);

		core7Panel.add(btnOutBrowse_7);

		JButton btnMapBrowse_7 = new JButton(new BrowseAction("Browse", txtMapFile_7, true));

		btnMapBrowse_7.setBounds(586, 51, 70, 26);

		core7Panel.add(btnMapBrowse_7);

		JButton btnRun_7 = new JButton(VPXUtilities.getImageIcon("image\\play.png", 18, 18));

		btnRun_7.setBounds(665, 51, 26, 26);

		core7Panel.add(btnRun_7);

		JButton btnHalt_7 = new JButton(VPXUtilities.getImageIcon("image\\pause.png", 18, 18));

		btnHalt_7.setBounds(698, 51, 26, 26);

		core7Panel.add(btnHalt_7);

		JButton btnDL_7 = new JButton(VPXUtilities.getImageIcon("image\\download1.png", 18, 18));

		btnDL_7.setBounds(731, 51, 26, 26);

		core7Panel.add(btnDL_7);

	}

	private void createBatchControlPanel() {

		JPanel batchControlPanel = new JPanel();

		FlowLayout flowLayout = (FlowLayout) batchControlPanel.getLayout();

		flowLayout.setAlignment(FlowLayout.LEFT);

		batchControlPanel.setBounds(10, 811, 767, 40);

		basePanel.add(batchControlPanel);

		JButton btnBatchDL = new JButton("Batch Download");

		btnBatchDL.setSize(111, 26);

		batchControlPanel.add(btnBatchDL);

		JButton btnBatchRun = new JButton("Batch Run");

		btnBatchRun.setSize(83, 26);

		batchControlPanel.add(btnBatchRun);

		JButton btnBatchHalt = new JButton("Batch Halt");

		btnBatchHalt.setSize(83, 26);

		batchControlPanel.add(btnBatchHalt);
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

package com.cti.vpx.controls.hex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

public class VPX_MemoryBrowserWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2851744765165677816L;
	private JPanel contentPane;
	private JTextField txtAutoRefresh;
	private JTextField txtMapFilePath;
	private JTextField txtMemoryAddres;
	private JTextField txtMemoryLength;
	private JTextField txtMemoryStride;
	private JComboBox<String> cmbSubSystem;
	private JComboBox<String> cmbProcessor;
	private JComboBox<String> cmbCores;
	private JCheckBox chkAutoRefresh;
	private JRadioButton radUseMap;
	private VPX_FilterComboBox cmbMemoryVariables;
	private JRadioButton radUserAddress;
	private JButton btnGo;
	private JButton btnNewWindow;
	private JButton btnClear;

	private final JFileChooser fileDialog = new JFileChooser();

	private final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Map Files", "map");

	private MemoryViewFilter memoryFilter;

	private VPX_ETHWindow parent;

	private int memoryBrowserID = -1;

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private MemoryViewFilter filter;

	private VPXSystem vpxSystem;

	private VPXSubSystem curProcFilter;

	private JLabel lblAddress;

	private JLabel lblMapFile;

	private JButton btnMapFileBrowse;
	private Map<String, String> memVariables;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPX_MemoryBrowserWindow frame = new VPX_MemoryBrowserWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VPX_MemoryBrowserWindow() {

		this.memoryBrowserID = 0;

		init();

		loadComponents();
	}

	public VPX_MemoryBrowserWindow(int id) {

		this.memoryBrowserID = id;

		init();

		loadComponents();
	}

	public VPX_MemoryBrowserWindow(MemoryViewFilter filter) {

		this.memoryFilter = filter;

		init();

		loadComponents();
	}

	private void init() {

		setTitle("Memory Browser " + memoryBrowserID);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		addWindowListener(this);

		setBounds(100, 100, 850, 650);
	}

	private void loadComponents() {

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));

		createFilterPanel();

		createHexPanel();
	}

	private void createFilterPanel() {

		JPanel filterPanel = new JPanel();

		filterPanel.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		filterPanel.setPreferredSize(new Dimension(10, 170));

		contentPane.add(filterPanel, BorderLayout.NORTH);

		filterPanel.setLayout(new GridLayout(4, 1, 0, 0));

		JPanel subSystemPanel = new JPanel();

		filterPanel.add(subSystemPanel);

		subSystemPanel.setLayout(new MigLayout("",
				"[46px][100px][46px][100px,fill][46px][100px,fill][97px][86px][46px][grow,fill]", "[23px,grow,fill]"));

		JLabel lblSubSystem = new JLabel("Sub System");

		subSystemPanel.add(lblSubSystem, "cell 0 0,alignx center,aligny center");

		cmbSubSystem = new JComboBox<String>();

		cmbSubSystem.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbSubSystem)) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						loadProcessorsFilter();
					}
				}
			}
		});

		cmbSubSystem.setPreferredSize(new Dimension(120, 20));

		subSystemPanel.add(cmbSubSystem, "cell 1 0,growx,aligny center");

		JLabel lblProcessors = new JLabel("Processors");

		subSystemPanel.add(lblProcessors, "cell 2 0,alignx center,aligny center");

		cmbProcessor = new JComboBox<String>();
		cmbProcessor.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbProcessor)) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						loadCoresFilter();
					}
				}
			}
		});

		cmbProcessor.setPreferredSize(new Dimension(120, 20));

		subSystemPanel.add(cmbProcessor, "cell 3 0,alignx left,aligny center");

		JLabel lblCore = new JLabel("Cores");

		subSystemPanel.add(lblCore, "cell 4 0,alignx center,aligny center");

		cmbCores = new JComboBox<String>();

		cmbCores.setPreferredSize(new Dimension(120, 20));

		subSystemPanel.add(cmbCores, "cell 5 0,alignx left,aligny center");

		chkAutoRefresh = new JCheckBox("Auto Refresh in every");

		chkAutoRefresh.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				txtAutoRefresh.setEnabled(chkAutoRefresh.isSelected());
			}
		});

		subSystemPanel.add(chkAutoRefresh, "cell 6 0,alignx center,aligny top");

		txtAutoRefresh = new JTextField();

		txtAutoRefresh.setEnabled(false);

		subSystemPanel.add(txtAutoRefresh, "cell 7 0,alignx left,aligny center");

		txtAutoRefresh.setColumns(10);

		JLabel lblMins = new JLabel("Mins");

		subSystemPanel.add(lblMins, "cell 8 0,alignx left,aligny center");

		JLabel lblEmpty = new JLabel("");

		subSystemPanel.add(lblEmpty, "cell 9 0");

		JPanel mapPanel = new JPanel();

		filterPanel.add(mapPanel);

		mapPanel.setLayout(new MigLayout("", "[109px][46px][406px,grow,fill][89px][120px]", "[23px]"));

		radUseMap = new JRadioButton("Use Map File");

		radUseMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				enableMemoryFields();
			}
		});

		buttonGroup.add(radUseMap);

		mapPanel.add(radUseMap, "cell 0 0,alignx left,aligny top");

		lblMapFile = new JLabel("Map File");

		lblMapFile.setEnabled(false);

		mapPanel.add(lblMapFile, "cell 1 0,alignx left,aligny center");

		txtMapFilePath = new JTextField();

		txtMapFilePath.setEnabled(false);

		txtMapFilePath.setPreferredSize(new Dimension(200, 20));

		mapPanel.add(txtMapFilePath, "cell 2 0,alignx left,aligny center");

		txtMapFilePath.setColumns(50);

		btnMapFileBrowse = new JButton("Browse");

		btnMapFileBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fileDialog.addChoosableFileFilter(filterOut);

				fileDialog.setAcceptAllFileFilterUsed(false);

				int returnVal = fileDialog.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fileDialog.getSelectedFile();

					loadMemoryVariables(file.getAbsolutePath());

				}

			}
		});

		btnMapFileBrowse.setEnabled(false);

		mapPanel.add(btnMapFileBrowse, "cell 3 0,alignx left,aligny top");

		cmbMemoryVariables = new VPX_FilterComboBox();

		cmbMemoryVariables.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getSource().equals(cmbMemoryVariables)) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

						fillMemoryAddress();

					}
				}

			}
		});

		cmbMemoryVariables.setEnabled(false);

		cmbMemoryVariables.setMinimumSize(new Dimension(250, 20));

		mapPanel.add(cmbMemoryVariables, "cell 4 0,alignx left,aligny center");

		JPanel memoryAddressPanel = new JPanel();

		filterPanel.add(memoryAddressPanel);

		memoryAddressPanel.setLayout(new MigLayout("", "[109px][46px][206px,grow,fill][46px][126px][46px][126px]",

		"[23px]"));

		radUserAddress = new JRadioButton("Use Direct Memory Address");

		radUserAddress.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				enableMemoryFields();
			}
		});

		radUserAddress.setSelected(true);

		buttonGroup.add(radUserAddress);

		memoryAddressPanel.add(radUserAddress, "cell 0 0,alignx left,aligny top");

		lblAddress = new JLabel("Address");

		memoryAddressPanel.add(lblAddress, "cell 1 0,alignx left,aligny center");

		txtMemoryAddres = new JTextField();

		txtMemoryAddres.setPreferredSize(new Dimension(20, 20));

		txtMemoryAddres.setColumns(25);

		memoryAddressPanel.add(txtMemoryAddres, "cell 2 0,alignx left,aligny center");

		JLabel lblLength = new JLabel("Length");

		memoryAddressPanel.add(lblLength, "cell 3 0,alignx left,aligny center");

		txtMemoryLength = new JTextField();

		txtMemoryLength.setPreferredSize(new Dimension(20, 20));

		txtMemoryLength.setColumns(15);

		memoryAddressPanel.add(txtMemoryLength, "cell 4 0,alignx left,aligny center");

		JLabel lblStride = new JLabel("Stride");

		memoryAddressPanel.add(lblStride, "cell 5 0,alignx left,aligny center");

		txtMemoryStride = new JTextField();

		txtMemoryStride.setPreferredSize(new Dimension(20, 20));

		txtMemoryStride.setColumns(15);

		memoryAddressPanel.add(txtMemoryStride, "cell 6 0,alignx left,aligny center");

		JPanel controlsPanel = new JPanel();

		filterPanel.add(controlsPanel);

		controlsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		btnGo = new JButton("Go");

		btnGo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				createFilters();

			}
		});

		controlsPanel.add(btnGo);

		btnNewWindow = new JButton("New Window");

		btnNewWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				createFilters(true);

				parent.openMemoryBrowser(filter);

			}
		});
		controlsPanel.add(btnNewWindow);

		btnClear = new JButton("Clear");

		controlsPanel.add(btnClear);
	}

	private void createHexPanel() {

		JPanel hexContentPanel = new JPanel();

		hexContentPanel.setLayout(new BorderLayout());

		hexContentPanel.add(new HexEditorPanel(), BorderLayout.CENTER);

		contentPane.add(hexContentPanel, BorderLayout.CENTER);
	}

	private void loadMemoryVariables(String fileName) {

		cmbMemoryVariables.removeAllItems();

		memVariables = VPXUtilities.getMemoryAddressVariables(fileName);

		cmbMemoryVariables.addMemoryVariables(memVariables);

		cmbMemoryVariables.setSelectedIndex(0);

	}

	private void fillMemoryAddress() {

		if (memVariables.containsKey(cmbMemoryVariables.getSelectedItem().toString())) {
			txtMemoryAddres.setText(memVariables.get(cmbMemoryVariables.getSelectedItem().toString()));
		}
	}

	private void createFilters() {

		createFilters(true);

		memoryFilter = filter;

	}

	private void createFilters(boolean isCreateNewFilter) {

		filter = null;

		filter = new MemoryViewFilter();

		filter.setSubsystem(cmbSubSystem.getSelectedItem().toString());

		filter.setProcessor(cmbProcessor.getSelectedItem().toString());

		if (cmbCores.getItemCount() > 0) {
			filter.setCore(cmbCores.getSelectedItem().toString());

		} else {
			filter.setCore("");
		}

		filter.setAutoRefresh(chkAutoRefresh.isSelected());

		filter.setTimeinterval(Integer.parseInt(txtAutoRefresh.getText().trim()));

		filter.setUseMapFile(radUseMap.isSelected());

		filter.setMapPath(txtMapFilePath.getText());

		if (cmbMemoryVariables.getItemCount() > 0) {

			filter.setMemoryName(cmbMemoryVariables.getSelectedItem().toString());

		} else {
			filter.setMemoryName("");
		}

		filter.setDirectMemory(radUserAddress.isSelected());

		filter.setMemoryAddress(txtMemoryAddres.getText());

		filter.setMemoryLength(txtMemoryLength.getText());

		filter.setMemoryStride(txtMemoryStride.getText());

	}

	public void showMemoryBrowser() {

		loadFilters();

		applyFilters();

		setVisible(true);
	}

	public void reloadSubsystems() {

		int sub = cmbSubSystem.getSelectedIndex();

		int proc = cmbProcessor.getSelectedIndex();

		int core = 0;

		if (cmbCores.getItemCount() > 0) {

			core = cmbCores.getSelectedIndex();
		}

		loadFilters();

		cmbSubSystem.setSelectedIndex(sub);

		cmbProcessor.setSelectedIndex(proc);

		if (cmbCores.getItemCount() > 0) {

			cmbCores.setSelectedIndex(core);
		}

	}

	private void enableMemoryFields() {

		boolean isMap = radUseMap.isSelected();

		boolean isDirect = radUserAddress.isSelected();

		lblMapFile.setEnabled(isMap);

		txtMapFilePath.setEnabled(isMap);

		btnMapFileBrowse.setEnabled(isMap);

		cmbMemoryVariables.setEnabled(isMap);

		lblAddress.setEnabled(isDirect);

		txtMemoryAddres.setEnabled(isDirect);

	}

	private void applyFilters() {

		for (int i = 0; i < cmbSubSystem.getItemCount(); i++) {

			if (memoryFilter.getSubsystem().equals(cmbSubSystem.getItemAt(i))) {

				cmbSubSystem.setSelectedIndex(i);

				break;
			}
		}

		for (int i = 0; i < cmbProcessor.getItemCount(); i++) {

			if (memoryFilter.getProcessor().equals(cmbProcessor.getItemAt(i))) {

				cmbProcessor.setSelectedIndex(i);

				break;
			}
		}

		for (int i = 0; i < cmbCores.getItemCount(); i++) {

			if (memoryFilter.getCore().equals(cmbCores.getItemAt(i))) {

				cmbCores.setSelectedIndex(i);

				break;
			}
		}

		chkAutoRefresh.setSelected(memoryFilter.isAutoRefresh());

		txtAutoRefresh.setEnabled(chkAutoRefresh.isSelected());

		txtAutoRefresh.setText(memoryFilter.getTimeinterval() + "");

		radUseMap.setSelected(memoryFilter.isUseMapFile());

		radUserAddress.setSelected(memoryFilter.isDirectMemory());

		txtMapFilePath.setText(memoryFilter.getMapPath());

		cmbMemoryVariables.setSelectedItem(memoryFilter.getMemoryName());

		txtMemoryAddres.setText(memoryFilter.getMemoryAddress());

		txtMemoryLength.setText(memoryFilter.getMemoryLength());

		txtMemoryStride.setText(memoryFilter.getMemoryStride());

		enableMemoryFields();

	}

	private void loadFilters() {

		vpxSystem = VPXSessionManager.getVPXSystem();

		cmbSubSystem.removeAllItems();

		cmbSubSystem.addItem("All Susb Systems");

		List<VPXSubSystem> subsystem = vpxSystem.getSubsystem();

		for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			cmbSubSystem.addItem(vpxSubSystem.getSubSystem());
		}
	}

	private void loadProcessorsFilter() {

		List<VPXSubSystem> subsystem = vpxSystem.getSubsystem();

		cmbProcessor.removeAllItems();

		cmbProcessor.addItem("All Processors");

		for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			if (vpxSubSystem.getSubSystem().equals(cmbSubSystem.getSelectedItem().toString())) {

				curProcFilter = vpxSubSystem;

				cmbProcessor.addItem(vpxSubSystem.getIpP2020());

				cmbProcessor.addItem(vpxSubSystem.getIpDSP1());

				cmbProcessor.addItem(vpxSubSystem.getIpDSP2());

				break;
			}

		}
	}

	private void loadCoresFilter() {

		cmbCores.removeAllItems();

		if (curProcFilter != null) {

			if (cmbProcessor.getSelectedItem().toString().equals(curProcFilter.getIpP2020())) {

				cmbCores.setEnabled(false);

			} else {

				cmbCores.setEnabled(true);

				cmbCores.addItem("All Cores");

				for (int i = 0; i < 8; i++) {
					cmbCores.addItem(String.format("Core %s", i));
				}

				cmbCores.setSelectedIndex(0);
			}
		}
	}

	public MemoryViewFilter getMemoryFilter() {
		return memoryFilter;
	}

	public void setMemoryFilter(MemoryViewFilter memoryFilter) {
		this.memoryFilter = memoryFilter;
	}

	public int getMemoryBrowserID() {
		return memoryBrowserID;
	}

	public void setMemoryBrowserID(int memoryBrowserID) {
		this.memoryBrowserID = memoryBrowserID;
	}

	public void setParent(VPX_ETHWindow prnt) {
		this.parent = prnt;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		parent.reindexMemoryBrowserIndex();

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}

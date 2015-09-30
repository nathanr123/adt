package com.cti.vpx.controls.hex;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import com.cti.vpx.command.ATP;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

public class VPX_MemoryBrowserWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2851744765165677816L;

	private int MINUTE = 5 * 1000;

	private JPanel contentPane;

	private JSpinner spinAutoRefresh;

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

	private SpinnerNumberModel periodicitySpinnerModel;

	private final JFileChooser fileDialog = new JFileChooser();

	private final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Map Files", "map");

	private MemoryViewFilter memoryFilter;

	private VPX_ETHWindow parent;

	private int memoryBrowserID = -1;

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private MemoryViewFilter filter;

	private VPXSystem vpxSystem;

	private Thread autoRefreshThread = null;

	private int currentThreadSleepTime;

	private JLabel lblAddress;

	private JLabel lblMapFile;

	private JButton btnMapFileBrowse;

	private Map<String, String> memVariables;

	private HexEditorPanel hexPanel;

	private boolean isAutoRefresh = false;

	private JDialog dialog;

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

		setTitle("Memory Browser " + (memoryBrowserID + 1));

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

				spinAutoRefresh.setEnabled(chkAutoRefresh.isSelected());
			}
		});

		chkAutoRefresh.setSelected(false);

		subSystemPanel.add(chkAutoRefresh, "cell 6 0,alignx center,aligny top");

		periodicitySpinnerModel = new SpinnerNumberModel(1, 1, 10, 1);

		spinAutoRefresh = new JSpinner(periodicitySpinnerModel);

		JFormattedTextField txt = ((JSpinner.NumberEditor) spinAutoRefresh.getEditor()).getTextField();

		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

		spinAutoRefresh.setEnabled(false);

		subSystemPanel.add(spinAutoRefresh, "cell 7 0,growx,aligny center");

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

		txtMemoryAddres.setText("0xA0000000");

		txtMemoryAddres.setPreferredSize(new Dimension(20, 20));

		txtMemoryAddres.setColumns(25);

		memoryAddressPanel.add(txtMemoryAddres, "cell 2 0,alignx left,aligny center");

		JLabel lblLength = new JLabel("Length");

		memoryAddressPanel.add(lblLength, "cell 3 0,alignx left,aligny center");

		txtMemoryLength = new JTextField();

		txtMemoryLength.setText("100");

		txtMemoryLength.setPreferredSize(new Dimension(20, 20));

		txtMemoryLength.setColumns(15);

		memoryAddressPanel.add(txtMemoryLength, "cell 4 0,alignx left,aligny center");

		JLabel lblStride = new JLabel("Stride");

		lblStride.setVisible(false);

		memoryAddressPanel.add(lblStride, "cell 5 0,alignx left,aligny center");

		txtMemoryStride = new JTextField();

		txtMemoryStride.setVisible(false);

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

				doReadMemory();

			}
		});

		controlsPanel.add(btnGo);

		btnNewWindow = new JButton("New Window");

		btnNewWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// createFilters(true);

				parent.openMemoryBrowser(null);

			}
		});
		controlsPanel.add(btnNewWindow);

		btnClear = new JButton("Clear");

		controlsPanel.add(btnClear);
	}

	private void createHexPanel() {

		JPanel hexContentPanel = new JPanel();

		hexContentPanel.setLayout(new BorderLayout());

		hexPanel = new HexEditorPanel(this);

		hexContentPanel.add(hexPanel, BorderLayout.CENTER);

		contentPane.add(hexContentPanel, BorderLayout.CENTER);
	}

	private void loadMemoryVariables(String fileName) {

		cmbMemoryVariables.removeAllItems();

		// cmbMemoryVariables.addItem("");

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

			filter.setCore(String.valueOf(cmbCores.getSelectedIndex() - 1));

		} else {

			filter.setCore("0");
		}

		filter.setAutoRefresh(chkAutoRefresh.isSelected());

		filter.setTimeinterval(Integer.parseInt(spinAutoRefresh.getValue().toString().trim()));

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

		filter.setMemoryBrowserID(memoryBrowserID);

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

		if (memoryFilter != null) {

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

			spinAutoRefresh.setEnabled(chkAutoRefresh.isSelected());

			spinAutoRefresh.setValue(memoryFilter.getTimeinterval());

			radUseMap.setSelected(memoryFilter.isUseMapFile());

			radUserAddress.setSelected(memoryFilter.isDirectMemory());

			txtMapFilePath.setText(memoryFilter.getMapPath());

			cmbMemoryVariables.setSelectedItem(memoryFilter.getMemoryName());

			txtMemoryAddres.setText(memoryFilter.getMemoryAddress());

			txtMemoryLength.setText(memoryFilter.getMemoryLength());

			txtMemoryStride.setText(memoryFilter.getMemoryStride());

			enableMemoryFields();
		}

	}

	private void loadFilters() {

		vpxSystem = VPXSessionManager.getVPXSystem();

		cmbSubSystem.removeAllItems();

		cmbSubSystem.addItem("Select SubSystem");

		List<VPXSubSystem> subsystem = vpxSystem.getSubsystem();

		if (subsystem.size() > 0) {

			for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				cmbSubSystem.addItem(vpxSubSystem.getSubSystem());
			}
		}

		List<Processor> unlist = vpxSystem.getUnListed();

		if (unlist.size() > 0) {

			cmbSubSystem.addItem(VPXConstants.VPXUNLIST);
		}
	}

	private void loadProcessorsFilter() {

		cmbProcessor.removeAllItems();

		cmbProcessor.addItem("Select Processor");

		if (cmbSubSystem.getSelectedIndex() > 0) {

			VPXSubSystem curProcFilter = vpxSystem.getSubSystemByName(cmbSubSystem.getSelectedItem().toString());

			List<VPXSubSystem> s = vpxSystem.getSubsystem();

			for (Iterator<VPXSubSystem> iterator = s.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getSubSystem().equals(cmbSubSystem.getSelectedItem().toString())) {

					curProcFilter = vpxSubSystem;

					// cmbProcessor.addItem(vpxSubSystem.getIpP2020());

					cmbProcessor.addItem(vpxSubSystem.getIpDSP1());

					cmbProcessor.addItem(vpxSubSystem.getIpDSP2());

					break;

				}

			}

			if (curProcFilter == null) {

				List<Processor> p = vpxSystem.getUnListed();

				for (Iterator<Processor> iterator = p.iterator(); iterator.hasNext();) {

					Processor processor = iterator.next();

					if (!processor.getName().contains("P2020")) {

						cmbProcessor.addItem(processor.getiP_Addresses());
					}
				}

			}
		}

	}

	private void loadCoresFilter() {

		cmbCores.removeAllItems();

		cmbCores.addItem("Select Core");

		if (cmbSubSystem.getSelectedIndex() > 0 && cmbProcessor.getSelectedIndex() > 0) {

			String subSystem = cmbSubSystem.getSelectedItem().toString();

			String proc = cmbProcessor.getSelectedItem().toString();

			if (subSystem.equals(VPXConstants.VPXUNLIST)) {

				List<Processor> s = vpxSystem.getUnListed();

				if (s.size() > 0) {

					for (Iterator<Processor> iterator = s.iterator(); iterator.hasNext();) {

						Processor processor = iterator.next();

						if (processor.getiP_Addresses().equals(proc)) {

							if (processor.getName().contains("P2020")) {

								cmbCores.setEnabled(false);

								break;

							} else {

								cmbCores.setEnabled(true);

								for (int i = 0; i < 8; i++) {
									cmbCores.addItem(String.format("Core %s", i));
								}

								cmbCores.setSelectedIndex(0);

								break;
							}

						}

					}
				}

			} else {

				VPXSubSystem curProcFilter = vpxSystem.getSubSystemByName(subSystem);

				if (proc.equals(curProcFilter.getIpP2020())) {

					cmbCores.setEnabled(false);

				} else {

					cmbCores.setEnabled(true);

					for (int i = 0; i < 8; i++) {
						cmbCores.addItem(String.format("Core %s", i));
					}

					cmbCores.setSelectedIndex(0);
				}

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

	public String getSelectedProcessor() {
		return cmbProcessor.getSelectedItem().toString();
	}

	public String getSelectedCore() {
		return cmbCores.getSelectedItem().toString();
	}

	private void doReadMemory() {

		boolean isSelectedProcessorValid = isSelectedProcessorValid();

		boolean isSelectedCoreValid = isSelectedCoreValid();

		boolean isAddressValid = isAddressValid();

		boolean isLengthValid = isLengthValid();

		if (isSelectedProcessorValid && isSelectedCoreValid && isAddressValid && isLengthValid) {

			createFilters();

			parent.readMemory(memoryFilter);

			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {

					showLoadProcessingWindow("Memory loading...");

				}
			});

			th.start();

		} else {
			if (!isSelectedProcessorValid) {

				JOptionPane.showMessageDialog(VPX_MemoryBrowserWindow.this, "Please select processor", "Validation",
						JOptionPane.ERROR_MESSAGE);

			} else if (!isSelectedCoreValid) {

				JOptionPane.showMessageDialog(VPX_MemoryBrowserWindow.this, "Please select core", "Validation",
						JOptionPane.ERROR_MESSAGE);

			} else if (!isAddressValid) {

				JOptionPane.showMessageDialog(VPX_MemoryBrowserWindow.this, getAddressErrorMessage(), "Validation",
						JOptionPane.ERROR_MESSAGE);

			} else if (!isLengthValid) {

				JOptionPane.showMessageDialog(VPX_MemoryBrowserWindow.this,
						"Length is invalid.\nEnter valid length( between 1 to " + (ATP.DEFAULTBUFFERSIZE * 10) + " )!.",
						"Validation", JOptionPane.ERROR_MESSAGE);

			}
		}

		if (chkAutoRefresh.isSelected()) {

			currentThreadSleepTime = Integer.valueOf(spinAutoRefresh.getValue().toString()) * MINUTE;

			isAutoRefresh = true;

			startAutoRefreshThread();

		} else {

			isAutoRefresh = false;
		}
	}

	private String getAddressErrorMessage() {

		String ret = "";

		if (cmbCores.getSelectedIndex() > 0) {

			switch (cmbCores.getSelectedIndex() - 1) {

			case 0:

				ret = String.format(
						"Addres is invalid for Core - %d.\nAddress Range between ( 0x%08X and 0x%08X ) or (  0x%08X and 0x%08X  )",
						(cmbCores.getSelectedIndex() - 1), ATP.CORE0_DDR3_START_ADDRESS, ATP.CORE0_DDR3_END_ADDRESS,
						ATP.C0_L2SRAM_START_ADDRESS, ATP.C0_L2SRAM_END_ADDRESS);

				break;

			case 1:

				ret = String.format(
						"Addres is invalid for Core - %d.\nAddress Range between ( 0x%08X and 0x%08X ) or (  0x%08X and 0x%08X  )",
						(cmbCores.getSelectedIndex() - 1), ATP.CORE1_DDR3_START_ADDRESS, ATP.CORE1_DDR3_END_ADDRESS,
						ATP.C1_L2SRAM_START_ADDRESS, ATP.C1_L2SRAM_END_ADDRESS);

				break;

			case 2:

				ret = String.format(
						"Addres is invalid for Core - %d.\nAddress Range between ( 0x%08X and 0x%08X ) or (  0x%08X and 0x%08X  )",
						(cmbCores.getSelectedIndex() - 1), ATP.CORE2_DDR3_START_ADDRESS, ATP.CORE2_DDR3_END_ADDRESS,
						ATP.C2_L2SRAM_START_ADDRESS, ATP.C2_L2SRAM_END_ADDRESS);

				break;

			case 3:

				ret = String.format(
						"Addres is invalid for Core - %d.\nAddress Range between ( 0x%08X and 0x%08X ) or (  0x%08X and 0x%08X  )",
						(cmbCores.getSelectedIndex() - 1), ATP.CORE3_DDR3_START_ADDRESS, ATP.CORE3_DDR3_END_ADDRESS,
						ATP.C3_L2SRAM_START_ADDRESS, ATP.C3_L2SRAM_END_ADDRESS);

				break;

			case 4:

				ret = String.format(
						"Addres is invalid for Core - %d.\nAddress Range between ( 0x%08X and 0x%08X ) or (  0x%08X and 0x%08X  )",
						(cmbCores.getSelectedIndex() - 1), ATP.CORE4_DDR3_START_ADDRESS, ATP.CORE4_DDR3_END_ADDRESS,
						ATP.C4_L2SRAM_START_ADDRESS, ATP.C4_L2SRAM_END_ADDRESS);

				break;

			case 5:

				ret = String.format(
						"Addres is invalid for Core - %d.\nAddress Range between ( 0x%08X and 0x%08X ) or (  0x%08X and 0x%08X  )",
						(cmbCores.getSelectedIndex() - 1), ATP.CORE5_DDR3_START_ADDRESS, ATP.CORE5_DDR3_END_ADDRESS,
						ATP.C5_L2SRAM_START_ADDRESS, ATP.C5_L2SRAM_END_ADDRESS);

				break;

			case 6:

				ret = String.format(
						"Addres is invalid for Core - %d.\nAddress Range between ( 0x%08X and 0x%08X ) or (  0x%08X and 0x%08X  )",
						(cmbCores.getSelectedIndex() - 1), ATP.CORE6_DDR3_START_ADDRESS, ATP.CORE6_DDR3_END_ADDRESS,
						ATP.C6_L2SRAM_START_ADDRESS, ATP.C6_L2SRAM_END_ADDRESS);
				break;

			case 7:

				ret = String.format(
						"Addres is invalid for Core - %d.\nAddress Range between ( 0x%08X and 0x%08X ) or (  0x%08X and 0x%08X  )",
						(cmbCores.getSelectedIndex() - 1), ATP.CORE7_DDR3_START_ADDRESS, ATP.CORE7_DDR3_END_ADDRESS,
						ATP.C7_L2SRAM_START_ADDRESS, ATP.C7_L2SRAM_END_ADDRESS);

				break;

			}

		}

		return ret;
	}

	private void startAutoRefreshThread() {

		if (autoRefreshThread == null) {

			autoRefreshThread = null;

			autoRefreshThread = new Thread(new Runnable() {

				@Override
				public void run() {

					while (isAutoRefresh) {

						try {

							parent.readMemory(memoryFilter);

							Thread.sleep(currentThreadSleepTime);

						} catch (Exception e) {

							e.printStackTrace();

						}

					}

				}
			});

			autoRefreshThread.start();
		}

	}

	private boolean isSelectedProcessorValid() {

		return cmbProcessor.getSelectedIndex() > 0;
	}

	private boolean isSelectedCoreValid() {

		return cmbCores.getSelectedIndex() > 0;
	}

	private boolean isAddressValid() {

		boolean retValue = true;

		long address = VPXUtilities.getValue(txtMemoryAddres.getText().trim());

		if (address == -1 || cmbCores.getSelectedIndex() == 0)
			return false;

		int core = cmbCores.getSelectedIndex() - 1;

		if (core == 0) {

			if (!VPXUtilities.isBetween(address, ATP.CORE0_DDR3_START_ADDRESS, ATP.CORE0_DDR3_END_ADDRESS)
					&& !VPXUtilities.isBetween(address, ATP.C0_L2SRAM_START_ADDRESS, ATP.C0_L2SRAM_END_ADDRESS)) {

				retValue = false;
			}

		} else if (core == 1) {

			if (!VPXUtilities.isBetween(address, ATP.CORE1_DDR3_START_ADDRESS, ATP.CORE1_DDR3_END_ADDRESS)
					&& !VPXUtilities.isBetween(address, ATP.C1_L2SRAM_START_ADDRESS, ATP.C1_L2SRAM_END_ADDRESS)) {

				retValue = false;
			}

		} else if (core == 2) {

			if (!VPXUtilities.isBetween(address, ATP.CORE2_DDR3_START_ADDRESS, ATP.CORE2_DDR3_END_ADDRESS)
					&& !VPXUtilities.isBetween(address, ATP.C2_L2SRAM_START_ADDRESS, ATP.C2_L2SRAM_END_ADDRESS)) {

				retValue = false;
			}

		} else if (core == 3) {

			if (!VPXUtilities.isBetween(address, ATP.CORE3_DDR3_START_ADDRESS, ATP.CORE3_DDR3_END_ADDRESS)
					&& !VPXUtilities.isBetween(address, ATP.C3_L2SRAM_START_ADDRESS, ATP.C3_L2SRAM_END_ADDRESS)) {

				retValue = false;
			}

		} else if (core == 4) {

			if (!VPXUtilities.isBetween(address, ATP.CORE4_DDR3_START_ADDRESS, ATP.CORE4_DDR3_END_ADDRESS)
					&& !VPXUtilities.isBetween(address, ATP.C4_L2SRAM_START_ADDRESS, ATP.C4_L2SRAM_END_ADDRESS)) {

				retValue = false;
			}

		} else if (core == 5) {

			if (!VPXUtilities.isBetween(address, ATP.CORE5_DDR3_START_ADDRESS, ATP.CORE5_DDR3_END_ADDRESS)
					&& !VPXUtilities.isBetween(address, ATP.C5_L2SRAM_START_ADDRESS, ATP.C5_L2SRAM_END_ADDRESS)) {

				retValue = false;
			}

		} else if (core == 6) {

			if (!VPXUtilities.isBetween(address, ATP.CORE6_DDR3_START_ADDRESS, ATP.CORE6_DDR3_END_ADDRESS)
					&& !VPXUtilities.isBetween(address, ATP.C6_L2SRAM_START_ADDRESS, ATP.C6_L2SRAM_END_ADDRESS)) {

				retValue = false;
			}

		} else if (core == 7) {

			if (!VPXUtilities.isBetween(address, ATP.CORE7_DDR3_START_ADDRESS, ATP.CORE7_DDR3_END_ADDRESS)
					&& !VPXUtilities.isBetween(address, ATP.C7_L2SRAM_START_ADDRESS, ATP.C7_L2SRAM_END_ADDRESS)) {

				retValue = false;
			}

		}

		return retValue;
	}

	private boolean isLengthValid() {
		boolean retval = false;

		try {

			int val = Integer.valueOf(txtMemoryLength.getText().trim());

			if (val > 0 && val <= (ATP.DEFAULTBUFFERSIZE * 10)) {

				retval = true;

			}

		} catch (Exception e) {
			retval = false;
		}

		return retval;
	}

	// Windows Listener

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

		isAutoRefresh = false;

		closeLoadProcessingWindow();

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

	public void setMemory(long fromAddress, int typeSize, int length, long newValue) {

		long addr = hexPanel.getHexEditor().getHexEditorRowHeader().getRowHeaderModel().getStartAddress() + fromAddress;

		parent.setMemoryValue(getSelectedProcessor(), cmbCores.getSelectedIndex() - 1, addr, memoryBrowserID, typeSize,
				length, newValue);
	}

	public void setBytes(long startAddress, byte[] buf) {

		closeLoadProcessingWindow();

		hexPanel.setBytes(startAddress, buf);
	}

	private void showLoadProcessingWindow(String msg) {

		final JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
				null, new Object[] {}, null);

		dialog = null;

		dialog = new JDialog();

		dialog.setLocationRelativeTo(VPX_MemoryBrowserWindow.this);

		dialog.setTitle("Message");

		dialog.setModal(false);

		dialog.setUndecorated(true);

		dialog.setContentPane(optionPane);

		optionPane.setBorder(new LineBorder(Color.GRAY));

		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		dialog.setAlwaysOnTop(true);

		dialog.setSize(new Dimension(450, 100));

		dialog.setVisible(true);
	}

	private void closeLoadProcessingWindow() {

		if (dialog != null)

			dialog.dispose();
	}
}

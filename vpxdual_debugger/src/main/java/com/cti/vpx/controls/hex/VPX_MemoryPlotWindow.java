package com.cti.vpx.controls.hex;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import com.cti.vpx.command.ATP;
import com.cti.vpx.controls.VPX_FilterComboBox;
import com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.DrawSurface;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

public class VPX_MemoryPlotWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8729031397351962182L;

	private static final int LINEID_1 = 1;

	private static final int LINEID_2 = 2;

	private byte[] plot1Bytes;

	private byte[] plot2Bytes;

	private boolean isPlot1 = false;

	private boolean isPlot2 = false;

	private int MINUTE = 60 * 1000;

	private int memoryPlotID = -1;

	private JPanel contentPane;

	private JPanel filtersPanel;

	private JPanel plot1FilterPanel;

	private JTextField txtPlot1MapFilePath;

	private JTextField txtPlot1MemoryAddres;

	private JTextField txtPlot1MemoryLength;

	private JSpinner spinPlot1MemoryStride;

	private JComboBox<String> cmbPlot1SubSystem;

	private JComboBox<String> cmbPlot1Processor;

	private JComboBox<String> cmbPlot1Cores;

	private JCheckBox chkAutoRefresh;

	private JRadioButton radPlot1UseMap;

	private VPX_FilterComboBox cmbPlot1MemoryVariables;

	private JRadioButton radPlot1UserAddress;

	private JLabel lblPlot1Address;

	private JLabel lblPlot1MapFile;

	private JButton btnPlot1MapFileBrowse;

	private MemoryViewFilter plot1MemoryFilter;

	private JPanel plot2FilterPanel;

	private JTextField txtPlot2MapFilePath;

	private JTextField txtPlot2MemoryAddres;

	private JTextField txtPlot2MemoryLength;

	private JSpinner spinPlot2MemoryStride;

	private JComboBox<String> cmbPlot2SubSystem;

	private JComboBox<String> cmbPlot2Processor;

	private JComboBox<String> cmbPlot2Cores;

	private JRadioButton radPlot2UseMap;

	private VPX_FilterComboBox cmbPlot2MemoryVariables;

	private JRadioButton radPlot2UserAddress;

	private JLabel lblPlot2Address;

	private JLabel lblPlot2MapFile;

	private JButton btnPlot2MapFileBrowse;

	private MemoryViewFilter plot2MemoryFilter;

	private VPX_ETHWindow parent;

	private VPXSystem vpxSystem = null;

	private MemoryViewFilter plot1Filter;

	private MemoryViewFilter plot2Filter;

	private final ButtonGroup Plot1MemoryGroup = new ButtonGroup();

	private final ButtonGroup Plot2MemoryGroup = new ButtonGroup();

	private JPanel controlsPanel;

	private JButton btnClear;

	private JButton btnPlot;

	private JCheckBox chkPlot1;

	private JCheckBox chkPlot2;

	private JLabel lblPlot1SubSystem;

	private JLabel lblPlot1Processors;

	private JLabel lblPlot1Core;

	private JLabel lblPlot1Stride;

	private JLabel lblPlot1Length;

	private JLabel lblPlot2SubSystem;

	private JLabel lblPlot2Processors;

	private JLabel lblPlot2Core;

	private JLabel lblPlot2Stride;

	private JLabel lblPlot2Length;

	private final JFileChooser fileDialog = new JFileChooser();

	private final FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Map Files", "map");

	private Map<String, String> plot1MemVariables;

	private Map<String, String> plot2MemVariables;

	private boolean isAutoRefresh = false;

	private boolean isPlot1Chkd = false;

	private boolean isPlot2Chkd = false;

	private int currentThreadSleepTime;

	private Thread autoRefreshThread = null;

	private JSlider slideAutoRefresh;

	private JLabel lblAutoRefreshValue;

	private JLabel lblMins;

	private JLabel lblPlot1Size;

	private JComboBox<String> cmbPlot1MemSize;

	private JLabel lblPlot2Size;

	private JComboBox<String> cmbPlot2MemSize;

	private JComboBox<String> cmbFormatBytes;

	private XYSeries plotSeries1, plotSeries2;

	private XYPlot plot;

	private XYSeriesCollection data;

	private JFreeChart chart;

	/**
	 * 
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					VPX_MemoryPlotWindow frame = new VPX_MemoryPlotWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					VPXLogger.updateError(e);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VPX_MemoryPlotWindow() {

		this.memoryPlotID = 0;

		init();

		loadComponents();
	}

	public VPX_MemoryPlotWindow(int id) {

		this.memoryPlotID = id;

		init();

		loadComponents();

		// testPlot();

	}

	private void init() {

		setTitle("Memory Plot " + (memoryPlotID + 1));

		setIconImage(VPXConstants.Icons.ICON_PLOT.getImage());

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setSize((int) (VPXUtilities.getScreenWidth() * .80), (int) (VPXUtilities.getScreenHeight() * .90));

		addWindowListener(this);

		centerFrame();

	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	public void setParent(VPX_ETHWindow prnt) {
		this.parent = prnt;
	}

	private void loadComponents() {

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel filtersBasePanel = new JPanel();

		filtersBasePanel.setPreferredSize(new Dimension(10, 330));

		filtersBasePanel
				.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		filtersBasePanel.setLayout(new BorderLayout());

		filtersPanel = new JPanel();

		filtersPanel.setPreferredSize(new Dimension(10, 330));

		filtersBasePanel.add(filtersPanel, BorderLayout.CENTER);

		contentPane.add(filtersBasePanel, BorderLayout.NORTH);

		filtersPanel.setLayout(new GridLayout(2, 1, 0, 0));

		controlsPanel = new JPanel();

		FlowLayout fl_controlsPanel = (FlowLayout) controlsPanel.getLayout();

		fl_controlsPanel.setAlignment(FlowLayout.LEFT);

		filtersBasePanel.add(controlsPanel, BorderLayout.SOUTH);

		btnPlot = new JButton("Plot");

		btnPlot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doPlotMemory();
				
			//	 testPlot();

			}
		});

		controlsPanel.add(btnPlot);

		btnClear = new JButton("Clear");

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				clearAllContents();

			}
		});

		controlsPanel.add(btnClear);

		cmbFormatBytes = new JComboBox<String>(
				new DefaultComboBoxModel<String>(new String[] { "8 Bit Hex", "16 Bit Hex", "32 Bit Hex", "64 Bit Hex",
						"16 Bit Signed", "32 Bit Signed", "16 Bit Unsigned", "32 Bit Unsigned", "32 Bit Floating" }));

		cmbFormatBytes.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getSource().equals(cmbFormatBytes)) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

						plotInDifferentFormat();

					}
				}
			}
		});

		controlsPanel.add(cmbFormatBytes);

		chkAutoRefresh = new JCheckBox("Auto Refresh in every");
		controlsPanel.add(chkAutoRefresh);

		slideAutoRefresh = new JSlider();
		controlsPanel.add(slideAutoRefresh);
		slideAutoRefresh.setMinimum(30);

		slideAutoRefresh.setMaximum(960);
		slideAutoRefresh.setValue(30);

		slideAutoRefresh.setEnabled(false);

		lblAutoRefreshValue = new JLabel("30");
		controlsPanel.add(lblAutoRefreshValue);
		lblAutoRefreshValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutoRefreshValue.setEnabled(false);
		lblAutoRefreshValue.setPreferredSize(new Dimension(25, 25));

		lblMins = new JLabel("Secs");
		controlsPanel.add(lblMins);
		lblMins.setEnabled(false);
		lblMins.setPreferredSize(new Dimension(35, 25));

		slideAutoRefresh.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				JSlider source = (JSlider) e.getSource();

				int fps = (int) source.getValue();

				if (fps <= 59) {

					lblAutoRefreshValue.setText("" + fps);

					lblMins.setText("Secs");

				} else {

					lblAutoRefreshValue.setText("" + (fps / 60));

					lblMins.setText("Mins");
				}

			}
		});

		chkAutoRefresh.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				slideAutoRefresh.setEnabled(chkAutoRefresh.isSelected());

				lblAutoRefreshValue.setEnabled(chkAutoRefresh.isSelected());

				lblMins.setEnabled(chkAutoRefresh.isSelected());
			}
		});

		createPlot();

		createPlot1FilterPanel();

		createPlot2FilterPanel();

	}

	private void plotInDifferentFormat() {

		reDrawPlot(cmbFormatBytes.getSelectedIndex());
	}

	private void createPlot1FilterPanel() {

		JPanel plot1Panel = new JPanel();

		filtersPanel.add(plot1Panel);

		plot1Panel.setLayout(new BorderLayout(0, 0));

		JPanel plot1OptionPanel = new JPanel();

		FlowLayout fl_plot1OptionPanel = (FlowLayout) plot1OptionPanel.getLayout();

		fl_plot1OptionPanel.setAlignment(FlowLayout.LEFT);

		plot1Panel.add(plot1OptionPanel, BorderLayout.NORTH);

		chkPlot1 = new JCheckBox("Plot 1");

		chkPlot1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				enablePlot1Components();
			}
		});

		plot1OptionPanel.add(chkPlot1);

		createPlot1Filter();

		plot1Panel.add(plot1FilterPanel, BorderLayout.CENTER);

	}

	private void createPlot2FilterPanel() {

		JPanel plot2Panel = new JPanel();

		filtersPanel.add(plot2Panel);

		plot2Panel.setLayout(new BorderLayout(0, 0));

		JPanel plot2OptionPanel = new JPanel();

		FlowLayout fl_plot2OptionPanel = (FlowLayout) plot2OptionPanel.getLayout();

		fl_plot2OptionPanel.setAlignment(FlowLayout.LEFT);

		plot2Panel.add(plot2OptionPanel, BorderLayout.NORTH);

		chkPlot2 = new JCheckBox("Plot 2");

		chkPlot2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				enablePlot2Components();
			}
		});

		plot2OptionPanel.add(chkPlot2);

		createPlot2Filter();

		plot2Panel.add(plot2FilterPanel, BorderLayout.CENTER);
	}

	public void reloadAllSubsystems() {

		int sub = cmbPlot1SubSystem.getSelectedIndex();

		int proc = cmbPlot1Processor.getSelectedIndex();

		int core = 0;

		if (cmbPlot1Cores.getItemCount() > 0) {

			core = cmbPlot1Cores.getSelectedIndex();
		}

		loadPlot1Filters();

		cmbPlot1SubSystem.setSelectedIndex(sub);

		cmbPlot1Processor.setSelectedIndex(proc);

		if (cmbPlot1Cores.getItemCount() > 0) {

			cmbPlot1Cores.setSelectedIndex(core);
		}

		int sub1 = cmbPlot2SubSystem.getSelectedIndex();

		int proc1 = cmbPlot2Processor.getSelectedIndex();

		int core1 = 0;

		if (cmbPlot2Cores.getItemCount() > 0) {

			core1 = cmbPlot2Cores.getSelectedIndex();
		}

		loadPlot2Filters();

		cmbPlot2SubSystem.setSelectedIndex(sub1);

		cmbPlot2Processor.setSelectedIndex(proc1);

		if (cmbPlot2Cores.getItemCount() > 0) {

			cmbPlot2Cores.setSelectedIndex(core1);
		}

	}

	private void createPlot1Filter() {

		plot1FilterPanel = new JPanel();

		plot1FilterPanel.setPreferredSize(new Dimension(10, 170));

		plot1FilterPanel.setLayout(new GridLayout(3, 1, 0, 0));

		JPanel plot1SubSystemPanel = new JPanel();

		plot1FilterPanel.add(plot1SubSystemPanel);

		plot1SubSystemPanel.setLayout(new MigLayout("",
				"[46px][100px][46px][100px,fill][46px][100px,fill][97px][86px,grow,fill][pref!][pref!,fill]",
				"[23px,grow,fill]"));

		lblPlot1SubSystem = new JLabel("Sub System");

		plot1SubSystemPanel.add(lblPlot1SubSystem, "cell 0 0,alignx center,aligny center");

		cmbPlot1SubSystem = new JComboBox<String>();

		cmbPlot1SubSystem.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbPlot1SubSystem)) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						loadPlot1ProcessorsFilter();
					}
				}
			}
		});

		cmbPlot1SubSystem.setPreferredSize(new Dimension(120, 20));

		plot1SubSystemPanel.add(cmbPlot1SubSystem, "cell 1 0,growx,aligny center");

		lblPlot1Processors = new JLabel("Processors");

		plot1SubSystemPanel.add(lblPlot1Processors, "cell 2 0,alignx center,aligny center");

		cmbPlot1Processor = new JComboBox<String>();

		cmbPlot1Processor.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbPlot1Processor)) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						loadPlot1CoresFilter();
					}
				}
			}
		});

		cmbPlot1Processor.setPreferredSize(new Dimension(120, 20));

		plot1SubSystemPanel.add(cmbPlot1Processor, "cell 3 0,alignx left,aligny center");

		lblPlot1Core = new JLabel("Cores");

		plot1SubSystemPanel.add(lblPlot1Core, "cell 4 0,alignx center,aligny center");

		cmbPlot1Cores = new JComboBox<String>();

		cmbPlot1Cores.setPreferredSize(new Dimension(120, 20));

		plot1SubSystemPanel.add(cmbPlot1Cores, "cell 5 0,alignx left,aligny center");

		JPanel plot1MapPanel = new JPanel();

		plot1FilterPanel.add(plot1MapPanel);

		plot1MapPanel.setLayout(new MigLayout("", "[109px][46px][406px,grow,fill][89px][center]", "[23px]"));

		radPlot1UseMap = new JRadioButton("Use Map File");

		radPlot1UseMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				enablePlot1MemoryFields();
			}
		});

		Plot1MemoryGroup.add(radPlot1UseMap);

		plot1MapPanel.add(radPlot1UseMap, "cell 0 0,alignx left,aligny top");

		lblPlot1MapFile = new JLabel("Map File");

		lblPlot1MapFile.setEnabled(false);

		plot1MapPanel.add(lblPlot1MapFile, "cell 1 0,alignx left,aligny center");

		txtPlot1MapFilePath = new JTextField();

		txtPlot1MapFilePath.setEnabled(false);

		txtPlot1MapFilePath.setPreferredSize(new Dimension(200, 20));

		plot1MapPanel.add(txtPlot1MapFilePath, "cell 2 0,alignx left,aligny center");

		txtPlot1MapFilePath.setColumns(50);

		btnPlot1MapFileBrowse = new JButton("Browse");

		btnPlot1MapFileBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fileDialog.addChoosableFileFilter(filterOut);

				fileDialog.setAcceptAllFileFilterUsed(false);

				int returnVal = fileDialog.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fileDialog.getSelectedFile();

					loadPlot1MemoryVariables(file.getAbsolutePath());

				}

			}
		});

		btnPlot1MapFileBrowse.setEnabled(false);

		plot1MapPanel.add(btnPlot1MapFileBrowse, "cell 3 0,alignx left,aligny top");

		cmbPlot1MemoryVariables = new VPX_FilterComboBox();

		cmbPlot1MemoryVariables.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getSource().equals(cmbPlot1MemoryVariables)) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

						fillMemory1Address();

					}
				}

			}
		});

		cmbPlot1MemoryVariables.setEnabled(false);

		cmbPlot1MemoryVariables.setPreferredSize(new Dimension(250, 20));

		plot1MapPanel.add(cmbPlot1MemoryVariables, "cell 4 0,alignx left,aligny center");

		JPanel plot1MemoryAddressPanel = new JPanel();

		plot1FilterPanel.add(plot1MemoryAddressPanel);

		plot1MemoryAddressPanel.setLayout(
				new MigLayout("", "[109px][46px][206px,grow,fill][46px][126px][][pref!][46px,right][126px]", "[23px]"));

		radPlot1UserAddress = new JRadioButton("Use Direct Memory Address");

		radPlot1UserAddress.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				enablePlot1MemoryFields();
			}
		});

		radPlot1UserAddress.setSelected(true);

		Plot1MemoryGroup.add(radPlot1UserAddress);

		plot1MemoryAddressPanel.add(radPlot1UserAddress, "cell 0 0,alignx left,aligny top");

		lblPlot1Address = new JLabel("Address");

		plot1MemoryAddressPanel.add(lblPlot1Address, "cell 1 0,alignx right,aligny center");

		txtPlot1MemoryAddres = new JTextField();

		txtPlot1MemoryAddres.setPreferredSize(new Dimension(20, 20));

		txtPlot1MemoryAddres.setColumns(25);

		plot1MemoryAddressPanel.add(txtPlot1MemoryAddres, "cell 2 0,alignx left,aligny center");

		lblPlot1Length = new JLabel("Length");

		plot1MemoryAddressPanel.add(lblPlot1Length, "cell 3 0,alignx right,aligny center");

		txtPlot1MemoryLength = new JTextField();

		txtPlot1MemoryLength.setPreferredSize(new Dimension(20, 20));

		txtPlot1MemoryLength.setColumns(15);

		plot1MemoryAddressPanel.add(txtPlot1MemoryLength, "cell 4 0,alignx left,aligny center");

		lblPlot1Size = new JLabel("Size");
		plot1MemoryAddressPanel.add(lblPlot1Size, "cell 5 0,alignx trailing");

		cmbPlot1MemSize = new JComboBox<String>();
		cmbPlot1MemSize.setPreferredSize(new Dimension(120, 20));

		cmbPlot1MemSize.addItem("Byte"); // 8 Bit * 1

		cmbPlot1MemSize.addItem("Word"); // 16 Bit * 2

		cmbPlot1MemSize.addItem("DWord"); // 32 Bit * 4

		cmbPlot1MemSize.addItem("QWord"); // 64 Bit * 8

		plot1MemoryAddressPanel.add(cmbPlot1MemSize, "cell 6 0,growx");

		lblPlot1Stride = new JLabel("Stride");

		plot1MemoryAddressPanel.add(lblPlot1Stride, "cell 7 0,alignx right,aligny center");

		SpinnerNumberModel strideSpinnerModel = new SpinnerNumberModel(0, 0, 512, 1);

		spinPlot1MemoryStride = new JSpinner(strideSpinnerModel);

		JFormattedTextField txt = ((JSpinner.NumberEditor) spinPlot1MemoryStride.getEditor()).getTextField();

		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

		spinPlot1MemoryStride.setPreferredSize(new Dimension(20, 20));

		plot1MemoryAddressPanel.add(spinPlot1MemoryStride, "cell 8 0,alignx left,aligny center");
	}

	private void createPlot1Filters() {

		plot1MemoryFilter = null;

		plot1MemoryFilter = new MemoryViewFilter();

		plot1MemoryFilter.setMemoryBrowserID((memoryPlotID * 10) + 1);

		if (cmbPlot1SubSystem.getItemCount() > 0)
			plot1MemoryFilter.setSubsystem(cmbPlot1SubSystem.getSelectedItem().toString());

		if (cmbPlot1Processor.getItemCount() > 0)
			plot1MemoryFilter.setProcessor(cmbPlot1Processor.getSelectedItem().toString());

		if (cmbPlot1Cores.getItemCount() > 0) {

			plot1MemoryFilter.setCore(String.valueOf(cmbPlot1Cores.getSelectedIndex() - 1));

		} else {
			plot1MemoryFilter.setCore("0");
		}

		plot1MemoryFilter.setAutoRefresh(chkAutoRefresh.isSelected());

		plot1MemoryFilter.setTimeinterval(slideAutoRefresh.getValue());

		plot1MemoryFilter.setUseMapFile(radPlot1UseMap.isSelected());

		plot1MemoryFilter.setMapPath(txtPlot1MapFilePath.getText());

		if (cmbPlot1MemoryVariables.getItemCount() > 0) {

			plot1MemoryFilter.setMemoryName(cmbPlot1MemoryVariables.getSelectedItem().toString());

		} else {
			plot1MemoryFilter.setMemoryName("");
		}

		plot1MemoryFilter.setDirectMemory(radPlot1UserAddress.isSelected());

		plot1MemoryFilter.setSize(cmbPlot1MemSize.getSelectedIndex());

		plot1MemoryFilter.setMemoryAddress(txtPlot1MemoryAddres.getText());

		plot1MemoryFilter
				.setMemoryLength(getLengthAsString(txtPlot1MemoryLength.getText(), plot1MemoryFilter.getSize()));

		plot1MemoryFilter.setMemoryStride(spinPlot1MemoryStride.getValue().toString());

	}

	public void showMemoryPlot() {

		loadPlot1Filters();

		loadPlot2Filters();

		createDefaultFilters();

		enablePlot1Components();

		enablePlot2Components();

		setVisible(true);
	}

	private void createDefaultFilters() {

		plot1Filter = null;

		plot1Filter = new MemoryViewFilter();

		plot1Filter.setSubsystem("");

		plot1Filter.setProcessor("");

		plot1Filter.setCore("");

		plot1Filter.setAutoRefresh(false);

		plot1Filter.setTimeinterval(0);

		plot1Filter.setUseMapFile(false);

		plot1Filter.setMapPath("");

		plot1Filter.setMemoryName("");

		plot1Filter.setDirectMemory(true);

		plot1Filter.setMemoryAddress("");

		plot1Filter.setMemoryLength("");

		plot1Filter.setMemoryStride("");

		plot1MemoryFilter = plot1Filter;

		plot2Filter = null;

		plot2Filter = new MemoryViewFilter();

		plot2Filter.setSubsystem("");

		plot2Filter.setProcessor("");

		plot2Filter.setCore("");

		plot2Filter.setAutoRefresh(false);

		plot2Filter.setTimeinterval(0);

		plot2Filter.setUseMapFile(false);

		plot2Filter.setMapPath("");

		plot2Filter.setMemoryName("");

		plot2Filter.setDirectMemory(true);

		plot2Filter.setMemoryAddress("");

		plot2Filter.setMemoryLength("");

		plot2Filter.setMemoryStride("");

		plot2MemoryFilter = plot2Filter;

	}

	private void enablePlot1MemoryFields() {

		boolean isMap = radPlot1UseMap.isSelected();

		boolean isDirect = radPlot1UserAddress.isSelected();

		lblPlot1MapFile.setEnabled(isMap);

		txtPlot1MapFilePath.setEnabled(isMap);

		btnPlot1MapFileBrowse.setEnabled(isMap);

		cmbPlot1MemoryVariables.setEnabled(isMap);

		lblPlot1Address.setEnabled(isDirect);

		txtPlot1MemoryAddres.setEnabled(isDirect);

	}

	private void applyPlot1Filters() {

		for (int i = 0; i < cmbPlot1SubSystem.getItemCount(); i++) {

			if (plot1MemoryFilter.getSubsystem().equals(cmbPlot1SubSystem.getItemAt(i))) {

				cmbPlot1SubSystem.setSelectedIndex(i);

				break;
			}
		}

		for (int i = 0; i < cmbPlot1Processor.getItemCount(); i++) {

			if (plot1MemoryFilter.getProcessor().equals(cmbPlot1Processor.getItemAt(i))) {

				cmbPlot1Processor.setSelectedIndex(i);

				break;
			}
		}

		for (int i = 0; i < cmbPlot1Cores.getItemCount(); i++) {

			if (plot1MemoryFilter.getCore().equals(cmbPlot1Cores.getItemAt(i))) {

				cmbPlot1Cores.setSelectedIndex(i);

				break;
			}
		}

		chkAutoRefresh.setSelected(plot1MemoryFilter.isAutoRefresh());

		slideAutoRefresh.setEnabled(chkAutoRefresh.isSelected());

		lblAutoRefreshValue.setEnabled(chkAutoRefresh.isSelected());

		lblMins.setEnabled(chkAutoRefresh.isSelected());

		slideAutoRefresh.setValue(plot1MemoryFilter.getTimeinterval());

		radPlot1UseMap.setSelected(plot1MemoryFilter.isUseMapFile());

		radPlot1UserAddress.setSelected(plot1MemoryFilter.isDirectMemory());

		txtPlot1MapFilePath.setText(plot1MemoryFilter.getMapPath());

		cmbPlot1MemoryVariables.setSelectedItem(plot1MemoryFilter.getMemoryName());

		txtPlot1MemoryAddres.setText(plot1MemoryFilter.getMemoryAddress());

		txtPlot1MemoryLength.setText(plot1MemoryFilter.getMemoryLength());

		enablePlot1MemoryFields();

	}

	private void loadPlot1Filters() {

		vpxSystem = VPXSessionManager.getVPXSystem();

		cmbPlot1SubSystem.removeAllItems();

		cmbPlot1SubSystem.addItem("Select SubSystem");

		List<VPXSubSystem> subsystem = vpxSystem.getSubsystem();

		if (subsystem.size() > 0) {

			for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				cmbPlot1SubSystem.addItem(vpxSubSystem.getSubSystem());
			}
		}

		List<Processor> unlist = vpxSystem.getUnListed();

		if (unlist.size() > 0) {

			cmbPlot1SubSystem.addItem(VPXConstants.VPXUNLIST);
		}

	}

	private void loadPlot1ProcessorsFilter() {

		cmbPlot1Processor.removeAllItems();

		cmbPlot1Processor.addItem("Select Processor");

		if (cmbPlot1SubSystem.getSelectedIndex() > 0) {

			VPXSubSystem curProcFilter = vpxSystem.getSubSystemByName(cmbPlot1SubSystem.getSelectedItem().toString());

			List<VPXSubSystem> s = vpxSystem.getSubsystem();

			for (Iterator<VPXSubSystem> iterator = s.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getSubSystem().equals(cmbPlot1SubSystem.getSelectedItem().toString())) {

					curProcFilter = vpxSubSystem;

					cmbPlot1Processor.addItem(vpxSubSystem.getIpDSP1());

					cmbPlot1Processor.addItem(vpxSubSystem.getIpDSP2());

					break;

				}

			}

			if (curProcFilter == null) {

				List<Processor> p = vpxSystem.getUnListed();

				for (Iterator<Processor> iterator = p.iterator(); iterator.hasNext();) {

					Processor processor = iterator.next();

					if (!processor.getName().contains("P2020")) {

						cmbPlot1Processor.addItem(processor.getiP_Addresses());
					}
				}

			}
		}

	}

	private void loadPlot1CoresFilter() {

		cmbPlot1Cores.removeAllItems();

		cmbPlot1Cores.addItem("Select Core");

		if (cmbPlot1SubSystem.getSelectedIndex() > 0 && cmbPlot1Processor.getSelectedIndex() > 0) {

			String subSystem = cmbPlot1SubSystem.getSelectedItem().toString();

			String proc = cmbPlot1Processor.getSelectedItem().toString();

			if (subSystem.equals(VPXConstants.VPXUNLIST)) {

				List<Processor> s = vpxSystem.getUnListed();

				if (s.size() > 0) {

					for (Iterator<Processor> iterator = s.iterator(); iterator.hasNext();) {

						Processor processor = iterator.next();

						if (processor.getiP_Addresses().equals(proc)) {

							if (processor.getName().contains("P2020")) {

								cmbPlot1Cores.setEnabled(false);

								break;

							} else {

								cmbPlot1Cores.setEnabled(true);

								for (int i = 0; i < 8; i++) {
									cmbPlot1Cores.addItem(String.format("Core %s", i));
								}

								cmbPlot1Cores.setSelectedIndex(0);

								break;
							}

						}

					}
				}

			} else {

				VPXSubSystem curProcFilter = vpxSystem.getSubSystemByName(subSystem);

				if (proc.equals(curProcFilter.getIpP2020())) {

					cmbPlot1Cores.setEnabled(false);

				} else {

					cmbPlot1Cores.setEnabled(true);

					for (int i = 0; i < 8; i++) {
						cmbPlot1Cores.addItem(String.format("Core %s", i));
					}

					cmbPlot1Cores.setSelectedIndex(0);
				}

			}

		}
	}

	// //////////

	private void createPlot2Filter() {

		plot2FilterPanel = new JPanel();

		plot2FilterPanel.setPreferredSize(new Dimension(10, 170));

		plot2FilterPanel.setLayout(new GridLayout(3, 1, 0, 0));

		JPanel plot2SubSystemPanel = new JPanel();

		plot2FilterPanel.add(plot2SubSystemPanel);

		plot2SubSystemPanel.setLayout(new MigLayout("",
				"[46px][100px][46px][100px,fill][46px][100px,fill][97px][86px][46px][grow,fill]", "[23px,grow,fill]"));

		lblPlot2SubSystem = new JLabel("Sub System");

		plot2SubSystemPanel.add(lblPlot2SubSystem, "cell 0 0,alignx center,aligny center");

		cmbPlot2SubSystem = new JComboBox<String>();

		cmbPlot2SubSystem.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbPlot2SubSystem)) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						loadPlot2ProcessorsFilter();
					}
				}
			}
		});

		cmbPlot2SubSystem.setPreferredSize(new Dimension(120, 20));

		plot2SubSystemPanel.add(cmbPlot2SubSystem, "cell 1 0,growx,aligny center");

		lblPlot2Processors = new JLabel("Processors");

		plot2SubSystemPanel.add(lblPlot2Processors, "cell 2 0,alignx center,aligny center");

		cmbPlot2Processor = new JComboBox<String>();

		cmbPlot2Processor.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbPlot2Processor)) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						loadPlot2CoresFilter();
					}
				}
			}
		});

		cmbPlot2Processor.setPreferredSize(new Dimension(120, 20));

		plot2SubSystemPanel.add(cmbPlot2Processor, "cell 3 0,alignx left,aligny center");

		lblPlot2Core = new JLabel("Cores");

		plot2SubSystemPanel.add(lblPlot2Core, "cell 4 0,alignx center,aligny center");

		cmbPlot2Cores = new JComboBox<String>();

		cmbPlot2Cores.setPreferredSize(new Dimension(120, 20));

		plot2SubSystemPanel.add(cmbPlot2Cores, "cell 5 0,alignx left,aligny center");

		JLabel lblPlot2Empty = new JLabel("");

		plot2SubSystemPanel.add(lblPlot2Empty, "cell 9 0");

		JPanel plot2MapPanel = new JPanel();

		plot2FilterPanel.add(plot2MapPanel);

		plot2MapPanel.setLayout(new MigLayout("", "[109px][46px][974.00px,grow,fill][89px][pref!,grow,center]", "[23px]"));

		radPlot2UseMap = new JRadioButton("Use Map File");

		radPlot2UseMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				enablePlot2MemoryFields();
			}
		});

		Plot2MemoryGroup.add(radPlot2UseMap);

		plot2MapPanel.add(radPlot2UseMap, "cell 0 0,alignx left,aligny top");

		lblPlot2MapFile = new JLabel("Map File");

		lblPlot2MapFile.setEnabled(false);

		plot2MapPanel.add(lblPlot2MapFile, "cell 1 0,alignx left,aligny center");

		txtPlot2MapFilePath = new JTextField();

		txtPlot2MapFilePath.setEnabled(false);

		txtPlot2MapFilePath.setPreferredSize(new Dimension(200, 20));

		plot2MapPanel.add(txtPlot2MapFilePath, "cell 2 0,alignx left,aligny center");

		txtPlot2MapFilePath.setColumns(50);

		btnPlot2MapFileBrowse = new JButton("Browse");

		btnPlot2MapFileBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fileDialog.addChoosableFileFilter(filterOut);

				fileDialog.setAcceptAllFileFilterUsed(false);

				int returnVal = fileDialog.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fileDialog.getSelectedFile();

					loadPlot2MemoryVariables(file.getAbsolutePath());

				}

			}
		});

		btnPlot2MapFileBrowse.setEnabled(false);

		plot2MapPanel.add(btnPlot2MapFileBrowse, "cell 3 0,alignx left,aligny top");

		cmbPlot2MemoryVariables = new VPX_FilterComboBox();

		cmbPlot2MemoryVariables.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getSource().equals(cmbPlot2MemoryVariables)) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

						fillMemory2Address();

					}
				}

			}
		});

		cmbPlot2MemoryVariables.setEnabled(false);

		cmbPlot2MemoryVariables.setPreferredSize(new Dimension(250, 20));

		plot2MapPanel.add(cmbPlot2MemoryVariables, "cell 4 0,alignx left,aligny center");

		JPanel plot2MemoryAddressPanel = new JPanel();

		plot2FilterPanel.add(plot2MemoryAddressPanel);

		plot2MemoryAddressPanel.setLayout(
				new MigLayout("", "[109px][46px][206px,grow,fill][46px][126px][][pref!][46px][126px]", "[23px]"));

		radPlot2UserAddress = new JRadioButton("Use Direct Memory Address");

		radPlot2UserAddress.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				enablePlot2MemoryFields();
			}
		});

		radPlot2UserAddress.setSelected(true);

		Plot2MemoryGroup.add(radPlot2UserAddress);

		plot2MemoryAddressPanel.add(radPlot2UserAddress, "cell 0 0,alignx left,aligny top");

		lblPlot2Address = new JLabel("Address");

		plot2MemoryAddressPanel.add(lblPlot2Address, "cell 1 0,alignx right,aligny center");

		txtPlot2MemoryAddres = new JTextField();

		txtPlot2MemoryAddres.setPreferredSize(new Dimension(20, 20));

		txtPlot2MemoryAddres.setColumns(25);

		plot2MemoryAddressPanel.add(txtPlot2MemoryAddres, "cell 2 0,alignx left,aligny center");

		lblPlot2Length = new JLabel("Length");

		plot2MemoryAddressPanel.add(lblPlot2Length, "cell 3 0,alignx right,aligny center");

		txtPlot2MemoryLength = new JTextField();

		txtPlot2MemoryLength.setPreferredSize(new Dimension(20, 20));

		txtPlot2MemoryLength.setColumns(15);

		plot2MemoryAddressPanel.add(txtPlot2MemoryLength, "cell 4 0,alignx left,aligny center");

		lblPlot2Size = new JLabel("Size");

		plot2MemoryAddressPanel.add(lblPlot2Size, "cell 5 0");

		cmbPlot2MemSize = new JComboBox<String>();
		cmbPlot2MemSize.setPreferredSize(new Dimension(120, 20));

		cmbPlot2MemSize.addItem("Byte"); // 8 Bit * 1

		cmbPlot2MemSize.addItem("Word"); // 16 Bit * 2

		cmbPlot2MemSize.addItem("DWord"); // 32 Bit * 4

		cmbPlot2MemSize.addItem("QWord"); // 64 Bit * 8

		plot2MemoryAddressPanel.add(cmbPlot2MemSize, "cell 6 0,growx");

		lblPlot2Stride = new JLabel("Stride");

		plot2MemoryAddressPanel.add(lblPlot2Stride, "cell 7 0,alignx right,aligny center");

		SpinnerNumberModel strideSpinnerModel = new SpinnerNumberModel(0, 0, 512, 1);

		spinPlot2MemoryStride = new JSpinner(strideSpinnerModel);

		JFormattedTextField txt = ((JSpinner.NumberEditor) spinPlot2MemoryStride.getEditor()).getTextField();

		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

		spinPlot2MemoryStride.setPreferredSize(new Dimension(20, 20));

		plot2MemoryAddressPanel.add(spinPlot2MemoryStride, "cell 8 0,alignx left,aligny center");
	}

	private void createPlot2Filters() {

		plot2MemoryFilter = null;

		plot2MemoryFilter = new MemoryViewFilter();

		plot2MemoryFilter.setMemoryBrowserID((memoryPlotID * 10) + 2);

		if (cmbPlot2SubSystem.getItemCount() > 0)
			plot2MemoryFilter.setSubsystem(cmbPlot2SubSystem.getSelectedItem().toString());

		if (cmbPlot2Processor.getItemCount() > 0)
			plot2MemoryFilter.setProcessor(cmbPlot2Processor.getSelectedItem().toString());

		if (cmbPlot2Cores.getItemCount() > 0) {

			plot2MemoryFilter.setCore(String.valueOf(cmbPlot2Cores.getSelectedIndex() - 1));

		} else {
			plot2MemoryFilter.setCore("0");
		}

		plot2MemoryFilter.setUseMapFile(radPlot2UseMap.isSelected());

		plot2MemoryFilter.setMapPath(txtPlot2MapFilePath.getText());

		if (cmbPlot2MemoryVariables.getItemCount() > 0) {

			plot2MemoryFilter.setMemoryName(cmbPlot2MemoryVariables.getSelectedItem().toString());

		} else {
			plot2MemoryFilter.setMemoryName("");
		}

		plot2MemoryFilter.setSize(cmbPlot2MemSize.getSelectedIndex());

		plot2MemoryFilter.setDirectMemory(radPlot2UserAddress.isSelected());

		plot2MemoryFilter.setMemoryAddress(txtPlot2MemoryAddres.getText());

		plot2MemoryFilter
				.setMemoryLength(getLengthAsString(txtPlot2MemoryLength.getText(), plot2MemoryFilter.getSize()));

		plot2MemoryFilter.setMemoryStride(spinPlot2MemoryStride.getValue().toString());

		plot2MemoryFilter.setAutoRefresh(chkAutoRefresh.isSelected());

		plot2MemoryFilter.setTimeinterval(slideAutoRefresh.getValue());

	}

	private String getLengthAsString(String value, int size) {

		long length = 0;

		try {

			length = Long.parseLong(value.trim());

		} catch (Exception e) {

			length = 0;
		}

		if (size == ATP.DATA_TYPE_SIZE_BIT8) {

			length = length * 1;

		} else if (size == ATP.DATA_TYPE_SIZE_BIT16) {

			length = length * 2;

		} else if (size == ATP.DATA_TYPE_SIZE_BIT32) {

			length = length * 4;

		} else if (size == ATP.DATA_TYPE_SIZE_BIT64) {

			length = length * 8;

		}

		return String.valueOf(length);
	}

	private void enablePlot2MemoryFields() {

		boolean isMap = radPlot2UseMap.isSelected();

		boolean isDirect = radPlot2UserAddress.isSelected();

		lblPlot2MapFile.setEnabled(isMap);

		txtPlot2MapFilePath.setEnabled(isMap);

		btnPlot2MapFileBrowse.setEnabled(isMap);

		cmbPlot2MemoryVariables.setEnabled(isMap);

		lblPlot2Address.setEnabled(isDirect);

		txtPlot2MemoryAddres.setEnabled(isDirect);

	}

	private void applyPlot2Filters() {

		for (int i = 0; i < cmbPlot2SubSystem.getItemCount(); i++) {

			if (plot2MemoryFilter.getSubsystem().equals(cmbPlot2SubSystem.getItemAt(i))) {

				cmbPlot2SubSystem.setSelectedIndex(i);

				break;
			}
		}

		for (int i = 0; i < cmbPlot2Processor.getItemCount(); i++) {

			if (plot2MemoryFilter.getProcessor().equals(cmbPlot2Processor.getItemAt(i))) {

				cmbPlot2Processor.setSelectedIndex(i);

				break;
			}
		}

		for (int i = 0; i < cmbPlot2Cores.getItemCount(); i++) {

			if (plot2MemoryFilter.getCore().equals(cmbPlot2Cores.getItemAt(i))) {

				cmbPlot2Cores.setSelectedIndex(i);

				break;
			}
		}

		chkAutoRefresh.setSelected(plot2MemoryFilter.isAutoRefresh());

		slideAutoRefresh.setEnabled(chkAutoRefresh.isSelected());

		lblAutoRefreshValue.setEnabled(chkAutoRefresh.isSelected());

		lblMins.setEnabled(chkAutoRefresh.isSelected());

		radPlot2UseMap.setSelected(plot2MemoryFilter.isUseMapFile());

		radPlot2UserAddress.setSelected(plot2MemoryFilter.isDirectMemory());

		txtPlot2MapFilePath.setText(plot2MemoryFilter.getMapPath());

		cmbPlot2MemoryVariables.setSelectedItem(plot2MemoryFilter.getMemoryName());

		txtPlot2MemoryAddres.setText(plot2MemoryFilter.getMemoryAddress());

		txtPlot2MemoryLength.setText(plot2MemoryFilter.getMemoryLength());

		enablePlot2MemoryFields();

	}

	private void loadPlot2Filters() {

		vpxSystem = VPXSessionManager.getVPXSystem();

		cmbPlot2SubSystem.removeAllItems();

		cmbPlot2SubSystem.addItem("Select SubSystem");

		List<VPXSubSystem> subsystem = vpxSystem.getSubsystem();

		if (subsystem.size() > 0) {

			for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				cmbPlot2SubSystem.addItem(vpxSubSystem.getSubSystem());
			}
		}

		List<Processor> unlist = vpxSystem.getUnListed();

		if (unlist.size() > 0) {

			cmbPlot2SubSystem.addItem(VPXConstants.VPXUNLIST);
		}
	}

	private void loadPlot2ProcessorsFilter() {

		cmbPlot2Processor.removeAllItems();

		cmbPlot2Processor.addItem("Select Processor");

		if (cmbPlot2SubSystem.getSelectedIndex() > 0) {

			VPXSubSystem curProcFilter = vpxSystem.getSubSystemByName(cmbPlot2SubSystem.getSelectedItem().toString());

			List<VPXSubSystem> s = vpxSystem.getSubsystem();

			for (Iterator<VPXSubSystem> iterator = s.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getSubSystem().equals(cmbPlot2SubSystem.getSelectedItem().toString())) {

					curProcFilter = vpxSubSystem;

					// cmbProcessor.addItem(vpxSubSystem.getIpP2020());

					cmbPlot2Processor.addItem(vpxSubSystem.getIpDSP1());

					cmbPlot2Processor.addItem(vpxSubSystem.getIpDSP2());

					break;

				}

			}

			if (curProcFilter == null) {

				List<Processor> p = vpxSystem.getUnListed();

				for (Iterator<Processor> iterator = p.iterator(); iterator.hasNext();) {

					Processor processor = iterator.next();

					if (!processor.getName().contains("P2020")) {

						cmbPlot2Processor.addItem(processor.getiP_Addresses());
					}
				}

			}
		}
	}

	private void loadPlot2CoresFilter() {

		cmbPlot2Cores.removeAllItems();

		cmbPlot2Cores.addItem("Select Core");

		if (cmbPlot2SubSystem.getSelectedIndex() > 0 && cmbPlot2Processor.getSelectedIndex() > 0) {

			String subSystem = cmbPlot2SubSystem.getSelectedItem().toString();

			String proc = cmbPlot2Processor.getSelectedItem().toString();

			if (subSystem.equals(VPXConstants.VPXUNLIST)) {

				List<Processor> s = vpxSystem.getUnListed();

				if (s.size() > 0) {

					for (Iterator<Processor> iterator = s.iterator(); iterator.hasNext();) {

						Processor processor = iterator.next();

						if (processor.getiP_Addresses().equals(proc)) {

							if (processor.getName().contains("P2020")) {

								cmbPlot2Cores.setEnabled(false);

								break;

							} else {

								cmbPlot2Cores.setEnabled(true);

								for (int i = 0; i < 8; i++) {
									cmbPlot2Cores.addItem(String.format("Core %s", i));
								}

								cmbPlot2Cores.setSelectedIndex(0);

								break;
							}

						}

					}
				}

			} else {

				VPXSubSystem curProcFilter = vpxSystem.getSubSystemByName(subSystem);

				if (proc.equals(curProcFilter.getIpP2020())) {

					cmbPlot2Cores.setEnabled(false);

				} else {

					cmbPlot2Cores.setEnabled(true);

					for (int i = 0; i < 8; i++) {
						cmbPlot2Cores.addItem(String.format("Core %s", i));
					}

					cmbPlot2Cores.setSelectedIndex(0);
				}

			}

		}
	}

	private void enablePlot1Components() {

		boolean isPlot1 = chkPlot1.isSelected();

		plot1FilterPanel.setEnabled(isPlot1);

		slideAutoRefresh.setEnabled(isPlot1);

		lblAutoRefreshValue.setEnabled(isPlot1);

		lblMins.setEnabled(isPlot1);

		txtPlot1MapFilePath.setEnabled(isPlot1);

		txtPlot1MemoryAddres.setEnabled(isPlot1);

		txtPlot1MemoryLength.setEnabled(isPlot1);

		spinPlot1MemoryStride.setEnabled(isPlot1);

		cmbPlot1SubSystem.setEnabled(isPlot1);

		cmbPlot1Processor.setEnabled(isPlot1);

		cmbPlot1Cores.setEnabled(isPlot1);

		chkAutoRefresh.setEnabled(isPlot1);

		radPlot1UseMap.setEnabled(isPlot1);

		cmbPlot1MemoryVariables.setEnabled(isPlot1);

		radPlot1UserAddress.setEnabled(isPlot1);

		lblPlot1Address.setEnabled(isPlot1);

		lblPlot1MapFile.setEnabled(isPlot1);

		btnPlot1MapFileBrowse.setEnabled(isPlot1);

		lblPlot1SubSystem.setEnabled(isPlot1);

		lblPlot1Processors.setEnabled(isPlot1);

		lblPlot1Core.setEnabled(isPlot1);

		lblPlot1Stride.setEnabled(isPlot1);

		lblPlot1Length.setEnabled(isPlot1);

		if (isPlot1) {

			applyPlot1Filters();

		} else {

			createPlot1Filters();
		}

		if (chkPlot1.isSelected() || chkPlot2.isSelected()) {

			btnPlot.setEnabled(true);

			btnClear.setEnabled(true);

			cmbFormatBytes.setEnabled(true);

		} else {

			btnPlot.setEnabled(false);

			btnClear.setEnabled(false);

			cmbFormatBytes.setEnabled(false);
		}
	}

	private void loadPlot1MemoryVariables(String fileName) {

		cmbPlot1MemoryVariables.removeAllItems();

		txtPlot1MapFilePath.setText(fileName);

		plot1MemVariables = VPXUtilities.getMemoryAddressVariables(fileName);

		cmbPlot1MemoryVariables.addMemoryVariables(plot1MemVariables);

		cmbPlot1MemoryVariables.setSelectedIndex(0);

	}

	private void fillMemory1Address() {

		if (plot1MemVariables.containsKey(cmbPlot1MemoryVariables.getSelectedItem().toString())) {

			txtPlot1MemoryAddres.setText(plot1MemVariables.get(cmbPlot1MemoryVariables.getSelectedItem().toString()));
		}
	}

	private void fillMemory2Address() {

		if (plot2MemVariables.containsKey(cmbPlot2MemoryVariables.getSelectedItem().toString())) {

			txtPlot2MemoryAddres.setText(plot2MemVariables.get(cmbPlot2MemoryVariables.getSelectedItem().toString()));
		}
	}

	private void loadPlot2MemoryVariables(String fileName) {

		cmbPlot2MemoryVariables.removeAllItems();

		txtPlot2MapFilePath.setText(fileName);

		plot2MemVariables = VPXUtilities.getMemoryAddressVariables(fileName);

		cmbPlot2MemoryVariables.addMemoryVariables(plot2MemVariables);

		cmbPlot2MemoryVariables.setSelectedIndex(0);

	}

	private void clearAllContents() {

		plotSeries1.clear();

		plotSeries2.clear();

		plot1Bytes = null;

		plot2Bytes = null;

		isPlot1 = false;

		isPlot2 = false;

		cmbFormatBytes.setSelectedIndex(0);

		loadPlot1Filters();

		loadPlot2Filters();

		createDefaultFilters();

		enablePlot1Components();

		enablePlot2Components();

		chkPlot1.setSelected(false);

		chkPlot2.setSelected(false);

		enablePlot1Components();

		enablePlot2Components();
	}

	private void enablePlot2Components() {

		boolean isPlot2 = chkPlot2.isSelected();

		plot2FilterPanel.setEnabled(isPlot2);

		txtPlot2MapFilePath.setEnabled(isPlot2);

		txtPlot2MemoryAddres.setEnabled(isPlot2);

		txtPlot2MemoryLength.setEnabled(isPlot2);

		spinPlot2MemoryStride.setEnabled(isPlot2);

		cmbPlot2SubSystem.setEnabled(isPlot2);

		cmbPlot2Processor.setEnabled(isPlot2);

		cmbPlot2Cores.setEnabled(isPlot2);

		radPlot2UseMap.setEnabled(isPlot2);

		cmbPlot2MemoryVariables.setEnabled(isPlot2);

		radPlot2UserAddress.setEnabled(isPlot2);

		lblPlot2Address.setEnabled(isPlot2);

		lblPlot2MapFile.setEnabled(isPlot2);

		btnPlot2MapFileBrowse.setEnabled(isPlot2);

		lblPlot2SubSystem.setEnabled(isPlot2);

		lblPlot2Processors.setEnabled(isPlot2);

		lblPlot2Core.setEnabled(isPlot2);

		lblPlot2Stride.setEnabled(isPlot2);

		lblPlot2Length.setEnabled(isPlot2);

		if (isPlot2) {

			applyPlot2Filters();

		} else {
			createPlot2Filters();

		}

		if (chkPlot1.isSelected() || chkPlot2.isSelected()) {

			btnPlot.setEnabled(true);

			btnClear.setEnabled(true);

			cmbFormatBytes.setEnabled(true);

		} else {

			btnPlot.setEnabled(false);

			btnClear.setEnabled(false);

			cmbFormatBytes.setEnabled(false);
		}
	}

	public void createPlot() {

		data = new XYSeriesCollection();

		plotSeries1 = new XYSeries("Plot - 1");

		plotSeries2 = new XYSeries("Plot - 2");

		data.addSeries(plotSeries1);

		data.addSeries(plotSeries2);

		chart = ChartFactory.createXYLineChart("", "", "", data, PlotOrientation.VERTICAL, true, true, false);

		chart.setAntiAlias(true);

		chart.setBackgroundPaint((new JPanel()).getBackground());

		chart.setBorderVisible(false);

		chart.setTextAntiAlias(true);

		plot = chart.getXYPlot();

		plot.setDomainPannable(true);
		
		plot.setBackgroundPaint(Color.BLACK);
		
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));

		plot.setDomainCrosshairVisible(true);

		plot.setRangeCrosshairVisible(true);

		//refreshRenderer();

		ValueAxis domain = new NumberAxis(), range = new NumberAxis();

		plot.setDomainAxis(domain);

		plot.setRangeAxis(range);

		ChartPanel phnPlotPanel = new ChartPanel(chart);

		phnPlotPanel.setCursor(DrawSurface.getDefaultCrossHairCursor());

		phnPlotPanel.setPopupMenu(null);

		contentPane.add(phnPlotPanel, BorderLayout.CENTER);

	}

	public void plot() {
		try {

			for (int i = 0; i < 1024; i++)
				plotSeries1.add(i, Math.sin(i / 70.0), false);

			plotSeries1.fireSeriesChanged();

			Thread.sleep(250);

			for (int i = 0; i < 1024; i++)
				plotSeries2.add(i, Math.sin(i / 30.0), false);

			plotSeries2.fireSeriesChanged();

			//refreshRenderer();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setBytes(int lineID, byte[] bytes) {

		if (lineID == LINEID_1) {

			plot1Bytes = bytes;

			isPlot1 = true;

		} else if (lineID == LINEID_2) {

			plot2Bytes = bytes;

			isPlot2 = true;
		}

		reDrawPlot(cmbFormatBytes.getSelectedIndex());
	}

	public void reDrawPlot(int format) {

		plotSeries1.clear();

		plotSeries2.clear();

		if (isPlot1) {

			double[] doubles = getDoubleArray(plot1Bytes, format);

			if (doubles != null) {

				for (int i = 0; i < doubles.length; i++)
					plotSeries1.add(i, doubles[i], true);

				plotSeries1.fireSeriesChanged();
			}

		}
		if (isPlot2) {

			double[] doubles = getDoubleArray(plot2Bytes, format);

			if (doubles != null) {

				for (int i = 0; i < doubles.length; i++)
					plotSeries2.add(i, doubles[i], true);

				plotSeries2.fireSeriesChanged();
			}
		}

	}

	private double[] getDoubleArray(byte[] bytes, int format) {

		double[] doubleArr = null;

		if (bytes == null) {

			return doubleArr;
		}

		// 0-8 Bit Hex
		// 1-16 Bit Hex
		// 2-32 Bit Hex
		// 3-64 Bit Hex
		// 4-16 Bit Signed
		// 5-32 Bit Signed
		// 6-16 Bit Unsigned
		// 7-32 Bit Unsigned
		// 8-32 Bit Floating

		int len = 0;

		if (format == 0) {// 8 Bit Hex

			len = bytes.length;

			double[] doubleArray = new double[len];

			for (int i = 0; i < doubleArray.length; i++) {

				doubleArray[i] = (double) (bytes[i] & 0x0ff);
			}

			doubleArr = doubleArray;

		} else if (format == 1) {// 16 Bit Hex

			len = bytes.length / 2;

			double[] doubleArray = new double[len];

			byte[] bb = null;

			for (int i = 0; i < doubleArray.length; i++) {

				bb = getByteByGroup(bytes, i, 2);

				doubleArray[i] = (double) new BigInteger(1, bb).intValue();
			}

			doubleArr = doubleArray;

		} else if (format == 2) {// 32 Bit Hex

			len = bytes.length / 4;

			double[] doubleArray = new double[len];

			byte[] bb = null;

			for (int i = 0; i < doubleArray.length; i++) {

				bb = getByteByGroup(bytes, i, 4);

				doubleArray[i] = (double) new BigInteger(1, bb).longValue();
			}

			doubleArr = doubleArray;

		} else if (format == 3) {// 64 Bit Hex

			len = bytes.length / 8;

			double[] doubleArray = new double[len];

			byte[] bb = null;

			for (int i = 0; i < doubleArray.length; i++) {

				bb = getByteByGroup(bytes, i, 8);

				doubleArray[i] = (double) new BigInteger(1, bb).longValue();
			}

			doubleArr = doubleArray;

		} else if (format == 4) {// 16 Bit Signed

			len = bytes.length / 2;

			double[] doubleArray = new double[len];

			byte[] bb = null;

			for (int i = 0; i < doubleArray.length; i++) {

				bb = getByteByGroup(bytes, i, 2);

				doubleArray[i] = (double) new BigInteger(1, bb).intValue();
			}

			doubleArr = doubleArray;

		} else if (format == 5) {// 32 Bit Signed

			len = bytes.length / 4;

			double[] doubleArray = new double[len];

			byte[] bb = null;

			for (int i = 0; i < doubleArray.length; i++) {

				bb = getByteByGroup(bytes, i, 4);

				doubleArray[i] = (double) new BigInteger(1, bb).intValue();
			}

			doubleArr = doubleArray;

		} else if (format == 6) {// 16 Bit Unsigned

			len = bytes.length / 2;

			double[] doubleArray = new double[len];

			byte[] bb = null;

			for (int i = 0; i < doubleArray.length; i++) {

				bb = getByteByGroup(bytes, i, 2);

				doubleArray[i] = (double) Integer.decode(String.format("0x%02x%02x", bb[0] & 0xff, bb[1] & 0xff));
			}

			doubleArr = doubleArray;

		} else if (format == 7) {// 32 Bit Unsigned

			len = bytes.length / 4;

			double[] doubleArray = new double[len];

			byte[] bb = null;

			for (int i = 0; i < doubleArray.length; i++) {

				bb = getByteByGroup(bytes, i, 4);

				doubleArray[i] = (double) Long.decode(
						String.format("0x%02x%02x%02x%02x", bb[0] & 0xff, bb[1] & 0xff, bb[2] & 0xff, bb[3] & 0xff));
			}

			doubleArr = doubleArray;

		} else if (format == 8) {// 32 Bit floating

			len = bytes.length / 4;

			double[] doubleArray = new double[len];

			byte[] bb = null;

			for (int i = 0; i < doubleArray.length; i++) {

				bb = getByteByGroup(bytes, i, 4);

				doubleArray[i] = (double) java.nio.ByteBuffer.wrap(bb).order(ByteOrder.BIG_ENDIAN).getFloat();
			}

			doubleArr = doubleArray;

		}

		return doubleArr;
	}

	public byte[] getByteByGroup(byte[] buffer, int offset, int group) {

		int j = offset * group;

		byte[] b = null;

		if (offset < 0)
			return b;

		if (j < buffer.length) {

			b = new byte[group];

			for (int i = b.length - 1; i >= 0; i--) {

				if (j < buffer.length) {

					b[i] = (byte) (buffer[j]);
				}

				j++;
			}

		}
		return b;
	}

	public void refreshRenderer() {

		APXYLineAndShapeRenderer r = new APXYLineAndShapeRenderer();

		r.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));

		r.setSeriesShape(0, new Rectangle(0, 0));

		r.setSeriesPaint(0, Color.red);

		r.setSeriesStroke(1, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));

		r.setSeriesShape(1, new Rectangle(0, 0));

		r.setSeriesPaint(1, Color.blue);

		XYToolTipGenerator tt1 = new XYToolTipGenerator() {
			public String generateToolTip(XYDataset dataset, int series, int item) {
				StringBuffer sb = new StringBuffer();
				Number x = dataset.getX(series, item);
				Number y = dataset.getY(series, item);

				String htmlStr = "<html><p style='color:#ff0000;'>Plot - 1:</p>" + x.doubleValue() + "<br />"
						+ y.doubleValue() + "</html>";
				sb.append(htmlStr);
				return sb.toString();
			}
		};
		XYToolTipGenerator tt2 = new XYToolTipGenerator() {
			public String generateToolTip(XYDataset dataset, int series, int item) {
				StringBuffer sb = new StringBuffer();
				Number x = dataset.getX(series, item);
				Number y = dataset.getY(series, item);
				String htmlStr = "<html><p style='color:blue;'>Plot - 2:</p>" + x.doubleValue() + "<br />"
						+ y.doubleValue() + "</html>";
				sb.append(htmlStr);
				return sb.toString();
			}
		};
		r.setSeriesToolTipGenerator(0, tt1);
		r.setSeriesToolTipGenerator(1, tt2);
		plot.setRenderer(r);

		UIManager.put("ToolTip.background", new Color(0.9f, 0.9f, 0.9f));
		UIManager.put("ToolTip.font", null);
	}

	public class APXYLineAndShapeRenderer extends XYLineAndShapeRenderer {
		private static final long serialVersionUID = 1L; // <- eclipse insists
															// on this and I
															// hate warnings ^^

		@Override
		protected void addEntity(EntityCollection entities, Shape area, XYDataset dataset, int series, int item,
				double entityX, double entityY) {
			if (area.getBounds().width < 2 || area.getBounds().height < 2)
				super.addEntity(entities, null, dataset, series, item, entityX, entityY);
			else
				super.addEntity(entities, area, dataset, series, item, entityX, entityY);
		}
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
		parent.reIndexMemoryPlotIndex();

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

	public void populateValues(int lineID, byte[] bytes) {
		setBytes(lineID, bytes);
	}

	private void readMemory() {

		if (isPlot1Chkd && isPlot2Chkd) {

			parent.readPlot(plot1MemoryFilter, plot2MemoryFilter);

		} else {

			if (isPlot1Chkd) {

				parent.readPlot(plot1MemoryFilter);

			} else if (isPlot2Chkd) {

				parent.readPlot(plot2MemoryFilter);

			}
		}
	}

	/*private void testPlot() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				plot();

			}
		});

		t.start();
	}*/

	private void doPlotMemory() {

		plotSeries1.clear();

		plotSeries2.clear();

		plot1Bytes = null;

		plot2Bytes = null;

		if (isPlot1Valid() && isPlot2Valid()) {

			if (chkPlot1.isSelected() && chkPlot2.isSelected()) {

				createPlot1Filters();

				createPlot2Filters();

				isPlot1Chkd = true;

				isPlot2Chkd = true;

			} else {

				if (chkPlot1.isSelected()) {

					createPlot1Filters();

					isPlot1Chkd = true;

					isPlot2Chkd = false;

				} else if (chkPlot2.isSelected()) {

					createPlot2Filters();

					isPlot1Chkd = false;

					isPlot2Chkd = true;

				}
			}

			readMemory();

			if (chkAutoRefresh.isSelected()) {

				loadCurrentThreadSleepTime(slideAutoRefresh.getValue());

				isAutoRefresh = true;

				startAutoRefreshThread();

			} else {

				isAutoRefresh = false;
			}
		}

	}

	private void loadCurrentThreadSleepTime(int value) {

		if (value < 60) {

			currentThreadSleepTime = value * 1000;
		} else {

			currentThreadSleepTime = (value / 60) * MINUTE;
		}

	}

	private void startAutoRefreshThread() {

		if (autoRefreshThread == null) {

			autoRefreshThread = null;

			autoRefreshThread = new Thread(new Runnable() {

				@Override
				public void run() {

					while (isAutoRefresh) {

						try {

							readMemory();

							Thread.sleep(currentThreadSleepTime);

						} catch (Exception e) {
							VPXLogger.updateError(e);
							e.printStackTrace();

						}

					}

				}
			});

			autoRefreshThread.start();
		}

	}
	// Validation

	private boolean isPlot1Valid() {

		boolean retValue = true;

		if (chkPlot1.isSelected()) {

			boolean isSelectedProcessorValid = isValidIndex(cmbPlot1Processor.getSelectedIndex());

			boolean isSelectedCoreValid = isValidIndex(cmbPlot1Cores.getSelectedIndex());

			int isAddressValid = isAddressValid(txtPlot1MemoryAddres.getText().trim(),
					txtPlot1MemoryLength.getText().trim(), cmbPlot1Cores.getSelectedIndex());

			boolean isLengthValid = isLengthValid(txtPlot1MemoryLength.getText().trim());

			if (isSelectedProcessorValid && isSelectedCoreValid && (isAddressValid == 0) && isLengthValid) {

				retValue = true;

			} else {
				if (!isSelectedProcessorValid) {

					JOptionPane.showMessageDialog(VPX_MemoryPlotWindow.this, "Please select processor for Plot-1",
							"Validation", JOptionPane.ERROR_MESSAGE);

					return false;

				} else if (!isSelectedCoreValid) {

					JOptionPane.showMessageDialog(VPX_MemoryPlotWindow.this, "Please select core for Plot-1",
							"Validation", JOptionPane.ERROR_MESSAGE);

					return false;

				} else if (isAddressValid < 0) {

					JOptionPane.showMessageDialog(VPX_MemoryPlotWindow.this,
							"Plot-1 " + getAddressErrorMessage(isAddressValid), "Validation",
							JOptionPane.ERROR_MESSAGE);

					return false;

				} else if (!isLengthValid) {

					JOptionPane
							.showMessageDialog(VPX_MemoryPlotWindow.this,
									"Plot-1 Length is invalid.\nEnter valid length( between 1 to "
											+ (ATP.DEFAULTBUFFERSIZE * 10) + " )!.",
									"Validation", JOptionPane.ERROR_MESSAGE);

					return false;

				}
			}

		}

		return retValue;
	}

	private boolean isPlot2Valid() {

		boolean retValue = true;

		if (chkPlot2.isSelected()) {

			boolean isSelectedProcessorValid = isValidIndex(cmbPlot2Processor.getSelectedIndex());

			boolean isSelectedCoreValid = isValidIndex(cmbPlot2Cores.getSelectedIndex());

			int isAddressValid = isAddressValid(txtPlot2MemoryAddres.getText().trim(),
					txtPlot2MemoryLength.getText().trim(), cmbPlot2Cores.getSelectedIndex());

			boolean isLengthValid = isLengthValid(txtPlot2MemoryLength.getText().trim());

			if (isSelectedProcessorValid && isSelectedCoreValid && (isAddressValid == 0) && isLengthValid) {

				retValue = true;

			} else {
				if (!isSelectedProcessorValid) {

					JOptionPane.showMessageDialog(VPX_MemoryPlotWindow.this, "Please select processor for Plot-2",
							"Validation", JOptionPane.ERROR_MESSAGE);

					return false;

				} else if (!isSelectedCoreValid) {

					JOptionPane.showMessageDialog(VPX_MemoryPlotWindow.this, "Please select core for Plot-2",
							"Validation", JOptionPane.ERROR_MESSAGE);

					return false;

				} else if (isAddressValid < 0) {

					JOptionPane.showMessageDialog(VPX_MemoryPlotWindow.this,
							"Plot-2 " + getAddressErrorMessage(isAddressValid), "Validation",
							JOptionPane.ERROR_MESSAGE);

					return false;

				} else if (!isLengthValid) {

					JOptionPane
							.showMessageDialog(VPX_MemoryPlotWindow.this,
									"Plot-2 Length is invalid.\nEnter valid length( between 1 to "
											+ (ATP.DEFAULTBUFFERSIZE * 10) + " )!.",
									"Validation", JOptionPane.ERROR_MESSAGE);

					return false;

				}
			}

		}

		return retValue;
	}

	private String getAddressErrorMessage(int option) {

		String ret = "";

		if (option == -2) {

			ret = String.format(
					"Addres is invalid\nAddress Range between ( 0x%08X and 0x%08X ) or (  0x%08X and 0x%08X  )or (  0x%08X and 0x%08X  )",
					ATP.DDR3_START_ADDRESS, ATP.DDR3_END_ADDRESS, ATP.L2SRAM_START_ADDRESS, ATP.L2SRAM_END_ADDRESS,
					ATP.MSMCSRAM_START_ADDRESS, ATP.MSMCSRAM_END_ADDRESS);

		} else if (option == -3) {

			ret = String.format("Length exceeding the limit.\nPlease ensure the length");
		} else if (option == -4) {

			ret = "Address is not valid";
		}

		return ret;
	}

	private boolean isValidIndex(int index) {

		return index > 0;
	}

	private boolean isLengthValid(String length) {

		boolean retval = false;

		try {

			int val = Integer.valueOf(length.trim());

			if (val > 0 && val <= (ATP.DEFAULTBUFFERSIZE * 10)) {

				retval = true;

			}

		} catch (Exception e) {
			VPXLogger.updateError(e);
			retval = false;
		}

		return retval;
	}

	private int isAddressValid(String addr, String len, int coreIDX) {

		if (addr.length() == 0) {

			return -4;
		}

		int retValue = 0;

		long address = VPXUtilities.getValue(addr);

		int val = Integer.valueOf(len);

		if (address == -1 || coreIDX == 0) {

			return -1;

		} else if (!VPXUtilities.isBetween(address, ATP.DDR3_START_ADDRESS, ATP.DDR3_END_ADDRESS)
				&& !VPXUtilities.isBetween(address, ATP.L2SRAM_START_ADDRESS, ATP.L2SRAM_END_ADDRESS)
				&& !VPXUtilities.isBetween(address, ATP.MSMCSRAM_START_ADDRESS, ATP.MSMCSRAM_END_ADDRESS)) {

			retValue = -2;

		} else if (!VPXUtilities.isBetween((address + val), ATP.DDR3_START_ADDRESS, ATP.DDR3_END_ADDRESS)
				&& !VPXUtilities.isBetween((address + val), ATP.L2SRAM_START_ADDRESS, ATP.L2SRAM_END_ADDRESS)
				&& !VPXUtilities.isBetween((address + val), ATP.MSMCSRAM_START_ADDRESS, ATP.MSMCSRAM_END_ADDRESS)) {

			retValue = -3;
		}

		return retValue;
	}
}

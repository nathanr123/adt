package com.cti.vpx.controls.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.cti.vpx.controls.graph.example.WaterfallGraphPanel;
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

public class VPX_SpectrumWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7945447785324008590L;

	@SuppressWarnings("unused")
	private String currentSubSystem = "";

	private String currentProcIP = "";

	private final JPanel contentPanel = new JPanel();

	private JLabel lblMins;

	private JLabel lblAutoRefreshValue;

	private JComboBox<String> cmbGraphObject;

	public XYSeries series;

	private XYSeriesCollection dataset;

	private WaterfallGraphPanel newWaterfallGraph = null;

	private int currentThreadSleepTime;

	private boolean isStarted = false;

	private int MINUTE = 60 * 1000;

	private int spectrumID = -1;

	private Thread autoRefreshThread = null;

	private JSlider slideAutoRefresh;

	private VPX_ETHWindow parent;

	private VPXSystem vpxSystem;

	private JComboBox<String> cmbSubSystem;

	private JComboBox<String> cmbProcessor;

	private JLabel lblMinVal;

	private JLabel lblMaxVal;

	private JCheckBox chkWaterfall;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_SpectrumWindow dialog = new VPX_SpectrumWindow(null, "sub1", "172.17.1.28");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @wbp.parser.constructor
	 */
	public VPX_SpectrumWindow(VPX_ETHWindow parent, int id) {

		this.spectrumID = id;

		this.parent = parent;

		setTitle("Data Graph " + (spectrumID + 1));

		newWaterfallGraph = new WaterfallGraphPanel();

		init();

		loadComponents();

		updateProcessorDetail();

		loadGraphObjects();

		centerFrame();

	}

	public VPX_SpectrumWindow(VPX_ETHWindow parent, String subsystem, String ip) {

		this.parent = parent;

		this.spectrumID = -1;

		setTitle("Data Graph");

		this.currentProcIP = ip;

		this.currentSubSystem = subsystem;

		newWaterfallGraph = new WaterfallGraphPanel();

		init();

		loadComponents();

		updateProcessorDetail();

		loadGraphObjects();

		centerFrame();
	}

	private void init() {

		//setResizable(false);

		setIconImage(VPXConstants.Icons.ICON_SPECTRUM.getImage());

		setSize((int) (VPXUtilities.getScreenWidth() * .80), (int) (VPXUtilities.getScreenHeight() * .90));

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new BorderLayout(0, 0));

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		addWindowListener(this);
	}

	private void loadComponents() {

		JPanel panelFilter = new JPanel();

		panelFilter.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filter",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		panelFilter.setPreferredSize(new Dimension(10, 100));

		contentPanel.add(panelFilter, BorderLayout.NORTH);

		panelFilter.setLayout(new MigLayout("",
				"[88px,left][pref!,grow][85px,fill][10px][214px][21px][49px][57px][][][][]", "[29px][30px,center]"));

		JLabel lblSubSystem = new JLabel("Sub System");
		lblSubSystem.setHorizontalAlignment(SwingConstants.LEFT);

		panelFilter.add(lblSubSystem, "cell 0 0,alignx trailing,growy");

		cmbSubSystem = new JComboBox<String>();

		cmbSubSystem.setPreferredSize(new Dimension(120, 20));

		cmbSubSystem.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbSubSystem)) {

					if (arg0.getStateChange() == ItemEvent.SELECTED) {

						loadProcessors();
					}
				}
			}
		});

		panelFilter.add(cmbSubSystem, "cell 1 0,growx");

		lblMinVal = new JLabel("");

		lblMinVal.setPreferredSize(new Dimension(45, 25));

		panelFilter.add(lblMinVal, "cell 11 0");

		JLabel lblProcessor = new JLabel("Processor");

		lblProcessor.setHorizontalAlignment(SwingConstants.LEFT);

		panelFilter.add(lblProcessor, "cell 0 1,alignx trailing,growy");

		JLabel lblGraphObject = new JLabel("Graph Object");

		lblGraphObject.setHorizontalAlignment(SwingConstants.RIGHT);

		panelFilter.add(lblGraphObject, "cell 2 0,grow");

		cmbGraphObject = new JComboBox<String>();

		panelFilter.add(cmbGraphObject, "cell 4 0,growx,aligny center");

		cmbProcessor = new JComboBox<String>();

		cmbProcessor.setPreferredSize(new Dimension(120, 20));

		panelFilter.add(cmbProcessor, "cell 1 1,growx");

		JLabel lblAutoRefresh = new JLabel("Auto Refresh");

		lblAutoRefresh.setHorizontalAlignment(SwingConstants.RIGHT);

		panelFilter.add(lblAutoRefresh, "cell 2 1,alignx left,growy");

		slideAutoRefresh = new JSlider();

		slideAutoRefresh.setMinimum(30);

		slideAutoRefresh.setMaximum(960);

		slideAutoRefresh.setValue(30);

		panelFilter.add(slideAutoRefresh, "cell 4 1,growx,aligny bottom");

		lblAutoRefreshValue = new JLabel("30");

		lblAutoRefreshValue.setHorizontalAlignment(SwingConstants.CENTER);

		lblAutoRefreshValue.setPreferredSize(new Dimension(25, 25));

		panelFilter.add(lblAutoRefreshValue, "cell 5 1,alignx center,aligny center");

		lblMins = new JLabel("Secs");

		lblMins.setPreferredSize(new Dimension(45, 25));

		panelFilter.add(lblMins, "cell 6 1,alignx left,aligny center");

		JButton btnStart = new JButton("Start");

		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				startPopulateData();

			}
		});

		panelFilter.add(btnStart, "cell 7 1,alignx center,aligny center");

		chkWaterfall = new JCheckBox("Waterfall");

		panelFilter.add(chkWaterfall, "cell 5 0 2 1,alignx left,aligny center");

		JLabel lblMax = new JLabel("Max Value");

		panelFilter.add(lblMax, "cell 9 1");

		JLabel lblMin = new JLabel("Min Value");

		panelFilter.add(lblMin, "cell 9 0");

		lblMaxVal = new JLabel("");

		lblMaxVal.setPreferredSize(new Dimension(45, 25));

		panelFilter.add(lblMaxVal, "cell 11 1");

		JPanel panelGraph = new JPanel();

		panelGraph.setForeground(Color.WHITE);

		panelGraph.setBackground(Color.BLACK);

		contentPanel.add(panelGraph, BorderLayout.CENTER);

		panelGraph.setLayout(new BorderLayout(0, 0));

		JPanel panelWaterfall = new JPanel();

		panelWaterfall.setForeground(Color.WHITE);

		panelWaterfall.setBackground(Color.BLACK);

		// panelWaterfall.setBorder(new
		// TitledBorder(UIManager.getBorder("TitledBorder.border"), "Waterfall",
		// TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255,
		// 255)));

		panelWaterfall.setPreferredSize(new Dimension(10, 350));

		panelWaterfall.setLayout(new BorderLayout(0, 0));

		panelWaterfall.add(newWaterfallGraph, BorderLayout.CENTER);

		panelGraph.add(panelWaterfall, BorderLayout.NORTH);

		JPanel panelAmplitude = new JPanel(new BorderLayout());

		panelAmplitude.setForeground(Color.WHITE);

		panelAmplitude.setBackground(Color.BLACK);

		// panelAmplitude.setBorder(new
		// TitledBorder(UIManager.getBorder("TitledBorder.border"), "Amplitude",
		// TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255,
		// 255)));

		series = new XYSeries("Amplitude", true, true);

		dataset = new XYSeriesCollection(series);

		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setCursor(DrawSurface.getDefaultCrossHairCursor());

		chartPanel.setPopupMenu(null);

		panelAmplitude.add(chartPanel, BorderLayout.CENTER);

		panelGraph.add(panelAmplitude);

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

		cmbProcessor.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbProcessor)) {

					if (arg0.getStateChange() == ItemEvent.SELECTED) {

						loadGraphObjects();
					}
				}
			}
		});

		loadSubSystems();
	}

	private JFreeChart createChart(final XYDataset dataset) {

		JFreeChart jfreechart = ChartFactory.createXYLineChart("", "Time", "Amplitude", dataset,
				PlotOrientation.VERTICAL, true, true, true);

		jfreechart.setAntiAlias(true);

		jfreechart.setBackgroundPaint(Color.BLACK);

		jfreechart.setBorderVisible(true);

		jfreechart.setTextAntiAlias(true);

		XYPlot xyplot = (XYPlot) jfreechart.getPlot();

		XYItemRenderer renderer = xyplot.getRenderer();

		renderer.setSeriesPaint(0, Color.RED);

		renderer.setSeriesItemLabelsVisible(0, false);

		renderer.setSeriesVisibleInLegend(0, false, false);

		xyplot.getRangeAxis().setLabelPaint(Color.WHITE);

		xyplot.getRangeAxis().setTickLabelPaint(Color.WHITE);

		xyplot.getDomainAxis().setLabelPaint(Color.WHITE);

		xyplot.getDomainAxis().setTickLabelPaint(Color.WHITE);

		xyplot.setDomainGridlinesVisible(true);

		xyplot.setRangeCrosshairVisible(true);

		xyplot.setBackgroundPaint(Color.DARK_GRAY);

		return jfreechart;
	}

	private void updateProcessorDetail() {

	}

	private void loadGraphObjects() {

		cmbGraphObject.removeAllItems();

		for (int i = 0; i < 8; i++) {

			cmbGraphObject.addItem(String.format("Graph Object %d", i));
		}

	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	private void startPopulateData() {

		// send command to to populate data

		doReadData();

		isStarted = true;

		loadCurrentThreadSleepTime(slideAutoRefresh.getValue());

		startAutoRefreshThread();
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

					while (isStarted) {

						try {

							// send command to to populate data
							doReadData();

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

	public void showSpectrumWindow() {

		loadSubSystems();

		cmbSubSystem.setSelectedItem(VPXSessionManager.getCurrentSubSystem());

		cmbProcessor.setSelectedItem(VPXSessionManager.getCurrentProcessor());

		setVisible(true);
	}

	private void loadSubSystems() {

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

	private void loadProcessors() {

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

	private void doReadData() {

		parent.readAnalyticalData(cmbProcessor.getSelectedItem().toString(), cmbGraphObject.getSelectedIndex(),
				spectrumID);
	}

	public void loadData(int core, float yAxis[]) {

		float[] f = new float[1024];

		this.series.clear();

		this.dataset.removeSeries(series);

		for (int i = 0; i < yAxis.length; i++) {
			yAxis[i] = ((Float.isNaN(yAxis[i])) ? 0 : yAxis[i]);
		}

		for (int i = 0; i < yAxis.length; i++) {

			final double x = i;

			final double y = yAxis[i];

			this.series.addOrUpdate(x, y);
		}

		this.dataset.addSeries(series);

		if (chkWaterfall.isSelected()) {

			if (yAxis.length != 1024) {

				for (int i = 0; i < yAxis.length; i++) {

					f[i] = yAxis[i];
				}

				for (int i = yAxis.length; i < 1024; i++) {

					f[i] = 0;
				}

				newWaterfallGraph.addWaterfallData(f);

			} else {

				newWaterfallGraph.addWaterfallData(yAxis);
			}

		}

		setMinMaxValues(yAxis);

	}

	private void setMinMaxValues(float[] yValues) {

		if (yValues.length > 0) {

			// assign first element of an array to largest and smallest
			double smallest = yValues[0];

			double largetst = yValues[0];

			for (int i = 1; i < yValues.length; i++) {

				double b = yValues[i];

				if (b > largetst)
					largetst = b;
				else if (b < smallest)
					smallest = b;

			}

			lblMaxVal.setText("" + largetst);

			lblMinVal.setText("" + smallest);
		}

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

		parent.sendAnalyticalDataInterrupt(currentProcIP);

	}

	@Override
	public void windowClosed(WindowEvent e) {

		parent.reindexSpectrumWindowIndex();

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

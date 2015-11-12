package com.cti.vpx.controls.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

public class VPX_SpectrumWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7945447785324008590L;

	private String currentSubSystem;

	private String currentProcIP;

	private final JPanel contentPanel = new JPanel();

	private JLabel lblSubSystemVal;

	private JLabel lblProcessorVal;

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
	 */
	public VPX_SpectrumWindow(VPX_ETHWindow parent, String subsystem, String ip) {
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

		setResizable(false);

		setSize((int) (VPXUtilities.getScreenWidth() * .80), (int) (VPXUtilities.getScreenHeight() * .90));

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new BorderLayout(0, 0));
	}

	private void loadComponents() {

		JPanel panelFilter = new JPanel();

		panelFilter.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filter",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		panelFilter.setPreferredSize(new Dimension(10, 100));

		contentPanel.add(panelFilter, BorderLayout.NORTH);

		panelFilter.setLayout(
				new MigLayout("", "[88px][69px][85px][10px][214px][21px][49px][57px][][][][]", "[29px][30px,center]"));

		JLabel lblSubSystem = new JLabel("Sub System");

		panelFilter.add(lblSubSystem, "cell 0 0,grow");

		lblSubSystemVal = new JLabel("Unlisted");

		panelFilter.add(lblSubSystemVal, "cell 1 0,grow");

		JLabel lblMinVal = new JLabel("66");

		lblMinVal.setPreferredSize(new Dimension(45, 25));

		panelFilter.add(lblMinVal, "cell 11 0");

		JLabel lblProcessor = new JLabel("Processor");

		panelFilter.add(lblProcessor, "cell 0 1,alignx left,growy");

		lblProcessorVal = new JLabel("172.17.1.2");

		panelFilter.add(lblProcessorVal, "cell 1 1,alignx left,growy");

		JLabel lblGraphObject = new JLabel("Graph Object");

		panelFilter.add(lblGraphObject, "cell 2 0,grow");

		cmbGraphObject = new JComboBox<String>();

		panelFilter.add(cmbGraphObject, "cell 4 0,growx,aligny center");

		JLabel lblAutoRefresh = new JLabel("Auto Refresh");

		panelFilter.add(lblAutoRefresh, "cell 2 1 3 1,alignx left,growy");

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

		JCheckBox chkWaterfall = new JCheckBox("Waterfall");

		panelFilter.add(chkWaterfall, "cell 5 0 2 1,alignx left,aligny center");

		JLabel lblMax = new JLabel("Max Value");

		panelFilter.add(lblMax, "cell 9 1");

		JLabel lblMin = new JLabel("Min Value");

		panelFilter.add(lblMin, "cell 9 0");

		JLabel lblMaxVal = new JLabel("4A");

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

		panelWaterfall.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Waterfall",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));

		panelWaterfall.setPreferredSize(new Dimension(10, 250));

		panelWaterfall.setLayout(new BorderLayout(0, 0));

		panelWaterfall.add(newWaterfallGraph, BorderLayout.CENTER);

		panelGraph.add(panelWaterfall, BorderLayout.NORTH);

		JPanel panelAmplitude = new JPanel(new BorderLayout());

		panelAmplitude.setForeground(Color.WHITE);

		panelAmplitude.setBackground(Color.BLACK);

		panelAmplitude.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Amplitude",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));

		series = new XYSeries("Amplitude", true, true);

		dataset = new XYSeriesCollection(series);

		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);

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

		lblProcessorVal.setText(currentProcIP);

		lblSubSystemVal.setText(currentSubSystem);

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

}

package com.cti.vpx.controls.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_AmpWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3949787968608726203L;

	private int amplitudeID = -1;

	private JPanel contentPane;

	private JTextField txtRefreshRate;

	private JTextField txtProcessor;

	private JTextField txtMinValue;

	private JTextField txtMaxValue;

	public XYSeries series;

	private XYSeriesCollection dataset;

	private VPX_ETHWindow parent;

	private String currentip="";

	private JFreeChart chart;

	private ChartPanel chartPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPX_AmpWindow frame = new VPX_AmpWindow(null, 0);
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
	public VPX_AmpWindow(VPX_ETHWindow parnt, int id) {

		this.amplitudeID = id;

		this.parent = parnt;

		init();

		loadComponents();

	}

	public void init() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 820, 597);

		setIconImage(VPXConstants.Icons.ICON_WATERFALL.getImage());

		addWindowListener(this);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));

		series = new XYSeries("Amplitude", true, true);

		dataset = new XYSeriesCollection(series);

	}

	public void loadComponents() {

		JPanel controlPanels = new JPanel();

		controlPanels.setPreferredSize(new Dimension(10, 75));

		contentPane.add(controlPanels, BorderLayout.NORTH);

		controlPanels.setLayout(new GridLayout(3, 4, 5, 5));

		JLabel lblProcessor = new JLabel("Processor");

		lblProcessor.setHorizontalAlignment(SwingConstants.CENTER);

		controlPanels.add(lblProcessor);

		JLabel lblMinVal = new JLabel("Min Value");

		lblMinVal.setHorizontalAlignment(SwingConstants.CENTER);

		controlPanels.add(lblMinVal);

		JLabel lblMaxVal = new JLabel("Max Value");

		lblMaxVal.setHorizontalAlignment(SwingConstants.CENTER);

		controlPanels.add(lblMaxVal);

		JLabel lblRefreshRate = new JLabel("Refresh Rate");

		lblRefreshRate.setHorizontalAlignment(SwingConstants.CENTER);

		controlPanels.add(lblRefreshRate);

		txtProcessor = new JTextField();

		txtProcessor.setHorizontalAlignment(SwingConstants.CENTER);

		txtProcessor.setEditable(false);

		controlPanels.add(txtProcessor);

		txtProcessor.setColumns(10);

		txtMinValue = new JTextField();

		txtMinValue.setHorizontalAlignment(SwingConstants.CENTER);

		txtMinValue.setEditable(false);

		controlPanels.add(txtMinValue);

		txtMinValue.setColumns(10);

		txtMaxValue = new JTextField();

		txtMaxValue.setHorizontalAlignment(SwingConstants.CENTER);

		txtMaxValue.setEditable(false);

		controlPanels.add(txtMaxValue);

		txtMaxValue.setColumns(10);

		txtRefreshRate = new JTextField();

		txtRefreshRate.setHorizontalAlignment(SwingConstants.CENTER);

		txtRefreshRate.setText("2 Secs.");

		txtRefreshRate.setEditable(false);

		controlPanels.add(txtRefreshRate);

		txtRefreshRate.setColumns(10);

		JLabel lblDumm1 = new JLabel("");

		controlPanels.add(lblDumm1);

		JLabel lblDummy2 = new JLabel("");

		controlPanels.add(lblDummy2);

		chart = createChart(dataset);

		chartPanel = new ChartPanel(chart);

		contentPane.add(chartPanel, BorderLayout.CENTER);
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

		renderer.setSeriesPaint(0, Color.YELLOW);

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

	public void showAmplitude(String ip) {

		this.currentip = ip;

		setTitle(String.format("Amplitude Graph (%d) :: %s", (amplitudeID + 1), currentip));

		txtProcessor.setText(currentip);

		setVisible(true);
	}

	public void loadData(float[] xAxis, float[] yAxis) {

		this.series.clear();

		this.dataset.removeSeries(series);

		for (int i = 0; i < xAxis.length; i++) {

			final double x = xAxis[i];// (i) / 10.0;

			final double y = yAxis[i];// Math.sin(x);

			this.series.addOrUpdate(x, y);
		}

		this.dataset.addSeries(series);
		
		setMinMaxValues(yAxis);
	}

	
	private void setMinMaxValues(float[] yValues) {

		// assign first element of an array to largest and smallest
		float smallest = yValues[0];

		float largetst = yValues[0];
		
		for (int i = 1; i < yValues.length; i++) {

			float b = yValues[i];

			if (b > largetst)
				largetst = b;
			else if (b < smallest)
				smallest = b;

		}

		txtMaxValue.setText(""+largetst);

		txtMinValue.setText(""+smallest);

	}

	public String getIP() {

		return this.currentip;

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

		parent.sendAmplitudeInterrupt(currentip);

	}

	@Override
	public void windowClosed(WindowEvent e) {

		this.currentip = "";
		
		parent.reindexAmplitudeIndex();

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

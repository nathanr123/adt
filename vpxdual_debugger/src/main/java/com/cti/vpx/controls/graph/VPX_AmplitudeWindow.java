package com.cti.vpx.controls.graph;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class VPX_AmplitudeWindow extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5440995117328295568L;

	public XYSeries series;

	private XYSeriesCollection dataset;

	public VPX_AmplitudeWindow(final String title) {

		super(title);

		series = new XYSeries("Amplitude", true, true);

		dataset = new XYSeriesCollection(series);

		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setPopupMenu(null);

		final JPanel content = new JPanel(new BorderLayout());

		content.add(chartPanel);

		chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));
		
		setSize(new java.awt.Dimension(900, 500));

		setContentPane(content);

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

	public void addData(float[] xAxis, float[] yAxis) {

		this.series.clear();

		VPX_AmplitudeWindow.this.dataset.removeSeries(series);

		for (int i = 0; i < xAxis.length; i++) {

			final double x = xAxis[i];// (i) / 10.0;

			final double y = yAxis[i];// Math.sin(x);

			this.series.addOrUpdate(x, y);
		}

		VPX_AmplitudeWindow.this.dataset.addSeries(series);
	}

	public static void main(final String[] args) {

		final VPX_AmplitudeWindow demo = new VPX_AmplitudeWindow("Dynamic Data view");
	//	demo.pack();
		// RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

}
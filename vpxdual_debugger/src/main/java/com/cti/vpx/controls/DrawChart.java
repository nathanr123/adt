package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class DrawChart extends ApplicationFrame implements ActionListener {

	public XYSeries series;

	double xaxis[] = { 0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11,
			11.5, 12, 12.5, 13, 13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17, 17.5, 18, 18.5, 19, 19.5, 20, 20.5, 21, 21.5,
			22, 22.5, 23, 23.5, 24, 24.5, 25, 25.5, 26, 26.5, 27, 27.5, 28, 28.5, 29, 29.5, 30, 30.5, 31, 31.5, 32,
			32.5, 33, 33.5, 34, 34.5, 35, 35.5, 36, 36.5, 37, 37.5, 38, 38.5, 39, 39.5, 40, 40.5, 41, 41.5, 42, 42.5,
			43, 43.5, 44, 44.5, 45, 45.5, 46, 46.5, 47, 47.5, 48, 48.5, 49, 49.5 };

	double yaxis[] = { 0.00, 0.24, 0.84, 1.50, 1.82, 1.50, 0.42, -1.23, -3.03, -4.40, -4.79, -3.88, -1.68, 1.40, 4.60,
			7.03, 7.91, 6.79, 3.71, -0.71, -5.44, -9.24, -11.00, -10.07, -6.44, -0.83, 5.46, 10.85, 13.87, 13.56, 9.75,
			3.20, -4.61, -11.74, -16.34, -17.07, -13.52, -6.34, 2.85, 11.81, 18.26, 20.44, 17.57, 10.14, -0.19, -10.96,
			-19.46, -23.45, -21.73, -14.49, -3.31, 9.16, 19.83, 25.95, 25.82, 19.23, 7.59, -6.38, -19.25, -27.76,
			-29.64, -24.19, -12.53, 2.65, 17.65, 28.73, 33.00, 29.18, 17.99, 1.98, -14.99, -28.72, -35.70, -34.01,
			-23.81, -7.42, 11.26, 27.64, 37.59, 38.46, 29.80, 13.53, -6.50, -25.42, -38.49, -42.33, -35.77, -20.18,
			0.78, 22.02, 38.29, 45.44, 41.48, 27.17, 5.81, -17.45, -36.88, -47.58, -46.73, -34.30 };

	List<Double> X = new ArrayList<>();
	List<Double> Y = new ArrayList<>();
	private XYSeriesCollection dataset;

	public DrawChart(final String title) {

		super(title);
		series = new XYSeries("Sine", true, true);
		dataset = new XYSeriesCollection(series);
		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);
		final JButton button = new JButton("Add New Data Item");
		button.setActionCommand("ADD_DATA");
		button.addActionListener(this);

		final JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel);
		content.add(button, BorderLayout.SOUTH);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(content);

	}

	private void readFile2(File fin) {

		try {

			// Construct BufferedReader from FileReader
			BufferedReader br = new BufferedReader(new FileReader(fin));

			String line = null;
			while ((line = br.readLine()) != null) {

				System.out.println(line);

				if (line.startsWith("X")) {
					X.add(Double.valueOf(line.split(":")[1].trim()));
				} else if (line.startsWith("Y")) {
					Y.add(Double.valueOf(line.split(":")[1].trim()));
				}
			}

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JFreeChart createChart(final XYDataset dataset) {
		JFreeChart jfreechart = ChartFactory.createXYLineChart("Sin Curve", "Angle (Deg)", "Value", dataset,
				PlotOrientation.VERTICAL, true, true, true);
		jfreechart.setBackgroundPaint(Color.white);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		return jfreechart;
	}

	static boolean isREad = false;

	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equals("ADD_DATA")) {

			this.series.clear();
			DrawChart.this.dataset.removeSeries(series);

			for (int i = 0; i < xaxis.length; i++) {
				final double x = xaxis[i];// (i) / 10.0;
				final double y = yaxis[i];// Math.sin(x);

				this.series.addOrUpdate(x, y);
			}
			DrawChart.this.dataset.addSeries(series);

		}
	}
	
	public void addData(byte[]xAxis,byte[] yAxis ){
		
		this.series.clear();
		DrawChart.this.dataset.removeSeries(series);

		for (int i = 0; i < xAxis.length; i++) {
			final double x = xAxis[i];// (i) / 10.0;
			final double y = yAxis[i];// Math.sin(x);

			this.series.addOrUpdate(x, y);
		}
		DrawChart.this.dataset.addSeries(series);
	}

	public static void main(final String[] args) {

		final DrawChart demo = new DrawChart("Dynamic Data view");
		demo.pack();
		// RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}
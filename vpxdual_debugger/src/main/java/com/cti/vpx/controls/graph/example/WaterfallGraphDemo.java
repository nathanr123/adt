package com.cti.vpx.controls.graph.example;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import com.cti.vpx.controls.graph.sharedlibs.dsphostl.TimeStamp;
import com.cti.vpx.controls.graph.utilities.ui.graphs.waterfallGraph.IntensityWaterfallGraph;

/**
 *
 * @author Noel Grandin
 */
public class WaterfallGraphDemo extends javax.swing.JPanel {

	private final com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.GraphWrapper graphWrapper;
	private final IntensityWaterfallGraph lineGraph;

	private static final int WATERFALL_WIDTH = 1024;

	/** Creates new form GraphWithMultipleLines */
	public WaterfallGraphDemo() {
		initComponents();

		lineGraph = new IntensityWaterfallGraph();
		lineGraph.setGridXMinMax(0, 1024);
		lineGraph.setThresholdLimits(0, 255);
		lineGraph.setThresholdValues(10, 200);
		lineGraph.setGridVisible(true);
		lineGraph.setZoomEnabled(true);

		graphWrapper = new com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.GraphWrapper(lineGraph);
		graphWrapper.replaceYAxis(lineGraph.getWaterfallIntensityAxis());
		graphWrapper.setXAxisLabelVisible(true);
		graphWrapper.setXAxisScaleVisible(true);
		graphWrapper.setCursorCoordinatesVisible(true);
		graphWrapper.getXAxisExtraPanel().setVisible(true);
		graphWrapper.setYAxisLabelVisible(true);
		graphWrapper.setYAxisScaleVisible(false);

		//graphWrapper.setAxisTitles("", "Waterfall_Time_Axis");
	//	graphWrapper.setTitle("Waterfall Graph with Intensity Axis");
		graphWrapper.setAxisTitlesAndUnits("X", "Address", "Y", "Data");

		graphPanel.add(graphWrapper, BorderLayout.CENTER);

		new javax.swing.Timer(50, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final float[] data = getFile("");

				// System.out.println((int) Math.round(WATERFALL_WIDTH * 0.4));
				// System.out.println((int) Math.round(WATERFALL_WIDTH * 0.6));
				// data[(int) Math.round(WATERFALL_WIDTH * 0.4)] += 40;
				// data[(int) Math.round(WATERFALL_WIDTH * 0.6)] += 40;

				lineGraph.setAmplitudeData(new TimeStamp(), data);
			}
		});// .start();

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					try {
						addAmplitudeData(getFile());

						Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}
		});

		th.start();
	}

	Random random = new Random();

	String[] names = { "D://tem0.bin", "D://tem1.bin", "D://tem2.bin", "D://tem3.bin", "D://tem4.bin", "D://tem5.bin" };
	boolean b = false;

	public float[] getFile(String fileName) {
		float[] f = null;
		try {
			Path path = Paths.get("D://tem.bin");

			int randomNumber = random.nextInt(5) + 0;

			path = Paths.get(names[randomNumber]);

			byte[] data = Files.readAllBytes(path);

			f = toFloatArray(data);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return f;

	}

	public byte[] getFile() {

		byte[] f = null;

		try {
			Path path = Paths.get("D://tem.bin");

			int randomNumber = random.nextInt(5) + 0;

			path = Paths.get(names[randomNumber]);

			f = Files.readAllBytes(path);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return f;

	}

	public void addAmplitudeData(byte[] bytes) {

		final float[] data = toFloatArray(bytes);

		// data[(int) Math.round(bytes.length * 0.4)] += 40;
		// data[(int) Math.round(bytes.length * 0.6)] += 40;

		lineGraph.setAmplitudeData(new TimeStamp(), data);
	}

	private static float[] toFloatArray(byte[] bytes) {

		float[] floatArray = new float[bytes.length];

		for (int i = 0; i < floatArray.length; i++) {

			floatArray[i] = (float) (bytes[i] & 0x0ff) / 100;

			// System.out.println(floatArray[i]);
		}

		return floatArray;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		graphPanel = new javax.swing.JPanel();
		controlPanel = new javax.swing.JPanel();

		setLayout(new java.awt.BorderLayout());

		graphPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		graphPanel.setLayout(new java.awt.BorderLayout());
		add(graphPanel, java.awt.BorderLayout.CENTER);

		controlPanel.setLayout(new java.awt.GridBagLayout());
		// add(controlPanel, java.awt.BorderLayout.EAST);
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel controlPanel;
	private javax.swing.JPanel graphPanel;
	// End of variables declaration//GEN-END:variables

}

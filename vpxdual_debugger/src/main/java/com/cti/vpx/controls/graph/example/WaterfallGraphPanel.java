package com.cti.vpx.controls.graph.example;

import java.awt.BorderLayout;
import java.awt.Color;

import com.cti.vpx.controls.graph.sharedlibs.dsphostl.TimeStamp;
import com.cti.vpx.controls.graph.utilities.ui.graphs.waterfallGraph.IntensityWaterfallGraph;

public class WaterfallGraphPanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8892692800920557849L;

	private final com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.GraphWrapper graphWrapper;

	private final IntensityWaterfallGraph lineGraph;

	/** Creates new form GraphWithMultipleLines */
	public WaterfallGraphPanel() {

		initComponents();

		lineGraph = new IntensityWaterfallGraph();

		lineGraph.setGridXMinMax(0, 1024);

		lineGraph.setThresholdLimits(0, 255);

		lineGraph.setThresholdValues(10, 200);

		lineGraph.setGridVisible(false);

		lineGraph.setZoomEnabled(false);

		graphWrapper = new com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.GraphWrapper(lineGraph);

		graphWrapper.replaceYAxis(lineGraph.getWaterfallIntensityAxis());

		graphWrapper.setXAxisLabelVisible(false);

		graphWrapper.setXAxisScaleVisible(false);

		graphWrapper.setCursorCoordinatesVisible(false);

		graphWrapper.getXAxisExtraPanel().setVisible(false);

		graphWrapper.setYAxisLabelVisible(false);

		graphWrapper.setYAxisScaleVisible(false);

		graphWrapper.setAxisTitlesAndUnits("X", "Address", "Y", "Data");

		graphPanel.add(graphWrapper, BorderLayout.CENTER);
	}

	public void clearAlldata() {

		lineGraph.clear();

	}

	public void addWaterfallData(byte[] bytes) {

		final float[] data = toFloatArray(bytes);

		lineGraph.setAmplitudeData(new TimeStamp(), data);
	}

	public void addWaterfallData(float[] values) {

		lineGraph.setAmplitudeData(new TimeStamp(), values);
	}

	private static float[] toFloatArray(byte[] bytes) {

		float[] floatArray = new float[bytes.length];

		for (int i = 0; i < floatArray.length; i++) {

			floatArray[i] = (float) (bytes[i] & 0x0ff) / 100;
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

		setBackground(Color.BLACK);

		graphPanel = new javax.swing.JPanel();

		graphPanel.setBackground(Color.BLACK);

		controlPanel = new javax.swing.JPanel();

		controlPanel.setBackground(Color.BLACK);

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

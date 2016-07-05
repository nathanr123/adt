package com.cti.vpx.controls.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.cti.vpx.controls.graph.utilities.ui.graphs.lineGraph.GeneratedLineData;
import com.cti.vpx.controls.graph.utilities.ui.graphs.lineGraph.MultiLineGraph;
import com.cti.vpx.util.VPXLogger;

public class VPX_MultipleLine extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2502405281767525187L;
	private static final String LINE_1 = "Line1";
	private static final String LINE_2 = "Line2";

	private static final int LINEID_1 = 1;

	private static final int LINEID_2 = 2;

	private final com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.GraphWrapper graphWrapper;
	private final MultiLineGraph lineGraph;
	private GeneratedLineData line1Data;
	private GeneratedLineData line2Data;

//	private final javax.swing.Timer dataTimer;
	//private final javax.swing.Timer dataTimer1;
	private byte[] plot1Bytes;
	private byte[] plot2Bytes;
	private boolean isPlot1 = false;
	private boolean isPlot2 = false;

	/** Creates new form GraphWithMultipleLines */
	public VPX_MultipleLine() {
		initComponents();

		lineGraph = new MultiLineGraph();

		lineGraph.setFrameLimitingEnabled(true);
		lineGraph.setGridVisible(true);
		lineGraph.setZoomEnabled(true);

		graphWrapper = new com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.GraphWrapper(lineGraph);
		// graphWrapper.setTitle("Multiple Line Graph");
		graphWrapper.setAxisTitlesAndUnits("X", "Address", "Y", "Data");

		graphPanel.add(graphWrapper, BorderLayout.CENTER);

		lineGraph.setLineColor(LINE_1, Color.RED);
		line1ColorComboBox.setSelectedItem("Red");
		lineGraph.setLineColor(LINE_2, Color.GREEN);
		line2ColorComboBox.setSelectedItem("Green");

		line1Data = new GeneratedLineData(0, 0, 0, new float[0]);

		line2Data = new GeneratedLineData(0, 0, 0, new float[0]);

		//lineGraph.setGridYMinMax(0, 255);

		/*dataTimer = new javax.swing.Timer(500, new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				float[] floats = getFile("D:\\test.bin");

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(0, floats.length);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
		});

		// dataTimer.start();

		dataTimer1 = new javax.swing.Timer(550, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				float[] floats = getFile("D:\\rews.bin");

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);

			}
		});

		// dataTimer1.start();
*/	
	}

	public float[] getFile(String fileName) {
		float[] f = null;
		try {
			Path path = Paths.get(fileName);

			byte[] data = Files.readAllBytes(path);

			f = toFloatArray(data);

		} catch (IOException e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}

		return f;

	}

	public void clearAll() {

		lineGraph.clear();

		line1Data = new GeneratedLineData(0, 0, 0, new float[0]);

		line2Data = new GeneratedLineData(0, 0, 0, new float[0]);

		//lineGraph.setGridYMinMax(0, 255);
	}

	public void setBytes(byte[] bytes1, byte[] bytes2) {

		float[] floats = toFloatArray(bytes1);

		line1Data.setXValues(0, floats.length, floats.length);

		lineGraph.setGridXMinMax(0, floats.length);

		lineGraph.setGridYMinMax(0, 255);

		final float[] yValues1 = new float[floats.length];

		System.arraycopy(floats, 0, yValues1, 0, floats.length);

		line1Data.setYValues(yValues1);

		lineGraph.setGraphData(LINE_1, line1Data);

		floats = toFloatArray(bytes2);

		line2Data.setXValues(0, floats.length, floats.length);

		final float[] yValues2 = new float[floats.length];

		line2Data.setYValues(yValues2);

		System.arraycopy(floats, 0, yValues2, 0, floats.length);

		lineGraph.setGraphData(LINE_2, line2Data);

	}

	public void setBytes(int lineID, byte[] bytes) {

		if (lineID == LINEID_1) {

			plot1Bytes = bytes;

			isPlot1 = true;

		} else if (lineID == LINEID_2) {

			plot2Bytes = bytes;

			isPlot2 = true;
		}

		reDraw(1);

		/*
		 * float[] floats = toFloatArray(bytes);
		 * 
		 * if (lineID == LINEID_1) {
		 * 
		 * plot1Bytes = bytes;
		 * 
		 * isPlot1 = true;
		 * 
		 * line1Data.setXValues(0, floats.length, floats.length);
		 * 
		 * lineGraph.setGridXMinMax(0, floats.length);
		 * 
		 * final float[] yValues1 = new float[floats.length];
		 * 
		 * System.arraycopy(floats, 0, yValues1, 0, floats.length);
		 * 
		 * line1Data.setYValues(yValues1);
		 * 
		 * lineGraph.setGraphData(LINE_1, line1Data);
		 * 
		 * } else if (lineID == LINEID_2) {
		 * 
		 * plot2Bytes = bytes;
		 * 
		 * isPlot2 = true;
		 * 
		 * line2Data.setXValues(0, floats.length, floats.length);
		 * 
		 * final float[] yValues2 = new float[floats.length];
		 * 
		 * System.arraycopy(floats, 0, yValues2, 0, floats.length);
		 * 
		 * line2Data.setYValues(yValues2);
		 * 
		 * lineGraph.setGraphData(LINE_2, line2Data); }
		 */
	}

	public void reDraw(int format) {

		lineGraph.clear();

		switch (format) {
		case 0:

			if (isPlot1) {

				float[] floats = toFloatArray(plot1Bytes);

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(0,255);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
			if (isPlot2) {

				float[] floats = toFloatArray(plot2Bytes);

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);
			}

			break;

		case 1:

			if (isPlot1) {

				float[] floats = toFloatArray(plot1Bytes, format);

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(floats.length,floats.length);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
			if (isPlot2) {

				float[] floats = toFloatArray(plot2Bytes, format);

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);
			}

			break;

		case 2:

			if (isPlot1) {

				float[] floats = toFloatArray(plot1Bytes);

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(0, floats.length);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
			if (isPlot2) {

				float[] floats = toFloatArray(plot2Bytes);

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);
			}

			break;

		case 3:

			if (isPlot1) {

				float[] floats = toFloatArray(plot1Bytes);

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(0, floats.length);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
			if (isPlot2) {

				float[] floats = toFloatArray(plot2Bytes);

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);
			}

			break;

		case 4:

			if (isPlot1) {

				float[] floats = toFloatArray(plot1Bytes);

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(0, floats.length);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
			if (isPlot2) {

				float[] floats = toFloatArray(plot2Bytes);

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);
			}

			break;

		case 5:

			if (isPlot1) {

				float[] floats = toFloatArray(plot1Bytes);

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(0, floats.length);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
			if (isPlot2) {

				float[] floats = toFloatArray(plot2Bytes);

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);
			}

			break;

		case 6:

			if (isPlot1) {

				float[] floats = toFloatArray(plot1Bytes);

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(0, floats.length);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
			if (isPlot2) {

				float[] floats = toFloatArray(plot2Bytes);

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);
			}

			break;

		case 7:

			if (isPlot1) {

				float[] floats = toFloatArray(plot1Bytes);

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(0, floats.length);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
			if (isPlot2) {

				float[] floats = toFloatArray(plot2Bytes);

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);
			}

			break;

		case 8:

			if (isPlot1) {

				float[] floats = toFloatArray(plot1Bytes);

				line1Data.setXValues(0, floats.length, floats.length);

				lineGraph.setGridXMinMax(-floats.length, floats.length);

				final float[] yValues1 = new float[floats.length];

				System.arraycopy(floats, 0, yValues1, 0, floats.length);

				line1Data.setYValues(yValues1);

				lineGraph.setGraphData(LINE_1, line1Data);

			}
			if (isPlot2) {

				float[] floats = toFloatArray(plot2Bytes);

				line2Data.setXValues(0, floats.length, floats.length);

				final float[] yValues2 = new float[floats.length];

				System.arraycopy(floats, 0, yValues2, 0, floats.length);

				line2Data.setYValues(yValues2);

				lineGraph.setGraphData(LINE_2, line2Data);
			}

			break;

		}

	}

	private static float[] toFloatArray(byte[] bytes) {

		float[] floatArray = new float[bytes.length];

		for (int i = 0; i < floatArray.length; i++) {

			floatArray[i] = (float) (bytes[i] & 0x0ff);
		}

		return floatArray;
	}

	private float[] toFloatArray(byte[] bytes, int format) {

		float[] floatArr = null;

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

			float[] floatArray = new float[len];

			for (int i = 0; i < floatArray.length; i++) {

				floatArray[i] = (float) (bytes[i] & 0x0ff);
			}

			floatArr = floatArray;

		} else if (format == 1) {// 16 Bit Hex

			len = bytes.length / 2;

			float[] floatArray = new float[len];

			byte[] bb = null;

			for (int i = 0; i < floatArray.length; i++) {

				bb = getByteByGroup(bytes, i, 2);

				floatArray[i] = (float) new BigInteger(1, bb).intValue();
			}

			floatArr = floatArray;

		} else if (format == 2) {// 32 Bit Hex

			len = bytes.length / 4;

			float[] floatArray = new float[len];

			byte[] bb = null;

			for (int i = 0; i < floatArray.length; i++) {

				bb = getByteByGroup(bytes, i, 4);

				floatArray[i] = (float) new BigInteger(1, bb).intValue();
			}

			floatArr = floatArray;

		} else if (format == 3) {// 64 Bit Hex

			len = bytes.length / 8;

			float[] floatArray = new float[len];

			byte[] bb = null;

			for (int i = 0; i < floatArray.length; i++) {

				bb = getByteByGroup(bytes, i, 8);

				floatArray[i] = (float) new BigInteger(1, bb).intValue();
			}

			floatArr = floatArray;

		} else if (format == 4) {// 16 Bit Signed

			len = bytes.length / 2;

		} else if (format == 5) {// 32 Bit Signed

			len = bytes.length / 4;

			float[] floatArray = new float[len];

			byte[] bb = null;

			for (int i = 0; i < floatArray.length; i++) {

				bb = getByteByGroup(bytes, i, 4);

				floatArray[i] = (float) new BigInteger(1, bb).intValue();
			}

			floatArr = floatArray;

		} else if (format == 6) {// 16 Bit Unsigned

			len = bytes.length / 2;

		} else if (format == 7) {// 32 Bit Unsigned

			len = bytes.length / 4;

			float[] floatArray = new float[len];

			byte[] bb = null;

			for (int i = 0; i < floatArray.length; i++) {

				bb = getByteByGroup(bytes, i, 4);

				floatArray[i] = (float) new BigInteger(1, bb).intValue();
			}

			floatArr = floatArray;

		} else if (format == 8) {// 32 Bit Floating

			len = bytes.length / 4;

			float[] floatArray = new float[len];

			byte[] bb = null;

			for (int i = 0; i < floatArray.length; i++) {

				bb = getByteByGroup(bytes, i, 4);

				floatArray[i] = (float) new BigInteger(1, bb).intValue();
			}

			floatArr = floatArray;

		}

		return floatArr;
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

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		graphPanel = new javax.swing.JPanel();
		controlPanel = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		dataPeriodSpinner = new javax.swing.JSpinner();
		gridVisibleCheckBox = new javax.swing.JCheckBox();
		frameRateLimitingCheckBox = new javax.swing.JCheckBox();
		zoomEnabledCheckBox = new javax.swing.JCheckBox();
		jLabel1 = new javax.swing.JLabel();
		line1ColorComboBox = new javax.swing.JComboBox<String>();
		jLabel2 = new javax.swing.JLabel();
		line2ColorComboBox = new javax.swing.JComboBox<String>();
		framePeriodSpinner = new javax.swing.JSpinner();
		jLabel3 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		noXPointsSpinner = new javax.swing.JSpinner();
		jSeparator1 = new javax.swing.JSeparator();

		setLayout(new java.awt.BorderLayout());

		graphPanel.setLayout(new java.awt.BorderLayout());
		add(graphPanel, java.awt.BorderLayout.CENTER);

		controlPanel.setLayout(new java.awt.GridBagLayout());

		jLabel4.setText("Data Period (ms)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(4, 13, 0, 0);
		controlPanel.add(jLabel4, gridBagConstraints);

		dataPeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(500, 1, 10000, 1));
		dataPeriodSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				dataPeriodSpinnerStateChanged(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
		controlPanel.add(dataPeriodSpinner, gridBagConstraints);

		gridVisibleCheckBox.setSelected(true);
		gridVisibleCheckBox.setText("Grid Visible");
		gridVisibleCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				gridVisibleCheckBoxActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(32, 8, 0, 0);
		controlPanel.add(gridVisibleCheckBox, gridBagConstraints);

		frameRateLimitingCheckBox.setSelected(true);
		frameRateLimitingCheckBox.setText("Frame Rate Limiting");
		frameRateLimitingCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				frameRateLimitingCheckBoxActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 0, 0);
		controlPanel.add(frameRateLimitingCheckBox, gridBagConstraints);

		zoomEnabledCheckBox.setSelected(true);
		zoomEnabledCheckBox.setText("Zoom Enabled");
		zoomEnabledCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				zoomEnabledCheckBoxActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 0, 0);
		controlPanel.add(zoomEnabledCheckBox, gridBagConstraints);

		jLabel1.setText("Line 1 Color");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(8, 13, 0, 0);
		controlPanel.add(jLabel1, gridBagConstraints);

		line1ColorComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Red", "Green", "Blue" }));
		line1ColorComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				line1ColorComboBoxActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(8, 4, 0, 0);
		controlPanel.add(line1ColorComboBox, gridBagConstraints);

		jLabel2.setText("Line 2 Color");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(4, 13, 0, 0);
		controlPanel.add(jLabel2, gridBagConstraints);

		line2ColorComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Red", "Green", "Blue" }));
		line2ColorComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				line2ColorComboBoxActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
		controlPanel.add(line2ColorComboBox, gridBagConstraints);

		framePeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(50, 1, 1000, 1));
		framePeriodSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				framePeriodSpinnerStateChanged(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
		controlPanel.add(framePeriodSpinner, gridBagConstraints);

		jLabel3.setText("Frame Period (ms)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(4, 20, 0, 0);
		controlPanel.add(jLabel3, gridBagConstraints);

		jLabel5.setText("No. X Points");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(4, 13, 0, 0);
		controlPanel.add(jLabel5, gridBagConstraints);

		noXPointsSpinner.setModel(new javax.swing.SpinnerNumberModel(1024, 1, 5000, 1));
		noXPointsSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				noXPointsSpinnerStateChanged(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
		controlPanel.add(noXPointsSpinner, gridBagConstraints);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
		controlPanel.add(jSeparator1, gridBagConstraints);

		// add(controlPanel, java.awt.BorderLayout.EAST);
	}// </editor-fold>//GEN-END:initComponents

	private void gridVisibleCheckBoxActionPerformed( java.awt.event.ActionEvent evt) {// GEN-FIRST:event_gridVisibleCheckBoxActionPerformed
		lineGraph.setGridVisible(gridVisibleCheckBox.isSelected());
	}// GEN-LAST:event_gridVisibleCheckBoxActionPerformed

	private void zoomEnabledCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_zoomEnabledCheckBoxActionPerformed
		lineGraph.setZoomEnabled(zoomEnabledCheckBox.isSelected());
	}// GEN-LAST:event_zoomEnabledCheckBoxActionPerformed

	private void frameRateLimitingCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_frameRateLimitingCheckBoxActionPerformed
		lineGraph.setFrameLimitingEnabled(frameRateLimitingCheckBox.isSelected());
		framePeriodSpinner.setEnabled(frameRateLimitingCheckBox.isSelected());
	}// GEN-LAST:event_frameRateLimitingCheckBoxActionPerformed

	private void line1ColorComboBoxActionPerformed( java.awt.event.ActionEvent evt) {// GEN-FIRST:event_line1ColorComboBoxActionPerformed
		lineGraph.setLineColor(LINE_1, toColor((String) line1ColorComboBox.getSelectedItem()));
	}// GEN-LAST:event_line1ColorComboBoxActionPerformed

	private void line2ColorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_line2ColorComboBoxActionPerformed
		lineGraph.setLineColor(LINE_2, toColor((String) line2ColorComboBox.getSelectedItem()));
	}// GEN-LAST:event_line2ColorComboBoxActionPerformed

	private void framePeriodSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_framePeriodSpinnerStateChanged
		lineGraph.setFrameLimitingPeriod((Integer) framePeriodSpinner.getValue());
	}// GEN-LAST:event_framePeriodSpinnerStateChanged

	private void dataPeriodSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_dataPeriodSpinnerStateChanged
		//dataTimer.setDelay((Integer) dataPeriodSpinner.getValue());
	}// GEN-LAST:event_dataPeriodSpinnerStateChanged

	private void noXPointsSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_noXPointsSpinnerStateChanged
	}// GEN-LAST:event_noXPointsSpinnerStateChanged

	private final Color toColor(String s) {
		if (s.equalsIgnoreCase("red")) {
			return Color.RED;
		}
		if (s.equalsIgnoreCase("blue")) {
			return Color.BLUE;
		}
		if (s.equalsIgnoreCase("green")) {
			return Color.GREEN;
		}
		throw new IllegalStateException(s);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel controlPanel;
	private javax.swing.JSpinner dataPeriodSpinner;
	private javax.swing.JSpinner framePeriodSpinner;
	private javax.swing.JCheckBox frameRateLimitingCheckBox;
	private javax.swing.JPanel graphPanel;
	private javax.swing.JCheckBox gridVisibleCheckBox;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JComboBox<String> line1ColorComboBox;
	private javax.swing.JComboBox<String> line2ColorComboBox;
	private javax.swing.JSpinner noXPointsSpinner;
	private javax.swing.JCheckBox zoomEnabledCheckBox;
	// End of variables declaration//GEN-END:variables

}

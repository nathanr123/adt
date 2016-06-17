package com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * This is similar to CoordinatesPanel, except that the x and y values are
 * arrange vertically, rather than horizontally.
 * 
 * FIXME make this simply a layout option of CoordinatesPanel.
 */
public class SimpleCoordinatePanel extends JPanel {
	private static final long serialVersionUID = -4685517561486806591L;

	public SimpleCoordinatePanel() {
		initComponents();
	}

	public String getMainLabel() {
		return this.oMainLabel.getText();
	}

	public void setMainLabel(final String mainLabel) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				oMainLabel.setText(mainLabel);
			}
		});
	}

	public void setMainLabelColor(final Color color) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				oMainLabel.setForeground(color);
			}
		});
	}

	public void setLabels(final String xLabel, final String yLabel) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				oXLabel.setText(xLabel);
				oYLabel.setText(yLabel);
			}
		});
	}

	public void setValues(final String x, final String y) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				oXValue.setText(x);
				oYValue.setText(y);
			}
		});
	}

	public void setXValue(final String value) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				oXValue.setText(value);
			}
		});
	}

	public void setYValue(final String value) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				oYValue.setText(value);
			}
		});
	}

	public void setValueLabelColor(Color color) {
		this.oXValue.setForeground(color);
		this.oYValue.setForeground(color);
	}

	public void setUnits(final String xUnit, final String yUnit) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				oXUnit.setText(xUnit);
				oYUnit.setText(yUnit);
			}
		});
	}

	public void setSamplePattern(String... samplePattern) {
		oXValue.setSamplePatterns(samplePattern);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
	// ">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		oMainLabel = new javax.swing.JLabel();
		oXLabel = new javax.swing.JLabel();
		oXValue = new com.cti.vpx.controls.graph.utilities.ui.FixedSizeLabel();
		oXUnit = new javax.swing.JLabel();
		oYLabel = new javax.swing.JLabel();
		oYValue = new com.cti.vpx.controls.graph.utilities.ui.FixedSizeLabel();
		oYUnit = new javax.swing.JLabel();

		setLayout(new java.awt.GridBagLayout());

		setBackground(new java.awt.Color(255, 255, 255));
		setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
		add(oMainLabel, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		add(oXLabel, gridBagConstraints);

		oXValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(oXValue, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		add(oXUnit, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		add(oYLabel, gridBagConstraints);

		oYValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(oYValue, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		add(oYUnit, gridBagConstraints);

	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel oMainLabel;
	private javax.swing.JLabel oXLabel;
	private javax.swing.JLabel oXUnit;
	private com.cti.vpx.controls.graph.utilities.ui.FixedSizeLabel oXValue;
	private javax.swing.JLabel oYLabel;
	private javax.swing.JLabel oYUnit;
	private com.cti.vpx.controls.graph.utilities.ui.FixedSizeLabel oYValue;
	// End of variables declaration//GEN-END:variables
}

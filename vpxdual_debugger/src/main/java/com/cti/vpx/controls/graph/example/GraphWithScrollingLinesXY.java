package com.cti.vpx.controls.graph.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cti.vpx.controls.graph.utilities.ui.graphs.scrollingline.XYScrollingLineGraph;

public class GraphWithScrollingLinesXY extends javax.swing.JPanel {
  
	private static final long serialVersionUID = 8171528018180652594L;

	private static final double GRAPH_WINDOW = 1000;
	
	private static final String LINE_1 = "Line1";
	private static final String LINE_2 = "Line2";
	
	private final com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.GraphWrapper graphWrapper;
  private final XYScrollingLineGraph lineGraph;
  
  /** Creates new form GraphWithMultipleLines */
  public GraphWithScrollingLinesXY() {
    initComponents();
    
		lineGraph = new XYScrollingLineGraph(GRAPH_WINDOW);
    lineGraph.setGridYMinMax(-20, 20);
		lineGraph.setGridVisible(true);
		lineGraph.setZoomEnabled(true);
		
    graphWrapper = new com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.GraphWrapper(lineGraph);
    graphWrapper.setTitle("Scrolling Line Graph");
    graphWrapper.setAxisTitlesAndUnits("X", "froobles", "Y", "bagets");
    
    graphPanel.add(graphWrapper, BorderLayout.CENTER);
    
		lineGraph.setLineColor(LINE_1, Color.RED);
		line1ColorComboBox.setSelectedItem("Red");
		lineGraph.setLineColor(LINE_2, Color.GREEN);
		line2ColorComboBox.setSelectedItem("Green");
    
    new javax.swing.Timer(50, new ActionListener() {
    	private double x = 0;
    	public void actionPerformed(ActionEvent e)
    	{
    		float val1 = (float) (Math.random() * 10) - 10;
    		float val2 = (float) (Math.random() * 10);
    		lineGraph.addLineValue(LINE_1, x, val1);
    		lineGraph.addLineValue(LINE_2, x, val2);
    		x += 2;
    	}
    }).start();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    graphPanel = new javax.swing.JPanel();
    controlPanel = new javax.swing.JPanel();
    gridVisibleCheckBox = new javax.swing.JCheckBox();
    zoomEnabledCheckBox = new javax.swing.JCheckBox();
    jLabel1 = new javax.swing.JLabel();
    line1ColorComboBox = new javax.swing.JComboBox<String>();
    jLabel2 = new javax.swing.JLabel();
    line2ColorComboBox = new javax.swing.JComboBox<String>();

    setLayout(new java.awt.BorderLayout());

    graphPanel.setLayout(new java.awt.BorderLayout());
    add(graphPanel, java.awt.BorderLayout.CENTER);

    controlPanel.setLayout(new java.awt.GridBagLayout());

    gridVisibleCheckBox.setSelected(true);
    gridVisibleCheckBox.setText("Grid Visible");
    gridVisibleCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        gridVisibleCheckBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
    controlPanel.add(gridVisibleCheckBox, gridBagConstraints);

    zoomEnabledCheckBox.setSelected(true);
    zoomEnabledCheckBox.setText("Zoom Enabled");
    zoomEnabledCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        zoomEnabledCheckBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 8, 0, 0);
    controlPanel.add(zoomEnabledCheckBox, gridBagConstraints);

    jLabel1.setText("Line 1 Color");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 12, 0, 0);
    controlPanel.add(jLabel1, gridBagConstraints);

    line1ColorComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Red", "Green", "Blue" }));
    line1ColorComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        line1ColorComboBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
    controlPanel.add(line1ColorComboBox, gridBagConstraints);

    jLabel2.setText("Line 2 Color");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 12, 0, 0);
    controlPanel.add(jLabel2, gridBagConstraints);

    line2ColorComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Red", "Green", "Blue" }));
    line2ColorComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        line2ColorComboBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
    controlPanel.add(line2ColorComboBox, gridBagConstraints);

    add(controlPanel, java.awt.BorderLayout.EAST);
  }// </editor-fold>//GEN-END:initComponents

  private void gridVisibleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gridVisibleCheckBoxActionPerformed
		lineGraph.setGridVisible(gridVisibleCheckBox.isSelected());
  }//GEN-LAST:event_gridVisibleCheckBoxActionPerformed

  private void zoomEnabledCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomEnabledCheckBoxActionPerformed
		lineGraph.setZoomEnabled(zoomEnabledCheckBox.isSelected());
  }//GEN-LAST:event_zoomEnabledCheckBoxActionPerformed

  private void line1ColorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_line1ColorComboBoxActionPerformed
		lineGraph.setLineColor(LINE_1, toColor((String)line1ColorComboBox.getSelectedItem()));
  }//GEN-LAST:event_line1ColorComboBoxActionPerformed

  private void line2ColorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_line2ColorComboBoxActionPerformed
		lineGraph.setLineColor(LINE_2, toColor((String)line2ColorComboBox.getSelectedItem()));
  }//GEN-LAST:event_line2ColorComboBoxActionPerformed
  
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
  private javax.swing.JPanel graphPanel;
  private javax.swing.JCheckBox gridVisibleCheckBox;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JComboBox<String> line1ColorComboBox;
  private javax.swing.JComboBox<String> line2ColorComboBox;
  private javax.swing.JCheckBox zoomEnabledCheckBox;
  // End of variables declaration//GEN-END:variables
  
}

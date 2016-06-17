package com.cti.vpx.controls.graph.utilities.ui.graphs.hopperHistogram.singleChannel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.cti.vpx.controls.graph.utilities.locale.ILocaleListener;
import com.cti.vpx.controls.graph.utilities.locale.PeralexLibsBundle;
import com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.PixelUnitConverter;
import com.cti.vpx.controls.graph.utilities.ui.graphs.hopperHistogram.IHopperHistogram;

/**
 * This class creates a new cHistogramSingleChannel.
 */
public class HistogramSingleChannel extends JPanel implements IHopperHistogram, ILocaleListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4609770615049641701L;

	/** The resource bundle used for multilingual support */
	private ResourceBundle textRes = PeralexLibsBundle.getResource();
  
  /**
   * The current HistogramSingleChannelDrawSurface.
   */
  private final HistogramSingleChannelDrawSurface oDrawSurface;
  
  /**
   * This stores the Title of this Graph.
   */
  private String sTitleText; 
  
  /**
   * This is the format for the Coordinates.
   */
  private final DecimalFormat oCoordinatesFormat = new DecimalFormat("# ### ###.00");

	/**
	 * Flags to keep track of which fields have been set so.
	 * This is for i18n.
	 */
	private boolean bTitleSet = false;
	private boolean bXAxisTextSet = false;
	private boolean bYAxisTextSet = false;

  /**
   * Creates new form cHistogramDualChannel.
   */
  public HistogramSingleChannel()
  {
    initComponents();
     
    oDrawSurface = new HistogramSingleChannelDrawSurface();
    oDrawSurface.setAxes(xAxis, yAxis);
    oDrawSurfacePanel.add(oDrawSurface, BorderLayout.CENTER);
    
    oDrawSurface.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    oDrawSurface.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseExited(MouseEvent e)
      {
        oXCoordinateValue.setText("-");
        oYCoordinateValue.setText("-");
      }
    });
    
    oDrawSurface.addMouseMotionListener(new MouseMotionAdapter()
    {
		  @Override
      public void mouseMoved(final MouseEvent e)
      {
	  		float fCursorValue, fPowerMax, fPowerMin;
				// Set the X Coordinate
				fPowerMax = (float)(Math.log10(oDrawSurface.getXAxisMaximum()));
				fPowerMin = (float)(Math.log10(oDrawSurface.getXAxisMinimum()));						
				fCursorValue = (float)Math.pow(10, fPowerMin + ((fPowerMax - fPowerMin) * ((float)e.getX() / oDrawSurface.getWidth())));
				oXCoordinateValue.setText(oCoordinatesFormat.format(fCursorValue));

				// Set the Y Coordinate
        fCursorValue = (float)(PixelUnitConverter.pixelToUnit(false, e.getY(), 0, oDrawSurface.getHeight(), oDrawSurface.getYAxisMinimum(), oDrawSurface.getYAxisMaximum()));
        oYCoordinateValue.setText(oCoordinatesFormat.format(fCursorValue));			
      }
    });
    
		/*
		 * make the default title font be 20% bigger than the default font, and bold
		 */
		Font titleFont = oTitleLabel.getFont();
		titleFont = titleFont.deriveFont(titleFont.getSize() * 1.2f);
		titleFont = titleFont.deriveFont(Font.BOLD);
		oTitleLabel.setFont(titleFont);
		
		PeralexLibsBundle.addLocaleListener(this); //do after components have been initialised
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents()
  {
    java.awt.GridBagConstraints gridBagConstraints;

    oDrawSurfacePanel = new javax.swing.JPanel();
    oYAxisPanel = new javax.swing.JPanel();
    yAxisLabel = new com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.YAxisLabel();
    yAxis = new com.cti.vpx.controls.graph.utilities.ui.graphs.axisscale.NumberAxisScale();
    xAxis = new com.cti.vpx.controls.graph.utilities.ui.graphs.axisscale.NumberAxisScale();
    oXAxisLabel = new javax.swing.JLabel();
    oTopPanel = new javax.swing.JPanel();
    jPanel2 = new javax.swing.JPanel();
    oTitleLabel = new javax.swing.JLabel();
    oCoordinatesPanel = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    oXCoordinateValue = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    oYCoordinateValue = new javax.swing.JLabel();

    setLayout(new java.awt.GridBagLayout());

    setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
    oDrawSurfacePanel.setLayout(new java.awt.BorderLayout());

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    add(oDrawSurfacePanel, gridBagConstraints);

    oYAxisPanel.setLayout(new java.awt.GridBagLayout());

    yAxisLabel.setLayout(new java.awt.FlowLayout());

    yAxisLabel.setText(textRes.getString("Y-Axis"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weighty = 1.0;
    oYAxisPanel.add(yAxisLabel, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weighty = 1.0;
    oYAxisPanel.add(yAxis, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    add(oYAxisPanel, gridBagConstraints);

    xAxis.setOrientation(com.cti.vpx.controls.graph.utilities.ui.graphs.axisscale.AbstractDefaultAxisScale.X_AXIS);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    add(xAxis, gridBagConstraints);

    oXAxisLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    oXAxisLabel.setText(textRes.getString("X-Axis"));
    oXAxisLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
    gridBagConstraints.weightx = 1.0;
    add(oXAxisLabel, gridBagConstraints);

    oTopPanel.setLayout(new java.awt.GridBagLayout());

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    oTopPanel.add(jPanel2, gridBagConstraints);

    oTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    oTitleLabel.setText(textRes.getString("HopperHistogram.Title"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    oTopPanel.add(oTitleLabel, gridBagConstraints);

    oCoordinatesPanel.setLayout(new java.awt.GridBagLayout());

    oCoordinatesPanel.setBackground(new java.awt.Color(255, 255, 255));
    oCoordinatesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    jLabel1.setText("X:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 4);
    oCoordinatesPanel.add(jLabel1, gridBagConstraints);

    oXCoordinateValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    oXCoordinateValue.setText("# ### ###.00");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
    oCoordinatesPanel.add(oXCoordinateValue, gridBagConstraints);

    jLabel3.setText("Y:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 4);
    oCoordinatesPanel.add(jLabel3, gridBagConstraints);

    oYCoordinateValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    oYCoordinateValue.setText("# ### ###.00");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
    oCoordinatesPanel.add(oYCoordinateValue, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 2;
    gridBagConstraints.ipady = 2;
    oTopPanel.add(oCoordinatesPanel, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    add(oTopPanel, gridBagConstraints);

  }// </editor-fold>//GEN-END:initComponents
  
  /**
   * This method passes new data into the Histogram.
   *
   * @param asDimensionName the name of the dimension
   * @param asDimensionUnitName the name of units for the dimension
   * @param awNumPoints the number of points in the histogram for this dimension (ns)
   * @param afMinScaleValue minimum value for the scale of the dimension
   * @param afMaxScaleValue maximum value for the scale of the dimension
   * @param abIsLogarithmic is the dimension's scale logarithmic?
   * @param afHistogramData histogram data 
   */
  public synchronized void onDataReceived(String[] asDimensionName, String[] asDimensionUnitName, short[] awNumPoints, float[] afMinScaleValue, float[] afMaxScaleValue, boolean[] abIsLogarithmic, float[] afHistogramData)
  {
    String sXAxisText = asDimensionName[0] + " (" + asDimensionUnitName[0] + ")";
    setXAxisText( sXAxisText );
  
    // Set the X-Axis Minimum and Maximum if it is not the same as the current
    if (getXAxisMaximum() != afMaxScaleValue[0] || getXAxisMinimum() != afMinScaleValue[0])
    {
      setXAxisRange(afMinScaleValue[0], afMaxScaleValue[0]);
    }
    
    // Set the XAxis Logarithmic or not, if it is not already the same.
    if (abIsLogarithmic[0] != isXAxisLogarithmic())
    {
      setXAxisLogarithmic(abIsLogarithmic[0]);
    }
    
    oDrawSurface.onDataReceived(afHistogramData, afMinScaleValue[0], afMaxScaleValue[0]);
  }
  
  /**
   * This methods sets the XAxisRange.
   *
   * @param dXAxisMinimum contains the minimum
   * @param dXAxisMaximum contains the maximum
   * @param dYAxisMinimum contains the minimum
   * @param dYAxisMaximum contains the maximum
   */
  public void setAxisRanges(double dXAxisMinimum, double dXAxisMaximum, double dYAxisMinimum, double dYAxisMaximum)
  {       
    final double[] minimalCoveringRange = getMinimalCoveringRange( dXAxisMinimum,
                                                                   dXAxisMaximum );

    oDrawSurface.setAxisRanges( minimalCoveringRange[0],
                                minimalCoveringRange[1],
                                dYAxisMinimum,
                                dYAxisMaximum );
  }

  private static double[] getMinimalCoveringRange( double dXAxisMinimum,
                                            double dXAxisMaximum )
   {
     // Compute the nearest N and M that [10^N, 10^M] can fully include [dXAxisMinimum, dXAxisMaximum]
     
     // We do the following because there is no log10() in Java 1.4 and rounding off floating 
     // number can be tricky; But even in 1.5, which has log10, due to the imprecise representation 
     // of floating number, this may still be necessary.
     final int lowerBound = (int) Math.floor(Math.log10(dXAxisMinimum));
     final double tmpA = Math.pow(10, lowerBound);
     final double tmpB = Math.pow(10, lowerBound + 1);
     
     // tmpA is guaranteed to cover dXAxisMinimum, but it may be a bit too conservative. 
     // Try tmpB (the next value.) If tmpB is also covering dXAxisMinimum, use tmpB.  
     dXAxisMinimum = tmpB <= dXAxisMinimum ? tmpB : tmpA;
     
     // Similarly, if tmpC (the previous value) covers dXAxisMaximum, use it. 
     final int upperBound = (int) Math.ceil(Math.log10(dXAxisMaximum));
     final double tmpC = Math.pow(10, upperBound - 1 );
     final double tmpD = Math.pow(10, upperBound);
     dXAxisMaximum = tmpC >= dXAxisMaximum ? tmpC : tmpD;
     
     return new double[] { dXAxisMinimum, dXAxisMaximum };
   }
  
  /**
   * This methods sets the XAxisRange.
   *
   * @param dXAxisMinimum contains the minimum
   * @param dXAxisMaximum contains the maximum
   */
  public void setXAxisRange(double dXAxisMinimum, double dXAxisMaximum)
  {       
    final double[] minimalCoveringRange = getMinimalCoveringRange( dXAxisMinimum,
                                                                   dXAxisMaximum );
    
    oDrawSurface.setXAxisRange( minimalCoveringRange[0],
                                minimalCoveringRange[1] );
  }
  
  /**
   * This methods sets the YAxisRange.
   *
   * @param dYAxisMinimum contains the minimum
   * @param dYAxisMaximum contains the maximum
   */
  public void setYAxisRange(double dYAxisMinimum, double dYAxisMaximum)
  {       
    oDrawSurface.setYAxisRange(dYAxisMinimum, dYAxisMaximum);
  }  
  
  /**
   * Get the XAxis Minimum.
   *
   * @return dMinX.
   */
  public double getXAxisMinimum()
  {
    return oDrawSurface.getXAxisMinimum();
  }
  
  /**
   * Get the XAxis Maximum.
   *
   * @return dMaxX.
   */
  public double getXAxisMaximum()
  {
    return oDrawSurface.getXAxisMaximum();
  }  

  /**
   * Get the YAxis Minimum.
   *
   * @return dMinY.
   */
  public double getYAxisMinimum()
  {
    return oDrawSurface.getYAxisMinimum();
  }
  
  /**
   * Get the YAxis Maximum.
   *
   * @return dMaxY.
   */
  public double getYAxisMaximum()
  {
    return oDrawSurface.getYAxisMaximum();
  }

  /**
   * Set the Title of the Graph.
   */
  public void setTitle(final String sTitleText)
  {
		bTitleSet = true;
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        oTitleLabel.setText(" " + sTitleText + " ");
      }
    });
  }
  
  /**
   * Get the Title of the Graph.
   */
  public String getTitle()
  {
    return sTitleText;
  }
  
  /**
   * Set the Text of the X-Axis.
   */
  public void setXAxisText(final String sXAxisText)
  {
		bXAxisTextSet = true;
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        oXAxisLabel.setText(" " + sXAxisText + " ");
      }
    });
  }
  
  /**
   * Get the Text of the X-Axis.
   *
   * @return XAxisTitle
   */
  public String getXAxisText()
  {
    return oXAxisLabel.getText();
  }
  
  /**
   * Set the Text of the Y-Axis.
   *
   * @param sYAxisText
   */
  public void setYAxisText(final String sYAxisText)
  {
		bYAxisTextSet = true;
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        yAxisLabel.setText(" " + sYAxisText + " ");
      }
    });
  }
  
  /**
   * Get the Text of the Y-Axis.
   *
   * @return YAxisTitle
   */
  public String getYAxisText()
  {
    return yAxisLabel.getText();
  }    
  
  /**
   * This method is used to Clear the graph.
   */
  public void clear()
  {
    oDrawSurface.clear();
  }
  
  /**
   * This method sets the Background color of this graph.
   *
   * @param oBackgroundColor contains the new color.
   */
  public void setBackgroundColor(Color oBackgroundColor)
  {
    oDrawSurface.setBackgroundColor(oBackgroundColor);
  }
  
  /**
   * This method sets the Grid Color of this graph.
   *
   * @param oGridColor contains the new color.
   */
  public void setGridColor(Color oGridColor)
  {
    oDrawSurface.setGridColor(oGridColor);
  }
  
  /**
   * This method sets whether the XAxis is Logarithmic or not.
   */  
  public void setXAxisLogarithmic(boolean bXAxisLogarithmic)
  {
    oDrawSurface.setXAxisLogarithmic(bXAxisLogarithmic);
  }
  
  /**
   * This method returns whether the XAxis is Logarithmic or not.
   *
   * @return bXAxisLogarithmic.
   */  
  public boolean isXAxisLogarithmic()
  {
    return oDrawSurface.isXAxisLogarithmic();
  }
	
  /**
   * This method is called when the locale has been changed. The listener should
	 * then update the visual components.
   */
	public void componentsLocaleChanged()
	{
		textRes = PeralexLibsBundle.getResource();
		
		if (!bTitleSet)
		{
			oTitleLabel.setText(textRes.getString("HopperHistogram.Title"));
		}
		if (!bXAxisTextSet)
		{
			oXAxisLabel.setText(textRes.getString("X-Axis"));
		}
		if (!bYAxisTextSet)
		{
			yAxisLabel.setText(textRes.getString("Y-Axis"));
		}
	}
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel oCoordinatesPanel;
  private javax.swing.JPanel oDrawSurfacePanel;
  private javax.swing.JLabel oTitleLabel;
  private javax.swing.JPanel oTopPanel;
  private javax.swing.JLabel oXAxisLabel;
  private javax.swing.JLabel oXCoordinateValue;
  private javax.swing.JPanel oYAxisPanel;
  private javax.swing.JLabel oYCoordinateValue;
  private com.cti.vpx.controls.graph.utilities.ui.graphs.axisscale.AbstractDefaultAxisScale xAxis;
  private com.cti.vpx.controls.graph.utilities.ui.graphs.axisscale.AbstractDefaultAxisScale yAxis;
  private com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.YAxisLabel yAxisLabel;
  // End of variables declaration//GEN-END:variables
}

package com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * A panel that displays X and Y co-ordinates of the current mouse position.
 * 
 * This version of the co-ordinates panel is intended for graphs like the waterfall graph
 * where the co-ordinates may not be simple numbers.
 * 
 * It has a timer to limit the rate of GUI update events.
 */
public class TextCoordinatesPanel extends javax.swing.JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1624150511502738588L;

	/**
   * Timer to delay setting of Coordinate Labels.
   */
	private final Timer oCoordinateLabelTimer;

	private String fXVal;
	private String fYVal;
	
  /** Creates new form cCoordinatesPanel */
  public TextCoordinatesPanel()
  {
    initComponents();
    
		oCoordinateLabelTimer = new javax.swing.Timer(50, new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				oXCoordinateValue.setText(fXVal);
				oYCoordinateValue.setText(fYVal);
			}
		});
		oCoordinateLabelTimer.setRepeats(false);

  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents()
  {
    oXCoordinateLabel = new javax.swing.JLabel();
    oXCoordinateValue = new com.cti.vpx.controls.graph.utilities.ui.FixedSizeLabel();
    oYCoordinateLabel = new javax.swing.JLabel();
    oYCoordinateValue = new com.cti.vpx.controls.graph.utilities.ui.FixedSizeLabel();

    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

    setBackground(new java.awt.Color(255, 255, 255));
    setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    oXCoordinateLabel.setText(" X: ");
    add(oXCoordinateLabel);

    oXCoordinateValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    add(oXCoordinateValue);

    oYCoordinateLabel.setText(" Y: ");
    add(oYCoordinateLabel);

    oYCoordinateValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    add(oYCoordinateValue);

  }// </editor-fold>//GEN-END:initComponents
  
  @Override
  public void setFont(Font font)
  {
  	super.setFont(font);
  	// have to check for null because we get called during construction
  	if (oXCoordinateLabel!=null)
  	{
			oXCoordinateLabel.setFont(font);
			oXCoordinateValue.setFont(font);
			oYCoordinateLabel.setFont(font);
			oYCoordinateValue.setFont(font);
  	}
  }
  
  public void setSamplePattern(String sXSamplePattern, String sYSamplePattern)
  {
  	oXCoordinateValue.setSamplePattern(sXSamplePattern);
  	oYCoordinateValue.setSamplePattern(sYSamplePattern);
  }

  public void setXSamplePattern(String sXSamplePattern)
  {
  	oXCoordinateValue.setSamplePattern(sXSamplePattern);
  }
  
  public void setYSamplePattern(String sYSamplePattern)
  {
  	oYCoordinateValue.setSamplePattern(sYSamplePattern);
  }
  
  public void setSamplePatterns(String [] sXSamplePattern, String [] sYSamplePattern)
  {
  	oXCoordinateValue.setSamplePatterns(sXSamplePattern);
  	oYCoordinateValue.setSamplePatterns(sYSamplePattern);
  }
  
  public void setXSamplePatterns(String [] sXSamplePattern)
  {
  	oXCoordinateValue.setSamplePatterns(sXSamplePattern);
  }

  public void setYSamplePatterns(String [] sYSamplePattern)
  {
  	oYCoordinateValue.setSamplePatterns(sYSamplePattern);
  }
  
  public void setCoordinates(String fXVal, String fYVal)
  {
		this.fXVal = fXVal;
		this.fYVal = fYVal;
		oCoordinateLabelTimer.start();
  }
  
  public void setXCoordinate(String fXVal)
  {
		this.fXVal = fXVal;
		oCoordinateLabelTimer.start();
  }
  
  public void setYCoordinate(String fYVal)
  {
		this.fYVal = fYVal;
		oCoordinateLabelTimer.start();
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel oXCoordinateLabel;
  private com.cti.vpx.controls.graph.utilities.ui.FixedSizeLabel oXCoordinateValue;
  private javax.swing.JLabel oYCoordinateLabel;
  private com.cti.vpx.controls.graph.utilities.ui.FixedSizeLabel oYCoordinateValue;
  // End of variables declaration//GEN-END:variables
  
}

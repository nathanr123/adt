package com.peralex.utilities.ui.graphs.hopperHistogram.dualChannel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 *
 * @author  Andre
 */
public class ProbabilityFactorColorPanel extends JPanel
{
  
  /** 
   * Creates new form oProbabilityFactorColorPanel 
   */
  public ProbabilityFactorColorPanel()
  {
    initComponents();
  }
  
	/**
	 * calculate my preferred size based on the font used
	 */
	@Override
	public Dimension getPreferredSize()
	{
		// base the width on the width of the format used
		final Rectangle2D rect = getFontMetrics(getFont()).getStringBounds(">=0.7m", getGraphics());
		// only the width matters - my container should resize my height to fit the graph
		return new Dimension((int) (rect.getWidth() * 1.1), 1);
	}

	@Override
	public Dimension getMinimumSize() 
	{
		return getPreferredSize();
	}
	
  /**
   * This panel's Paint method.
   */
	@Override
  public void paint(Graphics g)
  {
		final int maxLabelWidth = getFontMetrics(getFont()).stringWidth(">=0.7");
		final int lineWidth = getWidth() - maxLabelWidth;
    final float fBlockHeight = getHeight()/8f;
    for (int i=0; i<8; i++)
    {      
      final int iYPos = getHeight() - (int)fBlockHeight - (int)(fBlockHeight*i);      
      
      if (i == 7)
      {
        g.setColor(Color.BLACK);
        g.drawString(">=0.7", 0, iYPos + ((int)(fBlockHeight/2f) + 10)); 
        g.setColor(Color.GREEN);
      }
      else
      {
        g.setColor(Color.BLACK);
        g.drawString("0." + i, 12, iYPos + ((int)(fBlockHeight/2f) + 10)); 
        g.setColor(new Color(0, 0, (int)(255 * (i / 6f))));
      }
      
      g.fillRect(maxLabelWidth, iYPos, lineWidth, (int)fBlockHeight+1);
    }
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  private void initComponents()//GEN-BEGIN:initComponents
  {
    
    setLayout(new java.awt.BorderLayout());
    
  }//GEN-END:initComponents
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
  
}

package com.cti.vpx.controls.graph.utilities.ui;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;

/**
 * A label that sizes itself to the maximum length of the string(s) required, and does not change size.
 * 
 * Mostly used in graph co-ordinate panel displays.
 */
public class FixedSizeLabel extends JLabel
{
	private static final long serialVersionUID = 5655455555780108111L;
	private String [] samplePatterns;
	
	public FixedSizeLabel(String [] samplePatterns)
	{
		this.samplePatterns = samplePatterns;
	}
	
	public FixedSizeLabel()
	{
	}

	/**
	 * convenience setter
	 */
	public void setSamplePattern(String samplePattern)
	{
		this.samplePatterns = new String [] {samplePattern};
		revalidate();
	}
	
	public void setSamplePatterns(String [] samplePatterns)
	{
		this.samplePatterns = samplePatterns;
		revalidate();
	}

	public String [] getSamplePatterns()
	{
		return this.samplePatterns;
	}
	
	@Override
	public Dimension getMinimumSize()
	{
		final Dimension dX = super.getPreferredSize();
		
		if (samplePatterns!=null)
		{
			int maxWidth = 0;
			for (String pattern : samplePatterns)
			{
				final Rectangle2D rectX = getFontMetrics(getFont()).getStringBounds(pattern, getGraphics());
				maxWidth = Math.max(maxWidth, (int) rectX.getWidth());
			}
			dX.width = maxWidth;
		}
		
		return dX;
	}
	
	@Override
	public Dimension getMaximumSize()
	{
		return getMinimumSize();
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return getMinimumSize();
	}

}

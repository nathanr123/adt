package com.cti.vpx.controls.graph.example;

import java.awt.Color;
import java.awt.Graphics;

import com.cti.vpx.controls.graph.utilities.ui.graphs.lineGraph.MultiLineGraph;

public class GraphWithCustomDrawSurface extends MultiLineGraph
{
	private static final long serialVersionUID = -762119562578571005L;
	private static final Object OVERVIEW_DRAWSURFACE = new Object();
	
	public GraphWithCustomDrawSurface()
	{
		super();
		
		setDrawingOrder(new Object [] {
			OVERVIEW_DRAWSURFACE,
			RANGE_CURSOR_DRAWSURFACE,
			GRID_DRAWSURFACE,
			GRAPH_DRAWSURFACE,
			CURSOR_DRAWSURFACE});
	}

	@Override
	public void paint(Graphics g, Object iDrawSurfaceID)
	{
		if (iDrawSurfaceID!=OVERVIEW_DRAWSURFACE)
		{
			super.paint(g, iDrawSurfaceID);
			return;
		}

		g.setColor(Color.WHITE);
		g.drawRect((int) (getWidth() * 0.25f), (int) (getHeight() * 0.25f), getWidth()/2, getHeight()/2);
	}

}

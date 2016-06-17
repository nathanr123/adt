package com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase;

import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class GraphDrawSurface extends RangeCursorDrawSurface {
	private static final long serialVersionUID = -6095151180639756059L;

	/**
	 * Creates a new instance of cGraphDrawSurface
	 */
	protected GraphDrawSurface() {
	}

	@Override
	protected void paint(Graphics g, Object iDrawSurfaceID) {
		if (GRAPH_DRAWSURFACE == iDrawSurfaceID) {
			drawGraph((Graphics2D) g);
		} else {
			super.paint(g, iDrawSurfaceID);
		}
	}

	/**
	 * This method will do the actual drawing of the graph.
	 *
	 * @param g
	 */
	protected abstract void drawGraph(Graphics2D g);

	/**
	 * This method will cause the display to clear.
	 */
	public abstract void clear();
}

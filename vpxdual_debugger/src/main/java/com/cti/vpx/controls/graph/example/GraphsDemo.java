package com.cti.vpx.controls.graph.example;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.cti.vpx.controls.graph.VPX_MultipleLine;

/**
 * Need Range Cursor demo Cursor demo constellation graph cConstellationGraph
 * cBarGraph measure cursors
 */
public class GraphsDemo {

	private GraphsDemo() {
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignoreEx) {
		}

		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Multiple Line Graph", new VPX_MultipleLine());
		tabbedPane.addTab("Scrolling Line Graph", new GraphWithScrollingLines());
		tabbedPane.addTab("Scrolling Line Graph - XY", new GraphWithScrollingLinesXY());
		tabbedPane.addTab("Waterfall Graph", new WaterfallGraphPanel());
		//tabbedPane.addTab("Overview Graph", new OverviewGraphDisplay());
		tabbedPane.addTab("Custom Graph", new GraphWithCustomDrawSurface());

		final JFrame frame = new JFrame("Graphs Demo");
		frame.getContentPane().add(tabbedPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
}

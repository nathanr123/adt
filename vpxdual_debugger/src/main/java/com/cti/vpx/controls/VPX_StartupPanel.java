package com.cti.vpx.controls;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;

public class VPX_StartupPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 932451317722109993L;

	/**
	 * Create the panel.
	 */
	public VPX_StartupPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Help", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setPreferredSize(new Dimension(500, 10));
		add(panel, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(400, 10));
		add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Alias Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_3);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Recent Documents", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_2);

	}

}

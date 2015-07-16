package com.cti.vpx.controls;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

public class VPX_MAD extends JPanel {

	/**
	 * Create the panel.
	 */
	public VPX_MAD() {
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Configuration", null, new panelCon(), null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Compilation", null, new panelCom(), null);

	}

}

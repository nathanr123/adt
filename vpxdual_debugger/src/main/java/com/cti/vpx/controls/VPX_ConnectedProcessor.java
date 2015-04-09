package com.cti.vpx.controls;

import javax.swing.JPanel;

import com.cti.vpx.view.VPX_Dual_ADT_RootWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class VPX_ConnectedProcessor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1785528291718672208L;
	/**
	 * Create the panel.
	 */

	private VPX_Dual_ADT_RootWindow parent;

	public VPX_ConnectedProcessor(VPX_Dual_ADT_RootWindow prnt) {

		this.parent = prnt;

		init();

		loadComponents();

	}

	private void init() {
		setLayout(new BorderLayout(0, 0));
	}

	private void loadComponents() {
		JPanel panel = new JPanel();
		add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));

		VPX_MessagePanel panel_2 = new VPX_MessagePanel(parent);
		panel_2.setPreferredSize(new Dimension(375, 300));
		panel.add(panel_2, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
	}
}

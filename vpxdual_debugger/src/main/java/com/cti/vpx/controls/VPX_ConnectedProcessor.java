package com.cti.vpx.controls;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.cti.vpx.view.VPX_Dual_ADT_RootWindow;

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

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
	}
}

package com.cti.vpx;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.cti.vpx.view.VPX_Dual_ADT_RootWindow;

public class VPX_Dual_ADT {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					VPX_Dual_ADT_RootWindow window = new VPX_Dual_ADT_RootWindow();

					window.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

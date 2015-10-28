package com.cti.vpx;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.cti.vpx.controls.VPX_SplashWindow;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_Dual_ADT {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					if (Boolean.valueOf(VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.GENERAL_SPLASH))) {

						new VPX_SplashWindow();

					} else {
						// VPX_AppModeWindow window = new
						// VPX_AppModeWindow(VPXUtilities.getEthernetPorts(),VPXUtilities.getSerialPorts());

						// window.showWindow();

						VPX_ETHWindow window = new VPX_ETHWindow();

					}

				} catch (Exception e) {

					JOptionPane.showMessageDialog(null, e.getMessage(), "Opening Application",
							JOptionPane.ERROR_MESSAGE);

					e.printStackTrace();
					VPXUtilities.updateError(e);
					System.exit(0);
				}
			}
		});
	}

}

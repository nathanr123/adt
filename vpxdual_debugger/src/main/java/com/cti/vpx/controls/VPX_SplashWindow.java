package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;

public class VPX_SplashWindow extends JWindow {

    /**
	 * 
	 */

    private static final long serialVersionUID = 3933173505852261011L;

    private static JProgressBar progressBar = new JProgressBar();

    private static int count = 1, TIMER_PAUSE = 25, PROGBAR_MAX = 100;

    private static Timer progressBarTimer;

    private JLabel lblCurrreading;

    Properties props = System.getProperties();

    Enumeration<?> e;

    ResourceBundle rBundle = VPXUtilities.getResourceBundle();

    ActionListener al = new ActionListener() {

	@Override
	public void actionPerformed(java.awt.event.ActionEvent evt) {

	    progressBar.setValue(count);

	    if (PROGBAR_MAX == count) {

		VPX_SplashWindow.this.dispose();// dispose
						// of
						// splashscreen

		progressBarTimer.stop();// stop
					// the
					// timer

		createAndShowADTWindow();
	    }
	    if (e == null)

		e = props.propertyNames();

	    else if (!e.hasMoreElements()) {

		props = VPXUtilities.readProperties();

		e = props.propertyNames();

	    } else {

		lblCurrreading.setText("");
	    }

	    String key = (String) e.nextElement();

	    lblCurrreading.setText("reading " + key + " = " + props.getProperty(key));

	    try {

		Thread.sleep(50);

	    } catch (Exception e) {

	    }
	    count++;// increase
		    // counter

	}
    };

    public VPX_SplashWindow() {

	createSplash();

	setAlwaysOnTop(true);
    }

    private void createSplash() {

	Container container = getContentPane();

	JPanel panel = new JPanel();

	panel.setBorder(new javax.swing.border.EtchedBorder());

	container.add(panel, BorderLayout.CENTER);

	panel.setLayout(null);

	lblCurrreading = new JLabel("");

	lblCurrreading.setForeground(SystemColor.activeCaptionBorder);

	lblCurrreading.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 12));

	lblCurrreading.setBounds(2, 268, 495, 20);

	panel.add(lblCurrreading);

	JLabel lblV = new JLabel("V " + rBundle.getString("App.title.version"));

	lblV.setHorizontalAlignment(SwingConstants.RIGHT);

	lblV.setForeground(SystemColor.activeCaptionBorder);

	lblV.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 14));

	lblV.setBounds(369, 148, 44, 20);

	panel.add(lblV);

	JLabel lblCaption = new JLabel("A VPX Dual Application Debugger Tool");

	lblCaption.setForeground(UIManager.getColor("CheckBox.background"));

	lblCaption.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 14));

	lblCaption.setBounds(29, 175, 243, 29);

	panel.add(lblCaption);

	JLabel lblIcon = new JLabel();

	lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

	lblIcon.setIcon(VPXUtilities.getImageIcon(VPXConstants.Icons.ICON_CORNET_NAME, 72, 72));

	lblIcon.setBounds(29, 94, 72, 72);

	panel.add(lblIcon);

	JLabel lblBanner = new JLabel("Application Debugger Tool");

	lblBanner.setForeground(Color.WHITE);

	lblBanner.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 26));

	lblBanner.setBounds(111, 94, 321, 72);

	panel.add(lblBanner);

	JLabel lblBGImage = new JLabel("");

	lblBGImage.setBounds(2, 2, 495, 290);

	lblBGImage.setIcon(VPXConstants.Icons.IMAGE_BG);

	panel.add(lblBGImage);

	progressBar.setPreferredSize(new Dimension(150, 8));

	progressBar.setMaximum(PROGBAR_MAX);

	container.add(progressBar, BorderLayout.SOUTH);

	setBounds(600, 600, 499, 300);

	// pack();

	setLocationRelativeTo(null);

	setVisible(true);

	startProgressBar();
    }

    private void startProgressBar() {

	progressBarTimer = new Timer(TIMER_PAUSE, al);

	progressBarTimer.start();
    }

    private void createAndShowADTWindow() {

	VPX_AppMode window = new VPX_AppMode(VPXUtilities.getEthernetPorts(), VPXUtilities.getSerialPorts());

	window.showWindow();
    }
}

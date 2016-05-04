package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;
import net.miginfocom.swing.MigLayout;

public class VPX_AboutWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9125929546574219659L;
	private final JPanel contentPanel = new JPanel();
	private ResourceBundle rBundle;

	/**
	 * Create the dialog.
	 */
	public VPX_AboutWindow() {

		init();

		loadCompoenents();

		centerFrame();

	}

	private void init() {

		rBundle = VPXUtilities.getResourceBundle();

		setResizable(false);

		setBounds(100, 100, 414, 206);

		setIconImage(VPXConstants.Icons.ICON_ABOUT.getImage());

		setModal(true);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setAlwaysOnTop(true);
	}

	private void loadCompoenents() {

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new MigLayout("", "[50px][10px][294px]", "[17px][11px][14px][3px][14px][59px]"));

		JLabel lblNewLabel = new JLabel("");

		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel.setIcon(VPXConstants.Icons.ICON_CORNET_BIG);

		contentPanel.add(lblNewLabel, "cell 0 0 1 5,grow");

		JLabel lblNewLabel_1 = new JLabel(rBundle.getString("App.title.name"));

		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);

		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);

		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));

		contentPanel.add(lblNewLabel_1, "cell 2 0,alignx left,aligny top");

		JLabel lblNewLabel_2 = new JLabel("Version : " + rBundle.getString("App.title.version"));

		contentPanel.add(lblNewLabel_2, "cell 2 2,growx,aligny top");

		JLabel lblBuildOn = new JLabel("Build on: 01-02-2016 01:48:22");

		contentPanel.add(lblBuildOn, "cell 2 4,growx,aligny top");

		JLabel lblNewLabel_3 = new JLabel("(c) Copyright Cornet Technology India Pvt Ltd");

		contentPanel.add(lblNewLabel_3, "cell 0 5 3 1,growx,aligny top");

		JLabel lblNewLabel_4 = new JLabel("Web site: www.cornetindia.com");

		contentPanel.add(lblNewLabel_4, "cell 0 5 3 1,growx,aligny bottom");

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Close");

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_AboutWindow.this.dispose();

			}
		});

		buttonPane.add(cancelButton);

	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	public void showDialog() {

		setVisible(true);

	}

}

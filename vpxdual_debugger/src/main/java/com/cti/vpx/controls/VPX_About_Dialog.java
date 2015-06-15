package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.util.VPXUtilities;
import javax.swing.ImageIcon;

public class VPX_About_Dialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9125929546574219659L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			VPX_About_Dialog dialog = new VPX_About_Dialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.showDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_About_Dialog() {

		init();

		loadCompoenents();

		centerFrame();

	}

	private void init() {
		setResizable(false);

		setBounds(100, 100, 383, 206);

		setModal(true);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setAlwaysOnTop(true);
	}

	private void loadCompoenents() {
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 11, 50, 50);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(VPXUtilities.getImageIcon("C:\\debugger\\images\\cornet.png",50,50));
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("VPX Dual Application Debugger Tool");
		lblNewLabel_1.setBounds(70, 11, 249, 17);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Version: 1.0");
		lblNewLabel_2.setBounds(70, 39, 249, 14);
		contentPanel.add(lblNewLabel_2);

		JLabel lblBuildOn = new JLabel("Build on: 13-04-2015 15:42:45");
		lblBuildOn.setBounds(70, 56, 249, 14);
		contentPanel.add(lblBuildOn);

		JLabel lblNewLabel_3 = new JLabel("(c) Copyright Cornet Technology India Pvt Ltd");
		lblNewLabel_3.setBounds(10, 72, 354, 31);
		contentPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Web site: www.cornetindia.com");
		lblNewLabel_4.setBounds(10, 100, 354, 31);
		contentPanel.add(lblNewLabel_4);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Close");

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VPX_About_Dialog.this.dispose();

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

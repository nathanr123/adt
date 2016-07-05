package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class VPX_Shortcuts extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 908933148423590350L;
	private JPanel contentPane;
	private JLabel lblShortcuts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					VPX_Shortcuts frame = new VPX_Shortcuts();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VPX_Shortcuts() {

		init();

		loadComponents();

		centerFrame();

		loadShortcuts();
	}

	private void loadComponents() {

		JToolTip ref = new JToolTip();

		contentPane = new JPanel();

		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));

		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelBtn = new JPanel();

		contentPane.add(panelBtn, BorderLayout.SOUTH);

		JButton btnClose = new JButton("Close");

		btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				VPX_Shortcuts.this.setVisible(false);

			}
		});

		panelBtn.setBackground(ref.getBackground());

		panelBtn.add(btnClose);

		lblShortcuts = new JLabel("");

		lblShortcuts.setOpaque(true);

		lblShortcuts.setBackground(ref.getBackground());

		lblShortcuts.setFont(ref.getFont());

		contentPane.add(lblShortcuts, BorderLayout.CENTER);
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	private void init() {

		setUndecorated(true);

		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 400, 500);
	}

	private void loadShortcuts() {

		String test = "<html><body><table> <thead> <tr><th></th><th>Function</th><th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th><th>Shortcut</th><tr></thead>"

				+ "<tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>Alias Configuration</td><td><b></td><td><b>Ctrl + W</b></td>"

				+ "<tr> <td></td><td>Open Log File</td><td><b></td><td><b>Ctrl + L</b></td>"

				+ "<tr> <td></td><td>Subnet Filter</td><td><b></td><td><b>Ctrl + Q</b></td>"

				+ "<tr> <td></td><td>Refresh Processors</td><td><b></td><td><b>F5</b></td>"

				+ "<tr> <td></td><td>Detail of Processors</td><td><b></td><td><b>F4</b></td>"

				+ "<tr> <td></td><td>Exit Application</td><td><b></td><td><b>Alt + F4</b></td>"

				+ "<tr> <td></td><td>MAD Utility</td><td><b></td><td><b>Ctrl + M</b></td>"

				+ "<tr> <td></td><td>Flash Wizard</td><td><b></td><td><b>Ctrl + U</b></td>"

				+ "<tr> <td></td><td>Change Password</td><td><b></td><td><b>Ctrl + E</b></td>"

				+ "<tr> <td></td><td>Change Periodicity</td><td><b></td><td><b>Ctrl + T</b></td>"

				+ "<tr> <td></td><td>Preference</td><td><b></td><td><b>Ctrl + G</b></td>"

				+ "<tr> <td></td><td>Console</td><td><b></td><td><b>Ctrl + F6</b></td>"

				+ "<tr> <td></td><td>Log</td><td><b></td><td><b>Ctrl + F7</b></td>"

				+ "<tr> <td></td><td>Help</td><td><b></td><td><b>F1</b></td>"

				+ "<tr> <td></td><td>Shortcuts</td><td><b></td><td><b>Ctrl + F2</b></td>"

				+ "<tr> <td></td><td>About</td><td><b></td><td><b>Ctrl + F1</b></td>"

				+ "</table></body></html>";

		lblShortcuts.setText(test);

	}

}

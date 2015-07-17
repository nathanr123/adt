package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class VPX_ChangePasswordWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2014089062330390996L;

	private final JPanel contentPanel = new JPanel();

	private JPasswordField pwdOldPWD;

	private JPasswordField pwdNewPWD;

	private JPasswordField pwdConfirmPWD;

	/**
	 * Create the dialog.
	 */
	public VPX_ChangePasswordWindow() {

		init();

		loadComponents();
	}

	private void init() {

		setResizable(false);

		setTitle("Change Passoword");

		setBounds(100, 100, 350, 190);

		getContentPane().setLayout(new BorderLayout());
	}

	private void loadComponents() {

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new MigLayout("", "[][][grow]", "[][][][][]"));

		JLabel lblOldPWD = new JLabel("Enter Old Password");

		contentPanel.add(lblOldPWD, "cell 0 0");

		pwdOldPWD = new JPasswordField();

		contentPanel.add(pwdOldPWD, "cell 2 0,growx");

		JLabel lblNewPWD = new JLabel("Enter New Password");

		contentPanel.add(lblNewPWD, "cell 0 2");

		pwdNewPWD = new JPasswordField();

		contentPanel.add(pwdNewPWD, "cell 2 2,growx");

		JLabel lblConfirmPWD = new JLabel("Confirm New Password");

		contentPanel.add(lblConfirmPWD, "cell 0 4");

		pwdConfirmPWD = new JPasswordField();

		contentPanel.add(pwdConfirmPWD, "cell 2 4,growx");

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnChangePWD = new JButton("Change");

		btnChangePWD.setActionCommand("OK");

		buttonPane.add(btnChangePWD);

		getRootPane().setDefaultButton(btnChangePWD);

		JButton btnCancel = new JButton("Cancel");

		btnCancel.setActionCommand("Cancel");

		buttonPane.add(btnCancel);

	}

}

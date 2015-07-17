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

public class VPX_PasswordWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1991438363434310071L;

	private final JPanel contentPanel = new JPanel();

	private JPasswordField pwdAdminPassword;

	/**
	 * Create the dialog.
	 */
	public VPX_PasswordWindow() {

		init();

		loadComponents();

	}

	private void init() {

		setResizable(false);

		setTitle("Authentication");

		setBounds(100, 100, 350, 130);

		getContentPane().setLayout(new BorderLayout());
	}

	private void loadComponents() {

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new MigLayout("", "[79.00][][grow]", "[14.00,fill][fill]"));

		JLabel lblAdminPassword = new JLabel("Enter admin password");

		contentPanel.add(lblAdminPassword, "cell 0 1,alignx trailing");

		pwdAdminPassword = new JPasswordField();

		contentPanel.add(pwdAdminPassword, "cell 2 1,growx");

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnOK = new JButton("OK");

		btnOK.setActionCommand("OK");

		buttonPane.add(btnOK);

		getRootPane().setDefaultButton(btnOK);

		JButton btnCancel = new JButton("Cancel");

		btnCancel.setActionCommand("Cancel");

		buttonPane.add(btnCancel);
	}

}

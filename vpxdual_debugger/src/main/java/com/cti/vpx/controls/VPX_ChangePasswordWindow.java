package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

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

	private VPX_ETHWindow parent;

	/**
	 * Create the dialog.
	 */
	public VPX_ChangePasswordWindow(VPX_ETHWindow prnt) {

		this.parent = prnt;

		init();

		loadComponents();

		centerFrame();
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

		btnChangePWD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				changePassword();

			}
		});

		buttonPane.add(btnChangePWD);

		getRootPane().setDefaultButton(btnChangePWD);

		JButton btnCancel = new JButton("Cancel");

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				VPX_ChangePasswordWindow.this.dispose();

			}
		});

		btnCancel.setActionCommand("Cancel");

		buttonPane.add(btnCancel);

	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	private void changePassword() {

		String pwd = VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.SECURITY_PWD);

		if (pwd.equals(new String(pwdOldPWD.getPassword()))) {

			String pwd1 = new String(pwdNewPWD.getPassword());

			String pwd2 = new String(pwdConfirmPWD.getPassword());

			if (pwd1.equals(pwd2)) {

				VPXUtilities.updateProperties(VPXConstants.ResourceFields.SECURITY_PWD, pwd1);

				JOptionPane.showMessageDialog(parent, "Password Changed Succesfully", "Authentication",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(parent, "New password and Confirm password does not match",
						"Authentication", JOptionPane.ERROR_MESSAGE);

			}

		} else {
			JOptionPane.showMessageDialog(parent, "Old passwrod does not match", "Authentication",
					JOptionPane.ERROR_MESSAGE);

		}
		this.dispose();
	}
}

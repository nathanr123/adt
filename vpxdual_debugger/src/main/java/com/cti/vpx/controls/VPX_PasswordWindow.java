package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.util.VPXUtilities;

import net.miginfocom.swing.MigLayout;

public class VPX_PasswordWindow extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1991438363434310071L;

	private final JPanel contentPanel = new JPanel();

	private JPasswordField pwdAdminPassword;

	private boolean isAccepted = false;

	/**
	 * Create the dialog.
	 */
	public VPX_PasswordWindow() {

		init();

		loadComponents();

		centerFrame();

	}

	private void init() {

		setResizable(false);

		setIconImage(VPXUtilities.getAppIcon());

		setTitle("Authentication");

		setBounds(100, 100, 350, 130);

		setModal(true);

		getContentPane().setLayout(new BorderLayout());

		addWindowListener(this);
	}

	public void resetPassword() {

		pwdAdminPassword.setText("");
	}

	private void loadComponents() {

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new MigLayout("", "[79.00][][grow]", "[14.00,fill][fill]"));

		JLabel lblAdminPassword = new JLabel("Enter admin password");

		contentPanel.add(lblAdminPassword, "cell 0 1,alignx trailing");

		pwdAdminPassword = new JPasswordField();

		pwdAdminPassword.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					doAccept();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

					doCancel();
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		contentPanel.add(pwdAdminPassword, "cell 2 1,growx");

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnOK = new JButton("OK");

		btnOK.setActionCommand("OK");

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doAccept();

			}
		});

		buttonPane.add(btnOK);

		getRootPane().setDefaultButton(btnOK);

		JButton btnCancel = new JButton("Cancel");

		btnCancel.setActionCommand("Cancel");

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doCancel();

			}
		});

		buttonPane.add(btnCancel);
	}

	public String getPasword() {
		return new String(this.pwdAdminPassword.getPassword());
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	public boolean isAccepted() {
		return this.isAccepted;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		isAccepted = false;

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	private void doAccept() {

		isAccepted = true;

		VPX_PasswordWindow.this.dispose();

	}

	private void doCancel() {

		pwdAdminPassword.setText("");

		isAccepted = false;

		VPX_PasswordWindow.this.dispose();
	}
}

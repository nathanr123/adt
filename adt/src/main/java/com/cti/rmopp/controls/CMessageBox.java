package com.cti.rmopp.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;

public class CMessageBox implements ActionListener {

	protected JDialog dialog;

	protected int value;

	private final int MSG_COMFIRMATION = 0;

	private final int MSG_INFORMATION = 1;

	private final int MSG_POPUP = 2;

	protected final CButton submit = ComponentFactory.createButton("Yes");

	protected final CButton cancel = ComponentFactory.createButton("Cancel");

	public CMessageBox() {

		submit.addActionListener(this);

		submit.setPreferredSize(new Dimension(90, 35));

		cancel.addActionListener(this);

		cancel.setPreferredSize(new Dimension(90, 35));
	}

	private void initDialog(Window parent, String title, String msg, int msgType) {

		if (parent instanceof Dialog) {

			dialog = new JDialog((Dialog) parent, true);

		} else {

			dialog = new JDialog((Frame) parent, true);
		}

		dialog.setUndecorated(true);

		dialog.getContentPane().setLayout(new BorderLayout());

		CPanel contentPanel = ComponentFactory.createPanel(new BorderLayout());

		CPanel panel[] = new CPanel[3];

		panel[0] = ComponentFactory.createPanel(new BorderLayout());

		CLabel titleLabel = ComponentFactory.createLabel("  " + title);

		titleLabel.setPreferredSize(new Dimension(650, 50));

		titleLabel.setOpaque(true);

		titleLabel.setBackground(Color.DARK_GRAY.darker());

		titleLabel.setFont(Constants.FONTTITLE);

		panel[0].add(titleLabel, BorderLayout.NORTH);

		panel[1] = ComponentFactory.createPanel(new BorderLayout());

		CLabel msgLabel = ComponentFactory.createLabel(msg);

		msgLabel.setHorizontalAlignment(JLabel.CENTER);

		panel[1].add(msgLabel);

		panel[2] = ComponentFactory.createPanel();

		panel[2].setLayout(new FlowLayout(FlowLayout.CENTER));

		contentPanel.add(panel[0], BorderLayout.NORTH);

		contentPanel.add(panel[1], BorderLayout.CENTER);

		contentPanel.add(panel[2], BorderLayout.SOUTH);

		dialog.setContentPane(contentPanel);

		dialog.setSize(550, 250);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		dialog.setLocation((d.width >> 1) - (dialog.getWidth() >> 1), (d.height >> 1) - (dialog.getHeight() >> 1));

		switch (msgType) {

		case MSG_COMFIRMATION:

			panel[2].add(submit);

			panel[2].add(cancel);

			break;

		case MSG_INFORMATION:

			submit.setText("Ok");

			panel[2].add(submit);

			break;

		case MSG_POPUP:

			Timer timer = new Timer(1100, new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					dialog.setVisible(false);

					dialog.dispose();
				}
			});

			timer.setRepeats(false);

			timer.start();
			break;

		}

		dialog.setVisible(true);
	}

	public int showConfirmDialog(Window parent, String title, String msg) {

		initDialog(parent, title, msg, MSG_COMFIRMATION);

		return value;
	}

	public void showMessageOnly(Window parent, String title, String msg) {

		initDialog(parent, title, msg, MSG_POPUP);

	}

	public void showMessage(Window parent, String title, String msg) {

		initDialog(parent, title, msg, MSG_INFORMATION);

	}

	public void actionPerformed(ActionEvent e) {

		if (!dialog.isVisible()) {

			return;
		}

		if (e.getSource() == submit) {

			value = 1;

			dialog.setVisible(false);

		} else if (e.getSource() == cancel) {

			value = 0;

			dialog.setVisible(false);
		}
	}

	public static void main(String[] args) {

		CMessageBox cm = new CMessageBox();

		cm.showConfirmDialog(null, "Testing", "Actoin");

		cm.showMessage(null, "Testing", "Actoin");

		cm.showMessageOnly(null, "Testing", "Actoin");

	}
}
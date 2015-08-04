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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_ChangePeriodicity extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1991438363434310071L;

	private final JPanel contentPanel = new JPanel();

	private JTextField txtPeriodicity;

	private VPX_ETHWindow parent;

	/**
	 * Create the dialog.
	 */
	public VPX_ChangePeriodicity(VPX_ETHWindow prent) {

		super(prent);

		this.parent = prent;

		init();

		loadComponents();

		centerFrame();

	}

	private void init() {

		setResizable(false);

		setTitle("Change Periodicity");

		setBounds(100, 100, 350, 130);

		setModal(true);

		getContentPane().setLayout(new BorderLayout());
	}

	public void resetPassword() {
		txtPeriodicity.setText("");
	}

	private void loadComponents() {

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new MigLayout("", "[79.00][][grow]", "[14.00,fill][fill]"));

		JLabel lblPeriodicity = new JLabel("Enter new periodicity");

		contentPanel.add(lblPeriodicity, "cell 0 1,alignx trailing");

		txtPeriodicity = new JTextField();

		contentPanel.add(txtPeriodicity, "cell 2 1,growx");

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnOK = new JButton("Apply");

		btnOK.setActionCommand("OK");

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Thread th = new Thread(new Runnable() {

					@Override
					public void run() {
						parent.updatePeriodicity(Integer.valueOf(txtPeriodicity.getText().trim()));

						parent.updateLog("Periodicity updated successfully");

						JOptionPane.showMessageDialog(parent, "Periodicity updated successfully");

					}
				});

				th.start();

				VPX_ChangePeriodicity.this.dispose();

			}
		});

		buttonPane.add(btnOK);

		getRootPane().setDefaultButton(btnOK);

		JButton btnCancel = new JButton("Cancel");

		btnCancel.setActionCommand("Cancel");

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				txtPeriodicity.setText("");

				VPX_ChangePeriodicity.this.dispose();

			}
		});

		buttonPane.add(btnCancel);
	}

	public String getPasword() {
		return new String(this.txtPeriodicity.getText());
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

}

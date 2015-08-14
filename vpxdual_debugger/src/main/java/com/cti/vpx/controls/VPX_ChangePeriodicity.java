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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import net.miginfocom.swing.MigLayout;

import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_ChangePeriodicity extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1991438363434310071L;

	private final JPanel contentPanel = new JPanel();

	private JSpinner spinPeriodicity;

	private VPX_ETHWindow parent;

	private SpinnerNumberModel periodicitySpinnerModel;

	/**
	 * Create the dialog.
	 */
	public VPX_ChangePeriodicity(VPX_ETHWindow prent) {

		super(prent);

		this.parent = prent;

		init();

		loadComponents();

		centerFrame();

		periodicitySpinnerModel.setValue(VPXUtilities.getCurrentPeriodicity());
	}

	private void init() {

		setResizable(false);

		setTitle("Change Periodicity");

		setBounds(100, 100, 350, 130);

		setModal(true);

		getContentPane().setLayout(new BorderLayout());
	}

	private void loadComponents() {

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new MigLayout("", "[79.00][][grow]", "[14.00,fill][fill]"));

		JLabel lblPeriodicity = new JLabel("Enter new periodicity");

		contentPanel.add(lblPeriodicity, "cell 0 1,alignx trailing");

		periodicitySpinnerModel = new SpinnerNumberModel(3, 3, 60, 1);

		spinPeriodicity = new JSpinner(periodicitySpinnerModel);

		JFormattedTextField txt = ((JSpinner.NumberEditor) spinPeriodicity.getEditor()).getTextField();

		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

		contentPanel.add(spinPeriodicity, "cell 2 1,growx");

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
						parent.updatePeriodicity(Integer.valueOf(spinPeriodicity.getValue().toString().trim()));

						VPXUtilities.setCurrentPeriodicity(Integer
								.valueOf(spinPeriodicity.getValue().toString().trim()));

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

				VPX_ChangePeriodicity.this.dispose();

			}
		});

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

}

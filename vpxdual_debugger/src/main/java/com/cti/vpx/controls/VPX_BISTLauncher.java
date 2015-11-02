package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_BISTLauncher extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8219412088376944542L;

	private final JPanel contentPanel = new JPanel();

	private VPX_ETHWindow parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_BISTLauncher dialog = new VPX_BISTLauncher(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_BISTLauncher(VPX_ETHWindow parnt) {

		this.parent = parnt;

		init();

		loadComponents();

	}

	private void init() {

		setTitle("BIST Launcher");

		setIconImage(VPXConstants.Icons.ICON_BIST.getImage());

		setAlwaysOnTop(true);

		setLocationRelativeTo(parent);

		setResizable(false);

		setBounds(100, 100, 281, 248);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout());

	}

	private void loadComponents() {

		contentPanel.setBorder(
				new TitledBorder(null, "Processors List", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Select Processors to start BIST");

		lblNewLabel_1.setBounds(10, 12, 190, 23);

		contentPanel.add(lblNewLabel_1);

		JComboBox comboBox = new JComboBox();

		comboBox.setBounds(103, 47, 162, 23);

		contentPanel.add(comboBox);

		JCheckBox chckbxNewCheckBox = new JCheckBox("P2020");

		chckbxNewCheckBox.setEnabled(false);

		chckbxNewCheckBox.setSelected(true);

		chckbxNewCheckBox.setBounds(103, 82, 97, 23);

		contentPanel.add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("DSP 1");

		chckbxNewCheckBox_1.setBounds(103, 117, 97, 23);

		contentPanel.add(chckbxNewCheckBox_1);

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("DSP 2");

		chckbxNewCheckBox_2.setBounds(103, 152, 97, 23);

		contentPanel.add(chckbxNewCheckBox_2);

		JLabel lblSubSystems = new JLabel("Sub Systems");

		lblSubSystems.setBounds(10, 46, 83, 23);

		contentPanel.add(lblSubSystems);

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Start");

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_BISTLauncher.this.dispose();

				parent.startBist();

			}
		});

		buttonPane.add(okButton);

		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_BISTLauncher.this.dispose();

				parent.startBist();

			}
		});

		cancelButton.setActionCommand("Cancel");

		buttonPane.add(cancelButton);
	}
}

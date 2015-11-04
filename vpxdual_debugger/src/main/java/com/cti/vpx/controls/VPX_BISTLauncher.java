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
import javax.swing.UIManager;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;

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

		setBounds(100, 100, 280, 220);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout());

	}

	private void loadComponents() {

		contentPanel.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Select Processors to start BIST", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[83px][162px]", "[24px][23px][23px][23px]"));

		JComboBox comboBox = new JComboBox();

		contentPanel.add(comboBox, "cell 1 0,grow");

		JCheckBox chckbxNewCheckBox = new JCheckBox("P2020");

		chckbxNewCheckBox.setEnabled(false);

		chckbxNewCheckBox.setSelected(true);

		contentPanel.add(chckbxNewCheckBox, "cell 1 1,alignx left,aligny top");

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("DSP 1");

		contentPanel.add(chckbxNewCheckBox_1, "cell 1 2,alignx left,aligny top");

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("DSP 2");

		contentPanel.add(chckbxNewCheckBox_2, "cell 1 3,alignx left,aligny top");

		JLabel lblSubSystems = new JLabel("Sub Systems");

		contentPanel.add(lblSubSystems, "cell 0 0,grow");

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

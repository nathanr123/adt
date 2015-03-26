package com.cti.vpx.controls;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VPX_FlashProcessor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7349075513577956507L;
	private JTextField txt_Core_0;
	private JTextField txt_Core_1;
	private JTextField txt_Core_2;
	private JTextField txt_Core_3;
	private JTextField txt_Core_4;
	private JTextField txt_Core_5;
	private JTextField txt_Core_6;
	private JTextField txt_Core_7;

	final JFileChooser fileDialog = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("binary file", "bin", "out");

	/**
	 * Create the panel.
	 */
	public VPX_FlashProcessor() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 19, 288, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel_8 = new JLabel("  ");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 1;
		gbc_lblNewLabel_8.gridy = 1;
		add(lblNewLabel_8, gbc_lblNewLabel_8);

		JLabel lblNewLabel = new JLabel("Select Processor");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);

		JComboBox<String> comboBox = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {
				"Select Processor", "DSP - 1", "DSP - 2", "P2020" }));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 2;
		add(comboBox, gbc_comboBox);

		JLabel label = new JLabel("  ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 3;
		add(label, gbc_label);

		JLabel label_1 = new JLabel("  ");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 5;
		add(label_1, gbc_label_1);

		JLabel lblNewLabel_1 = new JLabel("Core 0");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 6;
		add(lblNewLabel_1, gbc_lblNewLabel_1);

		txt_Core_0 = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 6;
		add(txt_Core_0, gbc_textField);
		txt_Core_0.setColumns(10);

		JButton btnBrowse = new JButton(new BrowseAction("Browse", txt_Core_0));
		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowse.gridx = 5;
		gbc_btnBrowse.gridy = 6;
		add(btnBrowse, gbc_btnBrowse);

		JLabel label_2 = new JLabel("  ");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 7;
		add(label_2, gbc_label_2);

		JLabel lblNewLabel_2 = new JLabel("Core 1");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 8;
		add(lblNewLabel_2, gbc_lblNewLabel_2);

		txt_Core_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 8;
		add(txt_Core_1, gbc_textField_1);
		txt_Core_1.setColumns(10);

		JButton button = new JButton(new BrowseAction("Browse", txt_Core_1));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 5;
		gbc_button.gridy = 8;
		add(button, gbc_button);

		JLabel label_3 = new JLabel("  ");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 9;
		add(label_3, gbc_label_3);

		JLabel lblNewLabel_3 = new JLabel("Core 2");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 10;
		add(lblNewLabel_3, gbc_lblNewLabel_3);

		txt_Core_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 10;
		add(txt_Core_2, gbc_textField_2);
		txt_Core_2.setColumns(10);

		JButton button_1 = new JButton(new BrowseAction("Browse", txt_Core_2));
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 5;
		gbc_button_1.gridy = 10;
		add(button_1, gbc_button_1);

		JLabel label_4 = new JLabel("  ");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 11;
		add(label_4, gbc_label_4);

		JLabel lblNewLabel_4 = new JLabel("Core 3");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 12;
		add(lblNewLabel_4, gbc_lblNewLabel_4);

		txt_Core_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 12;
		add(txt_Core_3, gbc_textField_3);
		txt_Core_3.setColumns(10);

		JButton button_2 = new JButton(new BrowseAction("Browse", txt_Core_3));
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 5;
		gbc_button_2.gridy = 12;
		add(button_2, gbc_button_2);

		JLabel label_5 = new JLabel("  ");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 1;
		gbc_label_5.gridy = 13;
		add(label_5, gbc_label_5);

		JLabel lblNewLabel_5 = new JLabel("Core 4");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 14;
		add(lblNewLabel_5, gbc_lblNewLabel_5);

		txt_Core_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 14;
		add(txt_Core_4, gbc_textField_4);
		txt_Core_4.setColumns(10);

		JButton button_3 = new JButton(new BrowseAction("Browse", txt_Core_4));
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 5;
		gbc_button_3.gridy = 14;
		add(button_3, gbc_button_3);

		JLabel label_6 = new JLabel("  ");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 1;
		gbc_label_6.gridy = 15;
		add(label_6, gbc_label_6);

		JLabel lblNewLabel_6 = new JLabel("Core 5");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 1;
		gbc_lblNewLabel_6.gridy = 16;
		add(lblNewLabel_6, gbc_lblNewLabel_6);

		txt_Core_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 16;
		add(txt_Core_5, gbc_textField_5);
		txt_Core_5.setColumns(10);

		JButton button_4 = new JButton(new BrowseAction("Browse", txt_Core_5));
		GridBagConstraints gbc_button_4 = new GridBagConstraints();
		gbc_button_4.insets = new Insets(0, 0, 5, 5);
		gbc_button_4.gridx = 5;
		gbc_button_4.gridy = 16;
		add(button_4, gbc_button_4);

		JLabel label_7 = new JLabel("  ");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 1;
		gbc_label_7.gridy = 17;
		add(label_7, gbc_label_7);

		JLabel lblNewLabel_7 = new JLabel("Core 6");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 1;
		gbc_lblNewLabel_7.gridy = 18;
		add(lblNewLabel_7, gbc_lblNewLabel_7);

		txt_Core_6 = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 3;
		gbc_textField_6.gridy = 18;
		add(txt_Core_6, gbc_textField_6);
		txt_Core_6.setColumns(10);

		JButton button_5 = new JButton(new BrowseAction("Browse", txt_Core_6));
		GridBagConstraints gbc_button_5 = new GridBagConstraints();
		gbc_button_5.insets = new Insets(0, 0, 5, 5);
		gbc_button_5.gridx = 5;
		gbc_button_5.gridy = 18;
		add(button_5, gbc_button_5);

		JLabel label_8 = new JLabel("  ");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 1;
		gbc_label_8.gridy = 19;
		add(label_8, gbc_label_8);

		JLabel lblCore = new JLabel("Core 7");
		GridBagConstraints gbc_lblCore = new GridBagConstraints();
		gbc_lblCore.insets = new Insets(0, 0, 5, 5);
		gbc_lblCore.gridx = 1;
		gbc_lblCore.gridy = 20;
		add(lblCore, gbc_lblCore);

		txt_Core_7 = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 5);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 3;
		gbc_textField_7.gridy = 20;
		add(txt_Core_7, gbc_textField_7);
		txt_Core_7.setColumns(10);

		JButton button_6 = new JButton(new BrowseAction("Browse", txt_Core_7));
		GridBagConstraints gbc_button_6 = new GridBagConstraints();
		gbc_button_6.insets = new Insets(0, 0, 5, 5);
		gbc_button_6.gridx = 5;
		gbc_button_6.gridy = 20;
		add(button_6, gbc_button_6);

		JLabel label_9 = new JLabel("  ");
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.insets = new Insets(0, 0, 5, 5);
		gbc_label_9.gridx = 1;
		gbc_label_9.gridy = 21;
		add(label_9, gbc_label_9);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 5;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 22;
		add(panel, gbc_panel);

		JButton btnVerify = new JButton("Verify");
		panel.add(btnVerify);

		JButton btnFlash_1 = new JButton("Flash");
		panel.add(btnFlash_1);

	}

	public class BrowseAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 477649130981302914L;

		JTextField jtf;

		public BrowseAction(String name, JTextField txt) {
			jtf = txt;
			putValue(NAME, name);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			fileDialog.addChoosableFileFilter(filter);

			int returnVal = fileDialog.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				java.io.File file = fileDialog.getSelectedFile();
				jtf.setText(file.getPath());
			}
		}
	}
}

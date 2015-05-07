package com.cti.vpx.controls;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.util.VPXUtilities;
import java.awt.event.ActionListener;

public class VPX_MAD extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7349075513577956507L;

	final JFileChooser fileDialog = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("binary file", "bin", "out");
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;

	/**
	 * Create the panel.
	 */
	public VPX_MAD() {
		setPreferredSize(new Dimension(600, 600));
		setMinimumSize(new Dimension(600, 600));
		setMaximumSize(new Dimension(600, 600));
		setLayout(null);
		
		fileDialog.setAcceptAllFileFilterUsed(false);

		JLabel lblNewLabel = new JLabel("Select Processor");
		lblNewLabel.setBounds(36, 25, 79, 14);
		add(lblNewLabel);

		JComboBox<String> comboBox = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {
				"Select Processor", "DSP - 1", "DSP - 2", "P2020" }));
		comboBox.setBounds(150, 22, 283, 20);
		add(comboBox);

		JPanel panel = new JPanel();
		panel.setBounds(36, 533, 549, 33);
		add(panel);

		JButton btnVerify = new JButton("Compile");
		panel.add(btnVerify);

		JButton btnFlash_1 = new JButton("Save");
		panel.add(btnFlash_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Select out Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(36, 50, 549, 395);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("Core 0");
		label.setBounds(21, 36, 32, 14);
		panel_1.add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(112, 33, 283, 20);
		panel_1.add(textField);
		
		JButton button = new JButton(new BrowseAction("Browse", textField));
		button.setBounds(430, 32, 87, 23);
		panel_1.add(button);
		
		JLabel label_1 = new JLabel("Core 1");
		label_1.setBounds(21, 83, 32, 14);
		panel_1.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(112, 80, 283, 20);
		panel_1.add(textField_1);
		
		JButton button_1 = new JButton(new BrowseAction("Browse", textField_1));
		button_1.setBounds(430, 79, 87, 23);
		panel_1.add(button_1);
		
		JLabel label_2 = new JLabel("Core 2");
		label_2.setBounds(21, 130, 32, 14);
		panel_1.add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(112, 127, 283, 20);
		panel_1.add(textField_2);
		
		JButton button_2 = new JButton(new BrowseAction("Browse", textField_2));
		button_2.setBounds(430, 126, 87, 23);
		panel_1.add(button_2);
		
		JLabel label_3 = new JLabel("Core 3");
		label_3.setBounds(21, 177, 32, 14);
		panel_1.add(label_3);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(112, 174, 283, 20);
		panel_1.add(textField_3);
		
		JButton button_3 = new JButton(new BrowseAction("Browse", textField_3));
		button_3.setBounds(430, 173, 87, 23);
		panel_1.add(button_3);
		
		JLabel label_4 = new JLabel("Core 4");
		label_4.setBounds(21, 224, 32, 14);
		panel_1.add(label_4);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(112, 221, 283, 20);
		panel_1.add(textField_4);
		
		JButton button_4 = new JButton(new BrowseAction("Browse", textField_4));
		button_4.setBounds(430, 220, 87, 23);
		panel_1.add(button_4);
		
		JLabel label_5 = new JLabel("Core 5");
		label_5.setBounds(21, 271, 32, 14);
		panel_1.add(label_5);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(112, 268, 283, 20);
		panel_1.add(textField_5);
		
		JButton button_5 = new JButton(new BrowseAction("Browse", textField_5));
		button_5.setBounds(430, 267, 87, 23);
		panel_1.add(button_5);
		
		JLabel label_6 = new JLabel("Core 6");
		label_6.setBounds(21, 318, 32, 14);
		panel_1.add(label_6);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(112, 315, 283, 20);
		panel_1.add(textField_6);
		
		JButton button_6 = new JButton(new BrowseAction("Browse", textField_6));
		button_6.setBounds(430, 314, 87, 23);
		panel_1.add(button_6);
		
		JLabel label_7 = new JLabel("Core 7");
		label_7.setBounds(21, 365, 32, 14);
		panel_1.add(label_7);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(112, 362, 283, 20);
		panel_1.add(textField_7);
		
		JButton button_7 = new JButton(new BrowseAction("Browse", textField_7));
		button_7.setBounds(430, 361, 87, 23);
		panel_1.add(button_7);
		
		JButton btnNewButton = new JButton(new SettingsAction("Configuration"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(443, 21, 23, 23);
		add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Output File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(36, 467, 549, 55);
		add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblOutputFile = new JLabel("File Name");
		lblOutputFile.setBounds(21, 25, 81, 14);
		panel_2.add(lblOutputFile);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(112, 22, 283, 20);
		panel_2.add(textField_8);
		
		JButton button_8 = new JButton(new BrowseAction("Browse", textField_8));
		button_8.setBounds(430, 21, 87, 23);
		panel_2.add(button_8);

	}

	public class SettingsAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6695372620193421408L;
		public SettingsAction(String name) {
			//putValue(NAME, name);
			putValue(SHORT_DESCRIPTION, name);
			putValue(SMALL_ICON, VPXUtilities.getImageIcon("images\\settings2.png", 16, 16));
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
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

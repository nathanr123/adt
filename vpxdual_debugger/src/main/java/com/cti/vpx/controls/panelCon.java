package com.cti.vpx.controls;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import javax.swing.JCheckBox;

public class panelCon extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Create the panel.
	 */
	public panelCon() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Paths", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(22, 26, 100, 26);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(132, 26, 509, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(647, 26, 89, 26);
		panel.add(btnNewButton);
		
		JLabel label = new JLabel("New label");
		label.setBounds(22, 78, 100, 26);
		panel.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(132, 78, 509, 26);
		panel.add(textField_1);
		
		JButton button = new JButton("New button");
		button.setBounds(647, 78, 89, 26);
		panel.add(button);
		
		JLabel label_1 = new JLabel("New label");
		label_1.setBounds(22, 130, 100, 26);
		panel.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(132, 130, 509, 26);
		panel.add(textField_2);
		
		JButton button_1 = new JButton("New button");
		button_1.setBounds(647, 130, 89, 26);
		panel.add(button_1);
		
		JLabel label_2 = new JLabel("New label");
		label_2.setBounds(22, 182, 100, 26);
		panel.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(132, 182, 509, 26);
		panel.add(textField_3);
		
		JButton button_2 = new JButton("New button");
		button_2.setBounds(647, 182, 89, 26);
		panel.add(button_2);
		
		JLabel label_3 = new JLabel("New label");
		label_3.setBounds(22, 234, 100, 26);
		panel.add(label_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(132, 234, 509, 26);
		panel.add(textField_4);
		
		JButton button_3 = new JButton("New button");
		button_3.setBounds(647, 234, 89, 26);
		panel.add(button_3);
		
		JLabel label_4 = new JLabel("New label");
		label_4.setBounds(22, 286, 100, 26);
		panel.add(label_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(132, 286, 509, 26);
		panel.add(textField_5);
		
		JButton button_4 = new JButton("New button");
		button_4.setBounds(647, 286, 89, 26);
		panel.add(button_4);
		
		JLabel label_5 = new JLabel("New label");
		label_5.setBounds(22, 338, 100, 26);
		panel.add(label_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(132, 338, 509, 26);
		panel.add(textField_6);
		
		JButton button_5 = new JButton("New button");
		button_5.setBounds(647, 338, 89, 26);
		panel.add(button_5);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 140));
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Dummy File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JCheckBox checkBox = new JCheckBox("New check box");
		checkBox.setBounds(22, 19, 97, 23);
		panel_2.add(checkBox);
		
		JLabel label_6 = new JLabel("New label");
		label_6.setBounds(22, 61, 100, 26);
		panel_2.add(label_6);
		
		textField_7 = new JTextField();
		textField_7.setBounds(132, 61, 509, 26);
		textField_7.setColumns(10);
		panel_2.add(textField_7);
		
		JButton button_6 = new JButton("New button");
		button_6.setBounds(647, 61, 89, 26);
		panel_2.add(button_6);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel_3.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		panel_3.add(btnNewButton_2);

	}
}

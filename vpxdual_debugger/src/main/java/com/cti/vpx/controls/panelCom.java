package com.cti.vpx.controls;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import java.awt.Color;

public class panelCom extends JPanel {
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
	public panelCom() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Out Files", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(22, 19, 100, 26);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(132, 19, 509, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(647, 19, 89, 26);
		panel.add(btnNewButton);
		
		JLabel label = new JLabel("New label");
		label.setBounds(22, 64, 100, 26);
		panel.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(132, 64, 509, 26);
		panel.add(textField_1);
		
		JButton button = new JButton("New button");
		button.setBounds(647, 64, 89, 26);
		panel.add(button);
		
		JLabel label_1 = new JLabel("New label");
		label_1.setBounds(22, 109, 100, 26);
		panel.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(132, 109, 509, 26);
		panel.add(textField_2);
		
		JButton button_1 = new JButton("New button");
		button_1.setBounds(647, 109, 89, 26);
		panel.add(button_1);
		
		JLabel label_2 = new JLabel("New label");
		label_2.setBounds(22, 154, 100, 26);
		panel.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(132, 154, 509, 26);
		panel.add(textField_3);
		
		JButton button_2 = new JButton("New button");
		button_2.setBounds(647, 154, 89, 26);
		panel.add(button_2);
		
		JLabel label_3 = new JLabel("New label");
		label_3.setBounds(22, 199, 100, 26);
		panel.add(label_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(132, 199, 509, 26);
		panel.add(textField_4);
		
		JButton button_3 = new JButton("New button");
		button_3.setBounds(647, 199, 89, 26);
		panel.add(button_3);
		
		JLabel label_4 = new JLabel("New label");
		label_4.setBounds(22, 244, 100, 26);
		panel.add(label_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(132, 244, 509, 26);
		panel.add(textField_5);
		
		JButton button_4 = new JButton("New button");
		button_4.setBounds(647, 244, 89, 26);
		panel.add(button_4);
		
		JLabel label_5 = new JLabel("New label");
		label_5.setBounds(22, 289, 100, 26);
		panel.add(label_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(132, 289, 509, 26);
		panel.add(textField_6);
		
		JButton button_5 = new JButton("New button");
		button_5.setBounds(647, 289, 89, 26);
		panel.add(button_5);
		
		JLabel label_7 = new JLabel("New label");
		label_7.setBounds(22, 334, 100, 26);
		panel.add(label_7);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(132, 334, 509, 26);
		panel.add(textField_8);
		
		JButton button_7 = new JButton("New button");
		button_7.setBounds(647, 334, 89, 26);
		panel.add(button_7);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 100));
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Out File", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JLabel label_6 = new JLabel("New label");
		label_6.setBounds(25, 24, 100, 26);
		panel_2.add(label_6);
		
		textField_7 = new JTextField();
		textField_7.setBounds(135, 24, 509, 26);
		textField_7.setColumns(10);
		panel_2.add(textField_7);
		
		JButton button_6 = new JButton("New button");
		button_6.setBounds(650, 24, 89, 26);
		panel_2.add(button_6);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel_3.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		panel_3.add(btnNewButton_2);

	}
}

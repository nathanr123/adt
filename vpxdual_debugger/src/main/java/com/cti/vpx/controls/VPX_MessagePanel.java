package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;

public class VPX_MessagePanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public VPX_MessagePanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Select Processor");
		panel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		
		comboBox.setPreferredSize(new Dimension(150,23));
		
		panel.add(comboBox);
		
		JButton btnNewButton = new JButton("Settings");
		panel.add(btnNewButton);
		
		JButton btnNewButton1 = new JButton("Filter");
		panel.add(btnNewButton1);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		add(panel_1, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_1 = new JLabel("Message");
		panel_1.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(850,23));		
		panel_1.add(textField);
		
		
		JButton btnNewButton_1 = new JButton("Send Message");
		panel_1.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

	}

}

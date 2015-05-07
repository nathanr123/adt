package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class VPX_EthernetFlash extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public VPX_EthernetFlash() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Ethernet Flash", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Flash Device");
		lblNewLabel.setBounds(27, 85, 114, 22);
		panel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(165, 85, 175, 22);
		comboBox.setPreferredSize(new Dimension(175, 22));
		panel.add(comboBox);
		
		JLabel lblOffset = new JLabel("Offset");
		lblOffset.setBounds(27, 134, 114, 22);
		panel.add(lblOffset);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setPreferredSize(new Dimension(175, 22));
		comboBox_1.setBounds(165, 134, 175, 22);
		panel.add(comboBox_1);
		
		JLabel lblSelectoutFile = new JLabel("Select .out file");
		lblSelectoutFile.setBounds(27, 188, 114, 22);
		panel.add(lblSelectoutFile);
		
		textField = new JTextField();
		textField.setBounds(165, 188, 306, 22);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.setBounds(382, 222, 91, 23);
		panel.add(btnNewButton);
		
		JButton btnFlash = new JButton("Flash");
		btnFlash.setBounds(165, 293, 91, 23);
		panel.add(btnFlash);
		
		JLabel lblProcessor = new JLabel("Processor");
		lblProcessor.setBounds(27, 35, 114, 22);
		panel.add(lblProcessor);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setPreferredSize(new Dimension(175, 22));
		comboBox_2.setBounds(165, 35, 175, 22);
		panel.add(comboBox_2);

	}
	
	public static void main(String[] args) {
		JFrame frm = new JFrame();
		frm.getContentPane().setLayout(new BorderLayout());
		frm.getContentPane().add(new VPX_EthernetFlash());
		frm.setBounds(150, 150, 500, 320);
		frm.setVisible(true);
	}
}

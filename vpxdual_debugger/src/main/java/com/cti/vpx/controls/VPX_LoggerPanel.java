package com.cti.vpx.controls;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;

public class VPX_LoggerPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public VPX_LoggerPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Clear");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Copy");
		panel.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

	}

}

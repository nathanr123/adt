package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cti.vpx.util.ComponentFactory;

public class VPX_MessagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1739175553026398101L;

	private JTextField txt_Usr_Msg;

	private JTextArea txtA_Processor_Msg;

	/**
	 * Create the panel.
	 */
	public VPX_MessagePanel() {

		init();

		loadComponents();
	}

	private void init() {
		setLayout(new BorderLayout(0, 0));
	}

	private void loadComponents() {

		JPanel msgPanel = ComponentFactory.createJPanel();

		FlowLayout flowLayout_1 = (FlowLayout) msgPanel.getLayout();

		flowLayout_1.setAlignment(FlowLayout.TRAILING);

		add(msgPanel, BorderLayout.NORTH);

		JLabel lbl_Sel_Processor = ComponentFactory.createJLabel("Select Processor");

		msgPanel.add(lbl_Sel_Processor);

		JComboBox cmb_Sel_Processor = ComponentFactory.createJComboBox();

		cmb_Sel_Processor.setPreferredSize(new Dimension(150, 23));

		msgPanel.add(cmb_Sel_Processor);

		JButton btn_Settings = ComponentFactory.createJButton("Settings");

		msgPanel.add(btn_Settings);

		JButton btn_Filter = ComponentFactory.createJButton("Filter");

		msgPanel.add(btn_Filter);

		JPanel panel_1 = ComponentFactory.createJPanel();

		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();

		flowLayout.setAlignment(FlowLayout.LEADING);

		add(panel_1, BorderLayout.SOUTH);

		JLabel lblNewLabel_1 = ComponentFactory.createJLabel("Message");

		panel_1.add(lblNewLabel_1);

		txt_Usr_Msg = ComponentFactory.createJTextField();

		txt_Usr_Msg.setPreferredSize(new Dimension(850, 23));

		panel_1.add(txt_Usr_Msg);

		JButton btn_Send_Usr_Msg = ComponentFactory.createJButton("Send Message");

		panel_1.add(btn_Send_Usr_Msg);

		JScrollPane scrollPane = ComponentFactory.createJScrollPane();

		add(scrollPane, BorderLayout.CENTER);

		txtA_Processor_Msg = ComponentFactory.createJTextArea();

		scrollPane.setViewportView(txtA_Processor_Msg);
	}

}

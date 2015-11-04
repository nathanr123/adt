package com.cti.vpx.controls.graph;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import java.awt.Color;

public class VPX_SpectrumWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_SpectrumWindow dialog = new VPX_SpectrumWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_SpectrumWindow() {
		setBounds(100, 100, 1014, 790);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filter", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setPreferredSize(new Dimension(10, 100));
		contentPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][]", "[][][]"));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel, "cell 0 0");
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1, "cell 2 0");
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		panel.add(lblNewLabel_3, "cell 4 0");
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		panel.add(lblNewLabel_4, "cell 6 0");
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		panel.add(lblNewLabel_2, "cell 0 2");
		
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox, "cell 2 2 5 1,growx");
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		panel.add(lblNewLabel_5, "cell 8 2");
		
		JSlider slider = new JSlider();
		panel.add(slider, "cell 10 2,growx");
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		panel.add(lblNewLabel_6, "cell 11 2");
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		panel.add(lblNewLabel_7, "cell 12 2");
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		panel.add(chckbxNewCheckBox, "cell 14 2");
		
		JButton btnNewButton = new JButton("New button");
		panel.add(btnNewButton, "cell 16 2");
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.WHITE);
		panel_1.setBackground(Color.BLACK);
		contentPanel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setForeground(Color.WHITE);
		panel_2.setBackground(Color.BLACK);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Amplitude", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		panel_2.setPreferredSize(new Dimension(10, 250));
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setForeground(Color.WHITE);
		panel_3.setBackground(Color.BLACK);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Waterfall", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		panel_1.add(panel_3);
	}

}

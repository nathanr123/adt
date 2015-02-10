package com.cti.rmopp.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class Test extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8915453731892290113L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private CMenu nmsServerMenu;
	private CLed led;

	private void registerFont() {
		boolean isAvailable = false;
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Font f[] = ge.getAllFonts();
			for (int i = 0; i < f.length; i++) {
				if (f[i].getFontName() == Constants.FONTNAMEDEFAULT) {
					isAvailable = true;
					break;
				}
			}

			if (!isAvailable) {
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resource\\font\\segoeui.ttf")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		// registerFont();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 650, 450);

		JPanel panel = new JPanel();

		CCheckBox cjk = new CCheckBox("Chk");

		CComboBox comboBox = new CComboBox();

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		comboBox.addItem("Test");

		CButton btn = new CButton("Button");

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				CDialog jdl = new CDialog("Test Dialog");
				jdl.setBounds(50,50,600,300);
				jdl.setVisible(true);
				
				if (led.getStatus() == CLed.DISABLED)
					led.setStatus(CLed.ON);
				else if (led.getStatus() == CLed.ON)
					led.setStatus(CLed.IDLE);
				else if (led.getStatus() == CLed.IDLE)
					led.setStatus(CLed.OFF);
				else if (led.getStatus() == CLed.OFF)
					led.setStatus(CLed.DISABLED);

				
			}
			
			
			
		});

		CLabel lbl = new CLabel("Label");

		CPasswordField pass = new CPasswordField();

		CRadioButton rad = new CRadioButton("Radio");

		CSlider slider = new CSlider();

		CScrollBar scrl = new CScrollBar();

		CSpinner spin = new CSpinner();

		led = new CLed(CLed.SMALL, CLed.DISABLED);

		led.setPreferredSize(led.getSize());

		// panel.setLayout(new GridLayout(8, 1));

		panel.setBackground(Color.DARK_GRAY);

		panel.add(btn);

		panel.add(new CTextField());

		panel.add(pass);

		panel.add(rad);

		panel.add(slider);

		panel.add(scrl);

		panel.add(cjk);

		panel.add(comboBox);

		panel.add(new CTextArea());

		panel.add(lbl);

		panel.add(spin);

		panel.add(led);

		add(panel, BorderLayout.CENTER);

		CMenuBar nmsServerMenubar = ComponentFactory.createMenuBar();

		nmsServerMenu = ComponentFactory.createMenu("Server");

		CMenuItem miRestart = ComponentFactory.createMenuItem("Restart Server");

		CMenuItem miShutdown = ComponentFactory.createMenuItem("Shutdown Server");

		CMenuItem miExit = ComponentFactory.createMenuItem("Exit Server");

		nmsServerMenu.add(miRestart);

		nmsServerMenu.add(miShutdown);

		nmsServerMenu.add(new JSeparator());

		nmsServerMenu.add(miExit);

		nmsServerMenubar.add(nmsServerMenu);

		setJMenuBar(nmsServerMenubar);

	}

}

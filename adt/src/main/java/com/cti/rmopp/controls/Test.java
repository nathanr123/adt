package com.cti.rmopp.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
	private CCheckBox cjk;
	private CComboBox comboBox;
	private CButton btn;
	private CLabel lbl;
	private CPasswordField pass;
	private CRadioButton rad;
	private CSlider slider;
	private CScrollBar scrl;
	private CSpinner spin;
	
	private boolean isError = false;
	private CTextArea txtA;
	private CTextField txt;

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

		cjk = new CCheckBox("Chk");

		comboBox = new CComboBox();

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

		btn = new CButton("Button");

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
			/*	CDialog jdl = new CDialog("Test Dialog");
				jdl.setBounds(50,50,600,300);
				jdl.setVisible(true);*/
				
				if (led.getStatus() == CLed.DISABLED)
					led.setStatus(CLed.ON);
				else if (led.getStatus() == CLed.ON)
					led.setStatus(CLed.IDLE);
				else if (led.getStatus() == CLed.IDLE)
					led.setStatus(CLed.OFF);
				else if (led.getStatus() == CLed.OFF)
					led.setStatus(CLed.DISABLED);
				
				isError = !isError;
				
				cjk.setErrorStatus(isError);
				
				comboBox.setErrorStatus(isError);
				
				txtA.setErrorStatus(isError);
				
				lbl.setErrorStatus(isError);
				
				pass.setErrorStatus(isError);
				
				rad.setErrorStatus(isError);
				
				txt.setErrorStatus(isError);
				
				spin.setErrorStatus(isError);
				
				
			}
			
			
			
		});

		 lbl = new CLabel("Label");

		 pass = new CPasswordField();

		 rad = new CRadioButton("Radio");

		 slider = new CSlider();

		 scrl = new CScrollBar();

		 spin = new CSpinner();

		led = new CLed(CLed.SMALL, CLed.DISABLED);

		led.setPreferredSize(led.getSize());

		panel.setLayout(new GridLayout(8, 1));

		panel.setBackground(Color.DARK_GRAY);

		panel.add(btn);

		txt = new CTextField("Test");
		
		panel.add(txt);

		panel.add(pass);

		panel.add(rad);

		panel.add(slider);

		panel.add(scrl);

		panel.add(cjk);

		panel.add(comboBox);

		txtA = new CTextArea("This is the Message");
		
		panel.add(txtA);

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

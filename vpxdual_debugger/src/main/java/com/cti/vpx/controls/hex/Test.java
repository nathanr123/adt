package com.cti.vpx.controls.hex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.util.VPXUtilities;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Test extends JFrame {

	private JPanel contentPane;

	private VPX_FilterComboBox cmbMemoryVariables_1;
	private Map<String, String> memVariables;

	private JTextArea txtA_Log;

	private int idx =0;

	private int end;

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

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 883, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		
cmbMemoryVariables_1 = new VPX_FilterComboBox();
cmbMemoryVariables_1.setSize(200, 22);
cmbMemoryVariables_1.setLocation(10, 10);
		
		cmbMemoryVariables_1.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				fillMemoryAddress();
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});


		
		contentPane.add(cmbMemoryVariables_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//loadMemoryVariables("C:\\Users\\Senthil\\Desktop\\TMS_IOCntl.map");
				
				find("VPX");
				
			}
		});
		btnNewButton.setBounds(121, 43, 89, 23);
		contentPane.add(btnNewButton);
		
		txtA_Log = new JTextArea();
		txtA_Log.setText("12-05-2016 19:03:14  INFO:   VPX Dual Application Debugger Tool - 1.6 Started\r\n12-05-2016 19:03:14  INFO:   Ethernet 2 has connected successfully. IPv4 Address : 172.17.1.202 Subnet Mask : 255.255.0.0 Default Gateway :  \r\n12-05-2016 19:03:16  INFO:   Periodicity updated to 192.233.1.42 into 3 seconds\r\n12-05-2016 19:03:16  INFO:   Periodicity updated to 172.17.10.5 into 3 seconds\r\n12-05-2016 19:03:19  INFO:   Memory Browser opened\r\n12-05-2016 19:03:37  INFO:   Read memory from 192.233.1.42 core 0 address a0000000 length 100");
		txtA_Log.setBounds(10, 104, 847, 146);
		contentPane.add(txtA_Log);
		
		
	}
	
	private void find(String value){
		String str = txtA_Log.getText();

		idx = str.indexOf(value, idx);
		
		txtA_Log.setSelectedTextColor(Color.white);
		txtA_Log.setSelectionColor(Color.BLUE);

		txtA_Log.setSelectionStart(idx);

		end = idx + value.length();

		txtA_Log.setSelectionEnd(end);
		
		txtA_Log.select(idx, end);
		
		idx = end;
	}
	
	private void loadMemoryVariables(String fileName) {

		cmbMemoryVariables_1.removeAllItems();

		memVariables = VPXUtilities.getMemoryAddressVariables(fileName);

		cmbMemoryVariables_1.addMemoryVariables(memVariables);

		cmbMemoryVariables_1.setSelectedIndex(0);

	}
	
	private void fillMemoryAddress(){
		
		if (memVariables.containsKey(cmbMemoryVariables_1.getSelectedItem().toString())) {

			System.out.println(memVariables.get(cmbMemoryVariables_1.getSelectedItem().toString()));
		}
		
	}
}

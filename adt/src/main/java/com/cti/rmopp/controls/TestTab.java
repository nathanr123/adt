/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author nathanr_kamal
 *
 */
public class TestTab extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TestTab() {

		setBounds(100, 100, 700, 500);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CTabbedPane tab = new CTabbedPane();

		File dir = new File(System.getProperty("user.home"));

		FileTableModel model = new FileTableModel(dir);

		CTable table = new CTable(model,30);

		tab.add("CTIJTable", new CScrollPane(table));
		
		tab.add("Test", new JLabel("Hi"));

		getContentPane().add(tab);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					TestTab frame = new TestTab();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}


}

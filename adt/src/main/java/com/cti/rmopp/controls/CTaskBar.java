/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.cti.rmopp.util.Util;

/**
 * @author nathanr_kamal
 *
 */
public class CTaskBar extends CPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 474530957031019869L;

	private CLabel statusLbl;

	private CMemoryBar memBar;

	public CTaskBar() {
		// setDark(true);

		setLayout(new BorderLayout());

		statusLbl = ComponentFactory.createLabel("Status");

		add(statusLbl, BorderLayout.LINE_START);

		memBar = new CMemoryBar();

		add(memBar, BorderLayout.EAST);

		setPreferredSize(new Dimension(Util.getScreenWidth(), Util.getScreenHeight() / 20));
	}

	public void updateStatus(String status) {
		
		statusLbl.setText(status);
	}

	public static void main(String[] args) {

		JFrame jf = new JFrame();

		jf.setBounds(10, 10, 900, 90);

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CTaskBar cm = new CTaskBar();

		jf.getContentPane().add(cm);

		jf.setVisible(true);

		/*
		 * Thread t = new Thread(new Runnable() {
		 * 
		 * @Override public void run() { int i = 0; while(true){
		 * 
		 * cm.updateStatus("status no : "+i++);
		 * 
		 * try { Thread.sleep(500); } catch (InterruptedException e) {
		 * e.printStackTrace(); } }
		 * 
		 * } }); t.start();
		 */
	}

	public void stopUpdates() {
		
		memBar.stopMemoryMonitor();
	}
	
}

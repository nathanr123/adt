package com.cti.vpx.controls;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

public class VPX_FindLog extends JDialog implements WindowListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4641396962949547339L;
	
	private JTextField txtFind;
	
	private FindController logger = null;
	
	private JLabel lblSearchStatus;
	
	/**
	 * Create the dialog.
	 */
	public VPX_FindLog(FindController log, VPX_ETHWindow prnt) {
		
		super(prnt);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.logger = log;
		
		setTitle("Search");
		
		setResizable(false);
		
		setBounds(100, 100, 387, 120);
		
		getContentPane().setLayout(new MigLayout("", "[46px][89px][2px][305px]", "[20px,fill][23px,fill][grow]"));
		
		JLabel lblFind = new JLabel("Find:");
		
		getContentPane().add(lblFind, "cell 0 0,grow");
		
		txtFind = new JTextField();
		
		getContentPane().add(txtFind, "cell 1 0 3 1,growx,aligny top");
		
		txtFind.setColumns(10);
		
		JButton btnFind = new JButton("Find");
		
		btnFind.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				logger.find(txtFind.getText());
			}
		});
		
		getContentPane().add(btnFind, "cell 1 1,growx,aligny top");
		
		JButton btnClose = new JButton("Close");
		
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				logger.clearFind();
				
				VPX_FindLog.this.dispose();
			}
		});
		
		getContentPane().add(btnClose, "cell 3 1,alignx left,aligny top");
		
		lblSearchStatus = new JLabel("");
		
		getContentPane().add(lblSearchStatus, "cell 1 2 3 1,growx");
		
		addWindowListener(this);
		
		centerFrame();
	}
	
	private void clear() {
		
		txtFind.setText("");
		
		lblSearchStatus.setText("");
		
	}
	
	public void updateStatus(String msg) {
		
		lblSearchStatus.setText(msg);
		
	}
	
	private void centerFrame() {
		
		Dimension windowSize = getSize();
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		Point centerPoint = ge.getCenterPoint();
		
		int dx = centerPoint.x - windowSize.width / 2;
		
		int dy = centerPoint.y - windowSize.height / 2;
		
		setLocation(dx, dy);
	}
	
	public void showFindWindow() {
		
		clear();
		
		setVisible(true);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		
		logger.clearFind();
		
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}

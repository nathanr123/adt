package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_BusyWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8556421629676554896L;

	private final JPanel contentPanel = new JPanel();

	private JLabel lbl_Current_Scanning_IP;

	private JProgressBar progressBar;

	private JLabel lbl_Detecting_Processor;

	private VPX_ETHWindow parent;

	private String title;
	
	private String msg;

	/**
	 * Create the dialog.
	 */
	public VPX_BusyWindow(VPX_ETHWindow parent, String title, String msg) {

		super(parent);

		this.parent = parent;

		this.title = title;

		this.msg = msg;

		init();

		loadComponents();

		centerFrame();

		setVisible(true);
		
		toFront();
		
		validate();
		
		repaint();
	}

	private void init() {

		setTitle(title);

		setType(Type.NORMAL);

		setIconImage(VPXUtilities.getAppIcon());

		setAlwaysOnTop(true);
		
		setBounds(100, 100, 431, 166);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}

	private void loadComponents() {

		contentPanel.setLayout(new MigLayout("", "[523.00px]", "[14px][][][][][]"));

		lbl_Detecting_Processor = VPXComponentFactory.createJLabel("Detecting Processors");
		lbl_Detecting_Processor.setText("Flashing File");

		contentPanel.add(lbl_Detecting_Processor, "cell 0 0 1 2");
		
				lbl_Current_Scanning_IP = VPXComponentFactory.createJLabel("");
				
						lbl_Current_Scanning_IP.setText(msg);
						
								contentPanel.add(lbl_Current_Scanning_IP, "cell 0 2,alignx left,aligny top");
		
				progressBar = new JProgressBar();
				
						progressBar.setIndeterminate(true);
						
								progressBar.setStringPainted(false);
								
										contentPanel.add(progressBar, "cell 0 3,growx");

		JPanel buttonPane = VPXComponentFactory.createJPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

	}

	public void close() {
		VPX_BusyWindow.this.dispose();
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}
	
	public static void main(String[] args) {
		new VPX_BusyWindow(null, "Tielw", "message").setVisible(true);
	}
}

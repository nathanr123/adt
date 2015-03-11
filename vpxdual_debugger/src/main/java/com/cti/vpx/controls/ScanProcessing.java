package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.view.VPX_Dual_ADT_RootWindow;

public class ScanProcessing extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8556421629676554896L;

	private final JPanel contentPanel = new JPanel();

	private VPX_Dual_ADT_RootWindow parent;

	private String fromIP, toIP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ScanProcessing dialog = new ScanProcessing(null, "", "");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ScanProcessing(VPX_Dual_ADT_RootWindow parent, String from_ip, String to_ip) {

		super(parent);

		this.parent = parent;

		this.fromIP = from_ip;

		this.toIP = to_ip;

		parseNsetScanValues();

		init();

		loadComponents();

		centerFrame();

		setVisible(true);

	}

	private void parseNsetScanValues() {
		String[] fromips = fromIP.split("\\.");
		String[] toips = fromIP.split("\\.");
		
		int class1_start = Integer.parseInt(fromips[0]);
		int class2_start = Integer.parseInt(fromips[1]);
		int class3_start = Integer.parseInt(fromips[2]);
		int class4_start = Integer.parseInt(fromips[3]);
		
		int class1_end = Integer.parseInt(toips[0]);
		int class2_end = Integer.parseInt(toips[1]);
		int class3_end = Integer.parseInt(toips[2]);
		int class4_end = Integer.parseInt(toips[3]);
		
		for(;class1_start<=class1_end;class1_start++){
			for(;class2_start<=class2_end;class2_start++){
				
			}
		}
	}

	private void init() {

		setTitle("Processing on Scan");

		setType(Type.NORMAL);

		setAlwaysOnTop(true);

		setModal(true);

		setBounds(100, 100, 554, 166);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}

	private void loadComponents() {

		contentPanel.setLayout(new MigLayout("", "[523.00px]", "[14px][][][][][]"));

		JLabel lblNewLabel_1 = new JLabel("Detecting / Connecting Processors");

		contentPanel.add(lblNewLabel_1, "cell 0 0 1 2");

		JLabel lblNewLabel = new JLabel("IP Address : 172.17.1.2");

		contentPanel.add(lblNewLabel, "cell 0 4,alignx left,aligny top");

		JProgressBar progressBar = new JProgressBar();

		contentPanel.add(progressBar, "cell 0 5,growx");

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Cancel");

		cancelButton.setActionCommand("Cancel");

		buttonPane.add(cancelButton);

	}

	private void centerFrame() {

		Dimension windowSize = getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		setLocation(dx, dy);
	}

	class ScanWorker extends SwingWorker<VPXSystem, Integer> {

		@Override
		protected VPXSystem doInBackground() throws Exception {

			return null;
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
		}

		@Override
		protected void process(List<Integer> chunks) {
			// TODO Auto-generated method stub
			super.process(chunks);
		}
	}
}

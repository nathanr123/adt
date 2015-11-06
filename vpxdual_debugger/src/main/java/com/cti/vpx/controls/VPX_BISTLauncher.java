package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

public class VPX_BISTLauncher extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8219412088376944542L;

	private final JPanel contentPanel = new JPanel();

	private VPX_ETHWindow parent;
	
	private JLabel lblSubSystemName;
	
	private JLabel lblIPVal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_BISTLauncher dialog = new VPX_BISTLauncher(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_BISTLauncher(VPX_ETHWindow parnt) {

		this.parent = parnt;

		init();

		loadComponents();
		
		centerFrame();

	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public VPX_BISTLauncher(VPX_ETHWindow parnt,String ip,String subSystem) {

		this.parent = parnt;

		init();

		loadComponents();

		lblSubSystemName.setText(subSystem);
		
		lblIPVal.setText(ip);
		
		centerFrame();
	}

	private void init() {

		setTitle("BIST Launcher");

		setIconImage(VPXConstants.Icons.ICON_BIST.getImage());

		setAlwaysOnTop(true);

		setLocationRelativeTo(parent);

		setResizable(false);

		setBounds(100, 100, 280, 251);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout());

	}

	private void loadComponents() {

		contentPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Select Processors to start BIST", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new MigLayout("", "[83px][162px]", "[24px,grow,fill][grow,fill][23px,grow,fill][23px,grow,fill][23px,grow,fill]"));

		lblSubSystemName = new JLabel("Sub System Name");

		contentPanel.add(lblSubSystemName, "cell 1 0");
		
		JLabel lblIp = new JLabel("IP Address");
		
		contentPanel.add(lblIp, "cell 0 1");
		
		lblIPVal = new JLabel("172.17.1.28");
		
		contentPanel.add(lblIPVal, "cell 1 1");

		JCheckBox chckbxNewCheckBox = new JCheckBox("P2020");

		chckbxNewCheckBox.setEnabled(false);

		chckbxNewCheckBox.setSelected(true);

		contentPanel.add(chckbxNewCheckBox, "cell 1 2,alignx left,aligny top");

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("DSP 1");

		contentPanel.add(chckbxNewCheckBox_1, "cell 1 3,alignx left,aligny top");

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("DSP 2");

		contentPanel.add(chckbxNewCheckBox_2, "cell 1 4,alignx left,aligny top");

		JLabel lblSubSystems = new JLabel("Sub Systems");

		contentPanel.add(lblSubSystems, "cell 0 0,grow");

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Start");

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_BISTLauncher.this.dispose();

				parent.startBist();

			}
		});

		buttonPane.add(okButton);

		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Close");

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_BISTLauncher.this.dispose();
			}
		});
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


	public void setSubSystemName(String sub) {

		lblSubSystemName.setText(sub);

	}

	public void setP2020(String ip) {

		lblIPVal.setText(ip);

	}
}

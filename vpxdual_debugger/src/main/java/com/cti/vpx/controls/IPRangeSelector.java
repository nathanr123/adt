package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.view.VPX_Dual_ADT_RootWindow;

public class IPRangeSelector extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6540961732678062286L;

	private final JPanel contentPanel = new JPanel();

	private JTextField fromIP;

	private JTextField toIP;

	private VPX_Dual_ADT_RootWindow parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IPRangeSelector dialog = new IPRangeSelector(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IPRangeSelector(VPX_Dual_ADT_RootWindow parent) {

		super(parent);

		this.parent = parent;

		init();

		loadComponents();
		
		centerFrame();
	}

	private void centerFrame() {

		Dimension windowSize = getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		setLocation(dx, dy);
	}

	private void init() {

		setTitle("IP Scanning Window");

		setBounds(100, 100, 328, 232);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(null);
	}

	private void loadComponents() {

		JLabel lblNewLabel = new JLabel("From");

		lblNewLabel.setBounds(20, 11, 61, 29);

		contentPanel.add(lblNewLabel);

		JLabel lblTo = new JLabel("To");

		lblTo.setBounds(20, 63, 61, 29);

		contentPanel.add(lblTo);

		fromIP = new JTextField();

		fromIP.setBounds(89, 11, 219, 29);

		contentPanel.add(fromIP);

		fromIP.setColumns(10);

		toIP = new JTextField();

		toIP.setColumns(10);

		toIP.setBounds(89, 63, 219, 29);

		contentPanel.add(toIP);

		JSeparator separator = new JSeparator();

		separator.setBounds(0, 99, 353, 2);

		contentPanel.add(separator);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Keep old values");

		chckbxNewCheckBox.setBounds(6, 113, 232, 23);

		contentPanel.add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Revalidate old values");

		chckbxNewCheckBox_1.setBounds(6, 139, 232, 23);

		contentPanel.add(chckbxNewCheckBox_1);

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton(new ScanAction("Scan"));

		buttonPane.add(okButton);

		JButton cancelButton = new JButton(new CancelAction("Cancel"));

		buttonPane.add(cancelButton);
	}

	class ScanAction extends AbstractAction {

		/**
		 * 
		 */

		public ScanAction(String name) {
			putValue(Action.NAME, name);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			IPRangeSelector.this.setVisible(false);

			new ScanProcessing(parent, fromIP.getText(), toIP.getText());

		}

	}

	class CancelAction extends AbstractAction {

		/**
		 * 
		 */

		public CancelAction(String name) {
			putValue(Action.NAME, name);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			IPRangeSelector.this.dispose();

		}

	}

}

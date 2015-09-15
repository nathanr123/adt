package com.cti.vpx.controls.hex;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

public class VPX_MemorySetWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -883334257865770538L;

	private final JPanel contentPanel = new JPanel();

	private JTextField txtCurrentAddress;

	private JTextField txtCurrentValue;

	private JTextField txtNewValue;

	private JTextField txtTypeSize;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_MemorySetWindow dialog = new VPX_MemorySetWindow("0xA0000000", "6B", "8-Bit Hex");

			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_MemorySetWindow() {

		this("", "", "");
	}

	public VPX_MemorySetWindow(String address, String value, String type) {

		init();

		loadComponents();

		setValues(address, value, type);
	}

	private void init() {

		setResizable(false);

		setTitle("Fill Value");

		setBounds(100, 100, 346, 199);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(
				new MigLayout("", "[101.00px,fill][][grow]", "[14px,grow,fill][grow,fill][grow,fill][grow,fill]"));

	}

	private void loadComponents() {

		JLabel lblAddress = new JLabel("Address");

		contentPanel.add(lblAddress, "cell 0 0");

		JLabel lblNewLabel_1 = new JLabel("  ");

		contentPanel.add(lblNewLabel_1, "cell 1 0,alignx trailing");

		txtCurrentAddress = new JTextField();

		contentPanel.add(txtCurrentAddress, "cell 2 0,growx");

		txtCurrentAddress.setColumns(10);

		JLabel lblCurrentValue = new JLabel("Current Value");

		contentPanel.add(lblCurrentValue, "cell 0 1");

		txtCurrentValue = new JTextField();

		txtCurrentValue.setColumns(10);

		contentPanel.add(txtCurrentValue, "cell 2 1,growx");

		JLabel lblNewValue = new JLabel("New Value");

		contentPanel.add(lblNewValue, "cell 0 2");

		txtNewValue = new JTextField();

		txtNewValue.setColumns(10);

		contentPanel.add(txtNewValue, "cell 2 2,growx");

		JLabel lblTypeSize = new JLabel("Type Size");

		contentPanel.add(lblTypeSize, "cell 0 3");

		txtTypeSize = new JTextField();

		txtTypeSize.setEditable(false);

		txtTypeSize.setColumns(10);

		contentPanel.add(txtTypeSize, "cell 2 3,growx");

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnSetMemoryValue = new JButton("OK");

		btnSetMemoryValue.setActionCommand("OK");

		buttonPane.add(btnSetMemoryValue);

		getRootPane().setDefaultButton(btnSetMemoryValue);

		JButton btnCancel = new JButton("Cancel");

		btnCancel.setActionCommand("Cancel");

		buttonPane.add(btnCancel);

	}

	public void setValues(String address, String value, String type) {

		txtCurrentAddress.setText(address);

		txtCurrentValue.setText(value);

		txtNewValue.setText("");

		txtTypeSize.setText(type);
	}

	public String getNewValue() {

		return "";
	}
}

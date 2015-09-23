package com.cti.vpx.controls.hex;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.util.VPXUtilities;

import net.miginfocom.swing.MigLayout;

public class VPX_MemorySetWindow extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -883334257865770538L;

	public static final int SETMEMORY = 0;

	public static final int FILLMEMORY = 1;

	private final JPanel contentPanel = new JPanel();

	private JTextField txtStartAddress;

	private JTextField txtLengthValue;

	private JTextField txtDataValue;

	private JTextField txtTypeSize;

	private int currentMode = FILLMEMORY;

	private JLabel lblAddress;

	private JLabel lblLengthValue;

	private JLabel lblDataValue;

	private int retValue = 1;

	private String startAddress;

	private String length;

	private String data;

	private long startAddrVal;

	private long totalLength;

	private JButton btnSetMemoryValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_MemorySetWindow dialog = new VPX_MemorySetWindow(VPX_MemorySetWindow.SETMEMORY, "0xA0000000", "6B",
					"8-Bit Hex");

			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_MemorySetWindow(HexEditorPanel prnt) {

		this(FILLMEMORY, "", "", "");

		setLocationRelativeTo(prnt);
	}

	public VPX_MemorySetWindow(int mode) {

		this(mode, "", "", "");
	}

	public VPX_MemorySetWindow(int mode, String address, String value, String type) {

		this.currentMode = mode;

		init();

		loadComponents();

		loadmodeProerties();

		setValues(address, value, type);
	}

	private void init() {

		setResizable(false);

		setModal(true);

		setTitle("Fill Value");

		setBounds(100, 100, 346, 199);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(
				new MigLayout("", "[101.00px,fill][][grow]", "[14px,grow,fill][grow,fill][grow,fill][grow,fill]"));

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		addWindowListener(this);

	}

	private void loadComponents() {

		lblAddress = new JLabel("Start Address");

		contentPanel.add(lblAddress, "cell 0 0");

		JLabel lblNewLabel_1 = new JLabel("  ");

		contentPanel.add(lblNewLabel_1, "cell 1 0,alignx trailing");

		txtStartAddress = new JTextField();

		contentPanel.add(txtStartAddress, "cell 2 0,growx");

		txtStartAddress.setColumns(10);

		lblLengthValue = new JLabel("Length");

		contentPanel.add(lblLengthValue, "cell 0 1");

		txtLengthValue = new JTextField();

		txtLengthValue.setColumns(10);

		contentPanel.add(txtLengthValue, "cell 2 1,growx");

		lblDataValue = new JLabel("Data Value");

		contentPanel.add(lblDataValue, "cell 0 2");

		txtDataValue = new JTextField();

		txtDataValue.setColumns(10);

		contentPanel.add(txtDataValue, "cell 2 2,growx");

		JLabel lblTypeSize = new JLabel("Type Size");

		contentPanel.add(lblTypeSize, "cell 0 3");

		txtTypeSize = new JTextField();

		txtTypeSize.setEditable(false);

		txtTypeSize.setColumns(10);

		contentPanel.add(txtTypeSize, "cell 2 3,growx");

		JPanel buttonPane = new JPanel();

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnSetMemoryValue = new JButton("Fill");

		btnSetMemoryValue.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				startAddress = txtStartAddress.getText().trim();

				length = txtLengthValue.getText().trim();

				data = txtDataValue.getText().trim();

				boolean validStAddr = isValidAddress(startAddress);

				boolean validLen = isValidLength(length);

				boolean validData = isValidData(data);

				if (validLen && validStAddr && validData) {

					retValue = 1;

					dispose();
				} else {

					retValue = 0;

					if (!validStAddr && !validLen) {

						JOptionPane.showMessageDialog(VPX_MemorySetWindow.this, "Please enter valid detail", "Error",
								JOptionPane.ERROR_MESSAGE);

					} else if (!validStAddr) {

						JOptionPane.showMessageDialog(VPX_MemorySetWindow.this, "Please enter valid start address",
								"Error", JOptionPane.ERROR_MESSAGE);

					} else if (!validLen) {

						JOptionPane.showMessageDialog(VPX_MemorySetWindow.this, "Please enter valid length", "Error",
								JOptionPane.ERROR_MESSAGE);

					} else if (!validData) {

						JOptionPane.showMessageDialog(VPX_MemorySetWindow.this,
								"Data value is in valid.\nPlease enter valid data", "Error", JOptionPane.ERROR_MESSAGE);

					}
				}

			}
		});

		btnSetMemoryValue.setActionCommand("Fill");

		buttonPane.add(btnSetMemoryValue);

		getRootPane().setDefaultButton(btnSetMemoryValue);

		JButton btnCancel = new JButton("Cancel");

		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				retValue = 0;

				dispose();
			}
		});

		btnCancel.setActionCommand("Cancel");

		buttonPane.add(btnCancel);

	}

	public void setValues(String address, String value, String type) {

		txtStartAddress.setText(address);

		txtLengthValue.setText(value);

		txtDataValue.setText("");

		txtTypeSize.setText(type);
	}

	private void loadmodeProerties() {

		if (currentMode == FILLMEMORY) {

			lblAddress.setText("Start Address");

			lblLengthValue.setText("Length");

			btnSetMemoryValue.setText("Fill");

			txtStartAddress.setEditable(true);

			txtLengthValue.setEditable(true);

			txtStartAddress.setText("");

			txtLengthValue.setText("");

			setTitle("Fill Memory");

		} else if (currentMode == SETMEMORY) {

			lblAddress.setText("Address");

			txtStartAddress.setEditable(false);

			txtLengthValue.setEditable(false);

			lblLengthValue.setText("Current Value");

			btnSetMemoryValue.setText("Set");

			setTitle("Set Memory");
		}

	}

	public void setTypeSize(String typeSize) {

		txtTypeSize.setText(typeSize);
	}

	public int showFillMemoryWinow() {

		setVisible(true);

		return retValue;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public String getLength() {
		return length;
	}

	public String getData() {
		return data;
	}

	public void setStartAddr(long start) {

		this.startAddrVal = start;

	}

	public void setCurrentValue(String currValue) {

		this.length = currValue;

		txtLengthValue.setText(currValue);
	}

	public void setAddress(long start) {

		this.startAddrVal = start;

		txtStartAddress.setText(String.format("0x%08x", start).toUpperCase());
	}

	public String getAddress() {

		return txtStartAddress.getText();
	}

	public void setTotalLength(long length) {

		this.totalLength = length;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

		retValue = 0;

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

	private boolean isValidAddress(String addr) {

		if (currentMode == FILLMEMORY) {

			long address =(VPXUtilities.getValue(addr) == -1)?0:VPXUtilities.getValue(addr);

			if ((address < startAddrVal) || (address > (startAddrVal + totalLength))) {

				return false;
			}
		}

		return true;
	}

	private boolean isValidLength(String length) {

		boolean retVal = true;
		try {

			Long.valueOf(length);

			retVal = true;
		} catch (Exception e) {
			retVal = false;
		}

		return retVal;
	}

	private boolean isValidData(String data) {

		return true;/*
					 * try { Long.toHexString(Long.decode(data)).toUpperCase();
					 * 
					 * return true;
					 * 
					 * } catch (Exception e) {
					 * 
					 * return false; }
					 */
	}

	public void setMode(int mode) {

		this.currentMode = mode;

		loadmodeProerties();
	}

}

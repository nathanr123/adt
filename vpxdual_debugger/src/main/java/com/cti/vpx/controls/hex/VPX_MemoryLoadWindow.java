package com.cti.vpx.controls.hex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.math.BigInteger;
import java.awt.event.ActionEvent;

public class VPX_MemoryLoadWindow extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6534113281730821472L;

	private final JPanel contentPanel = new JPanel();

	private JTextField txtFileName;

	private JTextField txtStartAddress;

	private JLabel lblStartAddress;

	private String fileName;

	private String startAddress;

	private boolean isStartAddress = true;

	private int retValue = 1;

	private final JFileChooser fileDialog = new JFileChooser();

	FileNameExtensionFilter filterDat = new FileNameExtensionFilter("Dat Files", "dat");

	FileNameExtensionFilter filterBin = new FileNameExtensionFilter("Bin Files", "bin");

	FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Out Files", "out");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {

			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {

					VPX_MemoryLoadWindow dialog = new VPX_MemoryLoadWindow(null);

					int i = dialog.showLoadMemoryWinow();

					if (i == 1) {
						System.out.println(i);

						System.out.println(dialog.getFileName());

						System.out.println(dialog.isStartAddress());

						System.out.println(dialog.getStartAddress());
					}

				}
			});

			th.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_MemoryLoadWindow(HexEditorPanel prnt) {

		init();

		loadComponents();
		
		setLocationRelativeTo(prnt);

	}

	private void init() {

		setTitle("Load Memory");

		setModal(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 630, 276);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new BorderLayout(0, 0));

		addWindowListener(this);

		fileDialog.setAcceptAllFileFilterUsed(false);

		fileDialog.addChoosableFileFilter(filterBin);

		fileDialog.addChoosableFileFilter(filterOut);

		fileDialog.addChoosableFileFilter(filterDat);

		fileDialog.setDialogTitle("Load Memory");
	}

	private void loadComponents() {

		JPanel panelTitle = new JPanel();

		panelTitle.setPreferredSize(new Dimension(10, 40));

		contentPanel.add(panelTitle, BorderLayout.NORTH);

		panelTitle.setLayout(new BorderLayout(0, 0));

		JLabel lblLoadMemory = new JLabel("Load Memory");

		lblLoadMemory.setOpaque(true);

		lblLoadMemory.setBackground(Color.WHITE);

		lblLoadMemory.setFont(new Font("Tahoma", Font.BOLD, 16));

		panelTitle.add(lblLoadMemory);

		JPanel panelCenter = new JPanel();

		panelCenter.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		contentPanel.add(panelCenter, BorderLayout.CENTER);

		panelCenter.setLayout(new MigLayout("", "[right][][grow]", "[][][][][][]"));

		JLabel lblFile = new JLabel("File");

		panelCenter.add(lblFile, "cell 0 1");

		txtFileName = new JTextField();

		panelCenter.add(txtFileName, "cell 2 1,growx");

		txtFileName.setColumns(10);

		JButton btnBrowse = new JButton("Browse...");

		btnBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int returnVal = fileDialog.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					txtFileName.setText(fileDialog.getSelectedFile().getAbsolutePath());

				}

			}
		});

		panelCenter.add(btnBrowse, "cell 2 2,alignx right");

		JCheckBox chkStartAddress = new JCheckBox("Start Address");

		chkStartAddress.setSelected(true);

		chkStartAddress.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				isStartAddress = chkStartAddress.isSelected();

				txtStartAddress.setText("0x0000000");

				txtStartAddress.setEnabled(chkStartAddress.isSelected());

				lblStartAddress.setEnabled(chkStartAddress.isSelected());
			}
		});
		
		JLabel lblNewLabel = new JLabel("Note: Select a file containing the memory data to be loaded");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelCenter.add(lblNewLabel, "cell 2 3");

		panelCenter.add(chkStartAddress, "cell 0 4");

		lblStartAddress = new JLabel("Address");

		panelCenter.add(lblStartAddress, "cell 0 5");

		txtStartAddress = new JTextField();

		txtStartAddress.setText("0xA0000000");

		panelCenter.add(txtStartAddress, "flowx,cell 2 5,growx");

		txtStartAddress.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("");

		panelCenter.add(lblNewLabel_2, "cell 2 5,growx");

		JPanel panelControls = new JPanel();

		panelControls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		panelControls.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(panelControls, BorderLayout.SOUTH);

		JButton okButton = new JButton("Load");

		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				fileName = txtFileName.getText().trim();

				startAddress = txtStartAddress.getText().trim();

				boolean validFile = isValidFileName(fileName);

				boolean validValue = isValidValue(startAddress);

				if (validFile && validValue) {

					retValue = 1;

					dispose();

				} else {
					if (!validFile && !validValue) {

						JOptionPane.showMessageDialog(VPX_MemoryLoadWindow.this, "Please enter valid detail", "Error",
								JOptionPane.ERROR_MESSAGE);

					} else if (!validFile) {

						JOptionPane.showMessageDialog(VPX_MemoryLoadWindow.this, "Please enter valid file", "Error",
								JOptionPane.ERROR_MESSAGE);

					} else if (!validValue) {

						JOptionPane.showMessageDialog(VPX_MemoryLoadWindow.this, "Please enter valid start address",
								"Error", JOptionPane.ERROR_MESSAGE);

					}
				}

			}
		});

		okButton.setActionCommand("OK");

		panelControls.add(okButton);

		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");

		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				retValue = 0;

				dispose();
			}
		});

		cancelButton.setActionCommand("Cancel");

		panelControls.add(cancelButton);

	}

	public int showLoadMemoryWinow() {

		setVisible(true);

		return retValue;
	}

	public String getFileName() {
		return fileName;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public boolean isStartAddress() {
		return isStartAddress;
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

	private boolean isValidFileName(String fileName) {

		return new File(fileName).exists();

	}

	private boolean isValidValue(String value) {

		String val = value;

		try {

			if (value.startsWith("0x") || value.startsWith("0X")) {

				val = value.substring(2, value.length());
			}

			Long.parseLong(val, 16);
			
			return true;
		} catch (Exception e) {
			
			return false;

		}

	}

}

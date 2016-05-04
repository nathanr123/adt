package com.cti.vpx.controls.hex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.util.VPXLogger;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class VPX_MemoryLoadWindow extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6534113281730821472L;

	private final JPanel contentPanel = new JPanel();

	private JTextField txtFileName;

	private JTextField txtStartAddress;

	private String fileName;

	private String startAddress;

	private boolean isStartAddress = true;

	private int retValue = 1;

	private final JFileChooser fileDialog = new JFileChooser();

	private FileNameExtensionFilter filterDat = new FileNameExtensionFilter("Dat Files", "dat");

	private FileNameExtensionFilter filterBin = new FileNameExtensionFilter("Bin Files", "bin");

	private FileNameExtensionFilter filterOut = new FileNameExtensionFilter("Out Files", "out");

	private JTextField txtLengthBytes;

	private JTextField txtLengthWords;

	private String currentProcessor;

	private String currentCore;

	private JComboBox<String> cmbFormat;

	private JComboBox<String> cmbDelimiter;
	private JLabel lblDelimiter;
	private JLabel lblFormat;
	private JCheckBox chkReadBinary;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {

			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {

					VPX_MemoryLoadWindow dialog = new VPX_MemoryLoadWindow(null);

					int i = dialog.showLoadMemoryWinow("", "");

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
			VPXLogger.updateError(e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_MemoryLoadWindow(HexEditorPanel prnt) {
		setResizable(false);

		init();

		loadComponents();

		setLocationRelativeTo(prnt);

	}

	private void init() {

		setTitle("Load Memory");

		setModal(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 645, 364);

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
		panelCenter.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("93px"), ColumnSpec.decode("33px"),
						ColumnSpec.decode("143px"), FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("104px"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("79px"), ColumnSpec.decode("61px"),
						ColumnSpec.decode("3px"), FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("72px"), },
				new RowSpec[] { FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("32px"), FormSpecs.LINE_GAP_ROWSPEC,
						RowSpec.decode("27px"), FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("23px"),
						FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("23px"), FormSpecs.PARAGRAPH_GAP_ROWSPEC,
						RowSpec.decode("23px"), FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("27px"), }));

		JLabel lblFile = new JLabel("File");
		lblFile.setHorizontalAlignment(SwingConstants.RIGHT);

		panelCenter.add(lblFile, "2, 2, fill, fill");

		txtFileName = new JTextField();

		panelCenter.add(txtFileName, "4, 2, 9, 1, fill, top");

		txtFileName.setColumns(10);

		JButton btnBrowse = new JButton("Browse...");

		btnBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int returnVal = fileDialog.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					txtFileName.setText(fileDialog.getSelectedFile().getAbsolutePath());

					txtLengthBytes
							.setText("0x" + Long.toHexString(fileDialog.getSelectedFile().length()).toUpperCase());

					txtLengthWords
							.setText("0x" + Long.toHexString(fileDialog.getSelectedFile().length() / 4).toUpperCase());
				}

			}
		});

		panelCenter.add(btnBrowse, "10, 4, 3, 1, left, fill");

		JLabel lblNote = new JLabel("Note: Select a file containing the memory data to be loaded");
		lblNote.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelCenter.add(lblNote, "4, 4, 5, 1, left, fill");

		JCheckBox chkStartAddress = new JCheckBox("Start Address");

		chkStartAddress.setSelected(true);

		chkStartAddress.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				isStartAddress = chkStartAddress.isSelected();

				txtStartAddress.setText("0x0000000");

				txtStartAddress.setEnabled(chkStartAddress.isSelected());
			}
		});

		JLabel lblLength = new JLabel("Length");
		lblLength.setHorizontalAlignment(SwingConstants.RIGHT);
		panelCenter.add(lblLength, "2, 6, fill, fill");

		txtLengthBytes = new JTextField();

		txtLengthBytes.setEditable(false);

		txtLengthBytes.setColumns(10);

		panelCenter.add(txtLengthBytes, "4, 6, fill, fill");

		panelCenter.add(chkStartAddress, "2, 12, left, fill");

		txtStartAddress = new JTextField();

		txtStartAddress.setText("0xA0000000");

		panelCenter.add(txtStartAddress, "4, 12, 3, 1, fill, fill");

		txtStartAddress.setColumns(10);

		JLabel lblInBytes = new JLabel("Memory Bytes   (or)   ");
		panelCenter.add(lblInBytes, "6, 6, left, fill");

		txtLengthWords = new JTextField();

		txtLengthWords.setEditable(false);

		txtLengthWords.setColumns(10);

		panelCenter.add(txtLengthWords, "8, 6, 3, 1, fill, fill");

		JLabel lblInWords = new JLabel("Memory Words");
		panelCenter.add(lblInWords, "12, 6, left, fill");

		lblFormat = new JLabel("Format");
		lblFormat.setEnabled(false);
		lblFormat.setHorizontalAlignment(SwingConstants.RIGHT);
		panelCenter.add(lblFormat, "2, 10, fill, fill");

		lblDelimiter = new JLabel("Delimiter   ");
		lblDelimiter.setHorizontalAlignment(SwingConstants.RIGHT);
		panelCenter.add(lblDelimiter, "6, 10, fill, fill");

		cmbFormat = new JComboBox<String>(
				new DefaultComboBoxModel<String>(new String[] { "8 Bit Hex", "16 Bit Hex", "32 Bit Hex", "64 Bit Hex",
						"16 Bit Signed", "32 Bit Signed", "16 Bit Unsigned", "32 Bit Unsigned", "32 Bit Floating" }));
		cmbFormat.setEnabled(false);

		cmbFormat.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getSource().equals(cmbFormat)) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

					}
				}
			}
		});

		panelCenter.add(cmbFormat, "4, 10, fill, fill");

		cmbDelimiter = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] { "New Line", "Comma", }));
		cmbDelimiter.setEnabled(false);

		panelCenter.add(cmbDelimiter, "8, 10, 3, 1, fill, fill");

		chkReadBinary = new JCheckBox("Read as Binary");
		chkReadBinary.setSelected(true);

		chkReadBinary.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				enableReadOptions();
			}
		});

		panelCenter.add(chkReadBinary, "4, 8, left, top");

		JPanel panelControls = new JPanel();

		panelControls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		panelControls.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(panelControls, BorderLayout.SOUTH);

		JButton okButton = new JButton("Load");

		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (!currentProcessor.equals("Select Processor") && !currentCore.equals("Select Core")) {

					fileName = txtFileName.getText().trim();

					startAddress = txtStartAddress.getText().trim();

					boolean validFile = isValidFileName(fileName);

					boolean validValue = isValidValue(startAddress);

					if (validFile && validValue) {

						retValue = 1;

						dispose();

					} else {
						if (!validFile && !validValue) {

							JOptionPane.showMessageDialog(VPX_MemoryLoadWindow.this, "Please enter valid detail",
									"Error", JOptionPane.ERROR_MESSAGE);

						} else if (!validFile) {

							JOptionPane.showMessageDialog(VPX_MemoryLoadWindow.this, "Please enter valid file", "Error",
									JOptionPane.ERROR_MESSAGE);

						} else if (!validValue) {

							JOptionPane.showMessageDialog(VPX_MemoryLoadWindow.this, "Please enter valid start address",
									"Error", JOptionPane.ERROR_MESSAGE);

						}
					}

				} else {
					if (currentProcessor.equals("Select Processor")) {

						JOptionPane.showMessageDialog(VPX_MemoryLoadWindow.this, "Please select processor",
								"Validation", JOptionPane.ERROR_MESSAGE);

					} else if (currentCore.equals("Select Core")) {

						JOptionPane.showMessageDialog(VPX_MemoryLoadWindow.this, "Please select core", "Validation",
								JOptionPane.ERROR_MESSAGE);

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

	private void enableReadOptions() {

		lblDelimiter.setEnabled(!chkReadBinary.isSelected());

		lblFormat.setEnabled(!chkReadBinary.isSelected());

		cmbDelimiter.setEnabled(!chkReadBinary.isSelected());

		cmbFormat.setEnabled(!chkReadBinary.isSelected());
	}

	public int showLoadMemoryWinow(String processor, String core) {

		this.currentProcessor = processor;

		this.currentCore = core;

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

	public boolean isReadAsBinary() {

		return chkReadBinary.isSelected();

	}

	public int getFormat() {

		return cmbFormat.getSelectedIndex();

	}

	public int getDelimiter() {

		return cmbDelimiter.getSelectedIndex();

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
			VPXLogger.updateError(e);
			return false;

		}

	}
}

package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_BootWindow extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4001514154495393537L;
	
	private static String BOARDNOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reboots full board</left></body></html>";
	
	private static String P2020NOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reboots P2020 only</left></body></html>";
	
	private static String DSP1NOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reboots DSP 1 Processor only</left></body></html>";
	
	private static String DSP2NOTE = "<html><body><b>Note:</b><br><left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reboots DSP 2 Processor only</left></body></html>";
	
	private final JPanel contentPanel = new JPanel();
	
	private JComboBox<String> cmbProcessor;
	
	private JComboBox<String> cmbFlashDevice;
	
	private JComboBox<String> cmbPage;
	
	private VPX_ETHWindow parent;
	
	private String currentip;
	
	private JLabel lblNote;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			VPX_BootWindow dialog = new VPX_BootWindow(null);
			
			dialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public VPX_BootWindow(VPX_ETHWindow parent) {
		
		super(parent);
		
		this.parent = parent;
		
		init();
		
		loadComponents();
		
		loadFilters();
		
	}
	
	private void init() {
		
		setTitle("Boot Option");
		
		setBounds(100, 100, 359, 232);
		
		setResizable(false);
		
		setIconImage(VPXUtilities.getAppIcon());
		
		setModal(false);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setLayout(null);
		
	}
	
	private void loadComponents() {
		
		JLabel lblProcessor = new JLabel("Processor");
		
		lblProcessor.setBounds(21, 12, 114, 22);
		
		contentPanel.add(lblProcessor);
		
		cmbProcessor = new JComboBox<String>();
		
		cmbProcessor.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				if (arg0.getSource().equals(cmbProcessor)) {
					
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						
						loadProcessorSettings();
					}
				}
			}
		});
		
		cmbProcessor.setEditable(false);
		
		cmbProcessor.setPreferredSize(new Dimension(175, 22));
		
		cmbProcessor.setBounds(156, 12, 175, 22);
		
		contentPanel.add(cmbProcessor);
		
		JLabel lblFlashDevice = new JLabel("Flash Device");
		
		lblFlashDevice.setBounds(21, 46, 114, 22);
		
		contentPanel.add(lblFlashDevice);
		
		cmbFlashDevice = new JComboBox<String>();
		
		cmbFlashDevice.setEditable(false);
		
		cmbFlashDevice.setPreferredSize(new Dimension(175, 22));
		
		cmbFlashDevice.setBounds(156, 46, 175, 22);
		
		contentPanel.add(cmbFlashDevice);
		
		JLabel lblPage = new JLabel("Page");
		
		lblPage.setBounds(21, 80, 114, 22);
		
		contentPanel.add(lblPage);
		
		cmbPage = new JComboBox<String>();
		
		cmbPage.setEditable(false);
		
		cmbPage.setPreferredSize(new Dimension(175, 22));
		
		cmbPage.setBounds(156, 80, 175, 22);
		
		contentPanel.add(cmbPage);
		
		lblNote = new JLabel(P2020NOTE);
		
		lblNote.setVerticalAlignment(SwingConstants.TOP);
		
		lblNote.setBounds(10, 113, 333, 47);
		
		contentPanel.add(lblNote);
		
		JPanel buttonPane = new JPanel();
		
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("Boot");
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String msg = "";
				
				if (cmbProcessor.getSelectedIndex() == 0) {
					
					msg = "Please confirm do you want to reboot board?";
					
				} else {
					msg = String.format(
							"Please confirm the details\n\t\tProcessor : %s\n\t\tFlash Device : %s\n\t\tPage : %s\nDo you want to reboot?",
							cmbProcessor.getSelectedItem().toString(), cmbFlashDevice.getSelectedItem().toString(),
							cmbPage.getSelectedItem().toString());
					
				}
				int option = JOptionPane.showConfirmDialog(VPX_BootWindow.this, msg, "Confirmation",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
				if (option == JOptionPane.YES_OPTION) {
					
					parent.setReboot(currentip, cmbProcessor.getSelectedIndex(), cmbFlashDevice.getSelectedIndex(),
							cmbPage.getSelectedIndex());
					
					if (cmbProcessor.getSelectedIndex() > 0) {
						
						VPXLogger.updateLog(String.format("%s boot from flash device %s of page %s",
								cmbProcessor.getSelectedItem().toString(), cmbFlashDevice.getSelectedItem().toString(),
								cmbPage.getSelectedItem().toString()));
					} else {
						
						VPXLogger.updateLog(String.format("Board reboot initiated"));
					}
					
					VPX_BootWindow.this.dispose();
				}
				
			}
		});
		
		buttonPane.add(okButton);
		
		getRootPane().setDefaultButton(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				VPX_BootWindow.this.dispose();
				
			}
		});
		
		buttonPane.add(cancelButton);
		
	}
	
	private void loadFilters() {
		
		cmbProcessor.removeAllItems();
		
		cmbProcessor.addItem("Board");
		
		// cmbProcessor.addItem("P2020");
		
		cmbProcessor.addItem("DSP 1");
		
		cmbProcessor.addItem("DSP 2");
		
		cmbFlashDevice.removeAllItems();
		
		cmbFlashDevice.addItem("NAND");
		
		cmbFlashDevice.addItem("NOR");
		
		cmbPage.removeAllItems();
		
		cmbPage.addItem("0");
		
		cmbPage.addItem("1");
		
	}
	
	private void loadProcessorSettings() {
		
		int index = cmbProcessor.getSelectedIndex();
		
		if (index == 0) {
			
			// cmbFlashDevice.setEnabled(false);
			
			// cmbPage.setEnabled(false);
			
			lblNote.setText(BOARDNOTE);
			
		} else
			if (index == 1) {
				
				// cmbFlashDevice.setEnabled(true);
				
				// cmbPage.setEnabled(true);
				
				lblNote.setText(DSP1NOTE);
				
			} else
				if (index == 2) {
					
					// cmbFlashDevice.setEnabled(true);
					
					// cmbPage.setEnabled(true);
					
					lblNote.setText(DSP2NOTE);
					
				}
	}
	
	public void showBootoption(String ip) {
		
		currentip = ip;
		
		cmbProcessor.setSelectedIndex(0);
		
		cmbFlashDevice.setSelectedIndex(0);
		
		cmbPage.setSelectedIndex(0);
		
		setLocationRelativeTo(parent);
		
		setVisible(true);
	}
}

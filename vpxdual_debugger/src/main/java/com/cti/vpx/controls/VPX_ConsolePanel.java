package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import com.cti.vpx.command.MSGCommand;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_ConsolePanel extends JPanel implements ClipboardOwner {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2395447790825321429L;

	private JTextArea txtA_Console;

	private FileWriter fw;

	private JComboBox<String> cmbSubSystem;

	private JComboBox<String> cmbProcessor;

	private JComboBox<String> cmbCores;

	private VPXSystem vpxSystem;

	private VPXSubSystem curProcFilter;

	private boolean isFilterApply = false;

	List<String> ips = new ArrayList<String>();

	private VPX_ETHWindow parent;

	private int coreindex;

	/**
	 * Create the panel.
	 */
	public VPX_ConsolePanel(VPX_ETHWindow parnt) {

		this.parent = parnt;

		init();

		loadComponents();

		loadFilters();
	}

	private void init() {

		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		setLayout(new BorderLayout(0, 0));

	}

	private void loadComponents() {

		JPanel console_Panel = VPXComponentFactory.createJPanel();

		add(console_Panel, BorderLayout.NORTH);

		console_Panel.setLayout(new BorderLayout(0, 0));

		JPanel filterPanel = new JPanel();

		FlowLayout fl_filterPanel = (FlowLayout) filterPanel.getLayout();

		fl_filterPanel.setHgap(15);

		fl_filterPanel.setAlignment(FlowLayout.LEFT);

		console_Panel.add(filterPanel, BorderLayout.CENTER);

		JLabel lblSubSystem = new JLabel("Sub System");

		filterPanel.add(lblSubSystem);

		cmbSubSystem = new JComboBox<String>();

		cmbSubSystem.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbSubSystem)) {

					if (arg0.getStateChange() == ItemEvent.SELECTED) {

						loadProcessorsFilter();
					}
				}
			}
		});

		cmbSubSystem.setPreferredSize(new Dimension(120, 22));

		filterPanel.add(cmbSubSystem);

		JLabel lblProcessors = new JLabel("Processors");

		filterPanel.add(lblProcessors);

		cmbProcessor = new JComboBox<String>();

		cmbProcessor.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbProcessor)) {

					if (arg0.getStateChange() == ItemEvent.SELECTED) {

						loadCoresFilter();
					}
				}
			}
		});

		cmbProcessor.setPreferredSize(new Dimension(120, 22));

		filterPanel.add(cmbProcessor);

		JLabel lblCore = new JLabel("Core");

		filterPanel.add(lblCore);

		cmbCores = new JComboBox<String>();

		cmbCores.setPreferredSize(new Dimension(120, 22));

		filterPanel.add(cmbCores);

		JButton btnEnableFilter = new JButton("Filter");

		btnEnableFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				coreindex = 0;

				ips.clear();

				isFilterApply = true;

			}
		});

		filterPanel.add(btnEnableFilter);

		JButton btnClear = new JButton("Clear");

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				coreindex = 0;

				isFilterApply = false;

				ips.clear();

				cmbSubSystem.setSelectedIndex(0);

				cmbProcessor.setSelectedIndex(0);

				cmbCores.setSelectedIndex(0);

			}
		});

		filterPanel.add(btnClear);

		JPanel panel_1 = new JPanel();

		console_Panel.add(panel_1, BorderLayout.EAST);

		JButton btn_Console_Clear = VPXComponentFactory.createJButton(new ClearAction("Clear"));

		panel_1.add(btn_Console_Clear);

		btn_Console_Clear.setFocusPainted(false);

		btn_Console_Clear.setBorderPainted(false);

		btn_Console_Clear.setPreferredSize(new Dimension(22, 22));

		JButton btn_Console_Copy = VPXComponentFactory.createJButton(new CopyAction("Copy"));

		panel_1.add(btn_Console_Copy);

		btn_Console_Copy.setFocusPainted(false);

		btn_Console_Copy.setBorderPainted(false);

		btn_Console_Copy.setPreferredSize(new Dimension(22, 22));

		JButton btn_Console_Save = VPXComponentFactory.createJButton(new SaveAction("Save"));

		panel_1.add(btn_Console_Save);

		btn_Console_Save.setFocusPainted(false);

		btn_Console_Save.setBorderPainted(false);

		btn_Console_Save.setPreferredSize(new Dimension(22, 22));

		JScrollPane scrl_Console = VPXComponentFactory.createJScrollPane();

		add(scrl_Console, BorderLayout.CENTER);

		txtA_Console = VPXComponentFactory.createJTextArea();

		scrl_Console.setViewportView(txtA_Console);

		txtA_Console.setEditable(false);

	}

	private void loadFilters() {

		vpxSystem = VPXSessionManager.getVPXSystem();

		cmbSubSystem.removeAllItems();

		cmbSubSystem.addItem("All Susb Systems");

		List<VPXSubSystem> subsystem = vpxSystem.getSubsystem();

		for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

			VPXSubSystem vpxSubSystem = iterator.next();

			cmbSubSystem.addItem(vpxSubSystem.getSubSystem());
		}

		List<Processor> p = vpxSystem.getUnListed();

		if (p.size() > 0) {

			cmbSubSystem.addItem(VPXConstants.VPXUNLIST);
		}
	}

	private void loadProcessorsFilter() {

		cmbProcessor.removeAllItems();

		cmbProcessor.addItem("All Processors");

		if (VPXConstants.VPXUNLIST.equals(cmbSubSystem.getSelectedItem().toString())) {

			List<Processor> p = vpxSystem.getUnListed();

			if (p.size() > 0) {

				for (Iterator<Processor> iterator = p.iterator(); iterator.hasNext();) {

					Processor processor = iterator.next();

					cmbProcessor.addItem(processor.getiP_Addresses());
				}
			}

		} else {

			List<VPXSubSystem> subsystem = vpxSystem.getSubsystem();

			for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getSubSystem().equals(cmbSubSystem.getSelectedItem().toString())) {

					curProcFilter = vpxSubSystem;

					cmbProcessor.addItem(vpxSubSystem.getIpP2020());

					cmbProcessor.addItem(vpxSubSystem.getIpDSP1());

					cmbProcessor.addItem(vpxSubSystem.getIpDSP2());

					break;
				}

			}
		}

	}

	private void loadCoresFilter() {

		cmbCores.removeAllItems();

		if (VPXConstants.VPXUNLIST.equals(cmbSubSystem.getSelectedItem().toString())) {

			List<Processor> s = vpxSystem.getUnListed();

			for (Iterator<Processor> iterator = s.iterator(); iterator.hasNext();) {

				Processor processor = iterator.next();

				if (processor.getiP_Addresses().equals(cmbProcessor.getSelectedItem().toString())) {

					if (processor.getName().contains("P2020")) {

						cmbCores.setEnabled(false);

					} else {

						cmbCores.setEnabled(true);

						cmbCores.addItem("All Cores");

						for (int i = 0; i < 8; i++) {

							cmbCores.addItem(String.format("Core %s", i));
						}

						cmbCores.setSelectedIndex(0);
					}
				}
			}

		} else {

			if (curProcFilter != null) {

				if (cmbProcessor.getSelectedItem().toString().equals(curProcFilter.getIpP2020())) {

					cmbCores.setEnabled(false);

				} else {

					cmbCores.setEnabled(true);

					cmbCores.addItem("All Cores");

					for (int i = 0; i < 8; i++) {
						cmbCores.addItem(String.format("Core %s", i));
					}

					cmbCores.setSelectedIndex(0);
				}
			}

		}

	}

	public void printConsoleMsg(String msg) {

		if (txtA_Console.getCaretPosition() > 0)

			txtA_Console.append("\n");

		String[] strs = msg.split(":");

		txtA_Console.append(String.format("Core %s : %s", strs[0], strs[1]));

		txtA_Console.setCaretPosition(txtA_Console.getText().length());

	}

	public void printConsoleMsg(MSGCommand msg) {

		if (txtA_Console.getCaretPosition() > 0)

			txtA_Console.append("\n");

		txtA_Console.append(String.format("Core %d : %s", msg.core.get(), msg.command_msg));

		txtA_Console.setCaretPosition(txtA_Console.getText().length());

	}

	public void printConsoleMsg(String ip, String msg) {

		if (isFilterApply) {

			if (ips.size() == 0) {

				ips.clear();

				if (cmbSubSystem.getSelectedIndex() > 0) {

					VPXSubSystem sub = vpxSystem.getSubSystemByName(cmbSubSystem.getSelectedItem().toString());

					ips.add(sub.getIpP2020());

					ips.add(sub.getIpDSP1());

					ips.add(sub.getIpDSP2());
				}

				if (cmbProcessor.getSelectedIndex() > 0) {

					ips.clear();

					ips.add(cmbProcessor.getSelectedItem().toString());

				}

				coreindex = cmbCores.getSelectedIndex();
			}

			if (ips.contains(ip)) {

				if (coreindex == 0) {

					printConsoleMsg(msg);

				} else {

					if (msg.startsWith(String.valueOf((coreindex - 1)))) {

						printConsoleMsg(msg);
					}
				}

			}

		} else {

			printConsoleMsg(msg);

		}
	}

	public void printConsoleMsg(String ip, MSGCommand msg) {

		if (isFilterApply) {

			if (ips.size() == 0) {

				ips.clear();

				if (cmbSubSystem.getSelectedIndex() > 0) {

					VPXSubSystem sub = vpxSystem.getSubSystemByName(cmbSubSystem.getSelectedItem().toString());

					ips.add(sub.getIpP2020());

					ips.add(sub.getIpDSP1());

					ips.add(sub.getIpDSP2());
				}

				if (cmbProcessor.getSelectedIndex() > 0) {

					ips.clear();

					ips.add(cmbProcessor.getSelectedItem().toString());

				}

				coreindex = cmbCores.getSelectedIndex();
			}

			if (ips.contains(ip)) {

				if (coreindex == 0) {

					printConsoleMsg(msg);

				} else {

					if (msg.core.get() == (coreindex - 1)) {

						printConsoleMsg(msg);
					}
				}

			}

		} else {

			printConsoleMsg(msg);

		}
	}

	public void reloadSubsystems() {

		int sub = cmbSubSystem.getSelectedIndex();

		int proc = cmbProcessor.getSelectedIndex();

		int core = 0;

		if (cmbCores.getItemCount() > 0) {

			core = cmbCores.getSelectedIndex();
		}

		loadFilters();

		cmbSubSystem.setSelectedIndex(sub);

		cmbProcessor.setSelectedIndex(proc);

		if (cmbCores.getItemCount() > 0) {

			cmbCores.setSelectedIndex(core);
		}
	}

	private void clearContents() {

		txtA_Console.setText("");
	}

	private void setClipboardContents(String aString) {

		StringSelection stringSelection = new StringSelection(aString);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		clipboard.setContents(stringSelection, this);
	}

	private void saveConsoleMsgtoFile() {

		JFileChooser chooser;

		try {

			chooser = new JFileChooser();

			chooser.setCurrentDirectory(new java.io.File("."));

			chooser.setDialogTitle("Select folder to save");

			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {

				String path = chooser.getSelectedFile().getPath() + "\\"
						+ txtA_Console.getText().split("  ")[0].replace(':', '_').replace(' ', '_').replace('-', '_')
						+ ".log";

				fw = new FileWriter(new File(path), true);

				txtA_Console.write(fw);

				fw.close();

				VPXUtilities.showPopup("File Saved at " + path, path);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {

	}

	class SaveAction extends AbstractAction {

		/**
		 * 
		 */

		public SaveAction(String name) {
			putValue(Action.SHORT_DESCRIPTION, name);
			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_SAVE);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveConsoleMsgtoFile();
		}
	}

	class ClearAction extends AbstractAction {

		/**
		 * 
		 */

		public ClearAction(String name) {
			putValue(Action.SHORT_DESCRIPTION, name);
			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_CLEAR);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {
			clearContents();
		}
	}

	class CopyAction extends AbstractAction {

		/**
		 * 
		 */

		public CopyAction(String name) {
			putValue(Action.SHORT_DESCRIPTION, name);
			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_COPY);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			setClipboardContents(txtA_Console.getText());

			VPXUtilities.showPopup("Contents copied to clipboard");
		}
	}

}
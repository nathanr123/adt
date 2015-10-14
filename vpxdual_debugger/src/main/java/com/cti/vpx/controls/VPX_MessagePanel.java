package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.cti.vpx.command.MSGCommand;
import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_MessagePanel extends JPanel implements ClipboardOwner {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1739175553026398101L;

	private JTextField txt_Msg_Send;

	private VPX_ETHWindow parent;

	private JButton btn_Msg_Send;

	private JButton btn_Msg_Copy;

	private JButton btn_Msg_Save;

	private JButton btn_Msg_Clear;

	private JScrollPane scrl_Proc_Msg_Pane;

	private JTextPane txtP_Proc_Msg_Display;

	private JTextPane txtP_User_Msg_Display;

	private Style proc_Msg_Style, proc_From_Style, proc_To_Style, proc_Time_Style;

	private Style user_Msg_Style, user_From_Style, user_To_Style, user_Time_Style;

	private Style default_Style;

	private JPanel panel;

	private JPanel controlsPanel;

	private JPanel ProcMSGPanel;

	private JPanel UserMSGPanel;

	private JScrollPane scrl_User_Msg_Pane;

	private JComboBox<String> cmbCores;

	private final JPopupMenu vpxSendMsgContextMenu = new JPopupMenu();

	private JMenuItem vpxSendMsgContextMenu_Cut;

	private JMenuItem vpxSendMsgContextMenu_Copy;

	private JMenuItem vpxSendMsgContextMenu_Paste;

	private JMenuItem vpxSendMsgContextMenu_ShowCmdHistory;

	private JMenuItem vpxSendMsgContextMenu_ShowCmdList;

	private JMenuItem vpxSendMsgContextMenu_SetCmd;

	Map<Integer, String> cmdHistory = new LinkedHashMap<Integer, String>();

	VPX_ListCommands cmdLists = new VPX_ListCommands();

	private int currentCommandIndex = -1;

	private JComponent curComp;

	private JMenuItem vpxSendMsgContextMenu_SetCmdGo;

	/**
	 * Create the panel.
	 */
	public VPX_MessagePanel(VPX_ETHWindow parent) {

		setBorder(new TitledBorder(null, "Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		this.parent = parent;

		init();

		loadComponents();

		createContextMenus();

		loadCoresFilter();
	}

	private void init() {

		setLayout(new BorderLayout(5, 5));

		loadMessageStyles();
	}

	private void navigateCommand(KeyEvent keyEvent) {

		if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {

			if (currentCommandIndex > 0) {

				currentCommandIndex = currentCommandIndex - 1;

				txt_Msg_Send.setText(cmdHistory.get(currentCommandIndex));
			}

		} else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {

			if (currentCommandIndex < cmdHistory.size()) {

				currentCommandIndex = currentCommandIndex + 1;

				txt_Msg_Send.setText(cmdHistory.get(currentCommandIndex));
			}

		}

	}

	private void createContextMenus() {

		vpxSendMsgContextMenu_Cut = VPXComponentFactory.createJMenuItem("Cut");

		vpxSendMsgContextMenu_Cut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				doCut();
			}
		});

		vpxSendMsgContextMenu_Copy = VPXComponentFactory.createJMenuItem("Copy");

		vpxSendMsgContextMenu_Copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doCopy();
			}
		});

		vpxSendMsgContextMenu_Paste = VPXComponentFactory.createJMenuItem("Paste");

		vpxSendMsgContextMenu_Paste.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doPaste();
			}
		});

		vpxSendMsgContextMenu_SetCmd = VPXComponentFactory.createJMenuItem("Copy to Command box");

		vpxSendMsgContextMenu_SetCmd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doSetCommand();
			}
		});

		vpxSendMsgContextMenu_SetCmdGo = VPXComponentFactory.createJMenuItem("Copy to Command box & send");

		vpxSendMsgContextMenu_SetCmdGo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				doSetCommand();

				VPX_MessagePanel.this.btn_Msg_Send.doClick();

				cmdLists.updateCommands(cmdHistory);
			}
		});

		vpxSendMsgContextMenu_ShowCmdHistory = VPXComponentFactory.createJMenuItem("Show Command History");

		vpxSendMsgContextMenu_ShowCmdHistory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				showCommands();

			}
		});

		vpxSendMsgContextMenu_ShowCmdList = VPXComponentFactory.createJMenuItem("Show Commands");

		vpxSendMsgContextMenu_ShowCmdList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				showHelp();
			}
		});

		vpxSendMsgContextMenu.add(vpxSendMsgContextMenu_Cut);

		vpxSendMsgContextMenu.add(vpxSendMsgContextMenu_Copy);

		vpxSendMsgContextMenu.add(VPXComponentFactory.createJSeparator());

		vpxSendMsgContextMenu.add(vpxSendMsgContextMenu_Paste);

		vpxSendMsgContextMenu.add(VPXComponentFactory.createJSeparator());

		vpxSendMsgContextMenu.add(vpxSendMsgContextMenu_SetCmd);

		vpxSendMsgContextMenu.add(vpxSendMsgContextMenu_SetCmdGo);

		vpxSendMsgContextMenu.add(VPXComponentFactory.createJSeparator());

		vpxSendMsgContextMenu.add(vpxSendMsgContextMenu_ShowCmdList);

		vpxSendMsgContextMenu.add(vpxSendMsgContextMenu_ShowCmdHistory);
	}

	private void showPopupMenu(boolean isList, JComponent comp, int x, int y) {

		if (isList) {

			if (cmdHistory.size() > 0) {

				vpxSendMsgContextMenu_Cut.setEnabled(false);

				vpxSendMsgContextMenu_Copy.setEnabled(true);

				vpxSendMsgContextMenu_Paste.setEnabled(false);

				vpxSendMsgContextMenu_SetCmd.setEnabled(true);

				vpxSendMsgContextMenu_SetCmdGo.setEnabled(true);

				vpxSendMsgContextMenu_ShowCmdList.setEnabled(false);

				vpxSendMsgContextMenu_ShowCmdHistory.setEnabled(false);
			} else {
				vpxSendMsgContextMenu_Cut.setEnabled(false);

				vpxSendMsgContextMenu_Copy.setEnabled(false);

				vpxSendMsgContextMenu_Paste.setEnabled(false);

				vpxSendMsgContextMenu_SetCmd.setEnabled(false);

				vpxSendMsgContextMenu_SetCmdGo.setEnabled(false);

				vpxSendMsgContextMenu_ShowCmdList.setEnabled(false);

				vpxSendMsgContextMenu_ShowCmdHistory.setEnabled(false);
			}

		} else {

			vpxSendMsgContextMenu_Cut.setEnabled(true);

			vpxSendMsgContextMenu_Copy.setEnabled(true);

			vpxSendMsgContextMenu_Paste.setEnabled(true);

			vpxSendMsgContextMenu_SetCmd.setEnabled(false);

			vpxSendMsgContextMenu_SetCmdGo.setEnabled(false);

			vpxSendMsgContextMenu_ShowCmdList.setEnabled(true);

			vpxSendMsgContextMenu_ShowCmdHistory.setEnabled(true);

		}

		curComp = comp;

		vpxSendMsgContextMenu.show(comp, x, y);

	}

	private void loadComponents() {

		JPanel base_Panel = VPXComponentFactory.createJPanel();

		base_Panel.setPreferredSize(new Dimension(10, 55));

		add(base_Panel, BorderLayout.SOUTH);

		base_Panel.setLayout(new BorderLayout(0, 0));

		JPanel btn_Panel = VPXComponentFactory.createJPanel();

		base_Panel.add(btn_Panel, BorderLayout.SOUTH);

		btn_Panel.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();

		btn_Panel.add(panel);

		panel.setLayout(new BorderLayout(0, 0));

		btn_Msg_Send = VPXComponentFactory.createJButton(new SendMsgAction("Send Message"));

		panel.add(btn_Msg_Send);

		txt_Msg_Send = VPXComponentFactory.createJTextField();

		txt_Msg_Send.setFont(VPXConstants.BISTRESULTFONT);

		txt_Msg_Send.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 3) {
					showPopupMenu(false, txt_Msg_Send, e.getX(), e.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		txt_Msg_Send.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				if (arg0.getKeyChar() == KeyEvent.VK_ENTER) {

					btn_Msg_Send.doClick();

				}
				if (arg0.getKeyCode() == KeyEvent.VK_UP) {

					navigateCommand(arg0);

				} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {

					navigateCommand(arg0);
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}
		});

		base_Panel.add(txt_Msg_Send, BorderLayout.CENTER);

		txt_Msg_Send.setColumns(10);

		JPanel message_Panel = VPXComponentFactory.createJPanel();

		add(message_Panel, BorderLayout.CENTER);

		message_Panel.setLayout(new BorderLayout());

		controlsPanel = new JPanel();

		FlowLayout fl_controlsPanel = (FlowLayout) controlsPanel.getLayout();

		fl_controlsPanel.setAlignOnBaseline(true);

		fl_controlsPanel.setVgap(0);

		fl_controlsPanel.setHgap(3);

		fl_controlsPanel.setAlignment(FlowLayout.RIGHT);

		message_Panel.add(controlsPanel, BorderLayout.NORTH);

		cmbCores = new JComboBox<String>();

		cmbCores.setPreferredSize(new Dimension(140, 22));

		controlsPanel.add(cmbCores);

		btn_Msg_Clear = VPXComponentFactory.createJButton(new ClearAction("Clear"));

		btn_Msg_Clear.setPreferredSize(new Dimension(22, 22));

		btn_Msg_Clear.setBorderPainted(false);

		controlsPanel.add(btn_Msg_Clear);

		btn_Msg_Copy = VPXComponentFactory.createJButton(new CopyAction("Copy"));

		btn_Msg_Copy.setPreferredSize(new Dimension(22, 22));

		btn_Msg_Copy.setBorderPainted(false);

		controlsPanel.add(btn_Msg_Copy);

		btn_Msg_Save = VPXComponentFactory.createJButton(new SaveAction("Save"));

		btn_Msg_Save.setPreferredSize(new Dimension(22, 22));

		btn_Msg_Save.setBorderPainted(false);

		controlsPanel.add(btn_Msg_Save);

		ProcMSGPanel = new JPanel();

		ProcMSGPanel.setBorder(
				new TitledBorder(null, "Processor Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		ProcMSGPanel.setPreferredSize(new Dimension(10, 200));

		message_Panel.add(ProcMSGPanel, BorderLayout.CENTER);

		ProcMSGPanel.setLayout(new BorderLayout(0, 0));

		scrl_Proc_Msg_Pane = VPXComponentFactory.createJScrollPane();

		ProcMSGPanel.add(scrl_Proc_Msg_Pane);

		txtP_Proc_Msg_Display = VPXComponentFactory.createJTextPane(VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT);

		txtP_Proc_Msg_Display.setEditable(false);

		scrl_Proc_Msg_Pane.setViewportView(txtP_Proc_Msg_Display);

		UserMSGPanel = new JPanel();

		UserMSGPanel
				.setBorder(new TitledBorder(null, "User Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		UserMSGPanel.setPreferredSize(new Dimension(10, 250));

		message_Panel.add(UserMSGPanel, BorderLayout.SOUTH);

		UserMSGPanel.setLayout(new BorderLayout(0, 0));

		scrl_User_Msg_Pane = VPXComponentFactory.createJScrollPane();

		UserMSGPanel.add(scrl_User_Msg_Pane, BorderLayout.CENTER);

		txtP_User_Msg_Display = VPXComponentFactory.createJTextPane(VPXConstants.USER_MESSAGE_DISPLAY_DOCUMENT);

		txtP_User_Msg_Display.setEditable(false);

		scrl_User_Msg_Pane.setViewportView(txtP_User_Msg_Display);
	}

	private void loadMessageStyles() {

		default_Style = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_CONTEXT.getStyle(StyleContext.DEFAULT_STYLE);

		proc_Msg_Style = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_CONTEXT.addStyle("Processor_Message", default_Style);

		StyleConstants.setFontSize(proc_Msg_Style, StyleConstants.getFontSize(default_Style) + 2);

		proc_From_Style = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_CONTEXT.addStyle("Processor_Message_From",
				default_Style);

		StyleConstants.setBold(proc_From_Style, true);

		proc_To_Style = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_CONTEXT.addStyle("Processor_Message_To", default_Style);

		StyleConstants.setItalic(proc_To_Style, true);

		proc_Time_Style = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_CONTEXT.addStyle("Processor_Message_Time",
				default_Style);

		StyleConstants.setUnderline(proc_Time_Style, true);

		user_Msg_Style = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_CONTEXT.addStyle("User_Message", default_Style);

		StyleConstants.setFontSize(user_Msg_Style, StyleConstants.getFontSize(default_Style) + 2);

		StyleConstants.setAlignment(user_Msg_Style, StyleConstants.ALIGN_RIGHT);

		user_From_Style = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_CONTEXT.addStyle("User_Message_From", default_Style);

		StyleConstants.setItalic(user_From_Style, true);

		StyleConstants.setAlignment(user_From_Style, StyleConstants.ALIGN_RIGHT);

		user_To_Style = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_CONTEXT.addStyle("User_Message_To", default_Style);

		StyleConstants.setBold(user_To_Style, true);

		StyleConstants.setAlignment(user_To_Style, StyleConstants.ALIGN_RIGHT);

		user_Time_Style = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_CONTEXT.addStyle("User_Message_Time", default_Style);

		StyleConstants.setUnderline(user_Time_Style, true);

		StyleConstants.setAlignment(user_Time_Style, StyleConstants.ALIGN_RIGHT);

	}

	public void updateProcessorSettings() {

		if (VPXSessionManager.getCurrentProcType().contains("P2020")) {

			cmbCores.setEnabled(false);

		} else {

			cmbCores.setEnabled(true);
		}

	}

	public void updateProcessorMessage(String msg) {

		try {

			int eo = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT.getLength();

			VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT.insertString(eo, msg, null);

			VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT.setCharacterAttributes(eo, eo + msg.length(),
					proc_Msg_Style, false);

			VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT.setLogicalStyle(eo, proc_Msg_Style);

		} catch (BadLocationException e) {

			e.printStackTrace();
		}
	}

	public void updateProcessorMessage(String ip, String msg) {

		if (ip.equals(VPXSessionManager.getCurrentProcessor())) {

			if (cmbCores.getSelectedIndex() == 0) {

				updateProcessorMessage(VPXSessionManager.getCurrentSubSystem() + " "
						+ VPXSessionManager.getCurrentProcType() + " : \n", proc_From_Style);

				updateProcessorMessage("  " + msg.substring(2, msg.length()) + "\n\n", proc_Msg_Style);

			} else if (msg.startsWith((cmbCores.getSelectedIndex() - 1) + ":")) {

				updateProcessorMessage(VPXSessionManager.getCurrentSubSystem() + " "
						+ VPXSessionManager.getCurrentProcType() + " : \n", proc_From_Style);

				updateProcessorMessage("  " + msg.substring(2, msg.length()) + "\n\n", proc_Msg_Style);
			}
		}
	}

	public void updateProcessorMessage(String ip, MSGCommand msg) {

		String procs = "";

		if (ip.equals(VPXSessionManager.getCurrentProcessor())) {

			procs = VPXSessionManager.getCurrentSubSystem() + " " + VPXSessionManager.getCurrentProcType();

			if (!VPXSessionManager.getCurrentProcType().contains("P2020")) {

				procs = procs + " Core " + msg.core.get();
			}

			updateProcessorMessage(procs + " : \n", proc_From_Style);

			updateProcessorMessage("  " + msg.command_msg + "\n\n", proc_Msg_Style);

		}
	}

	public void updateProcessorMessage(String msg, Style style) {

		try {

			int eo = VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT.getLength();

			VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT.insertString(eo, msg, null);

			VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT.setCharacterAttributes(eo, eo + msg.length(), style, false);

			VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT.setLogicalStyle(eo, style);

			txtP_Proc_Msg_Display.setCaretPosition(VPXConstants.PROCESSOR_MESSAGE_DISPLAY_DOCUMENT.getLength());

		} catch (BadLocationException e) {

			e.printStackTrace();
		}
	}

	private void updateUserMessage(String msg) {

		updateUserMessage(
				VPXSessionManager.getCurrentSubSystem() + " " + VPXSessionManager.getCurrentProcType() + " : \n",
				proc_From_Style);

		updateUserMessage("  " + msg + "\n\n", proc_Msg_Style);
	}

	private void updateUserMessage(String msg, Style style) {
		try {

			int eo = VPXConstants.USER_MESSAGE_DISPLAY_DOCUMENT.getLength();

			VPXConstants.USER_MESSAGE_DISPLAY_DOCUMENT.insertString(eo, msg, null);

			VPXConstants.USER_MESSAGE_DISPLAY_DOCUMENT.setCharacterAttributes(eo, eo + msg.length(), style, false);

			VPXConstants.USER_MESSAGE_DISPLAY_DOCUMENT.setLogicalStyle(eo, style);

		} catch (BadLocationException e) {

			e.printStackTrace();
		}
	}

	private void doCut() {

		if (curComp instanceof JTextField) {

			JTextField jf = (JTextField) curComp;

			jf.cut();
		}
	}

	private void doCopy() {

		JTextComponent jf = null;

		if (curComp instanceof JTextField) {

			jf = (JTextField) curComp;

		} else if (curComp instanceof JTextArea) {

			jf = (JTextArea) curComp;
		}

		jf.copy();
	}

	private void doPaste() {

		if (curComp instanceof JTextField) {

			JTextField jf = (JTextField) curComp;

			jf.paste();

		}
	}

	private void doSetCommand() {

		if (curComp instanceof JTextArea) {

			JTextArea jf = (JTextArea) curComp;

			txt_Msg_Send.setText(jf.getSelectedText());

		}

	}

	private void clearContents() {

		txtP_Proc_Msg_Display.setText("");

		txtP_User_Msg_Display.setText("");
	}

	private void loadCoresFilter() {

		cmbCores.addItem("Select Core");

		for (int i = 0; i < 8; i++) {
			cmbCores.addItem(String.format("Core %s", i));
		}

		cmbCores.setSelectedIndex(0);
	}

	private void setClipboardContents(String aString) {

		StringSelection stringSelection = new StringSelection(aString);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		clipboard.setContents(stringSelection, this);
	}

	private void saveLogtoFile() {

		JFileChooser chooser;

		try {

			chooser = new JFileChooser();

			chooser.setCurrentDirectory(new java.io.File("."));

			chooser.setDialogTitle("Select folder to save");

			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {

				String path = chooser.getSelectedFile().getPath() + "\\Messages."
						+ getCurrentTime().split("  ")[0].replace(':', '_').replace(' ', '_').replace('-', '_')
						+ ".txt";

				FileWriter fw = new FileWriter(new File(path), true);

				fw.write("User Messages\n");

				fw.write("_______________________________\n");

				fw.write("\n");

				fw.write(txtP_User_Msg_Display.getText());

				fw.write("\n");

				fw.write("Processor Messages\n");

				fw.write("_______________________________\n");

				fw.write("\n");

				fw.write(txtP_Proc_Msg_Display.getText());

				fw.write("\n");

				fw.close();

				VPXUtilities.showPopup("File Saved at " + path, path);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void showHelp() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				parent.updateLog("Help document opened");

				final VPX_VLANConfig browserCanvas = new VPX_VLANConfig();

				JPanel contentPane = new JPanel();

				contentPane.setLayout(new BorderLayout());

				contentPane.add(browserCanvas, BorderLayout.CENTER);

				JDialog frame = new JDialog(parent, "List of commands");

				frame.setBounds(100, 100, 650, 600);

				frame.setLocationRelativeTo(parent);

				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				frame.setContentPane(contentPane);

				frame.setResizable(true);

				frame.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) { // Dispose of the
						// native
						// component cleanly
						browserCanvas.dispose();
					}
				});

				frame.setVisible(true);

				if (browserCanvas.initialise()) {

					browserCanvas.setUrl(System.getProperty("user.dir") + "\\help\\help.html");
				} else {
				}

				frame.setSize(651, 601);

			}
		});

		th.start();

	}

	public void showCommands() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				parent.updateLog("Command histroy opened");

				cmdLists.showCommands(cmdHistory);
			}
		});

		th.start();
	}

	private String getCurrentTime() {

		return VPXConstants.DATEFORMAT_FULL.format(Calendar.getInstance().getTime());
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {

	}

	class SendMsgAction extends AbstractAction {

		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		public SendMsgAction(String name) {

			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String str = txt_Msg_Send.getText();

			cmdHistory.put(cmdHistory.size(), str);

			currentCommandIndex = cmdHistory.size();

			if (str.equalsIgnoreCase("ls") || str.equalsIgnoreCase("help") || str.equalsIgnoreCase("?")) {

				if (str.equalsIgnoreCase("ls")) {

					showCommands();

				} else if (str.equalsIgnoreCase("help") || str.equalsIgnoreCase("?")) {

					showHelp();
				}

				txt_Msg_Send.setText("");

			} else if (VPXSessionManager.getCurrentProcessor().length() > 0) {

				if (str.length() > 0 && str.length() <= 48) {

					String[] s = str.split(" ");

					if (s.length <= 5) {

						if (VPXSessionManager.getCurrentProcType().contains("P2020")) {

							parent.sendMessage(VPXSessionManager.getCurrentProcessor(), cmbCores.getSelectedIndex(),
									txt_Msg_Send.getText());

							updateUserMessage(txt_Msg_Send.getText());

							txt_Msg_Send.setText("");

						} else {

							if (cmbCores.getSelectedIndex() > 0) {

								parent.sendMessage(VPXSessionManager.getCurrentProcessor(),
										cmbCores.getSelectedIndex() - 1, txt_Msg_Send.getText());

								updateUserMessage(txt_Msg_Send.getText());

								txt_Msg_Send.setText("");
							} else {
								JOptionPane.showMessageDialog(parent, "Please select any one core", "Message",
										JOptionPane.ERROR_MESSAGE);

								txt_Msg_Send.requestFocus();
							}
						}

					} else {
						JOptionPane.showMessageDialog(parent, "invalid Arguments", "Message",
								JOptionPane.ERROR_MESSAGE);

						txt_Msg_Send.requestFocus();
					}

				} else {
					JOptionPane.showMessageDialog(parent, "Message length is not valid", "Message",
							JOptionPane.ERROR_MESSAGE);

					txt_Msg_Send.requestFocus();
				}
			} else {

				JOptionPane.showMessageDialog(parent, "No processor is selected.Please select any one processor",
						"Processor Selection", JOptionPane.ERROR_MESSAGE);
				txt_Msg_Send.requestFocus();
			}

		}
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

			saveLogtoFile();
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

			String str = txtP_Proc_Msg_Display.getText() + txtP_User_Msg_Display.getText();

			setClipboardContents(str);

			VPXUtilities.showPopup("Contents copied to clipboard");
		}
	}

	public class VPX_ListCommands extends JDialog {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5100584982953579499L;

		private final JPanel contentPanel = new JPanel();

		Map<Integer, String> cmds;

		private JTextArea txtALstCmd;

		/**
		 * Create the dialog.
		 */

		public VPX_ListCommands() {

			init();

			loadComponents();
		}

		private void init() {

			setTitle("Command History");

			setIconImage(VPXUtilities.getAppIcon());

			setBounds(100, 100, 450, 300);

			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			getContentPane().setLayout(new BorderLayout());

		}

		private void loadComponents() {

			contentPanel.setBorder(
					new TitledBorder(null, "Command History", TitledBorder.LEADING, TitledBorder.TOP, null, null));

			getContentPane().add(contentPanel, BorderLayout.CENTER);

			contentPanel.setLayout(new BorderLayout(0, 0));

			JScrollPane lstCmdScrollPane = new JScrollPane();

			contentPanel.add(lstCmdScrollPane, BorderLayout.CENTER);

			txtALstCmd = new JTextArea();

			txtALstCmd.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == 3) {
						showPopupMenu(true, txtALstCmd, e.getX(), e.getY());
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});

			txtALstCmd.setEditable(false);

			lstCmdScrollPane.setViewportView(txtALstCmd);

			JPanel buttonPane = new JPanel();

			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton btnClose = new JButton("Close");

			btnClose.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					VPX_ListCommands.this.dispose();
				}
			});

			btnClose.setActionCommand("Cancel");

			buttonPane.add(btnClose);

		}

		private void loadCommands() {

			txtALstCmd.setText("");

			if (cmds.size() > 0) {

				Set<Entry<Integer, String>> set = cmds.entrySet();

				Iterator<Entry<Integer, String>> iteratorByteArray = set.iterator();

				while (iteratorByteArray.hasNext()) {
					Entry<Integer, String> entry = (Entry<Integer, String>) iteratorByteArray.next();

					txtALstCmd.append(entry.getValue() + "\n");
				}
			} else {
				txtALstCmd.setText("No command history found.");
			}
		}

		public void showCommands(Map<Integer, String> cmd) {

			this.cmds = cmd;

			loadCommands();

			setVisible(true);
		}

		public void updateCommands(Map<Integer, String> cmd) {

			this.cmds = cmd;

			loadCommands();

		}

	}
}

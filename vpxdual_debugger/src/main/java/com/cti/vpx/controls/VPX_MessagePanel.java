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
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.cti.vpx.util.ComponentFactory;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import javax.swing.JComboBox;

public class VPX_MessagePanel extends JPanel implements ClipboardOwner {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1739175553026398101L;

	private JTextField txt_Msg_Send;

	private VPX_ETHWindow parent;

	private JButton btn_Msg_Send;

	private JButton btn_Msg_Reset;

	private JButton btn_Msg_Copy;

	private JButton btn_Msg_Save;

	private JButton btn_Msg_Clear;

	private JScrollPane scrl_Proc_Msg_Pane;

	private JTextPane txtP_Proc_Msg_Display;

	private JTextPane txtP_User_Msg_Display;

	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private final StyleContext txtP_Proc_Msg_Display_Context = new StyleContext();

	private final StyledDocument txtP_Proc_Msg_Display_Document = new DefaultStyledDocument(
			txtP_Proc_Msg_Display_Context);

	private final StyleContext txtP_User_Msg_Display_Context = new StyleContext();

	private final StyledDocument txtP_User_Msg_Display_Document = new DefaultStyledDocument(
			txtP_User_Msg_Display_Context);

	private Style proc_Msg_Style, proc_From_Style, proc_To_Style, proc_Time_Style;

	private Style user_Msg_Style, user_From_Style, user_To_Style, user_Time_Style;

	private Style default_Style;

	private JPanel panel;

	private JPanel controlsPanel;

	private JPanel ProcMSGPanel;

	private JPanel UserMSGPanel;

	private JScrollPane scrl_User_Msg_Pane;
	private JComboBox<String> cmbCores;

	/**
	 * Create the panel.
	 */
	public VPX_MessagePanel(VPX_ETHWindow parent) {
		setBorder(new TitledBorder(null, "Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		this.parent = parent;

		init();

		loadComponents();

		loadCoresFilter();
	}

	private void init() {
		setLayout(new BorderLayout(5, 5));

		loadMessageStyles();
	}

	private void loadComponents() {

		JPanel base_Panel = ComponentFactory.createJPanel();
		base_Panel.setPreferredSize(new Dimension(10, 55));

		add(base_Panel, BorderLayout.SOUTH);

		base_Panel.setLayout(new BorderLayout(0, 0));

		JPanel btn_Panel = ComponentFactory.createJPanel();

		base_Panel.add(btn_Panel, BorderLayout.SOUTH);
		btn_Panel.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		btn_Panel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		btn_Msg_Send = ComponentFactory.createJButton(new SendMsgAction("Send Message"));
		panel.add(btn_Msg_Send);

		txt_Msg_Send = ComponentFactory.createJTextField();

		base_Panel.add(txt_Msg_Send, BorderLayout.CENTER);

		txt_Msg_Send.setColumns(10);

		JPanel message_Panel = ComponentFactory.createJPanel();

		add(message_Panel, BorderLayout.CENTER);

		message_Panel.setLayout(new BorderLayout());

		controlsPanel = new JPanel();
		FlowLayout fl_controlsPanel = (FlowLayout) controlsPanel.getLayout();
		fl_controlsPanel.setAlignOnBaseline(true);
		fl_controlsPanel.setVgap(0);
		fl_controlsPanel.setHgap(0);
		fl_controlsPanel.setAlignment(FlowLayout.RIGHT);
		message_Panel.add(controlsPanel, BorderLayout.NORTH);

		cmbCores = new JComboBox();
		cmbCores.setPreferredSize(new Dimension(140, 22));
		controlsPanel.add(cmbCores);

		btn_Msg_Reset = ComponentFactory.createJButton(new ResetAction("Reset"));
		btn_Msg_Reset.setPreferredSize(new Dimension(20, 16));
		btn_Msg_Reset.setBorderPainted(false);
		controlsPanel.add(btn_Msg_Reset);

		btn_Msg_Copy = ComponentFactory.createJButton(new CopyAction("Copy"));
		btn_Msg_Copy.setPreferredSize(new Dimension(20, 16));
		btn_Msg_Copy.setBorderPainted(false);
		controlsPanel.add(btn_Msg_Copy);

		btn_Msg_Clear = ComponentFactory.createJButton(new ClearAction("Clear"));
		btn_Msg_Clear.setPreferredSize(new Dimension(20, 16));
		btn_Msg_Clear.setBorderPainted(false);
		controlsPanel.add(btn_Msg_Clear);

		btn_Msg_Save = ComponentFactory.createJButton(new SaveAction("Save"));
		btn_Msg_Save.setPreferredSize(new Dimension(20, 16));
		btn_Msg_Save.setBorderPainted(false);
		controlsPanel.add(btn_Msg_Save);

		ProcMSGPanel = new JPanel();
		ProcMSGPanel.setBorder(new TitledBorder(null, "Processor Messages", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		ProcMSGPanel.setPreferredSize(new Dimension(10, 200));

		message_Panel.add(ProcMSGPanel, BorderLayout.CENTER);

		ProcMSGPanel.setLayout(new BorderLayout(0, 0));

		scrl_Proc_Msg_Pane = ComponentFactory.createJScrollPane();

		ProcMSGPanel.add(scrl_Proc_Msg_Pane);

		txtP_Proc_Msg_Display = ComponentFactory.createJTextPane(txtP_Proc_Msg_Display_Document);

		// txtP_Proc_Msg_Display.setPreferredSize(new Dimension(200, 35));

		txtP_Proc_Msg_Display.setEditable(false);

		scrl_Proc_Msg_Pane.setViewportView(txtP_Proc_Msg_Display);

		UserMSGPanel = new JPanel();

		UserMSGPanel.setBorder(new TitledBorder(null, "User Messages", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));

		UserMSGPanel.setPreferredSize(new Dimension(10, 250));

		message_Panel.add(UserMSGPanel, BorderLayout.SOUTH);

		UserMSGPanel.setLayout(new BorderLayout(0, 0));

		scrl_User_Msg_Pane = ComponentFactory.createJScrollPane();

		UserMSGPanel.add(scrl_User_Msg_Pane, BorderLayout.CENTER);

		txtP_User_Msg_Display = ComponentFactory.createJTextPane(txtP_User_Msg_Display_Document);

		// txtP_Proc_Msg_Display.setPreferredSize(new Dimension(200, 35));

		txtP_User_Msg_Display.setEditable(false);

		scrl_User_Msg_Pane.setViewportView(txtP_User_Msg_Display);
	}

	private void loadMessageStyles() {

		default_Style = txtP_Proc_Msg_Display_Context.getStyle(StyleContext.DEFAULT_STYLE);

		proc_Msg_Style = txtP_Proc_Msg_Display_Context.addStyle("Processor_Message", default_Style);

		StyleConstants.setFontSize(proc_Msg_Style, StyleConstants.getFontSize(default_Style) + 2);

		proc_From_Style = txtP_Proc_Msg_Display_Context.addStyle("Processor_Message_From", default_Style);

		StyleConstants.setBold(proc_From_Style, true);

		proc_To_Style = txtP_Proc_Msg_Display_Context.addStyle("Processor_Message_To", default_Style);

		StyleConstants.setItalic(proc_To_Style, true);

		proc_Time_Style = txtP_Proc_Msg_Display_Context.addStyle("Processor_Message_Time", default_Style);

		StyleConstants.setUnderline(proc_Time_Style, true);

		user_Msg_Style = txtP_Proc_Msg_Display_Context.addStyle("User_Message", default_Style);

		StyleConstants.setFontSize(user_Msg_Style, StyleConstants.getFontSize(default_Style) + 2);

		StyleConstants.setAlignment(user_Msg_Style, StyleConstants.ALIGN_RIGHT);

		user_From_Style = txtP_Proc_Msg_Display_Context.addStyle("User_Message_From", default_Style);

		StyleConstants.setItalic(user_From_Style, true);

		StyleConstants.setAlignment(user_From_Style, StyleConstants.ALIGN_RIGHT);

		user_To_Style = txtP_Proc_Msg_Display_Context.addStyle("User_Message_To", default_Style);

		StyleConstants.setBold(user_To_Style, true);

		StyleConstants.setAlignment(user_To_Style, StyleConstants.ALIGN_RIGHT);

		user_Time_Style = txtP_Proc_Msg_Display_Context.addStyle("User_Message_Time", default_Style);

		StyleConstants.setUnderline(user_Time_Style, true);

		StyleConstants.setAlignment(user_Time_Style, StyleConstants.ALIGN_RIGHT);

	}

	public void updateProcessorMessage(String msg) {

		try {
			int eo = txtP_Proc_Msg_Display_Document.getLength();

			txtP_Proc_Msg_Display_Document.insertString(eo, msg, null);

			txtP_Proc_Msg_Display_Document.setCharacterAttributes(eo, eo + msg.length(), proc_Msg_Style, false);

			txtP_Proc_Msg_Display_Document.setLogicalStyle(eo, proc_Msg_Style);

		} catch (BadLocationException e) {

			e.printStackTrace();
		}
	}

	public void updateProcessorMessage(String ip, String msg) {

		if (ip.equals(VPXUtilities.getCurrentProcessor())) {
			if (cmbCores.getSelectedIndex() == 0) {
				updateProcessorMessage(VPXUtilities.getCurrentSubSystem() + " " + VPXUtilities.getCurrentProcType()
						+ " : \n", proc_From_Style);

				updateProcessorMessage("  " + msg.substring(2, msg.length()) + "\n\n", proc_Msg_Style);
			} else if (msg.startsWith((cmbCores.getSelectedIndex() - 1) + ":")) {
				updateProcessorMessage(VPXUtilities.getCurrentSubSystem() + " " + VPXUtilities.getCurrentProcType()
						+ " : \n", proc_From_Style);

				updateProcessorMessage("  " + msg.substring(2, msg.length()) + "\n\n", proc_Msg_Style);
			}
		}
	}

	public void updateProcessorMessage(String msg, Style style) {

		try {
			int eo = txtP_Proc_Msg_Display_Document.getLength();

			txtP_Proc_Msg_Display_Document.insertString(eo, msg, null);

			txtP_Proc_Msg_Display_Document.setCharacterAttributes(eo, eo + msg.length(), style, false);

			txtP_Proc_Msg_Display_Document.setLogicalStyle(eo, style);

			txtP_Proc_Msg_Display.setCaretPosition(txtP_Proc_Msg_Display_Document.getLength());

		} catch (BadLocationException e) {

			e.printStackTrace();
		}
	}

	private void updateUserMessage(String msg) {

		updateUserMessage(VPXUtilities.getCurrentSubSystem() + " " + VPXUtilities.getCurrentProcType() + " : \n",
				proc_From_Style);

		updateUserMessage("  " + msg + "\n\n", proc_Msg_Style);
	}

	private void updateUserMessage(String msg, Style style) {
		try {
			int eo = txtP_User_Msg_Display_Document.getLength();

			txtP_User_Msg_Display_Document.insertString(eo, msg, null);

			txtP_User_Msg_Display_Document.setCharacterAttributes(eo, eo + msg.length(), style, false);

			txtP_User_Msg_Display_Document.setLogicalStyle(eo, style);

		} catch (BadLocationException e) {

			e.printStackTrace();
		}
	}

	private void clearContents() {

		txtP_Proc_Msg_Display.setText("");
	}

	private void loadCoresFilter() {

		cmbCores.addItem("All Cores");

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

		try {
			String path = System.getProperty("user.home") + "\\Messages."
					+ getCurrentTime().split("  ")[0].replace(':', '_').replace(' ', '_').replace('-', '_') + ".rtf";

			FileWriter fw = new FileWriter(new File(path), true);

			txtP_Proc_Msg_Display.write(fw);

			fw.close();

			VPXUtilities.showPopup("File Saved at " + path);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private String getCurrentTime() {

		return df.format(Calendar.getInstance().getTime());
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {

	}

	class ResetAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ResetAction(String name) {
			putValue(Action.SHORT_DESCRIPTION, name);
			putValue(Action.SMALL_ICON, VPXUtilities.getImageIcon(("image\\undo.gif"), 14, 14));
		}

		@Override
		public void actionPerformed(ActionEvent e) {

		}

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

			updateUserMessage(txt_Msg_Send.getText());

		}

	}

	class SaveAction extends AbstractAction {

		/**
		 * 
		 */

		public SaveAction(String name) {
			putValue(Action.SHORT_DESCRIPTION, name);
			putValue(Action.SMALL_ICON, VPXUtilities.getImageIcon(("image\\save.gif"), 14, 14));
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
			putValue(Action.SMALL_ICON, VPXUtilities.getImageIcon(("image\\delete.gif"), 14, 14));
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
			putValue(Action.SMALL_ICON, VPXUtilities.getImageIcon(("image\\copy.gif"), 14, 14));
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			setClipboardContents(txtP_Proc_Msg_Display.getText());

			VPXUtilities.showPopup("Contents copied to clipboard");
		}
	}
}

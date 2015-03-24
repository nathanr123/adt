package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
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
import com.cti.vpx.view.VPX_Dual_ADT_RootWindow;

public class VPX_MessagePanel extends JPanel implements ClipboardOwner {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1739175553026398101L;

	private JTextField txt_Msg_Send;

	private VPX_Dual_ADT_RootWindow parent;

	private JButton btn_Msg_Send;

	private JButton btn_Msg_Reset;

	private JButton btn_Msg_Copy;

	private JButton btn_Msg_Save;

	private JButton btn_Msg_Clear;

	private JScrollPane scrl_Msg_Pane;

	private JTextPane txtP_Msg_Display;

	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private final StyleContext txtP_Msg_Display_Context = new StyleContext();

	private final StyledDocument txtP_Msg_Display_Document = new DefaultStyledDocument(txtP_Msg_Display_Context);

	private Style proc_Msg_Style, proc_From_Style, proc_To_Style, proc_Time_Style;

	private Style user_Msg_Style, user_From_Style, user_To_Style, user_Time_Style;

	private Style default_Style;

	private JComboBox<String> comboBox;

	private JPanel processorList_Panel;

	private JList<String> list;

	/**
	 * Create the panel.
	 */
	public VPX_MessagePanel(VPX_Dual_ADT_RootWindow parent) {

		this.parent = parent;

		init();

		loadComponents();
	}

	private void init() {
		setLayout(new BorderLayout(5, 5));

		loadMessageStyles();
	}

	private void loadComponents() {

		JPanel base_Panel = ComponentFactory.createJPanel();

		add(base_Panel, BorderLayout.SOUTH);

		base_Panel.setLayout(new BorderLayout(0, 0));

		JPanel btn_Panel = ComponentFactory.createJPanel();

		base_Panel.add(btn_Panel, BorderLayout.EAST);

		btn_Msg_Send = ComponentFactory.createJButton(new SendMsgAction("Send Message"));

		btn_Panel.add(btn_Msg_Send);

		btn_Msg_Reset = ComponentFactory.createJButton(new ResetAction("Reset"));

		btn_Panel.add(btn_Msg_Reset);

		btn_Msg_Copy = ComponentFactory.createJButton(new CopyAction("Copy"));

		btn_Panel.add(btn_Msg_Copy);

		btn_Msg_Save = ComponentFactory.createJButton(new SaveAction("Save"));

		btn_Panel.add(btn_Msg_Save);

		btn_Msg_Clear = ComponentFactory.createJButton(new ClearAction("Clear"));

		btn_Panel.add(btn_Msg_Clear);

		txt_Msg_Send = ComponentFactory.createJTextField();

		base_Panel.add(txt_Msg_Send, BorderLayout.CENTER);

		txt_Msg_Send.setColumns(10);

		JPanel processor_Panel = ComponentFactory.createJPanel();

		processor_Panel.setPreferredSize(new Dimension(450, 350));

		processor_Panel.setBorder(new TitledBorder(null, "Select Processor", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		add(processor_Panel, BorderLayout.EAST);
		processor_Panel.setLayout(new BorderLayout(0, 0));

		comboBox = ComponentFactory.createJComboBox();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Select Slot", "Slot: 0", "Slot: 1" }));
		processor_Panel.add(comboBox, BorderLayout.NORTH);

		processorList_Panel = ComponentFactory.createJPanel();
		processorList_Panel.setBorder(new TitledBorder(null, "Processors", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		processor_Panel.add(processorList_Panel, BorderLayout.CENTER);
		processorList_Panel.setLayout(new BorderLayout(0, 0));

		list = ComponentFactory.createJList();
		list.setModel(new ProcessorModel(new String[] { "DSP -1", "DSP -2", "P2020" }));

		processorList_Panel.add(list);

		JPanel message_Panel = ComponentFactory.createJPanel();

		message_Panel.setBorder(new TitledBorder(null, "Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		add(message_Panel, BorderLayout.CENTER);

		message_Panel.setLayout(new BorderLayout(0, 0));

		scrl_Msg_Pane = ComponentFactory.createJScrollPane();

		message_Panel.add(scrl_Msg_Pane, BorderLayout.CENTER);

		txtP_Msg_Display = ComponentFactory.createJTextPane(txtP_Msg_Display_Document);

		// txtP_Msg_Display.setEditorKit(new RTFEditorKit());

		txtP_Msg_Display.setEditable(false);

		scrl_Msg_Pane.setViewportView(txtP_Msg_Display);
	}

	private void loadMessageStyles() {

		default_Style = txtP_Msg_Display_Context.getStyle(StyleContext.DEFAULT_STYLE);

		proc_Msg_Style = txtP_Msg_Display_Context.addStyle("Processor_Message", default_Style);

		StyleConstants.setFontSize(proc_Msg_Style, StyleConstants.getFontSize(default_Style) + 2);

		proc_From_Style = txtP_Msg_Display_Context.addStyle("Processor_Message_From", default_Style);

		StyleConstants.setBold(proc_From_Style, true);

		proc_To_Style = txtP_Msg_Display_Context.addStyle("Processor_Message_To", default_Style);

		StyleConstants.setItalic(proc_To_Style, true);

		proc_Time_Style = txtP_Msg_Display_Context.addStyle("Processor_Message_Time", default_Style);

		StyleConstants.setUnderline(proc_Time_Style, true);

		user_Msg_Style = txtP_Msg_Display_Context.addStyle("User_Message", default_Style);

		StyleConstants.setFontSize(user_Msg_Style, StyleConstants.getFontSize(default_Style) + 2);

		StyleConstants.setAlignment(user_Msg_Style, StyleConstants.ALIGN_RIGHT);

		user_From_Style = txtP_Msg_Display_Context.addStyle("User_Message_From", default_Style);

		StyleConstants.setItalic(user_From_Style, true);

		StyleConstants.setAlignment(user_From_Style, StyleConstants.ALIGN_RIGHT);

		user_To_Style = txtP_Msg_Display_Context.addStyle("User_Message_To", default_Style);

		StyleConstants.setBold(user_To_Style, true);

		StyleConstants.setAlignment(user_To_Style, StyleConstants.ALIGN_RIGHT);

		user_Time_Style = txtP_Msg_Display_Context.addStyle("User_Message_Time", default_Style);

		StyleConstants.setUnderline(user_Time_Style, true);

		StyleConstants.setAlignment(user_Time_Style, StyleConstants.ALIGN_RIGHT);

	}

	public void updateProcessorMessage(String msg) {

		try {
			int eo = txtP_Msg_Display_Document.getLength();

			txtP_Msg_Display_Document.insertString(eo, msg, null);

			txtP_Msg_Display_Document.setCharacterAttributes(eo, eo + msg.length(), proc_Msg_Style, false);

			txtP_Msg_Display_Document.setLogicalStyle(eo, proc_Msg_Style);

		} catch (BadLocationException e) {

			e.printStackTrace();
		}
	}

	public void updateProcessorMessage(String msg, Style style) {

		try {
			int eo = txtP_Msg_Display_Document.getLength();

			txtP_Msg_Display_Document.insertString(eo, msg, null);

			txtP_Msg_Display_Document.setCharacterAttributes(eo, eo + msg.length(), style, false);

			txtP_Msg_Display_Document.setLogicalStyle(eo, style);

		} catch (BadLocationException e) {

			e.printStackTrace();
		}
	}


	private void updateUserMessage(String msg, Style style) {
		try {
			int eo = txtP_Msg_Display_Document.getLength();

			txtP_Msg_Display_Document.insertString(eo, msg, null);

			txtP_Msg_Display_Document.setCharacterAttributes(eo, eo + msg.length(), style, false);

			txtP_Msg_Display_Document.setLogicalStyle(eo, style);

		} catch (BadLocationException e) {

			e.printStackTrace();
		}
	}

	private void clearContents() {

		txtP_Msg_Display.setText("");
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

			txtP_Msg_Display.write(fw);

			fw.close();

			/*
			 * RTFEditorKit kit = new RTFEditorKit();
			 * 
			 * BufferedOutputStream out;
			 * 
			 * out = new BufferedOutputStream(new FileOutputStream(path));
			 * 
			 * kit.write(out, txtP_Msg_Display_Document,
			 * txtP_Msg_Display_Document.getStartPosition().getOffset(),
			 * txtP_Msg_Display_Document.getLength());
			 * 
			 * out.close();
			 */

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
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			updateProcessorMessage("Slot : 0 :: DSP - 1: \n", proc_From_Style);

			updateProcessorMessage("to User: \n", proc_To_Style);

			updateProcessorMessage("Processor Message Comes Here\n", proc_Msg_Style);

			updateProcessorMessage("at " + getCurrentTime() + "\n\n", proc_Time_Style);

			// txt_Msg_Send.setText("");
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
			parent.updateLog("Message Sent.");

			updateUserMessage("User : \n", user_From_Style);

			updateUserMessage("to Slot: 0::DSP - 1 \n", user_To_Style);

			updateUserMessage(txt_Msg_Send.getText() + "\n", user_Msg_Style);

			updateUserMessage("at " + getCurrentTime() + "\n\n", user_Time_Style);
		}

	}

	class SaveAction extends AbstractAction {

		/**
		 * 
		 */

		public SaveAction(String name) {
			putValue(Action.NAME, name);
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
			putValue(Action.NAME, name);
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
			putValue(Action.NAME, name);
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			setClipboardContents(txtP_Msg_Display.getText());

			VPXUtilities.showPopup("Contents copied to clipboard");
		}
	}

	private class ProcessorModel extends AbstractListModel<String> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3395586591643389934L;
		String[] values = {};

		public ProcessorModel(String[] vals) {
			this.values = vals;
		}

		@Override
		public int getSize() {
			return values.length;
		}

		@Override
		public String getElementAt(int index) {
			return values[index];
		}

	}

}

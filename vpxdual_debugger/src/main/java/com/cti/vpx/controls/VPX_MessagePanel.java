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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
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

	private JScrollPane scrl_Msg_Pane;

	private JTextPane txtP_Msg_Display;

	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private final StyleContext txtP_Msg_Display_Context = new StyleContext();

	private final StyledDocument txtP_Msg_Display_Document = new DefaultStyledDocument(txtP_Msg_Display_Context);

	private Style proc_Msg_Style, proc_From_Style, proc_To_Style, proc_Time_Style;

	private Style user_Msg_Style, user_From_Style, user_To_Style, user_Time_Style;

	private Style default_Style;

	private JPanel panel;

	private JPanel panel_2;

	private MsgReceiver msgRecvr = new MsgReceiver();

	/**
	 * Create the panel.
	 */
	public VPX_MessagePanel(VPX_ETHWindow parent) {
		setBorder(new TitledBorder(null, "Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));

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

		scrl_Msg_Pane = ComponentFactory.createJScrollPane();

		message_Panel.add(scrl_Msg_Pane, BorderLayout.CENTER);

		txtP_Msg_Display = ComponentFactory.createJTextPane(txtP_Msg_Display_Document);

		txtP_Msg_Display.setPreferredSize(new Dimension(350, 35));
		txtP_Msg_Display.setEditable(false);

		scrl_Msg_Pane.setViewportView(txtP_Msg_Display);

		panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignOnBaseline(true);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		message_Panel.add(panel_2, BorderLayout.NORTH);

		btn_Msg_Reset = ComponentFactory.createJButton(new ResetAction("Reset"));
		btn_Msg_Reset.setPreferredSize(new Dimension(20, 16));
		btn_Msg_Reset.setBorderPainted(false);
		panel_2.add(btn_Msg_Reset);

		btn_Msg_Copy = ComponentFactory.createJButton(new CopyAction("Copy"));
		btn_Msg_Copy.setPreferredSize(new Dimension(20, 16));
		btn_Msg_Copy.setBorderPainted(false);
		panel_2.add(btn_Msg_Copy);

		btn_Msg_Clear = ComponentFactory.createJButton(new ClearAction("Clear"));
		btn_Msg_Clear.setPreferredSize(new Dimension(20, 16));
		btn_Msg_Clear.setBorderPainted(false);
		panel_2.add(btn_Msg_Clear);

		btn_Msg_Save = ComponentFactory.createJButton(new SaveAction("Save"));
		btn_Msg_Save.setPreferredSize(new Dimension(20, 16));
		btn_Msg_Save.setBorderPainted(false);
		panel_2.add(btn_Msg_Save);
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
			
			txtP_Msg_Display.setCaretPosition(txtP_Msg_Display_Document.getLength());
				
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

	public void startRecieveMessage() {
		msgRecvr.execute();
	}

	public void stopRecieveMessage() {
		msgRecvr.cancel(true);
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
			putValue(Action.SHORT_DESCRIPTION, name);
			putValue(Action.SMALL_ICON, VPXUtilities.getImageIcon(("images\\undo.gif"), 14, 14));
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			updateProcessorMessage("Slot : 0 :: DSP - 1: \n", proc_From_Style);

			// updateProcessorMessage("to User: \n", proc_To_Style);

			updateProcessorMessage("Processor Message Comes Here\n", proc_Msg_Style);

			// updateProcessorMessage("at " + getCurrentTime() + "\n\n",
			// proc_Time_Style);

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

			// updateUserMessage("User : \n", user_From_Style);

			updateUserMessage("to Slot: 0::DSP - 1 \n", user_To_Style);

			updateUserMessage(txt_Msg_Send.getText() + "\n", user_Msg_Style);

			// updateUserMessage("at " + getCurrentTime() + "\n\n",
			// user_Time_Style);
		}

	}

	class SaveAction extends AbstractAction {

		/**
		 * 
		 */

		public SaveAction(String name) {
			putValue(Action.SHORT_DESCRIPTION, name);
			putValue(Action.SMALL_ICON, VPXUtilities.getImageIcon(("images\\save.gif"), 14, 14));
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
			putValue(Action.SMALL_ICON, VPXUtilities.getImageIcon(("images\\delete.gif"), 14, 14));
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
			putValue(Action.SMALL_ICON, VPXUtilities.getImageIcon(("images\\copy.gif"), 14, 14));
		}

		private static final long serialVersionUID = -780929428772240491L;

		@Override
		public void actionPerformed(ActionEvent e) {

			setClipboardContents(txtP_Msg_Display.getText());

			VPXUtilities.showPopup("Contents copied to clipboard");
		}
	}

	class MsgReceiver extends SwingWorker<Void, String> {

		DatagramSocket serverSocket;

		byte[] receiveData = new byte[1024];

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		public MsgReceiver() {
			try {
				serverSocket = new DatagramSocket(12346);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground() throws Exception {
			while (true) {
				serverSocket.receive(receivePacket);

				String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
				
				updateProcessorMessage("Slot : 0 :: DSP - 1: \n", proc_From_Style);

				updateProcessorMessage(sentence+"\n", proc_Msg_Style);

				Thread.sleep(500);
			}
		}

		@Override
		protected void process(List<String> chunks) {
			// TODO Auto-generated method stub
			super.process(chunks);
		}

	}
}

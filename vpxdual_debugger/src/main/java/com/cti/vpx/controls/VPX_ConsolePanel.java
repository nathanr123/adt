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
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

import com.cti.vpx.model.VPX;
import com.cti.vpx.util.ComponentFactory;
import com.cti.vpx.util.VPXUtilities;

public class VPX_ConsolePanel extends JPanel implements ClipboardOwner {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2395447790825321429L;

	private JTextArea txtA_Console;

	private FileWriter fw;

	private ConsoleMessageReceiver consoleMsgReceiver = new ConsoleMessageReceiver();

	/**
	 * Create the panel.
	 */
	public VPX_ConsolePanel() {

		init();

		loadComponents();

		startRecieveMessage();
	}

	private void init() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		setLayout(new BorderLayout(0, 0));

	}

	private void loadComponents() {

		JPanel console_Panel = ComponentFactory.createJPanel();

		FlowLayout flowLayout = (FlowLayout) console_Panel.getLayout();

		flowLayout.setAlignment(FlowLayout.RIGHT);

		add(console_Panel, BorderLayout.NORTH);

		JButton btn_Console_Clear = ComponentFactory.createJButton(new ClearAction("Clear"));
		btn_Console_Clear.setFocusPainted(false);
		btn_Console_Clear.setBorderPainted(false);
		btn_Console_Clear.setPreferredSize(new Dimension(20, 16));

		console_Panel.add(btn_Console_Clear);

		JButton btn_Console_Copy = ComponentFactory.createJButton(new CopyAction("Copy"));
		btn_Console_Copy.setFocusPainted(false);
		btn_Console_Copy.setBorderPainted(false);
		btn_Console_Copy.setPreferredSize(new Dimension(20, 16));

		console_Panel.add(btn_Console_Copy);

		JButton btn_Console_Save = ComponentFactory.createJButton(new SaveAction("Save"));
		btn_Console_Save.setFocusPainted(false);
		btn_Console_Save.setBorderPainted(false);
		btn_Console_Save.setPreferredSize(new Dimension(20, 16));

		console_Panel.add(btn_Console_Save);

		JScrollPane scrl_Console = ComponentFactory.createJScrollPane();

		add(scrl_Console, BorderLayout.CENTER);

		txtA_Console = ComponentFactory.createJTextArea();

		scrl_Console.setViewportView(txtA_Console);

		txtA_Console.setEditable(false);

	}

	public void printConsoleMsg(String msg) {

		if (txtA_Console.getCaretPosition() > 0)

			txtA_Console.append("\n");

		String[] strs = msg.split(":");

		txtA_Console.append(String.format("Core %s : %s", strs[0], strs[1]));

		txtA_Console.setCaretPosition(txtA_Console.getText().length());

	}

	public void printConsoleMsg(String ip, String msg) {

		if (ip.equals(VPXUtilities.getCurrentProcessor())) {

			if (txtA_Console.getCaretPosition() > 0)

				txtA_Console.append("\n");

			String[] strs = msg.split(":");

			txtA_Console.append(String.format("Core %s : %s", strs[0], strs[1]));

			txtA_Console.setCaretPosition(txtA_Console.getText().length());
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

		try {
			String path = System.getProperty("user.home") + "\\"
					+ txtA_Console.getText().split("  ")[0].replace(':', '_').replace(' ', '_').replace('-', '_')
					+ ".log";

			fw = new FileWriter(new File(path), true);

			txtA_Console.write(fw);

			fw.close();

			VPXUtilities.showPopup("File Saved at " + path);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void startRecieveMessage() {
		consoleMsgReceiver.execute();
	}

	private void stopRecieveMessage() {
		consoleMsgReceiver.cancel(true);
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {

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
			saveConsoleMsgtoFile();
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

			setClipboardContents(txtA_Console.getText());

			VPXUtilities.showPopup("Contents copied to clipboard");
		}
	}

	class ConsoleMessageReceiver extends SwingWorker<Void, String> {

		DatagramSocket serverSocket;

		byte[] receiveData = new byte[1024];

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		public ConsoleMessageReceiver() {
			try {
				serverSocket = new DatagramSocket(VPX.CONSOLE_PORTNO);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground() throws Exception {
			while (true) {
				serverSocket.receive(receivePacket);

				String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

				printConsoleMsg(receivePacket.getAddress().getHostAddress(), sentence);

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
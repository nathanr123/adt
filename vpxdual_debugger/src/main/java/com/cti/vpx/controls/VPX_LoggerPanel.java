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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_LoggerPanel extends JPanel implements ClipboardOwner {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2395447790825321429L;

	private JTextArea txtA_Log;

	private FileWriter fw;

	private VPX_ETHWindow parent;

	/**
	 * Create the panel.
	 */
	public VPX_LoggerPanel(VPX_ETHWindow parnt) {

		this.parent = parnt;

		init();

		loadComponents();
	}

	private void init() {

		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		setLayout(new BorderLayout(0, 0));

	}

	private void loadComponents() {

		JPanel log_Panel = VPXComponentFactory.createJPanel();

		FlowLayout flowLayout = (FlowLayout) log_Panel.getLayout();

		flowLayout.setAlignment(FlowLayout.RIGHT);

		add(log_Panel, BorderLayout.NORTH);

		JButton btn_Log_Clear = VPXComponentFactory.createJButton(new ClearAction("Clear"));

		btn_Log_Clear.setFocusPainted(false);

		btn_Log_Clear.setBorderPainted(false);

		btn_Log_Clear.setPreferredSize(new Dimension(22, 22));

		log_Panel.add(btn_Log_Clear);

		JButton btn_Log_Copy = VPXComponentFactory.createJButton(new CopyAction("Copy"));

		btn_Log_Copy.setFocusPainted(false);

		btn_Log_Copy.setBorderPainted(false);

		btn_Log_Copy.setPreferredSize(new Dimension(22, 22));

		log_Panel.add(btn_Log_Copy);

		JButton btn_Log_Save = VPXComponentFactory.createJButton(new SaveAction("Save"));

		btn_Log_Save.setFocusPainted(false);

		btn_Log_Save.setBorderPainted(false);

		btn_Log_Save.setPreferredSize(new Dimension(22, 22));

		log_Panel.add(btn_Log_Save);

		JScrollPane scrl_Log = VPXComponentFactory.createJScrollPane();

		add(scrl_Log, BorderLayout.CENTER);

		txtA_Log = VPXComponentFactory.createJTextArea();

		scrl_Log.setViewportView(txtA_Log);

		txtA_Log.setEditable(false);

	}

	public void appendLog(String log) {

		if (log.length() > 0) {

			if (txtA_Log.getCaretPosition() > 0)

				txtA_Log.append("\n");

			txtA_Log.append(log);

			txtA_Log.setCaretPosition(txtA_Log.getText().length());
		}
	}

	private void clearContents() {

		txtA_Log.setText("");
	}

	private void setClipboardContents(String aString) {

		StringSelection stringSelection = new StringSelection(aString);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		clipboard.setContents(stringSelection, this);
	}

	private void saveLogtoFile() {

		try {

			String path = VPXSessionManager.getEventPath() + "/"
					+ VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.LOG_FILEPATH) + "_"
					+ VPXUtilities.getCurrentTime(3) + ".log";

			fw = new FileWriter(new File(path), true);

			txtA_Log.write(fw);

			fw.close();

			VPXUtilities.showPopup("File Saved at " + path, path);

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

			setClipboardContents(txtA_Log.getText());

			VPXUtilities.showPopup("Contents copied to clipboard");
		}
	}

}
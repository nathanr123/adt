package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.cti.vpx.util.ComponentFactory;

public class VPX_LoggerPanel extends JPanel implements ClipboardOwner {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2395447790825321429L;

	private JTextArea txtA_Log;

	public static int INFO = 0;

	public static int ERROR = 1;

	public static int WARN = 2;

	public static int FATAL = 3;

	private FileWriter fw;

	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	/**
	 * Create the panel.
	 */
	public VPX_LoggerPanel() {

		init();

		loadComponents();
	}

	private void init() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		setLayout(new BorderLayout(0, 0));

	}

	private void loadComponents() {

		JPanel log_Panel = ComponentFactory.createJPanel();

		FlowLayout flowLayout = (FlowLayout) log_Panel.getLayout();

		flowLayout.setAlignment(FlowLayout.RIGHT);

		add(log_Panel, BorderLayout.SOUTH);

		JButton btn_Log_Clear = ComponentFactory.createJButton(new ClearAction("Clear"));

		log_Panel.add(btn_Log_Clear);

		JButton btn_Log_Copy = ComponentFactory.createJButton(new CopyAction("Copy"));

		log_Panel.add(btn_Log_Copy);

		JButton btn_Log_Save = ComponentFactory.createJButton(new SaveAction("Save"));

		log_Panel.add(btn_Log_Save);

		JScrollPane scrl_Log = ComponentFactory.createJScrollPane();

		add(scrl_Log, BorderLayout.CENTER);

		txtA_Log = ComponentFactory.createJTextArea();

		scrl_Log.setViewportView(txtA_Log);

		txtA_Log.setEditable(false);

	}

	public void updateLog(String log) {

		updateLog(INFO, log);
	}

	public void updateLog(int LEVEL, String log) {

		if (txtA_Log.getCaretPosition() > 0)

			txtA_Log.append("\n");

		txtA_Log.append(getCurrentTime() + "  " + getLevel(LEVEL) + "  " + log);
	}

	private String getLevel(int level) {

		String lvl = "INFO: ";

		if (level == INFO) {

			lvl = "INFO: ";

		} else if (level == ERROR) {

			lvl = "ERROR: ";

		} else if (level == WARN) {

			lvl = "WARN: ";

		} else if (level == FATAL) {

			lvl = "FATAL: ";

		}

		return lvl;
	}

	private String getCurrentTime() {

		return df.format(Calendar.getInstance().getTime());
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
			String path = System.getProperty("user.home") + "\\"
					+ txtA_Log.getText().split("  ")[0].replace(':', '_').replace(' ', '_').replace('-', '_') + ".log";

			fw = new FileWriter(new File(path), true);

			txtA_Log.write(fw);

			fw.close();

			showPopup("File Saved at " + path);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {

	}

	private void showPopup(String msg) {
		final JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);

		final JDialog dialog = new JDialog();
		dialog.setTitle("Message");
		dialog.setModal(true);
		dialog.setUndecorated(true);
		dialog.setContentPane(optionPane);
		optionPane.setBorder(new LineBorder(Color.GRAY));
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		dialog.setSize(new Dimension(350, 100));

		// create timer to dispose of dialog after 5 seconds
		Timer timer = new Timer(1000, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false);// the timer should only go off once

		// start timer to close JDialog as dialog modal we must start the timer
		// before its visible
		timer.start();

		Dimension windowSize = dialog.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		dialog.setLocation(dx, dy);

		dialog.setVisible(true);
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

			setClipboardContents(txtA_Log.getText());

			showPopup("Contents copied to clipboard");
		}
	}

}
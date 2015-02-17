/**
 * 
 */
package com.cti.rmopp.controls;

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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author nathanr_kamal
 *
 */
public class CLoggerPane extends CPanel implements ActionListener, ClipboardOwner {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2126073148801164216L;

	private CScrollPane scrollPane;

	private CTextArea txtArea;

	private CPanel controlPanel;

	private CButton btnClear, btnSave, btnExport, btnCpyClipBrd;

	public static int INFO = 0;

	public static int ERROR = 1;

	public static int WARN = 2;

	public static int FATAL = 3;

	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private FileWriter fw;

	public CLoggerPane() {

		init("Activities Log");
	}

	public CLoggerPane(String title) {

		init(title);
	}

	private void init(String title) {

		setLayout(new BorderLayout(10, 10));

		txtArea = ComponentFactory.createTextArea();

		txtArea.setEditable(false);

		scrollPane = ComponentFactory.createScrollPane(txtArea);

		setBorder(ComponentFactory.createTitledBorder(title));

		controlPanel = ComponentFactory.createPanel();

		controlPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		btnClear = ComponentFactory.createButton("Clear");

		btnSave = ComponentFactory.createButton("Save");

		// btnExport = ComponentFactory.createButton("Export");

		btnCpyClipBrd = ComponentFactory.createButton("Copy to ClipBoard");

		btnClear.setPreferredSize(new Dimension(65, 25));

		btnSave.setPreferredSize(new Dimension(65, 25));

		// btnExport.setPreferredSize(new Dimension(85, 35));

		btnCpyClipBrd.setPreferredSize(new Dimension(150, 25));

		controlPanel.add(btnClear);

		controlPanel.add(btnSave);

		// controlPanel.add(btnExport);

		btnClear.addActionListener(this);
		
		btnSave.addActionListener(this);
		
		btnCpyClipBrd.addActionListener(this);

		controlPanel.add(btnCpyClipBrd);

		add(scrollPane, BorderLayout.CENTER);

		add(controlPanel, BorderLayout.SOUTH);

	}

	public void updateLog(String log) {

		updateLog(INFO, log);
	}

	public void updateLog(int LEVEL, String log) {

		if (txtArea.getCaretPosition() > 0)
			txtArea.append("\n");

		txtArea.append(getCurrentTime() + "  " + getLevel(LEVEL) + "  " + log);
	}

	private String getLevel(int level) {
		String lvl = "INFO";
		if (level == INFO) {
			lvl = "INFO";
		} else if (level == ERROR) {
			lvl = "ERROR";
		} else if (level == WARN) {
			lvl = "WARN";
		} else if (level == FATAL) {
			lvl = "FATAL";
		}
		return lvl;
	}

	private String getCurrentTime() {

		return df.format(Calendar.getInstance().getTime());
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Clear")) {

			clearContents();

		} else if (cmd.equals("Save")) {

			saveLogtoFile();

		} else if (cmd.equals("Copy to ClipBoard")) {

			setClipboardContents(txtArea.getText());
		}
	}

	private void clearContents() {
		txtArea.setText("");
	}

	private void setClipboardContents(String aString) {
		StringSelection stringSelection = new StringSelection(aString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);
	}

	private void saveLogtoFile() {

		try {

			fw = new FileWriter(new File(System.getProperty("user.home") + "\\"
					+ txtArea.getText().split("  ")[0].replace(':', '_').replace(' ', '_').replace('-', '_') + ".log"),
					true);

			txtArea.write(fw);

			fw.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {

	}
}

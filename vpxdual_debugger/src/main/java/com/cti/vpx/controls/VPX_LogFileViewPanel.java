package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.cti.vpx.util.VPXLogger;

public class VPX_LogFileViewPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 739551556332574938L;

	// Size of editing text area.
	private static final int NUM_ROWS = 25;

	private static final int NUM_COLS = 50;

	private JTextArea logFileText;

	/**
	 * Create the panel.
	 */
	public VPX_LogFileViewPanel(File logFile) {

		init();

		loadComponents();

		loadFile(logFile);

	}

	private void init() {

		setLayout(new BorderLayout());

		setPreferredSize(new Dimension(790, 863));

	}

	private void loadComponents() {

		logFileText = new JTextArea(NUM_ROWS, NUM_COLS);

		logFileText.setFont(new Font("Consolas", Font.PLAIN, 11));

		JScrollPane textScroller = new JScrollPane(logFileText);

		add(textScroller, BorderLayout.CENTER);

	}

	private void loadFile(File file) {

		String line;

		try {
			// Open the file.
			BufferedReader input = new BufferedReader(new FileReader(file));

			// Clear the editing area
			logFileText.setText("");

			Font ff = new Font(logFileText.getFont().getFamily(), Font.PLAIN, 14);

			logFileText.setFont(ff);

			// Fill up the ediitng area with the contents of the file being
			// read.
			line = input.readLine();

			while (line != null) {

				logFileText.append(line + "\n");

				line = input.readLine();
			}

			// Close the file
			input.close();

		} catch (IOException e) {

			VPXLogger.updateError(e);
			
			JOptionPane.showMessageDialog(logFileText, "Can't load file " + e.getMessage());
		}

	}
}

/*
 * Copyright (c) 2008 Robert Futrell
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name "HexEditor" nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.cti.vpx.controls.hex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cti.vpx.controls.hex.groupmodel.Floating32;
import com.cti.vpx.controls.hex.groupmodel.Hex16;
import com.cti.vpx.controls.hex.groupmodel.Hex32;
import com.cti.vpx.controls.hex.groupmodel.Hex64;
import com.cti.vpx.controls.hex.groupmodel.Hex8;
import com.cti.vpx.controls.hex.groupmodel.SignedInt16;
import com.cti.vpx.controls.hex.groupmodel.SignedInt32;
import com.cti.vpx.controls.hex.groupmodel.UnSignedInt16;
import com.cti.vpx.controls.hex.groupmodel.UnSignedInt32;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXUtilities;

;

/**
 * Content pane for the demo applet/standalone app, that demonstrates the
 * functionality of the Swing {@link HexEditor} component.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class HexEditorPanel extends JPanel implements ActionListener, HexEditorListener, SelectionChangedListener {

	private static final long serialVersionUID = 1L;

	private HexEditor editor;

	private JFileChooser chooser;

	private JCheckBox colHeaderCB;

	private JComboBox<String> cmbFormatBytes;

	private JCheckBox rowHeaderCB;

	private JCheckBox showGridCB;

	private JCheckBox altRowBGCB;

	private JCheckBox altColBGCB;

	private JCheckBox highlightAsciiSelCB;

	private JComboBox<Color> highlightAsciiSelCombo;

	private JCheckBox lowBytePaddingCB;

	private JTextField infoField;

	private JTextField selField;

	private JTextField sizeField;

	private Action openAction;

	private Action cutAction;

	private Action copyAction;

	private Action pasteAction;

	private Action deleteAction;

	private Action undoAction;

	private Action redoAction;

	private Action settingsAction;

	private DumpAction dumpAction;

	private DumpAsAction dumpAsAction;

	private JButton btnOpenAction;

	private JButton btnDumpAction;

	private JButton btnDumpAsAction;

	private JButton btnCutAction;

	private JButton btnCopyAction;

	private JButton btnPasteAction;

	private JButton btnDeleteAction;

	private JButton btnUndoAction;

	private JButton btnRedoAction;

	private JButton btnSettingsAction;

	private VPX_MemoryBrowserWindow memoryWindow;

	private VPX_MemorySetWindow memStFillWindow = new VPX_MemorySetWindow(this);

	private VPX_MemoryLoadWindow memoryLoadWindow = new VPX_MemoryLoadWindow(this);

	private ConfigPanel settingsPanel;

	private static final ResourceBundle msg = VPXUtilities.getResourceBundle();

	/**
	 * 
	 * Constructor.
	 */
	public HexEditorPanel(VPX_MemoryBrowserWindow memWindow) {

		this.memoryWindow = memWindow;

		setLayout(new BorderLayout());

		createActions(msg);

		add(createToolBar(), BorderLayout.NORTH);

		JPanel temp = new JPanel(new BorderLayout());

		infoField = new JTextField();

		infoField.setEditable(false);

		infoField.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), infoField.getBorder()));

		selField = new JTextField(30);

		selField.setEditable(false);

		selField.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5), selField.getBorder()));

		sizeField = new JTextField(15);

		sizeField.setEditable(false);

		sizeField.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5), sizeField.getBorder()));

		JPanel temp2 = new JPanel(new BorderLayout());

		JPanel temp3 = new JPanel(new BorderLayout());

		temp2.add(infoField);

		temp3.add(selField, BorderLayout.LINE_START);

		temp3.add(sizeField, BorderLayout.LINE_END);

		temp2.add(temp3, BorderLayout.LINE_END);

		temp.add(temp2, BorderLayout.SOUTH);

		add(temp, BorderLayout.SOUTH);

		// Add the editor after infoField as it listens to byte changes.
		editor = new HexEditor(this);

		editor.addHexEditorListener(this);

		editor.addSelectionChangedListener(this);

		// handleOpenFile("D:\\test1.bin", "0x0000000");

		add(editor);

	}

	/**
	 * Listens for events in this panel.
	 *
	 * @param e
	 *            The event that occurred.
	 */
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == colHeaderCB) {

			editor.setShowColumnHeader(colHeaderCB.isSelected());

		} else if (source == rowHeaderCB) {

			editor.setShowRowHeader(0, 0, rowHeaderCB.isSelected());

		} else if (source == showGridCB) {

			editor.setShowGrid(showGridCB.isSelected());

		} else if (source == altColBGCB) {

			editor.setAlternateColumnBG(altColBGCB.isSelected());

		} else if (source == altRowBGCB) {

			editor.setAlternateRowBG(altRowBGCB.isSelected());

		} else if (source == highlightAsciiSelCB) {

			editor.setHighlightSelectionInAsciiDump(highlightAsciiSelCB.isSelected());

		} else if (source == lowBytePaddingCB) {

			editor.setPadLowBytes(lowBytePaddingCB.isSelected());

		}

	}

	/**
	 * Creates the actions used by this panel.
	 *
	 * @param msg
	 *            The resource bundle used to localize the actions.
	 */
	private void createActions(ResourceBundle msg) {

		openAction = new OpenAction();

		cutAction = new CutAction();

		copyAction = new CopyAction();

		pasteAction = new PasteAction();

		deleteAction = new DeleteAction();

		undoAction = new UndoAction();

		redoAction = new RedoAction();

		settingsAction = new SettingsAction();

		dumpAction = new DumpAction();

		dumpAsAction = new DumpAsAction();
	}

	/**
	 * Creates the toolbar used in this demo panel.
	 *
	 * @return The toolbar.
	 */
	private JToolBar createToolBar() {

		JToolBar toolbar = new JToolBar();

		toolbar.setFloatable(false);

		cmbFormatBytes = new JComboBox<String>(
				new DefaultComboBoxModel<String>(new String[] { "8 Bit Hex", "16 Bit Hex", "32 Bit Hex", "64 Bit Hex",
						"16 Bit Signed", "32 Bit Signed", "16 Bit Unsigned", "32 Bit Unsigned", "32 Bit Floating" }));

		cmbFormatBytes.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getSource().equals(cmbFormatBytes)) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

						loadGroupingModel();
					}
				}
			}
		});

		cmbFormatBytes.setMaximumSize(new Dimension(200, 25));

		toolbar.add(cmbFormatBytes);

		toolbar.addSeparator();

		createToolBarButton();

		toolbar.add(btnOpenAction);

		toolbar.add(btnDumpAction);

		toolbar.add(btnDumpAsAction);

		toolbar.addSeparator();

		toolbar.add(btnCutAction);

		toolbar.add(btnCopyAction);

		toolbar.add(btnPasteAction);

		toolbar.add(btnDeleteAction);

		toolbar.addSeparator();

		// toolbar.add(btnUndoAction);

		// toolbar.add(btnRedoAction);

		// toolbar.addSeparator();

		toolbar.add(btnSettingsAction);

		return toolbar;

	}

	private void loadGroupingModel() {

		editor.setHexModel(cmbFormatBytes.getSelectedIndex());
	}

	public HexEditor getHexEditor() {
		return this.editor;
	}

	/**
	 * Creates a button to add to the toolbar for an action.
	 *
	 * @param a
	 *            The action.
	 * @return The button.
	 */
	private void createToolBarButton() {

		btnOpenAction = new JButton(openAction);
		btnOpenAction.setText(null);

		btnDumpAction = new JButton(dumpAction);
		btnDumpAction.setText(null);

		btnDumpAsAction = new JButton(dumpAsAction);
		btnDumpAsAction.setText(null);

		btnCutAction = new JButton(cutAction);
		btnCutAction.setText(null);

		btnCopyAction = new JButton(copyAction);
		btnCopyAction.setText(null);

		btnPasteAction = new JButton(pasteAction);
		btnPasteAction.setText(null);

		btnDeleteAction = new JButton(deleteAction);
		btnDeleteAction.setText(null);

		btnUndoAction = new JButton(undoAction);
		btnUndoAction.setText(null);

		btnRedoAction = new JButton(redoAction);
		btnRedoAction.setText(null);

		btnSettingsAction = new JButton(settingsAction);
		btnSettingsAction.setText(null);

	}

	public VPX_MemoryBrowserWindow getMemoryWindow() {

		return this.memoryWindow;

	}

	private void showSettingsDialog() {
		if (settingsPanel == null) {
			settingsPanel = new ConfigPanel();
		}
		settingsPanel.setVisible(true);
	}

	public void dumpToFile() {
		try {

			if (chooser == null) {

				chooser = new JFileChooser();
			}

			chooser.setAcceptAllFileFilterUsed(false);

			chooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin Files", "bin"));

			chooser.setDialogTitle("Specify a file to save");

			int rc = chooser.showSaveDialog(this);

			if (rc == JFileChooser.APPROVE_OPTION) {

				String path = chooser.getSelectedFile().getPath();

				if (!path.endsWith(".bin")) {
					path += ".bin";
				}

				FileOutputStream fos = new FileOutputStream(path);

				fos.write(getClipboardContents().getBytes());

				fos.close();

			}
		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}
	}

	public void dumpToTextFile() {
		try {

			if (chooser == null) {

				chooser = new JFileChooser();
			}

			chooser.setAcceptAllFileFilterUsed(false);

			chooser.addChoosableFileFilter(new FileNameExtensionFilter("txt Files", "txt"));

			chooser.setDialogTitle("Specify a file to save");

			int rc = chooser.showSaveDialog(this);

			if (rc == JFileChooser.APPROVE_OPTION) {

				String path = chooser.getSelectedFile().getPath();

				if (!path.endsWith(".txt")) {
					path += ".txt";
				}

				FileOutputStream fos = new FileOutputStream(path);

				fos.write(getAsFormattedString(getClipboardContents().getBytes()));

				fos.close();

			}
		} catch (Exception e) {
			VPXLogger.updateError(e);
			e.printStackTrace();
		}
	}

	private byte[] getAsFormattedString(byte[] values) {

		String str = "";

		byte[] formattedString = null;

		if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX8) {

			for (int i = 0; i < values.length; i++) {

				str = str + String.format("%02x", values[i])
						+ ((((i + 1) % 16) == 0) ? System.getProperty("line.separator") : " ");
			}

			formattedString = str.getBytes();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX16) {

			for (int i = 0; i < values.length; i = i + 2) {

				if ((i + 1) < values.length) {

					str = str + String.format("%02x%02x", values[i + 1], values[i]);

					if (i > 0) {

						if (((i + 2) % 32) == 0) {

							str = str + System.getProperty("line.separator");

						} else {

							str = str + " ";
						}
					} else {

						str = str + " ";
					}

				}

			}

			formattedString = str.getBytes();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX32) {

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX64) {

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.SINGNEDINT16) {

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.SINGNEDINT32) {

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.UNSINGNEDINT16) {

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.UNSINGNEDINT32) {

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.UNSINGNEDFLOAT32) {

		}

		return formattedString;
	}

	/**
	 * Prompts the user for a file to open, and opens it.
	 */
	public void doOpen() {

		File file = null;

		String startAddress = "0x0000000";

		int rc = memoryLoadWindow.showLoadMemoryWinow(memoryWindow.getSelectedProcessor(),
				memoryWindow.getSelectedCore());

		if (rc == 1) {// JFileChooser.APPROVE_OPTION) {

			VPX_MemoryLoadProgressWindow dialog = new VPX_MemoryLoadProgressWindow(memoryWindow);

			dialog.setVisible(true);

			file = new File(memoryLoadWindow.getFileName());// chooser.getSelectedFile();

			startAddress = (memoryLoadWindow.isStartAddress() ? memoryLoadWindow.getStartAddress() : "0x0000000");

			VPXUtilities.getParent().sendMemoryFile(memoryWindow.getSelectedProcessor(), memoryLoadWindow.getFileName(),
					(VPXUtilities.getValue(startAddress) == -1) ? 0 : VPXUtilities.getValue(startAddress), dialog);

			handleOpenFile(file.getAbsolutePath(), startAddress);
		}

	}

	private String getInfoString(String key, int offs, int param) {

		String text = msg.getString(key);

		text = MessageFormat.format(text, new Object[] { new Integer(offs), new Integer(param) });

		return text;
	}

	private String getInfoString(String key, int offs, int param1, int param2) {

		String text = msg.getString(key);

		text = MessageFormat.format(text, new Object[] { new Integer(offs), new Integer(param1), new Integer(param2) });

		return text;
	}

	public void handleRecievedBuffer(byte[] bytes, int stride) {

		try {

			if (bytes.length > 0) { // In a jar
				editor.open(bytes, stride);

			} else { // In a jar
				throw new IOException("byte array is not valid");
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
			String message = msg.getString("Error.Title");
			String title = msg.getString("Error.Desc");
			title = MessageFormat.format(title, new Object[] { ioe.toString() });
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setBytes(long startAddress, int stride, byte[] buffer) {
		try {
			editor.open(buffer, stride);
			editor.setShowRowHeader(startAddress, stride, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens a file. Displays an error dialog if the file cannot be opened.
	 *
	 * @param fileName
	 *            The file to open.
	 */
	private void handleOpenFile(String fileName, String startAddress) {
		// Check jar first to prevent Applet AccessControlException
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream in = cl.getResourceAsStream(fileName);
		try {
			if (in != null) { // In a jar
				editor.open(new BufferedInputStream(in));
			} else { // e.g. in Eclipse debugger
				File file = new File(fileName);
				if (file.isFile()) { // e.g. in Eclipse debugger
					editor.open(fileName);

					editor.setShowRowHeader(
							(VPXUtilities.getValue(startAddress) == -1) ? 0 : VPXUtilities.getValue(startAddress), 0,
							true);
				} else { // In a jar
					throw new IOException("Resource not found: " + fileName);
				}
			}
		} catch (IOException ioe) {
			VPXLogger.updateError(ioe);
			ioe.printStackTrace();
			String message = msg.getString("Error.Title");
			String title = msg.getString("Error.Desc");
			title = MessageFormat.format(title, new Object[] { ioe.toString() });
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Called when the bytes in the hex editor change.
	 *
	 * @param e
	 *            The event.
	 */
	public void hexBytesChanged(HexEditorEvent e) {

		String text = null;

		if (e.isModification()) {
			text = getInfoString("InfoFieldModified", e.getOffset(), e.getAddedCount());
		} else {
			int added = e.getAddedCount();
			int removed = e.getRemovedCount();
			if (added > 0 && removed == 0) {
				text = getInfoString("InfoFieldAdded", e.getOffset(), e.getAddedCount());
			} else if (added == 0 && removed > 0) {
				text = getInfoString("InfoFieldRemoved", e.getOffset(), e.getRemovedCount());
			} else {
				text = getInfoString("InfoFieldBoth", e.getOffset(), e.getAddedCount(), e.getRemovedCount());
			}
		}
		infoField.setText(text);

		text = msg.getString("SizeField");
		text = MessageFormat.format(text, new Object[] { new Integer(e.getHexEditor().getByteCount()) });
		sizeField.setText(text);

	}

	/**
	 * Returns whether Nimbus is supported by the current JVM. Nimbus was added
	 * in 6u10, and the package containing it changed in Java 7, so this is the
	 * only reliable way to check for it.
	 *
	 * @return Whether Nimbus is installed.
	 */
	@SuppressWarnings("unused")
	private boolean isNimbusSupported() {
		// Overridden to always return false, since Nimbus doesn't uninstall
		// cleanly (leaves table selection color behind) and we don't want it
		// to look like a HexEditor bug.
		return false;
		/*
		 * LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels(); for
		 * (int i=0; i<infos.length; i++) { if
		 * ("Nimbus".equals(infos[i].getName())) { return true; } } return
		 * false;
		 */
	}

	/**
	 * Called when the selection changes in the hex editor.
	 *
	 * @param An
	 *            object describing the selection.
	 */
	public void selectionChanged(SelectionChangedEvent e) {
		int offs = e.getNewSelecStart();
		int count = e.getNewSelecEnd() - offs + 1;
		String subject = count == 1 ? " byte" : " bytes";
		selField.setText(count + subject + " selected at offset " + offs);
	}

	/**
	 * Base class for all actions in the demo.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private abstract class ActionBase extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public ActionBase() {
			String name = msg.getString(getNameKey());
			putValue(Action.NAME, name);
			putValue(Action.SHORT_DESCRIPTION, name);
			ImageIcon icon = VPXUtilities.getImageIcon(("" + getIconKey()), 16, 16);
			putValue(Action.SMALL_ICON, icon);
		}

		protected abstract String getNameKey();

		protected abstract String getIconKey();

	}

	/**
	 * Renderer for JComboBox content lists containing colors.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private static class ColorCellRenderer extends DefaultListCellRenderer implements Icon {

		private static final long serialVersionUID = 1L;

		private Color c;

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected,
				boolean hasFocus) {
			super.getListCellRendererComponent(list, null, index, selected, hasFocus);
			if (value instanceof Color) {
				c = (Color) value;
			}
			return this;
		}

		public Icon getIcon() {
			return this;
		}

		public int getIconHeight() {
			return 16;
		}

		public int getIconWidth() {
			return 16;
		}

		public void paintIcon(Component comp, Graphics g, int x, int y) {
			g.setColor(c);
			g.fillRect(x, y, getIconWidth(), getIconHeight());
			g.setColor(Color.BLACK);
			g.drawRect(x, y, getIconWidth(), getIconHeight());
		}

	}

	private class ConfigPanel extends JDialog {

		private static final long serialVersionUID = 2134814425101136192L;

		private final JPanel contentPanel = new JPanel();

		public ConfigPanel() {

			init();

			loadComponents();

			pack();

			centerFrame();
		}

		private void init() {
			setAlwaysOnTop(true);
			setResizable(false);
			setModal(true);
			setTitle("Display Settings");
			setBounds(100, 100, 415, 247);
			getContentPane().setLayout(new BorderLayout());
		}

		private void loadComponents() {
			contentPanel
					.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[] { 13, 0, 0, 0, 0 };
			gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
			gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
			gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			contentPanel.setLayout(gbl_contentPanel);

			colHeaderCB = new JCheckBox(msg.getString("ColHeaderCB"), true);
			colHeaderCB.addActionListener(HexEditorPanel.this);
			GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
			gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
			gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxNewCheckBox.gridx = 1;
			gbc_chckbxNewCheckBox.gridy = 0;
			contentPanel.add(colHeaderCB, gbc_chckbxNewCheckBox);

			rowHeaderCB = new JCheckBox(msg.getString("RowHeaderCB"), true);
			rowHeaderCB.addActionListener(HexEditorPanel.this);
			GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
			gbc_chckbxNewCheckBox_1.anchor = GridBagConstraints.WEST;
			gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxNewCheckBox_1.gridx = 3;
			gbc_chckbxNewCheckBox_1.gridy = 0;
			// contentPanel.add(rowHeaderCB, gbc_chckbxNewCheckBox_1);

			showGridCB = new JCheckBox(msg.getString("GridLinesCB"), false);
			showGridCB.addActionListener(HexEditorPanel.this);
			GridBagConstraints gbc_chckbxNewCheckBox_2 = new GridBagConstraints();
			gbc_chckbxNewCheckBox_2.anchor = GridBagConstraints.WEST;
			gbc_chckbxNewCheckBox_2.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxNewCheckBox_2.gridx = 1;
			gbc_chckbxNewCheckBox_2.gridy = 1;
			contentPanel.add(showGridCB, gbc_chckbxNewCheckBox_2);

			altRowBGCB = new JCheckBox(msg.getString("AlternateRowBG"), false);
			altRowBGCB.addActionListener(HexEditorPanel.this);
			GridBagConstraints gbc_chckbxNewCheckBox_3 = new GridBagConstraints();
			gbc_chckbxNewCheckBox_3.anchor = GridBagConstraints.WEST;
			gbc_chckbxNewCheckBox_3.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxNewCheckBox_3.gridx = 3;
			gbc_chckbxNewCheckBox_3.gridy = 1;
			contentPanel.add(altRowBGCB, gbc_chckbxNewCheckBox_3);

			altColBGCB = new JCheckBox(msg.getString("AlternateColBG"), false);
			altColBGCB.addActionListener(HexEditorPanel.this);
			GridBagConstraints gbc_chckbxNewCheckBox_4 = new GridBagConstraints();
			gbc_chckbxNewCheckBox_4.anchor = GridBagConstraints.WEST;
			gbc_chckbxNewCheckBox_4.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxNewCheckBox_4.gridx = 1;
			gbc_chckbxNewCheckBox_4.gridy = 2;
			contentPanel.add(altColBGCB, gbc_chckbxNewCheckBox_4);

			lowBytePaddingCB = new JCheckBox(msg.getString("PadLowBytesCB"), true);
			lowBytePaddingCB.addActionListener(HexEditorPanel.this);
			GridBagConstraints gbc_chckbxNewCheckBox_5 = new GridBagConstraints();
			gbc_chckbxNewCheckBox_5.anchor = GridBagConstraints.WEST;
			gbc_chckbxNewCheckBox_5.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxNewCheckBox_5.gridx = 3;
			gbc_chckbxNewCheckBox_5.gridy = 2;
			contentPanel.add(lowBytePaddingCB, gbc_chckbxNewCheckBox_5);

			highlightAsciiSelCB = new JCheckBox(msg.getString("HighlightAsciiSel"), true);
			highlightAsciiSelCB.addActionListener(HexEditorPanel.this);
			GridBagConstraints gbc_chckbxNewCheckBox_6 = new GridBagConstraints();
			gbc_chckbxNewCheckBox_6.anchor = GridBagConstraints.WEST;
			gbc_chckbxNewCheckBox_6.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxNewCheckBox_6.gridx = 1;
			gbc_chckbxNewCheckBox_6.gridy = 3;
			// contentPanel.add(highlightAsciiSelCB, gbc_chckbxNewCheckBox_6);

			JLabel lblNewLabel = new JLabel("  Selection Color");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 4;
			// contentPanel.add(lblNewLabel, gbc_lblNewLabel);

			highlightAsciiSelCombo = new JComboBox<Color>();
			highlightAsciiSelCombo.setRenderer(new ColorCellRenderer());
			highlightAsciiSelCombo.addItem(new Color(255, 255, 192));
			highlightAsciiSelCombo.addItem(new Color(224, 224, 255));
			highlightAsciiSelCombo.addItem(new Color(224, 224, 224));
			highlightAsciiSelCombo.addActionListener(HexEditorPanel.this);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 3;
			gbc_comboBox.gridy = 4;
			// contentPanel.add(highlightAsciiSelCombo, gbc_comboBox);

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton cancelButton = new JButton("Close");
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ConfigPanel.this.dispose();

				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);

		}

		private void centerFrame() {

			Dimension windowSize = getSize();

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			Point centerPoint = ge.getCenterPoint();

			int dx = centerPoint.x - windowSize.width / 2;

			int dy = centerPoint.y - windowSize.height / 2;

			setLocation(dx, dy);
		}
	}

	/**
	 * Saves the currently selected bytes to the file.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class DumpAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			try {
				editor.copy();

				dumpToFile();

			} catch (Exception e1) {
				VPXLogger.updateError(e1);
				e1.printStackTrace();
			}

		}

		protected String getNameKey() {
			return "Action.Dump.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_SAVE_NAME;
		}

	}

	public String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException ex) {
				VPXLogger.updateError(ex);
				ex.printStackTrace();
			}
		}
		return result;
	}

	private class DumpAsAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			try {
				editor.copy();

				dumpToTextFile();

			} catch (Exception e1) {
				VPXLogger.updateError(e1);
				e1.printStackTrace();
			}

		}

		protected String getNameKey() {
			return "Action.DumpAs.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_SAVEAS_NAME;
		}

	}

	/**
	 * Copies the currently selected bytes to the clipboard.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class CopyAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			editor.copy();
		}

		protected String getNameKey() {
			return "Action.Copy.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_COPY_NAME;
		}

	}

	/**
	 * Moves the currently selected bytes to the clipboard, similar to a "cut"
	 * action in a text editor.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class CutAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			editor.cut();
		}

		protected String getNameKey() {
			return "Action.Cut.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_CUT_NAME;
		}

	}

	/**
	 * Deletes the currently selected bytes.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class DeleteAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			editor.delete();
		}

		protected String getNameKey() {
			return "Action.Delete.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_DELETE_EXIT_NAME;
		}

	}

	/**
	 * Action that prompts a user to open a file.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class SettingsAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			showSettingsDialog();
		}

		protected String getNameKey() {
			return "Action.Settings.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_SETTINGS_NAME;
		}

	}

	/**
	 * Action that prompts a user to open a file.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class OpenAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			doOpen();
		}

		protected String getNameKey() {
			return "Action.Open.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_OPEN_NAME;
		}

	}

	/**
	 * Pastes the current clipboard contents into the hex editor at the
	 * currently selected byte location.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class PasteAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			editor.paste();
		}

		protected String getNameKey() {
			return "Action.Paste.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_PASTE_NAME;
		}

	}

	/**
	 * Undoes the last "undo" operation.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class RedoAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			editor.redo();
		}

		protected String getNameKey() {
			return "Action.Redo.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_REDO_NAME;
		}

	}

	/**
	 * Takes back the last operation done.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class UndoAction extends ActionBase {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			editor.undo();
		}

		protected String getNameKey() {
			return "Action.Undo.Name";
		}

		protected String getIconKey() {
			return VPXConstants.Icons.ICON_UNDO_NAME;
		}

	}

	private long getDocLength() {

		long retValue = 0;

		if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX8) {

			retValue = ((Hex8) editor.getTable().getModel()).getByteCount();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX16) {

			retValue = ((Hex16) editor.getTable().getModel()).getByteCount();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX32) {

			retValue = ((Hex32) editor.getTable().getModel()).getByteCount();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX64) {

			retValue = ((Hex64) editor.getTable().getModel()).getByteCount();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.SINGNEDINT16) {

			retValue = ((SignedInt16) editor.getTable().getModel()).getByteCount();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.SINGNEDINT32) {

			retValue = ((SignedInt32) editor.getTable().getModel()).getByteCount();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.UNSINGNEDINT16) {

			retValue = ((UnSignedInt16) editor.getTable().getModel()).getByteCount();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.UNSINGNEDINT32) {

			retValue = ((UnSignedInt32) editor.getTable().getModel()).getByteCount();

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.UNSINGNEDFLOAT32) {

			retValue = ((Floating32) editor.getTable().getModel()).getByteCount();

		}

		return retValue;
	}

	private void setValue(String data, int row, int col) throws Exception {

		String d = data;// Long.toHexString(Long.decode(data)).toUpperCase();

		if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX8) {

			((Hex8) editor.getTable().getModel()).setValueAt(d, row, col);

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX16) {

			((Hex16) editor.getTable().getModel()).setValueAt(d, row, col);

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX32) {

			((Hex32) editor.getTable().getModel()).setValueAt(d, row, col);

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.HEX64) {

			((Hex64) editor.getTable().getModel()).setValueAt(d, row, col);

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.SINGNEDINT16) {

			((SignedInt16) editor.getTable().getModel()).setValueAt(d, row, col);

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.SINGNEDINT32) {

			((SignedInt32) editor.getTable().getModel()).setValueAt(d, row, col);

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.UNSINGNEDINT16) {

			((UnSignedInt16) editor.getTable().getModel()).setValueAt(d, row, col);

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.UNSINGNEDINT32) {

			((UnSignedInt32) editor.getTable().getModel()).setValueAt(d, row, col);

		} else if (cmbFormatBytes.getSelectedIndex() == HexEditor.UNSINGNEDFLOAT32) {

			((Floating32) editor.getTable().getModel()).setValueAt(d, row, col);

		}

	}

	public void doSetMemory(long address, String currentValue, String defaultSize) {

		memStFillWindow.setMode(VPX_MemorySetWindow.SETMEMORY);

		memStFillWindow.setAddress(address);

		memStFillWindow.setCurrentValue(currentValue);

		memStFillWindow.setTypeSize(cmbFormatBytes.getSelectedItem().toString());

		int rc = memStFillWindow.showFillMemoryWinow();

		if (rc == 1) {

			Point p = null;

			long start = editor.getHexEditorRowHeader().getRowHeaderModel().getStartAddress();

			long start1 = (VPXUtilities.getValue(memStFillWindow.getAddress()) == -1) ? 0
					: VPXUtilities.getValue(memStFillWindow.getAddress());

			int offset = (int) (start1 - start);

			p = editor.offsetToCell(offset);

			try {

				setValue(memStFillWindow.getData(), (int) p.getX(), (int) p.getY());
			} catch (Exception e) {
				VPXLogger.updateError(e);
				e.printStackTrace();

				JOptionPane.showMessageDialog(this, "Please enter valid value.", "Valid value",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	public void doFillMemory(long currAddress) {

		memStFillWindow.setMode(VPX_MemorySetWindow.FILLMEMORY);

		memStFillWindow.setStartAddr(editor.getHexEditorRowHeader().getRowHeaderModel().getStartAddress());

		memStFillWindow.setAddress(currAddress);

		memStFillWindow.setTotalLength(getDocLength());

		memStFillWindow.setTypeSize(cmbFormatBytes.getSelectedItem().toString());

		int rc = memStFillWindow.showFillMemoryWinow();

		if (rc == 1) {

			Point p = null;

			long start = editor.getHexEditorRowHeader().getRowHeaderModel().getStartAddress();

			long start1 = (VPXUtilities.getValue(memStFillWindow.getAddress()) == -1) ? 0
					: VPXUtilities.getValue(memStFillWindow.getAddress());

			int offset = (int) (start1 - start);

			long length = Long.parseLong(memStFillWindow.getLength());

			try {

				for (int i = offset; i < (offset + length); i++) {

					p = editor.offsetToCell(i);

					memoryWindow.setMemory(start1, editor.getCurrentModel(), (int) length,
							new BigInteger(memStFillWindow.getData().getBytes()).longValue());

					setValue(memStFillWindow.getData(), (int) p.getX(), (int) p.getY());

				}

			} catch (Exception e) {
				VPXLogger.updateError(e);
				JOptionPane.showMessageDialog(this, "Please enter valid value.", "Valid value",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	public void doOpenAction() {

		doOpen();
	}

	public void doDumpAction() {

		btnDumpAction.doClick();
	}

	public void doFormatSelectionAction(int index) {

		cmbFormatBytes.setSelectedIndex(index);
	}

	public void doCutAction() {

		btnCutAction.doClick();
	}

	public void doCopyAction() {

		btnCopyAction.doClick();
	}

	public void doPasteAction() {

		btnPasteAction.doClick();
	}

	public void doDeleteAction() {

		btnDeleteAction.doClick();
	}

	public void doUndoAction() {

		btnUndoAction.doClick();
	}

	public void doRedoAction() {

		btnRedoAction.doClick();
	}

	public void doSettingsAction() {

		btnSettingsAction.doClick();
	}

}
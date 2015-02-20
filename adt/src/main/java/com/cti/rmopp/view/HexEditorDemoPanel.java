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
package com.cti.rmopp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.cti.rmopp.controls.CCheckBox;
import com.cti.rmopp.controls.CComboBox;
import com.cti.rmopp.controls.CLabel;
import com.cti.rmopp.controls.CPanel;
import com.cti.rmopp.controls.CTextField;
import com.cti.rmopp.controls.CToolBar;
import com.cti.rmopp.controls.ComponentFactory;
import com.cti.rmopp.controls.hex.event.HexEditorEvent;
import com.cti.rmopp.controls.hex.event.HexEditorListener;
import com.cti.rmopp.controls.hex.event.SelectionChangedEvent;
import com.cti.rmopp.controls.hex.event.SelectionChangedListener;
import com.cti.rmopp.controls.hex.swing.HexEditor;




class HexEditorDemoPanel extends CPanel implements ActionListener,
							HexEditorListener, SelectionChangedListener {

	private static final long serialVersionUID = 1L;

	private HexEditor editor;
	
	private JFileChooser chooser;
	
	private CCheckBox colHeaderCB;
	
	private CCheckBox rowHeaderCB;
	
	private CCheckBox showGridCB;
	
	//private CComboBox lafCombo;
	
	private CCheckBox altRowBGCB;
	
	private CCheckBox altColBGCB;
	
	private CCheckBox highlightAsciiSelCB;
	
	private CComboBox highlightAsciiSelCombo;
	
	private CCheckBox lowBytePaddingCB;
	
	private CTextField infoField;
	
	private CTextField selField;
	
	private CTextField sizeField;

	private static final String MSG = "VPX_Dual_adt";
	
	private static final ResourceBundle msg = ResourceBundle.getBundle(MSG);

	/**
	 * Constructor.
	 */
	public HexEditorDemoPanel() {

		setLayout(new BorderLayout());
	
		CPanel temp =  ComponentFactory.createPanel(new BorderLayout());
		
		ConfigPanel configPanel = new ConfigPanel();
		
		temp.add(configPanel, BorderLayout.LINE_START);

		infoField = ComponentFactory.createTextField();

		infoField.setEditable(false);
		
		infoField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5, 5, 5, 5),
				infoField.getBorder()));

		selField =  ComponentFactory.createTextField(30);
		
		selField.setEditable(false);
		
		selField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5, 0, 5, 5),
				selField.getBorder()));

		sizeField =  ComponentFactory.createTextField(15);
		
		sizeField.setEditable(false);
		
		sizeField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5, 0, 5, 5),
				sizeField.getBorder()));

		CPanel temp2 = ComponentFactory.createPanel(new BorderLayout());
		
		CPanel temp3 = ComponentFactory.createPanel(new BorderLayout());
		
		temp2.add(infoField);
		
		temp3.add(selField, BorderLayout.LINE_START);
		
		temp3.add(sizeField, BorderLayout.LINE_END);
		
		temp2.add(temp3, BorderLayout.LINE_END);
		
		temp.add(temp2, BorderLayout.SOUTH);
		add(temp, BorderLayout.SOUTH);

	
		editor = new HexEditor();
	
		editor.addHexEditorListener(this);
		
		editor.addSelectionChangedListener(this);
		
		handleOpenFile("images\\copy.gif");
		
		add(editor);

	}


	/**
	 * Listens for events in this panel.
	 *
	 * @param e The event that occurred.
	 */
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source==colHeaderCB) {
			editor.setShowColumnHeader(colHeaderCB.isSelected());
		}
		else if (source==rowHeaderCB) {
			editor.setShowRowHeader(rowHeaderCB.isSelected());
		}
		else if (source==showGridCB) {
			editor.setShowGrid(showGridCB.isSelected());
		}		
		else if (source==altColBGCB) {
			editor.setAlternateColumnBG(altColBGCB.isSelected());
		}
		else if (source==altRowBGCB) {
			editor.setAlternateRowBG(altRowBGCB.isSelected());
		}
		else if (source==highlightAsciiSelCB) {
			editor.setHighlightSelectionInAsciiDump(
								highlightAsciiSelCB.isSelected());
		}
		else if (source==highlightAsciiSelCombo) {
			editor.setHighlightSelectionInAsciiDumpColor(
					(Color)highlightAsciiSelCombo.getSelectedItem());
		}
		else if (source==lowBytePaddingCB) {
		    editor.setPadLowBytes(lowBytePaddingCB.isSelected());
		}

	}

	/**
	 * Prompts the user for a file to open, and opens it.
	 */
	public void doOpen() {
		JFileChooser chooser = getFileChooser();
		int rc = chooser.showOpenDialog(this);
		if (rc==JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			handleOpenFile(file.getAbsolutePath());
		}
	}


	/**
	 * Returns the file chooser for opening files, lazily creating it if
	 * necessary.
	 *
	 * @return The file chooser.
	 */
	private JFileChooser getFileChooser() {
		if (chooser==null) {
			chooser = new JFileChooser();
		}
		return chooser;
	}


	private String getInfoString(String key, int offs, int param) {
		String text = msg.getString(key);
		text = MessageFormat.format(text,
				new Object[] { new Integer(offs), new Integer(param) });
		return text;
	}


	private String getInfoString(String key, int offs, int param1, int param2) {
		String text = msg.getString(key);
		text = MessageFormat.format(text,
				new Object[] { new Integer(offs), new Integer(param1),
								new Integer(param2) });
		return text;
	}

	private void handleOpenFile(String fileName) {
		// Check jar first to prevent Applet AccessControlException
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream in = cl.getResourceAsStream(fileName);
		try {
			if (in!=null) { // In a jar
				editor.open(new BufferedInputStream(in));
			}
			else { // e.g. in Eclipse debugger
				File file = new File(fileName);
				if (file.isFile()) { // e.g. in Eclipse debugger
					editor.open(fileName);
				}
				else { // In a jar
					throw new IOException("Resource not found: " + fileName);
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			String message = msg.getString("Error.Title");
			String title = msg.getString("Error.Desc");
			title = MessageFormat.format(title,
										new Object[] { ioe.toString() });
			JOptionPane.showMessageDialog(this, message, title,
										JOptionPane.ERROR_MESSAGE);
		}
	}


	/**
	 * Called when the bytes in the hex editor change.
	 *
	 * @param e The event.
	 */
	public void hexBytesChanged(HexEditorEvent e) {

		String text = null;

		if (e.isModification()) {
			text = getInfoString("InfoFieldModified",
									e.getOffset(), e.getAddedCount());
		}
		else {
			int added = e.getAddedCount();
			int removed = e.getRemovedCount();
			if (added>0 && removed==0) {
				text = getInfoString("InfoFieldAdded",
									e.getOffset(), e.getAddedCount());
			}
			else if (added==0 && removed>0) {
				text = getInfoString("InfoFieldRemoved",
									e.getOffset(), e.getRemovedCount());
			}
			else {
				text = getInfoString("InfoFieldBoth", e.getOffset(),
									e.getAddedCount(), e.getRemovedCount());
			}
		}
		infoField.setText(text);

		text = msg.getString("SizeField");
		text = MessageFormat.format(text,
				new Object[] { new Integer(e.getHexEditor().getByteCount()) });
		sizeField.setText(text);

	}

	/**
	 * Called when the selection changes in the hex editor.
	 *
	 * @param An object describing the selection.
	 */
	public void selectionChanged(SelectionChangedEvent e) {
		int offs = e.getNewSelecStart();
		int count = e.getNewSelecEnd() - offs + 1;
		String subject = count==1 ? " byte" : " bytes";
		selField.setText(count + subject + " selected at offset " + offs);
	}

	/**
	 * Renderer for JComboBox content lists containing colors.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private static class ColorCellRenderer extends DefaultListCellRenderer
											implements Icon {

		private static final long serialVersionUID = 1L;

		private Color c;

		public Component getListCellRendererComponent(JList list, Object value,
							int index, boolean selected, boolean hasFocus) {
			super.getListCellRendererComponent(list, null, index, selected,
												hasFocus);
			if (value instanceof Color) {
				c = (Color)value;
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
			g.drawRect(x,y, getIconWidth(), getIconHeight());
		}

	}


	private class ConfigPanel extends CPanel {

		private static final long serialVersionUID = 1L;

		public ConfigPanel() {

			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			JPanel temp = ComponentFactory.createPanel();
			temp.setLayout(new GridLayout(3,3, 5,5));
	
			colHeaderCB = ComponentFactory.createCheckBox(msg.getString("ColHeaderCB"), true);
			colHeaderCB.addActionListener(HexEditorDemoPanel.this);
			temp.add(colHeaderCB);

			rowHeaderCB = ComponentFactory.createCheckBox(msg.getString("RowHeaderCB"), true);
			rowHeaderCB.addActionListener(HexEditorDemoPanel.this);
			temp.add(rowHeaderCB);

			showGridCB = ComponentFactory.createCheckBox(msg.getString("GridLinesCB"), false);
			showGridCB.addActionListener(HexEditorDemoPanel.this);
			temp.add(showGridCB);

			altRowBGCB = ComponentFactory.createCheckBox(msg.getString("AlternateRowBG"), false);
			altRowBGCB.addActionListener(HexEditorDemoPanel.this);
			temp.add(altRowBGCB);

			altColBGCB = ComponentFactory.createCheckBox(msg.getString("AlternateColBG"), false);
			altColBGCB.addActionListener(HexEditorDemoPanel.this);
			temp.add(altColBGCB);

            lowBytePaddingCB = ComponentFactory.createCheckBox(msg.getString("PadLowBytesCB"), true);
            lowBytePaddingCB.addActionListener(HexEditorDemoPanel.this);
            temp.add(lowBytePaddingCB);

			highlightAsciiSelCB = ComponentFactory.createCheckBox(
									msg.getString("HighlightAsciiSel"), true);
			highlightAsciiSelCB.addActionListener(HexEditorDemoPanel.this);
			temp.add(highlightAsciiSelCB);

			highlightAsciiSelCombo = ComponentFactory.createComboBox();
			highlightAsciiSelCombo.setRenderer(new ColorCellRenderer());
			highlightAsciiSelCombo.addItem(new Color(255,255,192));
			highlightAsciiSelCombo.addItem(new Color(224,224,255));
			highlightAsciiSelCombo.addItem(new Color(224,224,224));
			highlightAsciiSelCombo.addActionListener(HexEditorDemoPanel.this);
			CPanel temp2 = ComponentFactory.createPanel(new BorderLayout());
			String text = msg.getString("HighlightColor");
			temp2.add(new CLabel(text), BorderLayout.LINE_START);
			temp2.add(highlightAsciiSelCombo);
			temp.add(temp2);

			add(temp);

			/*
			temp = ComponentFactory.createPanel(new BorderLayout());
			temp.add(new CLabel(msg.getString("LafLabel")),
									BorderLayout.LINE_START);
			
			
			lafCombo = ComponentFactory.createComboBox();
			lafCombo.addItem("System");
			lafCombo.addItem("Metal");
			lafCombo.addItem("Motif");
			if (isNimbusSupported()) {
				lafCombo.addItem("Nimbus");
			}
			lafCombo.addActionListener(HexEditorDemoPanel.this);
			temp.add(lafCombo);
			*/
			temp2 = ComponentFactory.createPanel(new BorderLayout());
			temp2.add(temp, BorderLayout.LINE_START);
			add(temp2, BorderLayout.SOUTH);

		}

	}
}
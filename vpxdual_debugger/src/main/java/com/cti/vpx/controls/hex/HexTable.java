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

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

import com.cti.vpx.controls.hex.HexEditorRowHeader.RowHeaderListModel;
import com.cti.vpx.controls.hex.groupmodel.Floating32;
import com.cti.vpx.controls.hex.groupmodel.Hex16;
import com.cti.vpx.controls.hex.groupmodel.Hex32;
import com.cti.vpx.controls.hex.groupmodel.Hex64;
import com.cti.vpx.controls.hex.groupmodel.Hex8;
import com.cti.vpx.controls.hex.groupmodel.SignedInt16;
import com.cti.vpx.controls.hex.groupmodel.SignedInt32;
import com.cti.vpx.controls.hex.groupmodel.UnSignedInt16;
import com.cti.vpx.controls.hex.groupmodel.UnSignedInt32;
import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXUtilities;

/**
 * The table displaying the hex content of a file. This is the meat of the hex
 * viewer.
 *
 *
 * @author Robert Futrell
 * @version 1.0
 */
class HexTable extends JTable {

	private static final long serialVersionUID = 1L;

	private final HexEditor hexEditor;
	private Hex8 model;
	int leadSelectionIndex;
	int anchorSelectionIndex;
	private int currRow;
	private long currAddress;
	private int currCol;

	private ResourceBundle rBundle = VPXUtilities.getResourceBundle();

	private final JPopupMenu vpx_Hex_ContextMenu = new JPopupMenu();

	private static final Color ANTERNATING_CELL_COLOR = new Color(224, 224, 255);

	private HexEditorPanel parent;

	private JCheckBoxMenuItem vpx_Hex_Cxt_GrpAs_Hex8;

	private JCheckBoxMenuItem vpx_Hex_Cxt_GrpAs_Hex16;

	private JCheckBoxMenuItem vpx_Hex_Cxt_GrpAs_Hex32;

	private JCheckBoxMenuItem vpx_Hex_Cxt_GrpAs_Hex64;

	private JCheckBoxMenuItem vpx_Hex_Cxt_GrpAs_SignedInt16;

	private JCheckBoxMenuItem vpx_Hex_Cxt_GrpAs_SignedInt32;

	private JCheckBoxMenuItem vpx_Hex_Cxt_GrpAs_UnSignedInt16;

	private JCheckBoxMenuItem vpx_Hex_Cxt_GrpAs_UnSignedInt32;

	private JCheckBoxMenuItem vpx_Hex_Cxt_GrpAs_Floating32;

	private JMenuItem vpx_Hex_Cxt_FillMemory;

	private JMenuItem vpx_Hex_Cxt_SetMemory;

	/**
	 * Creates a new table to display hex data.
	 *
	 * @param hexEditor
	 *            The parent hex editor component.
	 * @param model
	 *            The table model to use.
	 */

	public HexTable(HexEditorPanel parnt, HexEditor hexEditor, Hex8 model) {

		this(hexEditor, model);

		this.parent = parnt;
	}

	public HexTable(HexEditor hexEditor, Hex8 model) {

		super(model);

		this.hexEditor = hexEditor;

		this.model = model;

		enableEvents(AWTEvent.KEY_EVENT_MASK);

		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		setFont(new Font("Monospaced", Font.PLAIN, 14));

		// setRowHeight(28);

		setCellSelectionEnabled(true);

		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setRowSelectionAllowed(true);

		// setDefaultEditor(Object.class, new CellEditor());

		 setDefaultRenderer(Object.class, new CellRenderer());

		getTableHeader().setReorderingAllowed(false);

		setShowGrid(false);

		FontMetrics fm = getFontMetrics(getFont());

		Font headerFont = UIManager.getFont("TableHeader.font");

		FontMetrics headerFM = hexEditor.getFontMetrics(headerFont);

		int w = fm.stringWidth("wwww"); // cell contents, 0-255

		w = Math.max(w, headerFM.stringWidth("+999"));

		for (int i = 0; i < getColumnCount(); i++) {

			TableColumn column = getColumnModel().getColumn(i);

			if (i < 16) {

				column.setPreferredWidth(w);
			} else {

				column.setPreferredWidth(HexEditor.DUMP_COLUMN_WIDTH);
			}
		}

		setPreferredScrollableViewportSize(new Dimension(w * 16 + HexEditor.DUMP_COLUMN_WIDTH, 25 * getRowHeight()));

		anchorSelectionIndex = leadSelectionIndex = 0;

		createPopupMenu();

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent me) {

				JTable table = (JTable) me.getSource();

				Point p = me.getPoint();

				currRow = table.rowAtPoint(p);

				String addr = hexEditor.getHexEditorRowHeader().getRowHeaderModel().getElementAt(currRow).toString();

				currAddress = (VPXUtilities.getValue(addr) == -1) ? 0 : VPXUtilities.getValue(addr);

				currCol = table.columnAtPoint(p);

				if (me.isPopupTrigger()) { // if the event shows the menu

					if (hexEditor.getCurrentModel() == HexEditor.UNSINGNEDFLOAT32) {
						
						vpx_Hex_Cxt_FillMemory.setEnabled(false);
						vpx_Hex_Cxt_SetMemory.setEnabled(false);
						
					} else {

						vpx_Hex_Cxt_FillMemory.setEnabled(true);
						vpx_Hex_Cxt_SetMemory.setEnabled(true);

					}

					vpx_Hex_ContextMenu.show(table, me.getX(), me.getY()); // and
																			// show
																			// the
																			// menu
				} /*
					 * else if (me.getClickCount() == 2) {
					 * 
					 * parent.doSetMemory(currAddress + currCol,
					 * getValueAt(currRow, currCol).toString(), "");
					 * 
					 * }
					 */
			}
		});
	}

	public HexEditor getHexEditor() {
		return this.hexEditor;
	}

	/**
	 * Registers a prospect who is interested when the text selection from the
	 * hex editor becomes changed.
	 * 
	 * @param l
	 *            The concerning listener.
	 * @see #removeSelectionChangedListener(SelectionChangedListener)
	 */
	public void addSelectionChangedListener(SelectionChangedListener l) {
		listenerList.add(SelectionChangedListener.class, l);
	}

	/**
	 * Returns the column for the cell containing data that is the closest to
	 * the specified cell. This is used when, for example, the user clicks on an
	 * "empty" cell in the last row of the table.
	 * 
	 * @param row
	 *            The row of the cell clicked on.
	 * @param col
	 *            The column of the cell clicked on.
	 * @return The column of the closest cell containing data.
	 */
	private int adjustColumn(int row, int col) {
		if (col < 0) {
			return 0;
		}
		if (row == getRowCount() - 1) {

			int lastRowCount = model.getByteCount() % 16;

			if (lastRowCount == 0) {
				lastRowCount = 16;
			}

			if (lastRowCount < 17) { // Last row's not entirely full
				return col;// Math.min(col, (model.getByteCount() % 16) - 1);
			}
		}

		return Math.min(col, getColumnCount() - 1);
	}

	private int getCurrentColCount(int row, int col) {

		int i = -1;

		int cols = 0;

		TableModel model = getModel();

		if (model instanceof Hex8) {
			cols = 15;

		} else if (model instanceof Hex16) {

			if (row == (getRowCount() - 1)) {

				i = (((Hex16) model).getBytes().getSize() - (row * (((Hex16) model).getBytesPerRow() * 2)));

				if (i > 0)
					cols = i / 2;
			} else {
				cols = 15;
			}

		} else if (model instanceof Hex32) {

			if (row == (getRowCount() - 1)) {

				i = (((Hex32) model).getBytes().getSize() - (row * (((Hex32) model).getBytesPerRow() * 4)));

				if (i > 0)
					cols = i / 4;
			} else {
				cols = 15;
			}

		} else if (model instanceof Hex64) {

			if (row == (getRowCount() - 1)) {

				i = (((Hex64) model).getBytes().getSize() - (row * (((Hex64) model).getBytesPerRow() * 8)));

				if (i > 0)
					cols = i / 8;
			} else {
				cols = 15;
			}

		} else if (model instanceof SignedInt16) {

			if (row == (getRowCount() - 1)) {

				i = (((SignedInt16) model).getBytes().getSize() - (row * (((SignedInt16) model).getBytesPerRow() * 2)));

				if (i > 0)
					cols = i / 2;
			} else {
				cols = 15;
			}

		} else if (model instanceof SignedInt32) {

			if (row == (getRowCount() - 1)) {

				i = (((SignedInt32) model).getBytes().getSize() - (row * (((SignedInt32) model).getBytesPerRow() * 4)));

				if (i > 0)
					cols = i / 4;
			} else {
				cols = 15;
			}

		} else if (model instanceof UnSignedInt16) {

			if (row == (getRowCount() - 1)) {

				i = (((UnSignedInt16) model).getBytes().getSize()
						- (row * (((UnSignedInt16) model).getBytesPerRow() * 2)));

				if (i > 0)
					cols = i / 2;
			} else {
				cols = 15;
			}

		} else if (model instanceof UnSignedInt32) {

			if (row == (getRowCount() - 1)) {

				i = (((UnSignedInt32) model).getBytes().getSize()
						- (row * (((UnSignedInt32) model).getBytesPerRow() * 4)));

				if (i > 0)
					cols = i / 4;
			} else {
				cols = 15;
			}

		} else if (model instanceof Floating32) {

			if (row == (getRowCount() - 1)) {

				i = (((Floating32) model).getBytes().getSize() - (row * (((Floating32) model).getBytesPerRow() * 4)));

				if (i > 0)
					cols = i / 4;
			} else {
				cols = 15;
			}

		}

		return cols;
	}

	private void createPopupMenu() {

		JMenuItem vpx_Hex_Cxt_LoadMemory = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Dump.Load"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Hex_Cxt_LoadMemory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.doOpenAction();

			}
		});

		vpx_Hex_Cxt_FillMemory = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Dump.Fill"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Hex_Cxt_FillMemory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.doFillMemory();

			}
		});

		vpx_Hex_Cxt_SetMemory = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Dump.Set"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Hex_Cxt_SetMemory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doSetMemory(currAddress + currCol, getValueAt(currRow, currCol).toString(), "");

			}
		});

		JMenuItem vpx_Hex_Cxt_Save = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Dump.Name"),
				VPXConstants.Icons.ICON_SAVE);

		vpx_Hex_Cxt_Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doDumpAction();

			}
		});

		JMenu vpx_Hex_Cxt_GroupMenu = VPXComponentFactory.createJMenu(rBundle.getString("Action.Group.GroupAs"));

		vpx_Hex_Cxt_GrpAs_Hex8 = VPXComponentFactory.createJCheckBoxMenuItem(rBundle.getString("Action.Group.Hex8"));

		vpx_Hex_Cxt_GrpAs_Hex8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));

		vpx_Hex_Cxt_GrpAs_Hex8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doFormatSelectionAction(HexEditor.HEX8);

				setCheckedSelectedFormat(HexEditor.HEX8);

			}
		});

		vpx_Hex_Cxt_GrpAs_Hex16 = VPXComponentFactory.createJCheckBoxMenuItem(rBundle.getString("Action.Group.Hex16"));

		vpx_Hex_Cxt_GrpAs_Hex16.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));

		vpx_Hex_Cxt_GrpAs_Hex16.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doFormatSelectionAction(HexEditor.HEX16);

				setCheckedSelectedFormat(HexEditor.HEX16);

			}
		});

		vpx_Hex_Cxt_GrpAs_Hex32 = VPXComponentFactory.createJCheckBoxMenuItem(rBundle.getString("Action.Group.Hex32"));

		vpx_Hex_Cxt_GrpAs_Hex32.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));

		vpx_Hex_Cxt_GrpAs_Hex32.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doFormatSelectionAction(HexEditor.HEX32);

				setCheckedSelectedFormat(HexEditor.HEX32);

			}
		});

		vpx_Hex_Cxt_GrpAs_Hex64 = VPXComponentFactory.createJCheckBoxMenuItem(rBundle.getString("Action.Group.Hex64"));

		vpx_Hex_Cxt_GrpAs_Hex64.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.CTRL_MASK));

		vpx_Hex_Cxt_GrpAs_Hex64.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doFormatSelectionAction(HexEditor.HEX64);

				setCheckedSelectedFormat(HexEditor.HEX64);

			}
		});

		vpx_Hex_Cxt_GrpAs_SignedInt16 = VPXComponentFactory
				.createJCheckBoxMenuItem(rBundle.getString("Action.Group.SignedInt16"));

		vpx_Hex_Cxt_GrpAs_SignedInt16.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.CTRL_MASK));

		vpx_Hex_Cxt_GrpAs_SignedInt16.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doFormatSelectionAction(HexEditor.SINGNEDINT16);

				setCheckedSelectedFormat(HexEditor.SINGNEDINT16);

			}
		});

		vpx_Hex_Cxt_GrpAs_SignedInt32 = VPXComponentFactory
				.createJCheckBoxMenuItem(rBundle.getString("Action.Group.SignedInt32"));

		vpx_Hex_Cxt_GrpAs_SignedInt32.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.CTRL_MASK));

		vpx_Hex_Cxt_GrpAs_SignedInt32.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doFormatSelectionAction(HexEditor.SINGNEDINT32);

				setCheckedSelectedFormat(HexEditor.SINGNEDINT32);

			}
		});

		vpx_Hex_Cxt_GrpAs_UnSignedInt16 = VPXComponentFactory
				.createJCheckBoxMenuItem(rBundle.getString("Action.Group.UnSignedInt16"));

		vpx_Hex_Cxt_GrpAs_UnSignedInt16.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.CTRL_MASK));

		vpx_Hex_Cxt_GrpAs_UnSignedInt16.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doFormatSelectionAction(HexEditor.UNSINGNEDINT16);

				setCheckedSelectedFormat(HexEditor.UNSINGNEDINT16);

			}
		});

		vpx_Hex_Cxt_GrpAs_UnSignedInt32 = VPXComponentFactory
				.createJCheckBoxMenuItem(rBundle.getString("Action.Group.UnSignedInt32"));

		vpx_Hex_Cxt_GrpAs_UnSignedInt32.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, ActionEvent.CTRL_MASK));

		vpx_Hex_Cxt_GrpAs_UnSignedInt32.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doFormatSelectionAction(HexEditor.UNSINGNEDINT32);

				setCheckedSelectedFormat(HexEditor.UNSINGNEDINT32);

			}
		});

		vpx_Hex_Cxt_GrpAs_Floating32 = VPXComponentFactory
				.createJCheckBoxMenuItem(rBundle.getString("Action.Group.Floating32"));

		vpx_Hex_Cxt_GrpAs_Floating32.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, ActionEvent.CTRL_MASK));

		vpx_Hex_Cxt_GrpAs_Floating32.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doFormatSelectionAction(HexEditor.UNSINGNEDFLOAT32);

				setCheckedSelectedFormat(HexEditor.UNSINGNEDFLOAT32);

			}
		});

		setCheckedSelectedFormat(HexEditor.HEX8);

		vpx_Hex_Cxt_GroupMenu.add(vpx_Hex_Cxt_GrpAs_Hex8);

		vpx_Hex_Cxt_GroupMenu.add(vpx_Hex_Cxt_GrpAs_Hex16);

		vpx_Hex_Cxt_GroupMenu.add(vpx_Hex_Cxt_GrpAs_Hex32);

		vpx_Hex_Cxt_GroupMenu.add(vpx_Hex_Cxt_GrpAs_Hex64);

		vpx_Hex_Cxt_GroupMenu.add(vpx_Hex_Cxt_GrpAs_SignedInt16);

		vpx_Hex_Cxt_GroupMenu.add(vpx_Hex_Cxt_GrpAs_SignedInt32);

		vpx_Hex_Cxt_GroupMenu.add(vpx_Hex_Cxt_GrpAs_UnSignedInt16);

		vpx_Hex_Cxt_GroupMenu.add(vpx_Hex_Cxt_GrpAs_UnSignedInt32);

		vpx_Hex_Cxt_GroupMenu.add(vpx_Hex_Cxt_GrpAs_Floating32);

		JMenuItem vpx_Hex_Cxt_Cut = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Cut.Name"),
				VPXConstants.Icons.ICON_EMPTY);

		vpx_Hex_Cxt_Cut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doCutAction();

			}
		});

		JMenuItem vpx_Hex_Cxt_Copy = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Copy.Name"),
				VPXConstants.Icons.ICON_COPY);

		vpx_Hex_Cxt_Copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doCopyAction();

			}
		});

		JMenuItem vpx_Hex_Cxt_Paste = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Paste.Name"),
				VPXConstants.Icons.ICON_PASTE);

		vpx_Hex_Cxt_Paste.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doPasteAction();

			}
		});

		JMenuItem vpx_Hex_Cxt_Delete = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Delete.Name"),
				VPXConstants.Icons.ICON_DELETE_EXIT);

		vpx_Hex_Cxt_Delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doDeleteAction();

			}
		});

		JMenuItem vpx_Hex_Cxt_Undo = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Undo.Name"),
				VPXConstants.Icons.ICON_UNDO);

		vpx_Hex_Cxt_Undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doUndoAction();

			}
		});

		JMenuItem vpx_Hex_Cxt_Redo = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Redo.Name"),
				VPXConstants.Icons.ICON_REDO);

		vpx_Hex_Cxt_Redo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doRedoAction();

			}
		});
		JMenuItem vpx_Hex_Cxt_Settings = VPXComponentFactory.createJMenuItem(rBundle.getString("Action.Settings.Name"),
				VPXConstants.Icons.ICON_SETTINGS);

		vpx_Hex_Cxt_Settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.doSettingsAction();

			}
		});

		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_LoadMemory);

		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_FillMemory);

		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_SetMemory);

		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_Save);

		vpx_Hex_ContextMenu.add(VPXComponentFactory.createJSeparator());

		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_GroupMenu);

		vpx_Hex_ContextMenu.add(VPXComponentFactory.createJSeparator());

		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_Cut);

		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_Copy);

		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_Paste);

		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_Delete);

		vpx_Hex_ContextMenu.add(VPXComponentFactory.createJSeparator());
		/*
		 * vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_Redo);
		 * 
		 * vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_Undo);
		 * 
		 * vpx_Hex_ContextMenu.add(VPXComponentFactory.createJSeparator());
		 */
		vpx_Hex_ContextMenu.add(vpx_Hex_Cxt_Settings);

	}

	public void setCheckedSelectedFormat(int format) {

		if (format == HexEditor.HEX8)
			vpx_Hex_Cxt_GrpAs_Hex8.setSelected(true);
		else
			vpx_Hex_Cxt_GrpAs_Hex8.setSelected(false);

		if (format == HexEditor.HEX16)
			vpx_Hex_Cxt_GrpAs_Hex16.setSelected(true);
		else
			vpx_Hex_Cxt_GrpAs_Hex16.setSelected(false);

		if (format == HexEditor.HEX32)
			vpx_Hex_Cxt_GrpAs_Hex32.setSelected(true);
		else
			vpx_Hex_Cxt_GrpAs_Hex32.setSelected(false);

		if (format == HexEditor.HEX64)
			vpx_Hex_Cxt_GrpAs_Hex64.setSelected(true);
		else
			vpx_Hex_Cxt_GrpAs_Hex64.setSelected(false);

		if (format == HexEditor.SINGNEDINT16)
			vpx_Hex_Cxt_GrpAs_SignedInt16.setSelected(true);
		else
			vpx_Hex_Cxt_GrpAs_SignedInt16.setSelected(false);

		if (format == HexEditor.SINGNEDINT32)
			vpx_Hex_Cxt_GrpAs_SignedInt32.setSelected(true);
		else
			vpx_Hex_Cxt_GrpAs_SignedInt32.setSelected(false);

		if (format == HexEditor.UNSINGNEDINT16)
			vpx_Hex_Cxt_GrpAs_UnSignedInt16.setSelected(true);
		else
			vpx_Hex_Cxt_GrpAs_UnSignedInt16.setSelected(false);

		if (format == HexEditor.UNSINGNEDINT32)
			vpx_Hex_Cxt_GrpAs_UnSignedInt32.setSelected(true);
		else
			vpx_Hex_Cxt_GrpAs_UnSignedInt32.setSelected(false);

		if (format == HexEditor.UNSINGNEDFLOAT32)
			vpx_Hex_Cxt_GrpAs_Floating32.setSelected(true);
		else
			vpx_Hex_Cxt_GrpAs_Floating32.setSelected(false);
	}

	/**
	 * Returns the offset into the bytes being edited represented at the
	 * specified cell in the table, if any.
	 *
	 * @param row
	 *            The row in the table.
	 * @param col
	 *            The column in the table.
	 * @return The offset into the byte array, or <code>-1</code> if the cell
	 *         does not represent part of the byte array (such as the tailing
	 *         "ascii dump" column's cells).
	 * @see #offsetToCell(int)
	 */
	public int cellToOffset(int row, int col) {
		// Check row and column individually to prevent them being invalid
		// values but still pointing to a valid offset in the buffer.
		if (row < 0 || row >= getRowCount() || col < 0 || col > getCurrentColCount(row, col)) { // Don't
			// include
			// last
			// column
			// (ascii
			// dump)

			return -1;
		}

		int offs = row * 16 + col;
		return (offs >= 0 && offs < model.getByteCount()) ? offs : -1;
	}

	/**
	 * Changes the selected byte range.
	 *
	 * @param row
	 * @param col
	 * @param toggle
	 * @param extend
	 * @see #changeSelectionByOffset(int, boolean)
	 * @see #setSelectedRows(int, int)
	 * @see #setSelectionByOffsets(int, int)
	 */
	public void changeSelection(int row, int col, boolean toggle, boolean extend) {

		// remind previous selection range
		int prevSmallest = getSmallestSelectionIndex();
		int prevLargest = getLargestSelectionIndex();

		// Don't allow the user to select the "ascii dump" or any
		// empty cells in the last row of the table.
		col = adjustColumn(row, col);
		if (row < 0) {
			row = 0;
		}

		// Clear the old selection (may not be necessary).
		repaintSelection();

		if (extend) {
			leadSelectionIndex = cellToOffset(row, col);
		} else {
			anchorSelectionIndex = leadSelectionIndex = cellToOffset(row, col);
		}

		// Scroll after changing the selection as blit scrolling is
		// immediate, so that if we cause the repaint after the scroll we
		// end up painting everything!
		if (getAutoscrolls()) {
			ensureCellIsVisible(row, col);
		}

		// Draw the new selection.
		repaintSelection();

		fireSelectionChangedEvent(prevSmallest, prevLargest);

	}

	/**
	 * Changes the selection by an offset into the bytes being edited.
	 *
	 * @param offset
	 * @param extend
	 * @see #changeSelection(int, int, boolean, boolean)
	 * @see #setSelectedRows(int, int)
	 * @see #setSelectionByOffsets(int, int)
	 */
	public void changeSelectionByOffset(int offset, boolean extend) {
		offset = Math.max(0, offset);
		offset = Math.min(offset, model.getByteCount() - 1);
		int row = offset / 16;
		int col = offset % 16;
		changeSelection(row, col, false, extend);
	}

	/**
	 * Clears the selection. The "lead" of the selection is set back to the
	 * position of the "anchor."
	 */
	public void clearSelection() {
		if (anchorSelectionIndex > -1) { // Always true unless an error
			leadSelectionIndex = anchorSelectionIndex;
		} else {
			anchorSelectionIndex = leadSelectionIndex = 0;
		}
		repaintSelection();
	}

	/**
	 * Ensures the specified cell is visible.
	 *
	 * @param row
	 *            The row of the cell.
	 * @param col
	 *            The column of the cell.
	 */
	private void ensureCellIsVisible(int row, int col) {
		Rectangle cellRect = getCellRect(row, col, false);
		if (cellRect != null) {
			scrollRectToVisible(cellRect);
		}
	}

	/**
	 * Notifies any listeners that the selection has changed.
	 * 
	 * @see #addSelectionChangedListener(SelectionChangedListener)
	 * @see #removeSelectionChangedListener(SelectionChangedListener)
	 * @param e
	 *            Contains proper information.
	 */
	private void fireSelectionChangedEvent(int prevSmallest, int prevLargest) {

		// Lazily create the event
		SelectionChangedEvent e = null;

		// Guaranteed non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process last to first
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SelectionChangedListener.class) {
				if (e == null) {
					e = new SelectionChangedEvent(this, prevSmallest, prevLargest, getSmallestSelectionIndex(),
							getLargestSelectionIndex());
				}
				((SelectionChangedListener) listeners[i + 1]).selectionChanged(e);
			}
		}

	}

	/**
	 * Returns the byte at the specified offset.
	 *
	 * @param offset
	 *            The offset.
	 * @return The byte.
	 */
	public byte getByte(int offset) {
		return model.getByte(offset);
	}

	/**
	 * Returns the number of bytes being edited.
	 *
	 * @return The number of bytes.
	 */
	public int getByteCount() {
		return model.getByteCount();
	}

	/**
	 * Returns the largest selection index.
	 *
	 * @return The largest selection index.
	 * @see #getSmallestSelectionIndex()
	 */
	public int getLargestSelectionIndex() {
		int index = Math.max(leadSelectionIndex, anchorSelectionIndex);
		return Math.max(index, 0); // Don't return -1 if table is empty
	}

	/**
	 * Returns the smallest selection index.
	 *
	 * @return The smallest selection index.
	 * @see #getLargestSelectionIndex()
	 */
	public int getSmallestSelectionIndex() {
		int index = Math.min(leadSelectionIndex, anchorSelectionIndex);
		return Math.max(index, 0); // Don't return -1 if table is empty
	}

	public boolean isCellEditable(int row, int col) {

		if (hexEditor.getCurrentModel() == HexEditor.UNSINGNEDFLOAT32 || hexEditor.getCurrentModel() == HexEditor.HEX64) {
			return false;
		} else {
			return cellToOffset(row, col) > -1;
		}
	}

	public boolean isCellSelected(int row, int col) {
		int offset = cellToOffset(row, col);
		if (offset == -1) { 
			return false;
		}
		int start = getSmallestSelectionIndex();
		int end = getLargestSelectionIndex();
		return offset >= start && offset <= end;
	}

	/**
	 * Returns the cell representing the specified offset into the hex document.
	 *
	 * @param offset
	 *            The offset into the document.
	 * @return The cell, in the form <code>(row, col)</code>. If the specified
	 *         offset is invalid, <code>(-1, -1)</code> is returned.
	 * @see #cellToOffset(int, int)
	 */
	public Point offsetToCell(int offset) {
		if (offset < 0 || offset >= model.getByteCount()) {
			return new Point(-1, -1);
		}
		int row = offset / 16;
		int col = offset % 16;
		return new Point(row, col);
	}

	/**
	 * Sets the contents in the hex editor to the contents of the specified
	 * file.
	 *
	 * @param fileName
	 *            The name of the file to open.
	 * @throws IOException
	 *             If an IO error occurs.
	 */
	public void open(String fileName) throws IOException {

		TableModel model = getModel();

		if (model instanceof Hex8) {

			((Hex8) model).setBytes(fileName);

		} else if (model instanceof Hex16) {
			((Hex16) model).setBytes(fileName);

		} else if (model instanceof Hex32) {
			((Hex32) model).setBytes(fileName);

		} else if (model instanceof Hex64) {

			((Hex64) model).setBytes(fileName);

		} else if (model instanceof SignedInt16) {

			((SignedInt16) model).setBytes(fileName);

		} else if (model instanceof SignedInt32) {

			((SignedInt32) model).setBytes(fileName);

		} else if (model instanceof UnSignedInt16) {
			((UnSignedInt16) model).setBytes(fileName);

		} else if (model instanceof UnSignedInt32) {

			((UnSignedInt32) model).setBytes(fileName);

		} else if (model instanceof Floating32) {

			((Floating32) model).setBytes(fileName);

		}

		// model.setBytes(fileName); // Fires tableDataChanged event
	}

	public void open(byte[] bytes) throws IOException {

		TableModel model = getModel();

		if (model instanceof Hex8) {

			((Hex8) model).setBytes(bytes);

		} else if (model instanceof Hex16) {
			((Hex16) model).setBytes(bytes);

		} else if (model instanceof Hex32) {
			((Hex32) model).setBytes(bytes);

		} else if (model instanceof Hex64) {

			((Hex64) model).setBytes(bytes);

		} else if (model instanceof SignedInt16) {

			((SignedInt16) model).setBytes(bytes);

		} else if (model instanceof SignedInt32) {

			((SignedInt32) model).setBytes(bytes);

		} else if (model instanceof UnSignedInt16) {
			((UnSignedInt16) model).setBytes(bytes);

		} else if (model instanceof UnSignedInt32) {

			((UnSignedInt32) model).setBytes(bytes);

		} else if (model instanceof Floating32) {

			((Floating32) model).setBytes(bytes);
		}

		// model.setBytes(bytes); // Fires tableDataChanged event
	}

	/**
	 * Sets the contents in the hex editor to the contents of the specified
	 * input stream.
	 *
	 * @param in
	 *            An input stream.
	 * @throws IOException
	 *             If an IO error occurs.
	 */
	public void open(InputStream in) throws IOException {
		model.setBytes(in);
	}

	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

		Object value = getValueAt(row, column);

		boolean isSelected = isCellSelected(row, column);

		boolean hasFocus = cellToOffset(row, column) == leadSelectionIndex;

		Component component = renderer.getTableCellRendererComponent(this, value, isSelected, hasFocus, row, column);

		// component.setForeground(Color.RED);

		int rendererWidth = component.getPreferredSize().width;

		TableColumn tableColumn = getColumnModel().getColumn(column);

		HexEditor scrollPane = (HexEditor) SwingUtilities.getAncestorOfClass(JScrollPane.class, HexTable.this);

		int cols = 0;

		int model = scrollPane.getCurrentModel();

		if (model == HexEditor.HEX8) {

			cols = column;
		}
		if (model == HexEditor.HEX16 || model == HexEditor.SINGNEDINT16 || model == HexEditor.UNSINGNEDINT16) {

			cols = column * 2;

		} else if (model == HexEditor.HEX32 || model == HexEditor.SINGNEDINT32 || model == HexEditor.UNSINGNEDINT32
				|| model == HexEditor.UNSINGNEDFLOAT32) {

			cols = column * 4;

		} else if (model == HexEditor.HEX64) {

			cols = column * 8;
		}

		HexEditorRowHeader hexRowHeader = scrollPane.getHexEditorRowHeader();

		if (hexRowHeader != null) {

			RowHeaderListModel c = hexRowHeader.getRowHeaderModel();

			if (c != null) {
				((JComponent) component).setToolTipText(String.format("<html>Address : %s<br>External : %s</html>",

				("0x" + String.format("%08x", (Long.decode(c.getElementAt(row).toString()) + cols)).toUpperCase()),
						value.toString().toUpperCase())); // For all format
			}
		}

		tableColumn.setPreferredWidth(
				Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

		return component;

	}

	protected void processKeyEvent(java.awt.event.KeyEvent e) {

		if (e.getID() == KeyEvent.KEY_PRESSED) {

			if (!e.isControlDown()) {

				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					boolean extend = e.isShiftDown();
					int offs = Math.max(leadSelectionIndex - 1, 0);
					changeSelectionByOffset(offs, extend);
					e.consume();
					break;
				case KeyEvent.VK_RIGHT:
					extend = e.isShiftDown();
					offs = Math.min(leadSelectionIndex + 1, model.getByteCount() - 1);
					changeSelectionByOffset(offs, extend);
					e.consume();
					break;
				case KeyEvent.VK_UP:
					extend = e.isShiftDown();
					offs = Math.max(leadSelectionIndex - 16, 0);
					changeSelectionByOffset(offs, extend);
					e.consume();
					break;
				case KeyEvent.VK_DOWN:
					extend = e.isShiftDown();
					offs = Math.min(leadSelectionIndex + 16, model.getByteCount() - 1);
					changeSelectionByOffset(offs, extend);
					e.consume();
					break;
				case KeyEvent.VK_PAGE_DOWN:
					extend = e.isShiftDown();
					int visibleRowCount = getVisibleRect().height / getRowHeight();
					offs = Math.min(leadSelectionIndex + visibleRowCount * 16, model.getByteCount() - 1);
					changeSelectionByOffset(offs, extend);
					e.consume();
					break;
				case KeyEvent.VK_PAGE_UP:
					extend = e.isShiftDown();
					visibleRowCount = getVisibleRect().height / getRowHeight();
					offs = Math.max(leadSelectionIndex - visibleRowCount * 16, 0);
					changeSelectionByOffset(offs, extend);
					e.consume();
					break;
				case KeyEvent.VK_HOME:
					extend = e.isShiftDown();
					offs = (leadSelectionIndex / 16) * 16;
					changeSelectionByOffset(offs, extend);
					e.consume();
					break;
				case KeyEvent.VK_END:
					extend = e.isShiftDown();
					offs = (leadSelectionIndex / 16) * 16 + 15;
					offs = Math.min(offs, model.getByteCount() - 1);
					changeSelectionByOffset(offs, extend);
					e.consume();
					break;
				}
			} else if (e.isControlDown()) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_1:
					vpx_Hex_Cxt_GrpAs_Hex8.doClick();
					e.consume();
					break;

				case KeyEvent.VK_2:
					vpx_Hex_Cxt_GrpAs_Hex16.doClick();
					e.consume();
					break;

				case KeyEvent.VK_3:
					vpx_Hex_Cxt_GrpAs_Hex32.doClick();
					e.consume();
					break;

				case KeyEvent.VK_4:
					vpx_Hex_Cxt_GrpAs_Hex64.doClick();
					e.consume();
					break;

				case KeyEvent.VK_5:
					vpx_Hex_Cxt_GrpAs_SignedInt16.doClick();
					e.consume();
					break;

				case KeyEvent.VK_6:
					vpx_Hex_Cxt_GrpAs_SignedInt32.doClick();
					e.consume();
					break;

				case KeyEvent.VK_7:
					vpx_Hex_Cxt_GrpAs_UnSignedInt16.doClick();
					e.consume();
					break;

				case KeyEvent.VK_8:
					vpx_Hex_Cxt_GrpAs_UnSignedInt32.doClick();
					e.consume();
					break;

				case KeyEvent.VK_9:
					vpx_Hex_Cxt_GrpAs_Floating32.doClick();
					e.consume();
					break;

				}
			}
		}

		super.processKeyEvent(e);

	}

	/**
	 * Tries to redo the last action undone.
	 *
	 * @return Whether there is another action to redo after this one.
	 * @see #undo()
	 */
	public boolean redo() {
		return model.redo();
	}

	/**
	 * Removes a range of bytes.
	 *
	 * @param offs
	 *            The offset of the range of bytes to remove.
	 * @param len
	 *            The number of bytes to remove.
	 * @see #replaceBytes(int, int, byte[])
	 */
	void removeBytes(int offs, int len) {
		model.removeBytes(offs, len);
	}

	private void repaintSelection() {
		// TODO: Repaint only selected lines.
		repaint();
	}

	/**
	 * Removes a listener who isn't any longer interested whether the text
	 * selection from the hex editor becomes changed.
	 * 
	 * @param l
	 *            The concerning previous prospect.
	 * @see #addSelectionChangedListener(SelectionChangedListener)
	 */
	public void removeSelectionChangedListener(SelectionChangedListener l) {
		listenerList.remove(SelectionChangedListener.class, l);
	}

	/**
	 * Replaces a range of bytes.
	 *
	 * @param offset
	 *            The offset of the range of bytes to replace.
	 * @param len
	 *            The number of bytes to replace.
	 * @param bytes
	 *            The bytes to replace the range with.
	 * @see #removeBytes(int, int)
	 */
	public void replaceBytes(int offset, int len, byte[] bytes) {
		model.replaceBytes(offset, len, bytes);
	}

	public void setSelectedRows(int min, int max) {
		if (min < 0 || min >= getRowCount() || max < 0 || max >= getRowCount()) {
			throw new IllegalArgumentException();
		}
		int startOffs = min * 16;
		int endOffs = max * 16 + 15;
		// TODO: Have a single call to change selection by a range.
		changeSelectionByOffset(startOffs, false);
		changeSelectionByOffset(endOffs, true);
	}

	/**
	 * Selects the specified range of bytes in the table.
	 *
	 * @param startOffs
	 *            The "anchor" byte of the selection.
	 * @param endOffs
	 *            The "lead" byte of the selection.
	 * @see #changeSelection(int, int, boolean, boolean)
	 * @see #changeSelectionByOffset(int, boolean)
	 */
	public void setSelectionByOffsets(int startOffs, int endOffs) {

		startOffs = Math.max(0, startOffs);
		startOffs = Math.min(startOffs, model.getByteCount() - 1);

		// Clear the old selection (may not be necessary).
		repaintSelection();

		anchorSelectionIndex = startOffs;
		leadSelectionIndex = endOffs;

		// Scroll after changing the selection as blit scrolling is
		// immediate, so that if we cause the repaint after the scroll we
		// end up painting everything!
		if (getAutoscrolls()) {
			int endRow = endOffs / 16;
			int endCol = endOffs % 16;
			// Don't allow the user to select the "ascii dump" or any
			// empty cells in the last row of the table.
			endCol = adjustColumn(endRow, endCol);
			if (endRow < 0) {
				endRow = 0;
			}
			ensureCellIsVisible(endRow, endCol);
		}

		// Draw the new selection.
		repaintSelection();

	}

	/**
	 * Tries to undo the last action.
	 *
	 * @return Whether there is another action to undo after this one.
	 * @see #redo()
	 */
	public boolean undo() {
		return model.undo();
	}

	/**
	 * Table cell editor that restricts input to byte values (
	 * <code>0 - 255</code>).
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private static class CellEditor extends DefaultCellEditor implements FocusListener {

		private static final long serialVersionUID = 1L;

		public CellEditor() {
			super(new JTextField());
			AbstractDocument doc = (AbstractDocument) ((JTextComponent) editorComponent).getDocument();
			doc.setDocumentFilter(new EditorDocumentFilter());
			getComponent().addFocusListener(this);
		}

		public void focusGained(FocusEvent e) {
			JTextField textField = (JTextField) getComponent();
			textField.selectAll();
		}

		public void focusLost(FocusEvent e) {
		}

		public boolean stopCellEditing() {
			// Prevent the user from entering empty string as a value.
			String value = (String) getCellEditorValue();
			if (value.length() == 0) {
				UIManager.getLookAndFeel().provideErrorFeedback(null);
				return false;
			}
			return super.stopCellEditing();
		}

	}

	/**
	 * Custom cell renderer. This is primarily here for performance, especially
	 * on 1.4 JVM's. <code>DefaultTableCellRenderer</code>'s performance was
	 * horrible on tables displaying large amounts of rows and columns in 1.4,
	 * so this class helps to alleviate some of that pain. 1.4 and 1.5 JRE's
	 * don't have as much of a performance problem, but you still can see some
	 * lag at times.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class CellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		private Point highlight;

		public CellRenderer() {
			highlight = new Point();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focus,
				int row, int column) {

			super.getTableCellRendererComponent(table, value, selected, focus, row, column);

			highlight.setLocation(-1, -1);

			if (column == table.getColumnCount() - 1 && // "Ascii dump"
					hexEditor.getHighlightSelectionInAsciiDump()) {

				int selStart = getSmallestSelectionIndex();

				int selEnd = getLargestSelectionIndex();

				int b1 = row * 16;

				int b2 = b1 + 15;

				if (selStart <= b2 && selEnd >= b1) {

					int start = Math.max(selStart, b1) - b1;

					int end = Math.min(selEnd, b2) - b1;

					highlight.setLocation(start, end);
				}

				boolean colorBG = hexEditor.getAlternateRowBG() && (row & 1) > 0;

				setBackground(colorBG ? ANTERNATING_CELL_COLOR : ANTERNATING_CELL_COLOR);

			} else {

				if (!selected) {

					if ((hexEditor.getAlternateRowBG() && (row & 1) > 0)
							^ (hexEditor.getAlternateColumnBG() && (column & 1) > 0)) {

						setBackground(ANTERNATING_CELL_COLOR);

					} else {

						setBackground(table.getBackground());
					}
				}
			}

			return this;

		}

		protected void paintComponent(Graphics g) {

			g.setColor(getBackground());

			g.fillRect(0, 0, getWidth(), getHeight());

			if (highlight.x > -1) {

				int w = getFontMetrics(HexTable.this.getFont()).charWidth('w');

				g.setColor(hexEditor.getHighlightSelectionInAsciiDumpColor());

				int x = getInsets().left + highlight.x * w;

				g.fillRect(x, 0, (highlight.y - highlight.x + 1) * w, getRowHeight());
			}

			g.setColor(getForeground());

			int x = 2;

			String text = getText();

			// not padding low bytes, and this one is in range 00-0f.

			if (text.length() == 1) {

				x += g.getFontMetrics().charWidth('w');
			}

			g.drawString(text, x, 11);

		}

	}

	/**
	 * Filter that ensures the user only enters valid characters in a byte's
	 * cell while editing.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private static class EditorDocumentFilter extends DocumentFilter {

		private boolean ensureByteRepresented(String str) {
			try {
				int i = Integer.parseInt(str, 16);
				if (i < 0 || i > 0xff) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException nfe) {
				VPXLogger.updateError(nfe);
				UIManager.getLookAndFeel().provideErrorFeedback(null);
				return false;
			}
			return true;
		}

		public void insertString(FilterBypass fb, int offs, String string, AttributeSet attr)
				throws BadLocationException {
			Document doc = fb.getDocument();
			String temp = doc.getText(0, offs) + string + doc.getText(offs, doc.getLength() - offs);
			if (ensureByteRepresented(temp)) {
				fb.insertString(offs, temp, attr);
			}
		}

		public void replace(FilterBypass fb, int offs, int len, String text, AttributeSet attrs)
				throws BadLocationException {
			Document doc = fb.getDocument();
			String temp = doc.getText(0, offs) + text + doc.getText(offs + len, doc.getLength() - (offs + len));
			if (ensureByteRepresented(temp)) {
				fb.replace(offs, len, text, attrs);
			}
		}

	}

}
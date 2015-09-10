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
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

import com.cti.vpx.controls.hex.groupmodel.Floating32;
import com.cti.vpx.controls.hex.groupmodel.Hex16;
import com.cti.vpx.controls.hex.groupmodel.Hex32;
import com.cti.vpx.controls.hex.groupmodel.Hex64;
import com.cti.vpx.controls.hex.groupmodel.Hex8;
import com.cti.vpx.controls.hex.groupmodel.SignedInt16;
import com.cti.vpx.controls.hex.groupmodel.SignedInt32;
import com.cti.vpx.controls.hex.groupmodel.UnSignedInt16;
import com.cti.vpx.controls.hex.groupmodel.UnSignedInt32;
import com.cti.vpx.util.VPXUtilities;

/**
 * A Swing hex editor component.
 * <p>
 *
 * The hex editor's functionality includes:
 * <ul>
 * <li>Cut, copy, paste, delete of 1 or more bytes
 * <li>Undo/redo
 * <li>Selecting a contiguous block of bytes
 * </ul>
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class HexEditor extends JScrollPane {

	private static final long serialVersionUID = 1L;

	/**
	 * Property fired when the alternating of column background colors is
	 * toggled.
	 */
	public static final String PROPERTY_ALTERNATE_COLUMN_BG = "alternateColBG";

	/**
	 * Property fired when the alternating of row background colors is toggled.
	 */
	public static final String PROPERTY_ALTERNATE_ROW_BG = "alternateRowBG";

	/**
	 * Property fired when the highlight color of the ascii dump column is
	 * changed.
	 */
	public static final String PROPERTY_ASCII_DUMP_HIGHLIGHT_COLOR = "asciiDumpHighlightColor";

	/**
	 * Property fired when the visibility of the highlight in the "ascii dump"
	 * column is toggled.
	 */
	public static final String PROPERTY_HIGHLIGHT_ASCII_DUMP = "highlightAsciiDump";

	/**
	 * Property fired when the padding of low bytes (<code>0 - 15</code>) is
	 * toggled.
	 */
	public static final String PROPERTY_PAD_LOW_BYTES = "padLowBytes";

	/**
	 * Property fired when the visibility of the grid is toggled.
	 */
	public static final String PROPERTY_SHOW_GRID = "showGrid";

	/*
	 * "8 Bit Hex" "16 Bit Hex" "32 Bit Hex" "64 Bit Hex" "16 Bit Signed",
	 * "32 Bit Signed" "16 Bit Unsigned" "32 Bit Unsigned" "32 Bit Floating"
	 */
	public static final int HEX8 = 0;

	public static final int HEX16 = 1;

	public static final int HEX32 = 2;

	public static final int HEX64 = 3;

	public static final int SINGNEDINT16 = 4;

	public static final int SINGNEDINT32 = 5;

	public static final int UNSINGNEDINT16 = 6;

	public static final int UNSINGNEDINT32 = 7;

	public static final int UNSINGNEDFLOAT32 = 8;

	private HexTable table;
	private boolean alternateRowBG;
	private boolean alternateColumnBG;
	private boolean highlightSelectionInAsciiDump;
	private Color highlightSelectionInAsciiDumpColor;
	private boolean padLowBytes;

	private ResourceBundle msg;

	private static final TransferHandler DEFAULT_TRANSFER_HANDLER = new HexEditorTransferHandler();

	static final int DUMP_COLUMN_WIDTH = 200;

	/**
	 * Creates a new <code>HexEditor</code> component.
	 */
	public HexEditor() {

		JPanel jp = new JPanel();

		jp.setLayout(new BorderLayout());

		msg = VPXUtilities.getResourceBundle();

		Hex8 model = new Hex8(this, msg);

		table = new HexTable(this, model);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		jp.add(table, BorderLayout.CENTER);

		setViewportView(jp);

		setShowColumnHeader(true);

		setShowRowHeader(true);

		setAlternateRowBG(false);

		setAlternateColumnBG(false);

		setHighlightSelectionInAsciiDump(true);

		setHighlightSelectionInAsciiDumpColor(new Color(255, 255, 192));

		setPadLowBytes(true);

		setTransferHandler(DEFAULT_TRANSFER_HANDLER);

	}

	public void setHexModel(int mod) {

		ByteBuffer bb = getByteBuffer(table.getModel());

		switch (mod) {

		case HEX8:

			Hex8 hex8model = new Hex8(this, msg);

			hex8model.setBytes(bb);

			table.setModel(hex8model);

			break;

		case HEX16:

			Hex16 hex16model = new Hex16(this, msg);

			hex16model.setBytes(bb);

			table.setModel(hex16model);

			break;

		case HEX32:

			Hex32 hex32model = new Hex32(this, msg);

			hex32model.setBytes(bb);

			table.setModel(hex32model);

			break;

		case HEX64:

			Hex64 hex64model = new Hex64(this, msg);

			hex64model.setBytes(bb);

			table.setModel(hex64model);

			break;

		case UNSINGNEDINT16:

			UnSignedInt16 unsigned16model = new UnSignedInt16(this, msg);

			unsigned16model.setBytes(bb);

			table.setModel(unsigned16model);

			break;

		case UNSINGNEDINT32:

			UnSignedInt32 unsigned32model = new UnSignedInt32(this, msg);

			unsigned32model.setBytes(bb);

			table.setModel(unsigned32model);

			break;

		case SINGNEDINT16:

			SignedInt16 signed16model = new SignedInt16(this, msg);

			signed16model.setBytes(bb);

			table.setModel(signed16model);

			break;

		case SINGNEDINT32:

			SignedInt32 signed32model = new SignedInt32(this, msg);

			signed32model.setBytes(bb);

			table.setModel(signed32model);

			break;

		case UNSINGNEDFLOAT32:

			Floating32 floating32model = new Floating32(this, msg);

			floating32model.setBytes(bb);

			table.setModel(floating32model);

			break;
		}

	}

	private ByteBuffer getByteBuffer(TableModel model) {

		ByteBuffer bb = null;

		if (model instanceof Hex8) {

			bb = ((Hex8) table.getModel()).getBytes();

		} else if (model instanceof Hex16) {

			bb = ((Hex16) table.getModel()).getBytes();

		} else if (model instanceof Hex32) {

			bb = ((Hex32) table.getModel()).getBytes();

		} else if (model instanceof Hex64) {

			bb = ((Hex64) table.getModel()).getBytes();

		} else if (model instanceof SignedInt16) {

			bb = ((SignedInt16) table.getModel()).getBytes();

		} else if (model instanceof SignedInt32) {

			bb = ((SignedInt32) table.getModel()).getBytes();

		} else if (model instanceof UnSignedInt16) {

			bb = ((UnSignedInt16) table.getModel()).getBytes();

		} else if (model instanceof UnSignedInt32) {

			bb = ((UnSignedInt32) table.getModel()).getBytes();

		} else if (model instanceof Floating32) {

			bb = ((Floating32) table.getModel()).getBytes();

		}

		return bb;

	}

	/**
	 * Adds a hex editor listener to this editor.
	 *
	 * @param l
	 *            The listener to add.
	 * @see #removeHexEditorListener(HexEditorListener)
	 */
	public void addHexEditorListener(HexEditorListener l) {
		listenerList.add(HexEditorListener.class, l);
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
		table.addSelectionChangedListener(l);
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
		return table.cellToOffset(row, col);
	}

	/**
	 * Copies the currently selected bytes to the clipboard.
	 *
	 * @see #cut()
	 * @see #paste()
	 * @see #delete()
	 */
	public void copy() {
		invokeAction(TransferHandler.getCopyAction());
	}

	/**
	 * Removes the currently selected bytes and moves them to the clipboard.
	 *
	 * @see #copy()
	 * @see #paste()
	 * @see #delete()
	 */
	public void cut() {
		invokeAction(TransferHandler.getCutAction());
	}

	/**
	 * Removes the currently selected bytes.
	 *
	 * @see #cut()
	 * @see #copy()
	 * @see #paste()
	 */
	public void delete() {

		// Sanity check (should never happen)
		if (table.leadSelectionIndex == -1 || table.anchorSelectionIndex == -1) {
			UIManager.getLookAndFeel().provideErrorFeedback(table);
			return;
		}

		int start = table.getSmallestSelectionIndex();
		int end = table.getLargestSelectionIndex();
		int len = end - start + 1;
		removeBytes(start, len);

	}

	/**
	 * Notifies all interested listeners of an event in this hex editor.
	 *
	 * @param offset
	 *            The offset of the change.
	 * @param added
	 *            The number of bytes added.
	 * @param removed
	 *            The number of bytes removed.
	 */
	public void fireHexEditorEvent(int offset, int added, int removed) {
		HexEditorEvent e = null;
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == HexEditorListener.class) {
				// Lazily create the event in case there are no listeners.
				if (e == null) {
					e = new HexEditorEvent(this, offset, added, removed);
				}
				((HexEditorListener) listeners[i + 1]).hexBytesChanged(e);
			}
		}
	}

	/**
	 * Returns whether the color of columns in the hex editor should alternate.
	 *
	 * @return Whether the column color alternates.
	 * @see #setAlternateColumnBG(boolean)
	 * @see #getAlternateRowBG()
	 */
	public boolean getAlternateColumnBG() {
		return alternateColumnBG;
	}

	/**
	 * Returns whether the color of rows in the hex editor should alternate.
	 *
	 * @return Whether the row color alternates.
	 * @see #setAlternateRowBG(boolean)
	 * @see #getAlternateColumnBG()
	 */
	public boolean getAlternateRowBG() {
		return alternateRowBG;
	}

	/**
	 * Returns the byte at the specified offset.
	 *
	 * @param offset
	 *            The offset.
	 * @return The byte.
	 */
	public byte getByte(int offset) {
		return table.getByte(offset);
	}

	/**
	 * Returns the number of bytes being edited.
	 *
	 * @return The number of bytes.
	 */
	public int getByteCount() {
		return table.getByteCount();
	}

	/**
	 * Returns whether the selected bytes should also appear selected in the
	 * "ascii dump" column.
	 *
	 * @return Whether the selected bytes should also be selected in the
	 *         "ascii dump" column.
	 * @see #setHighlightSelectionInAsciiDump(boolean)
	 */
	public boolean getHighlightSelectionInAsciiDump() {
		return highlightSelectionInAsciiDump;
	}

	/**
	 * Returns the color used to highlight the selected bytes in the
	 * "ascii dump" column.
	 *
	 * @return The color used.
	 * @see #setHighlightSelectionInAsciiDumpColor(Color)
	 * @see #getHighlightSelectionInAsciiDump()
	 */
	public Color getHighlightSelectionInAsciiDumpColor() {
		return highlightSelectionInAsciiDumpColor;
	}

	/**
	 * Returns the largest selection index.
	 *
	 * @return The largest selection index.
	 * @see #getSmallestSelectionIndex()
	 */
	public int getLargestSelectionIndex() {
		return table.getLargestSelectionIndex();
	}

	/**
	 * Returns whether bytes that can be displayed as a single char (i.e.
	 * <code>0 - 15</code>) are prepended with a "<code>0</code>" char to make
	 * them two characters wide in the display.
	 *
	 * @return Whether bytes with one-character hex representations will be
	 *         prepended with a "<code>0</code>" in the display.
	 * @see #setPadLowBytes(boolean)
	 */
	public boolean getPadLowBytes() {
		return padLowBytes;
	}

	/**
	 * Returns the smallest selection index.
	 *
	 * @return The smallest selection index.
	 * @see #getLargestSelectionIndex()
	 */
	public int getSmallestSelectionIndex() {
		return table.getSmallestSelectionIndex();
	}

	/**
	 * Returns the table actually containing the hex data.
	 *
	 * @return The table.
	 */
	HexTable getTable() {
		return table;
	}

	private void invokeAction(Action a) {
		a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, (String) a.getValue(Action.NAME),
				EventQueue.getMostRecentEventTime(), 0));
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
		return table.offsetToCell(offset);
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
		table.open(fileName);
	}

	public void open(byte[] bytes) throws IOException {
		table.open(bytes);
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
		table.open(in);
	}

	/**
	 * "Pastes" the bytes in the clipboard into the current selection in the hex
	 * editor.
	 *
	 * @see #copy()
	 * @see #cut()
	 * @see #delete()
	 */
	public void paste() {
		invokeAction(TransferHandler.getPasteAction());
	}

	/**
	 * Tries to redo the last action undone.
	 *
	 * @return Whether there is another action to redo after this one.
	 * @see #undo()
	 */
	public boolean redo() {
		return table.redo();
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
	public void removeBytes(int offs, int len) {
		table.removeBytes(offs, len);
		table.changeSelectionByOffset(offs, false);
	}

	/**
	 * Removes the specified hex editor listener from this editor.
	 *
	 * @param l
	 *            The listener to remove.
	 * @see #addHexEditorListener(HexEditorListener)
	 */
	public void removeHexEditorListener(HexEditorListener l) {
		listenerList.remove(HexEditorListener.class, l);
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
		table.removeSelectionChangedListener(l);
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
	 * @see #replaceSelection(byte[])
	 */
	public void replaceBytes(int offset, int len, byte[] bytes) {
		if (len == 1) { // Just insert if 1 bytes is selected.
			len = 0;
		}
		table.replaceBytes(offset, len, bytes);
		table.changeSelectionByOffset(table.anchorSelectionIndex, false);
		int count = bytes == null ? 0 : bytes.length;
		table.setSelectionByOffsets(offset, offset + count - 1);
	}

	/**
	 * Replaces the currently selected bytes (if >=1) with the specified new
	 * bytes.
	 *
	 * @param bytes
	 *            The new bytes. If this is <code>null</code> or an empty array,
	 *            calling this method simply removes the currently selected
	 *            bytes.
	 * @see #replaceBytes(int, int, byte[])
	 */
	public void replaceSelection(byte[] bytes) {
		int offset = table.getSmallestSelectionIndex();
		int len = table.getLargestSelectionIndex() - offset + 1;
		replaceBytes(offset, len, bytes);
	}

	/**
	 * Sets whether the column color should alternate in the hex editor. This
	 * method fires a property change event of type
	 * {@link #PROPERTY_ALTERNATE_COLUMN_BG}.
	 *
	 * @param alternate
	 *            Whether the column color should alternate.
	 * @see #getAlternateColumnBG()
	 * @see #setAlternateRowBG(boolean)
	 */
	public void setAlternateColumnBG(boolean alternate) {
		if (alternate != alternateColumnBG) {
			this.alternateColumnBG = alternate;
			table.repaint();
			firePropertyChange(PROPERTY_ALTERNATE_COLUMN_BG, !alternate, alternate);
		}
	}

	/**
	 * Sets whether the row color should alternate in the hex editor. This
	 * method fires a property change event of type
	 * {@link #PROPERTY_ALTERNATE_ROW_BG}.
	 *
	 * @param alternate
	 *            Whether the row color should alternate.
	 * @see #getAlternateRowBG()
	 * @see #setAlternateColumnBG(boolean)
	 */
	public void setAlternateRowBG(boolean alternate) {
		if (alternate != alternateRowBG) {
			this.alternateRowBG = alternate;
			table.repaint();
			firePropertyChange(PROPERTY_ALTERNATE_ROW_BG, !alternate, alternate);
		}
	}

	/**
	 * Sets whether the selected bytes should also appear selected in the
	 * "ascii dump" column. This method fires a property change event of type
	 * {@link #PROPERTY_HIGHLIGHT_ASCII_DUMP}.
	 *
	 * @param highlight
	 *            Whether the selected bytes should also appear selected in the
	 *            "ascii dump" column.
	 * @see #getHighlightSelectionInAsciiDump()
	 * @see #setHighlightSelectionInAsciiDumpColor(Color)
	 */
	public void setHighlightSelectionInAsciiDump(boolean highlight) {
		if (highlight != highlightSelectionInAsciiDump) {
			highlightSelectionInAsciiDump = highlight;
			table.repaint();
			firePropertyChange(PROPERTY_HIGHLIGHT_ASCII_DUMP, !highlight, highlight);
		}
	}

	/**
	 * Sets what color should be used for the "ascii dump" selection. This
	 * method fires a property change event of type
	 * {@link #PROPERTY_ASCII_DUMP_HIGHLIGHT_COLOR}.
	 * 
	 * @param c
	 *            The color to use.
	 * @see #getHighlightSelectionInAsciiDumpColor()
	 * @see #setHighlightSelectionInAsciiDump(boolean)
	 */
	public void setHighlightSelectionInAsciiDumpColor(Color c) {
		if (c != null && !c.equals(highlightSelectionInAsciiDumpColor)) {
			Color old = highlightSelectionInAsciiDumpColor;
			highlightSelectionInAsciiDumpColor = c;
			table.repaint();
			firePropertyChange(PROPERTY_ASCII_DUMP_HIGHLIGHT_COLOR, old, c);
		}
	}

	/**
	 * Sets whether bytes that can be displayed as a single char (i.e.
	 * <code>0 - 15</code>) are prepended with a "<code>0</code>" char to make
	 * them two characters wide in the display. This method fires a property
	 * change event of type {@link #PROPERTY_PAD_LOW_BYTES}.
	 *
	 * @param pad
	 *            Whether to pad such bytes in the display.
	 * @see #getPadLowBytes()
	 */
	public void setPadLowBytes(boolean pad) {
		if (padLowBytes != pad) {
			padLowBytes = pad;
			table.repaint();
			firePropertyChange(PROPERTY_PAD_LOW_BYTES, !pad, pad);
		}
	}

	/**
	 * Sets the range of bytes to select in the hex editor.
	 *
	 * @param startOffs
	 *            The first byte to select.
	 * @param endOffs
	 *            The last byte to select.
	 */
	public void setSelectedRange(int startOffs, int endOffs) {
		table.setSelectionByOffsets(startOffs, endOffs);
	}

	/**
	 * Toggles whether table's column header is visible.
	 *
	 * @param show
	 *            Whether to show the table column header.
	 * @see #setShowRowHeader(boolean)
	 */
	public void setShowColumnHeader(boolean show) {
		// Since table header is added to a parent JScrollPane as a
		// column header, we have to remove it from the scrollpane to
		// make it not visible.
		setColumnHeaderView(show ? table.getTableHeader() : null);
	}

	/**
	 * Toggles whether the table's grid lines are visible. This method fires a
	 * property change event of type {@link #PROPERTY_SHOW_GRID}.
	 *
	 * @param show
	 *            Whether grid lines are visible.
	 */
	public void setShowGrid(boolean show) {
		// There is no "getShowGrid()" method.
		if (show != table.getShowHorizontalLines()) {
			table.setShowGrid(show);
			firePropertyChange(PROPERTY_SHOW_GRID, !show, show);
		}
	}

	/**
	 * Toggles whether table's row header is visible.
	 *
	 * @param show
	 *            Whether to show the table row header.
	 * @see #setShowColumnHeader(boolean)
	 */
	public void setShowRowHeader(boolean show) {
		setRowHeaderView(show ? new HexEditorRowHeader(table) : null);
	}

	/**
	 * Tries to undo the last action.
	 *
	 * @return Whether there is another action to undo after this one.
	 * @see #redo()
	 */
	public boolean undo() {
		return table.undo();
	}

}
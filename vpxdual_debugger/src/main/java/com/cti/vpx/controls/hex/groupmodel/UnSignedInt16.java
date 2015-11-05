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
package com.cti.vpx.controls.hex.groupmodel;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ResourceBundle;

import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import com.cti.vpx.command.ATP;
import com.cti.vpx.controls.hex.ByteBuffer;
import com.cti.vpx.controls.hex.HexEditor;

/**
 * The table model used by the <code>JTable</code> in the hex editor.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class UnSignedInt16 extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private HexEditor editor;
	private ByteBuffer doc;
	private int bytesPerRow;
	private UndoManager undoManager;
	private String[] columnNames;

	/**
	 * Cache of string values of "<code>0</code>"-"<code>ff</code>" for fast
	 * rendering.
	 */
	private String[] paddedLowerByteStrVals;

	/**
	 * Cache of "padded" string values of "<code>00</code>"-"<code>0f</code>"
	 * for fast rendering.
	 */
	private String[] byteStrVals;

	private BigInteger bi;

	/**
	 * Creates the model.
	 *
	 * @param editor
	 *            The parent hex editor.
	 * @param msg
	 *            The resource bundle for localizations.
	 */
	public UnSignedInt16(HexEditor editor, ResourceBundle msg) {

		this.editor = editor;
		doc = new ByteBuffer(16);
		bytesPerRow = 16;

		undoManager = new UndoManager();

		columnNames = new String[16];
		for (int i = 0; i < 16; i++) {
			columnNames[i] = Integer.toHexString(i).toUpperCase();
		}

		byteStrVals = new String[256];
		for (int i = 0; i < byteStrVals.length; i++) {
			byteStrVals[i] = Integer.toHexString(i).toUpperCase();
		}

		paddedLowerByteStrVals = new String[16];
		for (int i = 0; i < paddedLowerByteStrVals.length; i++) {
			paddedLowerByteStrVals[i] = "0" + Integer.toHexString(i).toUpperCase();
		}

	}

	/**
	 * Gets the byte at the specified offset in the document.
	 *
	 * @param offset
	 *            The offset.
	 * @return The byte.
	 */
	public byte getByte(int offset) {
		return doc.getByte(offset);
	}

	/**
	 * Returns the number of bytes in the document.
	 *
	 * @return The size of the document, in bytes.
	 */
	public int getByteCount() {
		return doc.getSize();
	}

	/**
	 * Returns the number of bytes to display in a row.
	 *
	 * @return The number of bytes to display in a row.
	 */
	public int getBytesPerRow() {
		return bytesPerRow;
	}

	/**
	 * Returns the number of columns to display in the hex editor.
	 *
	 * @return The number of columns to display.
	 */
	public int getColumnCount() {
		return getBytesPerRow();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	/**
	 * Gets the number of rows to display in the hex editor.
	 *
	 * @return The number of rows to display.
	 */
	public int getRowCount() {
		return doc.getSize() / (bytesPerRow * 2) + (doc.getSize() % (bytesPerRow * 2) > 0 ? 1 : 0);
	}

	/**
	 * Returns the value to display at the specified cell in the table, as a
	 * <code>String</code>.
	 *
	 * @param row
	 *            The row of the cell.
	 * @param col
	 *            The column of the cell.
	 * @return The value to display.
	 */
	public Object getValueAt(int row, int col) {

		int pos = editor.cellToOffset(row, col);
		if (pos == -1) { // A cell that isn't part of the byte array
			return "";
		}
		// & with 0xff to convert to unsigned

		byte[] b = doc.getByteByGroup(pos, 2, true);

		return new BigInteger(b).intValue();

	}

	/**
	 * Redoes the last undoable edit undone.
	 *
	 * @return Whether there is another operation that can be redone after this
	 *         one.
	 * @see #undo()
	 */
	public boolean redo() {
		boolean canRedo = undoManager.canRedo();
		if (canRedo) {
			undoManager.redo();
			canRedo = undoManager.canRedo();
		} else {
			UIManager.getLookAndFeel().provideErrorFeedback(editor);
		}
		return canRedo;
	}

	/**
	 * Removes bytes from the document.
	 *
	 * @param offset
	 *            The offset at which to remove.
	 * @param len
	 *            The number of bytes to remove. If this value is
	 *            <code>&lt;= 0</code>, nothing happens.
	 * @see #replaceBytes(int, int, byte[])
	 */
	public void removeBytes(int offset, int len) {
		replaceBytes(offset, len, null);
	}

	/**
	 * Replaces bytes in the document.
	 *
	 * @param offset
	 *            The offset of the range of bytes to replace.
	 * @param len
	 *            The number of bytes to replace.
	 * @param bytes
	 *            The bytes to replace with. If this is <code>null</code> or an
	 *            empty array, then this method call will be equivalent to
	 *            <code>removeBytes(offset, len)</code>.
	 * @see #removeBytes(int, int)
	 */
	public void replaceBytes(int offset, int len, byte[] bytes) {

		byte[] removed = null;
		if (len > 0) {
			removed = new byte[len];
			doc.remove(offset, len, removed);
		}

		byte[] added = null;
		if (bytes != null && bytes.length > 0) {
			doc.insertBytes(offset, bytes);
			added = (byte[]) bytes.clone();
		}

		if (removed != null || added != null) {
			undoManager.addEdit(new BytesReplacedUndoableEdit(offset, removed, added));
			fireTableDataChanged();
			int addCount = added == null ? 0 : added.length;
			int remCount = removed == null ? 0 : removed.length;
			editor.fireHexEditorEvent(offset, addCount, remCount);
		}

	}

	public ByteBuffer getBytes() {
		return doc;
	}

	public void setBytes(ByteBuffer buff) {
		this.doc = buff;
	}

	public void setBytes(byte[] bytes, int stride) throws IOException {
		doc = new ByteBuffer(bytes);
		undoManager.discardAllEdits();
		setColumnHeaders(stride);
		fireTableDataChanged();
		editor.fireHexEditorEvent(0, doc.getSize(), 0);
	}

	private void setColumnHeaders(int stride) {

		TableColumnModel header = editor.getTable().getTableHeader().getColumnModel();
		int j = 0;

		for (int i = 0; i < 16; i++) {

			columnNames[i] = Integer.toHexString(j).toUpperCase();

			header.getColumn(i).setHeaderValue(columnNames[i]);

			j = j + (stride + 1);
		}

		editor.setShowColumnHeader(true);
	}

	/**
	 * Sets the bytes displayed to those in the given file.
	 *
	 * @param fileName
	 *            The name of a file.
	 * @throws IOException
	 *             If an IO error occurs reading the file.
	 * @see #setBytes(InputStream)
	 */
	public void setBytes(String fileName) throws IOException {
		doc = new ByteBuffer(fileName);
		undoManager.discardAllEdits();
		fireTableDataChanged();
		editor.fireHexEditorEvent(0, doc.getSize(), 0);
	}

	/**
	 * Sets the bytes displayed to those from an input stream.
	 *
	 * @param in
	 *            The input stream to read from.
	 * @throws IOException
	 *             If an IO error occurs.
	 * @see #setBytes(String)
	 */
	public void setBytes(InputStream in) throws IOException {
		doc = new ByteBuffer(in);
		undoManager.discardAllEdits();
		fireTableDataChanged();
		editor.fireHexEditorEvent(0, doc.getSize(), 0);
	}

	/**
	 * Sets the value of a cell in the table.
	 *
	 * @param value
	 *            The new value for the cell. This should be a String
	 *            representing an unsigned byte in hexadecimal, i.e.
	 *            <code>"00"</code> to <code>"ff"</code>.
	 * @param row
	 *            The row of the cell to change.
	 * @param col
	 *            The column of the cell to change.
	 */
	public void setValueAt(Object value, int row, int col) {

		String val = String.format("%04x", Integer.parseInt(value.toString()));

		byte[] bArr = new byte[2];

		bArr[1] = (byte) Integer.parseInt(val.substring(0, 2), 16);

		bArr[0] = (byte) Integer.parseInt(val.substring(2, 4), 16);

		int offset = editor.cellToOffset(row, col) * 2;

		replaceBytes(offset, 2, bArr);

		bi = new BigInteger(bArr);

		this.editor.getMemoryWindow().setMemory(offset, ATP.DATA_TYPE_SIZE_BIT16, 1, bi.longValue());

		fireTableCellUpdated(row, col);

		editor.fireHexEditorEvent(offset, 2, 2);

	}

	/**
	 * Undoes the previous undoable edit.
	 *
	 * @return Whether there is another operation that can be undone after this
	 *         one.
	 * @see #redo()
	 */
	public boolean undo() {
		boolean canUndo = undoManager.canUndo();
		if (canUndo) {
			undoManager.undo();
			canUndo = undoManager.canUndo();
		} else {
			UIManager.getLookAndFeel().provideErrorFeedback(editor);
		}
		return canUndo;
	}

	/**
	 * An "undoable event" representing a single byte changing value.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class ByteChangedUndoableEdit extends AbstractUndoableEdit {

		private static final long serialVersionUID = 1L;

		private int offs;
		private byte oldVal;
		private byte newVal;

		public ByteChangedUndoableEdit(int offs, byte oldVal, byte newVal) {
			this.offs = offs;
			this.oldVal = oldVal;
			this.newVal = newVal;
		}

		public void undo() {
			super.undo();
			if (getByteCount() < offs) {
				throw new CannotUndoException();
			}
			setValueImpl(offs, oldVal);
		}

		public void redo() {
			super.redo();
			if (getByteCount() < offs) {
				throw new CannotRedoException();
			}
			setValueImpl(offs, newVal);
		}

		private void setValueImpl(int offset, byte val) {
			editor.setSelectedRange(offset, offset);
			doc.setByte(offset, val);
			Point p = editor.offsetToCell(offset);
			fireTableCellUpdated(p.x, p.y);
			fireTableCellUpdated(p.x, bytesPerRow); // "Ascii dump" column
			editor.fireHexEditorEvent(offset, 1, 1);
		}

	}

	/**
	 * An "undoable event" representing a range of bytes being replaced (or just
	 * removed or inserted)
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class BytesReplacedUndoableEdit extends AbstractUndoableEdit {

		private static final long serialVersionUID = 1L;

		private int offs;
		private byte[] removed;
		private byte[] added;

		public BytesReplacedUndoableEdit(int offs, byte[] removed, byte[] added) {
			this.offs = offs;
			this.removed = removed;
			this.added = added;
		}

		public void undo() {
			super.undo();
			if (getByteCount() < offs) {
				throw new CannotUndoException();
			}
			removeAndAdd(added, removed);
		}

		public void redo() {
			super.redo();
			if (getByteCount() < offs) {
				throw new CannotRedoException();
			}
			removeAndAdd(removed, added);
		}

		private void removeAndAdd(byte[] toRemove, byte[] toAdd) {
			int remCount = toRemove == null ? 0 : toRemove.length;
			int addCount = toAdd == null ? 0 : toAdd.length;
			doc.remove(offs, remCount);
			doc.insertBytes(offs, toAdd);
			fireTableDataChanged();
			int endOffs = offs;
			if (toAdd != null && toAdd.length > 0) {
				endOffs += toAdd.length - 1;
			}
			editor.setSelectedRange(offs, endOffs);
			editor.fireHexEditorEvent(offs, addCount, remCount);
		}

	}

}
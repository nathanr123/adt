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

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Header of the hex table; displays address of the first byte on the row.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class HexEditorRowHeader extends JList<Object>implements TableModelListener {

	private static final long serialVersionUID = 1L;

	private HexTable table;
	private RowHeaderListModel model;
	private int currentFormat = HexEditor.HEX8;
	private int currentGroup = HexEditor.HEX8;
	private int curStride = 0;
	private static final Border CELL_BORDER = BorderFactory.createEmptyBorder(0, 5, 0, 5);

	/**
	 * Constructor.
	 *
	 * @param table
	 *            The table displaying the hex content.
	 */
	public HexEditorRowHeader(long startAddress, int stride, HexTable table) {

		this.table = table;

		setCurrentFormat();

		this.curStride = stride;

		model = new RowHeaderListModel(startAddress, stride);

		setModel(model);

		setFocusable(false);

		setFont(table.getFont());

		setFixedCellHeight(table.getRowHeight());

		setCellRenderer(new CellRenderer());

		setBorder(new RowHeaderBorder());

		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		syncRowCount(); // Initialize to initial size of table.

		table.getModel().addTableModelListener(this);
	}

	public HexEditorRowHeader(long startAddress, HexTable table) {

		this.table = table;

		setCurrentFormat();

		int stride = 0;

		if (model != null)
			stride = model.getStride();

		model = new RowHeaderListModel(startAddress, stride);

		setModel(model);

		setFocusable(false);

		setFont(table.getFont());

		setFixedCellHeight(table.getRowHeight());

		setCellRenderer(new CellRenderer());

		setBorder(new RowHeaderBorder());

		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		syncRowCount(); // Initialize to initial size of table.

		table.getModel().addTableModelListener(this);
	}

	public HexEditorRowHeader(HexTable table, int stride) {

		this(0, stride, table);
	}

	public int getStride() {

		return this.curStride;
	}

	public void addSelectionInterval(int anchor, int lead) {
		super.addSelectionInterval(anchor, lead);
		int min = Math.min(anchor, lead);
		int max = Math.max(anchor, lead);
		table.setSelectedRows(min, max);
	}

	public void removeSelectionInterval(int index0, int index1) {
		super.removeSelectionInterval(index0, index1);
		int anchor = getAnchorSelectionIndex();
		int lead = getLeadSelectionIndex();
		table.setSelectedRows(Math.min(anchor, lead), Math.max(anchor, lead));
	}

	public void setSelectionInterval(int anchor, int lead) {
		super.setSelectionInterval(anchor, lead);
		int min = Math.min(anchor, lead);
		int max = Math.max(anchor, lead);
		// Table may be showing 0 bytes, but we're showing 1 row header
		if (max < table.getRowCount()) {
			table.setSelectedRows(min, max);
		}
	}

	private void syncRowCount() {
		if (table.getRowCount() != model.getSize()) {
			// Always keep 1 row, even if showing 0 bytes in editor
			model.setSize(Math.max(1, table.getRowCount()));
		}
	}

	public void tableChanged(TableModelEvent e) {
		syncRowCount();
	}

	public RowHeaderListModel getRowHeaderModel() {
		return model;
	}

	/**
	 * Renders the cells of the row header.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class CellRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;

		public CellRenderer() {
			setHorizontalAlignment(JLabel.RIGHT);
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected,
				boolean hasFocus) {
			// Never paint cells as "selected."
			super.getListCellRendererComponent(list, value, index, false, hasFocus);
			setBorder(CELL_BORDER);
			// setBackground(table.getBackground());
			return this;
		}

	}

	/**
	 * List model used by the header for the hex table.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	public class RowHeaderListModel extends AbstractListModel<Object> {

		private static final long serialVersionUID = 1L;

		private long start = 0;

		private int stride = 0;

		private int size;

		public RowHeaderListModel(long startAddress, int stride) {

			this.start = startAddress;

			this.stride = stride;

		}

		public Object getElementAt(int index) {

			String str = "0x"
					+ String.format("%08x", ((start) + ((index * currentGroup * getStrideValue()) * 16))).toUpperCase();

			return str;
		}

		private int getStrideValue() {

			return stride + 1;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			int old = this.size;
			this.size = size;
			int diff = size - old;
			if (diff > 0) {
				fireIntervalAdded(this, old, size - 1);
			} else if (diff < 0) {
				fireIntervalRemoved(this, size + 1, old - 1);
			}
		}

		public long getStartAddress() {

			return start;
		}

		public int getStride() {

			return stride;
		}
	}

	/**
	 * Border for the entire row header. This draws a line to separate the
	 * header from the table contents, and gives a small amount of whitespace to
	 * separate the two.
	 *
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private class RowHeaderBorder extends EmptyBorder {

		private static final long serialVersionUID = 1L;

		public RowHeaderBorder() {
			super(0, 0, 0, 2);
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			x = x + width - this.right;
			// g.setColor(table.getBackground());
			// g.fillRect(x,y, width,height);
			g.setColor(table.getGridColor());
			g.drawLine(x, y, x, y + height);
		}

	}

	private void getGroupAddress() {

		switch (currentFormat) {

		case HexEditor.HEX8:

			currentGroup = 1;

			break;

		case HexEditor.HEX16:
		case HexEditor.SINGNEDINT16:
		case HexEditor.UNSINGNEDINT16:

			currentGroup = 2;

			break;

		case HexEditor.HEX32:
		case HexEditor.SINGNEDINT32:
		case HexEditor.UNSINGNEDINT32:
		case HexEditor.UNSINGNEDFLOAT32:

			currentGroup = 4;

			break;

		case HexEditor.HEX64:

			currentGroup = 8;

			break;

		}

	}

	private void setCurrentFormat() {

		currentFormat = table.getHexEditor().getCurrentModel();

		getGroupAddress();
	}

}
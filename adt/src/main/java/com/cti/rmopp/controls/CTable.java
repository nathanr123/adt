/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * @author nathanr_kamal
 *
 */
public class CTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7842252770356508628L;

	private static final Color TABLE_BG_DEFAULT = new Color(254, 253, 251);

	private static final Color TABLE_FG_DEFAULT = Color.BLACK;

	private static final Color TABLE_BG_SELECTION = new Color(45, 137, 239);

	private static final Color TABLE_FG_SELECTION = Color.WHITE;
	
	private static final Color TABLE_SHADOW_DARK = Color.DARK_GRAY;

	private static final Color TABLE_CELL_BG_FOCUS = new Color(45, 137, 239);

	private static final Color TABLE_CELL_FG_FOCUS = Color.WHITE;

	private static final Color TABLE_GRID_DEFAULT = Color.GRAY.brighter();

	private static final Color TABLEHEADER_BG_DEFAULT = Color.GRAY.darker();

	private static final Color TABLEHEADER_FG_DEFAULT = Color.WHITE;

	private static final Color TABLEHEADER_CELL_BORDER_DEFAULT = Color.GRAY.brighter();

	/**
	 * 
	 * 
	 */
	public CTable() {

		init();
	}

	/**
	 * @param arg0
	 */
	public CTable(TableModel arg0) {
		super(arg0);

		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CTable(TableModel arg0, TableColumnModel arg1) {
		super(arg0, arg1);
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CTable(int arg0, int arg1) {
		super(arg0, arg1);
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CTable(Vector arg0, Vector arg1) {
		super(arg0, arg1);
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CTable(Object[][] arg0, Object[] arg1) {
		super(arg0, arg1);
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public CTable(TableModel arg0, TableColumnModel arg1, ListSelectionModel arg2) {
		super(arg0, arg1, arg2);
		init();
	}	

	private void init() {

		UIManager.put("Table.background", TABLE_BG_DEFAULT); // Color(245, 245,
																// 245)

		UIManager.put("Table.darkShadow", TABLE_SHADOW_DARK);

		UIManager.put("Table.focusCellBackground", TABLE_CELL_BG_FOCUS);

		UIManager.put("Table.focusCellForeground", TABLE_CELL_FG_FOCUS);

		UIManager.put("Table.focusCellHighlightBorder", Constants.NOBORDER);

		UIManager.put("Table.font", Constants.FONTSMALL);

		UIManager.put("Table.foreground", TABLE_FG_DEFAULT);

		UIManager.put("Table.gridColor", TABLE_GRID_DEFAULT);

		UIManager.put("Table.selectionBackground", TABLE_BG_SELECTION);

		UIManager.put("Table.selectionForeground", TABLE_FG_SELECTION);

		UIManager.put("Table.shadow", TABLE_FG_DEFAULT);

		UIManager.put("TableHeader.background", TABLEHEADER_BG_DEFAULT);

		UIManager.put("TableHeader.cellBorder", new LineBorder(TABLEHEADER_CELL_BORDER_DEFAULT));

		UIManager.put("TableHeader.font", Constants.FONTDEFAULT);

		UIManager.put("TableHeader.foreground", TABLEHEADER_FG_DEFAULT);

		setRowHeight(30);

		setTableHeader(new JTableHeader(getColumnModel()) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 462446199487181724L;

			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 40;
				return d;
			}
		});

		SwingUtilities.updateComponentTreeUI(this);

	}

}

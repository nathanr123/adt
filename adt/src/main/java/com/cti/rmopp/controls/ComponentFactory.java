/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager2;

import javax.swing.Icon;
import javax.swing.table.TableModel;

/**
 * @author nathanr_kamal
 *
 */
public class ComponentFactory {

	public static CPanel createPanel() {
		return new CPanel();
	}

	public static CPanel createPanel(LayoutManager2 layout) {
		return new CPanel(layout);
	}

	public static CLabel createLabel(String label) {
		return new CLabel(label, false);
	}

	public static CLabel createLabel(String label, boolean isTitle) {
		CLabel lbl = new CLabel(label, true);
		return lbl;
	}

	public static CLabel createLabel(String label, Icon icon, boolean isTitle) {
		CLabel lbl = new CLabel(label, icon, isTitle);
		return lbl;
	}

	public static CLabel createLabel(String label, Icon icon, boolean isTitle, Font font) {
		CLabel lbl = new CLabel(label, icon, isTitle, font);
		return lbl;
	}

	public static CLabel createLabel(Icon icon) {
		CLabel lbl = new CLabel(icon);
		return lbl;
	}

	public static CMenuBar createMenuBar() {
		return new CMenuBar();
	}

	public static CMenu createMenu(String menuName) {
		return new CMenu(menuName);
	}

	public static CMenuItem createMenuItem(String menuItemName) {
		return new CMenuItem(menuItemName);
	}

	public static CTabbedPane createTabbedPane() {
		return new CTabbedPane();
	}

	public static CTable createTable() {
		return new CTable();
	}

	public static CTable createTable(TableModel model) {
		return new CTable(model);
	}

	public static CTable createTable(TableModel model, int rowHeight) {
		return new CTable(model, rowHeight);
	}

	public static CLoggerPane createLoggerPane() {
		return new CLoggerPane();
	}

	public static CTextArea createTextArea() {
		return new CTextArea();
	}

	public static CTextField createTextField() {
		return new CTextField();
	}

	public static CTextField createTextField(int row) {
		return new CTextField(row);
	}

	public static CScrollPane createScrollPane() {
		return new CScrollPane();
	}

	public static CScrollPane createScrollPane(Component comp) {
		return new CScrollPane(comp);
	}

	public static CTitledBorder createTitledBorder(String title) {
		return new CTitledBorder(title);
	}

	public static CProgressBar createprogressBar() {
		return new CProgressBar();
	}

	public static CTaskBar createTaskBar() {
		return new CTaskBar();
	}

	public static CToolTip createToolTip() {
		return new CToolTip();
	}

	public static CButton createButton(String label) {
		return new CButton(label);
	}

	public static CCheckBox createCheckBox(String label, boolean selected) {
		return new CCheckBox(label, selected);
	}

	public static CComboBox createComboBox() {
		return new CComboBox();
	}

}

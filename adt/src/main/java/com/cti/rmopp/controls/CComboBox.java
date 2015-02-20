package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import static com.cti.rmopp.controls.ComponentFactory.getImageIcon;

public class CComboBox extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1614726146149044152L;

	private static final String IMAGE_PATH = "images\\";

	/*********************** COLORS ************************/

	private static final Color COMBO_FG_DEFAULT = new Color(254, 254, 254);

	private static final Color COMBO_FG_SELECTED = Color.DARK_GRAY;

	private static final Color COMBO_FG_ERROR = Color.RED;

	private static final Color COMBO_BG_DEFAULT = Color.GRAY;

	private static final Color COMBO_BG_SELECTED = Color.LIGHT_GRAY.brighter();

	private static final ImageIcon ic = getImageIcon(IMAGE_PATH + "chevrondown.png", 24, 24);

	public CComboBox() {

		super();

		init();

	}

	private void init() {

		UIManager.put("ComboBox.buttonBackground", COMBO_BG_DEFAULT);

		UIManager.put("ComboBox.foreground", COMBO_FG_DEFAULT);

		UIManager.put("ComboBox.selectionBackground", COMBO_BG_SELECTED);

		UIManager.put("ComboBox.selectionForeground", COMBO_FG_SELECTED);

		SwingUtilities.updateComponentTreeUI(this);

		setBorder(Constants.NOBORDER);

		setUI(new CTIComboUI());

		setBackground(COMBO_BG_DEFAULT);

		setForeground(COMBO_FG_DEFAULT);

		setFont(Constants.FONTDEFAULT);

		setRenderer(new ComboBoxRenderer());
	}

	class CTIComboUI extends javax.swing.plaf.basic.BasicComboBoxUI {

		@Override
		protected JButton createArrowButton() {

			JButton btn = new JButton();

			btn.setBackground(COMBO_BG_DEFAULT);

			btn.setFocusPainted(false);

			btn.setBorder(Constants.NOBORDER);

			btn.setIcon(ic);

			btn.setRolloverEnabled(false);

			btn.addChangeListener(new CTIComboButtonStateChangeListener(btn));

			return btn;
		}

		@Override
		protected ComboPopup createPopup() {

			return new CTIComboPopup(comboBox);// super.createPopup();
		}

	}

	private class CTIComboButtonStateChangeListener implements ChangeListener {

		JButton btn;

		public CTIComboButtonStateChangeListener(JButton button) {

			btn = button;
		}

		public void stateChanged(ChangeEvent e) {

			ButtonModel model = btn.getModel();

			model.setPressed(false);
		}
	}

	private class CTIComboPopup extends BasicComboPopup {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3422854644975240614L;

		public CTIComboPopup(JComboBox combo) {

			super(combo);
		}

		@Override
		public JScrollPane createScroller() {

			return new CScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
	}

	private class ComboBoxRenderer extends javax.swing.plaf.basic.BasicComboBoxRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -168607381774026869L;

		public ComboBoxRenderer() {

			super();

			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			list.setAutoscrolls(true);

			list.setCursor(Constants.HANDCURSOR);

			list.setFixedCellHeight(45);

			if (value != null) {

				setText(value.toString());
			}

			if (isSelected) {

				setBackground(COMBO_BG_SELECTED);

				setForeground(COMBO_FG_SELECTED);

			} else {

				setBackground(COMBO_BG_DEFAULT);

				setForeground(COMBO_FG_DEFAULT);
			}

			return this;
		}
	}

	public void setErrorStatus(boolean isError) {

		if (isError) {

			setForeground(COMBO_FG_ERROR);

			setBorder(BorderFactory.createLineBorder(COMBO_FG_ERROR));

		} else {

			setForeground(COMBO_FG_DEFAULT);

			setBorder(Constants.NOBORDER);
		}
	}
}

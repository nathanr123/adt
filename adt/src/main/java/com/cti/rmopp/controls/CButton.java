/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author nathanr_kamal
 * 
 */
public class CButton extends JButton implements ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9046829708894618565L;

	/*********************** COLORS ************************/

	private static final Color BUTTON_BG_DEFAULT = new Color(66, 131, 222);

	private static final Color BUTTON_BG_HOVER = new Color(88, 146, 226);

	private static final Color BUTTON_BG_PRESS = new Color(110, 160, 230);

	private static final Color BUTTON_BG_DISABLED = new Color(210, 210, 210);

	private static final Color BUTTON_FG_DEFAULT = new Color(254, 254, 254);

	private static final Color BUTTON_FG_DISABLED = new Color(198, 198, 198);

	/**
	 * @param name
	 */
	public CButton(String name) {
		super(name);

		init();

	}

	public CButton(String name, Icon icon) {
		super(name);

		init();

		setIcon(icon);

	}

	public void stateChanged(ChangeEvent arg0) {
		ButtonModel model = this.getModel();

		if (model.isRollover()) {

			setBackground(BUTTON_BG_HOVER);
		} else if (model.isPressed()) {

			setBackground(BUTTON_BG_PRESS);
		} else if (!model.isEnabled()) {

			setBackground(BUTTON_BG_DISABLED);

			setForeground(BUTTON_FG_DISABLED);
		} else {

			setBackground(BUTTON_BG_DEFAULT);
		}
	}

	private void init() {
		setBorder(Constants.NOBORDER);

		setBackground(BUTTON_BG_DEFAULT);

		addChangeListener(this);

		setForeground(BUTTON_FG_DEFAULT);

		setFocusPainted(false);

		setFont(Constants.FONTSMALL);

		//setPreferredSize(new Dimension(getWidth() + 10, getHeight() + 5));
	}

}

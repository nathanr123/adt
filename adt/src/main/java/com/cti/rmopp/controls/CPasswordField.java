package com.cti.rmopp.controls;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPasswordField;

public class CPasswordField extends JPasswordField implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5519490206078072456L;

	private static final char BULLET_CHARACTER = '\u2022';

	private static final Color PWD_FG_DEFAULT = Color.BLACK;

	private static final Color PWD_BG_DEFAULT = Color.WHITE;

	private static final Color PWD_BG_SELECTION = new Color(239, 244, 255);

	private static final Color PWD_FG_SELECTION = Color.RED.brighter();

	private static final Color PWD_FG_DISABLED = Color.GRAY;

	private static final Color PWD_BG_DISABLED = new Color(239, 244, 255);

	public CPasswordField() {

		setBackground(PWD_BG_DEFAULT);

		setForeground(PWD_FG_DEFAULT);

		setSelectionColor(PWD_BG_SELECTION);

		setSelectedTextColor(PWD_FG_SELECTION);

		setBorder(Constants.NOBORDER);

		setFont(Constants.FONTDEFAULT);

		setEchoChar(BULLET_CHARACTER);

		addPropertyChangeListener(this);

	}

	public void propertyChange(PropertyChangeEvent evt) {

		if (!isEnabled()) {

			setBackground(PWD_BG_DISABLED);

			setForeground(PWD_FG_DISABLED);

		} else {

			setBackground(PWD_BG_DEFAULT);

			setForeground(PWD_FG_DEFAULT);
		}
	}

}

package com.cti.rmopp.controls;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

public class CRadioButton extends JRadioButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3389115528159139983L;

	private static final String IMAGE_PATH = "images\\";

	/*********************** RADIO BUTTON - IMAGES ************************/

	String RADIO_DEFAULT_IMAGE = "radio_defalut.png";

	String RADIO_DEFAULT_DISABLED_IMAGE = "radio_default_disabled.png";

	String RADIO_DEFAULT_SELECTED_IMAGE = "radio_selected.png";

	String RADIO_DEFAULT_SELECTED_DISABLED_IMAGE = "radio_selected_disabled.png";

	private static final Color RADIO_BG_DEFAULT = Color.DARK_GRAY;// Color.BLACK;

	private static final Color RADIO_FG_DEFAULT = Color.WHITE;

	private ImageIcon defaultIcon = new ImageIcon(
			((new ImageIcon(IMAGE_PATH + RADIO_DEFAULT_IMAGE)).getImage()).getScaledInstance(14, 14,
					java.awt.Image.SCALE_SMOOTH));

	private ImageIcon selectedIcon = new ImageIcon(
			((new ImageIcon(IMAGE_PATH + RADIO_DEFAULT_SELECTED_IMAGE)).getImage()).getScaledInstance(14, 14,
					java.awt.Image.SCALE_SMOOTH));

	private ImageIcon defaultDisableIcon = new ImageIcon(
			((new ImageIcon(IMAGE_PATH + RADIO_DEFAULT_DISABLED_IMAGE)).getImage()).getScaledInstance(14, 14,
					java.awt.Image.SCALE_SMOOTH));

	private ImageIcon selectedDisableIcon = new ImageIcon(
			((new ImageIcon(IMAGE_PATH + RADIO_DEFAULT_SELECTED_DISABLED_IMAGE)).getImage()).getScaledInstance(14, 14,
					java.awt.Image.SCALE_SMOOTH));

	public CRadioButton(String text) {
		super(text);

		setIcon(defaultIcon);

		setDisabledIcon(defaultDisableIcon);

		setDisabledSelectedIcon(selectedDisableIcon);

		setSelectedIcon(selectedIcon);

		setBorder(Constants.NOBORDER);

		setBackground(RADIO_BG_DEFAULT);

		setForeground(RADIO_FG_DEFAULT);

		setFocusable(false);

		setCursor(Constants.HANDCURSOR);

		setFont(Constants.FONTDEFAULT);
	}

}

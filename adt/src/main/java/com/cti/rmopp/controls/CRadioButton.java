package com.cti.rmopp.controls;

import java.awt.Color;

import javax.swing.ImageIcon;

import javax.swing.JRadioButton;

import static com.cti.rmopp.controls.ComponentFactory.getImageIcon;

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
	
	private static final Color RADIO_FG_ERROR = Color.RED;

	private ImageIcon defaultIcon = getImageIcon(IMAGE_PATH + RADIO_DEFAULT_IMAGE, 14, 14);

	private ImageIcon selectedIcon = getImageIcon(IMAGE_PATH + RADIO_DEFAULT_SELECTED_IMAGE, 14, 14);

	private ImageIcon defaultDisableIcon = getImageIcon(IMAGE_PATH + RADIO_DEFAULT_DISABLED_IMAGE, 14, 14);

	private ImageIcon selectedDisableIcon = getImageIcon(IMAGE_PATH + RADIO_DEFAULT_SELECTED_DISABLED_IMAGE, 14, 14);

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
	
	public void setErrorStatus(boolean isError) {

		if (isError)

			setForeground(RADIO_FG_ERROR);

		else

			setForeground(RADIO_FG_DEFAULT);
	}

}

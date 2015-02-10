package com.cti.rmopp.controls;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class CCheckBox extends JCheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7534936962321849072L;

	private static final String IMAGE_PATH = "images\\";

	/*********************** CHECK BOX - IMAGES ************************/

	private static final String CHECK_DEFAULT_IMAGE = "check_defalut.png";

	private static final String CHECK_DEFAULT_DISABLED_IMAGE = "check_default_disabled.png";

	private static final String CHECK_DEFAULT_SELECTED_IMAGE = "check_selected.png";

	private static final String CHECK_DEFAULT_SELECTED_DISABLED_IMAGE = "check_selected_disabled.png";

	/*********************** COLORS ************************/

	private static final Color CHECKBOX_BG_DEFAULT = Color.DARK_GRAY;// Color.BLACK;

	private static final Color CHECKBOX_FG_DEFAULT = Color.WHITE;

	private static final ImageIcon DEFAULTICON = new ImageIcon(
			((new ImageIcon(IMAGE_PATH + CHECK_DEFAULT_IMAGE)).getImage()).getScaledInstance(14, 14,
					java.awt.Image.SCALE_SMOOTH));

	private static final ImageIcon SELECTEDICON = new ImageIcon(((new ImageIcon(IMAGE_PATH
			+ CHECK_DEFAULT_SELECTED_IMAGE)).getImage()).getScaledInstance(14, 14, java.awt.Image.SCALE_SMOOTH));

	private static final ImageIcon DEFAULTDISABLEDICON = new ImageIcon(((new ImageIcon(IMAGE_PATH
			+ CHECK_DEFAULT_DISABLED_IMAGE)).getImage()).getScaledInstance(14, 14, java.awt.Image.SCALE_SMOOTH));

	private static final ImageIcon SELECTEDDISABLEDICON = new ImageIcon(
			((new ImageIcon(IMAGE_PATH + CHECK_DEFAULT_SELECTED_DISABLED_IMAGE)).getImage()).getScaledInstance(14, 14,
					java.awt.Image.SCALE_SMOOTH));

	public CCheckBox(String text) {
		super(text);

		init();

	}
	
	public CCheckBox(String text,boolean selected ) {
		super(text);
		
		init();
		
		setSelected(selected);
		
	}
	
	private void init(){
		

		setIcon(DEFAULTICON);

		setDisabledIcon(DEFAULTDISABLEDICON);

		setDisabledSelectedIcon(SELECTEDDISABLEDICON);

		setSelectedIcon(SELECTEDICON);

		setBorder(Constants.NOBORDER);

		setBackground(CHECKBOX_BG_DEFAULT);

		setForeground(CHECKBOX_FG_DEFAULT);

		setFocusable(false);

		setCursor(Constants.HANDCURSOR);

		setFont(Constants.FONTDEFAULT);
	}

}

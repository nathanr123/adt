package com.cti.rmopp.controls;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class CTextField extends JTextField implements PropertyChangeListener {

	private static final long serialVersionUID = 1090697043016638116L;

	private static final Color TEXT_FG_DEFAULT = Color.BLACK;

	private static final Color TEXT_FG_ERROR = Color.RED;

	private static final Color TEXT_BG_DEFAULT = Color.WHITE;

	private static final Color TEXT_BG_SELECTION = new Color(66, 131, 222).brighter();

	private static final Color TEXT_FG_SELECTION = Color.WHITE;

	private static final Color TEXT_FG_DISABLED = Color.GRAY;

	private static final Color TEXT_BG_DISABLED = new Color(239, 244, 255);
	
	private boolean isError = false;

	public CTextField() {

		super();

		init();

	}

	public CTextField(int row) {

		super(row);

		init();

	}
	
	public CTextField(String text) {

		super(text);

		init();

	}

	private void init() {

		setBackground(TEXT_BG_DEFAULT);

		setForeground(TEXT_FG_DEFAULT);

		setSelectionColor(TEXT_BG_SELECTION);

		setSelectedTextColor(TEXT_FG_SELECTION);

		setBorder(Constants.NOBORDER);

		setFont(Constants.FONTDEFAULT);
		
		setOpaque(true);

		addPropertyChangeListener(this);
	}

	public void propertyChange(PropertyChangeEvent arg0) {

		if (!isEnabled()) {

			setBackground(TEXT_BG_DISABLED);

			setForeground(TEXT_FG_DISABLED);

		} else {

			setBackground(TEXT_BG_DEFAULT);

			if(isError)
				
				setForeground(TEXT_FG_ERROR);
			
			else
				
				setForeground(TEXT_FG_DEFAULT);
		}

	}

	public void setErrorStatus(boolean isError) {

		this.isError = isError;
				
		if (isError) {

			setForeground(TEXT_FG_ERROR);

			setBorder(BorderFactory.createLineBorder(TEXT_FG_ERROR));

		} else {

			setForeground(TEXT_FG_DEFAULT);

			setBorder(Constants.NOBORDER);
		}
	}

}

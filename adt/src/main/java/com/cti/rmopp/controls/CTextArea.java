/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;

/**
 * @author nathanr_kamal
 *
 */
public class CTextArea extends JTextArea implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7415614469831850711L;

	private static final Color TEXTAREA_FG_DEFAULT = Color.BLACK;

	private static final Color TEXTAREA_FG_ERROR = Color.RED;

	private static final Color TEXTAREA_BG_DEFAULT = Color.WHITE;

	private static final Color TEXTAREA_BG_SELECTION = new Color(66, 131, 222).brighter();

	private static final Color TEXTAREA_FG_SELECTION = Color.WHITE;

	private static final Color TEXTAREA_FG_DISABLED = Color.GRAY;

	private static final Color TEXTAREA_BG_DISABLED = new Color(239, 244, 255);

	private boolean isError = false;

	/**
	 * 
	 */
	public CTextArea() {

		super();

		init();
	}

	public CTextArea(String arg0) {

		super(arg0);

		init();
	}

	/**
	 * @param arg0
	 */
	public CTextArea(Document arg0) {

		super(arg0);

		init();
	}

	public CTextArea(int arg0, int arg1) {

		super(arg0, arg1);

		init();
	}

	public CTextArea(String arg0, int arg1, int arg2) {

		super(arg0, arg1, arg2);

		init();
	}

	public CTextArea(Document arg0, String arg1, int arg2, int arg3) {

		super(arg0, arg1, arg2, arg3);

		init();
	}

	private void init() {

		SwingUtilities.updateComponentTreeUI(this);

		setBackground(TEXTAREA_BG_DEFAULT);

		setForeground(TEXTAREA_FG_DEFAULT);

		setSelectionColor(TEXTAREA_BG_SELECTION);

		setSelectedTextColor(TEXTAREA_FG_SELECTION);

		setBorder(Constants.NOBORDER);

		setFont(Constants.FONTDEFAULT);

		setOpaque(true);

		addPropertyChangeListener(this);
	}

	public void propertyChange(PropertyChangeEvent arg0) {

		if (!isEnabled()) {

			setBackground(TEXTAREA_BG_DISABLED);

			setForeground(TEXTAREA_FG_DISABLED);

		} else {

			setBackground(TEXTAREA_BG_DEFAULT);

			if (isError)

				setForeground(TEXTAREA_FG_ERROR);

			else

				setForeground(TEXTAREA_FG_DEFAULT);
		}

	}

	public void setErrorStatus(boolean isError) {

		this.isError = isError;

		if (isError) {

			setForeground(TEXTAREA_FG_ERROR);

			setBorder(BorderFactory.createLineBorder(TEXTAREA_FG_ERROR));

		} else {

			setForeground(TEXTAREA_FG_DEFAULT);

			setBorder(Constants.NOBORDER);
		}
	}

}

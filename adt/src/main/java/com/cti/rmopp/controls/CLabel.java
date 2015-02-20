package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.Border;

public class CLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5917930397899326190L;

	private static final Color LABEL_FG_DEFAULT = new Color(254, 254, 254);

	private static final Color LABEL_FG_ERROR = Color.RED;

	Border NOBORDER = null;

	public CLabel(String arg0) {

		super(arg0);

		setBorder(NOBORDER);

		setForeground(LABEL_FG_DEFAULT);

		setFont(Constants.FONTDEFAULT);

	}

	public CLabel(String arg0, boolean isTitle) {

		super(arg0);

		setBorder(NOBORDER);

		setForeground(LABEL_FG_DEFAULT);

		if (isTitle) {

			setFont(Constants.FONTTITLE);

			setHorizontalAlignment(CENTER);
		}

		else
			setFont(Constants.FONTDEFAULT);

	}

	public CLabel(String arg0, Icon image, boolean isTitle) {

		super(arg0);

		setBorder(NOBORDER);

		setIcon(image);

		setIconTextGap(8);

		setForeground(LABEL_FG_DEFAULT);

		if (isTitle) {

			setFont(Constants.FONTTITLE);

			setHorizontalAlignment(CENTER);

		} else

			setFont(Constants.FONTDEFAULT);

	}

	public CLabel(String arg0, Icon image, boolean isTitle, Font font) {

		super(arg0);

		setBorder(NOBORDER);

		setIcon(image);

		setIconTextGap(8);

		setForeground(LABEL_FG_DEFAULT);

		if (isTitle) {

			setHorizontalAlignment(CENTER);

		} else

			setFont(Constants.FONTDEFAULT);

		setFont(font);
	}

	public CLabel(Icon image) {

		setIcon(image);
	}

	@Override
	public JToolTip createToolTip() {

		return ComponentFactory.createToolTip();
	}

	public void setErrorStatus(boolean isError) {

		if (isError)

			setForeground(LABEL_FG_ERROR);

		else

			setForeground(LABEL_FG_DEFAULT);
	}

}

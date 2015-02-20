package com.cti.rmopp.controls;

import java.awt.Component;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class CScrollPane extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1229641246499679961L;

	public CScrollPane() {

		super();

		init();

		setVerticalScrollBar(new CScrollBar(JScrollBar.VERTICAL));

		setHorizontalScrollBar(new CScrollBar(JScrollBar.HORIZONTAL));

	}

	public CScrollPane(Component view) {

		super(view);

		init();

		setVerticalScrollBar(new CScrollBar(JScrollBar.VERTICAL));

		setHorizontalScrollBar(new CScrollBar(JScrollBar.HORIZONTAL));

	}

	public CScrollPane(int vsbPolicy, int hsbPolicy) {

		super(vsbPolicy, hsbPolicy);

		init();

		setVerticalScrollBar(new CScrollBar(JScrollBar.VERTICAL));

		setHorizontalScrollBar(new CScrollBar(JScrollBar.HORIZONTAL));

	}

	public CScrollPane(Component view, int vsbPolicy, int hsbPolicy) {

		super(view, vsbPolicy, hsbPolicy);

		init();

		setVerticalScrollBar(new CScrollBar(JScrollBar.VERTICAL));

		setHorizontalScrollBar(new CScrollBar(JScrollBar.HORIZONTAL));

	}

	private void init() {

		setBorder(Constants.NOBORDER);
	}

}

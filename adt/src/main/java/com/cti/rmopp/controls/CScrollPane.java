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
		setVerticalScrollBar(new CScrollBar(JScrollBar.VERTICAL));
		setHorizontalScrollBar(new CScrollBar(JScrollBar.HORIZONTAL));
		init();
	}

	public CScrollPane(Component view) {
		super(view);
		setVerticalScrollBar(new CScrollBar(JScrollBar.VERTICAL));
		setHorizontalScrollBar(new CScrollBar(JScrollBar.HORIZONTAL));
		init();
	}

	public CScrollPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		setVerticalScrollBar(new CScrollBar(JScrollBar.VERTICAL));
		setHorizontalScrollBar(new CScrollBar(JScrollBar.HORIZONTAL));
		init();
	}

	public CScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		setVerticalScrollBar(new CScrollBar(JScrollBar.VERTICAL));
		setHorizontalScrollBar(new CScrollBar(JScrollBar.HORIZONTAL));
		init();
	}

	private void init() {
		setBorder(Constants.NOBORDER);
	}

}

package com.cti.vpx.util;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class VPX_EmptyIcon implements Icon {

	private int width;

	private int height;

	public VPX_EmptyIcon() {

		this(0, 0);
	}

	public VPX_EmptyIcon(int width, int height) {

		this.width = width;

		this.height = height;
	}

	public int getIconHeight() {

		return height;
	}

	public int getIconWidth() {

		return width;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
	}

}
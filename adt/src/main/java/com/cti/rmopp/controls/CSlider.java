package com.cti.rmopp.controls;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.metal.MetalSliderUI;

public class CSlider extends JSlider {

	/**
	 * 
	 */

	private static final int SLIDER_THUMB_ICON_WIDTH = 17;

	private static final int SLIDER_THUMB_ICON_HEIGHT = 17;

	private static final Color SLIDER_BG_DEFAULT = Color.DARK_GRAY;

	private static final Color SLIDER_THUMB_ICON_DEFAULT = Color.GRAY.brighter().brighter();

	private static final Color SLIDER_BG_FILLED = Color.DARK_GRAY.brighter().brighter();

	private static final Color SLIDER_BG_NOT_FILLED = Color.GRAY.brighter();

	private static final long serialVersionUID = -8660059739149192882L;

	public CSlider() {

		IconUIResource specialThumbIcon = new IconUIResource(new SpecialThumbIcon());

		UIManager.put("Slider.horizontalThumbIcon", specialThumbIcon);

		UIManager.put("Slider.verticalThumbIcon", specialThumbIcon);

		UIManager.put("Slider.background", new ColorUIResource(SLIDER_BG_DEFAULT));

		setCursor(Constants.HANDCURSOR);

		setUI(new CTI_NVRSliderUI());
	}

	static class CTI_NVRSliderUI extends MetalSliderUI {

		private static final Stroke endStroke = new BasicStroke(8, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);

		public static ComponentUI createUI(JComponent c) {
			return new CTI_NVRSliderUI();
		}

		@Override
		public Dimension getPreferredSize(JComponent c) {

			Dimension d = super.getPreferredSize(c);

			if (slider.getOrientation() == JSlider.HORIZONTAL) {
				d.height += 10;
			} else {
				d.width += 10;
			}
			return d;
		}

		@Override
		public void paintTrack(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			if (slider.getOrientation() == JSlider.HORIZONTAL) {

				int cy = (trackRect.height / 2) - 2;

				g.translate(trackRect.x, trackRect.y + cy);

				g2.setStroke(endStroke);

				g2.setPaint(SLIDER_BG_FILLED);

				g2.drawLine(thumbRect.x, 2, trackRect.width, 2);

				g2.setPaint(SLIDER_BG_NOT_FILLED);

				g2.drawLine(0, 2, thumbRect.x, 2);

				g.translate(-trackRect.x, -(trackRect.y + cy));
			} else {

				int cx = (trackRect.width / 2) - 2;

				g.translate(trackRect.x + cx, trackRect.y);

				g2.setStroke(endStroke);

				g2.setPaint(SLIDER_BG_FILLED);

				g2.drawLine(2, 0, 2, thumbRect.y);

				g2.setPaint(SLIDER_BG_NOT_FILLED);

				g2.drawLine(2, thumbRect.y, 2, trackRect.height);

				g.translate(-(trackRect.x + cx), -trackRect.y);
			}
		}
	}

	static class SpecialThumbIcon implements Icon {

	
		public void paintIcon(Component c, Graphics g, int x, int y) {

			Graphics2D g2 = (Graphics2D) g;

			g2.setPaint(SLIDER_THUMB_ICON_DEFAULT);

			g.fillRect(x + 3, y + 3, 14, 11);
		}

	
		public int getIconWidth() {
			return SLIDER_THUMB_ICON_WIDTH;
		}


		public int getIconHeight() {
			return SLIDER_THUMB_ICON_HEIGHT;
		}
	}
	
}

package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class CScrollBar extends JScrollBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6281394979936421417L;

	/*********************** DIMENSIONS, SIZES, UNITS ************************/

	private static final int COMPONENT_IMAGEICON_WIDTH = 24;

	private static final int COMPONENT_IMAGEICON_HEIGHT = 24;

	private static enum componentStatus {
		DEFAULT, DEFAULT_DISABLED, SELECTED, SELECTED_DISABLED, DESELCETED, DESELECTED_DISABLED, INCREEMENT, DECREEMENT
	}

	/*********************** SCROLL BAR - IMAGES ************************/

	private static final String SCROLL_UP_IMAGE = "chevronup.png";

	private static final String SCROLL_DOWN_IMAGE = "chevrondown.png";

	private static final String SCROLL_LEFT_IMAGE = "chevronleft.png";

	private static final String SCROLL_RIGHT_IMAGE = "chevronright.png";

	private static final String IMAGE_PATH = "images\\";

	private static final Color SCROLLBAR_BUTTON_BG_DEFAULT = new Color(93, 93, 93);

	private static final Color SCROLLBAR_TRACK_DEFAULT = SCROLLBAR_BUTTON_BG_DEFAULT;

	private static final Color SCROLLBAR_THUMB_DEFAULT = new Color(206, 206, 206);

	private ImageIcon decreaseIcon;

	private ImageIcon increaseIcon;

	public CScrollBar() {

		super();

		setIcons();

		setUI(new CTIScrollbarUI());
	}

	public CScrollBar(int orientation) {

		super(orientation);

		setIcons();

		setUI(new CTIScrollbarUI());
	}

	@Override
	public void setOrientation(int orientation) {

		super.setOrientation(orientation);

		setIcons();

		setUI(new CTIScrollbarUI());
	}

	public CScrollBar(int orientation, int value, int extent, int min, int max) {

		super(orientation, value, extent, min, max);

		setIcons();

		setUI(new CTIScrollbarUI());
	}

	private void setIcons() {

		decreaseIcon = getImageIcon(componentStatus.DECREEMENT);

		increaseIcon = getImageIcon(componentStatus.INCREEMENT);
	}

	private JButton getScrollBarButtons(ImageIcon icon) {

		JButton btn = new JButton();

		btn.setBackground(SCROLLBAR_BUTTON_BG_DEFAULT);

		btn.setFocusPainted(false);

		btn.setBorder(Constants.NOBORDER);

		btn.setIcon(icon);

		btn.setRolloverEnabled(false);

		btn.setCursor(Constants.HANDCURSOR);

		btn.addChangeListener(new CTIStateChangeListener(btn));

		return btn;
	}

	class CTIScrollbarUI extends MetalScrollBarUI {

		private Image imageThumb, imageTrack;

		CTIScrollbarUI() {

			imageThumb = FauxImage.create(32, 32, SCROLLBAR_THUMB_DEFAULT);

			imageTrack = FauxImage.create(32, 32, SCROLLBAR_TRACK_DEFAULT);
		}

		@Override
		protected void paintThumb(Graphics g, JComponent c, Rectangle r) {

			g.setColor(Color.blue);

			((Graphics2D) g).drawImage(imageThumb, r.x, r.y, r.width, r.height, null);
		}

		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle r) {

			((Graphics2D) g).drawImage(imageTrack, r.x, r.y, r.width, r.height, null);
		}

		@Override
		protected JButton createDecreaseButton(int orientation) {

			return getScrollBarButtons(decreaseIcon);
		}

		@Override
		protected JButton createIncreaseButton(int orientation) {

			return getScrollBarButtons(increaseIcon);
		}
	}

	private class CTIStateChangeListener implements ChangeListener {

		JButton btn;

		public CTIStateChangeListener(JButton button) {

			btn = button;
		}

		public void stateChanged(ChangeEvent e) {

			ButtonModel model = btn.getModel();

			model.setPressed(false);
		}
	}

	public ImageIcon getImageIcon(componentStatus status) {

		String path = IMAGE_PATH;

		if (getOrientation() == JScrollBar.HORIZONTAL) {

			if (status == componentStatus.DECREEMENT) {

				path += SCROLL_LEFT_IMAGE;

			} else if (status == componentStatus.INCREEMENT) {

				path += SCROLL_RIGHT_IMAGE;
			}

		} else if (getOrientation() == JScrollBar.VERTICAL) {

			if (status == componentStatus.DECREEMENT) {

				path += SCROLL_UP_IMAGE;

			} else if (status == componentStatus.INCREEMENT) {

				path += SCROLL_DOWN_IMAGE;
			}
		}

		return ComponentFactory.getImageIcon(path, COMPONENT_IMAGEICON_WIDTH, COMPONENT_IMAGEICON_HEIGHT);
	}

	private static class FauxImage {

		static public Image create(int w, int h, Color c) {

			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = bi.createGraphics();

			g2d.setPaint(c);

			g2d.fillRect(0, 0, w, h);

			g2d.dispose();

			return bi;
		}
	}
}

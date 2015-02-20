/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * @author nathanr_kamal
 *
 */
public class CLed extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1956370283425704577L;

	public static final int SMALL = 0;

	public static final int MEDIUM = 1;

	public static final int LARGE = 2;

	public static final int DISABLED = 3;

	public static final int ON = 4;

	public static final int IDLE = 5;

	public static final int OFF = 6;

	private static final int LED_LARGE_WIDTH = 20;

	private static final int LED_LARGE_HEIGHT = 20;

	private static final int LED_MEDIUM_WIDTH = 16;

	private static final int LED_MEDIUM_HEIGHT = 16;

	private static final int LED_SMALL_WIDTH = 12;

	private static final int LED_SMALL_HEIGHT = 12;

	private static final Color LED_BG_DISABLED = Color.GRAY;

	private static final Color LED_BG_ON = new Color(35, 228, 22);// Color.GREEN;

	private static final Color LED_BG_IDLE = Color.YELLOW;

	private static final Color LED_BG_OFF = Color.RED;

	private static final Color LED_BORDER_BG_DEFAULT = Color.LIGHT_GRAY;

	private static final Border LED_BORDER_DEFAULT = new LineBorder(LED_BORDER_BG_DEFAULT);

	private int defaultStatus;

	public CLed() {

		init(MEDIUM, OFF);
	}

	public CLed(int size) {

		init(size, OFF);
	}

	public CLed(int size, int status) {

		init(size, status);
	}

	private void init(int size, int status) {

		setOpaque(true);

		setSize(size);

		setStatus(status);

		setBorder(LED_BORDER_DEFAULT);
	}

	public Dimension getSize() {

		return getPreferredSize();
	}

	public int getStatus() {

		return defaultStatus;
	}

	public void setSize(int size) {

		switch (size) {

		case SMALL:

			setPreferredSize(new Dimension(LED_SMALL_WIDTH, LED_SMALL_HEIGHT));

			break;

		case MEDIUM:

			setPreferredSize(new Dimension(LED_MEDIUM_WIDTH, LED_MEDIUM_HEIGHT));

			break;

		case LARGE:

			setPreferredSize(new Dimension(LED_LARGE_WIDTH, LED_LARGE_HEIGHT));

			break;

		}

	}

	@Override
	public JToolTip createToolTip() {

		return ComponentFactory.createToolTip();
	}

	public void setStatus(int status) {

		defaultStatus = status;

		switch (status) {

		case DISABLED:

			setBackground(LED_BG_DISABLED);

			setToolTipText("STATUS : DISABLED");

			break;

		case ON:

			setBackground(LED_BG_ON);

			setToolTipText("STATUS : ON");

			break;

		case IDLE:

			setBackground(LED_BG_IDLE);

			setToolTipText("STATUS : IDLE");

			break;

		case OFF:

			setBackground(LED_BG_OFF);

			setToolTipText("STATUS : OFF");

			break;
		}
	}
}

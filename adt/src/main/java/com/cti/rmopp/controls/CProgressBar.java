/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * @author nathanr_kamal
 *
 */
public class CProgressBar extends JProgressBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8896911173953812558L;

	private static final Color PROGRESSBAR_BG_DEFAULT = Color.GRAY;

	private static final Color PROGRESSBAR_FG_DEFAULT = new Color(66, 131, 222);

	private static final Color PROGRESSBAR_BG_SELECTION = Color.DARK_GRAY;

	private static final Color PROGRESSBAR_FG_SELECTION = Color.DARK_GRAY;

	private static final Color PROGRESSBAR_TEXT_DEFAULT = new Color(254, 254, 254);

	private static final Border PROGRESSBAR_BORDER_DEFAULT = BorderFactory.createLineBorder(PROGRESSBAR_BG_SELECTION);

	public CProgressBar() {
		init();
	}

	/**
	 * @param orient
	 */
	public CProgressBar(int orient) {
		super(orient);
		init();
	}

	/**
	 * @param newModel
	 */
	public CProgressBar(BoundedRangeModel newModel) {
		super(newModel);
		init();
	}

	/**
	 * @param min
	 * @param max
	 */
	public CProgressBar(int min, int max) {
		super(min, max);
		init();
	}

	/**
	 * @param orient
	 * @param min
	 * @param max
	 */
	public CProgressBar(int orient, int min, int max) {
		super(orient, min, max);
		init();
	}

	private void init() {

		UIManager.put("ProgressBar.background", PROGRESSBAR_BG_DEFAULT);

		UIManager.put("ProgressBar.border", PROGRESSBAR_BORDER_DEFAULT);

		UIManager.put("ProgressBar.font", Constants.FONTDEFAULT);

		UIManager.put("ProgressBar.foreground", PROGRESSBAR_FG_DEFAULT);

		UIManager.put("ProgressBar.highlight", PROGRESSBAR_BG_SELECTION);

		UIManager.put("ProgressBar.selectionBackground", PROGRESSBAR_BG_SELECTION);

		UIManager.put("ProgressBar.selectionForeground", PROGRESSBAR_FG_SELECTION);

		UIManager.put("ProgressBar.shadow", PROGRESSBAR_BG_SELECTION);

		SwingUtilities.updateComponentTreeUI(this);

		setUI(new BasicProgressBarUI() {			

			protected Color getSelectionBackground() {
				return PROGRESSBAR_TEXT_DEFAULT;
			}

			protected Color getSelectionForeground() {
				return PROGRESSBAR_TEXT_DEFAULT;
			}
		});
	}

	@Override
	public JToolTip createToolTip() {

		return ComponentFactory.createToolTip();
	}

}

package com.cti.rmopp.controls;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class CSpinner extends JSpinner {

	/**
	 * 
	 */

	private static final int COMPONENT_IMAGEICON_WIDTH = 24;

	private static final int COMPONENT_IMAGEICON_HEIGHT = 24;

	private static final long serialVersionUID = -2168511991641276202L;

	/*********************** SPINNER BAR - IMAGES ************************/

	private static final String SPINNER_UP_IMAGE = "chevronup.png";

	private static final String SPINNER_DOWN_IMAGE = "chevrondown.png";

	private static final String IMAGE_PATH = "images\\";

	private static final Color SPINNER_BUTTON_BG_DEFAULT = new Color(93, 93, 93);

	private static final Color SPINNER_BORDER_COLOR_DEFAULT = Color.DARK_GRAY;

	private static final Border SPINNER_BORDER_DEFAULT = new LineBorder(SPINNER_BORDER_COLOR_DEFAULT);

	public CSpinner() {
		init();
	}

	private void init() {

		setBorder(SPINNER_BORDER_DEFAULT);

		setEditor(new Editor(this));

		setUI(new CSpinnerUI());
	}

	private class CSpinnerUI extends BasicSpinnerUI {

		@Override
		protected Component createPreviousButton() {

			Component component = createButton(IMAGE_PATH + SPINNER_DOWN_IMAGE);

			super.createPreviousButton();

			if (component != null) {

				installPreviousButtonListeners(component);
			}

			return component;
		}

		@Override
		protected Component createNextButton() {

			Component component = createButton(IMAGE_PATH + SPINNER_UP_IMAGE);

			if (component != null) {

				installNextButtonListeners(component);
			}

			return component;
		}

		private Component createButton(String url) {

			JButton btn = new JButton();

			btn.setBackground(SPINNER_BUTTON_BG_DEFAULT);

			btn.setFocusPainted(false);

			btn.setBorder(Constants.NOBORDER);

			btn.setIcon(getImageIcon(url));

			btn.setRolloverEnabled(false);

			btn.setCursor(Constants.HANDCURSOR);
			
			btn.addChangeListener(new CTIStateChangeListener(btn));

			return btn;
		}

	}

	public ImageIcon getImageIcon(String url) {

		return new ImageIcon(((new ImageIcon(url)).getImage()).getScaledInstance(COMPONENT_IMAGEICON_WIDTH,
				COMPONENT_IMAGEICON_HEIGHT, java.awt.Image.SCALE_SMOOTH));
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

	private class Editor extends JPanel implements ChangeListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5251777939969966483L;
		
		private final Color TEXT_FG_DEFAULT = Color.BLACK;

		private final Color TEXT_BG_DEFAULT = Color.WHITE;

		
		private JLabel label = new JLabel();

		private Editor(JSpinner spinner) {
			
			setLayout(new FlowLayout(FlowLayout.LEADING));
			
			label.setForeground(TEXT_FG_DEFAULT);
			
			label.setFont(Constants.FONTDEFAULT);
			
			setBackground(TEXT_BG_DEFAULT);
			
			setBorder(new EmptyBorder(5, 6, 5, 0));
			
			add(label);
			
			spinner.addChangeListener(this);
			
		}

		public void stateChanged(ChangeEvent e) {
			
			JSpinner spinner = (JSpinner) e.getSource();
			
			label.setText(spinner.getValue().toString());
		}
	}
}
package com.cti.rmopp.controls;

import static com.cti.rmopp.controls.ComponentFactory.getImageIcon;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
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

	private static final int COMPONENT_IMAGEICON_WIDTH = 24;

	private static final int COMPONENT_IMAGEICON_HEIGHT = 24;

	private static final long serialVersionUID = -2168511991641276202L;

	private static final String SPINNER_UP_IMAGE = "chevronup.png";

	private static final String SPINNER_DOWN_IMAGE = "chevrondown.png";

	private static final String IMAGE_PATH = "images\\";

	private static final Color SPINNER_BUTTON_BG_DEFAULT = new Color(93, 93, 93);

	private static final Color SPINNER_BORDER_COLOR_DEFAULT = Color.DARK_GRAY;

	private static final Color SPINNER_BORDER_COLOR_ERROR = Color.RED;

	private static final Border SPINNER_BORDER_DEFAULT = new LineBorder(SPINNER_BORDER_COLOR_DEFAULT);

	private static final Border SPINNER_BORDER_ERROR = new LineBorder(SPINNER_BORDER_COLOR_ERROR);

	private static final Color SPINNER_TEXT_FG_DEFAULT = Color.BLACK;

	private static final Color SPINNER_TEXT_BG_DEFAULT = Color.WHITE;

	private Editor spinnerEditor;

	public CSpinner() {

		super();

		init();
	}

	private void init() {

		setBorder(SPINNER_BORDER_DEFAULT);

		spinnerEditor = new Editor(this);

		setEditor(spinnerEditor);

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

			btn.setIcon(getImageIcon(url, COMPONENT_IMAGEICON_WIDTH, COMPONENT_IMAGEICON_HEIGHT));

			btn.setRolloverEnabled(false);

			btn.setCursor(Constants.HANDCURSOR);

			btn.addChangeListener(new CTIStateChangeListener(btn));

			return btn;
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

	private class Editor extends JPanel implements ChangeListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5251777939969966483L;

		private JLabel label = new JLabel();

		private Editor(JSpinner spinner) {

			setLayout(new FlowLayout(FlowLayout.LEADING));

			label.setForeground(SPINNER_TEXT_FG_DEFAULT);

			label.setFont(Constants.FONTDEFAULT);

			setBackground(SPINNER_TEXT_BG_DEFAULT);

			setBorder(new EmptyBorder(5, 6, 5, 0));

			add(label);

			spinner.addChangeListener(this);

		}

		public void setFG(Color colors) {

			label.setForeground(colors);
		}

		public void stateChanged(ChangeEvent e) {

			JSpinner spinner = (JSpinner) e.getSource();

			label.setText(spinner.getValue().toString());
		}
	}

	public void setErrorStatus(boolean isError) {

		if (isError) {

			spinnerEditor.setFG(SPINNER_BORDER_COLOR_ERROR);

			setBorder(SPINNER_BORDER_ERROR);

		} else {

			spinnerEditor.setFG(SPINNER_TEXT_FG_DEFAULT);

			setBorder(SPINNER_BORDER_DEFAULT);
		}
	}
}
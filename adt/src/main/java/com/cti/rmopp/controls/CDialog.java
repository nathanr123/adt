/**
 * 
 */
package com.cti.rmopp.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static com.cti.rmopp.controls.ComponentFactory.getImageIcon;

/**
 * @author nathanr_kamal
 *
 */
public class CDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5553840031771930764L;

	private static final int WINDOW_BUTTON_WIDTH_DEFAULT = 24;

	private static final int WINDOW_BUTTON_HEIGHT_DEFAULT = 24;

	private static final String IMAGE_PATH = "images\\";

	private boolean isCloseButton = true;

	private CPanel buttonPanel, titlePanel;

	private CLabel titleLbl;

	private TitleBarButton btnClose;

	private CPanel contentPanel;

	private ImageIcon ic;

	public CDialog() {
		
		super();
		
		init();
	}

	public CDialog(String title) {
		
		super();

		init();

		setAppTitle(title);
	}

	private void init() {

		setUndecorated(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		ic = getImageIcon(IMAGE_PATH + "cornet.png", 18, 18);

		setIconImage(ic.getImage());

		JPanel jp = (JPanel) getContentPane();

		jp.setBorder(new LineBorder(Color.GRAY));

		loadTitleBar();
	}

	private void loadTitleBar() {

		titlePanel = ComponentFactory.createPanel(new BorderLayout());

		titleLbl = ComponentFactory.createLabel("", ic, true);

		buttonPanel = ComponentFactory.createPanel();

		titlePanel.add(titleLbl, BorderLayout.CENTER);

		loadButtons();

		titlePanel.add(buttonPanel, BorderLayout.EAST);

		contentPanel = ComponentFactory.createPanel(new BorderLayout());

		contentPanel.add(titlePanel, BorderLayout.NORTH);

		getContentPane().add(contentPanel, BorderLayout.NORTH);
	}

	private void loadButtons() {

		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		if (isCloseButton) {

			if (btnClose == null)
				
				btnClose = new TitleBarButton("close");
			
			btnClose.setPreferredSize(new Dimension(WINDOW_BUTTON_WIDTH_DEFAULT, WINDOW_BUTTON_HEIGHT_DEFAULT));

			btnClose.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					close();

				}
			});
			buttonPanel.add(btnClose);
		}

	}

	public void setAppJMenuBar(CMenuBar menuBar) {

		contentPanel.add(menuBar, BorderLayout.CENTER);
	}

	public void setAppTitle(String title) {

		setTitle(title);

		titleLbl.setText(title);
	}

	public void close() {
		dispose();
	}

	private class TitleBarButton extends JButton implements ChangeListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2419313605594289553L;

		private String imagepath;

		public TitleBarButton(String path) {

			this.imagepath = path;

			setOpaque(true);

			setFocusable(false);

			setContentAreaFilled(false);

			setBackground(titlePanel.getBackground());

			setFocusPainted(false);

			setIcon(getIconImage(imagepath, false, 16, 16));

			setBorder(Constants.NOBORDER);

			addChangeListener(this);
		}

		public void stateChanged(ChangeEvent e) {

			ButtonModel model = this.getModel();

			this.setOpaque(true);

			if (model.isRollover()) {

				this.setBackground(this.getBackground().brighter().brighter());

				setIcon(getIconImage(imagepath, false, 18, 18));

			} else if (model.isPressed()) {

				setIcon(getIconImage(imagepath, true, 18, 18));

			} else {

				setBackground(titlePanel.getBackground());

				setIcon(getIconImage(imagepath, false, 16, 16));
			}

		}

		private ImageIcon getIconImage(String path, boolean isHover, int w, int h) {

			if (isHover)
				
				return getImageIcon(IMAGE_PATH + path + "_active.png", w, h);

			else
				return getImageIcon(IMAGE_PATH + path + ".png", w, h);
		}

	}

}
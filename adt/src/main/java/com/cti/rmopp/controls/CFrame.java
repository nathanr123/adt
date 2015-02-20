/**
 * 
 */
package com.cti.rmopp.controls;

import static com.cti.rmopp.controls.ComponentFactory.getImageIcon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author nathanr_kamal
 *
 */
public class CFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5553840031771930764L;	
	
	private static final int WINDOW_BUTTON_WIDTH_DEFAULT = 24;
	
	private static final int WINDOW_BUTTON_HEIGHT_DEFAULT = 24;

	private static final String IMAGE_PATH = "images\\";

	private boolean isMinimizeButton = false;

	private boolean isRestoreButton = false;

	private boolean isCloseButton = false;

	private CPanel buttonPanel, titlePanel;

	private CLabel titleLbl;

	private TitleBarButton btnMinimize, btnRestore, btnClose;

	private CPanel contentPanel;

	private ImageIcon ic;

	private CTaskBar taskBar;
	
	
	public CFrame() {
		init();
	}

	public CFrame(String title, boolean isMinimizeButton, boolean isRestoreButton, boolean isCloseButton) {

		this.isMinimizeButton = isMinimizeButton;

		this.isRestoreButton = isRestoreButton;

		this.isCloseButton = isCloseButton;

		init();
	}

	private void init() {

		setUndecorated(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		ic = getImageIcon(IMAGE_PATH + "cornet.png", 18, 18);


		setIconImage(ic.getImage());

		loadTitleBar();

		loadStatusBar();

	}

	private void loadStatusBar() {
		taskBar = ComponentFactory.createTaskBar();

		getContentPane().add(taskBar, BorderLayout.SOUTH);
	}

	private void loadTitleBar() {

		titlePanel = ComponentFactory.createPanel(new BorderLayout());

		titleLbl = ComponentFactory.createLabel("", ic, true,Constants.FONTDEFAULT);

		buttonPanel = ComponentFactory.createPanel();
		
		titlePanel.add(titleLbl, BorderLayout.CENTER);

		loadButtons();

		titlePanel.add(buttonPanel, BorderLayout.EAST);

		contentPanel = ComponentFactory.createPanel(new BorderLayout());

		contentPanel.add(titlePanel, BorderLayout.NORTH);

		getContentPane().add(contentPanel, BorderLayout.NORTH);
	}
	
	public void addContentPane(JComponent comp){
		getContentPane().add(comp, BorderLayout.CENTER);
	}

	private void loadButtons() {

		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

		if (isMinimizeButton) {
			if (btnMinimize == null)
				btnMinimize = new TitleBarButton("minimize");
			btnMinimize.setPreferredSize(new Dimension(WINDOW_BUTTON_WIDTH_DEFAULT, WINDOW_BUTTON_HEIGHT_DEFAULT));
			buttonPanel.add(btnMinimize);
		}
		if (isRestoreButton) {
			if (btnRestore == null)
				btnRestore = new TitleBarButton("restore");
			btnRestore.setPreferredSize(new Dimension(WINDOW_BUTTON_WIDTH_DEFAULT, WINDOW_BUTTON_HEIGHT_DEFAULT));
			buttonPanel.add(btnRestore);
		}

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

	public void setAppCToolBar(CToolBar toolbar)
	{
		contentPanel.add(toolbar, BorderLayout.SOUTH);
	}
	public void setAppJMenuBar(CMenuBar menuBar) {

		contentPanel.add(menuBar, BorderLayout.CENTER);
	}

	public void setAppTitle(String title) {

		setTitle(title);

		titleLbl.setText(title);
	}

	public void close() {
		taskBar.stopUpdates();

		dispose();
	}

	public boolean isMinimizeButton() {
		return isMinimizeButton;
	}

	public void setMinimizeButton(boolean isMinimizeButton) {
		this.isMinimizeButton = isMinimizeButton;

		loadButtons();

	}

	public boolean isRestoreButton() {
		return isRestoreButton;
	}

	public void setRestoreButton(boolean isRestoreButton) {
		this.isRestoreButton = isRestoreButton;

		loadButtons();

	}

	public boolean isCloseButton() {
		return isCloseButton;
	}

	public void setCloseButton(boolean isCloseButton) {
		this.isCloseButton = isCloseButton;

		loadButtons();

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

			setIcon(getIconImage(imagepath, false,12,12));

			setBorder(Constants.NOBORDER);

			addChangeListener(this);
		}

		public void stateChanged(ChangeEvent e) {

			ButtonModel model = this.getModel();
			
			this.setOpaque(true);	
			
			if (model.isRollover()) {
				
				this.setBackground(this.getBackground().brighter().brighter());
				
				setIcon(getIconImage(imagepath, false,14,14));

			} else if (model.isPressed()) {

				setIcon(getIconImage(imagepath, true,14,14));
				
			} else {				
				
				setBackground(titlePanel.getBackground());
				
				setIcon(getIconImage(imagepath, false,12,12));
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

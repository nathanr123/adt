/**
 * 
 */
package com.cti.rmopp.controls;

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
	
	private static final int WINDOW_BUTTON_WIDTH_DEFAULT = 34;
	
	private static final int WINDOW_BUTTON_HEIGHT_DEFAULT = 34;

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

		ic = new ImageIcon(((new ImageIcon(IMAGE_PATH + "cornet.png")).getImage()).getScaledInstance(24, 24,
				java.awt.Image.SCALE_SMOOTH));

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

		titleLbl = ComponentFactory.createLabel("", ic, true);

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

		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

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

			setIcon(getIconImage(imagepath, false,16,16));

			setBorder(Constants.NOBORDER);

			addChangeListener(this);
		}

		public void stateChanged(ChangeEvent e) {

			ButtonModel model = this.getModel();
			
			this.setOpaque(true);	
			
			if (model.isRollover()) {
				
				this.setBackground(this.getBackground().brighter().brighter());
				
				setIcon(getIconImage(imagepath, false,18,18));

			} else if (model.isPressed()) {

				setIcon(getIconImage(imagepath, true,18,18));
				
			} else {				
				
				setBackground(titlePanel.getBackground());
				
				setIcon(getIconImage(imagepath, false,16,16));
			}

		}		
		
		private ImageIcon getIconImage(String path, boolean isHover,int w,int h) {

			if (isHover)
				return new ImageIcon(((new ImageIcon(IMAGE_PATH + path + "_active.png")).getImage()).getScaledInstance(
						w, h, java.awt.Image.SCALE_SMOOTH));

			else
				return new ImageIcon(((new ImageIcon(IMAGE_PATH + path + ".png")).getImage()).getScaledInstance(w, h,
						java.awt.Image.SCALE_SMOOTH));
		}

	}

}

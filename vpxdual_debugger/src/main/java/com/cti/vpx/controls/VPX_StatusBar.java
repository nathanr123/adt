package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;

public class VPX_StatusBar extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JPanel leftPanel;

	protected JPanel rightPanel;

	private boolean isAppRunning = true;

	private JProgressBar progressBar;

	private Runtime jvm = Runtime.getRuntime();

	private long totalMemory = jvm.totalMemory();

	private long usedMemory;

	private int usedPct;

	private JLabel lblStatus;

	private JLabel lblProc;

	private JLabel lblLAN;

	private VPX_Shortcuts shortcuts = new VPX_Shortcuts();

	public VPX_StatusBar() {

		// createPartControl();
		init();

		loadComponents();

		updateLAN();
	}

	private void init() {

		setLayout(new BorderLayout());

		setPreferredSize(new Dimension(getWidth(), 23));
	}

	private void loadComponents() {

		leftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 3));

		leftPanel.setOpaque(false);

		add(leftPanel, BorderLayout.WEST);

		rightPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 3));

		rightPanel.setOpaque(false);

		add(rightPanel, BorderLayout.EAST);

		addLeftComponents();

		addRightComponents();

	}

	protected void createPartControl() {

		setLayout(new BorderLayout());

		setPreferredSize(new Dimension(getWidth(), 23));

		leftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 3));

		leftPanel.setOpaque(false);

		add(leftPanel, BorderLayout.WEST);

		rightPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 3));

		rightPanel.setOpaque(false);

		add(rightPanel, BorderLayout.EAST);
	}

	private void addLeftComponents() {

		lblStatus = VPXComponentFactory.createJLabel("");

		leftPanel.add(lblStatus);

		JLabel lbl = VPXComponentFactory.createJLabel("");

		lbl.setPreferredSize(new Dimension(10, 25));

		leftPanel.add(lbl);

	}

	private void addRightComponents() {

		lblProc = VPXComponentFactory.createJLabel("");

		lblLAN = VPXComponentFactory.createJLabel("");

		JRadioButton jrb = new JRadioButton();

		jrb.setIcon(VPXUtilities.getImageIcon(VPXConstants.Icons.ICON_SHORTCUT_NAME, 12, 12));

		jrb.setToolTipText("<html>Show Shortcuts<br>Press Ctrl + F2</html>");

		jrb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showShorcuts();

			}
		});

		addRightComponent(lblProc);

		addRightComponent(lblLAN);

		if (Boolean.valueOf(VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.GENERAL_MEMORY))) {

			progressBar = new JProgressBar();

			progressBar.setPreferredSize(new Dimension(200, 25));

			progressBar.setStringPainted(true);

			addRightComponent(progressBar);
		}

		addRightComponent(jrb);

		// MemoryMonitor memMonitor = new MemoryMonitor();

		// memMonitor.execute();

		Thread th = new Thread(new MemMonitor());

		th.start();
	}

	public void setLeftComponent(JComponent component) {

		leftPanel.add(component);
	}

	public void addRightComponent(JComponent component) {

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));

		panel.add(new SeparatorPanel(Color.GRAY, Color.WHITE));

		panel.add(component);

		rightPanel.add(panel);
	}

	private void clearStatus() {

		lblStatus.setText("");

	}

	public void showShorcuts() {

		shortcuts.setVisible(true);
	}

	private void updateMemory() {

		if (progressBar != null) {

			usedMemory = totalMemory - jvm.freeMemory();

			usedPct = (int) ((100 * usedMemory) / totalMemory);

			progressBar.setValue((int) usedPct);

			String ttText = String.format(
					"<html>Memory Usage : %d %%<br>Memory Using by Application : %d MB<br>Free Memory %d MB<br>Total Memory : %d MB<br>Memory Allocated : %d MB<br>Maximum Memory : %d MB</html>",
					usedPct, usedMemory / VPXConstants.MB, jvm.freeMemory() / VPXConstants.MB,
					totalMemory / VPXConstants.MB, totalMemory / VPXConstants.MB, jvm.maxMemory() / VPXConstants.MB);

			progressBar.setToolTipText(ttText);

			progressBar.setString(usedPct + " % " + usedMemory / VPXConstants.MB + "MB Out of "
					+ totalMemory / VPXConstants.MB + "MB");
		}
	}

	public void updateStatus(String status) {

		lblStatus.setText(VPXUtilities.getPathAsLinuxStandard(status));
	}

	public void updateLAN() {

		lblLAN.setText("Connected : " + VPXSessionManager.getCurrentLANName());

		lblLAN.setToolTipText(
				String.format("<html>Name : %s<br>IP Address : %s<br>Subnet Mask : %s<br>Gateway : %s</html>",
						VPXSessionManager.getCurrentLANName(), VPXSessionManager.getCurrentIP(),
						VPXSessionManager.getCurrentSubnet(), VPXSessionManager.getCurrentGateway()));

	}

	public void updateProcessor() {

		if (VPXSessionManager.getCurrentProcessor().length() > 0) {

			lblProc.setText("Selected Processor : " + VPXSessionManager.getCurrentProcessor());

			lblProc.setToolTipText(String.format("<html>Sub System : %s<br>Processor : %s<br>IP Address : %s</html>",
					VPXSessionManager.getCurrentSubSystem(), VPXSessionManager.getCurrentProcType(),
					VPXSessionManager.getCurrentProcessor()));
		} else {

			lblProc.setText("");

			lblProc.setToolTipText(null);
		}

	}

	public void stopMemoryMonitor() {

		isAppRunning = false;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		int y = 0;

		g.setColor(new Color(156, 154, 140));

		g.drawLine(0, y, getWidth(), y);

		y++;

		g.setColor(new Color(196, 194, 183));

		g.drawLine(0, y, getWidth(), y);

		y++;

		g.setColor(new Color(218, 215, 201));

		g.drawLine(0, y, getWidth(), y);

		y++;

		g.setColor(new Color(233, 231, 217));

		g.drawLine(0, y, getWidth(), y);

		y = getHeight() - 3;

		g.setColor(new Color(233, 232, 218));

		g.drawLine(0, y, getWidth(), y);

		y++;

		g.setColor(new Color(233, 231, 216));

		g.drawLine(0, y, getWidth(), y);

		y++;

		g.setColor(new Color(221, 221, 220));

		g.drawLine(0, y, getWidth(), y);
	}

	class SeparatorPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		protected Color leftColor;

		protected Color rightColor;

		public SeparatorPanel(Color leftColor, Color rightColor) {

			this.leftColor = leftColor;

			this.rightColor = rightColor;

			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {

			g.setColor(leftColor);

			g.drawLine(0, 0, 0, getHeight());

			g.setColor(rightColor);

			g.drawLine(1, 0, 1, getHeight());
		}

	}

	class MemMonitor implements Runnable {

		@Override
		public void run() {
			while (isAppRunning) {

				try {

					updateMemory();

					clearStatus();

					Thread.sleep(5000);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}

	}

}

package com.cti.vpx.controls.tab;

import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.TabbedPaneUI;

import com.cti.vpx.util.VPXUtilities;

public class VPX_TabbedPane extends JTabbedPane {

	private static final long serialVersionUID = -8801063107184397010L;

	private int overTabIndex = -1;

	private VPX_BasicTabPaneUI paneUI;

	public VPX_TabbedPane(boolean isDetachable, boolean isClosable) {

		super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		paneUI = new VPX_TabbedPaneUI();

		paneUI.setMaxIcon(isDetachable);

		paneUI.setCloseIcon(isClosable);

		super.setUI(paneUI);
	}

	/*
	 * 
	 * public VPX_TabbedPane(boolean enhancedUI) {
	 * super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	 * 
	 * if (enhancedUI) paneUI = new VPX_TabbedPaneUI(); else paneUI = new
	 * VPX_BasicTabPaneUI();
	 * 
	 * super.setUI(paneUI); }
	 */
	public int getOverTabIndex() {
		return overTabIndex;
	}

	public boolean isCloseEnabled() {
		return paneUI.isCloseEnabled();
	}

	public boolean isMaxEnabled() {
		return paneUI.isMaxEnabled();
	}

	public void setTabLayoutPolicy(int tabLayoutPolicy) {
	}

	public void setTabPlacement(int tabPlacement) {
	}

	public void setUI(TabbedPaneUI ui) {
	}

	public void setCloseIcon(boolean b) {
		paneUI.setCloseIcon(b);
	}

	public void setMaxIcon(boolean b) {
		paneUI.setMaxIcon(b);
	}

	public void closeTab(int index) {
		removeTabAt(index);
	}

	public void detachTab(int index) {

		if (index < 0 || index >= getTabCount())
			return;

		final JFrame frame = new JFrame();
		
		frame.setIconImage(VPXUtilities.getAppIcon());

		Window parentWindow = SwingUtilities.windowForComponent(this);

		final int tabIndex = index;
		final JComponent c = (JComponent) getComponentAt(tabIndex);

		final Icon icon = getIconAt(tabIndex);
		final String title = getTitleAt(tabIndex);
		final String toolTip = getToolTipTextAt(tabIndex);
		final Border border = c.getBorder();

		removeTabAt(index);

		c.setPreferredSize(c.getSize());

		frame.setTitle(title);
		frame.getContentPane().add(c);
		frame.setLocation(parentWindow.getLocation());
		frame.pack();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				frame.dispose();

				insertTab(title, icon, c, toolTip, Math.min(tabIndex, getTabCount()));

				c.setBorder(border);
				setSelectedComponent(c);
			}

		});

		WindowFocusListener windowFocusListener = new WindowFocusListener() {
			long start;

			long end;

			public void windowGainedFocus(WindowEvent e) {
				start = System.currentTimeMillis();
			}

			public void windowLostFocus(WindowEvent e) {
				end = System.currentTimeMillis();
				long elapsed = end - start;
				// System.out.println(elapsed);
				if (elapsed < 100)
					frame.toFront();

				frame.removeWindowFocusListener(this);
			}
		};

		frame.addWindowFocusListener(windowFocusListener);

		frame.setVisible(true);

		frame.toFront();

	}

	public void fireCloseTabEvent(MouseEvent e, int overTabIndex) {
		this.overTabIndex = overTabIndex;
		closeTab(overTabIndex);
	}

	public void fireMaxTabEvent(MouseEvent e, int overTabIndex) {
		this.overTabIndex = overTabIndex;

		detachTab(overTabIndex);

	}

	public void fireDoubleClickTabEvent(MouseEvent e, int overTabIndex) {
		this.overTabIndex = overTabIndex;
		detachTab(overTabIndex);
	}

	public void firePopupOutsideTabEvent(MouseEvent e) {
		this.overTabIndex = -1;

	}

}

package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;

import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;

public class VPX_StatusBar extends JPanel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 7921801919677398732L;

    private boolean isAppRunning = true;

    private JProgressBar progressBar;

    private Runtime jvm = Runtime.getRuntime();

    private long totalMemory = jvm.totalMemory();

    private long usedMemory;

    private int usedPct;

    private JPanel memBar;

    private JLabel statusLbl;

    public VPX_StatusBar() {
	setLayout(new BorderLayout());

	statusLbl = VPXComponentFactory.createJLabel("");

	add(statusLbl, BorderLayout.LINE_START);

	if (Boolean.valueOf(VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.GENERAL_MEMORY))) {
	    memBar = getMemoryPanel();

	    add(memBar, BorderLayout.EAST);
	}

	setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

	setPreferredSize(new Dimension(VPXUtilities.getScreenWidth(), VPXUtilities.getScreenHeight() / 35));
    }

    private JPanel getMemoryPanel() {

	JPanel memPanel = VPXComponentFactory.createJPanel();

	memPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

	progressBar = new JProgressBar();

	progressBar.setPreferredSize(new Dimension(200, 25));

	progressBar.setStringPainted(true);

	memPanel.add(progressBar);

	MemoryMonitor memMonitor = new MemoryMonitor();

	memMonitor.execute();

	return memPanel;
    }

    private void updateMemory() {
	usedMemory = totalMemory - jvm.freeMemory();

	usedPct = (int) ((100 * usedMemory) / totalMemory);

	progressBar.setValue((int) usedPct);

	String ttText = String
		.format("<html>Memory Usage : %d %%<br>Memory Using by Application : %d MB<br>Free Memory %d MB<br>Total Memory : %d MB<br>Memory Allocated : %d MB<br>Maximum Memory : %d MB</html>",
			usedPct, usedMemory / VPXConstants.MB, jvm.freeMemory() / VPXConstants.MB,
			totalMemory / VPXConstants.MB, totalMemory / VPXConstants.MB, jvm.maxMemory() / VPXConstants.MB);

	progressBar.setToolTipText(ttText);

	progressBar.setString(usedPct + " % " + usedMemory / VPXConstants.MB + "MB Out of " + totalMemory / VPXConstants.MB
		+ "MB");

    }

    public void updateStatus(String status) {

	statusLbl.setText(status);
    }

    public void stopMemoryMonitor() {

	isAppRunning = false;
    }

    class MemoryMonitor extends SwingWorker<Void, Void> {

	@Override
	protected Void doInBackground() throws Exception {

	    while (isAppRunning) {

		updateMemory();

		Thread.sleep(5000);
	    }
	    return null;
	}

    }

}
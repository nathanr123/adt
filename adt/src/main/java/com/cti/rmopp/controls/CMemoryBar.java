package com.cti.rmopp.controls;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CMemoryBar extends CPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7921801919677398732L;

	private CLabel dateNtimeLbl;

	private boolean isAppRunning = true;

	private CProgressBar progressBar;

	private Runtime jvm = Runtime.getRuntime();

	private long totalMemory = jvm.totalMemory();

	private long usedMemory;

	private int usedPct;

	private static final int MB = 1024 * 1024;

	private DateFormat date = new SimpleDateFormat("YYYY-MM-dd");

	private DateFormat tooltipF = new SimpleDateFormat("EEEE, MMMM dd, YYYY  hh:mm:ss.SSS a");

	private DateFormat time = new SimpleDateFormat("hh:mm a");

	private Date d;

	public CMemoryBar() {

		// setDark(true);

		setLayout(new FlowLayout(FlowLayout.RIGHT));

		dateNtimeLbl = ComponentFactory.createLabel("Date and time");

		dateNtimeLbl.setFont(Constants.FONTSMALL);

		progressBar = ComponentFactory.createprogressBar();

		progressBar.setPreferredSize(new Dimension(200, 25));

		progressBar.setStringPainted(true);

		add(progressBar);

		add(dateNtimeLbl);

		Thread th = new Thread(new Runnable() {

			int i = -1;

			public void run() {
				while (isAppRunning) {

					if (i == -1) {

						updateMemory();

						updateDateAndTime();

						updateThreads();

					} else if (i == 60) {

						updateMemory();

						updateDateAndTime();

					} else if (i >= 180) {

						updateThreads();

						i = 0;
					}

					try {

						Thread.sleep(1000);

					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}

		}, "Memory Monitor");

		th.start();
	}

	private void updateDateAndTime() {

		d = Calendar.getInstance().getTime();

		dateNtimeLbl.setToolTipText(tooltipF.format(d));// "<html>" +
														// tooltipF.format(d).replace("-",
														// "<br>") + "</html>");

		dateNtimeLbl.setText("<html>" + date.format(d) + "<br>" + time.format(d) + "</html>");
	}

	private void updateMemory() {
		usedMemory = totalMemory - jvm.freeMemory();

		usedPct = (int) ((100 * usedMemory) / totalMemory);

		progressBar.setValue((int) usedPct);

		String ttText = String
				.format("<html>Memory Usage : %d %%<br>Memory Using by Application : %d MB<br>Free Memory %d MB<br>Total Memory : %d MB<br>Memory Allocated : %d MB<br>Maximum Memory : %d MB</html>",
						usedPct, usedMemory / MB, jvm.freeMemory() / MB, totalMemory / MB, totalMemory / MB,
						jvm.maxMemory() / MB);

		progressBar.setToolTipText(ttText);

		progressBar.setString(usedPct + " % " + usedMemory / MB + "MB Out of " + totalMemory / MB + "MB");

	}

	private void updateThreads() {

		/*
		 * Thread[] threadList = getAllThreads();
		 * 
		 * for (Thread thread : threadList) System.out.println(new
		 * StringBuilder(
		 * ).append(thread.getThreadGroup().getName()).append("::")
		 * .append(thread
		 * .getName()).append("::PRIORITY:-").append(thread.getPriority()));
		 */
	}

	ThreadGroup rootThreadGroup = null;

	ThreadGroup getRootThreadGroup() {

		if (rootThreadGroup != null)

			return rootThreadGroup;

		ThreadGroup tg = Thread.currentThread().getThreadGroup();

		ThreadGroup ptg;

		while ((ptg = tg.getParent()) != null)

			tg = ptg;

		return tg;
	}

	Thread[] getAllThreads() {

		final ThreadGroup root = getRootThreadGroup();

		final ThreadMXBean thbean = ManagementFactory.getThreadMXBean();

		int nAlloc = thbean.getThreadCount();

		int n = 0;

		Thread[] threads;

		do {

			nAlloc *= 2;

			threads = new Thread[nAlloc];

			n = root.enumerate(threads, true);

		} while (n == nAlloc);

		return java.util.Arrays.copyOf(threads, n);
	}

	public void stopMemoryMonitor() {

		isAppRunning = false;
	}

}
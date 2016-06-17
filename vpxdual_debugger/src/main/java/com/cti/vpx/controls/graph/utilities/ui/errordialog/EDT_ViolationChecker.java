package com.cti.vpx.controls.graph.utilities.ui.errordialog;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JComponent;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

public final class EDT_ViolationChecker extends RepaintManager
{
	public static void install()
	{
		RepaintManager.setCurrentManager(new EDT_ViolationChecker());
	}

	private EDT_ViolationChecker()
	{
	}

	@Override
	public synchronized void addInvalidComponent(JComponent jComponent)
	{
		checkThreadViolation(jComponent);
		super.addInvalidComponent(jComponent);
	}

	@Override
	public synchronized void addDirtyRegion(JComponent jComponent, int i, int i1, int i2, int i3)
	{
		checkThreadViolation(jComponent);
		super.addDirtyRegion(jComponent, i, i1, i2, i3);
	}

	private static void checkThreadViolation(JComponent c)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			return;
		}
		
		final Exception exception = new Exception();
		boolean repaint = false;
		boolean fromSwing = false;
		final StackTraceElement[] stackTrace = exception.getStackTrace();
		for (StackTraceElement st : stackTrace)
		{
			if (repaint && st.getClassName().startsWith("javax.swing."))
			{
				fromSwing = true;
			}
			if ("repaint".equals(st.getMethodName()))
			{
				repaint = true;
			}
		}
		
		// You're allowed to call repaint from outside the EDT, but if we saw a repaint()
		// that was triggered by a Swing component method, then that would be illegal.
		if (repaint && !fromSwing)
		{
			// no problems here, since repaint() is thread safe
			return;
		}
		
		dumpMessage(c);
	}

	private static void dumpMessage(JComponent c)
	{
		System.out.println("----------Wrong Thread START");
		System.out.println(getStracktraceAsString(new Exception()));
		dumpComponentTree(c);
		System.out.println("----------Wrong Thread END");
	}

	private static String getStracktraceAsString(Exception e)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		printWriter.flush();
		return stringWriter.getBuffer().toString();
	}

	private static void dumpComponentTree(Component c)
	{
		System.out.println("----------Component Tree");
		int tabCount = 0;
		for (; c != null; c = c.getParent())
		{
			printTabIndent(tabCount);
			System.out.println(c);
			printTabIndent(tabCount);
			System.out.println("Showing:" + c.isShowing() + " Visible: " + c.isVisible());
			tabCount++;
		}
	}

	private static void printTabIndent(int tabCount)
	{
		for (int i = 0; i < tabCount; i++)
		{
			System.out.print("\t");
		}
	}
}

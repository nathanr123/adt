package com.cti.vpx.controls.graph.utilities.ui.errordialog;

/**
 * Install an event handler that will display an error dialog if an exception happens on the event thread.
 * 
 * @see <a href="http://lhankins.blogspot.com/#390328764"> </a>
 * @see <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5076724"> </a>
 */
public class UncaughtAwtExceptionHandler
{

	public static void install()
	{
		System.setProperty("sun.awt.exception.handler", UncaughtAwtExceptionHandler.class.getName());
	}

	public UncaughtAwtExceptionHandler()
	{
	}

	public void handle(Throwable ex)
	{
		ex.printStackTrace();
		ErrorDialog.show(ex);
	}
}
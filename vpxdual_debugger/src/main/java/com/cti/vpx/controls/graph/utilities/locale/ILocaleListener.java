package com.cti.vpx.controls.graph.utilities.locale;

/**
 * A locale listener that fires on the event thread, making it simple to use.
 */
public interface ILocaleListener {
	/**
	 * This method is called when the locale has been changed. The listener
	 * should then update the visual components.
	 */
	void componentsLocaleChanged();
}

/**
 * 
 */
package com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase;

public interface ISliderAxisListener
{
	/**
	 * Called every time the slide value changes.
	 */
	void sliderValueChanged(SliderAxis control, String sSliderName, float fSliderValue);
}
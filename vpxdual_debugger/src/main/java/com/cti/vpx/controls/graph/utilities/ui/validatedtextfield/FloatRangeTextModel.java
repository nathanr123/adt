package com.cti.vpx.controls.graph.utilities.ui.validatedtextfield;

public class FloatRangeTextModel extends FloatTextModel
{
	private final float minValue, maxValue;
	
	public FloatRangeTextModel(float initialValue, float minValue, float maxValue)
	{
		super(initialValue);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	public FloatRangeTextModel(float minValue, float maxValue)
	{
		this(minValue, minValue, maxValue);
	}

	
	@Override
	public boolean isValid(String text)
	{
		if (!super.isValid(text))
			return false;
		final float f = textToObject(text);
		return f >= minValue && f <= maxValue;
	}
}
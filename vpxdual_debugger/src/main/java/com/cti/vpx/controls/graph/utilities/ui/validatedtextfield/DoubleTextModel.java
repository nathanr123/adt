package com.cti.vpx.controls.graph.utilities.ui.validatedtextfield;

public class DoubleTextModel extends DecimalTextModel
{
	private double value;
	
	public DoubleTextModel(double initialValue)
	{
		this.value = initialValue;
	}
	
	public Double textToObject(String text)
	{
		return parse(text).doubleValue();
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public void setValue(Object obj)
	{
		this.value = ((Number) obj).doubleValue();
	}
}
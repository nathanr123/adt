package com.cti.vpx.controls.graph.utilities.ui.validatedtextfield;

public class IntTextModel extends DecimalTextModel
{
	private int value;
	
	public IntTextModel(int initialValue)
	{
		this.value = initialValue;
		format.setParseIntegerOnly(true);
	}

	public Integer textToObject(String text)
	{
		return parse(text).intValue();
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public void setValue(Object obj)
	{
		this.value = ((Number) obj).intValue();
	}
}
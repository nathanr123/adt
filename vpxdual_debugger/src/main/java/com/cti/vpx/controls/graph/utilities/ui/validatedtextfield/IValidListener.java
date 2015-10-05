package com.cti.vpx.controls.graph.utilities.ui.validatedtextfield;

import java.util.EventListener;

public interface IValidListener extends EventListener
{
	void validChanged(boolean valid);
}

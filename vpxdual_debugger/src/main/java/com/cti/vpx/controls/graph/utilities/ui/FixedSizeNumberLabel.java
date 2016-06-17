package com.cti.vpx.controls.graph.utilities.ui;

import java.awt.Font;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * A label that sizes itself to the number format required, and does not change
 * size.
 * 
 * Mostly used in graph co-ordinate panel displays.
 */
public class FixedSizeNumberLabel extends FixedSizeLabel {
	private static final long serialVersionUID = 6696011230310052681L;
	private DecimalFormat oFormat;
	private String pattern;
	private String oSuffix = "";

	public FixedSizeNumberLabel() {
		
		setFont( new Font("Serif", Font.BOLD, 14));
	}

	public DecimalFormat getFormat() {
		return oFormat;
	}

	/**
	 * uses the same pattern strings as DecimalFormat. But any extra whitespace
	 * is treated as significant for sizing the label.
	 */
	public void setFormat(String pattern) {
		this.oFormat = new DecimalFormat(pattern);
		this.pattern = pattern;
		updateSamplePattern();
	}

	private void updateSamplePattern() {
		setSamplePattern('-' + pattern.replace(' ', '0') + oSuffix);
	}

	public void setValue(double d) {
		if (oFormat != null) {

			// String.format("%x ",(Long.parseLong(String.valueOf(l), 16) &
			// 0x0ff))

			String s = String.format("%02x ", new BigDecimal(d).byteValue()).toUpperCase();

			setText(s);
		} else {
			String s = String.format("%02x ", new BigDecimal(d).byteValue()).toUpperCase();

			setText(s);// + oSuffix);
		}
	}

	public void setValue(Number l) {
		if (oFormat != null) {
			setText(oFormat.format(l));// + oSuffix);
		} else {
			setText("" + l);// + oSuffix);
		}
	}

	public String getSuffix() {
		return oSuffix;
	}

	public void setSuffix(String suffix) {
		final String oldValue = oSuffix;
		oSuffix = suffix;
		if (oSuffix == null || oldValue == null || !oSuffix.equals(oldValue)) {
			updateSamplePattern();
		}
	}
}

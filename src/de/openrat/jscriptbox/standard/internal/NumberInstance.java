package de.openrat.jscriptbox.standard.internal;


import de.openrat.jscriptbox.context.BaseScriptable;
import de.openrat.jscriptbox.context.ContextPrimitive;

import java.text.DecimalFormat;

public class NumberInstance extends BaseScriptable implements ContextPrimitive
{
	private Integer value;

	/**
	 * Number constructor.
	 * @param $value
	 */
	public NumberInstance(Integer value)
	{
		this.value = value;
	}

	public String toString()
	{
		return "" + this.value;
	}


	public String toFixed( int digits )
	{
		return new DecimalFormat("0"+digits).format(this.value);
	}

	public Integer toNumber() {
		return this.value;
	}

}
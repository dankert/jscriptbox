package de.openrat.jscriptbox.standard.internal;


import de.openrat.jscriptbox.context.BaseScriptable;

public class BooleanInstance extends BaseScriptable
{
	private boolean value;

	/**
	 * Number constructor.
	 * @param value
	 */
	public BooleanInstance( boolean value)
	{
		this.value = value;
	}

	public String toString()
	{
		return this.value?"true":"false";
	}

/*
	public function __invoke( value )
	{
		return new BooleanInstance( value );
	}
	public function length()
	{
		return 1;
	}
	*/

	public boolean isTrue() {
		return value;
	}
	public boolean isFalse() {
		return !isTrue();
	}
}
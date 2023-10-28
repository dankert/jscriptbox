package de.openrat.jscriptbox.standard.internal;


import de.openrat.jscriptbox.context.BaseScriptable;
import de.openrat.jscriptbox.context.ContextPrimitive;
import de.openrat.jscriptbox.context.Scriptable;

public class ObjectInstance extends BaseScriptable implements ContextPrimitive
{
	private Object value;

	/**
	 * Number constructor.
	 * @param value
	 */
	public ObjectInstance(Object value)
	{
		this.value = value;
	}

	public String toString() {
		return this.value.toString();
	}

	public Object getObject() {
		return value;
	}

	/**
	 * Detects if the object is a "safe" object. Safe objects are implementing the interface "Scriptable" and are always accessible.
	 *
	 * @return
	 */
	public boolean isScriptable() {
		return Scriptable.class.isAssignableFrom(this.value.getClass());
	}
}
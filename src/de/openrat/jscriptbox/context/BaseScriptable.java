package de.openrat.jscriptbox.context;

import de.openrat.jscriptbox.standard.Helper;

import java.util.Arrays;

public class BaseScriptable implements Scriptable
{
	/**
	 * Standard String representation of a Scriptable Object.
	 * This object becomes "Stringable".
	 * This string may be used in userscripts, if the object is used as a string, maybe by mistake.
	 *
	 * This method may be overwritten by subclasses.
	 *
	 * @return string
	 */
	public String toString()
	{
		return "Script object";
	}


	/**
	 * a useful help function which outputs all properties and methods of this objects.
	 *
	 * @return string a short info about this object
	 */
	public String help()
	{
		return Helper.getHelp(this);
	}


	public String getClassName() {
		return this.getClass().getName();
	}

	public String values() {
		return Arrays.asList(this.getClass().getDeclaredMethods()).toString();
	}
	public String keys() {
		return Arrays.asList(this.getClass().getDeclaredMethods()).toString();

	}
}
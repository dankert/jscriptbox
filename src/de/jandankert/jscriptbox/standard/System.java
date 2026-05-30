package de.jandankert.jscriptbox.standard;

import de.jandankert.jscriptbox.context.BaseScriptable;

/**
 * System information.
 */
public class System extends BaseScriptable
{
	/**
	 * Runtime version
	 */
	public String version;


	public System()
	{
		this.version = Runtime.version().toString();
	}

	/**
	 * @param name
	 * @return array|false|string
	 */
	public String env( String name ) {

		return java.lang.System.getenv( "SCRIPTBOX_" + name );
	}
}
package de.openrat.jscriptbox.standard;

import de.openrat.jscriptbox.context.BaseScriptable;

/**
 * System information.
 *
 * @package dsl\standard
 */
public class System extends BaseScriptable
{
	/**
	 * runtime
	 * @var string
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
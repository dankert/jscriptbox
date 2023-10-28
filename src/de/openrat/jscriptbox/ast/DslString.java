

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.parser.Token;

import java.util.Queue;

/**
 * A String representation.
 */
public class DslString implements DslStatement
{
	private CharSequence string;

	/**
	 * DslString constructor.
	 * @param string
	 */
	public DslString(CharSequence string)
	{
		this.string = string;
	}


	public Object execute( Context context ) {

		return this.string;
	}

	public void parse(Queue<Token> tokens)
	{
	}
}
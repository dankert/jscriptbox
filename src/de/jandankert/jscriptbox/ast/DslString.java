

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.parser.Token;

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

	@Override
	public String toString() {
		return "\"" + this.string + "\"";
	}
}
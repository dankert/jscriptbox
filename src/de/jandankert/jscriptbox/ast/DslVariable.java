

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.exception.ScriptRuntimeException;
import de.jandankert.jscriptbox.parser.Token;

import java.util.Queue;

class DslVariable implements DslStatement
{
	public String name;

	/**
	 * @param name
	 */
	public DslVariable( String name)
	{
		this.name = name;
	}


	public Object execute(Context context ) throws ScriptRuntimeException {

		return context.get( this.name );
	}

	@Override
	public void parse(Queue<Token> tokens) {

	}


	@Override
	public String toString() {
		return "Variable '" + name + "'";
	}
}
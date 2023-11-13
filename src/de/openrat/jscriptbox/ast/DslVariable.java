

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

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
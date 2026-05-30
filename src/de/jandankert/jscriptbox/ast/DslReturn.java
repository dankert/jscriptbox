

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.exception.ScriptParserException;
import de.jandankert.jscriptbox.exception.ScriptRuntimeException;
import de.jandankert.jscriptbox.parser.Token;

import java.util.Queue;

class DslReturn implements DslStatement
{
	private DslStatement value;

	public DslReturn(Queue<Token> expressionTokens ) throws ScriptParserException {
		this.value = new DslExpression( expressionTokens );
	}

	public Object execute(Context context ) throws ScriptRuntimeException {

		return this.value.execute( context );
	}

	public void parse(Queue<Token> tokens)
	{
	}


	@Override
	public String toString() {
		return "RETURN";
	}
}
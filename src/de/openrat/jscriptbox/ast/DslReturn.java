

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

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
}
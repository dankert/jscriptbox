

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

import java.util.Queue;

class DslThrow implements DslStatement
{
	private DslStatement value;

	public DslThrow(Queue<Token> expressionTokens ) throws ScriptParserException {
		this.value = new DslExpression( expressionTokens );
	}

	@Override
	public void parse(Queue<Token> tokens) {

	}

	@Override
	public Object execute(Context context) throws ScriptRuntimeException {
		Object value = this.value.execute( context );
		throw new ScriptRuntimeException( value.toString() );
	}


	@Override
	public String toString() {
		return "THROW";
	}
}
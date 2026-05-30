

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.exception.ScriptParserException;
import de.jandankert.jscriptbox.exception.ScriptRuntimeException;
import de.jandankert.jscriptbox.exception.ScriptUserDefinedException;
import de.jandankert.jscriptbox.parser.Token;

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
		throw new ScriptUserDefinedException( value.toString() );
	}


	@Override
	public String toString() {
		return "THROW";
	}
}
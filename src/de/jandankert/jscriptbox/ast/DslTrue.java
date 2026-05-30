

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.exception.ScriptRuntimeException;
import de.jandankert.jscriptbox.parser.Token;

import java.util.Queue;

class DslTrue implements DslStatement
{

	@Override
	public void parse(Queue<Token> tokens) {

	}

	@Override
	public Object execute(Context context) throws ScriptRuntimeException {
		return null;
	}

	@Override
	public String toString() {
		return "TRUE";
	}
}
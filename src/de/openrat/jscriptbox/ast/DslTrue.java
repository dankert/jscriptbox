

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

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
}
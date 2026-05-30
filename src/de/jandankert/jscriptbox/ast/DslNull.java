

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.parser.Token;

import java.util.Queue;

class DslNull implements DslStatement
{
	@Override
	public void parse(Queue<Token> tokens) {

	}

	@Override
	public Object execute(Context context) {
		return null;
	}

	@Override
	public String toString() {
		return "NULL";
	}
}
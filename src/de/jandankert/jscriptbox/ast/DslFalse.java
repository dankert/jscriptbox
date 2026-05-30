

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.parser.Token;

import java.util.Queue;

class DslFalse implements DslStatement
{
	public Object execute(Context context)
	{

		return false;
	}

	@Override
	public void parse(Queue<Token> tokens) {

	}


	@Override
	public String toString() {
		return "FALSE";
	}
}
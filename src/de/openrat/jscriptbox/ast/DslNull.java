

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.parser.Token;

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
}
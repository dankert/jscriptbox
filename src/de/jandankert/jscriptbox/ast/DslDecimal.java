

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.parser.Token;

import java.util.Queue;

public class DslDecimal implements DslStatement
{
	//private statements;

	public Object execute( Context context ) {
		return null;
	}

	public void parse(Queue<Token> tokens)
	{
	}


	@Override
	public String toString() {
		return "DEC";
	}
}
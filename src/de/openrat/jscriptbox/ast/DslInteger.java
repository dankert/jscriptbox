

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.parser.Token;

import java.util.Queue;

class DslInteger implements DslStatement
{
	private Long number;

	/**
	 * DslInteger constructor.
	 * @param number
	 */
	public DslInteger(long number)
	{
		this.number = number;
	}


	public Object execute(Context context ) {

		return this.number;
	}

	public void parse(Queue<Token> tokens)
	{
		Token firstToken = tokens.peek();
		this.number = Long.parseLong( firstToken.getValue() );
	}

	public Long getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return "" + number;
	}
}


package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

class DslSequence implements DslStatement
{
	public DslStatement left;
	public DslStatement right;


	/**
	 * DslSequence constructor.
	 * @param left
	 * @param right
	 */
	public DslSequence( DslStatement left, DslStatement right)
	{
		this.left  = left;
		this.right = right;
	}


	public Object execute( Context context ) throws ScriptRuntimeException {

		// Creating a sequence
		Object left  = this.left.execute( context );
		Object right = this.right.execute( context );


		// cast to array
		List leftList;
		if   ( left instanceof List )
			leftList  = (List) left;
		else
			leftList = Arrays.asList(left);

		if   ( right instanceof List )
			leftList.addAll( (List) right);
		else
			leftList.add( right );

		return leftList;
	}

	public void parse(Queue<Token> tokens)
	{
	}


	@Override
	public String toString() {
		return "SEQUENCE";
	}
}
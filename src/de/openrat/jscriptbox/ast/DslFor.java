

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;
import de.openrat.jscriptbox.standard.internal.ArrayInstance;

import java.util.Queue;

class DslFor implements DslStatement
{
	private String name;
	private DslExpression list;
	private DslStatementList statements;

	/**
	 * DslFor constructor.
	 *
	 * @param name String
	 * @param headerToken DslToken[]
	 * @param bodyTokens DslToken[]
	 */
	public DslFor(String name, Queue<Token> headerToken, Queue<Token> bodyTokens) throws ScriptParserException {
		this.name = name;
		this.list = new DslExpression( headerToken );
		this.statements = new DslStatementList( bodyTokens );
	}


	public Object execute(Context context ) throws ScriptRuntimeException {

		Object list = this.list.execute( context );

		if   ( ! (list instanceof ArrayInstance) )
			throw new ScriptRuntimeException("for loop: value must be an array/a list");

		ArrayInstance array = (ArrayInstance) list;
		Context copiedContext = context.copy();
		for( Object loopVar : array.getInternalValue()  ) {

			// copy loop var to current loop context
			copiedContext.put( this.name, loopVar );

			// Execute "for" block
			this.statements.execute( copiedContext );
		}
		return null; // nothing to return from the loop
	}

	public void parse(Queue<Token> tokens)
	{
	}


	@Override
	public String toString() {
		return "FOR-LOOP";
	}
}
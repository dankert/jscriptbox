

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.exception.ScriptParserException;
import de.jandankert.jscriptbox.exception.ScriptRuntimeException;
import de.jandankert.jscriptbox.parser.Token;

import java.util.Queue;

class DslIf implements DslStatement
{

	/**
	 * Expression for the condition
	 * @var DslExpression
	 */
	private DslExpression condition;

	/**
	 * @var DslStatementList
	 */
	private DslStatementList pos;

	/**
	 * @var DslStatementList
	 */
	private DslStatementList neg;

	public Object execute(Context context ) throws ScriptRuntimeException {

		Object conditionValue = this.condition.execute(context);

		if   ( conditionValue instanceof DslInteger && ((DslInteger)conditionValue).getNumber()>0 )
			return this.pos.execute( context );
		else
			return this.neg.execute( context );
	}

	public DslIf(Queue<Token> condition, Queue<Token> positive, Queue<Token> negative) throws ScriptParserException {
		this.condition = new DslExpression( condition );
		this.pos = new DslStatementList( positive );
		this.neg = new DslStatementList( negative );
	}

	public void parse(Queue<Token> tokens)
	{
	}


	@Override
	public String toString() {
		return "IF";
	}
}
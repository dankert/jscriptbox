

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;
import de.openrat.jscriptbox.parser.TokenEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * This is a self-defined scriptbox-function.
 */
class DslFunction extends DslElement implements DslStatement
{
	/**
	 * @var String[]
	 */
	public List<String> parameters;

	/**
	 * @var DslStatementList
	 */
	public DslStatementList body;

	/**
	 * creates the function.
	 *
	 * @param array context
	 * @return mixed|void
	 * @throws ScriptRuntimeException
	 */
	public Object execute( Context context ) throws ScriptRuntimeException {

		//clonedContext = context;
		return this.body.execute( context );
	}

	/**
	 * DslFunction constructor.
	 *
	 * @param functionParameter DslToken[]
	 * @param functionBody DslStatement
	 * @throws ScriptParserException
	 */
	public DslFunction(Queue<Token> functionParameter, Queue<Token> functionBody ) throws ScriptParserException {
		this.parameters = new ArrayList<>();

		for( List<Token> parameter : splitByComma( functionParameter ) ) {

			if   ( parameter.size() > 1 )
				throw new ScriptParserException("function parameter must be a single name",parameter.get(0));
			Token nameToken = parameter.get(0);
			if   ( nameToken.getType() == TokenEnum.T_NONE ) // no parameter
				continue; // this is a parameterless function
			if   ( nameToken.getType() != TokenEnum.T_STRING )
				throw new ScriptParserException("function parameter must be a name",nameToken);

			this.parameters.add( nameToken.getValue() );
		}

		this.body = new DslStatementList( functionBody );
	}

	public void parse(Queue<Token> tokens)
	{
	}

}
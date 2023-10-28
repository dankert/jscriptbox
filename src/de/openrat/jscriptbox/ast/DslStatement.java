

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

import java.util.Queue;

public interface DslStatement
{
	/**
	 * Parses a list of tokens.
	 * @param tokens DslToken[] List of tokens
	 */
	public void parse(Queue<Token> tokens ) throws ScriptParserException;


	/**
	 * Executes this statement.
	 *
	 * @param context array Context of execution.
	 * @return mixed
	 */
	public Object execute( Context context ) throws ScriptRuntimeException;

}

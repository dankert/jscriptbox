package de.openrat.jscriptbox.parser;

import de.openrat.jscriptbox.ast.DslStatement;
import de.openrat.jscriptbox.ast.DslStatementList;
import de.openrat.jscriptbox.exception.ScriptParserException;

import java.util.Queue;

/**
 * Creates an abstract syntax tree (AST).
 */
public class AstParser
{
	/**
	 * @throws ScriptParserException
	 */
	public DslStatement parse(Queue<Token> tokens ) throws ScriptParserException {

		return new DslStatementList( tokens );
	}
}
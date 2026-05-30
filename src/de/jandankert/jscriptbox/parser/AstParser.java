package de.jandankert.jscriptbox.parser;

import de.jandankert.jscriptbox.ast.DslStatement;
import de.jandankert.jscriptbox.ast.DslStatementList;
import de.jandankert.jscriptbox.exception.ScriptParserException;

import java.util.Queue;

/**
 * Creates an abstract syntax tree (AST).
 */
public class AstParser
{
	/**
	 * Parse all tokens and creats an AST.
	 *
	 * @param tokens a list of all tokens.
	 * @throws ScriptParserException if the tokens could not be parsed
	 */
	public DslStatement parse(Queue<Token> tokens ) throws ScriptParserException {

		return new DslStatementList( tokens );
	}
}
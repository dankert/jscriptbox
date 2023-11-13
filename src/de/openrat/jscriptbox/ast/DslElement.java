

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.parser.Token;
import de.openrat.jscriptbox.parser.TokenEnum;

import java.util.*;

class DslElement
{

	/**
	 * Detects a group.
	 * A groupp is a group of statements enclosed in "( ... )".
	 *
	 * @param tokens DslToken[]
	 * @return DslToken[]
	 * @throws ScriptParserException
	 */
	protected Queue<Token> getBracket(Queue<Token> tokens ) throws ScriptParserException {

		return this.getGroup(tokens, TokenEnum.T_BRACKET_OPEN, TokenEnum.T_BRACKET_CLOSE);
	}

	protected Queue<Token> getGroup(Queue<Token> tokens, TokenEnum begin, TokenEnum end ) throws ScriptParserException {
		Queue<Token> groupTokens = new LinkedList<>();
		int depth = 0;

		Token nextToken = tokens.poll();

		if (nextToken == null)
			throw new ScriptParserException("Unexpecting end, missing closing group",0);

		if (nextToken.getType() != begin)
			throw new ScriptParserException("Expecting '"+begin+"'", nextToken);

		while (true) {
			nextToken = tokens.poll();
			if (nextToken == null)
				throw new ScriptParserException("Missing '"+end+"'.",0);
			if (nextToken.getType() == begin)
				depth += 1;
			if (nextToken.getType() == end)
				if (depth == 0)
					return groupTokens;
				else
					depth--;

			groupTokens.add( nextToken );
		}
	}

	/**
	 * Detects a block.
	 * A block is a group of statements enclosed in "{ ... }".
	 *
	 * @param tokens DslToken[]
	 * @return DslToken[]
	 * @throws ScriptParserException
	 */
	protected Queue<Token> getBlock(Queue<Token> tokens) throws ScriptParserException {

		return getGroup(tokens, TokenEnum.T_BLOCK_BEGIN, TokenEnum.T_BLOCK_END);
	}

	/**
	 * Searches for a single Statement or for a block.
	 *
	 * @param tokens DslToken[]
	 * @return DslToken[]
	 * @throws ScriptParserException
	 */
	protected Queue<Token> getStatementOrBlock(Queue<Token> tokens) throws ScriptParserException {
		if (tokens.size() == 0)
			return new LinkedList<>();

		Token firstToken = tokens.peek();

		if (firstToken.getType() == TokenEnum.T_BLOCK_BEGIN)
			return this.getBlock(tokens);
		else
			return this.getSingleStatement(tokens,true);
	}


	/**
	 * Gets the first single statement out of the tokens.
	 *
	 * @param tokens DslToken[]
	 * @return DslToken[]
	 * @throws ScriptParserException
	 */
	protected Queue<Token> getSingleStatement(Queue<Token> tokens, boolean withEnd ) throws ScriptParserException {
		int depth = 0;
		final Queue<Token> statementTokens = new LinkedList<>();

		while ( true ) {

			Token nextToken = tokens.poll();

			if (nextToken == null)
				throw new ScriptParserException("unrecognized statement");

			if (depth == 0 && nextToken.getType() == TokenEnum.T_STATEMENT_END) {
				if   ( withEnd )
					statementTokens.add(nextToken);
				return statementTokens;
			}

			if (nextToken.getType() == TokenEnum.T_BLOCK_BEGIN)
				depth++;
			if (nextToken.getType() == TokenEnum.T_BLOCK_END)
				depth--;
			if (depth < 0)
				throw new ScriptParserException("Unexpected closing block", nextToken);

			statementTokens.add( nextToken );
		}
	}


	/**
	 * Split tokens on comma separator.
	 *
	 * @param DslToken[] functionParameter
	 * @return DslToken[][]
	 */
	protected List<List<Token>> splitByComma(Queue<Token> functionParameter)
	{
		List<List<Token>> parts = new ArrayList<>();
		List<Token> act   = new ArrayList<>();

		for ( Token token : functionParameter ) {

			if   ( token.getType() == TokenEnum.T_OPERATOR && token.getValue().equals(",")) {
				parts.add(act);
				act.clear(); // Cleanup
				continue;
			}

			act.add( token );
		}

		if   ( ! act.isEmpty() )
			parts.add(act);

		return parts;
	}


	@Override
	public String toString() {
		return "ELEMENT";
	}
}
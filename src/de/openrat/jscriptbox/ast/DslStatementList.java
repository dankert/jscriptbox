

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;
import de.openrat.jscriptbox.parser.TokenEnum;

import java.util.*;

public class DslStatementList extends DslElement implements DslStatement
{
	public DslStatementList(Queue<Token> tokens) throws ScriptParserException {

		this.parse( tokens );
	}

	private List<DslStatement> statements;

	private Map<String,DslFunction> functions = new HashMap<>();

	/**
	 * @param tokens DslToken[]
	 * @throws ScriptParserException
	 */
	public void parse(Queue<Token> tokens) throws ScriptParserException {
		this.parseTokens(tokens);
	}

	public Object execute(Context context) throws ScriptRuntimeException
	{
		// Auto hoisting for functions: Add functions to context.
		Context currentContext = context.copy();
		currentContext.putAll( this.functions );

		for (DslStatement statement : this.statements ) {

			Object value = statement.execute(currentContext);

			if (statement instanceof DslReturn)
				return value; // Return to the caller
		}

		return null;
	}



	/**
	 * @param tokens DslToken[]
	 * @throws ScriptParserException
	 */
	public void parseTokens(Queue<Token> tokens) throws ScriptParserException {

		this.statements = new ArrayList<>();

		while (true) {
			Token token = tokens.poll();

			if   ( token == null )
				return;

			switch (token.getType()) {

				case T_STATEMENT_END:
					// maybe an empty statement?
					break;

				case T_OPERATOR:
					throw new ScriptParserException("Unexpected operator", token);
				case T_BRACKET_CLOSE:
					throw new ScriptParserException("Unexpected closing group", token);
				case T_BLOCK_END:
					throw new ScriptParserException("Unexpected ending of an block", token);
				case T_NEGATION:
					throw new ScriptParserException("Unexpected negation", token);
				case T_DOT:
					throw new ScriptParserException("Unexpected dot", token);

				case T_FUNCTION:

					Token nameToken = tokens.poll();
					if (nameToken.getType() != TokenEnum.T_STRING)
						throw new ScriptParserException("function must have a name", token);
					String name = nameToken.getValue();

					Token functionCallOp = tokens.poll();
					if   ( functionCallOp.getType() != TokenEnum.T_OPERATOR || functionCallOp.getValue() != "" )
						throw new ScriptParserException("function '"+name+"' must have a function signature");

					Queue<Token> functionParameter = this.getBracket(tokens);
					Queue<Token> functionBlock = this.getBlock(tokens);

					this.functions.put(name,new DslFunction( functionParameter, functionBlock ));

					break;

				case T_IF:
					Queue<Token> condition = this.getBracket(tokens);
					Queue<Token> positiveBlock = this.getStatementOrBlock(tokens);
					Queue<Token> negativeBlock = new LinkedList<>();

					// Let's see if there is an "else" case.
					Token nextToken = tokens.peek();
					if (nextToken != null && nextToken.getType() == TokenEnum.T_ELSE) {
						tokens.poll(); // get the "else".
						negativeBlock.addAll(this.getStatementOrBlock(tokens));
					}
					this.statements.add(new DslIf(condition, positiveBlock, negativeBlock));
					break;

				case T_NEW:
				case T_LET:
					break; // ignore these statements

				case T_NULL:
					this.statements.add( new DslNull() );
					break;

				case T_TRUE:
					this.statements.add( new DslTrue() );
					break;

				case T_FALSE:
					this.statements.add( new DslFalse() );
					break;

				case T_FOR:
					Queue<Token> forGroup = this.getBracket( tokens );
					Queue<Token> forBlock = this.getStatementOrBlock( tokens );

					Token varName = forGroup.poll();
					if   ( varName == null || varName.getType() != TokenEnum.T_STRING )
						throw new ScriptParserException("for loop variable missing");
					Token ofName = forGroup.poll();
					if   ( ofName == null || ofName.getType() != TokenEnum.T_STRING || !ofName.getValue().equalsIgnoreCase("of") )
						throw new ScriptParserException("missing \"of\" in for loop");

					this.statements.add( new DslFor( varName.getValue(), forGroup, forBlock ) );
					break;

				case T_RETURN:
					Queue<Token> returnTokens = this.getSingleStatement( tokens,false );
					this.statements.add( new DslReturn( returnTokens ) );
					break;

				case T_THROW:
					Queue<Token> throwTokens = this.getSingleStatement(tokens ,false);
					this.statements.add( new DslThrow( throwTokens ) );
					break;

				case T_TEXT, T_NUMBER, T_STRING:
					((LinkedList<Token>)tokens).addFirst(token);// bad bad
					Queue<Token> statementTokens = this.getSingleStatement( tokens,false );

					this.statements.add( new DslExpression( statementTokens ) );
					break;

				default:
					throw new ScriptParserException("Unknown or unexpected token '"+token+"'.");
			}

		}

	}


	@Override
	public String toString() {
		return "statement list " + this.statements.size() + "x";
	}
}


package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.BaseScriptable;
import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.context.Scriptable;
import de.openrat.jscriptbox.context.ScriptableFunction;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;
import de.openrat.jscriptbox.parser.TokenEnum;
import de.openrat.jscriptbox.standard.internal.ArrayInstance;
import de.openrat.jscriptbox.standard.internal.NumberInstance;
import de.openrat.jscriptbox.standard.internal.ObjectInstance;
import de.openrat.jscriptbox.standard.internal.StringInstance;

import java.util.*;

class DslExpression extends DslElement implements DslStatement
{
	private int LEFT = 0;
	private int RIGHT = 1;

	private DslStatement value;

	public DslExpression(Queue<Token> valueTokens ) throws ScriptParserException {
		this.parse( valueTokens );
	}
	public DslExpression(DslStatement value ) throws ScriptParserException {
		this.value = value;
	}

	public Object execute(Context context ) throws ScriptRuntimeException {

		/*if   ( is_array( this->value ) )
			foreach( this->value as v)
				v->execute( context );
		else*/
			return this.value.execute( context );
	}

	/**
	 * @param tokens
	 * @throws ScriptParserException
	 */
	public void parse(Queue<Token> tokens) throws ScriptParserException
	{
		if   ( tokens == null || tokens.size() == 0 ) {
			this.value = new DslNull();
			return;
		}

		this.parseExpression( tokens );
	}


	/**
	 * Parsing an expression using the shunting yard algorithm.
	 *
	 * Precedences see https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Operators/Operator_Precedence#assoziativit%C3%A4t
	 * @param tokens
	 * @throws ScriptParserException
	 */
	private void parseExpression(Queue<Token> tokens ) throws ScriptParserException {
		Map<String,Integer> precedence = Map.ofEntries(
			Map.entry(",",2),
				Map.entry("=",  3),
				Map.entry("+=" ,  3),
				Map.entry("-=" ,  3),
				Map.entry("||" ,  5),
				Map.entry("&&" ,  6),
				Map.entry("==" , 10),
				Map.entry("!=" , 10),
				Map.entry("<", 11),
				Map.entry("<=" , 11),
				Map.entry(">", 11),
				Map.entry(">=" , 11),
				Map.entry("+", 13),
				Map.entry("-", 13),
				Map.entry("/", 14),
				Map.entry("*", 14),
				Map.entry("%", 14),
				Map.entry("**" , 15),
				Map.entry("!", 16),
				Map.entry("$", 19), // function call, provided by the lexer
				Map.entry(".", 19),
				// for the purpose of comparing only; it's forced to top priority explicitly
				Map.entry("(",0),
				Map.entry(")",0) );

		Map<String,Integer> assoc = Map.ofEntries(

				Map.entry(","   , LEFT),
				Map.entry("="   , RIGHT),
				Map.entry("-="  , RIGHT),
				Map.entry("+="  , RIGHT),
				Map.entry("||"  , LEFT),
				Map.entry("&&"  , LEFT),
				Map.entry("=="  , LEFT),
				Map.entry("!="  , LEFT),
				Map.entry("<"   , LEFT),
				Map.entry("<="  , LEFT),
				Map.entry("> "  , LEFT),
				Map.entry(">="  , LEFT),
				Map.entry("+"   , LEFT),
				Map.entry("-"   , LEFT),
				Map.entry("/"   , LEFT),
				Map.entry("*"   , LEFT),
				Map.entry("%"   , LEFT),
				Map.entry("^"   , RIGHT),
				Map.entry("!"   , RIGHT),
				Map.entry("**"  , RIGHT),
				Map.entry("."   , LEFT),
				Map.entry("$"   , LEFT) );




		Stack<DslStatement> outputStack   = new Stack<>();
		Stack<Token> operatorStack = new Stack<>();

		/*
		if   ( tokens instanceof DslStatement ) {

			this->value = tokens;
			return;
		}

		if   ( tokens instanceof DslToken )
			tokens = [tokens];

		if   ( ! is_array(tokens))
			throw new DslParserException("tokens must be an array, but it is ".get_class(tokens));
*/

		// while there are tokens to be read:
		DslStatement right;
		DslStatement left;
		for( Token token : tokens ) {
			// read a token.

			if (token.isOperator() ) {

				// while there is an operator at the top of the operator stack with
				// greater than or equal to precedence:

				while (!operatorStack.empty() &&
						precedence.get(operatorStack.peek().getValue()) >= precedence.get(token.getValue()) + assoc.get(token.getValue())) {
					// pop operators from the operator stack, onto the output queue.

					left  = outputStack.pop();
					right = outputStack.pop();
					outputStack.push( this.createNode( operatorStack.pop(),left,right ) );
				}
				// push the read operator onto the operator stack.
				operatorStack.add(token);

				// if the token is a left bracket (i.e. "("), then:
			} else if (token.getValue().equals("(")) {
				// push it onto the operator stack.
				operatorStack.add(token);

				// if the token is a right bracket (i.e. ")"), then:
			} else if (token.getValue().equals( ")")) {
				// while the operator at the top of the operator stack is not a left bracket:
				while (!operatorStack.peek().getValue().equals("(")) {
					// pop operators from the operator stack onto the output queue.
					left  = outputStack.pop();
					right = outputStack.pop();
					outputStack.add( this.createNode( operatorStack.pop(),left,right ));

					// /* if the stack runs out without finding a left bracket, then there are
					// mismatched parentheses. */
					if ( operatorStack.empty() ) {
						throw new ScriptParserException("Mismatched ')' parentheses", token.getLineNumber());
					}
				}

				// pop the left bracket from the stack.
				operatorStack.pop();

			}
			else {
					outputStack.add( this.tokenToStatement( token ) );
			}
		} // if there are no more tokens to read:

		// while there are still operator tokens on the stack:
		while (! operatorStack.empty()) {
			Token token = operatorStack.pop();

			// if the operator token on the top of the stack is a bracket, then
			// there are mismatched parentheses.
			if (token.getType() == TokenEnum.T_OPERATOR && token.getValue().equals("(")) {
				throw new ScriptParserException( "Mismatched '(' parentheses", token.getLineNumber());
			}
			// pop the operator onto the output queue.
			left  = outputStack.pop();
			right = outputStack.pop();
			outputStack.add(this.createNode( token,left,right ));
		}

		this.value = outputStack.pop();
	}

	/**
	 * @param op DslToken
	 * @param left
	 * @param right
	 * @throws ScriptParserException
	 */
	private DslStatement createNode(Token op, DslStatement left, DslStatement right) throws ScriptParserException {
		final String operator = op.getValue();

		if   ( operator.equals("=") )
			return new DslAssignment( right,left );
		if   ( operator.equals(",") )
			return new DslSequence( right,left );
		if   ( operator.equals(".") )
			return new DslProperty( right, left );
		if   ( operator.equals("$") )
			return new DslFunctionCall( right, left );
		else
			return new DslOperation( operator,right,left );
	}

	/**
	 * @param token DslToken
	 */
	private DslStatement tokenToStatement(Token token) throws ScriptParserException {
		switch( token.getType() ) {
			case T_NONE:
				return new DslInteger( 0 );
			case T_NULL:
				return new DslNull();
			case T_TRUE:
				return new DslTrue();
			case T_FALSE:
				return new DslFalse();
			case T_NUMBER:
				return new DslInteger( Integer.parseInt(token.getValue()) );
			case T_TEXT:
				return new DslString( token.getValue() );
			case T_STRING:
				return new DslVariable( token.getValue() );
			case T_DOT:
				throw new IllegalStateException("geht nicht");
				//return new DslProperty( token.getValue() );
			default:
				throw new ScriptParserException("Unknown token: '" + token.getValue()+"'",token.getLineNumber());
		}
	}


	public static Scriptable convertValueToStandardObject(Object value, Context context) {

		if   ( value instanceof List) {

			return new ArrayInstance( (List)value);
		}
		if   (  value instanceof Number ) {

			return new NumberInstance( ((Number)value).intValue() );
		}
		if   ( value instanceof String ) {

			return new StringInstance( value.toString() );
		}

		if   ( value instanceof BaseScriptable) {
			return (BaseScriptable)value;
		}

		if   ( value instanceof ScriptableFunction) {
			return (Scriptable)value;
		}

		if   ( value instanceof Scriptable) {
			return (Scriptable)value;
		}

		if   ( !context.isAllowNonScriptableObjects() )
			// Secured Sandbox, external objects are not evaluated.
			return new StringInstance( "ProtectedObject" );
		else
			return new ObjectInstance(value); // Unsecured, but wanted.
	}


	@Override
	public String toString() {
		return "EXPR";
	}
}
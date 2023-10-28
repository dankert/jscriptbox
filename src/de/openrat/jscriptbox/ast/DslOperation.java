

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;
import de.openrat.jscriptbox.standard.internal.ArrayInstance;
import de.openrat.jscriptbox.standard.internal.BooleanInstance;
import de.openrat.jscriptbox.standard.internal.NumberInstance;
import de.openrat.jscriptbox.standard.internal.StringInstance;

import java.util.Queue;

class DslOperation implements DslStatement
{
	private String operator;
	private DslStatement left;
	private DslStatement right;

	/**
	 * DslOperation constructor.
	 * @param operator
	 * @param left
	 * @param right
	 */
	public DslOperation(String operator, DslStatement left, DslStatement right) throws ScriptParserException {
		this.operator = operator;
		this.left  = new DslExpression(  left );
		this.right = new DslExpression( right );
	}


	/**
	 * @throws ScriptRuntimeException
	 */
	public Object execute(Context context ) throws ScriptRuntimeException {

		Object left  = this.left.execute( context );
		Object right = this.right.execute( context );

		try {

			switch (this.operator) {
				case "+":
					if (left instanceof ArrayInstance) {
						ArrayInstance array = ((ArrayInstance) left);
						if (right instanceof ArrayInstance)
							return array.getInternalValue().addAll(((ArrayInstance) right).getInternalValue());

						return array.getInternalValue().add(right);
					}

					if (left instanceof String)
						return ((String) left).concat(right.toString());

					if (left instanceof StringInstance)
						return left.toString() + right.toString();

					return tryToGetANumber(left) + tryToGetANumber(right);

				case "-":
					return tryToGetANumber(left) - tryToGetANumber(right);

				case "*":
					return tryToGetANumber(left) * tryToGetANumber(right);

				case "/":
					return tryToGetANumber(left) / tryToGetANumber(right);

				case "==":
					return left.equals(right);
				case "!=":
					return ! left.equals(right);
				case "<":
					return tryToGetANumber(left) < tryToGetANumber(right);
				case "<=":
					return tryToGetANumber(left) <= tryToGetANumber(right);
				case ">":
					return tryToGetANumber(left) > tryToGetANumber(right);
				case ">=":
					return tryToGetANumber(left) >= tryToGetANumber(right);

				case "||":
					return tryToGetABoolean(left) || tryToGetABoolean(right);
				case "&&":
					return tryToGetABoolean(left) && tryToGetABoolean(right);

				case "%":
					return tryToGetANumber(left) % tryToGetANumber(right);

				case "!":
					return ! tryToGetABoolean(left);

				default:
					throw new ScriptRuntimeException("Unknown operator '" + this.operator + "'");
			}
		} catch (Exception e ) {
			throw new ScriptRuntimeException("Unable to operate '"+left.getClass().getName() + "' "+this.operator+" '"+right.getClass().getName()+"' ("+e.getMessage()+").");
		}

	}


	public void parse(Queue<Token> tokens)
	{
	}


	protected Integer tryToGetANumber( Object someObject ) {
		if   ( someObject instanceof NumberInstance ) {
			return ((NumberInstance)someObject).toNumber();
		}
		if   ( someObject instanceof ArrayInstance ) {
			return ((ArrayInstance)someObject).getInternalValue().size();
		}
		Integer number = Integer.parseInt( someObject.toString() );
		return number;
	}
	protected Boolean tryToGetABoolean( Object someObject ) {
		if   ( someObject instanceof DslTrue)
			return true;
		if   ( someObject instanceof DslFalse)
			return false;
		if   ( someObject instanceof Boolean)
			return ((Boolean)someObject);
		if   ( someObject instanceof BooleanInstance)
			return ((BooleanInstance)someObject).isTrue();

		if   ( someObject instanceof NumberInstance ) {
			return ((NumberInstance)someObject).toNumber() != 0;
		}
		if   ( someObject instanceof StringInstance ) {
			return ((StringInstance)someObject).toString().length() > 0;
		}
		if   ( someObject instanceof ArrayInstance ) {
			return ((ArrayInstance)someObject).getInternalValue().size() > 0;
		}
		return true;
	}
}
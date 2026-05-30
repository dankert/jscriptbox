

package de.jandankert.jscriptbox.ast;

import de.jandankert.jscriptbox.context.Context;
import de.jandankert.jscriptbox.exception.ScriptParserException;
import de.jandankert.jscriptbox.exception.ScriptRuntimeException;
import de.jandankert.jscriptbox.parser.Token;

import java.util.Queue;

public class DslAssignment implements DslStatement
{
	private DslStatement target;
	private DslStatement value;

	/**
	 * DslAssignment constructor.
	 * @param target DslStatement
	 * @param value DslStatement
	 */
	public DslAssignment( DslStatement target, DslStatement value )
	{
		//echo "<h5>Assignment:</h5><pre>"; var_export( target ); var_export(value); echo "</pre>";

		this.target = target;
		this.value  = value;
	}

	/**
	 * @param context
	 * @return mixed|void
	 * @throws ScriptRuntimeException
	 */
	public Object execute( Context context ) throws ScriptRuntimeException {

		Object value = this.value.execute( context );

		if   ( this.target instanceof DslVariable variable) {

			// if the variable is not already bound in this context it will be created.
			// there is no need for a "var" or "let". they are completely obsolete.
			//if   ( ! context.containsKey( variable.name ) )
			//	throw new ScriptRuntimeException("Undefined variable: " + variable.name );

			context.put( variable.name ,value );
		} else {
			throw new ScriptRuntimeException("Not a variable: " + this.target.toString() );
		}

		return value;
	}

	public void parse(Queue<Token> tokens)
	{
		/*
		this.target = new DslExpression();
		this.value = new DslExpression();
		boolean assignmentOperatorFound = false;

		List<DslToken> targetToken = new ArrayList<>();
		List<DslToken> valueToken  = new ArrayList<>();

		for( DslToken token : tokens  ) {

			if   ( token.getType() == DslTokenEnum.T_OPERATOR &&
					Arrays.asList("=","+=","-=").contains(token.getValue()) ) {
				assignmentOperatorFound = true;
				continue;
			}

			if   ( ! assignmentOperatorFound )
				targetToken.add( token );
			else
				valueToken.add( token );
		}

		if  ( assignmentOperatorFound ) {
			this.target.parse( targetToken.toArray(new DslToken[]{}) );
			this.value.parse( valueToken.toArray(new DslToken[]{}) );
		} else 	{
			this.value.parse( targetToken.toArray(new DslToken[]{}) );
			this.target = null;
		}*/
	}


	@Override
	public String toString() {
		return "ASSIGNMENT";
	}
}
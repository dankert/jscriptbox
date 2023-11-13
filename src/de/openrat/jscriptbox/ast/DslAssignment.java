

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

import java.util.Queue;

public class DslAssignment implements DslStatement
{
	private DslStatement target;
	private DslStatement value;

	/**
	 * DslAssignment constructor.
	 * @param target DslStatement
	 * @param value DslStatement
	 * @throws ScriptParserException
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

		// if the variable is not already bound in this context it will be created.
		// there is no need for a "var" or "let". they are completely obsolete.
		//if   ( ! array_key_exists( this->target->name,context ) )
		//	throw new DslRuntimeException('variable \''.this->target->name.'\' does not exist');

		context.put( this.target.execute(context).toString() ,value );

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
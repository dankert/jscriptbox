

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.context.ObjectContext;
import de.openrat.jscriptbox.context.Scriptable;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

import java.util.Queue;

class DslProperty implements DslStatement
{
	public DslStatement variable;
	public DslStatement property;

	/**
	 * DslProperty constructor.
	 * @param variable
	 * @param property
	 */
	public DslProperty(DslStatement variable, DslStatement property)
	{
		this.variable = variable;
		this.property = property;
	}


	/**
	 * @param context
	 * @return mixed
	 * @throws ScriptRuntimeException
	 */
	public Object execute(Context context ) throws ScriptRuntimeException {

		Object object = this.variable.execute( context );

		Scriptable objectContext = DslExpression.convertValueToStandardObject(object,context);

		Context propertyContext = new ObjectContext( objectContext,context.isAllowNonScriptableObjects() );

		Object prop = this.property.execute( propertyContext );

		return prop;

	}

	public void parse(Queue<Token> tokens)
	{
	}


	@Override
	public String toString() {
		return "Property";
	}
}
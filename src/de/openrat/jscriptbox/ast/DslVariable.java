

package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.context.Scriptable;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Queue;

class DslVariable implements DslStatement
{
	public String name;

	/**
	 * @param name
	 */
	public DslVariable( String name)
	{
		this.name = name;
	}


	public Object execute(Context context ) throws ScriptRuntimeException {

		if   ( context.isObject() ) {

			Scriptable object = context.getObject();
			try {
				Field field = object.getClass().getDeclaredField(this.name);
				return field.get( object );
			} catch (NoSuchFieldException e) {
				//
			} catch (IllegalAccessException e) {
				throw new ScriptRuntimeException("property '"+this.name+"' of class '"+object.getClass().getName()+"' is not accessible",e);
            }

            // copy object methods to the object context to make them callable.
			String property = this.name;

			try {
				Method method = object.getClass().getDeclaredMethod(this.name);

				// For Security: Do not expose all available objects, they must implement a marker interface.
				if   ( ! (object instanceof Scriptable) && ! context.isAllowNonScriptableObjects()  )
					throw new ScriptRuntimeException("Object '"+object.getClass().getName() + "' is not marked as scriptable and therefore not available in secure mode.");

				return method;
			} catch (NoSuchMethodException e) {
                //
            }

			throw new ScriptRuntimeException("method or property '" + property + "' does not exist" );
		}

		if   ( ! context.containsKey( this.name ) )
			throw new ScriptRuntimeException("variable or property '"+this.name+"' does not exist");

		return context.get( this.name );
	}

	@Override
	public void parse(Queue<Token> tokens) {

	}


	@Override
	public String toString() {
		return "Variable '" + name + "'";
	}
}
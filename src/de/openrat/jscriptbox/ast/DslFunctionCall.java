package de.openrat.jscriptbox.ast;

import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.context.Invoke;
import de.openrat.jscriptbox.context.MethodWrapper;
import de.openrat.jscriptbox.context.ScriptableFunction;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;
import de.openrat.jscriptbox.standard.internal.ArrayInstance;
import de.openrat.jscriptbox.standard.internal.DateInstance;
import de.openrat.jscriptbox.standard.internal.NumberInstance;
import de.openrat.jscriptbox.standard.internal.StringInstance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Function call.
 *
 * A function call is able to call
 * <ul>
 * <li>a self defined {@see DslFunction}</li>
 * <li>a method of an object</li>
 * </ul>
 */
class DslFunctionCall implements DslStatement
{
	private DslStatement name;
	private DslStatement callParameters;

	/**
	 * DslFunctionCall constructor.
	 * @param name
	 * @param parameters DslToken[][]
	 */
	public DslFunctionCall(DslStatement name, DslStatement parameters)
	{
		this.name       = name;
		this.callParameters = parameters;
	}


	/**
	 * @throws ScriptRuntimeException
	 */
	public Object execute(Context context ) throws ScriptRuntimeException {

		Object function = this.name.execute( context );

		final Object parameterValuesRaw = this.callParameters.execute( context );

		List parameterValues = new ArrayList<>();

		if   ( parameterValuesRaw instanceof ArrayInstance)
			 parameterValues.addAll( ((ArrayInstance)parameterValuesRaw).getInternalValue() );
		else 		if   ( parameterValuesRaw instanceof List<?>)
			parameterValues.addAll( (List)parameterValuesRaw );

		else parameterValues.add(parameterValuesRaw);

		if   ( function instanceof DslFunction ) {
			// inscript custom function
			List<String> functionParameters = ((DslFunction) function).parameters;

			// The parameter count must match. Java has no optional parameters like PHP oder JS.
			if   ( functionParameters.size() != parameterValues.size() )
				throw new ScriptRuntimeException("Function call has " + parameterValues.size() + " parameters but the function needs " + functionParameters.size() + " parameters");

			// Put all function parameters to the function context.
			Map<String,Object> functionParameterContext = IntStream.range(0, functionParameters.size())
					.boxed()
					.collect(Collectors.toMap(i -> functionParameters.get(i), i -> parameterValues.get(i)));

			Context subContext = context.copyAndPut(functionParameterContext);

			return ((DslFunction)function).execute( subContext );

		}
		else if   ( function instanceof MethodWrapper) {

			MethodWrapper methodWrapper = ((MethodWrapper) function);
			//Object result = method.invoke(null, this.toPrimitiveValues(parameterValues));

			if (methodWrapper.getMethod().getParameterCount() != parameterValues.size())
				throw new ScriptRuntimeException("Method call '"+methodWrapper.getMethod().getName()+"()' has " + parameterValues.size() + " parameters but the function needs " + methodWrapper.getMethod().getParameterCount() + " parameters");

			try {
				Object result = methodWrapper.getMethod().invoke(methodWrapper.getScriptable(), parameterValues.toArray());
				return DslExpression.convertValueToStandardObject( result,context );
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new ScriptRuntimeException("Calling function '"+name.toString()+"' failed.");
			}
		}
		else if   ( function instanceof ScriptableFunction) {

			Method methodToCall = null;

			for( Method method : function.getClass().getMethods() ) {
				if   ( method.isAnnotationPresent(Invoke.class)) {
					methodToCall = method;
					break;
				}
			}

			if   ( methodToCall == null )
				throw new ScriptRuntimeException("ScriptableFunction '"+function.toString()+"' needs 1 method annotated with "+Invoke.class.getSimpleName() );
			
			// The parameter count must match. Java has no optional parameters like PHP oder JS.
			if (methodToCall.getParameterCount() != parameterValues.size())
				throw new ScriptRuntimeException("Function '"+ function.toString() +"' has " + parameterValues.size() + " parameters but the function needs " + methodToCall.getParameterCount() + " parameters");

			Object result;
			try {
				result = methodToCall.invoke(function,parameterValues.toArray());
			} catch (IllegalAccessException e) {
				throw new ScriptRuntimeException(e.getMessage(),e);
			} catch (InvocationTargetException e) {
				throw new ScriptRuntimeException(e.getMessage(),e);
			} catch (Exception e) {
			throw new ScriptRuntimeException(e.getMessage(),e);
		}

			return result;
		}
		else
			throw new ScriptRuntimeException("Function or method '"+function.toString()+"' is not callable."  );
	}

	public void parse(Queue<Token> tokens)
	{
		//???
		//this.statements[] = tokens;
	}


	/**
	 * Converts variables to its primitives, because external objects in applications will not be able to handle things like "StandardString".
	 * @param parameterValues
	 * @return array
	 */
	private Object toPrimitiveValues( DslStatement[] parameterValues )
	{
		return Arrays.stream(parameterValues).map(new Function<DslStatement, Object>() {

			@Override
			public Object apply(DslStatement val) {

				if   ( val instanceof ArrayInstance )
					return ((ArrayInstance)val).getInternalValue();
				if   ( val instanceof NumberInstance)
					return ((NumberInstance)val).toNumber();
				if   ( val instanceof DateInstance )
					return new Date( ((DateInstance)val).getTimeInMillis() );
				if   ( val instanceof StringInstance)
					return val.toString();
				return val; // an unknown object remains itself
			}

			}).toList();
	}

	@Override
	public String toString() {
		return "FunctionCall{" +
				"name=" + name +
				", callParameters=" + callParameters +
				'}';
	}
}
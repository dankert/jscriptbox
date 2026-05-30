package de.jandankert.jscriptbox.exception;

public class ScriptUserDefinedException extends ScriptRuntimeException
{
	/**
	 * DslParserException constructor.
	 * @param message
	 */
	public ScriptUserDefinedException(String message) {
		super(message);
	}
}
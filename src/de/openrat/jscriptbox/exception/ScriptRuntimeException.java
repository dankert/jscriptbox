package de.openrat.jscriptbox.exception;

public class ScriptRuntimeException extends ScriptException
{
	/**
	 * DslParserException constructor.
	 * @param message
	 * @param lineNumber
	 */
	public ScriptRuntimeException(String message, Integer lineNumber, Throwable previous )
	{
		super( message + (lineNumber>0 ? " on line " + lineNumber : ""),previous );
	}

	public ScriptRuntimeException(String message) {
		super(message);
	}

	public ScriptRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
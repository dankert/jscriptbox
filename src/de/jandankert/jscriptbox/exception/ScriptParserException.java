package de.jandankert.jscriptbox.exception;

import de.jandankert.jscriptbox.parser.Token;

public class ScriptParserException extends ScriptException
{

	public ScriptParserException(String message )
	{
		super(  message );
	}
	public ScriptParserException(String message, Integer lineNumber )
	{
		super(  message + (lineNumber>0?" on line " + lineNumber:"") );
	}
	public ScriptParserException(String message, Token token )
	{
		super(  message + (token.getLineNumber()>0?" on line " + token.getLineNumber():"") );
	}
}
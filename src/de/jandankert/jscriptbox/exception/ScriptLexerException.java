package de.jandankert.jscriptbox.exception;

public class ScriptLexerException extends ScriptParserException
{
	public ScriptLexerException(String message, Integer lineNumber )
	{
		super(  message + (lineNumber>0?" on line " + lineNumber:"") );
	}
}
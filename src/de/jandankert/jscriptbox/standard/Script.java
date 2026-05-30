

package de.jandankert.jscriptbox.standard;

import de.jandankert.jscriptbox.ast.DslStatement;
import de.jandankert.jscriptbox.context.BaseScriptable;
import de.jandankert.jscriptbox.parser.Token;

public class Script extends BaseScriptable
{
	/**
	 * @var DslToken[]
	 */
	private Token[] tokens;

	/**
	 * @var DslStatement
	 */
	private DslStatement ast;

	/**
	 * @param tokens DslToken[]
	 * @param ast DslStatement
	 */
	public Script(Token[] tokens, DslStatement ast )
	{
		this.tokens = tokens;
		this.ast    = ast;
	}

	public String getToken()
	{
		return this.tokens.toString();
	}


	public String getSource()
	{
		int line   = 0;
		StringBuffer source = new StringBuffer();

		for( Token token : this.tokens ) {

			source.append( (line != token.getLineNumber() ? "\n" + String.format("%6s", token.getLineNumber()) +": " : "") + token.getValue());
			line = token.getLineNumber();
		}

		return source.toString();
	}


	public String getSyntaxTree()
	{
		return this.ast.toString();
	}

	public String dump( Object value )
	{

		return value.toString();
	}

	public String toString()
	{
		return "Script Info, call help() for help.";
	}
}
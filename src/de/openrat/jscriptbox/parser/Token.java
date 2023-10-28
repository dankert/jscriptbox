package de.openrat.jscriptbox.parser;

import java.util.Objects;

public class Token
{
	private int lineNumber;
	private TokenEnum type;
	private String value;

	public Token(int lineNumber, TokenEnum type, String value) {
		this.lineNumber = lineNumber;
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {

		return "" +
				"#" + lineNumber +
				":" + type.name() +
				":\"" + value + '"';
	}


	/**
	 * @return bool
	 */
	public boolean isOperator() {
		return this.type == TokenEnum.T_OPERATOR;
	}
	/**
	 * @return bool
	 */
	public boolean isOperator( String value ) {
		if   ( value != null )
			return this.type == TokenEnum.T_OPERATOR;

		return this.type == TokenEnum.T_OPERATOR && Objects.equals(this.value, value);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public TokenEnum getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
}
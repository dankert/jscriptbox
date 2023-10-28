package de.openrat.jscriptbox.parser;

public enum TokenEnum {
    T_NONE,
    T_STRING,
    T_BRACKET_OPEN,
    T_BRACKET_CLOSE,
    T_BLOCK_BEGIN,
    T_BLOCK_END,
    T_TEXT,
    T_NUMBER,
    T_OPERATOR,
    T_FUNCTION,
    T_FOR,
    T_IF,
    T_ELSE,
    T_LET,
    T_RETURN,
    T_DOT,
    T_STATEMENT_END,
    T_NEGATION,
    T_COMMA,
    T_NEW,
    T_THROW,
    T_TRUE,
    T_FALSE,
    T_NULL
}
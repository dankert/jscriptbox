package de.openrat.jscriptbox.parser;

import de.openrat.jscriptbox.exception.ScriptLexerException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Lexer tokenizes a peace of code.
 */
public class Lexer {
    private Queue<Token> token;

    protected static final Map<String, TokenEnum> KEYWORDS = Map.ofEntries(
            Map.entry("function", TokenEnum.T_FUNCTION),
            Map.entry("for", TokenEnum.T_FOR),
            Map.entry("if", TokenEnum.T_IF),
            Map.entry("else", TokenEnum.T_ELSE),
            Map.entry("let", TokenEnum.T_LET),
            Map.entry("const", TokenEnum.T_LET),
            Map.entry("var", TokenEnum.T_LET),
            Map.entry("return", TokenEnum.T_RETURN),
            Map.entry("new", TokenEnum.T_NEW),
            Map.entry("throw", TokenEnum.T_THROW),
            Map.entry("null", TokenEnum.T_NULL),
            Map.entry("true", TokenEnum.T_TRUE),
            Map.entry("false", TokenEnum.T_FALSE)
    );

    protected static final List<String> IGNORED_KEYWORDS = Arrays.asList(
            "let",
            "new"
    );

    protected static final List<String> UNUSED_KEYWORDS = Arrays.asList(
            "implements",
            "interface",
            "package",
            "private",
            "protected",
            "public",
            "static",
            "in",
            "do",
            "try",
            "catch",
            "finally",
            "this",
            "case",
            "void",
            "with",
            "enum",
            "while",
            "break",
            "yield",
            "class",
            "super",
            "typeof",
            "delete",
            "switch",
            "export",
            "import",
            "default",
            "extends",
            "continue",
            "debugger",
            "instanceof",
            "goto" // ;)
    );

    protected static final List<Character> OPERATORS = Arrays.asList(
            '>', '<', '+', '-', '/', '*', '=', '|', '&', ',', '.'
    );

    /**
     * @param code
     * @return array(DslToken)
     */
    public void tokenize(String code) throws ScriptLexerException {

        int line = 1;
        this.token = new LinkedList<>();

        // split the whole code into its characters
        LinkedList<Character> chars = new LinkedList<Character>(code.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));

        charloop: // label it
        while (true) {
            Character character = chars.pollFirst();

            if ( character ==null )
            break;

            if (( character == ' ' ))
            continue;

            if (( character == '\n' )){
                line++;
                continue;
            }

            // Text
            if ( character =='"' || character =='\'' ){
                Character textEncloser = character ;
                String value = "";
                while (true) {
                    character = chars.pollFirst();
                    if ( character =='\n' )
                        throw new ScriptLexerException("Unclosed string", line);
                    if ( character =='\\'){
                        character =chars.pollFirst();
                        if ( character =='n' )
                        value = "\n";
                        else if( character =='t' )
                        value = "\t";
						else
                        value = value + character ;
                    }
                    else if( character !=textEncloser){
                        value = value + character;
                        continue;
                    } else{
                        this.addToken(line, TokenEnum.T_TEXT, value);
                        break;
                    }
                }
                continue;
            }

            // Comments
            if ( character =='/' ){
                Character nextChar = chars.pollFirst();
                if (nextChar == '/') { // Comment after "//"

                    while (true) {
                        Character c = chars.pollFirst();

                        if (c == null)
                            continue charloop;

                        if (c == '\n' ) {
                            line++;
                            continue charloop;
                        }
                    }

                }
                else if(nextChar == '*') { // Comment after "/*"

                    Character lastChar = null;
                    while (true) {
                        Character c = chars.pollFirst();
                        if (c == null)
                            break charloop;
                        if (c == '\n' )
                            line++;
                        if (lastChar == '*' && c == '/')
                            continue charloop;
                        lastChar = c;
                        continue;
                    }

                }
				else{
                    chars.addFirst(nextChar); // this is no comment
                }
            }

            // String
            TokenEnum type;
            if (( character >='a' && character <='z') ||
            ( character >='A' && character <='Z') ||
            character =='_' /*||
            character ==''*/ ){
                String value = ""+character ;
                while (true) {
                    Character charn = chars.pollFirst();
                    if (( charn >='a' && charn <='z') ||
                    ( charn >='A' && charn <='Z') ||
                    ( charn >='0' && charn <='9') ||
                    charn =='_' /*||
                    charn ==''*/ ){
                        value = value + charn ;
                    } else{
                        type = TokenEnum.T_STRING;

                        if ( UNUSED_KEYWORDS.contains(value))
                            throw new ScriptLexerException("use of reserved word '" + value + "' is not allowed.",line);

                        if (IGNORED_KEYWORDS.contains(value) )
                            break; // ignore this keyword

                        if ( KEYWORDS.containsKey( value ) )
                            type = KEYWORDS.get(value); // it is a keyword

                        this.addToken(line, type, value);
                        chars.addFirst(charn);
                        break;
                    }
                }
                continue;
            }

            // Numbers
            // TODO we have a problem with
            // - "-" is an operator, so we cannot parse negative numbers
            // - "." is the property character, so we cannot parse decimal values
            if ( character >='0' && character <='9' ){
                String value = ""+character ;
                while (true) {
                    character = chars.pollFirst();
                    if   ( character == null )
                        throw new ScriptLexerException("Unexpected end of source",line);
                    if (( character >='0' && character <='9') ||
                    character =='_' ){
                        value = value + character ;
                    } else{
                        this.addToken(line, TokenEnum.T_NUMBER, value.replace("_",""));
                        chars.addFirst(character);
                        break;
                    }
                }
                continue;
            }

            if ( OPERATORS.contains( character ) ) {

                String value = "" + character;
                while (true) {
                    Character charn = chars.pollFirst();
                    if (OPERATORS.contains(charn)) {
                        value = value + charn;
                    } else {
                        type = TokenEnum.T_OPERATOR;
                        this.addToken(line, type, value);
                        chars.addFirst(charn);
                        continue charloop;
                    }
                }
                //continue;
            }

            if (character == '\r' )
                continue;
            else if(character == '!')
                this.addToken(line, TokenEnum.T_NEGATION, ""+character);
            else if(character == ';')
                this.addToken(line, TokenEnum.T_STATEMENT_END, ""+character);
            else if(character == '.')
                this.addToken(line, TokenEnum.T_DOT, ""+character);
            else if(character == ',')
                this.addToken(line, TokenEnum.T_COMMA, ""+character);

            else if(character == '(') {
                if ( this.getLastTokenType() == TokenEnum.T_STRING)
                    // if string is followed by "(" it is a function or a function call
                    this.addToken(line, TokenEnum.T_OPERATOR, ""+'$'); // function call
                this.addToken(line, TokenEnum.T_BRACKET_OPEN, ""+character);
            }
            else if(character == ')') {
                if ( this.getLastTokenType() == TokenEnum.T_BRACKET_OPEN)
                    // if there is an empty parenthesis, make it contain something, otherwise the shunting yard algo will fail.
                    this.addToken(line, TokenEnum.T_NONE,""); //
                this.addToken(line, TokenEnum.T_BRACKET_CLOSE, ""+character);
            }
            else if(character == '{')
                this.addToken(line, TokenEnum.T_BLOCK_BEGIN, ""+character);
            else if(character == '}')
                this.addToken(line, TokenEnum.T_BLOCK_END, ""+character);
			else {
                throw new ScriptLexerException("Unknown character '" + character + "'", line);
            }
        }
    }

    private void addToken(int line, TokenEnum type, String value) {
        this.token.add( new Token(line, type, value) );
    }


    public Queue<Token> getToken() {
        return token;
    }

    private TokenEnum getLastTokenType() {

        if   ( this.token.isEmpty() )
            return null;

        return ((LinkedList<Token>)this.token).getLast().getType();
    }
}


package de.openrat.jscriptbox.executor;

import de.openrat.jscriptbox.ast.DslStatement;
import de.openrat.jscriptbox.context.Context;
import de.openrat.jscriptbox.context.Scriptable;
import de.openrat.jscriptbox.standard.*;
import de.openrat.jscriptbox.exception.ScriptParserException;
import de.openrat.jscriptbox.parser.AstParser;
import de.openrat.jscriptbox.exception.ScriptException;
import de.openrat.jscriptbox.parser.Lexer;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.parser.Token;

import java.io.PrintStream;
import java.util.Map;
import java.util.Queue;

public class ScriptInterpreter {

    private PrintStream logWriter = java.lang.System.out;

    /**
     * Execution context.
     *
     * @var array
     */
    private Context context;

    /**
     * Holds a reference to the write()-Function for getting the output buffer after execution.
     *
     * @var Writer
     */
    private Writer writer = new Writer();
    private int flags;

    /**
     * Print Error Messages in the script output.
     */
    public static int FLAG_SHOW_ERROR = 1;
    /**
     * Prints the stacktrace in script output, if an error occure.
     */
    public static int FLAG_SHOW_TRACE = 2;
    /**
     * Throw @{@link ScriptRuntimeException} if an error occured.
     */
    public static int FLAG_THROW_ERROR = 4;
    /**
     * Show debug messages on the standard outpout.
     */
    public static int FLAG_DEBUG = 8;
    /**
     * Only allow access to objects which are marked as @{@link Scriptable}.
     */
    public static int FLAG_ALLOW_UNSECURE = 16;

    private static boolean secure = true;
    private DslStatement rootStatement;

    public ScriptInterpreter() {
        this(FLAG_SHOW_ERROR + FLAG_THROW_ERROR );
    }

    public ScriptInterpreter(int flags) {
        this.flags = flags;

        this.context = new Context( this.hasFlag(FLAG_ALLOW_UNSECURE) );
        this.addStandardContext();
    }

    /**
     * adds an external context to the interpreter environment.
     *
     * @param object
     */
    public void addToContext(String name, Object object) {
        this.context.put(name, object);
    }


    public void prepareCode(String code) throws ScriptParserException {

        // Splitting the source code into tokens (the "Lexer")
        Lexer lexer = new Lexer();
        lexer.tokenize(code);
        Queue<Token> token = lexer.getToken();

        if   ( this.hasFlag( FLAG_DEBUG )) {

            if   ( this.logWriter != null ) {
                int line = 0;
                logWriter.print("Tokenized source:");
                for( Token tok : token ) {
                    if   ( line != tok.getLineNumber() ) {
                        line = tok.getLineNumber();
                        logWriter.print( "\n" + String.format("%6s", line) );
                    }

                    logWriter.print( tok.getValue() );
                }
                logWriter.println();
            }
        }
        // it has no security impact, so let's do it always.
        this.addToContext("Script", new Script(token.toArray(new Token[]{}), this.rootStatement));

        // Creating a syntax tree (abstract syntax tree, AST).
        AstParser parser = new AstParser();
        this.rootStatement = parser.parse(token);

        if   ( this.hasFlag( FLAG_DEBUG )) {

            if   ( this.logWriter != null ) {
                logWriter.println("Syntax Tree:");
                logWriter.print( this.rootStatement );
            }
        }
    }


    public Object run(Map<String,Object> additionalContext ) throws ScriptRuntimeException {
        this.context.putAll( additionalContext );
        return run();
    }


    public Object run() throws ScriptRuntimeException {

        if   ( this.hasFlag( FLAG_DEBUG )) {

            if   ( this.logWriter != null ) {
                logWriter.println( "Context:"+ this.context );
            }
        }

        if   ( rootStatement == null ) {
            throw new ScriptRuntimeException("No statement found.");
        }

        try {
            // Executing the syntax tree.
            return this.rootStatement.execute(this.context);
        } catch (ScriptRuntimeException e) {
            if (this.hasFlag(FLAG_SHOW_ERROR)) {
                if (this.hasFlag(FLAG_SHOW_TRACE))
                    this.writer.write(e.toString());
                else
                    this.writer.buffer.append(e.getMessage());
            }
            if (this.hasFlag(FLAG_THROW_ERROR))
                throw e;
            else
                return null;
        }
    }


    /**
     * Parses and runs the DSL code.
     *
     * If you want to cache the code, use {@link #prepareCode(String)} and {@link #run()}.
     *
     * @param code String Script-Code
     * @return mixed value of last return statement (if any)
     * @throws ScriptException
     */
    public Object runCode(String code) throws ScriptException {

        this.prepareCode(code);
        return this.run();
    }


    /**
     * Gets the output which was written by the code.
     *
     * @return mixed
     */
    public String getOutput() {

        return this.writer.getBuffer();
    }

    /**
     * @return bool
     */
    public static boolean isSecure() {
        return secure;
    }

    /**
     * Adding the standard context.
     *
     * @return void
     */
    protected void addStandardContext() {
        // Standard-Globals

        // Standard JS objects
        this.addToContext("Math", new MathWrapper());
        this.addToContext("Array", new ArrayWrapper());
        this.addToContext("String", new StringWrapper());
        this.addToContext("Number", new NumberWrapper());
        this.addToContext("Date", new DateWrapper());

        // Custom Scriptbox objects
        this.addToContext("System", new de.openrat.jscriptbox.standard.System());
        this.addToContext("write", this.writer);
        this.addToContext("writeln", new WriteWrapper(this.writer, "\n" ));
        this.addToContext("print", new WriteWrapper(this.writer, "" ));
        this.addToContext("println", new WriteWrapper(this.writer, "\n" ));
    }


    public void setLogWriter(PrintStream logWriter) {
        this.logWriter = logWriter;
    }

    public boolean hasFlag( int flag) {
        return (this.flags & flag) > 0;
    }

    public void addFlag( int flag ) {
        this.flags += flag;
    }
    public void removeFlag( int flag ) {
        this.flags -= flag;
    }
}
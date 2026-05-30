package de.jandankert.jscriptbox;

import de.jandankert.jscriptbox.executor.ScriptInterpreter;
import junit.framework.TestCase;

public class FunctionTest extends TestCase {

    public void testSimpleFunction() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.runCode("""
                sayHello(); // ok, lets call the function

                // My first function
                function sayHello() {
                  write("Hello, World");
                }
                """);
        assertEquals("Hello, World", interpreter.getOutput());
    }

    public void testFunctionWithParameter() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.runCode("""
                say("Hello, World"); // ok, lets call the function

                function say( text ) {
                    write( text );
                }
                """);
        assertEquals("Hello, World", interpreter.getOutput());

    }
    public void testFunctionWithNestedFunctionAndParameter() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.runCode("""
                
                say( text() ); // ok, lets call the function

                function say( text ) {
                    write( text );
                }
                function text() {
                    return "Hello, World";
                }
                """);
        assertEquals("Hello, World", interpreter.getOutput());

    }
}

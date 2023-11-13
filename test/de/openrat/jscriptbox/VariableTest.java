package de.openrat.jscriptbox;

import de.openrat.jscriptbox.executor.ScriptInterpreter;
import junit.framework.TestCase;

public class VariableTest extends TestCase{

    public void testHelloWorldFromVariable() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.runCode("""
               text = "Hello, World";
               write( text );
               """);

        assertEquals( "Hello, World",interpreter.getOutput() );
    }


}

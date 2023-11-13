package de.openrat.jscriptbox;

import de.openrat.jscriptbox.executor.ScriptInterpreter;
import junit.framework.TestCase;

public class SimpleTest extends TestCase{

    public void testNothingToDo() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.runCode( "// nothing to do");
        assertNull(interpreter.getOutput());
    }

    public void testHelloWorld() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        Object result = interpreter.runCode("""
               return "Hello, World";
               """);

        assertEquals( "Hello, World",result);


        interpreter.runCode("""
               write("Hello, World");
               """);

        assertEquals( "Hello, World",interpreter.getOutput() );
    }


}

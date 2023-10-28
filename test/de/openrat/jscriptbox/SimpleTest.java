package de.openrat.jscriptbox;

import de.openrat.jscriptbox.executor.ScriptInterpreter;
import junit.framework.TestCase;

public class SimpleTest extends TestCase{

    public void testSimpleAddition() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.runCode( "write(1+1);");
        assertEquals("2",interpreter.getOutput());

        interpreter.runCode( "write(1+'1');");
        assertEquals("2",interpreter.getOutput());
    }

    public void testSimpleSubstraction() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.runCode( "write(3-2);");
        assertEquals("1",interpreter.getOutput());

        interpreter.runCode( "write(3-'2');");
        assertEquals("1",interpreter.getOutput());
    }
}

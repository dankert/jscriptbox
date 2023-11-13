package de.openrat.jscriptbox;

import de.openrat.jscriptbox.executor.ScriptInterpreter;
import junit.framework.TestCase;

public class MathTest extends TestCase{

    public void testCalculations() throws Exception {

        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.runCode( "write( 6+2 );");
        assertEquals("8",interpreter.getOutput());
        interpreter.runCode( "write( 6-2 );");
        assertEquals("4",interpreter.getOutput());
        interpreter.runCode( "write( 12/3 );");
        assertEquals("4",interpreter.getOutput());

        interpreter.runCode( "write( 6*3 );");
        assertEquals("18",interpreter.getOutput());
    }
}

package de.openrat.jscriptbox;

import de.openrat.jscriptbox.executor.ScriptInterpreter;
import junit.framework.TestCase;

public class ArithmeticTest extends TestCase{

    public void testParenthesis() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.runCode( "write(1+2*3);");
        assertEquals("7",interpreter.getOutput());

        interpreter.runCode( "write( (1+2)*3);");
        assertEquals("9",interpreter.getOutput());

        assertEquals( 28,interpreter.runCode( "return 1+9*3;") );
    }
}

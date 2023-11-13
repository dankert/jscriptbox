package de.openrat.jscriptbox;

import de.openrat.jscriptbox.executor.ScriptInterpreter;
import junit.framework.TestCase;

public class StringTest extends TestCase{

    public void testConcats() throws Exception {

        ScriptInterpreter interpreter = new ScriptInterpreter();

        assertEquals("Twenty-Two 22",interpreter.runCode( "return 'Twenty-Two '+22;") );
    }
}

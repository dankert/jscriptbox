package de.openrat.jscriptbox;

import de.openrat.jscriptbox.executor.ScriptInterpreter;
import junit.framework.TestCase;

public class CacheTest extends TestCase {

    public void testCachedCode() throws Exception {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        interpreter.prepareCode("""
                return "Hello, World";
                """);
        assertEquals("Hello, World", interpreter.run());
        assertEquals("Hello, World", interpreter.run());
    }


}

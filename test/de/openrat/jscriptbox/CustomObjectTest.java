package de.openrat.jscriptbox;

import de.openrat.jscriptbox.example.Person;
import de.openrat.jscriptbox.example.ScriptSystem;
import de.openrat.jscriptbox.exception.ScriptRuntimeException;
import de.openrat.jscriptbox.executor.ScriptInterpreter;
import junit.framework.TestCase;

public class CustomObjectTest extends TestCase {

    public void testSecure() throws Exception {

        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.addToContext("System",ScriptSystem.class);
        interpreter.addToContext("Person",new Person("Bob"));

        try {
            interpreter.runCode("""
                for( value of System.getSystemProperties() ) {
                  write( "Systemproperty: " + value ); 
                }
                """);
            throw new IllegalStateException("System should not be readable");
        } catch( Exception e ) {
            if   ( ! (e instanceof ScriptRuntimeException) )
                throw e;
            //assertEquals(DslRuntimeException.class,e.getClass());
            // default is secure
        }
    }


    public void testCustomObject() throws Exception {

        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.addFlag( ScriptInterpreter.FLAG_DEBUG );

        interpreter.addToContext("System",ScriptSystem.class);
        interpreter.addToContext("bob",new Person("Bob"));

        interpreter.runCode("""
              write( bob.greet() ); 
            """);

        assertEquals("Hello, Bob",interpreter.getOutput() );
    }

}

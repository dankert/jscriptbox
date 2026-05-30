package de.jandankert.jscriptbox;

import de.jandankert.jscriptbox.executor.ScriptInterpreter;
import de.jandankert.jscriptbox.parser.Template;
import junit.framework.TestCase;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringContains;

public class TemplateTest extends TestCase {

    public void testTemplate() throws Exception {

        Template template = new Template();

        template.parseTemplate("""
                        <html>
                        <body>
                        <% age = 12; %>
                        Next year your age is <%= (age+1) %></br>
                        </body>
                        </html>
                """);

        ScriptInterpreter interpreter = new ScriptInterpreter();
        interpreter.runCode(template.getScriptCode());

        MatcherAssert.assertThat(interpreter.getOutput(), StringContains.containsString("Next year your age is 13"));

    }


}

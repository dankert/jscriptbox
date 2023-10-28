package de.openrat.jscriptbox.example;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * System infos.
 * This class is NOT marked as "scriptable", so it is
 * not available if the scriptbox is running with FLAG_SECURE.
 */
public class ScriptSystem {

    /**
     * get all system properties.
     *
     * @return
     */
    public static Map<String, String> getSystemProperties() {
        return java.lang.System.getProperties().entrySet().stream().collect(
                Collectors.toMap(
                        e -> String.valueOf(e.getKey()),
                        e -> String.valueOf(e.getValue()),
                        (prev, next) -> next, HashMap::new
                ));
    }

    /**
     * Get the system property.
     *
     * @param name
     * @return
     */
    public static String getSystemProperty(String name) {
        return java.lang.System.getProperty(name);
    }

    /**
     * Gives the current JVM version.
     *
     * @return
     */
    public static String getVersion() {
        return Runtime.version().toString();
    }
}

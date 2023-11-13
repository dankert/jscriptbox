package de.openrat.jscriptbox.context;

import de.openrat.jscriptbox.exception.ScriptRuntimeException;

import java.util.HashMap;
import java.util.Map;

/**
 * The actual context while execution.
 * The context can by a Map or a object with methods/properties.
 */
public interface Context  {


    boolean isAllowNonScriptableObjects();

    Context copy();

    public Context copyAndPut( Map<String,Object> subContext );


    void put(String name, Object object);

    void putAll(Map<String, ? extends Object> additionalContext);

    boolean containsKey(String name);

    Object get(String name) throws ScriptRuntimeException;
}

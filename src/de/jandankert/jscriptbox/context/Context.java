package de.jandankert.jscriptbox.context;

import de.jandankert.jscriptbox.exception.ScriptRuntimeException;

import java.util.Map;

/**
 * The actual context while execution.
 * The context can by a Map or a object with methods/properties.
 */
public interface Context  {


    boolean isAllowNonScriptableObjects();

    /**
     * gets a copy of this context.
     * @return {@link Context}
     */
    Context copy();

    public Context copyAndPut( Map<String,Object> subContext );


    void put(String name, Object object);

    void putAll(Map<String, ? extends Object> additionalContext);

    boolean containsKey(String name);

    Object get(String name) throws ScriptRuntimeException;
}

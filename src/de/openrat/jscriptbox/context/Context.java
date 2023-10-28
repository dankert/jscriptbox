package de.openrat.jscriptbox.context;

import java.util.HashMap;
import java.util.Map;

/**
 * The actual context while execution.
 * The context can by a Map or a object with methods/properties.
 */
public class Context extends HashMap<String,Object> {

    public Context(boolean allowNonScriptableObjects) {
        super();
        this.allowNonScriptableObjects = allowNonScriptableObjects;
    }

    private boolean allowNonScriptableObjects;

    private Scriptable object;

    public boolean isObject() {
        return object != null;
    }

    public Scriptable getObject() {
        return object;
    }

    public void setObject(Scriptable object) {
        this.object = object;
    }

    public boolean isAllowNonScriptableObjects() {
        return allowNonScriptableObjects;
    }

    public Context copy() {
        Context copy = new Context(allowNonScriptableObjects);
        copy.putAll( this );
        return copy;
    }

    public Context copyAndPut( Map<String,Object> subContext ) {
        Context copy = copy();
        copy.putAll( subContext );
        return copy;
    }
}

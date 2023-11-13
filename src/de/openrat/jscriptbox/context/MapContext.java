package de.openrat.jscriptbox.context;

import java.util.HashMap;
import java.util.Map;

/**
 * The actual context while execution.
 * The context can by a Map or a object with methods/properties.
 */
public class MapContext implements Context {

    Map<String,Object> values = new HashMap<>();

    public MapContext(boolean allowNonScriptableObjects) {
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

    public MapContext copy() {
        MapContext copy = new MapContext(allowNonScriptableObjects);
        copy.values.putAll( this.values );
        return copy;
    }

    public MapContext copyAndPut(Map<String,Object> subContext ) {
        MapContext copy = copy();
        copy.putAll( subContext );
        return copy;
    }

    @Override
    public void put(String name, Object object) {
        this.values.put(name,object);
    }

    @Override
    public void putAll(Map<String, ? extends Object> additionalContext) {
        this.values.putAll( additionalContext);
    }

    @Override
    public boolean containsKey(String name) {
        return this.values.containsKey(name);
    }

    @Override
    public Object get(String name) {
        return this.values.get(name);
    }


    @Override
    public String toString() {
        return "MapContext{" +
                "secure=" + !allowNonScriptableObjects +
                ", content=" + super.toString() +
                '}';
    }
}

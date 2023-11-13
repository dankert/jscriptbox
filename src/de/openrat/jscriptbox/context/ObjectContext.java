package de.openrat.jscriptbox.context;

import de.openrat.jscriptbox.exception.ScriptRuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * The actual context while execution.
 * The context can by a Map or a object with methods/properties.
 */
public class ObjectContext implements Context {

    public ObjectContext(Scriptable object, boolean allowNonScriptableObjects) {
        super();
        this.scriptable = object;
        this.allowNonScriptableObjects = allowNonScriptableObjects;
    }

    private boolean allowNonScriptableObjects;

    private Scriptable scriptable;

    public boolean getScriptable() {
        return scriptable != null;
    }

    public boolean isAllowNonScriptableObjects() {
        return allowNonScriptableObjects;
    }

    public ObjectContext copy() {
        throw new IllegalStateException("ObjectContext cannot be copied");
    }

    public ObjectContext copyAndPut(Map<String,Object> subContext ) {
        throw new IllegalStateException();
    }

    @Override
    public void put(String name, Object object) {
        throw new IllegalStateException();
    }

    @Override
    public void putAll(Map<String, ? extends Object> additionalContext) {

        throw new IllegalStateException();
    }

    @Override
    public boolean containsKey(String name) {
        return false;
    }

    @Override
    public Object get(String name) throws ScriptRuntimeException {

        try {
            Field field = scriptable.getClass().getField(name);
            return field.get(scriptable);
        } catch (NoSuchFieldException e) {
            //
        } catch (IllegalAccessException e) {
            throw new ScriptRuntimeException("Property or method '"+name+"' found in '"+scriptable.toString()+"' is not accessible." );
        }

        try {
            Method method = scriptable.getClass().getMethod(name,scriptable.getClass());
            return method;
        }catch( NoSuchMethodException e) {

        }

        throw new ScriptRuntimeException("No property or method '"+name+"' found in '"+scriptable.toString()+"'" );
    }


    @Override
    public String toString() {
        return "Context{" +
                "secure=" + !allowNonScriptableObjects +
                ", content=" + super.toString() +
                '}';
    }
}

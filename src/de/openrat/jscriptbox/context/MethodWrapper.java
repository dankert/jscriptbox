package de.openrat.jscriptbox.context;

import java.lang.reflect.Method;

public class MethodWrapper {

    private final Scriptable scriptable;
    private final Method method;

    public MethodWrapper(Scriptable scriptable, Method method) {
        this.scriptable = scriptable;

        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public Scriptable getScriptable() {
        return scriptable;
    }

    @Override
    public String toString() {
        return scriptable.getClass() + "#" + method.getName()+"()";
    }
}

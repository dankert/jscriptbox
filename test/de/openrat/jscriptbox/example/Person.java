package de.openrat.jscriptbox.example;

import de.openrat.jscriptbox.context.Scriptable;

public class Person implements Scriptable {
    private String name;

    public String greet() {
        return "Hello, " + name;
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

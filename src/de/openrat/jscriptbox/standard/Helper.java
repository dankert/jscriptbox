package de.openrat.jscriptbox.standard;


import de.openrat.jscriptbox.context.Scriptable;

public class Helper
{
    public static String getHelp( Scriptable obj ) {

        return "Object properties: " + obj.toString()+"";
    }
}
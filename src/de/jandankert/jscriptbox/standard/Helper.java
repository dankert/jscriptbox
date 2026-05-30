package de.jandankert.jscriptbox.standard;


import de.jandankert.jscriptbox.context.Scriptable;

public class Helper
{
    public static String getHelp( Scriptable obj ) {

        return "Object properties: " + obj.toString()+"";
    }
}
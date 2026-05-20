package de.openrat.jscriptbox.formatter;

import de.openrat.jscriptbox.ast.DslStatement;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectDumper {

    public static CharSequence getFormat(Object statement, CharSequence indentChars) {
        StringBuffer buffer = new StringBuffer();

        List<DumpLine> dumpLines = dumpStatement(statement, 0);
        for( DumpLine line : dumpLines ) {
            buffer.append( indentChars.toString().repeat(line.depth ) + line.line + "\n" );
        }

        return buffer;
    }


    public static List<DumpLine> dumpStatement(Object statement, int depth) {
        List<DumpLine> lines = new ArrayList<>();

        if   ( statement == null ) {
            lines.add( new DumpLine( depth, "NULL" ) );
            return lines;
        }

        lines.add( new DumpLine( depth, statement.getClass().getSimpleName() + " " + statement.toString() ) );
        for( Field field : statement.getClass().getDeclaredFields() ) {
            if   (!Modifier.isStatic(field.getModifiers())) {
                try {
                    field.setAccessible(true);
                    lines.add( new DumpLine(depth+1, field.getName() + ": " + statement.getClass().getSimpleName()));
                    if   ( field.get(statement) instanceof Map<?,?> list) {
                        for (Map.Entry<?,?> entry : list.entrySet() ) {
                            lines.add(new DumpLine(depth + 1, statement.getClass().getSimpleName() + " " + statement.toString()));
                            lines.addAll(dumpStatement(entry, depth + 2));
                        }
                    }
                    else if   ( field.get(statement) instanceof List<?> list) {
                        for ( Object l : list )
                            lines.addAll( dumpStatement( l, depth+2) );
                    }
                    else if   ( field.get(statement) instanceof Number s) {
                        lines.add( new DumpLine( depth+1, s.toString() ) );
                    }
                    else if   ( field.get(statement) instanceof CharSequence s) {
                        lines.add( new DumpLine( depth+1, "\""+s.toString()+"\"" ) );
                    }
                    else
                        lines.addAll( dumpStatement( field.get(statement), depth+2) );
                } catch (IllegalAccessException | InaccessibleObjectException e1) {
                    throw new RuntimeException("could not access field "+field.getName() + " of "+statement.getClass().getName(),e1);
                }
            }
        }

        return lines;
    }


    public static record DumpLine(
            int depth,
            CharSequence line
    ) {
    }
}

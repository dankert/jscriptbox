# Script Sandbox for Java

## Overview

This is a script interpreter for Java. Custom code is parsed and interpreted in a sandbox.

The code syntax is similar to [Javascript](https://developer.mozilla.org/en-US/docs/Web/JavaScript). More precisely, it is a **subset of Javascript**.

## Advantages

Tiny library with zero dependencies and a small no memory footprint. No need for a graal JS or Node JS engine.

## Disadvantages

Probably slow. And by no means a complete javascript engine.


## History

It was originally written in PHP for the [OpenRat CMS](http://www.openrat.de) and then migrated to Java.


## Details

Scriptbox is an in-memory script interpreter for the Java Virtual Machine. The scripts are interpreted directly in the syntax tree and are **not** transpiled to native Bytecode.

A script is running in its sandbox, but it may access java objects if you provide appropriate classes (see below).

It consists of a lexer, a syntax parser and an interpreter.

The scripts may be used as a [domain specific language (DSL)](https://en.wikipedia.org/wiki/Domain-specific_language) in your application.


## Usage

Just use the `ScriptInterpreter` to run your code:

```java
    ScriptInterpreter interpreter = new ScriptInterpreter();
    interpreter.runCode( """
      write( "Hello, World" );"
      """ );
```

### Get the output

For getting the standard output, simply call `getOutput()`:

```java
    ScriptInterpreter interpreter = new ScriptInterpreter();
    interpreter.runCode( "write( \"Hello, World\" );" );
    CharSequence output = interpreter.getOutput() ); // get the output "Hello, World"
```

### Add classes to the script context

A very powerful approach is to make custom objects from your application available in the script context:

```java
    ScriptInterpreter interpreter = new ScriptInterpreter();
    interpreter.addToContext("bob",new Person("Bob"));
    interpreter.runCode("""
          write( bob.greet() ); 
        """);
```

While in secure mode, your classes must implement the `Scriptable` interface, then your code may execute

    myclass.method();

### Return values

```java
    ScriptInterpreter interpreter = new ScriptInterpreter();
    String returnValue = interpreter.runCode( code );
```

### Caching
You may cache the Script for multiple executions:

```java
    ScriptInterpreter interpreter = new ScriptInterpreter();
    interpreter.prepareCode("""
            return "Hello, World";
            """);
    assertEquals("Hello, World", interpreter.run());
    assertEquals("Hello, World", interpreter.run());
```
## Syntax

The language syntax is a subset of javascript.

## Features

### comments

single line comments like

```javascript
    // this is a comment
```

and multiline comments like

```javascript

    /**
     * this is a comment
     */
```

are supported


### Text

```javascript
    write( "this is a 'string'" );  // writes to standard out
    write( 'this is a "string"' );  // writes to standard out 
```


### Variables

variables and string concatenation:

```javascript
    age = 18;
    write("my age is " + age );
```

variables *may* be initialized with `let`,`var` or `const` but this is optional:

```javascript
    let age = 18; // "let" is optional and completely ignored
    write("my age is " + age );
```

every variable is _block scoped_.


### Function scope

variables are valid for the current block.

```javascript
    age = 18;

    function add() {
        age = age + 1;
        write( "next year, you are " + age ); // 19
    }
    add();

    write( "but this year you are " + age ); // 18
```

### Function calls

Functions are auto-hoisted.

Example:

```javascript
    write( "powered by " + name() );

    function name() {
        return "script sandbox";
    {
```

### if / else

```javascript
    age = 17;
    if   ( age < 18 )
        write( "you are under 18" );
    else {
        write( "you are already 18" );
        write( "you are allowed to enter" );
    }
```

### Full arithmetic calculations

```javascript
    write( 1 + 2 * 3 ); // this resolves to 7 because of the operator priority
```
### arrays and for loops

```javascript
    animals = Array.of('lion', 'ape', 'fish');

    for( animal of animals )
        write( animal + " is an animal." );
```

### Access object properties

```javascript
    write( "PI is " + Math.PI );
```
### throw an error

You may throw an error:

```javascript
    throw "this is an error";
```
The message is thrown as a `ScriptUserDefinedException` and is able to be catched from the calling Java code.

Hint: try/catch blocks in scripts are **not** supported.

## Template script

Remember JSP, Smarty, Twig or Freemarker? Check out this template parser with a JSP-like syntax:

```html
    <html>
    <body>
    <% age = 12; %>
    Next year your age is <%= (age+1) %></br>
    </body></html>
```

### Usage

```java
    Template template = new Template();

    template.parseTemplate("""
                    <html>
                    <body>
                    <% age = 12; %>
                    Next year your age is <%= (age+1) %></br>
                    </body>
                    </html>
            """);

    ScriptInterpreter interpreter = new ScriptInterpreter();
    interpreter.runCode(template.getScriptCode());
```


## Unsupported

- There is NO support for creating classes or objects.
- no asynchronous things like `async` or `await`.
- No try/catch
- No `window`, `document` or `navigator` objects

## FAQ

_Does it generate Java sourcecode oder bytecode?_

No. The Interpreter works in memory. There is no way to create Java code, even if it would be possible to implement.

_Is it slow?_

Yes, maybe, because there is no cache and no compilation to bytecode. You may cache the syntax tree (see above).

_What about memory leaks_

When the interpreter is cleaned up by the JVM garbage collector, all of the script is freed from the heap memory.

_Is it safe?_

The code execution is sandboxed. No Java objects are directly available for the script. The default objects (Math, Number, Array) are safe. But please be careful if you are exposing your internal Java classes.

The Interpreter starts per default in secure mode, so only methods of objects, whose classes are marked as "Scriptable", can be called.

_Why did you do this?_

Because it was possible ;) And I needed a sandboxed DSL (domain specific language) for my CMS.
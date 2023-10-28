# Script Sandbox for Java

## Overview

This is a script interpreter for Java. Custom code is parsed and interpreted in a sandbox.

The code syntax is similar to [Javascript](https://developer.mozilla.org/en-US/docs/Web/JavaScript). More precisely, it is a subset of Javascript.

## History

It was orginally written in PHP for the [OpenRat CMS](http://www.openrat.de) and then migrated to Java.


## Details

Scriptbox is an in-memory script interpreter for the Java Virtual Machine. The scripts are interpreted directly in the syntax tree and are **not** transpiled to native Bytecode.

A script is running in its sandbox, but it may access java objects if you provide appropriate classes (see below).

It consists of a lexer, a syntax parser and an interpreter.

The scripts may be used as a [domain specific language (DSL)](https://en.wikipedia.org/wiki/Domain-specific_language) in your application.


## Usage

There is the `dsl.DslInterpreter` class to run your code:

    DslInterpreter interpreter = new DslInterpreter();
    interpreter.runCode( $code );

### get output

For getting the standard output, simply call `getOutput()`:

    $interpreter = new DslInterpreter();
    $interpreter->runCode( $code );
    $output = $interpreter->getOutput() ); // get the output

### add context

you may add custom objects to the calling context

    $interpreter = new DslInterpreter();
    $interpreter->addContext( [ 'mycontext'=> new MyContextObject() ]  );
    $interpreter->runCode( $code );

Your class `MyContextObject` must implement `dsl.Scriptable`,then your code may contain

    mycontext.method();

### Return values

```java
    DslInterpreter interpreter = new DslInterpreter();
    returnValue = interpreter.runCode( code );
```

### Caching
You may cache the Script for multiple executions:

```java
    DslInterpreter interpreter = new DslInterpreter();
    interpreter.prepareCode( code );
    
    interpreter.run(); // this can be called multiple times.

```
## Syntax

The language syntax is a subset of javascript.

## Features

### comments

single line comments like

    // this is a comment

and multiline commens like

    /**
     * this is a comment
     */

are supported


### text

    write( "this is a 'string'" );  // writes to standard out
    write( 'this is a "string"' );  // writes to standard out 


### variables

variables and string concatenation:

    age = 18;
    write("my age is " + age );

variables *may* be initialized with `let`,`var` or `const` but this is optional:

    let age = 18; // "let" is optional and completely ignored
    write("my age is " + age );

every variable is "block scoped".


### function scope

variables are valid for the current block.

    age = 18;

    function add() {
        age = age + 1;
        write( "next year, you are " + age ); // 19
    }
    add();

    write( "but this year you are " + age ); // 18


### function calls

Example

    write( "powered by " + name() );

    function name() {
        return "script sandbox";
    {


### if / else

    age = 17;
    if   ( age < 18 )
        write( "you are under 18" );
    else {
        write( "you are already 18" );
        write( "you are allowed to enter" );
    }

### full arithmetic calculations

    write( 1 + 2 * 3 ); // this resolves to 7 because of the operator priority

### arrays and for loops

    animals = Array.of('lion', 'ape', 'fish');

    for( animal of animals )
        write( animal + " is an animal." );


### object properties

    write( "PI is " + Math.PI );

### throw

    throw "this is an error";

The message is thrown as a DslRuntimeException and is able to be catched from the calling Java code.

Hint: try/catch blocks in scripts are not supported.

## Template script

Remember Smarty and Twig? Yes, but both have their strange syntax. Check out this template parser with a JSP-like syntax:

    <html>
    <body>
    <% age = 12; %>
    Next year your age is <%= (age+1) %></br>


### Usage

    // Parse the template, this will create a plain script
    $templateParser = new DslTemplate();
    $templateParser->parseTemplate($src);

    // That's all. Lets start the interpreter
    $executor = new DslInterpreter();
    $executor->runCode($templateParser->script);
    echo( $executor->getOutput() ); // get the output


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
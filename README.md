<p align="center">Language Design Proposal</p>




Language Name: ```JC+```

Compiler Implementation Language and Reasoning: ```Java. We all decided on Java because we are all familiar with the language, having taken courses that require us to code in Java.```

Target Language: ```C```

Language Description: ```Our language will have objects and functions with class-based inheritance. We want this so we can learn more about the backend of classes. Because we are implementing class-based inheritance, we must implement subtyping as well. Further, we wanted to implement bounds-checked arrays. Not having this can be a security issue in C so we felt like it would be a cool addition to the language. C is a low-level target language but not so low-level that it would become very difficult to understand.```


Planned Restrictions: ```We will not implement access modifiers for classes.```


Syntax: 
```
var is a variable
integer is a number
methodname is the name of a method
classname is a class name

type::= Int | Void | type[] |  // Built-in types
 	      classname class type; // includes Object and String

op ::= + | - | * | /  // Arithmetic operations
       == | != | < | > // Comparison operations

exp ::= var | integer |  // Variables and integers are expressions
        this | // Refers to my instance
        new type[exp] | // Array declaration
        var[exp]; | // Access array element
        len(exp); | // get length of array 
        exp op exp | // Arithmetic operations
        exp.methodname(exp*) | // Calls a method
        new classname(exp*) | // Creates a new instance of a class

vardec ::= type var | // Variable declaration

stmt ::= 	vardec = exp; | // Variable declaration and assignment
		      { stmt*; } | // block of code with several statements
          var = exp; | // Assignment 
          `println` `(` `exp` `)` | // Prints something to the terminal
          exp.methodname(exp*) | // Calls a method
          var[exp] = exp; | // Modify array element where exp is of equal type
          while (exp)  stmt  | // while loops 
          break; | // break 
          if (exp) stmt else stmt | // if/else 
          return exp; | // return an expression 
          return; | // return Void 

methoddef ::= type methodname( vardec*) stmt | // type vardecs are comma-separated

instancedec ::= vardec; // instance variable declaration 

classdef ::= class classname extends classname { 
              instancedec* 
              constructor(vardec*) stmt* // vardecs are comma-sep 
              methoddef* } 


```
Computation Abstraction Non-Trivial Feature: ```Objects + methods with class-based inheritance.```

Non-Trivial Feature #2: ```Subtyping.```

Non-Trivial Feature #3: ```Bounds checked arrays.```

Work Planned for Custom Component: ```Bounds checked arrays. Until then, all our arrays will be unbounded which can be a security issue.```

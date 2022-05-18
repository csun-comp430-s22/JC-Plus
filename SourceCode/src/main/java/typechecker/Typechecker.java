package typechecker;

import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.HashSet;

import parser.*;

import java.util.ArrayList;
import java.util.HashMap;

// typechecks: well-typed: no type errors
// doesn't typecheck: ill-typed: some number of type errors (>0)

public class Typechecker {
    public static final String BASE_CLASS_NAME = "Object";
    // Things to track:
    // 1.) Variables in scope, and their types
    // 2.) Classes available, parameters constructors take, methods they have, what
    // their parent
    // class is.
    //
    // Sorts of queries we want to make to class information:
    // 1. Is this a valid class?
    // 2. For this class, what are the argument types for the constructor?
    // 3. Does this class support a given method? If so, what are the parameter
    // types for the method?
    // - Need to take inheritance into account
    // 4. Is this given class a subclass of another class?
    // 5. Does our class hierarchy form a tree?

    public final Map<ClassNameToken, ClassDef> classes;

    // includes inherited methods
    public final Map<ClassNameToken, Map<MethodName, MethodDef>> methods;

    public final Program program;

    public static ClassDef getClass(final ClassNameToken className,
            final Map<ClassNameToken, ClassDef> classes) throws TypeErrorException {
        if (className.name.equals(BASE_CLASS_NAME)) {
            return null;
        } else {
            final ClassDef classDef = classes.get(className);
            if (classDef == null) {
                throw new TypeErrorException("no such class: " + className);
            } else {
                return classDef;
            }
        }
    }

    public ClassDef getClass(final ClassNameToken className) throws TypeErrorException {
        return getClass(className, classes);
    }

    public static ClassDef getParent(final ClassNameToken className,
            final Map<ClassNameToken, ClassDef> classes) throws TypeErrorException {
        final ClassDef classDef = getClass(className, classes);
        return getClass(classDef.extendsClassName, classes);
    }

    public ClassDef getParent(final ClassNameToken className) throws TypeErrorException {
        return getParent(className, classes);
    }

    public static void assertInheritanceNonCyclicalForClass(final ClassDef classDef,
            final Map<ClassNameToken, ClassDef> classes) throws TypeErrorException {
        final Set<ClassNameToken> seenClasses = new HashSet<ClassNameToken>();
        seenClasses.add(classDef.className);
        ClassDef parentClassDef = getParent(classDef.className, classes);
        while (parentClassDef != null) {
            final ClassNameToken parentClassName = parentClassDef.className;
            if (seenClasses.contains(parentClassName)) {
                throw new TypeErrorException("cyclic inheritance involving: " + parentClassName);
            }
            seenClasses.add(parentClassName);
            parentClassDef = getParent(parentClassName, classes);
        }
    }

    public static void assertInheritanceNonCyclical(final Map<ClassNameToken, ClassDef> classes)
            throws TypeErrorException {
        for (final ClassDef classDef : classes.values()) {
            assertInheritanceNonCyclicalForClass(classDef, classes);
        }
    }

    // includes inherited methods
    // duplicates are not permitted within the same class, but it's ok to override a
    // superclass' method
    public static Map<MethodName, MethodDef> methodsForClass(final ClassNameToken className,
            final Map<ClassNameToken, ClassDef> classes) throws TypeErrorException {
        final ClassDef classDef = getClass(className, classes);
        if (classDef == null) {
            return new HashMap<MethodName, MethodDef>();
        } else {
            final Map<MethodName, MethodDef> retval = methodsForClass(classDef.extendsClassName, classes);
            final Set<MethodName> methodsOnThisClass = new HashSet<MethodName>();
            for (final MethodDef methodDef : classDef.methods) {
                final MethodName methodName = methodDef.methodName;
                if (methodsOnThisClass.contains(methodName)) {
                    throw new TypeErrorException("duplicate method: " + methodName);
                }
                methodsOnThisClass.add(methodName);
                retval.put(methodName, methodDef);
            }
            return retval;
        }
    }

    public static Map<ClassNameToken, Map<MethodName, MethodDef>> makeMethodMap(
            final Map<ClassNameToken, ClassDef> classes) throws TypeErrorException {
        final Map<ClassNameToken, Map<MethodName, MethodDef>> retval = new HashMap<ClassNameToken, Map<MethodName, MethodDef>>();
        for (final ClassNameToken className : classes.keySet()) {
            retval.put(className, methodsForClass(className, classes));
        }
        return retval;
    }

    // also makes sure inheritance hierarchies aren't cyclical
    public static Map<ClassNameToken, ClassDef> makeClassMap(final List<ClassDef> classes) throws TypeErrorException {
        final Map<ClassNameToken, ClassDef> retval = new HashMap<ClassNameToken, ClassDef>();
        for (final ClassDef classDef : classes) {
            final ClassNameToken className = classDef.className;
            if (retval.containsKey(classDef.className)) {
                throw new TypeErrorException("Duplicate class name: " + className);
            }
            retval.put(className, classDef);

        }

        assertInheritanceNonCyclical(retval);

        return retval;
    }

    public Typechecker(final Program program) throws TypeErrorException {
        this.program = program;
        classes = makeClassMap(program.classes);
        methods = makeMethodMap(classes);
    }

    public static Type getVariable(final Map<Variable, Type> typeEnvironment,
            final Variable variable) throws TypeErrorException {
        final Type retval = typeEnvironment.get(variable);
        if (retval == null) {
            throw new TypeErrorException("Varible not in scope: " + variable.name);
        } else {
            return retval;
        }
    }

    public static Type getArray(final Map<Variable, Type> typeEnvironment,
            final Variable variable) throws TypeErrorException {
        final Type retval = typeEnvironment.get(variable);
        if (retval == null) {
            throw new TypeErrorException("Array not in scope: " + variable.name);
        } else {
            return retval;
        }
    }

    public Type typeofVariable(final VariableExp exp,
            final Map<Variable, Type> typeEnvironment) throws TypeErrorException {
        return getVariable(typeEnvironment, exp.variable);
    }

    public Type typeofArrayVariable(final ArrayExp array,
            final Map<Variable, Type> typeEnvironment) throws TypeErrorException {
        return getArray(typeEnvironment, array.variable.variable);
    }

    public Type typeofThis(final ClassNameToken classWeAreIn) throws TypeErrorException { // Osher: in Deweys file, he
                                                                                          // had classname so i changed
                                                                                          // it to classnameExp to match
                                                                                          // // what dewey did
        if (classWeAreIn == null) {
            throw new TypeErrorException("this used in the entry point");
        } else {
            return new ClassNameType(classWeAreIn); // Osher: we did not have a class name type in our parser and dewey
                                                    // did so i added it in
        }
    }

    public Type typeofOp(final OpExp exp,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {
        final Type leftType = typeof(exp.left, typeEnvironment, classWeAreIn);
        final Type rightType = typeof(exp.right, typeEnvironment, classWeAreIn); // Osher: not exactly certain where
                                                                                 // this function is coming from
        // (leftType, exp.op, rightType) match {
        // case (IntType, PlusOp, IntType) => IntType
        // case (IntType, LessThanOp | EqualsOp, IntType) => Booltype
        // case _ => throw new TypeErrorException("Operator mismatch")
        // }
        if (exp.op instanceof PlusOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType();
            } else {
                throw new TypeErrorException("Operand type mismatch for +");
            }
        } else if (exp.op instanceof MinusOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType(); // Osher: same idea as above, changed to inttype
            } else {
                throw new TypeErrorException("Operand type mismatch for -");
            }
        } else if (exp.op instanceof MultiplicationOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType(); // Osher: same idea as above, changed to inttype
            } else {
                throw new TypeErrorException("Operand type mismatch for *");
            }
        } else if (exp.op instanceof DivisionOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType(); // Osher: same idea as above, changed to inttype
            } else {
                throw new TypeErrorException("Operand type mismatch for /");
            }
        } else if (exp.op instanceof GreaterThanOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType(); // Osher: this originally said booltype, we dont have booltype in our language
                                      // so i changed it to inttype. This is similar to what happens in C
            } else {
                throw new TypeErrorException("Operand type mismatch for >");
            }
        } else if (exp.op instanceof LessThanOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType(); // Osher: this originally said booltype, we dont have booltype in our language
                                      // so i changed it to inttype. This is similar to what happens in C
            } else {
                throw new TypeErrorException("Operand type mismatch for <");
            }
        } else if (exp.op instanceof NotEqualOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType(); // Osher: same idea as above, changed to inttype
            } else {
                throw new TypeErrorException("Operand type mismatch for !=");
            }
        } else if (exp.op instanceof EqualsOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType(); // Osher: same idea as above, changed to inttype
            } else {
                throw new TypeErrorException("Operand type mismatch for ==");
            }
        } else {
            throw new TypeErrorException("Unsupported operation: " + exp.op);
        }
    }

    public MethodDef getMethodDef(final ClassNameToken className,
            final MethodName methodName) throws TypeErrorException {
        final Map<MethodName, MethodDef> methodMap = methods.get(className);
        if (methodMap == null) {
            throw new TypeErrorException("Unknown class name: " + className);
        } else {
            final MethodDef methodDef = methodMap.get(methodName);
            if (methodDef == null) {
                throw new TypeErrorException("Unknown method name " + methodName + " for class " + className);
            } else {
                return methodDef;
            }
        }
    }

    public Type expectedReturnTypeForClassAndMethod(final ClassNameToken className, // Osher: changed to classnameexp
            final MethodName methodName) throws TypeErrorException {
        return getMethodDef(className, methodName).returnType;
    }

    // Doesn't handle access modifiers right now; would be to know which class we
    // are calling from.
    //
    // class Base extends Object {
    // public void basePublic() {}
    // protected void baseProtected() {}
    // private void basePrivate() {}
    // }
    // class Sub extends Base {
    // public void foobar() {
    // this.basePublic(); // should be ok
    // this.baseProtected(); // should be ok
    // this.basePrivate(); // should give an error
    // }
    // }
    // class SomeOtherClass extends Object {
    // public void test() {
    // Sub sub = new Sub();
    // sub.basePublic(); // should be ok
    // sub.baseProtected(); // should give an error
    // sub.basePrivate(); // should give an error
    // }
    // }
    //
    // doesn't handle inherited methods
    // for every class:
    // - Methods on that class
    // - Methods on the parent of that class
    public List<Type> expectedParameterTypesForClassAndMethod(final ClassNameToken className,
            final MethodName methodName)
            throws TypeErrorException {
        final MethodDef methodDef = getMethodDef(className, methodName);
        final List<Type> retval = new ArrayList<Type>();
        for (final VarDec vardec : methodDef.arguments) {
            retval.add(vardec.type);
        }
        return retval;
    }

    public void assertEqualOrSubtypeOf(final Type first, final Type second) throws TypeErrorException {
        if (first.equals(second)) {
            return;
        } else if (first instanceof ClassNameType &&
                second instanceof ClassNameType) {
            final ClassDef parentClassDef = getParent(((ClassNameType) first).className);
            assertEqualOrSubtypeOf(new ClassNameType(parentClassDef.className), second);
        } else {
            throw new TypeErrorException("incompatible types: " + first + ", " + second);
        }
    }

    // List<Type> - expected types
    // List<Exp> - received expressions
    public void expressionsOk(final List<Type> expectedTypes,
            final List<Exp> receivedExpressions,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {
        if (expectedTypes.size() != receivedExpressions.size()) {
            throw new TypeErrorException("Wrong number of parameters");
        }
        for (int index = 0; index < expectedTypes.size(); index++) {
            final Type paramType = typeof(receivedExpressions.get(index), typeEnvironment, classWeAreIn); // Osher: not
                                                                                                          // sure where
                                                                                                          // this
                                                                                                          // function is
                                                                                                          // coming from
            final Type expectedType = expectedTypes.get(index);
            // myMethod(int, bool, int)
            // myMethod( 2, true, 3)
            //
            // myMethod2(BaseClass)
            // myMethod2(new SubClass())
            assertEqualOrSubtypeOf(paramType, expectedType);
        }
    }

    // 1.) target should be a class.
    // 2.) target needs to have the methodname method
    // 3.) need to know the expected parameter types for the method
    //
    // exp.methodname(exp*)
    // target.methodName(params)
    public Type typeofMethodCall(final MethodCallExp exp,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {
        final Type targetType = typeof(exp.target, typeEnvironment, classWeAreIn); // Osher: not sure where typeof is
                                                                                   // coming from
        if (targetType instanceof ClassNameType) {
            final ClassNameType asClassNameType = (ClassNameType) targetType;
            exp.targetType = asClassNameType;
            final ClassNameToken className = asClassNameType.className;
            final List<Type> expectedTypes = expectedParameterTypesForClassAndMethod(className, exp.methodName);
            expressionsOk(expectedTypes, exp.params, typeEnvironment, classWeAreIn);
            return expectedReturnTypeForClassAndMethod(className, exp.methodName);
        } else {
            throw new TypeErrorException("Called method on non-class type: " + targetType);
        }
    }

    public List<Type> expectedConstructorTypesForClass(final ClassNameToken className)
            throws TypeErrorException {
        final ClassDef classDef = getClass(className);
        final List<Type> retval = new ArrayList<Type>();
        if (classDef == null) { // Object
            return retval;
        } else {
            for (final VarDec vardec : classDef.constructorArguments) {
                retval.add(vardec.type);
            }
            return retval;
        }
    }

    // new classname(exp*)
    // new className(params)
    public Type typeofNew(final NewExp exp,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {
        // need to know what the constructor arguments for this class are
        final List<Type> expectedTypes = expectedConstructorTypesForClass(exp.className); // Osher: didnt make edits
                                                                                          // here in order not to break
                                                                                          // code
        expressionsOk(expectedTypes, exp.params, typeEnvironment, classWeAreIn);
        return new ClassNameType(exp.className);
    }

    public Type typeOfNewArray(final NewArrayDeclarationExp exp,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {

        final Type receivedType = typeof(exp.exp, typeEnvironment, classWeAreIn);

        assertEqualOrSubtypeOf(exp.type, new ArrayType());
        assertEqualOrSubtypeOf(receivedType, new IntType());

        return new ArrayType();
    }

    public Type typeOfArray(final ArrayExp exp,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {

        typeofArrayVariable(exp, typeEnvironment);
        typeof(exp.index, typeEnvironment, classWeAreIn);

        return new ArrayType();

    }

    public Type typeofClassNameExp(final ClassNameExp exp,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {

        getClass(exp.className);
        return new ClassNameType(exp.className);

    }

    // classWeAreIn is null if we are in the entry point
    public Type typeof(final Exp exp,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {
        if (exp instanceof IntLiteralExp) {
            return new IntType();
        } else if (exp instanceof VariableExp) {
            return typeofVariable((VariableExp) exp, typeEnvironment);
        } // else if (exp instanceof BoolLiteralExp) { Osher; bool doesnt exist so
          // commented it out
          // return new BoolType();}
        else if (exp instanceof ThisExp) {
            return typeofThis(classWeAreIn);// done
        } else if (exp instanceof LengthExp) {
            return new IntType();// done
        } else if (exp instanceof OpExp) {
            return typeofOp((OpExp) exp, typeEnvironment, classWeAreIn);// done
        } else if (exp instanceof MethodCallExp) {
            return typeofMethodCall((MethodCallExp) exp, typeEnvironment, classWeAreIn);
        } else if (exp instanceof NewExp) {
            return typeofNew((NewExp) exp, typeEnvironment, classWeAreIn); // done
        } else if (exp instanceof NewArrayDeclarationExp) {
            return typeOfNewArray((NewArrayDeclarationExp) exp, typeEnvironment, classWeAreIn); // done
        } else if (exp instanceof ArrayExp) {
            return typeOfArray((ArrayExp) exp, typeEnvironment, classWeAreIn);// done
        } else if (exp instanceof ClassNameExp) {
            return typeofClassNameExp((ClassNameExp) exp, typeEnvironment, classWeAreIn);// done
        } else {
            // add lenExp
            throw new TypeErrorException("Unrecognized expression: " + exp);
        }
    }

    public static Map<Variable, Type> addToMap(final Map<Variable, Type> map,
            final Variable variable,
            final Type type) {
        final Map<Variable, Type> result = new HashMap<Variable, Type>();
        result.putAll(map);
        result.put(variable, type);
        return result;
    }

    public Map<Variable, Type> isWellTypedVar(final VariableInitializationStmt stmt,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {
        final Type expType = typeof(stmt.exp, typeEnvironment, classWeAreIn);
        assertEqualOrSubtypeOf(expType, stmt.vardec.type);
        return addToMap(typeEnvironment, stmt.vardec.variable, stmt.vardec.type); // Osher: not sure about these
                                                                                  // functions and do not want to break
                                                                                  // code
    }

    public Map<Variable, Type> isWellTypedAssign(final AssignmentStmt stmt,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {
        final Type expType = typeof(stmt.exp, typeEnvironment, classWeAreIn);
        final Type variableType = getVariable(typeEnvironment, stmt.var);
        assertEqualOrSubtypeOf(expType, variableType);
        return typeEnvironment;
    }

    public Map<Variable, Type> isWellTypedArrayAssignment(final ArrayAssignment stmt,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {
        final Type expType = typeof(stmt.assignThis, typeEnvironment, classWeAreIn);
        final Type index = typeof(stmt.index, typeEnvironment, classWeAreIn);
        final Type variableType = getVariable(typeEnvironment, stmt.variable.variable);
        assertEqualOrSubtypeOf(expType, new IntType());
        assertEqualOrSubtypeOf(index, new IntType());
        assertEqualOrSubtypeOf(variableType, new IntType());

        return typeEnvironment;
    }

    public Map<Variable, Type> isWellTypedIf(final IfStmt stmt, // Osher: this if implements statement even tho its
                                                                // called ifexp
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn,
            final Type functionReturnType) throws TypeErrorException {
        if (typeof(stmt.guard, typeEnvironment, classWeAreIn) instanceof IntType) {
            isWellTypedStmt(stmt.trueBranch, typeEnvironment, classWeAreIn, functionReturnType); // Osher: not sure how
                                                                                                 // to rework this for
                                                                                                 // our language
            isWellTypedStmt(stmt.falseBranch, typeEnvironment, classWeAreIn, functionReturnType);
            return typeEnvironment;
        } else {
            throw new TypeErrorException("guard of if is not a boolean: " + stmt);
        }
    }

    public Map<Variable, Type> isWellTypedWhile(final WhileStmt stmt,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn,
            final Type functionReturnType) throws TypeErrorException {
        if (typeof(stmt.guard, typeEnvironment, classWeAreIn) instanceof IntType) {
            isWellTypedStmt(stmt.body, typeEnvironment, classWeAreIn, functionReturnType); // Osher: not sure how to
                                                                                           // rework this for our
                                                                                           // language
            return typeEnvironment;
        } else {
            throw new TypeErrorException("guard on while is not a boolean: " + stmt);
        }
    }

    public Map<Variable, Type> isWellTypedBlock(final BlockStmt stmt,
            Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn,
            final Type functionReturnType) throws TypeErrorException {
        for (final Stmt bodyStmt : stmt.stmts) {
            typeEnvironment = isWellTypedStmt(bodyStmt, typeEnvironment, classWeAreIn, functionReturnType);
        }
        return typeEnvironment;
    }

    // return exp;
    public Map<Variable, Type> isWellTypedReturnNonVoid(final ReturnNonVoidStmt stmt,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn,
            final Type functionReturnType) throws TypeErrorException {
        if (functionReturnType == null) {
            throw new TypeErrorException("return in program entry point");
        } else {
            final Type receivedType = typeof(stmt.exp, typeEnvironment, classWeAreIn);
            assertEqualOrSubtypeOf(receivedType, functionReturnType);
            return typeEnvironment;
        }
    }

    public Map<Variable, Type> isWellTypedReturnVoid(final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn,
            final Type functionReturnType) throws TypeErrorException {
        if (functionReturnType == null) {
            throw new TypeErrorException("return in program entry point");
        } else if (!functionReturnType.equals(new VoidType())) {
            throw new TypeErrorException("return of void in non-void context");
        } else {
            return typeEnvironment;
        }
    }

    public Map<Variable, Type> isWellTypedBreakStmt(final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn) throws TypeErrorException {

        return typeEnvironment;

    }

    // bool x = true;
    // while (true) {
    // int x = 17;
    // break;
    // }

    // need to add break
    public Map<Variable, Type> isWellTypedStmt(final Stmt stmt,
            final Map<Variable, Type> typeEnvironment,
            final ClassNameToken classWeAreIn,
            final Type functionReturnType) throws TypeErrorException {
        if (stmt instanceof VariableInitializationStmt) {
            return isWellTypedVar((VariableInitializationStmt) stmt, typeEnvironment, classWeAreIn);
        } else if (stmt instanceof AssignmentStmt) {
            return isWellTypedAssign((AssignmentStmt) stmt, typeEnvironment, classWeAreIn);
        } else if (stmt instanceof ArrayAssignment) {
            return isWellTypedArrayAssignment((ArrayAssignment) stmt, typeEnvironment, classWeAreIn);
        } else if (stmt instanceof IfStmt) {
            return isWellTypedIf((IfStmt) stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof WhileStmt) {
            return isWellTypedWhile((WhileStmt) stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof ReturnNonVoidStmt) {
            return isWellTypedReturnNonVoid((ReturnNonVoidStmt) stmt, typeEnvironment, classWeAreIn,
                    functionReturnType);
        } else if (stmt instanceof ReturnVoidStmt) {
            return isWellTypedReturnVoid(typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof BreakStmt) {
            return isWellTypedBreakStmt(typeEnvironment, classWeAreIn);
        } else if (stmt instanceof PrintlnStmt) {
            typeof(((PrintlnStmt) stmt).exp, typeEnvironment, classWeAreIn);
            return typeEnvironment;
        } else if (stmt instanceof BlockStmt) {
            return isWellTypedBlock((BlockStmt) stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else {
            throw new TypeErrorException("Unsupported statement: " + stmt);
        }
    }

    // methoddef ::= type methodname(vardec*) stmt
    public void isWellTypedMethodDef(final MethodDef method,
            Map<Variable, Type> typeEnvironment, // instance variables
            final ClassNameToken classWeAreIn) throws TypeErrorException {
        // starting type environment: just instance variables
        final Set<Variable> variablesInMethod = new HashSet<Variable>();
        for (final VarDec vardec : method.arguments) {
            final Variable variable = vardec.variable;
            if (variablesInMethod.contains(variable)) {
                throw new TypeErrorException("Duplicate variable in method definition: " + variable);
            }
            variablesInMethod.add(variable);
            // odd semantics: last variable declaration shadows prior one
            typeEnvironment = addToMap(typeEnvironment, variable, vardec.type);
        }

        isWellTypedStmt(method.body,
                typeEnvironment, // instance variables + parameters
                classWeAreIn,
                method.returnType);
    }

    public Map<Variable, Type> baseTypeEnvironmentForClass(final ClassNameToken className) throws TypeErrorException {
        final ClassDef classDef = getClass(className);
        if (classDef == null) {
            return new HashMap<Variable, Type>();
        } else {
            final Map<Variable, Type> retval = baseTypeEnvironmentForClass(classDef.extendsClassName);
            for (final VarDec instanceVariable : classDef.instanceVariables) {
                final Variable variable = instanceVariable.variable;
                if (retval.containsKey(variable)) {
                    throw new TypeErrorException("Duplicate instance variable (possibly inherited): " + variable);
                }
                retval.put(variable, instanceVariable.type);
            }
            return retval;
        }
    }

    // classdef ::= class classname extends classname {
    // vardec*; // comma-separated instance variables
    // constructor(vardec*) {
    // super(exp*);
    // stmt* // comma-separated
    // }
    // methoddef*
    // }

    // -Check constructor
    // -Check methods
    public void isWellTypedClassDef(final ClassDef classDef) throws TypeErrorException {
        // TODO: add instance variables from parent classes; currently broken
        // weird: duplicate instance variables
        // class MyClass extends Object {
        // int x;
        // bool x;
        // ...
        // }
        final Map<Variable, Type> typeEnvironment = baseTypeEnvironmentForClass(classDef.className);
        final Set<Variable> variablesInConstructor = new HashSet<Variable>();
        Map<Variable, Type> constructorTypeEnvironment = typeEnvironment;
        for (final VarDec vardec : classDef.constructorArguments) {
            final Variable variable = vardec.variable;
            if (variablesInConstructor.contains(variable)) {
                throw new TypeErrorException("Duplicate variable in constructor param: " + variable);
            }
            variablesInConstructor.add(variable);
            constructorTypeEnvironment = addToMap(constructorTypeEnvironment, variable, vardec.type);
        }
        // expressionsOk(expectedConstructorTypesForClass(classDef.extendsClassName),
        // classDef.constructorArguments,
        // constructorTypeEnvironment,
        // lassDef.className);

        // check call to super

        isWellTypedBlock(new BlockStmt(classDef.superBody),
                constructorTypeEnvironment,
                classDef.className,
                new VoidType());

        // check methods
        // TODO - this is broken - doesn't check for methods with duplicate names
        //
        // int foo(int x) { ... }
        // int foo(bool b) { ... }
        for (final MethodDef method : classDef.methods) {
            isWellTypedMethodDef(method,
                    typeEnvironment,
                    classDef.className);
        }
    }

    // program ::= classdef* stmt
    public void isWellTypedProgram() throws TypeErrorException {
        for (final ClassDef classDef : program.classes) {
            isWellTypedClassDef(classDef);
        }

        isWellTypedStmt(program.entryPoint,
                new HashMap<Variable, Type>(),
                null,
                null);
    }

    public void CheckTypeCheck(final Program program) throws TypeErrorException {
        typecheck(program);
    }

    public static void typecheck(final Program program) throws TypeErrorException {
        new Typechecker(program).isWellTypedProgram();
    }
}
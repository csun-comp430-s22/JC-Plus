package typechecker;

import parser.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Map;

import javax.sound.midi.Receiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TypecheckerTest {
    public static Typechecker emptyTypechecker() throws TypeErrorException {
        return new Typechecker(new Program(new ArrayList<ClassDef>(),
                new PrintlnStmt(new IntLiteralExp(1))));
    }

    public static Typechecker initTypechecker() throws TypeErrorException {
        final List<ClassDef> typecheck = new ArrayList<ClassDef>();
        final AssignmentStmt assignmentStmt = new AssignmentStmt(new Variable("x"), new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        final ClassNameToken className = new ClassNameToken("foo");
        final ClassNameToken extendsClassName = new ClassNameToken("Object");
        typeEnvironment.put(new Variable("x"), new ClassNameType(className));
        // typeEnvironment.put(new Variable("x"), new ClassNameType(className));
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        instanceVariables.add(new VarDec(new ClassNameType(className), new Variable("x")));
        // instanceVariables.add(new VarDec(new ClassNameType(className), new
        // Variable("x")));
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        constructorArguments.add(new VarDec(new ClassNameType(className), new Variable("x")));
        // constructorArguments.add(new VarDec(new ClassNameType(className), new
        // Variable("x")));
        List<Stmt> superBody = new ArrayList<Stmt>();
        superBody.add(new ReturnVoidStmt());
        List<MethodDef> methods = new ArrayList<MethodDef>();
        List<VarDec> arguments = new ArrayList<VarDec>();
        arguments.add(new VarDec(new ClassNameType(className), new Variable("x")));
        // arguments.add(new VarDec(new ClassNameType(className), new Variable("x")));
        MethodDef newMethod = new MethodDef(new IntType(), new MethodName("x"), arguments,
                new PrintlnStmt(new VariableExp(new Variable("x"))));
        methods.add(newMethod);

        final ClassDef classDef = new ClassDef(className, extendsClassName, instanceVariables, constructorArguments,
                superBody, methods);
        typecheck.add(classDef);

        return new Typechecker(new Program(typecheck,
                new PrintlnStmt(new IntLiteralExp(1))));
    }

    public static Typechecker initTypecheckerParentClass() throws TypeErrorException {
        final List<ClassDef> typecheck = new ArrayList<ClassDef>();
        final AssignmentStmt assignmentStmt = new AssignmentStmt(new Variable("x"), new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        final ClassNameToken className = new ClassNameToken("test");
        final ClassNameToken extendsClassName = new ClassNameToken("foo");
        typeEnvironment.put(new Variable("x"), new ClassNameType(className));
        // typeEnvironment.put(new Variable("x"), new ClassNameType(className));
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        instanceVariables.add(new VarDec(new ClassNameType(className), new Variable("x")));
        // instanceVariables.add(new VarDec(new ClassNameType(className), new
        // Variable("x")));
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        constructorArguments.add(new VarDec(new ClassNameType(className), new Variable("x")));
        // constructorArguments.add(new VarDec(new ClassNameType(className), new
        // Variable("x")));
        List<Stmt> superBody = new ArrayList<Stmt>();
        superBody.add(new ReturnVoidStmt());
        List<MethodDef> methods = new ArrayList<MethodDef>();
        List<VarDec> arguments = new ArrayList<VarDec>();
        arguments.add(new VarDec(new ClassNameType(className), new Variable("x")));
        // arguments.add(new VarDec(new ClassNameType(className), new Variable("x")));
        MethodDef newMethod = new MethodDef(new IntType(), new MethodName("x"), arguments,
                new PrintlnStmt(new VariableExp(new Variable("x"))));
        methods.add(newMethod);
        methods.add(newMethod);

        final ClassDef classDef = new ClassDef(className, extendsClassName, instanceVariables, constructorArguments,
                superBody, methods);
        typecheck.add(classDef);
        final ClassDef classDef2 = new ClassDef(new ClassNameToken("foo"), new ClassNameToken("test"),
                instanceVariables, constructorArguments,
                superBody, methods);
        typecheck.add(classDef2);

        return new Typechecker(new Program(typecheck,
                new PrintlnStmt(new IntLiteralExp(1))));
    }

    @Test
    public void typeOfOpPlusOpTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new IntLiteralExp(1), new PlusOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpPlusOpElseCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new ThisExp(), new PlusOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpMinusOpTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new IntLiteralExp(1), new MinusOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpMinusOpElseCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new ThisExp(), new MinusOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpMultiOpTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new IntLiteralExp(1), new MultiplicationOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpMultiOpElseCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new ThisExp(), new MultiplicationOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpDivOpTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new IntLiteralExp(1), new DivisionOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpDivOpElseCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new ThisExp(), new DivisionOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpGreaterOpTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new IntLiteralExp(1), new GreaterThanOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpGreaterOpElseCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new NewExp(new ClassNameToken("f"), Arrays.asList(new IntLiteralExp(1))),
                new GreaterThanOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpLessThanTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new IntLiteralExp(1), new LessThanOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpLessThanElseCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new ThisExp(), new LessThanOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpGreaterThanElseCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new ThisExp(), new GreaterThanOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpNotEqualsOpTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new IntLiteralExp(1), new NotEqualOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpNotEqualsOpElseCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new ThisExp(), new NotEqualOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpEqualsOpTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new IntLiteralExp(1), new EqualsOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpEqualsOpEdgeCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new ThisExp(), new EqualsOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpEqualsOpElseCaseTest() throws TypeErrorException {
        final Type expectedType = new IntType();
        List<Exp> params = new ArrayList<Exp>();
        params.add(new IntLiteralExp(1));
        NewExp newExp = new NewExp(new ClassNameToken("Object"), params);
        final OpExp op = new OpExp(newExp, new EqualsOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("Object");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfVariableExp() throws TypeErrorException {
        final Type expectedType = new IntType();
        final VariableExp var = new VariableExp(new Variable("x"));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeof(var, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfOpExp() throws TypeErrorException {
        final Type expectedType = new IntType();
        final OpExp op = new OpExp(new IntLiteralExp(1), new EqualsOp(), new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeof(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfMethodCallExp() throws TypeErrorException { // doesnt test methodcallexp, might need to implement
                                                                  // type and refactor parser
        final ClassNameToken class_name = new ClassNameToken("foo");
        final Exp class_exp = new ClassNameExp(class_name);
        final Type expectedType = new IntType();
        final MethodCallExp method_call = new MethodCallExp(new VariableExp(new Variable("f")), new MethodName("x"),
                Arrays.asList(
                        new VariableExp(new Variable("f"))));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("f"), new ClassNameType(class_name));
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = initTypechecker().typeof(method_call, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void typeOfMethodCallEdgeCaseExp() throws TypeErrorException { // doesnt test methodcallexp, might need to
                                                                          // implement type and refactor parser
        final ClassNameToken class_name = null;
        final Exp class_exp = new ClassNameExp(class_name);
        final Type expectedType = new ClassNameType(class_name);
        final MethodCallExp method_call = new MethodCallExp(new VariableExp(new Variable("f")), new MethodName("x"),
                Arrays.asList(
                        new VariableExp(new Variable("f"))));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("f"), new ClassNameType(class_name));
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type receivedType = initTypechecker().typeof(method_call, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }
    /*
     * @Test
     * public void testMethodsForClass() throws TypeErrorException { // doesnt test
     * methodcallexp, might need to implement type and refactor parser
     * final Map <MethodName, MethodDef> expectedType = new HashMap <MethodName,
     * MethodDef>();
     * final List<VarDec> vardecs = Arrays.asList(new VarDec(new IntType(), new
     * Variable("x")));
     * expectedType.put(new MethodName("x"), new MethodDef(new VoidType(), new
     * MethodName("x"), vardecs , new ReturnVoidStmt()));
     * final ClassNameToken className = new ClassNameToken("foo");
     * final ClassNameToken extendsClassName = new ClassNameToken("foo");
     * List<VarDec> instanceVariables = new ArrayList<VarDec>();
     * instanceVariables.add(new VarDec(new IntType(), new Variable("x")));
     * List<VarDec> constructorArguments = new ArrayList<VarDec>();
     * constructorArguments.add(new VarDec(new IntType(), new Variable("x")));
     * List<Stmt> superBody = new ArrayList<Stmt>();
     * superBody.add(new ReturnVoidStmt());
     * List<MethodDef> methods = new ArrayList<MethodDef>();
     * List<VarDec> arguments = new ArrayList<VarDec>();
     * arguments.add(new VarDec(new IntType(), new Variable("x")));
     * MethodDef newMethod = new MethodDef(new IntType(), new MethodName("x"),
     * arguments, new ReturnVoidStmt());
     * methods.add(newMethod);
     * 
     * final ClassDef classDef = new ClassDef(className, extendsClassName,
     * instanceVariables, constructorArguments,
     * superBody, methods);
     * final Map<ClassNameToken, ClassDef> classes = new HashMap<ClassNameToken,
     * ClassDef>();
     * classes.put(className, classDef);
     * final Map<MethodName, MethodDef> typeEnvironment = new HashMap<MethodName,
     * MethodDef>();
     * typeEnvironment.put(new MethodName("x"), new MethodDef(new VoidType(), new
     * MethodName("x"), vardecs , new ReturnVoidStmt()));
     * final Map<MethodName, MethodDef> receivedType =
     * initTypechecker().methodsForClass(className, classes);
     * assertEquals(expectedType, receivedType);
     * }
     */

    @Test
    public void typeOfNew() throws TypeErrorException {
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new ClassNameType(new ClassNameToken("foo")));
        assertEquals(new ClassNameType(new ClassNameToken("foo")),
                initTypechecker().typeofNew(new NewExp(new ClassNameToken("foo"), Arrays.asList(
                        new VariableExp(new Variable("x")))), typeEnvironment, new ClassNameToken("foo")));
    }

    @Test
    public void typeOfNewArray() throws TypeErrorException {
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new ArrayType());
        assertEquals(new ArrayType(),
                initTypechecker().typeof(new NewArrayDeclarationExp(new ArrayType(), new IntLiteralExp(0)),
                        typeEnvironment, new ClassNameToken("foo")));
    }

    @Test
    public void typeOfNewOpTest() throws TypeErrorException {
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new ClassNameType(new ClassNameToken("foo")));
        assertEquals(new ClassNameType(new ClassNameToken("foo")),
                initTypechecker().typeof(new NewExp(new ClassNameToken("foo"), Arrays.asList(
                        new VariableExp(new Variable("x")))), typeEnvironment, new ClassNameToken("foo")));
    }

    @Test
    public void typeOfArrayExpTest() throws TypeErrorException {
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("f"), new ArrayType());

        ArrayExp arrExp = new ArrayExp(new VariableExp(new Variable("f")), new IntLiteralExp(1));
        assertEquals(new ArrayType(),
                emptyTypechecker().typeof(arrExp, typeEnvironment, new ClassNameToken("foo")));
    }

    @Test
    public void typeOfArrayExpEdgeCaseTest() throws TypeErrorException {
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();


        ArrayExp arrExp = new ArrayExp(new VariableExp(new Variable("f")), new IntLiteralExp(1));
        assertEquals(new ArrayType(),
                emptyTypechecker().typeof(arrExp, typeEnvironment, new ClassNameToken("foo")));
    }



    @Test
    public void typeOfEdgeCaseTest() throws TypeErrorException {
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new ClassNameType(new ClassNameToken("foo")));
        assertEquals(new ClassNameType(new ClassNameToken("foo")),
                initTypechecker().typeof(new ExtendsExp(), typeEnvironment, new ClassNameToken("foo")));
    }

    @Test
    public void WellTypedIfStmtTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());
        final IfStmt ifstmt = new IfStmt(new IntLiteralExp(1), new ReturnVoidStmt(), new ReturnVoidStmt());
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(ifstmt, typeEnvironment, classes,
                type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedElseCaseTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());
        final ExpStmt expStmt = new ExpStmt(new IntLiteralExp(0));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(expStmt, typeEnvironment, classes,
                type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedWhileStmtTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());
        final WhileStmt whilestmt = new WhileStmt(new IntLiteralExp(1), new ReturnVoidStmt());
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(whilestmt, typeEnvironment, classes,
                type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedBlockStmtTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());
        List<Stmt> stmts = new ArrayList<Stmt>();
        stmts.add(new ReturnVoidStmt());
        final BlockStmt blockstmt = new BlockStmt(stmts);
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(blockstmt, typeEnvironment, classes,
                type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedReturnNonVoidTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());

        final ReturnNonVoidStmt returnNonVoidStmt = new ReturnNonVoidStmt(new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new IntType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(returnNonVoidStmt, typeEnvironment,
                classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedReturnNonVoidTestEdgeCase() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());

        final ReturnNonVoidStmt returnNonVoidStmt = new ReturnNonVoidStmt(new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = null;
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(returnNonVoidStmt, typeEnvironment,
                classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedReturnVoidTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());

        final ReturnVoidStmt returnVoidStmt = new ReturnVoidStmt();
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(returnVoidStmt, typeEnvironment,
                classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedBreakTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());
        final BreakStmt breakStmt = new BreakStmt();
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        // final Type type2 = new BreakType();
        emptyTypechecker().isWellTypedStmt(breakStmt, typeEnvironment,
                classes, new VoidType());
        // assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedReturnVoidTestEdgeCase() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());

        final ReturnVoidStmt returnVoidStmt = new ReturnVoidStmt();
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = null;
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(returnVoidStmt, typeEnvironment,
                classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedReturnVoidTestEdgeCase2() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());

        final ReturnVoidStmt returnVoidStmt = new ReturnVoidStmt();
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new IntType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(returnVoidStmt, typeEnvironment,
                classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedVarTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());
        expectedType.put(new Variable("f"), new IntType());

        final VariableInitializationStmt variableInitializationStmt = new VariableInitializationStmt(
                new VarDec(new IntType(), new Variable("f")), new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(variableInitializationStmt,
                typeEnvironment, classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedAssignTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());

        final AssignmentStmt assignmentStmt = new AssignmentStmt(new Variable("x"), new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(assignmentStmt,
                typeEnvironment, classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedArrayAssignmentTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());

        final ArrayAssignment arrayStmt = new ArrayAssignment(new VariableExp(new Variable("x")), new IntLiteralExp(1),
                new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(arrayStmt,
                typeEnvironment, classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void TestAssertEqualOrSubtypeOf() throws TypeErrorException {

        emptyTypechecker().assertEqualOrSubtypeOf(new ClassNameType(new ClassNameToken("f")),
                new ClassNameType(new ClassNameToken("foo")));

    }

    @Test
    public void WellTypedAssignAssertEqualsOrSubtypeOfTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new ClassNameType(new ClassNameToken("foo")));

        final AssignmentStmt assignmentStmt = new AssignmentStmt(new Variable("x"),
                new NewExp(new ClassNameToken("foo"),  Arrays.asList(new IntLiteralExp(1)))); // var = exp
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new ClassNameType(new ClassNameToken("foo")));
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new ClassNameType(new ClassNameToken("foo"));
        final Map<Variable, Type> receivedType = initTypechecker().isWellTypedStmt(assignmentStmt,
                typeEnvironment, classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedAssignAssertEqualsOrSubtypeOfTestV2() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new ClassNameType(new ClassNameToken("foo")));

        final AssignmentStmt assignmentStmt = new AssignmentStmt(new Variable("x"),
                new NewExp(new ClassNameToken("foo"),  Arrays.asList(new IntLiteralExp(1)))); // var = exp
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new ClassNameType(new ClassNameToken("Object")));
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new ClassNameType(new ClassNameToken("Object"));
        final Map<Variable, Type> receivedType = initTypechecker().isWellTypedStmt(assignmentStmt,
                typeEnvironment, classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedPrintlnTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());

        final PrintlnStmt printlnstmt = new PrintlnStmt(new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(printlnstmt,
                typeEnvironment, classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedStmtElseCaseTest() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        expectedType.put(new Variable("x"), new IntType());

        final BreakStmt breakstmt = new BreakStmt();
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes = new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable, Type> receivedType = emptyTypechecker().isWellTypedStmt(breakstmt,
                typeEnvironment, classes, type2);
        assertEquals(expectedType, receivedType);
    }

    @Test
    public void baseTypeEnvironmentForClass() throws TypeErrorException {
        final Map<Variable, Type> expectedType = new HashMap<Variable, Type>();
        // expectedType.put(new Variable("x"), new IntType());

        final Map<Variable, Type> receivedType = emptyTypechecker()
                .baseTypeEnvironmentForClass(new ClassNameToken("Object"));
        assertEquals(expectedType, receivedType);
    }

    /*
     * @Test
     * public void WellTypedMethodsForClassTest() throws TypeErrorException {
     * final Map<MethodName, MethodDef> expectedType = new HashMap<MethodName,
     * MethodDef>();
     * // expectedType.put(new Variable("x"), new IntType());
     * 
     * final Map<ClassNameToken, ClassDef> classes = new HashMap<ClassNameToken,
     * ClassDef>();
     * //final Map<MethodName, MethodDef> receivedType =
     * emptyTypechecker().methodsForClass(new ClassNameToken("f"),
     * //classes);
     * //assertEquals(expectedType, receivedType);
     * }
     */

    @Test
    public void WellTypedClassDefTest() throws TypeErrorException {
        // TODO: needs to be fixed not working properly...

        final VoidType expectedType = new VoidType();

        final AssignmentStmt assignmentStmt = new AssignmentStmt(new Variable("x"), new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        // typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken className = new ClassNameToken("foo");
        final ClassNameToken extendsClassName = new ClassNameToken("foo");
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        instanceVariables.add(new VarDec(new IntType(), new Variable("x")));

        // instanceVariables.add(new VarDec(new IntType(), new Variable("x")));
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        constructorArguments.add(new VarDec(new IntType(), new Variable("x")));
        // constructorArguments.add(new VarDec(new IntType(), new Variable("x")));
        List<Stmt> superBody = new ArrayList<Stmt>();
        superBody.add(new ReturnVoidStmt());
        List<MethodDef> methods = new ArrayList<MethodDef>();
        List<VarDec> arguments = new ArrayList<VarDec>();
        arguments.add(new VarDec(new IntType(), new Variable("x")));
        // arguments.add(new VarDec(new IntType(), new Variable("x")));
        // arguments.add(new VarDec(new IntType(), new Variable("x")));
        MethodDef newMethod = new MethodDef(new IntType(), new MethodName("x"), arguments,
                new PrintlnStmt(new VariableExp(new Variable("x"))));
        methods.add(newMethod);

        final ClassDef classDef = new ClassDef(className, extendsClassName, instanceVariables, constructorArguments,
                superBody, methods);
        initTypechecker().isWellTypedClassDef(classDef);
        // final Type receivedType = emptyTypechecker().isWellTypedClassDef(classDef);
        // assertEquals(expectedType, receivedType);
    }

    @Test
    public void testVariableInScope() throws TypeErrorException {
        final Type expectedType = new IntType();
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());

        final Type receivedType = emptyTypechecker().typeofVariable(new VariableExp(new Variable("x")),
                typeEnvironment);
        assertEquals(expectedType, receivedType);
    }

    @Test(expected = TypeErrorException.class)
    public void testVariableOutOfScope() throws TypeErrorException {
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        emptyTypechecker().typeofVariable(new VariableExp(new Variable("x")),
                typeEnvironment);
    }

    @Test
    public void testThisInClass() throws TypeErrorException {
        assertEquals(new ClassNameType(new ClassNameToken("foo")),
                emptyTypechecker().typeofThis(new ClassNameToken("foo")));
    }

    @Test(expected = TypeErrorException.class)
    public void testThisNotInClass() throws TypeErrorException {
        emptyTypechecker().typeofThis(null);
    }

    @Test
    public void WellTypedProgram() throws TypeErrorException {
        // TODO: needs to be fixed not working properly...

        final VoidType expectedType = new VoidType();

        final AssignmentStmt assignmentStmt = new AssignmentStmt(new Variable("x"), new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken className = new ClassNameToken("foo");
        final ClassNameToken extendsClassName = new ClassNameToken("foo");
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        instanceVariables.add(new VarDec(new IntType(), new Variable("x")));
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        constructorArguments.add(new VarDec(new IntType(), new Variable("x")));
        List<Stmt> superBody = new ArrayList<Stmt>();
        superBody.add(new ReturnVoidStmt());
        List<MethodDef> methods = new ArrayList<MethodDef>();
        List<VarDec> arguments = new ArrayList<VarDec>();
        arguments.add(new VarDec(new IntType(), new Variable("x")));
        MethodDef newMethod = new MethodDef(new IntType(), new MethodName("x"), arguments,
                new PrintlnStmt(new VariableExp(new Variable("x"))));
        methods.add(newMethod);

        final ClassDef classDef = new ClassDef(className, extendsClassName, instanceVariables, constructorArguments,
                superBody, methods);
        initTypechecker().isWellTypedClassDef(classDef);
        initTypechecker().isWellTypedProgram();

        initTypechecker().CheckTypeCheck(new Program(new ArrayList<ClassDef>(),
                new PrintlnStmt(new IntLiteralExp(1))));

        // final Type receivedType = emptyTypechecker().isWellTypedClassDef(classDef);
        // assertEquals(expectedType, receivedType);
    }

    @Test
    public void WellTypedProgramTypecheck() throws TypeErrorException {
        // TODO: needs to be fixed not working properly...

        final VoidType expectedType = new VoidType();

        final AssignmentStmt assignmentStmt = new AssignmentStmt(new Variable("x"), new IntLiteralExp(1));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken className = new ClassNameToken("foo");
        final ClassNameToken extendsClassName = new ClassNameToken("foo");
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        instanceVariables.add(new VarDec(new IntType(), new Variable("x")));
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        constructorArguments.add(new VarDec(new IntType(), new Variable("x")));
        List<Stmt> superBody = new ArrayList<Stmt>();
        superBody.add(new ReturnVoidStmt());
        List<MethodDef> methods = new ArrayList<MethodDef>();
        List<VarDec> arguments = new ArrayList<VarDec>();
        arguments.add(new VarDec(new IntType(), new Variable("x")));
        MethodDef newMethod = new MethodDef(new IntType(), new MethodName("x"), arguments,
                new PrintlnStmt(new VariableExp(new Variable("x"))));
        methods.add(newMethod);

        final ClassDef classDef = new ClassDef(className, extendsClassName, instanceVariables, constructorArguments,
                superBody, methods);
        final ClassDef classDef2 = new ClassDef(className, new ClassNameToken("foo"), instanceVariables,
                constructorArguments,
                superBody, methods);

        List<ClassDef> classes = new ArrayList<ClassDef>();
        classes.add(classDef);
        classes.add(classDef2);

        initTypecheckerParentClass().CheckTypeCheck(new Program(classes,
                new PrintlnStmt(new IntLiteralExp(1))));

        // final Type receivedType = emptyTypechecker().isWellTypedClassDef(classDef);
        // assertEquals(expectedType, receivedType);
    }
}
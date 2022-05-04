package typechecker;


import parser.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class TypecheckerTest {
    public static Typechecker emptyTypechecker() throws TypeErrorException {
        return new Typechecker(new Program(new ArrayList<ClassDef>(),
                                           new ExpStmt(new IntLiteralExp(0))));
    }
    @Test
    public void typeOfOpPlusOpTest() throws TypeErrorException{
        final Type expectedType = new IntType();
        final OpExp op = new OpExp (new IntLiteralExp(1),new PlusOp(),new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes= new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }
    @Test
    public void typeOfOpMinusOpTest() throws TypeErrorException{
        final Type expectedType = new IntType();
        final OpExp op = new OpExp (new IntLiteralExp(1),new MinusOp(),new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes= new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }
    @Test
    public void typeOfOpMultiOpTest() throws TypeErrorException{
        final Type expectedType = new IntType();
        final OpExp op = new OpExp (new IntLiteralExp(1),new MultiplicationOp(),new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes= new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }
    @Test
    public void typeOfOpDivOpTest() throws TypeErrorException{
        final Type expectedType = new IntType();
        final OpExp op = new OpExp (new IntLiteralExp(1),new DivisionOp(),new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes= new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }
    @Test
    public void typeOfOpGreaterOpTest() throws TypeErrorException{
        final Type expectedType = new IntType();
        final OpExp op = new OpExp (new IntLiteralExp(1),new GreaterThanOp(),new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes= new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }
    @Test
    public void typeOfOpLessThanTest() throws TypeErrorException{
        final Type expectedType = new IntType();
        final OpExp op = new OpExp (new IntLiteralExp(1),new LessThanOp(),new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes= new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }
    @Test
    public void typeOfOpNotEqualsOpTest() throws TypeErrorException{
        final Type expectedType = new IntType();
        final OpExp op = new OpExp (new IntLiteralExp(1),new NotEqualOp(),new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes= new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }
    @Test
    public void typeOfOpEqualsOpTest() throws TypeErrorException{
        final Type expectedType = new IntType();
        final OpExp op = new OpExp (new IntLiteralExp(1),new EqualsOp(),new IntLiteralExp(2));
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes= new ClassNameToken("foo");
        final Type receivedType = emptyTypechecker().typeofOp(op, typeEnvironment, classes);
        assertEquals(expectedType, receivedType);
    }
    @Test
    public void iftest() throws TypeErrorException{
        final Map<Variable,Type> expectedType = new HashMap<Variable, Type>();
       expectedType.put(new Variable("x"), new IntType());
        final IfExp ifexp = new IfExp(new IntLiteralExp(1), new ReturnVoidStmt(), new ReturnVoidStmt());
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        final ClassNameToken classes= new ClassNameToken("foo");
        final Type type2 = new VoidType();
        final Map<Variable,Type> receivedType = emptyTypechecker().isWellTypedStmt(ifexp, typeEnvironment, classes, type2);
        assertEquals(expectedType, receivedType);

    }
    @Test
    public void testVariableInScope() throws TypeErrorException {
        final Type expectedType = new IntType();
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        
        final Type receivedType =
            emptyTypechecker().typeofVariable(new VariableExp(new Variable("x")),
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
}
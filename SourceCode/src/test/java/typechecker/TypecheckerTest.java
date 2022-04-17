package typechecker;


import parser.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class TypecheckerTest {
    public static final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                 new ExpStmt(new IntLiteralExp(0))));  //Osher: not sure what expstmt is 

    @Test
    public void testVariableInScope() throws TypeErrorException {
        final Type expectedType = new IntType();
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        typeEnvironment.put(new Variable("x"), new IntType());
        
        final Type receivedType =
            emptyTypechecker.typeofVariable(new VariableExp(new Variable("x")),
                                            typeEnvironment);
        assertEquals(expectedType, receivedType);
    }
    
    @Test(expected = TypeErrorException.class)
    public void testVariableOutOfScope() throws TypeErrorException {
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        emptyTypechecker.typeofVariable(new VariableExp(new Variable("x")),
                                        typeEnvironment);
    }

    @Test
    public void testThisInClass() throws TypeErrorException {
        assertEquals(new ClassNameType(new ClassNameExp( new ClassNameToken("foo"))),
                     emptyTypechecker.typeofThis(new ClassNameExp(new ClassNameToken("foo"))));  //Osher: added token parameter to classnameexp
    }

    @Test(expected = TypeErrorException.class)
    public void testThisNotInClass() throws TypeErrorException {
        emptyTypechecker.typeofThis(null);
    }
}

package parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class ParserTest {
    @Test
    public void testEqualsOpExp() {
        // 1 + 1 == 1 + 1
        final OpExp first = new OpExp(new IntLiteralExp(1),
                                      new PlusOp(),
                                      new IntLiteralExp(1));
        final OpExp second = new OpExp(new IntLiteralExp(1),
                                      new PlusOp(),
                                      new IntLiteralExp(1));
        assertEquals(first, second);
    }



    @Test
    public void testTypeInt() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntToken()));
        assertEquals(new ParseResult<Type>(new IntType(), 1), parser.parseType(0));
    }

    @Test
    public void testTypeVoid() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new VoidToken()));
        assertEquals(new ParseResult<Type>(new VoidType(), 1), parser.parseType(0));
    }
    
    

    @Test
    public void testPrimaryVariable() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new Variable("x")));
        assertEquals(new ParseResult<Exp>(new VariableExp(new Variable("x")),
                                          1),
                     parser.parsePrimaryExp(0));
    }


    @Test
    public void testVarDec() throws ParseException {
        final Parser parser = new Parser(Arrays.asList( new VarDec((new TypeToken("Int"), new Variable("x"))));
        assertEquals(new ParseResult<Stmt>(new VarDecStmt(new VarDec(new TypeToken("Int"), new Variable("x")), new IntLiteralExp(0)),
                                          0),
                     parser.parseVardec(0));
    }



    @Test
    public void testPrimaryInteger() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                     parser.parsePrimaryExp(0));
    }

    @Test
    public void testPrimaryIntegerLiteral() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                     parser.parsePrimaryExp(0));
    }


    @Test
    public void testClassName() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new ClassName("asdfga")));
        assertEquals(new ParseResult<Exp>(new ClassNameType( new ClassName("asdfga")), 1),
                     parser.parseClass(0));
    }

    @Test
    public void testClassNameError() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                     parser.parseClass(0));
    }

    @Test
    public void testMethodName() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new MethodName("asdfga")));
        assertEquals(new ParseResult<Exp>(new MethodNameType( new MethodName("asdfga")), 1),
                     parser.parseMethodName(0));
    }

    @Test
    public void testMethodNameError() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                     parser.parseMethodName(0));
    }

    @Test
    public void testPrimaryLeftParent() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new LeftParenToken(), new IntLiteralToken(123), new RightParenToken()));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123) , 1),
                     parser.parsePrimaryExp(0));
    }

    @Test
    public void testSecondaryThis() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new ThisToken(), new DotToken(), new Variable("f")));
        assertEquals(new ParseResult<Exp>(new ThisExp() , 1),
                     parser.parseSecondaryExp(0));
    }

    @Test
    public void testSecondaryLength() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new LengthToken(), new LeftParenToken(), new Variable("f"), new RightParenToken(), new SemicolonToken()));
        assertEquals(new ParseResult<Exp>(new LengthExp() , 1),
                     parser.parseSecondaryExp(0));
    }

   /* @Test
    public void testSecondaryNew() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new NewToken(), new TypeToken("Int"), new LeftBracketToken(), new Variable("f"), new RightBracketToken()));
        assertEquals(new ParseResult<Exp>(new NewArrayDeclarationExp(new TypeExp(new TypeToken("Int")), new VariableExp( new Variable("f")) ), 1),
                     parser.parseSecondaryExp(0));
    }*/

    @Test
    public void testSecondaryNew() throws ParseException {
        
        final Parser parser = new Parser(Arrays.asList(new NewToken(), new IntToken(), new LeftBracketToken(), new Variable("f"), new RightBracketToken()));
        assertEquals(new ParseResult<Exp>(new NewArrayDeclarationExp(new TypeExp(new TypeToken("Int")), new VariableExp( new Variable("f")) ), 1),
                     parser.parseSecondaryExp(0));
    }

    @Test
    public void testSecondaryError() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123) ));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123) , 1),
                     parser.parseSecondaryExp(0));
    }




    @Test
    public void testPrimaryParens() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new LeftParenToken(),
                                                       new IntLiteralToken(123),
                                                       new RightParenToken()));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 3),
                     parser.parsePrimaryExp(0));
    }

    @Test
    public void testAdditiveOpPlus() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new PlusToken()));
        assertEquals(new ParseResult<Op>(new PlusOp(), 1),
                     parser.parseAdditiveOp(0));
    }
                                                       
    @Test
    public void testAdditiveOpMinus() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new MinusToken()));
        assertEquals(new ParseResult<Op>(new MinusOp(), 1),
                     parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveOpMultiplication() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new MultiplicationToken()));
        assertEquals(new ParseResult<Op>(new MultiplicationOp(), 1),
                     parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveOpDivision() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new DivisionToken()));
        assertEquals(new ParseResult<Op>(new DivisionOp(), 1),
                     parser.parseAdditiveOp(0));
    }


    @Test
    public void testAdditiveExpOnlyPrimary() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                     parser.parseAdditiveExp(0));
    }

    @Test
    public void testAdditiveExpSingleOperator() throws ParseException {
        // 1 + 2
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                                       new PlusToken(),
                                                       new IntLiteralToken(2)));
        assertEquals(new ParseResult<Exp>(new OpExp(new IntLiteralExp(1),
                                                    new PlusOp(),
                                                    new IntLiteralExp(2)),
                                          3),
                     parser.parseAdditiveExp(0));
    }

    @Test
    public void testAdditiveExpMultiOperator() throws ParseException {
        // 1 + 2 - 3 ==> (1 + 2) - 3
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                                       new PlusToken(),
                                                       new IntLiteralToken(2),
                                                       new MinusToken(),
                                                       new IntLiteralToken(3)));
        final Exp expected = new OpExp(new OpExp(new IntLiteralExp(1),
                                                 new PlusOp(),
                                                 new IntLiteralExp(2)),
                                       new MinusOp(),
                                       new IntLiteralExp(3));
        assertEquals(new ParseResult<Exp>(expected, 5),
                     parser.parseAdditiveExp(0));
    }

    @Test
    public void testComparisonOpLessThan() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new LessThanToken()));
        assertEquals(new ParseResult<Op>(new LessThanOp(), 1),
                     parser.parseComparisonOp(0));
    }

    @Test
    public void testComparisonOpGreaterThan() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new GreaterThanToken()));
        assertEquals(new ParseResult<Op>(new GreaterThanOp(), 1),
                     parser.parseComparisonOp(0));
    }
    
    @Test
    public void testComparisonOpEquals() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new EqualsToken()));
        assertEquals(new ParseResult<Op>(new EqualsOp(), 1),
                     parser.parseComparisonOp(0));
    }

    @Test
    public void testComparisonOpNotEquals() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new NotEqualToken()));
        assertEquals(new ParseResult<Op>(new NotEqualOp(), 1),
                     parser.parseComparisonOp(0));
    }

  /*  @Test
    public void testComparisonOpOnly() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntToken(123)));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                     parser.parseComparisonOp(0));
    }
*/
    @Test
    public void testLessThanSingleOperator() throws ParseException {
        // 1 < 2
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                                       new LessThanToken(),
                                                       new IntLiteralToken(2)));
        final Exp expected = new OpExp(new IntLiteralExp(1),
                                       new LessThanOp(),
                                       new IntLiteralExp(2));
        assertEquals(new ParseResult<Exp>(expected, 3),
                     parser.parseLessThanExp(0));
    }

    @Test
    public void testLessThanMultiOperator() throws ParseException {
        // 1 < 2 < 3 ==> (1 < 2) < 3
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                                       new LessThanToken(),
                                                       new IntLiteralToken(2),
                                                       new LessThanToken(),
                                                       new IntLiteralToken(3)));
        final Exp expected = new OpExp(new OpExp(new IntLiteralExp(1),
                                                 new LessThanOp(),
                                                 new IntLiteralExp(2)),
                                       new LessThanOp(),
                                       new IntLiteralExp(3));
        assertEquals(new ParseResult<Exp>(expected, 5),
                     parser.parseLessThanExp(0));
    }

    @Test
    public void testLessThanMixedOperator() throws ParseException {
        // 1 < 2 + 3 ==> 1 < (2 + 3)
        final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                                       new LessThanToken(),
                                                       new IntLiteralToken(2),
                                                       new PlusToken(),
                                                       new IntLiteralToken(3)));
        final Exp expected = new OpExp(new IntLiteralExp(1),
                                       new LessThanOp(),
                                       new OpExp(new IntLiteralExp(2),
                                                 new PlusOp(),
                                                 new IntLiteralExp(3)));
        assertEquals(new ParseResult<Exp>(expected, 5),
                     parser.parseLessThanExp(0));
    }

}
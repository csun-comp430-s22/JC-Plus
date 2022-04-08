package parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

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
    public void testPrimaryVariable() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new Variable("x")));
        assertEquals(new ParseResult<Exp>(new VariableExp(new Variable("x")),
                                          1),
                     parser.parsePrimaryExp(0));
    }

    @Test
    public void testPrimaryInteger() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntToken(123)));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                     parser.parsePrimaryExp(0));
    }

    @Test
    public void testPrimaryParens() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new LeftParenToken(),
                                                       new IntToken(123),
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
    public void testAdditiveExpOnlyPrimary() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntToken(123)));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                     parser.parseAdditiveExp(0));
    }

    @Test
    public void testAdditiveExpSingleOperator() throws ParseException {
        // 1 + 2
        final Parser parser = new Parser(Arrays.asList(new IntToken(1),
                                                       new PlusToken(),
                                                       new IntToken(2)));
        assertEquals(new ParseResult<Exp>(new OpExp(new IntLiteralExp(1),
                                                    new PlusOp(),
                                                    new IntLiteralExp(2)),
                                          3),
                     parser.parseAdditiveExp(0));
    }

    @Test
    public void testAdditiveExpMultiOperator() throws ParseException {
        // 1 + 2 - 3 ==> (1 + 2) - 3
        final Parser parser = new Parser(Arrays.asList(new IntToken(1),
                                                       new PlusToken(),
                                                       new IntToken(2),
                                                       new MinusToken(),
                                                       new IntToken(3)));
        final Exp expected = new OpExp(new OpExp(new IntLiteralExp(1),
                                                 new PlusOp(),
                                                 new IntLiteralExp(2)),
                                       new MinusOp(),
                                       new IntLiteralExp(3));
        assertEquals(new ParseResult<Exp>(expected, 5),
                     parser.parseAdditiveExp(0));
    }

    @Test
    public void testLessThanExpOnlyAdditive() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntToken(123)));
        assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                     parser.parseLessThanExp(0));
    }

    @Test
    public void testLessThanSingleOperator() throws ParseException {
        // 1 < 2
        final Parser parser = new Parser(Arrays.asList(new IntToken(1),
                                                       new LessThanToken(),
                                                       new IntToken(2)));
        final Exp expected = new OpExp(new IntLiteralExp(1),
                                       new LessThanOp(),
                                       new IntLiteralExp(2));
        assertEquals(new ParseResult<Exp>(expected, 3),
                     parser.parseLessThanExp(0));
    }

    @Test
    public void testLessThanMultiOperator() throws ParseException {
        // 1 < 2 < 3 ==> (1 < 2) < 3
        final Parser parser = new Parser(Arrays.asList(new IntToken(1),
                                                       new LessThanToken(),
                                                       new IntToken(2),
                                                       new LessThanToken(),
                                                       new IntToken(3)));
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
        final Parser parser = new Parser(Arrays.asList(new IntToken(1),
                                                       new LessThanToken(),
                                                       new IntToken(2),
                                                       new PlusToken(),
                                                       new IntToken(3)));
        final Exp expected = new OpExp(new IntLiteralExp(1),
                                       new LessThanOp(),
                                       new OpExp(new IntLiteralExp(2),
                                                 new PlusOp(),
                                                 new IntLiteralExp(3)));
        assertEquals(new ParseResult<Exp>(expected, 5),
                     parser.parseLessThanExp(0));
    }

}
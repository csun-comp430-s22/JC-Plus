package lexer;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TokenizerTest {

    public void assertTokenizes(final String input, final Token[] expected) throws TokenizerException {

        final Tokenizer tokenizer = new Tokenizer(input);
        final List<Token> received = tokenizer.tokenize();
        assertArrayEquals(expected,
                received.toArray(new Token[received.size()]));

    }

    @Test
    public void testEmptyString() throws TokenizerException {

        // Check that tokenizing empty string works
        assertTokenizes("", new Token[0]);

    }

    @Test
    public void testOnlyWhiteSpace() throws TokenizerException {

        // Check that tokenizing only white space works
        assertTokenizes("     ", new Token[0]);

    }


    @Test
    public void testAssignmentByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("=", new Token[] { new AssignmentToken() });
    }

    @Test
    public void testBreakByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("break", new Token[] { new BreakToken() });
    }

    @Test
    public void testClassByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("class", new Token[] { new ClassToken() });
    }
    @Test
    public void testDivisionByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("/", new Token[] { new DivisionToken() });
    }

    @Test
    public void testDotByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes(".", new Token[] { new DotToken() });
    }

    @Test
    public void testElseByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("else", new Token[] { new ElseToken() });
    }

    @Test
    public void testEqualsByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("==", new Token[] { new EqualsToken() });
    }

    @Test
    public void testExtendsByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("extends", new Token[] { new ExtendsToken() });
    }

    @Test
    public void testGreaterThanByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes(">", new Token[] { new GreaterThanToken() });
    }

    @Test
    public void testIfByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("if", new Token[] { new IfToken() });
    }

    @Test
    public void testIntByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("Int", new Token[] { new IntToken() });
    }

    @Test
    public void testLeftBracketByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("[", new Token[] { new LeftBracketToken() });
    }

    @Test
    public void testLeftCurlyByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("{", new Token[] { new LeftCurlyToken() });
    }

    @Test
    public void testLeftParenthesisByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("(", new Token[] { new LeftParenthesisToken() });
    }

    @Test
    public void testLengthByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("len", new Token[] { new LengthToken() });
    }

    @Test
    public void testLessThanByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("<", new Token[] { new LessThanToken() });
    }

    @Test
    public void testMinusByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("-", new Token[] { new MinusToken() });
    }

    @Test
    public void testMultiplicationByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("*", new Token[] { new MultiplicationToken() });
    }

    @Test
    public void testNewByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("new", new Token[] { new NewToken() });
    }

    @Test
    public void testNotEqualByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("!=", new Token[] { new NotEqualToken() });
    }

    @Test
    public void testPlusByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("+", new Token[] { new PlusToken() });
    }

    @Test
    public void testPrintByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("println", new Token[] { new PrintToken() });
    }

    @Test
    public void testReturnByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("return", new Token[] { new ReturnToken() });
    }

    @Test
    public void testRightBracketByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("]", new Token[] { new RightBracketToken() });
    }

    @Test
    public void testRightCurlyByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("}", new Token[] { new RightCurlyToken() });
    }

    @Test
    public void testRightParenthesisByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes(")", new Token[] { new RightParenthesisToken() });
    }

    @Test
    public void testSemiColonByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes(";", new Token[] { new SemiColonToken() });
    }

    @Test
    public void testThisByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("this", new Token[] { new ThisToken() });
    }

    @Test
    public void testVoidByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("Void", new Token[] { new VoidToken() });
    }

    @Test
    public void testWhileByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("while", new Token[] { new WhileToken() });
    }



    // TODO: add the rests of the token tests such as above ^

    @Test
    public void testVariable() throws TokenizerException {
        assertTokenizes("foo",
                        new Token[] { new VariableToken("foo") });
    }

    @Test
    public void testThisThisIsVariable() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("thisthis", new Token[] { new VariableToken("thisthis") });
    }

    @Test
    public void testThisSpaceThisIsVariable() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("this this", new Token[] { new ThisToken(), new ThisToken() });
    }

    @Test
    public void testMultiDigitInteger() throws TokenizerException {
        assertTokenizes("123", new Token[] { new IntegerToken(123) });
    }

    @Test
    public void testSingleDigitInteger() throws TokenizerException {
        assertTokenizes("1", new Token[] { new IntegerToken(1) });
    }

    @Test
    public void testAllRemaining() throws TokenizerException { // this works but not a good diagnostic way of unit
                                                               // testing. Either do them
        // individually or keep if diagnostic not a priority
        assertTokenizes("(){}else if", new Token[] {
                new LeftParenthesisToken(),
                new RightParenthesisToken(),
                new LeftCurlyToken(),
                new RightCurlyToken(),
                new ElseToken(),
                new IfToken(),
                
        });
    }

    @Test(expected = TokenizerException.class)
    public void testInvalid() throws TokenizerException {
        assertTokenizes("$", null);
    }

}

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
    public void testThisByItself() throws TokenizerException {

        // Check that tokenizing "this" works
        assertTokenizes("this", new Token[] { new ThisToken() });
    }
    // TODO: add the rests of the token tests such as above ^

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

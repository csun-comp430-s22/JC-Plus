package lexer;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TokenizerTest {

    public void assertTokenizes(final String input, final Token[] expected) {
        try {
            final Tokenizer tokenizer = new Tokenizer(input);
            final List<Token> received = tokenizer.tokenize();
            assertArrayEquals(expected,
                    received.toArray(new Token[received.size()]));
        } catch (final TokenizerException e) {
            fail("Tokenizer threw exception");
        }
    }

    @Test
    public void testEmptyString() {

        // Check that tokenizing empty string works
        assertTokenizes("", new Token[0]);

    }

    @Test
    public void testOnlyWhiteSpace() {

        // Check that tokenizing only white space works
        assertTokenizes("     ", new Token[0]);

    }

    @Test
    public void testThisByItself() {

        // Check that tokenizing "this" works
        assertTokenizes("this", new Token[] { new ThisToken() });
    }
    // TODO: add the rests of the token tests such as above ^

    @Test
    public void testThisThisIsVariable() {

        // Check that tokenizing "this" works
        assertTokenizes("thisthis", new Token[] { new VariableToken("thisthis") });
    }

    @Test
    public void testThisSpaceThisIsVariable() {

        // Check that tokenizing "this" works
        assertTokenizes("this this", new Token[] { new ThisToken(), new ThisToken() });
    }


    @Test
    public void testMultiDigitInteger() {
        assertTokenizes("123", new Token[] { new IntegerToken(123)});
    }
    @Test
    public void testSingleDigitInteger() {
        assertTokenizes("1", new Token[] { new IntegerToken(1)});
    }
    
    @Test
    public void testAllRemaining() {
        assertTokenizes("(){}else if", new Token[] {
                new LeftParenthesisToken(),
                new RightParenthesisToken(),
                new LeftCurlyToken(),
                new RightCurlyToken(),
                new ElseToken(),
                new IfToken(),
        });
    }

}

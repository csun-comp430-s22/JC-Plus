package lexer;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TokenizerTest {

    @Test //annotation
    public void testEmptyString() throws TokenizerException {
        Tokenizer tokenizer = new Tokenizer("");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(0, tokens.size());

    }

    @Test
    public void testOnlyWhiteSpace() throws TokenizerException {
        Tokenizer tokenizer = new Tokenizer("     ");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(0, tokens.size());

    }

    @Test
    public void testThisByItself() throws TokenizerException {
        Tokenizer tokenizer = new Tokenizer("this");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(1, tokens.size());
        Token thisToken = tokens.get(0);
        assertTrue(thisToken instanceof ThisToken);
    }
    //TODO: add the rests of the token tests such as above ^

}

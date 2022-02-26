import java.util.List;

public class TokenizerTest {

    public static void main(String[] args) throws TokenizerException {
        // check that tokenizing empty string works
        Tokenizer tokenizer = new Tokenizer("");
        List<Token> tokens = tokenizer.tokenize();
        assert (tokens.size() == 0);

    }
}

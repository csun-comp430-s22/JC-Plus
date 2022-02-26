import java.util.List;
import java.util.ArrayList;

public class Tokenizer {

    private final String input;
    private int offset;

    public Tokenizer(final String input) {
        this.input = input;
        offset = 0;
    }

    public void skipWhitespace() {
        while (Character.isWhitespace((input.charAt(offset)))) {
            offset++;
        }
    }

    // returns null if there are no more tokens left
    public Token tokenizeSingle() throws TokenizerException {

        // skip whitespace - breaks for trailing whitespace
        skipWhitespace();

        if (offset < input.length()) {
            if (input.startsWith("true", offset)) {
                offset += 4;
                return new TrueToken();

            } else if (input.startsWith("false", offset)) {
                offset += 5;
                return new FalseToken();

            }
            // else if .. //TODO: add all different types of tokens similar to above
            else {
                throw new TokenizerException();
            }
        } else {
            return null;
        }

    }

    public List<Token> tokenize() throws TokenizerException {

        final List<Token> tokens = new ArrayList<Token>();
        Token token = tokenizeSingle();

        while (token != null) {
            tokens.add(token);
            token = tokenizeSingle();
        }
        while (offset < input.length()) {

            if (token != null)
                tokens.add(tokenizeSingle());
        }

        return tokens;
    }

}

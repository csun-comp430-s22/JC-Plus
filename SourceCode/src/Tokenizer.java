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
            if (input.startsWith("]", offset)) {
                offset += 1;
                return new RightBracket();

            } else if (input.startsWith("[", offset)) {
                offset += 1;
                return new LeftBracket();

            } else if (input.startsWith("}", offset)) {
                offset += 1;
                return new RightCurly();

            } else if (input.startsWith("{", offset)) {
                offset += 1;
                return new LeftCurly();

            } else if (input.startsWith(")", offset)) {
                offset += 1;
                return new RightParenthesis();

            } else if (input.startsWith("(", offset)) {
                offset += 1;
                return new LeftParenthesis();

            } else if (input.startsWith("println", offset)) {
                offset += 7;
                return new PrintToken();

            } else if (input.startsWith("+", offset)) {
                offset += 1;
                return new PlusToken();

            } else if (input.startsWith("-", offset)) {
                offset += 1;
                return new MinusToken();

            } else if (input.startsWith("*", offset)) {
                offset += 1;
                return new MultiplicationToken();

            } else if (input.startsWith("/", offset)) {
                offset += 1;
                return new DivisionToken();

            } else if (input.startsWith("if", offset)) {
                offset += 2;
                return new ifToken();

            } else if (input.startsWith("else", offset)) {
                offset += 4;
                return new elseToken();

            } else if (input.startsWith("while", offset)) {
                offset += 5;
                return new whileToken();

            } else if (input.startsWith("break", offset)) {
                offset += 5;
                return new breakToken();

            } else if (input.startsWith("return", offset)) {
                offset += 6;
                return new returnToken();

            } else if (input.startsWith("Void", offset)) {
                offset += 4;
                return new voidToken();

            } else if (input.startsWith("Int", offset)) {
                offset += 3;
                return new intToken();

            } else if (input.startsWith("len", offset)) {
                offset += 3;
                return new lengthToken();

            } else if (input.startsWith("this", offset)) {
                offset += 4;
                return new thisToken();

            } else if (input.startsWith("new", offset)) {
                offset += 3;
                return new newToken();

            } else if (input.startsWith("class", offset)) {
                offset += 5;
                return new classToken();

            } else if (input.startsWith("extends", offset)) {
                offset += 7;
                return new extendsToken();

            } else if (input.startsWith(";", offset)) {
                offset += 1;
                return new semiColonToken();

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

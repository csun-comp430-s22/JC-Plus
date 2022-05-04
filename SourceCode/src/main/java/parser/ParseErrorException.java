package parser;

public class ParseErrorException extends Exception {
    public ParseErrorException(final String message) {
        super(message);
    }
}
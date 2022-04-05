package parser;

public class LeftBracketToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof LeftBracketToken;
    }

    public int hashCode() {
        return 1;
    }
    
    public String toString() {
        return "LeftBracketToken";
    }
}

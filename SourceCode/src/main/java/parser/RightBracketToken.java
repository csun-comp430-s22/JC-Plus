package parser;

public class RightBracketToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof RightBracketToken;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "RightBracketToken";
    }
}

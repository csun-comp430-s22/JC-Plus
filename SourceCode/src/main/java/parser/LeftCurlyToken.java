package parser;

public class LeftCurlyToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof LeftCurlyToken;
    }

    public int hashCode() {
        return 8;
    }
    
    public String toString() {
        return "LeftCurlyToken";
    }
}

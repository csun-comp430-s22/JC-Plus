package parser;

public class ExtendsToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof ExtendsToken;
    }

    public int hashCode() {
        return 22;
    }

    public String toString() {
        return "ExtendsToken";
    }
}

package parser;

public class LeftParenToken implements Token {
    public final String name;

    public LeftParenToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof LeftParenToken &&
                name.equals(((LeftParenToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "LeftParenToken(" + name + ")";
    }
}

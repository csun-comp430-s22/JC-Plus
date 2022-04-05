package parser;

public class RightParenToken implements Token {
    public final String name;

    public RightParenToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof RightParenToken &&
                name.equals(((RightParenToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "RightParenToken(" + name + ")";
    }
}

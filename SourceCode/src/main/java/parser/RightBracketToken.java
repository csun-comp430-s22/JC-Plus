package parser;

public class RightBracketToken implements Token {
    public final String name;

    public RightBracketToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof RightBracketToken &&
                name.equals(((RightBracketToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "RightBracketToken(" + name + ")";
    }
}

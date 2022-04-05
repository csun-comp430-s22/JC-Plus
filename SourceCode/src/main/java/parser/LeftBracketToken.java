package parser;

public class LeftBracketToken implements Token {
    public final String name;

    public LeftBracketToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof LeftBracketToken &&
                name.equals(((LeftBracketToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "LeftBracketToken(" + name + ")";
    }
}

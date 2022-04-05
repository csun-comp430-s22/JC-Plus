package parser;

public class BreakToken implements Token {
    public final String name;

    public BreakToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof BreakToken &&
                name.equals(((BreakToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "BreakToken(" + name + ")";
    }
}

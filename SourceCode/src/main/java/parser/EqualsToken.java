package parser;

public class EqualsToken implements Token {
    public final String name;

    public EqualsToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof EqualsToken &&
                name.equals(((EqualsToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "EqualsToken(" + name + ")";
    }
}

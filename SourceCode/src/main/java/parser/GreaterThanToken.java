package parser;

public class GreaterThanToken implements Token {
    public final String name;

    public GreaterThanToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof GreaterThanToken &&
                name.equals(((GreaterThanToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "GreaterThanToken(" + name + ")";
    }
}

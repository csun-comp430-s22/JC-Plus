package parser;

public class LessThanToken implements Token {
    public final String name;

    public LessThanToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof LessThanToken &&
                name.equals(((LessThanToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "LessThanToken(" + name + ")";
    }
}

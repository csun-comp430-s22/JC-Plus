package parser;

public class DivisionToken implements Token {
    public final String name;

    public DivisionToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof DivisionToken &&
                name.equals(((DivisionToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "DivisionToken(" + name + ")";
    }
}

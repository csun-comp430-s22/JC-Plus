package parser;

public class LenToken implements Token {
    public final String name;

    public LenToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof LenToken &&
                name.equals(((LenToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "LenToken(" + name + ")";
    }
}

package parser;

public class LeftCurlyToken implements Token {
    public final String name;

    public LeftCurlyToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof LeftCurlyToken &&
                name.equals(((LeftCurlyToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "LeftCurlyToken(" + name + ")";
    }
}

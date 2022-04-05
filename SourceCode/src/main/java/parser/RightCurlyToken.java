package parser;

public class RightCurlyToken implements Token {
    public final String name;

    public RightCurlyToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof RightCurlyToken &&
                name.equals(((RightCurlyToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "RightCurlyToken(" + name + ")";
    }
}

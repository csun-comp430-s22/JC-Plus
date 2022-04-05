package parser;

public class NotEqualToken implements Token {
    public final String name;

    public NotEqualToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof NotEqualToken &&
                name.equals(((NotEqualToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "NotEqualToken(" + name + ")";
    }
}

package parser;

public class VoidToken implements Token {
    public final int value;

    public VoidToken(final int value) {
        this.value = value;
    }

    public boolean equals(final VoidToken other) {
        return (other instanceof VoidToken &&
                value == ((VoidToken) other).value);
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return "VoidToken(" + value + ")";
    }
}

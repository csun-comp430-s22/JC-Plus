package parser;

public class IntLiteralToken implements Token {
    public final int value;

    public IntLiteralToken(final int value) {
        this.value = value;
    }

    public boolean equals(final IntLiteralToken other) {
        return (other instanceof IntLiteralToken &&
                value == ((IntLiteralToken) other).value);
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return "IntLiteralToken(" + value + ")";
    }
}

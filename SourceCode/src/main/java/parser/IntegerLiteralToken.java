package parser;

public class IntegerLiteralToken implements Token {
    public final int value;

    public IntegerLiteralToken(final int value) {
        this.value = value;
    }

    public boolean equals(final IntegerLiteralToken other) {
        return (other instanceof IntegerLiteralToken &&
                value == ((IntegerLiteralToken)other).value);
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return "IntegerLiteralToken(" + value + ")";
    }
}

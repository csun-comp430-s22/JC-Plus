package parser;

public class IntToken implements Token {
    public final int value;

    public IntToken(final int value) {
        this.value = value;
    }

    public boolean equals(final IntToken other) {
        return (other instanceof IntToken &&
                value == ((IntToken)other).value);
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return "IntToken(" + value + ")";
    }
}
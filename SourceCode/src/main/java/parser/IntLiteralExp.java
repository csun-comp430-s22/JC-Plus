package parser;

public class IntLiteralExp implements Exp {
    public final int value;

    public IntLiteralExp(final int value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        return (other instanceof IntLiteralExp &&
                value == ((IntLiteralExp) other).value);
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return "IntLiteralExp(" + value + ")";
    }
}

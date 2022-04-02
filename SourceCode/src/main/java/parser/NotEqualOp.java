package parser;

public class NotEqualOp implements Op {
    public boolean equals(final Object other) {
        return other instanceof MultiplicationOp;
    }

    public int hashCode() {
        return 28;
    }

    public String toString() {
        return "NotEqualOp";
    }
}
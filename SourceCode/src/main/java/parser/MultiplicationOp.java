package parser;

public class MultiplicationOp implements Op {
    public boolean equals(final Object other) {
        return other instanceof MultiplicationOp;
    }

    public int hashCode() {
        return 9;
    }

    public String toString() {
        return "MultiplicationOp";
    }
}

package parser;

public class PlusOp implements Op {
    public boolean equals(final Object other) {
        return other instanceof PlusOp;
    }

    public int hashCode() {
        return 7;
    }

    public String toString() {
        return "PlusOp";
    }
}

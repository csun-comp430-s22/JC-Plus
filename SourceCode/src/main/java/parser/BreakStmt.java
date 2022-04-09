package parser;

public class BreakStmt implements Stmt {
    public int hashCode() {
        return 14;
    }

    public boolean equals(final Object other) {
        return other instanceof BreakStmt;
    }

    public String toString() {
        return "BreakStmt";
    }
}
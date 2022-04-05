package parser;

public class AssignmentStmt implements Stmt {
    public final Exp exp;

    public AssignmentStmt(final Exp exp) {
        this.exp = exp;
    }

    public int hashCode() {
        return exp.hashCode();
    }
    
    public boolean equals(final Object other) {
        return (other instanceof AssignmentStmt &&
                exp.equals(((AssignmentStmt)other).exp));
    }

    public String toString() {
        return "AssignmentStmt(" + exp.toString() + ")";
    }
}
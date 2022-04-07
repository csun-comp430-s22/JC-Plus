package parser;

public class AssignmentStmt implements Stmt {
    public final VariableToken var;
    public final Exp exp;

    public AssignmentStmt(final VariableToken var,
            final Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    public int hashCode() {
        return var.hashCode() + exp.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof AssignmentStmt) {
            final AssignmentStmt otherVar = (AssignmentStmt) other;
            return (var.equals(otherVar.var) &&
                    exp.equals(otherVar.exp));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("AssignmentStmt(" +
                var.toString() + ", " +
                exp.toString() + ")");
    }
}
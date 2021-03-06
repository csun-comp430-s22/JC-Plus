package parser;

public class VariableInitializationStmt implements Stmt {
    public final VarDec vardec;
    public final Exp exp;

    public VariableInitializationStmt(final VarDec vardec,
            final Exp exp) {
        this.vardec = vardec;
        this.exp = exp;
    }

    public int hashCode() {
        return vardec.hashCode() + exp.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof VariableInitializationStmt) {
            final VariableInitializationStmt otherVar = (VariableInitializationStmt) other;
            return (vardec.equals(otherVar.vardec) &&
                    exp.equals(otherVar.exp));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("VariableInitializationStmt(" +
                vardec.toString() + ", " +
                exp.toString() + ")");
    }
}
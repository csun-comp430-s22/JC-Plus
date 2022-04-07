package parser;


public class VarDecStmt implements Stmt {
    public final VarDec vardec;
    public final Exp exp;

    public VarDecStmt(final VarDec vardec,
                      final Exp exp) {
        this.vardec = vardec;
        this.exp = exp;
    }

    public int hashCode() {
        return vardec.hashCode() + exp.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof VarDecStmt) {
            final VarDecStmt otherStmt = (VarDecStmt)other;
            return (vardec.equals(otherStmt.vardec) &&
                    exp.equals(otherStmt.exp));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("VarDecStmt(" + vardec.toString() + ", " +
                exp.toString() + ")");
    }
}

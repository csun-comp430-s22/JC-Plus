package parser;


public class InstanceDec implements Stmt {
    public final VarDec vardec;
    public final Exp exp;

    public InstanceDec(final VarDec vardec,
                      final Exp exp) {
        this.vardec = vardec;
        this.exp = exp;
    }

    public int hashCode() {
        return vardec.hashCode() + exp.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof InstanceDec) {
            final InstanceDec otherStmt = (InstanceDec)other;
            return (vardec.equals(otherStmt.vardec) &&
                    exp.equals(otherStmt.exp));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("InstanceDec(" + vardec.toString() + ", " +
                exp.toString() + ")");
    }
}

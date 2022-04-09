package parser;

import java.util.List;

public class ExpMethodNameStmt implements Exp {

    public final Exp exp;
    public final Exp methodName;
    public final List<Exp> params;

    public ExpMethodNameStmt(final Exp exp,
            final Exp methodName,
            final List<Exp> params) {
        this.exp = exp;
        this.methodName = methodName;
        this.params = params;
    }

    public int hashCode() {
        return (exp.hashCode() +
                methodName.hashCode() +
                params.hashCode());
    }

    public boolean equals(final Object other) {
        if (other instanceof ExpMethodNameStmt) {
            final ExpMethodNameStmt call = (ExpMethodNameStmt) other;
            return (exp.equals(call.exp) &&
                    methodName.equals(call.methodName) &&
                    params.equals(call.params));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("ExpMethodNameStmt(" + exp.toString() + ", " +
                methodName.toString() + ", " +
                params.toString() + ")");
    }

}

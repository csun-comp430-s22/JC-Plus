package parser;

import java.util.List;

public class ExpMethodNameExp implements Exp {

    public final Exp exp;
    public final MethodName methodName;
    public final List<Exp> params;

    public ExpMethodNameExp(final Exp exp,
            final MethodName methodName,
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
        if (other instanceof ExpMethodNameExp) {
            final ExpMethodNameExp call = (ExpMethodNameExp) other;
            return (exp.equals(call.exp) &&
                    methodName.equals(call.methodName) &&
                    params.equals(call.params));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("ExpMethodNameExp(" + exp.toString() + ", " +
                methodName.toString() + ", " +
                params.toString() + ")");
    }

}

package parser;

public class ArrayExp implements Exp {
    public final VariableExp variable;
    public final Exp index;

    public ArrayExp(final VariableExp variable, final Exp index) {
        this.variable = variable;
        this.index = index;
    }

    public boolean equals(final Object other) {
        if (other instanceof ArrayExp) {
            final ArrayExp otherExp = (ArrayExp) other;
            return (variable.equals(otherExp.variable) &&
                    index.equals(otherExp.index));
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (variable.hashCode() +
                index.hashCode());
    }

    public String toString() {
        return ("ArrayExp(" +
                variable.toString() + ", " +
                index.toString() + ", " +
                ")");
    }
}

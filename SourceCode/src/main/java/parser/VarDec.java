package parser;

public class VarDec implements Token{
    public final TypeToken type;
    public final Variable variable;

    public VarDec(final TypeToken type,
                  final Variable variable) {
        this.type = type;
        this.variable = variable;
    }

    public int hashCode() {
        return type.hashCode() + variable.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof VarDec) {
            final VarDec otherVar = (VarDec)other;
            return (type.equals(otherVar.type) &&
                    variable.equals(otherVar.variable));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("VarDec(" + type.toString() + ", " +
                variable.toString() + ")");
    }
}

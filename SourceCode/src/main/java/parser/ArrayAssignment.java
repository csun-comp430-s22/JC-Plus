package parser;

public class ArrayAssignment implements Stmt {
    public final VariableExp variable;
    public final Exp index;
    public final Exp assignThis;

    public ArrayAssignment(final VariableExp variable, final Exp index, final Exp assignThis) {
        this.variable = variable;
        this.index = index;
        this.assignThis = assignThis;
    }

public boolean equals(final Object other) {
if (other instanceof ArrayAssignment) {
final ArrayAssignment otherExp = (ArrayAssignment)other;
return (variable.equals(otherExp.variable) &&
       index.equals(otherExp.index) &&
       assignThis.equals(otherExp.assignThis));
} else {
return false;
}
}

public int hashCode() {
return (variable.hashCode() +
   index.hashCode() +
   assignThis.hashCode());
}

public String toString() {
return ("ArrayAssignment(" +
   variable.toString() + ", " +
   index.toString() + ", " +
   assignThis.toString() + ")");
}
}

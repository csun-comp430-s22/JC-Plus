package parser;
import java.util.List;
public class NewArrayDeclarationExp implements Exp {
    public final Type type;
    public final Exp exp;

    public NewArrayDeclarationExp(final Type type,
                  final Exp exp) {
        this.type = type;
        this.exp = exp;
    }

    public int hashCode() {
        return type.hashCode() + exp.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof NewArrayDeclarationExp) {
            final NewArrayDeclarationExp otherNew = (NewArrayDeclarationExp)other;
            return (type.equals(otherNew.type) &&
            exp.equals(otherNew.exp));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("NewArrayDeclarationExp(" + type.toString() + ", " +
        exp.toString() + ")");
    }
}

}

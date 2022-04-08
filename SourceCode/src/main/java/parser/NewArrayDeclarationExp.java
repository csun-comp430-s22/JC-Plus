package parser;
import java.util.List;
public class NewArrayDeclarationExp implements Exp {
    public final IntType intType;
    public final List<Exp> params;

    public NewArrayDeclarationExp(final IntType intType,
                  final List<Exp> params) {
        this.intType = intType;
        this.params = params;
    }

    public int hashCode() {
        return intType.hashCode() + params.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof NewArrayDeclarationExp) {
            final NewArrayDeclarationExp otherNew = (NewArrayDeclarationExp)other;
            return (intType.equals(otherNew.intType) &&
                    params.equals(otherNew.params));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("NewArrayDeclarationExp(" + intType.toString() + ", " +
                params.toString() + ")");
    }
}

}

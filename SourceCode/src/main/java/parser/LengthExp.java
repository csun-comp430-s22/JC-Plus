package parser;

public class LengthExp implements Exp {
    public final Exp exp;

    public LengthExp(final Exp exp) {
        this.exp = exp;
    }

    public int hashCode() {
        return exp.hashCode();
    }

    public boolean equals(final Object other) {
        return (other instanceof LengthExp &&
                exp.equals(((LengthExp) other).exp));
    }

    public String toString() {
        return "LenExp(" + exp.toString() + ")";
    }
}
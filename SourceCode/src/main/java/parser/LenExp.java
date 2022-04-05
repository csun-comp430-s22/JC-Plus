package parser;

public class LenExp implements Exp {
    public final Exp exp;

    public LenExp(final Exp exp) {
        this.exp = exp;
    }

    public int hashCode() {
        return exp.hashCode();
    }

    public boolean equals(final Object other) {
        return (other instanceof LenExp &&
                exp.equals(((LenExp) other).exp));
    }

    public String toString() {
        return "LenExp(" + exp.toString() + ")";
    }
}
package parser;

public class NewExp implements Exp {
    public final Exp exp;

    public NewExp(final Exp exp) {
        this.exp = exp;
    }

    public int hashCode() {
        return exp.hashCode();
    }

    public boolean equals(final Object other) {
        return (other instanceof NewExp &&
                exp.equals(((NewExp) other).exp));
    }

    public String toString() {
        return "NewExp(" + exp.toString() + ")";
    }
}
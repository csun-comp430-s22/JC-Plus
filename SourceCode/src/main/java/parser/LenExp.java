package parser;

public class LenExp implements Exp {
    public final String name;

    public LenExp(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof LenExp &&
                name.equals(((LenExp) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "LenExp(" + name + ")";
    }
}

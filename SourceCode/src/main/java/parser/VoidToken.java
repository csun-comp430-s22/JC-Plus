package parser;

public class VoidToken implements Token {
    public final String name;

    public VoidToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof VoidToken &&
                name.equals(((VoidToken)other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "VoidToken(" + name + ")";
    }
}

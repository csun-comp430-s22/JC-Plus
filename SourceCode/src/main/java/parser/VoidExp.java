package parser;

public class VoidExp implements Exp {
    public final String name;

    public VoidExp(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof VoidExp &&
                name.equals(((VoidExp) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "VoidExp(" + name + ")";
    }
}

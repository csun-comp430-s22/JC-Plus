package parser;

public class ThisExp implements Exp {
    public final String name;

    public ThisExp(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof ThisExp &&
                name.equals(((ThisExp) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "ThisExp(" + name + ")";
    }
}

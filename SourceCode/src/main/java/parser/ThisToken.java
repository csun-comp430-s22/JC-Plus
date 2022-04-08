package parser;

public class ThisToken implements Token {
    public final String name;

    public ThisToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof ThisToken &&
                name.equals(((ThisToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "ThisToken(" + name + ")";
    }
}

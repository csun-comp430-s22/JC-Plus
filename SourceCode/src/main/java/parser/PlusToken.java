package parser;

public class PlusToken implements Token {
    public final String name;

    public PlusToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof PlusToken &&
                name.equals(((PlusToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "PlusToken(" + name + ")";
    }
}

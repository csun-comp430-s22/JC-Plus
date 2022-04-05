package parser;

public class IfToken implements Token {
    public final String name;

    public IfToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof IfToken &&
                name.equals(((IfToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "IfToken(" + name + ")";
    }
}

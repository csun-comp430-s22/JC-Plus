package parser;

public class SemicolonToken implements Token {
    public final String name;

    public SemicolonToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof SemicolonToken &&
                name.equals(((SemicolonToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "SemicolonToken(" + name + ")";
    }
}

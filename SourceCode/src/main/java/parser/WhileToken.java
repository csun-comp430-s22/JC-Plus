package parser;

public class WhileToken implements Token {
    public final String name;

    public WhileToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof WhileToken &&
                name.equals(((WhileToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "WhileToken(" + name + ")";
    }
}

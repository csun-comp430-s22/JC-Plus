package parser;

public class ElseToken implements Token {
    public final String name;

    public ElseToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof ElseToken &&
                name.equals(((ElseToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "ElseToken(" + name + ")";
    }
}

package parser;

public class MinusToken implements Token {
    public final String name;

    public MinusToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof MinusToken &&
                name.equals(((MinusToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "MinusToken(" + name + ")";
    }
}

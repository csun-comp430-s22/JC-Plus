package parser;

public class MultiplicationToken implements Token {
    public final String name;

    public MultiplicationToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof MultiplicationToken &&
                name.equals(((MultiplicationToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "MultiplicationToken(" + name + ")";
    }
}

package parser;

public class PrintlnToken implements Token {
    public final String name;

    public PrintlnToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof PrintlnToken &&
                name.equals(((PrintlnToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "PrintlnToken(" + name + ")";
    }
}

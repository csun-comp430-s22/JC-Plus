package parser;

public class ReturnToken implements Token {
    public final String name;

    public ReturnToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof ReturnToken &&
                name.equals(((ReturnToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "ReturnToken(" + name + ")";
    }
}

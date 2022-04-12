package parser;

public class ClassNameToken implements Token {
    public final String name;

    public ClassNameToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof ClassNameToken &&
                name.equals(((ClassNameToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return name;
    }
}

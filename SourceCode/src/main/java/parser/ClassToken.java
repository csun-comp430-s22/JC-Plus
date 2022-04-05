package parser;

public class ClassToken implements Token {
    public final String name;

    public ClassToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof ClassToken &&
                name.equals(((ClassToken)other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "ClassToken(" + name + ")";
    }
}

package parser;

public class TypeToken implements Token {
    public final String name;

    public TypeToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof TypeToken &&
                name.equals(((TypeToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "TypeToken(" + name + ")";
    }
}

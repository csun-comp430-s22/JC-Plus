package parser;

public class TypeExp implements Exp {
    public final String name;

    public TypeExp(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof TypeExp &&
                name.equals(((TypeExp) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "TypeExp(" + name + ")";
    }
}

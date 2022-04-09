package parser;

public class TypeExp implements Type {
    public final TypeToken name;

    public TypeExp(final TypeToken typeToken) {
        this.name = typeToken;
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

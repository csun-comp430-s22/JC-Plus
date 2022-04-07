package parser;

public class VoidType implements Type {
    public int hashCode() {
        return 16;
    }

    public boolean equals(final Object other) {
        return other instanceof VoidType;
    }

    public String toString() {
        return "VoidType";
    }
}
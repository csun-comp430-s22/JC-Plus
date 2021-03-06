package parser;

public class IntType implements Type {
    public int hashCode() {
        return 17;
    }

    public boolean equals(final Object other) {
        return other instanceof IntType;
    }

    public String toString() {
        return "IntType";
    }
}

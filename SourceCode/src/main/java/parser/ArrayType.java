package parser;

public class ArrayType implements Type {
    public int hashCode() {
        return 0;
    }

    public boolean equals(final Object other) {
        return other instanceof ArrayType;
    }

    public String toString() {
        return "ArrayType";
    }

}
